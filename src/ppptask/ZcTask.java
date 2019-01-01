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

package ppptask;

import kosui.ppplogic.ZiTask;
import processing.core.PApplet;

public abstract class ZcTask implements ZiTask{
  
  protected static PApplet sysOwner=null;
  
  protected static boolean
    sysOneSecondPLS=false,
    sysOneSecondFLK=false
  ;//...
  
  public static final void ccSetSystemClock(
    boolean pxPulse, boolean pxFlikcer
  ){
    sysOneSecondPLS=pxPulse;
    sysOneSecondFLK=pxFlikcer;
  }//+++
  
  public static final void ccSetSystemClock(
    int pxRoller, int pxJudge
  ){
    sysOneSecondPLS=pxRoller==pxJudge;
    sysOneSecondFLK=pxRoller<=pxJudge;
  }//+++
  
  public static final void ccSetOwner(PApplet pxOwner){
    sysOwner=pxOwner;
  }//+++
  
  protected static final float ccRandom(float pxRange){
    return sysOwner==null?0f:sysOwner.random(pxRange);
  }//+++
  
}//***eof
