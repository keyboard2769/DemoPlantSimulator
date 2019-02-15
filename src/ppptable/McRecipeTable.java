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

import kosui.ppputil.VcConst;
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
  
  public final void ccAddRow(TableRow pxRow){
    if(pxRow==null){return;}
    TableRow lpRow=cmData.addRow();
    if(lpRow.getColumnCount()!=pxRow.getColumnCount()){return;}
    for(int i=0,s=lpRow.getColumnCount();i<s;i++){
      lpRow.setString(i, pxRow.getString(i));
    }//..~
  }//+++
  
  public final void ccAddDummyRecipe(){
    TableRow lpRow=cmData.addRow();
    for(int i=0,s=McRecipeRecord.C_TITLE.length;i<s;i++){
      lpRow.setString(i,
        (i==0)?"%d%":
        (i==1)?"%n%":
        "%pt%"
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
  
  public final McRecipeRecord ccGetRecord(int pxRow){
    
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
    if(lpRow==null){
      System.err.println("ppptable.McRecipeTable.ccGetRecipe()+"
        + "unknown_error_occurred_while_getting_row");
      return lpRes;
    }//..?
    McRecipeRecord.ccTransferRecord(lpRes, lpRow);
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
  
  synchronized 
  public final void ccUnlock(){
    cmBlocked=false;
  }//+++
  
  synchronized 
  public final void ccBlock(){
    cmBlocked=true;
  }//+++
  
  //===
  
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
