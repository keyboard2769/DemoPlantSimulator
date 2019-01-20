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

public class EcDryer extends EcElement implements EiMotorized{

  private int cmTphMax;
  private final EcGauge cmTPHGauge;

  public EcDryer(String pxName,int pxScaleW, int pxHeadID){
    
    super();
    ccSetupKey(pxName);
    ccSetID(pxHeadID);
    ccSetColor(
      EcUnitFactory.C_SHAPE_COLOR_POWERED,
      EcUnitFactory.C_SHAPE_COLOR_METAL
    );
    
    cmTphMax=340;

    cmTPHGauge=new EcGauge();
    cmTPHGauge.ccSetColor(EcFactory.C_ORANGE, EcFactory.C_DIM_GRAY);
    cmTPHGauge.ccSetPercentage(1);
    cmTPHGauge.ccSetSize(pxScaleW,pxScaleW*2/5);
    
  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    int lpGap=5;
    ccSetLocation(pxX, pxY);
    cmTPHGauge.ccSetLocation(pxX+lpGap, pxY+lpGap);
    ccSetEndPoint(cmTPHGauge,lpGap,lpGap);
  }//++!
  
  //===

  @Override public void ccUpdate(){
    drawRect(ccActColor());
    cmTPHGauge.ccUpdate();
  }//+++

  public final void ccSetIsOnFire(boolean pxStatus){
    cmTPHGauge.ccSetIsActivated(pxStatus);
    cmTPHGauge.ccSetGaugeColor(
      pxStatus?EcFactory.C_DARK_RED:EcFactory.C_DARK_GRAY,
      pxStatus?EcFactory.C_LIT_GRAY:EcFactory.C_GRAY
    );
  }//+++

  public final void ccSetTPH(int pxVal){
    //..plus one to avoid under bound over rap
    cmTPHGauge.ccSetPercentage(pxVal+1, cmTphMax);
  }//+++

  public final void ccSetMAX(int pxVal){
    cmTphMax=pxVal;
  }//+++

  @Override public void ccSetMotorON(boolean pxStatus){
    ccSetIsActivated(pxStatus);
  }//+++
  
}//***eof
