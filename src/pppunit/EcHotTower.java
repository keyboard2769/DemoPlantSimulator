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
import kosui.ppplocalui.EcLamp;
import kosui.ppplocalui.EcTriangleLamp;
import pppicon.EcMotorIcon;
import pppshape.EcBlowerShape;
import pppshape.EcElevatorShape;

public class EcHotTower extends EcElement implements EiMultipleMoterized{

  public final int//[TODO]::make static
    C_I_SCREEN=0,
    C_I_HOTELEVATOR=1,
    C_I_BLOWER=2;//...

  private final int //[TODO]::make static
    C_TOWER_W=40,
    C_FLOOR_H=20,
    C_FLOOR_GAP=4,
    C_DUCT_THICK=8,
    C_SCREEN_CUT=16;//...

  private final EcElevatorShape cmHotElevatorShape;

  private final EcBlowerShape cmBlowerShape;

  private final EcLamp cmOverFlowLV;

  private final EcLamp cmOverSizeLV;

  private final EcTriangleLamp cmOverFlowGate;

  private final EcTriangleLamp cmOverSizeGate;

  private final EcMotorIcon cmScreenMotor;

  private final EcMotorIcon cmElevatorMotor;

  private final EcMotorIcon cmBlowerMotor;

  public EcHotTower(String pxName, int pxX, int pxY, int pxHeadID){

    super();
    ccTakeKey(pxName);
    ccSetLocation(pxX, pxY);
    ccSetSize(C_TOWER_W, C_FLOOR_H*4+C_FLOOR_GAP*2);

    int lpCut=16;
    cmHotElevatorShape=new EcElevatorShape();
    cmHotElevatorShape.ccSetCut(lpCut);
    cmHotElevatorShape.ccSetLocation(
      ccEndX()+C_DUCT_THICK/2+C_FLOOR_GAP*2,
      cmY-C_FLOOR_H-lpCut-C_FLOOR_GAP*2
    );
    cmHotElevatorShape.ccSetSize(12, cmH+lpCut+C_FLOOR_H+C_FLOOR_GAP*2);
    cmHotElevatorShape.ccSetBaseColor(EcFactory.C_GRAY);

    cmBlowerShape=new EcBlowerShape();
    cmBlowerShape.ccSetLocation(ccCenterX()+C_FLOOR_H-4,
      cmY+C_FLOOR_H+C_FLOOR_GAP*2);
    cmBlowerShape.ccSetSize(cmH/6, C_FLOOR_H/3);
    cmBlowerShape.ccSetBaseColor(EcFactory.C_WHITE);
    cmBlowerShape.ccSetDirection('r');

    cmOverSizeLV=new EcLamp();
    cmOverSizeLV.ccSetLocation(cmX-C_FLOOR_GAP-C_DUCT_THICK-1, cmY-6);
    cmOverSizeLV.ccSetSize(12, 12);
    cmOverSizeLV.ccSetText(" ");
    cmOverSizeLV.ccSetColor(EcFactory.C_GREEN);

    cmOverFlowLV=new EcLamp();
    cmOverFlowLV.ccSetLocation(ccEndX()+C_FLOOR_GAP-5, cmY-6);
    cmOverFlowLV.ccSetSize(12, 12);
    cmOverFlowLV.ccSetText(" ");
    cmOverFlowLV.ccSetColor(EcFactory.C_GREEN);

    cmOverSizeGate=new EcTriangleLamp();
    cmOverSizeGate.ccSetLocation(cmOverSizeLV, 1, cmH);
    cmOverSizeGate.ccSetSize(12, 12);
    cmOverSizeGate.ccSetText(" ");
    cmOverSizeGate.ccSetDirection('d');
    cmOverSizeGate.ccSetColor(EcFactory.C_ORANGE);

    cmOverFlowGate=new EcTriangleLamp();
    cmOverFlowGate.ccSetLocation(cmOverFlowLV, 1, cmH);
    cmOverFlowGate.ccSetSize(12, 12);
    cmOverFlowGate.ccSetText(" ");
    cmOverFlowGate.ccSetDirection('d');
    cmOverFlowGate.ccSetColor(EcFactory.C_ORANGE);

    cmElevatorMotor=new EcMotorIcon();
    cmElevatorMotor.ccSetLocation(cmHotElevatorShape, 2, -4);

    cmScreenMotor=new EcMotorIcon();
    cmScreenMotor.ccSetLocation(cmX+C_SCREEN_CUT, cmY-C_FLOOR_GAP-C_FLOOR_H-8);

    cmBlowerMotor=new EcMotorIcon();
    cmBlowerMotor.ccSetLocation(cmX+C_TOWER_W/2+4, cmY+C_FLOOR_GAP+C_FLOOR_H-4);

  }//++!

  @Override
  public void ccUpdate(){

    //-- draw tower
    pbOwner.fill(EcFactory.C_LIT_GRAY);
    pbOwner.rect(cmX, cmY, cmW, C_FLOOR_H);
    pbOwner.rect(cmX, cmY+C_FLOOR_H+C_FLOOR_GAP, cmW, C_FLOOR_H);
    pbOwner.rect(cmX, cmY+2*(C_FLOOR_H+C_FLOOR_GAP), cmW, C_FLOOR_H);
    pbOwner.rect(
      cmX, cmY+3*C_FLOOR_H+2*C_FLOOR_GAP,
      C_DUCT_THICK, C_FLOOR_H
    );
    pbOwner.rect(
      ccEndX()-C_DUCT_THICK, cmY+3*C_FLOOR_H+2*C_FLOOR_GAP,
      C_DUCT_THICK, C_FLOOR_H
    );

    //-- draw screen
    pbOwner.fill(EcFactory.C_GRAY);
    pbOwner.quad(
      cmX+C_SCREEN_CUT, cmY-C_FLOOR_GAP-C_FLOOR_H,
      ccEndX(), cmY-C_FLOOR_GAP-C_FLOOR_H,
      ccEndX(), cmY-C_FLOOR_GAP,
      cmX, cmY-C_FLOOR_GAP
    );
    pbOwner.fill(EcFactory.C_LIT_GRAY);
    pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-2, -3*C_TOWER_W/4, -2);
    pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-6, -2*C_TOWER_W/4, -2);
    pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-10, -2*C_TOWER_W/4, -2);
    pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-14, -2*C_TOWER_W/4, -2);

    //-- draw extract duct
    pbOwner.fill(EcFactory.C_GRAY);
    pbOwner.rect(
      cmX-C_FLOOR_GAP, cmY,
      -1*C_DUCT_THICK/2, cmH-C_FLOOR_GAP*2
    );
    pbOwner.rect(
      ccEndX()+C_FLOOR_GAP, cmY,
      C_DUCT_THICK/2, cmH-C_FLOOR_GAP*2
    );

    //-- draw elevator
    cmHotElevatorShape.ccUpdate();

    //-- draw blower
    cmBlowerShape.ccUpdate();

    //-- update element
    cmOverSizeLV.ccUpdate();
    cmOverFlowLV.ccUpdate();
    cmOverSizeGate.ccUpdate();
    cmOverFlowGate.ccUpdate();
    cmElevatorMotor.ccUpdate();
    cmScreenMotor.ccUpdate();
    cmBlowerMotor.ccUpdate();

  }//+++

  @Override
  public void ccSetMotorStatus(int pxIndex, char pxStatus_acnlx){
    switch(pxIndex){
      case C_I_SCREEN:
        EcMotorIcon.ccSetMotorStatus(cmScreenMotor, pxStatus_acnlx);
        break;
      case C_I_HOTELEVATOR:
        EcMotorIcon.ccSetMotorStatus(cmElevatorMotor, pxStatus_acnlx);
        break;
      case C_I_BLOWER:
        EcMotorIcon.ccSetMotorStatus(cmBlowerMotor, pxStatus_acnlx);
        break;
      default: break;
    }//..?
  }//+++

  public final void ccSetIsOverFlowFull(boolean pxStatus){
    cmOverFlowLV.ccSetActivated(pxStatus);
  }//+++

  public final void ccSetIsOverSizeFull(boolean pxStatus){
    cmOverSizeLV.ccSetActivated(pxStatus);
  }//+++

  public final void ccSetIsOverFlowGateOpening(boolean pxStatus){
    cmOverFlowGate.ccSetActivated(pxStatus);
  }//+++

  public final void ccSetIsOverSizeGateOpening(boolean pxStatus){
    cmOverSizeGate.ccSetActivated(pxStatus);
  }//+++

}//***eof
