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

public class MainKeyInputManager implements ViOperable{
  
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
  
  //=== keyboard input

  @Override public void ccOperate(String[] pxLine){
    
    String lpCommand=pxLine[0];
    
    //-- empty check
    if(lpCommand.isEmpty()){
      MainSketch.hisUI.ccClearCurrentInputFocus();
      return;
    }//+++
    
    //-- book value input
    if(MainSketch.hisUI.ccHasInputtableFocused()){
      
      if(MainSketch.yourMOD.cmIsAutoWeighRunnning){
        VcConsole.ccSetMessage("modifying on the run is forbidden.");
        return;
      }//...
      
      int lpID=MainSketch.hisUI.ccGetInputFocusID();
      int lpValue;
      boolean lpAccepted=false;
      
      //-- basic filtering
      if(VcConst.ccIsIntegerString(lpCommand)){
        lpValue=VcConst.ccParseIntegerString(lpCommand);
      }else{
        VcConsole.ccSetMessage("input format illegal.");
        return;
      }//...
      
      if(lpValue<0){
        VcConsole.ccSetMessage("minus value is illegal::");
        return;
      }//...
      
      //-- redistribute
      if(lpID>=C_ID_BOOK_RECIPE_HEAD&&
         lpID<(C_ID_BOOK_RECIPE_HEAD+3)
      ){
        MainSketch.yourMOD.fsSetBookingRecipe
          (lpID-C_ID_BOOK_RECIPE_HEAD, lpValue);
        VcConsole.ccSetMessage("recipe value accepted.");
        lpAccepted=true;
      }//..?
      
      if(lpID>=C_ID_BOOK_KG_HEAD&&
         lpID<(C_ID_BOOK_KG_HEAD+3)
      ){
        MainSketch.yourMOD.fsSetBookingKG
          (lpID-C_ID_BOOK_KG_HEAD, lpValue);
        VcConsole.ccSetMessage("kg value accepted.");
        lpAccepted=true;
      }//..?
      
      if(lpID>=MainLocalCoordinator.C_ID_BOOK_BATCH_HEAD&&
         lpID<(MainLocalCoordinator.C_ID_BOOK_BATCH_HEAD+3)
      ){
        MainSketch.yourMOD.fsSetBookingBatch
          (lpID-C_ID_BOOK_BATCH_HEAD, lpValue);
        VcConsole.ccSetMessage("batch value accepted.");
        lpAccepted=true;
      }//..?
      
      if(lpAccepted){
        MainSketch.hisUI.ccToNextInputIndex();
      }else{
        VcConsole.ccSetMessage("input value is NOT accepted");
      }return;
      
    }//..?
    
    //-- command input
    
    if(lpCommand.equals("terr")){
      fsToggleErrorBits(pxLine);
      VcConsole.ccSetMessage("-- an error bit may be toggled");
      return;
    }//..?
    
    if(lpCommand.equals("ttrd")){
      MainSketch.yourMOD.fsLogBurningTrendRecord();
      VcConsole.ccSetMessage("-- a dummy trd log may be generated");
      return;
    }//..?
    
    if(lpCommand.equals("gcc")){
      fsSetupDummyBooks();
      return;
    }//..?
    
    if(lpCommand.equals("show")){
      VcConsole.ccSetIsMessageBarVisible();
      return;
    }//..?
  
    if(lpCommand.equals("quit")){
      mainSketch.fsPover();
      return;
    }//..?
    
    if(lpCommand.equals("help")){
      VcConsole.ccSetMessage("-- help info not abailable");
      return;
    }//..?
  
    VcConsole.ccSetMessage("-- command not found");
    
  }//+++
  
  //===
  
  private void fsSetupDummyBooks(){
    MainSketch.yourMOD.fsSetupBooking(0, 1, 4000, 3);
    MainSketch.yourMOD.fsSetupBooking(1, 2, 4000, 2);
    MainSketch.yourMOD.fsSetupBooking(2, 3, 3000, 1);
    MainSketch.yourMOD.fsSetupBooking(3, 1, 1500, 2);
  }//+++
  
  private void fsToggleErrorBits(String[] pxLine){
    if(pxLine.length<2){return;}
    for(int i=1,s=pxLine.length;i<s;i++){
      int lpParam=VcConst.ccParseIntegerString(pxLine[i]);
      if(lpParam==0){continue;}
      MainLogicController.ccGetReference().cmErrorMessageTask
        .testToggleErrorBit(lpParam);
    }//..~
    VcConsole.ccSetMessage("-- test error accepted.");
  }//+++
  
 }//***eof
