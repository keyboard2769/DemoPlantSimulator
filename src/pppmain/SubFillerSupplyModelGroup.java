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
import kosui.ppplocalui.EcLamp;
import kosui.ppplocalui.EcTriangleLamp;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppshape.EcHopperShape;
import pppunit.EcColdElevator;
import pppunit.EcUnitFactory;

public class SubFillerSupplyModelGroup implements EiGroup{
  
  private static SubFillerSupplyModelGroup self;
  public static SubFillerSupplyModelGroup ccGetReference(){
    if(self==null){self=new SubFillerSupplyModelGroup();}
    return self;
  }//++!
  
  //===
  
  public final EcColdElevator cmFEV;
  
  public final EcHopperShape cmFB;
  public final EcLamp cmFBL;
  public final EcTriangleLamp cmFF;
  
  private SubFillerSupplyModelGroup(){
    
    //-- icon
    cmFEV=new EcColdElevator("FEV", 60800);

    //-- component
    cmFB=new EcHopperShape();
    cmFB.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    cmFB.ccSetSize(12, 12);
    cmFB.ccSetCut(6);
    
    cmFBL=EcUnitFactory.ccCreateIndicatorLamp(EcFactory.C_GREEN);
    
    cmFF=new EcTriangleLamp();
    cmFF.ccSetSize(9,9);
    cmFF.ccSetColor(EcFactory.C_ORANGE);
    cmFF.ccSetDirection('r');
    cmFF.ccSetName("FF");
    cmFF.ccSetNameAlign('r');
    cmFF.ccSetText(" ");
    
    ccSetupLocation(20, 20);
    
  }//+++ 
  
  private void ccSetupLocation(int pxX, int pxY){
    
    cmFEV.ccSetupLocation(pxX+54, pxY+22);
    cmFB.ccSetLocation(cmFEV,16,25);
    cmFBL.ccSetLocation(cmFB, 4, -8);
    cmFF.ccSetLocation(cmFB, 2, 14);
    
  }//+++
  
  //===
  
  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmFEV);
    lpRes.add(cmFBL);
    lpRes.add(cmFF);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmFB);
    return lpRes;
  }//+++
  
}//***eof
