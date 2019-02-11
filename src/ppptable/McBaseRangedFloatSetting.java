/*
 * Copyright (C) 2019 Ker Parker
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

import java.util.HashMap;
import kosui.ppplogic.ZcRangedModel;
import static kosui.ppputil.VcConst.ccRoundForTwoAfter;

//[TODO]::should this be part of kosui??
public class McBaseRangedFloatSetting extends McBaseFloatSetting{
  
  private final HashMap<String, ZcRangedModel> cmFilterList;

  public McBaseRangedFloatSetting(){
    super();
    cmFilterList=new HashMap<>();
  }//++!
  
  protected final void ccAddItem(
    String pxKey, int pxValue, int pxMin, int pxRange
  ){
    ZcRangedModel lpModel=new ZcRangedModel(pxMin, pxRange);
    int lpValue=lpModel.ccLimit(pxValue);
    cmFilterList.put(pxKey, lpModel);
    ccAddItem(pxKey, lpValue);
    
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
    ccSetValue(pxKey, lpValue);
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
      ccSetValue(pxKey, pxValue);
    }else{
      lpIntegerValue=lpFilter.ccLimit(lpIntegerValue);
      ccSetValue(pxKey, ccRoundForTwoAfter(lpIntegerValue));
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
    float lpValue=ccGetFloatValue(pxKey);
    float lpValueN=(float)(lpFilter.ccLimit((int)lpValue));
    ccSetValue(pxKey, lpValueN);
  }//+++
  
}//**eof
