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

import static processing.core.PApplet.ceil;
import static processing.core.PApplet.constrain;

public class EcOnePathSkip extends EcMoterizedUnit{
  
  private static final int 
    C_SKIP_W=16,
    C_SKIP_H=16,
    C_SKIP_CUT=5,
    C_SKIP_OFFSET=5,
    C_RAIL_THICK=4
  ;//...
  //===
  
  private final int cmUpperLength;
  private int cmPosition;
  
  public EcOnePathSkip(
    String pxName, int pxX, int pxY,int pxLength, int pxHeadID
  ){
    
    super();
    ccTakeKey(pxName);
    ccSetLocation(pxX, pxY);
    ccSetID(pxHeadID);
    
    cmUpperLength=pxLength;
    ccSetSize(C_SKIP_W*2+C_SKIP_H*4+cmUpperLength,C_SKIP_H*4);
    cmPosition=cmW-C_SKIP_W;
    
    
    
  }//++!

  @Override public void ccUpdate(){
  
    //[DTFM]::
    pbOwner.fill(0xFF663333);
    pbOwner.rect(cmX, cmY, cmW, cmH);
    
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
    
    pbOwner.fill(EcUnitFactory.C_SHAPE_COLOR_METAL);
    pbOwner.quad(
      lpSkipX, lpSkipY, 
      lpSkipX+C_SKIP_W, lpSkipY,
      lpSkipX+C_SKIP_W, lpSkipY+C_SKIP_H, 
      lpSkipX+C_SKIP_CUT, lpSkipY+C_SKIP_H
    );
    
    
    
    //-- update element
    

  }//+++
  
  
  public final void ccSetPostion(int pxPosition){
    cmPosition=constrain(pxPosition, 0, cmW);
  }//+++
  
  public final void ccSetPostion(float pxZeroToOne){
    cmPosition=ceil(((float)cmW)*constrain(pxZeroToOne, 0.0f, 1.0f));
  }//+++
  
  //[DTFM]
  public final int testValue(){return cmPosition;}
    
  
}//***eof
