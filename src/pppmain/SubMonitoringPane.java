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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import kosui.pppswingui.ScFactory;

public class SubMonitoringPane extends JPanel{
  
  private static SubMonitoringPane moniteringPane=null;
  
  //===
  
  private final JProgressBar[] cmLesCurrentBar;
  
  //===
  
  private SubMonitoringPane(ActionListener pxListener){
    
    super(new BorderLayout(1, 1));
    
    //-- current bar
    cmLesCurrentBar=new JProgressBar[16];
    cmLesCurrentBar[0]=MainSwingCoordinator.ccMyCurrentBar("Compressor");
    cmLesCurrentBar[1]=MainSwingCoordinator.ccMyCurrentBar("Mixer");
    cmLesCurrentBar[2]=MainSwingCoordinator.ccMyCurrentBar("E-Fan");
    cmLesCurrentBar[3]=MainSwingCoordinator.ccMyCurrentBar("B-Compressor");
    
    cmLesCurrentBar[4]=MainSwingCoordinator.ccMyCurrentBar("B-Fan");
    cmLesCurrentBar[5]=MainSwingCoordinator.ccMyCurrentBar("F-Pump");
    cmLesCurrentBar[6]=MainSwingCoordinator.ccMyCurrentBar("Screen");
    cmLesCurrentBar[7]=MainSwingCoordinator.ccMyCurrentBar("H-Elevator");
    
    cmLesCurrentBar[ 8]=MainSwingCoordinator.ccMyCurrentBar("Dryer");
    cmLesCurrentBar[ 9]=MainSwingCoordinator.ccMyCurrentBar("I-Belcon");
    cmLesCurrentBar[10]=MainSwingCoordinator.ccMyCurrentBar("H-Belcon");
    cmLesCurrentBar[11]=MainSwingCoordinator.ccMyCurrentBar("C-Screw");
    
    cmLesCurrentBar[12]=MainSwingCoordinator.ccMyCurrentBar("B-Screw");
    cmLesCurrentBar[13]=MainSwingCoordinator.ccMyCurrentBar("ASSupply");
    cmLesCurrentBar[14]=MainSwingCoordinator.ccMyCurrentBar("ASSpray");
    cmLesCurrentBar[15]=MainSwingCoordinator.ccMyCurrentBar("E-Elevator");
    
    JPanel lpVertPaneA=new JPanel(new GridLayout(0, 1, 2, 2));
    lpVertPaneA.add(cmLesCurrentBar[0]);
    lpVertPaneA.add(cmLesCurrentBar[1]);
    lpVertPaneA.add(cmLesCurrentBar[2]);
    lpVertPaneA.add(cmLesCurrentBar[3]);
    lpVertPaneA.add(cmLesCurrentBar[4]);
    lpVertPaneA.add(cmLesCurrentBar[5]);
    lpVertPaneA.add(cmLesCurrentBar[6]);
    lpVertPaneA.add(cmLesCurrentBar[7]);
    lpVertPaneA.add(cmLesCurrentBar[8]);
    lpVertPaneA.add(cmLesCurrentBar[9]);
    lpVertPaneA.add(cmLesCurrentBar[10]);
    lpVertPaneA.add(cmLesCurrentBar[11]);
    lpVertPaneA.add(cmLesCurrentBar[12]);
    lpVertPaneA.add(cmLesCurrentBar[13]);
    lpVertPaneA.add(cmLesCurrentBar[14]);
    lpVertPaneA.add(cmLesCurrentBar[15]);
    
    //-- packing
    add(lpVertPaneA,BorderLayout.LINE_START);
    add(ScFactory.ccMyCommandButton("dummy-weigh-info"),BorderLayout.CENTER);
    add(ScFactory.ccMyCommandButton("dummy-weigh-info"),BorderLayout.PAGE_END);
    
    
  }//++!
  
  public static final SubMonitoringPane ccInit(ActionListener pxListener){
    if(moniteringPane==null){moniteringPane=new SubMonitoringPane(pxListener);}
    return moniteringPane;
  }//++!
  
}//***eof
