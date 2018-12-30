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

import ppptask.ZcAggregateSupplyTask;


public class MainLogicController {
  
  public boolean 
    mnWhatever,
    //--
    sysOneSecondPLS,sysOneSecondFlicker;
  ;
  
  public ZcAggregateSupplyTask cmAggregateSupplyTask;
  
  //===
  
  private int cmRoller;
  
  public MainLogicController(){
    
    cmRoller=0;
    
    cmAggregateSupplyTask=new ZcAggregateSupplyTask();
    
  }//+++ 
  
  public void ccRun(){
    
    //-- system flicker
    cmRoller++;cmRoller&=0xF;
    sysOneSecondPLS=cmRoller==7;
    sysOneSecondFlicker=cmRoller<=7;
    
    
    //-- run over takes
    cmAggregateSupplyTask.sysOneSecondFLK=sysOneSecondFlicker;
    cmAggregateSupplyTask.sysOneSecondPLS=sysOneSecondPLS;
    cmAggregateSupplyTask.ccScan();
    
  }//+++
  

}//***eof
