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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import kosui.pppswingui.ScFactory;

public class SubSystemPane extends JPanel implements ActionListener{
  
  private static SubSystemPane self;
  public static SubSystemPane ccGetReference(){
    if(self==null){self=new SubSystemPane();}
    return self;
  }//++!

  //===
  
  public JTextField cmMainPathBox;
  public JCheckBox cmSaveOnExitChecker;

  private SubSystemPane(){
    super(new GridLayout(8, 1, 1, 1));
    ccInit();
  }//++!
  
  private void ccInit(){
    
    //-- path setting
    JButton lpBrowseButton=ScFactory.ccMyCommandButton
      ("browse","--button-browse", this);
    cmMainPathBox=ScFactory.ccMyTextBox("%dummy-path%", 300, 26);
    JPanel lpPathSettingPane=ScFactory.ccMyFlowPanel(1, false, "MainPath");
    lpPathSettingPane.add(cmMainPathBox);
    lpPathSettingPane.add(lpBrowseButton);
    
    //-- misc setting
    cmSaveOnExitChecker=new JCheckBox("save_on_setting(DUMMY)");
    JPanel lpMiscPane=ScFactory.ccMyFlowPanel(1, false, "MISC");
    lpMiscPane.add(cmSaveOnExitChecker);
    
    //-- packing
    add(lpPathSettingPane);
    add(lpMiscPane);
    
  }//+++
  
  //===

  @Override
  public void actionPerformed(ActionEvent ae){
    String lpCommand=ae.getActionCommand();
    
    if(lpCommand.equals("--button-browse")){
      
      //[TOIMP]::but what??
      String lpDummy=ScFactory.ccGetPathByFileChooser('d');
      if(lpDummy.equals("<na>")){return;}//..may vary on method implementation
      cmMainPathBox.setText(lpDummy);
      return;
      
    }//+++
    
    System.err.println("pppmain.SubSystemPane.actionPerformed():"
      + "unhandled_command"+lpCommand);
  }//+++
  
 }//***eof
