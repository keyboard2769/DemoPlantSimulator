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
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppunit.EcBurner;
import pppunit.EcDryer;
import pppunit.EcExhaustFan;
import pppunit.EcHotTower;
import pppunit.EcInclineBelcon;

public class SubAGSupplyModelGroup implements EiGroup{
  
  private static SubAGSupplyModelGroup self;
  public static SubAGSupplyModelGroup ccGetReference(){
    if(self==null){self=new SubAGSupplyModelGroup();}
    return self;
  }//++!
  
  //===
  
  public final EcInclineBelcon cmVIBC;
  public final EcDryer cmVD;
  public final EcBurner cmVB;
  public final EcExhaustFan cmVEXF;
  public final EcHotTower cmMU;
  
  private SubAGSupplyModelGroup(){
    
    cmVD=new EcDryer("VD", MainLocalCoordinator.C_ID_VD_MGH);
    cmVIBC=new EcInclineBelcon("VIBC", 60, 20, 60400);
    cmVB=new EcBurner("VB", MainLocalCoordinator.C_ID_VB_MGH);
    cmVEXF=new EcExhaustFan("VEXF", 61000);
    cmMU=new EcHotTower("MU", 60100);
    
    ccSetupLocation(815, 90);
    
  }//+++ 
  
  //===
  
  private void ccSetupLocation(int pxX, int pxY){
    cmVD.ccSetupLocation(pxX, pxY);
    cmVIBC.ccSetupLocation(pxX+cmVD.ccGetW()+12, pxY+cmVD.ccGetH()*3/4);
    cmVB.ccSetupLocation(pxX-60, pxY+24);
    cmVEXF.ccSetupLocation(pxX-60, pxY-162);
    cmMU.ccSetupLocation(pxX-160, pxY+6);
  }//+++
  
  //===
  
  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmVD);
    lpRes.add(cmVIBC);
    lpRes.add(cmVB);
    lpRes.add(cmVEXF);
    lpRes.add(cmMU);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    return lpRes;
  }//+++

}//***eof
