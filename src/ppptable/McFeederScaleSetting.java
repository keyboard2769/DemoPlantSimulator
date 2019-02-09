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
    
    //-- adding
    ccAddItem(McKeyHolder.K_FS_VF_DIV, 50,0,100);
    for(int i=1;i<=6;i++){
      ccAddItem(McKeyHolder.ccGetVFeederRpmOffset(i), 0,0,1800);
      ccAddItem(McKeyHolder.ccGetVFeederRpmSpan(i), 1000,0,1800);
      ccAddItem(McKeyHolder.ccGetVFeederTphOffset(i),0,0,120);
      ccAddItem(McKeyHolder.ccGetVFeederTphSpan(i), 50,0,120);
    }//..~
    
    //-- packing
    ccPack(McKeyHolder.K_FS_TITLE);
    
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
