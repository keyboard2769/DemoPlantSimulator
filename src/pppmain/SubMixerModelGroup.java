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
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppicon.EcPumpIcon;
import pppshape.EcDuctShape;
import pppunit.EcMixer;
import pppunit.EcUnitFactory;

public class SubMixerModelGroup implements EiGroup{
  
  public final EcButton cmMixerDischargeSW;
  
  public final EcPumpIcon cmASSprayPump;
  
  public final EcMixer cmMixer;
  
  EcDuctShape lpASSprayPipe;
  
  public SubMixerModelGroup(){
    
    int lpX=520;
    int lpY=480;
    
    //-- mixer
    cmMixer=new EcMixer("mixer", lpX,lpY,1650);
    
    //-- as spray pump
    lpASSprayPipe = new EcDuctShape();
    lpASSprayPipe.ccSetLocation(cmMixer, 60, -12);
    lpASSprayPipe.ccSetSize(80, 80);
    lpASSprayPipe.ccSetDirection('b');
    lpASSprayPipe.ccSetCut(20);
    lpASSprayPipe.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_DUCT);
    
    cmASSprayPump=new EcPumpIcon();
    cmASSprayPump.ccSetLocation(lpASSprayPipe, 1, 0);
    cmASSprayPump.ccShiftLocation(-4, 60);
    
    //-- mixer button
    int lpDischargeSwitchGap=2;
    cmMixerDischargeSW=EcUnitFactory.ccCreateDischargeSW("MX", 1999);
    cmMixerDischargeSW.ccSetLocation(cmMixer, 0, lpDischargeSwitchGap);
    cmMixerDischargeSW.ccSetSize(cmMixer.ccGetW(),0);
    
  }//+++ 
  
  @Override
  public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    
    lpRes.add(cmMixer);
    lpRes.add(cmASSprayPump);
    lpRes.add(cmMixerDischargeSW);
    
    return lpRes;
  }//+++

  @Override
  public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(lpASSprayPipe);
    return lpRes;
  }//+++
  
}//***eof
