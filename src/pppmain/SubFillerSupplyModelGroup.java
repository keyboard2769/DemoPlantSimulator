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
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppunit.EcBin;

public class SubFillerSupplyModelGroup implements EiGroup{
  
  private static SubFillerSupplyModelGroup self;
  public static SubFillerSupplyModelGroup ccGetReference(){
    if(self==null){self=new SubFillerSupplyModelGroup();}
    return self;
  }//++!
  
  //===
  
  public final EcBin cmFillerBin, cmDustBin, cmFillerSilo;
  public final EcElement cmBagPulsePL;
  
  private final EcShape cmPane;
  
  private SubFillerSupplyModelGroup(){
    
    cmPane=new EcShape();
    cmPane.ccSetBaseColor(EcFactory.C_DARK_BLUE);
    
    cmFillerBin=new EcBin("FR", 24, EcFactory.C_ID_IGNORE);
    cmDustBin=new EcBin("DR", 24, EcFactory.C_ID_IGNORE);
    cmFillerSilo=new EcBin("FS", 24, EcFactory.C_ID_IGNORE);
    
    cmBagPulsePL=new EcElement();
    cmBagPulsePL.ccSetupKey("A-PLS");
    cmBagPulsePL.ccSetSize();
    
    ccSetupLocation(38, 80);
    
  }//+++ 
  
  private void ccSetupLocation(int pxX, int pxY){
    int lpGap=50;
    cmPane.ccSetLocation(pxX, pxY);
    cmDustBin.ccSetupLocation(cmPane.ccGetX()+2, cmPane.ccGetY()+2);
    cmFillerBin.ccSetupLocation(cmPane.ccGetX()+lpGap+2, cmPane.ccGetY()+2);
    cmPane.ccSetEndPoint(cmFillerBin, 2, 2);
    
    //-- optional
    cmBagPulsePL.ccSetLocation(cmDustBin, 1, -48);
    
    cmFillerSilo.ccSetupLocation(
      cmFillerBin.ccGetX(),
      cmFillerBin.ccGetY()-cmFillerBin.ccGetH()-8
    );
    
  }//+++
  
  //===
  
  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmFillerBin);
    lpRes.add(cmDustBin);
    
    //-- optional
    lpRes.add(cmBagPulsePL);
    lpRes.add(cmFillerSilo);
    
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    return lpRes;
  }//+++
  
}//***eof
