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
import pppicon.EcPumpIcon;
import processing.core.PApplet;

public class MainLocalCoordinator extends EcBaseCoordinator{
  
  public static final int
    
    C_ID_VMSW_HEAD=19200,
    //--
    C_ID_VEXFATSW=26510,
    C_ID_VEXFCLSW=26511,
    C_ID_VEXFOPSW=26512,
    //--
    C_ID_VBATSW=26520,
    C_ID_VBCLSW=26521,
    C_ID_VBOPSW=26522,
    //--
    C_ID_VBIGN=26529
    //--
    
  ;//...
  
  //===
  
  public final EcPumpIcon cmASSupplyPump;
  
  //===
  
  //-- model
  public final SubVFeederModelGroup cmVFeederGroup;
  public final SubAGSupplyModelGroup cmVSupplyGroup;
  public final SubFillerSupplyModelGroup cmFillerSupplyGroup;
  public final SubVCombustModelGroup cmVCombustGroup;
  public final SubMixerModelGroup cmMixerModelGroup;
  
  //-- control
  public final SubVBurnerControlGroup cmVBurnerControlGroup;
  public final SubVMortorControlGroup cmVMotorControlGroup;
  public final SubWeighControlGroup cmWeighControlGroup;
  
  public MainLocalCoordinator(PApplet pxOwner){
    
    super();
    EcFactory.ccInit(pxOwner);
    
    //-- group
    
    //-- group ** model
    cmVFeederGroup=new SubVFeederModelGroup();
    ccAddGroup(cmVFeederGroup);
    
    cmVSupplyGroup=new SubAGSupplyModelGroup();
    ccAddGroup(cmVSupplyGroup);
    
    cmFillerSupplyGroup=new SubFillerSupplyModelGroup();
    ccAddGroup(cmFillerSupplyGroup);
    
    cmVCombustGroup=new SubVCombustModelGroup();
    ccAddGroup(cmVCombustGroup);
    
    //-- group ** control
    cmVBurnerControlGroup=new SubVBurnerControlGroup();
    ccAddGroup(cmVBurnerControlGroup);
    
    cmVMotorControlGroup = new SubVMortorControlGroup();
    ccAddGroup(cmVMotorControlGroup);
    
    cmWeighControlGroup = new SubWeighControlGroup();
    ccAddGroup(cmWeighControlGroup);
    
    cmMixerModelGroup = new SubMixerModelGroup();
    ccAddGroup(cmMixerModelGroup);
    
    //-- group ** view
    
    //-- additional
    
    cmASSupplyPump=new EcPumpIcon();
    cmASSupplyPump.ccSetLocation(700, 290);
    ccAddElement(cmASSupplyPump);
    
  }//+++ 
  
}//***eof
