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

import kosui.ppputil.VcConst;

public class McAutoWeighRecord extends McCategoryStringBundle{
  
  public static final String[] C_TITLE={
    "time","m-temp","total",
    "AG6","AG5","AG4","AG3","AG2","AG1",
    "FR2","FR1",
    "AS1"
  };
  
  //===
  
  public final String cmTimeStamp=VcConst.ccTimeStamp(" ",false,true,true);
  public int cmMixerTemp,cmTotalKG;
  
  public final void ccSetupMixerValue(int pxTemperature, int pxTotal){
    cmMixerTemp=pxTemperature;
    cmTotalKG=pxTotal;
  }//+++
  
  //===
  
  //[NOTYET]::ccSetString(int pxIndex){}
  
  public final String ccGetString(int pxIndex){
    switch(pxIndex){
      case  1:return Integer.toString(cmMixerTemp);
      case  2:return Integer.toString(cmTotalKG);
      case  3:return cmAG[6];
      case  4:return cmAG[5];
      case  5:return cmAG[4];
      case  6:return cmAG[3];
      case  7:return cmAG[2];
      case  8:return cmAG[1];
      case  9:return cmFR[2];
      case 10:return cmFR[1];
      case 11:return cmAS[1];
      default:return cmTimeStamp;
    }//..?
  }//+++

}//***eof
