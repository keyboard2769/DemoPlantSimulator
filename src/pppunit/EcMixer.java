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
import kosui.ppplocalui.EcValueBox;
import pppicon.EcMixerGateIcon;
import pppshape.EcMixerShape;

public class EcMixer extends EcElement implements EiMotorized{
  
  private static final int C_GAP=4,C_LED_H=4;
  
  //===
  
  private boolean cmHasMixture;
  
  private final EcMixerShape cmMixerShape;
  private final EcValueBox cmWetTimerBox,cmDryTimerBox,cmTempratureBox;
  private final EcMixerGateIcon cmGateIcon;

  public EcMixer(String pxName, int pxHeadID){
    
    super();
    ccSetupKey(pxName);
    ccSetID(pxHeadID);
    
    cmHasMixture=false;
    
    cmMixerShape=new EcMixerShape();
    cmMixerShape.ccSetBaseColor(EcUnitFactory.C_C_METAL);
    
    cmWetTimerBox=EcUnitFactory
      .ccCreateSettingValueBox("000S", "S");
    cmWetTimerBox.ccSetValue(2, 2);
    cmWetTimerBox.ccSetName("W");
    cmWetTimerBox.ccSetNameAlign('l');
    
    cmDryTimerBox=EcUnitFactory
      .ccCreateSettingValueBox("000S", "S");
    cmDryTimerBox.ccSetValue(8, 2);
    cmDryTimerBox.ccSetName("D");
    cmDryTimerBox.ccSetNameAlign('l');
    
    cmTempratureBox=EcUnitFactory
      .ccCreateTemperatureValueBox("-000'C", "'C");
    cmTempratureBox.ccSetLocation(cmWetTimerBox, C_GAP*2+5,0);
    cmTempratureBox.ccSetValue(9,3);
    
    cmGateIcon=new EcMixerGateIcon();
    cmGateIcon.ccSetSize(48, 12);

  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    
    ccSetLocation(pxX, pxY);
    cmDryTimerBox.ccSetLocation(cmX+1, cmY+C_GAP*2+C_LED_H);
    cmWetTimerBox.ccSetLocation(cmDryTimerBox, 0, 2);
    cmTempratureBox.ccSetLocation(cmWetTimerBox, 20, 0);
    
    ccSetSize(
      cmDryTimerBox.ccGetW()+cmTempratureBox.ccGetW()+C_GAP*4,
      cmDryTimerBox.ccGetH()*4
    );
    cmMixerShape.ccSetBound(cmX, cmY, cmW, cmH);
    cmMixerShape.ccSetCut(cmW/8);
    
    cmGateIcon.ccSetLocation(
      ccCenterX(),
      ccEndY()+cmGateIcon.ccGetH()*2
    );
    
  }//++!
  
  //===

  @Override public void ccUpdate(){
    
    //--
    cmMixerShape.ccUpdate();
    cmWetTimerBox.ccUpdate();
    cmDryTimerBox.ccUpdate();
    cmTempratureBox.ccUpdate();
    
    //--
    pbOwner.fill(cmHasMixture?EcFactory.C_YELLOW:EcFactory.C_DARK_GRAY);
    pbOwner.rect(cmX+C_GAP, cmY+C_GAP, cmW-C_GAP*2, C_LED_H);
    
    //--
    cmGateIcon.ccUpdate();
    
  }//+++
  
  public final void ccSetHasMixture(boolean pxStatus){
    cmHasMixture=pxStatus;
  }//+++

  public final void ccSetWetTime(int pxSecond){
    cmWetTimerBox.ccSetValue(pxSecond);
  }//+++
  
  public final void ccSetDryTime(int pxSecond){
    cmDryTimerBox.ccSetValue(pxSecond);
  }//+++
  
  public final void ccSetTemprature(int pxCelcius){
    cmTempratureBox.ccSetValue(pxCelcius);
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
