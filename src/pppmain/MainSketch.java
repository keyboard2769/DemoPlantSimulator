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

import processing.core.*;

import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.VcAxis;
import kosui.ppplocalui.VcTagger;

public class MainSketch extends PApplet {
  
  static public volatile int pbRoller=0;
  static private int pbMillis=0;
  
  static private MainLocalCoordinator pbHisCoordinator;

  //=== overridden

  @Override public void setup() {

    //-- pre setting 
    size(800, 600);
    noSmooth();

    //-- inistiating
    EcFactory.ccInit(this);
  
    //-- constructing
    pbHisCoordinator=new MainLocalCoordinator();
    
    //-- post setting
    println("--done setup");
  }//+++
  
  @Override public void draw() { 

    //-- pre drawing
    pbMillis=millis();
    
    background(0);
    pbRoller++;pbRoller&=0x0F;
    
    //-- link and test
    boolean lpFlick=pbRoller<7;
    int lpTestValue=ceil(map(mouseX,0,width,0,900));
    if(lpFlick){
      pbHisCoordinator.cmMixer.ccSetMotorStatus('a');
      pbHisCoordinator.cmVFeederGroup.cmVHBC.ccSetMotorStatus('a');
    }else{
      pbHisCoordinator.cmMixer.ccSetMotorStatus('l');
      pbHisCoordinator.cmVFeederGroup.cmVHBC.ccSetMotorStatus('l');
    }
    
    pbHisCoordinator.cmVFeederGroup.cmVF05.ccSetRPM(lpTestValue);
    pbHisCoordinator.cmVFeederGroup.cmVF05
      .ccSetIsStucked(lpTestValue<200);
    
    
    pbHisCoordinator.cmVFeederGroup.cmVHBC.ccSetIsEMSPulled(lpFlick);
    
    pbHisCoordinator.cmMixer.ccSetHasMixture(lpFlick);
    pbHisCoordinator.cmMixer.ccSetIsGateOpening(lpFlick);
    pbHisCoordinator.cmMixer.ccSetIsGateOpened(lpFlick);
    pbHisCoordinator.cmMixer.ccSetIsGateClosed(!lpFlick);
    
    
    //-- updating
    pbHisCoordinator.ccUpdate();
    
    //-- system
    VcAxis.ccUpdate();
    
    //-- tagging
    VcTagger.ccTag("*----*", 0);
    VcTagger.ccTag("mouseID",pbHisCoordinator.ccGetMouseOverID());
    VcTagger.ccTag("roller", pbRoller);
    
    //-- tagging ** ending
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
  
  //=== operate

  void fsPover(){
    //-- flushing
    
    //-- defualt
    println("--exiting from PApplet of DemoPlantSimulator..");
    exit();
  }//+++
  
  //=== utiliry

  //=== inner
  
  //class EcCobHopper {}//***
  
  
  //class EcColdElevator {}//***
  //class EcRSurgeBin　{}//***
  //class EcRExhaustFan　{}//***
  
  //class EcOnePathSkip　{}//***
  //class EcMixtureSilo　{}//***
  
      //[DTFM]::
      //pbOwner.fill(0xFF663333);
      //pbOwner.rect(cmX, cmY, cmW, cmH);

  //=== entry

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "pppmain.MainSketch" };
    if (passedArgs != null) {PApplet.main(concat(appletArgs, passedArgs));}
    else {PApplet.main(appletArgs);}
  }//+++

}//***eof
