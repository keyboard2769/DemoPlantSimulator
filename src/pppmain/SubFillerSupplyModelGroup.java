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
import kosui.ppplocalui.EcRect;
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
  
  private final EcShape cmPane;
  
  private SubFillerSupplyModelGroup(){
    
    cmPane=new EcShape();
    cmPane.ccSetBaseColor(MainSketch.C_C_BACKGROUD);
    
    cmFillerBin=new EcBin("FR", 24, EcFactory.C_ID_IGNORE);
    cmDustBin=new EcBin("DR", 24, EcFactory.C_ID_IGNORE);
    cmFillerSilo=new EcBin("FS", 24, EcFactory.C_ID_IGNORE);
    
  }//++!
  
  private void ccSetupLocation(int pxX, int pxY){
    
    cmPane.ccSetLocation(pxX, pxY);
    
    //-- bin
    int lpGap=50;
    cmDustBin.ccSetupLocation(cmPane.ccGetX()+2, cmPane.ccGetY()+2);
    cmFillerBin.ccSetupLocation(cmPane.ccGetX()+lpGap+2, cmPane.ccGetY()+2);
    
    cmFillerSilo.ccSetupLocation(
      cmFillerBin.ccGetX(),
      cmFillerBin.ccGetY()-cmFillerBin.ccGetH()-8
    );
    
    cmPane.ccSetEndPoint(cmFillerBin, 2, 2);
    
  }//++!
  
  public final void ccSetupLocation(EcRect pxPaneBound){
    ccSetupLocation(pxPaneBound.ccGetX(), pxPaneBound.ccGetY());
    cmPane.ccSetSize(pxPaneBound);
  }//++!
  
  //===
  
  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmFillerBin);
    //[NOTYET]::lpRes.add(cmDustBin);
    lpRes.add(cmFillerSilo);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    return lpRes;
  }//+++
  
}//***eof
