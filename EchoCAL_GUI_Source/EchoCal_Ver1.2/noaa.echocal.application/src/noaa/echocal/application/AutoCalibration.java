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

/**
 *
 * @author spacetimeengineer
 */

public class AutoCalibration extends Thread
{
    public static String navigationState="stopped";
    public static String autoCalibrationModeType="";
    
    
    @Override
    public void run() 
    {
        //Just a single thread.  All this does is handle the individual autocal
        //functions.
        try
        {
            //If the isUpdatedToggleButton is pressed.
            if (NavigationControls.updatedButton.isSelected()) 
            {
                switch (autoCalibrationModeType)
                {
                    case "star":
                    {
                        //Begin the star type auto-calibration session.
                        Geometry.starTypeAutomatedNavigation(Float.parseFloat(NavigationControls.fullBeamAngleTextfieldInput.getText()));
                        break;
                    }
                    case "spiral":
                    {
                        //Begin the spiral type auto-calibration session.
                        Geometry.spiralTypeAutomatedNavigation(Float.parseFloat(NavigationControls.fullBeamAngleTextfieldInput.getText()));
                        break;
                    }
                    case "grid":
                    {
                        //Begin the grid type auto-calibration session.
                        Geometry.gridTypeAutomatedNavigation(Float.parseFloat(NavigationControls.fullBeamAngleTextfieldInput.getText()));
                        break;
                    }
                    case "single":
                    {
                        navigationState = "running";
                        try
                        {
                            float[] intendedDestination = {Float.parseFloat(NavigationControls.navigatePositionXTextbox.getText()),Float.parseFloat(NavigationControls.navigatePositionYTextbox.getText()),Float.parseFloat(NavigationControls.navigatePositionZTextbox.getText())};
                            new Thread(new SingleNavigation(intendedDestination)).start();
                        }
                        catch (NumberFormatException ex)
                        {
                            navigationState = "stopped";
                            Component frame = null;
                            JOptionPane.showMessageDialog(frame, "You must enter a valid integer for each coordinate input \n"
                                                         + "before you can navigate the sphere.");
                            //Reset Navigation Controls.
                            NavigationControls.updatedButton.setSelected(false);
                            NavigationControls.updatedButton.setText("Activate");
                            NavigationControls.stopCalibrationButton.setEnabled(false);
                            NavigationControls.pauseCalibrationButton.setEnabled(false);
                            NavigationControls.runCalibrationButton.setEnabled(true);
                        }
                        break;
                    }
                    case "":
                    {
                        Component frame = null;
                        JOptionPane.showMessageDialog(frame, "You must choose an auto-calibration type!\n"
                                                     +"This can be acomplished with the toggle buttons on the right side of the navigation controls.");
                        break;
                    }
                    default:
                    {
                        //Breaks out of default statement.
                        break;
                    }
                }
            }
            else if (!NavigationControls.updatedButton.isSelected() && autoCalibrationModeType.equals("single"))
            {
                navigationState = "running";
                try
                {
                    float[] intendedDestination = {Float.parseFloat(NavigationControls.navigatePositionXTextbox.getText()),Float.parseFloat(NavigationControls.navigatePositionYTextbox.getText()),Float.parseFloat(NavigationControls.navigatePositionZTextbox.getText())};
                    new Thread(new SingleNavigation(intendedDestination)).start();
                }
                catch (NumberFormatException ex)
                {
                    navigationState = "stopped";
                    Component frame = null;
                    JOptionPane.showMessageDialog(frame, "You must enter a valid integer for each coordinate input \n"
                                                 + " before you can navigate the sphere.");
                    //Reset Navigation Controls.
                    NavigationControls.updatedButton.setSelected(false);
                    NavigationControls.updatedButton.setText("Activate");
                    NavigationControls.stopCalibrationButton.setEnabled(false);
                    NavigationControls.pauseCalibrationButton.setEnabled(false);
                    NavigationControls.runCalibrationButton.setEnabled(true);
                }
            }
            else
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Make sure the target Echogram coordinates are set.\n"
                                              +"Once finished you must click the 'Activate' toggle button so that EchoCal knows this task is complete.\n"
                                              +"This button must be pressed before the auto-calibration can begin.");
            }
        }
        //If any of the following exceptions are caught.
        catch (PortInUseException | InterruptedException | IOException | UnsupportedCommOperationException | NoSuchPortException | NullPointerException | NumberFormatException ex)
        {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "You must enter the full beam angle before the calibration can begin.");
            navigationState="stopped";
            //Reset Navigation Controls.
            NavigationControls.updatedButton.setSelected(false);
            NavigationControls.updatedButton.setText("Activate");
            NavigationControls.stopCalibrationButton.setEnabled(false);
            NavigationControls.pauseCalibrationButton.setEnabled(false);
            NavigationControls.runCalibrationButton.setEnabled(true);
        }
    } 
}
