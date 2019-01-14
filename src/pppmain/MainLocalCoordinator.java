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
    C_ID_WEIGH_FR_DISH = 50120,
    C_ID_WEIGH_AG_DISH = 50110,
    C_ID_WEIGH_AS_DISH = 50130,
    C_ID_WEIGH_FR_LOCKH = 50170,
    C_ID_WEIGH_AG_LOCKH = 50160,
    C_ID_WEIGH_AS_LOCKH = 50180,
    //--
    C_ID_VMSW_HEAD=19200,
    C_ID_VD_MGH   =60300,
    C_ID_VB_MGH   =62800,
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
  public final SubVFeederModelGroup cmVFeederGroup;
  public final SubAGSupplyModelGroup cmVSupplyGroup;
  public final SubFillerSupplyModelGroup cmFillerSupplyGroup;
  public final SubASSupplyModelGroup cmASSSupplyModelGroup;
  public final SubVCombustModelGroup cmVCombustGroup;
  public final SubMixerModelGroup cmMixerModelGroup;
  
  //-- control
  public final SubVBurnerControlGroup cmVBurnerControlGroup;
  public final SubVMortorControlGroup cmVMotorControlGroup;
  public final SubWeighControlGroup cmWeighControlGroup;
  public final SubMixerControlGourp cmMixerControlGourp;
  public final SubBookingControlGroup cmBookingControlGroup;
  
  private MainLocalCoordinator(){
    
    super();
    EcFactory.ccInit(MainSketch.ccGetReference());
    VcConsole.ccSetOperation(MainKeyInputManager.ccGetReference());
    VcConsole.ccSetIsMessageBarVisible();
    
    //-- group
    
    //-- group ** model
    cmVFeederGroup=new SubVFeederModelGroup();
    ccAddGroup(cmVFeederGroup);
    
    cmVSupplyGroup=new SubAGSupplyModelGroup();
    ccAddGroup(cmVSupplyGroup);
    
    cmFillerSupplyGroup=new SubFillerSupplyModelGroup();
    ccAddGroup(cmFillerSupplyGroup);
    
    cmASSSupplyModelGroup=new SubASSupplyModelGroup();
    ccAddGroup(cmASSSupplyModelGroup);
    
    cmVCombustGroup=new SubVCombustModelGroup();
    ccAddGroup(cmVCombustGroup);
    
    cmMixerModelGroup = new SubMixerModelGroup();
    ccAddGroup(cmMixerModelGroup);
    
    //-- group ** control
    cmVBurnerControlGroup=new SubVBurnerControlGroup();
    ccAddGroup(cmVBurnerControlGroup);
    
    cmVMotorControlGroup = new SubVMortorControlGroup();
    ccAddGroup(cmVMotorControlGroup);
    
    cmWeighControlGroup = new SubWeighControlGroup();
    ccAddGroup(cmWeighControlGroup);
    
    cmMixerControlGourp = new SubMixerControlGourp();
    ccAddGroup(cmMixerControlGourp);
    
    cmBookingControlGroup = new SubBookingControlGroup();
    ccAddGroup(cmBookingControlGroup);
    
    //-- direct
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
    
    //-- input
    ccAddInputtable(cmBookingControlGroup.cmDesRecipeBox[0]);
    ccAddInputtable(cmBookingControlGroup.cmDesKGBox[0]);
    ccAddInputtable(cmBookingControlGroup.cmDesBatchBox[0]);
    //--
    ccAddInputtable(cmBookingControlGroup.cmDesRecipeBox[1]);
    ccAddInputtable(cmBookingControlGroup.cmDesKGBox[1]);
    ccAddInputtable(cmBookingControlGroup.cmDesBatchBox[1]);
    //--
    ccAddInputtable(cmBookingControlGroup.cmDesRecipeBox[2]);
    ccAddInputtable(cmBookingControlGroup.cmDesKGBox[2]);
    ccAddInputtable(cmBookingControlGroup.cmDesBatchBox[2]);
    
  }//+++ 
  
}//***eof
