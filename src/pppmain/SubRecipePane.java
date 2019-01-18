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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextField;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTable;
import ppptable.McRecipeTable;

public final class SubRecipePane extends JPanel implements ActionListener{

  private static SubRecipePane self;
  public static SubRecipePane ccGetReference(){
    if(self==null){self=new SubRecipePane();}
    return self;
  }//++!
  
  //===
  
  private ScTable cmTable;
  private JTextField[] cmAG,cmFR,cmAS;
  
  private SubRecipePane(){
    super(new BorderLayout(1, 1));
    ccInit();
  }//++!
  
  private void ccInit(){
    
    //-- file button
    JPanel lpFilePane=ScFactory.ccMyFlowPanel(2, false);
    lpFilePane.add(ScFactory.ccMyCommandButton("-DM-load"));
    lpFilePane.add(ScFactory.ccMyCommandButton("-DM-save"));
    lpFilePane.add(ScFactory.ccMyCommandButton("-DM-apply"));
    lpFilePane.add(ScFactory.ccMyCommandButton("-DM-analyze"));
    add(lpFilePane,BorderLayout.PAGE_START);
    
    //-- table
    cmTable=new ScTable(McRecipeTable.ccGetReference(), 200, 400);
    add(cmTable,BorderLayout.CENTER);
    
    //-- operate button
    JPanel lpOperate=ScFactory.ccMyGridPanel(3, 1, "opearate");
    lpOperate.add(ScFactory
      .ccMyCommandButton("   ADD   ", "--button-recipe-add", this));
    lpOperate.add(ScFactory
      .ccMyCommandButton("  DELETE ", "--button-recipe-delete", this));
    lpOperate.add(ScFactory
      .ccMyCommandButton("DUPLICATE", "--button-recipe-duplciate", this));
    
    //-- ag input flow
    
    cmAG=new JTextField[8];
    for(int i=0;i<cmAG.length;i++){
      cmAG[i]=new JTextField(Integer.toString(i*11), 4);
      cmAG[i].setEnabled(true);
      cmAG[i].setEditable(true);
    }//..~
    JPanel lpAGInputPane=ScFactory.ccMyFlowPanel(2, false, "AG:(6 -> 1)");
    lpAGInputPane.add(cmAG[6]);
    lpAGInputPane.add(cmAG[5]);
    lpAGInputPane.add(cmAG[4]);
    lpAGInputPane.add(cmAG[3]);
    lpAGInputPane.add(cmAG[2]);
    lpAGInputPane.add(cmAG[1]);
    
    //-- fr input flow
    cmFR=new JTextField[4];
    for(int i=0;i<cmFR.length;i++){
      cmFR[i]=new JTextField(Integer.toString(i*6), 4);
      cmFR[i].setEnabled(true);
      cmFR[i].setEditable(true);
    }//..~
    JPanel lpFRInputPane=ScFactory.ccMyFlowPanel(2, false, "FR:(2 -> 1)");
    lpFRInputPane.add(cmFR[2]);
    lpFRInputPane.add(cmFR[1]);
    
    //-- as input flow
    cmAS=new JTextField[4];
    for(int i=0;i<cmAS.length;i++){
      cmAS[i]=new JTextField(Integer.toString(i*6), 4);
      cmAS[i].setEnabled(true);
      cmAS[i].setEditable(true);
    }//..~
    JPanel lpASInputPane=ScFactory.ccMyFlowPanel(2, false, "AS:(1)");
    lpASInputPane.add(cmAS[1]);
    
    //-- input pane
    JPanel lpInputPane=ScFactory.ccMyGridPanel(3, 1);
    lpInputPane.add(lpAGInputPane);
    lpInputPane.add(lpFRInputPane);
    lpInputPane.add(lpASInputPane);
    
    //-- packing
    JPanel lpBottomPane=ScFactory.ccMyBorderPanel(2);
    lpBottomPane.add(lpInputPane,BorderLayout.CENTER);
    lpBottomPane.add(lpOperate,BorderLayout.LINE_END);
    add(lpBottomPane,BorderLayout.PAGE_END);
    
    
    
  }//+++
  
  //===

  @Override public void actionPerformed(ActionEvent ae){
    String lpCommand=ae.getActionCommand();
    System.err.println("pppmain.SubRecipePane.actionPerformed()::"
      + "unhandled_action:"+lpCommand);
  }//+++
  
}//***eof
