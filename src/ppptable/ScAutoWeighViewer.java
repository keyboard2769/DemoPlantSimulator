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
        case 0:return yourMOD.vmTargetFR2;
        case 1:return yourMOD.vmTargetFR1;
        case 2:return yourMOD.vmTargetAG6;
        case 3:return yourMOD.vmTargetAG5;
        case 4:return yourMOD.vmTargetAG4;
        case 5:return yourMOD.vmTargetAG3;
        case 6:return yourMOD.vmTargetAG2;
        case 7:return yourMOD.vmTargetAG1;
        case 8:return yourMOD.vmTargetAS1;
        default:return -13;
      }//..?
      
      case 1:switch(pxColumnIndex){
        case 0:return yourMOD.vmResultFR2;
        case 1:return yourMOD.vmResultFR1;
        case 2:return yourMOD.vmResultAG6;
        case 3:return yourMOD.vmResultAG5;
        case 4:return yourMOD.vmResultAG4;
        case 5:return yourMOD.vmResultAG3;
        case 6:return yourMOD.vmResultAG2;
        case 7:return yourMOD.vmResultAG1;
        case 8:return yourMOD.vmResultAS1;
        default:return -14;
      }//..?
      
      case 2:switch(pxColumnIndex){
        case 0:return yourMOD.vmPopedFR2;
        case 1:return yourMOD.vmPopedFR1;
        case 2:return yourMOD.vmPopedAG6;
        case 3:return yourMOD.vmPopedAG5;
        case 4:return yourMOD.vmPopedAG4;
        case 5:return yourMOD.vmPopedAG3;
        case 6:return yourMOD.vmPopedAG2;
        case 7:return yourMOD.vmPopedAG1;
        case 8:return yourMOD.vmPopedAS1;
        default:return -14;
      }//..?
        
      default:return -99;
    }//..?
  }//+++
  
}//***eof
