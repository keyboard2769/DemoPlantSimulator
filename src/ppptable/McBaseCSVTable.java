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

import processing.data.Table;
import kosui.pppswingui.McTableAdapter;

public class McBaseCSVTable extends McTableAdapter{
  
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
  
  public final void ccClearRows(){
    cmData.clearRows();
  }//+++
  
}//***eof
