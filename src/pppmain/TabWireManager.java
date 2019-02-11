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
import kosui.ppplocalui.VcTagger;
import static pppmain.MainSketch.herFrame;
import static pppmain.MainSketch.hisUI;
import static pppmain.MainSketch.myPLC;
import static pppmain.MainSketch.yourMOD;
import ppptable.McBaseRangedFloatSetting;
import ppptable.McErrorMessageFolder;
import ppptable.McRecipeTable;
import ppptable.McSettingFolder;
import ppptable.McCurrentScaleSetting;
import ppptable.McCurrentSlotModel;
import ppptable.McKeyHolder;

public final class TabWireManager {
  
  private static MainSketch mainSketch;
  
  private TabWireManager(){}//++!
  
  //===
  
  //-- for swing action
  public static final String C_M_INVALID="";
  public static final int 
    C_K_REFRESH_ERROR_LIST = 0xCA0013,
    C_K_MODIFY_SETTING     = 0xCA0010,
    C_K_QUIT               = 0xCA0001,
    C_K_NONE               = 0xCA0000
  ;//...
  private static volatile int
    actionID,
    //--
    settingID,
    settingValue
  ;
  private static volatile String actionPARAM=C_M_INVALID;
  
  //-- 
  private static int 
    cmFRWeighStageHolder=0,
    cmAGWeighStageHolder=0,
    cmASWeighStageHolder=0
  ;//...
  
  public static final void ccInit(){
    mainSketch=MainSketch.ccGetReference();
    actionID=0;
    actionPARAM=C_M_INVALID;
    settingID=0;
    settingValue=0;
  }//++!
  
  //===
  
  public static final void ccSetup(){
    
    //--
    //[TEST]:: laod from file ** setting
    {
      int[] lpDesSpan={
        76,290,230,31,
        90,22,77,92,
        170,15,16,8,
        11,37,45,11
      };//...
      int[] lpDesAlert={
        60,270,220,28,
        80,16,55,60,
        150,11,11,6,
        8,27,37,8
      };//...
      /*
      "V-Comp","Mixer","E-Fan","B-Comp",
      "B-Fan","F-Pump","Screen","H-EV",
      "Dryer","I-Belcon","H-Belcon","C-Screw",
      "B-Screw","AS-Supply","AS-Spray","F-EV"
      */
      McCurrentScaleSetting lpSetting = McCurrentScaleSetting.ccGetReference();
      for(int i=0,s=McCurrentSlotModel.C_CAPA;i<s;i++){
        lpSetting.ccSetIntegerValue
          (McKeyHolder.ccGetCTSlotSpanKey(i),lpDesSpan[i]);
        lpSetting.ccSetIntegerValue
          (McKeyHolder.ccGetCTSlotAlartKey(i),lpDesAlert[i]);
      }//..~ 
    }//[TODO]::..a load from file private method
    
    //-- ???
    yourMOD.ccApplySettingContent();
    
    //[TEST]:: laod from file ** recipe
    McRecipeTable.ccGetReference().dummyLoadFromFile();
    
  }//+++
  
  public static final void ccUpdate(){
    
    //-- single
    ssKeep();
    
    //-- plc
    ssRecieveLogic();
    ssSuperiorLogic();
    ssSendLogic();
    
    //-- control
    ssAutoWeighGroup();
    ssVMoterGroup();
    ssVFeederGroup();
    ssVBurnerGroupModel();
    ssVBurnerGourpControl();
    ssFillerSupplyGroup();
    //[REMAINNING]::wireASSupplyChain();
    ssAGSupplyGroup();
    ssMixerModelGroup();
    
    //-- system
    hisUI.cmSystemButton.ccSetIsActivated(
      MainSketch.fnFullSecondFLK()
    );
    hisUI.cmSystemSlotBar.ccSetMessate(
      McErrorMessageFolder.ccGetReference().ccGetMessage
        (myPLC.cmErrorMessageTask.mnMessage)
    );
    
    //-- test tag
    VcTagger.ccTag("*--*", 0);
    VcTagger.ccTag("*--*", 0);
    
  }//+++
  
  //=== swing
  
  private static void ssKeep(){
    switch(actionID){
      
      case C_K_REFRESH_ERROR_LIST:ssRefreshErrorList();break;
      
      case C_K_MODIFY_SETTING:ssModifySetting();break;
      
      case C_K_QUIT:mainSketch.fsPover();break;
       
      default:break;
    }//..?
    ccClearCommand();
  }//+++
  
  private static void ssRefreshErrorList(){
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run(){
        herFrame.cmErrorPane.ccApplyListModel
          (McErrorMessageFolder.ccGetReference().ccGetActivatedArray());
      }//+++
    });
  }//+++
  
  private static void ssModifySetting(){
    
    int lpTableID=settingID%100;
    int lpListID=(settingID-lpTableID)/100;
    
    McBaseRangedFloatSetting lpList=
      McSettingFolder.ccGetReference().ccGet(lpListID);
    lpList.ccSetFloatValue(lpTableID, ((float)settingValue)/10);
    
    yourMOD.ccApplySettingContent();
    SwingUtilities.invokeLater(new Runnable(){@Override public void run(){
      herFrame.cmSettingPane.ccUpdateContent();
    }});
    
  }//+++
  
  public static final void ccClearCommand(){
    ccSetCommand(C_K_NONE, C_M_INVALID);
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
  
  //=== logic
  
  private static void ssRecieveLogic(){
    
    //-- pl ** dust silo
    yourMOD.vmDustBinFullPL
      =myPLC.cmDustExtractTask.dcDustSiloFullLV;
    
    //-- pl ** mixer gate
    yourMOD.cmMXD=myPLC.cmAutoWeighTask.dcMXD;
    yourMOD.cmMOL=myPLC.cmAutoWeighTask.dcMOL;
    yourMOD.cmMCL=myPLC.cmAutoWeighTask.dcMCL;
    
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
    
    //-- cell
    yourMOD.cmAGCell.ccSetInputValue(myPLC.cmAutoWeighTask.dcAGCellAD);
    yourMOD.cmFRCell.ccSetInputValue(myPLC.cmAutoWeighTask.dcFRCellAD);
    yourMOD.cmASCell.ccSetInputValue(myPLC.cmAutoWeighTask.dcASCellAD);
    
    //-- degree
    yourMOD.cmVBunerDegree.ccSetInputValue(myPLC.cmVBurnerDryerTask.dcVBO);
    yourMOD.cmVExfanDegree.ccSetInputValue(myPLC.cmVBurnerDryerTask.dcVDO);
    
    //-- temperature
    yourMOD.cmChuteTemp.ccSetInputValue(myPLC.cmVBurnerDryerTask.dcTH1);
    yourMOD.cmEntanceTemp.ccSetInputValue(myPLC.cmVBurnerDryerTask.dcTH2);
    yourMOD.cmPipeTemp.ccSetInputValue(myPLC.cmMainTask.dcTH3);
    yourMOD.cmSandTemp.ccSetInputValue(myPLC.cmAggregateSupplyTask.dcTH4);
    yourMOD.cmMixtureTemp.ccSetInputValue(myPLC.cmAutoWeighTask.dcTH6);
    
    //-- misc
    yourMOD.cmVDryerPressure.ccSetInputValue
      (myPLC.cmVBurnerDryerTask.dcVSE);
    yourMOD.cmVConveyorScale.ccSetInputValue
      (myPLC.cmAggregateSupplyTask.dcVFCS);
    
    //-- current
    
    //-- ** current ** 0-3
    yourMOD.vmCurrentSlots.ccSetADValue(0, myPLC.cmMainTask.dcCT13);
    yourMOD.vmCurrentSlots.ccSetADValue(1, myPLC.cmMainTask.dcCT6);
    yourMOD.vmCurrentSlots.ccSetADValue(2, myPLC.cmVBurnerDryerTask.dcCT10);
    yourMOD.vmCurrentSlots.ccSetADValue(3, myPLC.cmVBurnerDryerTask.dcCT71);
    //-- ** current ** 4-7
    yourMOD.vmCurrentSlots.ccSetADValue(4, myPLC.cmVBurnerDryerTask.dcCT28);
    yourMOD.vmCurrentSlots.ccSetADValue(5, myPLC.cmVBurnerDryerTask.dcCT29);
    yourMOD.vmCurrentSlots.ccSetADValue(6, myPLC.cmAggregateSupplyTask.dcCT1);
    yourMOD.vmCurrentSlots.ccSetADValue(7, myPLC.cmAggregateSupplyTask.dcCT2);
    //-- ** current ** 8-11
    yourMOD.vmCurrentSlots.ccSetADValue( 8, myPLC.cmAggregateSupplyTask.dcCT3);
    yourMOD.vmCurrentSlots.ccSetADValue( 9, myPLC.cmAggregateSupplyTask.dcCT4);
    yourMOD.vmCurrentSlots.ccSetADValue(10, myPLC.cmAggregateSupplyTask.dcCT5);
    yourMOD.vmCurrentSlots.ccSetADValue(11, myPLC.cmDustExtractTask.dcCT22);
    //-- ** current ** 12-15
    yourMOD.vmCurrentSlots.ccSetADValue(12, myPLC.cmDustExtractTask.dcCT33);
    yourMOD.vmCurrentSlots.ccSetADValue(13, myPLC.cmMainTask.dcCT12);
    yourMOD.vmCurrentSlots.ccSetADValue(14, myPLC.cmAutoWeighTask.dcCT11);
    yourMOD.vmCurrentSlots.ccSetADValue(15, myPLC.cmFillerSupplyTask.dcCT8);
    
    //-- error
    McErrorMessageFolder.ccGetReference().ccTakeErrorBits
      (myPLC.cmErrorMessageTask.mnDesError);
    
  }//+++
  
  private static void ssSuperiorLogic(){
    
    //-- auto weigh
    //-- auto weigh ** every batch
    if(myPLC.cmAutoWeighTask.mnBatchCountDownPLS){
      yourMOD.fsLogAutoWeighResult();
      yourMOD.fsBatchCountDown();
      yourMOD.fsApplyCurrentAutoWeighRecipe();
    }//+++
  
    //-- auto weigh ** sampling
    if(myPLC.cmAutoWeighTask.mnDryStartPLS){
      yourMOD.fsPopAutoWeighResult();
      cmAGWeighStageHolder=0;
      cmFRWeighStageHolder=0;
      cmASWeighStageHolder=0;
    }//..?
    
    //-- auto weigh ** sampling ** declear flag
    int lpAGCellKG=MainOperationModel
      .snGetRevisedValue(yourMOD.cmAGCell);
    int lpFRCellKG=MainOperationModel
      .snGetRevisedValue(yourMOD.cmFRCell);
    int lpASCellKG=MainOperationModel
      .snGetRevisedValue(yourMOD.cmASCell);
    char
      lpAGGaugeMode=lpAGCellKG>yourMOD.vmAGEmptyKG?'w':'e',
      lpFRGaugeMode=lpFRCellKG>yourMOD.vmFREmptyKG?'w':'e',
      lpASGaugeMode=lpASCellKG>yourMOD.vmASEmptyKG?'w':'e'
    ;
    
    //-- auto weigh ** sampling ** ag
    if(yourMOD.cmAG6){cmAGWeighStageHolder=6;}
    if(yourMOD.cmAG5){cmAGWeighStageHolder=5;}
    if(yourMOD.cmAG4){cmAGWeighStageHolder=4;}
    if(yourMOD.cmAG3){cmAGWeighStageHolder=3;}
    if(yourMOD.cmAG2){cmAGWeighStageHolder=2;}
    if(yourMOD.cmAG1){cmAGWeighStageHolder=1;}
    for(int i=6;i>0;i--){if(cmAGWeighStageHolder==i){
      yourMOD.vmResultKG.ccSetAG(i, lpAGCellKG);
      lpAGGaugeMode=MainOperationModel.fnGetAGGaugeMode(lpAGCellKG, i);
    }}//..~
    if(cmAGWeighStageHolder==0){yourMOD.vmResultKG.ccClearAG();}
    
    //-- auto weigh ** sampling ** fr
    if(yourMOD.cmFR2){cmFRWeighStageHolder=2;}
    if(yourMOD.cmFR1){cmFRWeighStageHolder=1;}
    if(cmFRWeighStageHolder==2){
      yourMOD.vmResultKG.ccSetFR(2, lpFRCellKG);
      lpFRGaugeMode=MainOperationModel.fnGetFRGaugeMode(lpFRCellKG, 2);
    }//..?
    if(cmFRWeighStageHolder==1){
      yourMOD.vmResultKG.ccSetFR(1, lpFRCellKG);
      lpFRGaugeMode=MainOperationModel.fnGetFRGaugeMode(lpFRCellKG, 1);
    }//..?
    if(cmFRWeighStageHolder==0){yourMOD.vmResultKG.ccClearFR();}
    
    //-- auto weigh ** sampling ** as
    if(yourMOD.cmASD){cmASWeighStageHolder=0;}
    if(yourMOD.cmAS1){cmASWeighStageHolder=1;}
    if(cmASWeighStageHolder==1){
      yourMOD.vmResultKG.ccSetAS(1, lpASCellKG);
      lpASGaugeMode=MainOperationModel.fnGetASGaugeMode(lpASCellKG, 1);
    }//..?
    if(cmASWeighStageHolder==0){yourMOD.vmResultKG.ccClearAS();}
    
    //-- auto weigh ** indicating ** emptyness
    hisUI.cmWeighControlGroup.cmAGWeigher.ccSetGaugeStatus(lpAGGaugeMode);
    hisUI.cmWeighControlGroup.cmFRWeigher.ccSetGaugeStatus(lpFRGaugeMode);
    hisUI.cmWeighControlGroup.cmASWeigher.ccSetGaugeStatus(lpASGaugeMode);
  
  }//+++
  
  private static void ssSendLogic(){
    
    //-- assistance 
    //-- assistance ** ag
    myPLC.cmDustExtractTask.mnBagPulseRfSW
      =yourMOD.vmBagPulseRfSW;
    myPLC.cmDustExtractTask.mnBagPulseDisableSW
      =yourMOD.vmBagPulseDisableSW;
    myPLC.cmDustExtractTask.mnBagPulseAlwaysSW
      =yourMOD.vmBagPulseAlwaysSW;
    myPLC.cmAggregateSupplyTask.mnVF1VibAlwaysSW
      =yourMOD.vmVFeeder1VibratorAlwaysSW;
    myPLC.cmAggregateSupplyTask.mnVF1VibDisableSW
      =yourMOD.vmVFeeder1VibratorDisableSW;
    myPLC.cmAggregateSupplyTask.mnVF2VibAlwaysSW
      =yourMOD.vmVFeeder2VibratorAlwaysSW;
    myPLC.cmAggregateSupplyTask.mnVF2VibDisableSW
      =yourMOD.vmVFeeder2VibratorDisableSW;
    myPLC.cmVBurnerDryerTask.mnVFuelExchangeDisableSW
      =yourMOD.vmFuelExchangeDisableSW;
    //-- assistance ** fr
    myPLC.cmFillerSupplyTask.mnFRSiloAirDisableSW
      =yourMOD.vmFillerSiloAirDisableSW;
    myPLC.cmFillerSupplyTask.mnFRSiloAirAlwaysSW
      =yourMOD.vmFillerSiloAirAlwaysSW;
    myPLC.cmDustExtractTask.mnDustSiloAirDisableSW
      =yourMOD.vmDustSiloAirDisableSW;
    myPLC.cmDustExtractTask.mnDustSiloAirAlwaysSW
      =yourMOD.vmDustSiloAirAlwaysSW;
    myPLC.cmDustExtractTask.mnDustSiloDischargeSW
      =yourMOD.vmDustSiloDischargeSW;
    //-- assistance ** as
    
    //-- setting 
    
    //-- setting ** degree
    myPLC.cmVBurnerDryerTask.mnVDOLimitLow=yourMOD.cmVExfanDegree
        .ccToUnscaledInputValue(yourMOD.vmVExfanDegreeLimitLow);
    myPLC.cmVBurnerDryerTask.mnVDOLimitHigh=yourMOD.cmVExfanDegree
        .ccToUnscaledInputValue(yourMOD.vmVExfanDegreeLimitHigh);
      
    //-- setting ** temperature
    myPLC.cmVBurnerDryerTask.mnVBTemratureTargetAD=
      yourMOD.cmChuteTemp.ccToUnrevisedInputValue(yourMOD.vmVBurnerTargetTemp);
      
    //-- setting  ** misc
    myPLC.cmVBurnerDryerTask.mnVDPressureTargetAD=yourMOD.cmVDryerPressure
      .ccToUnscaledInputValue(yourMOD.vmVDryerTargetPressure);
      
    myPLC.cmAutoWeighTask.mnDryTimeSetting=
      yourMOD.vmDryTimeSetting;
    myPLC.cmAutoWeighTask.mnWetTimeSetting=
      yourMOD.vmWetTimeSetting;
    
    //-- setting ** weighing
    myPLC.cmAutoWeighTask.mnAG6OverAD=yourMOD.cmAGCell
      .ccToUnrevisedInputValue(yourMOD.vmDropPointKG.ccGetAG(6));
    myPLC.cmAutoWeighTask.mnAG5OverAD=yourMOD.cmAGCell
      .ccToUnrevisedInputValue(yourMOD.vmDropPointKG.ccGetAG(5));
    myPLC.cmAutoWeighTask.mnAG4OverAD=yourMOD.cmAGCell
      .ccToUnrevisedInputValue(yourMOD.vmDropPointKG.ccGetAG(4));
    myPLC.cmAutoWeighTask.mnAG3OverAD=yourMOD.cmAGCell
      .ccToUnrevisedInputValue(yourMOD.vmDropPointKG.ccGetAG(3));
    myPLC.cmAutoWeighTask.mnAG2OverAD=yourMOD.cmAGCell
      .ccToUnrevisedInputValue(yourMOD.vmDropPointKG.ccGetAG(2));
    myPLC.cmAutoWeighTask.mnAG1OverAD=yourMOD.cmAGCell
      .ccToUnrevisedInputValue(yourMOD.vmDropPointKG.ccGetAG(1));
    //--
    myPLC.cmAutoWeighTask.mnFR2OverAD=yourMOD.cmFRCell
      .ccToUnrevisedInputValue(yourMOD.vmDropPointKG.ccGetFR(2));
    myPLC.cmAutoWeighTask.mnFR1OverAD=yourMOD.cmFRCell
      .ccToUnrevisedInputValue(yourMOD.vmDropPointKG.ccGetFR(1));
    //--
    myPLC.cmAutoWeighTask.mnAS1OverAD=yourMOD.cmFRCell
      .ccToUnrevisedInputValue(yourMOD.vmDropPointKG.ccGetAS(1));
      
    //-- control
    //-- control ** auto weigh
    myPLC.cmAutoWeighTask.mnBatchCounter
      =yourMOD.fsGetCurrentRemianingBatch();
    
    //-- control ** ag
    int lpEntranceTemp=MainOperationModel
      .snGetRevisedValue(yourMOD.cmEntanceTemp);
    boolean lpAboveLowLimit=lpEntranceTemp>yourMOD.vmEntranceTempLimitLow;
    hisUI.cmVBurnerControlGroup.cmEntraceTempBox
      .ccSetIsActivated(lpAboveLowLimit);
    myPLC.cmVBurnerDryerTask.mnCoolingDamperOpenSIG=
      yourMOD.vmCoolingDamperDisableSW?false:
      yourMOD.vmCoolingDamperAlwaysSW?true:lpAboveLowLimit;
    myPLC.cmVBurnerDryerTask.mnFireStopSIG=
      (lpEntranceTemp>yourMOD.vmEntranceTempLimitHigh);
    
  }//+++
  
  //=== local
  
  private static void ssAutoWeighGroup(){
    
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
      (MainOperationModel.snGetRevisedValue(yourMOD.cmAGCell));
    hisUI.cmWeighControlGroup.cmFRWeigher.ccSetCurrentKG
      (MainOperationModel.snGetRevisedValue(yourMOD.cmFRCell));
    hisUI.cmWeighControlGroup.cmASWeigher.ccSetCurrentKG
      (MainOperationModel.snGetRevisedValue(yourMOD.cmASCell));
    
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
    
    //-- extraction
    myPLC.cmAggregateSupplyTask.mnOverFlowGateSW=
      hisUI.cmAGSupplyModelGroup.cmOFD.ccIsMousePressed();
    myPLC.cmAggregateSupplyTask.mnOverSizeGateSW=
      hisUI.cmAGSupplyModelGroup.cmOSD.ccIsMousePressed();
    
  }//+++
  
  private static void ssVMoterGroup(){
    
    //-- first row
    myPLC.cmMainTask.mnVCompressorSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+0);
    hisUI.cmVMotorControlGroup.cmMotorSW[0]
      .ccSetIsActivated(myPLC.cmMainTask.mnVCompressorPL);
    //--
    myPLC.cmMainTask.mnMixerMoterSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+6);
    hisUI.cmVMotorControlGroup.cmMotorSW[6]
      .ccSetIsActivated(myPLC.cmMainTask.mnMixerMoterPL);
    //--
    myPLC.cmVBurnerDryerTask.mnVExfanMotorSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+12);
    hisUI.cmVMotorControlGroup.cmMotorSW[12]
      .ccSetIsActivated(myPLC.cmVBurnerDryerTask.mnVExfanMotorPL);
    //--
    myPLC.cmVBurnerDryerTask.mnAPBlowerSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+3);
    hisUI.cmVMotorControlGroup.cmMotorSW[3]
      .ccSetIsActivated(myPLC.cmVBurnerDryerTask.mnAPBlowerPL);
    //--
    myPLC.cmAggregateSupplyTask.mnAGSupplyStartSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+9);
    hisUI.cmVMotorControlGroup.cmMotorSW[9]
      .ccSetIsActivated(myPLC.cmAggregateSupplyTask.mnAGSUpplyStartPL);
    
    //-- scond row
    myPLC.cmMainTask.mnASSupplyPumpSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+10);
    hisUI.cmVMotorControlGroup.cmMotorSW[10]
      .ccSetIsActivated(myPLC.cmMainTask.mnASSupplyPumpPL);
    //--
    myPLC.cmFillerSupplyTask.mnFRSupplyStartSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+7);
    hisUI.cmVMotorControlGroup.cmMotorSW[7]
      .ccSetIsActivated(myPLC.cmFillerSupplyTask.mnFRSupplyStartPL);
    //--
    myPLC.cmAggregateSupplyTask.mnVFeederStartSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+13);
    hisUI.cmVMotorControlGroup.cmMotorSW[13]
      .ccSetIsActivated(myPLC.cmAggregateSupplyTask.mnVFeederStartPL);
    
    //-- third row
    myPLC.cmDustExtractTask.mnDustExtractStartSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+5);
    hisUI.cmVMotorControlGroup.cmMotorSW[5]
      .ccSetIsActivated(myPLC.cmDustExtractTask.mnDustExtractStartPL);
    //--
    myPLC.cmVBurnerDryerTask.mnVBCompressorMoterSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+11);
    hisUI.cmVMotorControlGroup.cmMotorSW[11]
      .ccSetIsActivated(myPLC.cmVBurnerDryerTask.mnVBCompressorMoterPL);
    
  }//+++
  
  private static void ssVFeederGroup(){
    
    //-- vhbc
    hisUI.cmVFeederModelGroup.cmVHBC.ccSetIsActivated
      (myPLC.cmAggregateSupplyTask.dcVHorizontalBelconAN);
    
    //-- vf
    //-- ** ** motor
    hisUI.cmVFeederModelGroup.cmVF01.ccSetMotorON
      (myPLC.cmAggregateSupplyTask.dcVFAN01);
    hisUI.cmVFeederModelGroup.cmVF02.ccSetMotorON
      (myPLC.cmAggregateSupplyTask.dcVFAN02);
    hisUI.cmVFeederModelGroup.cmVF03.ccSetMotorON
      (myPLC.cmAggregateSupplyTask.dcVFAN03);
    hisUI.cmVFeederModelGroup.cmVF04.ccSetMotorON
      (myPLC.cmAggregateSupplyTask.dcVFAN04);
    hisUI.cmVFeederModelGroup.cmVF05.ccSetMotorON
      (myPLC.cmAggregateSupplyTask.dcVFAN05);
    hisUI.cmVFeederModelGroup.cmVF06.ccSetMotorON
      (myPLC.cmAggregateSupplyTask.dcVFAN06);
    
    //-- ** ** speed bar
    hisUI.cmVFeederModelGroup.cmVF01.ccSetRPM(yourMOD.cmVFRPM[1]);
    hisUI.cmVFeederModelGroup.cmVF02.ccSetRPM(yourMOD.cmVFRPM[2]);
    hisUI.cmVFeederModelGroup.cmVF03.ccSetRPM(yourMOD.cmVFRPM[3]);
    hisUI.cmVFeederModelGroup.cmVF04.ccSetRPM(yourMOD.cmVFRPM[4]);
    hisUI.cmVFeederModelGroup.cmVF05.ccSetRPM(yourMOD.cmVFRPM[5]);
    hisUI.cmVFeederModelGroup.cmVF06.ccSetRPM(yourMOD.cmVFRPM[6]);
    
    //-- ** ** stuck sensor
    hisUI.cmVFeederModelGroup.cmVF01
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG01);
    hisUI.cmVFeederModelGroup.cmVF02
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG02);
    hisUI.cmVFeederModelGroup.cmVF03
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG03);
    hisUI.cmVFeederModelGroup.cmVF04
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG04);
    hisUI.cmVFeederModelGroup.cmVF05
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG05);
    hisUI.cmVFeederModelGroup.cmVF06
      .ccSetIsSending(myPLC.cmAggregateSupplyTask.dcVFSG06);
    
    //-- ** ** speed ad
    myPLC.cmAggregateSupplyTask.dcVFSP01=yourMOD.ccGetVFeederRpmADValue(1);
    myPLC.cmAggregateSupplyTask.dcVFSP02=yourMOD.ccGetVFeederRpmADValue(2);
    myPLC.cmAggregateSupplyTask.dcVFSP03=yourMOD.ccGetVFeederRpmADValue(3);
    myPLC.cmAggregateSupplyTask.dcVFSP04=yourMOD.ccGetVFeederRpmADValue(4);
    myPLC.cmAggregateSupplyTask.dcVFSP05=yourMOD.ccGetVFeederRpmADValue(5);
    myPLC.cmAggregateSupplyTask.dcVFSP06=yourMOD.ccGetVFeederRpmADValue(6);
    
  }//+++
  
  private static void ssVBurnerGroupModel(){
    
    //-- bag
    hisUI.cmVBurnerControlGroup.cmBagPulsePL.ccSetIsActivated
      (myPLC.cmDustExtractTask.mnBagPulsePL);
    hisUI.cmVBurnerControlGroup.cmBagUpperLV.ccSetIsActivated
      (myPLC.cmDustExtractTask.dcF2H);
    hisUI.cmVBurnerControlGroup.cmBagLowerLV.ccSetIsActivated
      (myPLC.cmDustExtractTask.dcF2L);
    hisUI.cmVBurnerControlGroup.cmEntraceTempBox.ccSetValue
      (MainOperationModel.snGetRevisedValue(yourMOD.cmEntanceTemp));
    
    //-- exf
    hisUI.cmVBurnerControlGroup.cmVE.ccSetMotorON
      (myPLC.cmVBurnerDryerTask.dcVExfanAN);
    hisUI.cmVBurnerControlGroup.cmVExfanDegreeBox.ccSetValue
      (MainOperationModel.snGetScaledIntegerValue(yourMOD.cmVExfanDegree));
    
    //-- v burner
    hisUI.cmVBurnerControlGroup.cmVB.ccSetMotorON
      (myPLC.cmVBurnerDryerTask.dcVBurnerFanAN);
    hisUI.cmVBurnerControlGroup.cmVB.ccSetIsIgniting
      (myPLC.cmVBurnerDryerTask.dcIG);
    hisUI.cmVBurnerControlGroup.cmVB.ccSetIsPiloting
      (myPLC.cmVBurnerDryerTask.dcPV);
    hisUI.cmVBurnerControlGroup.cmVB.ccSetHasFire
      (myPLC.cmVBurnerDryerTask.dcMMV);
    hisUI.cmVBurnerControlGroup.cmVBurnerDegreeBox.ccSetValue
      (MainOperationModel.snGetScaledIntegerValue(yourMOD.cmVBunerDegree));
    hisUI.cmVBurnerControlGroup.cmTargetTempBox.ccSetValue
      (yourMOD.vmVBurnerTargetTemp);

    //-- dryer
    int lpVCSTPH=yourMOD.cmVConveyorScale.ccGetScaledIntegerValue();
    hisUI.cmVBurnerControlGroup.cmVD.ccSetTPH(lpVCSTPH);
    hisUI.cmVBurnerControlGroup.cmTPHBox.ccSetValue(lpVCSTPH);
    hisUI.cmVBurnerControlGroup.cmTPHBox.ccSetIsActivated
      (myPLC.cmAggregateSupplyTask.dcCAS);
    hisUI.cmVBurnerControlGroup.cmVD.ccSetIsOnFire
      (myPLC.cmVBurnerDryerTask.dcMMV);
    hisUI.cmVBurnerControlGroup.cmKPABox.ccSetValue
      (MainOperationModel.snGetScaledIntegerValue(yourMOD.cmVDryerPressure));
    hisUI.cmVBurnerControlGroup.cmVD.ccSetMotorON
      (myPLC.cmAggregateSupplyTask.dcVDryerAN);
    hisUI.cmVBurnerControlGroup.cmVIBC.ccSetIsActivated
      (myPLC.cmAggregateSupplyTask.dcVInclineBelconAN);
    
    //-- combust
    hisUI.cmVBurnerControlGroup.cmOilPL.ccSetIsActivated
      (!myPLC.cmVBurnerDryerTask.dcRSG);
    hisUI.cmVBurnerControlGroup.cmGasPL.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.dcRSG);
    hisUI.cmVBurnerControlGroup.cmFuelPL.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.dcFuelMV);
    hisUI.cmVBurnerControlGroup.cmHeavyPL.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.dcHeavyMV);
    
  }//+++
  
  private static void ssVBurnerGourpControl(){
    
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
    
    hisUI.cmVBurnerControlGroup.cmChuteTempBox.ccSetValue
      (MainOperationModel.snGetRevisedValue(yourMOD.cmChuteTemp));
    
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
    hisUI.cmVBurnerControlGroup.cmVBReadyPL.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.mnVBReadyPL);
    
  }//+++
  
  private static void ssFillerSupplyGroup(){
    hisUI.cmFillerSupplyGroup.cmFillerBin.ccSetLevelor
      (myPLC.cmFillerSupplyTask.dcFillerBinLV);
    hisUI.cmFillerSupplyGroup.cmDustBin.ccSetLevelor
      (myPLC.cmDustExtractTask.dcF2L,
       myPLC.cmDustExtractTask.dcF2H);
    hisUI.cmFillerSupplyGroup.cmFillerSilo.ccSetLevelor
      (myPLC.cmFillerSupplyTask.dcFillerSiloLLV, 
       myPLC.cmFillerSupplyTask.dcFillerSiloMLV,
       myPLC.cmFillerSupplyTask.dcFillerSiloHLV);
  }//+++
  
  //[TODO]::fill this private static void wireASSupplyChain(){}//+++
  
  private static void ssAGSupplyGroup(){
    
    //-- hb ** lv
    hisUI.cmAGSupplyModelGroup.cmMU.ccSetHotBinLevel(6, 
      myPLC.cmAggregateSupplyTask.dcHB6H?'f':
      myPLC.cmAggregateSupplyTask.dcHB6L?'l':'x'
    );
    hisUI.cmAGSupplyModelGroup.cmMU.ccSetHotBinLevel(5, 
      myPLC.cmAggregateSupplyTask.dcHB5H?'f':
      myPLC.cmAggregateSupplyTask.dcHB5L?'l':'x'
    );
    hisUI.cmAGSupplyModelGroup.cmMU.ccSetHotBinLevel(4, 
      myPLC.cmAggregateSupplyTask.dcHB4H?'f':
      myPLC.cmAggregateSupplyTask.dcHB4L?'l':'x'
    );
    hisUI.cmAGSupplyModelGroup.cmMU.ccSetHotBinLevel(3, 
      myPLC.cmAggregateSupplyTask.dcHB3H?'f':
      myPLC.cmAggregateSupplyTask.dcHB3L?'l':'x'
    );
    hisUI.cmAGSupplyModelGroup.cmMU.ccSetHotBinLevel(2, 
      myPLC.cmAggregateSupplyTask.dcHB2H?'f':
      myPLC.cmAggregateSupplyTask.dcHB2L?'l':'x'
    );
    hisUI.cmAGSupplyModelGroup.cmMU.ccSetHotBinLevel(1, 
      myPLC.cmAggregateSupplyTask.dcHB1H?'f':
      myPLC.cmAggregateSupplyTask.dcHB1L?'l':'x'
    );
    
    //-- extraction
    hisUI.cmAGSupplyModelGroup.cmOF1.ccSetIsActivated
      (myPLC.cmAggregateSupplyTask.dcOF1);
    hisUI.cmAGSupplyModelGroup.cmOS1.ccSetIsActivated
      (myPLC.cmAggregateSupplyTask.dcOS1);
    hisUI.cmAGSupplyModelGroup.cmOFD.ccSetIsActivated
      (myPLC.cmAggregateSupplyTask.dcOFD);
    hisUI.cmAGSupplyModelGroup.cmOSD.ccSetIsActivated
      (myPLC.cmAggregateSupplyTask.dcOSD);
    
    //-- temprature
    hisUI.cmAGSupplyModelGroup.cmSandTempBox.ccSetValue
      (MainOperationModel.snGetRevisedValue(yourMOD.cmSandTemp));
    
  }//+++
  
  private static void ssMixerModelGroup(){
    
    //-- box
    hisUI.cmMixerModelGroup.cmDryTimerBox.ccSetValue
      (myPLC.cmAutoWeighTask.mnDryTimeRemain);
    hisUI.cmMixerModelGroup.cmWetTimerBox.ccSetValue
      (myPLC.cmAutoWeighTask.mnWetTimeRemain);
    hisUI.cmMixerModelGroup.cmTempratureBox.ccSetValue
      (MainOperationModel.snGetRevisedValue(yourMOD.cmMixtureTemp));
    
    //-- pl
    hisUI.cmMixerModelGroup.cmMixer.ccSetMotorON
      (myPLC.cmMainTask.dcMixerAN);
    hisUI.cmMixerModelGroup.cmMixer.ccSetHasMixture
      (myPLC.cmAutoWeighTask.mnMixerHasMixturePL);
    hisUI.cmMixerModelGroup.cmMixer.ccSetIsGateClosed
      (yourMOD.cmMCL);
    hisUI.cmMixerModelGroup.cmMixer.ccSetIsGateOpened
      (yourMOD.cmMOL);
    hisUI.cmMixerModelGroup.cmMixer.ccSetIsGateOpening
      (yourMOD.cmMXD);
    
  }//+++
  
}//***eof
