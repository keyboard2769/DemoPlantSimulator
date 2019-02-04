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

public final class TcMainTask extends ZcTask{
    
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
    dcTH3=1672,//..[TODO]::
    dcCT6,dcCT13,dcCT12
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
  
  //===
  private final ZcMotor
    simM6 = new ZcMotor(),
    simM13 = new ZcMotor(),
    simM12 = new ZcMotor()
  ;//...
  
  
  @Override public void ccSimulate(){
    
    dcCT6=simM6.ccContact(dcMixerAN, 
      TcAutoWeighTask.ccGetReference().mnMixerHasMixturePL?
        0.66f:0.77f
    );
    
    dcCT13=simM13.ccContact(dcVCompressorAN, 0.70f);
    
    dcCT12=simM12.ccContact(dcASSupplyPumpAN,
      TcAutoWeighTask.ccGetReference().dcAS1?
      0.66f:0.60f
    );
    
  }//+++
  
}//***eof
