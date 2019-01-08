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

public class EcInclineBelcon extends EcMoterizedUnit{

  private final EcLamp cmCAS;
  private final EcLamp cmEmsLamp;


  public EcInclineBelcon(
    String pxName, int pxX, int pxY, int pxLength, int pxCut, int pxHeadID
  ){

    super();
    ccTakeKey(pxName);
    ccSetBound(pxX, pxY, pxLength, pxCut);
    ccSetID(pxHeadID);
    
    cmEmsLamp=EcUnitFactory.ccCreateIndicatorLamp("EMS", EcFactory.C_PURPLE);
    cmEmsLamp.ccSetNameAlign('b');
    cmEmsLamp.ccSetLocation(
      pxX+pxLength-cmEmsLamp.ccGetW()*2,
      pxY+pxCut-cmEmsLamp.ccGetW()/2
    );

    cmCAS=EcUnitFactory.ccCreateIndicatorLamp("CAS", EcFactory.C_YELLOW);
    cmCAS.ccSetLocation(ccCenterX()-4, cmY-cmCAS.ccGetW());
    cmCAS.ccSetNameAlign('a');
    
    cmMotor.ccSetLocation(ccEndX(), ccEndY());

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
    cmEmsLamp.ccUpdate();
    cmMotor.ccUpdate();
    
  }//+++

  public void ccSetHasAggregateFlow(boolean pxStatus){
    cmCAS.ccSetIsActivated(pxStatus);
  }//+++
  
}//***eof
