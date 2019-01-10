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
import pppmain.MainLogicController;
import pppmain.MainSketch;
import static processing.core.PApplet.ceil;

public class TcMainTask extends ZcTask{
    
  private static TcMainTask self;
  private TcMainTask(){}//++!
  public static TcMainTask ccGetReference(){
    if(self==null){self=new TcMainTask();}
    return self;
  }//++!
  
  //===
  public boolean
    //--
    mnASSupplyPumpSW,mnASSupplyPumpPL,
    mnMixerMoterSW,mnMixerMoterPL,
    mnVCompressorSW,mnVCompressorPL,
    //--
    dcASSupplyPumpAN,dcMixerAN,
    dcVCompressorAN
  ;//...
  
  public int
    dcCT6,dcCT13
  ;//...
  
  //===
  
  private final ZcHookFlicker
    cmASSupplyPumpHLD = new ZcHookFlicker(),
    cmMixerMotorHLD = new ZcHookFlicker(),
    cmVCompressorHLD = new ZcHookFlicker()
  ;//...
  
  private final ZcTimer
    cmMixerMoterSDTM=new ZcOnDelayTimer(40),
    cmVCompressorSDTM=new ZcOnDelayTimer(40)
  ;//...
  
  @Override public void ccScan(){
    
    //-- motor power
    cmMixerMoterSDTM.ccAct(cmMixerMotorHLD.ccHook(mnMixerMoterSW));
    dcMixerAN=cmMixerMoterSDTM.ccIsUp();
    mnMixerMoterPL=cmMixerMotorHLD.ccIsHooked()
      &&(sysOneSecondFLK||dcMixerAN);
    
    cmVCompressorSDTM.ccAct(cmVCompressorHLD.ccHook(mnVCompressorSW));
    dcVCompressorAN=cmVCompressorSDTM.ccIsUp();
    mnVCompressorPL=cmVCompressorHLD.ccIsHooked()
      &&(sysOneSecondFLK||dcVCompressorAN);
    
    cmASSupplyPumpHLD.ccHook(mnASSupplyPumpSW);
    dcASSupplyPumpAN=cmASSupplyPumpHLD.ccIsHooked();
    mnASSupplyPumpPL=dcASSupplyPumpAN;
  
  }//+++

  @Override public void ccSimulate(){
    
    dcCT6=!dcMixerAN?0:ceil(
      (TcAutoWeighTask.ccGetReference().mnMixerHasMixturePL?3250f:2250f)
      +sysOwner.random(-100,100));
    
    dcCT13=!dcVCompressorAN?0:ceil(
      2950f
      +sysOwner.random(-100,100));
    
  }//+++
  
}//***eof
