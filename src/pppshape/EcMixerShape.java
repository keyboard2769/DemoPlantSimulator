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

package pppshape;

import pppmain.MainSketch;

public class EcMixerShape extends EcHopperShape{

  private static final int C_BACKGROUND=/*0xFF000000*/
    MainSketch.C_C_BACKGROUD;
  
  private int cmRatio=4;

  @Override
  public void ccUpdate(){
    super.ccUpdate();
    pbOwner.fill(C_BACKGROUND);
    pbOwner.ellipse(ccCenterX(), ccEndY(), cmRatio, cmRatio);
  }//+++

  public final void ccSetRatio(int pxVal){
    cmRatio=pxVal;
  }//+++

  public final void ccSetRatio(){
    ccSetCut(cmW/6);
    cmRatio=cmW/4;
  }//+++

}//***eof
