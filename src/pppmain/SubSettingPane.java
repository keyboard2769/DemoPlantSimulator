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

public final class SubSettingPane 
  extends JPanel 
  implements ListSelectionListener,ActionListener
{
  
  private static SubSettingPane settingPane=null;
  public static final SubSettingPane ccGetReference(){
    if(settingPane==null){settingPane=new SubSettingPane();}
    return settingPane;
  }//++!
  
  //===
  
  public static final int
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
    
    McSettingFolder lpFolder=McSettingFolder.ccGetReference();
    
    cmList = new ScList(180, 400);
    for(String it:lpFolder.ccGetTitleList()){cmList.ccAdd(it);}
    cmList.ccAddListSelectionListener(this);
    add(cmList, BorderLayout.LINE_START);
    
    cmTable = new ScTable(lpFolder.ccGet(0), 200, 400);
    
    JButton lpModifyButton=ScFactory.ccMyCommandButton
      ("MOD", "--button-modify", this);
    
    JPanel lpRightPanel=ScFactory.ccMyBorderPanel(1);
    lpRightPanel.add(cmTable, BorderLayout.CENTER);
    lpRightPanel.add(lpModifyButton, BorderLayout.PAGE_END);
    
    add(lpRightPanel,BorderLayout.CENTER);
    
    /* ..this will invoke a listener, dont put before the table construction.
     */
    cmList.ccSetSelectedIndex(0);
    
  }//++!
  
  //===
  
  public final void ccUpdateContent(){
    cmTable.ccRepaintTable();
  }//+++
  
  //===

  @Override public void actionPerformed(ActionEvent ae){
    
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
  
  @Override public void valueChanged(ListSelectionEvent lse){
    McSettingFolder lpFolder=McSettingFolder.ccGetReference();
    cmTable.ccSetModel(lpFolder.ccGet(cmList.ccGetCurrentIndex()));
  }//+++
  
  
}//***eof
