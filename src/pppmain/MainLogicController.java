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
import ppptask.TcAutoWeighTask;
import ppptask.TcAggregateSupplyTask;
import ppptask.TcDustExtractTask;
import ppptask.TcErrorMessageTask;
import ppptask.TcFillerSupplyTask;
import ppptask.TcMainTask;
import ppptask.TcVBurnerDryerTask;
import ppptask.ZcSiloModel;
import ppptask.ZcTask;

public class MainLogicController {
  
  private static MainLogicController self;
  public static MainLogicController ccGetReference(){
    if(self==null){self=new MainLogicController();}
    return self;
  }//++!
  
  //===
  
  private int cmRoller;
  
  public final TcErrorMessageTask cmErrorMessageTask;
  public final TcMainTask cmMainTask;
  public final TcAutoWeighTask cmAutoWeighTask;
  public final TcAggregateSupplyTask cmAggregateSupplyTask;
  public final TcFillerSupplyTask cmFillerSupplyTask;
  public final TcVBurnerDryerTask cmVBurnerDryerTask;
  public final TcDustExtractTask cmDustExtractTask;
  
  private final ArrayList<ZcTask> cmTaskList;
  
  private MainLogicController(){
    
    cmRoller=0;
    ZcTask.ccSetOwner(MainSketch.ccGetReference());
    cmTaskList=new ArrayList<>();
    
    //--
    
    cmErrorMessageTask=TcErrorMessageTask.ccGetReference();
    cmTaskList.add(cmErrorMessageTask);
    
    cmMainTask=TcMainTask.ccGetReference();
    cmTaskList.add(cmMainTask);
    
    cmAutoWeighTask=TcAutoWeighTask.ccGetReference();
    cmTaskList.add(cmAutoWeighTask);
    
    cmAggregateSupplyTask=TcAggregateSupplyTask.ccGetReference();
    cmTaskList.add(cmAggregateSupplyTask);
    
    cmFillerSupplyTask=TcFillerSupplyTask.ccGetReference();
    cmTaskList.add(cmFillerSupplyTask);
    
    cmVBurnerDryerTask=TcVBurnerDryerTask.ccGetReference();
    cmTaskList.add(cmVBurnerDryerTask);
    
    cmDustExtractTask=TcDustExtractTask.ccGetReference();
    cmTaskList.add(cmDustExtractTask);
    
  }//+++ 
  
  public void ccRun(){
    
    //-- system flicker
    cmRoller++;cmRoller&=0xF;
    ZcTask.ccSetSystemClock(cmRoller, 7);
    
    //-- transgering
    ZcSiloModel.fnTransfer(
      cmDustExtractTask.simBagHopper, 
      cmDustExtractTask.simDustSilo,
      cmDustExtractTask.dcMainBagScrewAN
        &&cmDustExtractTask.dcDustExtractScrewAN,
      8
    );
    
    //-- run over takes
    for(ZcTask it : cmTaskList){it.ccScan();it.ccSimulate();}
    
  }//+++
  

}//***eof
