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
import kosui.ppplocalui.EcValueBox;
import pppicon.EcControlMotorIcon;
import pppshape.EcBlowerShape;

public class EcBurner extends EcMoterizedUnit{

  private static final int C_LED_W=4,C_LED_H=8;

  private boolean cmIG, cmPV, cmCDS;

  private final EcBlowerShape cmBurnerShape;

  private final EcValueBox cmDegreeBox;

  private final EcControlMotorIcon cmDamperIcon;

  public EcBurner(String pxName, int pxX, int pxY, int pxHeadID){

    super();
    ccTakeKey(pxName);
    ccSetLocation(pxX, pxY);
    ccSetID(pxHeadID);

    cmIG=false;
    cmPV=false;
    cmCDS=false;

    cmBurnerShape=new EcBlowerShape();
    cmBurnerShape.ccSetLocation(cmX, cmY);
    cmBurnerShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);

    cmDegreeBox=EcUnitFactory.ccCreateDegreeValueBox("-010%", "%");
    cmDegreeBox.ccSetLocation(cmX+2, cmY-12);
    cmDegreeBox.ccSetValue(1, 3);

    ccSetSize(cmDegreeBox.ccGetW()+8, cmDegreeBox.ccGetH()+4);
    cmBurnerShape.ccSetSize(cmW, cmH);
    cmBurnerShape.ccSetDirection('r');

    cmDamperIcon=new EcControlMotorIcon();
    cmDamperIcon.ccSetLocation(ccCenterX()+5, ccEndY()+cmDamperIcon.ccGetH());

    cmMotor.ccSetLocation(cmX-cmMotor.ccGetW()*3/2, cmY+cmMotor.ccGetH());

  }//++!

  @Override
  public void ccUpdate(){

    cmBurnerShape.ccUpdate();
    cmDegreeBox.ccUpdate();
    cmDamperIcon.ccUpdate();
    cmMotor.ccUpdate();

    pbOwner.fill(cmIG?EcFactory.C_YELLOW:EcFactory.C_DARK_GRAY);
    pbOwner.rect(ccEndX()-C_LED_W*4, ccEndY()-2-C_LED_H, C_LED_W, C_LED_H);
    pbOwner.fill(cmPV?EcFactory.C_ORANGE:EcFactory.C_DARK_GRAY);
    pbOwner.rect(ccEndX()-C_LED_W*3, ccEndY()-2-C_LED_H, C_LED_W, C_LED_H);
    pbOwner.fill(cmCDS?EcFactory.C_RED:EcFactory.C_DARK_GRAY);
    pbOwner.rect(ccEndX()-C_LED_W*2, ccEndY()-2-C_LED_H, C_LED_W, C_LED_H);

  }//+++

  public final void ccSetDegree(int pxPercentage){
    cmDegreeBox.ccSetValue(pxPercentage);
    cmDamperIcon.ccSetDegree(pxPercentage);
  }//+++

  public final void ccSetIsFull(boolean pxStatus){
    cmDamperIcon.ccSetIsFull(pxStatus);
  }//+++

  public final void ccSetIsClosed(boolean pxStatus){
    cmDamperIcon.ccSetIsClosed(pxStatus);
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

}//***eof
