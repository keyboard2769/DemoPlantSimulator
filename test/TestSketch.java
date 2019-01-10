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
import kosui.ppplogic.ZcOffDelayTimer;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppplogic.ZcStepper;
import kosui.ppplogic.ZiTimer;
import pppicon.EcMixerGateIcon;
import ppptask.ZcCylinderGateModel;
import ppptask.ZcPIDController;
import pppunit.EcOnePathSkip;
import processing.core.*; 

public class TestSketch extends PApplet {
    
  static volatile int pbRoller=0;
  static volatile int pbMillis=0;
  
  //=== overridden
  
  ZcRangedValueModel tttCell=new ZcRangedValueModel(400, 3200);
  
  TestLevelComparator tttComp=new TestLevelComparator();
  
  
  @Override public void setup() {
    
    //-- pre setting 
    size(320, 240);
    noSmooth();
    EcFactory.ccInit(this);
    VcAxis.ccFlip();
    
    //-- constructing
    
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
    
    //-- AND DONT DELETE THIS
    
    tttCell.ccShift(fnIsPressed('r')?   8:0);
    tttCell.ccShift(fnIsPressed('f')?-16:0);
    
    
    tttComp.ccSetCurrentLevel(tttCell.ccGetValue());
    tttComp.ccSetCompareLevel(0, 402);
    tttComp.ccSetCompareLevel(1, 600);
    tttComp.ccSetCompareLevel(2, 800);
    tttComp.ccSetCompareLevel(3, 900);
    tttComp.ccSetCompareLevel(4, 950);
    tttComp.ccStep();
    
    
    
    
    
    
    //-- system loop..DONT TOUCH THIS
    VcAxis.ccUpdate();
    //-- tagging
    VcTagger.ccTag("gate",tttComp.ccGetCurrentLevel());
    VcTagger.ccTag("cell",tttCell.ccGetValue());
    VcTagger.ccTag("*--*",0);
    VcTagger.ccTag("*--*",0);
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
        println(tttComp.cmLevel);
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
  
  class TestWeighController{
    
    final int
      C_S_STOPPED=0x00,
      C_S_WEIGHING=0x10,
      C_S_DISCHARGING=0x20
   ;//...
    
    TestLevelComparator cmComparator=new TestLevelComparator();
    ZcStepper cmStep=new ZcStepper();
    
    boolean cmIsRunning,cmIsWeighing,cmIsDischarging;
    
    //===
    
    void ccUpdate(){
      cmStep.ccSetTo(C_S_STOPPED, !cmIsRunning);
      cmStep.ccStep(C_S_STOPPED, C_S_WEIGHING, cmIsRunning);
      cmStep.ccStep(C_S_WEIGHING, C_S_DISCHARGING, cmComparator.ccIsFull());
      cmStep.ccStep(C_S_DISCHARGING, C_S_WEIGHING, cmComparator.ccIsZero());
    }//+++
    
    //===
    
    void ccSetLevel(int lv0, int lv1, int lv2, int lv3){
      cmComparator.ccSetCompareLevel(0, lv0);
      cmComparator.ccSetCompareLevel(1, lv1);
      cmComparator.ccSetCompareLevel(2, lv2);
      cmComparator.ccSetCompareLevel(3, lv3);
    }//+++
    
    boolean ccLevelOutput(int pxLevel){
      return cmStep.ccIsAt(C_S_WEIGHING)&&cmComparator.ccIsAtLevel(pxLevel);
    }//+++
    
    //[HEAD]:: now what???
    
  }//***
  

  class TestLevelComparator{
    
    int[] cmLevel={0,0,0,0, 0,0,0,0};
    int cmCurrentValue=0;
    int cmCurrentLevel=0;
    
    void ccSetCompareLevel(int pxLevel, int pxValue){
      if(pxLevel<0){return;}
      if(pxLevel>=8){return;}
      if(pxLevel==0){cmLevel[0]=pxValue;}
      if(pxLevel>0){
        if(pxValue<cmLevel[pxLevel-1]){
          cmLevel[pxLevel]=cmLevel[pxLevel-1];
        }else{
          cmLevel[pxLevel]=pxValue;
        }
      }
    }//+++
    
    void ccStep(){
      cmCurrentLevel=-1;
      for(int i=0;i<8;i++){
        if(cmCurrentValue<cmLevel[i]){
          cmCurrentLevel=i;break;
        }//..?
      }//..~
    }//+++
    
    void ccSetCurrentLevel(int pxValue){
      cmCurrentValue=pxValue;
    }//+++
    
    int ccGetCurrentLevel(){
      return cmCurrentLevel;
    }//+++
    
    boolean ccIsAtLevel(int pxLevel){
      return pxLevel==cmCurrentLevel;
    }//+++
    
    boolean ccIsZero(){
      return cmCurrentLevel==0;
    }//+++
    
    boolean ccIsFull(){
      return cmCurrentLevel==-1;
    }//+++
    
  }//***

  //=== entry
  
  static public void main(String[] passedArgs){
    PApplet.main(TestSketch.class.getCanonicalName());
  }//+++
  
}//***eof
