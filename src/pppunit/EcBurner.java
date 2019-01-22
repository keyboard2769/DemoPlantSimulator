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
import pppshape.EcBlowerShape;

public class EcBurner extends EcElement implements EiMotorized{

  private static final int C_LED_W=4,C_LED_H=8;
  
  //===

  private boolean cmIG, cmPV, cmCDS;
  private final EcBlowerShape cmBurnerShape;

  public EcBurner(String pxName,int pxScaleW, int pxHeadID){

    super();
    ccSetupKey(pxName);
    ccSetID(pxHeadID);

    cmIG=false;
    cmPV=false;
    cmCDS=false;

    cmBurnerShape=new EcBlowerShape();
    cmBurnerShape.ccSetBaseColor(EcUnitFactory.C_C_METAL);
    cmBurnerShape.ccSetSize(pxScaleW, pxScaleW/2);
    cmBurnerShape.ccSetDirection('r');

  }//++!
  
  //===
  
  public final void ccSetupLocation(int pxX,int pxY){
    
    int lpGap=2;
    ccSetLocation(pxX, pxY);
    cmBurnerShape.ccSetLocation(cmX+lpGap, cmY+lpGap);
    ccSetEndPoint(cmBurnerShape, lpGap, lpGap);
    
  }//++!
  
  //===

  @Override public void ccUpdate(){
    
    cmBurnerShape.ccUpdate();

    pbOwner.fill(cmIG?EcFactory.C_YELLOW:EcFactory.C_DARK_GRAY);
    pbOwner.rect(
      cmBurnerShape.ccEndX()-C_LED_W*4, 
      cmBurnerShape.ccEndY()-2-C_LED_H, C_LED_W, C_LED_H
    );
    pbOwner.fill(cmPV?EcFactory.C_ORANGE:EcFactory.C_DARK_GRAY);
    pbOwner.rect(
      cmBurnerShape.ccEndX()-C_LED_W*3,
      cmBurnerShape.ccEndY()-2-C_LED_H, C_LED_W, C_LED_H
    );
    pbOwner.fill(cmCDS?EcFactory.C_RED:EcFactory.C_DARK_GRAY);
    pbOwner.rect(
      cmBurnerShape.ccEndX()-C_LED_W*2,
      cmBurnerShape.ccEndY()-2-C_LED_H, C_LED_W, C_LED_H
    );

  }//+++

  public final void ccSetIsIgniting(boolean pxStatus){
    cmIG=pxStatus;
  }//+++

  public final void ccSetIsPiloting(boolean pxStatus){
    cmPV=pxStatus;
  }//+++

  public final void ccSetHasFire(boolean pxStatus){
    cmCDS=pxStatus;
  }//+++
  
  //===

  @Override public void ccSetMotorON(boolean pxStatus){
    cmBurnerShape.ccSetBaseColor(pxStatus?
      EcUnitFactory.C_C_POWERED:
      EcUnitFactory.C_C_METAL
    );
  }//+++
  
}//***eof
