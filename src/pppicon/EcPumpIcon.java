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

public class EcPumpIcon extends EcMotorIcon{

  private String cmDirectionText;

  public EcPumpIcon(){
    super();
    cmDirectionText="-";
    ccSetColor(EcFactory.C_DIM_YELLOW, EcFactory.C_DIM_GRAY);
  }//++!

  @Override
  public void ccUpdate(){
    super.ccUpdate();

    pbOwner.stroke(EcFactory.C_WHITE);
    ccActFill();
    pbOwner.rect(cmX, cmY+cmH+1, cmW, cmH, cmH/2);
    pbOwner.noStroke();
    pbOwner.fill(EcFactory.C_DARK_GRAY);
    pbOwner.text(cmDirectionText, cmX+5, cmY+cmH-1);

  }//***

  public final void ccSetDirectionText(char pxMode_lrx){
    switch(pxMode_lrx){
      case 'l': cmDirectionText="<";
        break;
      case 'r': cmDirectionText=">";
        break;
      default: cmDirectionText="-";
        break;
    }
  }//+++

  public final int ccPipeY(){
    return cmY+cmH+cmH/2;
  }//+++

}//***eof
