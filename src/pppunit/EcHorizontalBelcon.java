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
import kosui.ppplocalui.EcLamp;
import pppshape.EcBelconShape;

public class EcHorizontalBelcon extends EcMoterizedUnit{

  private final EcBelconShape cmShape;

  private final EcLamp cmEmsLamp;
  
  public EcHorizontalBelcon(
    String pxName, int pxX, int pxY, int pxLength, int pxHeadID
  ){

    super();
    ccTakeKey(pxName);
    ccSetBound(pxX, pxY, pxLength, 16);
    ccSetID(pxHeadID);

    cmEmsLamp=new EcLamp();
    cmEmsLamp.ccSetSize(16, 16);
    cmEmsLamp.ccSetName("EMS");
    cmEmsLamp.ccSetNameAlign('b');
    cmEmsLamp.ccSetText("E");
    cmEmsLamp.ccSetColor(EcFactory.C_RED);

    cmShape=new EcBelconShape();
    cmShape.ccSetBound(cmX, cmY, cmW, cmH);
    cmShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    
    cmEmsLamp.ccSetLocation(cmShape, pxLength/4, 8);
    
    cmMotor.ccSetLocation(cmX+8, cmY-4);

  }//++!

  @Override public void ccUpdate(){
    cmShape.ccUpdate();
    cmEmsLamp.ccUpdate();
    cmMotor.ccUpdate();
  }//+++
  
  public void ccSetIsEMSPulled(boolean pxStatus){
    cmEmsLamp.ccSetIsActivated(pxStatus);
  }//+++
  
}//***eof
