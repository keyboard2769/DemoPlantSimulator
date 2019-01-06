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

public class TcFillerSupplyTask extends ZcTask{
  
  public boolean 
    //--
    mnFRSupplyStartSW,mnFRSUpplyStartPL,
    //--
    dcFillerSiloAIR,dcFillerSiloScrewAN,dcFillerElevatorAN,
    dcFillerSiloHLV,dcFillerSiloMLV,dcFillerSiloLLV,dcFillerBinLV,
    //--
    cxFillerBinDischargeFLG,
    cyFillerBinHasContentFLG
  ;//...
  
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
    dcFillerSiloAIR=cmFillerAirTM.ccIsUp()&&dcFillerSiloScrewAN;
    
    //-- feedback
    mnFRSUpplyStartPL=lpHook&&
      (dcFillerSiloScrewAN||sysOneSecondFLK);
  
  }//+++
  
  //===
  
  //[TODO]::public final void ccSetTimer(){}//+++
  
  //===
  
  private int simFillerBinAD=30;
  private int simFillerSiloAD=30;
  
  private ZiTimer simBinChargeDelay = new ZcDelayor(60, 60);

  @Override public void ccSimulate(){
    
    //-- filler silo
    if(ccRandom(1f)<0.6f)
      {simFillerSiloAD+=simFillerSiloAD<600?4:0;}
    if(dcFillerElevatorAN&&dcFillerSiloScrewAN)
      {simFillerSiloAD-=simFillerSiloAD>3?2:0;}
    dcFillerSiloHLV=simFillerSiloAD>580;
    dcFillerSiloMLV=simFillerSiloAD>380;
    dcFillerSiloLLV=simFillerSiloAD>180;
    
    //-- filler bin
    simBinChargeDelay.ccAct(dcFillerSiloScrewAN);
    if(
      (simFillerSiloAD>3)
      &&dcFillerElevatorAN
      &&simBinChargeDelay.ccIsUp()
    ){simFillerBinAD+=simFillerBinAD<200?1:0;}
    if(cxFillerBinDischargeFLG)
      {simFillerBinAD-=simFillerBinAD>3?2:0;}
    dcFillerBinLV=simFillerBinAD>170;
    cyFillerBinHasContentFLG=simFillerBinAD>3;
    
  }//+++
  
  //===
  
  @Deprecated public final int ccGetContent(char px_sb){
    switch(px_sb){
      case 'b':return simFillerBinAD;
      case 's':return simFillerSiloAD;
      default:return -1;
    }//..?
  }//+++
  
}//***eof
