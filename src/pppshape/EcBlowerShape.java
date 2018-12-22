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

public class EcBlowerShape extends EcShape{

  private char cmDirection='r';

  @Override
  public void ccUpdate(){
    pbOwner.fill(cmBaseColor);
    pbOwner.rect(cmX, cmY, cmW, cmH);
    switch(cmDirection){
      case 'u': pbOwner.ellipse(ccEndX(), ccEndY(), cmW*2, cmW*2);
        break;
      case 'd': pbOwner.ellipse(ccEndX(), cmY, cmW*2, cmW*2);
        break;
      case 'r': pbOwner.ellipse(cmX, ccEndY(), cmH*2, cmH*2);
        break;
      case 'l': pbOwner.ellipse(ccEndX(), ccEndY(), cmH*2, cmH*2);
        break;
      default: break;
    }//+++
  }//+++

  public final void ccSetDirection(char pxMode_udlr){
    cmDirection=pxMode_udlr;
  }//+++

}//***eof
