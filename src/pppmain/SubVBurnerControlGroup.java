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
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppunit.EcBurner;
import pppunit.EcDryer;
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
  
  public final EcElement
    cmVIBC,cmCAS,cmVBReadyPL,
    cmOilPL,cmGasPL,cmHeavyPL,cmFuelPL
  ;//...
  
  public final EcValueBox 
    cmVExfanDegreeBox,
    cmVBurnerDegreeBox,
    cmTargetTempBox,cmChuteTempBox,
    cmKPABox,cmTPHBox
  ;//...
  
  public final EcButton
    cmVBurnerCLoseSW,cmVBurnerOpenSW,cmVBurnerAutoSW,
    cmVExfanCloseSW,cmVExfanOpenSW,cmVExfanAutoSW,
    cmVBIgnitSW
  ;//...
  
  public final EcDryer cmVD;
  public final EcBurner cmVB;
  
  private SubVBurnerControlGroup(){
    
    cmPane=new EcShape();
    cmPane.ccSetBaseColor(EcFactory.C_DARK_BLUE);
    
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
    cmCAS=EcFactory.ccCreateTextPL("cas");
    cmVIBC=EcFactory.ccCreateTextPL(" <<       ");
    cmCAS.ccSetSize(null, 0, -6);
    cmVIBC.ccSetSize(cmCAS,false,true);
    
    //-- PL ** combust
    cmOilPL=EcFactory.ccCreateTextPL("OIL");
    cmGasPL=EcFactory.ccCreateTextPL("GAS");
    cmGasPL.ccSetSize(cmOilPL);
    cmHeavyPL=EcFactory.ccCreateTextPL("HO");
    cmFuelPL=EcFactory.ccCreateTextPL("FO");
    cmHeavyPL.ccSetSize(cmFuelPL);
    
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
    
    cmChuteTempBox=EcUnitFactory.ccCreateTemperatureValueBox("-000'C", "'C");
    cmChuteTempBox.ccSetValue(0,3);
    
    cmKPABox=EcUnitFactory.ccCreateDegreeValueBox("-200kpa", "kpa");
    cmKPABox.ccSetValue(-1, 3);
    
    cmTPHBox=EcUnitFactory.ccCreateDegreeValueBox("400t/h", "t/h");
    cmTPHBox.ccSetValue(0,3);
    
    //-- model
    cmVD=new EcDryer("VD", 120,MainLocalCoordinator.C_ID_VD_MGH);
    cmVB=new EcBurner("VB",32, MainLocalCoordinator.C_ID_VB_MGH);
    
  }//+++ 
  
  public final void ccSetupLocation(int pxX, int pxY){
    
    cmPane.ccSetLocation(pxX, pxY);
    
    cmTargetTempBox.ccSetLocation(cmPane,5, 5);
    cmChuteTempBox.ccSetLocation(cmTargetTempBox, 0, 2);
    
    cmVB.ccSetupLocation(pxX+72, pxY+30);
    cmVD.ccSetupLocation(cmVB.ccEndX()+4,cmVB.ccGetY()-20);
    
    cmKPABox.ccSetLocation(cmVD, 10, 10);
    cmTPHBox.ccSetLocation(cmVD, 4, 0);
    
    cmVIBC.ccSetLocation(cmTPHBox, 0, 30);
    cmCAS.ccSetLocation(cmVIBC, 27, -18);
    
    cmVBReadyPL.ccSetLocation(cmPane, 5, 85);
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
    
    cmPane.ccSetEndPoint(cmVIBC.ccEndX()+5,
      cmVExfanDegreeBox.ccEndY()+5
    );
    
  }//++!
  
  public final EcRect ccGetPaneBound(){
    return cmPane;  
  }//+++
  
  //===
  
  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmVB);
    lpRes.add(cmVD);
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
    lpRes.add(cmTargetTempBox);
    lpRes.add(cmChuteTempBox);
    lpRes.add(cmKPABox);
    lpRes.add(cmTPHBox);
    lpRes.add(cmVIBC);
    lpRes.add(cmCAS);
    lpRes.add(cmOilPL);
    lpRes.add(cmGasPL);
    lpRes.add(cmFuelPL);
    lpRes.add(cmHeavyPL);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    return lpRes;
  }//+++
  
}//***eof
