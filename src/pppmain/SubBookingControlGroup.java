/*
 * Copyright (C) 2019 keypad
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
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppunit.EcUnitFactory;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_RUN;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_AUTO;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_MANN;
import static pppmain.MainLocalCoordinator.C_ID_BOOK_RECIPE_HEAD;
import static pppmain.MainLocalCoordinator.C_ID_BOOK_KG_HEAD;
import static pppmain.MainLocalCoordinator.C_ID_BOOK_BATCH_HEAD;

public class SubBookingControlGroup implements EiGroup{
  
  private final EcPane cmPane;//...
  private final EcShape cmCurrentRange,cmManualAutoRange;
  
  public final EcValueBox[] cmDesRecipeBox,cmDesKGBox,cmDesBatchBox;
  public final EcButton cmManualSW, cmAutoSW, cmRunSW;
  
  //===
  
  public SubBookingControlGroup(){
    
    cmPane=new EcPane();
    cmPane.ccSetLocation(290, 445);
    cmPane.ccSetTitle("A-Booking");
    
    //-- table 
    cmCurrentRange=new EcShape();
    cmCurrentRange.ccSetBaseColor(EcFactory.C_DARK_YELLOW);
    cmCurrentRange.ccSetLocation(cmPane, 5, 22);
    
    cmDesRecipeBox=new EcValueBox[3];
    for(int i=0;i<cmDesRecipeBox.length;i++){
      cmDesRecipeBox[i]=EcUnitFactory.ccCreateSettingValueBox("00/", "/");
      cmDesRecipeBox[i].ccSetValue(i, 2);
      cmDesRecipeBox[i].ccSetSize(null, 18, -4);
      cmDesRecipeBox[i].ccSetID(C_ID_BOOK_RECIPE_HEAD+i);
    }//..~
    cmDesRecipeBox[0].ccSetLocation(cmCurrentRange, 2, 2);
    cmDesRecipeBox[1].ccSetLocation(cmDesRecipeBox[0], 0, 5);
    cmDesRecipeBox[2].ccSetLocation(cmDesRecipeBox[1], 0, 3);
    
    cmDesKGBox=new EcValueBox[3];
    for(int i=0;i<cmDesKGBox.length;i++){
      cmDesKGBox[i]=EcUnitFactory.ccCreateSettingValueBox("0000kg", "kg");
      cmDesKGBox[i].ccSetValue(0, 4);
      cmDesKGBox[i].ccSetSize(null, 0, -4);
      cmDesKGBox[i].ccSetID(C_ID_BOOK_KG_HEAD+i);
    }//..~
    cmDesKGBox[0].ccSetLocation(cmDesRecipeBox[0], 23, 0);
    cmDesKGBox[1].ccSetLocation(cmDesKGBox[0], 0, 5);
    cmDesKGBox[2].ccSetLocation(cmDesKGBox[1], 0, 3);
    
    cmDesBatchBox=new EcValueBox[3];
    for(int i=0;i<cmDesBatchBox.length;i++){
      cmDesBatchBox[i]=EcUnitFactory.ccCreateSettingValueBox("0000b", "b");
      cmDesBatchBox[i].ccSetValue(0, 4);
      cmDesBatchBox[i].ccSetSize(null,0,-4);
      cmDesBatchBox[i].ccSetID(C_ID_BOOK_BATCH_HEAD+i);
    }//..~
    cmDesBatchBox[0].ccSetLocation(cmDesKGBox[0], 2, 0);
    cmDesBatchBox[1].ccSetLocation(cmDesBatchBox[0], 0, 5);
    cmDesBatchBox[2].ccSetLocation(cmDesBatchBox[1], 0, 3);
    
    cmDesRecipeBox[0].ccSetColor(EcFactory.C_DIM_GRAY, EcFactory.C_DARK_GRAY);
    cmDesKGBox[0].ccSetColor(EcFactory.C_DIM_GRAY, EcFactory.C_DARK_GRAY);
    cmDesBatchBox[0].ccSetColor(EcFactory.C_DIM_GRAY, EcFactory.C_DARK_GRAY);
 //   cmDesNumberBox[0].ccSetTextColor(EcFactory.C_DIM_GRAY);
  //  cmDesTonBox[0].ccSetTextColor(EcFactory.C_DIM_GRAY);
  //  cmDesBatchBox[0].ccSetTextColor(EcFactory.C_DIM_GRAY);
    
    cmCurrentRange.ccSetEndPoint(cmDesBatchBox[0], 3, 3);
    
    //-- switch
    cmManualSW=EcFactory.ccCreateButton("MANN", C_ID_WEIGH_MANN);
    cmAutoSW=EcFactory.ccCreateButton("AUTO", C_ID_WEIGH_AUTO);
    cmRunSW=EcFactory.ccCreateButton("RUN", C_ID_WEIGH_RUN);
    //--
    cmManualSW.ccSetLocation(cmDesRecipeBox[2], 0, 24);
    cmAutoSW.ccSetLocation(cmManualSW, 8, 0);
    //--
    cmManualAutoRange=new EcShape();
    cmManualAutoRange.ccSetBaseColor(EcFactory.C_DIM_BLUE);
    cmManualAutoRange.ccSetLocation(cmManualSW, -2, -2);
    cmManualAutoRange.ccSetEndPoint(cmAutoSW, 3, 3);
    //--
    cmRunSW.ccSetSize(cmAutoSW);
    cmRunSW.ccSetLocation(
      cmDesBatchBox[0].ccEndX()-cmRunSW.ccGetW()-2,
      cmAutoSW.ccGetY()
    );
    
    //--pack
    cmPane.ccSetEndPoint(cmRunSW,10, 10);
    
  }//+++ 
  
  //===
  
  @Override
  public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmDesRecipeBox[0]);
    lpRes.add(cmDesRecipeBox[1]);
    lpRes.add(cmDesRecipeBox[2]);
    lpRes.add(cmDesKGBox[0]);
    lpRes.add(cmDesKGBox[1]);
    lpRes.add(cmDesKGBox[2]);
    lpRes.add(cmDesBatchBox[0]);
    lpRes.add(cmDesBatchBox[1]);
    lpRes.add(cmDesBatchBox[2]);
    lpRes.add(cmRunSW);
    lpRes.add(cmManualSW);
    lpRes.add(cmAutoSW);
    return lpRes;
  }//+++

  @Override
  public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    lpRes.add(cmCurrentRange);
    lpRes.add(cmManualAutoRange);
    return lpRes;
  }//+++
  
}//***eof
