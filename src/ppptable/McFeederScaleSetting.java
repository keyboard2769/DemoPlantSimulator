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

public final class McFeederScaleSetting extends McBaseRangedFloatSetting{

  private static McFeederScaleSetting self;
  public static McFeederScaleSetting ccGetReference(){
    if(self==null){self=new McFeederScaleSetting();}
    return self;
  }//++!
  
  private final McTranslator cmTr;
  
  //===

  private McFeederScaleSetting(){
    super();
    cmTr=McTranslator.ccGetReference();
    
    
    ccAddItem("--aVF-adjust-div", 50,0,100);
    
    //-- scale
    ccAddItem("--VF1-rpm-offset", 0,0,1800);
    ccAddItem("--VF1-rpm-span", 1000,0,1800);
    ccAddItem("--VF1-tph-offset",0,0,120);
    ccAddItem("--VF1-tph-span", 50,0,120);
    
    ccAddItem("--VF2-rpm-offset", 0,0,1800);
    ccAddItem("--VF2-rpm-span", 1000,0,1800);
    ccAddItem("--VF2-tph-offset",0,0,120);
    ccAddItem("--VF2-tph-span", 50,0,120);
    
    ccAddItem("--VF3-rpm-offset", 0,0,1800);
    ccAddItem("--VF3-rpm-span", 1000,0,1800);
    ccAddItem("--VF3-tph-offset",0,0,120);
    ccAddItem("--VF3-tph-span", 50,0,120);
    
    ccAddItem("--VF4-rpm-offset", 0,0,1800);
    ccAddItem("--VF4-rpm-span", 1000,0,1800);
    ccAddItem("--VF4-tph-offset",0,0,120);
    ccAddItem("--VF4-tph-span", 50,0,120);
    
    ccAddItem("--VF5-rpm-offset", 0,0,1800);
    ccAddItem("--VF5-rpm-span", 1000,0,1800);
    ccAddItem("--VF5-tph-offset",0,0,120);
    ccAddItem("--VF5-tph-span", 50,0,120);
    
    ccAddItem("--VF6-rpm-offset", 0,0,1800);
    ccAddItem("--VF6-rpm-span", 1000,0,1800);
    ccAddItem("--VF6-tph-offset",0,0,120);
    ccAddItem("--VF6-tph-span", 50,0,120);
    
    //-- packing
    ccPack("--feederscale");
    
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
