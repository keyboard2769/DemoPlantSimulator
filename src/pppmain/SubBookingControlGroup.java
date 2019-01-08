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
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_AUTO;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_CANCEL;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_MANN;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_PAUSE;
import static pppmain.MainLocalCoordinator.C_ID_WEIGH_RUN;
import pppunit.EcUnitFactory;

public class SubBookingControlGroup implements EiGroup{
  
  private final EcPane cmPane;//...
  private final EcShape cmCurrentRange,cmAutoManualRange,cmReadyRunRange;
  
  public final EcValueBox[] cmDesNumberBox,cmDesTonBox,cmDesBatchBox;
  public final EcButton cmAutoSW, cmManualSW, cmRunSW,cmCancelSW,cmPauseSW;
  public final EcElement cmReadyPL;
  
  //===
  
  public SubBookingControlGroup(){
    
    cmPane=new EcPane();
    cmPane.ccSetLocation(290, 445);
    cmPane.ccSetTitle("A-Booking");
    
    //-- dummy table 
    cmCurrentRange=new EcShape();
    cmCurrentRange.ccSetBaseColor(EcFactory.C_DARK_YELLOW);
    cmCurrentRange.ccSetLocation(cmPane, 5, 22);
    
    cmDesNumberBox=new EcValueBox[3];
    for(int i=0;i<cmDesNumberBox.length;i++){
      cmDesNumberBox[i]=EcUnitFactory.ccCreateSettingValueBox("00/", "/");
      cmDesNumberBox[i].ccSetValue(i, 2);
      cmDesNumberBox[i].ccSetSize(null, 18, -4);
    }//..~
    cmDesNumberBox[0].ccSetLocation(cmCurrentRange, 2, 2);
    cmDesNumberBox[1].ccSetLocation(cmDesNumberBox[0], 0, 5);
    cmDesNumberBox[2].ccSetLocation(cmDesNumberBox[1], 0, 3);
    
    cmDesTonBox=new EcValueBox[3];
    for(int i=0;i<cmDesTonBox.length;i++){
      cmDesTonBox[i]=EcUnitFactory.ccCreateSettingValueBox("0000kg", "kg");
      cmDesTonBox[i].ccSetValue(0, 4);
      cmDesTonBox[i].ccSetSize(null, 0, -4);
    }//..~
    cmDesTonBox[0].ccSetLocation(cmDesNumberBox[0], 23, 0);
    cmDesTonBox[1].ccSetLocation(cmDesTonBox[0], 0, 5);
    cmDesTonBox[2].ccSetLocation(cmDesTonBox[1], 0, 3);
    
    cmDesBatchBox=new EcValueBox[3];
    for(int i=0;i<cmDesBatchBox.length;i++){
      cmDesBatchBox[i]=EcUnitFactory.ccCreateSettingValueBox("0000b", "b");
      cmDesBatchBox[i].ccSetValue(0, 4);
      cmDesBatchBox[i].ccSetSize(null,0,-4);
    }//..~
    cmDesBatchBox[0].ccSetLocation(cmDesTonBox[0], 2, 0);
    cmDesBatchBox[1].ccSetLocation(cmDesBatchBox[0], 0, 5);
    cmDesBatchBox[2].ccSetLocation(cmDesBatchBox[1], 0, 3);
    
    cmDesNumberBox[0].ccSetColor(EcFactory.C_LIT_YELLOW, EcFactory.C_YELLOW);
    cmDesTonBox[0].ccSetColor(EcFactory.C_LIT_YELLOW, EcFactory.C_YELLOW);
    cmDesBatchBox[0].ccSetColor(EcFactory.C_LIT_YELLOW, EcFactory.C_YELLOW);
    cmDesNumberBox[0].ccSetTextColor(EcFactory.C_DIM_GRAY);
    cmDesTonBox[0].ccSetTextColor(EcFactory.C_DIM_GRAY);
    cmDesBatchBox[0].ccSetTextColor(EcFactory.C_DIM_GRAY);
    
    cmCurrentRange.ccSetEndPoint(cmDesBatchBox[0], 3, 3);
    
    //-- auto vs manual
    cmAutoManualRange=new EcShape();
    cmAutoManualRange.ccSetBaseColor(EcFactory.C_DIM_BLUE);
    cmAutoManualRange.ccSetLocation(cmDesNumberBox[2], 0, 5);
    cmAutoManualRange.ccSetLocation(null, -2, 0);
    
    cmAutoSW=EcFactory.ccCreateButton("AUTO", C_ID_WEIGH_AUTO);
    cmAutoSW.ccSetSize(null, 0, -4);
    cmAutoSW.ccSetLocation(cmAutoManualRange, 2, 2);
    
    cmManualSW=EcFactory.ccCreateButton("MANN", C_ID_WEIGH_MANN);
    cmManualSW.ccSetSize(cmAutoSW);
    cmManualSW.ccSetLocation(cmAutoSW, 2, 0);
    
    cmAutoManualRange.ccSetEndPoint(cmManualSW, 3, 3);
    
    //-- auto weigh switch
    cmReadyRunRange=new EcShape();
    cmReadyRunRange.ccSetBaseColor(EcFactory.C_DIM_BLUE);
    cmReadyRunRange.ccSetLocation(cmAutoManualRange, 0, 2);
    
    cmReadyPL=new EcElement();
    cmReadyPL.ccTakeKey("READY");
    cmReadyPL.ccSetNameAlign('x');
    cmReadyPL.ccSetColor(EcFactory.C_YELLOW, EcFactory.C_GRAY);
    
    cmPauseSW=EcFactory.ccCreateButton("PAUSE", C_ID_WEIGH_PAUSE);
    cmCancelSW=EcFactory.ccCreateButton("CANCEL", C_ID_WEIGH_CANCEL);
    cmRunSW=EcFactory.ccCreateButton("RUN", C_ID_WEIGH_RUN);
    
    cmCancelSW.ccSetSize(null,4,0);
    cmCancelSW.ccSetSize(cmAutoSW, false, true);
    cmPauseSW.ccSetSize(cmCancelSW);
    cmRunSW.ccSetSize(cmCancelSW);
    cmReadyPL.ccSetSize(cmRunSW);
    
    cmReadyPL.ccSetLocation(cmManualSW, 35, 0);
    cmCancelSW.ccSetLocation(cmReadyRunRange, 3, 3);
    cmPauseSW.ccSetLocation(cmCancelSW, 3, 0);
    cmRunSW.ccSetLocation(cmPauseSW, 3, 0);
    
    //--pack
    cmReadyRunRange.ccSetEndPoint(cmRunSW, 3, 3);
    cmPane.ccSetEndPoint(cmReadyRunRange,5, 5);
    
  }//+++ 
  
  //===
  
  @Override
  public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmDesNumberBox[0]);
    lpRes.add(cmDesNumberBox[1]);
    lpRes.add(cmDesNumberBox[2]);
    lpRes.add(cmDesTonBox[0]);
    lpRes.add(cmDesTonBox[1]);
    lpRes.add(cmDesTonBox[2]);
    lpRes.add(cmDesBatchBox[0]);
    lpRes.add(cmDesBatchBox[1]);
    lpRes.add(cmDesBatchBox[2]);
    lpRes.add(cmAutoSW);
    lpRes.add(cmManualSW);
    lpRes.add(cmReadyPL);
    lpRes.add(cmCancelSW);
    lpRes.add(cmPauseSW);
    lpRes.add(cmRunSW);
    return lpRes;
  }//+++

  @Override
  public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    lpRes.add(cmCurrentRange);
    lpRes.add(cmAutoManualRange);
    lpRes.add(cmReadyRunRange);
    return lpRes;
  }//+++
  
}//***eof