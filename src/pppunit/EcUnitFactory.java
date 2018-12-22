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
import kosui.ppplocalui.EcGauge;
import kosui.ppplocalui.EcLamp;
import kosui.ppplocalui.EcValueBox;


public final class EcUnitFactory {
  
  public static final int C_SHAPE_COLOR_METAL=0xFF9A9A9A;
  
  public static final int C_SHAPE_COLOR_DUCT=0xFFABABAB;
  
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
  static public final EcValueBox ccCreateDegreeValueBox(
    String pxForm,String pxUnit
  ){
    EcValueBox lpRes=new EcValueBox();
    lpRes.ccSetText(pxForm);
    lpRes.ccSetSize();
    lpRes.ccSetUnit(pxUnit);
    lpRes.ccSetTextColor(EcFactory.C_LIT_GRAY);
    lpRes.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_DARK_BLUE);
    return lpRes;
  }//+++
  
  static public final EcValueBox ccCreateSettingValueBox(
    String pxForm,String pxUnit
  ){
    EcValueBox lpRes=new EcValueBox();
    lpRes.ccSetText(pxForm);
    lpRes.ccSetSize();
    lpRes.ccSetUnit(pxUnit);
    lpRes.ccSetTextColor(EcFactory.C_LIT_GRAY);
    lpRes.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_DARK_GREEN);
    return lpRes;
  }//+++
  
  static public final EcValueBox ccCreateTempratureValueBox(
    String pxForm,String pxUnit
  ){
    EcValueBox lpRes=new EcValueBox();
    lpRes.ccSetText(pxForm);
    lpRes.ccSetSize();
    lpRes.ccSetUnit(pxUnit);
    lpRes.ccSetTextColor(EcFactory.C_LIT_GRAY);
    lpRes.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_DARK_RED);
    return lpRes;
  }//+++
  
  static public final EcLamp ccCreateIndicatorLamp(
    String pxText,int pxOnColor
  ){
    EcLamp lpRes=new EcLamp();
    lpRes.ccSetSize(18, 18);
    lpRes.ccSetText(pxText);
    lpRes.ccSetNameAlign('x');
    lpRes.ccSetColor(pxOnColor, EcFactory.C_DIM_GRAY);
    return lpRes;
  }//+++

}//***eof
