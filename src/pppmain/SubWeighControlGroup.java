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
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppunit.EcUnitFactory;
import pppunit.EcWeigher;

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
  
  public SubWeighControlGroup(){
    
    int lpStartX=400,lpStartY=350;
    
    //-- all weigher
    int lpWeigherGap=48;
    cmFRWeigher=new EcWeigher("FR", lpStartX, lpStartY, 1660);
    cmAGWeigher=new EcWeigher("AG",
      cmFRWeigher.ccEndX()+lpWeigherGap, cmFRWeigher.ccGetY()
    ,1670);
    cmASWeigher=new EcWeigher("AS",
      cmAGWeigher.ccEndX()+lpWeigherGap, cmAGWeigher.ccGetY()
    ,1680);
    
    //-- fr weight button 
    int lpFRWeightButtonGapX=8;
    int lpFRWeightButtonGapY=2;
    cmFR2LockSW=EcUnitFactory.ccCreateWeighLockSW("FR2",1762);
    cmFR1LockSW=EcUnitFactory.ccCreateWeighLockSW("FR1",1761);
    cmFR2LockSW.ccSetLocation(cmFRWeigher, 1, -48);
    cmFR1LockSW.ccSetLocation(cmFR2LockSW, lpFRWeightButtonGapX, 0);
    cmFR2SW=EcUnitFactory.ccCreateWeighSW("FR2",1662);
    cmFR1SW=EcUnitFactory.ccCreateWeighSW("FR1",1661);
    cmFR2SW.ccSetLocation(cmFR2LockSW, 0,lpFRWeightButtonGapY);
    cmFR1SW.ccSetLocation(cmFR1LockSW, 0,lpFRWeightButtonGapY);
    
    //-- ag weigh button
    int lpAGWeightButtonGapX=4;
    int lpAGWeightButtonGapY=2;
    cmAG6LockSW=EcUnitFactory.ccCreateWeighLockSW("AG6",1676);
    cmAG5LockSW=EcUnitFactory.ccCreateWeighLockSW("AG5",1675);
    cmAG4LockSW=EcUnitFactory.ccCreateWeighLockSW("AG4",1674);
    cmAG3LockSW=EcUnitFactory.ccCreateWeighLockSW("AG3",1673);
    cmAG2LockSW=EcUnitFactory.ccCreateWeighLockSW("AG2",1672);
    cmAG1LockSW=EcUnitFactory.ccCreateWeighLockSW("AG1",1671);
    cmAG6LockSW.ccSetLocation(cmAGWeigher, -36, -48);
    cmAG5LockSW.ccSetLocation(cmAG6LockSW, lpAGWeightButtonGapX, 0);
    cmAG4LockSW.ccSetLocation(cmAG5LockSW, lpAGWeightButtonGapX, 0);
    cmAG3LockSW.ccSetLocation(cmAG4LockSW, lpAGWeightButtonGapX, 0);
    cmAG2LockSW.ccSetLocation(cmAG3LockSW, lpAGWeightButtonGapX, 0);
    cmAG1LockSW.ccSetLocation(cmAG2LockSW, lpAGWeightButtonGapX, 0);
    cmAG6SW=EcUnitFactory.ccCreateWeighSW("AG6",1776);
    cmAG5SW=EcUnitFactory.ccCreateWeighSW("AG5",1775);
    cmAG4SW=EcUnitFactory.ccCreateWeighSW("AG4",1774);
    cmAG3SW=EcUnitFactory.ccCreateWeighSW("AG3",1773);
    cmAG2SW=EcUnitFactory.ccCreateWeighSW("AG2",1772);
    cmAG1SW=EcUnitFactory.ccCreateWeighSW("AG1",1771);
    cmAG6SW.ccSetLocation(cmAG6LockSW, 0,lpAGWeightButtonGapY);
    cmAG5SW.ccSetLocation(cmAG5LockSW, 0,lpAGWeightButtonGapY);
    cmAG4SW.ccSetLocation(cmAG4LockSW, 0,lpAGWeightButtonGapY);
    cmAG3SW.ccSetLocation(cmAG3LockSW, 0,lpAGWeightButtonGapY);
    cmAG2SW.ccSetLocation(cmAG2LockSW, 0,lpAGWeightButtonGapY);
    cmAG1SW.ccSetLocation(cmAG1LockSW, 0,lpAGWeightButtonGapY);
    
    //-- as weight button
    cmAS1LockSW=EcUnitFactory.ccCreateWeighLockSW("AS1",1681);
    cmAS1LockSW.ccSetLocation(cmASWeigher, 4, -48);
    cmAS1SW=EcUnitFactory.ccCreateWeighSW("AG1",1781);
    cmAS1SW.ccSetLocation(cmAS1LockSW, 0,lpAGWeightButtonGapY);
    
    //-- discharge button
    int lpDischargeSwitchGap=2;
    
    cmFRDischargeSW=EcUnitFactory.ccCreateDischargeSW("FR", 1923);
    cmAGDischargeSW=EcUnitFactory.ccCreateDischargeSW("AG", 1923);
    cmASDischargeSW=EcUnitFactory.ccCreateDischargeSW("AS", 1923);
    cmFRDischargeSW.ccSetLocation(cmFRWeigher, 0, lpDischargeSwitchGap);
    cmAGDischargeSW.ccSetLocation(cmAGWeigher, 0, lpDischargeSwitchGap);
    cmASDischargeSW.ccSetLocation(cmASWeigher, 0, lpDischargeSwitchGap);
    cmFRDischargeSW.ccSetSize(cmFRWeigher.ccGetW(),0);
    cmAGDischargeSW.ccSetSize(cmAGWeigher.ccGetW(),0);
    cmFRDischargeSW.ccSetSize(cmASWeigher.ccGetW(),0);
    
    
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
    return lpRes;
  }//+++
  
}//***eof
