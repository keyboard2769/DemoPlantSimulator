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

import static processing.core.PApplet.nf;

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
    
    //-- general
    ccAddItem("--gct-ad-offset",    0, 0,9999);
    ccAddItem("--gct-ad-span"  , 5000, 0,9999);
    ccAddItem("--gct-ct-offset",    0, 0,9999);
    
    //-- adjust
    String lpI;
    for(int i=0,s=McCurrentSlotModel.C_CAPA;i<s;i++){
      lpI=nf(i,2);
      ccAddItem("--ctslot"+lpI+"-ct-span"  , 998, 0,999);
      ccAddItem("--ctslot"+lpI+"-ct-alart" , 900, 0,999);
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
