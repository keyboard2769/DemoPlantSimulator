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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import kosui.pppswingui.ScConsole;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScList;
import kosui.ppputil.VcConst;

public class SubErrorPane extends JPanel{
  
  private static SubErrorPane self;
  public static SubErrorPane ccGetReference(){
    if(self==null){self=new SubErrorPane();}
    return self;
  }//++!

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
    cmList.ccAdd("dummy-error-a");
    cmList.ccAdd("dummy-error-b");
    add(cmList,BorderLayout.LINE_START);
    
    //-- histroy
    cmStackConsole=new ScConsole(12, 40);
    cmStackConsole.ccStack("::have a nice day");
    cmStackConsole.ccStack(VcConst.ccTimeStamp("--", true, false,false));
    add(cmStackConsole,BorderLayout.CENTER);
    
    //-- description
    cmDescriptionArea=new JTextArea(4, 40);
    cmDescriptionArea.setEnabled(false);
    cmDescriptionArea.setEditable(false);
    cmDescriptionArea.setBackground(Color.LIGHT_GRAY);
    cmDescriptionArea.setDisabledTextColor(Color.BLACK);
    cmDescriptionArea.setText("%no-error-is-on-going%");
    add(cmDescriptionArea,BorderLayout.PAGE_END);
    
    //-- operate
    JPanel lpOperatePane=ScFactory.ccMyFlowPanel(2, false);
    lpOperatePane.add(ScFactory.ccMyCommandButton("-DM-CLEAR"));
    lpOperatePane.add(ScFactory.ccMyCommandButton("-DM-SAVELOG"));
    add(lpOperatePane,BorderLayout.PAGE_START);
    
  }//++!
  
 }//***eof
