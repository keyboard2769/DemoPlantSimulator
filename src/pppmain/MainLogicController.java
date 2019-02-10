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
import kosui.ppplocalui.VcTagger;
import ppptask.TcAutoWeighTask;
import ppptask.TcAggregateSupplyTask;
import ppptask.TcDustExtractTask;
import ppptask.TcErrorMessageTask;
import ppptask.TcFillerSupplyTask;
import ppptask.TcMainTask;
import ppptask.TcVBurnerDryerTask;
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
    
    //-- run over takes
    for(ZcTask it : cmTaskList){it.ccScan();it.ccSimulate();}
    
    //-- test
    
  }//+++
  
  //===
  @Deprecated public final void testTagContens(){
    VcTagger.ccTag("-o s-",cmAggregateSupplyTask.simOSChute.ccGetValue());
    VcTagger.ccTag("-o f-",cmAggregateSupplyTask.simOFChute.ccGetValue());
    VcTagger.ccTag("-hb6-",cmAggregateSupplyTask.simHB6.ccGetValue());
    VcTagger.ccTag("-hb5-",cmAggregateSupplyTask.simHB5.ccGetValue());
    VcTagger.ccTag("-hb4-",cmAggregateSupplyTask.simHB4.ccGetValue());
    VcTagger.ccTag("-hb3-",cmAggregateSupplyTask.simHB3.ccGetValue());
    VcTagger.ccTag("-hb2-",cmAggregateSupplyTask.simHB2.ccGetValue());
    VcTagger.ccTag("-hb1-",cmAggregateSupplyTask.simHB1.ccGetValue());
    VcTagger.ccTag("-f s-",cmFillerSupplyTask.simFillerSilo.ccGetValue());
    VcTagger.ccTag("-f b-",cmFillerSupplyTask.simFillerBin.ccGetValue());
    VcTagger.ccTag("-bag-",cmDustExtractTask.simBagHopper.ccGetValue());
    VcTagger.ccTag("-d s-",cmDustExtractTask.simDustSilo.ccGetValue());
  }//+++
  
}//***eof
