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
    
    cmEnglishDict.ccSet("--uno", "%one%");
    cmChineseDict.ccSet("--uno", "%???%");
    cmJapaneseDict.ccSet("--uno", "%???%");
    
    cmPointer=cmEnglishDict;
    
  }//++!
  
  //[TODO]::public final void ccInit(char)
  //[TODO]::public final void ccInit(String)
  //[TODO]::public final void ccInit(File)
  
  public final void ccInit(){
    ssHardCodeEnglishDict();
    cmPointer=cmEnglishDict;
  }//+++
  
  private void ssHardCodeEnglishDict(){
    
    cmEnglishDict.ccSet("V-Motor", "%Motor%");
    
    //--
    cmEnglishDict.ccSet("--vburning", "V-Burning");
    cmEnglishDict.ccSet("--temptarget-vburner", "['C]:v burner target");
    cmEnglishDict.ccSet("--templimit-bagentrance-low", "['C]:bag entrance low limit");
    cmEnglishDict.ccSet("--templimit-bagentrance-high", "['C]:bag entrance high limit");
    
    //--
    cmEnglishDict.ccSet("--autoweigh", "Auto-Weigh");
    cmEnglishDict.ccSet(McKeyHolder.K_AW_TIME_DRY,"[S]:dry time (will be rounded)");
    cmEnglishDict.ccSet(McKeyHolder.K_AW_TIME_WET, "[S]:wet time (will be rounded)");
    cmEnglishDict.ccSet("--bAD-asoverscale", "[AD]:asphalt over scale value (will be rounded)");
    
    //--
    cmEnglishDict.ccSet("--feederscale", "Feeder-Scale");
    
    //--
    cmEnglishDict.ccSet("--current", "Current");
    cmEnglishDict.ccSet("--gct-ad-offset","[A]:general AD value offset");
    cmEnglishDict.ccSet("--gct-ad-span","[A]:general AD value span");
    cmEnglishDict.ccSet("--gct-ct-offset","[A]:general current value span");
    
    cmEnglishDict.ccSet(McKeyHolder.K_CS_CT_SPAN_S_X, "[A]:compressor span value");
    cmEnglishDict.ccSet(McKeyHolder.K_CS_CT_ALART_S_X, "[A]:compressor alarm value");
    
    //--
    cmEnglishDict.ccSet(McKeyHolder.K_LT_TITLE, "Logic-Timer");
    cmEnglishDict.ccSet(McKeyHolder.ccGetLogicTimerSlotKey(1),"[S]:mixer gate open time");
    
    //--
    System.out.println("ppptable.McTranslator.ssHardCodeEnglishDict()::done");
    
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
