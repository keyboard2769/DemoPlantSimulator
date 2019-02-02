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

public final class McLogicTimerSetting extends McBaseRangedFloatSetting{

  private static McLogicTimerSetting self;
  public static McLogicTimerSetting ccGetReference(){
    if(self==null){self=new McLogicTimerSetting();}
    return self;
  }//++!
  
  //===
  
  public static final int C_LTIMER_MAX=200;
  
  //===
  
  private final McTranslator cmTr;
  
  //===

  private McLogicTimerSetting(){
    super();
    cmTr=McTranslator.ccGetReference();
    
    String lpI;
    for(int i=0;i<C_LTIMER_MAX;i++){
      lpI=nf(i,3);
      ccAddItem("--tmslot"+lpI+"-value"  , 2, 0,9999);
    }//+++
    
    //-- packing
    ccPack("--logic-timer");
    
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
