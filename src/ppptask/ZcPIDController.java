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

package ppptask;

import static processing.core.PApplet.map;

//[TODO]::shoud this be part of kosui??
public class ZcPIDController{
  
  private float
    cmCurrent,
    cmTarget,cmShiftedTarget,
    cmDeadZone,cmDeadZoneP,cmDeadZoneN,
    cmProportion,cmProportionP,cmProportionN,
    cmAnalogOutput
  ;//...
  
  public ZcPIDController(){
    cmTarget=100f;
    cmDeadZone=0.02f;
    cmProportion=0.20f;
    ccApplyProportion();
    //--
    cmCurrent=0f;
    cmAnalogOutput=0f;
    cmShiftedTarget=cmTarget;
  }//+++
  
  public ZcPIDController(float pxTarget, float pxProportion, float pxDead){
    ccSetupPID(pxTarget, pxProportion, pxDead);
    ccApplyProportion();
    //--
    cmCurrent=0f;
    cmAnalogOutput=0f;
    cmShiftedTarget=cmTarget;
  }//+++
  
  //=== 
  
  public final void ccStep(float pxCurrent){
    cmCurrent=pxCurrent;
    cmShiftedTarget=cmTarget;
    ccApplyProportion();
    ccProportional();
  }//+++
  
  public final void ccStep(
    float pxCurrent,boolean pxIntegralPLS, boolean pxDrivativePLS
  ){
    if(pxDrivativePLS){cmCurrent=pxCurrent;}
    if(pxIntegralPLS){ccIntegral();}
    ccApplyProportion();
    ccProportional();
  }//+++
  
  //===
  
  private void ccProportional(){
    cmAnalogOutput=0.0f;
    if(cmCurrent>=cmProportionN && cmCurrent<=cmDeadZoneN)
      {cmAnalogOutput=map(cmCurrent,cmProportionN,cmDeadZoneN,1.0f,0.0f);}
    if(cmCurrent>=cmDeadZoneP && cmCurrent<=cmProportionP)
      {cmAnalogOutput=map(cmCurrent,cmDeadZoneP,cmProportionP,0.0f,-1.0f);}
    if(cmCurrent<cmProportionN){cmAnalogOutput=1.0f;}
    if(cmCurrent>cmProportionP){cmAnalogOutput=-1.0f;}
  }//+++
  
  private void ccApplyProportion(){
    cmDeadZoneN=cmShiftedTarget*(1-cmDeadZone);
    cmDeadZoneP=cmShiftedTarget*(1+cmDeadZone);
    cmProportionN=cmShiftedTarget*(1-cmProportion);
    cmProportionP=cmShiftedTarget*(1+cmProportion);
  }//+++
  
  private void ccIntegral(){
    float lpDiff=cmTarget-cmCurrent;
    float lpDead=cmTarget*cmDeadZone;
    if(lpDiff>lpDead){cmShiftedTarget+=lpDead;}
    if(lpDiff<-lpDead){cmShiftedTarget-=lpDead;}
  }//+++
  
  //===
  
  public final void ccSetupPID(
    float pxTarget, float pxProportion, float pxDead
  ){
    cmTarget=pxTarget;
    cmDeadZone=pxDead;
    cmProportion=pxProportion;
  }//+++
  
  public final void ccSetTarget(float pxValue){
    cmTarget=pxValue;
  }//+++
  
  public final void ccSetDeadZone(float pxZeroToOne){
    if(pxZeroToOne>=0f && pxZeroToOne <= 1.0f){
      cmDeadZone=pxZeroToOne;
    }
  }//+++
  
  public final void ccSetProportion(float pxZeroToOne){
    if(pxZeroToOne>=0f && pxZeroToOne <= 1.0f){
      cmProportion=pxZeroToOne;
    }
  }//+++
  
  public final void ccResetIntegralResult(){
    cmShiftedTarget=cmTarget;
  }//+++
  
  //===
  
  public final float ccGetAnalogOutput(){return cmAnalogOutput;}//+++
  public final float ccGetShiftedTarget(){return cmShiftedTarget;}//+++
  public final boolean ccGetPositiveOutput(){return cmAnalogOutput>0;}//+++
  public final boolean ccGetNegativeOutput(){return cmAnalogOutput<0;}//+++
  
}//*** eof
