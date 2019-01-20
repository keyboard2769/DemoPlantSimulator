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
import kosui.ppplocalui.EcTriangleLamp;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;
import pppicon.EcMotorIcon;
import pppicon.EcPumpIcon;
import pppshape.EcDuctShape;
import pppunit.EcUnitFactory;

public class SubASSupplyModelGroup implements EiGroup{
  
  private static SubASSupplyModelGroup self;
  public static SubASSupplyModelGroup ccGetReference(){
    if(self==null){self=new SubASSupplyModelGroup();}
    return self;
  }//++!

  //===
  
  public final EcElement cmASP;
  
  public final EcPumpIcon cmASSupplyPump;
  public final EcMotorIcon cmASSprayPump;
  public final EcTriangleLamp cmASD,cmAS1;
  public final EcValueBox cmPipeTempratureBox;
  
  private final EcDuctShape cmASSupplyPipe;
  
  private SubASSupplyModelGroup(){
    
    cmASSprayPump=new EcMotorIcon();
    cmASSprayPump.ccSetLocation(728, 271);
    
    cmASD=new EcTriangleLamp();
    cmASD.ccSetSize(9,9);
    cmASD.ccSetColor(EcFactory.C_ORANGE);
    cmASD.ccSetDirection('r');
    cmASD.ccSetNameAlign('x');
    cmASD.ccSetText(" ");
    cmASD.ccSetLocation(cmASSprayPump, -12, 1);
    
    cmASP=new EcElement();
    cmASP.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_LIT_GRAY);
    cmASP.ccSetTextColor(EcFactory.C_DARK_GRAY);
    cmASP.ccSetText("ASP");
    cmASP.ccSetSize();
    cmASP.ccSetSize(null, -6, -10);
    cmASP.ccSetNameAlign('x');
    cmASP.ccSetLocation(cmASD, -16, -12);
    
    cmASSupplyPipe = new EcDuctShape();
    cmASSupplyPipe.ccSetLocation(728, 311);
    cmASSupplyPipe.ccSetSize(80, 20);
    cmASSupplyPipe.ccSetDirection('a');
    cmASSupplyPipe.ccSetCut(70);
    cmASSupplyPipe.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_DUCT);
    
    cmASSupplyPump=new EcPumpIcon();
    cmASSupplyPump.ccSetLocation(cmASSupplyPipe, 0,-16);
    cmASSupplyPump.ccSetDirectionText('r');
    
    cmAS1=new EcTriangleLamp();
    cmAS1.ccSetSize(9,9);
    cmAS1.ccSetColor(EcFactory.C_ORANGE);
    cmAS1.ccSetDirection('r');
    cmAS1.ccSetName("AS1");
    cmAS1.ccSetNameAlign('b');
    cmAS1.ccSetText(" ");
    cmAS1.ccSetLocation(cmASSupplyPump, 23, 13);
    
    cmPipeTempratureBox=EcUnitFactory
      .ccCreateTempratureValueBox("-000'C", "'C");
    cmPipeTempratureBox.ccSetValue(92, 3);
    cmPipeTempratureBox.ccSetLocation(cmAS1, 14, 12);
    
  }//+++ 
  
  @Override
  public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    lpRes.add(cmASSupplyPump);
    lpRes.add(cmAS1);
    lpRes.add(cmASSprayPump);
    lpRes.add(cmASD);
    lpRes.add(cmASP);
    lpRes.add(cmPipeTempratureBox);
    return lpRes;
  }//+++

  @Override
  public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmASSupplyPipe);
    return lpRes;
  }//+++
  
}//***eof
