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

public final class McAutoWeighSetting extends McBaseRangedFloatSetting{

  private static McAutoWeighSetting self;
  public static McAutoWeighSetting ccGetReference(){
    if(self==null){self=new McAutoWeighSetting();}
    return self;
  }//++!
  
  private final McTranslator cmTr;
  
  private McAutoWeighSetting(){
    super();
    cmTr=McTranslator.ccGetReference();
    
    //-- adding
    ccAddItem(McKeyHolder.K_AW_TIME_DRY, 5,0,99);
    ccAddItem(McKeyHolder.K_AW_TIME_DRY, 30,0,99);
    ccAddItem(McKeyHolder.K_AW_AD_ASP, 3400,0,9999);
    //--
    ccAddItem(McKeyHolder.K_AW_TARE_AG, 0,-500,999);
    ccAddItem(McKeyHolder.K_AW_TARE_FR, 0,-500,999);
    ccAddItem(McKeyHolder.K_AW_TARE_AS, 0,-500,999);
    //--
    ccAddItem(McKeyHolder.K_AW_EMPTY_AG, 5,0,999);
    ccAddItem(McKeyHolder.K_AW_EMPTY_FR, 5,0,999);
    ccAddItem(McKeyHolder.K_AW_EMPTY_AS, 5,0,999);
    //-- 
    for(int i=6;i>=1;i--){
      ccAddItem(McKeyHolder.ccGetAGDropPointKey(i), 5,0,200);
      ccAddItem(McKeyHolder.ccGetAGCutOffsetKey(i), 5,0,999);
      if(i<=2){
        ccAddItem(McKeyHolder.ccGetFRDropPointKey(i), 5,0,200);
        ccAddItem(McKeyHolder.ccGetFRCutOffsetKey(i), 5,0,999);
      }
      if(i==1){
        ccAddItem(McKeyHolder.ccGetASDropPointKey(i), 5,0,200);
        ccAddItem(McKeyHolder.ccGetASCutOffsetKey(i), 5,0,999);
      }
    }//..~
    
    //-- packing
    ccPack(McKeyHolder.K_AW_TITLE);
    
  }//++!
  
  //===
  
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex){
    if(pxColumnIndex==0){return super.getValueAt(pxRowIndex, pxColumnIndex);}
    else{return cmTr.ccTr(super.getValueAt(pxRowIndex, pxColumnIndex));}
  }//+++

  @Override public String toString(){
    return cmTr.ccTr(cmName);
  }//+++
  
}//***eof
