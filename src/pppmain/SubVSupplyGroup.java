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
import pppunit.EcBagFilter;
import pppunit.EcBurner;
import pppunit.EcDryer;
import pppunit.EcExhaustFan;
import pppunit.EcHotTower;
import pppunit.EcInclineBelcon;

public class SubVSupplyGroup implements EiGroup{
  
  public final EcInclineBelcon cmVIBC;
  public final EcDryer cmVD;
  public final EcBurner cmVB;
  public final EcBagFilter cmBAG;
  public final EcExhaustFan cmVEXF;
  public final EcHotTower cmMU;
  
  public SubVSupplyGroup(){
    
    int lpX=275;
    int lpY=186;
    
    cmVD=new EcDryer("VD", lpX, lpY, 60300);
    cmVIBC=new EcInclineBelcon("VIBC",
      lpX+cmVD.ccGetW()+12,
      lpY+cmVD.ccGetH()*3/4,
      60, 20, 60400
    );
    cmVB=new EcBurner("VB", lpX-60, lpY+24, 62800);
    cmBAG = new EcBagFilter("BAG", lpX, lpY-115, 24, 62200);
    cmVEXF=new EcExhaustFan("VEXF", lpX-60, lpY-162, 61000);
    cmMU=new EcHotTower("MU", lpX-160, lpY+6, 60100);
    
  }//+++ 
  
  @Override
  public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmVD);
    lpRes.add(cmVIBC);
    lpRes.add(cmVB);
    lpRes.add(cmBAG);
    lpRes.add(cmVEXF);
    lpRes.add(cmMU);
    return lpRes;
  }//+++

  @Override
  public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    return lpRes;
  }//+++

}//***eof
