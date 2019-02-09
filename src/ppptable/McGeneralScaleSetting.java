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

public final class McGeneralScaleSetting extends McBaseRangedFloatSetting{

  private static McGeneralScaleSetting self;
  public static McGeneralScaleSetting ccGetReference(){
    if(self==null){self=new McGeneralScaleSetting();}
    return self;
  }//++!
  
  //===
  
  private final McTranslator cmTr;

  private McGeneralScaleSetting(){
    super();
    cmTr=McTranslator.ccGetReference();
    
    //-- cell
    ccAddItem(McKeyHolder.K_GS_C_AG_AD_OFF,  400, 0,9999);
    ccAddItem(McKeyHolder.K_GS_C_AG_AD_SPN, 3600, 0,9999);
    ccAddItem(McKeyHolder.K_GS_C_AG_KG_OFF,    0, 0,9999);
    ccAddItem(McKeyHolder.K_GS_C_AG_KG_SPN, 4000, 0,9999);
    //--
    ccAddItem(McKeyHolder.K_GS_C_FR_AD_OFF,  400, 0,9999);
    ccAddItem(McKeyHolder.K_GS_C_FR_AD_SPN, 3600, 0,9999);
    ccAddItem(McKeyHolder.K_GS_C_FR_KG_OFF,    0, 0,9999);
    ccAddItem(McKeyHolder.K_GS_C_FR_KG_SPN,  500, 0,9999);
    //--
    ccAddItem(McKeyHolder.K_GS_C_AS_AD_OFF,  400, 0,9999);
    ccAddItem(McKeyHolder.K_GS_C_AS_AD_SPN, 3600, 0,9999);
    ccAddItem(McKeyHolder.K_GS_C_AS_KG_OFF,    0, 0,9999);
    ccAddItem(McKeyHolder.K_GS_C_AS_KG_SPN,  500, 0,9999);
    //-- degree
    ccAddItem(McKeyHolder.K_GS_D_VB_AD_OFF,  400, 0,9999);
    ccAddItem(McKeyHolder.K_GS_D_VB_AD_SPN, 3600, 0,9999);
    ccAddItem(McKeyHolder.K_GS_D_VB_PT_OFF,    0, 0,9999);
    ccAddItem(McKeyHolder.K_GS_D_VB_PT_SPN,  100, 0,9999);
    //--
    ccAddItem(McKeyHolder.K_GS_D_VE_AD_OFF,  400, 0,9999);
    ccAddItem(McKeyHolder.K_GS_D_VE_AD_SPN, 3600, 0,9999);
    ccAddItem(McKeyHolder.K_GS_D_VE_PT_OFF,    0, 0,9999);
    ccAddItem(McKeyHolder.K_GS_D_VE_PT_SPN,  100, 0,9999);
    //-- misc
    ccAddItem(McKeyHolder.K_GS_D_VD_AD_OFF, 1500, 0,9999);
    ccAddItem(McKeyHolder.K_GS_D_VD_AD_SPN, 2500, 0,9999);
    ccAddItem(McKeyHolder.K_GS_D_VD_KP_OFF,    0, 0,9999);
    ccAddItem(McKeyHolder.K_GS_D_VD_KP_SPN,  200, 0,9999);
    
    //-- packing
    ccPack(McKeyHolder.K_GS_TITLE);
    
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
