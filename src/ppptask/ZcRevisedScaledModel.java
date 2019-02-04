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

import kosui.ppplogic.ZcScaledModel;

public class ZcRevisedScaledModel extends ZcScaledModel{
  
  private int cmBias,cmOffset;
  
  public ZcRevisedScaledModel(
    int pxInputOffset, int pxInputSpan,int pxOutputOffset, int pxOutputSpan
  ){
    super(pxInputOffset, pxInputSpan, pxOutputOffset, pxOutputSpan);
    cmBias=100;
    cmOffset=0;
  }//+++ 
  
  //===
  
  public final void ccSetupRevicer(int pxBias, int pxOffset){
    ccSetBias(pxBias);
    ccSetOffset(pxOffset);
  }//+++
  
  public final void ccSetBias(int pxZeroToHundred){
    cmBias=pxZeroToHundred&0x1FFF;
  }//+++
  
  public final void ccSetOffset(int pxOffset){
    cmOffset=pxOffset;
  }//+++
  
  //===
  
  public final int ccGetRevisedIntegerValue(){
    int lpScaled=ccGetlScaledIntValue();
    lpScaled*=cmBias;
    lpScaled/=100;
    lpScaled+=cmOffset;
    return lpScaled;
  }//+++
  
  public final float ccGetRevisedFloatValue(){
    float lpScaled=ccGetScaledFloatValue();
    lpScaled*=(((float)cmBias)/100f);
    lpScaled+=(float)cmOffset;
    return lpScaled;
  }//+++

}//***eof
