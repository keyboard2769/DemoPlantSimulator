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

import pppunit.*;
import kosui.ppplocalui.EcBaseCoordinator;

public class MainLocalCoordinator extends EcBaseCoordinator{
  
  public final EcMixer cmMixer;
  
  public final SubVFeederGroup cmVFeederGroup;
  public final SubVSupplyGroup cmVSupplyGroup;
  public final SubFillerSupplyGroup cmFillerSupplyGroup;
  public final SubVCombustGroup cmVCombustGroup;  
  
  public MainLocalCoordinator(){
    
    super();
    
    cmMixer=new EcMixer("mixer", 100, 390, 1650);
    ccAddElement(cmMixer);
    
    cmVFeederGroup=new SubVFeederGroup();
    ccAddGroup(cmVFeederGroup);
    
    cmVSupplyGroup=new SubVSupplyGroup();
    ccAddGroup(cmVSupplyGroup);
    
    cmFillerSupplyGroup=new SubFillerSupplyGroup();
    ccAddGroup(cmFillerSupplyGroup);
    
    cmVCombustGroup=new SubVCombustGroup();
    ccAddGroup(cmVCombustGroup);
    
  }//+++ 
  
}//***eof
