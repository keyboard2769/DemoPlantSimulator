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
import kosui.ppplocalui.EcValueBox;
import pppicon.EcMotorIcon;
import pppicon.EcSingleSolenoidIcon;
import pppshape.EcHopperShape;
import pppshape.EcScrewShape;

class EcBagFilter extends EcElement implements EiMultipleMoterized{

  private final int //[TODO]::make static
    C_BAG_CUT=30,
    C_COARSE_CUT=15,
    //--
    C_FILTER_W=3,
    C_FILTER_H=18,
    C_FILTER_GAP=2;//...

  public final int //[TODO]::make static
    C_M_BAG_SCREW=0,
    C_M_COARSE_SCREW=1;//...

  private int cmFilterCount;

  private int cmCurrentCount;

  private final EcHopperShape cmBag;

  private final EcHopperShape cmCoarse;

  private final EcScrewShape cmBagScrew;

  private final EcScrewShape cmCoarseScrew;

  private final EcLamp cmBagUpperLV;

  private final EcLamp cmBagLowerLV;

  private final EcSingleSolenoidIcon cmCoolingDamper;

  private final EcMotorIcon cmBagScrewMotor;

  private final EcMotorIcon cmCoarseScrewMotor;

  private final EcTriangleLamp cmToDustFeederPL;

  private final EcTriangleLamp cmToDustExtractionPL;

  private final EcValueBox cmEntranceTemrature;

  public EcBagFilter(
    String pxName, int pxX, int pxY, int pxFilterCount, int pxHeadID
  ){

    super();
    ccTakeKey(pxName);
    ccSetLocation(pxX, pxY);
    ccSetSize(150, 100);//[TODO]::width should be as long as V dryer
    ccSetID(pxHeadID);

    cmFilterCount=pxFilterCount;
    cmCurrentCount=0;

    cmBag=new EcHopperShape();
    cmBag.ccSetLocation(cmX, cmY);
    cmBag.ccSetSize(cmW-C_BAG_CUT, cmH-C_BAG_CUT);
    cmBag.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    cmBag.ccSetCut(C_BAG_CUT);

    cmCoarse=new EcHopperShape();
    cmCoarse.ccSetLocation(ccEndX()-C_BAG_CUT, cmY+C_COARSE_CUT);
    cmCoarse.ccSetSize(C_BAG_CUT, cmH-C_COARSE_CUT*2);
    cmCoarse.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    cmCoarse.ccSetCut(C_COARSE_CUT);

    cmBagScrew=new EcScrewShape();
    cmBagScrew.ccSetLocation(cmBag, 0, 2);
    cmBagScrew.ccShiftLocation(C_BAG_CUT/2, 0);
    cmBagScrew.ccSetSize(cmBag.ccGetW()-C_BAG_CUT, 12);
    cmBagScrew.ccSetCut(C_BAG_CUT, -1);

    cmCoarseScrew=new EcScrewShape();
    cmCoarseScrew.ccSetLocation(ccCenterX()-2, cmCoarse.ccEndY()+2);
    cmCoarseScrew.ccSetSize(cmW/2, 24);
    cmCoarseScrew.ccSetCut(cmW/2-C_COARSE_CUT, 0);

    cmBagUpperLV=new EcLamp();
    cmBagUpperLV.ccSetLocation(cmX+C_BAG_CUT/2, cmBag.ccEndY()-C_BAG_CUT);
    cmBagUpperLV.ccSetSize(12, 12);
    cmBagUpperLV.ccSetName("F2H");
    cmBagUpperLV.ccSetNameAlign('l');
    cmBagUpperLV.ccSetText(" ");
    cmBagUpperLV.ccSetColor(EcFactory.C_LIT_GREEN);

    cmBagLowerLV=new EcLamp();
    cmBagLowerLV.ccSetLocation(cmBagUpperLV, 15, 15);
    cmBagLowerLV.ccSetSize(12, 12);
    cmBagLowerLV.ccSetName("F2H");
    cmBagLowerLV.ccSetNameAlign('l');
    cmBagLowerLV.ccSetText(" ");
    cmBagLowerLV.ccSetColor(EcFactory.C_LIT_GREEN);

    cmCoolingDamper=new EcSingleSolenoidIcon();
    cmCoolingDamper.ccSetLocation(ccEndX(), cmY+C_COARSE_CUT);

    cmBagScrewMotor=new EcMotorIcon();
    cmBagScrewMotor.ccSetLocation(cmBagScrew.ccEndX()-30,
      cmBagScrew.ccCenterY()-4);

    cmCoarseScrewMotor=new EcMotorIcon();
    cmCoarseScrewMotor.ccSetLocation(cmCoarseScrew.ccEndX()-20, cmCoarseScrew.
      ccCenterY()-2);

    cmToDustFeederPL=new EcTriangleLamp();
    cmToDustFeederPL.ccSetDirection('l');
    cmToDustFeederPL.ccSetName("DF");
    cmToDustFeederPL.ccSetNameAlign('b');
    cmToDustFeederPL.ccSetText(" ");
    cmToDustFeederPL.ccSetColor(EcFactory.C_ORANGE);
    cmToDustFeederPL.ccSetSize(8, 8);
    cmToDustFeederPL.ccSetLocation(cmBagScrew.ccGetX()-18, cmBagScrew.
      ccCenterY()-4);

    cmToDustExtractionPL=new EcTriangleLamp();
    cmToDustExtractionPL.ccSetDirection('r');
    cmToDustExtractionPL.ccSetName("DE");
    cmToDustExtractionPL.ccSetNameAlign('b');
    cmToDustExtractionPL.ccSetText(" ");
    cmToDustExtractionPL.ccSetColor(EcFactory.C_ORANGE);
    cmToDustExtractionPL.ccSetSize(8, 8);
    cmToDustExtractionPL.ccSetLocation(cmBagScrew.ccEndX()+8, cmBagScrew.
      ccCenterY()-4);

    cmEntranceTemrature=new EcValueBox();
    cmEntranceTemrature.ccSetText("-123'c");
    cmEntranceTemrature.ccSetSize();
    cmEntranceTemrature.ccSetValue(37, 3);
    cmEntranceTemrature.ccSetUnit("'c");
    cmEntranceTemrature.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_DIM_RED);
    cmEntranceTemrature.ccSetTextColor(EcFactory.C_LIT_GRAY);
    cmEntranceTemrature.ccSetLocation(cmCoarse, 5, C_COARSE_CUT);

  }//+++

  @Override
  public void ccUpdate(){

    cmBag.ccUpdate();
    cmCoarse.ccUpdate();
    cmBagScrew.ccUpdate();
    cmCoarseScrew.ccUpdate();
    cmBagUpperLV.ccUpdate();
    cmBagLowerLV.ccUpdate();
    cmCoolingDamper.ccUpdate();
    cmBagScrewMotor.ccUpdate();
    cmCoarseScrewMotor.ccUpdate();
    cmToDustFeederPL.ccUpdate();
    cmToDustExtractionPL.ccUpdate();
    cmEntranceTemrature.ccUpdate();

    for(int i=0; i<cmFilterCount; i++){
      pbOwner.fill(cmCurrentCount==i
        ?EcFactory.C_LIT_ORANGE:EcFactory.C_DARK_GRAY
      );
      pbOwner.rect(
        cmX+C_FILTER_GAP*2+(C_FILTER_GAP+C_FILTER_W)*i,
        cmY+C_FILTER_GAP*2,
        C_FILTER_W, C_FILTER_H
      );
    }

  }//+++

  @Override
  public void ccSetMotorStatus(int pxIndex, char pxStatus_acnlx){

    switch(pxIndex){

      case C_M_BAG_SCREW:
        EcMotorIcon.ccSetMotorStatus(cmBagScrewMotor, pxStatus_acnlx);
        break;

      case C_M_COARSE_SCREW:
        EcMotorIcon.ccSetMotorStatus(cmCoarseScrewMotor, pxStatus_acnlx);
        break;

    }//..?

  }//+++

  public void ccSetCoolingDamoerStatus(char pxTarget_soc, boolean pxStatus){
    switch(pxTarget_soc){
      case 's': cmCoolingDamper.ccSetIsOpening(pxStatus);
        break;
      case 'o': cmCoolingDamper.ccSetIsFull(pxStatus);
        break;
      case 'c': cmCoolingDamper.ccSetIsClosed(pxStatus);
        break;
    }
  }//+++

  public void ccSetEntranceTemprature(int pxVal){
    cmEntranceTemrature.ccSetValue(pxVal);
  }//+++

  public void ccSetBagLevelerStatus(char pxTarget_hl, boolean pxStatus){
    switch(pxTarget_hl){
      case 'h': cmBagUpperLV.ccSetActivated(pxStatus);
        break;
      case 'l': cmBagLowerLV.ccSetActivated(pxStatus);
        break;
    }
  }//+++

  public void ccSetDustFlow(char pxTarget_ef, boolean pxStatus){
    switch(pxTarget_ef){
      case 'e': cmToDustExtractionPL.ccSetActivated(pxStatus);
        break;
      case 'f': cmToDustFeederPL.ccSetActivated(pxStatus);
        break;
    }
  }//+++

  public void ccSetCurrentFilterCount(int pxCount){
    cmCurrentCount=pxCount;
  }//+++

  public void ccSetFilterCount(int pxCount){
    cmFilterCount=pxCount;
  }//+++

}//***eof

