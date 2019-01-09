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

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class SubAssistantPane extends JPanel{
  
  private static SubAssistantPane settingPane=null;
  
  //===
  
  public final JComboBox<String> 
    cmFillerAirCB,cmDustAirCB,
    cmBagPulseCB,cmCoolingDamperCB,
    cmVF1VibratorCB,cmVF2VibratorCB
  ;//...
  
  //===
  
  private SubAssistantPane(ActionListener pxListener){
    
    super(new FlowLayout(FlowLayout.LEADING, 4, 4));
    
    //-- ComboBox
    cmFillerAirCB=MainSwingCoordinator.ccMyCommandComboBox(
      new String[]{
        "filler-silo-air:auto",
        "filler-silo-air:disable",
        "filler-silo-air:always"
      },
      "--combo-fillerSiloAir", pxListener
    );
    cmFillerAirCB.setVisible(false);
    cmDustAirCB=MainSwingCoordinator.ccMyCommandComboBox(
      new String[]{
        "dust-silo-air:auto",
        "dust-silo-air:disable",
        "dust-silo-air:always"
      },
      "--combo-dustSiloAir", pxListener
    );
    cmBagPulseCB=MainSwingCoordinator.ccMyCommandComboBox(
      new String[]{
        "bag-pulse:RF",
        "bag-pulse:COM",
        "bag-pulse:disable",
        "bag-pulse:always",
      },
      "--combo-bagPulse", pxListener
    );
    cmCoolingDamperCB=MainSwingCoordinator.ccMyCommandComboBox(
      new String[]{
        "cooling-damper:auto",
        "cooling-damper:close",
        "cooling-damper:open"
      },
      "--combo-coolingDamper", pxListener
    );
    cmVF1VibratorCB=MainSwingCoordinator.ccMyCommandComboBox(
      new String[]{
        "VF1-vibrator:auto",
        "VF1-vibrator:disable",
        "VF1-vibrator:always"
      },
      "--combo-vf1Vib", pxListener
    );
    cmVF2VibratorCB=MainSwingCoordinator.ccMyCommandComboBox(
      new String[]{
        "VF2-vibrator:auto",
        "VF2-vibrator:disable",
        "VF2-vibrator:always"
      },
      "--combo-vf2Vib", pxListener
    );
    cmVF2VibratorCB.setSelectedIndex(1);
    
    //--??
    JPanel lpVertPaneA=new JPanel(new GridLayout(0, 1, 4, 4));
    lpVertPaneA.setBorder
      (BorderFactory.createTitledBorder("Supplying:"));
    lpVertPaneA.add(new JLabel("Filler-Related:"));
    lpVertPaneA.add(cmFillerAirCB);
    lpVertPaneA.add(cmDustAirCB);
    
    //-- ??
    JPanel lpVertPaneB=new JPanel(new GridLayout(0, 1,4,4));
    lpVertPaneB.setBorder
      (BorderFactory.createTitledBorder("Drying:"));
    lpVertPaneB.add(new JLabel("Bagfilter-Related:"));
    lpVertPaneB.add(cmBagPulseCB);
    lpVertPaneB.add(cmCoolingDamperCB);
    lpVertPaneB.add(new JLabel("Feeder-Related:"));
    lpVertPaneB.add(cmVF1VibratorCB);    
    lpVertPaneB.add(cmVF2VibratorCB);
    
    //-- packing
    add(lpVertPaneA);
    add(lpVertPaneB);
    
    
  }//++!
  
  public static final SubAssistantPane ccInit(ActionListener pxListener){
    if(settingPane==null){settingPane=new SubAssistantPane(pxListener);}
    return settingPane;
  }//++!
  
}//***eof
