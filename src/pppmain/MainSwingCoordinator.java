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

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTitledWindow;
import processing.core.PApplet;

public class MainSwingCoordinator{
  
  private final ScTitledWindow cmOperateWindow;
  private final TabActionManager cmActionManager;
  
  public final SubMonitoringPane cmMonitoringPane;
  public final SubAssistantPane cmAssistantPane;
  public final SubSettingPane cmSettingPane;

  public MainSwingCoordinator(PApplet pxOwner){
    
    //-- actioning
    cmActionManager=new TabActionManager();
    
    //-- construction
    JTabbedPane lpOperatePane = new JTabbedPane();
    
    cmMonitoringPane=SubMonitoringPane.ccInit(cmActionManager);
    lpOperatePane.addTab("Monitoring", cmMonitoringPane);
    
    cmAssistantPane=SubAssistantPane.ccInit(cmActionManager);
    lpOperatePane.addTab("Assistant", cmAssistantPane);
    
    cmSettingPane=SubSettingPane.ccGetReference(cmActionManager);
    lpOperatePane.addTab("Setting", cmSettingPane);
    
    //[TODO]::.addTab("Error", cmErrorPane)
    
    //[TODO]::.addTab("Recipe", cmRecipePane)
    
    lpOperatePane.addTab
      ("System", ScFactory.ccMyCommandButton("DONTOUCH", "<nn>", 800, 600));
        
    //-- packing
    cmOperateWindow=new ScTitledWindow(pxOwner.frame);
    cmOperateWindow.ccInit("Operate",Color.decode("#336633"));
    cmOperateWindow.ccAddCenter(lpOperatePane);
    cmOperateWindow.ccAddPageEnd(ScFactory.ccMyCommandButton(
      "QUIT", "--button-quit",cmActionManager
    ));
    cmOperateWindow.ccFinish(true,200,200);
    
  }//++!
  
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
