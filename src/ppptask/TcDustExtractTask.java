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

public class TcDustExtractTask extends ZcTask{
  
  public boolean
    //--
    dcCoarseScrewAN,
    //--
    cxBagPulseStartFLG
  ;//...
  
  public int
    mnBagPulseCurrentCount
  ;//...
  
  private int
    cmBagPulseRoller=0,cmBagPulseRollerJudge=20,
    cmBagPulseTotal=20,cmBagPulseCurrentCount
  ;//...

  @Override public void ccScan(){
    
    //-- rolls bag pulse
    if(cxBagPulseStartFLG){cmBagPulseRoller++;}cmBagPulseRoller&=0x1F;
    if(cmBagPulseRoller==cmBagPulseRollerJudge){cmBagPulseCurrentCount++;}
    if(cmBagPulseCurrentCount>=cmBagPulseTotal){cmBagPulseCurrentCount=1;}
    mnBagPulseCurrentCount=
      cmBagPulseRoller>cmBagPulseRollerJudge?cmBagPulseCurrentCount:0;
    if(!cxBagPulseStartFLG){mnBagPulseCurrentCount=0;}
    
    //-- 
    dcCoarseScrewAN=cxBagPulseStartFLG;
    
    
  }//+++
  
  //[TODO]::public final void ccSetBagPulserTimer(){}
  public final void ccSetBagFilterSize(int pxSize){
    cmBagPulseTotal=pxSize;
  }//+++
  
  //===

  @Override public void ccSimulate(){
  
  }//+++
  
}//***eof
