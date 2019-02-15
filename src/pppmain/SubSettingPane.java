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
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScList;
import kosui.pppswingui.ScTable;
import kosui.ppputil.VcConst;
import ppptable.McSettingFolder;
import ppptable.McWorkerManager;

public final class SubSettingPane extends JPanel 
  implements ListSelectionListener,ActionListener
{
  
  private static SubSettingPane settingPane=null;
  public static final SubSettingPane ccGetReference(){
    if(settingPane==null){settingPane=new SubSettingPane();}
    return settingPane;
  }//++!
  
  //===
  
  public static final int
    C_P_VALUE_COLUMN_WIDTH=64,
    C_I_SETTING_WEIGH=0,
    C_I_SETTING_VB=2
  ;//...
  
  //===
  
  private ScList cmList;
  private ScTable cmTable;
  
  private SubSettingPane(){
    super(new BorderLayout(1, 1));
    ccInit();
  }//++!
  
  private void ccInit(){
    
    //-- operationg
    JPanel lpFilePane=ScFactory.ccMyFlowPanel(2, false);
    lpFilePane.add(ScFactory.ccMyCommandButton
      ("LOAD","--button-setting-load",this));
    lpFilePane.add(ScFactory.ccMyCommandButton
      ("EXPORT","--button-setting-export",this));
    
    //-- list
    McSettingFolder lpFolder=McSettingFolder.ccGetReference();
    cmList = new ScList(180, 400);
    for(String it:lpFolder.ccGetTitleList()){cmList.ccAdd(it);}
    cmList.ccAddListSelectionListener(this);
    
    //-- table
    cmTable = new ScTable(lpFolder.ccGet(0), 200, 400);
    cmTable.ccSetColumnWidth(0, C_P_VALUE_COLUMN_WIDTH);
    JButton lpModifyButton=ScFactory.ccMyCommandButton
      ("MOD", "--button-modify", this);
    JPanel lpRightPanel=ScFactory.ccMyBorderPanel(1);
    lpRightPanel.add(cmTable, BorderLayout.CENTER);
    lpRightPanel.add(lpModifyButton, BorderLayout.PAGE_END);
    
    //-- packing
    add(lpFilePane,BorderLayout.PAGE_START);
    add(cmList, BorderLayout.LINE_START);
    add(lpRightPanel,BorderLayout.CENTER);
    
    
    //-- post packing
    
    /* ..this will invoke a listener, dont put before the table construction.
     */
    cmList.ccSetSelectedIndex(0);
    
  }//++!
  
  //===
  
  public final void ccUpdateContent(){
    cmTable.ccRepaintTable();
  }//+++
  
  //===
  
  private void ssSendModificationRequest(){
    
    //-- judge id
    int lpListID=cmList.ccGetCurrentIndex();
    int lpTableID=cmTable.ccGetSelectedRowIndex();
    if(lpListID<0 || lpTableID<0){ScFactory.ccMessageBox(
      "select item first!!"
    );return;}
    
    //-- judge input ** validity
    String lpInput=ScFactory.ccGetStringFromInputBox("input value", ":");
    if(!VcConst.ccIsValidString(lpInput)){ScFactory.ccMessageBox(
      "Invalid Input!!"
    );return;}
    
    //-- judge input ** numberic
    boolean lpIsFloat=VcConst.ccIsFloatString(lpInput);
    boolean lpIsInteger=VcConst.ccIsIntegerString(lpInput);
    if(!lpIsFloat && !lpIsInteger){ScFactory.ccMessageBox(
      "Number form illegal!!"
    );return;}
    
    //-- transform
    int lpRes=0;
    if(lpIsFloat){
      lpRes=(int)(Float.parseFloat(lpInput)*10);
    }//..?
    if(lpIsInteger){
      lpRes=Integer.parseInt(lpInput)*10;
    }//..?
    
    //-- packing up
    TabWireManager.ccSetSettingInfo(
      lpListID*100+lpTableID,
      lpRes
    );
  
  }//+++
  
  //===

  @Override public void actionPerformed(ActionEvent ae){
    String lpCommand=ae.getActionCommand();
    
    if(lpCommand.equals("--button-setting-load")){
      ScFactory.ccMessageBox("WARNING::current setting will be disposed!!");
      System.err.println(".SubSettingPane.(--button-setting-export)::no_yet");
      return;
    }//..?
    
    if(lpCommand.equals("--button-setting-export")){
      McWorkerManager.ccGetReference().ccSaveSetting(
        "stg",".json",
        true,false,false
      );
      return;
    }//..?
    
    if(lpCommand.equals("--button-modify")){
      ssSendModificationRequest();
      return;
    }//..?
    
    System.err.println("pppmain.SubSettingPane.actionPerformed()::"
      + "unhandled_command:"+lpCommand);
    
  }//+++
  
  @Override public void valueChanged(ListSelectionEvent lse){
    McSettingFolder lpFolder=McSettingFolder.ccGetReference();
    cmTable.ccSetModel(lpFolder.ccGet(cmList.ccGetCurrentIndex()));
    cmTable.ccSetColumnWidth(0, C_P_VALUE_COLUMN_WIDTH);
  }//+++
  
}//***eof
