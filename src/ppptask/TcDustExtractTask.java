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

import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZiTimer;

public class TcDustExtractTask extends ZcTask{
  
  public boolean
    mnDustExtractStartSW,mnDustExtractStartPL,
    //--
    dcCoarseScrewAN,
    dcMainBagScrewAN,dcDustExtractScrewAN,
    dcF2H,dcF2L,dcCoolingDamperMV,
    //--
    cxDustFeederStartFLG,
    cxBagPulseStartFLG,
    cxDustGenerateFLG
  ;//...
  
  public int
    mnBagPulseCurrentCount,
    mnBagEntranceTempLimitLOW,
    //--
    cxBagEntranceTempAD
  ;//...
  
  //=== private
  
  private int
    cmBagPulseRoller=0,cmBagPulseRollerJudge=20,
    cmBagPulseTotal=20,cmBagPulseCurrentCount
  ;//...
  
  private final ZcHookFlicker 
    cmDustExtractHLD=new ZcHookFlicker();//...
  
  private final ZiTimer 
    cmMainBagScrewStartTM = new ZcOnDelayTimer(50);

  @Override public void ccScan(){
    
    //-- rolls bag pulse
    if(cxBagPulseStartFLG){cmBagPulseRoller++;}cmBagPulseRoller&=0x1F;
    if(cmBagPulseRoller==cmBagPulseRollerJudge){cmBagPulseCurrentCount++;}
    if(cmBagPulseCurrentCount>=cmBagPulseTotal){cmBagPulseCurrentCount=1;}
    mnBagPulseCurrentCount=
      cmBagPulseRoller>cmBagPulseRollerJudge?cmBagPulseCurrentCount:0;
    if(!cxBagPulseStartFLG){mnBagPulseCurrentCount=0;}
    
    //-- motor control
    //-- motor control ** coarse screw
    dcCoarseScrewAN=cxDustGenerateFLG;
    
    //-- motor control ** extract screw
    cmDustExtractHLD.ccHook(mnDustExtractStartSW);
    dcDustExtractScrewAN=cmDustExtractHLD.ccIsHooked();
    
    //-- motor control ** main screw
    cmMainBagScrewStartTM.ccAct(dcDustExtractScrewAN||cxDustFeederStartFLG);
    dcMainBagScrewAN=cmMainBagScrewStartTM.ccIsUp();
    
    //-- motor control ** feed back
    mnDustExtractStartPL=
      cmDustExtractHLD.ccIsHooked()&&
      (dcMainBagScrewAN?true:sysOneSecondFLK);
    
  }//+++

  private int simBagHopperContant;
  
  @Override public void ccSimulate(){
    
    //-- bag levelor
    if(cxDustGenerateFLG)
      {simBagHopperContant+=simBagHopperContant<1200?1:0;}
    if(dcMainBagScrewAN&&(dcDustExtractScrewAN||cxDustFeederStartFLG))
      {simBagHopperContant-=simBagHopperContant>20?2:0;}
    dcF2L=simBagHopperContant>400;
    dcF2H=simBagHopperContant>800;
    
  }//+++
  
  //===
  
  //[TODO]::public final void ccSetBagPulserTimer(){}
  public final void ccSetBagFilterSize(int pxSize){
    cmBagPulseTotal=pxSize;
  }//+++
  
}//***eof
