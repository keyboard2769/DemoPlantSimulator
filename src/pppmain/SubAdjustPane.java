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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import pppicon.ScScaleBlock;

public final class SubAdjustPane extends JPanel implements ActionListener{

  private static SubAdjustPane self;
  public static SubAdjustPane ccGetReference(){
    if(self==null){self=new SubAdjustPane();}
    return self;
  }//++!
  
  //===
  
  public ScScaleBlock
    cmAGAdjuster, cmFRAdjuster, cmASAdjuster,
    cmRCAdjuster,
    cmVEAdjuster, cmVBAdjuster,
    cmREAdjuster, cmRBAdjuster
  ;//...
    

  private SubAdjustPane(){
    super(new GridLayout(8, 1, 2, 2));
    ccInit();
  }//++!
  
  private void ccInit(){
    
    
    cmAGAdjuster=new ScScaleBlock("Cell-AG"," kg",this);
    cmFRAdjuster=new ScScaleBlock("Cell-FR"," kg",this);
    cmASAdjuster=new ScScaleBlock("Cell-AS"," kg",this);
    cmRCAdjuster=new ScScaleBlock("Cell-RC"," kg",this);
    cmVEAdjuster=new ScScaleBlock("Deg-VE"," %",this);
    cmVBAdjuster=new ScScaleBlock("Deg-VB"," %",this);
    cmREAdjuster=new ScScaleBlock("Deg-RE"," %",this);
    cmRBAdjuster=new ScScaleBlock("Deg-RB"," %",this);
    
    //-- option
    cmRCAdjuster.setVisible(false);
    cmREAdjuster.setVisible(false);
    cmRBAdjuster.setVisible(false);
    
    add(cmAGAdjuster);
    add(cmFRAdjuster);
    add(cmASAdjuster);
    add(cmRCAdjuster);
    add(cmVEAdjuster);
    add(cmVBAdjuster);
    add(cmREAdjuster);
    add(cmRBAdjuster);
    
  }//++!
  
  //===

  @Override public void actionPerformed(ActionEvent ae){
    String lpCommand=ae.getActionCommand();
    
    System.err.println("pppmain.SubAdjustPane.actionPerformed()::"
      + "unhandled_command: "+lpCommand);
  }//+++
  
}//***eof
