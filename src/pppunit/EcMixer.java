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
import pppicon.EcMixerGateIcon;
import pppshape.EcMixerShape;

public class EcMixer extends EcElement implements EiMotorized{
  
  private static final int C_GAP=4,C_LED_H=4;
  
  //===
  
  private boolean cmHasMixture;
  private final EcMixerShape cmMixerShape;
  private final EcMixerGateIcon cmGateIcon;

  public EcMixer(String pxName, int pxScaleW, int pxHeadID){
    
    super();
    ccSetupKey(pxName);
    ccSetID(pxHeadID);
    ccSetSize(pxScaleW, pxScaleW*3/4);
    
    cmHasMixture=false;
    
    cmMixerShape=new EcMixerShape();
    cmMixerShape.ccSetSize(cmW,cmH);
    cmMixerShape.ccSetCut(cmW/8);
    cmMixerShape.ccSetBaseColor(EcUnitFactory.C_C_METAL);
    
    cmGateIcon=new EcMixerGateIcon();
    cmGateIcon.ccSetSize(48, 12);

  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    
    ccSetLocation(pxX, pxY);
    cmMixerShape.ccSetLocation(cmX, cmY);
    
    cmGateIcon.ccSetLocation(
      ccCenterX(),
      ccEndY()+cmGateIcon.ccGetH()*2
    );
    
  }//++!
  
  //===

  @Override public void ccUpdate(){
    
    //--
    cmMixerShape.ccUpdate();
    
    //--
    pbOwner.fill(cmHasMixture?
      EcFactory.C_PURPLE:
      EcFactory.C_DARK_GRAY
    );
    pbOwner.rect(cmX+C_GAP, cmY+C_GAP, cmW-C_GAP*2, C_LED_H);
    
    //--
    cmGateIcon.ccUpdate();
    
  }//+++
  
  public final void ccSetHasMixture(boolean pxStatus){
    cmHasMixture=pxStatus;
  }//+++

  public final void ccSetIsGateOpening(boolean pxStatus){
    cmGateIcon.ccSetIsOpening(pxStatus);
  }//+++
  
  public final void ccSetIsGateOpened(boolean pxStatus){
    cmGateIcon.ccSetIsOpened(pxStatus);
  }//+++
  
  public final void ccSetIsGateClosed(boolean pxStatus){
    cmGateIcon.ccSetIsClosed(pxStatus);
  }//+++

  @Override public void ccSetMotorON(boolean pxStatus){
    cmMixerShape.ccSetBaseColor(pxStatus?
      EcUnitFactory.C_C_POWERED:
      EcUnitFactory.C_C_METAL
    );
  }//+++
  
}//***eof
