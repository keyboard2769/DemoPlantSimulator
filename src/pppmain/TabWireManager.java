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
  
  private TabWireManager(){}//++!
  public static final void ccInit(MainSketch pxOwner){
    mainSketch=pxOwner;
  }//++!
  
  //===
  
  public static final void ccUpdate(){
    
    ccRecieveAndSend();
    
    //-- control
    controlVMotor();
    controlVBurner();
    
    //-- wire
    wireVFeeder();
    wireVBurnerDryer();
    wireBagFilter();
    wireFRSupplyChain();
    wireApTower();
    wireMixer();
    
  }//+++
  
  //===
  
  private static void ccRecieveAndSend(){
    
    //-- monitering
    yourMOD.cmBagEntranceTemrature=MainOperationModel.fnToRealValue
      (myPLC.cmVBurnerDryerTask.dcTH2, yourMOD.cmBagEntranceTempratureADJUST);
    
    //-- setting 
    myPLC.cmVBurnerDryerTask.mnVDPressureTargetAD=
      MainOperationModel.fntoADValue(
        yourMOD.cmVDryerTargetPressure, yourMOD.cmVDryerPressureADJUST
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
      MainOperationModel.fntoADValue(
        yourMOD.cmVBurnerTargetTempraure,
        yourMOD.cmAggregateChuteTempratureADJUST
      );
    
    myPLC.cmVBurnerDryerTask.mnCoolingDamperOpenSIG=
      (yourMOD.cmBagEntranceTemrature>yourMOD.cmBagEntranceTemprarueLimitLOW);
    
    myPLC.cmVBurnerDryerTask.mnFireStopSIG=
      (yourMOD.cmBagEntranceTemrature>yourMOD.cmBagEntranceTemprarueLimitHIGH);
    
  }//+++
  
  //=== control
  
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
      .ccSetIsActivated(myPLC.cmFillerSupplyTask.mnFRSUpplyStartPL);
    
    myPLC.cmAggregateSupplyTask.mnAGSupplyStartSW=
      mainSketch.fnIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+9);
    hisUI.cmVMotorControlGroup.cmMotorSW[9]
      .ccSetIsActivated(myPLC.cmAggregateSupplyTask.mnAGSUpplyStartPL);
    
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
      (yourMOD.cmVBurnerTargetTempraure);
    
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
    hisUI.cmVSupplyGroup.cmBAG.ccSetCurrentFilterCount
      (myPLC.cmDustExtractTask.mnBagPulseCurrentCount);
    hisUI.cmVSupplyGroup.cmBAG.ccSetMotorStatus(
      EcBagFilter.C_M_COARSE_SCREW,
      myPLC.cmDustExtractTask.dcCoarseScrewAN?'a':'x'
    );
    hisUI.cmVSupplyGroup.cmBAG.ccSetEntranceTemprature
      (yourMOD.cmBagEntranceTemrature);
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
    
    //-- hb
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
    
  }//+++

}//***eof
