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
import kosui.ppplocalui.EcTriangleLamp;
import pppicon.EcMotorIcon;
import pppshape.EcMixerShape;
import pppshape.EcScrewShape;
import static pppunit.EcUnitFactory.C_SHAPE_COLOR_DUCT;
import static pppunit.EcUnitFactory.C_SHAPE_COLOR_METAL;

class EcDustMixer extends EcElement implements EiMultipleMoterized{

  public final int //[TODO]::static
    C_I_MIXER=0,
    C_I_SCREW=1;//...

  private final int C_DUCT_THICK=4;//[TOOD]::static

  private final EcMixerShape cmMixerShape;

  private final EcScrewShape cmScrewShape;

  private final EcMotorIcon cmMixerMotor;

  private final EcMotorIcon cmScrewMotor;

  private final EcTriangleLamp cmWaterPumpPL;

  public EcDustMixer(String pxName, int pxX, int pxY, int pxHeadID){

    super();
    ccTakeKey(pxName);
    ccSetBound(pxX, pxY, 48, 24);
    ccSetID(pxHeadID);

    cmMixerShape=new EcMixerShape();
    cmMixerShape.ccSetBaseColor(C_SHAPE_COLOR_METAL);
    cmMixerShape.ccSetBound(cmX, cmY, cmW, cmH);
    cmMixerShape.ccSetRatio();

    cmScrewShape=new EcScrewShape();
    cmScrewShape.ccSetBaseColor(C_SHAPE_COLOR_METAL);
    cmScrewShape.ccSetSize(cmW, 12);
    cmScrewShape.ccSetLocation(cmMixerShape, -cmW/2, -14);
    cmScrewShape.ccSetCut(-1, cmW-8);

    cmMixerMotor=new EcMotorIcon();
    cmMixerMotor.ccSetLocation(cmMixerShape, 4, 1);

    cmScrewMotor=new EcMotorIcon();
    cmScrewMotor.ccSetLocation(cmScrewShape, 4, 4);

    cmWaterPumpPL=new EcTriangleLamp();
    cmWaterPumpPL.ccSetSize(12, 12);
    cmWaterPumpPL.ccSetLocation(ccEndX()+C_DUCT_THICK*4-4,
      ccEndY()-C_DUCT_THICK*2-4);
    cmWaterPumpPL.ccSetColor(EcFactory.C_WATER);
    cmWaterPumpPL.ccSetDirection('l');
    cmWaterPumpPL.ccSetName("WP");
    cmWaterPumpPL.ccSetNameAlign('r');
    cmWaterPumpPL.ccSetText(" ");

  }//++!

  @Override
  public void ccUpdate(){

    //-- draw base shape
    cmScrewShape.ccUpdate();
    cmMixerShape.ccUpdate();

    //-- draw duct
    pbOwner.fill(C_SHAPE_COLOR_DUCT);
    pbOwner.rect(
      ccEndX()-C_DUCT_THICK, cmY-C_DUCT_THICK*2,
      C_DUCT_THICK*3, C_DUCT_THICK
    );
    pbOwner.rect(
      ccEndX()+C_DUCT_THICK, cmY-C_DUCT_THICK*2,
      C_DUCT_THICK, cmH
    );
    pbOwner.rect(
      ccEndX()+C_DUCT_THICK, ccEndY()-C_DUCT_THICK*2,
      C_DUCT_THICK*3, C_DUCT_THICK
    );

    //-- update element
    cmMixerMotor.ccUpdate();
    cmScrewMotor.ccUpdate();
    cmWaterPumpPL.ccUpdate();

  }//+++

  @Override
  public void ccSetMotorStatus(int pxIndex, char pxStatus_acnlx){
    switch(pxIndex){
      case C_I_MIXER:
        EcMotorIcon.ccSetMotorStatus(cmMixerMotor, pxStatus_acnlx);
        break;
      case C_I_SCREW:
        EcMotorIcon.ccSetMotorStatus(cmScrewMotor, pxStatus_acnlx);
        break;
      default: break;
    }//..?
  }//+++

  public final void ccSetIsWaterPumpOn(boolean pxStatus){
    cmWaterPumpPL.ccSetIsActivated(pxStatus);
  }//+++

}//***eof
