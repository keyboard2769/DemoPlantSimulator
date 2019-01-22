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
import pppshape.EcElevatorShape;

public class EcColdElevator extends EcElement implements EiMotorized{
  
  private final EcElevatorShape cmElevatorShape;
  
  public EcColdElevator(String pxName, int pxHeadID){
    
    super();
    ccSetupKey(pxName);
    ccSetID(pxHeadID);
    
    int lpG=12;
    
    cmElevatorShape=new EcElevatorShape();
    cmElevatorShape.ccSetBaseColor(EcUnitFactory.C_C_METAL);
    cmElevatorShape.ccSetSize(lpG, lpG*6);
    cmElevatorShape.ccSetCut(lpG*3/2);
    cmElevatorShape.ccSetDirection('r');
    
  }//+++ 
  
  public final void ccSetupLocation(int pxX, int pxY){
    ccSetLocation(pxX, pxY);
    cmElevatorShape.ccSetLocation(pxX, pxY);
    ccSetEndPoint(cmElevatorShape, 0, 0);
  }//+++

  //===
  
  @Override public void ccUpdate(){
    cmElevatorShape.ccUpdate();
  }//+++

  @Override public void ccSetMotorON(boolean pxStatus){
    cmElevatorShape.ccSetBaseColor(pxStatus?
      EcUnitFactory.C_C_POWERED:
      EcUnitFactory.C_C_METAL
    );
  }//+++
  
}//***eof
