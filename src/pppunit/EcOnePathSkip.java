/*
 * Copyright (C) 2018 Key Parker
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

package pppunit;

import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcValueBox;
import pppicon.EcDoubleSolenoidIcon;
import static processing.core.PApplet.ceil;
import static processing.core.PApplet.constrain;

public class EcOnePathSkip extends EcMoterizedUnit{
  
  private static final int 
    C_SKIP_W=16,
    C_SKIP_H=20,
    C_SKIP_CUT=5,
    C_SKIP_OFFSET=3,
    C_LIMIT_LED_H=2,
    C_RAIL_THICK=4,
    C_BOX_GAP=12
  ;//...
  
  //===
  
  private final int cmUpperLength;
  private int cmPosition,cmLimitColor;
  private boolean cmHasMixture;
  
  private final EcValueBox cmPulseBox;
  private final EcDoubleSolenoidIcon cmHoistIcon;
  
  //[TOCLEAR]::tested by this value:("n",100,100,50,12);
  public EcOnePathSkip(
    String pxName, int pxX, int pxY,int pxLength, int pxHeadID
  ){
    
    super();
    ccSetupKey(pxName);
    ccSetLocation(pxX, pxY);
    ccSetID(pxHeadID);
    
    cmLimitColor=EcFactory.C_DARK_GRAY;
    cmHasMixture=false;
    
    cmUpperLength=pxLength;
    ccSetSize(C_SKIP_W*2+C_SKIP_H*4+cmUpperLength,C_SKIP_H*4);
    cmPosition=cmW-C_SKIP_W;
    
    cmPulseBox=EcUnitFactory.ccCreateDegreeValueBox("000000p", "p");
    cmPulseBox.ccSetValue(1000, 6);
    cmPulseBox.ccSetLocation(pxX, pxY-cmPulseBox.ccGetH()-C_BOX_GAP);
    
    cmHoistIcon = new EcDoubleSolenoidIcon();
    cmHoistIcon.ccSetLocation(ccEndX()+2, ccEndY()-cmHoistIcon.ccGetH()/2);
    
    cmMotor.ccSetLocation(cmX-cmMotor.ccGetW()+2, cmY-cmMotor.ccGetH()/2);
    
  }//++!

  @Override public void ccUpdate(){
  
    //-- draw rail
    pbOwner.fill(EcUnitFactory.C_SHAPE_COLOR_DUCT);
    pbOwner.rect(cmX, cmY, cmUpperLength, C_RAIL_THICK);
    pbOwner.quad(
      cmX+cmUpperLength, cmY, 
      cmX+cmUpperLength+cmH, ccEndY(), 
      cmX+cmUpperLength+cmH, ccEndY()+C_RAIL_THICK, 
      cmX+cmUpperLength, cmY+C_RAIL_THICK
    );
    pbOwner.rect(
      cmX+cmUpperLength+cmH, ccEndY(),
      C_SKIP_W*2, C_RAIL_THICK
    );
    
    //-- draw backet
    int lpSkipX=cmX+cmPosition;
    int lpSkipY=cmY;
    if(cmPosition<cmUpperLength)
      {lpSkipY=cmY;}
    if(cmPosition>=cmUpperLength
      && cmPosition<(cmUpperLength+C_SKIP_H*4)  
    ){lpSkipY=cmY+cmPosition-cmUpperLength;}
    if(cmPosition>=(cmUpperLength+C_SKIP_H*4))
      {lpSkipY=ccEndY();}
    lpSkipY-=C_SKIP_OFFSET;
    
    pbOwner.fill(cmHasMixture?
      EcFactory.C_DIM_YELLOW:
      EcUnitFactory.C_SHAPE_COLOR_METAL
    );
    pbOwner.quad(
      lpSkipX, lpSkipY, 
      lpSkipX+C_SKIP_W, lpSkipY,
      lpSkipX+C_SKIP_W, lpSkipY+C_SKIP_H, 
      lpSkipX+C_SKIP_CUT, lpSkipY+C_SKIP_H
    );
    
    //-- draw limit led
    pbOwner.fill(cmLimitColor);
    pbOwner.rect(
      lpSkipX+2,lpSkipY+2,
      C_SKIP_W-4,C_LIMIT_LED_H
    );
    
    //-- update element
    cmMotor.ccUpdate();
    cmPulseBox.ccUpdate();
    cmHoistIcon.ccUpdate();

  }//+++
  
  /**
   * 
   * @param pxPosition basically, pixel, simply added to X coordinate. 
   */
  public final void ccSetPosition(int pxPosition){
    cmPosition=constrain(pxPosition, 0, cmW);
  }//+++
  
  public final void ccSetPosition(float pxZeroToOne){
    cmPosition=ceil(((float)cmW)*constrain(pxZeroToOne, 0.0f, 1.0f));
  }//+++
  
  public final void ccShiftPosition(int pxOffset){
    cmPosition+=pxOffset;
    cmPosition=constrain(cmPosition, 0, cmW);
  }//+++
  
  public final void ccSetPulseCount(int pxCount){
    cmPulseBox.ccSetValue(pxCount);
  }//+++
  
  public final void ccSetHoistGoesUp(boolean pxStatus){
    cmHoistIcon.ccSetIsOpening(pxStatus);
  }//+++
  
  public final void ccSetHoistGoesDown(boolean pxStatus){
    cmHoistIcon.ccSetIsClosing(pxStatus);
  }//+++
  
  public final void ccSetIsHoistUpSide(boolean pxStatus){
    cmHoistIcon.ccSetIsFull(pxStatus);
  }//+++
  
  public final void ccSetIsHoistDownSide(boolean pxStatus){
    cmHoistIcon.ccSetIsClosed(pxStatus);
  }//+++
  
  public final void ccSetHasMixture(boolean pxStatus){
    cmHasMixture=pxStatus;
  }//+++
  
  public final void ccSetLimitStatus(char pxMode_pox){switch(pxMode_pox){
    case 'p':cmLimitColor=EcFactory.C_LIT_RED;break; 
    case 'o':cmLimitColor=EcFactory.C_PURPLE;break; 
    case 'x':
    default:cmLimitColor=EcFactory.C_DARK_GRAY;break; 
  }}//+++
  
  //===
  
}//***eof
