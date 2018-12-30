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
import kosui.ppplocalui.EcGauge;
import kosui.ppplocalui.EcValueBox;
import pppicon.EcMotorIcon;
import pppicon.EcPulleyIcon;
import pppmain.MainOperationModel;
import pppshape.EcHopperShape;
import pppshape.EcBelconShape;

public class EcFeeder extends EcElement implements EiMotorized{

  private final EcValueBox cmBox;

  private final EcGauge cmGauge;

  private final EcHopperShape cmHopper;

  private final EcBelconShape cmBelt;
  
  private final EcPulleyIcon cmMotor;

  public EcFeeder(String pxName,int pxX, int pxY, int pxHeadID){

    super();
    ccTakeKey(pxName);
    ccSetLocation(pxX, pxY);
    ccSetID(pxHeadID);

    cmBox=EcUnitFactory.ccCreateDegreeValueBox("1111r", "r");
    cmBox.ccSetLocation(cmX, cmY);
    cmBox.ccSetValue(555, 4);

    ccSetSize(cmBox.ccGetW()+1, cmBox.ccGetW()+1);

    cmGauge=EcFactory.ccCreateGauge(pxName, true, false, cmW-1, 4);
    cmGauge.ccSetLocation(cmBox, 0, -cmBox.ccGetH()-5);
    cmGauge.ccSetNameAlign('a');
    cmGauge.ccSetColor(EcFactory.C_YELLOW, EcFactory.C_DIM_GRAY);
    cmGauge.ccSetPercentage(1);

    cmHopper=new EcHopperShape();
    cmHopper.ccSetBound(cmX, cmY, cmW, cmH);
    cmHopper.ccSetCut(cmW/4);
    cmHopper.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);

    cmBelt=new EcBelconShape();
    cmBelt.ccSetSize(cmW, 10);
    cmBelt.ccSetLocation(cmHopper, 0, 4);
    cmBelt.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    
    cmMotor=new EcPulleyIcon();
    cmMotor.ccSetLocation(cmBelt,1, 1);

  }//++!

  @Override
  public void ccUpdate(){

    cmHopper.ccUpdate();
    cmBelt.ccUpdate();
    cmMotor.ccUpdate();
    cmGauge.ccUpdate();
    cmBox.ccUpdate();
    
    if(ccIsMouseHovered()){
      pbOwner.fill(0xCC339933);
      pbOwner.ellipse(pbOwner.mouseX, pbOwner.mouseY, 32,32);
    }

  }//+++

  public final void ccSetRPM(int pxVal){
    cmGauge.ccSetPercentage(pxVal, MainOperationModel.C_FEEDER_RPM_MAX);
    cmBox.ccSetValue(pxVal);
  }//+++

  public final void ccSetIsSending(boolean pxStatus){
    cmGauge.ccSetIsActivated(pxStatus);
  }//+++

  @Override public void ccSetMotorStatus(char pxStatus_nlx){
    EcMotorIcon.ccSetMotorStatus(cmMotor, pxStatus_nlx);
  }//+++

}//***eof
