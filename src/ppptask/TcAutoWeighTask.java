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

package ppptask;

import kosui.ppplogic.ZcDelayor;
import kosui.ppplogic.ZcOffDelayTimer;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZiTimer;


public class TcAutoWeighTask extends ZcTask{
  
  public boolean
    mnWeighAutoSW,mnWeighAutoPL,
    mnWeighManualSW,mnWeighManualPL,
    
    mnASLockPL,mnASLockSW,mnASDPL,mnASDSW,
    mnAS1SW,
    //--
    cxCompressorFLG,
    cxASCanSupplyFLG,
    //--
    dcASSprayPumpAN,
    dcFRD,dcFR2,dcFR1,
    dcASD,dcAS1
  ;//...
  
  public int
    dcAGCellAD,dcFRCellAD,dcASCellAD=500
  ;//...
  
  //===
  
  private boolean cmWeighAutoFLG;
  
  private final ZcHookFlicker
    cmASDischargeHLD=new ZcHookFlicker()
  ;//...
  
  private final ZiTimer
    cmASDischargeValveDelayTM = new ZcOnDelayTimer(20),
    cmASSprayPumpDelayTM = new ZcOffDelayTimer(40)
    ;//...
  
  @Override public void ccScan(){
    
    //-- mode exchange
    if(mnWeighAutoSW){cmWeighAutoFLG=true;}
    if(mnWeighManualSW){cmWeighAutoFLG=false;}
    mnWeighAutoPL=cmWeighAutoFLG;
    mnWeighManualPL=!cmWeighAutoFLG;
    
    //-- discharge hook
    cmASDischargeHLD.ccHook(mnASDSW,cmWeighAutoFLG);
    
    //-- auto flag
    boolean lpASDischargeFLG=false;
    boolean lpAS1weighFLG=false;
    
    //-- output
    
    //-- output ** as
    boolean lpASDischageDUM=cmWeighAutoFLG?lpASDischargeFLG:
      cmASDischargeHLD.ccIsHooked();
    cmASDischargeValveDelayTM.ccAct(lpASDischageDUM);
    cmASSprayPumpDelayTM.ccAct(lpASDischageDUM);
    dcASD=cmASDischargeValveDelayTM.ccIsUp();
    dcASSprayPumpAN=cmASSprayPumpDelayTM.ccIsUp();
    mnASDPL=lpASDischageDUM;
    
    dcAS1=cmWeighAutoFLG?lpAS1weighFLG:
      mnAS1SW;
    
  }//+++

  //===
  
  private final ZiTimer
    simASCellChargeDelay = new ZcDelayor(10, 5),
    simASCellDischargeDelay = new ZcDelayor(5, 30)
  ;//...
  
  @Override public void ccSimulate(){
    
    //-- cell ** as
    simASCellChargeDelay.ccAct(cxCompressorFLG&&cxASCanSupplyFLG&&dcAS1);
    simASCellDischargeDelay.ccAct(cxCompressorFLG&&dcASD&&dcASSprayPumpAN);
    if(simASCellChargeDelay.ccIsUp())
      {dcASCellAD+=dcASCellAD<3602?sysOwner.random(3, 6):0;}
    if(simASCellDischargeDelay.ccIsUp())
      {dcASCellAD-=dcASCellAD>398?sysOwner.random(5, 10):0;}
    
    //-- gate
    
  }//+++
  
  //===
  
  @Deprecated public final boolean ccGetStatus(){
    return cmASDischargeHLD.ccIsHooked();
  }//+++
  
  

}//***eof
