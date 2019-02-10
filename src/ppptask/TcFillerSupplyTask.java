/*
 * Copyright (C) 2018 keypad
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

package ppptask;

import kosui.ppplogic.ZcDelayor;
import kosui.ppplogic.ZcFlicker;
import kosui.ppplogic.ZcTimer;
import kosui.ppplogic.ZcOffDelayTimer;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZiTimer;

public final class TcFillerSupplyTask extends ZcTask{
    
  private static TcFillerSupplyTask self;
  private TcFillerSupplyTask(){}//++!
  public static TcFillerSupplyTask ccGetReference(){
    if(self==null){self=new TcFillerSupplyTask();}
    return self;
  }//++!
  
  //===
  public boolean 
    //--
    mnFRSupplyStartSW,mnFRSupplyStartPL,
    mnFRSiloAirDisableSW,mnFRSiloAirAlwaysSW,
    //--
    dcFillerSiloAIR,dcFillerSiloScrewAN,dcFillerElevatorAN,
    dcFillerSiloHLV,dcFillerSiloMLV,dcFillerSiloLLV,dcFillerBinLV
  ;//...
  
  public int 
    dcCT8;
  
  private final ZcHookFlicker
    cmFRSupplyHLD = new ZcHookFlicker()
  ;//...
  
  private final ZcTimer
    cmFillerSiloScrewStartTM = new ZcOnDelayTimer(60),
    cmFillerElevatorStopTM = new ZcOffDelayTimer(60),
    cmFillerAirTM = new ZcFlicker(50, 0.2f);
  ;//...
  
  @Override public void ccScan(){
    
    //-- silo ev
    boolean lpHook=cmFRSupplyHLD.ccHook(mnFRSupplyStartSW);
    cmFillerElevatorStopTM.ccAct(lpHook);
    dcFillerElevatorAN=cmFillerElevatorStopTM.ccIsUp();
    
    //-- silo screw
    cmFillerSiloScrewStartTM.ccAct(dcFillerElevatorAN);
    dcFillerSiloScrewAN=lpHook
      &&cmFillerSiloScrewStartTM.ccIsUp()
      &&!dcFillerBinLV;
    
    //-- silo airation
    cmFillerAirTM.ccAct(dcFillerSiloScrewAN);
    dcFillerSiloAIR=
      mnFRSiloAirAlwaysSW?true:
      mnFRSiloAirDisableSW?(cmFillerAirTM.ccIsUp()&&dcFillerSiloScrewAN):
      false;
    
    //-- feedback
    mnFRSupplyStartPL=lpHook&&
      (dcFillerSiloScrewAN||sysOneSecondFLK);
  
  }//+++
  
  //===
  
  //[TODO]::public final void ccSetTimer(){}//+++
  
  //===
  
  public final ZcSiloModel 
    simFillerSilo = new ZcSiloModel(3000, 500,1200, 2900),
    simFillerBin  = new ZcSiloModel(999, 300, 600, 900)
  ;//...
  
  private final ZiTimer simBinChargeDelay = new ZcDelayor(60, 60);
  
  private final ZcMotor simM8 = new ZcMotor();

  @Override public void ccSimulate(){
    
    //-- transfer
    simBinChargeDelay.ccAct(dcFillerSiloScrewAN);
    if(dcFillerElevatorAN&&dcFillerSiloScrewAN){}
    ZcSiloModel.fnTransfer(
      simFillerSilo, simFillerBin,
      dcFillerElevatorAN&&simBinChargeDelay.ccIsUp(),
      4
    );
    
    //-- filler silo
    if(sysOneSecondPLS){
      simFillerSilo.ccCharge(ccRandom(1f)<0.6f, (int)ccRandom(32, 64));
    }//..?
    dcFillerSiloLLV=simFillerSilo.ccIsLow();
    dcFillerSiloMLV=simFillerSilo.ccIsMiddle();
    dcFillerSiloHLV=simFillerSilo.ccIsFull();
    
    //-- filler bin
    dcFillerBinLV=simFillerBin.ccIsMiddle();
    
    //-- power
    dcCT8=simM8.ccContact(dcFillerElevatorAN,
      dcFillerSiloScrewAN?0.78f:0.53f);
    
  }//+++
  
}//***eof
