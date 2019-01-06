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

package ppptask;

import kosui.ppplogic.ZcRangedValueModel;

public class ZcCylinderGateModel extends ZcRangedValueModel{
  
  public ZcCylinderGateModel(){
    super(0,20);
  }//+++ 
  
  public final void ccOpen(boolean pxEngage, int pxSpeed){
    if(pxEngage){ccShift(pxSpeed);}
  }//+++
  
  public final void ccClose(boolean pxEngage, int pxSpeed){
    if(pxEngage){ccShift(-1*pxSpeed);}
  }//+++
  
  public final boolean ccIsAtFullAS(){return cmValue>=17&&cmValue<=19;}//+++
  
  public final boolean ccIsAtMiddleAS(){return cmValue>=9&&cmValue<=11;}//+++
  
  public final boolean ccIsAtClosedAS(){return cmValue>=2&&cmValue<=4;}//+++
  
  //===
  
  public final boolean ccIsFullyClosed(){return cmValue<=3;}//+++
  
  public final boolean ccIsFullyOpened(){return cmValue>=17;}//+++
  
  public final boolean ccIsNotClosed(){return cmValue>4;}//+++
  
}//***eof
