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

public class EcWeigher extends EcElement{
  
  private static final int
    C_GAUGE_W=4,
    C_GAP=1
  ;//...
  
  private int cmMaxKG;
  
  private final EcGauge cmCellGauge;
  private final EcValueBox cmTargetBox,cmCellBox;
  
  public EcWeigher(String pxName, int pxX, int pxY, int pxKG, int pxHeadID){
    
    super();
    ccSetupKey(pxName);
    ccSetID(pxHeadID);
    
    cmMaxKG=pxKG;
    
    cmCellGauge=new EcGauge();
    cmCellGauge.ccSetHasStroke(true);
    cmCellGauge.ccSetIsVertical(true);
    cmCellGauge.ccSetGaugeColor(EcFactory.C_DIM_GRAY, EcFactory.C_LIT_GRAY);
    cmCellGauge.ccSetColor(EcFactory.C_YELLOW, EcFactory.C_DIM_GRAY);
    cmCellGauge.ccSetIsActivated(true);
    
    cmTargetBox=EcUnitFactory.ccCreateSettingValueBox("-0000kg", "kg");
    cmTargetBox.ccSetValue(-1, 4);
    
    cmCellBox=EcUnitFactory.ccCreateDegreeValueBox("-0000kg", "kg");
    cmCellBox.ccSetValue(-1, 4);
    cmCellBox.ccSetColor(EcFactory.C_DARK_RED);
    
    cmCellGauge.ccSetSize(C_GAUGE_W,cmTargetBox.ccGetH()*2+C_GAP);
    
    ccSetupLocation(pxX, pxY, cmCellBox.ccGetW()+C_GAP+C_GAUGE_W);
    
  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY, int pxW){
    
    ccSetLocation(pxX, pxY);
    
    cmCellGauge.ccSetLocation(cmX, cmY);
    cmTargetBox.ccSetLocation(cmCellGauge,C_GAP, 0);
    cmCellBox.ccSetLocation(cmTargetBox,0,C_GAP);
    
    if(pxW>(cmCellBox.ccGetW()+C_GAP+C_GAUGE_W)){
      int lpOffset=pxW-(cmCellBox.ccGetW()+C_GAP+C_GAUGE_W);
      cmTargetBox.ccSetSize(null, lpOffset, 0);
      cmCellBox.ccSetSize(cmTargetBox);
    }//..?
    
    ccSetEndPoint(cmCellBox.ccEndX(), cmCellBox.ccEndY());
    
  }//++!
  
  //===

  @Override public void ccUpdate(){
      
    cmCellGauge.ccUpdate();
    cmTargetBox.ccUpdate();
    cmCellBox.ccUpdate();
    
  }//+++
  
  //===
  
  public final void ccSetMaxKG(int pxValue){
    cmMaxKG=pxValue;
  }//+++
  
  public final void ccSetCurrentKG(int pxValue){
    cmCellBox.ccSetValue(pxValue);
    int lpValue=pxValue;
    if(lpValue<0){lpValue=0;}
    if(lpValue>cmMaxKG){lpValue=cmMaxKG;}
    cmCellGauge.ccSetPercentage(lpValue, cmMaxKG);
  }//+++
  
  public final void ccSetGaugeStatus(char pxStatus_ewcpt){
    cmCellGauge.ccSetColor(pxStatus_ewcpt=='e'?EcFactory.C_GRAY:
      pxStatus_ewcpt=='c'?EcFactory.C_ORANGE:
      pxStatus_ewcpt=='d'?EcFactory.C_PURPLE:
      pxStatus_ewcpt=='t'?EcFactory.C_WATER:
        EcFactory.C_YELLOW
    );
  }//+++
  
  public final void ccSetTargetKG(int pxValue){
    cmTargetBox.ccSetValue(pxValue);
  }//+++
  
  public final void ccSetIsLocked(boolean pxStatus){
    cmCellBox.ccSetIsActivated(pxStatus);
  }//+++
  
}//***eof
