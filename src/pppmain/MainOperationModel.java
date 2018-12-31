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

package pppmain;

import static processing.core.PApplet.ceil;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;

public class MainOperationModel {
  
  private static MainSketch pbMain;
  
  public static final int 
    C_GENERAL_AD_MAX = 3600,
    C_GENERAL_AD_MIN = 400,
    C_FEEDER_RPM_MAX=1800,
    C_FEEDER_AD_MAX = 5000
  ;//...
  
  public int
    cmVF01RPM,cmVF02RPM,cmVF03RPM,cmVF04RPM,cmVF05RPM,cmVF06RPM,
    cmVFeederAdjustment,
    cmVDryerCapability,
    cmBagFilterSize
  ;//...
  
  public int[] 
    cmVExfanDegreeADJUST={400,3600,0,100},
    cmVBurnerDegreeADJUST={400,3600,0,100}
  ;//...
  
  public MainOperationModel(MainSketch pxSketch){
    
    pbMain=pxSketch;
    
    //--
    cmVFeederAdjustment=50;
    cmVDryerCapability=320;
    cmBagFilterSize=24;
    
    //--
    
  }//+++ 
  
  //=== supporter
  
  public final void fsShiftFeederRPM(int pxIndex, int pxCount){switch(pxIndex){

    case SubVFeederModelGroup.C_ID_VF01:
      cmVF01RPM+=pxCount*cmVFeederAdjustment;
      cmVF01RPM=constrain(cmVF01RPM,0,C_FEEDER_RPM_MAX);
    break;

    case SubVFeederModelGroup.C_ID_VF02:
      cmVF02RPM+=pxCount*cmVFeederAdjustment;
      cmVF02RPM=constrain(cmVF02RPM,0,C_FEEDER_RPM_MAX);
    break;

    case SubVFeederModelGroup.C_ID_VF03:
      cmVF03RPM+=pxCount*cmVFeederAdjustment;
      cmVF03RPM=constrain(cmVF03RPM,0,C_FEEDER_RPM_MAX);
    break;

    case SubVFeederModelGroup.C_ID_VF04:
      cmVF04RPM+=pxCount*cmVFeederAdjustment;
      cmVF04RPM=constrain(cmVF04RPM,0,C_FEEDER_RPM_MAX);
    break;

    case SubVFeederModelGroup.C_ID_VF05:
      cmVF05RPM+=pxCount*cmVFeederAdjustment;
      cmVF05RPM=constrain(cmVF05RPM,0,C_FEEDER_RPM_MAX);
    break;

    case SubVFeederModelGroup.C_ID_VF06:
      cmVF06RPM+=pxCount*cmVFeederAdjustment;
      cmVF06RPM=constrain(cmVF06RPM,0,C_FEEDER_RPM_MAX);
    break;

    default:
    break;
    
  }}//+++
  
  //=== function
  public static final int fnAdjustADValue(int pxAD, int[] pxADJ){
    return ceil(map(pxAD,
      pxADJ[0],
      pxADJ[1],
      pxADJ[2],
      pxADJ[3]
    ));
  }//+++
  
  

}//***eof
