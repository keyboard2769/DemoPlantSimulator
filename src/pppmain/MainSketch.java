/* DEMO of PLANT SIMULATOR
 *
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

import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.core.PVector;

import kosui.ppplocalui.VcAxis;
import kosui.ppplocalui.VcTagger;

import pppunit.EcHotTower;
import pppunit.EcBagFilter;

import static pppmain.MainOperationModel.C_GENERAL_AD_MIN;
import static pppmain.MainOperationModel.C_GENERAL_AD_MAX;
import static pppmain.MainOperationModel.C_FEEDER_AD_MAX;
import static pppmain.MainOperationModel.C_FEEDER_RPM_MAX;

public class MainSketch extends PApplet {
  
  static private int pbMillis=0;
  
  static private MainLocalCoordinator hisUI;
  //static private MainSwingCoordinator herFrame;
  static private MainLogicController myPLC;
  static private MainOperationModel yourMOD;

  //=== overridden

  @Override public void setup() {

    //-- pre setting 
    size(800, 600);
    noSmooth();
    frame.setTitle("Plant Simulator");

    //-- initiating
    hisUI=new MainLocalCoordinator(this);
    myPLC=new MainLogicController(this);
    yourMOD=new MainOperationModel(this);
    //-- initatating ** flipping
    VcAxis.ccFlip();
    
    //-- setting up
    hisUI.cmVSupplyGroup.cmBAG
      .ccSetBagFilterSize(yourMOD.cmBagFilterSize);
    myPLC.cmDustExtractTask
      .ccSetBagFilterSize(yourMOD.cmBagFilterSize);
    
    //-- test 
    
    //-- post setting
    println("-- DemoPlantSimulator:setup done.");
  }//+++
  
  @Override public void draw() { 

    //-- pre drawing
    pbMillis=millis();
    background(0);
    
    //-- links
    fsLinking();
    
    //-- updating
    myPLC.ccRun();
    hisUI.ccUpdate();
    
    //-- system
    VcAxis.ccUpdate();
    
    //-- tagging
    VcTagger.ccTag("*----*", 0);
    VcTagger.ccTag("mouseID",hisUI.ccGetMouseOverID());
    
    //-- tagging ** ending
    VcTagger.ccTag("fps", nfc(frameRate,2));
    pbMillis=millis()-pbMillis;
    VcTagger.ccTag("ms/f", pbMillis);
    VcTagger.ccStabilize();

  }//+++
  
  @Override public void keyPressed() {switch(key){
    //-- triiger
    case 'n':VcTagger.ccFlip();break;
    case 'm':VcAxis.ccFlip();break;
    
    //-- system 
    case ',':VcAxis.ccSetAnchor(mouseX, mouseY);break;
    case '.':VcAxis.ccSetAnchor(0,0);break;
    case 'q':fsPover();break;
    default:break;
  }}//+++

  @Override public void mouseWheel(MouseEvent e){
    int lpCount=-1*e.getCount();
    if(yourMOD.fsShfitVBurnerTargetTemp(hisUI.ccGetMouseOverID(), lpCount))
      {return;}
    if(yourMOD.fsShiftFeederRPM(hisUI.ccGetMouseOverID(), lpCount))
      {return;}
    lpCount=0;
  }//+++
  
  //=== support

  private void fsPover(){
    //-- flushing
    
    //-- defualt
    println("-- DemoPlantSimulator:exit done.");
    exit();
  }//+++
  
  private boolean fsIsPressed(int pxID)
    {return mousePressed && (hisUI.ccGetMouseOverID()==pxID);}//+++
  
  private void fsLinking(){
    
    //-- control
    //-- control ** v motor
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    
    myPLC.cmMainTask.mnVCompressorSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+0);
    hisUI.cmVMotorControlGroup.cmMotorSW[0]
      .ccSetIsActivated(myPLC.cmMainTask.mnVCompressorPL);
    
    myPLC.cmVBurnerDryerTask.mnAPBlowerSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+3);
    hisUI.cmVMotorControlGroup.cmMotorSW[3]
      .ccSetIsActivated(myPLC.cmVBurnerDryerTask.mnAPBlowerPL);
    
    myPLC.cmMainTask.mnMixerMoterSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+6);
    hisUI.cmVMotorControlGroup.cmMotorSW[6]
      .ccSetIsActivated(myPLC.cmMainTask.mnMixerMoterPL);
    
    myPLC.cmFillerSupplyTask.mnFRSupplyStartSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+7);
    hisUI.cmVMotorControlGroup.cmMotorSW[7]
      .ccSetIsActivated(myPLC.cmFillerSupplyTask.mnFRSUpplyStartPL);
    
    myPLC.cmAggregateSupplyTask.mnAGSupplyStartSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+9);
    hisUI.cmVMotorControlGroup.cmMotorSW[9]
      .ccSetIsActivated(myPLC.cmAggregateSupplyTask.mnAGSUpplyStartPL);
    
    myPLC.cmVBurnerDryerTask.mnVExfanMotorSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+12);
    hisUI.cmVMotorControlGroup.cmMotorSW[12]
      .ccSetIsActivated(myPLC.cmVBurnerDryerTask.mnVExfanMotorPL);
    
    myPLC.cmAggregateSupplyTask.mnVFeederStartSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+13);
    hisUI.cmVMotorControlGroup.cmMotorSW[13]
      .ccSetIsActivated(myPLC.cmAggregateSupplyTask.mnVFeederStartPL);
    //</editor-fold>
    
    //-- control ** v burner
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    
    //-- control ** v burner ** degree ** v burner
    myPLC.cmVBurnerDryerTask.mnVBCLSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VBCLSW);
    hisUI.cmVBurnerControlGroup.cmVBurnerCLoseSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.dcVBCLRY);
    
    myPLC.cmVBurnerDryerTask.mnVBOPSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VBOPSW);
    hisUI.cmVBurnerControlGroup.cmVBurnerOpenSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.dcVBOPRY);
    
    myPLC.cmVBurnerDryerTask.mnVBATSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VBATSW);
    hisUI.cmVBurnerControlGroup.cmVBurnerAutoSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.mnVBATPL);
    
    //-- control ** v burner ** degree ** v exf
    myPLC.cmVBurnerDryerTask.mnVEXFCLSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VEXFCLSW);
    hisUI.cmVBurnerControlGroup.cmVExfanCloseSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.dcVEFCLRY);
    
    myPLC.cmVBurnerDryerTask.mnVEXFOPSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VEXFOPSW);
    hisUI.cmVBurnerControlGroup.cmVExfanOpenSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.dcVEFOPRY);
    
    myPLC.cmVBurnerDryerTask.mnVEXFATSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VEXFATSW);
    hisUI.cmVBurnerControlGroup.cmVExfanAutoSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.mnVEXFATPL);
    
    //-- control ** v burner ** ignite
    myPLC.cmVBurnerDryerTask.mnVBIGNSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VBIGN);
    hisUI.cmVBurnerControlGroup.cmVBIgnitSW.ccSetIsActivated
      (myPLC.cmVBurnerDryerTask.mnVBIGNPL);
    hisUI.cmVBurnerControlGroup.cmVBurnerStagePL.ccSetStage
      (myPLC.cmVBurnerDryerTask.mnVBurnerIgniteStage);
    
    //</editor-fold>
    
    //-- device icons
    
    //-- device icons ** preparation
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    hisUI.cmMixerModelGroup.cmMixer.ccSetMotorStatus
      (myPLC.cmMainTask.dcMixerAN?'a':'x');
    //</editor-fold>
    
    //-- device icons ** ag supply chain
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    hisUI.cmVSupplyGroup.cmMU.ccSetMotorStatus(EcHotTower.C_I_SCREEN,
      myPLC.cmAggregateSupplyTask.dcScreenAN?'a':'x'
    );
    hisUI.cmVSupplyGroup.cmMU.ccSetMotorStatus(EcHotTower.C_I_HOTELEVATOR,
      myPLC.cmAggregateSupplyTask.dcHotElevatorAN?'a':'x'
    );
    hisUI.cmVSupplyGroup.cmVD.ccSetMotorStatus(myPLC.cmAggregateSupplyTask.dcVDryerAN?'a':'x'
    );
    hisUI.cmVSupplyGroup.cmVIBC.ccSetMotorStatus(myPLC.cmAggregateSupplyTask.dcVInclineBelconAN?'a':'x'
    );
    hisUI.cmVFeederGroup.cmVHBC.ccSetMotorStatus(myPLC.cmAggregateSupplyTask.dcVHorizontalBelconAN?'a':'x'
    );
    //</editor-fold>
    
    //-- device icons ** v feeder chain
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
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
    //</editor-fold>
    
    //-- device icons ** v bond and burner
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    //-- ** ** burner
    //-- ** ** dryer
    
    hisUI.cmVSupplyGroup.cmVIBC.ccSetHasAggregateFlow
      (myPLC.cmAggregateSupplyTask.dcCAS);
    hisUI.cmVSupplyGroup.cmVD.ccSetIsOnFire
      (myPLC.cmVBurnerDryerTask.dcMMV);
    hisUI.cmVSupplyGroup.cmVD.ccSetKPA
      (MainOperationModel.fnAdjustADValue(myPLC.cmVBurnerDryerTask.dcVSE,
        yourMOD.cmVDryerPressureADJUST
      ));
    hisUI.cmVSupplyGroup.cmVD.ccSetTPH(ceil(map(
      myPLC.cmAggregateSupplyTask.dcVFCS,
      C_GENERAL_AD_MIN,C_GENERAL_AD_MAX,
      0,yourMOD.cmVDryerCapability
    )));
    //-- ** ** bag
    hisUI.cmVSupplyGroup.cmBAG.ccSetCurrentFilterCount
      (myPLC.cmDustExtractTask.mnBagPulseCurrentCount);
    hisUI.cmVSupplyGroup.cmBAG.ccSetMotorStatus(EcBagFilter.C_M_COARSE_SCREW,
      myPLC.cmDustExtractTask.dcCoarseScrewAN?'a':'x'
    );
    hisUI.cmVSupplyGroup.cmBAG.ccSetEntranceTemprature(
      MainOperationModel.fnAdjustADValue(
        myPLC.cmVBurnerDryerTask.dcTH2, yourMOD.cmBagEntranceTempratureADJUST
      )
    );
    //-- ** ** v exf
    hisUI.cmVSupplyGroup.cmVEXF.ccSetMotorStatus
      (myPLC.cmVBurnerDryerTask.dcVExfanAN?'a':'x');
    hisUI.cmVSupplyGroup.cmVEXF.ccSetIsFull
      (myPLC.cmVBurnerDryerTask.dcVEFOPLS);
    hisUI.cmVSupplyGroup.cmVEXF.ccSetIsClosed
      (myPLC.cmVBurnerDryerTask.dcVEFCLLS);
    hisUI.cmVSupplyGroup.cmVEXF.ccSetDegree(
      MainOperationModel.fnAdjustADValue(
        myPLC.cmVBurnerDryerTask.dcVDO,yourMOD.cmVExfanDegreeADJUST
      )
    );
    //-- ** ** v burner
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
      (MainOperationModel.fnAdjustADValue(
        myPLC.cmVBurnerDryerTask.dcVBO,
        yourMOD.cmVBurnerDegreeADJUST
      ));
    hisUI.cmVSupplyGroup.cmVB.ccSetTargetTemp
      (yourMOD.cmVBurnerTargetTempraure);
    //-- ** ** v combustor
    hisUI.cmVCombustGroup.cmVFU.ccSetMotorStatus
      (myPLC.cmVBurnerDryerTask.dcFuelPumpAN?'a':'x');
    //</editor-fold>
    
    //-- device icons ** fr supply chain
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    hisUI.cmFillerSupplyGroup.cmFS.ccSetIsAirating(myPLC.cmFillerSupplyTask.dcFillerSiloAIR
    );
    hisUI.cmFillerSupplyGroup.cmFS.ccSetMotorStatus(myPLC.cmFillerSupplyTask.dcFillerSiloScrewAN?'a':'x'
    );
    hisUI.cmFillerSupplyGroup.cmFEV.ccSetMotorStatus(myPLC.cmFillerSupplyTask.dcFillerElevatorAN?'a':'x'
    );
    hisUI.cmFillerSupplyGroup.cmFBL.ccSetIsActivated(myPLC.cmFillerSupplyTask.dcFillerBinLV
    );
    hisUI.cmFillerSupplyGroup.cmFF.ccSetIsActivated(myPLC.cmFillerSupplyTask.cxFillerBinDischargeFLG
    );
    hisUI.cmFillerSupplyGroup.cmFS.ccSetSiloLevel(myPLC.cmFillerSupplyTask.dcFillerSiloHLV?'f':
      myPLC.cmFillerSupplyTask.dcFillerSiloMLV?'m':
      myPLC.cmFillerSupplyTask.dcFillerSiloLLV?'l':'e'
    );
    //</editor-fold>
    
    //-- device icons ** ap tower
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    hisUI.cmVSupplyGroup.cmMU.ccSetMotorStatus
      (EcHotTower.C_I_BLOWER, myPLC.cmVBurnerDryerTask.dcAPBlowerAN?'a':'x');
    hisUI.cmVSupplyGroup.cmMU.ccSetChuteTemrature(
      MainOperationModel.fnAdjustADValue(
        myPLC.cmVBurnerDryerTask.dcTH1,
        yourMOD.cmAggregateChuteTempratureADJUST
      )
    );
    //</editor-fold>
    
    
  }//+++
  
  //=== utility
  
  public static void ccEffect(
    PVector pxPlus, PVector pxMinus, float pxAmp
  ){
    PVector lpDif=PVector.sub(pxPlus, pxMinus);
    lpDif.mult(pxAmp);
    pxPlus.sub(lpDif);
    pxMinus.add(lpDif);
  }//+++
  
  //=== inner
  
  //<editor-fold defaultstate="collapsed" desc="%folded code%">
  //--
  //</editor-fold>
  
  
  //=== entry

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "pppmain.MainSketch" };
    if (passedArgs != null) {PApplet.main(concat(appletArgs, passedArgs));}
    else {PApplet.main(appletArgs);}
  }//+++
  
  /* ***--- wish list to kosui ---***
   * 
   * - maybe we should reimp all timer class with no model used
   * - axis dont need to draw anchor rectangle all the time
   * - tasks can have thier own static roller and pulser and flicker
   * - EcButton needs to call draw name in update
   * - we really need a multi status lamp, for now it will be stage box
   * - EcRect really needs a ccSetSize(Rect,bool,bool)
   * - and maybe EcRect needs another ccSetEndPoint(Rect,int,int)
   * 
   */

}//***eof
