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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
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
  
  //[TODO]::should button be out reffed??
  public final JButton cmQuitButton;
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
    cmQuitButton=ScFactory.ccMyCommandButton(
      "QUIT", "--button-quit",cmActionManager
    );
    JPanel lpSwitchPane=ScFactory.ccMyFlowPanel(2, false);
    lpSwitchPane.add(cmQuitButton);
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
  
  public static final
  JSpinner ccMyRecipeItemSpinner(double pxStep,int pxW, int pxH){
    JSpinner lpRes = ccMyRecipeItemSpinner(pxStep);
    lpRes.setPreferredSize(new Dimension(pxW, pxH));
    return lpRes;
  }//+++
  
  public static final
  JSpinner ccMyRecipeItemSpinner(double pxStep){
    SpinnerNumberModel lpModel
      =new SpinnerNumberModel(0.0d, 0.0d, 100.0d, pxStep);
    JSpinner lpRes=new JSpinner(lpModel);
    return lpRes;
  }//+++
  
}//***eof
