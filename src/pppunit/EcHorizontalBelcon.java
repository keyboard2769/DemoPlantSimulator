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
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcLamp;
import pppicon.EcMotorIcon;
import pppicon.EcPulleyIcon;
import pppshape.EcBelconShape;

public class EcHorizontalBelcon extends EcElement implements EiMotorized{

  private static final int C_BELCON_THICK=18;

  private final EcBelconShape cmShape;

  private final EcLamp cmEmsLamp;

  private final EcPulleyIcon cmMotorLampL, cmMotorLampR;

  public EcHorizontalBelcon(
    String pxName, int pxX, int pxY, int pxLength, int pxHeadID
  ){

    super();
    ccTakeKey(pxName);
    ccSetBound(pxX, pxY, pxLength, C_BELCON_THICK);
    ccSetID(pxHeadID);

    cmMotorLampL=new EcPulleyIcon();
    cmMotorLampL.ccSetSize(12, 12);
    cmMotorLampL.ccSetLocation(pxX+3, pxY+3);

    cmMotorLampR=new EcPulleyIcon();
    cmMotorLampR.ccSetSize(12, 12);
    cmMotorLampR.ccSetLocation(pxX+pxLength-cmMotorLampR.ccGetW()-3, pxY+3);

    cmEmsLamp=new EcLamp();
    cmEmsLamp.ccSetSize(16, 16);
    cmEmsLamp.ccSetNameAlign('x');
    cmEmsLamp.ccSetText("E");
    cmEmsLamp.ccSetColor(EcFactory.C_RED);

    cmShape=new EcBelconShape();
    cmShape.ccSetBound(pxX, pxY, pxLength, C_BELCON_THICK);
    cmShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    
    cmEmsLamp.ccSetLocation(cmShape, pxLength/4, 1);

  }//++!

  @Override public void ccUpdate(){
    cmShape.ccUpdate();
    cmMotorLampL.ccUpdate();
    cmEmsLamp.ccUpdate();
    cmMotorLampR.ccUpdate();
  }//+++

  @Override
  public void ccSetMotorStatus(char pxStatus_acnlx){
    EcMotorIcon.ccSetMotorStatus(cmMotorLampL, pxStatus_acnlx);
    EcMotorIcon.ccSetMotorStatus(cmMotorLampR, pxStatus_acnlx);
  }//+++
  
  public void ccSetIsEMSPulled(boolean pxStatus){
    cmEmsLamp.ccSetActivated(pxStatus);
  }//+++
  
}//***eof
