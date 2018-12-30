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

import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.VcAxis;
import kosui.ppplocalui.VcTagger;
import pppunit.EcHotTower;

public class MainSketch extends PApplet {
  
  static private int pbMillis=0;
  
  static private MainLocalCoordinator pbHisCoordinator;
  static private MainLogicController pbMyPLC;

  //=== overridden

  @Override public void setup() {

    //-- pre setting 
    size(800, 600);
    noSmooth();

    //-- inistiating
    EcFactory.ccInit(this);
    frame.setTitle("Plant Simulator");
  
    //-- constructing
    pbHisCoordinator=new MainLocalCoordinator();
    pbMyPLC=new MainLogicController();
    
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
    pbHisCoordinator.ccUpdate();
    
    //-- system
    VcAxis.ccUpdate();
    
    //-- tagging
    VcTagger.ccTag("*----*", 0);
    VcTagger.ccTag("mouseID",pbHisCoordinator.ccGetMouseOverID());
    
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
  
  //=== support

  private void fsPover(){
    //-- flushing
    
    //-- defualt
    println("--exiting from PApplet of DemoPlantSimulator..");
    exit();
  }//+++
  
  private boolean fsIsPressed(int pxID)
    {return mousePressed && (pbHisCoordinator.ccGetMouseOverID()==pxID);}//+++
  
  private void fsLinking(){
    
    //-- motor switch
    pbMyPLC.cmAggregateSupplyTask.mnAGSupplyStartSW=
      fsIsPressed(MainLocalCoordinator.C_ID_VMSW_HEAD+9);
    pbHisCoordinator.cmMotorSW[9]
      .ccSetIsActivated(pbMyPLC.cmAggregateSupplyTask.mnAGSUpplyStartPL);
    
    //-- device icons
    //-- device icons ** ag supply chain
    pbHisCoordinator.cmVSupplyGroup.cmMU.ccSetMotorStatus(
      EcHotTower.C_I_SCREEN,
      pbMyPLC.cmAggregateSupplyTask.dcScreenAN?'a':'x'
    );
    pbHisCoordinator.cmVSupplyGroup.cmMU.ccSetMotorStatus(
      EcHotTower.C_I_HOTELEVATOR,
      pbMyPLC.cmAggregateSupplyTask.dcHotElevatorAN?'a':'x'
    );
    pbHisCoordinator.cmVSupplyGroup.cmVD.ccSetMotorStatus(
      pbMyPLC.cmAggregateSupplyTask.dcVDryerAN?'a':'x'
    );
    pbHisCoordinator.cmVSupplyGroup.cmVIBC.ccSetMotorStatus(
      pbMyPLC.cmAggregateSupplyTask.dcVInclineBelconAN?'a':'x'
    );
    pbHisCoordinator.cmVFeederGroup.cmVHBC.ccSetMotorStatus(
      pbMyPLC.cmAggregateSupplyTask.dcVHorizontalBelconAN?'a':'x'
    );
    
    
    //-- how knows 
    pbHisCoordinator.cmMixer.ccSetHasMixture(true);
    
  }//+++
  

  //=== inner
  
  //=== entry

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "pppmain.MainSketch" };
    if (passedArgs != null) {PApplet.main(concat(appletArgs, passedArgs));}
    else {PApplet.main(appletArgs);}
  }//+++

}//***eof
