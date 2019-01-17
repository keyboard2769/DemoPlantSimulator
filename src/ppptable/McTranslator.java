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

public class McTranslator{
  
  private static McTranslator self;
  public static McTranslator ccGetReference(){
    if(self==null){self=new McTranslator();}
    return self;
  }//++!
  
  //===
  
  public final McBaseKeyValueSetting
    cmEnglishDict,cmChineseDict,cmJapaneseDict;//...
  
  private McBaseKeyValueSetting cmPointer;
  
  private McTranslator(){
    super();
    
    cmEnglishDict=new McBaseKeyValueSetting();
    cmChineseDict=new McBaseKeyValueSetting();
    cmJapaneseDict=new McBaseKeyValueSetting();
    
    cmEnglishDict.ccAddItem("--src", "%source%");
    cmChineseDict.ccAddItem("--src", "%???%");
    cmJapaneseDict.ccAddItem("--src", "%???%");
    
    //-- english
    ccBasicEnglishDescription();//..should this just be a dummy??
    cmEnglishDict.ccPack("=EN=");
    
    //-- chinese
    cmChineseDict.ccPack("=CN=");
    
    //-- jp
    cmJapaneseDict.ccPack("=JP=");
    
    //-- point
    cmPointer=cmEnglishDict;
    
  }//++!
  
  private void ccBasicEnglishDescription(){
    
    //--
    cmEnglishDict.ccAddItem("--VBurnerTarget", "v burner target:['C]");
    cmEnglishDict.ccAddItem("--BagEntranceLowLimit", "bag entrance low limit:['c]");
    cmEnglishDict.ccAddItem("--BagEntranceHighLimit", "bag entrance high limit:['c]");
    
    //--
    cmEnglishDict.ccAddItem("--drytime", "dry time:[s]");
    cmEnglishDict.ccAddItem("--wettime", "wet time:[s]");
    
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
    if(cmPointer.cmData.hasKey(pxSource))
      {return cmPointer.cmData.getString(pxSource);}
    else
      {return pxSource;}
  }//+++
  
  synchronized public final String ccTr(Object pxSource){
    if(pxSource instanceof String)
      {return ccTr((String)pxSource);}
    else
      {return pxSource.toString();}
  }//+++
  
}//***eof
