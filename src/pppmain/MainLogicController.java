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

package pppmain;

import java.util.ArrayList;
import ppptask.TcAggregateSupplyTask;
import ppptask.TcDustExtractTask;
import ppptask.TcFillerSupplyTask;
import ppptask.TcMainTask;
import ppptask.TcVBurnerDryerTask;
import ppptask.ZcTask;
import processing.core.PApplet;

public class MainLogicController {
  
  private int cmRoller;
  
  public final TcMainTask cmMainTask;
  public final TcAggregateSupplyTask cmAggregateSupplyTask;
  public final TcFillerSupplyTask cmFillerSupplyTask;
  public final TcVBurnerDryerTask cmVBurnerDryerTask;
  public final TcDustExtractTask cmDustExtractTask;
  
  private final ArrayList<ZcTask> cmTaskList;
  
  public MainLogicController(PApplet pxOwner){
    
    cmRoller=0;
    ZcTask.ccSetOwner(pxOwner);
    cmTaskList=new ArrayList<>();
    
    //--
    cmMainTask=new TcMainTask();
    cmTaskList.add(cmMainTask);
    
    cmAggregateSupplyTask=new TcAggregateSupplyTask();
    cmTaskList.add(cmAggregateSupplyTask);
    
    cmFillerSupplyTask=new TcFillerSupplyTask();
    cmTaskList.add(cmFillerSupplyTask);
    
    cmVBurnerDryerTask=new TcVBurnerDryerTask();
    cmTaskList.add(cmVBurnerDryerTask);
    
    cmDustExtractTask=new TcDustExtractTask();
    cmTaskList.add(cmDustExtractTask);
    
  }//+++ 
  
  public void ccRun(){
    
    //-- system flicker
    cmRoller++;cmRoller&=0xF;
    ZcTask.ccSetSystemClock(cmRoller, 7);
    
    //-- intertasks
    
    cmAggregateSupplyTask.cxAggregateChuteTempAD=
      cmVBurnerDryerTask.dcTH1;
    
    cmVBurnerDryerTask.cxVBIgniteConditionFLG=
      cmAggregateSupplyTask.dcVInclineBelconAN&&
      cmMainTask.dcVCompressorAN;
    cmVBurnerDryerTask.cxVFCS=
      cmAggregateSupplyTask.dcVFCS;
    cmVBurnerDryerTask.cxCAS=
      cmAggregateSupplyTask.dcCAS;
    
    cmDustExtractTask.cxBagPulseStartFLG=
      cmMainTask.dcVCompressorAN&&cmAggregateSupplyTask.dcCAS;
    cmDustExtractTask.cxDustGenerateFLG=
      cmVBurnerDryerTask.dcVExfanAN && cmAggregateSupplyTask.dcCAS;
    
    //-- run over takes
    for(ZcTask it : cmTaskList){it.ccScan();it.ccSimulate();}
    
  }//+++
  

}//***eof
