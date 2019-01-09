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

import kosui.pppswingui.McTableAdapter;
import pppmain.MainSketch;

public final class McVBurningSetting extends McTableAdapter{
  
  private static McVBurningSetting self;
  public static McVBurningSetting ccGetReference(){
    if(self==null){self=new McVBurningSetting();}
    return self;
  }//++!
  
  //===
  
  private final String[] cmLesItems={
    "VB Target Temprature['C]",//..0
    "VD Target Pressure['C]",//..1
  };
  
  private McVBurningSetting(){
    super();
  }//++!
  
  //===
  
  private String ccGetItemString(int pxIndex){
    if(pxIndex<0){return "<>";}
    if(pxIndex>cmLesItems.length){return "<>";}
    return cmLesItems[pxIndex];
  }//+++
  
  //===

  @Override public String getColumnName(int pxColumnIndex){
    switch(pxColumnIndex){
      case 0:return "key";
      case 1:return "value";
      default:return "<>";
    }
  }//+++
  
  @Override public int getRowCount(){
    return cmLesItems.length;
  }//+++

  @Override
  public Object getValueAt(int pxRowIndex, int pxColumnIndex){
    if(pxColumnIndex==1){
      switch(pxRowIndex){
        
        case 0:return MainSketch.yourMOD.vmVBurnerTargetTempraure;
        case 1:return MainSketch.yourMOD.vmVDryerTargetPressure;
        
        default:return "<>";
      }
    }
    else{return ccGetItemString(pxRowIndex);}
  }//++
  
}//***eof
