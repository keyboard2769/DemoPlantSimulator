/*
 * Copyright (C) 2019 keypad
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

import processing.data.TableRow;

public class McAutoWeighLogger extends McBaseCSVTable{
  
  private static McAutoWeighLogger self;
  public static McAutoWeighLogger ccGetReference(){
    if(self==null){self=new McAutoWeighLogger();}
    return self;
  }//++!

  //===
  
  private McAutoWeighLogger(){
    
    super();
    
    for(String it:McAutoWeighRecord.C_TITLE){
      cmData.addColumn(it);
    }//..~
    
    ccAddDummyRecord();
    
  }//++!
  
  public final void ccAddDummyRecord(){
    TableRow lpRow=cmData.addRow();
    for(int i=0,s=McAutoWeighRecord.C_TITLE.length;i<s;i++){
      lpRow.setString(i,
        (i==0)?"%s":
        (i==1)?"%t":
        "%kg"
      );
    }//..~
  }//+++
  
  public final void ccAddRecord(McAutoWeighRecord pxRecord){
    TableRow lpRow=cmData.addRow();
    for(int i=0,s=McAutoWeighRecord.C_TITLE.length;i<s;i++){
      lpRow.setString(i, pxRecord.ccGetString(i));
    }//..~
  }//+++
  
 }//***eof
