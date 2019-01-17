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

public class McBaseCategoryStringRecord{
  
  public String[] cmAG={
    "0","0","0","0", "0","0","0","0"
  } ;
  public String[] cmFR={
    "0","0","0","0"
  } ;
  public String[] cmAS={
    "0","0","0","0"
  } ;
    
  public final void ccSetAGValue(int pxIndex,int pxValue){
    cmAG[pxIndex&0x07]=Integer.toString(pxValue);
  }//+++
  
  public final void ccSetFRValue(int pxIndex,int pxValue){
    cmFR[pxIndex&0x03]=Integer.toString(pxValue);
  }//+++
  
  public final void ccSetASValue(int pxIndex,int pxValue){
    cmAS[pxIndex&0x03]=Integer.toString(pxValue);
  }//+++
  
  
}//***eof
