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
import kosui.ppplogic.ZcTimer;
import kosui.ppplogic.ZcOnDelayTimer;

import static pppmain.MainOperationModel.C_FEEDER_AD_MAX;
import static pppmain.MainOperationModel.C_GENERAL_AD_MAX;
import static pppmain.MainOperationModel.C_GENERAL_AD_MIN;
import static processing.core.PApplet.ceil;
import static processing.core.PApplet.map;

public class TcAggregateSupplyTask extends ZcTask{
  
  public boolean 
    //--
    mnAGSupplyStartSW,mnAGSUpplyStartPL,
    mnVFeederStartSW,mnVFeederStartPL,
    //--
    dcScreenAN,dcHotElevatorAN,dcVDryerAN,
    dcVInclineBelconAN,dcVHorizontalBelconAN,
    dcVFAN01,dcVFAN02,dcVFAN03,dcVFAN04,dcVFAN05,dcVFAN06,
    dcVFSG01,dcVFSG02,dcVFSG03,dcVFSG04,dcVFSG05,dcVFSG06,
    //--
    dcCAS
  ;//...
  
  public int 
    dcVFSP01,dcVFSP02,dcVFSP03,dcVFSP04,dcVFSP05,dcVFSP06,
    dcVFCS
  ;//...
  
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
  
  //===
  
  private int simCSAll;
  
  private final ZcTimer
    simVFSG01TM=new ZcOnDelayTimer(7),
    simVFSG02TM=new ZcOnDelayTimer(7),
    simVFSG03TM=new ZcOnDelayTimer(7),
    simVFSG04TM=new ZcOnDelayTimer(7),
    simVFSG05TM=new ZcOnDelayTimer(7),
    simVFSG06TM=new ZcOnDelayTimer(7),
    simVCASTM=new ZcDelayor(20,20)
  ;//...

  @Override public void ccSimulate(){
    
    simVFSG01TM.ccAct(dcVFAN01&&(dcVFSP01>888));dcVFSG01=simVFSG01TM.ccIsUp();
    simVFSG02TM.ccAct(dcVFAN02&&(dcVFSP02>888));dcVFSG02=simVFSG02TM.ccIsUp();
    simVFSG03TM.ccAct(dcVFAN03&&(dcVFSP03>888));dcVFSG03=simVFSG03TM.ccIsUp();
    simVFSG04TM.ccAct(dcVFAN04&&(dcVFSP04>888));dcVFSG04=simVFSG04TM.ccIsUp();
    simVFSG05TM.ccAct(dcVFAN05&&(dcVFSP05>888));dcVFSG05=simVFSG05TM.ccIsUp();
    simVFSG06TM.ccAct(dcVFAN06&&(dcVFSP06>888));dcVFSG06=simVFSG06TM.ccIsUp();
    
    simVCASTM.ccAct(
      dcVHorizontalBelconAN&&(
      dcVFSG01||dcVFSG02||dcVFSG03||
      dcVFSG04||dcVFSG05||dcVFSG06)
    );
    dcCAS=simVCASTM.ccIsUp();
    
    simCSAll
      =(dcVFSG01?dcVFSP01:0)
      +(dcVFSG02?dcVFSP02:0)
      +(dcVFSG03?dcVFSP03:0)
      +(dcVFSG04?dcVFSP04:0)
      +(dcVFSG05?dcVFSP05:0)
      +(dcVFSG06?dcVFSP06:0)
    ;
    dcVFCS=ceil(map(simCSAll,
      0,C_FEEDER_AD_MAX*6,
      C_GENERAL_AD_MIN,C_GENERAL_AD_MAX
    ));
    
  }//+++
  
}//***eof
