/* *
 * TestSketch
 *
 * add describs here
 *
 * (for Processing 2.x core )
 */

//package ;

import processing.core.*; 
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.VcAxis;
import kosui.ppplocalui.VcTagger;

public class TestSketch extends PApplet {
  
  public static TestSketch self;
    
  static volatile int pbRoller=0;
  static volatile int pbMillis=0;
  
  //=== overridden
  
  ppptable.McLockedCategoryIntegerRecord ttt=
    new ppptable.McLockedCategoryIntegerRecord();
  
  @Override public void setup() {
    
    //-- pre setting 
    size(320, 240);
    noSmooth();
    EcFactory.ccInit(this);
    VcAxis.ccSetIsEnabled();
    self=this;
    
    //-- constructing
    
    //-- configuring
    for(int i=6;i>0;i--){
      ttt.ccSetAG(i, (int)random(0,9999));
      if(i<=2){ttt.ccSetFR(i, (int)random(0,9999));}
      if(i==1){ttt.ccSetAS(i, (int)random(0,9999));}
    }//..~
    
    //-- binding
    
    //--post setting
    ttt.testReadup();
    println("\n-ag-fr-as-");
    println(ttt.ccGetMaxAG());
    println(ttt.ccGetMaxFR());
    println(ttt.ccGetMaxAS());
    
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
    
    //-- AND DONT DELETE THIS
    
    
    //-- system loop..DONT TOUCH THIS
    VcAxis.ccUpdate();
    //-- tagging
    VcTagger.ccTag("*--*",0);
    VcTagger.ccTag("*--*",0);
    VcTagger.ccTag("*--*",0);
    VcTagger.ccTag("roller",pbRoller);
    VcTagger.ccTag("*--lpTestValue--*",lpTestValue);
    VcTagger.ccTag("*--*",0);
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
      case 'm':VcAxis.ccSetIsEnabled();break;
      
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
  
  boolean fnIsPressed(char pxKey){return keyPressed && (key==pxKey);}//+++
  
  void aat(Object pxVal){
    VcTagger.ccTag("???", pxVal);
  }//+++
  
  //=== inner
  
  //=== entry
  
  static public void main(String[] passedArgs){
    PApplet.main(TestSketch.class.getCanonicalName());
  }//+++
  
}//***eof
