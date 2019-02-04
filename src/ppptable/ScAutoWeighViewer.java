/*
 * Copyright (C) 2019 keypad
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

package ppptable;

import kosui.pppswingui.McTableAdapter;
import static pppmain.MainSketch.yourMOD;

public class ScAutoWeighViewer extends McTableAdapter{
    
  private static ScAutoWeighViewer self;
  private ScAutoWeighViewer(){}//++!
  public static ScAutoWeighViewer ccGetReference(){
    if(self==null){self=new ScAutoWeighViewer();}
    return self;
  }//++!
  
  //===
  
  private final String[] cmTitles={
    "FR2","FR1",
    "AG6","AG5","AG4","AG3","AG2","AG1",
    "AS1"
  };
  
  //===
  
  @Override public int getColumnCount(){
    return cmTitles.length;
  }//+++
  
  @Override public String getColumnName(int pxColumnIndex){
    if(pxColumnIndex<0 || pxColumnIndex>=cmTitles.length){return "<>";}
    return cmTitles[pxColumnIndex];
  }//+++

  @Override public int getRowCount(){
    return 3;
  }//+++

  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex){
    
    switch(pxRowIndex){
      
      case 0:switch(pxColumnIndex){
        
        case 0:return yourMOD.vmTargetKG.ccGetFR(2);
        case 1:return yourMOD.vmTargetKG.ccGetFR(1);
        
        case 2:return yourMOD.vmTargetKG.ccGetAG(6);
        case 3:return yourMOD.vmTargetKG.ccGetAG(5);
        case 4:return yourMOD.vmTargetKG.ccGetAG(4);
        case 5:return yourMOD.vmTargetKG.ccGetAG(3);
        case 6:return yourMOD.vmTargetKG.ccGetAG(2);
        case 7:return yourMOD.vmTargetKG.ccGetAG(1);
        
        case 8:return yourMOD.vmTargetKG.ccGetAS(1);
        default:return -9992;
      }//..?
      
      case 1:switch(pxColumnIndex){
        case 0:return yourMOD.vmResultKG.ccGetFR(2);
        case 1:return yourMOD.vmResultKG.ccGetFR(1);
        case 2:return yourMOD.vmResultKG.ccGetAG(6);
        case 3:return yourMOD.vmResultKG.ccGetAG(5);
        case 4:return yourMOD.vmResultKG.ccGetAG(4);
        case 5:return yourMOD.vmResultKG.ccGetAG(3);
        case 6:return yourMOD.vmResultKG.ccGetAG(2);
        case 7:return yourMOD.vmResultKG.ccGetAG(1);
        case 8:return yourMOD.vmResultKG.ccGetAS(1);
        default:return -9993;
      }//..?
      
      case 2:switch(pxColumnIndex){
        case 0:return yourMOD.vmPoppedtKG.ccGetFR(2);
        case 1:return yourMOD.vmPoppedtKG.ccGetFR(1);
        case 2:return yourMOD.vmPoppedtKG.ccGetAG(6);
        case 3:return yourMOD.vmPoppedtKG.ccGetAG(5);
        case 4:return yourMOD.vmPoppedtKG.ccGetAG(4);
        case 5:return yourMOD.vmPoppedtKG.ccGetAG(3);
        case 6:return yourMOD.vmPoppedtKG.ccGetAG(2);
        case 7:return yourMOD.vmPoppedtKG.ccGetAG(1);
        case 8:return yourMOD.vmPoppedtKG.ccGetAS(1);
        default:return -9994;
      }//..?
        
      default:return -9996;
    }//..?
  }//+++
  
}//***eof
