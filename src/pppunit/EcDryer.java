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

public class EcDryer extends EcElement implements EiMotorized{
  
  static private final int C_OFFSET=3;
  
  //===

  private int cmDryerColor;
  private int cmTphMax;
  private final EcValueBox cmKPABox;
  private final EcGauge cmTPHGauge;
  private final EcValueBox cmTPHBox;

  public EcDryer(String pxName, int pxHeadID){
    
    super();

    cmTphMax=340;
    
    cmDryerColor=EcUnitFactory.C_SHAPE_COLOR_METAL;

    ccSetupKey(pxName);
    ccSetID(pxHeadID);

    //-- construction
    cmKPABox=new EcValueBox();
    cmKPABox.ccSetText("-9999 kPa");
    cmKPABox.ccSetSize();
    cmKPABox.ccSetValue(-20, 4);
    cmKPABox.ccSetUnit(" kPa");
    cmKPABox.ccSetTextColor(EcFactory.C_LIT_GRAY);
    cmKPABox.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_DIM_BLUE);

    cmTPHGauge=new EcGauge();
    cmTPHGauge.ccSetColor(EcFactory.C_ORANGE, EcFactory.C_DIM_GRAY);
    cmTPHGauge.ccSetPercentage(1);

    cmTPHBox=new EcValueBox();
    cmTPHBox.ccSetText("999 TpH");
    cmTPHBox.ccSetSize();
    cmTPHBox.ccSetValue(1, 3);
    cmTPHBox.ccSetUnit(" TpH");
    cmTPHBox.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_DIM_YELLOW);

    //-- layout

  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    ccSetLocation(pxX, pxY);
    cmKPABox.ccSetLocation(pxX+C_OFFSET*2, pxY+C_OFFSET*2);
    cmTPHGauge.ccSetLocation(pxX+C_OFFSET, pxY+C_OFFSET);
    cmTPHBox.ccSetLocation(
      cmKPABox, C_OFFSET*4+cmKPABox.ccGetW(), 
      cmKPABox.ccGetH()+C_OFFSET*2
    );
    cmTPHGauge.ccSetEndPoint
      (cmTPHBox.ccEndX()+C_OFFSET,cmTPHBox.ccEndY()+C_OFFSET);
    ccSetEndPoint(cmTPHGauge,C_OFFSET,C_OFFSET);
  }//++!
  
  //===

  @Override public void ccUpdate(){

    pbOwner.fill(cmDryerColor);
    pbOwner.rect(cmX, cmY, cmW, cmH);

    cmTPHGauge.ccUpdate();
    cmKPABox.ccUpdate();
    cmTPHBox.ccUpdate();

  }//+++

  public final void ccSetIsOnFire(boolean pxStatus){
    cmTPHGauge.ccSetIsActivated(pxStatus);
    cmTPHGauge.ccSetGaugeColor(
      pxStatus?EcFactory.C_DARK_RED:EcFactory.C_DARK_GRAY,
      pxStatus?EcFactory.C_LIT_GRAY:EcFactory.C_GRAY
    );
  }//+++

  public final void ccSetKPA(int pxVal){
    cmKPABox.ccSetValue(pxVal);
  }//+++

  public final void ccSetTPH(int pxVal){
    cmTPHBox.ccSetValue(pxVal);
    //..plus one to avoid under bound over rap
    cmTPHGauge.ccSetPercentage(pxVal+1, cmTphMax);
  }//+++

  public final void ccSetMAX(int pxVal){
    cmTphMax=pxVal;
  }//+++

  @Override
  public void ccSetMotorON(boolean pxStatus){
    cmDryerColor=pxStatus?
      EcUnitFactory.C_SHAPE_COLOR_POWERED:
      EcUnitFactory.C_SHAPE_COLOR_METAL;
  }//+++
  
}//***eof
