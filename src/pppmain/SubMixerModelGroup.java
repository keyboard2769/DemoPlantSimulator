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
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppunit.EcMixer;
import pppunit.EcUnitFactory;

public class SubMixerModelGroup implements EiGroup{
    
  private static SubMixerModelGroup self;
  public static SubMixerModelGroup ccGetReference(){
    if(self==null){self=new SubMixerModelGroup();}
    return self;
  }//++!
  
  //===
  
  public final EcMixer cmMixer;
  public final EcValueBox cmWetTimerBox,cmDryTimerBox,cmTempratureBox;
  
  private final EcShape cmPane;
  
  private SubMixerModelGroup(){
    
    cmPane=new EcShape();
    cmPane.ccSetBaseColor(EcUnitFactory.C_C_MODEL_PANE);
    
    //-- box
    cmWetTimerBox=EcUnitFactory
      .ccCreateSettingValueBox("000S", "S");
    cmWetTimerBox.ccSetValue(2, 2);
    cmWetTimerBox.ccSetName("W");
    cmWetTimerBox.ccSetNameAlign('l');
    
    cmDryTimerBox=EcUnitFactory
      .ccCreateSettingValueBox("000S", "S");
    cmDryTimerBox.ccSetValue(8, 2);
    cmDryTimerBox.ccSetName("D");
    cmDryTimerBox.ccSetNameAlign('l');
    
    cmTempratureBox=EcUnitFactory
      .ccCreateTemperatureValueBox("-000'C", "'C");
    cmTempratureBox.ccSetValue(9,3);
    
    //-- mixer
    cmMixer=new EcMixer
      ("mixer", cmTempratureBox.ccGetW()*2 ,EcFactory.C_ID_IGNORE);
    
  }//++!
  
  public final void ccSetupLocation(int pxX, int pxY){
    
    cmPane.ccSetLocation(pxX, pxY);
    
    cmMixer.ccSetupLocation(
      cmPane.ccGetX()+(cmPane.ccGetW()-cmMixer.ccGetW())/2,
      cmPane.ccGetY()+5
    );
    
    cmDryTimerBox.ccSetLocation(cmMixer, -5, 16);
    cmWetTimerBox.ccSetLocation(cmDryTimerBox, 0, 2);
    
    cmTempratureBox.ccSetLocation(
      cmPane.ccCenterX()+5,
      cmWetTimerBox.ccGetY()
    );
    
  }//++!
  
  
  public final void ccSetupLocation(EcRect pxBound){
    cmPane.ccSetSize(pxBound);
    ccSetupLocation(pxBound.ccGetX(), pxBound.ccGetY());
  }//++!
  
  /*
  
    cmTempratureBox.ccSetLocation(cmWetTimerBox, C_GAP*2+5,0);
    cmDryTimerBox.ccSetLocation(cmX+1, cmY+C_GAP*2+C_LED_H);
    cmWetTimerBox.ccSetLocation(cmDryTimerBox, 0, 2);
    cmTempratureBox.ccSetLocation(cmWetTimerBox, 20, 0);
  
  
  */
  
  //===
  
  public final EcRect ccGetBound(){return cmPane;}
  
  @Override public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmMixer);
    lpRes.add(cmWetTimerBox);
    lpRes.add(cmDryTimerBox);
    lpRes.add(cmTempratureBox);
    return lpRes;
  }//+++

  @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    return lpRes;
  }//+++
  
}//***eof
