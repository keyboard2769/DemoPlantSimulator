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

package pppunit;

import kosui.ppplocalui.EcElement;
import pppicon.EcMotorIcon;

/**
 *
 * @author keypad
 */
class EcMoterizedUnit extends EcElement implements EiMotorized{

  EcMotorIcon cmMotor;

  public EcMoterizedUnit(){
    super();
    cmMotor=new EcMotorIcon();
  }

  /**
   * [a]mc+an.. [c]mc.. [n]an.. [l]al.. [x]OFF
   *
   * @param pxStatus_acnlx #
   */
  @Override
  public void ccSetMotorStatus(char pxStatus_acnlx){
    EcMotorIcon.ccSetMotorStatus(cmMotor, pxStatus_acnlx);
  }//+++

}//***eof
