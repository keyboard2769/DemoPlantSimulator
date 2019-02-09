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

public final class McVBurningSetting extends McBaseRangedFloatSetting{
  
  private static McVBurningSetting self;
  public static McVBurningSetting ccGetReference(){
    if(self==null){self=new McVBurningSetting();}
    return self;
  }//++!
  
  //===
  
  private final McTranslator cmTr;
  
  private McVBurningSetting(){
    super();
    cmTr=McTranslator.ccGetReference();
    
    //-- adding
    ccAddItem(McKeyHolder.K_VB_TTGT_VB, 160,0,999);
    ccAddItem(McKeyHolder.K_VB_TLMT_ENT_L, 220,0,999);
    ccAddItem(McKeyHolder.K_VB_TLMT_ENT_H, 240,0,999);
    ccAddItem(McKeyHolder.K_VB_DLMT_VB_L, 1,0,100);
    ccAddItem(McKeyHolder.K_VB_DLMT_VB_H, 99,0,100);
    ccAddItem(McKeyHolder.K_VB_DLMT_VE_L, 10,0,100);
    ccAddItem(McKeyHolder.K_VB_DLMT_VE_H, 90,0,100);
    ccAddItem(McKeyHolder.K_VB_VB_PID_P, 90,0,99);
    ccAddItem(McKeyHolder.K_VB_VB_PID_D, 1,0,99);
    ccAddItem(McKeyHolder.K_VB_VE_PID_P, 15,0,99);
    ccAddItem(McKeyHolder.K_VB_VE_PID_D, 1,0,99);
    
    //-- packing
    ccPack(McKeyHolder.K_VB_TITLE);
    
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
