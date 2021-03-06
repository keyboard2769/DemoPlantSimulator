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

import kosui.ppplocalui.EcButton;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcGauge;
import kosui.ppplocalui.EcLamp;
import kosui.ppplocalui.EcValueBox;

public final class EcUnitFactory {
  
  public static final int
    //-- color
    C_C_MODEL_PANE   = 0xFF306030,
    C_C_CONTROL_PANE = 0xFF457845,
    C_C_SWITCH_PANE  = 0xFF345678,
    C_C_POWERED      = 0xFFDEDE34,
    C_C_LED          = 0xFF34ABCD,
    C_C_METAL        = 0xFF898989,
    C_C_DUCT         = 0xFFBCBCBC
  ;//...
  
  //=== configurator
  static public final void ccConfigLevel(EcGauge pxTarget, char pxMode_elmf){
    switch(pxMode_elmf){
      case 'l':pxTarget.ccSetPercentage(0x22);break;
      case 'm':pxTarget.ccSetPercentage(0x44);break;
      case 'f':pxTarget.ccSetPercentage(0x76);break;
      default: pxTarget.ccSetPercentage(0x01);break;
    }//..?
  }//+++
  
  //=== creator
    
  //=== creator ** button
  
  static public final EcButton ccCreateWeighLockSW(
    String pxMattName, int pxID
  ){
    EcButton lpRes=ccCreateSingleCharacterSW("X", pxID);
    lpRes.ccSetName(pxMattName);
    lpRes.ccSetNameAlign('a');
    lpRes.ccSetColor(EcFactory.C_RED, EcFactory.C_DIM_RED);
    lpRes.ccSetNameColor(EcFactory.C_LIT_GRAY);
    return lpRes;
  }//+++
  
  static public final EcButton ccCreateWeighSW(
    String pxMattName, int pxID
  ){
    EcButton lpRes=ccCreateSingleCharacterSW("+", pxID);
    lpRes.ccSetName(pxMattName);
    lpRes.ccSetColor(EcFactory.C_YELLOW, EcFactory.C_DARK_GRAY);
    return lpRes;
  }//+++
    
  static public final EcButton ccCreateSingleCharacterSW(
    String pxText, int pxID
  ){
    EcButton lpRes=new EcButton();
    lpRes.ccSetSize(20, 20);
    lpRes.ccSetID(pxID);
    lpRes.ccSetKey("<nsc>");
    lpRes.ccSetText(pxText);
    lpRes.ccSetNameAlign('x');
    lpRes.ccSetColor(EcFactory.C_YELLOW, EcFactory.C_DARK_GRAY);
    return lpRes;
  }//+++
    
  static public final EcButton ccCreateDischargeSW(
    String pxMattName, int pxID
  ){
    EcButton lpRes=new EcButton();
    lpRes.ccSetID(pxID);
    lpRes.ccSetupKey(pxMattName);
    lpRes.ccSetSize();
    lpRes.ccSetNameAlign('x');
    lpRes.ccSetColor(EcFactory.C_YELLOW, EcFactory.C_DARK_GRAY);
    return lpRes;
  }//+++
  
    //=== creator ** Lamp
  
  static public final EcLamp ccCreateIndicatorLamp(
    String pxText,int pxOnColor
  ){
    EcLamp lpRes=new EcLamp();
    lpRes.ccSetSize(18, 18);
    lpRes.ccSetText(pxText.substring(0, 1));
    lpRes.ccSetName(pxText);
    lpRes.ccSetNameAlign('a');
    lpRes.ccSetColor(pxOnColor, EcFactory.C_DIM_GRAY);
    return lpRes;
  }//+++
  
  static public final EcLamp ccCreateIndicatorLamp(
    int pxOnColor
  ){
    EcLamp lpRes=new EcLamp();
    lpRes.ccSetSize(12, 12);
    lpRes.ccSetText(" ");
    lpRes.ccSetNameAlign('x');
    lpRes.ccSetColor(pxOnColor, EcFactory.C_DIM_GRAY);
    return lpRes;
  }//+++
  
  //=== creator ** box
  
  static public final EcValueBox ccCreateDegreeValueBox(
    String pxForm,String pxUnit
  ){
    EcValueBox lpRes=new EcValueBox();
    lpRes.ccSetText(pxForm);
    lpRes.ccSetSize();
    lpRes.ccSetUnit(pxUnit);
    lpRes.ccSetTextColor(EcFactory.C_WHITE);
    lpRes.ccSetColor(EcFactory.C_DARK_PURPLE, EcFactory.C_BLACK);
    return lpRes;
  }//+++
  
  static public final EcValueBox ccCreateSettingValueBox(
    String pxForm,String pxUnit
  ){
    EcValueBox lpRes=new EcValueBox();
    lpRes.ccSetText(pxForm);
    lpRes.ccSetSize();
    lpRes.ccSetUnit(pxUnit);
    lpRes.ccSetTextColor(EcFactory.C_LIT_YELLOW);
    lpRes.ccSetColor(EcFactory.C_DARK_YELLOW, EcFactory.C_DARK_GREEN);
    return lpRes;
  }//+++
  
  static public final EcValueBox ccCreateTemperatureValueBox(
    String pxForm,String pxUnit
  ){
    EcValueBox lpRes=new EcValueBox();
    lpRes.ccSetText(pxForm);
    lpRes.ccSetSize();
    lpRes.ccSetUnit(pxUnit);
    lpRes.ccSetTextColor(EcFactory.C_LIT_GRAY);
    lpRes.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_DIM_RED);
    return lpRes;
  }//+++

  //=== creator ** gauge
  
  static public final EcGauge ccCreateHotBinGauge(int pxScaleW){
    EcGauge lpRes=new EcGauge();
    
    lpRes.ccSetSize(pxScaleW, pxScaleW*3/2);
    lpRes.ccSetHasStroke(true);
    lpRes.ccSetIsVertical(true);
    lpRes.ccSetGaugeColor(EcFactory.C_DARK_GRAY, EcFactory.C_LIT_GRAY);
    lpRes.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_YELLOW);
    lpRes.ccSetPercentage(0x05);
    
    return lpRes;
  }//+++
  
}//***eof
