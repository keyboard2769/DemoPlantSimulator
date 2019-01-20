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

package pppunit;

import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcLamp;
import kosui.ppplocalui.EcValueBox;
import pppicon.EcControlMotorIcon;
import pppshape.EcBlowerShape;

public class EcExhaustFan extends EcElement implements EiMotorized{
    
    private final int //[TODO]::make static
      C_DUCT_THICK=8,
      C_DUCT_GAP=4
    ;//...
    
    private final EcBlowerShape cmFanShape;
    private final EcLamp cmPressurePL;
    private final EcControlMotorIcon cmDamper;
    private final EcValueBox cmDegreeBox;
    
    public EcExhaustFan(String pxName, int pxHeadID){

      super();
      ccSetupKey(pxName);
      ccSetID(pxHeadID);
      
      cmFanShape = new EcBlowerShape();
      cmFanShape.ccSetSize(C_DUCT_THICK*2, C_DUCT_THICK*4);
      cmFanShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
      cmFanShape.ccSetDirection('u');
        
      cmPressurePL = new EcLamp();
      cmPressurePL.ccSetSize(16,16);
      cmPressurePL.ccSetText("L");
      cmPressurePL.ccSetColor(EcFactory.C_LIT_GREEN);
      
      cmDamper = new EcControlMotorIcon();
      
      cmDegreeBox=EcUnitFactory.ccCreateDegreeValueBox("-099%", "%");
      cmDegreeBox.ccSetValue(1,3);
      
    }//++!
    
    public final void ccSetupLocation(int pxX, int pxY){
      ccSetLocation(pxX,pxY);
      ccSetSize(C_DUCT_THICK*7, C_DUCT_THICK*12);
      cmFanShape.ccSetLocation(cmX, cmY+C_DUCT_THICK*8);
      cmPressurePL.ccSetLocation(cmFanShape, -8, 10);
      cmDamper.ccSetLocation(ccEndX()-3*C_DUCT_THICK+3, ccEndY()+3);
      cmDegreeBox.ccSetLocation(cmFanShape, 0, C_DUCT_THICK*5/2);
    }//++!

    //===
    
    @Override public void ccUpdate(){
      
      pbOwner.fill(EcUnitFactory.C_SHAPE_COLOR_METAL);
      pbOwner.rect(cmX, cmY, C_DUCT_THICK*2, C_DUCT_THICK*8-C_DUCT_GAP);
      pbOwner.rect(ccEndX()-C_DUCT_THICK, ccCenterY(), C_DUCT_THICK, cmH/2);
      
      int lpFanEnd=cmFanShape.ccGetW()*2+C_DUCT_GAP;
      pbOwner.rect(cmX+lpFanEnd, ccEndY(), cmW-lpFanEnd, C_DUCT_THICK);
      
      cmFanShape.ccUpdate();
      cmPressurePL.ccUpdate();
      cmDamper.ccUpdate();
      cmDegreeBox.ccUpdate();
      
    }//***
    
    /**
     * NOT constraining, don't get over. 
     * @param pxVal 0-100
     */
    public final void ccSetDegree(int pxVal){
      cmDegreeBox.ccSetValue(pxVal);
      cmDamper.ccSetDegree(100-pxVal);//..direction problem
    }//+++
    
    public final void ccSetIsFull(boolean pxStatus){
      cmDamper.ccSetIsFull(pxStatus);
    }//+++
    
    public final void ccSetIsClosed(boolean pxStatus){
      cmDamper.ccSetIsClosed(pxStatus);
    }//+++

  public final void ccSetHasPressure(boolean pxStatus){
    cmPressurePL.ccSetIsActivated(pxStatus);
  }//+++

  @Override public void ccSetMotorON(boolean pxStatus){
    cmFanShape.ccSetBaseColor(pxStatus?
      EcUnitFactory.C_SHAPE_COLOR_POWERED:
      EcUnitFactory.C_SHAPE_COLOR_METAL
    );
  }//+++
  
}//***eof
