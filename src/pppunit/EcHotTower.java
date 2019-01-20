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

public class EcHotTower extends EcElement{

  public static final int
    C_I_SCREEN=0,
    C_I_HOTELEVATOR=1,
    C_I_BLOWER=2;//...

  private static final int
    C_TOWER_W=40,
    C_FLOOR_H=20,
    C_FLOOR_GAP=4,
    C_DUCT_THICK=8,
    C_SCREEN_CUT=16;//...

  private final EcValueBox cmSandTempBox;
  private final EcGauge cmAG6LV,cmAG5LV,cmAG4LV,cmAG3LV,cmAG2LV,cmAG1LV;

  public EcHotTower(String pxName, int pxHeadID){

    super();
    ccSetupKey(pxName);
    
    cmAG6LV=EcUnitFactory.ccCreateHotBinGauge();
    cmAG5LV=EcUnitFactory.ccCreateHotBinGauge();
    cmAG4LV=EcUnitFactory.ccCreateHotBinGauge();
    cmAG3LV=EcUnitFactory.ccCreateHotBinGauge();
    cmAG2LV=EcUnitFactory.ccCreateHotBinGauge();
    cmAG1LV=EcUnitFactory.ccCreateHotBinGauge();
    
    cmSandTempBox=EcUnitFactory.ccCreateTempratureValueBox("-000'C", "'C");
    cmSandTempBox.ccSetValue(17, 3);
    
  }//++!
  
  public final void ccSetupLocation( int pxX, int pxY){
    
    ccSetLocation(pxX, pxY);
    ccSetSize(C_TOWER_W, C_FLOOR_H*4+C_FLOOR_GAP*2);
    cmAG6LV.ccSetLocation(cmX+2, cmY+2);
    cmAG5LV.ccSetLocation(cmAG6LV,2,0);
    cmAG4LV.ccSetLocation(cmAG5LV,2,0);
    cmAG3LV.ccSetLocation(cmAG4LV,2,0);
    cmAG2LV.ccSetLocation(cmAG3LV,2,0);
    cmAG1LV.ccSetLocation(cmAG2LV,2,0);
    cmSandTempBox.ccSetLocation(cmX-8, cmY-70);
    
  }//++!
  
  //===

  @Override public void ccUpdate(){

    //-- draw tower
    pbOwner.fill(EcFactory.C_LIT_GRAY);
    pbOwner.rect(cmX, cmY, cmW, C_FLOOR_H);
    pbOwner.rect(cmX, cmY+C_FLOOR_H+C_FLOOR_GAP, cmW, C_FLOOR_H);
    pbOwner.rect(cmX, cmY+2*(C_FLOOR_H+C_FLOOR_GAP), cmW, C_FLOOR_H);
    pbOwner.rect(
      cmX, cmY+3*C_FLOOR_H+2*C_FLOOR_GAP,
      C_DUCT_THICK, C_FLOOR_H
    );
    pbOwner.rect(
      ccEndX()-C_DUCT_THICK, cmY+3*C_FLOOR_H+2*C_FLOOR_GAP,
      C_DUCT_THICK, C_FLOOR_H
    );

    //-- draw screen
    pbOwner.fill(EcFactory.C_GRAY);
    pbOwner.quad(
      cmX+C_SCREEN_CUT, cmY-C_FLOOR_GAP-C_FLOOR_H,
      ccEndX(), cmY-C_FLOOR_GAP-C_FLOOR_H,
      ccEndX(), cmY-C_FLOOR_GAP,
      cmX, cmY-C_FLOOR_GAP
    );
    pbOwner.fill(EcFactory.C_LIT_GRAY);
    pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-2, -3*C_TOWER_W/4, -2);
    pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-6, -2*C_TOWER_W/4, -2);
    pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-10, -2*C_TOWER_W/4, -2);
    pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-14, -2*C_TOWER_W/4, -2);

    //-- draw extract duct
    pbOwner.fill(EcFactory.C_GRAY);
    pbOwner.rect(
      cmX-C_FLOOR_GAP, cmY,
      -1*C_DUCT_THICK/2, cmH-C_FLOOR_GAP*2
    );
    pbOwner.rect(
      ccEndX()+C_FLOOR_GAP, cmY,
      C_DUCT_THICK/2, cmH-C_FLOOR_GAP*2
    );

    cmSandTempBox.ccUpdate();
    
    //-- update element ** bin lv
    cmAG6LV.ccUpdate();
    cmAG5LV.ccUpdate();
    cmAG4LV.ccUpdate();
    cmAG3LV.ccUpdate();
    cmAG2LV.ccUpdate();
    cmAG1LV.ccUpdate();
    
  }//+++
  
  //===

  public final void ccSetSandTemrature(int pxCelsius){
    cmSandTempBox.ccSetValue(pxCelsius);
  }//+++
  
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

