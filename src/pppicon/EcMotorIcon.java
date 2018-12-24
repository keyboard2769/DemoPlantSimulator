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

public class EcMotorIcon extends EcElement{

  private static final int
    C_LED_W=4,
    C_LED_H=8;

  protected boolean cmMC, cmAN, cmAL;

  public EcMotorIcon(){
    super();

    cmMC=false;
    cmAN=false;
    cmAL=false;

    ccSetSize(C_LED_W*5, C_LED_H+3);

  }//++!

  @Override
  public void ccUpdate(){

    pbOwner.stroke(EcFactory.C_WHITE);
    pbOwner.fill(EcFactory.C_DIM_GRAY);
    pbOwner.rect(cmX, cmY, cmW, cmH, cmH/2);
    pbOwner.noStroke();

    pbOwner.fill(cmAN?EcFactory.C_GREEN:EcFactory.C_DARK_GRAY);
    pbOwner.rect(cmX+C_LED_W*1, cmY+2, C_LED_W, C_LED_H);
    pbOwner.fill(cmMC?EcFactory.C_ORANGE:EcFactory.C_DARK_GRAY);
    pbOwner.rect(cmX+C_LED_W*2, cmY+2, C_LED_W, C_LED_H);
    pbOwner.fill(cmAL?EcFactory.C_RED:EcFactory.C_DARK_GRAY);
    pbOwner.rect(cmX+C_LED_W*3, cmY+2, C_LED_W, C_LED_H);

  }//+++

  public void ccSetIsContacted(boolean pxStatus){
    cmMC=pxStatus;
  }//+++

  public void ccSetHasAnswer(boolean pxStatus){
    cmIsActivated=pxStatus;
    cmAN=pxStatus;
  }//+++

  public void ccSetHasAlarm(boolean pxStatus){
    cmAL=pxStatus;
  }//+++

  //=== static
  static public final void ccSetMotorStatus(
    EcMotorIcon pxTarget, char pxStatus_acnlx
  ){
    switch(pxStatus_acnlx){

      case 'a':
        pxTarget.ccSetIsContacted(true);
        pxTarget.ccSetHasAnswer(true);
        pxTarget.ccSetHasAlarm(false);
      break;

      case 'c':
        pxTarget.ccSetIsContacted(true);
        pxTarget.ccSetHasAnswer(false);
        pxTarget.ccSetHasAlarm(false);
      break;

      case 'n':
        pxTarget.ccSetIsContacted(false);
        pxTarget.ccSetHasAnswer(true);
        pxTarget.ccSetHasAlarm(false);
      break;

      case 'l':
        pxTarget.ccSetIsContacted(false);
        pxTarget.ccSetHasAnswer(false);
        pxTarget.ccSetHasAlarm(true);
      break;

      default:
        pxTarget.ccSetIsContacted(false);
        pxTarget.ccSetHasAnswer(false);
        pxTarget.ccSetHasAlarm(false);
      break;
        
    }//..?
  }//+++

}//***eof
