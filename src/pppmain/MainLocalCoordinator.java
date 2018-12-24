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

package pppmain;

import kosui.ppplocalui.EcBaseCoordinator;
import kosui.ppplocalui.EcButton;
import pppicon.EcPumpIcon;
import pppunit.EcMixer;
import pppunit.EcUnitFactory;
import pppunit.EcWeigher;

public class MainLocalCoordinator extends EcBaseCoordinator{
  
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
    cmMixDischargeSW
  ;//...
  
  public final EcPumpIcon cmASSupplyPump,cmASSprayPump;
  
  public final EcMixer cmMixer;
  public final EcWeigher cmFRWeigher,cmAGWeigher,cmASWeigher;
  
  public final SubVFeederGroup cmVFeederGroup;
  public final SubVSupplyGroup cmVSupplyGroup;
  public final SubFillerSupplyGroup cmFillerSupplyGroup;
  public final SubVCombustGroup cmVCombustGroup;  
  
  public MainLocalCoordinator(){
    
    super();
    
    //-- weigher
    int lpWeigherGap=48;
    int lpMixerGap=24;
    cmFRWeigher=new EcWeigher("FR", 400, 350, 1660);
    cmAGWeigher=new EcWeigher("AG",
      cmFRWeigher.ccEndX()+lpWeigherGap, cmFRWeigher.ccGetY()
    ,1670);
    cmASWeigher=new EcWeigher("AS",
      cmAGWeigher.ccEndX()+lpWeigherGap, cmAGWeigher.ccGetY()
    ,1680);
    ccAddElement(cmFRWeigher);
    ccAddElement(cmAGWeigher);
    ccAddElement(cmASWeigher);
    
    //-- mixer
    cmMixer=new EcMixer("mixer", 
      cmAGWeigher.ccGetX()-12, cmAGWeigher.ccEndY()+lpMixerGap
    ,1650);
    ccAddElement(cmMixer);
    
    //-- addtional component
    
    //-- addtional component ** as pipe
    //[TODO]::fill this!!
    
    //-- addtional component ** as pump
    
    cmASSupplyPump=new EcPumpIcon();
    cmASSupplyPump.ccSetLocation(cmASWeigher, 40, -32);
    ccAddElement(cmASSupplyPump);
    
    cmASSprayPump=new EcPumpIcon();
    cmASSprayPump.ccSetLocation(cmMixer, 145, 50);
    ccAddElement(cmASSprayPump);
    
    //-- group
    cmVFeederGroup=new SubVFeederGroup();
    ccAddGroup(cmVFeederGroup);
    
    cmVSupplyGroup=new SubVSupplyGroup();
    ccAddGroup(cmVSupplyGroup);
    
    cmFillerSupplyGroup=new SubFillerSupplyGroup();
    ccAddGroup(cmFillerSupplyGroup);
    
    cmVCombustGroup=new SubVCombustGroup();
    ccAddGroup(cmVCombustGroup);
    
    //-- button
    //-- button ** thosse always on
    //-- button ** thosse always on ** weigh system
    //-- button ** thosse always on ** weigh system ** FR
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
    ccAddElement(cmFR2LockSW);
    ccAddElement(cmFR1LockSW);
    ccAddElement(cmFR2SW);
    ccAddElement(cmFR1SW);
    
    //-- button ** thosse always on ** weigh system ** AG
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
    ccAddElement(cmAG6LockSW);
    ccAddElement(cmAG5LockSW);
    ccAddElement(cmAG4LockSW);
    ccAddElement(cmAG3LockSW);
    ccAddElement(cmAG2LockSW);
    ccAddElement(cmAG1LockSW);
    ccAddElement(cmAG6SW);
    ccAddElement(cmAG5SW);
    ccAddElement(cmAG4SW);
    ccAddElement(cmAG3SW);
    ccAddElement(cmAG2SW);
    ccAddElement(cmAG1SW);
    
    //-- button ** thosse always on ** weigh system ** AS
    cmAS1LockSW=EcUnitFactory.ccCreateWeighLockSW("AS1",1681);
    cmAS1LockSW.ccSetLocation(cmASWeigher, 4, -48);
    cmAS1SW=EcUnitFactory.ccCreateWeighSW("AG1",1781);
    cmAS1SW.ccSetLocation(cmAS1LockSW, 0,lpAGWeightButtonGapY);
    ccAddElement(cmAS1LockSW);
    ccAddElement(cmAS1SW);
    
    //-- button ** thosse always on ** mixer 
    cmMixDischargeSW=EcUnitFactory.ccCreateDischargeSW("MX", 1999);
    cmMixDischargeSW.ccSetLocation(cmMixer, 0, 2);
    cmMixDischargeSW.ccSetSize(cmMixer.ccGetW(),-1);
    ccAddElement(cmMixDischargeSW);
    
    
    
  }//+++ 
  
}//***eof
