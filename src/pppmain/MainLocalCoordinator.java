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
import pppshape.EcDuctShape;
import pppunit.EcMixer;
import pppunit.EcUnitFactory;
import pppunit.EcWeigher;
import static processing.core.PApplet.nf;

public class MainLocalCoordinator extends EcBaseCoordinator{
  
  private static final int
    //--
    C_ID_VMSW_HEAD=19200,
    //--
    C_P_WEIGHSYS_X=400,C_P_WEIGHSYS_Y=350,
    C_P_VMSW_X= 50,C_P_VMSW_Y=380,
    //--
    C_C_DUMMY=65535
  ;//...
  
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
    cmFRDischargeSW,cmAGDischargeSW,cmASDischargeSW,
    //--
    cmMixerDischargeSW
  ;//...
  
  public final EcButton[] cmMotorSW;
  
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
    int lpMixerGap=48;
    cmFRWeigher=new EcWeigher("FR", C_P_WEIGHSYS_X, C_P_WEIGHSYS_Y, 1660);
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
    EcDuctShape lpASSprayPipe = new EcDuctShape();
    lpASSprayPipe.ccSetLocation(cmMixer, 60, -12);
    lpASSprayPipe.ccSetSize(80, 80);
    lpASSprayPipe.ccSetDirection('b');
    lpASSprayPipe.ccSetCut(20);
    lpASSprayPipe.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_DUCT);
    ccAddShape(lpASSprayPipe);
    
    //[TODO]::fill this!!
    
    //-- addtional component ** as pump
    
    cmASSupplyPump=new EcPumpIcon();
    cmASSupplyPump.ccSetLocation(cmASWeigher, 40, -32);
    ccAddElement(cmASSupplyPump);
    
    cmASSprayPump=new EcPumpIcon();
    cmASSprayPump.ccSetLocation(lpASSprayPipe, 1, 0);
    cmASSprayPump.ccShiftLocation(-4, 60);
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
    //-- button ** those always on
    //-- button ** those always on ** motor switch
    int lpVMSwitchW=50;
    int lpVMSwitchH=50;
    int lpVMSwitchGap=2;
    cmMotorSW=new EcButton[15];
    for(int i=0;i<cmMotorSW.length;i++){
      cmMotorSW[i]=new EcButton();
      cmMotorSW[i].ccTakeKey("VM"+nf(i,2));
      cmMotorSW[i].ccSetID(C_ID_VMSW_HEAD+i);
      cmMotorSW[i].ccSetSize(lpVMSwitchW, lpVMSwitchH);
    }//..~
    for(int i=0;i<cmMotorSW.length;i++){
      cmMotorSW[i].ccSetLocation(
        C_P_VMSW_X+(i%5)*(lpVMSwitchW+lpVMSwitchGap), 
        C_P_VMSW_Y+(i%3)*(lpVMSwitchW+lpVMSwitchGap)
      );
      ccAddElement(cmMotorSW[i]);
    }//..~
    
    //-- button ** those always on ** weigh system
    //-- button ** those always on ** weigh system ** FR
    //<editor-fold defaultstate="collapsed" desc=" ** FR">
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
    //</editor-fold>
    
    //-- button ** those always on ** weigh system ** AG
    //<editor-fold defaultstate="collapsed" desc=" ** AG">
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
    //</editor-fold>
    
    //-- button ** those always on ** weigh system ** AS
    //<editor-fold defaultstate="collapsed" desc=" ** AS">
    cmAS1LockSW=EcUnitFactory.ccCreateWeighLockSW("AS1",1681);
    cmAS1LockSW.ccSetLocation(cmASWeigher, 4, -48);
    cmAS1SW=EcUnitFactory.ccCreateWeighSW("AG1",1781);
    cmAS1SW.ccSetLocation(cmAS1LockSW, 0,lpAGWeightButtonGapY);
    ccAddElement(cmAS1LockSW);
    ccAddElement(cmAS1SW);
    //</editor-fold>
    
    //-- button ** those always on ** setting
    int lpDischargeSwitchGap=2;
    
    //-- button ** those always on ** weigher discharge button
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    cmFRDischargeSW=EcUnitFactory.ccCreateDischargeSW("FR", 1923);
    cmAGDischargeSW=EcUnitFactory.ccCreateDischargeSW("AG", 1923);
    cmASDischargeSW=EcUnitFactory.ccCreateDischargeSW("AS", 1923);
    cmFRDischargeSW.ccSetLocation(cmFRWeigher, 0, lpDischargeSwitchGap);
    cmAGDischargeSW.ccSetLocation(cmAGWeigher, 0, lpDischargeSwitchGap);
    cmASDischargeSW.ccSetLocation(cmASWeigher, 0, lpDischargeSwitchGap);
    cmFRDischargeSW.ccSetSize(cmFRWeigher.ccGetW(),0);
    cmAGDischargeSW.ccSetSize(cmAGWeigher.ccGetW(),0);
    cmFRDischargeSW.ccSetSize(cmASWeigher.ccGetW(),0);
    ccAddElement(cmFRDischargeSW);
    ccAddElement(cmAGDischargeSW);
    ccAddElement(cmASDischargeSW);
    //</editor-fold>
    
    //-- button ** those always on ** mixer 
    //<editor-fold defaultstate="collapsed" desc="%folded code%">
    cmMixerDischargeSW=EcUnitFactory.ccCreateDischargeSW("MX", 1999);
    cmMixerDischargeSW.ccSetLocation(cmMixer, 0, lpDischargeSwitchGap);
    cmMixerDischargeSW.ccSetSize(cmMixer.ccGetW(),0);
    ccAddElement(cmMixerDischargeSW);
    //</editor-fold>
    
    
    
  }//+++ 
  
}//***eof
