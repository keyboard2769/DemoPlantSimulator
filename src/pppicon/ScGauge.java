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
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.nfc;
import static processing.core.PApplet.ceil;

public class ScGauge extends JProgressBar{
  
  private final String cmName, cmUnit;
  private float cmSpan;

  public ScGauge(String pxName, String pxUnit, float pxSpan){
    super(HORIZONTAL,0,100);
    cmName=pxName;
    cmUnit=pxUnit;
    cmSpan=pxSpan<1f?1f:pxSpan;
    setValue(2);
    setString(cmName+cmUnit);
    setBorderPainted(true);
    setStringPainted(true);
    //setBackground(Color.decode("#EEEEEE"));
    setForeground(Color.decode("#999933"));
  }//++!
  
  //===
  
  public final void ccSetSpan(float pxSpan){
    cmSpan=pxSpan;
  }//+++
  
  public final void ccSetPercentage(int pxValue){
    int lpPercentage=constrain(pxValue,0,100);
    float lpReal=cmSpan*lpPercentage/100;
    setValue(lpPercentage);
    setString(cmName+nfc(lpReal,1)+cmUnit);
  }//+++

  public final void ccSetValue(int pxValue){
    float lpReal=constrain(pxValue, 0, cmSpan*20);
    int lpPercentage=ceil(100*lpReal/(cmSpan*10));
    setValue(lpPercentage);
    setString(cmName+nfc(lpReal/10,1)+cmUnit);
  }//+++
  
}//***eof
