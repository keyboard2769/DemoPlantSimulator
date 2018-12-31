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

import pppunit.EcFeeder;
import pppunit.EcHorizontalBelcon;

public class SubVFeederModelGroup implements EiGroup{
  
  public static final int 
    C_ID_VHBC = 60500,
    C_ID_VF01 = 61600,
    C_ID_VF02 = 61700,
    C_ID_VF03 = 61800,
    C_ID_VF04 = 61900,
    C_ID_VF05 = 62000,
    C_ID_VF06 = 62100
  ;//...
  
  public final EcFeeder
    cmVF01,cmVF02,cmVF03,cmVF04,cmVF05,cmVF06
  ;//...
  
  public final EcHorizontalBelcon cmVHBC;
  
  public SubVFeederModelGroup(){
    
    int lpX=543;
    int lpY=145;
    int lpGap=64;
    
    cmVHBC=new EcHorizontalBelcon("VHBC", lpX-48, lpY+72, 280, C_ID_VHBC);
    
    cmVF01=new EcFeeder("VF01",lpX+lpGap*0,lpY, C_ID_VF01);
    cmVF02=new EcFeeder("VF02",lpX+lpGap*1,lpY, C_ID_VF02);
    cmVF03=new EcFeeder("VF03",lpX+lpGap*2,lpY, C_ID_VF03);
    cmVF04=new EcFeeder("VF04",lpX+lpGap*3,lpY, C_ID_VF04);
    
    lpY-=96;
    cmVF05=new EcFeeder("VF05",lpX+lpGap*2,lpY, C_ID_VF05);
    cmVF06=new EcFeeder("VF06",lpX+lpGap*3,lpY, C_ID_VF06);
    
    
  }//+++ 

  @Override
  public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmVF01);
    lpRes.add(cmVF02);
    lpRes.add(cmVF03);
    lpRes.add(cmVF04);
    lpRes.add(cmVF05);
    lpRes.add(cmVF06);
    lpRes.add(cmVHBC);
    return lpRes;
  }//+++

  @Override
  public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    return lpRes;
  }//+++
  
}//***eof
