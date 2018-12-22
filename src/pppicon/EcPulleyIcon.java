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

package pppicon;

import kosui.ppplocalui.EcFactory;

public class EcPulleyIcon extends EcMotorIcon{
  
  public EcPulleyIcon(){
    super();
    ccSetSize(8,8);
  }//++!

  @Override
  public void ccUpdate(){

    int lpColor=EcFactory.C_DARK_GRAY;
    
    if(cmMC){lpColor=EcFactory.C_DARK_ORANGE;}
    if(cmAN){lpColor=EcFactory.C_DARK_GREEN;}
    if(cmMC&&cmAN){lpColor=EcFactory.C_YELLOW;}
    if(cmAL){lpColor=EcFactory.C_DARK_RED;}
    pbOwner.fill(lpColor);
    pbOwner.ellipse(ccCenterX(), ccCenterY(), cmW, cmH);

  }//+++

}//***eof
