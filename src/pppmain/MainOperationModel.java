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
    C_MAX_BOOK_CAPABILITY=3,
    C_MAX_MIXER_CAPABILITY=5000,
    C_MAX_BATCH_CAPABILITY=9999,
    //--
    C_GENERAL_AD_MAX = 3600,
    C_GENERAL_AD_MIN = 400,
    C_FEEDER_RPM_MAX = 1800,
    C_FEEDER_AD_MAX  = 5000
  ;//...
  
  //===
  
  public int
    
    //-- foundamental
    cmVDryerCapability=340,
    cmBagFilterSize=24,
    
    //-- setting
    cmVExfanDegreeLimitLow=20,cmVExfanDegreeLimithigh=80,
    cmBagEntranceTemprarueLimitLOW=230,cmBagEntranceTemprarueLimitHIGH=260,
    
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
    cmBookedRecipe    = {0,0,0},
    cmBookedKillogram = {0,0,0},
    cmBookedBatch     = {0,0,0},
    //-- cell
    cmAGCellADJUTST = {400,3600,0,4000},
    cmFRCellADJUTST = {400,3600,0,500},
    cmASCellADJUTST = {400,3600,0,500},
    //-- degree
    cmVExfanDegreeADJUST={400,3600,0,100},
    cmVBurnerDegreeADJUST={400,3600,0,100},
    cmVDryerPressureADJUST={1500,3000,0,200},
    //-- temp
    cmAggregateChuteTempratureADJUST={0,1000,0,100},
    cmAsphaultPipeTempratureADJUST={0,1000,0,100},
    cmBagEntranceTempratureADJUST={0,1000,0,100},
    cmSandBinTempratureADJUST={0,1000,0,100},
    cmMixtureTempratureADJUST={0,1000,0,100},
    //-- current
    cmVCompressorCurrentADJUST={0,5000,0,75},
    cmMixerCurrentADJUST={0,5000,0,280}
  ;//...
  
  //===
  
  public volatile int
    
    //-- setting
    vsFillerSiloAirNT=1,
    vsVBurnerTargetTempraure=160,
    vsVDryerTargetPressure=-50,
    
    //-- monitering
    vmBagEntranceTemprature,
    vmMixtureTemprature,
    vmAGCellKG,vmFRCellKG,vmASCellKG,
    //-- monitering ** current
    vmVCompressorCurrent,vmMixerCurrent
    
  ;//...
  
  //=== supporter
  
  public final int ccGetCurrentRemianingBatch(){
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
  
  public final void fsBookingSetup(
    int pxIndex, int pxRecipe,int pxKG,int pxBatch
  ){
    if(pxIndex<0||pxIndex>C_MAX_BOOK_CAPABILITY){return;}
    cmBookedRecipe[pxIndex]=pxRecipe<0?0:pxRecipe;
    cmBookedKillogram[pxIndex]=constrain(pxKG, 0, C_MAX_MIXER_CAPABILITY);
    cmBookedBatch[pxIndex]=constrain(pxBatch, 0, C_MAX_BATCH_CAPABILITY);
  }//+++
  
  public final boolean fsShfitVBurnerTargetTemp(int pxID, int pxCount){
    if(pxID==MainLocalCoordinator.C_ID_VB_MGH){
      vsVBurnerTargetTempraure+=pxCount;
      vsVBurnerTargetTempraure=constrain(vsVBurnerTargetTempraure,50,250);
      return true;
    }return false;
  }//+++
  
  public final boolean fsShiftFeederRPM(int pxID, int pxCount){
    switch(pxID){

      case SubVFeederModelGroup.C_ID_VF01:
        cmVF01RPM+=pxCount*cmVFeederAdjustment;
        cmVF01RPM=constrain(cmVF01RPM,0,C_FEEDER_RPM_MAX);
      break;

      case SubVFeederModelGroup.C_ID_VF02:
        cmVF02RPM+=pxCount*cmVFeederAdjustment;
        cmVF02RPM=constrain(cmVF02RPM,0,C_FEEDER_RPM_MAX);
      break;

      case SubVFeederModelGroup.C_ID_VF03:
        cmVF03RPM+=pxCount*cmVFeederAdjustment;
        cmVF03RPM=constrain(cmVF03RPM,0,C_FEEDER_RPM_MAX);
      break;

      case SubVFeederModelGroup.C_ID_VF04:
        cmVF04RPM+=pxCount*cmVFeederAdjustment;
        cmVF04RPM=constrain(cmVF04RPM,0,C_FEEDER_RPM_MAX);
      break;

      case SubVFeederModelGroup.C_ID_VF05:
        cmVF05RPM+=pxCount*cmVFeederAdjustment;
        cmVF05RPM=constrain(cmVF05RPM,0,C_FEEDER_RPM_MAX);
      break;

      case SubVFeederModelGroup.C_ID_VF06:
        cmVF06RPM+=pxCount*cmVFeederAdjustment;
        cmVF06RPM=constrain(cmVF06RPM,0,C_FEEDER_RPM_MAX);
      break;

      default:return false;

    }return true;
  }//+++
  
  //=== function
  public static final int fnToRealValue(int pxAD, int[] pxADJ){
    return ceil(map(pxAD,
      pxADJ[0],pxADJ[1],
      pxADJ[2],pxADJ[3]
    ));
  }//+++
  
  public static final int fntoADValue(int pxReal, int[] pxADJ){
     return ceil(map(pxReal,
      pxADJ[2],pxADJ[3],
      pxADJ[0],pxADJ[1]
    ));
  }//+++
  
}//***eof
