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

import static processing.core.PApplet.nf;

public final class McKeyHolder {
  
  private static McKeyHolder self;
  public static McKeyHolder ccGetReference(){
    if(self==null){self=new McKeyHolder();}
    return self;
  }//++!

  //===
  
  public static final String
    
    //-- Auto Weigh
    K_AW_TITLE = "--autoweigh",
    //-- Auto Weigh ** mix
    K_AW_TIME_DRY="--aTime-dry",
    K_AW_TIME_WET="--aTime-wet",
    K_AW_AD_ASP = "--bAD-asoverscale",
    //-- Auto Weigh ** zero
    K_AW_ZREF_AG = "--wZeroref-ag",
    K_AW_ZREF_FR = "--wZeroref-fr",
    K_AW_ZREF_AS = "--wZeroref-as",
    //--
    K_AW_ZOFF_AG = "--wZerooffset-ag",
    K_AW_ZOFF_FR = "--wZerooffset-fr",
    K_AW_ZOFF_AS = "--wZrooffset-as",
    //--
    K_AW_EMPTY_AG = "--empty-ag",
    K_AW_EMPTY_FR = "--empty-fr",
    K_AW_EMPTY_AS = "--empty-as",
    //-- Auto Weigh ** control
    K_AW_DP_AG_X = "--droppoint-ag",
    K_AW_DP_FR_X = "--droppoint-fr",
    K_AW_DP_AS_X = "--droppoint-as",
    //--
    K_AW_CP_AG_X = "--cutoffset-ag",
    K_AW_CP_FR_X = "--cutoffset-fr",
    K_AW_CP_AS_X = "--cutoffset-as",
    
    //-- VBurning
    K_VB_TITLE = "--vburning",
    //-- VBurning ** target
    K_VB_TTGT_VB = "--temptarget-vburner",
    //-- VBurning ** limit
    K_VB_TLMT_ENT_L = "--templimit-bagentrance-low",
    K_VB_TLMT_ENT_H = "--templimit-bagentrance-high",
    K_VB_DLMT_VB_L = "--deglimit-vb-low",
    K_VB_DLMT_VB_H = "--deglimit-vb-high",
    K_VB_DLMT_VE_L = "--deglimit-ve-low",
    K_VB_DLMT_VE_H = "--deglimit-ve-high",
    //-- VBurning ** pid
    K_VB_VB_PID_P = "--vburnertemp-pid-proportion",
    K_VB_VB_PID_D = "--vburnertemp-pid-dead",
    K_VB_VE_PID_P = "--vexfpress-pid-proportion",
    K_VB_VE_PID_D = "--vexfpress-pid-dead",
    
    //-- Feeder Scale
    K_FS_TITLE = "--feederscale",
    //-- Feeder Scale ** general
    K_FS_VF_DIV ="--aVF-adjust-div",
    K_FS_F_RPM_OFF_X="--feeder-rpm-offset-v",
    K_FS_F_RPM_SPN_X="--feeder-rpm-span-v",
    K_FS_F_TPH_OFF_X="--feeder-tph-offset-v",
    K_FS_F_TPH_SPN_X="--feeder-tph-span-v",
    
    //-- General Sclae
    K_GS_TITLE = "--general-scale",
    //-- General Sclae ** cell
    K_GS_C_AG_AD_OFF = "--cell-ag-ad-offset",
    K_GS_C_AG_AD_SPN = "--cell-ag-ad-span"  ,
    K_GS_C_AG_KG_OFF = "--cell-ag-kg-offset",
    K_GS_C_AG_KG_SPN = "--cell-ag-kg-span"  ,
    //--
    K_GS_C_FR_AD_OFF = "--cell-fr-ad-offset",
    K_GS_C_FR_AD_SPN = "--cell-fr-ad-span"  ,
    K_GS_C_FR_KG_OFF = "--cell-fr-kg-offset",
    K_GS_C_FR_KG_SPN = "--cell-fr-kg-span"  ,
    //--
    K_GS_C_AS_AD_OFF = "--cell-as-ad-offset",
    K_GS_C_AS_AD_SPN = "--cell-as-ad-span"  ,
    K_GS_C_AS_KG_OFF = "--cell-as-kg-offset",
    K_GS_C_AS_KG_SPN = "--cell-as-kg-span"  ,
    //-- General Sclae ** degree
    K_GS_D_VB_AD_OFF = "--degree-vb-ad-offset",
    K_GS_D_VB_AD_SPN = "--degree-vb-ad-span"  ,
    K_GS_D_VB_PT_OFF = "--degree-vb-pt-offset",
    K_GS_D_VB_PT_SPN = "--degree-vb-pt-span"  ,
    //--
    K_GS_D_VE_AD_OFF = "--degree-ve-ad-offset",
    K_GS_D_VE_AD_SPN = "--degree-ve-ad-span"  ,
    K_GS_D_VE_PT_OFF = "--degree-ve-pt-offset",
    K_GS_D_VE_PT_SPN = "--degree-ve-pt-span"  ,
    //-- General Sclae ** misc
    K_GS_D_VD_AD_OFF = "--degree-vd-ad-offset",
    K_GS_D_VD_AD_SPN = "--degree-vd-ad-span"  ,
    K_GS_D_VD_KP_OFF = "--degree-vd-kp-offset",
    K_GS_D_VD_KP_SPN = "--degree-vd-kp-span"  ,
    
    //-- Temp Scale
    K_TS_TITLE = "--temperature",
    //-- Temp Scale ** general
    K_TS_GT_AD_OFF="--aaGtemp-ad-offset",
    K_TS_GT_AD_SPN = "--aaGtemp-ad-span"  ,
    K_TS_GT_DC_OFF = "--aaGtemp-dc-offset",
    K_TS_GT_DC_SPN = "--aaGtemp-dc-span" ,
    //-- Temp Scale ** thermo
    K_TS_CHUTE_BIAS = "--agchute-tbias"   ,
    K_TS_CHUTE_OFFS = "--agchute-toffset" ,
    //--
    K_TS_PIPE_BIAS = "--aspipe-tbias"   ,
    K_TS_PIPE_OFFS = "--aspipe-toffset" ,
    //--
    K_TS_ENT_BIAS = "--bagentrance-tbias"   ,
    K_TS_ENT_OFFS = "--bagentrance-toffset" ,
    //--
    K_TS_SAND_BIAS = "--agsand-tbias"   ,
    K_TS_SAND_OFFS = "--agsand-toffset" ,
    //--
    K_TS_MIXER_BIAS = "--mixer-tbias"   ,
    K_TS_MIXER_OFFS = "--mixer-toffset" ,
    
    //-- Current Scale
    K_CS_TITLE = "--current",
    //-- Current Scale ** general
    K_CS_G_AD_OF = "--gct-ad-offset",
    K_CS_G_AD_SP = "--gct-ad-span",
    K_CS_G_CT_OF = "--gct-ad-span",
    //-- Current Scale ** span n alart
    K_CS_CT_SPAN_S_X="--ct-span-slot",
    K_CS_CT_ALART_S_X="--ct-alart-slot",
    
    //-- Logic Timer
    K_LT_TITLE  = "--logic-timer",
    K_LT_SLOT_X = "--l-tm-slot" 
    
  ;//...
  
  //===

  private McKeyHolder(){}//++!
  
  //===
  
  public static final String ccGetAGDropPointKey(int pxIndex){
    return K_AW_DP_AG_X+nf(pxIndex&0x7,1);
  }//+++
  
  public static final String ccGetFRDropPointKey(int pxIndex){
    return K_AW_DP_FR_X+nf(pxIndex&0x3,1);
  }//+++
  
  
  public static final String ccGetASDropPointKey(int pxIndex){
    return K_AW_DP_AS_X+nf(pxIndex&0x3,1);
  }//+++
  
  public static final String ccGetAGCutOffsetKey(int pxIndex){
    return K_AW_CP_AG_X+nf(pxIndex&0x7,1);
  }//+++
  
  public static final String ccGetFRCutOffsetKey(int pxIndex){
    return K_AW_CP_FR_X+nf(pxIndex&0x7,1);
  }//+++
  
  public static final String ccGetASCutOffsetKey(int pxIndex){
    return K_AW_CP_AS_X+nf(pxIndex&0x7,1);
  }//+++
  
  //===
  
  public static final String ccGetVFeederRpmOffset(int pxIndex){
    return K_FS_F_RPM_OFF_X+nf(pxIndex&0x0F,2);
  }//+++
  
  public static final String ccGetVFeederRpmSpan(int pxIndex){
    return K_FS_F_RPM_SPN_X+nf(pxIndex&0x0F,2);
  }//+++
  
  public static final String ccGetVFeederTphOffset(int pxIndex){
    return K_FS_F_TPH_OFF_X+nf(pxIndex&0x0F,2);
  }//+++
  
  public static final String ccGetVFeederTphSpan(int pxIndex){
    return K_FS_F_TPH_SPN_X+nf(pxIndex&0x0F,2);
  }//+++
  
  //===
  
  public static final String ccGetCTSlotSpanKey(int pxIndex){
    return K_CS_CT_SPAN_S_X+nf(pxIndex&0x1F,2);
  }//+++
  
  public static final String ccGetCTSlotAlartKey(int pxIndex){
    return K_CS_CT_ALART_S_X+nf(pxIndex&0x1F,2);
  }//+++
  
  //===
  
  public static final String ccGetLogicTimerSlotKey(int pxIndex){
    return K_LT_SLOT_X+nf(pxIndex&0xFF,3);
  }//+++
  
 }//***eof
