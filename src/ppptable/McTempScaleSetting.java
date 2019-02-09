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
    
    //-- adding
    ccAddItem(McKeyHolder.K_TS_GT_AD_OFF, C_DEFAULT_TEMP_AD_OFFS, 0,9999);
    ccAddItem(McKeyHolder.K_TS_GT_AD_SPN, C_DEFAULT_TEMP_AD_SPAN, 0,9999);
    ccAddItem(McKeyHolder.K_TS_GT_DC_OFF, C_DEFAULT_TEMP_DC_OFFS, 0,9999);
    ccAddItem(McKeyHolder.K_TS_GT_DC_SPN, C_DEFAULT_TEMP_DC_SPAN, 0,9999);
    //---
    ccAddItem(McKeyHolder.K_TS_CHUTE_BIAS, 100, 0,200);
    ccAddItem(McKeyHolder.K_TS_CHUTE_OFFS,   0, 0,999);
    //--
    ccAddItem(McKeyHolder.K_TS_PIPE_BIAS, 100, 0,200);
    ccAddItem(McKeyHolder.K_TS_PIPE_OFFS,   0, 0,999);
    //--
    ccAddItem(McKeyHolder.K_TS_ENT_BIAS, 100, 0,200);
    ccAddItem(McKeyHolder.K_TS_ENT_OFFS,   0, 0,999);
    //--
    ccAddItem(McKeyHolder.K_TS_SAND_BIAS, 100, 0,200);
    ccAddItem(McKeyHolder.K_TS_SAND_OFFS,   0, 0,999);
    //--
    ccAddItem(McKeyHolder.K_TS_MIXER_BIAS, 100, 0,200);
    ccAddItem(McKeyHolder.K_TS_MIXER_OFFS,   0, 0,999);
    
    //-- packing
    ccPack(McKeyHolder.K_TS_TITLE);
    
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
