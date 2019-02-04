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

import java.util.concurrent.atomic.AtomicIntegerArray;
import javax.swing.SwingUtilities;
import ppptable.McAutoWeighLogger;
import ppptable.McAutoWeighRecord;
import ppptable.McAutoWeighSetting;
import ppptable.McLockedCategoryIntegerRecord;
import ppptable.McRecipeTable;
import ppptable.McTrendLogger;
import ppptable.McTempScaleSetting;
import ppptable.McTrendRecord;
import ppptask.ZcRevisedScaledModel;
import kosui.ppplogic.ZcScaledModel;
import static processing.core.PApplet.ceil;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;

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
    C_DEFAULT_AD_OFFS = 400,
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
    cmMixerCapability=5000,
    cmVDryerCapability=340,
    cmBagFilterSize=24,
    
    //-- optional
    cmVFeederAdjustment=50,
    
    //-- commanding
    cmVF01RPM=900,cmVF02RPM=900,cmVF03RPM=900,
    cmVF04RPM=850,cmVF05RPM=750,cmVF06RPM=650,
    
    //--
    duummy=0
  ;//...
  
  public int[] 
    //-- booking
    cmBookedRecipe    = {0,0,0,0},
    cmBookedKillogram = {0,0,0,0},
    cmBookedBatch     = {0,0,0,0},
    //-- cell
    cmAGCellADJUTST = {400,3600,0,4000},
    cmFRCellADJUTST = {400,3600,0,500},
    cmASCellADJUTST = {400,3600,0,500},
    //-- degree
    cmVExfanDegreeADJUST={400,3600,0,100},
    cmVBurnerDegreeADJUST={400,3600,0,100},
    cmVDryerPressureADJUST={1500,3000,0,200},
    //-- temp
    cmAggregateChuteTempADJUST={0,1000,0,100},
    cmAsphaultPipeTempratureADJUST={0,1000,0,100},
    cmBagEntranceTempADJUST={0,1000,0,100},
    cmSandBinTempratureADJUST={0,1000,0,100}
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
    //-- setting ** temperature
    cmVExfanDegreeLimitLow=20,cmVExfanDegreeLimithigh=80,
    cmBagEntranceTemperatureLimitLOW=230,cmBagEntranceTemperatureLimitHIGH=260,
    vmVBurnerTargetTemp=160,
    //-- setting ** misc
    cmDryTimeSetting,cmWetTimeSetting,
    vmVDryerTargetPressure=-50,
    
    //-- monitering
    //-- monitering ** weighing
    vmResultFR2,vmResultFR1,
    vmResultAG6,vmResultAG5,vmResultAG4,vmResultAG3,vmResultAG2,vmResultAG1,
    vmResultAS1,
    //--
    vmPopedFR2,vmPopedFR1,
    vmPopedAG6,vmPopedAG5,vmPopedAG4,vmPopedAG3,vmPopedAG2,vmPopedAG1,
    vmPopedAS1,
    //--
    //-- monitering ** temprature
    vmHotChuteTempCD,
    vmBagEntranceTempCD,
    
    //-- monitering ** cell
    vmAGCellKG,vmFRCellKG,vmASCellKG,
    
    //-- monitering ** degree
    vmVBurnerDegreeAD,vmVBurnerDegreePT,
    
    //-- monitering ** ton per hour
    vmVTPH
    
  ;//...
  
  public final McLockedCategoryIntegerRecord
    vmTargetKG=new McLockedCategoryIntegerRecord()
  ;//...
  
  public final AtomicIntegerArray vmCurrentVALUE
    =new AtomicIntegerArray(new int[]{
      0,0,0,0, 0,0,0,0,
      0,0,0,0, 0,0,0,0
    });
  
  
  //-- temperature
  public final ZcRevisedScaledModel
    cmMixtureTemp=new ZcRevisedScaledModel(
      C_DEFAULT_TEMP_AD_OFFS, C_DEFAULT_TEMP_AD_SPAN,
      C_DEFAULT_TEMP_DC_OFFS, C_DEFAULT_TEMP_DC_SPAN
    )
  ;//...
  
  //===
  
  
  public final void ccApplySettingContent(){
    
    //-- auto weigh 
    McAutoWeighSetting lpAutoWeighSetting=McAutoWeighSetting.ccGetReference();
    cmDryTimeSetting=lpAutoWeighSetting.ccGetIntegerValue("--aTime-dry");
    cmWetTimeSetting=lpAutoWeighSetting.ccGetIntegerValue("--aTime-wet");
    
    ssApplyTempScaleSetting();
    
    
  }//+++
  
  private void ssApplyTempScaleSetting(){
    McTempScaleSetting lpSetting = McTempScaleSetting.ccGetReference();
    
    //-- mixer
    cmMixtureTemp.ccSetBias
      (lpSetting.ccGetIntegerValue("--mixer-tbias"));
    cmMixtureTemp.ccSetOffset
      (lpSetting.ccGetIntegerValue("--mixer-toffset"));
    cmMixtureTemp.ccSetInputOffset
      (lpSetting.ccGetIntegerValue("--aaGtemp-ad-offset"));
    cmMixtureTemp.ccSetInputSpan
      (lpSetting.ccGetIntegerValue("--aaGtemp-ad-span"));
    cmMixtureTemp.ccSetOutputOffset
      (lpSetting.ccGetIntegerValue("--aaGtemp-dc-offset"));
    cmMixtureTemp.ccSetOutputSpan
      (lpSetting.ccGetIntegerValue("--aaGtemp-dc-span"));
    
  }//+++
  
  
  //=== 
  
  public final void fsLogBurningTrendRecord(){
    
    //[TODO]::replace dummy data
    McTrendRecord lpRecord=new McTrendRecord(
      vmHotChuteTempCD, 99, vmBagEntranceTempCD,
      vmVBurnerDegreePT, 99
    );
    
    McTrendLogger.ccGetReference().ccAddRecord(lpRecord);
    
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run(){
        MainSketch.herFrame
          .cmMonitoringPane.cmTrendViewTable.ccUpdateTable();
        //[TODO]::transfer to ScFactory
        MainSketch.fnScrollToLast
          (MainSketch.herFrame.cmMonitoringPane.cmTrendViewTable);
        
      }//+++
    });
    
  }//+++
  
  public final void fsLogAutoWeighResult(){
    
    McAutoWeighRecord lpRecord=new McAutoWeighRecord();
    //[TOFIX]::sum may get lost when specific category is skipepd
    int lpSum=
       vmPopedAG1+
       vmPopedFR1+
       vmPopedAS1;
    lpRecord.ccSetAGIntegerValue(6, vmPopedAG6);
    lpRecord.ccSetAGIntegerValue(5, vmPopedAG5);
    lpRecord.ccSetAGIntegerValue(4, vmPopedAG4);
    lpRecord.ccSetAGIntegerValue(3, vmPopedAG3);
    lpRecord.ccSetAGIntegerValue(2, vmPopedAG2);
    lpRecord.ccSetAGIntegerValue(1, vmPopedAG1);
    lpRecord.ccSetFRIntegerValue(2, vmPopedFR2);
    lpRecord.ccSetFRIntegerValue(1, vmPopedFR1);
    lpRecord.ccSetASIntegerValue(1, vmPopedAS1);
    lpRecord.ccSetupMixerValue(cmMixtureTemp.ccGetlScaledIntValue(), lpSum);
    
    McAutoWeighLogger.ccGetReference().ccAddRecord(lpRecord);
    
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run(){
        MainSketch.herFrame
          .cmMonitoringPane.cmWeighLogTable.ccUpdateTable();
        //[TODO]::transfer to ScFactory
        MainSketch.fnScrollToLast
          (MainSketch.herFrame.cmMonitoringPane.cmWeighLogTable);
      }//+++
    });
    
    vmPopedAG6=0;
    vmPopedAG5=0;
    vmPopedAG4=0;
    vmPopedAG3=0;
    vmPopedAG2=0;
    vmPopedAG1=0;
    vmPopedFR2=0;
    vmPopedFR1=0;
    vmPopedAS1=0;
    
  }//+++
  
  public final void fsClearCurrentAutoWeighTargetValue(){
    McLockedCategoryIntegerRecord lpRecord
      = McRecipeTable.ccGetReference().ccGetRecipeKG(0, 0);
    vmTargetKG.ccSet(lpRecord);
  }//+++
  
  public final void fsApplyCurrentAutoWeighRecipe(){
    int lpRecipe=cmBookedRecipe[0];
    int lpKG=cmBookedKillogram[0];
    McLockedCategoryIntegerRecord lpRecord
      = McRecipeTable.ccGetReference().ccGetRecipeKG(lpRecipe, lpKG);
    vmTargetKG.ccSet(lpRecord);
  }//+++
  
  public final void fsPopAutoWeighResult(){
    
    vmPopedAG6=vmResultAG6;
    vmPopedAG5=vmResultAG5;
    vmPopedAG4=vmResultAG4;
    vmPopedAG3=vmResultAG3;
    vmPopedAG2=vmResultAG2;
    vmPopedAG1=vmResultAG1;
    vmPopedFR2=vmResultFR2;
    vmPopedFR1=vmResultFR1;
    vmPopedAS1=vmResultAS1;
    
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
    if(pxID==MainLocalCoordinator.C_ID_VB_MGH){
      vmVBurnerTargetTemp+=pxCount;
      vmVBurnerTargetTemp=constrain(vmVBurnerTargetTemp,50,250);
      return true;
    }return false;
  }//+++
  
  public final boolean fsShiftFeederRPM(int pxID, int pxCount){
    switch(pxID){

      case MainLocalCoordinator.C_ID_VF01:
        cmVF01RPM+=pxCount*cmVFeederAdjustment;
        cmVF01RPM=constrain(cmVF01RPM,0,C_DEFAULT_FEEDER_RPM_MAX);
      break;

      case MainLocalCoordinator.C_ID_VF02:
        cmVF02RPM+=pxCount*cmVFeederAdjustment;
        cmVF02RPM=constrain(cmVF02RPM,0,C_DEFAULT_FEEDER_RPM_MAX);
      break;

      case MainLocalCoordinator.C_ID_VF03:
        cmVF03RPM+=pxCount*cmVFeederAdjustment;
        cmVF03RPM=constrain(cmVF03RPM,0,C_DEFAULT_FEEDER_RPM_MAX);
      break;

      case MainLocalCoordinator.C_ID_VF04:
        cmVF04RPM+=pxCount*cmVFeederAdjustment;
        cmVF04RPM=constrain(cmVF04RPM,0,C_DEFAULT_FEEDER_RPM_MAX);
      break;

      case MainLocalCoordinator.C_ID_VF05:
        cmVF05RPM+=pxCount*cmVFeederAdjustment;
        cmVF05RPM=constrain(cmVF05RPM,0,C_DEFAULT_FEEDER_RPM_MAX);
      break;

      case MainLocalCoordinator.C_ID_VF06:
        cmVF06RPM+=pxCount*cmVFeederAdjustment;
        cmVF06RPM=constrain(cmVF06RPM,0,C_DEFAULT_FEEDER_RPM_MAX);
      break;

      default:return false;

    }return true;
  }//+++
  
  //=== 
  
  /**
   * [TODO]::
   * @param pxAD #
   * @param pxSpan Max value of ct
   * @return in Ampare
   * @deprecated a scaled model should be used
   */
  @Deprecated public static final int fnAdjustCurrent(int pxAD, int pxSpan){
    return pxAD*pxSpan/5000;
  }//+++
  
  public static final int fnToRealIntegerValue(int pxAD, int[] pxADJ){
    return ceil(map(pxAD,
      pxADJ[0],pxADJ[1],
      pxADJ[2],pxADJ[3]
    ));
  }//+++
  
  public static final int fnToAdValue(int pxReal, int[] pxADJ){
     return ceil(map(pxReal,
      pxADJ[2],pxADJ[3],
      pxADJ[0],pxADJ[1]
    ));
  }//+++
  
  //===
  
  synchronized public static
  int snGetScaledIntegerValue(ZcScaledModel pxModel){
    return pxModel.ccGetlScaledIntValue();
  }//+++
  
  synchronized public static
  float snGetScaledFloatValue(ZcScaledModel pxModel){
    return pxModel.ccGetScaledFloatValue();
  }//+++ 
  
  synchronized public static
  int snGetUnscaledValue(ZcScaledModel pxModel){
    return pxModel.ccGetInputValue();
  }//+++ 
  
  synchronized public static
  int snGetRevisedTempValue(ZcRevisedScaledModel pxModel){
    return pxModel.ccGetRevisedIntegerValue();
  }//+++
  
}//***eof
