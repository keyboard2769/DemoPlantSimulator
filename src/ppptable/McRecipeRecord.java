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

package ppptable;

import processing.data.TableRow;
import kosui.ppputil.VcConst;

public class McRecipeRecord extends McCategoryStringBundle{
  
  public static final String[] C_TITLE={
    "id","name",
    "AG6","AG5","AG4","AG3","AG2","AG1",
    "FR2","FR1",
    "AS1"
  };
  
  //===
  
  public int cmID=0;
  public String cmName="<>";
  //[TODO]::dry time? wet time?
  
  //===
  
  public final void ccSetup(float[] pxAG, float[] pxFR, float[] pxAS){
    if(pxAG==null){return;}
    if(pxFR==null){return;}
    if(pxAS==null){return;}
    //[TOIMP]::AGX?FRX?6->1?6->0? ...maybe not nessesary
    for(int i=0;i<pxAG.length;i++){ccSetAGFloatValue(i, pxAG[i]);}
    for(int i=0;i<pxFR.length;i++){ccSetFRFloatValue(i, pxFR[i]);}
    for(int i=0;i<pxAS.length;i++){ccSetASFloatValue(i, pxAS[i]);}
  }//++!
  
  public final void ccSetup(
    int pxID, String pxName,
    float[] pxAG, float[] pxFR, float[] pxAS
  ){
    cmID=pxID;
    cmName=pxName;
    ccSetup(pxAG, pxFR, pxAS);
  }//++!
  
  public final void ccSetString(int pxIndex, String pxValue){
    switch(pxIndex){
      case  1:cmName=pxValue;break;
      case  2:cmAG[6]=pxValue;break;
      case  3:cmAG[5]=pxValue;break;
      case  4:cmAG[4]=pxValue;break;
      case  5:cmAG[3]=pxValue;break;
      case  6:cmAG[2]=pxValue;break;
      case  7:cmAG[1]=pxValue;break;
      case  8:cmFR[2]=pxValue;break;
      case  9:cmFR[1]=pxValue;break;
      case 10:cmAS[1]=pxValue;break;
      default:cmID=VcConst.ccParseIntegerString(pxValue);break;
    }
  }//+++
  
  public final String ccGetString(int pxIndex){
    switch(pxIndex){
      case  1:return cmName;
      case  2:return cmAG[6];
      case  3:return cmAG[5];
      case  4:return cmAG[4];
      case  5:return cmAG[3];
      case  6:return cmAG[2];
      case  7:return cmAG[1];
      case  8:return cmFR[2];
      case  9:return cmFR[1];
      case 10:return cmAS[1];
      default:return Integer.toString(cmID);
    }//..?
  }//+++
  
  public final boolean ccCheck(){
    boolean lpRes=true;
    McCategoryIntegerBundle lpIntRec=new McCategoryIntegerBundle();
    ccTransferRecord(lpIntRec, this);
    
    //-- AG
    if(lpIntRec.ccGetAG(6)<0 || lpIntRec.ccGetAG(6)>1000){lpRes=false;}
    if(lpIntRec.ccGetAG(6)>lpIntRec.ccGetAG(5)){lpRes=false;}
    
    if(lpIntRec.ccGetAG(5)<0 || lpIntRec.ccGetAG(5)>1000){lpRes=false;}
    if(lpIntRec.ccGetAG(5)>lpIntRec.ccGetAG(4)){lpRes=false;}
    
    if(lpIntRec.ccGetAG(4)<0 || lpIntRec.ccGetAG(4)>1000){lpRes=false;}
    if(lpIntRec.ccGetAG(4)>lpIntRec.ccGetAG(3)){lpRes=false;}
    
    if(lpIntRec.ccGetAG(3)<0 || lpIntRec.ccGetAG(3)>1000){lpRes=false;}
    if(lpIntRec.ccGetAG(3)>lpIntRec.ccGetAG(2)){lpRes=false;}
    
    if(lpIntRec.ccGetAG(2)<0 || lpIntRec.ccGetAG(2)>1000){lpRes=false;}
    if(lpIntRec.ccGetAG(2)>lpIntRec.ccGetAG(1)){lpRes=false;}
    
    if(lpIntRec.ccGetAG(1)<0 || lpIntRec.ccGetAG(1)>1000){lpRes=false;}
    
    //-- FR
    if(lpIntRec.ccGetFR(2)<0 || lpIntRec.ccGetFR(2)>1000){lpRes=false;}
    if(lpIntRec.ccGetFR(2)>lpIntRec.ccGetFR(1)){lpRes=false;}
    
    if(lpIntRec.ccGetFR(1)<0 || lpIntRec.ccGetFR(1)>1000){lpRes=false;}
    
    //-- AS
    if(lpIntRec.ccGetAS(1)<0 || lpIntRec.ccGetAS(1)>1000){lpRes=false;}
    
    return lpRes;
  }//+++
  
  //=== 
  
  public static final
  void ccTransferRecord(McRecipeRecord pxTo, TableRow pxFrom){
    if(pxTo==null){return;}
    if(pxFrom==null){return;}
    for(int i=0,s=C_TITLE.length;i<s;i++){
      pxTo.ccSetString(i, pxFrom.getString(i));
    }//..~
  }//+++
  
  public static final
  void ccTransferRecord(McCategoryIntegerBundle pxTo, McRecipeRecord pxFrom){
    if(pxTo==null){return;}
    if(pxFrom==null){return;}
    for(int i=6;i>=1;i--){
      pxTo.ccSetAG(i, (int)(VcConst.ccParseFloatString(pxFrom.cmAG[i])*10));
      if(i<=2){
        pxTo.ccSetFR(i, (int)(VcConst.ccParseFloatString(pxFrom.cmFR[i])*10));
      }//..?
      if(i<=1){
        pxTo.ccSetAS(i, (int)(VcConst.ccParseFloatString(pxFrom.cmAS[i])*10));
      }//..?
    }//..~
  }//+++
  
}//***eof
