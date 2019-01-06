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
    //--
    mnFRLockPL,mnFRLockSW,mnFRDPL,mnFRDSW,
    mnFR2SW,mnFR1SW,
    mnASLockPL,mnASLockSW,mnASDPL,mnASDSW,
    mnAS1SW,
    //--
    cxCompressorFLG,
    cxASCanSupplyFLG,cxFillerCanSupplyFLG,cxDustCanSupply,
    //--
    dcASSprayPumpAN,
    dcFRD,dcFR2,dcFR1,
    dcASD,dcAS1
  ;//...
  
  public int
    dcAGCellAD=500,dcFRCellAD=500,dcASCellAD=500
  ;//...
  
  //===
  
  private boolean cmWeighAutoFLG;
  
  private final ZcHookFlicker
    cmFRDischargeHLD=new ZcHookFlicker(),
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
    cmFRDischargeHLD.ccHook(mnFRDSW,cmWeighAutoFLG);
    cmASDischargeHLD.ccHook(mnASDSW,cmWeighAutoFLG);
    
    //-- auto flag
    boolean lpFRDischargeFLG=false;
    boolean lpFR2WeighFLG=false;
    boolean lpFR1WeighFLG=false;
    
    boolean lpASDischargeFLG=false;
    boolean lpAS1WeighFLG=false;
    
    //-- output
    
    //-- output ** fr
    dcFRD=cmWeighAutoFLG?lpFRDischargeFLG:cmFRDischargeHLD.ccIsHooked();
    dcFR1=cmWeighAutoFLG?lpFR1WeighFLG:mnFR1SW;
    dcFR2=cmWeighAutoFLG?lpFR2WeighFLG:mnFR2SW;
    mnFRDPL=dcFRD;
    
    //-- output ** as
    boolean lpASDischageDUM=cmWeighAutoFLG?lpASDischargeFLG:
      cmASDischargeHLD.ccIsHooked();
    cmASDischargeValveDelayTM.ccAct(lpASDischageDUM);
    cmASSprayPumpDelayTM.ccAct(lpASDischageDUM);
    mnASDPL=lpASDischageDUM;
    dcASD=cmASDischargeValveDelayTM.ccIsUp();
    dcASSprayPumpAN=cmASSprayPumpDelayTM.ccIsUp();
    dcAS1=cmWeighAutoFLG?lpAS1WeighFLG:mnAS1SW;
    
  }//+++

  //===
  
  private final ZiTimer
    simFRCellChargeDelay = new ZcDelayor(10, 5),
    simFRCellDischargeDelay = new ZcDelayor(5, 10),
    simASCellChargeDelay = new ZcDelayor(10, 5),
    simASCellDischargeDelay = new ZcDelayor(5, 30)
  ;//...
  
  @Override public void ccSimulate(){
    
    //-- cell ** fr
    simFRCellChargeDelay.ccAct(cxCompressorFLG
      &&(  (cxFillerCanSupplyFLG&&dcFR1)
         ||(cxDustCanSupply     &&dcFR2))
    );
    simFRCellDischargeDelay.ccAct(cxCompressorFLG&&dcFRD);
    if(simFRCellChargeDelay.ccIsUp())
      {dcFRCellAD+=dcFRCellAD<3602?sysOwner.random(3, 6):0;}
    if(simFRCellDischargeDelay.ccIsUp())
      {dcFRCellAD-=dcFRCellAD>398?sysOwner.random(5, 10):0;}
    
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
  
  public final boolean cyUsingAG(int pxMatt){
    switch(pxMatt){
      //[TOIMP]::
      default:return false;
    }//..?
  }//+++
  
  public final boolean cyUsingFR(int pxMatt){
    switch(pxMatt){
      case 1:return simFRCellChargeDelay.ccIsUp()&&dcFR1;
      case 2:return simFRCellChargeDelay.ccIsUp()&&dcFR2;
      default:return false;
    }//..?
  }//+++
  
  //[TEST]::
  @Deprecated public final boolean ccGetStatus(){
    return false;
  }//+++
  

}//***eof
