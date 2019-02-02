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
    ccAddItem("--cell-ag-ad-offset",  400, 0,9999);
    ccAddItem("--cell-ag-ad-span"  , 3600, 0,9999);
    ccAddItem("--cell-ag-kg-offset",    0, 0,9999);
    ccAddItem("--cell-ag-kg-span"  , 4000, 0,9999);
    //--
    ccAddItem("--cell-fr-ad-offset",  400, 0,9999);
    ccAddItem("--cell-fr-ad-span"  , 3600, 0,9999);
    ccAddItem("--cell-fr-kg-offset",    0, 0,9999);
    ccAddItem("--cell-fr-kg-span"  ,  500, 0,9999);
    //--
    ccAddItem("--cell-as-ad-offset",  400, 0,9999);
    ccAddItem("--cell-as-ad-span"  , 3600, 0,9999);
    ccAddItem("--cell-as-kg-offset",    0, 0,9999);
    ccAddItem("--cell-as-kg-span"  ,  500, 0,9999);
    
    //-- degree
    ccAddItem("--degree-vb-ad-offset",  400, 0,9999);
    ccAddItem("--degree-vb-ad-span"  , 3600, 0,9999);
    ccAddItem("--degree-vb-pt-offset",    0, 0,9999);
    ccAddItem("--degree-vb-pt-span"  ,  100, 0,9999);
    //--
    ccAddItem("--degree-ve-ad-offset",  400, 0,9999);
    ccAddItem("--degree-ve-ad-span"  , 3600, 0,9999);
    ccAddItem("--degree-ve-pt-offset",    0, 0,9999);
    ccAddItem("--degree-ve-pt-span"  ,  100, 0,9999);
    
    //-- misc
    ccAddItem("--degree-vd-ad-offset", 1500, 0,9999);
    ccAddItem("--degree-vd-ad-span"  , 2500, 0,9999);
    ccAddItem("--degree-vd-ps-offset",    0, 0,9999);
    ccAddItem("--degree-vd-ps-span"  ,  200, 0,9999);
    
    //-- packing
    ccPack("--general-scale");
    
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
