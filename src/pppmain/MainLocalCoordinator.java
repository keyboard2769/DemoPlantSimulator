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
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.VcConsole;
import pppicon.EcClockButton;
import pppicon.EcErrorSlotBar;

public class MainLocalCoordinator extends EcBaseCoordinator{
  
  private static MainLocalCoordinator self;
  public static MainLocalCoordinator ccGetReference(){
    if(self==null){self=new MainLocalCoordinator();}
    return self;
  }//++!
  
  //===
  
  public static final int
    C_ID_BOOK_RECIPE_HEAD = 70010,
    C_ID_BOOK_KG_HEAD     = 70020,
    C_ID_BOOK_BATCH_HEAD  = 70030,
    //--
    C_ID_MIXER_GATE_AUTO = 60601,
    C_ID_MIXER_GATE_HOLD = 60602,
    C_ID_MIXER_GATE_OPEN = 60603,
    C_ID_WEIGH_MANN    = 60611,
    C_ID_WEIGH_AUTO    = 60612,
    C_ID_WEIGH_RUN     = 60613,
    //--
    C_ID_BELL  = 60621,
    C_ID_SIREN = 60622,
    C_ID_OFD   = 60625,
    C_ID_OSD   = 60626,
    //--
    C_ID_ZERO_APP = 50080,
    C_ID_ZERO_FR  = 50082,
    C_ID_ZERO_AG  = 50081,
    C_ID_ZERO_AS  = 50083,
    //--
    C_ID_WEIGH_FR_DISH = 50120,
    C_ID_WEIGH_AG_DISH = 50110,
    C_ID_WEIGH_AS_DISH = 50130,
    C_ID_WEIGH_FR_LOCKH = 50170,
    C_ID_WEIGH_AG_LOCKH = 50160,
    C_ID_WEIGH_AS_LOCKH = 50180,
    //--
    C_ID_VF_HEAD   = 61600,
    C_ID_VMSW_HEAD = 19200,
    C_ID_VD_HEAD   = 60300,
    C_ID_VB_HEAD   = 62800,
    //--
    C_ID_VEXFATSW=26510,
    C_ID_VEXFCLSW=26511,
    C_ID_VEXFOPSW=26512,
    //--
    C_ID_VBATSW=26520,
    C_ID_VBCLSW=26521,
    C_ID_VBOPSW=26522,
    //--
    C_ID_VBIGN=26529,
    //--
    C_ID_SYSTEM=9999
  ;//...
  
  //===
  
  //-- system
  public final EcClockButton cmSystemButton;
  public final EcErrorSlotBar cmSystemSlotBar;
  
  //-- model
  public final SubVFeederModelGroup cmVFeederModelGroup;
  public final SubAGSupplyModelGroup cmAGSupplyModelGroup;
  public final SubFillerSupplyModelGroup cmFillerSupplyGroup;
  public final SubASSupplyModelGroup cmASSSupplyModelGroup;
  public final SubMixerModelGroup cmMixerModelGroup;
  
  //-- mediate
  public final SubVBurnerControlGroup cmVBurnerControlGroup;
  public final SubWeighControlGroup cmWeighControlGroup;
  
  //-- control
  public final SubVMortorControlGroup cmVMotorControlGroup;
  public final SubZeroAdjustControlGroup cmZeroAdjustControlGroup;
  public final SubMixerControlGourp cmMixerControlGourp;
  public final SubBookingControlGroup cmBookingControlGroup;
  
  private MainLocalCoordinator(){
    
    super();
    EcFactory.ccInit(MainSketch.ccGetReference());
    VcConsole.ccSetOperation(MainKeyInputManager.ccGetReference());
    VcConsole.ccSetIsMessageBarVisible();
    
    //-- group
    
    //-- group ** model
    cmVFeederModelGroup=new SubVFeederModelGroup();
    ccAddGroup(cmVFeederModelGroup);
    
    cmAGSupplyModelGroup = SubAGSupplyModelGroup.ccGetReference();
    ccAddGroup(cmAGSupplyModelGroup);
    
    cmFillerSupplyGroup =  SubFillerSupplyModelGroup.ccGetReference();
    ccAddGroup(cmFillerSupplyGroup);
    
    cmASSSupplyModelGroup = SubASSupplyModelGroup.ccGetReference();
    ccAddGroup(cmASSSupplyModelGroup);
    
    cmMixerModelGroup = SubMixerModelGroup.ccGetReference();
    ccAddGroup(cmMixerModelGroup);
    
    //-- group ** mediate
    cmVBurnerControlGroup = SubVBurnerControlGroup.ccGetReference();
    ccAddGroup(cmVBurnerControlGroup);
    
    cmWeighControlGroup = SubWeighControlGroup.ccGetReference();
    ccAddGroup(cmWeighControlGroup);
    
    //-- group ** control
    cmVMotorControlGroup = SubVMortorControlGroup.ccGetReference();
    ccAddGroup(cmVMotorControlGroup);
    
    cmMixerControlGourp = SubMixerControlGourp.ccGetReference();
    ccAddGroup(cmMixerControlGourp);
    
    cmBookingControlGroup = SubBookingControlGroup.ccGetReference();
    ccAddGroup(cmBookingControlGroup);
    
    cmZeroAdjustControlGroup = SubZeroAdjustControlGroup.ccGetReference();
    ccAddGroup(cmZeroAdjustControlGroup);
    
    //-- relocate
    //-- relocate ** anch
    cmWeighControlGroup.ccSetupLocation(50, 220);
    
    int lpWeigherLX=cmWeighControlGroup.ccGetFRBound().ccGetX();
    int lpBurnerLX=470;
    
    int lpWeigherLY=cmWeighControlGroup.ccGetFRBound().ccGetY();
    int lpFeederLY=25;
    int lpSupplyLY=lpWeigherLY-60;
    
    int lpSupplySmallW= cmWeighControlGroup.ccGetFRBound().ccGetW();
    int lpSupplyBigW = cmWeighControlGroup.ccGetAGBound().ccGetW();
    int lpSupplyH = 50;
    
    //-- relocate ** model
    
    //-- relocate ** model ** weighing
    EcRect lpDummyBound=new EcRect();
    lpDummyBound.ccSetBound(cmWeighControlGroup.ccGetASBound().ccGetX(),
      lpSupplyLY, lpSupplySmallW, lpSupplyH);
    cmASSSupplyModelGroup.ccSetupLocation(lpDummyBound);
    
    lpDummyBound.ccSetLocation(lpWeigherLX, lpSupplyLY);
    cmFillerSupplyGroup.ccSetupLocation(lpDummyBound);
    
    lpDummyBound.ccSetBound(cmWeighControlGroup.ccGetAGBound().ccGetX(),
      lpSupplyLY,lpSupplyBigW,lpSupplyH);
    cmAGSupplyModelGroup.ccSetupLocation(lpDummyBound);
    
    //-- relocate ** model ** dicharge
    lpDummyBound.ccSetSize(cmWeighControlGroup.ccGetAGBound());
    lpDummyBound.ccSetLocation(
      cmWeighControlGroup.ccGetAGBound().ccGetX(), 
      cmWeighControlGroup.ccGetAGBound().ccEndY()+8
    ); cmMixerModelGroup.ccSetupLocation(lpDummyBound);
    
    //-- relocate ** model ** burning
    cmVBurnerControlGroup.ccSetupLocation
      (lpBurnerLX, cmWeighControlGroup.ccGetFRBound().ccGetY());
    
    lpDummyBound.ccSetBound(
      lpBurnerLX,lpFeederLY,
      cmVBurnerControlGroup.ccGetPaneBound().ccGetW(), 
      cmVBurnerControlGroup.ccGetPaneBound().ccGetY()-lpFeederLY-4
    );cmVFeederModelGroup.ccSetupLocation(lpDummyBound);
    
    //-- relocate ** control
    int lpWidth=MainSketch.ccGetReference().width;
    int lpHeight=MainSketch.ccGetReference().height;
    int lpOffsetX=4;
    int lpOffsetY=20;
    int lpControlSubH=160;
    int lpControlPrimW=270;
    int lpControlPrimH=190;
    int lpControlSubY=lpHeight-lpOffsetY-lpControlSubH;
    
    lpDummyBound.ccSetBound(
      lpOffsetX,lpControlSubY,
      270,lpControlSubH
    );cmBookingControlGroup.ccSetupLocation(lpDummyBound);
    
    lpDummyBound.ccSetBound(
      cmBookingControlGroup.ccGetBound().ccEndX()+lpOffsetX,lpControlSubY,
      80,lpControlSubH
    );cmMixerControlGourp.ccSetupLocation(lpDummyBound);
    
    lpDummyBound.ccSetBound(
      lpWidth-lpControlPrimW-lpOffsetX, lpHeight-lpControlPrimH-lpOffsetY,
      lpControlPrimW, lpControlPrimH
    );cmVMotorControlGroup.ccSetupLocation(lpDummyBound);
    
    int lpSplitH=(lpControlSubH-lpOffsetX)/2;
    lpDummyBound.ccSetBound(
      cmMixerControlGourp.ccGetBound().ccEndX()+lpOffsetX,
      lpControlSubY+lpSplitH+lpOffsetX,
      cmVMotorControlGroup.ccGetBound().ccGetX()
        -cmMixerControlGourp.ccGetBound().ccEndX()-lpOffsetX*2,
      lpSplitH
    );cmZeroAdjustControlGroup.ccSetupLocation(lpDummyBound);
    
    //-- system component
    cmSystemButton=new EcClockButton();
    cmSystemButton.ccSetLocation(0, 0);
    cmSystemButton.ccSetID(C_ID_SYSTEM);
    ccAddElement(cmSystemButton);
    //--
    cmSystemSlotBar=new EcErrorSlotBar();
    cmSystemSlotBar.ccSetSize(cmSystemButton);
    cmSystemSlotBar.ccSetSize(
      MainSketch.ccGetReference().width-cmSystemButton.ccGetW(),
      0
    );
    cmSystemSlotBar.ccSetLocation(cmSystemButton, 2, 0);
    ccAddShape(cmSystemSlotBar);
    
    //-- regist input
    for(int i=0;i<MainOperationModel.C_MAX_BOOK_CAPABILITY;i++){
      ccAddInputtable(cmBookingControlGroup.cmDesRecipeBox[i]);
      ccAddInputtable(cmBookingControlGroup.cmDesKGBox[i]);
      ccAddInputtable(cmBookingControlGroup.cmDesBatchBox[i]);
    }//..~
    
    //-- regist tips
    String lpWheelInfo="M-Wheel\nto ADJ!";
    for(int i=1;i<=6;i++){
      ccAddTip(C_ID_VF_HEAD+i, lpWheelInfo);
    }//..~
    ccAddTip(C_ID_VB_HEAD, lpWheelInfo);
    
  }//+++ 
  
}//***eof
