/*
 * Copyright (C) 2019 keypad
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

import kosui.ppplocalui.VcConsole;
import kosui.ppplocalui.ViOperable;
import kosui.ppputil.VcConst;
import static pppmain.MainLocalCoordinator.C_ID_BOOK_RECIPE_HEAD;
import static pppmain.MainLocalCoordinator.C_ID_BOOK_KG_HEAD;
import static pppmain.MainLocalCoordinator.C_ID_BOOK_BATCH_HEAD;

public final class MainKeyInputManager{
  
  private static MainSketch mainSketch;
  
  //===
  
  private static MainKeyInputManager self;
  public static MainKeyInputManager ccGetReference(){
    if(self==null){self=new MainKeyInputManager();}
    return self;
  }//++!

  //===

  private MainKeyInputManager(){
    mainSketch=MainSketch.ccGetReference();  
  }//++!  
  
  public final void ccInit(){
    
    VcConsole.ccAddEmptyOperation(new ViOperable() {
      @Override public void ccOperate(String[] pxLine){
        MainSketch.hisUI.ccClearCurrentInputFocus();
      }//+++
    });
    
    VcConsole.ccAddNumericOperation(new ViOperable() {
      @Override public void ccOperate(String[] pxLine){
        
        //-- check in
        if(pxLine==null){return;}
        if(pxLine.length<=1){return;}
        if(!MainSketch.hisUI.ccHasInputtableFocused()){return;}
        if(MainSketch.yourMOD.cmIsAutoWeighRunnning){
          VcConsole.ccSetMessage("modifying on the run is forbidden.");
          return;
        }//..?
        
        //-- basic filtering
        int lpID=MainSketch.hisUI.ccGetInputFocusID();
        int lpValue;
        boolean lpAccepted=false;
        if(VcConst.ccIsIntegerString(pxLine[1])){
          lpValue=VcConst.ccParseIntegerString(pxLine[1]);
        }else{
          VcConsole.ccSetMessage("input format illegal.");
          return;
        }//..?
        if(lpValue<0){
          VcConsole.ccSetMessage("minus value is illegal::");
          return;
        }//..?
        
        //-- redistribute
        //-- redistribute ** recipe
        if(lpID>=C_ID_BOOK_RECIPE_HEAD&&
           lpID<(C_ID_BOOK_RECIPE_HEAD+3)
        ){
          MainSketch.yourMOD.fsSetBookingRecipe
            (lpID-C_ID_BOOK_RECIPE_HEAD, lpValue);
          VcConsole.ccSetMessage("recipe value accepted.");
          lpAccepted=true;
        }//..?
        //-- redistribute ** kg
        if(lpID>=C_ID_BOOK_KG_HEAD&&
           lpID<(C_ID_BOOK_KG_HEAD+3)
        ){
          MainSketch.yourMOD.fsSetBookingKG
            (lpID-C_ID_BOOK_KG_HEAD, lpValue);
          VcConsole.ccSetMessage("kg value accepted.");
          lpAccepted=true;
        }//..?
        //-- redistribute ** batch
        if(lpID>=MainLocalCoordinator.C_ID_BOOK_BATCH_HEAD&&
           lpID<(MainLocalCoordinator.C_ID_BOOK_BATCH_HEAD+3)
        ){
          MainSketch.yourMOD.fsSetBookingBatch
            (lpID-C_ID_BOOK_BATCH_HEAD, lpValue);
          VcConsole.ccSetMessage("batch value accepted.");
          lpAccepted=true;
        }//..?
        
        //-- accepting
        if(lpAccepted){MainSketch.hisUI.ccToNextInputIndex();}
        else{VcConsole.ccSetMessage("input value is NOT accepted");}
      
      }//+++
    });
    
    ssAddOperation(cmQuit);
    ssAddOperation(cmHelp);
    ssAddOperation(cmFlipMessageBar);
    ssAddOperation(cmTestError);
    ssAddOperation(cmTestTrendLog);
    ssAddOperation(cmInputDummyRecipe);
    
  }//++!

  //===
  
  private final McInputOperation cmQuit=new McInputOperation() {
    @Override public String ccGetCommand(){return "quit";}
    @Override public void ccOperate(String[] pxLine){
      mainSketch.fsPover();
    }//+++
  };//...
  
  private final McInputOperation cmHelp=new McInputOperation() {
    @Override public String ccGetCommand(){return "help";}
    @Override public void ccOperate(String[] pxLine){
      VcConsole.ccSetMessage("-- help info not abailable");
    }//+++
  };//...
  
  private final McInputOperation cmFlipMessageBar=new McInputOperation() {
    @Override public String ccGetCommand(){return "flip";}
    @Override public void ccOperate(String[] pxLine){
      VcConsole.ccSetIsMessageBarVisible();
    }//+++
  };//...
  
  private final McInputOperation cmTestError=new McInputOperation() {
    @Override public String ccGetCommand(){return "terr";}//+++
    @Override public void ccOperate(String[] pxLine){
      if(pxLine.length<2){return;}
      for(int i=1,s=pxLine.length;i<s;i++){
        int lpParam=VcConst.ccParseIntegerString(pxLine[i]);
        if(lpParam==0){continue;}
        MainLogicController.ccGetReference().cmErrorMessageTask
          .testToggleErrorBit(lpParam);
      }//..~
      VcConsole.ccSetMessage("-- test error accepted.");
    }//+++
  };//...
  
  private final McInputOperation cmTestTrendLog=new McInputOperation() {
    @Override public String ccGetCommand(){return "ttrd";}//+++
    @Override public void ccOperate(String[] pxLine){
      MainSketch.yourMOD.fsLogBurningTrendRecord();
      VcConsole.ccSetMessage("-- a dummy trd log may be generated");
    }//+++
  };//...
  
  private final McInputOperation cmInputDummyRecipe=new McInputOperation() {
    @Override public String ccGetCommand(){return "gcc";}//+++
    @Override public void ccOperate(String[] pxLine){
      MainSketch.yourMOD.fsSetupBooking(0, 1, 4000, 3);
      MainSketch.yourMOD.fsSetupBooking(1, 2, 4000, 2);
      MainSketch.yourMOD.fsSetupBooking(2, 3, 3000, 1);
      MainSketch.yourMOD.fsSetupBooking(3, 1, 1500, 2);
      VcConsole.ccSetMessage("-- a dummy recipe may be inputed");
    }//+++
  };//...
  
  //===
  
  private static void ssAddOperation(McInputOperation pxOperation){
    VcConsole.ccAddOperation(pxOperation.ccGetCommand(), pxOperation);
  }//+++
  
  interface McInputOperation extends ViOperable{
    String ccGetCommand();
  }//+++
  
 }//***eof
