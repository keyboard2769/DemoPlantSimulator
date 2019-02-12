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

package pppunit;

import kosui.ppplocalui.EcShape;

public class EcErrorSlotBar extends EcShape{
  
  private String cmMessage="--";

  public EcErrorSlotBar(){
    super();
    ccSetBaseColor(0xAA333333);
  }//++!
  
  @Override public void ccUpdate(){
    
    pbOwner.fill(0xCC,0x33);
    pbOwner.rect(cmX, cmY, cmW, cmH);
    
    pbOwner.fill(0xEE);
    pbOwner.text(cmMessage, cmX+4, cmY+2);
    
  }//+++
  
  public final void ccSetMessate(String pxMessage){
    cmMessage=pxMessage;
  }//+++
  
}//***
