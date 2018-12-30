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

import kosui.ppplogic.ZcOnDelayPulser;
import kosui.ppplogic.ZcTimer;
import kosui.ppplogic.ZiTask;

public class ZcAggregateSupplyTask implements ZiTask{
  
  public boolean 
    //--
    mnAGSupplyStartSW,mnAGSUpplyStartPL,
    mnVFeederStartSW,mnVFeederStartPL,
    //--
    sysOneSecondPLS,sysOneSecondFLK,
    //--
    dcScreenAN,dcHotElevatorAN,dcVDryerAN,
    dcVInclineBelconAN,dcVHorizontalBelconAN,
    dcVFAN01,dcVFAN02,dcVFAN03,dcVFAN04,dcVFAN05,dcVFAN06
  ;//...
  
  //===
  
  
  private final ZcHookFlicker
    cmAGSupplyHLD = new ZcHookFlicker(),
    cmVFeederStartHLD = new ZcHookFlicker()
  ;//...
  
  private final ZcChainStepper
    cmAGSupplySTP= new  ZcChainStepper(8),
    cmVFeederSTP= new ZcChainStepper(10)
  ;//...
  

  @Override public void ccScan(){
    
    //-- ag supply chain
    if(cmAGSupplyHLD.ccHook(mnAGSupplyStartSW))
      {cmAGSupplySTP.ccStep(sysOneSecondPLS);}
    else
      {cmAGSupplySTP.ccStop(true);}
    dcScreenAN=cmAGSupplySTP.ccIsAt(2);
    dcHotElevatorAN=cmAGSupplySTP.ccIsAt(3);
    dcVDryerAN=cmAGSupplySTP.ccIsAt(4);
    dcVInclineBelconAN=cmAGSupplySTP.ccIsAt(5);
    dcVHorizontalBelconAN=cmAGSupplySTP.ccIsAt(6);
    if(cmAGSupplySTP.ccIsStepping())
      {mnAGSUpplyStartPL=dcVHorizontalBelconAN?true:sysOneSecondFLK;}
    else
      {mnAGSUpplyStartPL=false;}
    
    //-- feeder chain
    if(cmVFeederStartHLD.ccHook(mnVFeederStartSW&&dcVHorizontalBelconAN))
      {cmVFeederSTP.ccStep(sysOneSecondPLS);}
    else
      {cmVFeederSTP.ccStop(true);}
    if(!dcVHorizontalBelconAN){
      cmVFeederSTP.ccStop(true);
      cmVFeederStartHLD.ccSetIsHooked(false);
    }
    dcVFAN01=cmVFeederSTP.ccIsAt(2);
    dcVFAN02=cmVFeederSTP.ccIsAt(3);
    dcVFAN03=cmVFeederSTP.ccIsAt(4);
    dcVFAN04=cmVFeederSTP.ccIsAt(5);
    dcVFAN05=cmVFeederSTP.ccIsAt(6);
    dcVFAN06=cmVFeederSTP.ccIsAt(7);
    if(cmVFeederSTP.ccIsStepping())
      {mnVFeederStartPL=dcVFAN06?true:sysOneSecondFLK;}
    else
      {mnVFeederStartPL=false;}
    
  }//+++

  @Override public void ccSimulate(){;}//+++
  
}//***eof
