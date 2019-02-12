/*
 * Copyright (C) 2019 Key Parker
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

package ppptask;

import kosui.ppplogic.ZcLevelComparator;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcStepper;
import kosui.ppplogic.ZiTimer;

public class ZcWeighController{
  
  private static final int 
    C_S_STOPPED       = 0x00,
    C_S_WEIGHING      = 0x10,
    C_S_WAITING       = 0x15,
    C_S_DISCHARGING   = 0x20,
    C_S_EMPTY_CONFIRM = 0x25
  ;//...
  
  //===
  
  private boolean cmIsActivated, cmStartDischargeFlag,cmShutOut;

  private final ZcLevelComparator cmComparator=new ZcLevelComparator();

  private final ZcStepper cmStep=new ZcStepper();

  private final ZiTimer
    cmFullConfirmTimer=new ZcOnDelayTimer(20),
    cmZeroConfirmTimer=new ZcOnDelayTimer(20),
    cmEmptyConfirmTimer=new ZcOnDelayTimer(10)
  ;//...

  //===
  public final void ccRun(){

    //-- step control ** step
    cmStep.ccSetTo(C_S_STOPPED, !cmIsActivated);
    cmStep.ccStep(C_S_STOPPED, C_S_WEIGHING, cmIsActivated);
    cmStep.ccStep(C_S_WEIGHING, C_S_WAITING,
      cmFullConfirmTimer.ccIsUp());
    cmStep.ccStep(C_S_WAITING, C_S_DISCHARGING, cmStartDischargeFlag);
    cmStep.ccStep(C_S_DISCHARGING, C_S_EMPTY_CONFIRM,
      cmZeroConfirmTimer.ccIsUp());
    cmStep.ccStep(C_S_EMPTY_CONFIRM, C_S_WEIGHING,
      cmEmptyConfirmTimer.ccIsUp());

    //-- step control ** timer
    cmFullConfirmTimer.ccAct(cmComparator.ccIsFull());
    cmZeroConfirmTimer.ccAct(cmComparator.ccIsZero());
    cmEmptyConfirmTimer.ccAct(cmStep.ccIsAt(C_S_EMPTY_CONFIRM));
    
    //-- update
    cmComparator.ccRun();

  }//+++

  //===
  
  public final void ccTakeTargetAD(int lv0, int lv1){
    cmComparator.ccSetCompareLevel(0, lv0);
    cmComparator.ccSetCompareLevel(1, lv1);
    cmComparator.ccSetCompareLevel(2, lv1);
    cmComparator.ccSetCompareLevel(3, lv1);
    cmComparator.ccSetCompareLevel(4, lv1);
    cmComparator.ccSetCompareLevel(5, lv1);
    cmComparator.ccSetCompareLevel(6, lv1);
    cmComparator.ccSetCompareLevel(7, lv1);
  }//+++

  public final void ccTakeTargetAD(int lv0, int lv1, int lv2, int lv3){
    cmComparator.ccSetCompareLevel(0, lv0);
    cmComparator.ccSetCompareLevel(1, lv1);
    cmComparator.ccSetCompareLevel(2, lv2);
    cmComparator.ccSetCompareLevel(3, lv3);
    cmComparator.ccSetCompareLevel(4, lv3);
    cmComparator.ccSetCompareLevel(5, lv3);
    cmComparator.ccSetCompareLevel(6, lv3);
    cmComparator.ccSetCompareLevel(7, lv3);
  }//+++

  public final void ccTakeTargetAD(
    int lv0,
    int lv1, int lv2, int lv3, int lv4, int lv5, int lv6, int lv7
  ){
    cmComparator.ccSetCompareLevel(0, lv0);
    cmComparator.ccSetCompareLevel(1, lv1);
    cmComparator.ccSetCompareLevel(2, lv2);
    cmComparator.ccSetCompareLevel(3, lv3);
    cmComparator.ccSetCompareLevel(4, lv4);
    cmComparator.ccSetCompareLevel(5, lv5);
    cmComparator.ccSetCompareLevel(6, lv6);
    cmComparator.ccSetCompareLevel(7, lv7);
  }//+++
  
  //===
  public final void ccTakeControlBit(boolean pxActivate, boolean pxDischarge){
    cmIsActivated=pxActivate;
    cmStartDischargeFlag=pxDischarge;
  }//+++

  public final void ccSetIsActivated(boolean pxStatus){
    cmIsActivated=pxStatus;
  }//+++
  
  public final void ccSetShutOut(boolean pxStatus){
    cmShutOut=pxStatus;
  }//+++

  public final void ccSetStartDischargeFlag(boolean pxStatus){
    cmStartDischargeFlag=pxStatus;
  }//+++

  public final void ccSetCellAD(int pxValue){
    cmComparator.ccSetCurrentLevel(pxValue);
  }//+++

  //[TOIMP]::void ccSet%whatever%Timer(int)
  
  //===
  
  public final boolean ccIsWaiting(){
    return cmStep.ccIsAt(C_S_WAITING);
  }//+++

  public final boolean ccIsWeighingAt(int pxLevel){
    if(cmShutOut){return false;}
    return cmStep.ccIsAt(C_S_WEIGHING)&&cmComparator.ccIsAtLevel(pxLevel);
  }//+++

  public final boolean ccIsDischarging(){
    return cmStep.ccIsAt(C_S_DISCHARGING);
  }//+++

  public final boolean cmIsEmptyConfirming(){
    return cmStep.ccIsAt(C_S_EMPTY_CONFIRM);
  }//+++
  
  //===
  
  //[TODELETE]::
  @Deprecated public final ZcLevelComparator testGetComparator()
    {return cmComparator;}
  @Deprecated public final int testGetStage()
    {return cmStep.testGetStage();}

}//***eof
