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

/*
 * ECHOCal Software
 * 
 * Description:
 * This software is designed to communicate with the firmware of the downriggers built by Joe.
 * The ECHOCal software GUI gives the user access to the control of the downriggers.
 * Purpose:
 * To increase the overall efficiency of calibration sessions.
 */

/*
 * ECHOCal Developers:
 * 
 * Michael.C Ryan - Software Engineer
 * Email: michael.cranston.ryan@noaa.gov
 * Office: (508) 495-2384
 *
 * Joseph.M Godlewski - Senior Electrical Engineer
 * Email: joseph.godlewski@noaa.gov
 * Office: (508) 495-2039
 */

/*
 * Organization:
 * 
 * United States Department of Commerce
 * National Oceanic and Atmospheric Administration
 * Northeast Fisheries Center
 * Ecosystems Survey Branch
 * 
 */

/*
 * Location
 * NOAA/NMFS/NEFSC
 * 166 Water Street
 * Woods Hole, MA 02543
 */

//The ECHOCal java package.
package noaa.echocal.application;

//Imports the necessary library for retrieving the screen dimension characteristics.
import java.awt.Component;
import java.util.Arrays;
//Imports netbeans logger library for debug use only.
import java.util.logging.Level;
//Imports netbeans logger library for debug use only.
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
* Description:
* The TensionSensor class is designed to help the echocal system detect when the
* monofiliment line is caught or snagged.  This can help prevent the lines getting
* snapped.  This is helpful since it costs time to replace monofiliment line.
* This runs a thread which reads the tension and prompts the user when the line 
* is dangerously close to snapping.
* Status:
* Class is stable.
*/
//public class TensionSensor implements Runnable
public class TensionSensor
{
    //This is the main lock which allows the tension sensor thread run or not.
    //Due to the influence of this thread on the ECHOCal system, the lock is initially turned on.
    public static boolean tensionSensorThreadLock = false;
    //The port forward downrigger coordinate index identifier.
    public static int portForward=0;
    //The port middle downrigger coordinate index identifier.
    public static int portMiddle=1;
    //The port aft downrigger coordinate index identifier.
    public static int portAft=2;
    //The starboard forward downrigger coordinate index identifier.
    public static int starboardForward=3;
    //The starboard middle downrigger coordinate index identifier.
    public static int starboardMiddle=4;
    //The starboard aft downrigger coordinate index identifier.
    public static int starboardAft=5;
    //Quick reference to the downrigger response signal.
    public static String currentResponseString = null;
    //This is implemented as a way to detect if a new response string has been sent.
    //This is more like a time limit for the system to tell if a downrigger has not
    //modified counts.
    public static float[] tensionReader = {0,0,0,0,0,0};
    //The downrigger coordinates which were intended to be reached but not necessarily
    //reached.  This is essentially how we measure tension since if the encoder wheel
    //on the downrigger is not turning due to tension then we can know how much tension
    //there is by measuring the difference to the downrigger coordinates and the
    //intended downrigger coordinates.
    public static float[] intendedDownriggerSpherePosition = {0,0,0,0,0,0};
    //This is a useful copy of the downrigger sphere position in downrigger coordinates.
    public static float[] downriggerSpherePosition = {0,0,0,0,0,0};
    //"tensionTolerance" is set as follows....currently its set at 3.0 but to
    //increase the tolerance, increase this number to 4.0.
    //It is not recomended to set to 1.0.
    public static int tensionTolerance = 3;
    //
    int progressBarTensionLimit = 4;
    
    public static void markDownriggerCoordinatesForTenionSensor()
    {
        //Copies port forward downrigger coordinates to store in variable exclusive to tension sensor class.
        downriggerSpherePosition[portForward] = Geometry.downriggerSpherePosition[portForward];
        //Copies port middle downrigger coordinates to store in variable exclusive to tension sensor class.
        downriggerSpherePosition[portMiddle] = Geometry.downriggerSpherePosition[portMiddle];
        //Copies port aft downrigger coordinates to store in variable exclusive to tension sensor class.
        downriggerSpherePosition[portAft] = Geometry.downriggerSpherePosition[portAft];
        //Copies starboard forward downrigger coordinates to store in variable exclusive to tension sensor class.
        downriggerSpherePosition[starboardForward] = Geometry.downriggerSpherePosition[starboardForward];
        //Copies starboard middle downrigger coordinates to store in variable exclusive to tension sensor class.
        downriggerSpherePosition[starboardMiddle] = Geometry.downriggerSpherePosition[starboardMiddle];
        //Copies starboard aft downrigger coordinates to store in variable exclusive to tension sensor class.
        downriggerSpherePosition[starboardAft] = Geometry.downriggerSpherePosition[starboardAft];
    }
    
    
   /*
    * Description:
    * This function defines the TensionSensor.java thread.  This thread is used to moniter in 
    * real time the approximated tension of the monofiliment line.  This is a programatic sensor
    * since it can only read not tension itself but what tension would mean for the downrigger encoders.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    /*
    @Override
    public void run()
    */
    public void checkTension()
    {
        //If the tension sensor lock is off then continue.
        if(tensionSensorThreadLock == false)
        {   
            //Retrieves the serial number from the response string.
            final String serialNumber = currentResponseString.split(",")[0];
            //If the response string is a "reel in command"
            if(currentResponseString.split(",").length >= 4 && currentResponseString.split(",")[3].equals("in\r"))
            {
                //If response string is a port forward response.
                if(serialNumber.equals(Communication.portForwardSerialNumber))
                {
                    //Calculates intended downrigger position.
                    intendedDownriggerSpherePosition[portForward]=downriggerSpherePosition[portForward]-1;
                    //Give Geometry.downriggerSpherePosition time to register.
                    try 
                    {
                        //Gives time for signals to register.
                        Thread.sleep(50);
                    } 
                    //If interrupted exception is caught.
                    catch (InterruptedException ex) 
                    {
                        //Informs user in netbeans log.
                        Logger.getLogger(TensionSensor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //If with intention to, but no change in downrigger coordinates.  
                    if (Geometry.downriggerSpherePosition[portForward] == downriggerSpherePosition[portForward])
                    {
                        //Increase tension sensor value (with negative number).
                        tensionReader[portForward]=tensionReader[portForward]-intendedDownriggerSpherePosition[portForward]+Geometry.downriggerSpherePosition[portForward];
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.portForwardTensionBar.setValue((int)Math.abs((100*tensionReader[portForward]/progressBarTensionLimit)));
                    }
                    //If it did turn.
                    else
                    {
                        //Reset tension sensor to for port forward.
                        tensionReader[portForward]=0;
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.portForwardTensionBar.setValue(0);
                    }
                    //If tension sensor reaches tensionTolerance.
                    if((int)tensionReader[portForward]==tensionTolerance)
                    {
                        //Sets the navigation state to 'paused'.
                        //AutoCalibration.navigationState="paused";
                        AutoCalibration.navigationState="stopped";
                        NavigationControls.pauseCalibrationButton.setSelected(false);
                        NavigationControls.stopCalibrationButton.setEnabled(true);
                        //NavigationControls.runCalibrationButton.setEnabled(true);
                        Component frame = null;
                        JOptionPane.showMessageDialog(frame, "The monofiliment line of downrigger "+serialNumber+" is caught! Check downrigger now!");
                    }
                    //Resets the current response string to null.
                    //currentResponseString=null;
                }
                //If response string is a port middle response.
                else if(serialNumber.equals(Communication.portMiddleSerialNumber))
                {
                    //Calculates intended downrigger position.
                    intendedDownriggerSpherePosition[portMiddle]=downriggerSpherePosition[portMiddle]-1;
                    //Give Geometry.downriggerSpherePosition time to register.
                    try 
                    {
                        //Sleeps the thread to give time to register.
                        Thread.sleep(50);
                    } 
                    //If an InterruptedException is caught.
                    catch (InterruptedException ex) 
                    {
                        //Logs the InterruptedException to the debugger.
                        Logger.getLogger(TensionSensor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //If with intention to, but no change in downrigger coordinates.  
                    if (Geometry.downriggerSpherePosition[portMiddle] == downriggerSpherePosition[portMiddle])
                    {
                        //Apply tension reading to tension reader.
                        tensionReader[portMiddle]=tensionReader[portMiddle]-intendedDownriggerSpherePosition[portMiddle]+Geometry.downriggerSpherePosition[portMiddle];
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.portMiddleTensionBar.setValue((int)Math.abs((100*tensionReader[portMiddle]/progressBarTensionLimit)));
                    }
                    //If it did turn.
                    else
                    {
                        //Resets tension reader for port middle.
                        tensionReader[portMiddle]=0;
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.portMiddleTensionBar.setValue(0);
                    }
                    //If tension sensor reaches tensionTolerance.
                    if((int)tensionReader[portMiddle]==tensionTolerance)
                    {
                        //Sets the navigation state to 'paused'.
                        //AutoCalibration.navigationState="paused";
                        AutoCalibration.navigationState="stopped";
                        NavigationControls.pauseCalibrationButton.setSelected(false);
                        NavigationControls.stopCalibrationButton.setEnabled(true);
                        //NavigationControls.runCalibrationButton.setEnabled(true);
                        Component frame = null;
                        JOptionPane.showMessageDialog(frame, "The monofiliment line of downrigger "+serialNumber+" is caught! Check downrigger now!");
                    }
                    //Resets current response string.
                    //currentResponseString=null;
                }
                //If response string is a port aft response.
                else if(serialNumber.equals(Communication.portAftSerialNumber))
                {
                    //Calculates intended downrigger position.
                    intendedDownriggerSpherePosition[portAft]=downriggerSpherePosition[portAft]-1;
                    //Give Geometry.downriggerSpherePosition time to register.
                    try 
                    {
                        //Sleeps the thread to give time to register.
                        Thread.sleep(50);
                    } 
                    //If interrupted exception is caught.
                    catch (InterruptedException ex) 
                    {
                        //Informs user in netbeans log.
                        Logger.getLogger(TensionSensor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //If with intention to, but no change in downrigger coordinates.  
                    if (Geometry.downriggerSpherePosition[portAft] == downriggerSpherePosition[portAft])
                    {
                        //Apply tension reading to tension reader.
                        tensionReader[portAft]=tensionReader[portAft]-intendedDownriggerSpherePosition[portAft]+Geometry.downriggerSpherePosition[portAft];
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.portAftTensionBar.setValue((int)Math.abs((100*tensionReader[portAft]/progressBarTensionLimit)));
                    }
                    //If it did turn.
                    else
                    {
                        //Resets tension reader for port aft.
                        tensionReader[portAft]=0;
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.portAftTensionBar.setValue(0);
                    }
                    //If tension sensor reaches tensionTolerance.
                    if((int)tensionReader[portAft]==tensionTolerance)
                    {
                        //Sets the navigation state to 'paused'.
                        //AutoCalibration.navigationState="paused";
                        AutoCalibration.navigationState="stopped";
                        NavigationControls.pauseCalibrationButton.setSelected(false);
                        NavigationControls.stopCalibrationButton.setEnabled(true);
                        //NavigationControls.runCalibrationButton.setEnabled(true);
                        Component frame = null;
                        JOptionPane.showMessageDialog(frame, "The monofiliment line of downrigger "+serialNumber+" is caught! Check downrigger now!");
                    }
                    //Resets current response string.
                    //currentResponseString=null;
                }
                //If response string is a starboard forward response.
                else if(serialNumber.equals(Communication.starboardForwardSerialNumber))
                {
                    //Calculates intended downrigger position.
                    intendedDownriggerSpherePosition[starboardForward]=downriggerSpherePosition[starboardForward]-1;
                    //Give Geometry.downriggerSpherePosition time to register.
                    try 
                    {
                        //Sleeps the thread to give time to register.
                        Thread.sleep(50);
                    } 
                    //If interrupted exception is caught.
                    catch (InterruptedException ex) 
                    {
                        //Informs user in netbeans log.
                        Logger.getLogger(TensionSensor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //If with intention to, but no change in downrigger coordinates.  
                    if (Geometry.downriggerSpherePosition[starboardForward] == downriggerSpherePosition[starboardForward])
                    {
                        //Apply tension reading to tension reader.
                        tensionReader[starboardForward]=tensionReader[starboardForward]-intendedDownriggerSpherePosition[starboardForward]+Geometry.downriggerSpherePosition[starboardForward];
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.starboardForwardTensionBar.setValue((int)Math.abs((100*tensionReader[starboardForward]/progressBarTensionLimit)));
                    }
                    //If it did turn.
                    else
                    {
                        //Resets tension reader for starboard forward.
                        tensionReader[starboardForward]=0;
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.starboardForwardTensionBar.setValue(0);
                    }
                    //If tension sensor reaches tensionTolerance.
                    if((int)tensionReader[starboardForward]==tensionTolerance)
                    {
                        //Sets the navigation state to 'paused'.
                        //AutoCalibration.navigationState="paused";
                        AutoCalibration.navigationState="stopped";
                        NavigationControls.pauseCalibrationButton.setSelected(false);
                        NavigationControls.stopCalibrationButton.setEnabled(true);
                        //NavigationControls.runCalibrationButton.setEnabled(true);
                        Component frame = null;
                        JOptionPane.showMessageDialog(frame, "The monofiliment line of downrigger "+serialNumber+" is caught! Check downrigger now!");
                    }
                    //Resets current response string.
                    //currentResponseString=null;
                }
                //If response string is a starboard middle response.
                else if(serialNumber.equals(Communication.starboardMiddleSerialNumber))
                {
                    //Calculates intended downrigger position.
                    intendedDownriggerSpherePosition[starboardMiddle]=downriggerSpherePosition[starboardMiddle]-1;
                    //Give Geometry.downriggerSpherePosition time to register.
                    try 
                    {
                        //Sleeps the thread to give time to register.
                        Thread.sleep(50);
                    } 
                    //If interrupted exception is caught.
                    catch (InterruptedException ex) 
                    {
                        //Informs user in netbeans log.
                        Logger.getLogger(TensionSensor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //If with intention to, but no change in downrigger coordinates.  
                    if (Geometry.downriggerSpherePosition[starboardMiddle] == downriggerSpherePosition[starboardMiddle])
                    {
                        //Apply tension reading to tension reader.
                        tensionReader[starboardMiddle]=tensionReader[starboardMiddle]-intendedDownriggerSpherePosition[starboardMiddle]+Geometry.downriggerSpherePosition[starboardMiddle];
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.starboardMiddleTensionBar.setValue((int)Math.abs((100*tensionReader[starboardMiddle]/progressBarTensionLimit)));
                    }
                    //If it did turn.
                    else
                    {
                        //Resets tension reader for starboard middle.
                        tensionReader[starboardMiddle]=0;
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.starboardMiddleTensionBar.setValue(0);
                    }
                    //If tension sensor reaches tensionTolerance.
                    if((int)tensionReader[starboardMiddle]==tensionTolerance)
                    {
                        //Sets the navigation state to 'paused'.
                        //AutoCalibration.navigationState="paused";
                        AutoCalibration.navigationState="stopped";
                        NavigationControls.pauseCalibrationButton.setSelected(false);
                        NavigationControls.stopCalibrationButton.setEnabled(true);
                        //NavigationControls.runCalibrationButton.setEnabled(true);
                        Component frame = null;
                        JOptionPane.showMessageDialog(frame, "The monofiliment line of downrigger "+serialNumber+" is caught! Check downrigger now!");
                    }
                    //Resets current response string.
                    //currentResponseString=null;
                }
                //If response string is a starboard aft response.
                else if(serialNumber.equals(Communication.starboardAftSerialNumber))
                {
                    //Calculates intended downrigger position.
                    intendedDownriggerSpherePosition[starboardAft]=downriggerSpherePosition[starboardAft]-1;
                    //Give Geometry.downriggerSpherePosition time to register.
                    try 
                    {
                        //Sleeps the thread to give time to register.
                        Thread.sleep(50);
                    } 
                    //If interrupted exception is caught.
                    catch (InterruptedException ex) 
                    {
                        //Informs user in netbeans log.
                        Logger.getLogger(TensionSensor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //If with intention to, but no change in downrigger coordinates.  
                    if (Geometry.downriggerSpherePosition[starboardAft] == downriggerSpherePosition[starboardAft])
                    {
                        //Apply tension reading to tension reader.
                        tensionReader[starboardAft]=tensionReader[starboardAft]-intendedDownriggerSpherePosition[starboardAft]+Geometry.downriggerSpherePosition[starboardAft];
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.starboardAftTensionBar.setValue((int)Math.abs((100*tensionReader[starboardAft]/progressBarTensionLimit)));
                    }
                    //If it did turn.
                    else
                    {
                        //Resets tension reader for starbord aft.
                        tensionReader[starboardAft]=0;
                        //Sets the tension value on the progress bar.
                        CoordinateDisplayTopComponent.starboardAftTensionBar.setValue(0);
                    }
                    //If tension sensor reaches tensionTolerance.
                    if((int)tensionReader[starboardAft]==tensionTolerance)
                    {
                        //Sets the navigation state to 'paused'.
                        //AutoCalibration.navigationState="paused";
                        AutoCalibration.navigationState="stopped";
                        NavigationControls.pauseCalibrationButton.setSelected(false);
                        NavigationControls.stopCalibrationButton.setEnabled(true);
                        //NavigationControls.runCalibrationButton.setEnabled(true);
                        Component frame = null;
                        JOptionPane.showMessageDialog(frame, "The monofiliment line of downrigger "+serialNumber+" is caught! Check downrigger now!");
                    }
                    //Resets current response string.
                    //currentResponseString=null;
                }
                //If there is an error with the response string.
                else
                {
                    //Pass.
                }
            }
            //Relieves the tension.
            else if(currentResponseString.split(",").length >= 4 && currentResponseString.split(",")[3].equals("out\r"))
            {
                //If response string is a port forward response.
                if(serialNumber.equals(Communication.portForwardSerialNumber))
                {
                    if ((CoordinateDisplayTopComponent.portForwardTensionBar.getValue() > 0)&&
                        (CoordinateDisplayTopComponent.portForwardTensionBar.getValue() <= 75))
                    {
                        tensionReader[portForward]=tensionReader[portForward]-1;
                        CoordinateDisplayTopComponent.portForwardTensionBar.setValue((int)Math.abs((100*tensionReader[portForward]/progressBarTensionLimit)));
                    }
                    else
                    {
                        tensionReader[portForward]=0;
                        CoordinateDisplayTopComponent.portForwardTensionBar.setValue(0);
                    }
                }
                else if (serialNumber.equals(Communication.portMiddleSerialNumber))
                {
                    if ((CoordinateDisplayTopComponent.portMiddleTensionBar.getValue() > 0)&&
                        (CoordinateDisplayTopComponent.portMiddleTensionBar.getValue() <= 75))
                    {
                        tensionReader[portMiddle]=tensionReader[portMiddle]-1;
                        CoordinateDisplayTopComponent.portMiddleTensionBar.setValue((int)Math.abs((100*tensionReader[portMiddle]/progressBarTensionLimit)));
                    }
                    else
                    {
                        tensionReader[portMiddle]=0;
                        CoordinateDisplayTopComponent.portMiddleTensionBar.setValue(0);
                    }
                }
                else if (serialNumber.equals(Communication.portAftSerialNumber))
                {
                    if ((CoordinateDisplayTopComponent.portAftTensionBar.getValue() > 0)&&
                        (CoordinateDisplayTopComponent.portAftTensionBar.getValue() <= 75))
                    {
                        tensionReader[portAft]=tensionReader[portAft]-1;
                        CoordinateDisplayTopComponent.portAftTensionBar.setValue((int)Math.abs((100*tensionReader[portAft]/progressBarTensionLimit)));
                    }
                    else
                    {
                        tensionReader[portAft]=0;
                        CoordinateDisplayTopComponent.portAftTensionBar.setValue(0);
                    }
                }
                else if (serialNumber.equals(Communication.starboardForwardSerialNumber))
                {
                    if ((CoordinateDisplayTopComponent.starboardForwardTensionBar.getValue() > 0)&&
                        (CoordinateDisplayTopComponent.starboardForwardTensionBar.getValue() <= 75))
                    {
                        tensionReader[starboardForward]=tensionReader[starboardForward]-1;
                        CoordinateDisplayTopComponent.starboardForwardTensionBar.setValue((int)Math.abs((100*tensionReader[starboardForward]/progressBarTensionLimit)));
                    }
                    else
                    {
                        tensionReader[starboardForward]=0;
                        CoordinateDisplayTopComponent.starboardForwardTensionBar.setValue(0);
                    }
                }
                else if (serialNumber.equals(Communication.starboardMiddleSerialNumber))
                {
                    if ((CoordinateDisplayTopComponent.starboardMiddleTensionBar.getValue() > 0)&&
                        (CoordinateDisplayTopComponent.starboardMiddleTensionBar.getValue() <= 75))
                    {
                        tensionReader[starboardMiddle]=tensionReader[starboardMiddle]-1;
                        CoordinateDisplayTopComponent.starboardMiddleTensionBar.setValue((int)Math.abs((100*tensionReader[starboardMiddle]/progressBarTensionLimit)));
                    }
                    else
                    {
                        tensionReader[starboardMiddle]=0;
                        CoordinateDisplayTopComponent.starboardMiddleTensionBar.setValue(0);
                    }
                }
                else if (serialNumber.equals(Communication.starboardAftSerialNumber))
                {
                    if ((CoordinateDisplayTopComponent.starboardAftTensionBar.getValue() > 0)&&
                        (CoordinateDisplayTopComponent.starboardAftTensionBar.getValue() <= 75))
                    {
                        tensionReader[starboardAft]=tensionReader[starboardAft]-1;
                        CoordinateDisplayTopComponent.starboardAftTensionBar.setValue((int)Math.abs((100*tensionReader[starboardAft]/progressBarTensionLimit)));
                    }
                    else
                    {
                        tensionReader[starboardAft]=0;
                        CoordinateDisplayTopComponent.starboardAftTensionBar.setValue(0);
                    }
                }
            }
            else
            {
                //Pass
            }
        }
    }
}



