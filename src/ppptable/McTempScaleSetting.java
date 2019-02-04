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

import static pppmain.MainOperationModel.C_DEFAULT_TEMP_AD_OFFS;
import static pppmain.MainOperationModel.C_DEFAULT_TEMP_AD_SPAN;
import static pppmain.MainOperationModel.C_DEFAULT_TEMP_DC_OFFS;
import static pppmain.MainOperationModel.C_DEFAULT_TEMP_DC_SPAN;

public final class McTempScaleSetting extends McBaseRangedFloatSetting{

  private static McTempScaleSetting self;
  public static McTempScaleSetting ccGetReference(){
    if(self==null){self=new McTempScaleSetting();}
    return self;
  }//++!
  
  //===

  private final McTranslator cmTr;
  
  private McTempScaleSetting(){
    super();
    cmTr=McTranslator.ccGetReference();
    
    //-- general
    ccAddItem("--aaGtemp-ad-offset", C_DEFAULT_TEMP_AD_OFFS, 0,9999);
    ccAddItem("--aaGtemp-ad-span"  , C_DEFAULT_TEMP_AD_SPAN, 0,9999);
    ccAddItem("--aaGtemp-dc-offset", C_DEFAULT_TEMP_DC_OFFS, 0,9999);
    ccAddItem("--aaGtemp-dc-span" ,  C_DEFAULT_TEMP_DC_SPAN, 0,9999);
    
    //-- adjust
    ccAddItem("--agchute-tbias"   , 100, 0,200);
    ccAddItem("--agchute-toffset" ,   0, 0,999);
    //--
    ccAddItem("--aspipe-tbias"   , 100, 0,200);
    ccAddItem("--aspipe-toffset" ,   0, 0,999);
    //--
    ccAddItem("--bagentrance-tbias"   , 100, 0,200);
    ccAddItem("--bagentrance-toffset" ,   0, 0,999);
    //--
    ccAddItem("--agsand-tbias"   , 100, 0,200);
    ccAddItem("--agsand-toffset" ,   0, 0,999);
    //--
    ccAddItem("--mixer-tbias"   , 100, 0,200);
    ccAddItem("--mixer-toffset" ,   0, 0,999);
    
    //-- packing
    ccPack("--temperature");
    
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
