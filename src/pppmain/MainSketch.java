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

import kosui.ppplocalui.VcAxis;
import kosui.ppplocalui.VcTagger;
import pppunit.EcHotTower;
import processing.event.MouseEvent;

import static pppmain.MainOperationModel.C_FEEDER_AD_MAX;
import static pppmain.MainOperationModel.C_FEEDER_RPM_MAX;
import static pppmain.MainOperationModel.C_GENERAL_AD_MAX;
import static pppmain.MainOperationModel.C_GENERAL_AD_MIN;
import pppunit.EcBagFilter;

public class MainSketch extends PApplet {
  
  static private int pbMillis=0;
  
  static private MainLocalCoordinator pbHisUI;
  static private MainLogicController pbMyPLC;
  static private MainOperationModel pbYourMOD;

  //=== overridden

  @Override public void setup() {

    //-- pre setting 
    size(800, 600);
    noSmooth();
    frame.setTitle("Plant Simulator");

    //-- initiating
    pbHisUI=new MainLocalCoordinator(this);
    pbMyPLC=new MainLogicController(this);
    pbYourMOD=new MainOperationModel();
    
    //-- setting up
    pbHisUI.cmVSupplyGroup.cmBAG
      .ccSetBagFilterSize(pbYourMOD.cmBagFilterSize);
    pbMyPLC.cmDustExtractTask
      .ccSetBagFilterSize(pbYourMOD.cmBagFilterSize);
    
    //-- post setting
    println("--done setup");
  }//+++
  
  @Override public void draw() { 

    //-- pre drawing
    pbMillis=millis();
    
    background(0);
    
    //-- links
    fsLinking();
    
    //-- updating
    pbMyPLC.ccRun();
    pbHisUI.ccUpdate();
    
    //-- system
    VcAxis.ccUpdate();
    
    //-- tagging
    VcTagger.ccTag("*----*", 0);
    VcTagger.ccTag("mouseID",pbHisUI.ccGetMouseOverID());
    
    //-- tagging ** ending
    VcTagger.ccTag("fps", nfc(frameRate,2));
    pbMillis=millis()-pbMillis;
    VcTagger.ccTag("ms/f", pbMillis);
    VcTagger.ccStabilize();

  }//+++
  
  @Override public void keyPressed() {switch(key){
    //-- triiger
    
    //-- system 
    case ',':VcAxis.ccSetAnchor(mouseX, mouseY);break;
    case '.':VcAxis.ccSetAnchor(0,0);break;
    case 'q':fsPover();break;
    default:break;
  }}//+++

  @Override public void mouseWheel(MouseEvent e){
    int lpCount=-1*e.getCount();
    pbYourMOD.ccShiftFeederRPM(pbHisUI.ccGetMouseOverID(), lpCount);
  }//+++
  
  //=== support

  private void fsPover(){
    //-- flushing
    
    //-- defualt
    println("--exiting from PApplet of DemoPlantSimulator..");
    exit();
  }//+++
  
  private boolean fsIsPressed(int pxID)
    {return mousePressed && (pbHisUI.ccGetMouseOverID()==pxID);}//+++
  
  private void fsLinking(){
    
    //-- motor switch
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    
    pbMyPLC.cmMainTask.mnVCompressorSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+0);
    pbHisUI.cmMotorSW[0]
      .ccSetIsActivated(pbMyPLC.cmMainTask.mnVCompressorPL);
    
    pbMyPLC.cmVBurnerDryerTask.mnAPBlowerSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+3);
    pbHisUI.cmMotorSW[3]
      .ccSetIsActivated(pbMyPLC.cmVBurnerDryerTask.mnAPBlowerPL);
    
    pbMyPLC.cmMainTask.mnMixerMoterSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+6);
    pbHisUI.cmMotorSW[6]
      .ccSetIsActivated(pbMyPLC.cmMainTask.mnMixerMoterPL);
    
    pbMyPLC.cmFillerSupplyTask.mnFRSupplyStartSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+7);
    pbHisUI.cmMotorSW[7]
      .ccSetIsActivated(pbMyPLC.cmFillerSupplyTask.mnFRSUpplyStartPL);
    
    pbMyPLC.cmAggregateSupplyTask.mnAGSupplyStartSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+9);
    pbHisUI.cmMotorSW[9]
      .ccSetIsActivated(pbMyPLC.cmAggregateSupplyTask.mnAGSUpplyStartPL);
    
    pbMyPLC.cmVBurnerDryerTask.mnVExfanMotorSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+12);
    pbHisUI.cmMotorSW[12]
      .ccSetIsActivated(pbMyPLC.cmVBurnerDryerTask.mnVExfanMotorPL);
    
    pbMyPLC.cmAggregateSupplyTask.mnVFeederStartSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+13);
    pbHisUI.cmMotorSW[13]
      .ccSetIsActivated(pbMyPLC.cmAggregateSupplyTask.mnVFeederStartPL);
    //</editor-fold>
    
    
    //-- device icons
    
    //-- device icons ** preparation
    pbHisUI.cmMixer.ccSetMotorStatus(
      pbMyPLC.cmMainTask.dcMixerAN?'a':'x'
    );
    
    //-- device icons ** ag supply chain
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    pbHisUI.cmVSupplyGroup.cmMU.ccSetMotorStatus(
      EcHotTower.C_I_SCREEN,
      pbMyPLC.cmAggregateSupplyTask.dcScreenAN?'a':'x'
    );
    pbHisUI.cmVSupplyGroup.cmMU.ccSetMotorStatus(
      EcHotTower.C_I_HOTELEVATOR,
      pbMyPLC.cmAggregateSupplyTask.dcHotElevatorAN?'a':'x'
    );
    pbHisUI.cmVSupplyGroup.cmVD.ccSetMotorStatus(
      pbMyPLC.cmAggregateSupplyTask.dcVDryerAN?'a':'x'
    );
    pbHisUI.cmVSupplyGroup.cmVIBC.ccSetMotorStatus(
      pbMyPLC.cmAggregateSupplyTask.dcVInclineBelconAN?'a':'x'
    );
    pbHisUI.cmVFeederGroup.cmVHBC.ccSetMotorStatus(
      pbMyPLC.cmAggregateSupplyTask.dcVHorizontalBelconAN?'a':'x'
    );
    //</editor-fold>
    
    //-- device icons ** v feeder chain
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    //-- ** ** motor
    pbHisUI.cmVFeederGroup.cmVF01.ccSetMotorStatus
      (pbMyPLC.cmAggregateSupplyTask.dcVFAN01?'a':'x');
    pbHisUI.cmVFeederGroup.cmVF02.ccSetMotorStatus
      (pbMyPLC.cmAggregateSupplyTask.dcVFAN02?'a':'x');
    pbHisUI.cmVFeederGroup.cmVF03.ccSetMotorStatus
      (pbMyPLC.cmAggregateSupplyTask.dcVFAN03?'a':'x');
    pbHisUI.cmVFeederGroup.cmVF04.ccSetMotorStatus
      (pbMyPLC.cmAggregateSupplyTask.dcVFAN04?'a':'x');
    pbHisUI.cmVFeederGroup.cmVF05.ccSetMotorStatus
      (pbMyPLC.cmAggregateSupplyTask.dcVFAN05?'a':'x');
    pbHisUI.cmVFeederGroup.cmVF06.ccSetMotorStatus
      (pbMyPLC.cmAggregateSupplyTask.dcVFAN06?'a':'x');
    //-- ** ** speed bar
    pbHisUI.cmVFeederGroup.cmVF01.ccSetRPM(pbYourMOD.cmVF01RPM);
    pbHisUI.cmVFeederGroup.cmVF02.ccSetRPM(pbYourMOD.cmVF02RPM);
    pbHisUI.cmVFeederGroup.cmVF03.ccSetRPM(pbYourMOD.cmVF03RPM);
    pbHisUI.cmVFeederGroup.cmVF04.ccSetRPM(pbYourMOD.cmVF04RPM);
    pbHisUI.cmVFeederGroup.cmVF05.ccSetRPM(pbYourMOD.cmVF05RPM);
    pbHisUI.cmVFeederGroup.cmVF06.ccSetRPM(pbYourMOD.cmVF06RPM);
    //-- ** ** stuck sensor
    pbHisUI.cmVFeederGroup.cmVF01
      .ccSetIsSending(pbMyPLC.cmAggregateSupplyTask.dcVFSG01);
    pbHisUI.cmVFeederGroup.cmVF02
      .ccSetIsSending(pbMyPLC.cmAggregateSupplyTask.dcVFSG02);
    pbHisUI.cmVFeederGroup.cmVF03
      .ccSetIsSending(pbMyPLC.cmAggregateSupplyTask.dcVFSG03);
    pbHisUI.cmVFeederGroup.cmVF04
      .ccSetIsSending(pbMyPLC.cmAggregateSupplyTask.dcVFSG04);
    pbHisUI.cmVFeederGroup.cmVF05
      .ccSetIsSending(pbMyPLC.cmAggregateSupplyTask.dcVFSG05);
    pbHisUI.cmVFeederGroup.cmVF06
      .ccSetIsSending(pbMyPLC.cmAggregateSupplyTask.dcVFSG06);
    //-- ** ** speed ad
    pbMyPLC.cmAggregateSupplyTask.dcVFSP01=
      ceil(map(pbYourMOD.cmVF01RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    pbMyPLC.cmAggregateSupplyTask.dcVFSP02=
      ceil(map(pbYourMOD.cmVF02RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    pbMyPLC.cmAggregateSupplyTask.dcVFSP03=
      ceil(map(pbYourMOD.cmVF03RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    pbMyPLC.cmAggregateSupplyTask.dcVFSP04=
      ceil(map(pbYourMOD.cmVF04RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    pbMyPLC.cmAggregateSupplyTask.dcVFSP05=
      ceil(map(pbYourMOD.cmVF05RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    pbMyPLC.cmAggregateSupplyTask.dcVFSP06=
      ceil(map(pbYourMOD.cmVF06RPM,0,C_FEEDER_RPM_MAX,0,C_FEEDER_AD_MAX));
    //</editor-fold>
    
    //-- device icons ** v bond and burner
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    //-- ** ** burner
    //-- ** ** dryer
    pbHisUI.cmVSupplyGroup.cmVIBC.ccSetHasAggregateFlow
      (pbMyPLC.cmAggregateSupplyTask.dcCAS);
    pbHisUI.cmVSupplyGroup.cmVD.ccSetTPH(ceil(map(
      pbMyPLC.cmAggregateSupplyTask.dcVFCS,
      C_GENERAL_AD_MIN,C_GENERAL_AD_MAX,
      0,pbYourMOD.cmVDryerCapability
    )));
    //-- ** ** bag
    pbHisUI.cmVSupplyGroup.cmBAG.ccSetCurrentFilterCount
      (pbMyPLC.cmDustExtractTask.mnBagPulseCurrentCount);
    pbHisUI.cmVSupplyGroup.cmBAG.ccSetMotorStatus(
      EcBagFilter.C_M_COARSE_SCREW,
      pbMyPLC.cmDustExtractTask.dcCoarseScrewAN?'a':'x'
    );
    //-- ** ** vexf
    pbHisUI.cmVSupplyGroup.cmVEXF.ccSetMotorStatus
      (pbMyPLC.cmVBurnerDryerTask.dcVExfanAN?'a':'x');
    pbHisUI.cmVSupplyGroup.cmVEXF.ccSetIsFull
      (pbMyPLC.cmVBurnerDryerTask.dcVEFOPLS);
    pbHisUI.cmVSupplyGroup.cmVEXF.ccSetIsClosed
      (pbMyPLC.cmVBurnerDryerTask.dcVEFCLLS);
    //</editor-fold>
    
    //-- device icons ** fr supply chain
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    pbHisUI.cmFillerSupplyGroup.cmFS.ccSetIsAirating(
      pbMyPLC.cmFillerSupplyTask.dcFillerSiloAIR
    );
    pbHisUI.cmFillerSupplyGroup.cmFS.ccSetMotorStatus(
      pbMyPLC.cmFillerSupplyTask.dcFillerSiloScrewAN?'a':'x'
    );
    pbHisUI.cmFillerSupplyGroup.cmFEV.ccSetMotorStatus(
      pbMyPLC.cmFillerSupplyTask.dcFillerElevatorAN?'a':'x'
    );
    pbHisUI.cmFillerSupplyGroup.cmFBL.ccSetIsActivated(
      pbMyPLC.cmFillerSupplyTask.dcFillerBinLV
    );
    pbHisUI.cmFillerSupplyGroup.cmFF.ccSetIsActivated(
      pbMyPLC.cmFillerSupplyTask.cxFillerBinDischargeFLG
    );
    pbHisUI.cmFillerSupplyGroup.cmFS.ccSetSiloLevel(
      pbMyPLC.cmFillerSupplyTask.dcFillerSiloHLV?'f':
      pbMyPLC.cmFillerSupplyTask.dcFillerSiloMLV?'m':
      pbMyPLC.cmFillerSupplyTask.dcFillerSiloLLV?'l':'e'
    );
    //</editor-fold>
    
    //-- device icons ** ap tower
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    pbHisUI.cmVSupplyGroup.cmMU.ccSetMotorStatus
      (EcHotTower.C_I_BLOWER, pbMyPLC.cmVBurnerDryerTask.dcAPBlowerAN?'a':'x');
    //</editor-fold>
    
    //-- how knows 
    pbHisUI.cmMixer.ccSetHasMixture(true);
    
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
