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

import processing.data.FloatDict;
import kosui.pppswingui.McTableAdapter;
import static kosui.ppputil.VcConst.ccRoundForOneAfter;

public class McBaseFloatSetting extends McTableAdapter{
  
  protected static final String C_INVALID = "<>";
    
  //===
  
  protected final FloatDict cmData;
  
  protected boolean cmIsDone;
  protected String cmName;
  private Object[] cmDesItemName;
  
  public McBaseFloatSetting(){
    super();
    cmData=new FloatDict();
    cmIsDone=false;
    cmDesItemName=null;
    cmName="<>";
  }//++!
  
  //===
  
  protected final void ccAddItem(String pxKey, float pxValue){
    cmData.add(pxKey, pxValue);
  }//+++
  
  protected final void ccPack(String pxName){
    cmData.sortKeys();
    cmDesItemName=cmData.keyArray();
    if(cmDesItemName==null){return;}
    if(cmDesItemName.length<=1){return;}
    cmName=pxName;
    cmIsDone=true;
  }//+++
  
  //===
    
  public final void ccSetValue(int pxIndex, float pxValue){
    if(!cmIsDone){return;}
    String lpKey=ccGetKey(pxIndex);
    if(cmData.hasKey(lpKey)){
      cmData.set(lpKey, pxValue);
    }//..?
  }//+++
  
  public final void ccSetValue(String pxKey, float pxValue){
    if(!cmIsDone){return;}
    if(cmData.hasKey(pxKey)){
      cmData.set(pxKey, pxValue);
    }//..?
  }//+++
  
  //===
  
  public final String ccGetKey(int pxIndex){
    if(!cmIsDone){return C_INVALID;}
    if(cmDesItemName==null){return C_INVALID;}
    if(pxIndex<0){return C_INVALID;}
    if(pxIndex>=cmDesItemName.length){return C_INVALID;}
    return cmDesItemName[pxIndex].toString();
  }//+++
  
  //===
  
  public final float ccGetFloatValue(String pxKey){
    if(!cmIsDone){return 0;}
    if(cmData.hasKey(pxKey))
      {return cmData.get(pxKey);}
    else
      {return -1f;}
  }//+++
  
  public final float ccGetFloatValue(int pxIndex){
    if(!cmIsDone){return 0;}
    return ccGetFloatValue(ccGetKey(pxIndex));
  }//+++
  
  public final int ccGetIntegerValue(String pxKey){
    if(!cmIsDone){return 0;}
    return (int)(ccGetFloatValue(pxKey));
  }//+++
  
  public final int ccGetIntegerValue(int pxIndex){
    if(!cmIsDone){return 0;}
    return ccGetIntegerValue(ccGetKey(pxIndex));
  }//+++
  
  public final String[] ccGetKeyArray(){
    return cmData.keyArray();
  }//+++
  
  //===
  
  @Override public String toString(){
    if(!cmIsDone){return super.toString();}
    return cmName;
  }//+++
  
  //=== table
  
  @Override public String getColumnName(int pxColumnIndex){
    switch(pxColumnIndex){
      case 1:return "key";
      case 0:return "value";
      default:return C_INVALID;
    }//..?
  }//+++
  
  @Override public int getRowCount(){
    if(!cmIsDone){return 2;}
    return cmDesItemName.length;
  }//+++
  
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex){
    if(!cmIsDone){return C_INVALID;}
    switch(pxColumnIndex){
      case 0:
        return Float.toString
          (ccRoundForOneAfter(ccGetFloatValue(pxRowIndex)));
      case 1:return ccGetKey(pxRowIndex);
      default:return C_INVALID;
    }//..?
  }//+++
  
  //=== file
  //[TOIMP]::read from file
  //[TOIMP]::save to file
  
}//+++
