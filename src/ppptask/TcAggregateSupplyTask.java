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

import kosui.ppplogic.ZcHookFlicker;
import processing.core.PVector;
import kosui.ppplogic.ZcDelayor;
import kosui.ppplogic.ZcFlicker;
import kosui.ppplogic.ZcChainStepper;
import kosui.ppplogic.ZcTimer;
import kosui.ppplogic.ZcOnDelayTimer;
import static processing.core.PApplet.ceil;
import static processing.core.PApplet.map;
import static pppmain.MainSketch.fnEffect;
import static pppmain.MainOperationModel.C_DEFAULT_FEEDER_AD_MAX;
import static pppmain.MainOperationModel.C_DEFAULT_AD_SPAN;
import static pppmain.MainOperationModel.C_DEFAULT_AD_OFFS;

public final class TcAggregateSupplyTask extends ZcTask{
    
  private static TcAggregateSupplyTask self;
  private TcAggregateSupplyTask(){}//++!
  public static TcAggregateSupplyTask ccGetReference(){
    if(self==null){self=new TcAggregateSupplyTask();}
    return self;
  }//++!
  
  //===
  
  public boolean 
    //--
    mnAGSupplyStartSW,mnAGSUpplyStartPL,
    mnVFeederStartSW,mnVFeederStartPL,
    mnVF1VibDisableSW,mnVF1VibAlwaysSW,
    mnVF2VibDisableSW,mnVF2VibAlwaysSW,
    mnOverFlowGateSW,mnOverSizeGateSW,
    //--
    dcScreenAN,dcHotElevatorAN,dcVDryerAN,
    dcVInclineBelconAN,dcVHorizontalBelconAN,
    dcVFAN01,dcVFAN02,dcVFAN03,dcVFAN04,dcVFAN05,dcVFAN06,
    dcVF1VB,dcVF2VB,
    dcVFSG01,dcVFSG02,dcVFSG03,dcVFSG04,dcVFSG05,dcVFSG06,
    dcHB1H,dcHB2H,dcHB3H,dcHB4H,dcHB5H,dcHB6H,
    dcHB1L,dcHB2L,dcHB3L,dcHB4L,dcHB5L,dcHB6L,
    dcOFD,dcOSD,dcOF1,dcOS1,
    dcCAS
  ;//...
  
  public int 
    dcVFSP01,dcVFSP02,dcVFSP03,dcVFSP04,dcVFSP05,dcVFSP06,
    dcVFCS,
    dcCT1,dcCT2,dcCT3,dcCT4,dcCT5,
    dcTH4
  ;//...
  
  //=== private
  
  private final ZcFlicker
    cmVF1VibratorTM = new ZcFlicker(20, 0.5f),
    cmVF2VibratorTM = new ZcFlicker(20, 0.5f)
  ;//...
  
  private final ZcHookFlicker
    
    //-- extraction
    cmOverFlowGateHLD = new ZcHookFlicker(),
    cmOverSizeGateHLD = new ZcHookFlicker(),
    //-- motor
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
    if(cmVFeederStartHLD.ccHook(mnVFeederStartSW,!dcVHorizontalBelconAN))
      {cmVFeederSTP.ccStep(sysOneSecondPLS);}
    else
      {cmVFeederSTP.ccStop(true);}
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
    
    //-- vibrator
    cmVF1VibratorTM.ccAct(dcVFAN01&&!dcVFSG01);
    dcVF1VB=mnVF1VibDisableSW?false:
      mnVF1VibAlwaysSW?true:
      cmVF1VibratorTM.ccIsUp();
    
    cmVF2VibratorTM.ccAct(dcVFAN02&&!dcVFSG02);
    dcVF2VB=mnVF2VibDisableSW?false:
      mnVF2VibAlwaysSW?true:
      cmVF2VibratorTM.ccIsUp();
    
    //-- extraction
    dcOFD=cmOverFlowGateHLD.ccHook(mnOverFlowGateSW);
    dcOSD=cmOverSizeGateHLD.ccHook(mnOverSizeGateSW);
    
  }//+++
  
  //===
  
  private int simCSAll;
  
  private final ZcTimer
    simVFSG01TM=new ZcOnDelayTimer(7),
    simVFSG02TM=new ZcOnDelayTimer(7),
    simVFSG03TM=new ZcOnDelayTimer(7),
    simVFSG04TM=new ZcOnDelayTimer(7),
    simVFSG05TM=new ZcOnDelayTimer(7),
    simVFSG06TM=new ZcOnDelayTimer(7),
    //--
    simHB1StockTM=new ZcOnDelayTimer(80),
    simHB2StockTM=new ZcOnDelayTimer(80),
    simHB3StockTM=new ZcOnDelayTimer(80),
    simHB4StockTM=new ZcOnDelayTimer(80),
    simHB5StockTM=new ZcOnDelayTimer(80),
    simHB6StockTM=new ZcOnDelayTimer(80),
    simVCASTM=new ZcDelayor(20,20)
  ;//...
  
  public final ZcSiloModel
    simHB6=new ZcSiloModel(1200, 200, 600,1000),
    simHB5=new ZcSiloModel(1200, 200, 600,1000),
    simHB4=new ZcSiloModel(1200, 200, 600,1000),
    simHB3=new ZcSiloModel(1200, 200, 600,1000),
    simHB2=new ZcSiloModel(1200, 200, 600,1000),
    simHB1=new ZcSiloModel(1200, 200, 600,1000),
    //--
    simOFChute=new ZcSiloModel(6000, 200, 600,5200),
    simOSChute=new ZcSiloModel(2800, 200, 600,2500)
  ;//...
  
  private final PVector
    simAggregateTemp=new PVector(320f,0),
    simSandBinTemrature= new PVector(270f, 0),
    simAirTemp=new PVector(320f,0)
  ;//...
  
  private final ZcMotor
    simM1 = new ZcMotor(),
    simM2 = new ZcMotor(),
    simM3 = new ZcMotor(),
    simM4 = new ZcMotor(),
    simM5 = new ZcMotor()
  ;//...

  @Override public void ccSimulate(){
    
    //-- sg sensor
    simVFSG01TM.ccAct(dcVFAN01&&(dcVFSP01>888));dcVFSG01=simVFSG01TM.ccIsUp();
    simVFSG02TM.ccAct(dcVFAN02&&(dcVFSP02>888));dcVFSG02=simVFSG02TM.ccIsUp();
    simVFSG03TM.ccAct(dcVFAN03&&(dcVFSP03>888));dcVFSG03=simVFSG03TM.ccIsUp();
    simVFSG04TM.ccAct(dcVFAN04&&(dcVFSP04>888));dcVFSG04=simVFSG04TM.ccIsUp();
    simVFSG05TM.ccAct(dcVFAN05&&(dcVFSP05>888));dcVFSG05=simVFSG05TM.ccIsUp();
    simVFSG06TM.ccAct(dcVFAN06&&(dcVFSP06>888));dcVFSG06=simVFSG06TM.ccIsUp();
    
    //-- conveyor scale
    simVCASTM.ccAct(
      dcVHorizontalBelconAN&&(
      dcVFSG01||dcVFSG02||dcVFSG03||
      dcVFSG04||dcVFSG05||dcVFSG06)
    );
    dcCAS=simVCASTM.ccIsUp();
    //--
    simCSAll
      =(dcVFSG01?dcVFSP01:0)
      +(dcVFSG02?dcVFSP02:0)
      +(dcVFSG03?dcVFSP03:0)
      +(dcVFSG04?dcVFSP04:0)
      +(dcVFSG05?dcVFSP05:0)
      +(dcVFSG06?dcVFSP06:0)
    ;
    dcVFCS=ceil(map(simCSAll,
      0,C_DEFAULT_FEEDER_AD_MAX*6,
      C_DEFAULT_AD_OFFS,C_DEFAULT_AD_SPAN
    ));
    
    //-- hot bin level
    simHB6StockTM.ccAct(dcVFSG06);
    simHB5StockTM.ccAct(dcVFSG05);
    simHB4StockTM.ccAct(dcVFSG04);
    simHB3StockTM.ccAct(dcVFSG03);
    simHB2StockTM.ccAct(dcVFSG02);
    simHB1StockTM.ccAct(dcVFSG01);
    //--
    simHB6.ccCharge(simHB6StockTM.ccIsUp(), dcVFSP06/500);
    simHB5.ccCharge(simHB5StockTM.ccIsUp(), dcVFSP05/500);
    simHB4.ccCharge(simHB4StockTM.ccIsUp(), dcVFSP04/500);
    simHB3.ccCharge(simHB3StockTM.ccIsUp(), dcVFSP03/500);
    simHB2.ccCharge(simHB2StockTM.ccIsUp(), dcVFSP02/500);
    simHB1.ccCharge(simHB1StockTM.ccIsUp(), dcVFSP01/500);
    //--
    dcHB6H=simHB6.ccIsFull();dcHB6L=simHB6.ccIsLow();
    dcHB5H=simHB5.ccIsFull();dcHB5L=simHB5.ccIsLow();
    dcHB4H=simHB4.ccIsFull();dcHB4L=simHB4.ccIsLow();
    dcHB3H=simHB3.ccIsFull();dcHB3L=simHB3.ccIsLow();
    dcHB2H=simHB2.ccIsFull();dcHB2L=simHB2.ccIsLow();
    dcHB1H=simHB1.ccIsFull();dcHB1L=simHB1.ccIsLow();
    
    //-- over size and over flow
    simOSChute.ccCharge(simHB6.ccIsOverFlowed()&&dcVFSG06, dcVFSP06/500);
    simOFChute.ccCharge(simHB5.ccIsOverFlowed()&&dcVFSG05, dcVFSP05/500);
    simOFChute.ccCharge(simHB4.ccIsOverFlowed()&&dcVFSG04, dcVFSP04/500);
    simOFChute.ccCharge(simHB3.ccIsOverFlowed()&&dcVFSG03, dcVFSP03/500);
    simOFChute.ccCharge(simHB2.ccIsOverFlowed()&&dcVFSG02, dcVFSP02/500);
    simOFChute.ccCharge(simHB1.ccIsOverFlowed()&&dcVFSG01, dcVFSP01/500);
    simOSChute.ccDischarge(dcOSD, 10);
    simOFChute.ccDischarge(dcOFD, 10);
    dcOF1=simOFChute.ccIsFull();
    dcOS1=simOSChute.ccIsFull();
    
    //-- sand bin temp
    simAirTemp.x=265;
    simAggregateTemp.x=TcVBurnerDryerTask.ccGetReference().dcTH1;
    float lpSandBinAMP=map(simHB1.ccGetValue(),0,1200,0.1f,0.4f);
    if(sysOneSecondPLS){
      fnEffect(
        simAggregateTemp, simSandBinTemrature, 
        lpSandBinAMP+sysOwner.random(0.03f, 0.06f)
      );
      fnEffect(
        simSandBinTemrature, simAirTemp,
        sysOwner.random(0.03f, 0.06f)
      );
    }
    dcTH4=ceil(simSandBinTemrature.x*(dcHB1L?1.3f:0.9f));
    
    //-- power
    dcCT1=simM1.ccContact(dcScreenAN, dcCAS?0.75f:0.65f);
    dcCT2=simM2.ccContact(dcHotElevatorAN, dcCAS?0.74f:0.64f);
    dcCT3=simM3.ccContact(dcVDryerAN, dcCAS?0.73f:0.63f);
    dcCT4=simM4.ccContact(dcVInclineBelconAN, dcCAS?0.72f:0.62f);
    dcCT5=simM5.ccContact(dcVHorizontalBelconAN, dcCAS?0.71f:0.61f);
    
  }//+++
  
  //===
  
}//***eof
