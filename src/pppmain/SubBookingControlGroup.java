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

package pppmain;

import java.util.ArrayList;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcPane;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiUpdatable;

public class SubBookingControlGroup implements EiGroup{
  
  private final EcPane cmPane;//...
  
  public SubBookingControlGroup(){
    
    cmPane=new EcPane();
    cmPane.ccSetLocation(290, 465);
    cmPane.ccSetTitle("A-Booking");
    
    //[HEAD]::what now??
    
    
    cmPane.ccSetSize(200, 100);
    
  }//+++ 
  
  

  @Override
  public ArrayList<EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<>();
    return lpRes;
  }//+++

  @Override
  public ArrayList<EiUpdatable> ccGiveShapeList(){
    ArrayList<EiUpdatable> lpRes=new ArrayList<>();
    lpRes.add(cmPane);
    return lpRes;
  }//+++
  
  

}//***eof
