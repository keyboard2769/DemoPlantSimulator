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
import kosui.ppplocalui.EcGauge;
import pppshape.EcHopperShape;

public class EcHotTower extends EcElement{

  private final EcGauge cmAG6LV,cmAG5LV,cmAG4LV,cmAG3LV,cmAG2LV,cmAG1LV;
  
  private final EcHopperShape cmHopperShape;

  public EcHotTower(String pxName, int pxHeadID){

    super();
    ccSetupKey(pxName);
    ccSetColor(EcUnitFactory.C_C_POWERED,
      EcUnitFactory.C_C_METAL
    );
    
    int lpBinScale=15;
    
    cmAG6LV=EcUnitFactory.ccCreateHotBinGauge(lpBinScale);
    cmAG5LV=EcUnitFactory.ccCreateHotBinGauge(lpBinScale);
    cmAG4LV=EcUnitFactory.ccCreateHotBinGauge(lpBinScale);
    cmAG3LV=EcUnitFactory.ccCreateHotBinGauge(lpBinScale);
    cmAG2LV=EcUnitFactory.ccCreateHotBinGauge(lpBinScale);
    cmAG1LV=EcUnitFactory.ccCreateHotBinGauge(lpBinScale);
    
    cmHopperShape=new EcHopperShape();
    cmHopperShape.ccSetBaseColor(EcUnitFactory.C_C_METAL);
    
  }//++!
  
  public final void ccSetupLocation( int pxX, int pxY){
    
    int lpGap=6;
    
    ccSetLocation(pxX, pxY);
    cmAG6LV.ccSetLocation(cmX+lpGap, cmY+lpGap);
    cmAG5LV.ccSetLocation(cmAG6LV,lpGap,0);
    cmAG4LV.ccSetLocation(cmAG5LV,lpGap,0);
    cmAG3LV.ccSetLocation(cmAG4LV,lpGap,0);
    cmAG2LV.ccSetLocation(cmAG3LV,lpGap,0);
    cmAG1LV.ccSetLocation(cmAG2LV,lpGap,0);
    ccSetEndPoint(cmAG1LV, lpGap, lpGap);
    
    cmHopperShape.ccSetLocation(cmX, cmY);
    cmHopperShape.ccSetSize(cmW,cmH);
    cmHopperShape.ccSetCut(8);
    
  }//++!
  
  //===

  @Override public void ccUpdate(){
    
    //drawRect(ccActColor());
    
    cmHopperShape.ccUpdate();
    
    cmAG6LV.ccUpdate();
    cmAG5LV.ccUpdate();
    cmAG4LV.ccUpdate();
    cmAG3LV.ccUpdate();
    cmAG2LV.ccUpdate();
    cmAG1LV.ccUpdate();
    
  }//+++
  
  //===
  
  public final int ccGetShapeWidth(){return cmHopperShape.ccGetW();}//+++

  public final void ccSetHotBinLevel(int pxBin, char pxLV_elmf){
    switch(pxBin){
      case 6:EcUnitFactory.ccConfigLevel(cmAG6LV, pxLV_elmf);break;
      case 5:EcUnitFactory.ccConfigLevel(cmAG5LV, pxLV_elmf);break;
      case 4:EcUnitFactory.ccConfigLevel(cmAG4LV, pxLV_elmf);break;
      case 3:EcUnitFactory.ccConfigLevel(cmAG3LV, pxLV_elmf);break;
      case 2:EcUnitFactory.ccConfigLevel(cmAG2LV, pxLV_elmf);break;
      case 1:EcUnitFactory.ccConfigLevel(cmAG1LV, pxLV_elmf);break;
      default:break;
    }//..?
  }//+++
  
}//***eof

