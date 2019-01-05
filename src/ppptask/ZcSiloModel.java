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

import static processing.core.PApplet.constrain;

//[TODO]:: but im very sure this dont have to be part of kosui
public class ZcSiloModel {
  
  private final int 
    cmMax,cmUpper,cmMidd,cmLow;//...
  private int cmContent;//...
  
  public ZcSiloModel(int pxMax, int pxL, int pxM,int pxU){
    cmMax=pxMax;
    cmLow=pxL;
    cmMidd=pxM;
    cmUpper=pxU;
    cmContent=cmLow;
  }//+++ 
  
  public final void ccCharge(boolean pxStatus, int pxSpeed){
    if(pxStatus){ccShiftContent(pxSpeed);}
  }//+++
  
  public final void ccDischarge(boolean pxStatus, int pxSpeed){
    if(pxStatus){ccShiftContent(-1*pxSpeed);}
  }//+++
  
  public final void ccShiftContent(int pxOffset){
    cmContent+=pxOffset;
    cmContent=constrain(cmContent, 0, cmMax);
  }//+++
  
  public final boolean ccIsFull(){
    return cmContent>cmUpper;
  }//+++
  
  public final boolean ccIsMiddle(){
    return cmContent>cmMidd;
  }//+++
  
  public final boolean ccIsLow(){
    return cmContent>cmLow;
  }//+++
  
  public final boolean ccIsEmpty(){
    return cmContent<=1;
  }//+++
  
  public final boolean ccIsOverFlowed(){
    return cmContent>=(cmMax-1);
  }//+++
  
  public final float ccGetPercentage(){
    return ((float)cmContent)/((float)cmMax);
  }//+++
  
  public final int ccGetContent(){
    return cmContent;
  }//+++
  
}//***eof
