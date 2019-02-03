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
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTable;
import kosui.ppputil.VcConst;
import ppptable.McRecipeTable;
import ppptable.McWorkerManager;

public final class SubRecipePane extends JPanel implements ActionListener{

  private static SubRecipePane self;
  public static SubRecipePane ccGetReference(){
    if(self==null){self=new SubRecipePane();}
    return self;
  }//++!
  
  private static final double
    C_RECIPE_PER_MIN =   0.0d,
    C_RECIPE_PER_MAX = 100.0d
  ;//...
  
  //===
  
  private ScTable cmTable;
  private JTextField cmIndexBox, cmTitleBox;
  private JSpinner[] cmAG,cmFR,cmAS;
  
  private SubRecipePane(){
    super(new BorderLayout(1, 1));
    ccInit();
  }//++!
  
  private void ccInit(){
    
    //-- file button
    JPanel lpFilePane=ScFactory.ccMyFlowPanel(2, false);
    lpFilePane.add(ScFactory.ccMyCommandButton
      ("LOAD","--button-load",this));
    lpFilePane.add(ScFactory.ccMyCommandButton
      ("SAVE","--button-save",this));
    lpFilePane.add(ScFactory.ccMyCommandButton("-DM-force"));
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
    
    //-- number and title flow
    cmIndexBox=new JTextField("000/",4);
    cmTitleBox=new JTextField("%new-title%",64);
    JPanel lpTitleInputPane=ScFactory.ccMyFlowPanel(2, false);
    lpTitleInputPane.add(new JLabel("  index:"));
    lpTitleInputPane.add(cmIndexBox);
    lpTitleInputPane.add(new JLabel("  title:"));
    lpTitleInputPane.add(cmTitleBox);
    lpTitleInputPane.add(new JLabel("  /"));
    
    //-- ag input flow
    cmAG=new JSpinner[8];
    for(int i=0;i<cmAG.length;i++){
      cmAG[i]=MainSwingCoordinator.ccMyRecipeItemSpinner(11.7);//[TODO]::p-set
      cmAG[i].addChangeListener(lpAGChangeListener);
    }//..~
    JPanel lpAGInputPane=ScFactory.ccMyFlowPanel(2, false, "AG:(6 -> 1)");
    lpAGInputPane.add(cmAG[6]);
    lpAGInputPane.add(cmAG[5]);
    lpAGInputPane.add(cmAG[4]);
    lpAGInputPane.add(cmAG[3]);
    lpAGInputPane.add(cmAG[2]);
    lpAGInputPane.add(cmAG[1]);
    
    //-- fr input flow
    cmFR=new JSpinner[4];
    for(int i=0;i<cmFR.length;i++){
      cmFR[i]=MainSwingCoordinator.ccMyRecipeItemSpinner(0.6);//[TODO]::p-set
      cmFR[i].addChangeListener(lpFRChangeListener);
    }//..~
    JPanel lpFRInputPane=ScFactory.ccMyFlowPanel(2, false, "FR:(2 -> 1)");
    lpFRInputPane.add(cmFR[2]);
    lpFRInputPane.add(cmFR[1]);
    
    //-- as input flow
    cmAS=new JSpinner[4];
    for(int i=0;i<cmAS.length;i++){
      cmAS[i]=MainSwingCoordinator.ccMyRecipeItemSpinner(0.6);//[TODO]::p-set
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
    lpBottomPane.add(lpTitleInputPane,BorderLayout.PAGE_START);
    lpBottomPane.add(lpInputPane,BorderLayout.CENTER);
    lpBottomPane.add(lpOperate,BorderLayout.LINE_END);
    add(lpBottomPane,BorderLayout.PAGE_END);
    
  }//++!
  
  //===
  
  private double ssLimitRecipeSpinner(JSpinner pyTarget){
    Object lpSource=pyTarget.getValue();
    if(!(lpSource instanceof Double)){
      System.err.println("pppmain.SubRecipePane.ccLimitRecipeSpinner()::"
        + "illegal model");
      return 0.0d;
    }//..?
    double lpVal=(Double)lpSource;
    if(lpVal<C_RECIPE_PER_MIN){pyTarget.setValue(C_RECIPE_PER_MIN);}
    if(lpVal>C_RECIPE_PER_MAX){pyTarget.setValue(C_RECIPE_PER_MAX);}
    return lpVal;
  }//+++
  
  private void ssRegulateRecipeSpinner(JSpinner pyPrev, JSpinner pyNext){
    double lpPrev=ssLimitRecipeSpinner(pyPrev);
    double lpNext=ssLimitRecipeSpinner(pyNext);
    if(lpPrev>lpNext){pyNext.setValue(lpPrev);}
  }//+++
  
  private void ssExportToFile(){
    if(ScFactory.ccIsEDT()){
      VcConst.ccSetupTimeStampSeparator('_', '_');
      String lpPath=ScFactory.ccGetPathByFileChooser(
        MainSketch.ccGetReference().sketchPath
          +"\\"+"recipe"+VcConst.ccTimeStamp("_", true,false,false)+".csv"
      );
      VcConst.ccDefaultTimeStampSeparator();
      if(lpPath.equals("<np>")){return;}
      File lpFile=new File(lpPath);
      McWorkerManager.ccGetReference().ccSaveRecipeTable
        (McRecipeTable.ccGetReference().ccGetData(), lpFile);
    }//..?
  }//+++
  
  private void ssLoadFromFile(){
    
    //[TOFIX]::
    McRecipeTable lpTable=McRecipeTable.ccGetReference();
      
    //[TODO]::..it will always show a message box 
    //            telling the user that unsaved data will get lost.
    //[TODO]::..if it is running, it will be blocked.

    String lpPath=ScFactory.ccGetPathByFileChooser('f');
    if(lpPath.equals("<np>")){
      return;
    }
    File lpFile=new File(lpPath);
    //[TOFIX]::
    lpTable.ccLoadFromFile(lpFile);
  
  }//+++
  
  //===
  
  private final ChangeListener lpAGChangeListener = new ChangeListener() {
    @Override public void stateChanged(ChangeEvent ce){
      ssRegulateRecipeSpinner(cmAG[6], cmAG[5]);
      ssRegulateRecipeSpinner(cmAG[5], cmAG[4]);
      ssRegulateRecipeSpinner(cmAG[4], cmAG[3]);
      ssRegulateRecipeSpinner(cmAG[3], cmAG[2]);
      ssRegulateRecipeSpinner(cmAG[2], cmAG[1]);
    }//+++
  };
  
  private final ChangeListener lpFRChangeListener = new ChangeListener() {
    @Override public void stateChanged(ChangeEvent ce){
      ssRegulateRecipeSpinner(cmFR[2], cmFR[1]);
    }//+++
  };
  
  //[FUTURE]::private final ChangeListener lpASChangeListener
  //[FUTURE]::private final ChangeListener lpRCChangeListener
  
  //===
  
  @Override public void actionPerformed(ActionEvent ae){
    String lpCommand=ae.getActionCommand();
    
    if(lpCommand.equals("--button-load")){
      ssLoadFromFile();
      return;
    }//..?
    
    if(lpCommand.equals("--button-save")){
      ssExportToFile();
      return;
    }//..?
    
    System.err.println("pppmain.SubRecipePane.actionPerformed()::"
      + "unhandled_source:"+lpCommand);
  }//+++
  
}//***eof
