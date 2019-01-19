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

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import kosui.pppswingui.McRunner;
import static pppmain.MainSketch.herFrame;
import static pppmain.MainSketch.yourMOD;

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
      if(!SwingUtilities.isEventDispatchThread())
        {System.err.println("cmSetupRunner.run()::"
        +"blocking_outside_from_edt");}
      
      //-- set theme
      try{
        UIManager.setLookAndFeel
          (UIManager.getCrossPlatformLookAndFeelClassName());
      }catch(Exception e)
        {System.err.println("cmSetupRunner.run::"+e.toString());}
      
      //-- construction
      herFrame=MainSwingCoordinator.ccGetReference();
      
      //-- post setting
      herFrame.cmAssistantPane.cmFillerAirCB.setSelectedIndex
        (yourMOD.vsFillerSiloAirNT);
      herFrame.cmSystemPane.cmMainPathBox.setText
        (MainSketch.ccGetReference().sketchPath);
      
      //-- end of setting
      cmIsSetupDone=true;
      System.out.println(".run()::done setup from EDT");
    
    }//+++
  };
  
  public final McRunner cmUpdateRunner=new McRunner(){
    @Override public void run(){
      
      if(!cmIsSetupDone){return;}
      
      //-- auto weigh
      herFrame.cmMonitoringPane.cmWeighViewTable.ccRepaintTable();
      
      //-- current 
      for(int i=0;i<yourMOD.vmCurrentVALUE.length();i++){
      herFrame.cmMonitoringPane.cmLesCurrentBar[i]
        .ccSetValue(yourMOD.vmCurrentVALUE.get(i));
      }//..~
      
    }//+++
  };
  
  public final Runnable cmShowOperateWindow=new Runnable() {
    @Override public void run(){
      herFrame.cmOperateWindow.setVisible(true);
    }//+++
  };
  
  public final Runnable cmUpdateSettingTable=new Runnable() {
    @Override public void run(){
      herFrame.cmSettingPane.ccUpdateContent();
    }//+++
  };
  
}//***eof
