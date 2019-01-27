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

package ppptask;

import java.util.Arrays;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcTimer;

public class ZcMessageController {
  
  private static final int C_SIZE=64;//..power of 2!!
  private static final int C_MASK=C_SIZE-1;
  
  //===

  private final boolean cmERR[];
  private int cmHead,cmOut;
  private final ZcTimer cmSpanTM, cmSuppressTM;

  public ZcMessageController(){
    cmERR=new boolean[C_SIZE];
    Arrays.fill(cmERR, false);
    cmHead=0;
    cmOut=0;
    cmSpanTM=new ZcOnDelayTimer(30);
    cmSuppressTM=new ZcOnDelayTimer(15);
  }//++!

  //===

  public final void ccRun(){

    boolean lpCurrent=ccGetBitOutput(cmHead);
    cmSpanTM.ccAct(lpCurrent);
    cmSuppressTM.ccAct(lpCurrent);
    if(cmSpanTM.ccIsUp()||!lpCurrent){
      cmHead++;cmHead&=C_MASK;
      cmSpanTM.ccSetValue(0);
      cmSuppressTM.ccSetValue(0);
    }//..?

    //-- output
    cmOut=(ccGetBitOutput(cmHead)&&!cmSuppressTM.ccIsUp())?cmHead:-1;

    //-- clear message
    boolean lpJudge=false;
    for(int i=1;i<cmERR.length;i++){lpJudge|=cmERR[i];}
    cmERR[0]=!lpJudge;

  }//++~

  //===

  public final void ccSetTimer(int pxSpan, int pxSuppress){
    if(pxSuppress>=pxSpan){return;}
    cmSpanTM.ccSetTime(pxSpan);
    cmSuppressTM.ccSetTime(pxSuppress);
  }//+++

  public final void ccSetBit(int pxBit,boolean pxStatus){
    cmERR[pxBit&0xFF]=pxStatus;
  }//+++

  public final void ccSetBit(int pxBit){
    cmERR[pxBit&0xFF]=!cmERR[pxBit&0xFF];
  }//+++

  public final int ccGetWordOuput()
    {return cmOut;}//+++

  public final boolean ccGetBitOutput(int pxBit)
    {return cmERR[pxBit&0xFF];}//+++

  //===

  @Deprecated public final  int testGetHead(){return cmHead;}//+++

}//***eof
