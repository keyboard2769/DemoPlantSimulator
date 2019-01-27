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

import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcTimer;

public class TcErrorMessageTask extends ZcTask{
  
  private static TcErrorMessageTask self;
  private TcErrorMessageTask(){}//++!
  public static TcErrorMessageTask ccGetReference(){
    if(self==null){self=new TcErrorMessageTask();}
    return self;
  }//++!

  //===
  
  public int mnMessage=0;
  public boolean[] mnDesError = new boolean[32];
  
  private final ZcMessageController cmController = new ZcMessageController();

  @Override public void ccScan(){
    
    //--
    //[TODO]:: like : cmController.ccSetBit(1, ..getR().dcAL1);
    
    //--
    cmController.ccRun();
    mnMessage=cmController.ccGetWordOuput();
    for(int i=0;i<mnDesError.length;i++){
      mnDesError[i]=cmController.ccGetBitOutput(i);
    }//+++
    
  }//+++

  //===
  
  private final ZcTimer dummyTM=new ZcOnDelayTimer(100);
  
  @Override public void ccSimulate(){
    //[TODEL]::
    dummyTM.ccAct(true);
    cmController.ccSetBit(5, dummyTM.ccIsUp());
  }//+++
  
 }//***eof
