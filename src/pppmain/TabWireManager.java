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

package pppmain;

import javax.swing.SwingUtilities;
import static processing.core.PApplet.ceil;
import static processing.core.PApplet.map;

import static pppmain.MainOperationModel.C_FEEDER_AD_MAX;
import static pppmain.MainOperationModel.C_FEEDER_RPM_MAX;
import static pppmain.MainOperationModel.C_GENERAL_AD_MAX;
import static pppmain.MainOperationModel.C_GENERAL_AD_MIN;

import static pppmain.MainSketch.hisUI;
import static pppmain.MainSketch.myPLC;
import static pppmain.MainSketch.yourMOD;
import static pppmain.MainSketch.herManager;
import ppptable.McAutoWeighSetting;
import ppptable.McBaseKeyValueRangedSetting;
import ppptable.McRecipeTable;
import ppptable.McSettingFolder;

public final class TabWireManager {
  
  private static MainSketch mainSketch;
  
  private TabWireManager(){}//++!
  
  //===
  
  //-- for swing action
  public static final int 
    C_K_MODIFY_SETTING = 0xCA0010,
    C_K_QUIT           = 0xCA0001,
    C_K_NONE           = 0xCA0000
  ;//...
  private static volatile int
    actionID,
    //--
    settingID,
    settingValue
  ;
  private static volatile String actionPARAM;
  
  //-- 
  private static int 
    cmFRWeighStageHolder=0,
    cmAGWeighStageHolder=0,
    cmASWeighStageHolder=0
  ;//...
  
  public static final void ccInit(){
    mainSketch=MainSketch.ccGetReference();
    actionID=0;
    actionPARAM="<>";
    settingID=0;
    settingValue=0;
  }//++!
  
  //===
  
  public static final void ccSetup(){
    
    ckWireTableContent();
    
    //[TEST]::
    McRecipeTable.ccGetReference().dummyLoadFromFile();
    
  }//+++
  
  public static final void ccUpdate(){
    
    //-- single
    ccKeep();
    
    //-- plc
    logicRecieveFromPLC();
    logicSuperior();
    logicSendToPLC();
    
    //-- control
    controlWeigh();
    controlVMotor();
    controlVBurner();
    
    //-- wire
    wireVFeeder();
    wireVBurnerDryer();
    wireBagFilter();
    wireFillerAndDust();
    wireASSupplyChain();
    wireApTower();
    wireMixer();
    
  }//+++
  
  //=== swing
  
  private static void ccKeep(){
    switch(actionID){
      
      case C_K_MODIFY_SETTING:ckModifySetting();break;
      
      case C_K_QUIT:mainSketch.fsPover();break;
       
      default:break;
    }//..?
    ccClearCommand();
  }//+++
  
  private static void ckModifySetting(){
    
    int lpTableID=settingID%100;
    int lpListID=(settingID-lpTableID)/100;
    
    McBaseKeyValueRangedSetting lpList=
      McSettingFolder.ccGetReference().ccGet(lpListID);
    lpList.ccSetFloatValue(lpTableID, ((float)settingValue)/10);
    
    ckWireTableContent();
    SwingUtilities.invokeLater(herManager.cmUpdateSettingTable);
    
  }//+++
  
  private static void ckWireTableContent(){
    
    yourMOD.cmDryTimeSetting=
      McAutoWeighSetting.ccGetReference().ccGetIntegerValue("--drytime");
    
    yourMOD.cmWetTimeSetting=
      McAutoWeighSetting.ccGetReference().ccGetIntegerValue("--wettime");
    
  }//+++
  
  //=== PLC
  
  private static void logicRecieveFromPLC(){
    
    //-- weigh gate
    //-- weigh gate ** control
    yourMOD.cmIsAutoWeighRunnning=myPLC.cmAutoWeighTask.mnWeighRunPL;
    //-- weigh gate ** ag
    yourMOD.cmAGD=myPLC.cmAutoWeighTask.dcAGD;
    yourMOD.cmAG6=myPLC.cmAutoWeighTask.cmAG6W;
    yourMOD.cmAG5=myPLC.cmAutoWeighTask.cmAG5W;
    yourMOD.cmAG4=myPLC.cmAutoWeighTask.cmAG4W;
    yourMOD.cmAG3=myPLC.cmAutoWeighTask.cmAG3W;
    yourMOD.cmAG2=myPLC.cmAutoWeighTask.cmAG2W;
    yourMOD.cmAG1=myPLC.cmAutoWeighTask.cmAG1W;
    //-- weigh gate ** fr
    yourMOD.cmFRD=myPLC.cmAutoWeighTask.dcFRD;
    yourMOD.cmFR2=myPLC.cmAutoWeighTask.dcFR2;
    yourMOD.cmFR1=myPLC.cmAutoWeighTask.dcFR1;
    //-- weigh gate ** as
    yourMOD.cmASD=myPLC.cmAutoWeighTask.dcASD;
    yourMOD.cmAS1=myPLC.cmAutoWeighTask.dcAS1;
    //-- 
    
    
    
    //-- cell
    yourMOD.vmAGCellKG=MainOperationModel.fnToRealValue(
      myPLC.cmAutoWeighTask.dcAGCellAD,
      yourMOD.cmAGCellADJUTST
    );
    yourMOD.vmFRCellKG=MainOperationModel.fnToRealValue(
      myPLC.cmAutoWeighTask.dcFRCellAD,
      yourMOD.cmFRCellADJUTST
    );
    yourMOD.vmASCellKG=MainOperationModel.fnToRealValue(
      myPLC.cmAutoWeighTask.dcASCellAD,
      yourMOD.cmASCellADJUTST
    );
    
    //-- temperature
    yourMOD.vmBagEntranceTemprature=MainOperationModel.fnToRealValue
      (myPLC.cmVBurnerDryerTask.dcTH2, yourMOD.cmBagEntranceTempratureADJUST);
    
    yourMOD.vmMixtureTemprature=MainOperationModel.fnToRealValue
      (myPLC.cmAutoWeighTask.dcTH6, yourMOD.cmMixtureTempratureADJUST);
    
    //-- recieveing ** current
    yourMOD.vmCurrentVALUE.set(0, MainOperationModel.testCurrent(
      myPLC.cmMainTask.dcCT13,75
    ));
    yourMOD.vmCurrentVALUE.set(1, MainOperationModel.testCurrent(
      myPLC.cmMainTask.dcCT6,280
    ));
    yourMOD.vmCurrentVALUE.set(2, MainOperationModel.testCurrent(
      myPLC.cmVBurnerDryerTask.dcCT10,226
    ));
    yourMOD.vmCurrentVALUE.set(4, MainOperationModel.testCurrent(
      myPLC.cmVBurnerDryerTask.dcCT28,95
    ));
    yourMOD.vmCurrentVALUE.set(5, MainOperationModel.testCurrent(
      myPLC.cmVBurnerDryerTask.dcCT29,22
    ));
    
  }//+++
  
  private static void logicSuperior(){
    
    //-- system
    hisUI.cmSystemButton.ccSetIsActivated(MainSketch.fnFullSecondFLK());
    
    //-- auto weigh
    if(myPLC.cmAutoWeighTask.mnBatchCountDownPLS){
      yourMOD.ccLogAutoWeighResult();
      yourMOD.fsBatchCountDown();
      yourMOD.ccApplyCurrentRecipe();
    }//+++
  
    //-- auto weigh ** sampling
    
    if(myPLC.cmAutoWeighTask.mnDryStartPLS){
      yourMOD.ccPopAutoWeighResult();
      cmAGWeighStageHolder=0;
      cmFRWeighStageHolder=0;
      cmASWeighStageHolder=0;
    }//..?
    //-- auto weigh ** sampling ** ag
    if(yourMOD.cmAG6){cmAGWeighStageHolder=6;}
    if(yourMOD.cmAG5){cmAGWeighStageHolder=5;}
    if(yourMOD.cmAG4){cmAGWeighStageHolder=4;}
    if(yourMOD.cmAG3){cmAGWeighStageHolder=3;}
    if(yourMOD.cmAG2){cmAGWeighStageHolder=2;}
    if(yourMOD.cmAG1){cmAGWeighStageHolder=1;}
    if(cmAGWeighStageHolder==6){yourMOD.vmResultAG6=yourMOD.vmAGCellKG;}
    if(cmAGWeighStageHolder==5){yourMOD.vmResultAG5=yourMOD.vmAGCellKG;}
    if(cmAGWeighStageHolder==4){yourMOD.vmResultAG4=yourMOD.vmAGCellKG;}
    if(cmAGWeighStageHolder==3){yourMOD.vmResultAG3=yourMOD.vmAGCellKG;}
    if(cmAGWeighStageHolder==2){yourMOD.vmResultAG2=yourMOD.vmAGCellKG;}
    if(cmAGWeighStageHolder==1){yourMOD.vmResultAG1=yourMOD.vmAGCellKG;}
    if(cmAGWeighStageHolder==0){
      yourMOD.vmResultAG1=0;
      yourMOD.vmResultAG2=0;
      yourMOD.vmResultAG3=0;
      yourMOD.vmResultAG4=0;
      yourMOD.vmResultAG5=0;
      yourMOD.vmResultAG6=0;
    }//..?
    //-- auto weigh ** sampling ** fr
    if(yourMOD.cmFR2){cmFRWeighStageHolder=2;}
    if(yourMOD.cmFR1){cmFRWeighStageHolder=1;}
    if(cmFRWeighStageHolder==2){yourMOD.vmResultFR2=yourMOD.vmFRCellKG;}
    if(cmFRWeighStageHolder==1){yourMOD.vmResultFR1=yourMOD.vmFRCellKG;}
    if(cmFRWeighStageHolder==0){
      yourMOD.vmResultFR1=0;
      yourMOD.vmResultFR2=0;
    }//..?
    //-- auto weigh ** sampling ** as
    if(yourMOD.cmASD){cmASWeighStageHolder=0;}
    if(yourMOD.cmAS1){cmASWeighStageHolder=1;}
    if(cmASWeighStageHolder==1){yourMOD.vmResultAS1=yourMOD.vmASCellKG;}
    if(cmASWeighStageHolder==0){
      yourMOD.vmResultAS1=0;
    }//..?
  
  }//+++
  
  private static void logicSendToPLC(){
    
    //-- whatever
    myPLC.cmVBurnerDryerTask.mnVDPressureTargetAD=
      MainOperationModel.fntoADValue(
        yourMOD.vsVDryerTargetPressure, yourMOD.cmVDryerPressureADJUST
      );
    
    myPLC.cmVBurnerDryerTask.mnVDOLimitLow=
      MainOperationModel.fntoADValue(
        yourMOD.cmVExfanDegreeLimitLow, yourMOD.cmVExfanDegreeADJUST
      );
    
    myPLC.cmVBurnerDryerTask.mnVDOLimitHigh=
      MainOperationModel.fntoADValue(
        yourMOD.cmVExfanDegreeLimithigh, yourMOD.cmVExfanDegreeADJUST
      );
    
    myPLC.cmVBurnerDryerTask.mnVBTemratureTargetAD=
      MainOperationModel.fntoADValue(yourMOD.vsVBurnerTargetTempraure,
        yourMOD.cmAggregateChuteTempratureADJUST
      );
    
    myPLC.cmVBurnerDryerTask.mnCoolingDamperOpenSIG=
      (yourMOD.vmBagEntranceTemprature>yourMOD.cmBagEntranceTemprarueLimitLOW);
    
    myPLC.cmVBurnerDryerTask.mnFireStopSIG=
      (yourMOD.vmBagEntranceTemprature>yourMOD.cmBagEntranceTemprarueLimitHIGH);
    
    //-- filler 
    myPLC.cmFillerSupplyTask.mnFRSiloAirAutoSW=
      (yourMOD.vsFillerSiloAirNT==0);
    myPLC.cmFillerSupplyTask.mnFRSiloAirManualSW=
      (yourMOD.vsFillerSiloAirNT==2);
    
    //-- auto weigh ** batch control
    myPLC.cmAutoWeighTask.mnDryTimeSetting=
      yourMOD.cmDryTimeSetting;
    myPLC.cmAutoWeighTask.mnWetTimeSetting=
      yourMOD.cmWetTimeSetting;
    myPLC.cmAutoWeighTask.mnBatchCounter
      =yourMOD.ccGetCurrentRemianingBatch();
    
    //-- auto weigh ** taget ad
    myPLC.cmAutoWeighTask.mnAG6TargetAD=MainOperationModel.fntoADValue
      (yourMOD.vmTargetKG.ccGetAG(6),yourMOD.cmAGCellADJUTST);
    myPLC.cmAutoWeighTask.mnAG5TargetAD=MainOperationModel.fntoADValue
      (yourMOD.vmTargetKG.ccGetAG(5),yourMOD.cmAGCellADJUTST);
    myPLC.cmAutoWeighTask.mnAG4TargetAD=MainOperationModel.fntoADValue
      (yourMOD.vmTargetKG.ccGetAG(4),yourMOD.cmAGCellADJUTST);
    myPLC.cmAutoWeighTask.mnAG3TargetAD=MainOperationModel.fntoADValue
      (yourMOD.vmTargetKG.ccGetAG(3),yourMOD.cmAGCellADJUTST);
    myPLC.cmAutoWeighTask.mnAG2TargetAD=MainOperationModel.fntoADValue
      (yourMOD.vmTargetKG.ccGetAG(2),yourMOD.cmAGCellADJUTST);
    myPLC.cmAutoWeighTask.mnAG1TargetAD=MainOperationModel.fntoADValue
      (yourMOD.vmTargetKG.ccGetAG(1),yourMOD.cmAGCellADJUTST);
    myPLC.cmAutoWeighTask.mnFR2TargetAD=MainOperationModel.fntoADValue
      (yourMOD.vmTargetKG.ccGetFR(2),yourMOD.cmFRCellADJUTST);
    myPLC.cmAutoWeighTask.mnFR1TargetAD=MainOperationModel.fntoADValue
      (yourMOD.vmTargetKG.ccGetFR(1),yourMOD.cmFRCellADJUTST);
    myPLC.cmAutoWeighTask.mnAS1TargetAD=MainOperationModel.fntoADValue
      (yourMOD.vmTargetKG.ccGetAS(1),yourMOD.cmASCellADJUTST);
  
  }//+++
  
  //=== control
  
  private static void controlWeigh(){
    
    //-- mode exchange
    myPLC.cmAutoWeighTask.mnWeighManualSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_MANN);
    hisUI.cmBookingControlGroup.cmManualSW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnWeighManualPL);
    
    myPLC.cmAutoWeighTask.mnWeighAutoSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AUTO);
    hisUI.cmBookingControlGroup.cmAutoSW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnWeighAutoPL);
    
    myPLC.cmAutoWeighTask.mnWeighRunSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_RUN);
    hisUI.cmBookingControlGroup.cmRunSW.ccSetIsActivated
      (yourMOD.cmIsAutoWeighRunnning);
    
    //-- booking table
    
    for(int i=0;i<MainOperationModel.C_MAX_BOOK_CAPABILITY;i++){
      
      hisUI.cmBookingControlGroup.cmDesRecipeBox[i].ccSetValue
        (yourMOD.cmBookedRecipe[i]);
      hisUI.cmBookingControlGroup.cmDesKGBox[i].ccSetValue
        (yourMOD.cmBookedKillogram[i]);
      hisUI.cmBookingControlGroup.cmDesBatchBox[i].ccSetValue
        (yourMOD.cmBookedBatch[i]);
      
    }//..~
    
    //-- gate
    
    //-- gate ** ag
    myPLC.cmAutoWeighTask.mnAGDSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH);
    hisUI.cmWeighControlGroup.cmAGDischargeSW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnAGDPL);
    
    myPLC.cmAutoWeighTask.mnAG6SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+6);
    hisUI.cmWeighControlGroup.cmAG6SW.ccSetIsActivated
      (yourMOD.cmAG6);
    
    myPLC.cmAutoWeighTask.mnAG5SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+5);
    hisUI.cmWeighControlGroup.cmAG5SW.ccSetIsActivated
      (yourMOD.cmAG5);
    
    myPLC.cmAutoWeighTask.mnAG4SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+4);
    hisUI.cmWeighControlGroup.cmAG4SW.ccSetIsActivated
      (yourMOD.cmAG4);
    
    myPLC.cmAutoWeighTask.mnAG3SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+3);
    hisUI.cmWeighControlGroup.cmAG3SW.ccSetIsActivated
      (yourMOD.cmAG3);
    
    myPLC.cmAutoWeighTask.mnAG2SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+2);
    hisUI.cmWeighControlGroup.cmAG2SW.ccSetIsActivated
      (yourMOD.cmAG2);
    
    myPLC.cmAutoWeighTask.mnAG1SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+1);
    hisUI.cmWeighControlGroup.cmAG1SW.ccSetIsActivated
      (yourMOD.cmAG1);
    
    //-- gate ** fr
    myPLC.cmAutoWeighTask.mnFRDSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_FR_DISH);
    hisUI.cmWeighControlGroup.cmFRDischargeSW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnFRDPL);
    
    myPLC.cmAutoWeighTask.mnFR2SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_FR_DISH+2);
    hisUI.cmWeighControlGroup.cmFR2SW.ccSetIsActivated
      (yourMOD.cmFR2);
    
    myPLC.cmAutoWeighTask.mnFR1SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_FR_DISH+1);
    hisUI.cmWeighControlGroup.cmFR1SW.ccSetIsActivated
      (yourMOD.cmFR1);
    
    //-- gate ** as
    myPLC.cmAutoWeighTask.mnASDSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AS_DISH);
    hisUI.cmWeighControlGroup.cmASDischargeSW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnASDPL);
    
    myPLC.cmAutoWeighTask.mnAS1SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AS_DISH+1);
    hisUI.cmWeighControlGroup.cmAS1SW.ccSetIsActivated
      (yourMOD.cmAS1);
    
    //-- cell value box
    
    //-- cell value box ** target 
    int lpAGTargetKG=0;
    int lpFRTargetKG=0;
    int lpASTargetKG=0;
    switch(cmAGWeighStageHolder){
      case 6:lpAGTargetKG=yourMOD.vmTargetKG.ccGetAG(6);break;
      case 5:lpAGTargetKG=yourMOD.vmTargetKG.ccGetAG(5);break;
      case 4:lpAGTargetKG=yourMOD.vmTargetKG.ccGetAG(4);break;
      case 3:lpAGTargetKG=yourMOD.vmTargetKG.ccGetAG(3);break;
      case 2:lpAGTargetKG=yourMOD.vmTargetKG.ccGetAG(2);break;
      case 1:lpAGTargetKG=yourMOD.vmTargetKG.ccGetAG(1);break;
    }//..?
    switch(cmFRWeighStageHolder){
      case 2:lpFRTargetKG=yourMOD.vmTargetKG.ccGetFR(2);break;
      case 1:lpFRTargetKG=yourMOD.vmTargetKG.ccGetFR(1);break;
    }//..?
    switch(cmASWeighStageHolder){
      case 1:lpASTargetKG=yourMOD.vmTargetKG.ccGetAS(1);break;
    }//..?
    hisUI.cmWeighControlGroup.cmAGWeigher.ccSetTargetKG(lpAGTargetKG);
    hisUI.cmWeighControlGroup.cmFRWeigher.ccSetTargetKG(lpFRTargetKG);
    hisUI.cmWeighControlGroup.cmASWeigher.ccSetTargetKG(lpASTargetKG);
    
    //-- cell value box ** current
    hisUI.cmWeighControlGroup.cmAGWeigher.ccSetCurrentKG
      (yourMOD.vmAGCellKG);
    hisUI.cmWeighControlGroup.cmASWeigher.ccSetCurrentKG
      (yourMOD.vmASCellKG);
    hisUI.cmWeighControlGroup.cmFRWeigher.ccSetCurrentKG
      (yourMOD.vmFRCellKG);
    
    //-- mixer 
    myPLC.cmAutoWeighTask.mnMixerGateAutoSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_MIXER_GATE_AUTO);
    hisUI.cmMixerControlGourp.cmAUTO.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnMixerGateAutoPL);
    
    myPLC.cmAutoWeighTask.mnMixerGateHoldSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_MIXER_GATE_HOLD);
    hisUI.cmMixerControlGourp.cmHOLD.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnMixerGateHoldPL);
    
    myPLC.cmAutoWeighTask.mnMixerGateOpenSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_MIXER_GATE_OPEN);
    hisUI.cmMixerControlGourp.cmOPEN.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnMixerGateOpenPL);
    
  }//+++
  
  private static void controlVMotor(){
    
    myPLC.cmMainTask.mnVCompressorSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+0);
    hisUI.cmVMotorControlGroup.cmMotorSW[0]
      .ccSetIsActivated(myPLC.cmMainTask.mnVCompressorPL);
    
    myPLC.cmVBurnerDryerTask.mnAPBlowerSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+3);
    hisUI.cmVMotorControlGroup.cmMotorSW[3]
      .ccSetIsActivated(myPLC.cmVBurnerDryerTask.mnAPBlowerPL);
    
    myPLC.cmDustExtractTask.mnDustExtractStartSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+5);
    hisUI.cmVMotorControlGroup.cmMotorSW[5]
      .ccSetIsActivated(myPLC.cmDustExtractTask.mnDustExtractStartPL);
    
    myPLC.cmMainTask.mnMixerMoterSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+6);
    hisUI.cmVMotorControlGroup.cmMotorSW[6]
      .ccSetIsActivated(myPLC.cmMainTask.mnMixerMoterPL);
    
    myPLC.cmFillerSupplyTask.mnFRSupplyStartSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+7);
    hisUI.cmVMotorControlGroup.cmMotorSW[7]
      .ccSetIsActivated(myPLC.cmFillerSupplyTask.mnFRSupplyStartPL);
    
    myPLC.cmAggregateSupplyTask.mnAGSupplyStartSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+9);
    hisUI.cmVMotorControlGroup.cmMotorSW[9]
      .ccSetIsActivated(myPLC.cmAggregateSupplyTask.mnAGSUpplyStartPL);
    
    myPLC.cmMainTask.mnASSupplyPumpSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+10);
    hisUI.cmVMotorControlGroup.cmMotorSW[10]
      .ccSetIsActivated(myPLC.cmMainTask.mnASSupplyPumpPL);
    
    myPLC.cmVBurnerDryerTask.mnVExfanMotorSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+12);
    hisUI.cmVMotorControlGroup.cmMotorSW[12]
      .ccSetIsActivated(myPLC.cmVBurnerDryerTask.mnVExfanMotorPL);
    
    myPLC.cmAggregateSupplyTask.mnVFeederStartSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+13);
    hisUI.cmVMotorControlGroup.cmMotorSW[13]
      .ccSetIsActivated(myPLC.cmAggregateSupplyTask.mnVFeederStartPL);
    
  }//+++
  
  private static void controlVBurner(){
    
    //-- vb
    myPLC.cmVBurnerDryerTask.mnVBCLSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VBCLSW);
    hisUI.cmVBurnerControlGroup.cmVBurnerCLoseSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.dcVBCLRY);
    
    myPLC.cmVBurnerDryerTask.mnVBOPSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VBOPSW);
    hisUI.cmVBurnerControlGroup.cmVBurnerOpenSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.dcVBOPRY);
    
    myPLC.cmVBurnerDryerTask.mnVBATSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VBATSW);
    hisUI.cmVBurnerControlGroup.cmVBurnerAutoSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.mnVBATPL);
    
    //-- vexf
    myPLC.cmVBurnerDryerTask.mnVEXFCLSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VEXFCLSW);
    hisUI.cmVBurnerControlGroup.cmVExfanCloseSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.dcVEFCLRY);
    
    myPLC.cmVBurnerDryerTask.mnVEXFOPSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VEXFOPSW);
    hisUI.cmVBurnerControlGroup.cmVExfanOpenSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.dcVEFOPRY);
    
    myPLC.cmVBurnerDryerTask.mnVEXFATSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VEXFATSW);
    hisUI.cmVBurnerControlGroup.cmVExfanAutoSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.mnVEXFATPL);
    
    //-- ignite
    myPLC.cmVBurnerDryerTask.mnVBIGNSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VBIGN);
    hisUI.cmVBurnerControlGroup.cmVBIgnitSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.mnVBIGNPL);
    hisUI.cmVBurnerControlGroup.cmVBurnerStagePL.ccSetStage
      (myPLC.cmVBurnerDryerTask.mnVBurnerIgniteStage);
    
  }//+++
  
  //=== wire
  
  private static void wireVFeeder(){
    
    //-- vhbc
    //(myPLC.cmAggregateSupplyTask.dcVHorizontalBelconAN?'a':'x');
    
    //-- vf
    //-- ** ** motor
    //  [TODO]::fill 
    //  (myPLC.cmAggregateSupplyTask.dcVFAN01?'a':'x');
    //  (myPLC.cmAggregateSupplyTask.dcVFAN02?'a':'x');
    //  (myPLC.cmAggregateSupplyTask.dcVFAN03?'a':'x');
    //  (myPLC.cmAggregateSupplyTask.dcVFAN04?'a':'x');
    //  (myPLC.cmAggregateSupplyTask.dcVFAN05?'a':'x');
    //  (myPLC.cmAggregateSupplyTask.dcVFAN06?'a':'x');
    
    //-- ** ** speed bar
    hisUI.cmVFeederGroup.cmVF01.ccSetRPM(yourMOD.cmVF01RPM);
    hisUI.cmVFeederGroup.cmVF02.ccSetRPM(yourMOD.cmVF02RPM);
    hisUI.cmVFeederGroup.cmVF03.ccSetRPM(yourMOD.cmVF03RPM);
    hisUI.cmVFeederGroup.cmVF04.ccSetRPM(yourMOD.cmVF04RPM);
    hisUI.cmVFeederGroup.cmVF05.ccSetRPM(yourMOD.cmVF05RPM);
    hisUI.cmVFeederGroup.cmVF06.ccSetRPM(yourMOD.cmVF06RPM);
    
    //-- ** ** stuck sensor
    hisUI.cmVFeederGroup.cmVF01
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG01);
    hisUI.cmVFeederGroup.cmVF02
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG02);
    hisUI.cmVFeederGroup.cmVF03
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG03);
    hisUI.cmVFeederGroup.cmVF04
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG04);
    hisUI.cmVFeederGroup.cmVF05
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG05);
    hisUI.cmVFeederGroup.cmVF06
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG06);
    
    //-- ** ** speed ad
    myPLC.cmAggregateSupplyTask.dcVFSP01=
      ceil(map(yourMOD.cmVF01RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    myPLC.cmAggregateSupplyTask.dcVFSP02=
      ceil(map(yourMOD.cmVF02RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    myPLC.cmAggregateSupplyTask.dcVFSP03=
      ceil(map(yourMOD.cmVF03RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    myPLC.cmAggregateSupplyTask.dcVFSP04=
      ceil(map(yourMOD.cmVF04RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    myPLC.cmAggregateSupplyTask.dcVFSP05=
      ceil(map(yourMOD.cmVF05RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    myPLC.cmAggregateSupplyTask.dcVFSP06=
      ceil(map(yourMOD.cmVF06RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    
  }//+++
  
  private static void wireVBurnerDryer(){
    
    //-- v burner
    hisUI.cmVBurnerControlGroup.cmVB.ccSetMotorON
      (myPLC.cmVBurnerDryerTask.dcVBurnerFanAN);
    hisUI.cmVBurnerControlGroup.cmVB.ccSetIsIgniting
      (myPLC.cmVBurnerDryerTask.dcIG);
    hisUI.cmVBurnerControlGroup.cmVB.ccSetIsPiloting
      (myPLC.cmVBurnerDryerTask.dcPV);
    hisUI.cmVBurnerControlGroup.cmVB.ccSetHasFire
      (myPLC.cmVBurnerDryerTask.dcMMV);
    hisUI.cmVBurnerControlGroup.cmVB.ccSetIsFull
      (myPLC.cmVBurnerDryerTask.dcVBOPLS);
    hisUI.cmVBurnerControlGroup.cmVB.ccSetIsClosed
      (myPLC.cmVBurnerDryerTask.dcVBCLLS);
    hisUI.cmVBurnerControlGroup.cmVB.ccSetDegree
      (MainOperationModel.fnToRealValue(
        myPLC.cmVBurnerDryerTask.dcVBO,
        yourMOD.cmVBurnerDegreeADJUST
      ));
    hisUI.cmVBurnerControlGroup.cmVB.ccSetTargetTemp
      (yourMOD.vsVBurnerTargetTempraure);
    
    //-- v combustor
    //[TODO]:: fill it
    //(myPLC.cmVBurnerDryerTask.dcFuelPumpAN?'a':'x');
    //(myPLC.cmVBurnerDryerTask.dcFuelMV);
    //(myPLC.cmVBurnerDryerTask.dcHeavyMV);
    
    //-- dryer
    hisUI.cmVBurnerControlGroup.cmVIBC.ccSetHasAggregateFlow
      (myPLC.cmAggregateSupplyTask.dcCAS);
    hisUI.cmVBurnerControlGroup.cmVD.ccSetIsOnFire
      (myPLC.cmVBurnerDryerTask.dcMMV);
    hisUI.cmVBurnerControlGroup.cmVD.ccSetKPA
      (MainOperationModel.fnToRealValue(
        myPLC.cmVBurnerDryerTask.dcVSE,
        yourMOD.cmVDryerPressureADJUST
      ));
    hisUI.cmVBurnerControlGroup.cmVD.ccSetTPH(ceil(map(
      myPLC.cmAggregateSupplyTask.dcVFCS,
      C_GENERAL_AD_MIN,C_GENERAL_AD_MAX,
      0,yourMOD.cmVDryerCapability
    )));
    hisUI.cmVBurnerControlGroup.cmVD.ccSetMotorON
      (myPLC.cmAggregateSupplyTask.dcVDryerAN);
    hisUI.cmVBurnerControlGroup.cmVIBC.ccSetMotorON
      (myPLC.cmAggregateSupplyTask.dcVInclineBelconAN);
    
  }//+++
  
  private static void wireBagFilter(){
    
    //-- bag
    
    //-- v exf
    hisUI.cmVBurnerControlGroup.cmVEXF.ccSetMotorON
      (myPLC.cmVBurnerDryerTask.dcVExfanAN);
    hisUI.cmVBurnerControlGroup.cmVEXF.ccSetIsFull
      (myPLC.cmVBurnerDryerTask.dcVEFOPLS);
    hisUI.cmVBurnerControlGroup.cmVEXF.ccSetIsClosed
      (myPLC.cmVBurnerDryerTask.dcVEFCLLS);
    hisUI.cmVBurnerControlGroup.cmVEXF.ccSetHasPressure
      (myPLC.cmVBurnerDryerTask.dcHSW);
    hisUI.cmVBurnerControlGroup.cmVEXF.ccSetDegree(
      MainOperationModel.fnToRealValue(
        myPLC.cmVBurnerDryerTask.dcVDO,yourMOD.cmVExfanDegreeADJUST
      )
    );
    
  }//+++
  
  private static void wireFillerAndDust(){
    hisUI.cmFillerSupplyGroup.cmFillerBin.ccSetLevelor
      (myPLC.cmFillerSupplyTask.dcFillerBinLV);
    hisUI.cmFillerSupplyGroup.cmDustBin.ccSetLevelor
      (myPLC.cmDustExtractTask.dcF2L,
       myPLC.cmDustExtractTask.dcF2H);
    hisUI.cmFillerSupplyGroup.cmFillerSilo.ccSetLevelor
      (myPLC.cmFillerSupplyTask.dcFillerSiloLLV, 
       myPLC.cmFillerSupplyTask.dcFillerSiloMLV,
       myPLC.cmFillerSupplyTask.dcFillerSiloHLV);
    hisUI.cmFillerSupplyGroup.cmBagPulsePL.ccSetIsActivated
      (myPLC.cmDustExtractTask.mnBagPulseCurrentCount%2==1);
  }//+++
  
  private static void wireASSupplyChain(){
    
    hisUI.cmASSSupplyModelGroup.cmASSupplyPump.ccSetHasAnswer
      (myPLC.cmMainTask.dcASSupplyPumpAN);
    hisUI.cmASSSupplyModelGroup.cmASSupplyPump.ccSetIsContacted
      (myPLC.cmMainTask.dcASSupplyPumpAN);
    hisUI.cmASSSupplyModelGroup.cmAS1.ccSetIsActivated
      (myPLC.cmAutoWeighTask.dcAS1);
    
    hisUI.cmASSSupplyModelGroup.cmASSprayPump.ccSetHasAnswer
      (myPLC.cmAutoWeighTask.dcASSprayPumpAN);
    hisUI.cmASSSupplyModelGroup.cmASSprayPump.ccSetIsContacted
      (myPLC.cmAutoWeighTask.dcASSprayPumpAN);
    hisUI.cmASSSupplyModelGroup.cmASD.ccSetIsActivated
      (myPLC.cmAutoWeighTask.dcASD);
    
  }//+++
  
  private static void wireApTower(){
    
    //-- motor
    
    //-- hb ** lv
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinLevel(6, 
      myPLC.cmAggregateSupplyTask.dcHB6H?'f':
      myPLC.cmAggregateSupplyTask.dcHB6L?'l':'x'
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinLevel(5, 
      myPLC.cmAggregateSupplyTask.dcHB5H?'f':
      myPLC.cmAggregateSupplyTask.dcHB5L?'l':'x'
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinLevel(4, 
      myPLC.cmAggregateSupplyTask.dcHB4H?'f':
      myPLC.cmAggregateSupplyTask.dcHB4L?'l':'x'
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinLevel(3, 
      myPLC.cmAggregateSupplyTask.dcHB3H?'f':
      myPLC.cmAggregateSupplyTask.dcHB3L?'l':'x'
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinLevel(2, 
      myPLC.cmAggregateSupplyTask.dcHB2H?'f':
      myPLC.cmAggregateSupplyTask.dcHB2L?'l':'x'
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinLevel(1, 
      myPLC.cmAggregateSupplyTask.dcHB1H?'f':
      myPLC.cmAggregateSupplyTask.dcHB1L?'l':'x'
    );
    
    
    //-- temprature
      
    /*[TODO]::move to some where  
    (
      MainOperationModel.fnToRealValue(
        myPLC.cmVBurnerDryerTask.dcTH1,
        yourMOD.cmAggregateChuteTempratureADJUST
      )
    );
    */
    hisUI.cmVSupplyGroup.cmMU.ccSetSandTemrature(
      MainOperationModel.fnToRealValue(
        myPLC.cmAggregateSupplyTask.dcTH4,
        yourMOD.cmSandBinTempratureADJUST
      )
    );
    
  }//+++
  
  private static void wireMixer(){
    
    //-- val
    hisUI.cmMixerModelGroup.cmMixer.ccSetDryTime
      (myPLC.cmAutoWeighTask.mnDryTimeRemain);
    hisUI.cmMixerModelGroup.cmMixer.ccSetWetTime
      (myPLC.cmAutoWeighTask.mnWetTimeRemain);
    
    //-- pl
    hisUI.cmMixerModelGroup.cmMixer.ccSetMotorON
      (myPLC.cmMainTask.dcMixerAN);
    hisUI.cmMixerModelGroup.cmMixer.ccSetIsGateClosed
      (myPLC.cmAutoWeighTask.dcMCL);
    hisUI.cmMixerModelGroup.cmMixer.ccSetIsGateOpened
      (myPLC.cmAutoWeighTask.dcMOL);
    hisUI.cmMixerModelGroup.cmMixer.ccSetIsGateOpening
      (myPLC.cmAutoWeighTask.dcMXD);
    hisUI.cmMixerModelGroup.cmMixer.ccSetHasMixture
      (myPLC.cmAutoWeighTask.mnMixerHasMixturePL);
    hisUI.cmMixerModelGroup.cmMixer.ccSetTemprature
      (yourMOD.vmMixtureTemprature);
    
  }//+++
  
  //===
  
  public static final void ccClearCommand(){
    ccSetCommand(C_K_NONE, "<>");
  }//+++
  
  public static final void ccSetCommand(int pxID){
    actionID=pxID;
  }//+++
  
  public static final void ccSetCommand(int pxID, String pxParam){
    actionID=pxID;
    actionPARAM=pxParam;
  }//+++
  
  public static final void ccSetSettingInfo(int pxID, int pxValue){
    actionID=C_K_MODIFY_SETTING;
    settingID=pxID;
    settingValue=pxValue;
  }//+++
  
}//***eof
