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

public final class McVBurningSetting extends McBaseKeyValueRangedSetting{
  
  private static McVBurningSetting self;
  public static McVBurningSetting ccGetReference(){
    if(self==null){self=new McVBurningSetting();}
    return self;
  }//++!
  
  //===
  
  private McVBurningSetting(){
    super();
    
    ccAddItem("--VBurnerTarget", 160,0,999);
    ccAddItem("--BagEntranceLowLimit", 220,0,999);
    ccAddItem("--BagEntranceHighLimit", 240,0,999);
    
    ccPack("V Burning");
    
  }//++!

  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex){
    if(pxColumnIndex==1){return super.getValueAt(pxRowIndex, pxColumnIndex);}
    else{
      return McTranslator.ccGetReference()
        .ccTr(super.getValueAt(pxRowIndex, pxColumnIndex));
    }
  }//+++
  
}//***eof
