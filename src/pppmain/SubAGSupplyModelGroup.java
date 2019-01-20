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
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppunit.EcHotTower;

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
  
  private final EcShape cmPane;
  
  private SubAGSupplyModelGroup(){
    
    cmPane=new EcShape();
    cmPane.ccSetBaseColor(EcFactory.C_DARK_BLUE);
    
    cmOF1=EcFactory.ccCreateTextPL("OF1");
    cmOS1=EcFactory.ccCreateTextPL("OS1");
    cmOFD=EcFactory.ccCreateButton("OFD", EcFactory.C_ID_IGNORE);
    cmOSD=EcFactory.ccCreateButton("OSD", EcFactory.C_ID_IGNORE);
    
    cmOF1.ccSetSize(cmOFD, true, false);
    cmOS1.ccSetSize(cmOSD, true, false);
    
    cmMU=new EcHotTower("MU", 60100);
    
    ccSetupLocation(148, 41);
    
  }//+++ 
  
  //===
  
  private void ccSetupLocation(int pxX, int pxY){
    
    int lpGap=2;
    
    cmPane.ccSetLocation(pxX, pxY);
    cmOF1.ccSetLocation(cmPane,lpGap, lpGap);
    cmOS1.ccSetLocation(cmOF1, lpGap, 0);
    cmOFD.ccSetLocation(cmOF1, 0, lpGap);
    cmOSD.ccSetLocation(cmOS1, 0, lpGap);
    cmPane.ccSetEndPoint(cmOSD, lpGap, lpGap);
    
    cmMU.ccSetupLocation(pxX, pxY+45);
  }//+++
  
  //===
  
  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmMU);
    lpRes.add(cmOFD);
    lpRes.add(cmOSD);
    lpRes.add(cmOF1);
    lpRes.add(cmOS1);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    return lpRes;
  }//+++

}//***eof
