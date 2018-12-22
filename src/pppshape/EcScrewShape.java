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

import kosui.ppplocalui.EcShape;

public class EcScrewShape extends EcShape{

  private static final int
    C_SHEATH=8,
    C_SHAFT=4;

  private int cmInCut=0, cmOutCut=0;

  @Override
  public void ccUpdate(){

    pbOwner.fill(cmBaseColor);
    pbOwner.rect(cmX, ccCenterY()-C_SHEATH/2, cmW, C_SHEATH);
    pbOwner.rect(cmX-C_SHAFT, ccCenterY()-C_SHAFT/2, cmW+C_SHAFT*2, C_SHAFT);
    if(cmInCut>0){
      pbOwner.rect(cmX+cmInCut, cmY, C_SHAFT, cmH/2);
    }
    if(cmOutCut>0){
      pbOwner.rect(cmX+cmOutCut, ccCenterY(), C_SHAFT, cmH/2);
    }

  }//+++

  public final void ccSetCut(int pxIn, int pxOut){
    cmInCut=pxIn>=cmW?0:pxIn;
    cmOutCut=pxOut>=cmW?0:pxOut;
  }//+++

}//***eof
