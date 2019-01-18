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

public class McCategoryStringRecord{
  
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
  
  public final void ccSetAGValue(int pxIndex,int pxValue){
    cmAG[pxIndex&0x07]=Integer.toString(pxValue);
  }//+++
  
  public final void ccSetFRValue(int pxIndex,int pxValue){
    cmFR[pxIndex&0x03]=Integer.toString(pxValue);
  }//+++
  
  public final void ccSetASValue(int pxIndex,int pxValue){
    cmAS[pxIndex&0x03]=Integer.toString(pxValue);
  }//+++
  
  //===
  
  public final int ccGetAGIntegerValue(int pxIndex){
    return VcConst.ccParseIntegerString(cmAG[pxIndex&0x07]);
  }//+++
  
  public final int ccGetFRIntegerValue(int pxIndex){
    return VcConst.ccParseIntegerString(cmFR[pxIndex&0x03]);
  }//+++
  
  public final int ccGetASIntegerValue(int pxIndex){
    return VcConst.ccParseIntegerString(cmAS[pxIndex&0x03]);
  }//+++
  
  //===
  
  public final float ccGetAGFloatValue(int pxIndex){
    return VcConst.ccParseFloatString(cmAG[pxIndex&0x07]);
  }//+++
  
  public final float ccGetFRFloatValue(int pxIndex){
    return VcConst.ccParseFloatString(cmFR[pxIndex&0x03]);
  }//+++
  
  public final float ccGetASFloatValue(int pxIndex){
    return VcConst.ccParseFloatString(cmAS[pxIndex&0x03]);
  }//+++
  
}//***eof
