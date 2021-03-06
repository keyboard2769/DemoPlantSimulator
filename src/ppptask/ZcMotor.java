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

package ppptask;

import kosui.ppplogic.ZcRangedValueModel;
import static ppptask.ZcTask.sysOwner;
import static processing.core.PApplet.ceil;

public class ZcMotor extends ZcRangedValueModel{
  
  private boolean cmAN, cmAL;
  
  public ZcMotor(){
    super(0, 5000);
    cmAN=cmAL=false;
  }//++!
  
  public final boolean ccGetIsTripped(){return cmAL;}
  public final boolean ccGetIsContacted(){return cmAN;}
  
  public final int ccContact(boolean pxMC, float pxLoad){
    
    cmAN=pxMC;
    
    if(!cmAN){
      ccSetValue(1);
    }else{
      ccSetValue(
        ceil(pxLoad*5000f+sysOwner.random(-200,200))
      );
    }
    
    cmAL=cmValue>4765;
    if(cmAL){
      cmAN=false;
      ccSetValue(1);
    }//+++
    
    return cmValue;
    
  }//+++
  
}//***eof
