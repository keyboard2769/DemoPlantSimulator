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

import processing.data.JSONObject;
import kosui.pppswingui.McTableAdapter;

//[TODO]::should this be part of kosui??
public class McBaseKeyValueSetting extends McTableAdapter{
  
  private final JSONObject cmData;
  
  private boolean cmIsDone;
  private Object[] cmDesItemName;
  
  public McBaseKeyValueSetting(){
    super();
    cmData=new JSONObject();
    cmIsDone=false;
    cmDesItemName=null;
  }
  
  //===
  
  protected final void ccAddItem(String pxKey, String pxValue){
    cmData.setString(pxKey, pxValue);
  }//+++
  
  protected final void ccPack(){
    cmDesItemName=cmData.keys().toArray();
    if(cmDesItemName==null){return;}
    if(cmDesItemName.length<=1){return;}
    cmIsDone=true;
  }//+++
  
  public final String ccGetKey(int pxIndex){
    if(!cmIsDone){return "<>";}
    if(cmDesItemName==null){return "<>";}
    if(pxIndex<0){return "<>";}
    if(pxIndex>=cmDesItemName.length){return "<>";}
    return cmDesItemName[pxIndex].toString();
  }//+++
  
  public final String ccGetValue(int pxIndex){
    if(!cmIsDone){return "<>";}
    return ccGetValue(ccGetKey(pxIndex));
  }//+++
  
  public final String ccGetValue(String pxKey){
    if(!cmIsDone){return "<>";}
    if(!cmData.hasKey(pxKey)){return "<>";}
    return cmData.getString(pxKey);
  }//+++
  
  public final void ccSetValue(int pxIndex, String pxValue){
    if(!cmIsDone){return;}
    String lpKey=ccGetKey(pxIndex);
    if(cmData.hasKey(lpKey)){
      cmData.setString(lpKey, pxValue);
    }
  }//+++

  //=== table
  
  @Override public String getColumnName(int pxColumnIndex){
    switch(pxColumnIndex){
      case 0:return "key";
      case 1:return "value";
      default:return "<>";
    }
  }//+++
  
  @Override public int getRowCount(){
    if(!cmIsDone){return 2;}
    return cmDesItemName.length;
  }//+++
  
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex){
    if(!cmIsDone){return "<>";}
    switch(pxColumnIndex){
      case 0:return ccGetKey(pxRowIndex);
      case 1:return ccGetValue(pxRowIndex);
      default:return "<>";
    }//..?
  }//+++
  
  //=== file
  //[TOIMP]::read from file
  //[TOIMP]::save to file
  
}//+++
