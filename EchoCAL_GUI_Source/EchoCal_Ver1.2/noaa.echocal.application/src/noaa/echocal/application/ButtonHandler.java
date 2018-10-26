/*
 * Copyright (C) 2016 JGodlewski
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package noaa.echocal.application;


public class ButtonHandler
{
    //If a downrigger control button was pressed this boolean is true.
    public static boolean buttonWasPressed = false;
    
    public static void enableNavigationControls()
    {
        NavigationControls.portForwardInButton.setEnabled(true);
        NavigationControls.portForwardOutButton.setEnabled(true);
        NavigationControls.portMiddleInButton.setEnabled(true);
        NavigationControls.portMiddleOutButton.setEnabled(true);
        NavigationControls.portAftInButton.setEnabled(true);
        NavigationControls.portAftOutButton.setEnabled(true);

        NavigationControls.starboardForwardInButton.setEnabled(true);
        NavigationControls.starboardForwardOutButton.setEnabled(true);
        NavigationControls.starboardMiddleInButton.setEnabled(true);
        NavigationControls.starboardMiddleOutButton.setEnabled(true);
        NavigationControls.starboardAftInButton.setEnabled(true);
        NavigationControls.starboardAftOutButton.setEnabled(true);

        // Check to see if the Joystick is disabled. If it is, then we want
        // to enable the virtual joystick buttons. If the Joystick is enabled,
        // we want to leave these buttons disabled.
        if (!JoystickConfigurationTopComponent.bEnable){
            NavigationControls.leftButton.setEnabled(true);
            NavigationControls.rightButton.setEnabled(true);
            NavigationControls.forwardButton.setEnabled(true);
            NavigationControls.aftButton.setEnabled(true);
            NavigationControls.aftLeftButton.setEnabled(true);
            NavigationControls.aftRightButton.setEnabled(true);
            NavigationControls.forwardLeftButton.setEnabled(true);
            NavigationControls.forwardRightButton.setEnabled(true);
        }
        
        NavigationControls.sinkButton.setEnabled(true);
        NavigationControls.riseButton.setEnabled(true);

        NavigationControls.gridTypeButton.setEnabled(true);
        NavigationControls.starTypeButton.setEnabled(true);
        NavigationControls.spiralTypeButton.setEnabled(true);
    }
    
    public static void disableNavigationControls()
    {
        NavigationControls.portForwardInButton.setEnabled(false);
        NavigationControls.portForwardOutButton.setEnabled(false);
        NavigationControls.portMiddleInButton.setEnabled(false);
        NavigationControls.portMiddleOutButton.setEnabled(false);
        NavigationControls.portAftInButton.setEnabled(false);
        NavigationControls.portAftOutButton.setEnabled(false);

        NavigationControls.starboardForwardInButton.setEnabled(false);
        NavigationControls.starboardForwardOutButton.setEnabled(false);
        NavigationControls.starboardMiddleInButton.setEnabled(false);
        NavigationControls.starboardMiddleOutButton.setEnabled(false);
        NavigationControls.starboardAftInButton.setEnabled(false);
        NavigationControls.starboardAftOutButton.setEnabled(false);

        NavigationControls.leftButton.setEnabled(false);
        NavigationControls.rightButton.setEnabled(false);
        NavigationControls.forwardButton.setEnabled(false);
        NavigationControls.aftButton.setEnabled(false);
        NavigationControls.aftLeftButton.setEnabled(false);
        NavigationControls.aftRightButton.setEnabled(false);
        NavigationControls.forwardLeftButton.setEnabled(false);
        NavigationControls.forwardRightButton.setEnabled(false);
        NavigationControls.sinkButton.setEnabled(false);
        NavigationControls.riseButton.setEnabled(false);   
    }
}
