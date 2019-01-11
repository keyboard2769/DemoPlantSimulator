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

import static processing.core.PApplet.ceil;
import static processing.core.PApplet.map;

import pppunit.EcHotTower;
import pppunit.EcBagFilter;

import static pppmain.MainOperationModel.C_FEEDER_AD_MAX;
import static pppmain.MainOperationModel.C_FEEDER_RPM_MAX;
import static pppmain.MainOperationModel.C_GENERAL_AD_MAX;
import static pppmain.MainOperationModel.C_GENERAL_AD_MIN;

import static pppmain.MainSketch.hisUI;
import static pppmain.MainSketch.myPLC;
import static pppmain.MainSketch.yourMOD;

public final class TabWireManager {
  
  static private MainSketch mainSketch;
  
  //===
  
  //-- for swing action
  public static final int 
    C_K_MODIFY_SETTING = 0xCA0010,
    C_K_QUIT           = 0xCA0001,
    C_K_NONE           = 0xCA0000
  ;//...
  static volatile int actionID;
  static volatile String actionPARAM;
  
  //===
  
  private TabWireManager(){}//++!
  public static final void ccInit(MainSketch pxOwner){
    mainSketch=pxOwner;
    actionID=0;
    actionPARAM="<>";
  }//++!
  
  //===
  
  public static final void ccUpdate(){
    
    //-- single
    ccKeep();
    ccRecieveAndSend();
    
    //-- control
    controlWeigh();
    controlVMotor();
    controlVBurner();
    
    //-- wire
    wireVFeeder();
    wireVBurnerDryer();
    wireBagFilter();
    wireFRSupplyChain();
    wireASSupplyChain();
    wireApTower();
    wireMixer();
    
  }//+++
  
  //=== swing
  
  public static final void ccSetCommand(int pxID, String pxParam){
    actionID=pxID;
    actionPARAM=pxParam;
  }//+++
  
  public static final void ccClearCommand(){
    ccSetCommand(C_K_NONE, "<>");
  }//+++
  
  private static void ccKeep(){
    switch(actionID){
      
      case C_K_MODIFY_SETTING:ckModifySetting();break;
      
      case C_K_QUIT:mainSketch.fsPover();break;
       
      default:break;
    }//..?
    ccClearCommand();
  }//+++
  
  private static void ckModifySetting(){
    System.out.println(".ckModifySetting()::"+actionPARAM);
    System.out.println("..ccModifySetting()::not implemented yet");
  }//+++
  
  //=== PLC
  
  private static void ccRecieveAndSend(){
    
    //-- monitering
    
    //-- monitering ** cell
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
    
    //-- monitering ** temprature
    
    yourMOD.vmBagEntranceTemprature=MainOperationModel.fnToRealValue
      (myPLC.cmVBurnerDryerTask.dcTH2, yourMOD.cmBagEntranceTempratureADJUST);
    
    yourMOD.vmMixtureTemprature=MainOperationModel.fnToRealValue
      (myPLC.cmAutoWeighTask.dcTH6, yourMOD.cmMixtureTempratureADJUST);
    
    //-- monitering ** current
    yourMOD.vmVCompressorCurrent=MainOperationModel.fnToRealValue
      (myPLC.cmMainTask.dcCT13, yourMOD.cmVCompressorCurrentADJUST);
    yourMOD.vmMixerCurrent=MainOperationModel.fnToRealValue
      (myPLC.cmMainTask.dcCT6, yourMOD.cmMixerCurrentADJUST);
    
    //-- setting 
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
    
    //-- setting ** filler
    myPLC.cmFillerSupplyTask.mnFRSiloAirAutoSW=
      (yourMOD.vsFillerSiloAirNT==0);
    myPLC.cmFillerSupplyTask.mnFRSiloAirManualSW=
      (yourMOD.vsFillerSiloAirNT==2);
    
  }//+++
  
  //=== control
  
  private static void controlWeigh(){
    
    //-- auto vs manual
    myPLC.cmAutoWeighTask.mnWeighAutoSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AUTO);
    hisUI.cmBookingControlGroup.cmAutoSW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnWeighAutoPL);
    
    myPLC.cmAutoWeighTask.mnWeighManualSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_MANN);
    hisUI.cmBookingControlGroup.cmManualSW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnWeighManualPL);
    
    //-- gate
    
    //-- gate ** ag
    myPLC.cmAutoWeighTask.mnAGDSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH);
    hisUI.cmWeighControlGroup.cmAGDischargeSW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnAGDPL);
    
    myPLC.cmAutoWeighTask.mnAG6SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+6);
    hisUI.cmWeighControlGroup.cmAG6SW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.cmAG6W);
    
    myPLC.cmAutoWeighTask.mnAG5SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+5);
    hisUI.cmWeighControlGroup.cmAG5SW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.cmAG5W);
    
    myPLC.cmAutoWeighTask.mnAG4SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+4);
    hisUI.cmWeighControlGroup.cmAG4SW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.cmAG4W);
    
    myPLC.cmAutoWeighTask.mnAG3SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+3);
    hisUI.cmWeighControlGroup.cmAG3SW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.cmAG3W);
    
    myPLC.cmAutoWeighTask.mnAG2SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+2);
    hisUI.cmWeighControlGroup.cmAG2SW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.cmAG2W);
    
    myPLC.cmAutoWeighTask.mnAG1SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AG_DISH+1);
    hisUI.cmWeighControlGroup.cmAG1SW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.cmAG1W);
    
    //-- gate ** fr
    myPLC.cmAutoWeighTask.mnFRDSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_FR_DISH);
    hisUI.cmWeighControlGroup.cmFRDischargeSW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnFRDPL);
    
    myPLC.cmAutoWeighTask.mnFR1SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_FR_DISH+1);
    hisUI.cmWeighControlGroup.cmFR1SW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.dcFR1);
    
    myPLC.cmAutoWeighTask.mnFR2SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_FR_DISH+2);
    hisUI.cmWeighControlGroup.cmFR2SW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.dcFR2);
    
    //-- gate ** as
    myPLC.cmAutoWeighTask.mnASDSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AS_DISH);
    hisUI.cmWeighControlGroup.cmASDischargeSW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.mnASDPL);
    
    myPLC.cmAutoWeighTask.mnAS1SW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_WEIGH_AS_DISH+1);
    hisUI.cmWeighControlGroup.cmAS1SW.ccSetIsActivated
      (myPLC.cmAutoWeighTask.dcAS1);
    
    //-- cell
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
    hisUI.cmVFeederGroup.cmVHBC.ccSetMotorStatus
      (myPLC.cmAggregateSupplyTask.dcVHorizontalBelconAN?'a':'x');
    
    //-- vf
    //-- ** ** motor
    hisUI.cmVFeederGroup.cmVF01.ccSetMotorStatus
      (myPLC.cmAggregateSupplyTask.dcVFAN01?'a':'x');
    hisUI.cmVFeederGroup.cmVF02.ccSetMotorStatus
      (myPLC.cmAggregateSupplyTask.dcVFAN02?'a':'x');
    hisUI.cmVFeederGroup.cmVF03.ccSetMotorStatus
      (myPLC.cmAggregateSupplyTask.dcVFAN03?'a':'x');
    hisUI.cmVFeederGroup.cmVF04.ccSetMotorStatus
      (myPLC.cmAggregateSupplyTask.dcVFAN04?'a':'x');
    hisUI.cmVFeederGroup.cmVF05.ccSetMotorStatus
      (myPLC.cmAggregateSupplyTask.dcVFAN05?'a':'x');
    hisUI.cmVFeederGroup.cmVF06.ccSetMotorStatus
      (myPLC.cmAggregateSupplyTask.dcVFAN06?'a':'x');
    
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
    hisUI.cmVSupplyGroup.cmVB.ccSetMotorStatus
      (myPLC.cmVBurnerDryerTask.dcVBurnerFanAN?'a':'x');
    hisUI.cmVSupplyGroup.cmVB.ccSetIsIgniting
      (myPLC.cmVBurnerDryerTask.dcIG);
    hisUI.cmVSupplyGroup.cmVB.ccSetIsPiloting
      (myPLC.cmVBurnerDryerTask.dcPV);
    hisUI.cmVSupplyGroup.cmVB.ccSetHasFire
      (myPLC.cmVBurnerDryerTask.dcMMV);
    hisUI.cmVSupplyGroup.cmVB.ccSetIsFull
      (myPLC.cmVBurnerDryerTask.dcVBOPLS);
    hisUI.cmVSupplyGroup.cmVB.ccSetIsClosed
      (myPLC.cmVBurnerDryerTask.dcVBCLLS);
    hisUI.cmVSupplyGroup.cmVB.ccSetDegree
      (MainOperationModel.fnToRealValue(
        myPLC.cmVBurnerDryerTask.dcVBO,
        yourMOD.cmVBurnerDegreeADJUST
      ));
    hisUI.cmVSupplyGroup.cmVB.ccSetTargetTemp
      (yourMOD.vsVBurnerTargetTempraure);
    
    //-- v combustor
    hisUI.cmVCombustGroup.cmVFU.ccSetMotorStatus
      (myPLC.cmVBurnerDryerTask.dcFuelPumpAN?'a':'x');
    hisUI.cmVCombustGroup.cmVFU.ccSetFuelON
      (myPLC.cmVBurnerDryerTask.dcFuelMV);
    hisUI.cmVCombustGroup.cmVFU.ccSetHeavyON
      (myPLC.cmVBurnerDryerTask.dcHeavyMV);
    
    //-- dryer
    hisUI.cmVSupplyGroup.cmVIBC.ccSetHasAggregateFlow
      (myPLC.cmAggregateSupplyTask.dcCAS);
    hisUI.cmVSupplyGroup.cmVD.ccSetIsOnFire
      (myPLC.cmVBurnerDryerTask.dcMMV);
    hisUI.cmVSupplyGroup.cmVD.ccSetKPA
      (MainOperationModel.fnToRealValue(
        myPLC.cmVBurnerDryerTask.dcVSE,
        yourMOD.cmVDryerPressureADJUST
      ));
    hisUI.cmVSupplyGroup.cmVD.ccSetTPH(ceil(map(
      myPLC.cmAggregateSupplyTask.dcVFCS,
      C_GENERAL_AD_MIN,C_GENERAL_AD_MAX,
      0,yourMOD.cmVDryerCapability
    )));
    hisUI.cmVSupplyGroup.cmVD.ccSetMotorStatus
      (myPLC.cmAggregateSupplyTask.dcVDryerAN?'a':'x');
    hisUI.cmVSupplyGroup.cmVIBC.ccSetMotorStatus
      (myPLC.cmAggregateSupplyTask.dcVInclineBelconAN?'a':'x');
    
  }//+++
  
  private static void wireBagFilter(){
    
    //-- bag
    hisUI.cmVSupplyGroup.cmBAG.ccSetCoolingDamperStatus
      (myPLC.cmVBurnerDryerTask.dcCoolingDamperMV);
    hisUI.cmVSupplyGroup.cmBAG.ccSetMotorStatus(EcBagFilter.C_M_BAG_SCREW,
      myPLC.cmDustExtractTask.dcMainBagScrewAN?'a':'x'
    );
    hisUI.cmVSupplyGroup.cmBAG.ccSetDustFlow
      ('e', myPLC.cmDustExtractTask.dcDustExtractScrewAN);
    hisUI.cmVSupplyGroup.cmBAG.ccSetDustFlow
      ('f', myPLC.cmAutoWeighTask.dcFR2);
    hisUI.cmVSupplyGroup.cmBAG.ccSetCurrentFilterCount
      (myPLC.cmDustExtractTask.mnBagPulseCurrentCount);
    hisUI.cmVSupplyGroup.cmBAG.ccSetMotorStatus(
      EcBagFilter.C_M_COARSE_SCREW,
      myPLC.cmDustExtractTask.dcCoarseScrewAN?'a':'x'
    );
    hisUI.cmVSupplyGroup.cmBAG.ccSetEntranceTemprature
      (yourMOD.vmBagEntranceTemprature);
    hisUI.cmVSupplyGroup.cmBAG.ccSetBagLevelerStatus
      ('h', myPLC.cmDustExtractTask.dcF2H);
    hisUI.cmVSupplyGroup.cmBAG.ccSetBagLevelerStatus
      ('l', myPLC.cmDustExtractTask.dcF2L);
    
    //-- v exf
    hisUI.cmVSupplyGroup.cmVEXF.ccSetMotorStatus
      (myPLC.cmVBurnerDryerTask.dcVExfanAN?'a':'x');
    hisUI.cmVSupplyGroup.cmVEXF.ccSetIsFull
      (myPLC.cmVBurnerDryerTask.dcVEFOPLS);
    hisUI.cmVSupplyGroup.cmVEXF.ccSetIsClosed
      (myPLC.cmVBurnerDryerTask.dcVEFCLLS);
    hisUI.cmVSupplyGroup.cmVEXF.ccSetHasPressure
      (myPLC.cmVBurnerDryerTask.dcHSW);
    hisUI.cmVSupplyGroup.cmVEXF.ccSetDegree(
      MainOperationModel.fnToRealValue(
        myPLC.cmVBurnerDryerTask.dcVDO,yourMOD.cmVExfanDegreeADJUST
      )
    );
    
  }//+++
  
  private static void wireFRSupplyChain(){
    hisUI.cmFillerSupplyGroup.cmFS.ccSetIsAirating
      (myPLC.cmFillerSupplyTask.dcFillerSiloAIR);
    hisUI.cmFillerSupplyGroup.cmFS.ccSetMotorStatus
      (myPLC.cmFillerSupplyTask.dcFillerSiloScrewAN?'a':'x');
    hisUI.cmFillerSupplyGroup.cmFEV.ccSetMotorStatus
      (myPLC.cmFillerSupplyTask.dcFillerElevatorAN?'a':'x');
    hisUI.cmFillerSupplyGroup.cmFBL.ccSetIsActivated
      (myPLC.cmFillerSupplyTask.dcFillerBinLV);
    hisUI.cmFillerSupplyGroup.cmFF.ccSetIsActivated
      (myPLC.cmFillerSupplyTask.cxFillerBinDischargeFLG);
    hisUI.cmFillerSupplyGroup.cmFS.ccSetSiloLevel(
      myPLC.cmFillerSupplyTask.dcFillerSiloHLV?'f':
      myPLC.cmFillerSupplyTask.dcFillerSiloMLV?'m':
      myPLC.cmFillerSupplyTask.dcFillerSiloLLV?'l':'e'
    );
    hisUI.cmFillerSupplyGroup.cmFF.ccSetIsActivated
      (myPLC.cmAutoWeighTask.dcFR1);
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
    hisUI.cmVSupplyGroup.cmMU.ccSetMotorStatus(
      EcHotTower.C_I_SCREEN,
      myPLC.cmAggregateSupplyTask.dcScreenAN?'a':'x'
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetMotorStatus(
      EcHotTower.C_I_HOTELEVATOR,
      myPLC.cmAggregateSupplyTask.dcHotElevatorAN?'a':'x'
    );
    
    hisUI.cmVSupplyGroup.cmMU.ccSetMotorStatus(
      EcHotTower.C_I_BLOWER, 
      myPLC.cmVBurnerDryerTask.dcAPBlowerAN?'a':'x'
    );
    
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
    
    //-- hb ** gate
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinGate(6,
      myPLC.cmAutoWeighTask.dcAG6OMV,
      myPLC.cmAutoWeighTask.dcAG6MAS,
      myPLC.cmAutoWeighTask.dcAG6CMV
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinGate(5,
      myPLC.cmAutoWeighTask.dcAG5OMV,
      myPLC.cmAutoWeighTask.dcAG5MAS,
      myPLC.cmAutoWeighTask.dcAG5CMV
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinGate(4,
      myPLC.cmAutoWeighTask.dcAG4OMV,
      myPLC.cmAutoWeighTask.dcAG4MAS,
      myPLC.cmAutoWeighTask.dcAG4CMV
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinGate(3,
      myPLC.cmAutoWeighTask.dcAG3OMV,
      myPLC.cmAutoWeighTask.dcAG3MAS,
      myPLC.cmAutoWeighTask.dcAG3CMV
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinGate(2,
      myPLC.cmAutoWeighTask.dcAG2OMV,
      myPLC.cmAutoWeighTask.dcAG2MAS,
      myPLC.cmAutoWeighTask.dcAG2CMV
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetHotBinGate(1,
      myPLC.cmAutoWeighTask.dcAG1OMV,
      myPLC.cmAutoWeighTask.dcAG1MAS,
      myPLC.cmAutoWeighTask.dcAG1CMV
    );
    
    //-- extraction
    hisUI.cmVSupplyGroup.cmMU.ccSetIsOverFlowFull
      (myPLC.cmAggregateSupplyTask.dcOF1);
    hisUI.cmVSupplyGroup.cmMU.ccSetIsOverSizeFull
      (myPLC.cmAggregateSupplyTask.dcOS1);
    
    //-- temprature
    hisUI.cmVSupplyGroup.cmMU.ccSetChuteTemrature(
      MainOperationModel.fnToRealValue(
        myPLC.cmVBurnerDryerTask.dcTH1,
        yourMOD.cmAggregateChuteTempratureADJUST
      )
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetSandTemrature(
      MainOperationModel.fnToRealValue(
        myPLC.cmAggregateSupplyTask.dcTH4,
        yourMOD.cmSandBinTempratureADJUST
      )
    );
    
  }//+++
  
  private static void wireMixer(){
    hisUI.cmMixerModelGroup.cmMixer.ccSetMotorStatus
      (myPLC.cmMainTask.dcMixerAN?'a':'x');
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

}//***eof
