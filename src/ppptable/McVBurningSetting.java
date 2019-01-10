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

public final class McVBurningSetting extends McBaseKeyValueSetting{
  
  private static McVBurningSetting self;
  public static McVBurningSetting ccGetReference(){
    if(self==null){self=new McVBurningSetting();}
    return self;
  }//++!
  
  //===
  
  private McVBurningSetting(){
    super();
    
    ccAddItem("v burner target:['C]", "160");
    ccAddItem("bag entrance low limit:['c]", "220");
    ccAddItem("bag entrance high limit:['c]", "240");
    ccPack("V Burning");
    
  }//++!

}//***eof
