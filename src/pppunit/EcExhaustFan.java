/*
 * Copyright (C) 2019 Key Parker
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
import pppshape.EcBlowerShape;

public class EcExhaustFan extends EcElement implements EiMotorized{
  
  private static final int C_LED_W=4,C_LED_H=4;
  
  //===
  
  private boolean cmHSW;
  private final EcBlowerShape cmFanShape;
  
  public EcExhaustFan(String pxName, int pxScaleW, int pxHeadID){
    
    super();
    ccSetupKey(pxName);
    ccSetID(pxHeadID);
    
    cmHSW=false;
    
    cmFanShape=new EcBlowerShape();
    cmFanShape.ccSetBaseColor(EcUnitFactory.C_C_METAL);
    cmFanShape.ccSetSize(pxScaleW, pxScaleW*2);
    cmFanShape.ccSetDirection('u');
    
  }//++!
  
  public final void ccSetupLocation(int pxX,int pxY){
    
    ccSetLocation(pxX, pxY);
    cmFanShape.ccSetLocation(cmX, cmY);
    
    //[TODO]:: add bound method to blower shape
    ccSetSize(cmFanShape);
    
  }//++!
  
  //===
  
  @Override public void ccUpdate(){
    
    cmFanShape.ccUpdate();
    
    pbOwner.fill(cmHSW?EcUnitFactory.C_C_LED:EcFactory.C_DARK_GRAY);
    pbOwner.rect(
      cmFanShape.ccGetX()+2, 
      cmFanShape.ccGetY()+2,
      C_LED_W, C_LED_H
    );
    
  }//+++
  
  //===
  
  @Override public void ccSetMotorON(boolean pxStatus){
    cmFanShape.ccSetBaseColor(pxStatus?
      EcUnitFactory.C_C_POWERED:
      EcUnitFactory.C_C_METAL
    );
  }//+++
  
  public final void ccSetHasPressrue(boolean pxStatus){
    cmHSW=pxStatus;
  }//+++
  
}//***eof
