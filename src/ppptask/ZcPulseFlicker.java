/*
 * Copyright (C) 2019 2053
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

import kosui.ppplogic.ZiTimer;

//[TODO]::may add to kosui??
public class ZcPulseFlicker implements ZiTimer{
  
  private int cmSpan,cmJudge,cmCurrent;

  public ZcPulseFlicker(int pxCount){
    int lpLimit=pxCount<=3?3:pxCount;
    cmSpan=lpLimit;
    cmJudge=lpLimit-1;
    cmCurrent=lpLimit;
  }//+++
  
  @Override
  public void ccAct(boolean pxAct){
    if(pxAct){
      cmCurrent++;
      if(cmCurrent>cmSpan){cmCurrent=0;}
    }else{
      cmCurrent=0; 
    }
  }//+++

  @Override
  public boolean ccIsUp(){return cmCurrent==cmJudge;}//+++
  
}//***eof
