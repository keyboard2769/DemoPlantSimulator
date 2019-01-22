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
import static pppmain.MainLocalCoordinator.C_ID_VF_HEAD;

import pppunit.EcFeeder;
import pppunit.EcUnitFactory;

public class SubVFeederModelGroup implements EiGroup{

  public final EcFeeder
    cmVF01,cmVF02,cmVF03,cmVF04,cmVF05,cmVF06
  ;//...
  public final EcElement cmVHBC;
 
  private final EcShape cmPane;
  
  public SubVFeederModelGroup(){
    
    cmPane=new EcShape();
    cmPane.ccSetBaseColor(EcUnitFactory.C_C_MODEL_PANE);
    
    cmVHBC=EcFactory.ccCreateTextPL(" << ");
    cmVHBC.ccSetTextAlign('l');
    cmVHBC.ccSetColor
      (EcUnitFactory.C_C_POWERED, EcUnitFactory.C_C_METAL);
    
    cmVF01=new EcFeeder("VF01", C_ID_VF_HEAD+1);
    cmVF02=new EcFeeder("VF02", C_ID_VF_HEAD+2);
    cmVF03=new EcFeeder("VF03", C_ID_VF_HEAD+3);
    cmVF04=new EcFeeder("VF04", C_ID_VF_HEAD+4);
    
    cmVF05=new EcFeeder("VF05", C_ID_VF_HEAD+5);
    cmVF06=new EcFeeder("VF06", C_ID_VF_HEAD+6);
    
  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    
    cmPane.ccSetLocation(pxX, pxY);
    
    cmVF01.ccSetupLocation(pxX+2,pxY+2);
    
    int lpGapX=cmVF01.ccGetW()+4;
    int lpSubY=cmVF01.ccGetY();
    
    cmVF02.ccSetupLocation(pxX+lpGapX*1,lpSubY);
    cmVF03.ccSetupLocation(pxX+lpGapX*2,lpSubY);
    cmVF04.ccSetupLocation(pxX+lpGapX*3,lpSubY);
    cmVF05.ccSetupLocation(pxX+lpGapX*4,lpSubY);
    cmVF06.ccSetupLocation(pxX+lpGapX*5,lpSubY);
    
    cmVHBC.ccSetSize((cmVF01.ccGetW()+4)*6, 12);
    cmVHBC.ccSetLocation(cmVF01, 0, 6);
    
    cmPane.ccSetEndPoint(cmVHBC, 2, 2);
    
  }//++!
  
  public final void ccSetupLocation(EcRect pxPaneBound){
    ccSetupLocation(pxPaneBound.ccGetX(), pxPaneBound.ccGetY());
    cmPane.ccSetSize(pxPaneBound);
  }//++!
  
  //===
  
  public final EcRect ccGetPaneBound(){return cmPane;}//+++
  
  //===

  @Override public ArrayList<EcElement> ccGiveElementList(){
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

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    return lpRes;
  }//+++
  
}//***eof
