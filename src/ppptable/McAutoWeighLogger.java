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

import processing.data.TableRow;

public class McAutoWeighLogger extends McBaseCSVTable{
  
  private static McAutoWeighLogger self;
  public static McAutoWeighLogger ccGetReference(){
    if(self==null){self=new McAutoWeighLogger();}
    return self;
  }//++!

  //===
  
  private McAutoWeighLogger(){
    
    super();
    
    cmData.addColumn("time");
    cmData.addColumn("m-temp");
    cmData.addColumn("total");
    cmData.addColumn("AG6");
    cmData.addColumn("AG5");
    cmData.addColumn("AG4");
    cmData.addColumn("AG3");
    cmData.addColumn("AG2");
    cmData.addColumn("AG1");
    cmData.addColumn("FR2");
    cmData.addColumn("FR1");
    cmData.addColumn("AS1");
    
    ccAddDummyRecord();
    
  }//++!
  
  public final void ccAddDummyRecord(){
    
    //[FIXIT]::
    TableRow lpRow=cmData.addRow();
    lpRow.setString("time", "--00:00:00");
    lpRow.setString("m-temp", "%'C%");
    lpRow.setString("total", "%kg%");
    lpRow.setString("AG6", "%kg%");
    lpRow.setString("AG5", "%kg%");
    lpRow.setString("AG4", "%kg%");
    lpRow.setString("AG3", "%kg%");
    lpRow.setString("AG2", "%kg%");
    lpRow.setString("AG1", "%kg%");
    lpRow.setString("FR2", "%kg%");
    lpRow.setString("FR1", "%kg%");
    lpRow.setString("AS1", "%kg%");
    
  }//+++
  
  public final void ccAddRecord(McAutoWeighRecord pxRecord){
    
    TableRow lpRow=cmData.addRow();
    lpRow.setString("time", pxRecord.cmTimeStamp);
    lpRow.setString("m-temp", pxRecord.cmMixtureTemperature);
    lpRow.setString("total", pxRecord.cmTotalKG);
    lpRow.setString("AG6", pxRecord.cmAG[6]);
    lpRow.setString("AG5", pxRecord.cmAG[5]);
    lpRow.setString("AG4", pxRecord.cmAG[4]);
    lpRow.setString("AG3", pxRecord.cmAG[3]);
    lpRow.setString("AG2", pxRecord.cmAG[2]);
    lpRow.setString("AG1", pxRecord.cmAG[1]);
    lpRow.setString("FR2", pxRecord.cmFR[2]);
    lpRow.setString("FR1", pxRecord.cmFR[1]);
    lpRow.setString("AS1", pxRecord.cmAS[1]);
    
  }//+++
  
  //===
  
 }//***eof
