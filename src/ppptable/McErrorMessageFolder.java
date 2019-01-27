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

public class McErrorMessageFolder {
  
  private static McErrorMessageFolder self;
  public static McErrorMessageFolder ccGetReference(){
    if(self==null){self=new McErrorMessageFolder();}
    return self;
  }//++!
  
  //===
  
  private static final String C_EMPTY=" ";
  
  //[TODO]::private final ArrayList<McError> cmList;
  
  private McErrorMessageFolder(){
    
  }//++!
  
  //===
  
  synchronized public final String ccGetMessage(int pxInt){
    if(pxInt<0){return C_EMPTY;}
    if(pxInt==0){return "all clear";}//[TODO]::..
    return "???";//[TODO]::..
  }//+++
  
}//***eof
