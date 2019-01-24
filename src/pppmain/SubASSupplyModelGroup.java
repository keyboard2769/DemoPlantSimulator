/*
 * Copyright (C) 2019 keypad
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
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppunit.EcBin;
import pppunit.EcUnitFactory;

public class SubASSupplyModelGroup implements EiGroup{
  
  private static SubASSupplyModelGroup self;
  public static SubASSupplyModelGroup ccGetReference(){
    if(self==null){self=new SubASSupplyModelGroup();}
    return self;
  }//++!

  //===
  
  public final EcElement cmUsingTankNumber,cmReturnTankNumber;
  public final EcBin cmTank;
  public final EcValueBox cmPipeTemperatureBox;
  
  private final EcShape cmPane;
  
  private SubASSupplyModelGroup(){
    
    cmPane = new EcShape();
    cmPane.ccSetBaseColor(EcUnitFactory.C_C_MODEL_PANE);
    
    cmTank=new EcBin(" T", 24, EcFactory.C_ID_IGNORE);
    cmTank.ccSetLevelor(1);
    
    cmPipeTemperatureBox=EcUnitFactory
      .ccCreateTemperatureValueBox("-000'C", "'C");
    cmPipeTemperatureBox.ccSetName("pipe");
    cmPipeTemperatureBox.ccSetNameAlign('a');
    cmPipeTemperatureBox.ccSetValue(92, 3);
    
    cmUsingTankNumber=EcFactory.ccCreateTextPL("#0");
    cmReturnTankNumber=EcFactory.ccCreateTextPL("#0");
    cmUsingTankNumber.ccSetSize(null, 0, -4);
    cmReturnTankNumber.ccSetSize(cmUsingTankNumber);
    
  }//++! 
  
  public final void ccSetupLocation(int pxX, int pxY){
    
    int lpGap=2;
    cmPane.ccSetLocation(pxX, pxY);
    
    //-- tank
    cmTank.ccSetupLocation(cmPane.ccGetX()+lpGap,cmPane.ccGetY()+lpGap);
    cmUsingTankNumber.ccSetLocation(cmTank, lpGap*2, 0);
    cmReturnTankNumber.ccSetLocation(cmUsingTankNumber, 0, lpGap);
    
    //-- valve
    cmPipeTemperatureBox.ccSetLocation(cmPane,
      lpGap,
      -lpGap-cmPipeTemperatureBox.ccGetH()*2
    );
    
    cmPane.ccSetEndPoint(cmPipeTemperatureBox, lpGap*24, lpGap);
    
  }//++!
  
  public final void ccSetupLocation(EcRect pxPaneBound){
    ccSetupLocation(pxPaneBound.ccGetX(), pxPaneBound.ccGetY());
    cmPane.ccSetSize(pxPaneBound);
  }//++!
  
  //===
  
  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmPipeTemperatureBox);
    lpRes.add(cmTank);
    lpRes.add(cmUsingTankNumber);
    lpRes.add(cmReturnTankNumber);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    //[TODO]::lpRes.add(cmPane);
    return lpRes;
  }//+++
  
}//***eof
