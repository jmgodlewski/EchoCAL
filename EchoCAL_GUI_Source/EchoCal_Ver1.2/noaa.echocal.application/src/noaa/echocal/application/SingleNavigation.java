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

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.awt.Component;
import java.io.IOException;
import javax.swing.JOptionPane;

public class SingleNavigation implements Runnable
{
    //A lock designed to stop a single navigation thread.
    public static boolean stopSingleNavigationInstance = false;
    
    public SingleNavigation(float[] intendedDestination) 
    {
        try 
        {
            NavigationControls.navigateSphereToPositionButton.setText("Navigating sphere...");
            Geometry.euclidianInputNavigation(intendedDestination);
            NavigationControls.navigateSphereToPositionButton.setText("Navigate to position");
            ButtonHandler.buttonWasPressed = false;
        }//Catch problems with the COM port communications. 
        catch (PortInUseException | IOException | InterruptedException | UnsupportedCommOperationException | NoSuchPortException | UnsupportedOperationException ex) 
        {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "There was an error with the navigation command.");
            NavigationControls.navigateSphereToPositionButton.setText("Navigate to position");
        }//Catch problems when no downrigger serial number is assigned to
         //the downrigger position.
        catch (NullPointerException ex)
        {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "No device is assigned to downrigger position.");
            NavigationControls.navigateSphereToPositionButton.setText("Navigate to position");
            
        }
        ButtonHandler.buttonWasPressed = false;
     }

    @Override
    public void run() 
    {

    }
}
