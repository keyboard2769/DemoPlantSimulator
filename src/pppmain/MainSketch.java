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

import javax.swing.SwingUtilities;
import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.core.PVector;

import kosui.ppplocalui.VcAxis;
import kosui.ppplocalui.VcConsole;
import kosui.ppplocalui.VcTagger;

public class MainSketch extends PApplet {
  
  public static final int C_C_BACKGROUD=0xFF113311;
  
  //=== selfie
  
  private static MainSketch self;
  private static int cmMillis=0;
  private static int cmRoller=0;
  
  //=== reference
  
  public static MainLocalCoordinator hisUI;
  public static MainSwingCoordinator herFrame;
  public static MainRunnerManager herManager;
  public static MainLogicController myPLC;
  public static MainOperationModel yourMOD;

  //=== overridden

  @Override public void setup() {

    //-- pre setting 
    size(800, 600);
    noSmooth();
    frame.setTitle("Plant Simulator");
    self=this;

    //-- initiating
    hisUI=MainLocalCoordinator.ccGetReference();
    myPLC=MainLogicController.ccGetReference();
    yourMOD=MainOperationModel.ccGetReference();
    TabWireManager.ccInit();
    //-- initatating ** configuring
    VcAxis.ccFlip();
    VcTagger.ccSetRow(12);
    
    //-- setting up
    hisUI.cmVSupplyGroup.cmBAG
      .ccSetBagFilterSize(yourMOD.cmBagFilterSize);
    myPLC.cmDustExtractTask
      .ccSetBagFilterSize(yourMOD.cmBagFilterSize);
    
    //-- swing
    herManager=MainRunnerManager.ccGetReference();
    SwingUtilities.invokeLater(herManager.cmSetupRunner);
    
    //-- test 
    
    //-- post setting
    println("-- DemoPlantSimulator:setup done.");
  }//+++
  
  @Override public void draw() {

    //-- pre drawing
    cmRoller++;cmRoller&=0x0F;
    cmMillis=millis();
    background(C_C_BACKGROUD);
    
    //-- wiring
    TabWireManager.ccUpdate();
    
    //-- updating
    hisUI.ccUpdate();
    myPLC.ccRun();
    if(fnHalfSecondPLC()){
      SwingUtilities.invokeLater(herManager.cmUpdateRunner);
    }//..?
    
    //-- system
    VcConsole.ccUpdate();
    VcAxis.ccUpdate();
    
    //-- tagging
    VcTagger.ccTag("*--*", 0);
    VcTagger.ccTag("*--*", 0);
    VcTagger.ccTag("==system==");
    VcTagger.ccTag("inputID",hisUI.ccGetInpuFocusID());
    
    VcTagger.ccTag("mouseID",hisUI.ccGetMouseOverID());
    
    //-- tagging ** ending
    VcTagger.ccTag("fps", Float.toString(frameRate));
    cmMillis=millis()-cmMillis;
    VcTagger.ccTag("ms/f", cmMillis);
    VcTagger.ccStabilize();

  }//+++
  
  @Override public void keyPressed() {
    if(VcConsole.ccKeyTyped(key, keyCode)){return;}
    switch(key){
      
      //-- test

      case '1':
      break;

      case 'f':
      break;
      
      case 's':
        myPLC.cmAutoWeighTask.testReadUpRecipe();
      break;
      
      //-- direct inputting
      
      case 0x09:
        hisUI.ccChangeCurrentInputIndex();
      break;
      
      case ' ':
        hisUI.ccClearCurrentInputFocus();
      break;

      //-- trigger
      case 'n':VcTagger.ccFlip();break;
      case 'm':VcAxis.ccFlip();break;

      //-- system 
      case ',':VcAxis.ccSetAnchor(mouseX, mouseY);break;
      case '.':VcAxis.ccSetAnchor(0,0);break;
      case 'q':fsPover();break;
      default:break;
    }//..?
  }//+++

  @Override public void mouseClicked(){
    switch(hisUI.ccGetMouseOverID()){
      
      case MainLocalCoordinator.C_ID_SYSTEM:
        SwingUtilities.invokeLater(herManager.cmShowOperateWindow);
      break;
      
    }//..?
  }//+++
  
  @Override public void mouseWheel(MouseEvent e){
    int lpCount=-1*e.getCount();
    if(yourMOD.fsShfitVBurnerTargetTemp(hisUI.ccGetMouseOverID(), lpCount))
      {return;}
    if(yourMOD.fsShiftFeederRPM(hisUI.ccGetMouseOverID(), lpCount))
      {return;}
    lpCount=0;
  }//+++
  
  //=== support

  void fsPover(){
    //-- flushing
    
    //-- defualt
    println("-- DemoPlantSimulator:exit done.");
    exit();
  }//+++
  
  //=== utility
  
  
  //=== utility ** dynamic
  
  boolean fnIsPressed(int pxID)
    {return mousePressed && (hisUI.ccGetMouseOverID()==pxID);}//+++
  
  boolean fnIsPressed(char pxKey)
    {return keyPressed && (key==pxKey);}//+++
  
  //=== utility ** static
  
  public static final boolean fnHalfSecondPLC(){
    return (cmRoller%7)==1;
  }//+++
  
  public static final boolean fnFullSecondPLS(){
    return cmRoller==14;
  }//+++
  
  public static final boolean fnFullSecondFLK(){
    return cmRoller<=7;
  }//+++
  
  public static final void fnEffect(
    PVector pxPlus, PVector pxMinus, float pxAmp
  ){
    PVector lpDif=PVector.sub(pxPlus, pxMinus);
    lpDif.mult(pxAmp);
    pxPlus.sub(lpDif);
    pxMinus.add(lpDif);
  }//+++
  
  //=== inner
  
  //=== entry
  
  synchronized public static MainSketch ccGetReference(){return self;}

  static public void main(String[] passedArgs) {
    
    //-- launch processing
    String[] appletArgs = new String[] { "pppmain.MainSketch" };
    if (passedArgs != null) {PApplet.main(concat(appletArgs, passedArgs));}
    else {PApplet.main(appletArgs);}
    
  }//+++
  
  /* ***--- wish list to kosui ---***
   * 
   * - maybe we should reimp all timer class with no model used
   *    : use span and judge of int
   *    : add ccSetTime() method
   *    : we actually need some more flicker stepper classes
   * - axis dont need to draw anchor rectangle all the time
   * - tasks can have thier own static roller and pulser and flicker
   * - EcButton needs to call draw name in update
   * - we really need a multi status lamp, for now it will be stage box
   * - EcRect really needs a ccSetSize(Rect,bool,bool)
   * - EcRect can have a ccSetSize(null, offset, offset) to do self adjust
   * - EcRect can have a ccSetLocation(null, offset, offset) to do self adjust
   * - and maybe EcRect needs another ccSetEndPoint(Rect,int,int)
   * - VcTagger may need a ccSetRow() method
   * - i want edit folder xml line to be somewhere
   * - we really need text box to have text align
   * - constan C_IGNORE_ID should be changed to C_ID_IGNORE
   * - maybe ZcRangedValueModel need some name refactoring
   *    : value -> current ? 
   *    : span -> range ? 
   * - ZcRangedValueModel can have a float:ccGetPercentage() method
   * - maybe fnEffect(PV,PV,float) should be in VcConst
   * - my swing titled window may need a ccInit(String, Color)
   * - ScFactory may need a ccMyComboBox(Strign[], String, Listener) method
   * - ScTable may need a ccGetSelectedRowIndex(), and a ccRefresh()
   *     : for some reason repaint seems cant update the content
   *     ; you need another "ccUpdateTable()" with updatUI()
   * - ZcStepper may have a test method to tell current stage
   * - ccSetLocation() of EcPoint should be able to set to zero!(but not minus)
   * - change "mouseID" to "mouseFocus", and "inputFocus" is misspelled
   * - VcTagger and VcAxis can have the flip thing changed to Visible thing
   * - i want every factory method to be like "ccMy%whatever%%class%"
   * - maybe swing rapper classes of kosui dont need a "cmSelf" ?
   * - it canbe good if watch bar of VcConsoles can shift its position
   * - take some check on how console adjust text location
   * - for convention reason, maybe "take" should be "setup"??
   * - time stamp from vcconst should have more mode to eimit 
   * - ecrect can have bunch of static "auto layout" function
   *     : one instance is the classic "DUO" 5*3 push button layout
   * - vcConsole should be able to accept empty input!!
   * - input focus change method of base coordinator need a more readble name
   *
   *
   * 
   */

}//***eof
