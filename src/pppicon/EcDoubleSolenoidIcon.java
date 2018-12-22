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

package pppicon;

import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;

public class EcDoubleSolenoidIcon extends EcElement{

  private static final int
    C_LED_W=8,
    C_LED_H=2;

  protected boolean
    cmUP, cmDN,
    cmFAS, cmMAS, cmCAS;

  public EcDoubleSolenoidIcon(){
    super();
    cmUP=false;
    cmDN=false;
    cmFAS=false;
    cmMAS=false;
    cmCAS=false;
    ccSetSize(C_LED_W+3, C_LED_H*3+C_LED_W*2+6);
  }//++!

  @Override
  public void ccUpdate(){

    pbOwner.stroke(EcFactory.C_WHITE);
    pbOwner.fill(EcFactory.C_DARK_GRAY);
    pbOwner.rect(cmX, cmY, cmW, cmH, cmH/2);
    pbOwner.noStroke();

    pbOwner.fill(cmFAS?EcFactory.C_GREEN:EcFactory.C_DIM_GRAY);
    pbOwner.rect(cmX+2, cmY+1+C_LED_W+C_LED_H*1, C_LED_W, C_LED_H);
    pbOwner.fill(cmMAS?EcFactory.C_WATER:EcFactory.C_DIM_GRAY);
    pbOwner.rect(cmX+2, cmY+1+C_LED_W+C_LED_H*2, C_LED_W, C_LED_H);
    pbOwner.fill(cmCAS?EcFactory.C_RED:EcFactory.C_DIM_GRAY);
    pbOwner.rect(cmX+2, cmY+1+C_LED_W+C_LED_H*3, C_LED_W, C_LED_H);

    pbOwner.fill(cmUP?EcFactory.C_ORANGE:EcFactory.C_DIM_GRAY);
    pbOwner.ellipse(ccCenterX()+1, cmY+1+C_LED_W/2, C_LED_W, C_LED_W);
    pbOwner.fill(cmDN?EcFactory.C_ORANGE:EcFactory.C_DIM_GRAY);
    pbOwner.ellipse(ccCenterX()+1, cmY-1+cmH-C_LED_W/2, C_LED_W, C_LED_W);

  }//+++

  public final void ccSetIsOpening(boolean pxStatus){
    cmUP=pxStatus;
  }//+++

  public final void ccSetIsClosing(boolean pxStatus){
    cmDN=pxStatus;
  }//+++

  public final void ccSetIsFull(boolean pxStatus){
    cmFAS=pxStatus;
  }//+++

  public final void ccSetIsMiddle(boolean pxStatus){
    cmMAS=pxStatus;
  }//+++

  public final void ccSetIsClosed(boolean pxStatus){
    cmCAS=pxStatus;
  }//+++

}//***eof
