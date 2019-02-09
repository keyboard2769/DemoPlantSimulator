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

public final class McCurrentScaleSetting extends McBaseRangedFloatSetting{

  private static McCurrentScaleSetting self;
  public static McCurrentScaleSetting ccGetReference(){
    if(self==null){self=new McCurrentScaleSetting();}
    return self;
  }//++!
  
  private final McTranslator cmTr;
  
  //===

  private McCurrentScaleSetting(){
    super();
    cmTr=McTranslator.ccGetReference();
    
    //-- adding
    ccAddItem(McKeyHolder.K_CS_G_AD_OF,    0, 0,9999);
    ccAddItem(McKeyHolder.K_CS_G_AD_SP, 5000, 0,9999);
    ccAddItem(McKeyHolder.K_CS_G_CT_OF,    0, 0,9999);
    for(int i=0,s=McCurrentSlotModel.C_CAPA;i<s;i++){
      ccAddItem(McKeyHolder.ccGetCTSlotSpanKey(i), 998, 0,999);
      ccAddItem(McKeyHolder.ccGetCTSlotAlartKey(i), 900, 0,999);
    }//..~
    
    //-- packing
    ccPack("--current");
    
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
