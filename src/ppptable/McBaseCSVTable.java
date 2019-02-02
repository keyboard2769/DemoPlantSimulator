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
import processing.data.Table;
import kosui.pppswingui.McTableAdapter;
import kosui.pppswingui.ScFactory;

public class McBaseCSVTable extends McTableAdapter{
  
  private static final String C_FORM="csv";
  
  protected final Table cmData;

  public McBaseCSVTable(){
    cmData=new Table();
  }//++!
  
  //===

  @Override public int getRowCount(){
    return cmData.getRowCount();
  }//+++

  @Override public int getColumnCount(){
    return cmData.getColumnCount();
  }//+++
  
  @Override public String getColumnName(int pxColumnIndex){
    return cmData.getColumnTitle(pxColumnIndex);
  }//+++
  
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex){
    return cmData.getString(pxRowIndex, pxColumnIndex);
  }//+++
  
  //===
  
  public final String[] ccGetColumnTitles(){
    return cmData.getColumnTitles();
  }//+++
  
  public final Table ccGetData(){
    return cmData;
  }//+++
  
  public final void ccSaveToFile(File pxFile){
    if(!pxFile.isAbsolute()){
      System.err.println("McBaseCSVTable.ccSaveToFile()::"
        + "passed referrence is not an absolute path!!");
      return;
    }//..?
    if(ScFactory.ccIsEDT()){
      SwingWorker lpSaver=new SwingWorker<Void, Void>() {
        private boolean lpDone=false;
        @Override protected Void doInBackground() throws Exception{
          try{
            cmData.save(pxFile, C_FORM);
            lpDone=true;
          }catch(Exception e){
            System.err.println("McBaseCSVTable.ccSaveToFile()::"
              + e.getMessage());
            lpDone=false;
          }
          return null;
        }//+++
        @Override protected void done(){
          System.out.println("McBaseCSVTable.ccSaveToFile()::"
            + (lpDone?"table successfully saved.":"failed to save table"));
        }//+++
      };
      lpSaver.execute();
    }//..?
  }//+++
  
}//***eof
