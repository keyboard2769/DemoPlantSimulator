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

package pppmain;

import java.util.ArrayList;
import kosui.ppplocalui.EcButton;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppunit.EcUnitFactory;
import pppunit.EcWeigher;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_AG_LOCKH;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_FR_LOCKH;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_FR_DISH;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_AG_DISH;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_AS_LOCKH;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_AS_DISH;

public class SubWeighControlGroup implements EiGroup{
  
  public final EcButton
    //--
    cmFR2SW,cmFR1SW,
    cmAG6SW,cmAG5SW,cmAG4SW,cmAG3SW,cmAG2SW,cmAG1SW,
    cmAS1SW,
    //--
    cmFR2LockSW,cmFR1LockSW,
    cmAG6LockSW,cmAG5LockSW,cmAG4LockSW,cmAG3LockSW,cmAG2LockSW,cmAG1LockSW,
    cmAS1LockSW,
    //--
    cmFRDischargeSW,cmAGDischargeSW,cmASDischargeSW
    //--
  ;//...
  
  public final EcWeigher cmFRWeigher,cmAGWeigher,cmASWeigher;
  
  private final EcShape cmFRRange, cmAGRange, cmASRange;
  
  public SubWeighControlGroup(){
    
    //-- all weigher
    cmFRWeigher=new EcWeigher("FR", 0, 0, 500,C_ID_WEIGH_FR_LOCKH);
    cmAGWeigher=new EcWeigher("AG", 0, 0,4000,C_ID_WEIGH_AG_LOCKH);
    cmASWeigher=new EcWeigher("AS", 0, 0, 500,C_ID_WEIGH_AS_LOCKH);
    
    //-- fr weight button 
    cmFR2LockSW=EcUnitFactory.ccCreateWeighLockSW("2",C_ID_WEIGH_FR_LOCKH+2);
    cmFR1LockSW=EcUnitFactory.ccCreateWeighLockSW("1",C_ID_WEIGH_FR_LOCKH+1);
    cmFR2SW=EcUnitFactory.ccCreateWeighSW("FR2",C_ID_WEIGH_FR_DISH+2);
    cmFR1SW=EcUnitFactory.ccCreateWeighSW("FR1",C_ID_WEIGH_FR_DISH+1);
    
    //-- ag weigh button
    cmAG6LockSW=EcUnitFactory.ccCreateWeighLockSW("6",C_ID_WEIGH_AG_LOCKH+6);
    cmAG5LockSW=EcUnitFactory.ccCreateWeighLockSW("5",C_ID_WEIGH_AG_LOCKH+5);
    cmAG4LockSW=EcUnitFactory.ccCreateWeighLockSW("4",C_ID_WEIGH_AG_LOCKH+4);
    cmAG3LockSW=EcUnitFactory.ccCreateWeighLockSW("3",C_ID_WEIGH_AG_LOCKH+3);
    cmAG2LockSW=EcUnitFactory.ccCreateWeighLockSW("2",C_ID_WEIGH_AG_LOCKH+2);
    cmAG1LockSW=EcUnitFactory.ccCreateWeighLockSW("1",C_ID_WEIGH_AG_LOCKH+1);
    cmAG6SW=EcUnitFactory.ccCreateWeighSW("AG6",C_ID_WEIGH_AG_DISH+6);
    cmAG5SW=EcUnitFactory.ccCreateWeighSW("AG5",C_ID_WEIGH_AG_DISH+5);
    cmAG4SW=EcUnitFactory.ccCreateWeighSW("AG4",C_ID_WEIGH_AG_DISH+4);
    cmAG3SW=EcUnitFactory.ccCreateWeighSW("AG3",C_ID_WEIGH_AG_DISH+3);
    cmAG2SW=EcUnitFactory.ccCreateWeighSW("AG2",C_ID_WEIGH_AG_DISH+2);
    cmAG1SW=EcUnitFactory.ccCreateWeighSW("AG1",C_ID_WEIGH_AG_DISH+1);
    
    //-- as weight button
    cmAS1LockSW=EcUnitFactory.ccCreateWeighLockSW("1",C_ID_WEIGH_AS_LOCKH+1);
    cmAS1SW=EcUnitFactory.ccCreateWeighSW("AG1",C_ID_WEIGH_AS_DISH+1);
    
    //-- weigher relocate
    int lpStartX=400,lpStartY=350;
    int lpWeigherGap=60;
    cmFRWeigher.ccSetup(lpStartX, lpStartY, cmFR2SW.ccGetW()*4);
    cmAGWeigher.ccSetup(
      cmFRWeigher.ccEndX()+lpWeigherGap, lpStartY,
      cmAG6SW.ccGetW()*4
    );
    cmASWeigher.ccSetup(
      cmAGWeigher.ccEndX()+lpWeigherGap, lpStartY,
      cmAS1SW.ccGetW()*4
    );
    
    //-- relocate
    int lpFRWeightButtonGapX=4;
    int lpFRWeightButtonGapY=2;
    int lpAGWeightButtonGapX=4;
    int lpAGWeightButtonGapY=2;
    cmFR2LockSW.ccSetLocation(cmFRWeigher, 1, -48);
    cmFR1LockSW.ccSetLocation(cmFR2LockSW, lpFRWeightButtonGapX, 0);
    cmFR2SW.ccSetLocation(cmFR2LockSW, 0,lpFRWeightButtonGapY);
    cmFR1SW.ccSetLocation(cmFR1LockSW, 0,lpFRWeightButtonGapY);
    cmAG6LockSW.ccSetLocation(cmAGWeigher, -27, -48);
    cmAG5LockSW.ccSetLocation(cmAG6LockSW, lpAGWeightButtonGapX, 0);
    cmAG4LockSW.ccSetLocation(cmAG5LockSW, lpAGWeightButtonGapX, 0);
    cmAG3LockSW.ccSetLocation(cmAG4LockSW, lpAGWeightButtonGapX, 0);
    cmAG2LockSW.ccSetLocation(cmAG3LockSW, lpAGWeightButtonGapX, 0);
    cmAG1LockSW.ccSetLocation(cmAG2LockSW, lpAGWeightButtonGapX, 0);
    cmAG6SW.ccSetLocation(cmAG6LockSW, 0,lpAGWeightButtonGapY);
    cmAG5SW.ccSetLocation(cmAG5LockSW, 0,lpAGWeightButtonGapY);
    cmAG4SW.ccSetLocation(cmAG4LockSW, 0,lpAGWeightButtonGapY);
    cmAG3SW.ccSetLocation(cmAG3LockSW, 0,lpAGWeightButtonGapY);
    cmAG2SW.ccSetLocation(cmAG2LockSW, 0,lpAGWeightButtonGapY);
    cmAG1SW.ccSetLocation(cmAG1LockSW, 0,lpAGWeightButtonGapY);
    cmAS1LockSW.ccSetLocation(cmASWeigher, 1, -48);
    cmAS1SW.ccSetLocation(cmAS1LockSW, 0,lpAGWeightButtonGapY);
    
    //-- discharge button
    int lpDischargeSwitchGap=2;
    cmFRDischargeSW=EcUnitFactory.ccCreateDischargeSW("FR", C_ID_WEIGH_FR_DISH);
    cmAGDischargeSW=EcUnitFactory.ccCreateDischargeSW("AG", C_ID_WEIGH_AG_DISH);
    cmASDischargeSW=EcUnitFactory.ccCreateDischargeSW("AS", C_ID_WEIGH_AS_DISH);
    cmFRDischargeSW.ccSetLocation(cmFRWeigher, 0, lpDischargeSwitchGap);
    cmAGDischargeSW.ccSetLocation(cmAGWeigher, 0, lpDischargeSwitchGap);
    cmASDischargeSW.ccSetLocation(cmASWeigher, 0, lpDischargeSwitchGap);
    cmFRDischargeSW.ccSetSize(cmFRWeigher.ccGetW(),0);
    cmAGDischargeSW.ccSetSize(cmAGWeigher.ccGetW(),0);
    cmASDischargeSW.ccSetSize(cmASWeigher.ccGetW(),0);
    
    //-- range
    cmFRRange=new EcShape();
    cmFRRange.ccSetBaseColor(EcFactory.C_DARK_BLUE);
    cmFRRange.ccSetLocation(cmFR2LockSW, -4, -20);
    cmFRRange.ccSetEndPoint(cmFRDischargeSW, 4, 4);
    
    cmAGRange=new EcShape();
    cmAGRange.ccSetBaseColor(EcFactory.C_DARK_BLUE);
    cmAGRange.ccSetLocation(cmAG6LockSW, -4, -20);
    cmAGRange.ccSetEndPoint(cmAG1LockSW.ccEndX()+4,cmAGDischargeSW.ccEndY()+4);
    
    cmASRange=new EcShape();
    cmASRange.ccSetBaseColor(EcFactory.C_DARK_BLUE);
    cmASRange.ccSetLocation(cmAS1LockSW, -4, -20);
    cmASRange.ccSetEndPoint(cmASDischargeSW, 4, 4);
    
  }//+++ 

  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    
    //-- weigher
    lpRes.add(cmFRWeigher);
    lpRes.add(cmAGWeigher);
    lpRes.add(cmASWeigher);
    
    //-- fr
    lpRes.add(cmFR2LockSW);
    lpRes.add(cmFR1LockSW);
    lpRes.add(cmFR2SW);
    lpRes.add(cmFR1SW);
    
    //-- ag
    lpRes.add(cmAG6LockSW);
    lpRes.add(cmAG5LockSW);
    lpRes.add(cmAG4LockSW);
    lpRes.add(cmAG3LockSW);
    lpRes.add(cmAG2LockSW);
    lpRes.add(cmAG1LockSW);
    lpRes.add(cmAG6SW);
    lpRes.add(cmAG5SW);
    lpRes.add(cmAG4SW);
    lpRes.add(cmAG3SW);
    lpRes.add(cmAG2SW);
    lpRes.add(cmAG1SW);
    
    //-- as
    lpRes.add(cmAS1LockSW);
    lpRes.add(cmAS1SW);
    
    //-- dis
    lpRes.add(cmFRDischargeSW);
    lpRes.add(cmAGDischargeSW);
    lpRes.add(cmASDischargeSW);
    
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmFRRange);
    lpRes.add(cmAGRange);
    lpRes.add(cmASRange);
    return lpRes;
  }//+++
  
}//***eof
