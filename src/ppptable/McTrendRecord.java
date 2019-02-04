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

import java.util.Arrays;
import kosui.ppputil.VcConst;

public class McTrendRecord{
  
  public static final String[] C_TITLE={
    "time",
    "chute","sand","bag","VB","VE"
  };
  
  //===
  
  private String cmTimeStamp;
  private final int[] cmDesTemp;
  
  public McTrendRecord(){
    cmDesTemp=new int[5];
    cmTimeStamp="%t%";
    Arrays.fill(cmDesTemp, 0);
  }//+++

  public McTrendRecord(
    int pxChute, int pxSand, int pxBag,
    int pxVB, int pxVE
  ){
    cmDesTemp=new int[5];
    ccSetup(pxChute, pxSand, pxBag, pxVB, pxVE);
  }//+++
  
  //===
  
  public final void ccSetup(
    int pxChute, int pxSand, int pxBag,
    int pxVB, int pxVE
  ){
    cmTimeStamp=VcConst.ccTimeStamp("--", true, true, true);
    cmDesTemp[0]=pxChute;
    cmDesTemp[1]=pxSand;
    cmDesTemp[2]=pxBag;
    cmDesTemp[3]=pxVB;
    cmDesTemp[4]=pxVE;
  }//+++
  
  //===
  
  //[TODO]::ccSetString
  
  public final String ccGetString(int pxIndex){
    if(pxIndex<=0 || pxIndex>=C_TITLE.length){return cmTimeStamp;}
    else{return Integer.toString(cmDesTemp[pxIndex-1]);}//..?
  }//+++
  
}//***eof
