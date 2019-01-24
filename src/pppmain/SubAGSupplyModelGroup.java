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
import pppunit.EcHotTower;
import pppunit.EcUnitFactory;

public class SubAGSupplyModelGroup implements EiGroup{
  
  private static SubAGSupplyModelGroup self;
  public static SubAGSupplyModelGroup ccGetReference(){
    if(self==null){self=new SubAGSupplyModelGroup();}
    return self;
  }//++!
  
  //===
  
  public final EcElement cmOF1,cmOS1;
  public final EcButton cmOFD,cmOSD;
  public final EcHotTower cmMU;
  public final EcValueBox cmSandTempBox;
  
  private final EcShape cmPane;
  
  private SubAGSupplyModelGroup(){
    
    cmPane=new EcShape();
    cmPane.ccSetBaseColor(EcUnitFactory.C_C_MODEL_PANE);
    
    cmOF1=EcFactory.ccCreateTextPL("OF1");
    cmOS1=EcFactory.ccCreateTextPL("OS1");
    cmOFD=EcFactory.ccCreateButton("OFD", EcFactory.C_ID_IGNORE);
    cmOSD=EcFactory.ccCreateButton("OSD", EcFactory.C_ID_IGNORE);
    
    cmOF1.ccSetSize(cmOFD, true, false);
    cmOS1.ccSetSize(cmOSD, true, false);
    
    cmSandTempBox=EcUnitFactory.ccCreateTemperatureValueBox("-000'C", "'C");
    cmSandTempBox.ccSetValue(17, 3);
    cmSandTempBox.ccSetName("sand");
    cmSandTempBox.ccSetNameAlign('a');
    
    cmMU=new EcHotTower("MU", 60100);
    
  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    
    int lpGap=2;
    
    cmPane.ccSetLocation(pxX, pxY);
    
    cmMU.ccSetupLocation(
      cmPane.ccGetX()+7,
      cmPane.ccGetY()+5
    );
    cmOF1.ccSetLocation(cmPane,lpGap, -lpGap*2-cmOF1.ccGetH()*2);
    cmOS1.ccSetLocation(cmOF1, lpGap, 0);
    cmOFD.ccSetLocation(cmOF1, 0, lpGap);
    cmOSD.ccSetLocation(cmOS1, 0, lpGap);
    cmPane.ccSetEndPoint(cmOSD, lpGap, lpGap);
    
    cmSandTempBox.ccSetLocation(cmOS1,8,0);
    
  }//++!
  
  public final void ccSetupLocation(EcRect pxPaneBound){
    ccSetupLocation(pxPaneBound.ccGetX(), pxPaneBound.ccGetY());
    cmPane.ccSetSize(pxPaneBound);
  }//++!
    
  //===
  
  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmMU);
    lpRes.add(cmOFD);
    lpRes.add(cmOSD);
    lpRes.add(cmOF1);
    lpRes.add(cmOS1);
    lpRes.add(cmSandTempBox);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    //[TODO]::lpRes.add(cmPane);
    return lpRes;
  }//+++

}//***eof
