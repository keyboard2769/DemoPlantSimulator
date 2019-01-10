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
    
  private static TcAutoWeighTask self;
  private TcAutoWeighTask(){}//++!
  public static TcAutoWeighTask ccGetReference(){
    if(self==null){self=new TcAutoWeighTask();}
    return self;
  }//++!
  
  //===
  
  public boolean
    mnWeighAutoSW,mnWeighAutoPL,
    mnWeighManualSW,mnWeighManualPL,
    mnMixerGateAutoSW,mnMixerGateHoldSW,mnMixerGateOpenSW,
    mnMixerGateAutoPL,mnMixerGateHoldPL,mnMixerGateOpenPL,
    mnMixerHasMixturePL,
    //--
    mnAGLockPL,mnAGLockSW,mnAGDPL,mnAGDSW,
    mnAG6SW,mnAG5SW,mnAG4SW,mnAG3SW,mnAG2SW,mnAG1SW,
    mnFRLockPL,mnFRLockSW,mnFRDPL,mnFRDSW,
    mnFR2SW,mnFR1SW,
    mnASLockPL,mnASLockSW,mnASDPL,mnASDSW,
    mnAS1SW,
    //--
    cmAG6W,cmAG5W,cmAG4W,cmAG3W,cmAG2W,cmAG1W,
    //--
    cxCompressorFLG,
    cxASCanSupplyFLG,cxFillerCanSupplyFLG,cxDustCanSupply,
    cxAG6CanSupplyFLG,cxAG5CanSupplyFLG,cxAG4CanSupplyFLG,
    cxAG3CanSupplyFLG,cxAG2CanSupplyFLG,cxAG1CanSupplyFLG,
    //--
    dcMXD,dcMOL,dcMCL,
    //--
    dcASSprayPumpAN,
    dcAGD,
    dcAG6OMV,dcAG5OMV,dcAG4OMV,dcAG3OMV,dcAG2OMV,dcAG1OMV,
    dcAG6CMV,dcAG5CMV,dcAG4CMV,dcAG3CMV,dcAG2CMV,dcAG1CMV,
    dcAG6MAS,dcAG5MAS,dcAG4MAS,dcAG3MAS,dcAG2MAS,dcAG1MAS,
    dcFRD,dcFR2,dcFR1,
    dcASD,dcAS1
  ;//...
  
  public int
    dcTH6=600,
    dcAGCellAD=500,dcFRCellAD=500,dcASCellAD=500
  ;//...
  
  //===
  
  private boolean 
    cmWeighAutoFLG,
    cmMixerGateAutoFLG,cmMixerGateOpenFLG
  ;//...
  
  private final ZcHookFlicker
    cmAGDischargeHLD=new ZcHookFlicker(),
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
    cmAGDischargeHLD.ccHook(mnAGDSW,cmWeighAutoFLG);
    cmFRDischargeHLD.ccHook(mnFRDSW,cmWeighAutoFLG);
    cmASDischargeHLD.ccHook(mnASDSW,cmWeighAutoFLG);
    
    //-- auto flag
    boolean lpMixerDischargeFLG=false;
    
    boolean lpAGDischargeFLG=false;
    
    //--
    boolean lpAG6OpenFLG=false;
    boolean lpAG6CloseFLG=false;
    
    boolean lpAG5OpenFLG=false;
    boolean lpAG5CloseFLG=false;
    
    boolean lpAG4OpenFLG=false;
    boolean lpAG4CloseFLG=false;
    
    boolean lpAG3OpenFLG=false;
    boolean lpAG3CloseFLG=false;
    
    boolean lpAG2OpenFLG=false;
    boolean lpAG2CloseFLG=false;
    
    boolean lpAG1OpenFLG=false;
    boolean lpAG1CloseFLG=false;
    
    //--
    boolean lpFRDischargeFLG=false;
    boolean lpFR2WeighFLG=false;
    boolean lpFR1WeighFLG=false;
    
    boolean lpASDischargeFLG=false;
    boolean lpAS1WeighFLG=false;
    
    //-- output
    
    //-- output ** ag
    dcAGD=cmWeighAutoFLG?lpAGDischargeFLG:cmAGDischargeHLD.ccIsHooked();
    mnAGDPL=dcAGD;
    
    dcAG6OMV=cmWeighAutoFLG?lpAG6OpenFLG:mnAG6SW;
    dcAG6CMV=cmWeighAutoFLG?lpAG6CloseFLG:!mnAG6SW;
    
    dcAG5OMV=cmWeighAutoFLG?lpAG5OpenFLG:mnAG5SW;
    dcAG5CMV=cmWeighAutoFLG?lpAG5CloseFLG:!mnAG5SW;
    
    dcAG4OMV=cmWeighAutoFLG?lpAG4OpenFLG:mnAG4SW;
    dcAG4CMV=cmWeighAutoFLG?lpAG4CloseFLG:!mnAG4SW;
    
    dcAG3OMV=cmWeighAutoFLG?lpAG3OpenFLG:mnAG3SW;
    dcAG3CMV=cmWeighAutoFLG?lpAG3CloseFLG:!mnAG3SW;
    
    dcAG2OMV=cmWeighAutoFLG?lpAG2OpenFLG:mnAG2SW;
    dcAG2CMV=cmWeighAutoFLG?lpAG2CloseFLG:!mnAG2SW;
    
    dcAG1OMV=cmWeighAutoFLG?lpAG1OpenFLG:mnAG1SW;
    dcAG1CMV=cmWeighAutoFLG?lpAG1CloseFLG:!mnAG1SW;
    
    //[TODO]::fixit:
    cmAG6W=simAG6.ccIsNotClosed();
    cmAG5W=simAG5.ccIsNotClosed();
    cmAG4W=simAG4.ccIsNotClosed();
    cmAG3W=simAG3.ccIsNotClosed();
    cmAG2W=simAG2.ccIsNotClosed();
    cmAG1W=simAG1.ccIsNotClosed();
    
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
    
    //-- output ** mixergate
    if(mnMixerGateAutoSW){cmMixerGateAutoFLG=true;cmMixerGateOpenFLG=false;}
    if(mnMixerGateHoldSW){cmMixerGateAutoFLG=false;cmMixerGateOpenFLG=false;}
    if(mnMixerGateOpenSW){cmMixerGateAutoFLG=false;cmMixerGateOpenFLG=true;}
    mnMixerGateAutoPL=cmMixerGateAutoFLG;
    mnMixerGateOpenPL=cmMixerGateOpenFLG;
    mnMixerGateHoldPL=(!cmMixerGateAutoFLG)&&(!cmMixerGateOpenFLG);
    dcMXD=cmMixerGateAutoFLG?lpMixerDischargeFLG:cmMixerGateOpenFLG;
    
    //-- output ** mixer has mixer pl
    if(dcMCL&&(dcFRD||dcAGD)){mnMixerHasMixturePL=true;}
    if(dcMOL){mnMixerHasMixturePL=false;}
    
  }//+++

  //===
  
  private final ZcCylinderGateModel
    simMixerGate=new ZcCylinderGateModel(),
    simAG6=new ZcCylinderGateModel(),
    simAG5=new ZcCylinderGateModel(),
    simAG4=new ZcCylinderGateModel(),
    simAG3=new ZcCylinderGateModel(),
    simAG2=new ZcCylinderGateModel(),
    simAG1=new ZcCylinderGateModel()
  ;//...
  
  private final ZiTimer
    simAGCellDischargeDelay = new ZcDelayor(5, 10),
    simFRCellChargeDelay = new ZcDelayor(10, 5),
    simFRCellDischargeDelay = new ZcDelayor(5, 10),
    simASCellChargeDelay = new ZcDelayor(10, 5),
    simASCellDischargeDelay = new ZcDelayor(5, 30)
  ;//...
  
  @Override public void ccSimulate(){
    
    //-- mixer gate
    simMixerGate.ccOpen(cxCompressorFLG&&dcMXD, 1);
    simMixerGate.ccClose(cxCompressorFLG&&!dcMXD, 1);
    dcMOL=simMixerGate.ccIsFullyOpened();
    dcMCL=simMixerGate.ccIsFullyClosed();
    
    //-- hotbin gate 
    
    //-- hotbin gate ** mv
    simAG6.ccOpen(cxCompressorFLG&&dcAG6OMV, 1);
    simAG6.ccClose(cxCompressorFLG&&dcAG6CMV, 1);
    simAG5.ccOpen(cxCompressorFLG&&dcAG5OMV, 1);
    simAG5.ccClose(cxCompressorFLG&&dcAG5CMV, 1);
    simAG4.ccOpen(cxCompressorFLG&&dcAG4OMV, 1);
    simAG4.ccClose(cxCompressorFLG&&dcAG4CMV, 1);
    simAG3.ccOpen(cxCompressorFLG&&dcAG3OMV, 1);
    simAG3.ccClose(cxCompressorFLG&&dcAG3CMV, 1);
    simAG2.ccOpen(cxCompressorFLG&&dcAG2OMV, 1);
    simAG2.ccClose(cxCompressorFLG&&dcAG2CMV, 1);
    simAG1.ccOpen(cxCompressorFLG&&dcAG1OMV, 1);
    simAG1.ccClose(cxCompressorFLG&&dcAG1CMV, 1);
    
    //-- hotbin gate ** as
    dcAG6MAS=simAG6.ccIsAtMiddleAS();
    dcAG5MAS=simAG5.ccIsAtMiddleAS();
    dcAG4MAS=simAG4.ccIsAtMiddleAS();
    dcAG3MAS=simAG3.ccIsAtMiddleAS();
    dcAG2MAS=simAG2.ccIsAtMiddleAS();
    dcAG1MAS=simAG1.ccIsAtMiddleAS();
    
    //-- cell
    
    //-- cell ** ag
    if(simAG6.ccIsNotClosed() && cxAG6CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG6.ccGetValue()/sysOwner.random(2, 4):0;}
    if(simAG5.ccIsNotClosed() && cxAG5CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG5.ccGetValue()/sysOwner.random(2, 4):0;}
    if(simAG4.ccIsNotClosed() && cxAG4CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG4.ccGetValue()/sysOwner.random(2, 3):0;}
    if(simAG3.ccIsNotClosed() && cxAG3CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG3.ccGetValue()/sysOwner.random(2, 3):0;}
    if(simAG2.ccIsNotClosed() && cxAG2CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG2.ccGetValue()/sysOwner.random(2, 3):0;}
    if(simAG1.ccIsNotClosed() && cxAG1CanSupplyFLG)
      {dcAGCellAD+=dcAGCellAD<3602?simAG1.ccGetValue()/sysOwner.random(2, 3):0;}
    simAGCellDischargeDelay.ccAct(cxCompressorFLG&&dcAGD);
    if(simAGCellDischargeDelay.ccIsUp())
      {dcAGCellAD-=dcAGCellAD>398?sysOwner.random(7, 14):0;}
    
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
    
  }//+++
  
  //===
  
  public final int cyUsingAG(int pxMatt){
    switch(pxMatt){
      case 6:return simAG6.ccGetValue();
      case 5:return simAG5.ccGetValue();
      case 4:return simAG4.ccGetValue();
      case 3:return simAG3.ccGetValue();
      case 2:return simAG2.ccGetValue();
      case 1:return simAG1.ccGetValue();
      default:return 0;
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
