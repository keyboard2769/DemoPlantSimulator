/*
 * Copyright (C) 2019 keypad
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
import kosui.ppplogic.ZcOffDelayTimer;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcPulser;
import kosui.ppplogic.ZcStepper;
import kosui.ppplogic.ZiTimer;

import kosui.ppplocalui.VcTagger;
import static processing.core.PApplet.println;

public final class TcAutoWeighTask extends ZcTask{
    
  private static TcAutoWeighTask self;
  private TcAutoWeighTask(){}//++!
  public static TcAutoWeighTask ccGetReference(){
    if(self==null){self=new TcAutoWeighTask();}
    return self;
  }//++!
  
  //===
  
  private static final int
    C_S_MIXER_STOP    = 0x00,
    C_S_MIXER_WAITING = 0x10,
    C_S_MIXER_DRY     = 0x20,
    C_S_MIXER_WET     = 0x30,
    C_S_MIXER_OPEN    = 0x40,
    C_S_MIXER_OPEN_CONFIRM  = 0x45,
    C_S_MIXER_CLOSE         = 0x50,
    C_S_MIXER_CLOSE_CONFIRM = 0x55
  ;//..
  
  //===
  
  public boolean
    mnWeighManualSW,mnWeighManualPL,
    mnWeighAutoSW,mnWeighAutoPL,
    mnWeighRunSW,mnWeighRunPL,
    //--
    mnMixerGateAutoSW,mnMixerGateHoldSW,mnMixerGateOpenSW,
    mnMixerGateAutoPL,mnMixerGateHoldPL,mnMixerGateOpenPL,
    mnMixerHasMixturePL,
    mnBatchCountDownPLS,mnDryStartPLS,
    //--
    mnAGLockPL,mnAGLockSW,mnAGDPL,mnAGDSW,
    mnAG6SW,mnAG5SW,mnAG4SW,mnAG3SW,mnAG2SW,mnAG1SW,
    mnFRLockPL,mnFRLockSW,mnFRDPL,mnFRDSW,
    mnFR2SW,mnFR1SW,
    mnASLockPL,mnASLockSW,mnASDPL,mnASDSW,
    mnAS1SW,
    //--
    cmAG6W,cmAG5W,cmAG4W,cmAG3W,cmAG2W,cmAG1W,
    //--
    dcMXD,dcMOL,dcMCL,
    //--
    dcASSprayPumpAN,
    dcAGD,
    dcAG6OMV,dcAG5OMV,dcAG4OMV,dcAG3OMV,dcAG2OMV,dcAG1OMV,
    dcAG6CMV,dcAG5CMV,dcAG4CMV,dcAG3CMV,dcAG2CMV,dcAG1CMV,
    dcAG6MAS,dcAG5MAS,dcAG4MAS,dcAG3MAS,dcAG2MAS,dcAG1MAS,
    dcFRD,dcFR2,dcFR1,
    dcASD,dcAS1
  ;//...
  
  public int
    mnBatchCounter=3,
    mnWetTimeSetting=20, mnDryTimeSetting=10,
    mnWetTimeRemain, mnDryTimeRemain,
    //--
    mnAGEmptyAD=410,mnFREmptyAD=410,mnASEmptryAD=420,
    //--
    mnAG6TargetAD,mnAG5TargetAD,mnAG4TargetAD,
    mnAG3TargetAD,mnAG2TargetAD,mnAG1TargetAD,
    mnFR2TargetAD,mnFR1TargetAD,
    mnAS1TargetAD,
    //--
    dcTH6=600,
    dcAGCellAD=500,dcFRCellAD=500,dcASCellAD=500
  ;//...
  
  //===
  
  private boolean 
    cmWeighAutoFLG,
    cmMixerGateHoldFLG,cmMixerGateOpenFLG,
    
    cmHasAll, cmHasAG, cmHasFR, cmHasAS
    
  ;//...
  
  private final ZcHookFlicker
    cmWeighRunHLD = new ZcHookFlicker(),
    //--
    cmAGDischargeHLD=new ZcHookFlicker(),
    cmFRDischargeHLD=new ZcHookFlicker(),
    cmASDischargeHLD=new ZcHookFlicker()
  ;//...
  
  private final ZiTimer
    
    cmAllWeighOverConfirmTM = new ZcOnDelayTimer(10),
    cmMixerDischargeTM=new ZcOnDelayTimer(40),
    cmMixerConfirmTM=new ZcOnDelayTimer(10),
    
    //--
    cmFR2WeighStartWait = new ZcOnDelayTimer(30),
    cmFR1WeighStartWait = new ZcOnDelayTimer(30),
    //--
    cmAG6WeighStartWait = new ZcOnDelayTimer(30),
    cmAG5WeighStartWait = new ZcOnDelayTimer(30),
    cmAG4WeighStartWait = new ZcOnDelayTimer(30),
    cmAG3WeighStartWait = new ZcOnDelayTimer(30),
    cmAG2WeighStartWait = new ZcOnDelayTimer(30),
    cmAG1WeighStartWait = new ZcOnDelayTimer(30),
    //--
    //--
    cmAS1WeighStartWait = new ZcOnDelayTimer(30),
    cmASDischargeValveDelayTM = new ZcOnDelayTimer(20),
    cmASSprayPumpDelayTM     = new ZcOffDelayTimer(40)
  ;//...
  
  private final ZcWeighController
    cmFRController=new ZcWeighController(),
    cmAGController=new ZcWeighController(),
    cmASController=new ZcWeighController()
  ;//...
  
  private final ZcPulser
    cmBatchCountDownPLS=new ZcPulser(),
    cmDryStartPLS=new ZcPulser(),
    cmWetStartPLS=new ZcPulser()
  ;//...
  
  ZcStepper cmMixerStepper=new ZcStepper();
  
  @Override public void ccScan(){
    
    //-- mode exchange
    //-- mode exchange ** manual vs auto
    if(mnWeighAutoSW){cmWeighAutoFLG=true;}
    if(mnWeighManualSW){cmWeighAutoFLG=false;}
    mnWeighAutoPL=cmWeighAutoFLG;
    mnWeighManualPL=!cmWeighAutoFLG;
    //-- mode exchange ** running or not
    mnWeighRunPL=cmWeighRunHLD.ccHook(
      mnWeighRunSW,
      !mnWeighAutoPL//[EXTENDLATER]::like compressor is on or something
    );
    
    //-- discharge hook
    cmAGDischargeHLD.ccHook(mnAGDSW,cmWeighAutoFLG);
    cmFRDischargeHLD.ccHook(mnFRDSW,cmWeighAutoFLG);
    cmASDischargeHLD.ccHook(mnASDSW,cmWeighAutoFLG);
    
    //-- auto weight control
    
    //-- auto weight control ** judge
    boolean lpDryStart=cmDryStartPLS.ccUpPulse
      (cmMixerStepper.ccIsAt(C_S_MIXER_DRY));
    boolean lpWetStart=cmWetStartPLS.ccUpPulse
      (cmMixerStepper.ccIsAt(C_S_MIXER_WET));
    boolean lpActivateFlag=(mnBatchCounter>0)&&mnWeighRunPL;
    boolean lpLastBatchFlag=
      (mnBatchCounter<=1)&&
      !cmMixerStepper.ccIsAt(C_S_MIXER_WAITING
    );
    mnDryStartPLS=lpDryStart;
    
    //-- auto weight control ** ag
    cmAGController.ccTakeTargetAD(mnAGEmptyAD,
      mnAG6TargetAD, mnAG5TargetAD,
      mnAG4TargetAD, mnAG3TargetAD, mnAG2TargetAD, mnAG1TargetAD,
      mnAG1TargetAD
    );
    cmAGController.ccTakeControlBit(lpActivateFlag,
      lpDryStart
    );
    cmAGController.ccSetShutOut(lpLastBatchFlag);
    cmAGController.ccSetCellAD(dcAGCellAD);
    cmAGController.ccRun();
    //--
    cmAG6WeighStartWait.ccAct(cmAGController.ccIsWeighingAt(1));
    cmAG5WeighStartWait.ccAct(cmAGController.ccIsWeighingAt(2));
    cmAG4WeighStartWait.ccAct(cmAGController.ccIsWeighingAt(3));
    cmAG3WeighStartWait.ccAct(cmAGController.ccIsWeighingAt(4));
    cmAG2WeighStartWait.ccAct(cmAGController.ccIsWeighingAt(5));
    cmAG1WeighStartWait.ccAct(cmAGController.ccIsWeighingAt(6));
    
    //-- auto weight control ** fr
    cmFRController.ccTakeTargetAD(mnFREmptyAD,
      mnFR2TargetAD, mnFR1TargetAD, mnFR1TargetAD
    );
    cmFRController.ccTakeControlBit(lpActivateFlag,
      lpDryStart
    );
    cmFRController.ccSetShutOut(lpLastBatchFlag);
    cmFRController.ccSetCellAD(dcFRCellAD);
    cmFRController.ccRun();
    //--
    cmFR2WeighStartWait.ccAct(cmFRController.ccIsWeighingAt(1));
    cmFR1WeighStartWait.ccAct(cmFRController.ccIsWeighingAt(2));
    
    //-- auto weight control ** as
    cmASController.ccTakeTargetAD(mnASEmptryAD, mnAS1TargetAD);
    cmASController.ccTakeControlBit(lpActivateFlag,
      lpWetStart
    );
    cmASController.ccSetShutOut(lpLastBatchFlag);
    cmASController.ccSetCellAD(dcASCellAD);
    cmASController.ccRun();
    //--
    cmAS1WeighStartWait.ccAct(cmASController.ccIsWeighingAt(1));
    
    //-- step control
    
    //-- step control ** prepair
    cmAllWeighOverConfirmTM.ccAct(
      cmAGController.ccIsWaiting()
      &&cmFRController.ccIsWaiting()
      &&cmASController.ccIsWaiting()
    );
    
    //-- step control ** step
    cmMixerStepper.ccSetTo(C_S_MIXER_STOP, !mnWeighAutoPL);
    //--
    cmMixerStepper.ccStep(C_S_MIXER_STOP, C_S_MIXER_WAITING,
      mnWeighAutoPL);
    cmMixerStepper.ccStep(C_S_MIXER_WAITING, C_S_MIXER_DRY,
      cmAllWeighOverConfirmTM.ccIsUp());
    cmMixerStepper.ccStep(C_S_MIXER_DRY, C_S_MIXER_WET,
      mnDryTimeRemain==0);
    cmMixerStepper.ccStep(C_S_MIXER_WET, C_S_MIXER_OPEN,
      mnWetTimeRemain==0&&cmHasAll);
    cmMixerStepper.ccStep(C_S_MIXER_OPEN, C_S_MIXER_OPEN_CONFIRM,
      dcMOL);
    cmMixerStepper.ccStep(C_S_MIXER_OPEN_CONFIRM, C_S_MIXER_CLOSE,
      cmMixerDischargeTM.ccIsUp());
    cmMixerStepper.ccStep(C_S_MIXER_CLOSE, C_S_MIXER_CLOSE_CONFIRM,
      dcMCL);
    cmMixerStepper.ccStep(C_S_MIXER_CLOSE_CONFIRM, C_S_MIXER_WAITING,
      cmMixerConfirmTM.ccIsUp());
    
    //-- step control ** feedback
    
    boolean lpDischargeConfirmPeriod=
      cmMixerStepper.ccIsAt(C_S_MIXER_WAITING)||
      cmMixerStepper.ccIsAt(C_S_MIXER_DRY)||
      cmMixerStepper.ccIsAt(C_S_MIXER_WET);
    
    if(lpDischargeConfirmPeriod&&cmAGController.cmIsEmptyConfirming())
      {cmHasAG=true;}
    if(lpDischargeConfirmPeriod&&cmFRController.cmIsEmptyConfirming())
      {cmHasFR=true;}
    if(lpDischargeConfirmPeriod&&cmASController.cmIsEmptyConfirming())
      {cmHasAS=true;}
    if(mnBatchCountDownPLS){cmHasAG=false;cmHasFR=false;cmHasAS=false;}
    
    cmHasAll=cmHasAG&&cmHasAS&&cmHasFR;
    //--
    mnBatchCountDownPLS=cmBatchCountDownPLS.ccUpPulse
      (cmMixerStepper.ccIsAt(C_S_MIXER_CLOSE));
    
    //-- step control ** timer 
    if(cmMixerStepper.ccIsAt(C_S_MIXER_WAITING)){
      mnDryTimeRemain=mnDryTimeSetting;
      mnWetTimeRemain=mnWetTimeSetting;
    }//..?
    if(cmMixerStepper.ccIsAt(C_S_MIXER_DRY)&&sysOneSecondPLS)
      {mnDryTimeRemain-=mnDryTimeRemain>0?1:0;}
    if(cmMixerStepper.ccIsAt(C_S_MIXER_WET)&&sysOneSecondPLS)
      {mnWetTimeRemain-=mnWetTimeRemain>0?1:0;}
    cmMixerDischargeTM.ccAct(cmMixerStepper.ccIsAt(C_S_MIXER_OPEN_CONFIRM));
    cmMixerConfirmTM.ccAct(cmMixerStepper.ccIsAt(C_S_MIXER_CLOSE_CONFIRM));
    
    //-- auto flag
    cmAG6W=cmWeighAutoFLG? cmAG6WeighStartWait.ccIsUp():mnAG6SW;
    cmAG5W=cmWeighAutoFLG? cmAG5WeighStartWait.ccIsUp():mnAG5SW;
    cmAG4W=cmWeighAutoFLG? cmAG4WeighStartWait.ccIsUp():mnAG4SW;
    cmAG3W=cmWeighAutoFLG? cmAG3WeighStartWait.ccIsUp():mnAG3SW;
    cmAG2W=cmWeighAutoFLG? cmAG2WeighStartWait.ccIsUp():mnAG2SW;
    cmAG1W=cmWeighAutoFLG? cmAG1WeighStartWait.ccIsUp():mnAG1SW;
    //--
    boolean lpAGDischargeFLG=  cmAGController.ccIsDischarging();
    //--
    boolean lpFRDischargeFLG = cmFRController.ccIsDischarging();
    boolean lpFR2WeighFLG    = cmFR2WeighStartWait.ccIsUp();
    boolean lpFR1WeighFLG    = cmFR1WeighStartWait.ccIsUp();
    //--
    boolean lpASDischargeFLG = cmASController.ccIsDischarging();
    boolean lpAS1WeighFLG    = cmAS1WeighStartWait.ccIsUp();
    //--
    boolean lpMixerDischargeFLG=
      cmMixerStepper.ccIsAt(C_S_MIXER_OPEN)||
      cmMixerStepper.ccIsAt(C_S_MIXER_OPEN_CONFIRM);
    
    //-- output
    
    //-- output ** ag
    dcAGD=cmWeighAutoFLG?lpAGDischargeFLG:cmAGDischargeHLD.ccIsHooked();
    mnAGDPL=dcAGD;
    
    dcAG6OMV=cmAG6W;
    dcAG6CMV=!cmAG6W;
    
    dcAG5OMV= cmAG5W;
    dcAG5CMV= !cmAG5W;
    
    dcAG4OMV= cmAG4W;
    dcAG4CMV= !cmAG4W;
    
    dcAG3OMV= cmAG3W;
    dcAG3CMV= !cmAG3W;
    
    dcAG2OMV= cmAG2W;
    dcAG2CMV= !cmAG2W;
    
    dcAG1OMV= cmAG1W;
    dcAG1CMV= !cmAG1W;
    
    //-- output ** fr
    dcFRD=cmWeighAutoFLG?lpFRDischargeFLG:cmFRDischargeHLD.ccIsHooked();
    dcFR1=cmWeighAutoFLG?lpFR1WeighFLG:mnFR1SW;
    dcFR2=cmWeighAutoFLG?lpFR2WeighFLG:mnFR2SW;
    mnFRDPL=dcFRD;
    
    //-- output ** as
    boolean lpASDischageDUM=cmWeighAutoFLG?lpASDischargeFLG:
      cmASDischargeHLD.ccIsHooked();
    cmASDischargeValveDelayTM.ccAct(lpASDischageDUM);
    cmASSprayPumpDelayTM.ccAct(lpASDischageDUM);
    mnASDPL=lpASDischageDUM;
    dcASD=cmASDischargeValveDelayTM.ccIsUp();
    dcASSprayPumpAN=cmASSprayPumpDelayTM.ccIsUp();
    dcAS1=cmWeighAutoFLG?lpAS1WeighFLG:mnAS1SW;
    
    //-- output ** mixergate
    if(mnMixerGateAutoSW){cmMixerGateHoldFLG=false;cmMixerGateOpenFLG=false;}
    if(mnMixerGateHoldSW){cmMixerGateHoldFLG=true;cmMixerGateOpenFLG=false;}
    if(mnMixerGateOpenSW){cmMixerGateHoldFLG=false;cmMixerGateOpenFLG=true;}
    mnMixerGateAutoPL=(!cmMixerGateHoldFLG)&&(!cmMixerGateOpenFLG);
    mnMixerGateOpenPL=cmMixerGateOpenFLG;
    mnMixerGateHoldPL=cmMixerGateHoldFLG;
    dcMXD=cmMixerGateHoldFLG?false:
      cmMixerGateOpenFLG?true:
      lpMixerDischargeFLG;
    
    //-- output ** mixer has mixer pl
    if(dcMCL&&(dcFRD||dcAGD)){mnMixerHasMixturePL=true;}
    if(dcMOL){mnMixerHasMixturePL=false;}
    
  }//+++

  //===
  
  private final ZcCylinderGateModel
    simMixerGate=new ZcCylinderGateModel(),
    simAG6=new ZcCylinderGateModel(),
    simAG5=new ZcCylinderGateModel(),
    simAG4=new ZcCylinderGateModel(),
    simAG3=new ZcCylinderGateModel(),
    simAG2=new ZcCylinderGateModel(),
    simAG1=new ZcCylinderGateModel()
  ;//...
  
  private final ZiTimer
    simAGCellDischargeDelay = new ZcDelayor(5, 10),
    simFRCellChargeDelay = new ZcDelayor(10, 5),
    simFRCellDischargeDelay = new ZcDelayor(5, 10),
    simASCellChargeDelay = new ZcDelayor(10, 5),
    simASCellDischargeDelay = new ZcDelayor(5, 30)
  ;//...
  
  @Override public void ccSimulate(){
    
    boolean lpTest=true;
    
    boolean
      lpCompressorAN=TcMainTask.ccGetReference().dcVCompressorAN,
      //--
      lpAG6CanSupplyFLG=lpTest?true:
        TcAggregateSupplyTask.ccGetReference().cyHotbinHasContent(6),
      lpAG5CanSupplyFLG=lpTest?true:
        TcAggregateSupplyTask.ccGetReference().cyHotbinHasContent(5),
      lpAG4CanSupplyFLG=lpTest?true:
        TcAggregateSupplyTask.ccGetReference().cyHotbinHasContent(4),
      lpAG3CanSupplyFLG=lpTest?true:
        TcAggregateSupplyTask.ccGetReference().cyHotbinHasContent(3),
      lpAG2CanSupplyFLG=lpTest?true:
        TcAggregateSupplyTask.ccGetReference().cyHotbinHasContent(2),
      lpAG1CanSupplyFLG=lpTest?true:
        TcAggregateSupplyTask.ccGetReference().cyHotbinHasContent(1),
      //--
      lpFillerCanSupplyFLG=lpTest?true:
        TcFillerSupplyTask.ccGetReference().cyFillerBinHasContant(),
      lpDustCanSupply=lpTest?true:
        TcDustExtractTask.ccGetReference().cyBagHopperHasContent(),
      //--
      lpASCanSupplyFLG=
        TcMainTask.ccGetReference().dcASSupplyPumpAN
    ;//...
    
    //-- mixer gate
    simMixerGate.ccOpen(lpCompressorAN&&dcMXD, 1);
    simMixerGate.ccClose(lpCompressorAN&&!dcMXD, 1);
    dcMOL=simMixerGate.ccIsFullyOpened();
    dcMCL=simMixerGate.ccIsFullyClosed();
    
    //-- hotbin gate 
    
    //-- hotbin gate ** mv
    simAG6.ccOpen(lpCompressorAN&&dcAG6OMV, 1);
    simAG6.ccClose(lpCompressorAN&&dcAG6CMV, 1);
    simAG5.ccOpen(lpCompressorAN&&dcAG5OMV, 1);
    simAG5.ccClose(lpCompressorAN&&dcAG5CMV, 1);
    simAG4.ccOpen(lpCompressorAN&&dcAG4OMV, 1);
    simAG4.ccClose(lpCompressorAN&&dcAG4CMV, 1);
    simAG3.ccOpen(lpCompressorAN&&dcAG3OMV, 1);
    simAG3.ccClose(lpCompressorAN&&dcAG3CMV, 1);
    simAG2.ccOpen(lpCompressorAN&&dcAG2OMV, 1);
    simAG2.ccClose(lpCompressorAN&&dcAG2CMV, 1);
    simAG1.ccOpen(lpCompressorAN&&dcAG1OMV, 1);
    simAG1.ccClose(lpCompressorAN&&dcAG1CMV, 1);
    
    //-- hotbin gate ** as
    dcAG6MAS=simAG6.ccIsAtMiddleAS();
    dcAG5MAS=simAG5.ccIsAtMiddleAS();
    dcAG4MAS=simAG4.ccIsAtMiddleAS();
    dcAG3MAS=simAG3.ccIsAtMiddleAS();
    dcAG2MAS=simAG2.ccIsAtMiddleAS();
    dcAG1MAS=simAG1.ccIsAtMiddleAS();
    
    //-- cell
    
    //-- cell ** ag
    if(simAG6.ccIsNotClosed() && lpAG6CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG6.ccGetValue()/sysOwner.random(2, 4):0;}
    if(simAG5.ccIsNotClosed() && lpAG5CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG5.ccGetValue()/sysOwner.random(2, 4):0;}
    if(simAG4.ccIsNotClosed() && lpAG4CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG4.ccGetValue()/sysOwner.random(2, 3):0;}
    if(simAG3.ccIsNotClosed() && lpAG3CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG3.ccGetValue()/sysOwner.random(2, 3):0;}
    if(simAG2.ccIsNotClosed() && lpAG2CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG2.ccGetValue()/sysOwner.random(2, 3):0;}
    if(simAG1.ccIsNotClosed() && lpAG1CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG1.ccGetValue()/sysOwner.random(2, 3):0;}
    simAGCellDischargeDelay.ccAct(lpCompressorAN&&dcAGD);
    if(simAGCellDischargeDelay.ccIsUp())
      {dcAGCellAD-=dcAGCellAD>398?sysOwner.random(7, 14):0;}
    
    //-- cell ** fr
    simFRCellChargeDelay.ccAct(lpCompressorAN
      &&(  (lpFillerCanSupplyFLG&&dcFR1)
         ||(lpDustCanSupply     &&dcFR2))
    );
    simFRCellDischargeDelay.ccAct(lpCompressorAN&&dcFRD);
    if(simFRCellChargeDelay.ccIsUp())
      {dcFRCellAD+=dcFRCellAD<3602?sysOwner.random(3, 6):0;}
    if(simFRCellDischargeDelay.ccIsUp())
      {dcFRCellAD-=dcFRCellAD>398?sysOwner.random(8, 14):0;}
    
    //-- cell ** as
    simASCellChargeDelay.ccAct(lpCompressorAN&&lpASCanSupplyFLG&&dcAS1);
    simASCellDischargeDelay.ccAct(lpCompressorAN&&dcASD&&dcASSprayPumpAN);
    if(simASCellChargeDelay.ccIsUp())
      {dcASCellAD+=dcASCellAD<3602?sysOwner.random(3, 6):0;}
    if(simASCellDischargeDelay.ccIsUp())
      {dcASCellAD-=dcASCellAD>398?sysOwner.random(8, 14):0;}
    
  }//+++
  
  //===
  
  public final int cyUsingAG(int pxMatt){
    switch(pxMatt){
      case 6:return simAG6.ccGetValue();
      case 5:return simAG5.ccGetValue();
      case 4:return simAG4.ccGetValue();
      case 3:return simAG3.ccGetValue();
      case 2:return simAG2.ccGetValue();
      case 1:return simAG1.ccGetValue();
      default:return 0;
    }//..?
  }//+++
  
  public final boolean cyUsingFR(int pxMatt){
    switch(pxMatt){
      case 1:return simFRCellChargeDelay.ccIsUp()&&dcFR1;
      case 2:return simFRCellChargeDelay.ccIsUp()&&dcFR2;
      default:return false;
    }//..?
  }//+++
  
  //===
  
  //[TODELETE]::
  @Deprecated public final boolean testGetStatus(){
    return false;
  }//+++
  @Deprecated public final void testReadUpRecipe(){
    println("=== FR === :");
    println(cmFRController.testGetComparator().testGetLevelSetting());
    println("=== AG === :");
    println(cmAGController.testGetComparator().testGetLevelSetting());
    println("=== AS === :");
    println(cmASController.testGetComparator().testGetLevelSetting());
  }//+++
  @Deprecated public final void testTag(){
    VcTagger.ccTag("==weigh-stage==");
    VcTagger.ccTag("AG",Integer.toHexString(cmAGController.testGetStage()));
    VcTagger.ccTag("FR",Integer.toHexString(cmFRController.testGetStage()));
    VcTagger.ccTag("AS",Integer.toHexString(cmASController.testGetStage()));
    VcTagger.ccTag("==mixer-stage==");
    VcTagger.ccTag("MX", Integer.toHexString(cmMixerStepper.testGetStage()));
  }//+++
  
}//***eof
