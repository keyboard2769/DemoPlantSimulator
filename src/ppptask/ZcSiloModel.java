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

import kosui.ppplogic.ZcRangedValueModel;

public class ZcSiloModel extends ZcRangedValueModel{
  
  private final int cmUpper,cmMidd,cmLow;//...
  
  public ZcSiloModel(int pxMax, int pxL, int pxM,int pxU){
    super(0,pxMax);
    cmLow=pxL;
    cmMidd=pxM;
    cmUpper=pxU;
    cmValue=cmLow;
  }//+++ 
  
  public final void ccCharge(boolean pxStatus, int pxSpeed){
    if(pxStatus){ccShift(pxSpeed);}
  }//+++
  
  public final void ccDischarge(boolean pxStatus, int pxSpeed){
    if(pxStatus){ccShift(-1*pxSpeed);}
  }//+++
  
  public final boolean ccIsFull(){
    return cmValue>cmUpper;
  }//+++
  
  public final boolean ccIsMiddle(){
    return cmValue>cmMidd;
  }//+++
  
  public final boolean ccIsLow(){
    return cmValue>cmLow;
  }//+++
  
  public final boolean ccIsEmpty(){
    return cmValue<=1;
  }//+++
  
  public final boolean ccCanSupply(){
    return cmValue>(cmLow/2);
  }//+++
  
  public final boolean ccIsOverFlowed(){
    return cmValue>=(cmMax-1);
  }//+++
  
  //===
  
  public static final void fnTransfer(
    ZcSiloModel pxFrom, ZcSiloModel pxTo, boolean pxCondition, int pxSpeed
  ){
    if(pxFrom.ccCanSupply()){
      pxFrom.ccDischarge(pxCondition, pxSpeed);
      pxTo.ccCharge(pxCondition, pxSpeed);
    }
  }//+++
  
}//***eof
