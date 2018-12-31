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
    //--
    mnVExfanMotorSW, mnVExfanMotorPL,
    mnAPBlowerSW,mnAPBlowerPL,
    mnVEXFOPSW,mnVEXFCLSW,mnVEXFATSW,mnVEXFATPL,
    mnVBOPSW,mnVBCLSW,mnVBATSW,mnVBATPL,
    //--
    dcVExfanAN,dcVEFCLLS,dcVEFOPLS,dcVEFOPRY,dcVEFCLRY,
    dcVBurnerFanAN,dcVBCLLS,dcVBOPLS,dcVBOPRY,dcVBCLRY,
    dcAPBlowerAN
  ;//...
  
  public int
    dcVDO=550,dcVBO=550
  ;//...
  
  //===
  
  private boolean cmVExfanStartLock;
  
  private final ZcHookFlicker
    cmVExfanMotorHLD = new ZcHookFlicker(),
    cmAPBlowerHLD = new ZcHookFlicker(),
    cmVEXFATHLD=new ZcHookFlicker(),
    cmVBATHLD = new ZcHookFlicker()
  ;//...
  
  private final ZcTimer
    cmVExfanMotorSDTM = new ZcOnDelayTimer(40)
  ;//...

  @Override public void ccScan(){
    
    //-- vexfan start
    //[TODO]::the closed limit problem
    cmVExfanMotorSDTM.ccAct(cmVExfanMotorHLD
      .ccHook(mnVExfanMotorSW,cmVExfanStartLock));
    dcVExfanAN=cmVExfanMotorSDTM.ccIsUp();
    mnVExfanMotorPL=cmVExfanMotorHLD.ccGetIsHooked()
      &&(sysOneSecondFLK||dcVExfanAN);
    cmVExfanStartLock=dcVExfanAN?false:!dcVEFCLLS;
    
    //-- main unit blower start
    dcAPBlowerAN=cmAPBlowerHLD.ccHook(mnAPBlowerSW,!dcVExfanAN);
    mnAPBlowerPL=dcAPBlowerAN;
    
    //-- burner ignit stepp
    
    //-- vefx damper control
    boolean lpVEXFAutoOpenFLG=dcVExfanAN;//..[TOIMP]::
    boolean lpVEXFAutoCloseFLG=!dcVExfanAN;//..[TOIMP]::
    mnVEXFATPL=cmVEXFATHLD.ccHook(mnVEXFATSW);
    dcVEFOPRY=(!dcVEFOPLS)&&(
      (!mnVEXFATPL&&mnVEXFOPSW)||
      ( mnVEXFATPL&&lpVEXFAutoOpenFLG)
    );
    
    dcVEFCLRY=(!dcVEFCLLS)&&(
      (!mnVEXFATPL&&mnVEXFCLSW)||
      ( mnVEXFATPL&&lpVEXFAutoCloseFLG)
    );
    
    //-- vefx damper control
    boolean lpVBurnerAutoOpenFLG=false;//..[TOIMP]::
    boolean lpVBurnerAutoCloseFLG=false;//..[TOIMP]::
    mnVBATPL=cmVBATHLD.ccHook(mnVBATSW);
    dcVBOPRY=(!dcVBOPLS)&&(
      (!mnVBATPL&&mnVBOPSW)||
      ( mnVBATPL&&lpVBurnerAutoOpenFLG)
    );
    
    dcVBCLRY=(!dcVBCLLS)&&(
      (!mnVBATPL&&mnVBCLSW)||
      ( mnVBATPL&&lpVBurnerAutoCloseFLG)
    );
    
    
    
    
    
    
  }//+++
  
  //===
  
  @Override public void ccSimulate(){
    
    //-- vefx damper
    if(dcVEFOPRY){dcVDO+=dcVDO<3600?16:0;}
    if(dcVEFCLRY){dcVDO-=dcVDO>400?16:0;}
    dcVEFCLLS=dcVDO<450;
    dcVEFOPLS=dcVDO>3550;
    
    //-- vb damper
    if(dcVBOPRY){dcVBO+=dcVBO<3600?16:0;}
    if(dcVBCLRY){dcVBO-=dcVBO>400?16:0;}
    dcVBCLLS=dcVBO<450;
    dcVBOPLS=dcVBO>3550;
    
  }//+++
  
}//***eof
