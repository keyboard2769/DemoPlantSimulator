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

package ppptask;

import kosui.ppplogic.ZcFlicker;
import kosui.ppplogic.ZcPulser;
import kosui.ppplogic.ZcRangedValueModel;

public class ZcBagPulseController {
  
  private final ZcFlicker cmFlicker=new ZcFlicker(40, 0.5f);
  private final ZcRangedValueModel cmModel=new ZcRangedValueModel(0, 24);
  private final ZcPulser cmPulser = new ZcPulser();
  
  //===
  
  public final void ccRun(boolean pxStatus){
    if(!pxStatus){cmModel.ccSetValue(0);return;}
    cmFlicker.ccAct(pxStatus);
    if(cmPulser.ccUpPulse(cmFlicker.ccIsUp()))
      {cmModel.ccRoll(1);}
  }//+++
  
  //===
  
  public final void ccSetMaxCount(int pxCount){
    cmModel.ccSetRange(pxCount);
  }//+++
  
  /**
   * all in frame
   * @param pxSpan #
   * @param pxDutyHB half byte, say 64 means 50%
   */
  public final void ccSetFlicker(int pxSpan, int pxDutyHB){
    cmFlicker.ccSetTime(pxSpan);
    cmFlicker.ccSetDuty(((float)pxDutyHB)/128f);
  }//+++
  
  public final int ccGetCurrentCount()
    {return cmModel.ccGetValue();}//+++
  
  public final boolean ccGetOutputSignal()
    {return cmFlicker.ccIsUp();}//+++

}//***eof
