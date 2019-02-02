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

public class McAutoWeighSetting extends McBaseRangedFloatSetting{

  private static McAutoWeighSetting self;
  public static McAutoWeighSetting ccGetReference(){
    if(self==null){self=new McAutoWeighSetting();}
    return self;
  }//++!
  
  private final McTranslator cmTr;
  
  private McAutoWeighSetting(){
    super();
    cmTr=McTranslator.ccGetReference();
    
    //-- mix
    ccAddItem("--aTime-dry", 5,0,99);
    ccAddItem("--aTime-wet", 30,0,99);
    
    ccAddItem("--bAD-asoverscale", 3400,0,9999);
    
    //-- zero
    ccAddItem("--wZeroref-ag", 400,0,999);
    ccAddItem("--wZeroref-fr", 400,0,999);
    ccAddItem("--wZeroref-as", 400,0,999);
    //--
    ccAddItem("--wZerooffset-ag", 5,-50,999);
    ccAddItem("--wZerooffset-fr", 5,-50,999);
    ccAddItem("--wZrooffset-as", 5,-50,999);
    //--
    ccAddItem("--empty-ag", 5,0,999);
    ccAddItem("--empty-fr", 5,0,999);
    ccAddItem("--empty-as", 5,0,999);
    
    //-- control
    ccAddItem("--droppoint-ag6", 5,0,200);
    ccAddItem("--droppoint-ag5", 5,0,200);
    ccAddItem("--droppoint-ag4", 5,0,200);
    ccAddItem("--droppoint-ag3", 5,0,200);
    ccAddItem("--droppoint-ag2", 5,0,200);
    ccAddItem("--droppoint-ag1", 5,0,200);
    ccAddItem("--droppoint-fr2", 5,0,200);
    ccAddItem("--droppoint-fr1", 5,0,200);
    ccAddItem("--droppoint-as1", 5,0,200);
    //--
    ccAddItem("--cutoffset-ag6", 5,0,999);
    ccAddItem("--cutoffset-ag5", 5,0,999);
    ccAddItem("--cutoffset-ag4", 5,0,999);
    ccAddItem("--cutoffset-ag3", 5,0,999);
    ccAddItem("--cutoffset-ag2", 5,0,999);
    ccAddItem("--cutoffset-ag1", 5,0,999);
    ccAddItem("--cutoffset-fr2", 5,0,999);
    ccAddItem("--cutoffset-fr1", 5,0,999);
    ccAddItem("--cutoffset-as1", 5,0,999);
    
    
    //-- packing
    ccPack("--autoweigh");
    
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
