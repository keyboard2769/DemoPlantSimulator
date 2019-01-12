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
import pppicon.ScGauge;

public class SubMonitoringPane extends JPanel{
  
  private static SubMonitoringPane moniteringPane=null;
  
  //===
  
  public final ScGauge[] cmLesCurrentBar;
  
  //===
  
  private SubMonitoringPane(ActionListener pxListener){
    
    super(new BorderLayout(1, 1));
    
    //-- current bar
    cmLesCurrentBar=new ScGauge[16];
    cmLesCurrentBar[0]=new ScGauge("Compressor: ", " A", 100f);
    cmLesCurrentBar[1]=new ScGauge("Mixer: ", " A", 300f);
    cmLesCurrentBar[2]=new ScGauge("E-Fan: ", " A", 300f);
    cmLesCurrentBar[3]=new ScGauge("B-Compressor: ", " A", 50f);
    
    cmLesCurrentBar[4]=new ScGauge("B-Fan: ", " A", 100f);
    cmLesCurrentBar[5]=new ScGauge("F-Pump: ", " A", 25f);
    cmLesCurrentBar[6]=new ScGauge("Screen: ", " A", 100f);
    cmLesCurrentBar[7]=new ScGauge("H-Elevator: ", " A", 100f);
    
    cmLesCurrentBar[ 8]=new ScGauge("Dryer: ", " A", 200f);
    cmLesCurrentBar[ 9]=new ScGauge("I-Belcon: ", " A", 25f);
    cmLesCurrentBar[10]=new ScGauge("H-Belcon: ", " A", 25f);
    cmLesCurrentBar[11]=new ScGauge("C-Screw: ", " A", 25f);
    
    cmLesCurrentBar[12]=new ScGauge("B-Screw: ", " A", 25f);
    cmLesCurrentBar[13]=new ScGauge("ASSupply: ", " A", 50f);
    cmLesCurrentBar[14]=new ScGauge("ASSpray: ", " A", 50f);
    cmLesCurrentBar[15]=new ScGauge("E-Elevator: ", " A", 25f);
    
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
