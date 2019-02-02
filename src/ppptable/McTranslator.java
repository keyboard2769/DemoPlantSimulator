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
    
    ssHardEnglishDict();
    
    //-- point
    cmPointer=cmEnglishDict;
    
  }//++!
  
  private void ssHardEnglishDict(){
    
    //--
    cmEnglishDict.ccSet("--vburning", "V-Burning");
    cmEnglishDict.ccSet("--temptarget-vburner", "['C]:v burner target");
    cmEnglishDict.ccSet("--templimit-bagentrance-low", "['C]:bag entrance low limit");
    cmEnglishDict.ccSet("--templimit-bagentrance-high", "['C]:bag entrance high limit");
    
    //--
    cmEnglishDict.ccSet("--autoweigh", "Auto-Weigh");
    cmEnglishDict.ccSet("--aTime-dry", "[S]:dry time (will be rounded)");
    cmEnglishDict.ccSet("--aTime-wet", "[S]:wet time (will be rounded)");
    cmEnglishDict.ccSet("--bAD-asoverscale", "[AD]:asphalt over scale value (will be rounded)");
    
    //--
    cmEnglishDict.ccSet("--feederscale", "Feeder-Scale");
    
    //--
    cmEnglishDict.ccSet("--current", "Current");
    cmEnglishDict.ccSet("--gct-ad-offset","[A]:general AD value offset");
    cmEnglishDict.ccSet("--gct-ad-span","[A]:general AD value span");
    cmEnglishDict.ccSet("--gct-ct-offset","[A]:general current value span");
    cmEnglishDict.ccSet("--ctslot00-ct-span", "[A]:compressor span value");
    cmEnglishDict.ccSet("--ctslot00-ct-alart", "[A]:compressor alarm value");
    
    //--
    cmEnglishDict.ccSet("--logic-timer", "Logic-Timer");
    cmEnglishDict.ccSet("--tmslot001-value","[S]:mixer gate open time");
    
    
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
  
  //===
  
  //[TODO]::save to file
  //[TODO]::load from file
  
}//***eof
