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

import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcShape;
import static processing.core.PConstants.PI;
import static processing.core.PConstants.PIE;

public class EcMixerGateIcon extends EcShape{
  
  private boolean cmMV=false,cmMOL=false,cmMCL=false;

  @Override public void ccUpdate(){
    
    //[TEST]::
    //bOwner.fill(0xFF663333);
    //pbOwner.rect(cmX,cmY,cmW,cmH);
    
    pbOwner.stroke(cmMV?EcFactory.C_YELLOW:EcFactory.C_GRAY);
    pbOwner.fill((cmMOL||cmMCL)?EcFactory.C_DIM_GREEN:EcFactory.C_DIM_GRAY);
    pbOwner.strokeWeight(2);
    
    int lpOffset=cmMCL?0:(cmW/8);
    pbOwner.arc(cmX-lpOffset, cmY, cmW, cmH, PI/2+0.1f, PI*3/2-0.1f,PIE);
    pbOwner.arc(cmX+lpOffset, cmY, cmW, cmH, -PI/2+0.1f, PI/2-0.1f,PIE);
    
    pbOwner.strokeWeight(1);
    pbOwner.noStroke();
    
  }//+++
  
  public final void ccSetIsOpening(boolean pxStatus){cmMV=pxStatus;}//+++
  public final void ccSetIsOpened(boolean pxStatus){cmMOL=pxStatus;}//+++
  public final void ccSetIsClosed(boolean pxStatus){cmMCL=pxStatus;}//+++
  
}//**eof

