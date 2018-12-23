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
import pppshape.EcDuctShape;
import pppunit.EcFuelUnit;
import pppunit.EcGasUnit;
import pppunit.EcUnitFactory;

public class SubVCombustGroup implements EiGroup{
  
  public final EcFuelUnit cmVFU;
  public final EcGasUnit cmVGU;
  
  public final EcDuctShape cmFuelDuct;
  public final EcDuctShape cmGasDuct;
  
  public SubVCombustGroup(){
    
    int lpX=282;
    int lpY=280;
    
    cmVFU=new EcFuelUnit("VFU", lpX, lpY, 62900);
    cmVGU=new EcGasUnit("VGU", lpX, lpY+50, 71500);
    
    cmFuelDuct=new EcDuctShape();
    cmFuelDuct.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_DUCT);
    cmFuelDuct.ccSetLocation(lpX-37, lpY-20);
    cmFuelDuct.ccSetSize(37,20);
    cmFuelDuct.ccSetDirection('d');
    
    cmGasDuct=new EcDuctShape();
    cmGasDuct.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_DUCT);
    cmGasDuct.ccSetLocation(lpX-37, lpY);
    cmGasDuct.ccSetSize(37,70);
    cmGasDuct.ccSetDirection('d');
    
  }//++!
  
  @Override
  public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmVFU);
    lpRes.add(cmVGU);
    return lpRes;
  }//+++

  @Override
  public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmFuelDuct);
    lpRes.add(cmGasDuct);
    return lpRes;
  }//+++

}//***eof
