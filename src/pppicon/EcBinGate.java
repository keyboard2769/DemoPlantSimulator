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

package pppicon;

import com.sun.istack.internal.NotNull;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcShape;

public class EcBinGate extends EcShape{
  
  private static final int 
    C_GAP=1,
    C_LED_H=3,
    C_LED_W=2
    ;//...
  
  private boolean cmOPMV,cmCLMV,cmAS;
  
  public EcBinGate(){
    cmOPMV=false;
    cmCLMV=false;
    cmAS=false;
    ccSetSize(C_LED_W+C_GAP*2, C_LED_H*3+C_GAP*2);
    ccSetBaseColor(EcFactory.C_GRAY);
  }//+++ 

  @Override public void ccUpdate(){
    
    pbOwner.fill(cmBaseColor);
    pbOwner.rect(cmX,cmY,cmW,cmH);
    
    pbOwner.fill(cmOPMV?EcFactory.C_ORANGE:EcFactory.C_DARK_GRAY);
    pbOwner.rect(cmX+C_GAP, cmY+C_GAP, C_LED_W, C_LED_H);
    
    pbOwner.fill(cmAS?EcFactory.C_LIT_BLUE:EcFactory.C_DARK_GRAY);
    pbOwner.rect(cmX+C_GAP, cmY+C_LED_H, C_LED_W, C_LED_H);
    
    pbOwner.fill(cmCLMV?EcFactory.C_ORANGE:EcFactory.C_DARK_GRAY);
    pbOwner.rect(cmX+C_GAP, cmY+C_LED_H*2, C_LED_W, C_LED_H);
    
  }//+++
  
  public final void ccSetIsOpening(boolean pxStatus){cmOPMV=pxStatus;}//+++
  
  public final void ccSetIsClosing(boolean pxStatus){cmCLMV=pxStatus;}//+++
  
  public final void ccSetIsMiddle(boolean pxStatus){cmAS=pxStatus;}//+++
  
  //===
  
  public static final void fnApplyStatus(
    @NotNull EcBinGate pxTarget, char pxBit_omc, boolean pxStatus
  ){
    switch(pxBit_omc){
      case 'o':pxTarget.ccSetIsOpening(pxStatus);break;
      case 'm':pxTarget.ccSetIsMiddle(pxStatus);break;
      case 'c':pxTarget.ccSetIsClosing(pxStatus);break;
      default:break;
    }//..?
  }//+++
  
}//***eof
