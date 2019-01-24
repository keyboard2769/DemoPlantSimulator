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

public class EcFeeder extends EcElement implements EiMotorized{

  private final EcValueBox cmBox;
  private final EcGauge cmGauge;
  private final EcHopperShape cmHopperShape;
  
  public EcFeeder(String pxName, int pxHeadID){

    super();
    ccSetupKey(pxName);
    ccSetID(pxHeadID);
    
    //--
    cmBox=EcUnitFactory.ccCreateSettingValueBox("1111r", "r");
    cmBox.ccSetValue(555, 4);
    
    cmHopperShape=new EcHopperShape();
    cmHopperShape.ccSetSize(cmBox.ccGetW()+3, cmBox.ccGetH()*2);
    cmHopperShape.ccSetCut();

    //--
    cmGauge=EcFactory.ccCreateGauge(pxName, true, false, cmW-1, 4);
    cmGauge.ccSetNameAlign('b');
    cmGauge.ccSetColor(EcFactory.C_YELLOW, EcFactory.C_DIM_GRAY);
    cmGauge.ccSetPercentage(1);
    cmGauge.ccSetSize(cmBox, true, false);
    
    //--
    ccSetSize(cmHopperShape);
    
  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    
    int lpGap=1;
    
    ccSetLocation(pxX, pxY);
    cmHopperShape.ccSetLocation(cmX, cmY);
    cmBox.ccSetLocation(cmHopperShape,lpGap,lpGap);
    cmGauge.ccSetLocation(cmBox,0,lpGap);
    
  }//++!
  
  //===

  @Override public void ccUpdate(){

    cmHopperShape.ccUpdate();
    cmGauge.ccUpdate();
    cmBox.ccUpdate();

  }//+++

  public final void ccSetRPM(int pxVal){
    cmGauge.ccSetPercentage(pxVal, MainOperationModel.C_FEEDER_RPM_MAX);
    cmBox.ccSetValue(pxVal);
  }//+++

  public final void ccSetIsSending(boolean pxStatus){
    cmGauge.ccSetIsActivated(pxStatus);
  }//+++

  @Override public void ccSetMotorON(boolean pxStatus){
    cmHopperShape.ccSetBaseColor(pxStatus?
      EcUnitFactory.C_C_POWERED:
      EcUnitFactory.C_C_METAL
    );
  }//+++
  
}//***eof
