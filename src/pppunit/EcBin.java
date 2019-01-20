/*
 * Copyright (C) 2019 keypad
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
import pppshape.EcHopperShape;

public class EcBin extends EcElement {
  
  private final EcHopperShape cmBinShape;
  private final EcGauge cmLevelor;
  
  public EcBin(String pxName, int pxScaleW, int pxHeadID){
    
    super();
    ccSetupKey(pxName);
    ccSetID(pxHeadID);
    ccSetSize(pxScaleW, pxScaleW*3/2);
    
    cmBinShape=new EcHopperShape();
    cmBinShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    cmBinShape.ccSetSize(cmW,cmH);
    cmBinShape.ccSetCut();
    
    cmLevelor=new EcGauge();
    cmLevelor.ccSetIsVertical(true);
    cmLevelor.ccSetHasStroke(true);
    cmLevelor.ccSetGaugeColor(EcFactory.C_DARK_GRAY, EcFactory.C_LIT_GRAY);
    cmLevelor.ccSetColor(EcFactory.C_RED, EcFactory.C_YELLOW);
    cmLevelor.ccSetSize(4, cmH*2/3);
    
  }//++! 
  
  public final void ccSetupLocation(int pxX, int pxY){
    ccSetLocation(pxX, pxY);
    cmBinShape.ccSetLocation(cmX, cmY);
    cmLevelor.ccSetLocation(cmBinShape,2, 2);
  }//++!
  
  //===

  @Override public void ccUpdate(){
    cmBinShape.ccUpdate();
    cmLevelor.ccUpdate();
    pbOwner.fill(EcFactory.C_LIT_GRAY);
    pbOwner.text(cmName, cmLevelor.ccEndX()+2, cmLevelor.ccGetY());
  }//+++
  
  //===
  
  public final void ccSetLevelor(int pxHalfByte){
    cmLevelor.ccSetPercentage(pxHalfByte);
  }//+++
  
  public final void ccSetIsOverFlowed(boolean pxStatus){
    cmLevelor.ccSetIsActivated(pxStatus);
  }//+++
  
  public final void ccSetLevelor(boolean pxL, boolean pxM, boolean pxH){
    cmLevelor.ccSetPercentage(4);
    if(pxL){cmLevelor.ccSetPercentage(40);}
    if(pxM){cmLevelor.ccSetPercentage(70);}
    if(pxH){cmLevelor.ccSetPercentage(120);}
    cmLevelor.ccSetIsActivated(pxH);
  }//+++
  
  public final void ccSetLevelor(boolean pxL, boolean pxH){
    cmLevelor.ccSetPercentage(4);
    if(pxL){cmLevelor.ccSetPercentage(60);}
    if(pxH){cmLevelor.ccSetPercentage(120);}
    cmLevelor.ccSetIsActivated(pxH);
  }//+++
  
  public final void ccSetLevelor(boolean pxOne){
    cmLevelor.ccSetPercentage(pxOne?88:4);
  }//+++
  
}//***eof
