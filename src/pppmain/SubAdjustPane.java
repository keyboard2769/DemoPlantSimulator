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
import pppunit.ScScaleBlock;

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
    
    //-- optional
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
    
    //-- ag
    if(lpCommand.equals("--Cell-AG-button-zero")){
      MainOperationModel.ccGetReference().ccAdjustAGCell('z');
      return;
    }//..?
    if(lpCommand.equals("--Cell-AG-button-span")){
      MainOperationModel.ccGetReference().ccAdjustAGCell('s');
      return;
    }//..?
    
    //-- FR
    if(lpCommand.equals("--Cell-FR-button-zero")){
      MainOperationModel.ccGetReference().ccAdjustFRCell('z');
      return;
    }//..?
    if(lpCommand.equals("--Cell-FR-button-span")){
      MainOperationModel.ccGetReference().ccAdjustFRCell('s');
      return;
    }//..?
    
    //-- AS
    if(lpCommand.equals("--Cell-AS-button-zero")){
      MainOperationModel.ccGetReference().ccAdjustASCell('z');
      return;
    }//..?
    if(lpCommand.equals("--Cell-AS-button-span")){
      MainOperationModel.ccGetReference().ccAdjustASCell('s');
      return;
    }//..?
    
    //-- vb
    if(lpCommand.equals("--Deg-VB-button-zero")){
      MainOperationModel.ccGetReference().ccAdjustVBDegree('z');
      return;
    }//..?
    if(lpCommand.equals("--Deg-VB-button-span")){
      MainOperationModel.ccGetReference().ccAdjustVBDegree('s');
      return;
    }//..?
    
    //-- ve
    if(lpCommand.equals("--Deg-VE-button-zero")){
      MainOperationModel.ccGetReference().ccAdjustVEDegree('z');
      return;
    }//..?
    if(lpCommand.equals("--Deg-VE-button-span")){
      MainOperationModel.ccGetReference().ccAdjustVEDegree('s');
      return;
    }//..?
    
    System.err.println("pppmain.SubAdjustPane.actionPerformed()::"
      + "unhandled_command: "+lpCommand);
    
  }//+++
  
}//***eof
