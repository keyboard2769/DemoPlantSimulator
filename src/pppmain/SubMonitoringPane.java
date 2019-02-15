/*
 * Copyright (C) 2019 Key Parker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pppmain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTable;
import pppunit.ScGauge;
import ppptable.ScAutoWeighViewer;
import ppptable.McAutoWeighLogger;
import ppptable.McTrendLogger;
import ppptable.McWorkerManager;
import static ppptable.McCurrentSlotModel.C_CAPA;

public class SubMonitoringPane extends JPanel implements ActionListener{
  
  private static SubMonitoringPane self=null;
  public static final SubMonitoringPane ccGetReference(){
    if(self==null){self=new SubMonitoringPane();}
    return self;
  }//++!
  
  //===
  
  public final ScGauge[] cmLesCurrentBar;
  
  public JComboBox<String> 
    cmTrendUpdateCB,cmTrendSavingCB
  ;//...
  
  public final ScTable 
    cmWeighViewTable=new ScTable(ScAutoWeighViewer.ccGetReference(), 200, 80),
    cmWeighLogTable=new ScTable(McAutoWeighLogger.ccGetReference(),200,160),
    cmTrendViewTable= new ScTable(McTrendLogger.ccGetReference(), 200, 80)
  ;//...
  
  private SubMonitoringPane(){
    super(new BorderLayout(1, 1));
    cmLesCurrentBar=new ScGauge[C_CAPA];
    ccInit();
  }//++!
  
  private void ccInit(){
    
    //-- current bar
    String[] lpDesSlotName={
      "V-Comp","Mixer","E-Fan","B-Comp",
      "B-Fan","F-Pump","Screen","H-EV",
      "Dryer","I-Belcon","H-Belcon","C-Screw",
      "B-Screw","AS-Supply","AS-Spray","F-EV"
    };
    for(int i=0;i<C_CAPA;i++){
      cmLesCurrentBar[i]=new ScGauge(lpDesSlotName[i]+": ", " A");
    }//..~
    JPanel lpVertPaneA=new JPanel(new GridLayout(0, 1, 2, 2));
    for(JProgressBar it:cmLesCurrentBar){lpVertPaneA.add(it);}
    
    //-- weighing
    //-- weighing ** flow
    JPanel lpWeighOperatePane= ScFactory.ccMyFlowPanel(2, false);
    lpWeighOperatePane.add
      (ScFactory.ccMyCommandButton("EXPORT", "--button-weigh-export", this));
    lpWeighOperatePane.add
      (ScFactory.ccMyCommandButton("PRINT", "--button-weigh-print", this));
    
    //-- weighing ** table
    cmWeighViewTable.ccSetEnabled(false);
    cmWeighViewTable.ccSetColor(Color.WHITE, Color.DARK_GRAY, Color.GRAY);
    cmWeighLogTable.ccSetEnabled(false);
    cmWeighLogTable.ccSetColor(Color.BLACK, Color.LIGHT_GRAY, Color.GRAY);
    cmWeighLogTable.ccSetColumnWidth(0, 128);
    JPanel lpWeighViewPanel=ScFactory.ccMyBorderPanel(2);
    lpWeighViewPanel.add(lpWeighOperatePane,BorderLayout.PAGE_START);
    lpWeighViewPanel.add(cmWeighViewTable,BorderLayout.CENTER);
    lpWeighViewPanel.add(cmWeighLogTable,BorderLayout.PAGE_END);
    
    //-- trending
    //-- trending ** flow
    cmTrendUpdateCB=ScFactory.ccMyCommandComboBox(new String[]{
        "update:never",
        "update:30s",
        "update:60s",
        "update:300s",
        "update:600s",
      },
      "--combo-trend-update", this
    );
    cmTrendSavingCB=ScFactory.ccMyCommandComboBox(new String[]{
        "save:manual",
        "save:every update",
        "save:afet fire",
        "save:on exit"
      },
      "--combo-trend-save", this
    );
    JPanel lpTrendOperatePane= ScFactory.ccMyFlowPanel(2, false);
    lpTrendOperatePane.add(
      ScFactory.ccMyCommandButton("EXPORT", "--button-trend-export", this)
    );
    lpTrendOperatePane.add(cmTrendUpdateCB);
    lpTrendOperatePane.add(cmTrendSavingCB);
    
    //-- trending ** table
    cmTrendViewTable.ccSetEnabled(false);
    cmTrendViewTable.ccSetColor(Color.BLACK, Color.LIGHT_GRAY, Color.GRAY);
    cmTrendViewTable.ccSetColumnWidth(0, 256);
    
    //-- right hand
    JPanel lpRightHandPane=ScFactory.ccMyBorderPanel(2);
    lpRightHandPane.add(lpWeighViewPanel,BorderLayout.PAGE_START);
    lpRightHandPane.add(cmTrendViewTable,BorderLayout.CENTER);
    lpRightHandPane.add(lpTrendOperatePane,BorderLayout.PAGE_END);
    
    //-- packing
    add(lpVertPaneA,BorderLayout.LINE_START);
    add(lpRightHandPane,BorderLayout.CENTER);
  
  }//+++
  
  //===
  
  @Override public void actionPerformed(ActionEvent ae){
    
    Object lpSource=ae.getSource();
    
    //-- combo
    if(lpSource instanceof JComboBox){
      JComboBox lpBox=(JComboBox)lpSource;
      int lpNotch=lpBox.getSelectedIndex();
      System.err.println(".SubMonitoringPane.actionPerformed()::"
        + "not_yet:"+lpNotch);
      return;
    }//..?
    
    
    //-- push
    if(lpSource instanceof JButton){
      String lpCommand = ae.getActionCommand();
      
      if(lpCommand.equals("--button-weigh-export")){
        McWorkerManager.ccGetReference().ccSaveTable(
          McAutoWeighLogger.ccGetReference().ccGetData(),
          "wlg", ".csv", true, true, false
        );
        return;
      }//+++
      
      if(lpCommand.equals("--button-weigh-print")){
        cmWeighLogTable.ccPrint();
        return;
      }//+++
      
      if(lpCommand.equals("--button-trend-export")){
        McWorkerManager.ccGetReference().ccSaveTable(
          McTrendLogger.ccGetReference().ccGetData(),
          "trd", ".csv", true, true, false
        );
        return;
      }//+++
      
    }//..?
    
    //-- warn
    System.out.println(
      ".SubAssistantPane"
      +"::unhandled_command:"
      +ae.getActionCommand()
    );
    
  }//+++
  
}//***eof
