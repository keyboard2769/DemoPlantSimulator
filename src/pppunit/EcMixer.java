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
import pppicon.EcSingleSolenoidIcon;
import pppshape.EcMixerShape;

public class EcMixer extends EcMoterizedUnit{
  
  private static final int C_GAP=4,C_LED_H=4;
  
  private boolean cmHasMixture;
  
  private final EcMixerShape cmMixerShape;
  private final EcValueBox cmWetTimerBox,cmDryTimerBox,cmTempratureBox;
  private final EcSingleSolenoidIcon cmGateIcon;

  public EcMixer(String pxName, int pxX, int pxY, int pxHeadID){
    
    super();
    ccTakeKey(pxName);
    ccSetLocation(pxX, pxY);
    ccSetID(pxHeadID);
    
    cmHasMixture=false;
    
    cmMixerShape=new EcMixerShape();
    cmMixerShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    
    cmWetTimerBox=EcUnitFactory
      .ccCreateSettingValueBox("000S", "S");
    cmWetTimerBox.ccSetLocation(cmX+1, cmY+C_GAP*2+C_LED_H);
    cmWetTimerBox.ccSetValue(2, 2);
    cmWetTimerBox.ccSetName("W");
    cmWetTimerBox.ccSetNameAlign('l');
    
    cmDryTimerBox=EcUnitFactory
      .ccCreateSettingValueBox("000S", "S");
    cmDryTimerBox.ccSetLocation(cmWetTimerBox, 0, 2);
    cmDryTimerBox.ccSetValue(8, 2);
    cmDryTimerBox.ccSetName("D");
    cmDryTimerBox.ccSetNameAlign('l');
    
    cmTempratureBox=EcUnitFactory
      .ccCreateTempratureValueBox("-000'C", "'C");
    cmTempratureBox.ccSetLocation(cmDryTimerBox, C_GAP*2+5,0);
    cmTempratureBox.ccSetValue(9,3);
    
    ccSetSize(
      cmWetTimerBox.ccGetW()+cmTempratureBox.ccGetW()+C_GAP*4,
      cmWetTimerBox.ccGetH()*4
    );
    
    cmMixerShape.ccSetBound(cmX, cmY, cmW, cmH);
    cmMixerShape.ccSetRatio();
    
    cmMotor.ccSetLocation
      (ccEndX()-C_GAP-cmMotor.ccGetW(), cmY+C_GAP*2+C_LED_H);
    
    cmGateIcon=new EcSingleSolenoidIcon();
    cmGateIcon.ccSetLocation(
      ccEndX()-cmGateIcon.ccGetW()/2,
      ccEndY()-cmGateIcon.ccGetH()/2
    );

  }//++!

  @Override
  public void ccUpdate(){
    
    //--
    cmMixerShape.ccUpdate();
    cmWetTimerBox.ccUpdate();
    cmDryTimerBox.ccUpdate();
    cmTempratureBox.ccUpdate();
    
    //--
    pbOwner.fill(cmHasMixture?EcFactory.C_YELLOW:EcFactory.C_DIM_GRAY);
    pbOwner.rect(cmX+C_GAP, cmY+C_GAP, cmW-C_GAP*2, C_LED_H);
    
    //--
    cmMotor.ccUpdate();
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
    cmGateIcon.ccSetIsFull(pxStatus);
  }//+++
  
  public final void ccSetIsGateClosed(boolean pxStatus){
    cmGateIcon.ccSetIsClosed(pxStatus);
  }//+++
  
}//***eof
