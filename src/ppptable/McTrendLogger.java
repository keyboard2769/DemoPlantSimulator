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

import processing.data.TableRow;

public final class McTrendLogger extends McBaseCSVTable{

  private static McTrendLogger self;
  public static McTrendLogger ccGetReference(){
    if(self==null){self=new McTrendLogger();}
    return self;
  }//++!
  
  //===

  private McTrendLogger(){
    super();
    for(String it:McTrendRecord.C_TITLE){
      cmData.addColumn(it);
    }//..~
    ccAddDummyRecord();
    
  }//++!
  
  public final void ccAddRecord(McTrendRecord pxRecord){
    TableRow lpRow=cmData.addRow();
    for(int i=0,s=McTrendRecord.C_TITLE.length;i<s;i++){
      lpRow.setString(i, pxRecord.ccGetString(i));
    }//..~
  }//+++
  
  @Deprecated public final void ccAddDummyRecord(){
    ccAddRecord(new McTrendRecord());
  }//+++

}//***eof
