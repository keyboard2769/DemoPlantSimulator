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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScList;
import kosui.pppswingui.ScTable;
import ppptable.McAutoWeighSetting;
import ppptable.McBaseKeyValueSetting;
import ppptable.McVBurningSetting;

public final class SubSettingPane 
  extends JPanel 
  implements ListSelectionListener,ActionListener
{
  
  private static SubSettingPane settingPane=null;
  
  public static final int
    C_I_SETTING_WEIGH=0,
    C_I_SETTING_VB=2
  ;
  
  private ScList cmList;
  private ScTable cmTable;
  private TableModel cmDummyModel;
  
  //===
  
  private SubSettingPane(ActionListener pxListener){
    super(new BorderLayout(1, 1));
    ccInit(pxListener);
  }//++!
  
  private void ccInit(ActionListener pxListener){
    
    cmList = new ScList(180, 400);
    cmList.ccAdd("Weigh");
    cmList.ccAdd("Cell");
    cmList.ccAdd("VBurning");
    cmList.ccAdd("AG Supply");
    cmList.ccAdd("FR Supply");
    cmList.ccAdd("AS Supply");
    cmList.ccAddListSelectionListener(this);
    add(cmList, BorderLayout.LINE_START);
    
    cmDummyModel=new McBaseKeyValueSetting();
    
    cmTable = new ScTable(cmDummyModel, 200, 400);
    
    JButton lpModifyButton=ScFactory.ccMyCommandButton
      ("MOD", "--button-modify", this);
    
    JPanel lpRightPanel=ScFactory.ccMyBorderPanel(1);
    lpRightPanel.add(cmTable, BorderLayout.CENTER);
    lpRightPanel.add(lpModifyButton, BorderLayout.PAGE_END);
    
    add(lpRightPanel,BorderLayout.CENTER);
    
  }//++!
  
  public static final SubSettingPane ccGetReference(ActionListener pxListener){
    if(settingPane==null){settingPane=new SubSettingPane(pxListener);}
    return settingPane;
  }//++!
  
  //===

  @Override public void actionPerformed(ActionEvent ae){
    
    int lpListID=cmList.ccGetCurrentIndex();
    int lpTableID=cmTable.ccGetSelectedRowIndex();
    int lpAddress=lpListID*10000+lpTableID;
    
    String lpInput=ScFactory.ccGetStringFromInputBox("input value", ":");
    System.out.println("::"+lpInput);
    System.out.println("@"+lpAddress);
    
    switch(lpAddress){
      
      case 20000:
        MainSketch.yourMOD.vmVBurnerTargetTempraure=150;
      break;
      
      case 20001:
        
        MainSketch.yourMOD.vmVBurnerTargetTempraure=220;
        
      break;
      
      
    }//..?
    
    cmTable.ccRefresh();
    
  }//+++
  
  @Override public void valueChanged(ListSelectionEvent lse){
    switch(cmList.ccGetCurrentIndex()){
      
      case C_I_SETTING_WEIGH:
        cmTable.ccSetModel(McAutoWeighSetting.ccGetReference());
      break;
      
      case C_I_SETTING_VB:
        cmTable.ccSetModel(McVBurningSetting.ccGetReference());
      break;
      
      default:
        cmTable.ccSetModel(cmDummyModel);
      break;
      
    }//..?
  }//+++
  
  
}//***eof
