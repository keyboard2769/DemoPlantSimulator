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

public class McAutoWeighSetting extends McBaseKeyValueSetting{

  private static McAutoWeighSetting self;
  public static McAutoWeighSetting ccGetReference(){
    if(self==null){self=new McAutoWeighSetting();}
    return self;
  }//++!
  
  private McAutoWeighSetting(){
    super();
    
    ccAddItem("wet time:[s]", "5");
    ccAddItem("dry time:[s]", "38");
    ccPack();
    
  }
  
}
