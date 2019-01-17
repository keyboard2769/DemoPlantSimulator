/*
 * Copyright (C) 2019 2053
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

//[TODO]::should this be part of kosui??

import java.util.HashMap;
import kosui.ppplogic.ZcRangedModel;
import kosui.ppputil.VcConst;

public class McBaseKeyValueRangedSetting extends McBaseKeyValueSetting{
  
  private final HashMap<String, ZcRangedModel> cmFilterList;

  public McBaseKeyValueRangedSetting(){
    super();
    cmFilterList=new HashMap<>();
  }//++!
  
  protected final void ccAddItem(
    String pxKey, int pxValue, int pxMin, int pxRange
  ){
    ZcRangedModel lpModel=new ZcRangedModel(pxMin, pxRange);
    int lpValue=lpModel.ccLimit(pxValue);
    cmFilterList.put(pxKey, lpModel);
    ccAddItem(pxKey, Integer.toString(lpValue));
  }//+++
  
  //===
  
  public final void ccSetIntegerValue(int pxIndex, int pxValue){
    if(!cmIsDone){return;}
    ccSetIntegerValue(ccGetKey(pxIndex), pxValue);
  }//+++
  
  public final void ccSetIntegerValue(String pxKey, int pxValue){
    if(!cmIsDone){return;}
    ZcRangedModel lpFilter=cmFilterList.get(pxKey);
    int lpValue=lpFilter.ccLimit(pxValue);
    ccSetValue(pxKey, Integer.toString(lpValue));
  }//+++
  
  public final void ccSetFloatValue(int pxIndex, float pxValue){
    if(!cmIsDone){return;}
    ccSetFloatValue(ccGetKey(pxIndex), pxValue);
  }//+++
  
  public final void ccSetFloatValue(String pxKey, float pxValue){
    if(!cmIsDone){return;}
    ZcRangedModel lpFilter=cmFilterList.get(pxKey);
    int lpIntegerValue=(int)pxValue;
    if(lpFilter.ccContains(lpIntegerValue)){
      ccSetValue(pxKey, Float.toString(pxValue));
    }else{
      lpIntegerValue=lpFilter.ccLimit(lpIntegerValue);
      ccSetValue(pxKey, Float.toString((float)lpIntegerValue));
    }//..?
  }//+++
  
  //===
  
  public final void ccConstrainAll(){
    if(!cmIsDone){return;}
    for(Object it:cmData.keys()){
      String lpKey=it.toString();
      ccConstrain(lpKey);
    }//..~
  }//+++
  
  public final void ccConstrain(String pxKey){
    if(!cmIsDone){return;}
    
    ZcRangedModel lpFilter=cmFilterList.get(pxKey);
    String lpValue=ccGetValue(pxKey);
    String lpFilted="2";
    boolean lpIsInteger=VcConst.ccIsIntegerString(lpValue);
    boolean lpIsFloat=VcConst.ccIsFloatString(lpValue);

    if(!lpIsInteger && !lpIsFloat){
      ccSetValue(pxKey, lpFilted);
      return;
    }//..?

    if(lpIsInteger){
      int lpValueN=Integer.parseInt(lpValue);
      if(lpFilter.ccContains(lpValueN)){
        return;
      }//..?
      else{
        lpValueN=lpFilter.ccLimit(lpValueN);
        ccSetValue(pxKey, Integer.toString(lpValueN));
        return;
      }//..?
    }//..?

    if(lpIsFloat){
      float lpValueN=Float.parseFloat(lpValue);
      if(lpFilter.ccContains((int)lpValueN)){
        return;
      }//..?
      else{
        lpValueN=(float)lpFilter.ccLimit((int)lpValueN);
        ccSetValue(pxKey, Float.toString(lpValueN));
        return;
      }//..?
    }//..?
    
    System.err.println(
      "ppptable.McBaseKeyRangedValueSetting.ccConstrain()::"
      + "indefinible_error_occurred_with:"
      + pxKey
    );
    
  }//+++
  
}//**eof
