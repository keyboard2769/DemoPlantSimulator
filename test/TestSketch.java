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
  
  kosui.ppplogic.ZcRangedValueModel testInputAD;
  kosui.ppplogic.ZcRangedValueModel testInputKG;
  ppptask.ZcRevisedScaledModel testCell;
  int testTareWeight;
  
  @Override public void setup() {
    
    //-- pre setting 
    size(320, 240);
    noSmooth();
    EcFactory.ccInit(this);
    VcAxis.ccSetIsEnabled();
    self=this;
    
    //-- constructing
    testInputKG=new kosui.ppplogic.ZcRangedValueModel(   0, 1000);
    testInputAD=new kosui.ppplogic.ZcRangedValueModel(1000, 1000);
    testCell=new ppptask.ZcRevisedScaledModel(1000, 2000 , 0, 100);
    testTareWeight=0;
    
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
    
    if(fnIsPressed('w')){testInputAD.ccShift( 16);}
    if(fnIsPressed('s')){testInputAD.ccShift(-16);}
    if(fnIsPressed('z')){
      testTareWeight=testCell.ccGetlScaledIntegerValue();
    }
    testCell.ccSetOffset(-1*testTareWeight);
    testCell.ccSetInputValue(testInputAD.ccGetValue());
    
    if(fnIsPressed('d')){testInputKG.ccShift( 10);}
    if(fnIsPressed('a')){testInputKG.ccShift(-10);}
    
    //-- AND DONT DELETE THIS
    
    //-- system loop..DONT TOUCH THIS
    VcAxis.ccUpdate();
    //-- tagging
    VcTagger.ccTag("*-tare-*",testTareWeight);
    VcTagger.ccTag("*--*",0);
    VcTagger.ccTag("*-ad-*",testCell.ccGetInputValue());
    VcTagger.ccTag("*-rkg-*",testCell.ccGetRevisedIntegerValue());
    VcTagger.ccTag("*-nkg-*",testCell.ccGetlScaledIntegerValue());
    VcTagger.ccTag("*--*",0);
    VcTagger.ccTag("*-ikg-*",testInputKG.ccGetValue());
    VcTagger.ccTag("*-iad-*",testCell.ccToUnrevisedInputValue(testInputKG.ccGetValue()));
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
