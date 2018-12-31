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
import pppunit.EcFillerSilo;
import pppunit.EcUnitFactory;

public class SubFillerSupplyModelGroup implements EiGroup{
  
  public final EcFillerSilo cmFS;
  public final EcColdElevator cmFEV;
  
  public final EcHopperShape cmFB;
  public final EcLamp cmFBL;
  public final EcTriangleLamp cmFF;
  
  public SubFillerSupplyModelGroup(){
    
    int lpX=10;
    int lpY=162;
    
    //-- icon
    cmFS=new EcFillerSilo("FS", lpX, lpY, 60900);
    cmFEV=new EcColdElevator("FEV", lpX+54, lpY+22, 60800);

    //-- component
    cmFB=new EcHopperShape();
    cmFB.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
    cmFB.ccSetSize(12, 12);
    cmFB.ccSetCut(6);
    cmFB.ccSetLocation(cmFEV,16,25);
    
    cmFBL=EcUnitFactory.ccCreateIndicatorLamp(EcFactory.C_GREEN);
    cmFBL.ccSetLocation(cmFB, 4, -8);
    
    cmFF=new EcTriangleLamp();
    cmFF.ccSetSize(9,9);
    cmFF.ccSetColor(EcFactory.C_ORANGE);
    cmFF.ccSetDirection('r');
    cmFF.ccSetNameAlign('x');
    cmFF.ccSetText(" ");
    cmFF.ccSetLocation(cmFB, 2, 14);
    
  }//+++ 
  
  @Override
  public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmFS);
    lpRes.add(cmFEV);
    lpRes.add(cmFBL);
    lpRes.add(cmFF);
    return lpRes;
  }//+++

  @Override
  public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmFB);
    return lpRes;
  }//+++
  
}//***eof
