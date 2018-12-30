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

import kosui.ppplogic.ZcStepper;


//[TODO]::may add to kosui?
public class ZcChainStepper extends ZcStepper{
  
  private final int cmMax;
  
  public ZcChainStepper(int pxMax){
    super();
    cmMax=pxMax;
  }//+++ 
  
  public final void ccStep(boolean pxAct){
    if(pxAct && (cmStage<cmMax)){cmStage++;}
  }//+++
  
  public final void ccStop(boolean pxAct){
    if(pxAct){cmStage=0;}
  }//+++
  
  public final boolean ccIsStepping()
    {return cmStage>0;}//+++

  @Override public boolean ccIsAt(int pxStage)
    {return cmStage>=pxStage;}//+++
  
}//***eof
