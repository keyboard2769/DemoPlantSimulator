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
    cmPane.ccSetBaseColor(EcFactory.C_DARK_BLUE);
    
    cmTank=new EcBin("AST", 30, EcFactory.C_ID_IGNORE);
    
    cmPipeTemperatureBox=EcUnitFactory
      .ccCreateTemperatureValueBox("-000'C", "'C");
    cmPipeTemperatureBox.ccSetName("pipe");
    cmPipeTemperatureBox.ccSetNameAlign('r');
    cmPipeTemperatureBox.ccSetValue(92, 3);
    
    cmUsingTankNumber=EcFactory.ccCreateTextPL("#1");
    cmUsingTankNumber.ccSetIsActivated();
    
    cmReturnTankNumber=EcFactory.ccCreateTextPL("#1");
    cmReturnTankNumber.ccSetIsActivated();
    
    //--
    ccSetupLocation(318, 85);
    
  }//++! 
  
  public final void ccSetupLocation(int pxX, int pxY){
    int lpGap=3;
    
    cmPane.ccSetLocation(pxX, pxY);
    cmPipeTemperatureBox.ccSetLocation(cmPane, lpGap, lpGap);
    cmPane.ccSetEndPoint(cmPipeTemperatureBox, lpGap*24, lpGap);
    
    cmTank.ccSetupLocation(cmPane.ccGetX(), cmPane.ccGetY()-60);
    
    cmUsingTankNumber.ccSetLocation(cmTank, lpGap*2, 0);
    cmReturnTankNumber.ccSetLocation(cmUsingTankNumber, 0, lpGap);
    
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
    lpRes.add(cmPane);
    return lpRes;
  }//+++
  
}//***eof
