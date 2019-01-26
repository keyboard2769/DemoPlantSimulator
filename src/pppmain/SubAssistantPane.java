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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import kosui.pppswingui.ScFactory;

public final class SubAssistantPane extends JPanel implements ActionListener{
  
  private static SubAssistantPane self=null;
  public static final SubAssistantPane ccGetReference(){
    if(self==null){self=new SubAssistantPane();}
    return self;
  }//++!
  
  //===
  
  public JTextField 
    cmDustBinFullPL
  ;//...
  
  public JComboBox<String> 
    //-- ag
    cmBagPulseNT,cmCoolingDamperNT,
    cmFuelExchangeNT,
    cmVF1VibratorNT,cmVF2VibratorNT,
    //-- fr
    cmFillerAirNT,cmDustAirNT,cmDustDischargeNT,
    //-- as
    cmAsPumpReverseNT
  ;//...
  
  //===
  
  private SubAssistantPane(){
    super(new FlowLayout(FlowLayout.LEADING, 4, 4));
    Object lpLayout=getLayout();
    if(lpLayout instanceof FlowLayout)
      { ((FlowLayout)lpLayout).setAlignOnBaseline(true); }
    ccInit();
  }//++!
  
  private void ccInit(){
    
    //-- AG
    cmBagPulseNT=MainSwingCoordinator.ccMyCommandComboBox(new String[]{
        "bag-pulse:COM",
        "bag-pulse:RF",
        "bag-pulse:disable",
        "bag-pulse:always",
      },
      "--NT-BagPulse", this
    );
    cmCoolingDamperNT=MainSwingCoordinator.ccMyCommandComboBox(new String[]{
        "cooling-damper:auto",
        "cooling-damper:close",
        "cooling-damper:open"
      },
      "--NT-CoolingDamper", this
    );
    cmFuelExchangeNT=MainSwingCoordinator.ccMyCommandComboBox(new String[]{
        "fuel-exchange:use",
        "fuel-exchange:disable",
      },
      "--NT-FuelExchange", this
    );
    cmVF1VibratorNT=MainSwingCoordinator.ccMyCommandComboBox(new String[]{
        "VF1-vibrator:auto",
        "VF1-vibrator:disable",
        "VF1-vibrator:always"
      },
      "--NT-VF1Vibrator", this
    );
    cmVF2VibratorNT=MainSwingCoordinator.ccMyCommandComboBox(new String[]{
        "VF2-vibrator:auto",
        "VF2-vibrator:disable",
        "VF2-vibrator:always"
      },
      "--NT-VF2Vibrato", this
    );
    JPanel lpAGPane=new JPanel(new GridLayout(0, 1,4,4));
    lpAGPane.setBorder
      (BorderFactory.createTitledBorder("Aggregate:"));
    lpAGPane.add(new JLabel("Bagfilter-Related:"));
    lpAGPane.add(cmBagPulseNT);
    lpAGPane.add(cmCoolingDamperNT);
    lpAGPane.add(new JLabel("Feeder-Related:"));
    lpAGPane.add(cmVF1VibratorNT);    
    lpAGPane.add(cmVF2VibratorNT);
    lpAGPane.add(new JLabel("Combust-Related:"));
    lpAGPane.add(cmFuelExchangeNT);
    
    //-- FR
    cmFillerAirNT=MainSwingCoordinator.ccMyCommandComboBox(new String[]{
        "filler-silo-air:auto",
        "filler-silo-air:disable",
        "filler-silo-air:always"
      },
      "--combo-fillerSiloAir", this
    );
    cmDustAirNT=MainSwingCoordinator.ccMyCommandComboBox(new String[]{
        "dust-silo-air:auto",
        "dust-silo-air:disable",
        "dust-silo-air:always"
      },
      "--NT-DustSiloAir", this
    );
    cmDustBinFullPL=ScFactory.ccMyTextLamp("dust-bin:full", 48, 20);
    
    cmDustDischargeNT=MainSwingCoordinator.ccMyCommandComboBox(new String[]{
        "dust-discharge:off",
        "dust-discharge:on"
      },
      "--NT-DustDischarge", this
    );
    
    JPanel lpFRPane=new JPanel(new GridLayout(0, 1, 4, 4));
    lpFRPane.setBorder
      (BorderFactory.createTitledBorder("Filler:"));
    lpFRPane.add(new JLabel("Filler-Related:"));
    lpFRPane.add(cmFillerAirNT);
    lpFRPane.add(new JLabel("Dust-Related:"));
    lpFRPane.add(cmDustAirNT);
    lpFRPane.add(cmDustBinFullPL);
    lpFRPane.add(cmDustDischargeNT);
    
    //-- AS
    cmAsPumpReverseNT=MainSwingCoordinator.ccMyCommandComboBox(new String[]{
        "AS-supply-pump:foreward",
        "AS-supply-pump:reverse"
      },
      "--NT-AsPumpReverse", this
    );
    JPanel lpASPane=new JPanel(new GridLayout(0, 1, 4, 4));
    lpASPane.setBorder
      (BorderFactory.createTitledBorder("Asphalt:"));
    lpASPane.add(new JLabel("supply-pump:"));
    lpASPane.add(cmAsPumpReverseNT);
    
    //-- packing
    add(lpAGPane);
    add(lpFRPane);
    add(lpASPane);
  
  }//++!

  @Override public void actionPerformed(ActionEvent ae){
    Object lpSource=ae.getSource();
    if(!(lpSource instanceof JComboBox)){return;}
    JComboBox lpBox=(JComboBox)lpSource;
    int lpNotch=lpBox.getSelectedIndex();
    
    //-- dirty if 
    
    //-- if ** aggregate
    
    if(lpSource.equals((Object)cmBagPulseNT)){
      MainSketch.yourMOD.vmBagPulseRfSW=lpNotch==1;
      MainSketch.yourMOD.vmBagPulseDisableSW=lpNotch==2;
      MainSketch.yourMOD.vmBagPulseAlwaysSW=lpNotch==3;
      return;
    }//..?
    
    if(lpSource.equals((Object)cmCoolingDamperNT)){
      MainSketch.yourMOD.vmCoolingDamperDisableSW=lpNotch==1;
      MainSketch.yourMOD.vmCoolingDamperAlwaysSW=lpNotch==2;
      return;
    }//..?
    
    if(lpSource.equals((Object)cmVF1VibratorNT)){
      MainSketch.yourMOD.vmVFeeder1VibratorDisableSW=lpNotch==1;
      MainSketch.yourMOD.vmVFeeder1VibratorAlwaysSW=lpNotch==2;
      return;
    }//..?
    
    if(lpSource.equals((Object)cmVF2VibratorNT)){
      MainSketch.yourMOD.vmVFeeder2VibratorDisableSW=lpNotch==1;
      MainSketch.yourMOD.vmVFeeder2VibratorAlwaysSW=lpNotch==2;
      return;
    }//..?
    
    if(lpSource.equals((Object)cmFuelExchangeNT)){
      MainSketch.yourMOD.vmFuelExchangeDisableSW=lpNotch==1;
      return;
    }//..?
    
    //-- if ** filler
    
    if(lpSource.equals((Object)cmFillerAirNT)){
      MainSketch.yourMOD.vmFillerSiloAirDisableSW=lpNotch==1;
      MainSketch.yourMOD.vmFillerSiloAirAlwaysSW=lpNotch==2;
      return;
    }//..?
    
    if(lpSource.equals((Object)cmDustAirNT)){
      MainSketch.yourMOD.vmDustSiloAirDisableSW=lpNotch==1;
      MainSketch.yourMOD.vmDustSiloAirAlwaysSW=lpNotch==2;
      return;
    }//..?
    
    //-- asphalt
    if(lpSource.equals((Object)cmAsPumpReverseNT)){
      MainSketch.yourMOD.vmASSupplyPumpReverseSW=lpNotch==1;
      return;
    }//..?
    
    //-- warning
    System.out.println(
      ".SubAssistantPane"
      +"::unhandled_command:"
      +ae.getActionCommand()
    );
    
  }//+++
  
}//***eof
