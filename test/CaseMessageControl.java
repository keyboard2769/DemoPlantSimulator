
/* <def A="revision"/>
 * # %flag% "file/version" :issue $description
 * - %% "" : $
 * - %% "" : $
 * - %% "" : $
 * <end/>
 *
 * code : _
 * name : 
 * core : Processing 2.x
 * original : NKH653
 *
 */

//package ;

import processing.core.*;

import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.VcTagger;
import kosui.ppputil.VcConst;

import ppptask.ZcMessageController;

public class CaseMessageControl extends PApplet {

  static public int pbRoller=0;

  //=== overridden
  
  ZcMessageController controller;
  
  @Override public void setup() {

    //-- pre setting 
    size(320, 240);
    noSmooth();
    EcFactory.ccInit(this);
    
    //-- init
    controller=new ZcMessageController();
  
    //-- post setting
    println("--done setup");
  }//+++
  
  @Override public void draw() { 

    //-- pre drawing
    background(0);
    pbRoller++;pbRoller&=0x0F;

    //-- updating
    controller.ccRun();
    
    //-- tagging
    VcTagger.ccTag("*-WM39-*",controller.ccGetWordOuput());
    VcTagger.ccTag("*-0-*",controller.ccGetBitOutput(0));
    VcTagger.ccTag("*-1-*",controller.ccGetBitOutput(1));
    VcTagger.ccTag("*-2-*",controller.ccGetBitOutput(2));
    VcTagger.ccTag("*-3-*",controller.ccGetBitOutput(3));
    VcTagger.ccTag("*-4-*",controller.ccGetBitOutput(4));
    VcTagger.ccTag("*-5-*",controller.ccGetBitOutput(5));
    VcTagger.ccTag("*-6-*",controller.ccGetBitOutput(6));
    VcTagger.ccTag("*-??-*",controller.testGetHead());
    VcTagger.ccTag("roll",pbRoller);
    VcTagger.ccStabilize();

  }//+++
  
  @Override public void keyPressed() {switch(key){
    //-- trigger
    case '1':case '2':case '3':case '4':case '5':case '6':
      String lpKey=new String(new char[]{key});
      int lpIndex=VcConst.ccParseIntegerString(lpKey);
      controller.ccSetBit(lpIndex);
    break;
    
    //-- system 
    case 'q':fsPover();break;
    default:break;
  }}//+++
  
  //=== operate

  void fsPover(){
    //-- flushing
    
    //-- defualt
    println("--exiting from PApplet");
    exit();
  }//+++
  
  //=== utiliry

  //=== inner
  
  //=== entry

  static public void main(String[] passedArgs) {
    PApplet.main(CaseMessageControl.class.getCanonicalName());
  }//+++

}//***eof
