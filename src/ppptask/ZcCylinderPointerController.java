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

import kosui.ppplogic.ZcStepper;

public class ZcCylinderPointerController{
  
  private static final int 
    CS_CLOSE=0x00,
    CS_NORMAL_OPEN=0x01,
    CS_NORMAL_CUT=0x02,
    CS_NORMAL_HOLD=0x04,
    CS_RST_OPEN=0x10,
    CS_RST_HOLD=0x20
  ;//...
  
  //===

  private boolean
    cmWeighSIG, cmCutSIG, cmPositionSIG
  ;//...

  private final ZcStepper cmStep=new ZcStepper();
  
  //===

  public final void ccRun(){
    
    //-- stopping
    cmStep.ccSetTo(CS_CLOSE, !cmWeighSIG);

    //-- normal mode
    cmStep.ccStep(CS_CLOSE, CS_NORMAL_OPEN, cmWeighSIG && !cmCutSIG);
    cmStep.ccStep(CS_NORMAL_OPEN, CS_NORMAL_CUT, cmCutSIG);
    cmStep.ccStep(CS_NORMAL_CUT, CS_NORMAL_HOLD, cmPositionSIG);

    //-- restriction mode
    cmStep.ccStep(CS_CLOSE, CS_RST_OPEN, cmWeighSIG && cmCutSIG);
    cmStep.ccStep(CS_RST_OPEN, CS_RST_HOLD, cmPositionSIG);

  }//+++
  
  //===

  public final void ccTakeControlBit(
    boolean pxWeigh, boolean pxCut, boolean pxPosition
  ){
    cmWeighSIG=pxWeigh;
    cmCutSIG=pxCut;
    cmPositionSIG=pxPosition;
  }//+++

  public final boolean ccGetOpenSignal(){
    return cmStep.ccIsAt(CS_NORMAL_OPEN)||cmStep.ccIsAt(CS_RST_OPEN);
  }//+++

  public final boolean ccGetCloseSignal(){
    return cmStep.ccIsAt(CS_CLOSE)||cmStep.ccIsAt(CS_NORMAL_CUT);
  }//+++
  
}//***eof
