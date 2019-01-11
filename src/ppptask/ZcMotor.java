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
import static processing.core.PApplet.ceil;

public class ZcMotor extends ZcRangedValueModel{

  public ZcMotor(){
    super(0, 5000);
  }//++!
  
  public final int ccGetCurrent(boolean pxMC, float pxLoad){
    if(pxMC){
      ccSetValue(1);
    }else{
      ccSetValue(
        ceil(pxLoad*5000f+ZcTask.sysOwner.random(200,200))
      );
    }
    return cmValue;
  }//+++
  
}//***eof
