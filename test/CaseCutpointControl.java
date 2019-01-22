/* *
 * CaseCutpointControl
 *
 * add describs here
 *
 * (for Processing 2.x core )
 */

//package ;

import kosui.ppplocalui.EcBaseCoordinator;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcGauge;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcTextBox;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.VcTagger;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppplogic.ZcStepper;
import kosui.ppplogic.ZiTask;
import ppptask.ZcCylinderGateModel;
import ppptask.ZcCylinderPointerController;
import pppunit.EcUnitFactory;
import processing.core.*; 

public class CaseCutpointControl extends PApplet {
  
  static volatile int pbRoller;
  
  //=== overridden
  
  int cmCutpoint;
  
  ShowcaseUI hisui;
  ShowcasePLC myplc;
  
  @Override public void setup() {
    
    //pre setting 
    size(320, 240);
    noSmooth();
    EcFactory.ccInit(this);
    
    //-- initiating
    
    cmCutpoint=200;
    
    hisui=new ShowcaseUI();
    myplc=new ShowcasePLC();
    
    //--
    
    //--post setting
    
  }//+++
  
  @Override public void draw(){
    
    //-- pre draw
    background(0);
    pbRoller++;pbRoller&=0x0F;
    
    //-- local loop
    fsLogic();
    hisui.ccUpdate();
    myplc.ccScan();
    myplc.ccSimulate();
    
    //-- system loop
    VcTagger.ccTag("roll",pbRoller);
    VcTagger.ccStabilize();
    
  }//+++
  
  @Override public void keyPressed(){
    switch(key){
      
      //--
      case 'w':cmCutpoint+=cmCutpoint<1000?100:0;break;
      case 's':cmCutpoint-=cmCutpoint>0?100:0;break;
      
      //--
      case 'q':fsPover();
      default:break;
    }//..?
  }//+++
  
  //=== operate
  
  void fsPover(){
    
    //-- flushsing or closign
    
    //-- post exit
    println("::exit from main sketch.");
    exit();
  }//+++
  
  //=== utility
  
  boolean fnIsPressed(char pxKey){return keyPressed && (key==pxKey);}//+++
  
  void fsLogic(){
    
    //-- pc->plc
    
    int lpTarget=1000;
    
    myplc.mnWeighSW=fnIsPressed('f');
    myplc.mnDischargeSW=fnIsPressed('d');
    myplc.mnTargetAD=lpTarget;
    myplc.mnCutpointAD=lpTarget-cmCutpoint;
    
    //-- ><
    
    //-- mod->plc
    hisui.eeCut.ccSetText(nf(cmCutpoint,4));
    
    //-- plc->pc
    hisui.eeCLMV.ccSetIsActivated(myplc.dcCLMV);
    hisui.eeOPMV.ccSetIsActivated(myplc.dcOPMV);
    hisui.eeMAS.ccSetIsActivated(myplc.dcMAS);
    hisui.eeGate.ccSetPercentage(myplc.simGate.ccGetValue()*6);
    hisui.eeTarget.ccSetValue(myplc.mnTargetAD);
    hisui.eeCell.ccSetValue(myplc.dcCellAD);
    hisui.eeCharge.ccSetIsActivated(myplc.mnWeighSW);
    hisui.eeDischarge.ccSetIsActivated(myplc.dcDDD);
    hisui.eeDummyGauge.ccSetBaseColor(
      myplc.cmDone?EcFactory.C_GREEN:
      myplc.cmCut?EcFactory.C_WATER:
      EcFactory.C_DIM_YELLOW
    );
    
  }//+++
  
  class ShowcaseUI extends EcBaseCoordinator{
    
    EcElement 
      eeCharge,
      eeOPMV,eeCLMV,eeMAS,
      eeDischarge
    ;
    EcGauge eeGate;
    EcValueBox eeTarget,eeCell;
    EcTextBox eeCut;
    
    EcShape eeDummyGauge;
    
    ShowcaseUI(){
      
      //-- construction
      eeOPMV=EcFactory.ccCreateTextPL(">>");
      eeCLMV=EcFactory.ccCreateTextPL("<<");
      eeMAS=EcFactory.ccCreateTextPL("[]");
      eeCharge=EcFactory.ccCreateTextPL("<F>");
      eeDischarge=EcFactory.ccCreateTextPL("<D>");
      eeGate=EcFactory.ccCreateGauge("g", true, false, 100, 16);
      eeGate.ccSetSize(eeOPMV, false, true);
      eeCell=EcUnitFactory.ccCreateDegreeValueBox("-0000&", "&");
      eeCell.ccSetValue(0, 4);
      eeTarget=EcUnitFactory.ccCreateSettingValueBox("-0000&", "&");
      eeTarget.ccSetValue(0, 4);
      eeCut=EcFactory.ccCreateBox("0000");
      eeCut.ccSetSize();
      eeDummyGauge=new EcShape();
      eeDummyGauge.ccSetSize(8, eeTarget.ccGetH()*2+2);
      eeDischarge.ccSetSize(eeCell);
      
      //-- relocating
      eeCLMV.ccSetLocation(100, 100);
      eeGate.ccSetLocation(eeCLMV, 2, 0);
      eeOPMV.ccSetLocation(eeGate, 2, 0);
      eeMAS.ccSetLocation(eeGate.ccCenterX(), eeGate.ccGetY()-eeMAS.ccGetH()-2);
      eeTarget.ccSetLocation(eeGate, 0, 20);
      eeCell.ccSetLocation(eeTarget, 0, 2);
      eeDummyGauge.ccSetLocation(eeTarget,2, 0);
      eeDischarge.ccSetLocation(eeCell, 0, 8);
      eeCharge.ccSetLocation(eeDischarge.ccGetX(), 50);
      eeCut.ccSetLocation(eeTarget, 20, 0);
      
      //-- tagging
      eeCharge.ccSetName("charge");eeCharge.ccSetNameAlign('a');
      eeDischarge.ccSetName("discharge");eeDischarge.ccSetNameAlign('b');
      
      eeCLMV.ccSetName("close");eeCLMV.ccSetNameAlign('l');
      eeMAS.ccSetName("auto-switch");eeMAS.ccSetNameAlign('r');
      eeOPMV.ccSetName("open");eeOPMV.ccSetNameAlign('r');
      
      eeTarget.ccSetName("target");eeTarget.ccSetNameAlign('l');
      eeCell.ccSetName("cell");eeCell.ccSetNameAlign('l');
      eeCut.ccSetName("cut-point");eeCut.ccSetNameAlign('r');
      
      //-- packing
      ccAddElement(eeOPMV);
      ccAddElement(eeCLMV);
      ccAddElement(eeMAS);
      ccAddElement(eeGate);
      ccAddElement(eeCell);
      ccAddElement(eeTarget);
      ccAddElement(eeCharge);
      ccAddElement(eeDischarge);
      ccAddShape(eeDummyGauge);
      ccAddElement(eeCut);
      
    }//++!
    
  }//***
  
  class ShowcasePLC implements ZiTask{
    
    boolean mnWeighSW,mnDischargeSW,
      dcOPMV, dcCLMV, dcMAS,
      //--
      cmCut,cmDone,
      //--
      dcDDD
    ;//...
    
    int
      mnTargetAD, mnCutpointAD,
      dcCellAD;
    
    ZcCylinderPointerController cmWCutter = new ZcCylinderPointerController();
    
    @Override public void ccScan(){
      
      //--
      cmCut=dcCellAD>mnCutpointAD;
      cmDone=dcCellAD>mnTargetAD;
      
      boolean lpWeigh=(dcCellAD<mnTargetAD)&&mnWeighSW;
      
      //--
      cmWCutter.ccTakeControlBit(lpWeigh, cmCut, dcMAS);
      cmWCutter.ccRun();
      
      //--
      dcOPMV=cmWCutter.ccGetOpenSignal();
      dcCLMV=cmWCutter.ccGetCloseSignal();
      dcDDD=mnDischargeSW;
    
    }//+++
    
    ZcCylinderGateModel simGate=new ZcCylinderGateModel();
    ZcRangedValueModel simCell=new ZcRangedValueModel(400, 3600);
    
    @Override public void ccSimulate(){
      
      //--
      simGate.ccOpen(dcOPMV, 1);
      simGate.ccClose(dcCLMV, 1);
      dcMAS=simGate.ccIsAtMiddleAS();
      
      //--
      if(simGate.ccGetValue()>2){
        simCell.ccShift(simGate.ccGetValue()/4);
      }//..?
      if(dcDDD){
        simCell.ccShift(-8);
      }//..?
      dcCellAD=simCell.ccGetValue();
    
    }//+++
    
  }//***
  
  //=== inner
  
  //=== entry
  
  static public void main(String[] passedArgs){
    PApplet.main(CaseCutpointControl.class.getCanonicalName());
  }//+++
  
}//***eof
