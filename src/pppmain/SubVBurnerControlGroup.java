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
import kosui.ppplocalui.EcButton;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcPane;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppicon.EcStageBox;
import static pppunit.EcUnitFactory.ccCreateSingleCharacterSW;

public class SubVBurnerControlGroup implements EiGroup{
  
  public final EcButton
    cmVBurnerCLoseSW,cmVBurnerOpenSW,cmVBurnerAutoSW,
    cmVExfanCloseSW,cmVExfanOpenSW,cmVExfanAutoSW,
    cmVBIgnitSW
  ;//...
  
  public final EcStageBox cmVBurnerStagePL;
  
  private final EcPane
    cmPane
  ;//...
  
  public SubVBurnerControlGroup(){
    
    cmPane=new EcPane();
    cmPane.ccSetTitle("V-Burner");
    
    //-- burner degree
    cmVBurnerCLoseSW=ccCreateSingleCharacterSW
      ("-", MainLocalCoordinator.C_ID_VBCLSW);
    cmVBurnerCLoseSW.ccSetName("VB");
    cmVBurnerCLoseSW.ccSetNameAlign('l');
    
    cmVBurnerOpenSW=ccCreateSingleCharacterSW
      ("+", MainLocalCoordinator.C_ID_VBOPSW);
    
    cmVBurnerAutoSW=ccCreateSingleCharacterSW
      ("#", MainLocalCoordinator.C_ID_VBATSW);
    
    //-- vexf degree
    cmVExfanCloseSW=ccCreateSingleCharacterSW
      ("-", MainLocalCoordinator.C_ID_VEXFCLSW);
    cmVExfanCloseSW.ccSetName("VE");
    cmVExfanCloseSW.ccSetNameAlign('l');
    
    cmVExfanOpenSW=ccCreateSingleCharacterSW
      ("+", MainLocalCoordinator.C_ID_VEXFOPSW);
    
    cmVExfanAutoSW=ccCreateSingleCharacterSW
      ("#", MainLocalCoordinator.C_ID_VEXFATSW);
    
    //-- vb ignit
    cmVBurnerStagePL=new EcStageBox();
    cmVBurnerStagePL.ccAddStage("READY");
    cmVBurnerStagePL.ccAddStage("C-LEAK");
    cmVBurnerStagePL.ccAddStage("PRE-P");
    cmVBurnerStagePL.ccAddStage("IG");
    cmVBurnerStagePL.ccAddStage("PV");
    cmVBurnerStagePL.ccAddStage("MMV");
    cmVBurnerStagePL.ccAddStage("POST-P");
    cmVBurnerStagePL.ccSetText( "--mv--");
    cmVBurnerStagePL.ccSetSize();
    
    cmVBIgnitSW=new EcButton();
    cmVBIgnitSW.ccTakeKey("V-IGN");
    cmVBIgnitSW.ccSetID(MainLocalCoordinator.C_ID_VBIGN);
    cmVBIgnitSW.ccSetNameAlign('x');
    cmVBIgnitSW.ccSetSize(cmVBurnerStagePL, 1, 2);
    
    //-- resetting 
    ccSetLocation(10, 300);
    cmPane.ccSetEndPoint(cmVBIgnitSW,12, 12);
    
  }//+++ 
  
  public final void ccSetLocation(int pxX, int pxY){
    
    cmPane.ccSetLocation(pxX, pxY);
    cmVBurnerCLoseSW.ccSetLocation(cmPane, 5+22, 25);
    cmVBurnerOpenSW.ccSetLocation(cmVBurnerCLoseSW, 1, 0);
    cmVBurnerAutoSW.ccSetLocation(cmVBurnerOpenSW,4,0);
    cmVExfanCloseSW.ccSetLocation(cmVBurnerCLoseSW,0, 4);
    cmVExfanOpenSW.ccSetLocation(cmVExfanCloseSW, 1, 0);
    cmVExfanAutoSW.ccSetLocation(cmVExfanOpenSW, 4, 0);
    cmVBurnerStagePL.ccSetLocation(cmVBurnerAutoSW, 16, 0);
    cmVBIgnitSW.ccSetLocation(cmVBurnerStagePL, 0, 1);
    
  }//+++

  @Override
  public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmVBurnerCLoseSW);
    lpRes.add(cmVBurnerOpenSW);
    lpRes.add(cmVBurnerAutoSW);
    lpRes.add(cmVExfanCloseSW);
    lpRes.add(cmVExfanOpenSW);
    lpRes.add(cmVExfanAutoSW);
    lpRes.add(cmVBurnerStagePL);
    lpRes.add(cmVBIgnitSW);
    return lpRes;
  }//+++

  @Override
  public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    return lpRes;
  }//+++
  
}//***eof
