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

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.core.PVector;

import kosui.ppplocalui.VcAxis;
import kosui.ppplocalui.VcConsole;
import kosui.ppplocalui.VcTagger;
import kosui.pppswingui.ScFactory;

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
    VcAxis.ccSetIsEnabled();
    VcTagger.ccSetRow(12);
    
    //-- setting up
    myPLC.cmDustExtractTask
      .ccSetBagFilterSize(yourMOD.cmBagFilterSize);
    
    //-- swing
    herManager=MainRunnerManager.ccGetReference();
    SwingUtilities.invokeLater(herManager.cmSetupRunner);
    
    //-- wire
    TabWireManager.ccSetup();
    
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
    VcTagger.ccTag("inputID",hisUI.ccGetInputFocusID());
    
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
        hisUI.ccToNextInputIndex();
      break;
      
      case ' ':
        hisUI.ccClearCurrentInputFocus();
      break;

      //-- trigger
      case 'n':VcTagger.ccSetIsVisible();break;
      case 'm':VcAxis.ccSetIsEnabled();break;

      //-- system 
      case ',':VcAxis.ccSetAnchor(mouseX, mouseY);break;
      case '.':VcAxis.ccSetAnchor(0,0);break;
      case 'q':fsPover();break;
      default:break;
    }//..?
  }//+++

  @Override public void mouseClicked(){
    switch(hisUI.ccGetMouseOverID()){
      
      case MainLocalCoordinator.C_ID_WEIGH_MANN:
        yourMOD.ccClearCurrentTarget();
      break;
      
      case MainLocalCoordinator.C_ID_WEIGH_AUTO:
        yourMOD.ccApplyCurrentRecipe();
      break;
      
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
  
  //[TODO]:: should be part of kosui.utility.const??
  public static final void fnEffect(
    PVector pxPlus, PVector pxMinus, float pxAmp
  ){
    PVector lpDif=PVector.sub(pxPlus, pxMinus);
    lpDif.mult(pxAmp);
    pxPlus.sub(lpDif);
    pxMinus.add(lpDif);
  }//+++
  
  //[TODO]:: should be part of kosui.swing.factory??
  public static final void fnScrollToLast(JScrollPane pxTarget){
    if(ScFactory.ccIsEDT()){
      int lpMax=pxTarget.getVerticalScrollBar().getModel().getMaximum();
      pxTarget.getVerticalScrollBar().getModel().setValue(lpMax);
    }//..?
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
  
  /* ***--- temp code ---***
  
    //[TODO]:: move to library
    synchronized public static int ccParseIntegerString(String pxSource){

      //-- pre judge
      if(!VcConst.ccIsValidString(pxSource)){return 0;}

      //-- judge input ** pxSource
      boolean lpIsFloat=VcConst.ccIsFloatString(pxSource);
      boolean lpIsInteger=VcConst.ccIsIntegerString(pxSource);
      if(!lpIsFloat && !lpIsInteger){return 0;}

      //-- transform
      int lpRes=0;
      if(lpIsFloat){
        lpRes=(int)(Float.parseFloat(pxSource));
      }//..?
      if(lpIsInteger){
        lpRes=Integer.parseInt(pxSource);
      }//..?
      return lpRes;

    }//+++

    //[TODO]:: move to library
    synchronized public static float ccParseFloatString(String pxSource){

      //-- pre judge
      if(!VcConst.ccIsValidString(pxSource)){return 0.0f;}

      //-- judge input ** pxSource
      boolean lpIsFloat=VcConst.ccIsFloatString(pxSource);
      boolean lpIsInteger=VcConst.ccIsIntegerString(pxSource);
      if(!lpIsFloat && !lpIsInteger){return 0.0f;}

      //-- transform
      float lpRes=0.0f;
      if(lpIsFloat){
        lpRes=Float.parseFloat(pxSource);
      }//..?
      if(lpIsInteger){
        lpRes=(float)(Integer.parseInt(pxSource));
      }//..?
      return lpRes;

    }//+++
  
    //[TODO]::to ScTitledWindow
    cmTitle.addMouseListener(new MouseAdapter() {
      @Override public void mouseReleased(MouseEvent pxE){
        cmAnchorX=0;
        cmAnchorY=0;
      }//+++
    });
    cmTitle.addMouseMotionListener(new MouseMotionAdapter() {
      @Override public void mouseDragged(MouseEvent e) {
        if(cmAnchorX==0 && cmAnchorY==0){
          cmAnchorX=e.getXOnScreen()-lpWindow.getLocationOnScreen().x;
          cmAnchorY=e.getYOnScreen()-lpWindow.getLocationOnScreen().y;
        }//..?
        lpWindow.setLocation(
          e.getXOnScreen()-cmAnchorX, 
          e.getYOnScreen()-cmAnchorY
        );
      }//+++
    });

   */
  
  /* ***--- wish list to kosui ---***
   * 
   * - ScList may need a setSelectedIndex(0) wrapper
   * - ScList may rename ccRefreshModel() to ccSetModel()
   * - both ScTable and ScList may need a ccScrollToLast()
   *     : or maybe ScFactory?? like ccScrollToLast(JScrollPane)
   * - VcConst might need a ccParseFloatString()
   *     : and ccParseIntegerString need a refactoring
   *     : and ccIsFloatString need to accept less-than-one values
   *       `"^[0][.][0-9]{0,3}$"`
   * - maybe VcConst also can have a `ccParseBoolString()`
   *     which returns true on "true" or "1" or "ON" or whatever.
   * - ScTitleWindow can have a better drag implementation
   *     : we dont need `cmCenterY`
   * - EcFactory may have a `+ EcElement ccCreateTextLamp()`
   * - EcRect has its `ccSetSize(EcRect, bool ,bool)` missed in H
   *
   *
   *
   *
   *
   *
   *
   *
   * 
   */

}//***eof
