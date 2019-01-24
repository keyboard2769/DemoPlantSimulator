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
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcTextBox;
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
  
  private static SubBookingControlGroup self;
  public static SubBookingControlGroup ccGetReference(){
    if(self==null){self=new SubBookingControlGroup();}
    return self;
  }//++!
  
  //===
  
  private final EcPane cmPane;//...
  private final EcShape cmManualAutoRange;
  
  public final EcValueBox[] cmDesRecipeBox,cmDesKGBox,cmDesBatchBox;
  public final EcTextBox[] cmDesNameBox;
  public final EcButton cmManualSW, cmAutoSW, cmRunSW;
  
  //===
  
  private SubBookingControlGroup(){
    
    cmPane=new EcPane();
    cmPane.ccSetTitle("A-Booking");
    
    //-- pane
    cmManualAutoRange=new EcShape();
    cmManualAutoRange.ccSetBaseColor(EcFactory.C_DIM_BLUE);
    
    //-- table
    int lpCapa=MainOperationModel.C_MAX_BOOK_CAPABILITY;
    
    cmDesRecipeBox=new EcValueBox[lpCapa];
    for(int i=0;i<cmDesRecipeBox.length;i++){
      cmDesRecipeBox[i]=EcUnitFactory.ccCreateSettingValueBox("00/", "/");
      cmDesRecipeBox[i].ccSetValue(i, 2);
      cmDesRecipeBox[i].ccSetSize(null, 18, -4);
      cmDesRecipeBox[i].ccSetID(C_ID_BOOK_RECIPE_HEAD+i);
      cmDesRecipeBox[i].ccSetName(Integer.toString(i));
      cmDesRecipeBox[i].ccSetNameAlign('l');
      if(i>0){cmDesRecipeBox[i].ccSetColor
        (EcFactory.C_DIM_GRAY, EcFactory.C_DARK_GRAY);}
    }//..~
    
    cmDesKGBox=new EcValueBox[lpCapa];
    for(int i=0;i<cmDesKGBox.length;i++){
      cmDesKGBox[i]=EcUnitFactory.ccCreateSettingValueBox("0000kg", "kg");
      cmDesKGBox[i].ccSetValue(0, 4);
      cmDesKGBox[i].ccSetSize(null, 0, -4);
      cmDesKGBox[i].ccSetID(C_ID_BOOK_KG_HEAD+i);
      if(i>0){cmDesKGBox[i].ccSetColor
        (EcFactory.C_DIM_GRAY, EcFactory.C_DARK_GRAY);}
    }//..~
    
    cmDesBatchBox=new EcValueBox[lpCapa];
    for(int i=0;i<cmDesBatchBox.length;i++){
      cmDesBatchBox[i]=EcUnitFactory.ccCreateSettingValueBox("0000b", "b");
      cmDesBatchBox[i].ccSetValue(0, 4);
      cmDesBatchBox[i].ccSetSize(null,0,-4);
      cmDesBatchBox[i].ccSetID(C_ID_BOOK_BATCH_HEAD+i);
      if(i>0){cmDesBatchBox[i].ccSetColor
        (EcFactory.C_DIM_GRAY, EcFactory.C_DARK_GRAY);}
    }//..~
    
    cmDesNameBox=new EcTextBox[lpCapa];
    for(int i=0;i<cmDesNameBox.length;i++){
      cmDesNameBox[i]=EcFactory.ccCreateBox("--------");
      cmDesNameBox[i].ccSetTextColor(EcFactory.C_LIT_GRAY);
      cmDesNameBox[i].ccSetColor
        (EcFactory.C_DIM_YELLOW, EcFactory.C_DARK_GREEN);
      cmDesNameBox[i].ccSetSize(cmDesRecipeBox[i], false, true);
      if(i>0){cmDesNameBox[i].ccSetColor
        (EcFactory.C_DIM_GRAY, EcFactory.C_DARK_GRAY);}
    }//..~
    
    //-- switch
    cmManualSW=EcFactory.ccCreateButton("MANN", C_ID_WEIGH_MANN);
    cmAutoSW=EcFactory.ccCreateButton("AUTO", C_ID_WEIGH_AUTO);
    cmRunSW=EcFactory.ccCreateButton("RUN", C_ID_WEIGH_RUN);
    cmRunSW.ccSetSize(cmAutoSW);
    
  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    
    cmPane.ccSetLocation(pxX, pxY);
    
    //-- book
    cmDesRecipeBox[0].ccSetLocation(cmPane, 25, 25);
    cmDesRecipeBox[1].ccSetLocation(cmDesRecipeBox[0], 0, 5);
    cmDesRecipeBox[2].ccSetLocation(cmDesRecipeBox[1], 0, 3);
    cmDesRecipeBox[3].ccSetLocation(cmDesRecipeBox[2], 0, 3);
    
    cmDesNameBox[0].ccSetLocation(cmDesRecipeBox[0], 8, 0);
    cmDesNameBox[1].ccSetLocation(cmDesNameBox[0], 0, 5);
    cmDesNameBox[2].ccSetLocation(cmDesNameBox[1], 0, 3);
    cmDesNameBox[3].ccSetLocation(cmDesNameBox[2], 0, 3);
    
    cmDesKGBox[0].ccSetLocation(cmDesNameBox[0], 8, 0);
    cmDesKGBox[1].ccSetLocation(cmDesKGBox[0], 0, 5);
    cmDesKGBox[2].ccSetLocation(cmDesKGBox[1], 0, 3);
    cmDesKGBox[3].ccSetLocation(cmDesKGBox[2], 0, 3);
    
    cmDesBatchBox[0].ccSetLocation(cmDesKGBox[0], 2, 0);
    cmDesBatchBox[1].ccSetLocation(cmDesBatchBox[0], 0, 5);
    cmDesBatchBox[2].ccSetLocation(cmDesBatchBox[1], 0, 3);
    cmDesBatchBox[3].ccSetLocation(cmDesBatchBox[2], 0, 3);
        
    //-- switch
    cmManualSW.ccSetLocation(cmDesRecipeBox[3], 0, 24);
    cmAutoSW.ccSetLocation(cmManualSW, 8, 0);
    cmManualAutoRange.ccSetLocation(cmManualSW, -2, -2);
    cmManualAutoRange.ccSetEndPoint(cmAutoSW, 3, 3);
    
    cmRunSW.ccSetLocation(
      cmDesBatchBox[0].ccEndX()-cmRunSW.ccGetW()-2,
      cmAutoSW.ccGetY()
    );
    
  }//++!
  
  public final void ccSetupLocation(EcRect pxBound){
    cmPane.ccSetSize(pxBound);
    ccSetupLocation(pxBound.ccGetX(), pxBound.ccGetY());
  }//+++
  
  //===
  
  public final EcRect ccGetBound(){return cmPane;}
  
  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    for(int i=0;i<cmDesRecipeBox.length;i++){
      lpRes.add(cmDesRecipeBox[i]);
      lpRes.add(cmDesNameBox[i]);
      lpRes.add(cmDesKGBox[i]);
      lpRes.add(cmDesBatchBox[i]);
    }//.~
    lpRes.add(cmRunSW);
    lpRes.add(cmManualSW);
    lpRes.add(cmAutoSW);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    lpRes.add(cmManualAutoRange);
    return lpRes;
  }//+++
  
}//***eof
