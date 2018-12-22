/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pppmain;

import pppunit.*;
import kosui.ppplocalui.EcBaseCoordinator;

public class MainLocalCoordinator extends EcBaseCoordinator{
  
  
  public EcMixer cmMixer;
  public SubVFeederGroup cmVFeederGroup;
    
  public MainLocalCoordinator(){
    
    super();
    
    cmMixer=new EcMixer("mixer", 100, 190, 1650);
    ccAddElement(cmMixer);
    
    cmVFeederGroup=new SubVFeederGroup();
    ccAddGroup(cmVFeederGroup);
    
    
  }//+++ 
  

}//***eof
