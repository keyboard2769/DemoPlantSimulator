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
import pppshape.EcBelconShape;

public class EcHorizontalBelcon extends EcElement implements EiMotorized{

  private final EcBelconShape cmShape;
  
  public EcHorizontalBelcon(String pxName, int pxLength, int pxHeadID){

    super();
    ccSetupKey(pxName);
    ccSetSize(pxLength, 16);
    ccSetID(pxHeadID);

    cmShape=new EcBelconShape();
    cmShape.ccSetSize(cmW, cmH);
    cmShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);

  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    ccSetLocation(pxX, pxY);
    cmShape.ccSetLocation(cmX, cmY);
  }//++!
  
  //===

  @Override public void ccUpdate(){
    cmShape.ccUpdate();
  }//+++

  @Override
  public void ccSetMotorON(boolean pxStatus){
    cmShape.ccSetBaseColor(pxStatus?
      EcUnitFactory.C_SHAPE_COLOR_POWERED:
      EcUnitFactory.C_SHAPE_COLOR_METAL
    );
  }//+++
  
}//***eof
