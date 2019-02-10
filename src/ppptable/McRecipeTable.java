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

package ppptable;

import java.io.File;
import javax.swing.SwingWorker;
import kosui.pppswingui.ScFactory;
import kosui.ppputil.VcConst;
import processing.data.Table;
import processing.data.TableRow;
import static processing.core.PApplet.constrain;

public final class McRecipeTable extends McBaseCSVTable{

  private static McRecipeTable self;
  public static McRecipeTable ccGetReference(){
    if(self==null){self=new McRecipeTable();}
    return self;
  }//++!
  
  //===
  
  private boolean cmBlocked;

  private McRecipeTable(){
    
    super();
    cmBlocked=false;
    
    for(String it:McRecipeRecord.C_TITLE){
      cmData.addColumn(it);
    }//..~
    
    ccAddDummyRecipe();
    
  }//++!
  
  public final void ccAddDummyRecipe(){
    TableRow lpRow=cmData.addRow();
    for(int i=0,s=McRecipeRecord.C_TITLE.length;i<s;i++){
      lpRow.setString(i,
        (i==0)?"%d":
        (i==1)?"%n":
        "%pt"
      );
    }//..~
  }//++!
  
  public final void ccAddRecipe(McRecipeRecord pxRecord){
    TableRow lpRow=cmData.addRow();
    for(int i=0,s=McRecipeRecord.C_TITLE.length;i<s;i++){
      lpRow.setString(i, pxRecord.ccGetString(i));
    }//..~
  }//+++
  
  public final void ccRemoveRecipe(int pxRow){
    //--  check in
    int lpRowCount=cmData.getRowCount();
    if(lpRowCount<=1){
      return;
    }//..?
    if(pxRow<0 || pxRow>lpRowCount){
      System.err.println("ppptable.McRecipeTable.ccGetRecipe()+"
        + "passed_row_request_out_of_bound");
      return;
    }//..?
    if(cmBlocked){
      System.err.println("McRecipeTable.ccGetRecipeKG()::"
        + "blocked_out_for_loading_file");
      return;
    }//..?
    try{
      cmData.removeRow(pxRow);
    }catch(Exception e){
      System.err.println("ppptable.McRecipeTable.ccRemoveRecipe()::"
        + "unannounced_exception:"+e.getMessage());
    }//..$
  }//+++
  
  public final McRecipeRecord ccGetRecipe(int pxRow){
    
    //--  check in
    McRecipeRecord lpRes=new McRecipeRecord();
    if(pxRow<0 || pxRow>cmData.getRowCount()){
      System.err.println("ppptable.McRecipeTable.ccGetRecipe()+"
        + "passed_row_request_out_of_bound");
      return lpRes;
    }//..?
    if(cmBlocked){
      System.err.println("McRecipeTable.ccGetRecipeKG()::"
        + "blocked_out_for_loading_file");
      return lpRes;
    }//..?
    
    //-- assemble
    TableRow lpRow=cmData.getRow(pxRow);
    for(int i=0,s=McRecipeRecord.C_TITLE.length;i<s;i++){
      lpRes.ccSetString(i, lpRow.getString(i));
    }//..~
    return lpRes;
    
  }//+++
  
  public final McCategoryIntegerBundle ccGetRecipeKG(
    int pxIndex, int pxTotal
  ){
    
    //-- checking
    McCategoryIntegerBundle lpRes=new McCategoryIntegerBundle();
    
    if(pxTotal<=1){
      System.err.println("ppptable.McRecipeTable.ccGetRecipeKG()::"
        + "total_value_setting_unprocessable:"+pxTotal);
      return lpRes;
    }//..?
    if(cmBlocked){
      System.err.println("McRecipeTable.ccGetRecipeKG()::"
        + "blocked_out_for_loading_file");
      return lpRes;
    }//..?
    TableRow lpRow=cmData.findRow(Integer.toString(pxIndex), "id");
    if(lpRow==null){
      System.out.println("ppptable.McRecipeTable.ccGetRecipeKG()::"
        + "cannot_find_recipe_index");
      return lpRes;
    }//..?
    
    //-- assemble
    McRecipeRecord lpRecord=new McRecipeRecord();
    for(int i=0,s=McRecipeRecord.C_TITLE.length;i<s;i++){
      lpRecord.ccSetString(i, lpRow.getString(i));
    }//..~
    
    lpRes.ccSetAG(6, ssGetKG(lpRecord.cmAG[6],pxTotal));
    lpRes.ccSetAG(5, ssGetKG(lpRecord.cmAG[5],pxTotal));
    lpRes.ccSetAG(4, ssGetKG(lpRecord.cmAG[4],pxTotal));
    lpRes.ccSetAG(3, ssGetKG(lpRecord.cmAG[3],pxTotal));
    lpRes.ccSetAG(2, ssGetKG(lpRecord.cmAG[2],pxTotal));
    lpRes.ccSetAG(1, ssGetKG(lpRecord.cmAG[1],pxTotal));
    lpRes.ccSetFR(2, ssGetKG(lpRecord.cmFR[2],pxTotal));
    lpRes.ccSetFR(1, ssGetKG(lpRecord.cmFR[1],pxTotal));
    lpRes.ccSetAS(1, ssGetKG(lpRecord.cmAS[1],pxTotal));
    return lpRes;
    
  }//+++
  
  private int ssGetKG(String pxRecipeValue, int pxTotal){
    float lpValue=VcConst.ccParseFloatString(pxRecipeValue);
    lpValue=constrain(lpValue,0f,100f);
    return (int)(((float)pxTotal)*lpValue/100f);
  }//+++
  
  //===
  
  private void ccCheckAndApply(Table pxTable){
    cmBlocked=true;
    
    //[TODO]::????
    /*
     *-> if column count dont match the size of key , exit
     *-> if row count it bigger than the capability, exit. (supposedly 255)
     *-> get all those keys, aka, coloumn names
     *-> check if every key is in the title
     *-> if any is not, exist
     *-> clear the data
     *-> get all those rows
     *-> for every row, remember index first
     *-> if the index is not a new one, skip to next
     *-> if the name is an invalid string, replace a default one 
     *-> if the value is less than 0 or bigger than 199, skip to next 
     *   (we just dont check the prev-next relation at here)
     *-> thats all
     *
     */
    System.out.println("ppptable.McRecipeTable.ccCheckAndApply()"
      + pxTable.toString());
    ScFactory.ccMessageBox("not supported yet?!");
    
    cmBlocked=false;
  }//+++
  
  //[TOFIX]::
  public final void ccLoadFromFile(File pxFile){
    if(!pxFile.isAbsolute()){
      System.err.println("McRecipeTable.ccLoadFromFile()::"
        + "passed referrence is not an absolute path!!");
      return;
    }//..?
    //[TODO]:: if the file is too big , exit
    if(ScFactory.ccIsEDT()){
      SwingWorker lpLoader=new SwingWorker<Void, Void>() {
        private boolean lpDone=false;
        private Table lpResult=null;
        @Override protected Void doInBackground() throws Exception{
          try{
            lpResult=new Table(pxFile);
            lpDone=true;
          }catch(Exception e){
            System.err.println("McRecipeTable.ccLoadFromFile()::"
              + e.getMessage());
            lpDone=false;
            lpResult=null;
          }
          return null;
        }//+++
        @Override protected void done(){
          System.out.println("McRecipeTable.ccLoadFromFile()::"
            + (lpDone?"table successfully loaded.":"failed to load table"));
          if(lpResult!=null){
            ccCheckAndApply(lpResult);
          }//..?
        }//+++
      };
      lpLoader.execute();
    }//..?
  }//+++
  
  @Deprecated public final void dummyLoadFromFile(){
    
    McRecipeRecord lpDummy=new McRecipeRecord();
    
    lpDummy.ccSetup(
      1,"test-01",
      new float[]{0, 80,70,50, 40,30,20},
      new float[]{0, 6,3},
      new float[]{0, 5.5f}
    );
    ccAddRecipe(lpDummy);
    
    lpDummy.ccSetup(
      2,"test-02",
      new float[]{0, 0,0,0, 40,30,20},
      new float[]{0, 0,6},
      new float[]{0, 5}
    );
    ccAddRecipe(lpDummy);
    
    lpDummy.ccSetup(
      3,"test-03",
      new float[]{0, 40,30,20, 20,20,20},
      new float[]{0, 6,0},
      new float[]{0, 0}
    );
    ccAddRecipe(lpDummy);
    
    lpDummy.ccSetup(
      21,"TCTT",
      new float[]{0, 60,50,40, 30,20,10},
      new float[]{0, 6,3},
      new float[]{0, 2}
    );
    ccAddRecipe(lpDummy);
    
  }//+++
  
}//***eof
