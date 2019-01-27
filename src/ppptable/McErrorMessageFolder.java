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

import java.util.HashMap;
import kosui.ppplogic.ZcPulser;
import kosui.ppputil.VcConst;
import pppmain.SubErrorPane;
import processing.data.StringList;

public class McErrorMessageFolder {
  
  private static McErrorMessageFolder self;
  public static McErrorMessageFolder ccGetReference(){
    if(self==null){self=new McErrorMessageFolder();}
    return self;
  }//++!
  
  //===
  
  private static final int C_MAX  = 32;
  private static final int C_MASK = C_MAX-1;
  private static final String C_EMPTY="...";
  private static final String[] C_EMPTY_LIST={C_EMPTY};
  
  //=== 
  
  private final boolean[] cmErrorBits;
  private final ZcPulser[] cmDesPulser;
  private final StringList cmActiveList;
  private final HashMap<Integer, McErrorMessage> cmErrorMap;
  
  private McErrorMessageFolder(){
    
    //-- init
    cmErrorBits=new boolean[C_MAX];
    cmDesPulser=new ZcPulser[C_MAX];
    cmActiveList=new StringList();
    cmErrorMap=new HashMap<>();
    for(int i=0,s=cmDesPulser.length;i<s;i++)
      {cmDesPulser[i]=new ZcPulser();}
    
    //-- load
    dummyLoadError();
    
  }//++!
  
  @Deprecated private void dummyLoadError(){
    ccAdd(0, "ALL CLEAR!!", "every thing is alright!");
    ccAdd(1, "ERR-001", "this is the first dummy");
    ccAdd(2, "ERR-002", "this is the second dummy");
    ccAdd(3, "ERR-003", "this is the third dummy");
    ccAdd(4, "ERR-004", "this is the fourth dummy");
    ccAdd(5, "ERR-005", "this is the fifth dummy");
    ccAdd(6, "ERR-006", "this is the sixth dummy");
    ccAdd(7, "ERR-007", "this is seventh dummy");
    ccAdd(8, "ERR-008", "this is eighth dummy");
    ccAdd(9, "ERR-009", "this is ninth dummy");
  }//++!
  
  //===
  
  public final void ccUpdate(){
    cmActiveList.clear();
    boolean lpTestBit;
    for(int i=1,s=cmErrorBits.length;i<s;i++){
      if(cmErrorMap.containsKey(i)){
        String lpTitle=cmErrorMap.get(i).toString();
        if(cmErrorBits[i]){if(VcConst.ccIsValidString(lpTitle)){
          cmActiveList.append(lpTitle);
        }}//..?
        lpTestBit=cmErrorBits[i];
        if(cmDesPulser[i].ccPulse(lpTestBit)){
          SubErrorPane.ccGetReference().ccStack(
            (lpTestBit?"[+]":"[-]")
            +lpTitle
            +VcConst.ccTimeStamp(" @ ",false, true, false));
        }//..?
      }//..?
    }//..~
  }//+++
  
  //===
  
  public final void ccTakeErrorBits(boolean[] pxBits){
    for(int i=0,s=cmErrorBits.length;i<s;i++){
      cmErrorBits[i]=pxBits[i];
    }
  }//+++
  
  public final void ccAdd(int pxID, String pxContent, String pxDescription){
    cmErrorMap.put(pxID, new McErrorMessage(pxID, pxContent, pxDescription));
  }//+++
  
  public final void ccAdd(McErrorMessage pxErrorMessage){
    cmErrorMap.put(pxErrorMessage.cmID,pxErrorMessage);
  }//+++
  
  synchronized public final String ccGetMessage(int pxInt){
    if(pxInt<0){return C_EMPTY;}
    if(pxInt==0){return "all clear";}//[TODO]::..
    return "???";//[TODO]::..
  }//+++
  
  synchronized public final int ccGetActivatedCount()
    {return cmActiveList.size();}//+++
  
  synchronized public final String[] ccGetActivatedArray(){
    return ccGetActivatedCount()==0?
      C_EMPTY_LIST:
      cmActiveList.array();
  }//+++
  
}//***eof
