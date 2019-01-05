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

public class MainSketch extends PApplet {
  
  static private int pbMillis=0;
  
  static MainLocalCoordinator hisUI;
  //static private MainSwingCoordinator herFrame;
  static MainLogicController myPLC;
  static MainOperationModel yourMOD;

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
    VcTagger.ccSetRow(12);
    //-- initatating ** wire manager
    TabWireManager.ccInit(this);
    
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
    
    //-- wiring
    TabWireManager.ccUpdate();
    
    //-- updating
    myPLC.ccRun();
    hisUI.ccUpdate();
    
    //-- system
    VcAxis.ccUpdate();
    
    //-- tagging
    VcTagger.ccTag("*-bin-*", myPLC.cmFillerSupplyTask.ccGetContent(1));
    VcTagger.ccTag("*-silo-*", myPLC.cmFillerSupplyTask.ccGetContent(2));
    VcTagger.ccTag("*--*", 0);
    VcTagger.ccTag("*--*", 0);
    VcTagger.ccTag("*--*", 0);
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

  void fsPover(){
    //-- flushing
    
    //-- defualt
    println("-- DemoPlantSimulator:exit done.");
    exit();
  }//+++
  
  //=== utility
  
  boolean fnIsPressed(int pxID)
    {return mousePressed && (hisUI.ccGetMouseOverID()==pxID);}//+++
  
  boolean fnIsPressed(char pxKey)
    {return keyPressed && (key==pxKey);}//+++
  
  public static void fnEffect(
    PVector pxPlus, PVector pxMinus, float pxAmp
  ){
    PVector lpDif=PVector.sub(pxPlus, pxMinus);
    lpDif.mult(pxAmp);
    pxPlus.sub(lpDif);
    pxMinus.add(lpDif);
  }//+++
  
  //=== inner
  
  //=== entry

  static public void main(String[] passedArgs) {
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
   * - and maybe EcRect needs another ccSetEndPoint(Rect,int,int)
   * - VcTagger may need a ccSetRow() method
   * - i want edit folder xml line to be somewhere
   *
   *
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
