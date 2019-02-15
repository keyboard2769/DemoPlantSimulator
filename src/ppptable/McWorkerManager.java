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
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import kosui.pppswingui.ScFactory;
import kosui.ppputil.VcConst;
import pppmain.MainSketch;
import pppmain.SubRecipePane;
import pppmain.TabWireManager;
import processing.data.Table;
import processing.data.StringList;
import processing.data.JSONObject;
import processing.data.TableRow;

public final class McWorkerManager {

  private static McWorkerManager self;
  private McWorkerManager(){}//++!
  public static McWorkerManager ccGetReference(){
    if(self==null){self=new McWorkerManager();}
    return self;
  }//++!
  
  //===
  
  private final MainSketch mainSketch
    = MainSketch.ccGetReference();
  
  private String cmPath           = null;
  private File cmFileRef          = null;
  
  private Table cmCSVDataRef      = null;
  private String[] cmTextDataRef  = null;
  private JSONObject cmJsonObjRef = null;
  
  //=== 
  
  private File ssInstantiateFile(
    String pxName, String pxExtension,
    boolean pxHasDate, boolean pxHasTime, boolean pxHasSecond
  ){
    //-- check in
    if(!VcConst.ccIsValidString(pxName)){return null;}
    if(pxExtension == null){return null;}
    if(!pxExtension.startsWith(".")){return null;}
    //-- generate
    VcConst.ccSetupTimeStampSeparator('_', '_');
    String lpPath=ScFactory.ccGetPathByFileChooser(
      MainSketch.C_V_PWD+VcConst.C_V_PATHSEP
      +pxName+VcConst.ccTimeStamp("_", pxHasDate,pxHasTime,pxHasSecond)
      +pxExtension
    );
    VcConst.ccDefaultTimeStampSeparator();
    if(lpPath.equals(ScFactory.C_M_INVALID)){return null;}
    return new File(lpPath);
  }//+++
  
  private File ssGetFileFromSwing(String pxExtensionForCheck){
    
    //-- check in
    if(pxExtensionForCheck==null){return null;}
    if(pxExtensionForCheck.length()<2){return null;}
    if(!pxExtensionForCheck.startsWith(".")){return null;}
    
    //-- generate
    String lpPath=ScFactory.ccGetPathByFileChooser('f');
    if(lpPath.equals(ScFactory.C_M_INVALID)){return null;}
    if(!lpPath.endsWith(pxExtensionForCheck)){
      ScFactory.ccMessageBox("file name extension illeagal.");
      return null;
    }//..?
    File lpFile = new File(lpPath);
    if(!lpFile.exists()){
      ScFactory.ccMessageBox("file dees not exist!");
      return null;
    }//..?
    return lpFile;
    
  }//+++
  
  //===
  
  private class McTextSaver extends SwingWorker<Void, Void> {
    private boolean lpDone;
    @Override protected Void doInBackground() throws Exception{
      lpDone=false;
      if(cmTextDataRef==null || cmFileRef==null){return null;}
      try{
        processing.core.PApplet.saveStrings(cmFileRef, cmTextDataRef);
        lpDone=true;
      }catch(Exception e){
        System.err.println(".doInBackground().cought:"+e.toString());
        lpDone=false;
      }//..$
      return null;
    }//+++
    @Override protected void done(){
      SwingUtilities.invokeLater(new Runnable() {
        @Override public void run(){
          String lpName=cmFileRef.getName();
          ScFactory.ccMessageBox(lpName+(lpDone?
            "saved successfully":
            "saving failed"
          ));
          cmTextDataRef=null;
          cmFileRef=null;
        }//+++
      });
    }//+++
  }//***
  
  public final void ccSaveText(String[] pxText, File pxFile){
    if(!ScFactory.ccIsEDT()){return;}
    cmTextDataRef=pxText;
    cmFileRef=pxFile;
    if(!cmFileRef.isAbsolute()){
      System.err.println("McWorkerManager.ccSaveErrorLog()::"
        + "file_ref_is_not_absolute");
      return;
    }//..?
    new McTextSaver().execute();
  }//+++
  
  public final void ccSaveText(
    String[] pxText, String pxName, String pxExtension,
    boolean pxHasDate, boolean pxHasTime, boolean pxHasSecond
  ){
    File lpFile=ssInstantiateFile
      (pxName, pxExtension, pxHasDate, pxHasTime, pxHasSecond);
    if(lpFile==null){return;}
    ccSaveText(pxText, lpFile);
  }//+++
  
  //===
  
  private class McTableSaver extends SwingWorker<Void, Void>{
    private boolean lpDone;
    @Override protected Void doInBackground() throws Exception{
      lpDone=false;
      if(cmCSVDataRef==null || cmFileRef==null){return null;}
      try{
        cmCSVDataRef.save(cmFileRef, "csv");
        lpDone=true;
      }catch(Exception e){
        System.err.println("McTableSaver.doInBackground().cought:"
          +e.toString());
        lpDone=false;
      }//..$
      return null;
    }//+++
    @Override protected void done(){
      SwingUtilities.invokeLater(new Runnable() {
        @Override public void run(){
          String lpName=cmFileRef.getName();
          ScFactory.ccMessageBox(lpName+(lpDone?
            "saved successfully":
            "saving failed"
          ));
          cmCSVDataRef=null;
          cmFileRef=null;
        }//+++
      });
    }//+++
  }//***
  
  public final void ccSaveTable(Table pxTable, File pxFile){
    if(!ScFactory.ccIsEDT()){return;}
    cmCSVDataRef=pxTable;
    cmFileRef=pxFile;
    if(!cmFileRef.isAbsolute()){
      System.err.println("McWorkerManager.ccSaveRecipeTable()::"
        + "file_ref_is_not_absolute");
      return;
    }//..?
    new McTableSaver().execute();
  }//+++
  
  public final void ccSaveTable(
    Table pxTable, String pxName, String pxExtension,
    boolean pxHasDate, boolean pxHasTime, boolean pxHasSecond
  ){
    File lpFile=ssInstantiateFile
      (pxName, pxExtension, pxHasDate, pxHasTime, pxHasSecond);
    if(lpFile==null){return;}
    ccSaveTable(pxTable, lpFile);
  }//+++
  
  //===
  
  private class McChineseDictionaryLoader extends SwingWorker<Void, Void>{
    private boolean lpDone;
    @Override protected Void doInBackground() throws Exception{
      lpDone=false;
      if(cmJsonObjRef!=null || cmPath==null){return null;}
      try{
        cmJsonObjRef=mainSketch.loadJSONObject(cmPath);
        lpDone=true;
      }catch(Exception e){
        System.err.println(
          "cmChineseDictionaryLoader.doInBackground()::"
            +e.getMessage()
        );
        lpDone=false;
      }//..$
      if(!lpDone || cmJsonObjRef==null){
        System.err.println("cmChineseDictionaryLoader.doInBackground()::"
          + "unknown loading error");
        lpDone=false;
        return null;
      }//..?
      McTranslator lpRef=McTranslator.ccGetReference();
      for(Object it:cmJsonObjRef.keys().toArray()){
        if(it instanceof String){
          lpRef.ccAddToCurrent(
            ((String)it),
            cmJsonObjRef.getString(((String)it))
          );
        }//..?
      }//..~
      return null;
    }//+++
    @Override protected void done(){
      TabWireManager.ccSetCommand(TabWireManager.C_K_APPLY_ZN_DIC);
      cmJsonObjRef=null;
      cmPath=null;
      System.out.println(
        "cmChineseDictionaryLoader.done()::"
        +(lpDone?"successed":"failed")
      );
    }//+++
  }//***
  
  public final void ccLoadChineseDictionary(){
    if(!ScFactory.ccIsEDT()){return;}
    File lpFile=ssGetFileFromSwing(".json");
    if(lpFile==null){return;}
    cmPath=lpFile.getAbsolutePath();
    new McChineseDictionaryLoader().execute();
  }//+++
  
  //===
  
  private class McRecipeTableLoader extends SwingWorker<Void, Void>{
    private int lpChecker;
    @Override protected Void doInBackground() throws Exception{
      
      //-- check in
      lpChecker=0;
      if(cmCSVDataRef!=null || cmPath==null){return null;}
            
      //-- load from file
      try{
        cmCSVDataRef=mainSketch.loadTable(cmPath, "header,csv");
        if(cmCSVDataRef!=null){lpChecker=1;}
      }catch(Exception e){
        lpChecker=-301;
        System.err.println("McRecipeTableLoader.doInBackground()::"
        +e.getMessage());
      }//..$//..$
      if(lpChecker<=0 || cmCSVDataRef==null){return null;}
      
      //-- check title length
      String[] lpLoadedTitles=cmCSVDataRef.getColumnTitles();
      if(lpLoadedTitles==null){
        lpChecker=-291;
        return null;
      }//..?
      if(lpLoadedTitles.length != McRecipeRecord.C_TITLE.length){
        lpChecker=-302;
        return null;
      }//..?
      
      //-- check row count
      if(cmCSVDataRef.getRowCount()<=2){
        lpChecker=-303;
        return null;
      }//..?
      
      //-- check title form
      lpChecker=1;
      StringList lpTitleList=new StringList(McRecipeRecord.C_TITLE);
      for(String it:cmCSVDataRef.getColumnTitles()){
        if(!lpTitleList.hasValue(it)){lpChecker=-304;break;}
      }//..?
      if(lpChecker<=0){
        return null;
      }//..?
      
      //-- clear and add
      McRecipeTable lpTableRef=McRecipeTable.ccGetReference();
      lpTableRef.ccClearRows();
      McRecipeRecord lpRecord=new McRecipeRecord();
      for(int i=0,s=cmCSVDataRef.getRowCount();i<s;i++){
        TableRow lpRow = cmCSVDataRef.getRow(i);
        if(lpRow==null){continue;}
        McRecipeRecord.ccTransferRecord(lpRecord, lpRow);
        if(lpRecord.ccCheck()){
          lpTableRef.ccAddRow(lpRow);
        }else{
          System.err.println("McRecipeTableLoader.doInBackground()::"
            + "row_didnt_pass_value_check: "+i);
        }//..?
      }//..~
      
      //-- done
      return null;
      
    }//+++
    @Override protected void done(){
      SwingUtilities.invokeLater(new Runnable() {
        @Override public void run(){
          McRecipeTable.ccGetReference().ccUnlock();
          SubRecipePane.ccGetReference().ccRefreshTable();
          ScFactory.ccMessageBox(cmPath+(lpChecker>0?
            " loaded successfully:with ":
            " loading failed:with "
          )+lpChecker);
          cmCSVDataRef=null;
          cmPath=null;
        }//+++
      });
    }//+++
  }//***
  
  public final void ccLoadRecipeTable(){
    if(!ScFactory.ccIsEDT()){return;}
    File lpFile=ssGetFileFromSwing(".csv");
    if(lpFile==null){return;}
    cmPath=lpFile.getAbsolutePath();
    McRecipeTable.ccGetReference().ccBlock();
    new McRecipeTableLoader().execute();
  }//+++
  
}//***eof
