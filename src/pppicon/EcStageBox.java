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
import java.util.ArrayList;
import kosui.ppplocalui.EcFactory;
import static processing.core.PApplet.constrain;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

public class EcStageBox extends EcElement{
  
  private int cmStage;
  private final ArrayList<String> cmTextList;

  public EcStageBox(){
    
    super();
    cmStage=0;
    cmTextList=new ArrayList<>(8);
    cmTextList.add("off");
    
  }//++!

  @Override public void ccUpdate(){
    
    ccActFill();
    pbOwner.stroke(EcFactory.C_LIT_GRAY);
    pbOwner.rect(cmX,cmY,cmW,cmH);
    pbOwner.noStroke();
    
    pbOwner.fill(cmTextColor);
    pbOwner.textAlign(CENTER, CENTER);
    pbOwner.text(cmTextList.get(cmStage),ccCenterX(),ccCenterY());
    pbOwner.textAlign(LEFT,TOP);
    
    ccDrawName(cmNameColor);
      
  }//+++
  
  public final void ccAddStage(String pxStageText){
    cmTextList.add(pxStageText);
  }//+++
  
  public final void ccSetStage(int pxStage){
    cmStage=constrain(pxStage,0,cmTextList.size()-1);  
    ccSetIsActivated(pxStage>0);
  }//+++
  
}//***eof
