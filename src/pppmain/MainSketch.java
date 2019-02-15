/* * DEMO of PLANT SIMULATOR
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
import processing.core.PVector;
import processing.event.MouseEvent;
import kosui.ppplocalui.VcAxis;
import kosui.ppplocalui.VcConsole;
import kosui.ppplocalui.VcTagger;
import ppptable.McErrorMessageFolder;

public class MainSketch extends PApplet {
  
  public static final int C_C_BACKGROUD=0xFF336633;
  public static final String C_V_PWD
    = System.getProperty("user.dir");
  
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

    //-- single
    hisUI=MainLocalCoordinator.ccGetReference();
    herManager=MainRunnerManager.ccGetReference();
    myPLC=MainLogicController.ccGetReference();
    yourMOD=MainOperationModel.ccGetReference();
    
    //-- static
    TabWireManager.ccInit();
    VcAxis.ccSetIsEnabled();
    VcTagger.ccSetRow(12);
    
    //-- setting up
    TabWireManager.ccSetup();
    SwingUtilities.invokeLater(herManager.cmSetupRunner);
    
    //-- post setting
    println("pppmain.MainSketch.setup()::done");
    
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
      McErrorMessageFolder.ccGetReference().ccUpdate();
    }//..?
    
    //-- system
    VcConsole.ccUpdate();
    VcAxis.ccUpdate();
    
    //-- tagging
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
      
      //-- booking pane
      case MainLocalCoordinator.C_ID_WEIGH_MANN:
        //[TOOD]::when not auto, swing table view should clear its self
        yourMOD.fsClearCurrentAutoWeighTargetValue();
      break;
      //--
      case MainLocalCoordinator.C_ID_WEIGH_AUTO:
        yourMOD.fsApplyCurrentAutoWeighRecipe();
      break;
      
      //-- zero pane
      case MainLocalCoordinator.C_ID_ZERO_APP:
        yourMOD.ccAdjustZERO(
          hisUI.cmZeroAdjustControlGroup.cmAGZero.ccIsActivated(),
          hisUI.cmZeroAdjustControlGroup.cmFRZero.ccIsActivated(),
          hisUI.cmZeroAdjustControlGroup.cmASZero.ccIsActivated()
        );
        hisUI.cmZeroAdjustControlGroup.cmAGZero.ccSetIsActivated(false);
        hisUI.cmZeroAdjustControlGroup.cmFRZero.ccSetIsActivated(false);
        hisUI.cmZeroAdjustControlGroup.cmASZero.ccSetIsActivated(false);
      break;
      //--
      case MainLocalCoordinator.C_ID_ZERO_AG:
        hisUI.cmZeroAdjustControlGroup.cmAGZero.ccSetIsActivated();
      break;
      //--
      case MainLocalCoordinator.C_ID_ZERO_FR:
        hisUI.cmZeroAdjustControlGroup.cmFRZero.ccSetIsActivated();
      break;
      //--
      case MainLocalCoordinator.C_ID_ZERO_AS:
        hisUI.cmZeroAdjustControlGroup.cmASZero.ccSetIsActivated();
      break;
      
      //-- system
      case MainLocalCoordinator.C_ID_SYSTEM:
        SwingUtilities.invokeLater(new Runnable() {
          @Override 
          public void run(){herFrame.cmOperateWindow.setVisible(true);}
        });
      break;
      
    }//..?
  }//+++
  
  @Override public void mouseWheel(MouseEvent e){
    int lpCount=-1*e.getCount();
    if(
      yourMOD.fsShfitVBurnerTargetTemp(hisUI.ccGetMouseOverID(), lpCount)
    ){return;}
    if(
      yourMOD.ccShiftVFeederRPM(hisUI.ccGetMouseOverID(), lpCount)
    ){return;}
    lpCount=0;
  }//+++
  
  //=== support

  void fsPover(){
    //-- flushing
    
    //-- defualt
    println("pppmain.MainSketch.fsPover()::exit");
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
  
  //[NOTYET]:: should be part of kosui.utility.const?? <- no, i dont think so.
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
  
  synchronized public static MainSketch ccGetReference(){return self;}//+++

  static public void main(String[] passedArgs) {
    
    //-- ???
    ppptable.McTranslator.ccGetReference().ccInit();
    
    //-- launch processing
    String[] appletArgs = new String[] { "pppmain.MainSketch" };
    if (passedArgs != null) {PApplet.main(concat(appletArgs, passedArgs));}
    else {PApplet.main(appletArgs);}
    
  }//!!!
  
  /* ***--- wish list to kosui ---***

   - we need to find a way to adjust text location for different font 
   - EcElement should be able to tell the key and name and text
   - we need to replace all `back slash N` with the newline constant 
     > as well as the ScConsole is the biggest problem
   - ???
   - ???
   - ???

   */
  
}//***eof
