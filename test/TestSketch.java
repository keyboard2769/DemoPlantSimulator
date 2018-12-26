/* *
 * TestSketch
 *
 * add describs here
 *
 * (for Processing 2.x core )
 */

//package ;

import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.VcAxis;
import kosui.ppplocalui.VcTagger;
import pppshape.EcHopperShape;
import pppunit.EcOnePathSkip;
import processing.core.*; 

public class TestSketch extends PApplet {
    
  static volatile int pbRoller=0;
  static volatile int pbMillis=0;
  
  //=== overridden
  
  EcOnePathSkip ttt=new EcOnePathSkip("n",100,100,50,12);
  
  @Override public void setup() {
    
    //-- pre setting 
    size(320, 240);
    noSmooth();
    EcFactory.ccInit(this);
    
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
    
    //-- local loop
    
    ttt.ccSetPostion(lpTestRatio);
    
    ttt.ccUpdate();
    
    //-- system loop
    VcAxis.ccUpdate();
    
    int j=constrain(1, 2, 3);
    
    //-- tagging
    VcTagger.ccTag("roller",pbRoller);
    VcTagger.ccTag("*--lpTestValue--*",lpTestValue);
    VcTagger.ccTag("*--test--*",ttt.testValue());
    VcTagger.ccTag("*----*",0);
    
    //-- tagging ** over
    pbMillis=millis()-pbMillis;
    VcTagger.ccTag("ms/f", pbMillis);
    
    VcTagger.ccStabilize();
    
  }//+++
  
  @Override public void keyPressed(){
    switch(key){
      
      //-- system
      case ',':VcAxis.ccSetAnchor(mouseX, mouseY);break;
      case '.':VcAxis.ccFlip();break;
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
  
  //=== utiliry
  
  //=== inner
  
  //class EcCobHopper {}//***
  
  //class EcRSurgeBin　{}//***
  //class EcRExhaustFan　{}//***
  
  //class EcOnePathSkip　{}//***
  //class EcMixtureSilo　{}//***
  
      //[DTFM]::
      //pbOwner.fill(0xFF663333);
      //pbOwner.rect(cmX, cmY, cmW, cmH);

  
  //=== entry
  
  static public void main(String[] passedArgs){
    PApplet.main(TestSketch.class.getCanonicalName());
  }//+++
  
}//***eof
