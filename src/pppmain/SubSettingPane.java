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

package pppmain;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import kosui.pppswingui.McTableAdapter;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScList;
import kosui.pppswingui.ScTable;

public final class SubSettingPane extends JPanel{
  
  private static SubSettingPane settingPane=null;
  
  //===
  
  private SubSettingPane(ActionListener pxListener){
    
    super(new BorderLayout(1, 1));
    
    ScList lpList = new ScList(180, 400);
    lpList.ccAdd("Weigh");
    lpList.ccAdd("Cell");
    lpList.ccAdd("AG Supply");
    lpList.ccAdd("FR Supply");
    lpList.ccAdd("AS Supply");
    add(lpList, BorderLayout.LINE_START);
    
    
    ScTable lpTable = new ScTable(new McTableAdapter(), 200, 400);
    JButton lpModifyButton=ScFactory.ccMyCommandButton
      ("MOD", "--button-modify", pxListener);
    
    JPanel lpRightPanel=ScFactory.ccMyBorderPanel(1);
    lpRightPanel.add(lpTable, BorderLayout.CENTER);
    lpRightPanel.add(lpModifyButton, BorderLayout.PAGE_END);
    
    add(lpRightPanel,BorderLayout.CENTER);
    
  }//++!
  
  public static final SubSettingPane ccInit(ActionListener pxListener){
    if(settingPane==null){settingPane=new SubSettingPane(pxListener);}
    return settingPane;
  }//++!
  
}//***eof
