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

public class EcInclineBelcon extends EcElement implements EiMotorized{

  private final EcLamp cmCAS;
  private int cmBelconColor;
  
  public EcInclineBelcon(
    String pxName, int pxLength, int pxCut, int pxHeadID
  ){

    super();
    ccSetupKey(pxName);
    ccSetSize(pxLength, pxCut);
    ccSetID(pxHeadID);
    
    cmBelconColor=EcUnitFactory.C_C_METAL;
    
    cmCAS=EcUnitFactory.ccCreateIndicatorLamp("CAS", EcFactory.C_YELLOW);
    cmCAS.ccSetNameAlign('a');
    
  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    ccSetLocation(pxX, pxY);
    cmCAS.ccSetLocation(ccCenterX()-4, cmY-cmCAS.ccGetW());
  }//++!
  
  //===

  @Override public void ccUpdate(){

    pbOwner.strokeWeight(20);
    pbOwner.stroke(cmBelconColor);
    {
      pbOwner.line(cmX, cmY, cmX+cmH, cmY+cmH);
      pbOwner.line(cmX+cmH, cmY+cmH, ccEndX(), ccEndY());
    }
    pbOwner.strokeWeight(1);
    pbOwner.noStroke();

    cmCAS.ccUpdate();
    
  }//+++

  public void ccSetHasAggregateFlow(boolean pxStatus){
    cmCAS.ccSetIsActivated(pxStatus);
  }//+++

  @Override public void ccSetMotorON(boolean pxStatus){
    cmBelconColor=pxStatus?
      EcUnitFactory.C_C_POWERED:
      EcUnitFactory.C_C_METAL;
  }//+++
  
}//***eof
