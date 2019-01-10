/*
 * Copyright (C) 2019 2053
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TabActionManager implements ActionListener{
  
  //===
  
  @Override public void actionPerformed(ActionEvent ae){
    
    String lpCommand=ae.getActionCommand();
    Object lpSource=ae.getSource();
    
    
      
      
    if(lpCommand.equals("--combo-fillerSiloAir")){
      if(lpSource instanceof JComboBox){
        JComboBox lpBox=(JComboBox)lpSource;
        MainSketch.yourMOD.vsFillerSiloAirNT=lpBox.getSelectedIndex();
        return;
      }
    }//..?
    
    if(lpCommand.equals("--button-quit")){
      TabWireManager.actionID=TabWireManager.C_K_QUIT;
      return;
    }//..?
    
    System.out.println(
      ".TabActionManager"
      +"::unhandled_command:"
      +ae.getActionCommand()
    );
    
  }//+++
  
}//***eof
