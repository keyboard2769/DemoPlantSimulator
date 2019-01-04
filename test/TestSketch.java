/* *
 * TestSketch
 *
 * add describs here
 *
 * (for Processing 2.x core )
 */

//package ;

import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.VcAxis;
import kosui.ppplocalui.VcTagger;
import ppptask.ZcPIDController;
import pppunit.EcOnePathSkip;
import processing.core.*; 

public class TestSketch extends PApplet {
    
  static volatile int pbRoller=0;
  static volatile int pbMillis=0;
  
  //=== overridden
  
  ZcPIDController pid=new ZcPIDController(160, 0.5f, 0.02f);
  
  @Override public void setup() {
    
    //-- pre setting 
    size(320, 240);
    noSmooth();
    EcFactory.ccInit(this);
    VcAxis.ccFlip();
    
    //-- configuring
    
    
    //-- binding
    
    //--post setting
    
  }//+++
  
  @Override public void draw(){
    
    //-- pre draw
    background(0);
    pbRoller++;pbRoller&=0x0F;pbMillis=millis();
    
    //-- test bit
    int lpTestValue=ceil(map(mouseX,0,width,0,500));
    float lpTestRatio=map((float)mouseX,0.0f,(float)width,0.0f,1.0f);
    boolean lpTestBit=(pbRoller<7);
    boolean lpFullSecondPLS=pbRoller==7;
    
    //-- local loop
    //   DONT DELET THE IF PART!!
    if(lpTestBit){
      
      
    }else{
      
      
    }
    
    //[SAMPLE]::ttt.ccSetIsHoistDownSide(lpTestBit);
    
    //-- AND DONT DELETE THIS
    
    pid.ccStep(mouseX);
    
    fill(0xFFEEEE33);
    rect(mouseX,120,2,100);
    
    fill(0xFF33EE33);
    rect(160,100,2,100);
    
    fill(0xFF33EEEE);
    rect(pid.ccGetShiftedTarget(),80,2,100);
    
    VcTagger.ccTag("pid?", nfc(pid.ccGetShiftedTarget(),2));
    VcTagger.ccTag("pid", nfc(pid.ccGetAnalogOutput(),2));
    VcTagger.ccTag("pid_+", pid.ccGetPositiveOutput());
    VcTagger.ccTag("pid_-", pid.ccGetNegativeOutput());
    
    
    //-- DONT TOUCH THIS
    
    //-- system loop
    VcAxis.ccUpdate();
    //-- tagging
    VcTagger.ccTag("roller",pbRoller);
    VcTagger.ccTag("*--lpTestValue--*",lpTestValue);
    VcTagger.ccTag("*----*",0);
    //-- tagging ** over
    pbMillis=millis()-pbMillis;
    VcTagger.ccTag("ms/f", pbMillis);
    VcTagger.ccStabilize();
    
  }//+++
  
  @Override public void keyPressed(){
    switch(key){
      
      case 'w':
      break;
      
      case 's':
      break;
      
      case 'a':
      break;
      
      case 'd':
      break;
      
      //--
      case 'm':VcAxis.ccFlip();break;
      
      //-- system
      case ',':VcAxis.ccSetAnchor(mouseX, mouseY);break;
      case '.':VcAxis.ccSetAnchor(0, 0);break;
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
  
  boolean fnIsPressed(char pxKey){
    return keyPressed && (key==pxKey);
  }//+++
  
  void aat(Object pxVal){
    VcTagger.ccTag("???", pxVal);
  }//+++
  
  //=== inner
  
  //class EcCobHopper {}//***
  
  //class EcRSurgeBin　{}//***
  //class EcRExhaustFan　{}//***
  
  //class EcOnePathSkip　{}//***
  //class EcMixtureSilo　{}//***
  
      //[DTFM]:: maybe we should put this to EcRect
      //pbOwner.fill(0xFF663333);
      //pbOwner.rect(cmX, cmY, cmW, cmH);

  
  //=== entry
  
  static public void main(String[] passedArgs){
    PApplet.main(TestSketch.class.getCanonicalName());
  }//+++
  
}//***eof
