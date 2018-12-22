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

import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;
import static processing.core.PConstants.CHORD;
import static processing.core.PConstants.PI;

public class EcSingleSolenoidIcon extends EcElement{

  private static final float C_CUT=0.2f;

  private static final int C_GAP=2;

  protected boolean
    cmUP, cmFAS, cmCAS;

  public EcSingleSolenoidIcon(){
    super();
    ccSetSize(16, 16);
  }//++!

  @Override
  public void ccUpdate(){

    pbOwner.strokeWeight(C_GAP);
    {
      pbOwner.stroke(EcFactory.C_LIT_GRAY);
      pbOwner.fill(EcFactory.C_DIM_GRAY);
      pbOwner.ellipse(cmX, cmY, cmW-1, cmH-1);
    }
    pbOwner.strokeWeight(1);
    pbOwner.noStroke();

    pbOwner.fill(cmFAS?EcFactory.C_WHITE:EcFactory.C_DIM_GRAY);
    pbOwner.arc
      (cmX, cmY, cmW-C_GAP*2, cmW-C_GAP*2, PI+C_CUT, 2*PI-C_CUT, CHORD);
    pbOwner.fill(cmCAS?EcFactory.C_RED:EcFactory.C_DIM_GRAY);
    pbOwner.arc
      (cmX, cmY, cmW-C_GAP*2, cmW-C_GAP*2, C_CUT, PI-C_CUT, CHORD);

    pbOwner.fill(cmUP?EcFactory.C_ORANGE:EcFactory.C_LIT_GRAY);
    pbOwner.ellipse(cmX, cmY, cmW-C_GAP*5, cmH-C_GAP*5);

  }//+++

  public final void ccSetIsOpening(boolean pxStatus){
    cmUP=pxStatus;
  }//+++

  public final void ccSetIsFull(boolean pxStatus){
    cmFAS=pxStatus;
  }//+++

  public final void ccSetIsClosed(boolean pxStatus){
    cmCAS=pxStatus;
  }//+++

}//***eof
