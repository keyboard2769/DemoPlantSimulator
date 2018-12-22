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

import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcGauge;
import kosui.ppplocalui.EcLamp;
import pppshape.EcHopperShape;
import pppshape.EcScrewShape;

class EcFillerSilo extends EcMoterizedUnit{
    
    private final EcHopperShape cmSiloShape;
    private final EcGauge cmSiloLV;
    private final EcLamp cmSiloAir;
    protected final EcScrewShape cmScrewShape;

    public EcFillerSilo(String pxName, int pxX, int pxY, int pxHeadID){
      
      super();
      ccTakeKey(pxName);
      ccSetBound(pxX, pxY, 30, 60);
      ccSetID(pxHeadID);
      
      cmSiloShape=new EcHopperShape();
      cmSiloShape.ccSetBound(cmX, cmY, cmW, cmH);
      cmSiloShape.ccSetCut(cmW/3);
      cmSiloShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
      
      cmScrewShape=new EcScrewShape();
      cmScrewShape.ccSetCut(-1, -1);
      cmScrewShape.ccSetBound(cmX+2, ccEndY()+2, cmW*3/2, 10);
      cmScrewShape.ccSetBaseColor(EcUnitFactory.C_SHAPE_COLOR_METAL);
      
      cmSiloLV=new EcGauge();
      cmSiloLV.ccSetBound(cmX+3, cmY+3, 4, cmH*3/4);
      cmSiloLV.ccSetIsVertical(true);
      cmSiloLV.ccSetHasStroke(true);
      cmSiloLV.ccSetGaugeColor(EcFactory.C_DIM_GRAY, EcFactory.C_LIT_GRAY);
      cmSiloLV.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_GREEN);
      
      cmSiloAir=new EcLamp();
      cmSiloAir.ccSetSize(16, 16);
      cmSiloAir.ccSetLocation(ccEndX()-8, ccEndY()-8-cmW/3);
      cmSiloAir.ccSetNameAlign('x');
      cmSiloAir.ccSetText("A");
      cmSiloAir.ccSetColor(EcFactory.C_ORANGE);
      
      cmMotor.ccSetLocation(cmScrewShape, 4,4);
      
    }//++!
    
    @Override
    public void ccUpdate(){
      
      cmSiloShape.ccUpdate();
      cmScrewShape.ccUpdate();
      cmSiloLV.ccUpdate();
      cmSiloAir.ccUpdate();
      cmMotor.ccUpdate();
      
    }//+++
    
    public final void ccSetIsAirating(boolean pxStatus){
      cmSiloAir.ccSetActivated(pxStatus);
    }//+++
    
    public final void ccSetSiloLevel(char pxMode_elmf){
      EcUnitFactory.ccConfigLevel(cmSiloLV, pxMode_elmf);
    }//+++
    
  }//***
  