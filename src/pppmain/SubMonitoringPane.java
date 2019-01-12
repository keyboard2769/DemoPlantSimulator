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
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTable;
import pppicon.ScGauge;
import ppptable.ScAutoWeighViewer;

public class SubMonitoringPane extends JPanel{
  
  private static SubMonitoringPane self=null;
  public static final SubMonitoringPane ccGetReference(){
    if(self==null){self=new SubMonitoringPane();}
    return self;
  }//++!
  
  //===
  
  public final ScGauge[] cmLesCurrentBar;
  
  public final ScTable cmWeighViewTable=new ScTable
      (ScAutoWeighViewer.ccGetReference(), 200, 100);
  
  private SubMonitoringPane(){
    super(new BorderLayout(1, 1));
    cmLesCurrentBar=new ScGauge[16];
    ccInit();
  }//++!
  
  private void ccInit(){  
    
    //-- current bar
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
    for(JProgressBar it:cmLesCurrentBar){lpVertPaneA.add(it);}
    
    //-- weighing
    cmWeighViewTable.ccSetEnabled(false);
    cmWeighViewTable.ccSetColor(Color.WHITE, Color.DARK_GRAY, Color.BLACK);
    JPanel lpWeighViewPanel=ScFactory.ccMyBorderPanel(2);
    lpWeighViewPanel.add(cmWeighViewTable);
    
    
    //-- packing
    add(lpVertPaneA,BorderLayout.LINE_START);
    add(lpWeighViewPanel,BorderLayout.CENTER);
    add(ScFactory.ccMyCommandButton("dummy-weigh-info"),BorderLayout.PAGE_END);
  
  }//+++
  
}//***eof
