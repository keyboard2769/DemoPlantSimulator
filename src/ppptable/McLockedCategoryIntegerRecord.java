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

import java.util.concurrent.atomic.AtomicIntegerArray;

public class McLockedCategoryIntegerRecord{
  
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
    cmAG.set(pxIndex&0x7, pxValue);
  }//+++
  
  public final void ccSetFR(int pxIndex, int pxValue){
    cmFR.set(pxIndex&0x7, pxValue);
  }//+++
  
  public final void ccSetAS(int pxIndex, int pxValue){
    cmAS.set(pxIndex&0x7, pxValue);
  }//+++
  
  //===
  
  public final int ccGetAG(int pxIndex){return cmAG.get(pxIndex&0x7);}//+++
  public final int ccGetFR(int pxIndex){return cmFR.get(pxIndex&0x3);}//+++
  public final int ccGetAS(int pxIndex){return cmAS.get(pxIndex&0x3);}//+++
  
  //=== 
  
  @Deprecated public final void testReadup(){
    
    print("\n ag ");
    for(int i=0;i<cmAG.length();i++)
      {print("---");print(Integer.toString(cmAG.get(i)));}
    
    
    print("\n fr ");
    for(int i=0;i<cmFR.length();i++)
      {print("---");print(Integer.toString(cmFR.get(i)));}
    
    
    print("\n as ");
    for(int i=0;i<cmAS.length();i++)
      {print("---");print(Integer.toString(cmAS.get(i)));}
    
  }//+++
  
}//***eof
