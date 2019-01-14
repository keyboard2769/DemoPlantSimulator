/*
 * Copyright (C) 2019 Key Parker
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

import kosui.ppplocalui.EcElement;
import static processing.core.PApplet.hour;
import static processing.core.PApplet.minute;
import static processing.core.PApplet.nf;

public class EcClockButton extends EcElement{

  public EcClockButton(){
    super();
    ccSetSize(48,17);
  }//++!
  
  @Override public void ccUpdate(){
    
    pbOwner.fill(ccIsMouseHovered()?0xAA:0x99);
    pbOwner.rect(cmX, cmY, cmW, cmH);
    
    cmText=nf(hour(),2)+"  "+nf(minute(),2);
    drawText(0x11);
    
    if(cmIsActivated){
      pbOwner.text(":", ccCenterX()-6, cmY);
    }
    
  }//+++
  
}//***eof
