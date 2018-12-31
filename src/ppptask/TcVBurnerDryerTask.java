/*
 * Copyright (C) 2018 keypad
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

import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcTimer;

public class TcVBurnerDryerTask extends ZcTask{
  
  public boolean 
    mnVExfanMotorSW, mnVExfanMotorPL,
    mnAPBlowerSW,mnAPBlowerPL,
    //--
    dcVExfanAN,dcVEFCLLS,dcVEFOPLS,
    dcAPBlowerAN
  ;//...
  
  private final ZcHookFlicker
    cmVExfanMotorHLD = new ZcHookFlicker(),
    cmAPBlowerHLD = new ZcHookFlicker()
  ;//...
  
  private final ZcTimer
    cmVExfanMotorSDTM = new ZcOnDelayTimer(40)
  ;//...

  @Override public void ccScan(){
    
    //-- vexfan start
    //[TODO]::the closed limit problem
    cmVExfanMotorSDTM.ccAct(cmVExfanMotorHLD.ccHook(mnVExfanMotorSW));
    dcVExfanAN=cmVExfanMotorSDTM.ccIsUp();
    mnVExfanMotorPL=cmVExfanMotorHLD.ccGetIsHooked()
      &&(sysOneSecondFLK||dcVExfanAN);
    
    //-- main unit blower start
    dcAPBlowerAN=cmAPBlowerHLD.ccHook(mnAPBlowerSW,!dcVExfanAN);
    mnAPBlowerPL=dcAPBlowerAN;
    
  }//+++
  
  //===

  @Override public void ccSimulate(){
  
  }//+++
  
}//***eof
