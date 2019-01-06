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
    cxDustGenerateFLG,
    cxBagHopperDischargeFLG,
    //--
    cyBagHopperHasContentFLG
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
    cmMainBagScrewStartTM = new ZcOnDelayTimer(20);

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
  
  private final ZiTimer
    simDustGenerateDelay=new ZcDelayor(90,90);
    ;//...
  
  @Override public void ccSimulate(){
    
    //-- bag levelor
    simDustGenerateDelay.ccAct(cxDustGenerateFLG);
    if(simDustGenerateDelay.ccIsUp()){
      simBagHopperContant+=simBagHopperContant<1200?
        sysOwner.random(0,3):0;
    }//..?
    if(dcMainBagScrewAN&&dcDustExtractScrewAN)
      {simBagHopperContant-=simBagHopperContant>20?2:0;}
    if(cxBagHopperDischargeFLG){
      simBagHopperContant-=simBagHopperContant>20?
        sysOwner.random(3,4):0;
    }//..?
    dcF2L=simBagHopperContant>400;
    dcF2H=simBagHopperContant>800;
    cyBagHopperHasContentFLG=simBagHopperContant>30;
    
  }//+++
  
  //===
  
  
  //[TODO]::public final void ccSetBagPulserTimer(){}
  
  public final void ccSetBagFilterSize(int pxSize){
    cmBagPulseTotal=pxSize;
  }//+++
  
  @Deprecated public final int ccGetBagHopperContent(){
    return simBagHopperContant;
  }//+++
  
}//***eof
