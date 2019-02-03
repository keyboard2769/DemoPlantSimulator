/*
 * Copyright (C) 2019 keypad
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import kosui.pppswingui.ScConsole;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScList;
import kosui.ppputil.VcConst;
import ppptable.McErrorMessage;
import ppptable.McErrorMessageFolder;
import ppptable.McWorkerManager;

public class SubErrorPane extends JPanel
  implements ListSelectionListener, ActionListener
{
  
  private static SubErrorPane self;
  public static SubErrorPane ccGetReference(){
    if(self==null){self=new SubErrorPane();}
    return self;
  }//++!
  
  //===
  
  private static final String C_DEFAULT="::have a nice day";

  //===
  
  private ScList cmList;
  private ScConsole cmStackConsole;
  private JTextArea cmDescriptionArea;
  
  private SubErrorPane(){
    super(new BorderLayout(1,1));
    ccInit();
  }//++!
  
  private void ccInit(){
    
    //-- list
    cmList=new ScList(150, 100);
    cmList.ccAdd("...");
    cmList.ccAddListSelectionListener(this);
    
    //-- histroy
    cmStackConsole=new ScConsole(12, 40);
    ssCheers();
    
    //-- description
    cmDescriptionArea=new JTextArea(4, 40);
    cmDescriptionArea.setEnabled(false);
    cmDescriptionArea.setEditable(false);
    cmDescriptionArea.setBackground(Color.LIGHT_GRAY);
    cmDescriptionArea.setDisabledTextColor(Color.BLACK);
    cmDescriptionArea.setText("%no-error-is-on-going%");
    
    //-- operate
    JPanel lpOperatePane=ScFactory.ccMyFlowPanel(2, false);
    lpOperatePane.add
      (ScFactory.ccMyCommandButton("CLEAR","--button-clear",this));
    lpOperatePane.add
      (ScFactory.ccMyCommandButton("EXPORT","--button-export",this));
    lpOperatePane.add
      (ScFactory.ccMyCommandButton("PRINT","--button-print",this));
    
    //-- packing
    add(cmList,BorderLayout.LINE_START);
    add(cmStackConsole,BorderLayout.CENTER);
    add(cmDescriptionArea,BorderLayout.PAGE_END);
    add(lpOperatePane,BorderLayout.PAGE_START);
    
  }//++!
  
  public final void ccStack(String pxLine){
    if(!VcConst.ccIsValidString(pxLine)){return;}
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run(){
        cmStackConsole.ccStack(pxLine);
      }//+++
    });
  }//+++
  
  public final void ccApplyListModel(String[] pxModel){
    cmList.ccRefreshModel(pxModel);
    MainSwingCoordinator.ccGetReference().ccSetErrorSum(pxModel.length);
  }//+++
  
  //===

  @Override public void actionPerformed(ActionEvent ae){
    String lpCommand=ae.getActionCommand();
    
    //-- switch
    
    if(lpCommand.equals("--button-clear")){
      ssClear();
      return;
    }//+++
    
    if(lpCommand.equals("--button-export")){
      ssExportToFile();
      return;
    }//+++
    
    if(lpCommand.equals("--button-print")){
      cmStackConsole.ccPrint();
      return;
    }//+++
    
    //-- last
    System.err.println("pppmain.SubErrorPane.actionPerformed():"
      + "unhandled_command:"+lpCommand);
    
  }//+++
  
  private void ssClear(){
    cmStackConsole.ccClear();
    ssCheers();
  }//+++
  
  private void ssCheers(){
    cmStackConsole.ccStack(C_DEFAULT);
    cmStackConsole.ccStack(VcConst.ccTimeStamp("--", true, false,false));
  }//+++
  
  private void ssExportToFile(){
    if(ScFactory.ccIsEDT()){
      VcConst.ccSetupTimeStampSeparator('_', '_');
      String lpPath=ScFactory.ccGetPathByFileChooser(
        MainSketch.ccGetReference().sketchPath
          +"\\"+"eLog"+VcConst.ccTimeStamp("_", true,false,false)+".txt"
      );
      VcConst.ccDefaultTimeStampSeparator();
      if(lpPath.equals("<np>")){return;}
      File lpFile=new File(lpPath);
      String[] lpData={cmStackConsole.ccGetText()};
      McWorkerManager.ccGetReference().ccSaveErrorLog(lpData, lpFile);
    }//..?
  }//+++
  
  //===

  @Override public void valueChanged(ListSelectionEvent lse){
    
    //-- index check
    if(cmList.ccGetCurrentIndex()<0){return;}
    
    //-- from list to error
    String lpIndex=cmList.ccGetCurrentItem().split("/")[0];
    McErrorMessage lpError=McErrorMessageFolder.ccGetReference()
      .ccGet(VcConst.ccParseIntegerString(lpIndex));
    
    //-- apply description
    StringBuilder lpBuilder=new StringBuilder(" # ");
    lpBuilder.append(lpError.cmID);
    lpBuilder.append(" : ");
    lpBuilder.append(lpError.cmContent);
    lpBuilder.append(" \n");
    lpBuilder.append(lpError.cmDescription);
    cmDescriptionArea.setText(lpBuilder.toString());
    
  }//+++
  
 }//***eof
