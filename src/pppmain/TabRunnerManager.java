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
import static pppmain.MainSketch.theSketch;

public class TabRunnerManager{
  
  McRunner cmSetupRunner=new McRunner(){
    @Override public void run(){
      
      //-- check thread
      if(!SwingUtilities.isEventDispatchThread())
        {System.err.println("cmSetupRunner.run()::"
        +"blocking_outside_from_edt");}
      if(theSketch==null){return;}
      
      //-- set theme
      try{
        UIManager.setLookAndFeel
          (UIManager.getCrossPlatformLookAndFeelClassName());
      }catch(Exception e)
        {System.err.println("cmSetupRunner.run::"+e.toString());}
      
      //-- construction
      MainSketch.herFrame=new MainSwingCoordinator(theSketch);
      
      //-- post setting
      
      //-- end of setting
      System.out.println(".run()::done setup from EDT");
    
    }//+++
  };
  
  McRunner cmUpdateRunner=new McRunner(){
    @Override public void run(){
      //[TODO]::? 
    }
  };
  
}//***eof
