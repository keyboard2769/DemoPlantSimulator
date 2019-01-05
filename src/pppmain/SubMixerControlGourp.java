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
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import static pppmain.MainLocalCoordinator.C_ID_MIXER_GATE_AUTO;
import static pppmain.MainLocalCoordinator.C_ID_MIXER_GATE_HOLD;
import static pppmain.MainLocalCoordinator.C_ID_MIXER_GATE_OPEN;

public class SubMixerControlGourp implements EiGroup{
  
  private final EcPane cmPane;//...
  private final EcButton cmAUTO,cmHOLD,cmOPEN;//...
  
  public SubMixerControlGourp(){
    
    cmPane=new EcPane();
    cmPane.ccSetLocation(680, 480);
    cmPane.ccSetTitle("Mixer-G");
    
    cmAUTO=EcFactory.ccCreateButton("AUTO", C_ID_MIXER_GATE_AUTO);
    cmHOLD=EcFactory.ccCreateButton("HOLD", C_ID_MIXER_GATE_HOLD);
    cmOPEN=EcFactory.ccCreateButton("OPEN", C_ID_MIXER_GATE_OPEN);
    
    cmAUTO.ccSetSize(null, 12, 0);
    cmHOLD.ccSetSize(cmAUTO);
    cmOPEN.ccSetSize(cmAUTO);
    
    cmAUTO.ccSetLocation(cmPane, 5, 25);
    cmHOLD.ccSetLocation(cmAUTO,0, 2);
    cmOPEN.ccSetLocation(cmHOLD,0, 2);
    
    cmPane.ccSetEndPoint(cmOPEN, 5, 5);
    
  }//+++ 
  

  @Override
  public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmAUTO);
    lpRes.add(cmHOLD);
    lpRes.add(cmOPEN);
    return lpRes;
  }//+++

  @Override
  public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    return lpRes;
  }//+++
  
}//***eof
