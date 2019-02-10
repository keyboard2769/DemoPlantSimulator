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

package ppptable;

import kosui.ppputil.VcConst;
import static pppmain.MainSketch.fnOneAfterDecimal;

public class McCategoryStringBundle{
  
  /**
   * you may have to change it manually
   */
  protected static final int 
    C_MASK_BIG   = 0x07,
    C_MASK_SMALL = 0x03
  ;//...
  
  //===
  
  public String[] cmAG={
    "0","0","0","0", "0","0","0","0"
  } ;
  public String[] cmFR={
    "0","0","0","0"
  } ;
  public String[] cmAS={
    "0","0","0","0"
  } ;
  
  //===
  
  public final void ccSetAGIntegerValue(int pxIndex,int pxValue){
    cmAG[pxIndex&C_MASK_BIG]=Integer.toString(pxValue);
  }//+++
  
  public final void ccSetFRIntegerValue(int pxIndex,int pxValue){
    cmFR[pxIndex&C_MASK_SMALL]=Integer.toString(pxValue);
  }//+++
  
  public final void ccSetASIntegerValue(int pxIndex,int pxValue){
    cmAS[pxIndex&C_MASK_SMALL]=Integer.toString(pxValue);
  }//+++
  
  //===
  
  public final void ccSetAGFloatValue(int pxIndex,float pxValue){
    cmAG[pxIndex&C_MASK_BIG]=Float.toString
      (fnOneAfterDecimal(pxValue));
  }//+++
  
  public final void ccSetFRFloatValue(int pxIndex,float pxValue){
    cmFR[pxIndex&C_MASK_SMALL]=Float.toString
      (fnOneAfterDecimal(pxValue));
  }//+++
  
  public final void ccSetASFloatValue(int pxIndex,float pxValue){
    cmAS[pxIndex&C_MASK_SMALL]=Float.toString
      (fnOneAfterDecimal(pxValue));
  }//+++
  
  //===
  
  public final int ccGetAGIntegerValue(int pxIndex){
    return VcConst.ccParseIntegerString(cmAG[pxIndex&C_MASK_BIG]);
  }//+++
  
  public final int ccGetFRIntegerValue(int pxIndex){
    return VcConst.ccParseIntegerString(cmFR[pxIndex&C_MASK_SMALL]);
  }//+++
  
  public final int ccGetASIntegerValue(int pxIndex){
    return VcConst.ccParseIntegerString(cmAS[pxIndex&C_MASK_SMALL]);
  }//+++
  
  //===
  
  public final float ccGetAGFloatValue(int pxIndex){
    return VcConst.ccParseFloatString(cmAG[pxIndex&C_MASK_BIG]);
  }//+++
  
  public final float ccGetFRFloatValue(int pxIndex){
    return VcConst.ccParseFloatString(cmFR[pxIndex&C_MASK_SMALL]);
  }//+++
  
  public final float ccGetASFloatValue(int pxIndex){
    return VcConst.ccParseFloatString(cmAS[pxIndex&C_MASK_SMALL]);
  }//+++
  
}//***eof
