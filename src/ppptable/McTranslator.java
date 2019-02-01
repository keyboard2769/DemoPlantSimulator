/*
 * Copyright (C) 2019 2053
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

import kosui.ppputil.McKeyValueModel;

public class McTranslator{
  
  private static McTranslator self;
  public static McTranslator ccGetReference(){
    if(self==null){self=new McTranslator();}
    return self;
  }//++!
  
  //===
  
  public final McKeyValueModel
    cmEnglishDict,cmChineseDict,cmJapaneseDict;//...
  
  private McKeyValueModel cmPointer;
  
  private McTranslator(){
    super();
    
    cmEnglishDict=new McKeyValueModel("=en=");
    cmChineseDict=new McKeyValueModel("=ch=");
    cmJapaneseDict=new McKeyValueModel("=jp=");
    
    cmEnglishDict.ccSet("--src", "%source%");
    cmChineseDict.ccSet("--src", "%???%");
    cmJapaneseDict.ccSet("--src", "%???%");
    
    ssBasicEnglishDescription();
    
    //-- point
    cmPointer=cmEnglishDict;
    
  }//++!
  
  private void ssBasicEnglishDescription(){
    
    //--
    cmEnglishDict.ccSet("--VBurnerTarget", "v burner target:['C]");
    cmEnglishDict.ccSet("--BagEntranceLowLimit", "bag entrance low limit:['c]");
    cmEnglishDict.ccSet("--BagEntranceHighLimit", "bag entrance high limit:['c]");
    
    //--
    cmEnglishDict.ccSet("--time-dry", "dry time:[s]");
    cmEnglishDict.ccSet("--time-wet", "wet time:[s]");
    
  }//+++
  
  //===
  
  public final void ccSetDictionary(char pxMode_ejc){
    switch(pxMode_ejc){
      case 'j':cmPointer=cmJapaneseDict;break;
      case 'c':cmPointer=cmChineseDict;break;
      default:cmPointer=cmEnglishDict;break;
    }//..?
  }//+++
  
  synchronized public final String ccTr(String pxSource){
    return cmPointer.ccGetOrDefault(pxSource, pxSource);
  }//+++
  
  synchronized public final String ccTr(Object pxSource){
    if(pxSource instanceof String)
      {return ccTr((String)pxSource);}
    else
      {return pxSource.toString();}
  }//+++
  
}//***eof
