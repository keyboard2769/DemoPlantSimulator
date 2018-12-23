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
import pppshape.EcHopperShape;

public class EcWeigher extends EcElement{
  
  private static final int C_GAP=3;
  
  private final EcHopperShape cmHopperShape;
  private final EcGauge cmCellGauge;
  private final EcValueBox cmTargetBox,cmCellBox;
  
  public EcWeigher(String pxName, int pxX, int pxY, int pxHeadID){
    
    super();
    ccTakeKey(pxName);
    ccSetLocation(pxX, pxY);
    ccSetID(pxHeadID);
    
    cmTargetBox=EcUnitFactory.ccCreateSettingValueBox("-0000kg", "kg");
    cmTargetBox.ccSetValue(-1, 4);
    cmTargetBox.ccSetLocation(cmX+C_GAP*2, cmY+C_GAP*2);
    
    cmCellBox=EcUnitFactory.ccCreateDegreeValueBox("-0000kg", "kg");
    cmCellBox.ccSetValue(-1, 4);
    cmCellBox.ccSetLocation(cmTargetBox,0,C_GAP);
    
    cmCellGauge=new EcGauge();
    cmCellGauge.ccSetHasStroke(true);
    cmCellGauge.ccSetIsVertical(true);
    cmCellGauge.ccSetGaugeColor(EcFactory.C_DIM_GRAY, EcFactory.C_LIT_GRAY);
    cmCellGauge.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_YELLOW);
    cmCellGauge.ccSetSize(
      cmTargetBox.ccGetW()  +C_GAP*2,
      cmTargetBox.ccGetH()*2+C_GAP*3
    );
    cmCellGauge.ccSetLocation(cmX+C_GAP, cmY+C_GAP);
    
    cmHopperShape=new EcHopperShape();
    cmHopperShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    cmHopperShape.ccSetSize(
      cmCellGauge.ccGetW()+C_GAP*2, 
      cmCellGauge.ccGetH()*3/2
    );
    cmHopperShape.ccSetLocation(cmX, cmY);
    cmHopperShape.ccSetCut();
    
    ccSetSize(cmHopperShape.ccGetW(),cmHopperShape.ccGetH());
    
  }//+++ 

  @Override public void ccUpdate(){
    
    //[DTFM]::
    pbOwner.fill(0xFF663333);
    pbOwner.rect(cmX, cmY, cmW, cmH);
      
    cmHopperShape.ccUpdate();
    cmCellGauge.ccUpdate();
    cmTargetBox.ccUpdate();
    cmCellBox.ccUpdate();
    
  }//+++
  
}//***eof
