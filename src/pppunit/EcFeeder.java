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
import pppmain.MainOperationModel;
import pppshape.EcHopperShape;
import pppshape.EcBelconShape;

public class EcFeeder extends EcElement implements EiMotorized{

  private final EcValueBox cmBox;

  private final EcGauge cmGauge;

  private final EcHopperShape cmHopper;

  private final EcBelconShape cmBelt;
  
  public EcFeeder(String pxName, int pxHeadID){

    super();
    ccSetupKey(pxName);
    ccSetID(pxHeadID);

    cmBox=EcUnitFactory.ccCreateSettingValueBox("1111r", "r");
    cmBox.ccSetValue(555, 4);

    cmGauge=EcFactory.ccCreateGauge(pxName, true, false, cmW-1, 4);
    cmGauge.ccSetNameAlign('a');
    cmGauge.ccSetColor(EcFactory.C_YELLOW, EcFactory.C_DIM_GRAY);
    cmGauge.ccSetPercentage(1);

    cmHopper=new EcHopperShape();
    cmHopper.ccSetBound(cmX, cmY, cmW, cmH);
    cmHopper.ccSetCut(cmW/4);
    cmHopper.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);

    cmBelt=new EcBelconShape();
    cmBelt.ccSetSize(cmW, 10);
    cmBelt.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    
  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    
    ccSetLocation(pxX, pxY);
    cmBox.ccSetLocation(cmX, cmY);
    cmGauge.ccSetLocation(cmBox, 0, -cmBox.ccGetH()-5);
    cmBelt.ccSetLocation(cmHopper, 0, 4);
    ccSetSize(cmBox.ccGetW()+1, cmBox.ccGetW()+1);
    
  }//++!
  
  //===

  @Override public void ccUpdate(){

    cmHopper.ccUpdate();
    cmBelt.ccUpdate();
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

  @Override
  public void ccSetMotorON(boolean pxStatus){
    //[TODO]::fill this
    System.err.println("Not supported yet."); 
  }//+++
  
}//***eof
