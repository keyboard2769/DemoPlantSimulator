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

public class EcInclineBelcon extends EcElement implements EiMotorized{

  private final EcLamp cmCAS;

  private final EcLamp cmEmsLamp;

  private final EcPulleyIcon cmMotorLampL, cmMotorLampR;

  public EcInclineBelcon(
    String pxName, int pxX, int pxY, int pxLength, int pxCut, int pxHeadID
  ){

    super();
    ccTakeKey(pxName);
    ccSetBound(pxX, pxY, pxLength, pxCut);
    ccSetID(pxHeadID);
    
    cmEmsLamp=new EcLamp();
    cmEmsLamp.ccSetSize(16, 16);
    cmEmsLamp.ccSetNameAlign('x');
    cmEmsLamp.ccSetText("E");
    cmEmsLamp.ccSetColor(EcFactory.C_RED);
    cmEmsLamp.ccSetLocation(
      pxX+pxLength-cmEmsLamp.ccGetW()*2,
      pxY+pxCut-cmEmsLamp.ccGetW()/2
    );

    cmMotorLampL=new EcPulleyIcon();
    cmMotorLampL.ccSetSize(16, 16);
    cmMotorLampL.ccSetLocation(
      pxX+1-cmEmsLamp.ccGetW()/2,
      pxY+1-cmEmsLamp.ccGetW()/2
    );

    cmMotorLampR=new EcPulleyIcon();
    cmMotorLampR.ccSetSize(16, 16);
    cmMotorLampR.ccSetLocation(
      pxX+pxLength-cmEmsLamp.ccGetW()/2,
      pxY+pxCut-cmEmsLamp.ccGetW()/2
    );

    cmCAS=new EcLamp();
    cmCAS.ccSetLocation(cmMotorLampL, pxCut*2, -6);
    cmCAS.ccSetSize(cmEmsLamp);
    cmCAS.ccSetText("C");
    cmCAS.ccSetColor(EcFactory.C_LIT_GREEN);

  }//++!

  @Override
  public void ccUpdate(){

    pbOwner.strokeWeight(20);
    pbOwner.stroke(EcUnitFactory.C_SHAPE_COLOR_METAL);
    {
      pbOwner.line(cmX, cmY, cmX+cmH, cmY+cmH);
      pbOwner.line(cmX+cmH, cmY+cmH, ccEndX(), ccEndY());
    }
    pbOwner.strokeWeight(1);
    pbOwner.noStroke();

    cmCAS.ccUpdate();
    cmMotorLampL.ccUpdate();
    cmEmsLamp.ccUpdate();
    cmMotorLampR.ccUpdate();
    
  }//+++

  void ccSetIsCAS(boolean pxStatus){
    cmCAS.ccSetIsActivated(pxStatus);
  }//+++

  @Override public void ccSetMotorStatus(char pxStatus_acnlx){
    EcMotorIcon.ccSetMotorStatus(cmMotorLampL, pxStatus_acnlx);
    EcMotorIcon.ccSetMotorStatus(cmMotorLampR, pxStatus_acnlx);
  }//+++
  
}//***eof
