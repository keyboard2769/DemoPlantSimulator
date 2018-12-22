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
import pppshape.EcBelconShape;

public class EcGasUnit extends EcElement{

  final int C_DUCT_THICK=4;//[TODO]make const

  private final int //[TODO]make const
    C_LED_W=8,
    C_LED_H=4;

  private boolean cmIsLeakingHigh, cmIsLeakingLow;//...

  private final EcBelconShape cmGasUnitShape;

  private final EcLamp cmGasPressureHI;

  private final EcLamp cmGasPressureLO;

  private final EcTriangleLamp cmGasValveA;

  private final EcTriangleLamp cmGasValveB;

  public EcGasUnit(String pxName, int pxX, int pxY, int pxHeadID){

    super();
    ccTakeKey(pxName);
    ccSetLocation(pxX, pxY);
    ccSetSize(65, 20);
    ccSetID(pxHeadID);

    cmIsLeakingHigh=false;
    cmIsLeakingLow=false;

    cmGasUnitShape=new EcBelconShape();
    cmGasUnitShape.ccSetLocation(cmX+16, ccEndY()-6);
    cmGasUnitShape.ccSetSize(32, 16);
    cmGasUnitShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);

    cmGasPressureHI=new EcLamp();
    cmGasPressureHI.ccSetLocation(cmX-5, cmY-6);
    cmGasPressureHI.ccSetSize(12, 12);
    cmGasPressureHI.ccSetText(" ");
    cmGasPressureHI.ccSetName("Hi");
    cmGasPressureHI.ccSetNameAlign('a');
    cmGasPressureHI.ccSetColor(EcFactory.C_PURPLE);

    cmGasPressureLO=new EcLamp();
    cmGasPressureLO.ccSetLocation(ccEndX()-6, ccEndY()-5);
    cmGasPressureLO.ccSetSize(12, 12);
    cmGasPressureLO.ccSetText(" ");
    cmGasPressureLO.ccSetName("Lo");
    cmGasPressureLO.ccSetNameAlign('r');
    cmGasPressureLO.ccSetColor(EcFactory.C_PURPLE);

    cmGasValveA=new EcTriangleLamp();
    cmGasValveA.ccSetLocation(cmGasUnitShape, 17, -14);
    cmGasValveA.ccSetSize(8, 8);
    cmGasValveA.ccSetText(" ");
    cmGasValveA.ccSetName("A");
    cmGasValveA.ccSetNameAlign('a');
    cmGasValveA.ccSetDirection('d');
    cmGasValveA.ccSetColor(EcFactory.C_ORANGE);

    cmGasValveB=new EcTriangleLamp();
    cmGasValveB.ccSetLocation(cmGasUnitShape, 1, -14);
    cmGasValveB.ccSetSize(8, 8);
    cmGasValveB.ccSetText(" ");
    cmGasValveB.ccSetName("B");
    cmGasValveB.ccSetNameAlign('a');
    cmGasValveB.ccSetDirection('d');
    cmGasValveB.ccSetColor(EcFactory.C_ORANGE);

  }//++!

  @Override
  public void ccUpdate(){

    //-- gas pipe
    pbOwner.fill(EcUnitFactory.C_SHAPE_COLOR_DUCT);
    pbOwner.rect(cmX, cmY, C_DUCT_THICK, cmH);
    pbOwner.rect(cmX, ccEndY(), cmW, C_DUCT_THICK);

    //-- gas unit shape
    pbOwner.stroke(EcFactory.C_LIT_GRAY);
    cmGasUnitShape.ccUpdate();
    pbOwner.noStroke();

    //-- pressure switch
    pbOwner.fill(cmIsLeakingHigh
      ?EcFactory.C_LIT_GREEN:EcFactory.C_BLACK
    );
    pbOwner.rect(
      cmGasUnitShape.ccGetX()+8, cmGasUnitShape.ccGetY()+4,
      C_LED_W, C_LED_H
    );
    pbOwner.fill(cmIsLeakingLow
      ?EcFactory.C_LIT_YELLOW:EcFactory.C_BLACK
    );
    pbOwner.rect(
      cmGasUnitShape.ccGetX()+8, cmGasUnitShape.ccGetY()+C_LED_H+6,
      C_LED_W, C_LED_H
    );

    //-- elements
    cmGasPressureHI.ccUpdate();
    cmGasPressureLO.ccUpdate();
    cmGasValveA.ccUpdate();
    cmGasValveB.ccUpdate();

  }//+++

  public final void ccSetIsPressureHI(boolean pxStatus){
    cmGasPressureHI.ccSetActivated(pxStatus);
  }//+++

  public final void ccSetIsPressureLO(boolean pxStatus){
    cmGasPressureLO.ccSetActivated(pxStatus);
  }//+++

  public final void ccSetIsLeakHI(boolean pxStatus){
    cmIsLeakingHigh=pxStatus;
  }//+++

  public final void ccSetIsLeakLO(boolean pxStatus){
    cmIsLeakingLow=pxStatus;
  }//+++

  public final void ccSetIsValveAOpen(boolean pxStatus){
    cmGasValveA.ccSetActivated(pxStatus);
  }//+++

  public final void ccSetIsValveBOpen(boolean pxStatus){
    cmGasValveB.ccSetActivated(pxStatus);
  }//+++

}//***eof
