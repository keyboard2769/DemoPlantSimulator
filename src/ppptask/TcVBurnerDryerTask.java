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

import kosui.ppplogic.ZcDelayor;
import kosui.ppplogic.ZcOffDelayTimer;
import processing.core.PVector;
import static processing.core.PApplet.ceil;
import static processing.core.PApplet.map;
import static processing.core.PApplet.constrain;

import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcPulser;
import kosui.ppplogic.ZcStepper;
import kosui.ppplogic.ZiTimer;

import pppmain.SubVBurnerControlGroup;
import static pppmain.MainSketch.fnEffect;

public class TcVBurnerDryerTask extends ZcTask{
    
  private static TcVBurnerDryerTask self;
  private TcVBurnerDryerTask(){}//++!
  public static TcVBurnerDryerTask ccGetReference(){
    if(self==null){self=new TcVBurnerDryerTask();}
    return self;
  }//++!
  
  //===
  public boolean 
    //--
    mnVExfanMotorSW, mnVExfanMotorPL,
    mnAPBlowerSW,mnAPBlowerPL,
    mnVEXFOPSW,mnVEXFCLSW,mnVEXFATSW,mnVEXFATPL,
    mnVBOPSW,mnVBCLSW,mnVBATSW,mnVBATPL,
    mnVBReadyPL,mnVBIGNSW,mnVBIGNPL,
    //--
    mnCoolingDamperOpenSIG,mnFireStopSIG,
    //--
    dcVExfanAN,dcVEFCLLS,dcVEFOPLS,dcVEFOPRY,dcVEFCLRY,
    dcVBurnerFanAN,dcVBCLLS,dcVBOPLS,dcVBOPRY,dcVBCLRY,
    dcAPBlowerAN,dcHSW,
    dcIG,dcPV,dcMMV,dcFuelPumpAN,dcFuelMV,dcHeavyMV,
    dcCoolingDamperMV
  ;//...
  
  public int
    mnVBTemratureTargetAD,mnVDPressureTargetAD,
    mnVDOLimitLow,mnVDOLimitHigh,
    //--
    dcVDO=550,dcVBO=550,dcVSE,
    dcTH2,dcTH1,
    dcCT10,dcCT28,dcCT29
  ;//...
  
  //===
  
  private boolean cmVExfanStartLock;
  
  private final ZcHookFlicker
    cmVExfanMotorHLD = new ZcHookFlicker(),
    cmAPBlowerHLD = new ZcHookFlicker(),
    cmVEXFATHLD=new ZcHookFlicker(),
    cmVBATHLD = new ZcHookFlicker()
  ;//...
  
  private final ZiTimer
    cmCoolingDamperOpenTM = new ZcDelayor(5,30),
    cmFireStopTM = new ZcOnDelayTimer(60),
    //--
    cmVBurnerIgnitionSparkTM = new ZcOnDelayTimer(20),
    cmVBurnerPilotValveTM = new ZcOnDelayTimer(20),
    cmVBurnerPrePurgeTM = new ZcOnDelayTimer(40),
    cmVBurnerPostPurgeTM = new ZcOnDelayTimer(60),
    cmVExfanMotorSDTM = new ZcOnDelayTimer(40),
    cmVDryerPressureControlDelay = new ZcOnDelayTimer(40),
    cmVBurnerAutoControlDelay = new ZcOnDelayTimer(40),
    cmVBurnerAutoIntegralCLK = new ZcPulseFlicker(60),
    cmVBurnerAutoDerivativeCLK = new ZcPulseFlicker(40),
    cmVFuelExchangeStartTM = new ZcOnDelayTimer(20),
    cmVFuelExchangeEndTM = new ZcOffDelayTimer(20)
  ;//...
  
  private final ZcPulser cmVBIGNSWPLS=new ZcPulser();
  
  private final ZcStepper
    cmVBurnerIgniteSTP=new ZcStepper();
  ;//...
  
  private final ZcPIDController
    cmExfanPressurePID=new ZcPIDController(1000,0.15f,0.10f),
    cmVBurnerPID = new ZcPIDController(1600,0.9f,0.1f),
    cmVBurnerDegreePID = new ZcPIDController(1600,0.10f,0.05f)
  ;//...

  @Override public void ccScan(){
    
    //-- temrature EMS
    cmFireStopTM.ccAct(mnFireStopSIG);
    
    //-- vexfan start
    //[TODO]::the closed limit problem
    cmVExfanMotorSDTM.ccAct(cmVExfanMotorHLD
      .ccHook(mnVExfanMotorSW,cmVExfanStartLock||cmFireStopTM.ccIsUp()));
    dcVExfanAN=cmVExfanMotorSDTM.ccIsUp();
    mnVExfanMotorPL=cmVExfanMotorHLD.ccIsHooked()
      &&(sysOneSecondFLK||dcVExfanAN);
    cmVExfanStartLock=dcVExfanAN?false:!dcVEFCLLS;
    
    //-- main unit blower start
    dcAPBlowerAN=cmAPBlowerHLD.ccHook(mnAPBlowerSW,!dcVExfanAN);
    mnAPBlowerPL=dcAPBlowerAN;
    
    //-- burner ignit stepp
    //-- burner ignit stepp ** define
    boolean
      lpVBSStop=cmVBurnerIgniteSTP.ccIsAt(0),
      lpVBSReady=cmVBurnerIgniteSTP.ccIsAt(1),
      lpVBSPrePurgeGoesUp=cmVBurnerIgniteSTP.ccIsAt(2),
      lpVBSPrePurgeGoesDown=cmVBurnerIgniteSTP.ccIsAt(3),
      lpVBSIgnitionSpark=cmVBurnerIgniteSTP.ccIsAt(4),
      lpVBSPilotValve=cmVBurnerIgniteSTP.ccIsAt(5),
      lpVBSMainValve=cmVBurnerIgniteSTP.ccIsAt(6),
      lpVBSPostPurge=cmVBurnerIgniteSTP.ccIsAt(7)
    ;//...
    
    //-- burner ignit stepp ** step
    boolean lpVBStepCondition=
      dcVExfanAN&&
      TcMainTask.ccGetReference().dcVCompressorAN&&
      TcAggregateSupplyTask.ccGetReference().dcVInclineBelconAN;
    cmVBurnerIgniteSTP.ccSetTo(7, 
      (lpVBSPrePurgeGoesUp||lpVBSPrePurgeGoesDown||
      lpVBSIgnitionSpark||lpVBSPilotValve||lpVBSMainValve)&&
      cmVBIGNSWPLS.ccUpPulse(mnVBIGNSW)
    );
    cmVBurnerIgniteSTP.ccSetTo(0, cmFireStopTM.ccIsUp());
    cmVBurnerIgniteSTP.ccSetTo(0, !lpVBStepCondition);
    cmVBurnerIgniteSTP.ccStep(0, 1, lpVBStepCondition);
    cmVBurnerIgniteSTP.ccStep(1, 2, cmVBIGNSWPLS.ccUpPulse(mnVBIGNSW));
    cmVBurnerIgniteSTP.ccStep(2, 3, cmVBurnerPrePurgeTM.ccIsUp());
    cmVBurnerIgniteSTP.ccStep(3, 4, dcVBCLLS);
    cmVBurnerIgniteSTP.ccStep(4, 5, cmVBurnerIgnitionSparkTM.ccIsUp());
    cmVBurnerIgniteSTP.ccStep(5, 6, cmVBurnerPilotValveTM.ccIsUp());
    cmVBurnerIgniteSTP.ccStep(7,0,cmVBurnerPostPurgeTM.ccIsUp());
    
    //-- burner ignit stepp ** timer
    cmVBurnerPrePurgeTM.ccAct(lpVBSPrePurgeGoesUp&&dcVBOPLS);
    cmVBurnerIgnitionSparkTM.ccAct(lpVBSIgnitionSpark);
    cmVBurnerPilotValveTM.ccAct(lpVBSPilotValve);
    cmVBurnerPostPurgeTM.ccAct(lpVBSPostPurge);
    
    //-- burner ignit stepp ** stage feedback
    mnVBReadyPL=cmVBurnerIgniteSTP.ccIsAt(1);
    
    //-- burner ignit stepp ** lamp feedback
    if(lpVBSReady){mnVBIGNPL=false;}
    if(dcVBurnerFanAN){mnVBIGNPL=sysOneSecondFLK;}
    if(lpVBSMainValve){mnVBIGNPL=true;}
    
    //-- -
    
    //-- combust control
    
    //-- combust control ** basic
    
    dcVBurnerFanAN=
      lpVBSPrePurgeGoesUp||lpVBSPrePurgeGoesDown||
      lpVBSIgnitionSpark||lpVBSPilotValve||lpVBSMainValve||lpVBSPostPurge;
    dcIG=lpVBSIgnitionSpark;
    dcPV=lpVBSPilotValve;
    dcMMV=dcFuelPumpAN=lpVBSMainValve;
    
    //-- combust control ** fuel exchange
    boolean lpCAS=TcAggregateSupplyTask.ccGetReference().dcCAS;
    cmVFuelExchangeStartTM.ccAct(lpCAS);
    cmVFuelExchangeEndTM.ccAct(lpCAS);
    dcFuelMV=dcMMV&!cmVFuelExchangeStartTM.ccIsUp();
    dcHeavyMV=dcMMV&cmVFuelExchangeEndTM.ccIsUp();
    
    //-- vefx damper control
    
    //-- vefx damper control ** pid
    cmExfanPressurePID.ccSetTarget(mnVDPressureTargetAD);
    cmExfanPressurePID.ccStep(dcVSE);
    
    //-- vefx damper control ** limiting
    cmVDryerPressureControlDelay.ccAct(dcMMV);
    boolean lpVEXFAutoOpenFLG=
      !dcVExfanAN?false:
      (dcVDO<mnVDOLimitLow)?true:
      cmVDryerPressureControlDelay.ccIsUp()?
      cmExfanPressurePID.ccGetNegativeOutput():false;
    boolean lpVEXFAutoCloseFLG=
      !dcVExfanAN?true:
      (dcVDO>mnVDOLimitHigh)?true:
      cmVDryerPressureControlDelay.ccIsUp()?
      cmExfanPressurePID.ccGetPositiveOutput():false;
      
    //-- vefx damper control ** output 
    mnVEXFATPL=cmVEXFATHLD.ccHook(mnVEXFATSW);
    dcVEFOPRY=(!dcVEFOPLS)&&(
      (!mnVEXFATPL&&mnVEXFOPSW)||
      ( mnVEXFATPL&&lpVEXFAutoOpenFLG)
    );
    
    dcVEFCLRY=(!dcVEFCLLS)&&(
      (!mnVEXFATPL&&mnVEXFCLSW)||
      ( mnVEXFATPL&&lpVEXFAutoCloseFLG)
    );
    
    //-- v burner damper control
        
    //-- v burner damper control ** pid
    boolean lpDoPID=cmVBurnerAutoControlDelay.ccIsUp();
    cmVBurnerAutoDerivativeCLK.ccAct(lpDoPID);
    cmVBurnerAutoIntegralCLK.ccAct(lpDoPID);
    cmVBurnerPID.ccSetTarget(lpCAS?mnVBTemratureTargetAD:0);
    cmVBurnerPID.ccStep(
      dcTH1,
      cmVBurnerAutoIntegralCLK.ccIsUp(),
      cmVBurnerAutoDerivativeCLK.ccIsUp()
    );
    float lpVBTargetDegreeLMT = constrain(
      map(cmVBurnerPID.ccGetAnalogOutput(),0f,1f,401f,3599f),
      401f, 3600f
    );
    cmVBurnerDegreePID.ccSetTarget(lpVBTargetDegreeLMT);
    cmVBurnerDegreePID.ccStep(dcVBO);
        
    //-- v burner damper control ** flagging
    cmVBurnerAutoControlDelay.ccAct(dcMMV&&lpCAS);
    boolean lpVBurnerAutoOpenFLG=
      !dcVBurnerFanAN?false:
      !dcMMV?lpVBSPrePurgeGoesUp:
      cmVBurnerAutoControlDelay.ccIsUp()?
      cmVBurnerDegreePID.ccGetPositiveOutput():false;//..[TOIMP]::
    boolean lpVBurnerAutoCloseFLG=
      !dcVBurnerFanAN?true:
      !dcMMV?lpVBSPrePurgeGoesDown:
      cmVBurnerAutoControlDelay.ccIsUp()?
      cmVBurnerDegreePID.ccGetNegativeOutput():false;//..[TOIMP]::
        
    //-- v burner damper control ** output
    mnVBATPL=cmVBATHLD.ccHook(mnVBATSW);
    dcVBOPRY=(!dcVBOPLS)&&(!dcVBCLRY)&&(
      (!mnVBATPL&&mnVBOPSW)||
      ( mnVBATPL&&lpVBurnerAutoOpenFLG)
    );
    
    dcVBCLRY=(!dcVBCLLS)&&(!dcVBOPRY)&&(
      (!mnVBATPL&&mnVBCLSW)||
      ( mnVBATPL&&lpVBurnerAutoCloseFLG)
    );
    
    //-- cooling damper
    cmCoolingDamperOpenTM.ccAct(mnCoolingDamperOpenSIG);
    dcCoolingDamperMV=cmCoolingDamperOpenTM.ccIsUp();
    
  }//+++
  
  //===
  
  private final PVector
    
    //-- pressure[AD]
    simAtomsphere=new PVector(1888f, 0),
    simBurnerPressure=new PVector(1888f, 0),
    simDryerPressure=new PVector(1888f, 0),
    simExfanPressure=new PVector(1888f, 0),
    //-- temprature[x10'C]
    simAirTemp=new PVector(320f,0),
    simBurnerTemp=new PVector(320f,0),
    simDryerTemp=new PVector(320f,0),
    simAggregateTemp=new PVector(320f,0),
    simBagEntranceTemp=new PVector(320f,0)
    
  ;//...
  
  private final ZcMotor
    cm10 = new ZcMotor(),
    cm28 = new ZcMotor(),
    cm29 = new ZcMotor()
  ;//...
  
  @Override public void ccSimulate(){
    
    boolean lpCAS=TcAggregateSupplyTask.ccGetReference().dcCAS;
    
    //-- vefx damper
    if(dcVEFOPRY){dcVDO+=dcVDO<3600?16:0;}
    if(dcVEFCLRY){dcVDO-=dcVDO>400?16:0;}
    dcVEFCLLS=dcVDO<450;
    dcVEFOPLS=dcVDO>3550;
    dcHSW=dcVExfanAN&&(dcVDO>ceil(sysOwner.random(600,625)));
    
    //-- vb damper
    if(dcVBOPRY){dcVBO+=dcVBO<3600?16:0;}
    if(dcVBCLRY){dcVBO-=dcVBO>400?16:0;}
    dcVBCLLS=dcVBO<450;
    dcVBOPLS=dcVBO>3550;
    
    //-- pressure simulate
    simAtomsphere.x=1488f;
    simBurnerPressure.x=1500f
      +(dcVBurnerFanAN?4000f:10f)
      *map(dcVBO, 400f,3600f, 0.1f, 0.9f);
    simExfanPressure.x=1500f
      -(dcVExfanAN?5000f:10f)
      *map(dcVDO, 400f, 3600f,0.1f, 0.9f);
    //-- pressure simulate ** effection
    if(sysOneSecondPLS){
      fnEffect(
        simBurnerPressure, simDryerPressure,
        sysOwner.random(0.15f,0.25f)
      );
      fnEffect(
        simDryerPressure,simExfanPressure,
        sysOwner.random(0.15f,0.25f)
      );
      fnEffect(
        simDryerPressure,simAtomsphere,
        sysOwner.random(0.05f,0.15f)
      );
    }//..?
    //-- pressure simulate ** feedback
    dcVSE=ceil(simDryerPressure.x);
    
    //-- temprature simulate
    int lpVFCS=TcAggregateSupplyTask.ccGetReference().dcVFCS;
    simAirTemp.x=275;
    simBurnerTemp.x=(dcMMV?5500:320)*map(dcVBO,400,3600,0.25f,0.99f);
    float lpDryerAMP=dcMMV?0.25f:0.07f;
    float lpChuteAMP=map(lpVFCS,400,3600,0.25f,0.01f);
    float lpEntranceAMP=!dcVExfanAN?0.10f:
      map(dcVDO,400,3600,0.55f,0.30f);
    float lpChuteDampingAMP=lpCAS?0.01f:0.75f;
    float lpEntranceDampingAMP=map(lpVFCS,400,3600,0.01f,0.35f);
      
    //-- temprature simulate ** effection
    if(sysOneSecondPLS){
      fnEffect(
        simBurnerTemp,simDryerTemp,
        lpDryerAMP+sysOwner.random(0.03f,0.06f)
      );
      fnEffect(
        simDryerTemp,simAggregateTemp,
        lpChuteAMP+sysOwner.random(0.03f,0.06f)
      );
      fnEffect(
        simDryerTemp,simBagEntranceTemp,
        lpEntranceAMP+sysOwner.random(0.03f,0.06f)
      );
      //-- temprature simulate ** effection ** air
      fnEffect(
        simDryerTemp,simAirTemp,
        sysOwner.random(0.03f,0.06f)
      );
      fnEffect(
        simAggregateTemp,simAirTemp,
        lpChuteDampingAMP+sysOwner.random(0.02f,0.04f)
      );
      fnEffect(
        simBagEntranceTemp, simAirTemp, 
        lpEntranceDampingAMP
         +(dcCoolingDamperMV?0.25f:0.0f)
         +sysOwner.random(0.02f,0.04f)
      );
    }//..?
    
    //-- temprature simulate ** feedback
    dcTH1=ceil(simAggregateTemp.x);
    dcTH2=ceil(simBagEntranceTemp.x*(lpCAS?0.9f:1.5f));
    
    //-- power
    dcCT10=cm10.ccGetCurrent(dcVExfanAN, map(dcVDO,400,3600,0.5f,0.8f));
    dcCT28=cm28.ccGetCurrent(dcVBurnerFanAN, map(dcVBO,400,3600,0.4f,0.75f));
    dcCT29=cm29.ccGetCurrent(dcMMV, 0.66f);
    
  }//+++
  
  //===
  
  @Deprecated public final int testGetVBTargetDegree()
    {return ceil(100*cmVBurnerPID.ccGetAnalogOutput());}//+++
  
  @Deprecated public final int testGetPIDShiftedTempAD()
    {return ceil(cmVBurnerPID.ccGetShiftedTarget());}//+++
  
}//***eof
