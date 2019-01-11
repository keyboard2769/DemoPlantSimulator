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

import kosui.ppplocalui.*;
import kosui.ppplogic.*;
import kosui.ppputil.*;

import ppptask.ZcWeighController;

import static processing.core.PApplet.println;

public final class TtfmTask{

  private static TtfmTask self;

  public static TtfmTask ccGetReference(){
    if(self==null){
      self=new TtfmTask();
    }
    return self;
  }//++!

  //===
  
  private TtfmTask(){

  }//++!

  //===
  final int C_S_MIXER_STOP=0x00,
    C_S_MIXER_WAITING=0x10,
    C_S_MIXER_WET=0x20,
    C_S_MIXER_DRY=0x30,
    C_S_MIXER_OPEN=0x40,
    C_S_MIXER_OPEN_CONFIRM=0x45,
    C_S_MIXER_CLOSE=0x50,
    C_S_MIXER_CLOSE_CONFIRM=0x55;

  int sysRoller=6;

  boolean sysFullSecondPLS;

  int mnWetTimeSetting=20, mnDryTimeSetting=10,
    mnWetTimeRemain, mnDryTimeRemain,
    mnBatchCounter=3;

  boolean mnMixerAUTO, mnMixerHOLD, mnMixerOPEN,
    mnWeighStart, mnDischarge, mnDischargePLS,
    //--

    cmActivateFlag,
    cmDischargeFlag,
    cmHasAll, cmHasAG, cmHasFR, cmHasAS,
    //--
    dcFR2, dcFR1, dcFRD,
    dcAG4, dcAG3, dcAG2, dcAG1, dcAGD,
    dcAS1, dcASD,
    dcMXD, dcMOL, dcMCL;

  ZcPulser cmBatchCountPLS=new ZcPulser();

  ZiTimer
    
    cmAllWeighOverConfirmTM = new ZcOnDelayTimer(10),
    cmMixerDischargeTM=new ZcOnDelayTimer(40),
    cmMixerConfirmTM=new ZcOnDelayTimer(10),
    //--
    cmFR2WeighStartWait=new ZcOnDelayTimer(10),
    cmFR1WeighStartWait=new ZcOnDelayTimer(10),
    cmAG4WeighStartWait=new ZcOnDelayTimer(10),
    cmAG3WeighStartWait=new ZcOnDelayTimer(10),
    cmAG2WeighStartWait=new ZcOnDelayTimer(10),
    cmAG1WeighStartWait=new ZcOnDelayTimer(10),
    cmAS1WeighStartWait=new ZcOnDelayTimer(10);

  ZcWeighController cmFRController=new ZcWeighController(),
    cmAGController=new ZcWeighController(),
    cmASController=new ZcWeighController();

  ZcRangedValueModel simMixerGate=new ZcRangedValueModel(0, 20),
    simFRCell=new ZcRangedValueModel(400, 3600),
    simAGCell=new ZcRangedValueModel(400, 3600),
    simASCell=new ZcRangedValueModel(400, 3600);

  ZcStepper cmMixerStepper=new ZcStepper();

  void ccUpdate(){

    sysRoller++;
    sysRoller&=0x0F;
    sysFullSecondPLS=sysRoller==9;

    if(mnDischargePLS){
      mnBatchCounter--;
    }

    VcTagger.ccTag("mnBatch", mnBatchCounter);
    VcTagger.ccTag("mnStartBit", mnWeighStart);
    VcTagger.ccTag("mnDischargeSW", mnDischarge);
    VcTagger.ccTag("--- ---");
    VcTagger.ccTag("MX-auto", mnMixerAUTO);
    VcTagger.ccTag("MX-hold", mnMixerHOLD);
    VcTagger.ccTag("MX-open", mnMixerOPEN);
    VcTagger.ccTag("--- ---");
    VcTagger.ccTag("-DRY-", mnDryTimeRemain);
    VcTagger.ccTag("-WET-", mnWetTimeRemain);
    VcTagger.ccTag("--- ---");

    ccScan();
    ccSimulate();
  }//+++

  void ccScan(){

    //-- judge

    //-- fr


    //-- ag

    dcAGD=cmAGController.ccIsDischarging();

    //-- as
    cmASController.ccTakeTargetAD(410, 750);
    cmASController.ccTakeControlBit(cmActivateFlag, cmMixerStepper.ccIsAt(C_S_MIXER_WET));
    cmASController.ccSetCellAD(simASCell.ccGetValue());
    cmASController.ccRun();

    cmAS1WeighStartWait.ccAct(cmASController.ccIsWeighingAt(1));

    dcAS1=cmAS1WeighStartWait.ccIsUp();
    dcASD=cmAGController.ccIsDischarging();

    //-- mixer
    cmAllWeighOverConfirmTM.ccAct(
      cmAGController.ccIsWaiting()
      &&cmFRController.ccIsWaiting()
      &&cmASController.ccIsWaiting()
    );

    cmMixerStepper.ccSetTo(C_S_MIXER_STOP, !mnMixerAUTO);
    cmMixerStepper.ccStep(C_S_MIXER_STOP, C_S_MIXER_WAITING, mnMixerAUTO);
    cmMixerStepper.ccStep(C_S_MIXER_WAITING, C_S_MIXER_DRY,
      cmAllWeighOverConfirmTM.ccIsUp());
    cmMixerStepper.ccStep(C_S_MIXER_DRY, C_S_MIXER_WET, mnDryTimeRemain==0);
    cmMixerStepper.ccStep(C_S_MIXER_WET, C_S_MIXER_OPEN,
      mnWetTimeRemain==0&&cmHasAll);
    cmMixerStepper.ccStep(C_S_MIXER_OPEN, C_S_MIXER_OPEN_CONFIRM, dcMOL);
    cmMixerStepper.ccStep(C_S_MIXER_OPEN_CONFIRM, C_S_MIXER_CLOSE,
      cmMixerDischargeTM.ccIsUp());
    cmMixerStepper.ccStep(C_S_MIXER_CLOSE, C_S_MIXER_CLOSE_CONFIRM, dcMCL);
    cmMixerStepper.ccStep(C_S_MIXER_CLOSE_CONFIRM, C_S_MIXER_WAITING,
      cmMixerConfirmTM.ccIsUp());

    cmHasAG=cmMixerStepper.ccIsAt(C_S_MIXER_WAITING)
      &&cmAGController.cmIsEmptyConfirming();
    cmHasFR=cmMixerStepper.ccIsAt(C_S_MIXER_WAITING)
      &&cmFRController.cmIsEmptyConfirming();
    cmHasAS=cmMixerStepper.ccIsAt(C_S_MIXER_WAITING)
      &&cmASController.cmIsEmptyConfirming();
    cmHasAll=cmHasAG&&cmHasAS&&cmHasFR;

    if(cmMixerStepper.ccIsAt(C_S_MIXER_WAITING)){
      mnWetTimeRemain=mnWetTimeSetting;
      mnDryTimeRemain=mnDryTimeSetting;
    }
    if(cmMixerStepper.ccIsAt(C_S_MIXER_DRY)&&sysFullSecondPLS){
      mnDryTimeRemain-=mnDryTimeRemain>0?1:0;
    }
    if(cmMixerStepper.ccIsAt(C_S_MIXER_WET)&&sysFullSecondPLS){
      mnWetTimeRemain-=mnWetTimeRemain>0?1:0;
    }
    cmMixerDischargeTM.ccAct(cmMixerStepper.ccIsAt(C_S_MIXER_OPEN_CONFIRM));
    cmMixerConfirmTM.ccAct(cmMixerStepper.ccIsAt(C_S_MIXER_OPEN_CONFIRM));
    mnDischargePLS=cmBatchCountPLS.ccUpPulse(cmMixerStepper.ccIsAt(C_S_MIXER_CLOSE));

    dcMXD=mnMixerAUTO?(cmMixerStepper.ccIsAt(C_S_MIXER_OPEN)
      ||cmMixerStepper.ccIsAt(C_S_MIXER_OPEN_CONFIRM))
      :mnMixerHOLD?false:mnMixerOPEN;

    //-- output
    VcTagger.ccTag("*-- fr --*");
    VcTagger.ccTag("FR2", dcFR2);
    VcTagger.ccTag("FR1", dcFR1);
    VcTagger.ccTag("FRD", dcFRD);

    VcTagger.ccTag("*-- ag --*");
    VcTagger.ccTag("AG4", dcAG4);
    VcTagger.ccTag("AG3", dcAG3);
    VcTagger.ccTag("AG2", dcAG2);
    VcTagger.ccTag("AG1", dcAG1);
    VcTagger.ccTag("AGD", dcAGD);

    VcTagger.ccTag("*-- as --*");
    VcTagger.ccTag("AS1", dcAS1);
    VcTagger.ccTag("ASD", dcASD);

    VcTagger.ccTag("*-- mixer --*");
    VcTagger.ccTag("MXD", dcMXD);
    VcTagger.ccTag("MOL", dcMOL);
    VcTagger.ccTag("MCL", dcMCL);

    VcTagger.ccTag("mstage", cmMixerStepper.testGetStage());

  }//+++

  void ccSimulate(){

    VcTagger.ccTag("*-cell-*");

    //-- fr
    simFRCell.ccShift((dcFR2||dcFR1)?4:0);
    simFRCell.ccShift(dcFRD?-6:0);
    VcTagger.ccTag("fr-cell", simFRCell.ccGetValue());

    //-- ag
    simAGCell.ccShift((dcAG4||dcAG3||dcAG2||dcAG1)?4:0);
    simAGCell.ccShift(dcAGD?-6:0);
    VcTagger.ccTag("ag-cell", simAGCell.ccGetValue());

    //-- as
    simASCell.ccShift((dcAS1)?4:0);
    simASCell.ccShift(dcASD?-6:0);
    VcTagger.ccTag("as-cell", simASCell.ccGetValue());

    //-- mxd
    simMixerGate.ccShift(dcMXD?1:0);
    simMixerGate.ccShift(!dcMXD?-1:0);
    dcMOL=simMixerGate.ccGetValue()>17;
    dcMCL=simMixerGate.ccGetValue()<3;

  }//+++

  //===
  void ccFlipStart(){
    mnWeighStart=!mnWeighStart;
  }//+++

  void ccReadupRecipe(){
    println("=== FR === :");
    println(cmFRController.testGetComparator().testGetLevelSetting());
    println("=== AG === :");
    println(cmAGController.testGetComparator().testGetLevelSetting());
    println("=== AS === :");
    println(cmASController.testGetComparator().testGetLevelSetting());
  }//+++

  void ccSetMixerMode(int m){
    mnMixerAUTO=m==1;
    mnMixerHOLD=m==2;
    mnMixerOPEN=m==3;
  }//+++

}//***eof
