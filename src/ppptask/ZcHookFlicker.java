/*
 * Copyright (C) 2018 keypad
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

package ppptask;

//[TODO]::should this be a part of kosui??
public class ZcHookFlicker {
  
  private boolean cmHolder=false;
  private int cmTimer=0;
  
  public final boolean ccHook(boolean pxVal){
    if(pxVal){cmTimer+=cmTimer<3?1:0;}else{cmTimer=0;}
    if(cmTimer==1){cmHolder=!cmHolder;}
    return cmHolder;
  }//+++
  
  public final boolean ccHook(boolean pxTrigger, boolean pxLock){
    if(pxLock){cmHolder=false;return cmHolder;}
    else{return ccHook(pxTrigger);}
  }//+++
  
  public final void ccSetIsHooked(boolean pxStatus){
    cmHolder=pxStatus;
  }//+++
  
  public final boolean ccIsHooked(){
    return cmHolder;
  }//+++
  
}//***eof
