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

package ppptable;

import java.util.Arrays;

public class McCurrentSlotModel {
  
  public  static final int C_CAPA=0x10;
  private static final int C_MASK=0x0F;
  private static final int C_ACCR=  10;
  
  //===
  
  private int cmADSpan;
  private final int[] cmAD,cmCT,cmAL,cmCA;
  
  public McCurrentSlotModel(){
    cmAD=new int[C_CAPA];Arrays.fill(cmAD, 0);
    cmCT=new int[C_CAPA];Arrays.fill(cmCT, 0);
    cmAL=new int[C_CAPA];Arrays.fill(cmAL, 0);
    cmCA =new int[C_CAPA];Arrays.fill(cmCA, 0);
    cmADSpan=5000;
  }//++!
  
  //===
  
  public final void ccSetADSpan(int pxSpan){
    if(cmADSpan<=0){return;}
    cmADSpan=pxSpan;
  }//+++
  
  public final void ccSetADValue(int pxIndex,int pxAD){
    int lpIndex=pxIndex&C_MASK;
    cmAD[lpIndex]=pxAD;
    cmCA[lpIndex]=cmAD[lpIndex]*cmCT[lpIndex]/cmADSpan;
  }//+++
  
  public final void ccSetALValue(int pxIndex,int pxAmpere){
    cmAL[pxIndex&C_MASK]=pxAmpere*C_ACCR;
  }//+++
  
  public final void ccSetCTValue(int pxIndex,int pxAmpere){
    cmCT[pxIndex&C_MASK]=pxAmpere*C_ACCR;
  }//+++
  
  //===
  
  public final int ccGetADSpan(){
    return cmADSpan;
  }//+++
  
  public final int ccGetADValue(int pxIndex){
    return cmAD[pxIndex&C_MASK];
  }//+++
  
  public final int ccGetALValue(int pxIndex){
    return cmAL[pxIndex&C_MASK];
  }//+++
  
  public final int ccGetCTValue(int pxIndex){
    return cmCT[pxIndex&C_MASK];
  }//+++
  
  //===
  
  synchronized 
  public final boolean ccIsOverwhelming(int pxIndex){
    int lpIndex=pxIndex&C_MASK;
    return cmCA[lpIndex]>cmAL[lpIndex];
  }//+++
  
  synchronized 
  public final int ccGetAmpereValue(int pxIndex){
    return cmCA[pxIndex&C_MASK];
  }//+++
  
  synchronized 
  public final float ccGetContertiveSpan(int pxIndex){
    return (float)(cmCT[pxIndex]/C_ACCR);
  }//+++
  
}//***eof
