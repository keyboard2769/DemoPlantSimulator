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
import java.io.File;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import kosui.pppswingui.ScFactory;
import kosui.ppputil.VcConst;
import ppptable.McWorkerManager;

public class SubSystemPane extends JPanel implements ActionListener{
  
  private static SubSystemPane self;
  public static SubSystemPane ccGetReference(){
    if(self==null){self=new SubSystemPane();}
    return self;
  }//++!

  //===
  
  public JTextField
    cmMainPathBox,
    cmLogPathBox,cmSettingPathBox, cmLanguagePathBox
  ;//...
  public JCheckBox
    cmSaveOnExitChecker,
    cmAGExtractionChecker,cmFRExtractionChecker
  ;//...

  private SubSystemPane(){
    super(new GridLayout(4, 1, 2, 2));
    ccInit();
  }//++!
  
  private void ccInit(){
    
    //-- path setting
    cmMainPathBox=ScFactory.ccMyTextBox("%dummy-main-path%", 0, 0);
    cmLogPathBox=ScFactory.ccMyTextBox("%dummy-log-path%", 0, 0);
    cmSettingPathBox=ScFactory.ccMyTextBox("%dummy-setting-path%", 0, 0);
    cmLanguagePathBox=ScFactory.ccMyTextBox("%dummy-lang-path%", 0, 0);
    JPanel lpPathSettingPane=new JPanel(new SpringLayout());
    lpPathSettingPane.add(new JLabel("  Main:"));
    lpPathSettingPane.add(cmMainPathBox);
    lpPathSettingPane.add(ScFactory.ccMyCommandButton
      ("log","--button-path-log", this));
    lpPathSettingPane.add(cmLogPathBox);
    lpPathSettingPane.add(ScFactory.ccMyCommandButton
      ("setting","--button-path-setting", this));
    lpPathSettingPane.add(cmSettingPathBox);
    lpPathSettingPane.add(ScFactory.ccMyCommandButton
      ("setting","--button-path-lang", this));
    lpPathSettingPane.add(cmLanguagePathBox);
    MainSwingCoordinator.ccApplySpringLayout(lpPathSettingPane, 2, 2);
    MainSwingCoordinator.ccApplyTitleBoard(lpPathSettingPane, "PATH");
    
    //-- misc setting
    cmAGExtractionChecker=new JCheckBox("AG-Extract");
    cmFRExtractionChecker=new JCheckBox("FR-Extract");
    JPanel lpMiscPane=ScFactory.ccMyFlowPanel(1, false, "MISC");
    lpMiscPane.add(cmAGExtractionChecker);
    lpMiscPane.add(cmFRExtractionChecker);
    
    //-- data 
    cmSaveOnExitChecker=new JCheckBox("disable_exit_saving");
    JPanel lpDataPane=ScFactory.ccMyFlowPanel(1, false, "DATA");
    lpDataPane.add(cmSaveOnExitChecker);
    
    //-- Language
    JPanel lpLangPane=ScFactory.ccMyFlowPanel(1, false, "Language");
    lpLangPane.add(ScFactory.ccMyCommandButton
      ("font","--button-font", this));
    lpLangPane.add(new JLabel(" | "));
    lpLangPane.add(ScFactory.ccMyCommandButton
      (">>ZH","--button-zhongwen", this));
    lpLangPane.add(new JLabel(" | "));
    lpLangPane.add(ScFactory.ccMyCommandButton
      ("EN>>","--button-template", this));
    
    //-- packing
    add(lpPathSettingPane);
    add(lpMiscPane);
    add(lpDataPane);
    add(lpLangPane);
    
  }//+++
  
  //===

  @Override public void actionPerformed(ActionEvent ae){
    String lpCommand=ae.getActionCommand();
    
    if(lpCommand.equals("--button-font")){
      ssLoadFont();
      return;
    }//+++
    
    if(lpCommand.equals("--button-zhongwen")){
      McWorkerManager.ccGetReference().ccLoadChineseDictionary();
      return;
    }//+++
    
    System.err.println("pppmain.SubSystemPane.actionPerformed():"
      + "unhandled_command"+lpCommand);
  }//+++
  
  private void ssLoadFont(){
    String lpPath=ScFactory.ccGetPathByFileChooser('f');
    if(!VcConst.ccIsValidString(lpPath)){return;}
    if(!lpPath.endsWith(".vlw")){
      ScFactory.ccMessageBox("file name extension illeagal.");
      return;
    }//..?
    File lpFile = new File(lpPath);
    if(!lpFile.exists()){
      ScFactory.ccMessageBox("file dees not exist!");
      return;
    }//..?
    TabWireManager.ccSetCommand(TabWireManager.C_K_SET_FONT, lpPath);
  }//+++
  
 }//***eof
