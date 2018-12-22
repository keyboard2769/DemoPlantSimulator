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
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcTriangleLamp;
import pppicon.EcMotorIcon;
import pppicon.EcPumpIcon;

class EcFuelUnit extends EcElement implements EiMotorized{

  final static int C_DUCT_THICK=4;

  final static int C_MV_LAMP_SIZE=16;

  EcPumpIcon cmPump;

  EcTriangleLamp cmFuelPL;

  EcTriangleLamp cmHeavyPL;

  public EcFuelUnit(String pxName, int pxX, int pxY, int pxHeadID){

    ccTakeKey(pxName);
    ccSetLocation(pxX, pxY);
    ccSetSize(60, 30);
    ccSetID(pxHeadID);

    cmPump=new EcPumpIcon();
    cmPump.ccSetLocation(pxX, pxY-cmPump.ccGetH()*3/2);
    cmPump.ccSetDirectionText('l');

    cmFuelPL=new EcTriangleLamp();
    cmFuelPL.ccSetLocation(ccEndX()-C_MV_LAMP_SIZE/2, cmY+2-C_MV_LAMP_SIZE/2);
    cmFuelPL.ccSetSize(C_MV_LAMP_SIZE, C_MV_LAMP_SIZE);
    cmFuelPL.ccSetColor(EcFactory.C_ORANGE, EcFactory.C_DIM_GRAY);
    cmFuelPL.ccSetText(" ");
    cmFuelPL.ccSetName("FO");
    cmFuelPL.ccSetNameAlign('r');

    cmHeavyPL=new EcTriangleLamp();
    cmHeavyPL.ccSetLocation(ccEndX()-C_MV_LAMP_SIZE/2,
      ccEndY()-2-C_MV_LAMP_SIZE/2);
    cmHeavyPL.ccSetSize(C_MV_LAMP_SIZE, C_MV_LAMP_SIZE);
    cmHeavyPL.ccSetColor(EcFactory.C_ORANGE, EcFactory.C_DIM_GRAY);
    cmHeavyPL.ccSetText(" ");
    cmHeavyPL.ccSetName("HO");
    cmHeavyPL.ccSetNameAlign('r');

  }//++!

  @Override
  public void ccUpdate(){

    pbOwner.fill(EcUnitFactory.C_SHAPE_COLOR_DUCT);
    pbOwner.rect(cmX, cmY, cmW, C_DUCT_THICK);
    pbOwner.rect(ccCenterX(), cmY, C_DUCT_THICK, cmH);
    pbOwner.rect(ccCenterX(), cmY+cmH-C_DUCT_THICK, cmW/2, C_DUCT_THICK);

    cmPump.ccUpdate();
    cmHeavyPL.ccUpdate();
    cmFuelPL.ccUpdate();

  }//+++

  @Override
  public void ccSetMotorStatus(char pxStatus_acnlx){
    //[TODO]::give static owner back
    EcMotorIcon.ccSetMotorStatus(cmPump, pxStatus_acnlx);
  }//+++

  public final void ccSetFuelON(boolean pxStatus){
    cmFuelPL.ccSetActivated(pxStatus);
  }//+++

  public final void ccSetHeavyON(boolean pxStatus){
    cmHeavyPL.ccSetActivated(pxStatus);
  }//+++

}//***eof
