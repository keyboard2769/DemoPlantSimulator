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

import java.util.ArrayList;

public class McSettingFolder{
  
  private static McSettingFolder self;
  public static McSettingFolder ccGetReference(){
    if(self==null){self=new McSettingFolder();}
    return self;
  }//++!
  
  //===
  
  private final ArrayList<McBaseRangedFloatSetting> cmList;
  
  private McSettingFolder(){
    
    cmList=new ArrayList<>();
    
    cmList.add(McAutoWeighSetting.ccGetReference());
    cmList.add(McVBurningSetting.ccGetReference());
    cmList.add(McFeederScaleSetting.ccGetReference());
    cmList.add(McGeneralScaleSetting.ccGetReference());
    cmList.add(McTempScaleSetting.ccGetReference());
    cmList.add(McCurrentScaleSetting.ccGetReference());
    cmList.add(McLogicTimerSetting.ccGetReference());
    
  }//++!
  
  //===
  
  public final McBaseRangedFloatSetting ccGet(int pxIndex){
    int lpIndex=pxIndex<0?0:pxIndex;
    if(lpIndex>=cmList.size()){lpIndex=cmList.size();}
    return cmList.get(lpIndex);
  }//+++
  
  public final ArrayList<String> ccGetTitleList(){
    ArrayList<String> lpRes=new ArrayList<>();
    for(McBaseRangedFloatSetting it:cmList){lpRes.add(it.toString());}
    return lpRes;
  }//+++
  
  public final String ccGetTitile(int pxIndex){
    return ccGet(pxIndex).toString();
  }//+++
  
  public final McBaseRangedFloatSetting[] ccGetContentArray(){
    return cmList.toArray(new McBaseRangedFloatSetting[]{});
  }//+++
  
}//***eof
