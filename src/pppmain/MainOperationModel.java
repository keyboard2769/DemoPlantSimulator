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

package pppmain;

import javax.swing.SwingUtilities;
import kosui.ppplogic.ZcScaledModel;
import kosui.ppplogic.ZcRevisedScaledModel;
import ppptable.McAutoWeighLogger;
import ppptable.McAutoWeighRecord;
import ppptable.McAutoWeighSetting;
import ppptable.McCategoryIntegerBundle;
import ppptable.McRecipeTable;
import ppptable.McTrendLogger;
import ppptable.McTempScaleSetting;
import ppptable.McTrendRecord;
import ppptable.McCurrentSlotModel;
import ppptable.McCurrentScaleSetting;
import ppptable.McGeneralScaleSetting;
import ppptable.McVBurningSetting;
import ppptable.McKeyHolder;
import static kosui.pppswingui.ScFactory.fnScrollToLast;
import static processing.core.PApplet.constrain;

public final class MainOperationModel {
  
  private static MainOperationModel self;
  private MainOperationModel(){}//++!
  public static MainOperationModel ccGetReference(){
    if(self==null){self=new MainOperationModel();}
    return self;
  }//++!
  
  //===
  
  public static final int 
    C_MAX_BOOK_CAPABILITY=4,
    C_MAX_BATCH_CAPABILITY=9999,
    //--
    C_DEFAULT_TEMP_AD_SPAN=1000,//..actually it should be 4672
    C_DEFAULT_TEMP_AD_OFFS=0,//..actually it should be 1000
    C_DEFAULT_TEMP_DC_SPAN=100,//..actually it should be 0
    C_DEFAULT_TEMP_DC_OFFS=0,//..actually it should be 1680
    //--
    C_DEFAULT_AD_SPAN = 3600,
    C_DEFAULT_AD_OFFS =  400,
    C_DEFAULT_DEG_MAX =  100,
    C_DEFAULT_DEG_MIN =    0,
    //--
    C_DEFAULT_DRYERP_AD_OFFS  = 1500,
    C_DEFAULT_DRYERP_AD_SPAN  = 2500,
    C_DEFAULT_DRYERP_KPA_OFFS =    0,
    C_DEFAULT_DRYERP_KPA_SPAN =  200,
    //--
    C_DEFAULT_FEEDER_RPM_MAX = 1800,
    C_DEFAULT_FEEDER_AD_MAX  = 5000
  ;//...
  
  //===
  
  public boolean 
    
    //-- direct pl 
    //-- direct pl ** weighing
    //-- direct pl ** weighing ** status
    cmIsAutoWeighRunnning,
    //-- direct pl ** weighing ** gate 
    cmFRD,cmFR2,cmFR1,
    cmAGD,cmAG6,cmAG5,cmAG4,cmAG3,cmAG2,cmAG1,
    cmASD,cmAS1,
    //-- direct pl ** weighing ** mixer
    cmMXD,cmMOL,cmMCL
    
  ;//...
  
  public int
    
    //-- foundamental
    cmMixerCapability=4000,
    cmAGCapability=4000,cmFRCapability=500,cmASCapability=500,
    cmVDryerCapability=340,
    cmBagFilterSize=24,
    
    //-- optional
    cmVFeederAdjustment=50
    
  ;//...
  
  public int[] 
    //-- vf speed command
    cmVFRPM={
      -1,
      900,900,900,850,
      750,650,  0,  0,
      0
    },
    //-- booking
    cmBookedRecipe    = {0,0,0,0},
    cmBookedKillogram = {0,0,0,0},
    cmBookedBatch     = {0,0,0,0}
  ;//...
  
  //===
  
  public volatile boolean
    
    //-- switch
    
    //-- switch ** assistance
    
    //-- switch ** assistance ** ag supply
    vmBagPulseRfSW,vmBagPulseDisableSW,vmBagPulseAlwaysSW,
    vmCoolingDamperDisableSW,vmCoolingDamperAlwaysSW,
    vmVFeeder1VibratorDisableSW,vmVFeeder1VibratorAlwaysSW,
    vmVFeeder2VibratorDisableSW,vmVFeeder2VibratorAlwaysSW,
    vmFuelExchangeDisableSW,
    
    //-- switch ** assistance ** filler supply
    vmFillerSiloAirDisableSW,vmFillerSiloAirAlwaysSW,
    vmDustSiloAirDisableSW,vmDustSiloAirAlwaysSW,
    vmDustSiloDischargeSW,
    
    //-- switch ** assistance ** as supply
    vmASSupplyPumpReverseSW,
    
    //-- lamp
    vmDustBinFullPL
    
  ;//...
  
  public volatile int
    
    //-- setting
    //-- setting ** auto weigh
    vmAGEmptyKG,vmFREmptyKG,vmASEmptyKG,
    //-- setting ** temperature
    vmVExfanDegreeLimitLow=20,vmVExfanDegreeLimitHigh=80,
    vmEntranceTempLimitLow=230,vmEntranceTempLimitHigh=260,
    vmVBurnerTargetTemp=160,
    
    //-- setting ** misc
    vmDryTimeSetting,vmWetTimeSetting,
    vmVDryerTargetPressure=50
    
  ;//...
  
  public final McCategoryIntegerBundle
    //-- setting
    vmDropOffsetKG= new McCategoryIntegerBundle(),
    vmCutOffsetKG= new McCategoryIntegerBundle(),
    //-- control
    vmDropPointKG= new McCategoryIntegerBundle(),
    vmCutPointKG= new McCategoryIntegerBundle(),
    //-- visible
    vmTargetKG=new McCategoryIntegerBundle(),
    vmResultKG=new McCategoryIntegerBundle(),
    vmPoppedtKG=new McCategoryIntegerBundle()
  ;//...
  
  public final ZcScaledModel
    //-- degree
    cmVBunerDegree = new ZcScaledModel(
      C_DEFAULT_AD_OFFS, C_DEFAULT_AD_SPAN,
      C_DEFAULT_DEG_MIN, C_DEFAULT_DEG_MAX
    ),
    cmVExfanDegree = new ZcScaledModel(
      C_DEFAULT_AD_OFFS, C_DEFAULT_AD_SPAN,
      C_DEFAULT_DEG_MIN, C_DEFAULT_DEG_MAX
    ),
    //-- misc
    cmVDryerPressure = new ZcScaledModel(
      C_DEFAULT_DRYERP_AD_OFFS, C_DEFAULT_DRYERP_AD_SPAN,
      C_DEFAULT_DRYERP_KPA_OFFS, C_DEFAULT_DRYERP_KPA_SPAN
    ),
    cmVConveyorScale = new ZcScaledModel(
      C_DEFAULT_AD_OFFS,C_DEFAULT_AD_SPAN,
      0,cmVDryerCapability
    )
  ;//...
  
  //-- current
  public final McCurrentSlotModel vmCurrentSlots
    =new McCurrentSlotModel();
  
  //-- temperature
  public final ZcRevisedScaledModel
    //-- cell
    cmAGCell= new ZcRevisedScaledModel(
      C_DEFAULT_AD_OFFS, C_DEFAULT_AD_SPAN,
      0, cmAGCapability
    ),
    cmFRCell= new ZcRevisedScaledModel(
      C_DEFAULT_AD_OFFS, C_DEFAULT_AD_SPAN,
      0, cmFRCapability
    ),
    cmASCell= new ZcRevisedScaledModel(
      C_DEFAULT_AD_OFFS, C_DEFAULT_AD_SPAN,
      0, cmASCapability
    ),
    //-- ** TH1
    cmChuteTemp=new ZcRevisedScaledModel(
      C_DEFAULT_TEMP_AD_OFFS, C_DEFAULT_TEMP_AD_SPAN,
      C_DEFAULT_TEMP_DC_OFFS, C_DEFAULT_TEMP_DC_SPAN
    ),
    //-- ** TH2
    cmEntanceTemp=new ZcRevisedScaledModel(
      C_DEFAULT_TEMP_AD_OFFS, C_DEFAULT_TEMP_AD_SPAN,
      C_DEFAULT_TEMP_DC_OFFS, C_DEFAULT_TEMP_DC_SPAN
    ),
    //-- ** TH3
    cmPipeTemp=new ZcRevisedScaledModel(
      C_DEFAULT_TEMP_AD_OFFS, C_DEFAULT_TEMP_AD_SPAN,
      C_DEFAULT_TEMP_DC_OFFS, C_DEFAULT_TEMP_DC_SPAN
    ),
    //-- ** TH4
    cmSandTemp=new ZcRevisedScaledModel(
      C_DEFAULT_TEMP_AD_OFFS, C_DEFAULT_TEMP_AD_SPAN,
      C_DEFAULT_TEMP_DC_OFFS, C_DEFAULT_TEMP_DC_SPAN
    ),
    //-- ** TH6
    cmMixtureTemp=new ZcRevisedScaledModel(
      C_DEFAULT_TEMP_AD_OFFS, C_DEFAULT_TEMP_AD_SPAN,
      C_DEFAULT_TEMP_DC_OFFS, C_DEFAULT_TEMP_DC_SPAN
    )
  ;//...
  
  //===
  
  public final void ccApplySettingContent(){
    
    ssApplyAutoWeighSetting();
    ssApplyVBurningSetting();
    ssApplyGeneralScaleSetting();
    ssApplyTempScaleSetting();
    ssApplyCurrentScaleSetting();
    
  }//+++
  
  private void ssApplyAutoWeighSetting(){
    McAutoWeighSetting lpSetting=McAutoWeighSetting.ccGetReference();
    vmDryTimeSetting=lpSetting
      .ccGetIntegerValue(McKeyHolder.K_AW_TIME_DRY);
    vmWetTimeSetting=lpSetting
      .ccGetIntegerValue(McKeyHolder.K_AW_TIME_WET);
    cmAGCell.ccSetOffset
      (-1*lpSetting.ccGetIntegerValue(McKeyHolder.K_AW_TARE_AG));
    cmFRCell.ccSetOffset
      (-1*lpSetting.ccGetIntegerValue(McKeyHolder.K_AW_TARE_FR));
    cmASCell.ccSetOffset
      (-1*lpSetting.ccGetIntegerValue(McKeyHolder.K_AW_TARE_AS));
    //--
    vmAGEmptyKG=lpSetting.ccGetIntegerValue(McKeyHolder.K_AW_EMPTY_AG);
    vmFREmptyKG=lpSetting.ccGetIntegerValue(McKeyHolder.K_AW_EMPTY_FR);
    vmASEmptyKG=lpSetting.ccGetIntegerValue(McKeyHolder.K_AW_EMPTY_AS);
    //--
    for(int i=6;i>=1;i--){
      vmDropOffsetKG.ccSetAG(i, lpSetting.ccGetIntegerValue(
        McKeyHolder.ccGetAGDropOffsetKey(i)));
      vmCutOffsetKG.ccSetAG(i, lpSetting.ccGetIntegerValue(
        McKeyHolder.ccGetAGCutOffsetKey(i)));
      if(i<=2){
        vmDropOffsetKG.ccSetFR(i, lpSetting.ccGetIntegerValue(
          McKeyHolder.ccGetFRDropOffsetKey(i)));
        vmCutOffsetKG.ccSetFR(i, lpSetting.ccGetIntegerValue(
          McKeyHolder.ccGetFRCutOffsetKey(i)));
      }//..?
      if(i<=1){
        vmDropOffsetKG.ccSetAS(i, lpSetting.ccGetIntegerValue(
          McKeyHolder.ccGetASDropOffsetKey(i)));
        vmCutOffsetKG.ccSetAS(i, lpSetting.ccGetIntegerValue(
          McKeyHolder.ccGetASCutOffsetKey(i)));
      }//..?
    }//..~
  }//+++
  
  private void ssApplyVBurningSetting(){
    McVBurningSetting lpSetting = McVBurningSetting.ccGetReference();
    vmEntranceTempLimitLow=lpSetting.ccGetIntegerValue
      (McKeyHolder.K_VB_TLMT_ENT_L);
    vmEntranceTempLimitHigh=lpSetting.ccGetIntegerValue
      (McKeyHolder.K_VB_TLMT_ENT_H);
  }//+++
  
  private void ssApplyGeneralScaleSetting(){
    McGeneralScaleSetting lpSetting = McGeneralScaleSetting.ccGetReference();
    
    //-- cell
    //-- cell ** ag
    cmAGCell.ccSetInputOffset
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_AG_AD_OFF));
    cmAGCell.ccSetInputSpan
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_AG_AD_SPN));
    cmAGCell.ccSetOutputOffset
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_AG_KG_OFF));
    cmAGCell.ccSetOutputSpan
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_AG_KG_SPN));
    //-- cell ** fr
    cmFRCell.ccSetInputOffset
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_FR_AD_OFF));
    cmFRCell.ccSetInputSpan
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_FR_AD_SPN));
    cmFRCell.ccSetOutputOffset
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_FR_KG_OFF));
    cmFRCell.ccSetOutputSpan
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_FR_KG_SPN));
    //-- cell ** as
    cmASCell.ccSetInputOffset
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_AS_AD_OFF));
    cmASCell.ccSetInputSpan
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_AS_AD_SPN));
    cmASCell.ccSetOutputOffset
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_AS_KG_OFF));
    cmASCell.ccSetOutputSpan
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_C_AS_KG_SPN));
    
    //-- degree ** vb
    cmVBunerDegree.ccSetInputOffset
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_D_VB_AD_OFF));
    cmVBunerDegree.ccSetInputSpan
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_D_VB_AD_SPN));
    cmVBunerDegree.ccSetOutputOffset
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_D_VB_PT_OFF));
    cmVBunerDegree.ccSetOutputSpan
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_D_VB_PT_SPN));
    //-- degree ** ve
    cmVExfanDegree.ccSetInputOffset
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_D_VE_AD_OFF));
    cmVExfanDegree.ccSetInputSpan
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_D_VE_AD_SPN));
    cmVExfanDegree.ccSetOutputOffset
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_D_VE_PT_OFF));
    cmVExfanDegree.ccSetOutputSpan
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_GS_D_VE_PT_SPN));
    //-- misc
    
  }//+++
  
  private void ssApplyTempScaleSetting(){
    McTempScaleSetting lpSetting = McTempScaleSetting.ccGetReference();
    
    //-- mixer
    cmMixtureTemp.ccSetBias
      (lpSetting.ccGetIntegerValue("--mixer-tbias"));
    cmMixtureTemp.ccSetOffset
      (lpSetting.ccGetIntegerValue("--mixer-toffset"));
    cmMixtureTemp.ccSetInputOffset
      (lpSetting.ccGetIntegerValue(McKeyHolder.K_TS_GT_AD_OFF));
    cmMixtureTemp.ccSetInputSpan
      (lpSetting.ccGetIntegerValue("--aaGtemp-ad-span"));
    cmMixtureTemp.ccSetOutputOffset
      (lpSetting.ccGetIntegerValue("--aaGtemp-dc-offset"));
    cmMixtureTemp.ccSetOutputSpan
      (lpSetting.ccGetIntegerValue("--aaGtemp-dc-span"));
    
  }//+++
  
  private void ssApplyCurrentScaleSetting(){
    
    McCurrentScaleSetting lpSetting = McCurrentScaleSetting.ccGetReference();
    for(int i=0,s=McCurrentSlotModel.C_CAPA;i<s;i++){
      vmCurrentSlots.ccSetCTValue
        (i, lpSetting.ccGetIntegerValue(McKeyHolder.ccGetCTSlotSpanKey(i)));
      vmCurrentSlots.ccSetALValue
        (i, lpSetting.ccGetIntegerValue(McKeyHolder.ccGetCTSlotAlartKey(i)));
    }//..~ 
    
  }//+++
  
  //===
  
  public final void ccAdjustZERO(boolean pxAG, boolean pxFR, boolean pxAS){
    if(!pxAG && !pxFR && !pxAS){return;}
    McAutoWeighSetting lpSetting = McAutoWeighSetting.ccGetReference();
    if(pxAG){
      lpSetting.ccSetIntegerValue
        (McKeyHolder.K_AW_TARE_AG, cmAGCell.ccGetScaledIntegerValue());
    }//..?
    if(pxFR){
      lpSetting.ccSetIntegerValue
        (McKeyHolder.K_AW_TARE_FR, cmFRCell.ccGetScaledIntegerValue());
    }//..?
    if(pxAS){
      lpSetting.ccSetIntegerValue
        (McKeyHolder.K_AW_TARE_AS, cmASCell.ccGetScaledIntegerValue());
    }//..?
    ssApplyAutoWeighSetting();
  }//+++
  
  synchronized 
  public final void ccAdjustAGCell(char pxMode_zs){
    switch(pxMode_zs){
      case 'z':
        McGeneralScaleSetting.ccGetReference().ccSetIntegerValue(
          McKeyHolder.K_GS_C_AG_AD_OFF,
          cmAGCell.ccGetInputValue()
        );
      break;
      case 's':
        McGeneralScaleSetting.ccGetReference().ccSetIntegerValue(
          McKeyHolder.K_GS_C_AG_AD_SPN,
          cmAGCell.ccGetInputValue()
        );
      break;
      default:break;
    }
    ssApplyGeneralScaleSetting();
  }//+++
  
  synchronized 
  public final void ccAdjustFRCell(char pxMode_zs){
    switch(pxMode_zs){
      case 'z':
        McGeneralScaleSetting.ccGetReference().ccSetIntegerValue(
          McKeyHolder.K_GS_C_FR_AD_OFF,
          cmFRCell.ccGetInputValue()
        );
      break;
      case 's':
        McGeneralScaleSetting.ccGetReference().ccSetIntegerValue(
          McKeyHolder.K_GS_C_FR_AD_SPN,
          cmFRCell.ccGetInputValue()
        );
      break;
      default:break;
    }
    ssApplyGeneralScaleSetting();
  }//+++
  
  synchronized 
  public final void ccAdjustASCell(char pxMode_zs){
    switch(pxMode_zs){
      case 'z':
        McGeneralScaleSetting.ccGetReference().ccSetIntegerValue(
          McKeyHolder.K_GS_C_AS_AD_OFF,
          cmASCell.ccGetInputValue()
        );
      break;
      case 's':
        McGeneralScaleSetting.ccGetReference().ccSetIntegerValue(
          McKeyHolder.K_GS_C_AS_AD_SPN,
          cmASCell.ccGetInputValue()
        );
      break;
      default:break;
    }
    ssApplyGeneralScaleSetting();
  }//+++
  
  synchronized 
  public final void ccAdjustVBDegree(char pxMode_zs){
    switch(pxMode_zs){
      case 'z':
        McGeneralScaleSetting.ccGetReference().ccSetIntegerValue(
          McKeyHolder.K_GS_D_VB_AD_OFF,
          cmVBunerDegree.ccGetInputValue()
        );
      break;
      case 's':
        McGeneralScaleSetting.ccGetReference().ccSetIntegerValue(
          McKeyHolder.K_GS_D_VB_AD_SPN,
          cmVBunerDegree.ccGetInputValue()
        );
      break;
      default:break;
    }
    ssApplyGeneralScaleSetting();
  }//+++
  
  synchronized 
  public final void ccAdjustVEDegree(char pxMode_zs){
    switch(pxMode_zs){
      case 'z':
        McGeneralScaleSetting.ccGetReference().ccSetIntegerValue(
          McKeyHolder.K_GS_D_VE_AD_OFF,
          cmVExfanDegree.ccGetInputValue()
        );
      break;
      case 's':
        McGeneralScaleSetting.ccGetReference().ccSetIntegerValue(
          McKeyHolder.K_GS_D_VE_AD_SPN,
          cmVExfanDegree.ccGetInputValue()
        );
      break;
      default:break;
    }
    ssApplyGeneralScaleSetting();
  }//+++
  
  
  //=== 
  
  public final void fsLogBurningTrendRecord(){
    
    //-- getting
    McTrendRecord lpRecord=new McTrendRecord(
      snGetRevisedValue(cmChuteTemp),
      snGetRevisedValue(cmSandTemp),
      snGetRevisedValue(cmEntanceTemp),
      snGetScaledIntegerValue(cmVBunerDegree),
      snGetScaledIntegerValue(cmVExfanDegree)
    );
    McTrendLogger.ccGetReference().ccAddRecord(lpRecord);
    
    //-- sending
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run(){
        MainSketch.herFrame
          .cmMonitoringPane.cmTrendViewTable.ccUpdateTable();
        fnScrollToLast(MainSketch.herFrame.cmMonitoringPane.cmTrendViewTable);
      }//+++
    });
    
  }//+++
  
  public final void fsLogAutoWeighResult(){
    
    McAutoWeighRecord lpRecord=new McAutoWeighRecord();
    
    //-- calculate sum
    int lpSum=
       vmPoppedtKG.ccGetMaxAG()+
       vmPoppedtKG.ccGetMaxFR()+
       vmPoppedtKG.ccGetMaxAS();
    lpRecord.ccSetupMixerValue(cmMixtureTemp.ccGetScaledIntegerValue(), lpSum);
    
    //-- packup record
    for(int i=6;i>0;i--){
    lpRecord.ccSetAGIntegerValue(i, vmPoppedtKG.ccGetAG(i));
      if(i<=2){
        lpRecord.ccSetFRIntegerValue(i, vmPoppedtKG.ccGetFR(i));
      }if(i==1){
        lpRecord.ccSetASIntegerValue(i, vmPoppedtKG.ccGetAS(i));
      }//..?
    }//..~
    
    //-- log
    McAutoWeighLogger.ccGetReference().ccAddRecord(lpRecord);
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run(){
        MainSketch.herFrame
          .cmMonitoringPane.cmWeighLogTable.ccUpdateTable();
        fnScrollToLast(MainSketch.herFrame.cmMonitoringPane.cmWeighLogTable);
      }//+++
    });
    vmPoppedtKG.ccClearAll();
    
  }//+++
  
  public final void fsClearCurrentAutoWeighTargetValue(){
    McCategoryIntegerBundle lpRecord
      = McRecipeTable.ccGetReference().ccGetRecipeKG(0, 0);
    vmTargetKG.ccSet(lpRecord);
  }//+++
  
  public final void fsApplyCurrentAutoWeighRecipe(){
    
    //-- target
    int lpRecipe=cmBookedRecipe[0];
    int lpKG=cmBookedKillogram[0];
    McCategoryIntegerBundle lpRecord
      = McRecipeTable.ccGetReference().ccGetRecipeKG(lpRecipe, lpKG);
    vmTargetKG.ccSet(lpRecord);
    
    //-- drop point
    McCategoryIntegerBundle.ccSub(vmDropPointKG,
      vmTargetKG, vmDropOffsetKG,
      0,9999
    );
    
    //-- cut point
    McCategoryIntegerBundle.ccSub(vmCutPointKG,
      vmDropPointKG, vmCutOffsetKG,
      0,9999
    );
    
  }//+++
  
  public final void fsPopAutoWeighResult(){
    vmPoppedtKG.ccSet(vmResultKG);
  }//+++
  
  public final int fsGetCurrentRemianingBatch(){
    return cmBookedBatch[0];
  }//+++
  
  public final void fsBatchCountDown(){
    cmBookedBatch[0]--;
    if(cmBookedBatch[0]==0){
      
      cmBookedRecipe[0]=cmBookedRecipe[1];
      cmBookedKillogram[0]=cmBookedKillogram[1];
      cmBookedBatch[0]=cmBookedBatch[1];
      
      cmBookedRecipe[1]=cmBookedRecipe[2];
      cmBookedKillogram[1]=cmBookedKillogram[2];
      cmBookedBatch[1]=cmBookedBatch[2];
      
      cmBookedRecipe[2]=0;
      cmBookedKillogram[2]=0;
      cmBookedBatch[2]=0;
      
    }//..?
  }//+++
  
  public final void fsSetBookingRecipe(int pxIndex, int pxValue){
    if(pxIndex<0||pxIndex>C_MAX_BOOK_CAPABILITY){return;}
    cmBookedRecipe[pxIndex]=pxValue<0?0:pxValue;
  }//+++
  
  public final void fsSetBookingKG(int pxIndex, int pxValue){
    if(pxIndex<0||pxIndex>C_MAX_BOOK_CAPABILITY){return;}
    cmBookedKillogram[pxIndex]=constrain(pxValue, 0, cmMixerCapability);
  }//+++
  
  public final void fsSetBookingBatch(int pxIndex, int pxValue){
    if(pxIndex<0||pxIndex>C_MAX_BOOK_CAPABILITY){return;}
    cmBookedBatch[pxIndex]=constrain(pxValue, 0, C_MAX_BATCH_CAPABILITY);
  }//+++
  
  public final void fsSetupBooking(
    int pxIndex, int pxRecipe,int pxKG,int pxBatch
  ){
    fsSetBookingRecipe(pxIndex, pxRecipe);
    fsSetBookingKG(pxIndex, pxKG);
    fsSetBookingBatch(pxIndex, pxBatch);
  }//+++
  
  public final boolean fsShfitVBurnerTargetTemp(int pxID, int pxCount){
    if(pxID==MainLocalCoordinator.C_ID_VB_HEAD){
      vmVBurnerTargetTemp+=pxCount;
      vmVBurnerTargetTemp=constrain(vmVBurnerTargetTemp,50,250);
      return true;
    }return false;
  }//+++
  
  //===
  
  public final boolean ccShiftVFeederRPM(int pxID, int pxCount){
    int lpID=pxID-MainLocalCoordinator.C_ID_VF_HEAD;
    if(lpID<1){return false;}
    if(lpID>=cmVFRPM.length){return false;}
    int lpBuf=cmVFRPM[lpID];
    lpBuf+=pxCount*cmVFeederAdjustment;
    lpBuf=constrain(lpBuf, 0, C_DEFAULT_FEEDER_RPM_MAX);
    cmVFRPM[lpID]=lpBuf;
    return true;
  }//+++
  
  public final int ccGetVFeederRpmADValue(int pxIndex){
    if(pxIndex<1){return 0;}
    if(pxIndex>=cmVFRPM.length){return 0;}
    int lpBuf=cmVFRPM[pxIndex];
    return lpBuf*C_DEFAULT_FEEDER_AD_MAX/C_DEFAULT_FEEDER_RPM_MAX;
  }//+++
  
  //===
  
  synchronized public static
  int snGetScaledIntegerValue(ZcScaledModel pxModel){
    return pxModel.ccGetScaledIntegerValue();
  }//+++
  
  synchronized public static
  float snGetScaledFloatValue(ZcScaledModel pxModel){
    return pxModel.ccGetScaledFloatValue();
  }//+++ 
  
  synchronized public static
  int snGetInputValue(ZcScaledModel pxModel){
    return pxModel.ccGetInputValue();
  }//+++ 
  
  synchronized public static
  int snGetRevisedValue(ZcRevisedScaledModel pxModel){
    return pxModel.ccGetRevisedIntegerValue();
  }//+++
  
  //===
  
  public static char fnGetAGGaugeMode(int pxAGCellKG, int pxStage){
    return
      pxAGCellKG>self.vmTargetKG.ccGetAG(pxStage)?'t':
      pxAGCellKG>self.vmDropPointKG.ccGetAG(pxStage)?'d':
      pxAGCellKG>self.vmCutPointKG.ccGetAG(pxStage)?'c':'w';
  }//..?
  
  public static char fnGetFRGaugeMode(int pxFRCellKG, int pxStage){
    return
      pxFRCellKG>self.vmTargetKG.ccGetFR(pxStage)?'t':
      pxFRCellKG>self.vmDropPointKG.ccGetFR(pxStage)?'d':
      pxFRCellKG>self.vmCutPointKG.ccGetFR(pxStage)?'c':'w';
  }//..?
  
  public static char fnGetASGaugeMode(int pxASCellKG, int pxStage){
    return
      pxASCellKG>self.vmTargetKG.ccGetAS(pxStage)?'t':
      pxASCellKG>self.vmDropPointKG.ccGetAS(pxStage)?'d':
      pxASCellKG>self.vmCutPointKG.ccGetAS(pxStage)?'c':'w';
  }//..?
  
  //===
  
  @Deprecated public final void testReadupBundles(){
    
    System.out.println("-TGT-:");vmTargetKG.testReadup();
    System.out.println("-DRP-:");vmDropPointKG.testReadup();
    System.out.println("-CTP-^:");vmCutPointKG.testReadup();
    System.out.println("===:");
    System.out.println("-DRF-:");vmDropOffsetKG.testReadup();
    System.out.println("-CTF-:");vmCutOffsetKG.testReadup();
  
  }//+++
  
}//***eof
