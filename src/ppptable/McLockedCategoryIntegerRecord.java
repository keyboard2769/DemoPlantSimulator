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

import static processing.core.PApplet.print;
import static kosui.ppputil.VcConst.C_V_NEW_LINE;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class McLockedCategoryIntegerRecord{
  
  /**
   * you may have to change it manually
   */
  protected static final int 
    C_MASK_BIG   = 0x07,
    C_MASK_SMALL = 0x03
  ;//...
  
  //===
  
  private final AtomicIntegerArray
    cmAG = new AtomicIntegerArray(new int[]{0,0,0,0, 0,0,0,0}),
    cmFR = new AtomicIntegerArray(new int[]{0,0,0,0}),
    cmAS = new AtomicIntegerArray(new int[]{0,0,0,0})
  ;//...
  
  //===
  
  public final void ccSet(McLockedCategoryIntegerRecord pxTarget){
    for(int i=0;i<cmAG.length();i++){cmAG.set(i, pxTarget.ccGetAG(i));}
    for(int i=0;i<cmFR.length();i++){cmFR.set(i, pxTarget.ccGetFR(i));}
    for(int i=0;i<cmAS.length();i++){cmAS.set(i, pxTarget.ccGetAS(i));}
  }//+++
  
  public final void ccSetAG(int pxIndex, int pxValue){
    cmAG.set(pxIndex&C_MASK_BIG, pxValue);
  }//+++
  
  public final void ccSetFR(int pxIndex, int pxValue){
    cmFR.set(pxIndex&C_MASK_SMALL, pxValue);
  }//+++
  
  public final void ccSetAS(int pxIndex, int pxValue){
    cmAS.set(pxIndex&C_MASK_SMALL, pxValue);
  }//+++
  
  //===
  
  public final void ccClearAG(){
    for(int i=0;i<cmAG.length();i++){cmAG.set(i, 0);}
  }//+++
  
  public final void ccClearFR(){
    for(int i=0;i<cmFR.length();i++){cmFR.set(i, 0);}
  }//+++
  
  public final void ccClearAS(){
    for(int i=0;i<cmAS.length();i++){cmAS.set(i, 0);}
  }//+++
  
  public final void ccClearAll(){
    ccClearAG();
    ccClearFR();
    ccClearAS();
  }//+++
  
  //===
  
  public final int ccGetAG(int pxIndex)
    {return cmAG.get(pxIndex&C_MASK_BIG);}//+++
  public final int ccGetFR(int pxIndex)
    {return cmFR.get(pxIndex&C_MASK_SMALL);}//+++
  public final int ccGetAS(int pxIndex)
    {return cmAS.get(pxIndex&C_MASK_SMALL);}//+++
  
  //===
  
  private int ssGetMax(AtomicIntegerArray pxSource){
    int lpRes=0;
    for(int i=1;i<pxSource.length();i++){
      int lpPrev=pxSource.get(i-1);
      int lpNext=pxSource.get(i);
      int lpInner=lpPrev>lpNext?lpPrev:lpNext;
      lpRes=lpInner>lpRes?lpInner:lpRes;
    }//..~
    return lpRes;
  }//+++
  public final int ccGetMaxAG(){return ssGetMax(cmAG);}//+++
  public final int ccGetMaxFR(){return ssGetMax(cmFR);}//+++
  public final int ccGetMaxAS(){return ssGetMax(cmAS);}//+++
  
  //=== 
  
  @Deprecated public final void testReadup(){
    
    print(C_V_NEW_LINE+"ag ");
    for(int i=0;i<cmAG.length();i++)
      {print(" - ");print(Integer.toString(cmAG.get(i)));}
    
    print(C_V_NEW_LINE+"fr ");
    for(int i=0;i<cmFR.length();i++)
      {print(" - ");print(Integer.toString(cmFR.get(i)));}
    
    print(C_V_NEW_LINE+"as ");
    for(int i=0;i<cmAS.length();i++)
      {print(" - ");print(Integer.toString(cmAS.get(i)));}
    
    print(C_V_NEW_LINE+"<<<"+C_V_NEW_LINE);
    
  }//+++
  
}//***eof
