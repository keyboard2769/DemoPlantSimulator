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
import ppptable.McCategoryIntegerBundle;

public class TestSketch extends PApplet {
  
  public static TestSketch self;
    
  static volatile int pbRoller=0;
  static volatile int pbMillis=0;
  
  //=== overridden
  
  ppptable.McCategoryIntegerBundle to,from,with;
  
  pppunit.EcWeigher ttt;
  
  @Override public void setup() {
    
    //-- pre setting 
    size(320, 240);
    noSmooth();
    EcFactory.ccInit(this);
    VcAxis.ccSetIsEnabled();
    self=this;
    
    //-- constructing
    ttt=new pppunit.EcWeigher("dd", 100, 100, 500, 0);
    
    //-- configuring
    
    to=new McCategoryIntegerBundle();
    from=new McCategoryIntegerBundle();
    with=new McCategoryIntegerBundle();
    for(int i=6;i>=1;i--){
      from.ccSetAG(i,i*10);
      with.ccSetAG(i,i);
      if(i<=2){
        from.ccSetFR(i,i*10);
        with.ccSetFR(i,i);
      }//..?
      if(i<=1){
        from.ccSetAS(i,i*10);
        with.ccSetAS(i,i);
      }//..?
    }//...
    
    McCategoryIntegerBundle.ccAdd(to, from, with, 0, 100);
    
    println("from:");from.testReadup();
    println("with:");with.testReadup();
    println("to:");to.testReadup();
    
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
    
    char e='w';
    if(lpTestValue<100){e='e';}
    if(lpTestValue>200){e='c';}
    if(lpTestValue>300){e='d';}
    if(lpTestValue>400){e='t';}
    
    
    //-- AND DONT DELETE THIS
    ttt.ccSetIsLocked(lpTestBit);
    ttt.ccSetGaugeStatus(e);
    ttt.ccSetCurrentKG(lpTestValue);
    ttt.ccUpdate();
    
    
    //-- system loop..DONT TOUCH THIS
    VcAxis.ccUpdate();
    //-- tagging
    
    VcTagger.ccTag("*-ee-*",e);
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
