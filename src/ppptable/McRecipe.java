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

public class McRecipe extends McCategoryStringRecord{
  
  public int cmID=0;
  public String cmName="<>";
  //[TODO]::dry time? wet time?
  
  //===
  
  public final void ccSetup(float[] pxAG, float[] pxFR, float[] pxAS){
    if(pxAG==null){return;}
    if(pxFR==null){return;}
    if(pxAS==null){return;}
    //[TOIMP]::AGX?FRX?6->1?6->0? com'on, think about it !!
    for(int i=0;i<pxAG.length;i++){ccSetAGFloatValue(i, pxAG[i]);}
    for(int i=0;i<pxFR.length;i++){ccSetFRFloatValue(i, pxFR[i]);}
    for(int i=0;i<pxAS.length;i++){ccSetASFloatValue(i, pxAS[i]);}
  }//++!
  
  public final void ccSetup(
    int pxID, String pxName,
    float[] pxAG, float[] pxFR, float[] pxAS
  ){
    cmID=pxID;
    cmName=pxName;
    ccSetup(pxAG, pxFR, pxAS);
  }//++!
  
}//***eof
