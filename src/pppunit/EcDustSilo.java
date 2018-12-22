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

import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcTriangleLamp;

class EcDustSilo extends EcFillerSilo{

  private final EcTriangleLamp cmRecievePL;

  public EcDustSilo(String pxName, int pxX, int pxY, int pxHeadID){

    super(pxName, pxX, pxY, pxHeadID);

    cmScrewShape.ccSetLocation(cmX-20, cmY-10);
    cmScrewShape.ccSetCut(-1, cmScrewShape.ccGetW()-8);

    cmMotor.ccSetLocation(cmScrewShape, 4, -8);

    cmRecievePL=new EcTriangleLamp();
    cmRecievePL.ccSetColor(EcFactory.C_ORANGE);
    cmRecievePL.ccSetText(" ");
    cmRecievePL.ccSetName("BF");
    cmRecievePL.ccSetNameAlign('l');
    cmRecievePL.ccSetDirection('r');
    cmRecievePL.ccSetLocation(cmScrewShape, -16, -1);

  }//++!

  @Override
  public void ccUpdate(){

    super.ccUpdate();
    cmRecievePL.ccUpdate();

  }//+++

  public final void ccSetIsTakingIn(boolean pxStatus){
    cmRecievePL.ccSetActivated(pxStatus);
  }//+++

}//***eof

