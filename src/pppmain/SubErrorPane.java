/*
 * Copyright (C) 2019 keypad
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import kosui.pppswingui.ScConsole;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScList;
import kosui.ppputil.VcConst;
import ppptable.McErrorMessage;
import ppptable.McErrorMessageFolder;

public class SubErrorPane extends JPanel
  implements ListSelectionListener, ActionListener
{
  
  private static SubErrorPane self;
  public static SubErrorPane ccGetReference(){
    if(self==null){self=new SubErrorPane();}
    return self;
  }//++!
  
  //===
  
  private static final String C_DEFAULT="::have a nice day";

  //===
  
  private ScList cmList;
  private ScConsole cmStackConsole;
  private JTextArea cmDescriptionArea;
  
  private SubErrorPane(){
    super(new BorderLayout(1,1));
    ccInit();
  }//++!
  
  private void ccInit(){
    
    //-- list
    cmList=new ScList(150, 100);
    cmList.ccAdd("...");
    cmList.ccAddListSelectionListener(this);
    
    //-- histroy
    cmStackConsole=new ScConsole(12, 40);
    fsCheers();
    
    //-- description
    cmDescriptionArea=new JTextArea(4, 40);
    cmDescriptionArea.setEnabled(false);
    cmDescriptionArea.setEditable(false);
    cmDescriptionArea.setBackground(Color.LIGHT_GRAY);
    cmDescriptionArea.setDisabledTextColor(Color.BLACK);
    cmDescriptionArea.setText("%no-error-is-on-going%");
    
    //-- operate
    JPanel lpOperatePane=ScFactory.ccMyFlowPanel(2, false);
    lpOperatePane.add
      (ScFactory.ccMyCommandButton("CLEAR-LOG","--button-clear",this));
    lpOperatePane.add
      (ScFactory.ccMyCommandButton("SAVE-LOG","--button-save",this));
    lpOperatePane.add
      (ScFactory.ccMyCommandButton("PRINT-LOG","--button-print",this));
    
    //-- packing
    add(cmList,BorderLayout.LINE_START);
    add(cmStackConsole,BorderLayout.CENTER);
    add(cmDescriptionArea,BorderLayout.PAGE_END);
    add(lpOperatePane,BorderLayout.PAGE_START);
    
  }//++!
  
  public final void ccStack(String pxLine){
    if(!VcConst.ccIsValidString(pxLine)){return;}
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run(){
        cmStackConsole.ccStack(pxLine);
      }//+++
    });
  }//+++
  
  public final void ccApplyListModel(String[] pxModel){
    cmList.ccRefreshModel(pxModel);
    MainSwingCoordinator.ccGetReference().ccSetErrorSum(pxModel.length);
  }//+++
  
  //===

  @Override public void actionPerformed(ActionEvent ae){
    String lpCommand=ae.getActionCommand();
    
    //-- switch
    
    if(lpCommand.equals("--button-clear")){
      fsClearHistory();
      return;
    }//+++
    
    if(lpCommand.equals("--button-save")){
      fsSaveToFile();
      return;
    }//+++
    
    if(lpCommand.equals("--button-print")){
      cmStackConsole.ccPrint();
      return;
    }//+++
    
    //-- last
    System.err.println("pppmain.SubErrorPane.actionPerformed():"
      + "unhandled_command:"+lpCommand);
    
  }//+++
  
  private void fsClearHistory(){
    cmStackConsole.ccClear();
    fsCheers();
  }//+++
  
  private void fsCheers(){
    cmStackConsole.ccStack(C_DEFAULT);
    cmStackConsole.ccStack(VcConst.ccTimeStamp("--", true, false,false));
  }//+++
  
  private void fsSaveToFile(){
    if(ScFactory.ccIsEDT()){
      final String lpPath=ScFactory.ccGetPathByFileChooser('f');
      if(lpPath.equals("<np>")){return;}
      final File lpFile=new File(lpPath);
      final String[] lpData={cmStackConsole.ccGetText()};
      
      if(!lpFile.isFile()){return;}
      
      SwingWorker lpWorker=new SwingWorker() {
        private boolean lpDone=false;
        @Override protected Object doInBackground() throws Exception{
          try{
            processing.core.PApplet.saveStrings(lpFile, lpData);
            lpDone=true;
          }catch(Exception e){
            System.err.println(".doInBackground().cought:"+e.toString());
            lpDone=false;
          }
          return null;
        }//+++
        @Override protected void done(){
          SwingUtilities.invokeLater(new Runnable() {
            @Override public void run(){
              ScFactory.ccMessageBox(lpDone?
                "file saving ok":
                "file saving failed"
              );
            }
          });
        }//+++
      };
      
      lpWorker.execute();
      
    }//..?
  }//+++
  
  //===

  @Override public void valueChanged(ListSelectionEvent lse){
    
    //-- index check
    if(cmList.ccGetCurrentIndex()<0){return;}
    
    //-- from list to error
    String lpIndex=cmList.ccGetCurrentItem().split("/")[0];
    McErrorMessage lpError=McErrorMessageFolder.ccGetReference()
      .ccGet(VcConst.ccParseIntegerString(lpIndex));
    
    //-- apply description
    StringBuilder lpBuilder=new StringBuilder(" # ");
    lpBuilder.append(lpError.cmID);
    lpBuilder.append(" : ");
    lpBuilder.append(lpError.cmContent);
    lpBuilder.append(" \n");
    lpBuilder.append(lpError.cmDescription);
    cmDescriptionArea.setText(lpBuilder.toString());
    
  }//+++
  
 }//***eof
