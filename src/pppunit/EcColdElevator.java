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

import pppshape.EcElevatorShape;

public class EcColdElevator extends EcMoterizedUnit{
  
  private final EcElevatorShape cmElevatorShape;
  
  public EcColdElevator(String pxName, int pxX, int pxY, int pxHeadID){
    
    super();
    ccSetupKey(pxName);
    ccSetLocation(pxX, pxY);
    ccSetID(pxHeadID);
    
    int lpG=12;
    
    cmElevatorShape=new EcElevatorShape();
    cmElevatorShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    cmElevatorShape.ccSetSize(lpG, lpG*6);
    cmElevatorShape.ccSetCut(lpG*3/2);
    cmElevatorShape.ccSetDirection('r');
    cmElevatorShape.ccSetLocation(pxX, pxY);
    
    cmMotor.ccSetLocation(pxX+2, pxY-2);
    
  }//+++ 

  @Override public void ccUpdate(){
    cmElevatorShape.ccUpdate();
    cmMotor.ccUpdate();
  }//+++
  
}//***eof
