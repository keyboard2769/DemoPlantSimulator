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

import java.util.ArrayList;
import kosui.ppplocalui.EcButton;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcLamp;
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppshape.EcDuctShape;
import pppshape.EcHopperShape;
import pppunit.EcBurner;
import pppunit.EcDryer;
import pppunit.EcExhaustFan;
import pppunit.EcUnitFactory;
import static pppunit.EcUnitFactory.ccCreateSingleCharacterSW;

public class SubVBurnerControlGroup implements EiGroup{
  
  private static SubVBurnerControlGroup self;
  public static SubVBurnerControlGroup ccGetReference(){
    if(self==null){self=new SubVBurnerControlGroup();}
    return self;
  }//++!
  
  //===
  
  private final EcShape cmPane;//...
  private final EcHopperShape cmBagShape;
  private final EcDuctShape cmPrimeDuctShape,cmSubDuctShape;
  
  public final EcElement
    cmVIBC,cmVBReadyPL,
    cmOilPL,cmGasPL,cmHeavyPL,cmFuelPL,
    cmBagPulsePL
  ;//...
  
  public final EcLamp
    cmBagUpperLV,cmBagLowerLV
  ;//...
  
  public final EcButton
    cmVBurnerCLoseSW,cmVBurnerOpenSW,cmVBurnerAutoSW,
    cmVExfanCloseSW,cmVExfanOpenSW,cmVExfanAutoSW,
    cmVBIgnitSW
  ;//...
  
  public final EcValueBox 
    cmVExfanDegreeBox,
    cmVBurnerDegreeBox,
    cmTargetTempBox,cmChuteTempBox,cmEntraceTempBox,
    cmKPABox,cmTPHBox
  ;//...
  
  public final EcDryer cmVD;
  public final EcBurner cmVB;
  public final EcExhaustFan cmVE;
  
  private SubVBurnerControlGroup(){
    
    cmPane=new EcShape();
    cmPane.ccSetBaseColor(EcUnitFactory.C_C_CONTROL_PANE);
    
    //-- burner degree
    cmVBurnerCLoseSW=ccCreateSingleCharacterSW
      ("-", MainLocalCoordinator.C_ID_VBCLSW);
    cmVBurnerCLoseSW.ccSetName("VB");
    cmVBurnerCLoseSW.ccSetNameAlign('l');
    
    cmVBurnerOpenSW=ccCreateSingleCharacterSW
      ("+", MainLocalCoordinator.C_ID_VBOPSW);
    
    cmVBurnerAutoSW=ccCreateSingleCharacterSW
      ("#", MainLocalCoordinator.C_ID_VBATSW);
    
    //-- vexf degree
    cmVExfanCloseSW=ccCreateSingleCharacterSW
      ("-", MainLocalCoordinator.C_ID_VEXFCLSW);
    cmVExfanCloseSW.ccSetName("VE");
    cmVExfanCloseSW.ccSetNameAlign('l');
    
    cmVExfanOpenSW=ccCreateSingleCharacterSW
      ("+", MainLocalCoordinator.C_ID_VEXFOPSW);
    
    cmVExfanAutoSW=ccCreateSingleCharacterSW
      ("#", MainLocalCoordinator.C_ID_VEXFATSW);
    
    //-- PL ** incline
    cmVIBC=EcFactory.ccCreateTextPL(" <<");
    cmVIBC.ccSetTextAlign('l');
    cmVIBC.ccSetSize(60,11);
    cmVIBC.ccSetColor
      (EcUnitFactory.C_C_POWERED, EcUnitFactory.C_C_METAL);
    
    //-- PL ** combust
    cmOilPL=EcFactory.ccCreateTextPL("OIL");
    cmGasPL=EcFactory.ccCreateTextPL("GAS");
    cmGasPL.ccSetSize(cmOilPL);
    cmHeavyPL=EcFactory.ccCreateTextPL("HO");
    cmFuelPL=EcFactory.ccCreateTextPL("FO");
    cmHeavyPL.ccSetSize(cmFuelPL);
    
    //-- PL ** bag
    cmBagPulsePL=EcFactory.ccCreateTextPL("---");
    cmBagPulsePL.ccSetSize(24, 4);
    cmBagPulsePL.ccSetColor(EcUnitFactory.C_C_LED);
    cmBagUpperLV=EcUnitFactory.ccCreateIndicatorLamp(EcFactory.C_DARK_GREEN);
    cmBagLowerLV=EcUnitFactory.ccCreateIndicatorLamp(EcFactory.C_DARK_GREEN);
    
    //-- vb ignit
    cmVBReadyPL=EcFactory.ccCreateTextPL("READY");
    
    //cmVBIgnitSW=new EcButton();
    cmVBIgnitSW=EcFactory.ccCreateButton
      ("IGN", MainLocalCoordinator.C_ID_VBIGN);
    cmVBIgnitSW.ccSetSize(cmVBReadyPL);
    
    //-- box
    cmVExfanDegreeBox=EcUnitFactory.ccCreateDegreeValueBox("-099%", "%");
    cmVExfanDegreeBox.ccSetValue(1,3);
    
    cmVBurnerDegreeBox=EcUnitFactory.ccCreateDegreeValueBox("-010%", "%");
    cmVBurnerDegreeBox.ccSetValue(1, 3);
    
    cmTargetTempBox=EcUnitFactory.ccCreateSettingValueBox("-000'C", "'C");
    cmTargetTempBox.ccSetValue(160, 3);
    cmTargetTempBox.ccSetID(MainLocalCoordinator.C_ID_VB_MGH);
    
    cmChuteTempBox=EcUnitFactory.ccCreateTemperatureValueBox("-000'C", "'C");
    cmChuteTempBox.ccSetValue(0,3);
    
    cmEntraceTempBox=EcUnitFactory.ccCreateTemperatureValueBox("-000'C", "'C");
    cmEntraceTempBox.ccSetValue(160, 3);
    
    cmKPABox=EcUnitFactory.ccCreateDegreeValueBox("-200kpa", "kpa");
    cmKPABox.ccSetValue(-1, 3);
    cmKPABox.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_DIM_WATER);
    
    cmTPHBox=EcUnitFactory.ccCreateDegreeValueBox("400t/h", "t/h");
    cmTPHBox.ccSetValue(0,3);
    cmTPHBox.ccSetColor(EcFactory.C_DIM_GREEN, EcFactory.C_DIM_GRAY);
    
    //-- model
    cmVD=new EcDryer("VD", 80,MainLocalCoordinator.C_ID_VD_MGH);
    cmVB=new EcBurner("VB",25, MainLocalCoordinator.C_ID_VB_MGH);
    cmVE=new EcExhaustFan("VE", 10, EcFactory.C_ID_IGNORE);
    cmBagShape=new EcHopperShape();
    cmBagShape.ccSetSize(80, 35);
    cmBagShape.ccSetCut(10);
    cmBagShape.ccSetBaseColor(EcUnitFactory.C_C_DUCT);
    cmSubDuctShape=new EcDuctShape();
    cmSubDuctShape.ccSetDirection('c');
    cmSubDuctShape.ccSetSize(30, cmBagShape.ccGetH()*3/4);
    cmSubDuctShape.ccSetBaseColor(EcUnitFactory.C_C_DUCT);
    cmPrimeDuctShape=new EcDuctShape();
    cmPrimeDuctShape.ccSetDirection('b');
    cmPrimeDuctShape.ccSetSize(12, cmBagShape.ccGetH()*7/4);
    cmPrimeDuctShape.ccSetBaseColor(EcUnitFactory.C_C_DUCT);
    
  }//+++ 
  
  public final void ccSetupLocation(int pxX, int pxY){
    
    cmPane.ccSetLocation(pxX, pxY);
    
    //-- target
    cmTargetTempBox.ccSetLocation(cmPane,5, 5);
    cmChuteTempBox.ccSetLocation(cmTargetTempBox, 0, 2);
    
    //-- model
    int lpBurnerLX=75;
    int lpDryerLX=115;
    
    cmBagShape.ccSetLocation(cmPane,lpDryerLX,2);
    cmVD.ccSetupLocation(cmPane.ccGetX()+lpDryerLX,cmBagShape.ccEndY()+8);
    cmSubDuctShape.ccSetLocation(cmBagShape.ccGetX()-cmSubDuctShape.ccGetW()-2,
      cmBagShape.ccGetY()
    );
    cmPrimeDuctShape.ccSetLocation(cmBagShape, 2, 0);
    cmVE.ccSetupLocation(cmSubDuctShape.ccGetX()-10, cmSubDuctShape.ccEndY()-20);
    cmVB.ccSetupLocation(
      cmPane.ccGetX()+lpBurnerLX,
      cmVD.ccGetY()+15
    );
    cmKPABox.ccSetLocation(cmVD, 10, 10);
    cmVIBC.ccSetLocation(
      cmVD.ccEndX()+2,
      cmVD.ccEndY()-cmVIBC.ccGetH()
    );
    cmTPHBox.ccSetLocation(
      cmVIBC.ccCenterX(),
      cmVIBC.ccGetY()-cmTPHBox.ccGetH()-8
    );
    cmBagPulsePL.ccSetLocation(cmBagShape, 2, 2);
    cmBagUpperLV.ccSetLocation(cmBagPulsePL, 0, 4);
    cmBagLowerLV.ccSetLocation(cmBagUpperLV, 11, 11);
    cmEntraceTempBox.ccSetLocation
      (cmBagShape.ccCenterX(), cmBagShape.ccGetY()+8);
    
    //-- switch
    int lpSwitchLY=cmVD.ccEndY()+8;
    cmVBReadyPL.ccSetLocation(cmPane.ccGetX()+5, lpSwitchLY);
    cmVBIgnitSW.ccSetLocation(cmVBReadyPL, 0, 2);
    
    cmVBurnerCLoseSW.ccSetLocation(cmVBReadyPL, 40, 0);
    cmVBurnerOpenSW.ccSetLocation(cmVBurnerCLoseSW, 1, 0);
    cmVBurnerAutoSW.ccSetLocation(cmVBurnerOpenSW,4,0);
    cmVExfanCloseSW.ccSetLocation(cmVBurnerCLoseSW,0, 4);
    cmVExfanOpenSW.ccSetLocation(cmVExfanCloseSW, 1, 0);
    cmVExfanAutoSW.ccSetLocation(cmVExfanOpenSW, 4, 0);
    
    cmVBurnerDegreeBox.ccSetLocation(cmVBurnerAutoSW, 4, 0);
    cmVExfanDegreeBox.ccSetLocation(cmVExfanAutoSW, 4, 0);
    
    cmOilPL.ccSetLocation(cmVBurnerDegreeBox, 8, 0);
    cmHeavyPL.ccSetLocation(cmOilPL, 4, 0);
    cmFuelPL.ccSetLocation(cmHeavyPL, 2, 0);
    cmGasPL.ccSetLocation(cmVExfanDegreeBox, 8, 0);
    
    cmPane.ccSetEndPoint(
      cmFuelPL.ccEndX()+5,
      cmVExfanDegreeBox.ccEndY()+5
    );
    
  }//++!
  
  public final EcRect ccGetPaneBound(){
    return cmPane;  
  }//+++
  
  //===
  
  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    //-- target
    lpRes.add(cmTargetTempBox);
    lpRes.add(cmChuteTempBox);
    //-- model
    lpRes.add(cmVB);
    lpRes.add(cmVD);
    lpRes.add(cmVE);
    lpRes.add(cmBagPulsePL);
    lpRes.add(cmBagLowerLV);
    lpRes.add(cmBagUpperLV);
    lpRes.add(cmKPABox);
    lpRes.add(cmTPHBox);
    lpRes.add(cmVIBC);
    lpRes.add(cmEntraceTempBox);
    //-- switch
    lpRes.add(cmVBReadyPL);
    lpRes.add(cmVBIgnitSW);
    lpRes.add(cmVBurnerCLoseSW);
    lpRes.add(cmVBurnerOpenSW);
    lpRes.add(cmVBurnerAutoSW);
    lpRes.add(cmVExfanCloseSW);
    lpRes.add(cmVExfanOpenSW);
    lpRes.add(cmVExfanAutoSW);
    lpRes.add(cmVBurnerDegreeBox);
    lpRes.add(cmVExfanDegreeBox);
    //-- combust
    lpRes.add(cmOilPL);
    lpRes.add(cmGasPL);
    lpRes.add(cmFuelPL);
    lpRes.add(cmHeavyPL);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    lpRes.add(cmBagShape);
    lpRes.add(cmSubDuctShape);
    lpRes.add(cmPrimeDuctShape);
    return lpRes;
  }//+++
  
}//***eof
