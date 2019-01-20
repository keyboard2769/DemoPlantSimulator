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
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import static pppmain.MainLocalCoordinator.C_ID_MIXER_GATE_AUTO;
import static pppmain.MainLocalCoordinator.C_ID_MIXER_GATE_HOLD;
import static pppmain.MainLocalCoordinator.C_ID_MIXER_GATE_OPEN;

public class SubMixerControlGourp implements EiGroup{
  
  private static SubMixerControlGourp self;
  public static SubMixerControlGourp ccGetReference(){
    if(self==null){self=new SubMixerControlGourp();}
    return self;
  }//++!
  
  //===
  
  private final EcPane cmPane;//...
  
  public final EcButton cmAUTO,cmHOLD,cmOPEN;//...
  
  private SubMixerControlGourp(){
    
    cmPane=new EcPane();
    cmPane.ccSetTitle("Mixer-G");
    
    cmAUTO=EcFactory.ccCreateButton("AUTO", C_ID_MIXER_GATE_AUTO);
    cmHOLD=EcFactory.ccCreateButton("HOLD", C_ID_MIXER_GATE_HOLD);
    cmOPEN=EcFactory.ccCreateButton("OPEN", C_ID_MIXER_GATE_OPEN);
    
    cmAUTO.ccSetSize(null, 12, 0);
    cmHOLD.ccSetSize(cmAUTO);
    cmOPEN.ccSetSize(cmAUTO);
    
  }//+++ 
  
  //===
  
  public final void ccSetupLocation(int pxStartX, int pxStartY){
    cmPane.ccSetLocation(pxStartX, pxStartY);
    cmAUTO.ccSetLocation(cmPane, 5, 25);
    cmHOLD.ccSetLocation(cmAUTO,0, 2);
    cmOPEN.ccSetLocation(cmHOLD,0, 2);
    cmPane.ccSetSize(80, 160);
  }//+++
  
  public final EcRect ccGetPaneBound(){return cmPane;}//+++
  
  //===

  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmAUTO);
    lpRes.add(cmHOLD);
    lpRes.add(cmOPEN);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    return lpRes;
  }//+++
  
}//***eof
