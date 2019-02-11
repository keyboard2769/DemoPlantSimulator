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

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import kosui.pppswingui.ScFactory;
import static kosui.ppputil.VcConst.ccRoundForOneAfter;

public class ScScaleBlock extends JPanel{
  
  private static final String C_AD_UNIT=" AD";
  
  private final String cmUnit;
  private final JTextField cmADValueBox, cmRealValueBox;
  private final JButton cmZeroButton, cmSpanButton;
  
  public ScScaleBlock(String pxTitle,String pxUnit, ActionListener pxListener){
    super();
    
    cmUnit=pxUnit;
    
    cmADValueBox=ScFactory.ccMyTextBox("0000"+C_AD_UNIT, 80, 31);
    cmRealValueBox=ScFactory.ccMyTextBox("0000"+cmUnit,  80, 31);
    cmZeroButton=ScFactory.ccMyCommandButton
      ("ZERO", "--"+pxTitle+"-button-zero", pxListener);
    cmSpanButton=ScFactory.ccMyCommandButton
      ("SPAN", "--"+pxTitle+"-button-span", pxListener);
    
    ssPackup(pxTitle);
    
  }//++!
  
  private void ssPackup(String pxTitle){
    setLayout(new FlowLayout(FlowLayout.LEADING, 2, 2));
    setBorder(BorderFactory.createTitledBorder(pxTitle));
    add(cmADValueBox);
    add(cmRealValueBox);
    add(cmZeroButton);
    add(cmSpanButton);
  }//++!
  
  public final void ccSetTitle(String pxTitle){
    Object lpBorder = getBorder();
    if(lpBorder instanceof TitledBorder){
      ((TitledBorder)lpBorder).setTitle(pxTitle);
    }//..?
  }//+++
  
  public final void ccSetupValue(int pxAD, float pxReal){
    ccSetADValue(pxAD);
    ccSetRealValue(pxReal);
  }//+++
  
  public final void ccSetADValue(int pxValue){
    cmADValueBox.setText(Integer.toString(pxValue)+C_AD_UNIT);
  }//+++
  
  public final void ccSetRealValue(float pxValue){
    cmRealValueBox.setText
      (Float.toString(ccRoundForOneAfter(pxValue))+cmUnit);
  }//+++
  
}//***eof
