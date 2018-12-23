/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pppshape;

import kosui.ppplocalui.EcShape;

public class EcHopperShape extends EcShape{

  protected int cmCut=2;

  private int cmHoldLength=6;

  @Override
  public void ccUpdate(){

    pbOwner.fill(cmBaseColor);
    pbOwner.rect(cmX, cmY, cmW, cmH-cmCut);
    pbOwner.quad(
      cmX, cmY+cmHoldLength,
      cmX+cmW, cmY+cmHoldLength,
      cmX+cmW-cmCut, cmY+cmH,
      cmX+cmCut, cmY+cmH
    );

  }//+++
  
  /**
   * will be set to quad of width
   */
  public final void ccSetCut(){
    ccSetCut(cmW/4);
  }//+++

  public final void ccSetCut(int pxCut){
    if(pxCut>=(cmW/2)){
      cmCut=(cmW/2)-1;
    }else{
      cmCut=pxCut;
    }
    cmHoldLength=cmH-cmCut;
  }//+++

}//***eof
