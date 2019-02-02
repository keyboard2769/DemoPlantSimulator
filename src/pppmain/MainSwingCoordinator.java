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
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.crypto.interfaces.PBEKey;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTitledWindow;

public class MainSwingCoordinator{
  
  private static MainSwingCoordinator self;
  public static MainSwingCoordinator ccGetReference(){
    if(self==null){self=new MainSwingCoordinator();}
    return self;
  }//++!
  
  //===
  
  public final ScTitledWindow cmOperateWindow;
  
  public final SubMonitoringPane cmMonitoringPane;
  public final SubAssistantPane cmAssistantPane;
  public final SubErrorPane cmErrorPane;
  public final SubRecipePane cmRecipePane;
  public final SubSettingPane cmSettingPane;
  public final SubAdjustPane cmAdjustPane;
  public final SubSystemPane cmSystemPane;
  
  public final JTextField cmErrorBox;

  private MainSwingCoordinator(){
    
    //-- actioning
    ActionListener cmActionManager=MainActionManager.ccGetReference();
    
    //-- construction
    //-- tabbed pane
    JTabbedPane lpOperatePane = new JTabbedPane();
    
    cmMonitoringPane=SubMonitoringPane.ccGetReference();
    lpOperatePane.addTab("Monitoring", cmMonitoringPane);
    
    cmAssistantPane=SubAssistantPane.ccGetReference();
    lpOperatePane.addTab("Assistant", cmAssistantPane);
    
    cmErrorPane=SubErrorPane.ccGetReference();
    lpOperatePane.addTab("Error", cmErrorPane);
    
    cmRecipePane=SubRecipePane.ccGetReference();
    lpOperatePane.addTab("Recipe", cmRecipePane);
    
    cmSettingPane=SubSettingPane.ccGetReference();
    lpOperatePane.addTab("Setting", cmSettingPane);
    
    cmAdjustPane = SubAdjustPane.ccGetReference();
    lpOperatePane.addTab("Adjust", cmAdjustPane);
    
    cmSystemPane=SubSystemPane.ccGetReference();
    lpOperatePane.addTab("System", cmSystemPane);
    
    //-- flow pane
    JPanel lpSwitchPane=ScFactory.ccMyFlowPanel(2, false);
    lpSwitchPane.add(ScFactory.ccMyCommandButton(
      "QUIT", "--button-quit",cmActionManager
    ));
    lpSwitchPane.add(ScFactory.ccMyCommandButton(
      "HIDE", "--button-hide",cmActionManager
    ));
    
    cmErrorBox=ScFactory.ccMyTextContainer(" 00", 48, 20);
    JPanel lpLampPane=ScFactory.ccMyFlowPanel(2, true);
    lpLampPane.add(new JLabel("ERR:"));
    lpLampPane.add(cmErrorBox);
    
    JPanel lpBottomPane=ScFactory.ccMyBorderPanel(1);
    lpBottomPane.add(lpSwitchPane,BorderLayout.LINE_START);
    lpBottomPane.add(lpLampPane,BorderLayout.LINE_END);
    
    //-- packing
    
    /* ..pass null to the window makes it easier to judge which one should
     *   be on top, but text field input functionality won't be usable.
     */
    cmOperateWindow=new ScTitledWindow(null);
    cmOperateWindow.ccInit("Operate",Color.decode("#336633"));
    cmOperateWindow.ccAddCenter(lpOperatePane);
    cmOperateWindow.ccAddPageEnd(lpBottomPane);
    cmOperateWindow.ccFinish(true,50,50);
    
  }//++!
  
  //===
  
  public final void ccSetErrorSum(int pxSum){
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run(){
        cmErrorBox.setText(Integer.toString(pxSum));
      }//+++
    });
  }//+++
  
  //===
  
  //[TODO]::to library
  public static final JComboBox<String> ccMyCommandComboBox(
    String[] pxList, String pxCommand, ActionListener pxListener
  ){
    JComboBox<String> lpRes=new JComboBox<>(pxList);
    lpRes.setActionCommand(pxCommand);
    lpRes.addActionListener(pxListener);
    lpRes.setBackground(Color.decode("#EEEECC"));
    return lpRes;
  }//+++
  
  //[TODO]::to library?? or delete??
  public static final JProgressBar ccMyCurrentBar(String pxName){
    JProgressBar lpRes= new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
    lpRes.setValue(50);
    lpRes.setString(pxName);
    lpRes.setBorderPainted(true);
    lpRes.setStringPainted(true);
    //ttt.setBackground(Color.decode("#EEEEEE"));
    lpRes.setForeground(Color.decode("#999933"));
    return lpRes;
  }//+++
  
}//***eof
