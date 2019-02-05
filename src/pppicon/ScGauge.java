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

import java.awt.Color;
import javax.swing.JProgressBar;
import static pppmain.MainSketch.fnOneAfterDecimal;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.nfc;
import static processing.core.PApplet.ceil;

public class ScGauge extends JProgressBar{
  
  private static final Color
    C_C_BACK_NORM = Color.decode("#EEEEEE"),
    C_C_FORE_NORM = Color.decode("#999933"),
    C_C_BOTH_ALRT = Color.decode("#EE6666")
  ;//...
  
  //===
  
  private String cmName;
  private final String cmUnit;
  private float cmSpan;

  public ScGauge(String pxName, String pxUnit){
    super(HORIZONTAL,0,100);
    cmName=pxName;
    cmUnit=pxUnit;
    cmSpan=100f;
    ssInit();
  }//++!
  
  private void ssInit(){
    setValue(2);
    setString(cmName+cmUnit);
    setBorderPainted(true);
    setStringPainted(true);
    setBackground(C_C_BACK_NORM);
    setForeground(C_C_FORE_NORM);
  }//++!
  
  //===
  
  public final void ccSetName(String pxName){
    cmName=pxName;
  }//+++
  
  public final void ccSetSpan(float pxSpan){
    cmSpan=pxSpan<1f?1f:pxSpan;
    cmSpan=pxSpan;
  }//+++
  
  public final void ccSetPercentage(int pxValue){
    int lpPercentage=constrain(pxValue,0,100);
    float lpReal=cmSpan*lpPercentage/100;
    setValue(lpPercentage);
    setString(cmName
      +Float.toString(fnOneAfterDecimal(lpReal))
      +cmUnit);
  }//+++

  public final void ccSetValue(int pxValue){
    float lpReal=constrain(pxValue, 0, cmSpan*20);
    int lpPercentage=ceil(100*lpReal/(cmSpan*10));
    setValue(lpPercentage);
    setString(cmName+nfc(lpReal/10,1)+cmUnit);
  }//+++
  
  public final void ccSetIsAlerting(boolean pxStatus){
    setForeground(pxStatus?C_C_BOTH_ALRT:C_C_FORE_NORM);
  }//+++
  
}//***eof
