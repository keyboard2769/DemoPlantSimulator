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
import processing.data.Table;

public final class McWorkerManager {

  private static McWorkerManager self;
  private McWorkerManager(){}//++!
  public static McWorkerManager ccGetReference(){
    if(self==null){self=new McWorkerManager();}
    return self;
  }//++!
  
  //===
  
  private Table cmCSVDataRef = null;
  private String[] cmTextDataRef=null;
  private File cmFileRef=null;
  
  //===
  
  private final SwingWorker<Void, Void> cmErrorLogSaver=new SwingWorker() {
    private boolean lpDone=false;
    @Override protected Void doInBackground() throws Exception{
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
          ScFactory.ccMessageBox(lpDone?
            "errorlog saving ok":
            "errorlog saving failed"
          );
          cmTextDataRef=null;
          cmFileRef=null;
        }
      });
    }//+++
  };
  public final void ccSaveErrorLog(String[] pxText, File pxFile){
    if(!ScFactory.ccIsEDT()){return;}
    cmTextDataRef=pxText;
    cmFileRef=pxFile;
    if(!cmFileRef.isAbsolute()){
      System.err.println("McWorkerManager.ccSaveErrorLog()::"
        + "file_ref_is_not_absolute");
      return;
    }
    cmErrorLogSaver.execute();
  }//+++
  
  //===
  
  private final SwingWorker<Void, Void> cmRecipeTableSaver=new SwingWorker() {
    private boolean lpDone;
    @Override protected Void doInBackground() throws Exception{
      if(cmCSVDataRef==null || cmFileRef==null){return null;}
      try{
        cmCSVDataRef.save(cmFileRef, "csv");
        lpDone=true;
      }catch(Exception e){
        System.err.println("cmRecipeTableSaver.doInBackground().cought:"
          +e.toString());
        lpDone=false;
      }//..$
      return null;
    }//+++
    @Override protected void done(){
      SwingUtilities.invokeLater(new Runnable() {
        @Override public void run(){
          ScFactory.ccMessageBox(lpDone?
            "recipe saving ok":
            "recipe saving failed"
          );
          cmCSVDataRef=null;
          cmFileRef=null;
        }
      });
    }//+++
  };
  public final void ccSaveRecipeTable(Table pxTable, File pxFile){
    if(!ScFactory.ccIsEDT()){return;}
    cmCSVDataRef=pxTable;
    cmFileRef=pxFile;
    if(!cmFileRef.isAbsolute()){
      System.err.println("McWorkerManager.ccSaveRecipeTable()::"
        + "file_ref_is_not_absolute");
      return;
    }//..?
    cmRecipeTableSaver.execute();
  }//+++
  
}//***eof
