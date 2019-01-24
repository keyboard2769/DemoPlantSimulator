/*
 * Copyright (C) 2019 2053
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
import kosui.ppplocalui.EcPane;
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppunit.EcUnitFactory;

public final class SubZeroAdjustControlGroup implements EiGroup{

  private static SubZeroAdjustControlGroup self;
  public static SubZeroAdjustControlGroup ccGetReference(){
    if(self==null){self=new SubZeroAdjustControlGroup();}
    return self;
  }//++!
  
  //===
  
  public final EcButton cmZeroADJ,cmFRZero, cmAGZero, cmASZero;
  
  private final EcPane cmPane;
  private final EcShape cmCatePane;

  private SubZeroAdjustControlGroup(){
    
    cmPane=new EcPane();
    cmPane.ccSetTitle("Z-ADJ");
    cmCatePane=new EcShape();
    cmCatePane.ccSetBaseColor(EcUnitFactory.C_C_SWITCH_PANE);
    
    cmZeroADJ=EcFactory.ccCreateButton("ZERO\nAPP", EcFactory.C_ID_IGNORE);
    
    cmFRZero=EcFactory.ccCreateButton("FR", EcFactory.C_ID_IGNORE);
    cmAGZero=EcFactory.ccCreateButton("AG", EcFactory.C_ID_IGNORE);
    cmASZero=EcFactory.ccCreateButton("AS", EcFactory.C_ID_IGNORE);
    
    cmFRZero.ccSetSize(null, 4, 0);
    cmAGZero.ccSetSize(cmFRZero);
    cmASZero.ccSetSize(cmFRZero);
    
  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    cmPane.ccSetLocation(pxX, pxY);
    
    cmZeroADJ.ccSetLocation(cmPane,5, 25);
    
    cmCatePane.ccSetLocation(cmZeroADJ, 2, 0);
    cmCatePane.ccSetEndPoint(cmPane.ccEndX()-5, cmZeroADJ.ccEndY());
    
    cmFRZero.ccSetLocation(cmCatePane, 2, 2);
    cmAGZero.ccSetLocation(cmFRZero, 2, 0);
    cmASZero.ccSetLocation(cmAGZero, 2, 0);
  }//++!
  
  public final void ccSetupLocation(EcRect pxBound){
    cmPane.ccSetSize(pxBound);
    ccSetupLocation(pxBound.ccGetX(), pxBound.ccGetY());
  }//++!
  
  //===
  
  public final EcRect ccGetPaneBound(){return cmPane;}

  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmZeroADJ);
    lpRes.add(cmFRZero);
    lpRes.add(cmAGZero);
    lpRes.add(cmASZero);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    lpRes.add(cmCatePane);
    return lpRes;
  }//+++

}//***eof
