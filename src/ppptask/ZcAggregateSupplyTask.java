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
    mnAGSupplyStartSW,
    mnAGSUpplyStartPL,
    //--
    sysOneSecondPLS,sysOneSecondFlicker,
    //--
    dcScreenAN,dcHotElevatorAN,dcVDryerAN,
    dcVInclineBelconAN,dcVHorizontalBelconAN
  ;//...
  
  //===
  
  private boolean
    cmAGSupplyStartHLD
  ;//...
  
  private int 
    cmAGStepper=0
  ;//...
  
  private final ZcTimer cmAGSupplyStartPLS = new ZcOnDelayPulser(3);

  @Override public void ccScan(){
    
    //-- ag supply chain
    cmAGSupplyStartPLS.ccAct(mnAGSupplyStartSW);
    if(cmAGSupplyStartPLS.ccIsUp()){cmAGSupplyStartHLD=!cmAGSupplyStartHLD;}
    if(cmAGSupplyStartHLD)
      {if(cmAGStepper<8 && sysOneSecondPLS){cmAGStepper++;}}
    else
      {cmAGStepper=0;}
    dcScreenAN=cmAGStepper>2;
    dcHotElevatorAN=cmAGStepper>3;
    dcVDryerAN=cmAGStepper>4;
    dcVInclineBelconAN=cmAGStepper>5;
    dcVHorizontalBelconAN=cmAGStepper>6;
    if(cmAGStepper>0)
      {mnAGSUpplyStartPL=dcVHorizontalBelconAN?true:sysOneSecondFlicker;}
    else
      {mnAGSUpplyStartPL=false;}
    
    
    
    

  }//+++

  @Override public void ccSimulate(){;}//+++
  
}//***eof
