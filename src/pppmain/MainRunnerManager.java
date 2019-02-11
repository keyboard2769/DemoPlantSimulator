/*
 * Copyright (C) 2019 Key Parker
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

import java.awt.Color;
import javax.swing.SwingUtilities;
import kosui.pppswingui.McRunner;
import kosui.pppswingui.ScFactory;
import static pppmain.MainSketch.herFrame;
import static pppmain.MainSketch.yourMOD;
import static pppmain.MainOperationModel.snGetScaledIntegerValue;
import ppptable.McCurrentSlotModel;
import static pppmain.MainOperationModel.snGetInputValue;

public final class MainRunnerManager{
  
  private static MainRunnerManager self;
  private MainRunnerManager(){}//++!
  public static MainRunnerManager ccGetReference(){
    if(self==null){self=new MainRunnerManager();}
    return self;
  }//++!
  
  //===
  
  private boolean cmIsSetupDone=false;
  
  public final McRunner cmSetupRunner=new McRunner(){
    @Override public void run(){
      
      //-- check thread
      if(!SwingUtilities.isEventDispatchThread()){
        System.err.println("cmSetupRunner.run()::"
          +"blocking_outside_from_edt");
      }//..?
      
      //-- set theme
      ScFactory.ccApplyLookAndFeel(-1, true);
      
      //-- construction
      herFrame=MainSwingCoordinator.ccGetReference();
      
      //-- post setting
      
      for(int i=0;i<McCurrentSlotModel.C_CAPA;i++){
        herFrame.cmMonitoringPane.cmLesCurrentBar[i]
          .ccSetSpan(yourMOD.vmCurrentSlots.ccGetContertiveSpan(i));
      }//..~
      
      herFrame.cmSystemPane.cmMainPathBox.setText
        (MainSketch.ccGetReference().sketchPath);
      
      //-- end of setting
      cmIsSetupDone=true;
      System.out.println("MainRunnerManager.cmSetupRunner::done");
    
    }//+++
  };//...
  
  public final McRunner cmUpdateRunner=new McRunner(){
    @Override public void run(){
      
      if(!cmIsSetupDone){return;}
      
      //-- monitor pane ** weigh table
      herFrame.cmMonitoringPane.cmWeighViewTable.ccRepaintTable();
      
      //-- monitor pane ** current 
      for(int i=0;i<McCurrentSlotModel.C_CAPA;i++){
        herFrame.cmMonitoringPane.cmLesCurrentBar[i]
          .ccSetValue(yourMOD.vmCurrentSlots.ccGetAmpereValue(i));
        herFrame.cmMonitoringPane.cmLesCurrentBar[i]
          .ccSetIsAlerting(yourMOD.vmCurrentSlots.ccIsOverwhelming(i));
      }//..~
      
      //-- assistant pane
      
      //-- assistant pane ** pl
      herFrame.cmAssistantPane.cmDustBinFullPL.setBackground(
        yourMOD.vmDustBinFullPL?
        ScFactory.DARK_RED:
        Color.BLACK
      );
      
      //-- adjust pane **
      //-- adjust pane ** cell
      herFrame.cmAdjustPane.cmAGAdjuster.ccSetupValue(
        snGetInputValue(yourMOD.cmAGCell),
        snGetScaledIntegerValue(yourMOD.cmAGCell)
      );
      herFrame.cmAdjustPane.cmFRAdjuster.ccSetupValue(
        snGetInputValue(yourMOD.cmFRCell),
        snGetScaledIntegerValue(yourMOD.cmFRCell)
      );
      herFrame.cmAdjustPane.cmASAdjuster.ccSetupValue(
        snGetInputValue(yourMOD.cmASCell),
        snGetScaledIntegerValue(yourMOD.cmASCell)
      );
      //-- adjust pane ** degree
      herFrame.cmAdjustPane.cmVBAdjuster.ccSetupValue(
        snGetInputValue(yourMOD.cmVBunerDegree),
        snGetScaledIntegerValue(yourMOD.cmVBunerDegree)
      );
      herFrame.cmAdjustPane.cmVEAdjuster.ccSetupValue(
        snGetInputValue(yourMOD.cmVExfanDegree),
        snGetScaledIntegerValue(yourMOD.cmVExfanDegree)
      );
      
    }//+++
  };//...
  
}//***eof
