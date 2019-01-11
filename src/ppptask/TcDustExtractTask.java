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
import static processing.core.PApplet.ceil;

public final class TcDustExtractTask extends ZcTask{
  
  private static TcDustExtractTask self;
  private TcDustExtractTask(){}//++!
  public static TcDustExtractTask ccGetReference(){
    if(self==null){self=new TcDustExtractTask();}
    return self;
  }//++!
  
  //===
  public boolean
    mnDustExtractStartSW,mnDustExtractStartPL,
    //--
    dcCoarseScrewAN,
    dcMainBagScrewAN,dcDustExtractScrewAN,
    dcF2H,dcF2L,dcCoolingDamperMV
  ;//...
  
  public int
    mnBagPulseCurrentCount,
    mnBagEntranceTempLimitLOW
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
    
    boolean
      lpAN10=TcVBurnerDryerTask.ccGetReference().dcVExfanAN,
      lpAN13=TcMainTask.ccGetReference().dcVCompressorAN,
      lpCAS=TcAggregateSupplyTask.ccGetReference().dcCAS;
        
    
    //-- rolls bag pulse
    boolean lpBagPulseStartFLG=lpAN13&&lpCAS;
    if(lpBagPulseStartFLG){cmBagPulseRoller++;}cmBagPulseRoller&=0x1F;
    if(cmBagPulseRoller==cmBagPulseRollerJudge){cmBagPulseCurrentCount++;}
    if(cmBagPulseCurrentCount>=cmBagPulseTotal){cmBagPulseCurrentCount=1;}
    mnBagPulseCurrentCount=
      cmBagPulseRoller>cmBagPulseRollerJudge?cmBagPulseCurrentCount:0;
    if(!lpBagPulseStartFLG){mnBagPulseCurrentCount=0;}
    
    //-- motor control
    //-- motor control ** coarse screw
    dcCoarseScrewAN=lpAN10&&lpCAS;
    
    //-- motor control ** extract screw
    cmDustExtractHLD.ccHook(mnDustExtractStartSW);
    dcDustExtractScrewAN=cmDustExtractHLD.ccIsHooked();
    
    //-- motor control ** main screw
    cmMainBagScrewStartTM.ccAct(
      dcDustExtractScrewAN||
      TcAutoWeighTask.ccGetReference().dcFR2);
    dcMainBagScrewAN=cmMainBagScrewStartTM.ccIsUp();
    
    //-- motor control ** feed back
    mnDustExtractStartPL=
      cmDustExtractHLD.ccIsHooked()&&
      (dcMainBagScrewAN?true:sysOneSecondFLK);
    
  }//+++
  
  //===

  private final ZcSiloModel simBagHopper=
    new ZcSiloModel(2000, 500, 600, 1200);//...
  
  private final ZiTimer simDustGenerateDelay=
    new ZcDelayor(90,90);//...
  
  @Override public void ccSimulate(){
    
    //-- bag hopper
    simDustGenerateDelay.ccAct(
      TcVBurnerDryerTask.ccGetReference().dcVExfanAN&&
      TcAggregateSupplyTask.ccGetReference().dcCAS
    );
    simBagHopper.ccCharge(
      simDustGenerateDelay.ccIsUp(),
      ceil(sysOwner.random(4,8))
    );
    simBagHopper.ccDischarge(
      TcAutoWeighTask.ccGetReference().cyUsingFR(2),
      ceil(sysOwner.random(8,12))
    );
    
    //-- bag levelor
    dcF2L=simBagHopper.ccIsMiddle();
    dcF2H=simBagHopper.ccIsFull();
    
  }//+++
  
  //===
  
  //[TODO]::public final void ccSetBagPulserTimer(){}
  
  public final void ccSetBagFilterSize(int pxSize){
    cmBagPulseTotal=pxSize;
  }//+++
  
  //===
  
  public final boolean cyBagHopperHasContent(){
    return simBagHopper.ccCanSupply();
  }//+++
  
  @Deprecated public final int testGetBagHopperContent(){
    return simBagHopper.ccGetValue();
  }//+++
  
}//***eof
