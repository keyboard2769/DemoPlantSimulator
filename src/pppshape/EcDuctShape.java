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

package pppshape;

import kosui.ppplocalui.EcShape;

public class EcDuctShape extends EcShape{
  
  static final int C_DUCT_THICK = 4;
  
  private char cmDirection='a';
  private int cmCut=0;

  @Override public void ccUpdate(){
    
    //[TEST]::
    //pbOwner.fill(0xFF663333);
    //pbOwner.rect(cmX, cmY, cmW, cmH);
    
    pbOwner.fill(cmBaseColor);
    switch(cmDirection){
      
      case 'a':
        pbOwner.rect(cmX+cmCut, cmY, cmW-cmCut, C_DUCT_THICK);
        pbOwner.rect(cmX+cmCut, cmY, C_DUCT_THICK, cmH);
        if(cmCut>0){pbOwner.rect(
          cmX,ccEndY(),
          cmCut+C_DUCT_THICK,C_DUCT_THICK
        );}
      break;
        
      case 'b':
        pbOwner.rect(cmX, cmY, cmW-cmCut, C_DUCT_THICK);
        pbOwner.rect(ccEndX()-cmCut, cmY, C_DUCT_THICK, cmH);
        if(cmCut>0){pbOwner.rect(
          ccEndX()-cmCut,ccEndY()-C_DUCT_THICK,
          cmCut,C_DUCT_THICK
        );}
      break;
        
      case 'c':
        pbOwner.rect(ccEndX()-C_DUCT_THICK, cmY, C_DUCT_THICK, cmH-cmCut);
        pbOwner.rect(cmX, ccEndY()-cmCut-C_DUCT_THICK, cmW, C_DUCT_THICK);
        if(cmCut>0){pbOwner.rect(
          cmX, ccEndY()-cmCut,
          C_DUCT_THICK, cmCut
        );}
      break;
        
      case 'd':
        pbOwner.rect(cmX, cmY, C_DUCT_THICK, cmH-cmCut);
        pbOwner.rect(cmX, ccEndY()-cmCut, cmW, C_DUCT_THICK);
        if(cmCut>0){pbOwner.rect(
          ccEndX()-C_DUCT_THICK, ccEndY()-cmCut,
          C_DUCT_THICK, cmCut
        );}
      break;
        
      default:break;
    }//..?
    
  }//+++
  
  /**
   * - [a]..left top<p>
   * - [b]..right top<p>
   * - [d]..right buttom<p>
   * - [c]..left buttton<p>
   * 
   * @param pxMode_abcdx 
   */
  public final void ccSetDirection(char pxMode_abcdx){
    cmDirection=pxMode_abcdx;
  }//+++
  
  /**
   * cut should be above zero but under the length of width or height
   * this does not check, don't get over.
   * @param pxCut
   */
  public final void ccSetCut(int pxCut){
    cmCut=pxCut;
  }//+++

}//***eof
