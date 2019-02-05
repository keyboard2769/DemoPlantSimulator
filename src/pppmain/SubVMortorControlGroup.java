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
import java.util.Arrays;
import kosui.ppplocalui.EcButton;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcPane;
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import static processing.core.PApplet.nf;

public class SubVMortorControlGroup implements EiGroup{

  private static SubVMortorControlGroup self;
  public static SubVMortorControlGroup ccGetReference(){
    if(self==null){self=new SubVMortorControlGroup();}
    return self;
  }//++!
  
  //===
  
  private final EcPane cmPane;
  public final EcButton[] cmMotorSW;
  
  private SubVMortorControlGroup(){
    
    cmPane=new EcPane();
    cmPane.ccSetTitle("V-Motor");
    
    int lpVMSwitchW=50;
    int lpVMSwitchH=50;
    
    cmMotorSW=new EcButton[15];
    for(int i=0;i<cmMotorSW.length;i++){
      cmMotorSW[i]=new EcButton();
      cmMotorSW[i].ccSetupKey("VM"+nf(i,2));
      cmMotorSW[i].ccSetID(MainLocalCoordinator.C_ID_VMSW_HEAD+i);
      cmMotorSW[i].ccSetSize(lpVMSwitchW, lpVMSwitchH);
    }//..~
    
    //-- rename
    cmMotorSW[0].ccSetText("V\nCOMP");
    cmMotorSW[6].ccSetText("MIXER");
    cmMotorSW[12].ccSetText("V\nEXF");
    cmMotorSW[3].ccSetText("AP\nBLW");
    cmMotorSW[9].ccSetText("AG\nSUPP");
    
    cmMotorSW[10].ccSetText("AS\nSUPP");
    cmMotorSW[7].ccSetText("FR\nSYS");
    cmMotorSW[13].ccSetText("VF\nSTA");
    
    cmMotorSW[5].ccSetText("DUST\nEXT");
    cmMotorSW[11].ccSetText("VB\nCOMP");
    
  }//+++ 
  
  public final void ccSetupLocation(int pxX, int pxY){
    cmPane.ccSetLocation(pxX, pxY);
    cmMotorSW[0].ccSetLocation(cmPane, 5, 22);
    EcRect.ccGridLayout(
      new ArrayList<>(Arrays.asList(cmMotorSW)),
      cmMotorSW[0].ccGetW()+2, cmMotorSW[0].ccGetH()+2,
      5, 3
    );
  }//++!
  
  public final void ccSetupLocation(EcRect pxBound){
    cmPane.ccSetSize(pxBound);
    ccSetupLocation(pxBound.ccGetX(), pxBound.ccGetY());
  }//++!
  
  //===
  
  public final EcRect ccGetBound(){return cmPane;}

  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    for(EcButton it : cmMotorSW){lpRes.add(it);}//..~
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    return lpRes;
  }//+++
  
}//***eof
