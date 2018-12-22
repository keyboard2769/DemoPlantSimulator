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
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CHORD;
import static processing.core.PConstants.CORNER;
import static processing.core.PConstants.PI;

public class EcControlMotorIcon extends EcDoubleSolenoidIcon{

  private static final float C_CUT=0.2f;

  private static final int C_GAP=2;

  private float cmDegree;

  public EcControlMotorIcon(){
    super();
    cmDegree=0.1f;
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

    pbOwner.fill(EcFactory.C_LIT_GRAY);
    pbOwner.rectMode(CENTER);
    pbOwner.pushMatrix();
    {
      pbOwner.translate(cmX, cmY);
      pbOwner.rotate(cmDegree);
      pbOwner.rect(0, 0, cmW+C_GAP*4, C_GAP);
    }
    pbOwner.popMatrix();
    pbOwner.rectMode(CORNER);

    pbOwner.fill(cmFAS?EcFactory.C_WHITE:EcFactory.C_DIM_GRAY);
    pbOwner.arc(cmX, cmY, cmW-C_GAP*2, cmW-C_GAP*2, PI+C_CUT, 2*PI-C_CUT, CHORD);
    pbOwner.fill(cmCAS?EcFactory.C_RED:EcFactory.C_DIM_GRAY);
    pbOwner.arc(cmX, cmY, cmW-C_GAP*2, cmW-C_GAP*2, C_CUT, PI-C_CUT, CHORD);

    pbOwner.fill(EcFactory.C_GRAY);
    pbOwner.ellipse(cmX, cmY, cmW-C_GAP*5, cmH-C_GAP*5);

  }//+++

  public final void ccSetDegree(int pxPercentage){
    cmDegree=PI*((float)pxPercentage)/200.0f;
  }//+++

}//***eof
