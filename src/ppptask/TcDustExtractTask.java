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

import kosui.ppplogic.ZcHookFlicker;
import kosui.ppplogic.ZcDelayor;
import kosui.ppplogic.ZcFlicker;
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
    mnBagPulseRfSW,mnBagPulseDisableSW,mnBagPulseAlwaysSW,
    mnDustExtractStartSW,mnDustExtractStartPL,
    mnBagPulsePL,
    mnDustSiloAirDisableSW, mnDustSiloAirAlwaysSW,
    mnDustSiloDischargeSW,
    //--
    msBagSmallFG,msBagMiddleFG,msBagBigFG,
    //--
    dcCoarseScrewAN,
    dcMainBagScrewAN,dcDustExtractScrewAN,dcDustSiloAirMV,
    dcDustSiloFullLV,dcDustSiloDischargeMV,
    dcF2H,dcF2L,dcCoolingDamperMV
  ;//...
  
  public int
    mnBagEntranceTempLimitLOW,
    //--
    dcCT33,dcCT22
  ;//...
  
  //=== private
  
  private final ZcHookFlicker 
    cmDustExtractHLD=new ZcHookFlicker();//...
  
  private final ZiTimer 
    cmMainBagScrewStartTM = new ZcOnDelayTimer(20)
  ;//...
  
  private final ZcFlicker
    cmDustSiloAirTM = new ZcFlicker(40, 0.5f)
  ;//...
  
  public final ZcBagPulseController cmController=new ZcBagPulseController();

  @Override public void ccScan(){
    
    boolean
      lpAN10=TcVBurnerDryerTask.ccGetReference().dcVExfanAN,
      lpAN13=TcMainTask.ccGetReference().dcVCompressorAN,
      lpCAS=TcAggregateSupplyTask.ccGetReference().dcCAS,
      lpPulseRunFLG=mnBagPulseAlwaysSW?true:
        mnBagPulseDisableSW?false:
        mnBagPulseRfSW?(lpAN13&&lpCAS):lpAN13
    ;//...
    
    
    int //[TODO]::timer numbering system not done yet
      lpBagPulseSpan=40,
      lpBagPulseDuty=40
    ;//...
    
    //-- rolls bag pulse
    cmController.ccSetFlicker(lpBagPulseSpan,lpBagPulseDuty);
    cmController.ccSetMaxCount(
      msBagSmallFG?20:
      msBagMiddleFG?24:
      msBagBigFG?30:24
    );
    cmController.ccRun(lpPulseRunFLG);
    mnBagPulsePL=cmController.ccGetOutputSignal()&&lpPulseRunFLG;
    
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
    
    //-- dust silo ** airation
    cmDustSiloAirTM.ccAct(dcDustExtractScrewAN);
    dcDustSiloAirMV=mnDustSiloAirDisableSW?false:
      mnDustSiloAirAlwaysSW?true:
      cmDustSiloAirTM.ccIsUp();
    
    //-- dust silo ** discharge
    dcDustSiloDischargeMV=mnDustSiloDischargeSW;
    
  }//+++
  
  //===

  public final ZcSiloModel 
    simBagHopper = new ZcSiloModel(2000, 500, 600, 1200),
    simDustSilo  = new ZcSiloModel(3000, 800, 1800,2800)
  ;//...
  
  private final ZiTimer
    simDustGenerateDelay = new ZcDelayor(90,90)
  ;//...
  
  private final ZcMotor
    simM33=new ZcMotor(),
    simM22=new ZcMotor()
  ;//...
    
  @Override public void ccSimulate(){
    
    boolean lpGenerating=
      TcVBurnerDryerTask.ccGetReference().dcVExfanAN&&
      TcAggregateSupplyTask.ccGetReference().dcCAS;
    
    //-- transfer
    ZcSiloModel.fnTransfer(
      simBagHopper,simDustSilo,
      dcMainBagScrewAN&&dcDustExtractScrewAN,
      8
    );
    
    //-- bag hopper
    simDustGenerateDelay.ccAct(lpGenerating);
    simBagHopper.ccCharge(
      simDustGenerateDelay.ccIsUp(),
      ceil(sysOwner.random(4,8))
    );
    dcF2L=simBagHopper.ccIsMiddle();
    dcF2H=simBagHopper.ccIsFull();
    
    //-- dust silo
    simDustSilo.ccDischarge(dcDustSiloDischargeMV, 10);
    dcDustSiloFullLV=simDustSilo.ccIsFull();
    
    //-- power 
    dcCT33=simM33.ccContact(dcMainBagScrewAN, 0.75f);
    dcCT22=simM22.ccContact(dcCoarseScrewAN,lpGenerating?0.88f:0.75f);
    
  }//+++
  
  //===
  
  public final boolean cyBagHopperHasContent(){
    return simBagHopper.ccCanSupply();
  }//+++
  
  @Deprecated public final int testGetBagHopperContent(){
    return simBagHopper.ccGetValue();
  }//+++
  
  @Deprecated public final int testGetCurrentPulseCount(){
    return cmController.ccGetCurrentCount();
  }//+++
  
}//***eof
