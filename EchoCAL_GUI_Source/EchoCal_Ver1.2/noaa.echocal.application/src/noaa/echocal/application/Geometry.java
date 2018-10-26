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
 * EchoCAL Software
 * 
 * Description:
 * This software is designed to communicate with the EchoCAL Downrigger Control Assemblies.
 * The EchoCAL software GUI gives the user access to the control of the downriggers.
 * Purpose:
 * To increase the overall efficiency of calibration sessions.
 */

/*
 * EchoCAL Developers:
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

//Echosounder package.
package noaa.echocal.application;
//Imports exception handler for dealing with nonexistent ports.
import gnu.io.NoSuchPortException;
//Imports exception handler for dealing with ports in use.
import gnu.io.PortInUseException;
//Imports exception handler for dealing with unsupported comm port actions.
import gnu.io.UnsupportedCommOperationException;
import java.awt.Component;
//Imports exception handler for dealing with IO inconsistencies.
import java.io.IOException;
import java.sql.Timestamp;
//Imports arraylist library.
import java.util.ArrayList;
import java.util.Arrays;
//Imports collection library.
import java.util.Collections;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;

/*
 * Description:
 * This class holds all of the functions related to the computations of the geometrically relevent quantities.
 * Status:
 * Code should be cleaned up. Functions are stable though.
 */
public class Geometry
{
   /*
    * Description:
    * Cartesian sphere position in real time.
    */
    
    //The current position of the calibration sphere in euclidian coordinates.
    public static float[] euclidianSpherePosition={0,0,0};
    //Final euclidian sphere position. (Only used for calculating "euclidianSpherePositionDisplacement")
    public static float[] finalEuclidianSpherePosition={0,0,0};
    //The change in calibration sphere in euclidian coordinates.
    public static float[] euclidianSpherePositionDisplacement={0,0,0};
   
   /*
    * Description:
    * Downrigger sphere position in real time.
    */
    
    //The current position of the calibration sphere in downrigger coordinates.
    //The format for this variable is as follows:
    // {PortFwd Count, PortMid Count, PortAft Count, StbdFwd Count, StbdMid Count, StbdAft Count}
    public static float[] downriggerSpherePosition={0,0,0,0,0,0};
    //The change in calibration sphere coordinates in downrigger coordinates.
    //The format for this variable is as follows:
    // {PortFwd Count, PortMid Count, PortAft Count, StbdFwd Count, StbdMid Count, StbdAft Count}
    public static float[] downriggerSpherePositionDisplacement = {0,0,0,0,0,0};

   /*
    * Description:
    * Standardized units for downrigger geometry.
    */
    
    //Most basic physical unit of length defined by the echocal software.  This is
    //the average length of a downrigger reel count.  
    //It is approximatly equal to 0.18518 meters.  This will not change the conversion
    //used in the interface file is deeply related and subject to change.
    public static float downriggerCountUnit=1;
    //This is the component in one direction out of two of one count unit.
    //This is used for diagonal displacements of the calibration sphere.
    //Computed using pythagorean theorem.
    public static float partialDownriggerCountUnit=(float) Math.sqrt(1/2f);
    
   /*
    * Description:
    * Progress bar variables for the automated navigation session.
    */
    
    //Numerator is used to calcultate progress state bar for automated
    //navigation session.
    public static float numerator = 0;
    //Denominator is used to calculate progress bar state for automated
    //navigation session.
    public static float denominator = 0;
    //Percentage is used to represent progress bar state for automated
    //navigation session.
    public static float percentage = 0;

   /*
    * Description:
    * Downrigger coordinate index identifiers.
    */
    
    //Port forward downrigger coordinate identifier.  
    public static int portForward=0;
    //Port middle downrigger coordinate identifier. 
    public static int portMiddle=1;
    //Port aft downrigger coordinate identifier. 
    public static int portAft=2;
    //Starboard forward downrigger coordinate identifier. 
    public static int starboardForward=3;
    //Starboard middle downrigger coordinate identifier. 
    public static int starboardMiddle=4;
    //Starboard aft downrigger coordinate identifier. 
    public static int starboardAft=5;
    
   /*
    * Description:
    * Cartesian coordinate index identifiers. These are used to identifiy indices
    * of a coordinate array.
    */
    
    //X coordinate identifier:
    //   Axis spans port to starboard. The "0" point is the vessels alongship centerline.
    public static int X=0;
    //Y coordinate identifier:
    //   Axis spans alongship.  The "0" mark depends on the configuration but tends
    //to be the port or starboard middle or the transducer.
    public static int Y=1;
    //Z coordinate identifier:
    //   Axis spans ocean floor to sky. The "0" point is the ocean surface.
    public static int Z=2;
    
   /*
    * Description:
    * Serial communication string constants. These were made for software readability.
    */
    
    //This is the "in" string.
    static String in = "in";
    //This is the "out" string.
    static String out = "out";
    //This is the "fine" string.
    static String fine = "fine";
    
   /*
    * Description:
    * Coordinate type string identifiers.
    */
    
    //Euclidian coordinate system identifier.
    public static String cartesian = "cartesian";
    //Downrigger coordinate system identifier.
    public static String downrigger = "downrigger";
   
   /*
    * Description:
    * Downrigger positions.  These values are all subject to change yet are initialized to zero.
    */
    
    //Position of port forward downrigger.
    public static float[] portForwardDownriggerPosition = {0,0,0};
    //Position of port middle downrigger.
    public static float[] portMiddleDownriggerPosition = {0,0,0};
    //Position of port aft downrigger.
    public static float[] portAftDownriggerPosition = {0,0,0};
    //Position of starboard forward downrigger.
    public static float[] starboardForwardDownriggerPosition = {0,0,0};
    //Position of starboard middle downrigger.
    public static float[] starboardMiddleDownriggerPosition = {0,0,0};
    //Position of starboard aft downrigger.
    public static float[] starboardAftDownriggerPosition = {0,0,0};
    //Position of target sphere position.
    public static float[] echosounderBeamTargetPosition = {0,0,0};
    //Position of transducer.
    public static float[] transducerPosition = {0,0,0};
    
   /*
    * Description:
    * Assigns a particular vessel configuration type; the specific amount and
    * arrangment of downriggers.
    */
    public static String configurationCode;
    
   /*
    * Description:
    * Vessel configuration xml storage variables.
    */
    public static float pfsf, sfsm, smpm, pmpf, sfsa, sapa, papf;
    public static float pmsm, smsa, papm, pfsm, smpa, pmsf, sapm;
    public static float pfho, pmho, paho, sfho, smho, saho, vw;
    public static float pfad, pmad, sfad, smad, td, esd, dfcl;
    
   /*
    * Description:
    * Displaces the calibration sphere one count unit in the +X direction. 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void right() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        if ("paused".equals(AutoCalibration.navigationState)){
            //If the navigatioState = paused, then the tension sensor sensed a problem.
            //Warn the operator to fix the error..
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "The tension sensor routine sensed a problem.\n"
                                            + "Make sure downrigger lines are not overly tight \n"
                                            + "before proceeding..");
            AutoCalibration.navigationState = "stopped";
        } else{
            //The cartesian position which stands two downrigger units right relative
            //to the current position of the calibration sphere.
            float[] intendedDestination = {euclidianSpherePosition[X]+2*downriggerCountUnit,euclidianSpherePosition[Y],euclidianSpherePosition[Z]};
            //Navigates to intendedNavigationPosition.
            new Thread(new SingleNavigation(intendedDestination)).start();
            NavigationControls.runCalibrationButton.setEnabled(true);
        }
    }

   /*
    * Description:
    * Displaces the calibration sphere one count unit in the -X direction. 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void left() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        if ("paused".equals(AutoCalibration.navigationState)){
            //If the navigatioState = paused, then the tension sensor sensed a problem.
            //Warn the operator to fix the error..
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "The tension sensor routine sensed a problem.\n"
                                            + "Make sure downrigger lines are not overly tight \n"
                                            + "before proceeding..");
            AutoCalibration.navigationState = "stopped";
        } else{
            //The cartesian position which stands two downrigger units left relative
            //to the current position of the calibration sphere.
            float[] intendedDestination = {euclidianSpherePosition[X]-2*downriggerCountUnit,euclidianSpherePosition[Y],euclidianSpherePosition[Z]};
            //Navigates to intendedNavigationPosition
            new Thread(new SingleNavigation(intendedDestination)).start();
            NavigationControls.runCalibrationButton.setEnabled(true);
        }
    }

   /*
    * Description:
    * Displaces the calibration sphere one count unit in the +Y direction. 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void forward() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        if ("paused".equals(AutoCalibration.navigationState)){
            //If the navigatioState = paused, then the tension sensor sensed a problem.
            //Warn the operator to fix the error..
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "The tension sensor routine sensed a problem.\n"
                                            + "Make sure downrigger lines are not overly tight \n"
                                            + "before proceeding..");
            AutoCalibration.navigationState = "stopped";
        } else{
            //The cartesian position which stands two downrigger units forward relative
            //to the current position of the calibration sphere.
            float[] intendedDestination = {euclidianSpherePosition[X],euclidianSpherePosition[Y]+2*downriggerCountUnit,euclidianSpherePosition[Z]};
            //Navigates to intendedNavigationPosition.
            new Thread(new SingleNavigation(intendedDestination)).start();
            NavigationControls.runCalibrationButton.setEnabled(true);
        }
    }

   /*
    * Description:
    * Displaces the calibration sphere one count unit in the -Y direction. 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void backward() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    { 
        if ("paused".equals(AutoCalibration.navigationState)){
            //If the navigatioState = paused, then the tension sensor sensed a problem.
            //Warn the operator to fix the error..
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "The tension sensor routine sensed a problem.\n"
                                            + "Make sure downrigger lines are not overly tight \n"
                                            + "before proceeding..");
            AutoCalibration.navigationState = "stopped";
        } else{
            //The cartesian position which stands two downrigger units backward
            //relative to the current position of the calibration sphere.
            float[] intendedDestination = {euclidianSpherePosition[X],euclidianSpherePosition[Y]-2*downriggerCountUnit,euclidianSpherePosition[Z]};
            //Navigates to intendedNavigationPosition.
            new Thread(new SingleNavigation(intendedDestination)).start();
            NavigationControls.runCalibrationButton.setEnabled(true);
        }
    }

   /*
    * Description:
    * Displaces the calibration sphere one count unit in the +X/+Y direction. 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void forwardRight() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        if ("paused".equals(AutoCalibration.navigationState)){
            //If the navigatioState = paused, then the tension sensor sensed a problem.
            //Warn the operator to fix the error..
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "The tension sensor routine sensed a problem.\n"
                                            + "Make sure downrigger lines are not overly tight \n"
                                            + "before proceeding..");
            AutoCalibration.navigationState = "stopped";
        } else{
            //The cartesian position which stands two downrigger units forward right
            //relative to the current position of the calibration sphere.
            float[] intendedDestination = {euclidianSpherePosition[X]+2*partialDownriggerCountUnit,
                                       euclidianSpherePosition[Y]+2*partialDownriggerCountUnit,
                                       euclidianSpherePosition[Z]};
            //Navigates to intendedNavigationPosition.
            new Thread(new SingleNavigation(intendedDestination)).start();
            NavigationControls.runCalibrationButton.setEnabled(true);
        }
    }

   /*
    * Description:
    * Displaces the calibration sphere one count unit in the -X/+Y direction. 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void forwardLeft() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        if ("paused".equals(AutoCalibration.navigationState)){
            //If the navigatioState = paused, then the tension sensor sensed a problem.
            //Warn the operator to fix the error..
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "The tension sensor routine sensed a problem.\n"
                                            + "Make sure downrigger lines are not overly tight \n"
                                            + "before proceeding..");
            AutoCalibration.navigationState = "stopped";
        } else{
            //The cartesian position which stands two downrigger units forward left
            //relative to the current position of the calibration sphere.
            float[] intendedDestination = {euclidianSpherePosition[X]-2*partialDownriggerCountUnit,
                                       euclidianSpherePosition[Y]+2*partialDownriggerCountUnit,
                                       euclidianSpherePosition[Z]};
            //Navigates to intendedNavigationPosition.
            new Thread(new SingleNavigation(intendedDestination)).start();
            NavigationControls.runCalibrationButton.setEnabled(true);
        }
    }

   /*
    * Description:
    * Displaces the calibration sphere one count unit in the +X/-Y direction. 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void backwardRight() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        if ("paused".equals(AutoCalibration.navigationState)){
            //If the navigatioState = paused, then the tension sensor sensed a problem.
            //Warn the operator to fix the error..
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "The tension sensor routine sensed a problem.\n"
                                            + "Make sure downrigger lines are not overly tight \n"
                                            + "before proceeding..");
            AutoCalibration.navigationState = "stopped";
        } else{
            //The cartesian position which stands two downrigger units backward right
            //relative to the current position of the calibration sphere.
            float[] intendedDestination = {euclidianSpherePosition[X]+2*partialDownriggerCountUnit,
                                       euclidianSpherePosition[Y]-2*partialDownriggerCountUnit,
                                       euclidianSpherePosition[Z]};
            //Navigates to intendedNavigationPosition.
            new Thread(new SingleNavigation(intendedDestination)).start();
            NavigationControls.runCalibrationButton.setEnabled(true);
        }
    }

   /*
    * Description:
    * Displaces the calibration sphere one count unit in the -X/-Y direction. 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void backwardLeft() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        if ("paused".equals(AutoCalibration.navigationState)){
            //If the navigatioState = paused, then the tension sensor sensed a problem.
            //Warn the operator to fix the error..
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "The tension sensor routine sensed a problem.\n"
                                            + "Make sure downrigger lines are not overly tight \n"
                                            + "before proceeding..");
            AutoCalibration.navigationState = "stopped";
        } else{
            //The cartesian position which stands two downrigger units backward left
            //relative to the current position of the calibration sphere.
            float[] intendedDestination = {euclidianSpherePosition[X]-2*partialDownriggerCountUnit,
                                       euclidianSpherePosition[Y]-2*partialDownriggerCountUnit,
                                       euclidianSpherePosition[Z]};
            //Navigates to intendedNavigationPosition.
            new Thread(new SingleNavigation(intendedDestination)).start();
            NavigationControls.runCalibrationButton.setEnabled(true);
        }
    }

   /*
    * Description:
    * Displaces the calibration sphere in the +Z direction by reeling in every
    * downrigger once.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void rise() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {   
        //Check the configuration code because the rise() command will be
        //different for each.
        switch (configurationCode)
        {
            //If it is a port forward - starboard middle - port aft configuration.
            case "pfsmpa":
            {
                reelInPortForward();
                //Thread.sleep(300);
                reelInStarboardMiddle();
                //Thread.sleep(300);
                reelInPortAft();
                //Thread.sleep(300);
                //Breaks out of case statement.  
                break;
            }
            //If it is a port middle - starboard forward - starboard aft configuration.
            case "pmsfsa":
            {
                reelInPortMiddle();
                //Thread.sleep(300);
                reelInStarboardForward();
                //Thread.sleep(300);
                reelInStarboardAft();
                //Thread.sleep(300);
                //Breaks out of case statement.  
                break;
            }
            //If it is a port forward - starboard forward - starboard aft - port aft configuration.
            case "pfsfsapa":
            {
                reelInPortForward();
                //Thread.sleep(300);
                reelInStarboardForward();
                //Thread.sleep(300);
                reelInStarboardAft();
                //Thread.sleep(300);
                reelInPortAft();
                //Thread.sleep(300);
                //Breaks out of case statement.   
                break;
            }
            //If it is a port forward - starboard forward - starboard middle - port middle configuration.
            case "pfsfsmpm":
            {
                reelInPortForward();
                //Thread.sleep(300);
                reelInStarboardForward();
                //Thread.sleep(300);
                reelInStarboardMiddle();
                //Thread.sleep(300);
                reelInPortMiddle();
                //Thread.sleep(300);
                //Breaks out of case statement.   
                break;
            }
            //If it is a port middle - starboard middle - starboard aft - port aft configuration.
            case "pmsmsapa":
            {
                reelInPortMiddle();
                //Thread.sleep(300);
                reelInStarboardMiddle();
                //Thread.sleep(300);
                reelInStarboardAft();
                //Thread.sleep(300);
                reelInPortAft();
                //Thread.sleep(300);
                //Breaks out of case statement.  
                break;
            }
            //This state should never be reached but it is here just so something
            //happens.
            default:
            {
                //Breaks out of case statement.  
                break;
            }
        }
        ButtonHandler.buttonWasPressed = false;
    }

   /*
    * Description:
    * Displaces the calibration sphere in the -Z direction by reeling out every
    * tethered downrigger once.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void sink() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Check the configuration code because the sink() command will be
        //different for each.
        switch (configurationCode)
        {
            //In the case of a port forward - starboard middle - port aft configuration.
            case "pfsmpa":
            {
                reelOutPortForward();
                //Thread.sleep(300);
                reelOutStarboardMiddle();
                //Thread.sleep(300);
                reelOutPortAft();
                //Thread.sleep(300);
                //Breaks out of case statement.  
                break;
            }
            //In the case of a port middle - starboard forward - starboard aft configuration.
            case "pmsfsa":
            {    
                reelOutPortMiddle();
                //Thread.sleep(300);
                reelOutStarboardForward();
                //Thread.sleep(300);
                reelOutStarboardAft();
                //Thread.sleep(300);
                //Breaks out of case statement.  
                break;
            }
            //In the case of a port forward - starboard forward - starboard aft - port aft configuration.
            case "pfsfsapa":
            {
                reelOutPortForward();
                //Thread.sleep(300);
                reelOutStarboardForward();
                //Thread.sleep(300);
                reelOutStarboardAft();
                //Thread.sleep(300);
                reelOutPortAft();
                //Thread.sleep(300);
                //Breaks out of case statement.  
                break;
            }
            //In the case of a port forward - starboard forward - starboard middle - port middle configuration.
            case "pfsfsmpm":
            {
                reelOutPortForward();
                //Thread.sleep(300);
                reelOutStarboardForward();
                //Thread.sleep(300);
                reelOutStarboardMiddle();
                //Thread.sleep(300);
                reelOutPortMiddle();
                //Thread.sleep(300);
                //Breaks out of case statement.  
                break;
            }
            //In the case of a port middle - starboard middle - starboard aft - port aft configuration.
            case "pmsmsapa":
            {
                reelOutPortMiddle();
                //Thread.sleep(300);
                reelOutStarboardMiddle();
                //Thread.sleep(300);
                reelOutStarboardAft();
                //Thread.sleep(300);
                reelOutPortAft();
                //Thread.sleep(300);
                //Breaks out of case statement.  
                break;
            }
            //In the case of some error.
            default:
            {
                //Breaks out of case statement.  
                break;
            }
        }
        ButtonHandler.buttonWasPressed = false;
    }

   /*
    * Description:
    * Reels in the starboard forward downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelInStarboardForward()  throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel in signal to starboard forward downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.starboardForwardSerialNumber, in, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled in starboard forward downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }

   /*
    * Description:
    * Reels out the starboard forward downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelOutStarboardForward()  throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel out signal to starboard forward downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.starboardForwardSerialNumber, out, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled out starboard forward downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }
    
   /*
    * Description:
    * Reels in the starboard middle downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelInStarboardMiddle()  throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel in signal to starboard middle downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.starboardMiddleSerialNumber, in, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled in starboard middle downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }

    /*
    * Description:
    * Reels out the starboard middle downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelOutStarboardMiddle()  throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {   
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel out signal to starboard middle downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.starboardMiddleSerialNumber, out, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled out starboard middle downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }
    
   /*
    * Description:
    * Reels in the starboard aft downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelInStarboardAft() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel in signal to starboard aft downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.starboardAftSerialNumber, in, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled in starboard aft downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }

   /*
    * Description:
    * Reels out the starboard aft downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelOutStarboardAft() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel out signal to starboard aft downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.starboardAftSerialNumber, out, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled out starboard aft downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }

   /*
    * Description:
    * Reels in the port forward downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelInPortForward() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {   
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel in signal to port forward downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.portForwardSerialNumber, in, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled in port forward downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }

   /*
    * Description:
    * Reels out the port forward downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelOutPortForward() throws PortInUseException, UnsupportedCommOperationException, UnsupportedCommOperationException, IOException, NoSuchPortException, InterruptedException
    {
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel out signal to port forward downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.portForwardSerialNumber, out, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled out port forward downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }
    
   /*
    * Description:
    * Reels in the port middle downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelInPortMiddle() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel in signal to port middle downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.portMiddleSerialNumber, in, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled in port middle downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }

   /*
    * Description:
    * Reels out the port middle downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelOutPortMiddle() throws PortInUseException, UnsupportedCommOperationException, UnsupportedCommOperationException, IOException, NoSuchPortException, InterruptedException
    {
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel out signal to port middle downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.portMiddleSerialNumber, out, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled out port middle downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }
    
   /*
    * Description:
    * Reels in the port aft downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelInPortAft() throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel in signal to port aft downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.portAftSerialNumber, in, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled in port aft downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }

   /*
    * Description:
    * Reels out the port aft downrigger one count unit.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelOutPortAft() throws PortInUseException, UnsupportedCommOperationException, UnsupportedCommOperationException, IOException, NoSuchPortException, InterruptedException
    {
        //Checks for changes in the downrigger counts to see if there is any tension on the line.
        TensionSensor tensionSensor = new TensionSensor();

        TensionSensor.markDownriggerCoordinatesForTenionSensor();
        //Sends reel out signal to port aft downrigger box.
        Communication.reelDownrigger(Communication.selectedCommPortTag,Communication.portAftSerialNumber, out, fine);
        //Calculates the coordinates.
        CoordinateTransformThread.calibrate();
        //(new Thread(new CoordinateTransformThread())).start();
        //(new Thread(new TensionSensor())).start();
        tensionSensor.checkTension();
        //Sends message to system log informing the user of successfull reel.
        //(fix placement)
        Terminal.systemLogMessage("Reeled out port aft downrigger.", CoordinateDisplayTopComponent.positionUILogger);
    }
    
   /*
    * Description:
    * Takes in euclidian position vector as input and returns the count position
    * vector.
    * Example:
    * 
    * Status:
    * Function is stable.
    */
    public static float[] convertFromEuclidianToDownriggerCoordinates(float[] euclidianPosition)
    {
        //Initializes the local variable downrigger position which would be used
        //to make calculations.
        //Do not confuse this with downriggerSpherePosition.
        float[] downriggerPosition={0,0,0,0,0,0};
        //This function relies on the specific configuration code.
        switch (configurationCode)
        {
            //In the case of a port forward - starboard middle - port aft configuration.
            case "pfsmpa":
            {
                //Calculates port forward downrigger position.
                downriggerPosition[portForward]=(float) Math.sqrt
                (Math.pow(portForwardDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(portForwardDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(portForwardDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates starboard middle downrigger position.
                downriggerPosition[starboardMiddle]=(float) Math.sqrt
                (Math.pow(starboardMiddleDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(starboardMiddleDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(starboardMiddleDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates port aft downrigger position.
                downriggerPosition[portAft]=(float) Math.sqrt
                (Math.pow(portAftDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(portAftDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(portAftDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Breaks out of case statement.
                break;
            }
            //In the case of a port middle - starboard forward - starboard aft configuration.
            case "pmsfsa":
            {   
                //Calculates port middle downrigger position.
                downriggerPosition[portMiddle]=(float) Math.sqrt
                (Math.pow(portMiddleDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(portMiddleDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(portMiddleDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates starboard forward downrigger position.
                downriggerPosition[starboardForward]=(float) Math.sqrt
                (Math.pow(starboardForwardDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(starboardForwardDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(starboardForwardDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates starboard aft downrigger position.
                downriggerPosition[starboardAft]=(float) Math.sqrt
                (Math.pow(starboardAftDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(starboardAftDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(starboardAftDownriggerPosition[Z]-euclidianPosition[Z],2));        
                //Breaks out of case statement.
                break;
            }
            //In the case of a port forward - starboard forward - starboard aft - port aft configuration.
            case "pfsfsapa":
            {
                //Calculates port forward downrigger position.
                downriggerPosition[portForward]=(float) Math.sqrt
                (Math.pow(portForwardDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(portForwardDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(portForwardDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates starboard forward downrigger position.
                downriggerPosition[starboardForward]=(float) Math.sqrt
                (Math.pow(starboardForwardDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(starboardForwardDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(starboardForwardDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates starboard aft downrigger position.
                downriggerPosition[starboardAft]=(float) Math.sqrt
                (Math.pow(starboardAftDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(starboardAftDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(starboardAftDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates port aft downrigger position.
                downriggerPosition[portAft]=(float) Math.sqrt
                (Math.pow(portAftDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(portAftDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(portAftDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Breaks out of case statement.
                break;
            }
            //In the case of a port forward - starboard forward - starboard middle - port middle configuration.
            case "pfsfsmpm":
            {
                //Calculates port forward downrigger position.
                downriggerPosition[portForward]=(float) Math.sqrt
                (Math.pow(portForwardDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(portForwardDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(portForwardDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates starboard forward downrigger position.
                downriggerPosition[starboardForward]=(float) Math.sqrt
                (Math.pow(starboardForwardDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(starboardForwardDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(starboardForwardDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates starboard middle downrigger position.
                downriggerPosition[starboardMiddle]=(float) Math.sqrt
                (Math.pow(starboardMiddleDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(starboardMiddleDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(starboardMiddleDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates port middle downrigger position.
                downriggerPosition[portMiddle]=(float) Math.sqrt
                (Math.pow(portMiddleDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(portMiddleDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(portMiddleDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Breaks out of case statement.
                break;
            }    
            //In the case of a port middle - starboard middle - starboard aft - port aft configuration.
            case "pmsmsapa":
            {
                //Calculates port middle downrigger position.
                downriggerPosition[portMiddle]=(float) Math.sqrt
                (Math.pow(portMiddleDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(portMiddleDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(portMiddleDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates starboard middle downrigger position.
                downriggerPosition[starboardMiddle]=(float) Math.sqrt
                (Math.pow(starboardMiddleDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(starboardMiddleDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(starboardMiddleDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates starboard aft downrigger position.
                downriggerPosition[starboardAft]=(float) Math.sqrt
                (Math.pow(starboardAftDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(starboardAftDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(starboardAftDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Calculates port aft downrigger position.
                downriggerPosition[portAft]=(float) Math.sqrt
                (Math.pow(portAftDownriggerPosition[X]-euclidianPosition[X],2)
                +Math.pow(portAftDownriggerPosition[Y]-euclidianPosition[Y],2)
                +Math.pow(portAftDownriggerPosition[Z]-euclidianPosition[Z],2));
                //Breaks out of case statement.
                break;
            }  
            //In the case that there is no configuration.
            default:
            {
                //Pass.
                //Breaks out of case statement.  
                break;
            }
        }     
        //Return the calculated downrigger position.
        return downriggerPosition;
    }
    
   /*
    * Description:
    * Returns the separation magnitude between the two position vectors.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static double magnitude(float[] positionVectorAlpha, float[] positionVectorBeta)
    {
        //Calculate magnitude between positionVectorAlpha and positionVectorBeta.
        //This is done using the Pythagorean theorem.
        double magnitude=Math.pow(Math.pow(positionVectorAlpha[X]-positionVectorBeta[X],2)
                                + Math.pow(positionVectorAlpha[Y]-positionVectorBeta[Y],2)
                                + Math.pow(positionVectorAlpha[Z]-positionVectorBeta[Z],2),1/2f);
        //Returns magnitude.
        return magnitude;
    }
    
    /*
    * Description:
    * Navigates the calibration sphere to the location represented euclidian
    * position vector input by the user.
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static synchronized void euclidianInputNavigation(float[] euclidianTargetPosition) throws PortInUseException, IOException, InterruptedException, UnsupportedCommOperationException, NoSuchPortException
    {
        //Converts euclidianTargetPosition to downrigger coordinates.
        float[] downriggerTargetPosition={0,0,0,0,0,0};
        //Reads the configuration code.
        if (configurationCode == null)
        {
            configurationCode = "null";
        }
        switch (configurationCode)
        {
            //In the case of a port forward - starboard middle - port aft configuration.
            case "pfsmpa":
            {
                //Calculates port forward downrigger position.
                downriggerTargetPosition[portForward]=(float) Math.sqrt
                (Math.pow(portForwardDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(portForwardDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(portForwardDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates starboard middle downrigger position.
                downriggerTargetPosition[starboardMiddle]=(float) Math.sqrt
                (Math.pow(starboardMiddleDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(starboardMiddleDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(starboardMiddleDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates port aft downrigger position.
                downriggerTargetPosition[portAft]=(float) Math.sqrt
                (Math.pow(portAftDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(portAftDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(portAftDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Breaks out of case statement.
                break;
            }
            //In the case of a port middle - starboard forward - starboard aft configuration.
            case "pmsfsa":
            {                   
                //Calculates port middle downrigger position.
                downriggerTargetPosition[portMiddle]=(float) Math.sqrt
                (Math.pow(portMiddleDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(portMiddleDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(portMiddleDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates starboard forward downrigger position.
                downriggerTargetPosition[starboardForward]=(float) Math.sqrt
                (Math.pow(starboardForwardDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(starboardForwardDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(starboardForwardDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates starboard aft downrigger position.
                downriggerTargetPosition[starboardAft]=(float) Math.sqrt
                (Math.pow(starboardAftDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(starboardAftDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(starboardAftDownriggerPosition[Z]-euclidianTargetPosition[Z],2)); 
                //Breaks out of case statement.
                break;
            }
            //In the case of a port forward - starboard forward - starboard aft - port aft configuration.
            case "pfsfsapa":
            {
                //Calculates port forward downrigger position.
                downriggerTargetPosition[portForward]=(float) Math.sqrt
                (Math.pow(portForwardDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(portForwardDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(portForwardDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates starboard forward downrigger position.
                downriggerTargetPosition[starboardForward]=(float) Math.sqrt
                (Math.pow(starboardForwardDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(starboardForwardDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(starboardForwardDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates starboard aft downrigger position.
                downriggerTargetPosition[starboardAft]=(float) Math.sqrt
                (Math.pow(starboardAftDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(starboardAftDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(starboardAftDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates port aft downrigger position.
                downriggerTargetPosition[portAft]=(float) Math.sqrt
                (Math.pow(portAftDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(portAftDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(portAftDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Breaks out of case statement.
                break;
            }   
            //In the case of a port forward - starboard forward - starboard middle - port middle configuration.
            case "pfsfsmpm":
            {
                //Calculates port forward downrigger position.
                downriggerTargetPosition[portForward]=(float) Math.sqrt
                (Math.pow(portForwardDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(portForwardDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(portForwardDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates starboard forward downrigger position.
                downriggerTargetPosition[starboardForward]=(float) Math.sqrt
                (Math.pow(starboardForwardDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(starboardForwardDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(starboardForwardDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates starboard middle downrigger position.
                downriggerTargetPosition[starboardMiddle]=(float) Math.sqrt
                (Math.pow(starboardMiddleDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(starboardMiddleDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(starboardMiddleDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates port middle downrigger position.
                downriggerTargetPosition[portMiddle]=(float) Math.sqrt
                (Math.pow(portMiddleDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(portMiddleDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(portMiddleDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Breaks out of case statement.
                break;
            }
            //In the case of a port middle - starboard middle - starboard aft - port aft configuration.
            case "pmsmsapa":
            {
                //Calculates port middle downrigger position.
                downriggerTargetPosition[portMiddle]=(float) Math.sqrt
                (Math.pow(portMiddleDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(portMiddleDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(portMiddleDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates starboard middle downrigger position.
                downriggerTargetPosition[starboardMiddle]=(float) Math.sqrt
                (Math.pow(starboardMiddleDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(starboardMiddleDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(starboardMiddleDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates starboard aft downrigger position.
                downriggerTargetPosition[starboardAft]=(float) Math.sqrt
                (Math.pow(starboardAftDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(starboardAftDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(starboardAftDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Calculates port aft downrigger position.
                downriggerTargetPosition[portAft]=(float) Math.sqrt
                (Math.pow(portAftDownriggerPosition[X]-euclidianTargetPosition[X],2)
                +Math.pow(portAftDownriggerPosition[Y]-euclidianTargetPosition[Y],2)
                +Math.pow(portAftDownriggerPosition[Z]-euclidianTargetPosition[Z],2));
                //Breaks out of case statement.
                break;
            }
            case "null":
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "You cannot navigate the sphere if a configuration isn't set.");
                configurationCode = null;
                //Breaks out of case statement.  
                break;
            }
            //If there is no valid configuration code.
            default:
            {
                //Pass.
                //Breaks out of case statement.  
                break;
            }
        }
        if (configurationCode == null)
        {
            //Pass.
        }
        else
        {
            //Converts the float calculated downriggerTargetPosition into a rounded integer
            //version so that it may be handled properly by the navigation do while loop below.
            downriggerTargetPosition[portForward]      = Math.round(downriggerTargetPosition[portForward]);
            downriggerTargetPosition[portMiddle]       = Math.round(downriggerTargetPosition[portMiddle]);
            downriggerTargetPosition[portAft]          = Math.round(downriggerTargetPosition[portAft]);
            downriggerTargetPosition[starboardForward] = Math.round(downriggerTargetPosition[starboardForward]);
            downriggerTargetPosition[starboardMiddle]  = Math.round(downriggerTargetPosition[starboardMiddle]);
            downriggerTargetPosition[starboardAft]     = Math.round(downriggerTargetPosition[starboardAft]);        
            //Defines numerical string for euclidian x coordinate.
            String x = Integer.toString(Math.round(euclidianTargetPosition[X]));
            //Defines numerical string for euclidian y coordinate.
            String y = Integer.toString(Math.round(euclidianTargetPosition[Y]));
            //Defines numerical string for euclidian z coordinate.
            String z = Integer.toString(Math.round(euclidianTargetPosition[Z]));
            //Print out system log message on interface informing the user of successful
            //intension to navigate to a given position.
            Terminal.systemLogMessage("Attempting to navigate the calibration sphere to [ "+x+" , "+y+" , "+z+" ].",
                                        CoordinateDisplayTopComponent.positionUILogger);
            //This lock is used so that the downriggers know when to stop navigating.
            //It is implemented below.
            boolean navigationLock = true;
            //Do at least once.
            OUTER:
            do 
            {
                //if (!((int)Math.round(downriggerSpherePosition[portForward])==(int)Math.round(downriggerTargetPosition[portForward])))
                if (!(downriggerSpherePosition[portForward]==downriggerTargetPosition[portForward]))
                {
                    //If the port forward downrigger coordinate is greater than
                    //the target port forward downrigger coordinate, then reel it in.
                    if (downriggerSpherePosition[portForward]>downriggerTargetPosition[portForward])
                    {
                        reelInPortForward();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //If the port forward downrigger coordinate is less than
                    //the target port forward downrigger coordinate, then reel it out.
                    else if (downriggerSpherePosition[portForward]<downriggerTargetPosition[portForward])
                    {
                        reelOutPortForward();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //Unreachable state.
                    else
                    {
                        //Pass.
                    }
                }
                //if (!((int)Math.round(downriggerSpherePosition[portMiddle])==(int)Math.round(downriggerTargetPosition[portMiddle])))
                if (!(downriggerSpherePosition[portMiddle]==downriggerTargetPosition[portMiddle]))
                {
                    //If the port middle downrigger coordinate is greater than
                    //the target port middle downrigger coordinate, then reel it in.
                    if (downriggerSpherePosition[portMiddle]>downriggerTargetPosition[portMiddle])
                    {
                        reelInPortMiddle();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //If the port middle downrigger coordinate is less than
                    //the target port middle downrigger coordinate, then reel it out.
                    else if (downriggerSpherePosition[portMiddle]<downriggerTargetPosition[portMiddle])
                    {
                        reelOutPortMiddle();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //Unreachable state.
                    else
                    {
                        //Pass.
                    }
                }
                //if (!((int)Math.round(downriggerSpherePosition[portAft])==(int)Math.round(downriggerTargetPosition[portAft])))
                if (!(downriggerSpherePosition[portAft]==downriggerTargetPosition[portAft]))
                {
                    //If the port aft downrigger coordinate is greater than
                    //the target port aft downrigger coordinate, then reel it in.
                    if (downriggerSpherePosition[portAft]>downriggerTargetPosition[portAft])
                    {
                        reelInPortAft();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //If the port aft downrigger coordinate is less than
                    //the target port aft downrigger coordinate, then reel it out.
                    else if (downriggerSpherePosition[portAft]<downriggerTargetPosition[portAft])
                    {
                        reelOutPortAft();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //Unreachable state.
                    else
                    {
                        //Pass.
                    }
                }
                //if (!((int)Math.round(downriggerSpherePosition[starboardForward])==(int)Math.round(downriggerTargetPosition[starboardForward])))
                if (!(downriggerSpherePosition[starboardForward]==downriggerTargetPosition[starboardForward]))
                {
                    //If the starboard forward downrigger coordinate is greater than
                    //the target starboard forward downrigger coordinate, then reel it in.
                    if (downriggerSpherePosition[starboardForward]>downriggerTargetPosition[starboardForward])
                    {
                        reelInStarboardForward();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //If the starboard forward downrigger coordinate is less than
                    //the target starboard forward downrigger coordinate, then reel it out.
                    else if (downriggerSpherePosition[starboardForward]<downriggerTargetPosition[starboardForward])
                    {
                        reelOutStarboardForward();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //Unreachable state.
                    else
                    {
                        //Pass.
                    }
                }
                //if (!((int)Math.round(downriggerSpherePosition[starboardMiddle])==(int)Math.round(downriggerTargetPosition[starboardMiddle])))
                if (!(downriggerSpherePosition[starboardMiddle]==downriggerTargetPosition[starboardMiddle]))
                {
                    //If the starboard middle downrigger coordinate is greater than
                    //the target starboard middle downrigger coordinate, then reel it in.
                    if (downriggerSpherePosition[starboardMiddle]>downriggerTargetPosition[starboardMiddle])
                    {
                        reelInStarboardMiddle();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //If the starboard middle downrigger coordinate is less than
                    //the target starboard middle downrigger coordinate, then reel it out.
                    else if (downriggerSpherePosition[starboardMiddle]<downriggerTargetPosition[starboardMiddle])
                    {
                        reelOutStarboardMiddle();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //Unreachable state.
                    else
                    {
                        //Pass.
                    }
                }
                //if (!((int)Math.round(downriggerSpherePosition[starboardAft])==(int)Math.round(downriggerTargetPosition[starboardAft])))
                if (!(downriggerSpherePosition[starboardAft]==downriggerTargetPosition[starboardAft]))
                {
                    //If the starboard aft downrigger coordinate is greater than
                    //the target starboard aft downrigger coordinate, then reel it in.
                    if (downriggerSpherePosition[starboardAft]>downriggerTargetPosition[starboardAft])
                    {
                        reelInStarboardAft();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //If the starboard aft downrigger coordinate is less than
                    //the target starboard aft downrigger coordinate, then reel it out.
                    else if (downriggerSpherePosition[starboardAft]<downriggerTargetPosition[starboardAft])
                    {
                        reelOutStarboardAft();
                        //Thread.sleep(100);
                        //Thread.sleep(400);
                        if (SingleNavigation.stopSingleNavigationInstance == true)
                        {   
                            break;
                        }
                    }
                    //Unreachable state.
                    else
                    {
                        //Pass.
                    }
                }
                //The purpose of this is to have the power to stop the calibration,
                //or any navigation for that matter, since this override is embedded
                //deep in the navigation algorithm.  June 25 2015
                switch (AutoCalibration.navigationState) 
                {
                    //Stopping here really does not do anything because this function
                    //represents a single navigation.
                    case "stopped":
                    {
                        System.out.println("Entering Euclidean routine STOPPED state.");
                        NavigationControls.runCalibrationButton.setEnabled(true);
                        NavigationControls.pauseCalibrationButton.setEnabled(false);
                        NavigationControls.stopCalibrationButton.setEnabled(false);
                        navigationLock=false;
                        break;
                    }
                    case "paused":
                    {
                        System.out.println("Entering Euclidean routine PAUSED state.");
                        NavigationControls.runCalibrationButton.setEnabled(true);
                        NavigationControls.pauseCalibrationButton.setEnabled(false);
                        //navigationLock=false;
                        
                        while (AutoCalibration.navigationState.equals("paused"))
                        {
                            //Pass
                            Thread.sleep(200);
                        }
                        
                        break;
                    }
                    case "running":
                    {
                        NavigationControls.pauseCalibrationButton.setEnabled(true);
                        NavigationControls.runCalibrationButton.setEnabled(false);
                        ButtonHandler.disableNavigationControls();
                        NavigationControls.starTypeButton.setEnabled(false);
                        NavigationControls.spiralTypeButton.setEnabled(false);
                        NavigationControls.gridTypeButton.setEnabled(false);
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                
                NavigationControls.runCalibrationButton.setEnabled(false);
                Terminal.systemLogMessage("The calibration sphere just reached: "+Arrays.toString(downriggerSpherePosition),
                                            CoordinateDisplayTopComponent.positionUILogger);
                if (downriggerSpherePosition[portForward]==downriggerTargetPosition[portForward])
                {
                    //If the port middle downrigger satisfies the target position proceed accordingly.
                    if (downriggerSpherePosition[portMiddle]==downriggerTargetPosition[portMiddle])
                    {
                        //If the port aft downrigger satisfies the target position proceed accordingly.
                        if (downriggerSpherePosition[portAft]==downriggerTargetPosition[portAft])
                        {
                            //If the starboard forward downrigger satisfies the target position proceed accordingly.
                            if (downriggerSpherePosition[starboardForward]==downriggerTargetPosition[starboardForward])
                            {
                                //If the starboard middle downrigger satisfies the target position proceed accordingly.
                                if (downriggerSpherePosition[starboardMiddle]==downriggerTargetPosition[starboardMiddle])
                                {
                                    //If the starboard aft downrigger satisfies the target position proceed accordingly.
                                    if (downriggerSpherePosition[starboardAft]==downriggerTargetPosition[starboardAft])
                                    {
                                        //The navigation lock is turned off because all
                                        //of the downrigger counts have reached their target position,
                                        //hence completing the automated navigation.
                                        navigationLock=false;
                                    }    
                                }
                            }
                        }
                    }
                }

            } 
            while (navigationLock==true);
            //So that the user knows when the calibration sphere has stopped navigating.
            Terminal.systemLogMessage("Calibration sphere reached [ "+x+" , "+y+" , "+z+" ].",
                                        CoordinateDisplayTopComponent.positionUILogger);
            //Give the calibration sphere a short time to wait in its position.
            if (AutoCalibration.autoCalibrationModeType.equals("single"))
            {
                NavigationControls.updatedButton.setSelected(false);
                NavigationControls.updatedButton.setText("Activate");
                NavigationControls.runCalibrationButton.setEnabled(true);
                NavigationControls.pauseCalibrationButton.setEnabled(false);
                NavigationControls.stopCalibrationButton.setEnabled(false);
                ButtonHandler.enableNavigationControls();
                NavigationControls.starTypeButton.setEnabled(true);
                NavigationControls.spiralTypeButton.setEnabled(true);
                NavigationControls.gridTypeButton.setEnabled(true);
                AutoCalibration.autoCalibrationModeType="";
            }
            SingleNavigation.stopSingleNavigationInstance = false;
        }
    }
        
    public static void spiralTypeAutomatedNavigation(float fullBeamAngle)
            throws PortInUseException, InterruptedException, IOException, UnsupportedCommOperationException, NoSuchPortException
    {
        //ButtonHandler.disableNavigationControls();
        //Defines an arraylist which is used to store the different positions to
        //navigate to.
        ArrayList <double[]> downriggerTargetPositionList = new ArrayList();
        //Uses the distance between the transducer and the echosounder view along with
        //the beam angle input in order to define the radius of the echosounder view.
        //double sonarCrossSectionalRadius = (float) (Math.abs(echosounderBeamTargetPosition[Z]-transducerPosition[Z])*Math.tan((fullBeamAngle*Math.PI/360)%(2*Math.PI)))+3;
        double sonarCrossSectionalRadius = (Math.abs(echosounderBeamTargetPosition[Z]-transducerPosition[Z]))*Math.tan(Math.toRadians(fullBeamAngle/2))+3;
        //The 'theta' for the logarithmic spiral equation.
        //Spiral path is parametrized by the 'theta' parameter.
        double theta = 0;
        double growingRadius;
        //Constants 'spiralConstant1' & 'spiralConstant2' make reference to
        //constants 'a' and 'b' respectivly found at
        //URL: http://mathworld.wolfram.com/LogarithmicSpiral.html .
        double spiralConstant1 = 1;
        double spiralConstant2 = 0.005;
        //The X and Y coordinates for the spiral equation.
        double navPointX;
        double navPointY;
        double navPointZ;
        //This loop builds a list of spiral points until distance from the target
        //point exceeds the radius ('sonarCrossSectionalRadius').
        do
        {
            //navPointX = spiralConstant1*Math.cos(theta)*Math.exp(spiralConstant2*theta)+echosounderBeamTargetPosition[X];
            //navPointY = spiralConstant1*Math.sin(theta)*Math.exp(spiralConstant2*theta)+echosounderBeamTargetPosition[Y];
            navPointX = Math.round(spiralConstant1*Math.cos(theta)*Math.exp(spiralConstant2*theta)+echosounderBeamTargetPosition[X]);
            navPointY = Math.round(spiralConstant1*Math.sin(theta)*Math.exp(spiralConstant2*theta)+echosounderBeamTargetPosition[Y]);
            navPointZ = echosounderBeamTargetPosition[Z];
            //Defines next point for navigation list.
            double[] navigationPoint = {navPointX,navPointY,navPointZ};
            //Add this point to the list of navigation points.
            downriggerTargetPositionList.add(navigationPoint);
            //Radius of the growing spiral.
            growingRadius = Math.pow(Math.pow(navPointX-echosounderBeamTargetPosition[X], 2)
                            +Math.pow(navPointY-echosounderBeamTargetPosition[Y], 2),0.5);
            //
            //theta = theta + 2*Math.PI/(8+growingRadius);
            theta = theta + (2*Math.PI/(2+growingRadius));
            System.out.println("theta: "+theta);
            System.out.println("growingRadius: "+growingRadius);
            System.out.println("sonarCrossSectionalRadius: "+sonarCrossSectionalRadius);
            System.out.println(Arrays.toString(navigationPoint));
        }
        while(growingRadius < sonarCrossSectionalRadius);
        //The last point of navigation is the target point so that the sphere is not lost.
        double[] targetPosition = {echosounderBeamTargetPosition[X],echosounderBeamTargetPosition[Y],echosounderBeamTargetPosition[Z]};
        downriggerTargetPositionList.add(targetPosition);
        //The numerator is a variable when combined with the denominator can determine
        //the current progress of the automated navigation function.
        numerator = 0;
        //The denominator is known by the total size of the navigationPositionList.
        denominator = downriggerTargetPositionList.size();
        for (double[] targetCoordinates : downriggerTargetPositionList) 
        {
            //The progress bar must be updated every time it successfully navigates
            //to a navigation point.
            numerator=numerator+1;
            //The percentage of the progress bar must be updated.
            percentage = numerator/denominator;

            //Convert downriggerTargetPositionList to "floats" for use in SingleNavigation function.
            float[] navigationPoint = {(float) targetCoordinates[X],(float) targetCoordinates[Y],(float) targetCoordinates[Z]};
            //Starts a single navigation.
            if (!AutoCalibration.navigationState.equals("stopped"))
            {
                (new Thread(new SingleNavigation(navigationPoint))).start();
            }
            else
            {
                numerator=0;
                //percentage=0;
                updateProgressBar();
                break;
            }
            //Updates the progress bar.
            updateProgressBar();
            //Puts a small thread.sleep to settle the position of the calibration
            //sphere once it reaches its navigation point.
            Thread.sleep(100);
            //If the navigation state is set to "stopped" the the navigation ceases
            //along with the navigation points in the targetCoordinatesList.
        } 
        //Set the navigation state to "stopped" since the automated calibration
        //session is over.
        AutoCalibration.navigationState="stopped";
        NavigationControls.starTypeButton.setEnabled(true);
        NavigationControls.spiralTypeButton.setEnabled(true);
        NavigationControls.gridTypeButton.setEnabled(true);
        NavigationControls.updatedButton.setSelected(false);
        NavigationControls.updatedButton.setText("Activate");
        ButtonHandler.enableNavigationControls();
        NavigationControls.runCalibrationButton.setEnabled(true);
        NavigationControls.pauseCalibrationButton.setEnabled(false);
        NavigationControls.stopCalibrationButton.setEnabled(false);
    }
    
   /*
    * Description:
    * Navigates the calibration sphere in a star like pattern to ensure optimal
    * coverage, efficiency and algorithm simplicity.
    * Status:
    * Function is stable.
    */ 
    public static void starTypeAutomatedNavigation(float fullBeamAngle)
            throws PortInUseException, InterruptedException, IOException, UnsupportedCommOperationException, NoSuchPortException
    {
        //ButtonHandler.disableNavigationControls();
        //Defines an arraylist which is used to store the different positions
        //to navigate to.
        ArrayList <double[]> downriggerTargetPositionList = new ArrayList();
        //Uses the distance between the transducer and the echosounder view along
        //with the beam angle input in order to define the radius of the
        //echosounder view.
        //float sonarCrossSectionalRadius = (float) (Math.abs(echosounderBeamTargetPosition[Z]-transducerPosition[Z])
        //                                                    *Math.tan((fullTransducerBeamAngle*Math.PI/360)%(2*Math.PI)))+3;
        double sonarCrossSectionalRadius = (Math.abs(echosounderBeamTargetPosition[Z]-transducerPosition[Z]))*Math.tan(Math.toRadians(fullBeamAngle/2))+3;
        //Calculate the circumfrence of the echosounder beam at depth.
        double sonarBeamCircumfrence = (2*Math.PI)*sonarCrossSectionalRadius;
        /* ----- Remove commented out code after testing new version of Star Nav function.
        
        //Since the echosounder view may come in many different sizes, the incremental
        //angular deviation must change as well.  
        float angularDifferentialSize = (float) (Math.PI * 2/sonarBeamCircumfrence) * 16;
        //Since we use downrigger counts in order to serve as a basis for the downrigger
        //geometry, the circumference of the echosounder also serves as an identifier
        //of the amount of angular deviations in a single navigation session.
        int quantityOfAngularDifferentials = (int)Math.round(sonarBeamCircumfrence/angularDifferentialSize);
        ------------- */
        //New setup for Star navigation session.. JMG 25 Sep 2016..
        //numSlices -> The number of slices to divide up the Sonar Cross Section area..
        int numSlices = 24;
        //Determine the incremental angular deviations for a single navigation sequence.
        //double angleDiffs = (double) (Math.PI * 2 / sonarBeamCircumfrence) * 16;
        int angleDiffs = 360/numSlices;
      
        //Determine number of angular points to divide the cross section into..
        //int quantityOfAngleDiffs = (int) Math.round(sonarBeamCircumfrence/angleDiffs);
        int quantityOfAngleDiffs = numSlices/2;
        
        //Beginning with theta = 0, in the next FOR loop the angle will slowly work
        //its way around the circle on both sides of the echosounder centroid
        //when defining the navigation points.
        double thetaFromXAxis = 0;
        
        //Save the sphere's original location, so that we can go back to it.
        double[] origPosition = {echosounderBeamTargetPosition[X],
                                 echosounderBeamTargetPosition[Y],
                                 echosounderBeamTargetPosition[Z]};
        //This FOR loop builds the navigation points in a star like pattern.
        for (int angle=0;angle<quantityOfAngleDiffs;angle++)
        {
            //Converts from spherical coordinate changes to cartesian x coordinate
            //changes so that they can be defined on the star more easily.
            double xDeviation = Math.round(sonarCrossSectionalRadius*Math.cos(Math.toRadians(thetaFromXAxis)));
            //Converts from spherical to cartesian x coordinate changes so that
            //they can be defined on the star more easily..
            //double yDeviation = Math.round(sonarCrossSection*Math.sin((thetaFromXAxis*2*Math.PI/360)%(2*Math.PI)));
            double yDeviation = Math.round(sonarCrossSectionalRadius*Math.sin(Math.toRadians(thetaFromXAxis)));
            //Converts from spherical to cartesian x coordinate changes so that
            //they can be defined on the star more easily..
            //double oppositeXDeviation = Math.round(sonarCrossSection*Math.cos(((thetaFromXAxis+180)*2*Math.PI/360)%(2*Math.PI)));
            double oppositeXDeviation = Math.round(sonarCrossSectionalRadius*Math.cos(Math.toRadians(thetaFromXAxis+180)));
            //Converts from spherical to cartesian x coordinate changes so that
            //they can be defined on the star more easily..
            //double oppositeYDeviation = Math.round(sonarCrossSection*Math.sin(((thetaFromXAxis+180)*2*Math.PI/360)%(2*Math.PI)));
            double oppositeYDeviation = Math.round(sonarCrossSectionalRadius*Math.sin(Math.toRadians(thetaFromXAxis+180)));
            //Defines next point for navigation in this star type navigation session.
            double[] navigationPoint = {echosounderBeamTargetPosition[X]+xDeviation,
                                       echosounderBeamTargetPosition[Y]+yDeviation,
                                       echosounderBeamTargetPosition[Z]};
            //Add this point to the list of navigation points.
            downriggerTargetPositionList.add(navigationPoint);
            //Adds star node euclidian position.
            //downriggerTargetPositionList.add(origPosition);
            //Defines the navigation point opposite to the last on the star.
            double[] oppositeNavigationPoint = {echosounderBeamTargetPosition[X]+oppositeXDeviation,
                                               echosounderBeamTargetPosition[Y]+oppositeYDeviation,
                                               echosounderBeamTargetPosition[Z]};
            //Adds another star node euclidian position on the opposite side.
            downriggerTargetPositionList.add(oppositeNavigationPoint);
            //Caculates theta from the x axis.
            thetaFromXAxis=thetaFromXAxis+angleDiffs;
            //Adds center euclidian position to ensure that the sphere does not get lost.
            //downriggerTargetPositionList.add(echosounderBeamTargetPosition);
            downriggerTargetPositionList.add(origPosition);
        }
        //The numerator is a variable, when combined with the denominator, can
        //determine the current progress of the automated navigation function.
        numerator = 0;
        //The denominator is known by the total size of the navigationPositionList.
        denominator = downriggerTargetPositionList.size();
        //
        for (double[] targetCoordinates : downriggerTargetPositionList) 
        {
            //Convert downriggerTargetPositionList to "floats" for use in SingleNavigation function.
            float[] navigationPoint = {(float) targetCoordinates[X],(float) targetCoordinates[Y],(float) targetCoordinates[Z]};

            if (!AutoCalibration.navigationState.equals("stopped"))
            {
                (new Thread(new SingleNavigation(navigationPoint))).start();
            }
            else
            {
                numerator=0;
                //percentage=0;
                updateProgressBar();
                break;
            }
            //The progress bar must be updated every time it successfully
            //navigates to a navigation point.
            numerator=numerator+1;
            //The percentage of the progress bar must be updated.
            percentage = numerator/denominator;
            //Updates the progress bar.
            updateProgressBar();
            //Puts a small thread.sleep to settle the position of the calibration
            //sphere once it reaches its navigation point.
            Thread.sleep(100);
            //If the navigation state is set to "stopped" then the navigation
            //ceases along with the navigation points in the targetCoordinatesList.
        }
        //Set the navigation state to "stopped" since the automated calibration session is over.
        AutoCalibration.navigationState="stopped";
        NavigationControls.starTypeButton.setEnabled(true);
        NavigationControls.spiralTypeButton.setEnabled(true);
        NavigationControls.gridTypeButton.setEnabled(true);
        NavigationControls.updatedButton.setSelected(false);
        NavigationControls.updatedButton.setText("Activate");
        ButtonHandler.enableNavigationControls();
        NavigationControls.runCalibrationButton.setEnabled(true);
        NavigationControls.pauseCalibrationButton.setEnabled(false);
        NavigationControls.stopCalibrationButton.setEnabled(false);
    }

    
    /*
    * Description:
    * Navigates the calibration sphere in a star like pattern to ensure optimal
    * coverage, efficiency and algorithm simplicity.
    * Status:
    * Function is untested.
    */ 
    public static void gridTypeAutomatedNavigation(float fullBeamAngle)
            throws PortInUseException, InterruptedException, IOException, UnsupportedCommOperationException, NoSuchPortException
    {
        //ButtonHandler.disableNavigationControls();
        //Defines an arraylist which is used to store the different positions
        //to navigate to.
        ArrayList <double[]> downriggerTargetPositionList = new ArrayList();
        //Uses the distance between the transducer and the echosounder view along
        //with the beam angle input in order to define the radius of the
        //echosounder view.
        //float sonarCrossSectionalRadius = (float) (Math.abs(echosounderBeamTargetPosition[Z]-transducerPosition[Z])
        //                                                    *Math.tan(Math.toRadians(fullTransducerBeamAngle/2)))+3;
        double sonarCrossSectionalRadius = (Math.abs(echosounderBeamTargetPosition[Z]-transducerPosition[Z]))*Math.tan(Math.toRadians(fullBeamAngle/2))+3;
        int iSonarCross = Math.round((float) sonarCrossSectionalRadius);

        //Builds an array of points which forms a grid.
        for (int xDifferential = -1; xDifferential<(iSonarCross*2)+1; xDifferential++)
        {
            for (int yDifferential=0; yDifferential<iSonarCross*2; yDifferential++)
            {
                double[] navigationPoint = {echosounderBeamTargetPosition[X]-sonarCrossSectionalRadius+xDifferential,
                                           echosounderBeamTargetPosition[Y]-sonarCrossSectionalRadius+yDifferential,
                                           echosounderBeamTargetPosition[Z]};
                //Add this point to the list of navigation points.
                downriggerTargetPositionList.add(navigationPoint);
            }
        }
        //The last point of navigation is the target point so that the sphere is not lost.
        double[] targetPosition = {echosounderBeamTargetPosition[X],echosounderBeamTargetPosition[Y],echosounderBeamTargetPosition[Z]};
        downriggerTargetPositionList.add(targetPosition);
        //The numerator is a variable, when combined with the denominator, can
        //determine the current progress of the automated navigation function.
        numerator = 0;
        //The denominator is known by the total size of the navigationPositionList.
        denominator = downriggerTargetPositionList.size();
        //Navigates through the navigation points.
        for (double[] targetCoordinates : downriggerTargetPositionList) 
        {
            //The progress bar must be updated every time it successfully
            //navigates to a navigation point.
            numerator=numerator+1;
            //The percentage of the progress bar must be updated.
            percentage = numerator/denominator;

            //Convert downriggerTargetPositionList to "floats" for use in SingleNavigation function.
            float[] navigationPoint = {(float) targetCoordinates[X],(float) targetCoordinates[Y],(float) targetCoordinates[Z]};

            if (!AutoCalibration.navigationState.equals("stopped"))
            {
                (new Thread(new SingleNavigation(navigationPoint))).start();
            }
            else
            {
                numerator=0;
                //percentage=0;
                updateProgressBar();
                break;
            }
            //Updates the progress bar.
            updateProgressBar();
            //Puts a small thread.sleep to settle the position of the calibration
            //sphere once it reaches its navigation point.
            Thread.sleep(100);
        }
        //Set the navigation state to "stopped" since the automated calibration
        //session is over.
        AutoCalibration.navigationState="stopped";
        NavigationControls.starTypeButton.setEnabled(true);
        NavigationControls.spiralTypeButton.setEnabled(true);
        NavigationControls.gridTypeButton.setEnabled(true);
        NavigationControls.updatedButton.setSelected(false);
        NavigationControls.updatedButton.setText("Activate");
        ButtonHandler.enableNavigationControls();
        NavigationControls.runCalibrationButton.setEnabled(true);
        NavigationControls.pauseCalibrationButton.setEnabled(false);
        NavigationControls.stopCalibrationButton.setEnabled(false);
    }
    
    
   /*
    * Description:
    * Update the automated navigation session progress bar in real time in terms
    * of completion percentage.
    * Status:
    * Function is stable.
    */
    public static void updateProgressBar() throws InterruptedException
    {
        //If percentage does not equal 1 then everything has been finished.
        if (percentage<1)
        {  
            //Set the new value of the progress bar.
            NavigationControls.calibrationProgressBar.setValue(Math.round((100*percentage)));
        }
        //If percentage is at 100%.
        else
        {   
            //Set the new value of the progress bar to 100%.
            NavigationControls.calibrationProgressBar.setValue(100);
            //Informs the user of a successful automated caibration session.
            Terminal.systemLogMessage("The calibration session has finished successfully!", CoordinateDisplayTopComponent.positionUILogger);
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "The calibration session has finished successfully!");
            NavigationControls.calibrationProgressBar.setValue(0);
        }
    }

   /*
    * Description:
    * Generates euclidian reference map of geometrically valuable nodes
    * (downriggers, ocean surface, transducer) for the calibration session.
    * This comes directly from the vessel configuration xml file.
    * Status:
    * Function is stable.
    */    
    public static void generateCoordinatesForFourDownriggers(String configurationCode)
    {
        //Reads the configuration code.
        switch (configurationCode) 
        {
            //In the case of a port forward - starboard forward - starboard middle - port middle configuration.
            case "pfsfsmpm":
            {
                //Defines the vessel width.
                float vesselWidth = vw;
                //Sets the X position of the port forward downrigger.
                portForwardDownriggerPosition[X]=-pfsf/2;
                //Sets the Y position of the port forward downrigger.
                portForwardDownriggerPosition[Y]=pmpf;
                //Sets the Z position of the port forward downrigger.
                portForwardDownriggerPosition[Z]=pfho;
                //Sets the X position of the starboard forward downrigger.
                starboardForwardDownriggerPosition[X]=pfsf/2;
                //Sets the Y position of the starboard forward downrigger.
                starboardForwardDownriggerPosition[Y]=sfsm;
                //Sets the Z position of the starboard forward downrigger.
                starboardForwardDownriggerPosition[Z]=sfho;
                //Sets the X position of the starboard middle downrigger.
                starboardMiddleDownriggerPosition[X]=smpm/2;
                //Sets the Y position of the starboard middle downrigger.
                starboardMiddleDownriggerPosition[Y]=0;
                //Sets the Z position of the starboard middle downrigger.
                starboardMiddleDownriggerPosition[Z]=smho;
                //Sets the X position of the port middle downrigger.
                portMiddleDownriggerPosition[X]=-smpm/2;
                //Sets the Y position of the port middle downrigger.
                portMiddleDownriggerPosition[Y]=0;
                //Sets the Z position of the port middle downrigger.
                portMiddleDownriggerPosition[Z]=pmho;
                //Sets the X position of the echosounder target postion.
                echosounderBeamTargetPosition[X]=dfcl;
                //Sets the Y position of the echosounder target postion.
                echosounderBeamTargetPosition[Y]=(portForwardDownriggerPosition[Y]-pfad);
                //Sets the Z position of the echosounder target postion.
                //The depth is denoted by esd.
                echosounderBeamTargetPosition[Z]=-esd;
                //needs conversion factor -> this was here before ? (fix)
                //Sets the x position of the transducer postion.
                transducerPosition[X]=dfcl;
                //Sets the y position of the transducer postion.
                transducerPosition[Y]=portForwardDownriggerPosition[Y]-pfad;
                //Sets the x position of the transducer postion.
                transducerPosition[Z]=-td;
                //Displays the gui x coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionXTextbox.setText("X = "+Integer.toString(Math.round(echosounderBeamTargetPosition[X]))+" counts");
                //Displays the gui y coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionYTextbox.setText("Y = "+Integer.toString(Math.round(echosounderBeamTargetPosition[Y]))+" counts");
                //Displays the gui z coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionZTextbox.setText("Z = "+Integer.toString(Math.round(echosounderBeamTargetPosition[Z]))+" counts");
                //Displays the gui x coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionXTextbox.setText("X = "+Integer.toString(Math.round(transducerPosition[X]))+" counts");
                //Displays the gui y coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionYTextbox.setText("Y = "+Integer.toString(Math.round(transducerPosition[Y]))+" counts");
                //Displays the gui z coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionZTextbox.setText("Z = "+Integer.toString(Math.round(transducerPosition[Z]))+" counts");
                //Breaks out of case statement.
                break;
            }                
            //In the case of a port forward - starboard forward - starboard aft - port aft configuration.
            case "pfsfsapa":
            {
                //Defines the vessel width.
                float vesselWidth = vw;
                //Sets the X position of the port forward downrigger.
                portForwardDownriggerPosition[X]=-pfsf/2;
                //Sets the Y position of the port forward downrigger.
                portForwardDownriggerPosition[Y]=papf/2;
                //Sets the Z position of the port forward downrigger.
                portForwardDownriggerPosition[Z]=pfho;
                //Sets the X position of the starboard forward downrigger.
                starboardForwardDownriggerPosition[X]=pfsf/2;
                //Sets the Y position of the starboard forward downrigger.
                starboardForwardDownriggerPosition[Y]=sfsa/2;
                //Sets the Z position of the starboard forward downrigger.
                starboardForwardDownriggerPosition[Z]=sfho;
                //Sets the X position of the starboard aft downrigger.
                starboardAftDownriggerPosition[X]=sapa/2;
                //Sets the Y position of the starboard aft downrigger.
                starboardAftDownriggerPosition[Y]=-sfsa/2;
                //Sets the Z position of the starboard aft downrigger.
                starboardAftDownriggerPosition[Z]=saho;
                //Sets the X position of the port aft downrigger.
                portAftDownriggerPosition[X]=-sapa/2;
                //Sets the Y position of the port aft downrigger.
                portAftDownriggerPosition[Y]=-papf/2;
                //Sets the Z position of the port aft downrigger.
                portAftDownriggerPosition[Z]=paho;
                //Sets the x position of the echosounder target postion.
                echosounderBeamTargetPosition[X]=dfcl;
                //Sets the y position of the echosounder target postion.
                echosounderBeamTargetPosition[Y]=(portForwardDownriggerPosition[Y]-pfad);
                //Sets the z position of the echosounder target postion.
                echosounderBeamTargetPosition[Z]=-esd;
                //needs conversion factor --> this was here before (fix) figure out what this is.
                //Sets the x position of the transducer postion.
                transducerPosition[X]=dfcl;
                //Sets the y position of the transducer postion.
                transducerPosition[Y]=portForwardDownriggerPosition[Y]-pfad;
                //Sets the z position of the transducer postion.
                transducerPosition[Z]=-td;
                //Displays the gui x coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionXTextbox.setText("X = "+Integer.toString(Math.round(echosounderBeamTargetPosition[X]))+" counts");
                //Displays the gui y coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionYTextbox.setText("Y = "+Integer.toString(Math.round(echosounderBeamTargetPosition[Y]))+" counts");
                //Displays the gui z coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionZTextbox.setText("Z = "+Integer.toString(Math.round(echosounderBeamTargetPosition[Z]))+" counts");
                //Displays the gui x coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionXTextbox.setText("X = "+Integer.toString(Math.round(transducerPosition[X]))+" counts");
                //Displays the gui y coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionYTextbox.setText("Y = "+Integer.toString(Math.round(transducerPosition[Y]))+" counts");
                //Displays the gui z coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionZTextbox.setText("Z = "+Integer.toString(Math.round(transducerPosition[Z]))+" counts");
                //Breaks out of case statement.
                break;
            }       
            //In the case of a port middle - starboard middle - starboard aft - port aft configuration.
            case "pmsmsapa":
            {     
                //Defines the vessel width.
                float vesselWidth = vw;
                //Sets the x position of the port middle downrigger.
                portMiddleDownriggerPosition[X]=-pmsm/2;
                //Sets the y position of the port middle downrigger.
                portMiddleDownriggerPosition[Y]=0;
                //Sets the z position of the port middle downrigger.
                portMiddleDownriggerPosition[Z]=pmho;
                //Sets the x position of the starboard middle downrigger.
                starboardMiddleDownriggerPosition[X]=pmsm/2;
                //Sets the y position of the starboard middle downrigger.
                starboardMiddleDownriggerPosition[Y]=0;
                //Sets the z position of the starboard middle downrigger.
                starboardMiddleDownriggerPosition[Z]=smho;
                //Sets the x position of the starboard aft downrigger.
                starboardAftDownriggerPosition[X]=sapa/2;
                //Sets the y position of the starboard aft downrigger.
                starboardAftDownriggerPosition[Y]=-smsa;
                //Sets the z position of the starboard aft downrigger.
                starboardAftDownriggerPosition[Z]=saho;
                //Sets the x position of the port aft downrigger.
                portAftDownriggerPosition[X]=-sapa/2;
                //Sets the y position of the port aft downrigger.
                portAftDownriggerPosition[Y]=-papm;
                //Sets the z position of the port aft downrigger.
                portAftDownriggerPosition[Z]=paho;
                //Sets the x position of the echosounder target postion.
                echosounderBeamTargetPosition[X]=dfcl;
                //Sets the y position of the echosounder target postion.
                echosounderBeamTargetPosition[Y]=(portMiddleDownriggerPosition[Y]-pmad);
                //Sets the z position of the echosounder target postion.
                echosounderBeamTargetPosition[Z]=-esd;
                //needs conversion factor -? This comment was here before.
                //Sets the x position of transducer position.
                transducerPosition[X]=dfcl;
                //Sets the y position of transducer position.
                transducerPosition[Y]=portMiddleDownriggerPosition[Y]-pmad;
                //Sets the z position of transducer position.
                transducerPosition[Z]=-td;
                //Displays the gui x coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionXTextbox.setText("X = "+Integer.toString(Math.round(echosounderBeamTargetPosition[X]))+" counts");
                //Displays the gui y coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionYTextbox.setText("Y = "+Integer.toString(Math.round(echosounderBeamTargetPosition[Y]))+" counts");
                //Displays the gui z coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionZTextbox.setText("Z = "+Integer.toString(Math.round(echosounderBeamTargetPosition[Z]))+" counts");
                //Displays the gui x coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionXTextbox.setText("X = "+Integer.toString(Math.round(transducerPosition[X]))+" counts");
                //Displays the gui y coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionYTextbox.setText("Y = "+Integer.toString(Math.round(transducerPosition[Y]))+" counts");
                //Displays the gui z coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionZTextbox.setText("Z = "+Integer.toString(Math.round(transducerPosition[Z]))+" counts");
                //Breaks out of case statment.
                break;
            }
            //In the case of no configuration code.    
            default:
            {
                //Breaks out of default statement.
                break;
            }
        }
    }

   /*
    * Description:
    * Generates euclidian reference map of geometrically valuable nodes
    * (downriggers, ocean surface, transducer) for the calibration session.
    * This comes directly from the vessel configuration xml file.
    * Status:
    * Function is stable.
    */    
    public static void generateCoordinatesForThreeDownriggers(String configurationCode)
    {
        //Two different ways to calculate the coordinates depending each on how 
        //the triangle between downriggers is formed. There are 2 different
        //location codes which set the basic configuration for the calibration, 
        //each forms a different triangle of six possible points which correspond
        //to downrigger locations.
        switch (configurationCode) 
        {
            //In the case of a port forward - starboard middle - port aft configuration.
            case "pfsmpa":
            {
                //Defines the vessel width.
                float vesselWidth = vw;
                //Sets the x position of the port forward downrigger.
                portForwardDownriggerPosition[X]=-1*vesselWidth/2;
                //Sets the y position of the port forward downrigger.
                portForwardDownriggerPosition[Y]=papf/2;
                //Sets the z position of the port forward downrigger.
                portForwardDownriggerPosition[Z]=pfho;
                //Sets the x position of the starboard middle downrigger.
                starboardMiddleDownriggerPosition[X]=vesselWidth/2;
                //Sets the y position of the starboard middle downrigger.
                starboardMiddleDownriggerPosition[Y]=0;
                //Sets the z position of the starboard middle downrigger.
                starboardMiddleDownriggerPosition[Z]=smho;
                //Sets the x position of the port aft downrigger.
                portAftDownriggerPosition[X]=-vesselWidth/2;
                //Sets the y position of the port aft downrigger.
                portAftDownriggerPosition[Y]=-papf/2;
                //Sets the z position of the port aft downrigger.
                portAftDownriggerPosition[Z]=paho;
                //Sets the x position of the echosounder target postion.
                echosounderBeamTargetPosition[X]=dfcl;
                //Sets the y position of the echosounder target postion.
                echosounderBeamTargetPosition[Y]=(portForwardDownriggerPosition[Y]-pfad);
                //Sets the z position of the echosounder target postion.
                echosounderBeamTargetPosition[Z]=-esd;
                //Sets the x position of the transducer postion.
                transducerPosition[X]=dfcl;
                //Sets the y position of the transducer postion.
                transducerPosition[Y]=portForwardDownriggerPosition[Y]-pfad;
                //Sets the z position of the transducer postion.
                transducerPosition[Z]=-td;
                //Displays the gui x coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionXTextbox.setText("X = "+Integer.toString(Math.round(echosounderBeamTargetPosition[X]))+" counts");
                //Displays the gui y coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionYTextbox.setText("Y = "+Integer.toString(Math.round(echosounderBeamTargetPosition[Y]))+" counts");
                //Displays the gui z coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionZTextbox.setText("Z = "+Integer.toString(Math.round(echosounderBeamTargetPosition[Z]))+" counts");
                //Displays the gui x coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionXTextbox.setText("X = "+Integer.toString(Math.round(transducerPosition[X]))+" counts");
                //Displays the gui y coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionYTextbox.setText("Y = "+Integer.toString(Math.round(transducerPosition[Y]))+" counts");
                //Displays the gui z coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionZTextbox.setText("Z = "+Integer.toString(Math.round(transducerPosition[Z]))+" counts");
                //Breaks out of case statement.
                break;
            }
            //In the case of a port middle - starboard forward - starboard aft configuration.
            case "pmsfsa":
            {   
                //Defines the vessel width.
                double vesselWidth = vw;
                //Sets the x position of the port middle downrigger.
                portMiddleDownriggerPosition[X]=(float) (-vesselWidth/2);
                //Sets the y position of the port middle downrigger.
                portMiddleDownriggerPosition[Y]=0;
                //Sets the z position of the port middle downrigger.
                portMiddleDownriggerPosition[Z]=pmho;
                //Sets the x position of the starboard forward downrigger.
                starboardForwardDownriggerPosition[X]=(float) (vesselWidth/2);
                //Sets the y position of the starboard forward downrigger.
                starboardForwardDownriggerPosition[Y]=(float) Math.pow(Math.pow(pmsf,2)-Math.pow(vesselWidth,2),0.5);
                //Sets the z position of the starboard forward downrigger.
                starboardForwardDownriggerPosition[Z]=sfho;
                //Sets the x position of the starboard aft downrigger.
                starboardAftDownriggerPosition[X]=(float) (vesselWidth/2);
                //Sets the y position of the starboard aft downrigger.
                starboardAftDownriggerPosition[Y]=starboardForwardDownriggerPosition[Y]-sfsa;
                //Sets the z position of the starboard aft downrigger.
                starboardAftDownriggerPosition[Z]=saho;
                //Sets the x position of the echosounder target postion.
                echosounderBeamTargetPosition[X]=dfcl;
                //Sets the y position of the echosounder target postion.
                echosounderBeamTargetPosition[Y]=(starboardForwardDownriggerPosition[Y]-sfad);
                //Sets the z position of the echosounder target postion.
                echosounderBeamTargetPosition[Z]=-esd;
                //Sets the x position of the transducer postion.
                transducerPosition[X]=dfcl;
                //Sets the y position of the transducer postion.
                transducerPosition[Y]=starboardForwardDownriggerPosition[Y]-sfad;
                //Sets the z position of the transducer postion.
                transducerPosition[Z]=-td;
                //Displays the gui x coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionXTextbox.setText("X = "+Integer.toString(Math.round(echosounderBeamTargetPosition[X]))+" counts");
                //Displays the gui y coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionYTextbox.setText("Y = "+Integer.toString(Math.round(echosounderBeamTargetPosition[Y]))+" counts");
                //Displays the gui z coordinate of the echosounder beam target.
                CoordinateDisplayTopComponent.targetPositionZTextbox.setText("Z = "+Integer.toString(Math.round(echosounderBeamTargetPosition[Z]))+" counts");
                //Displays the gui x coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionXTextbox.setText("X = "+Integer.toString(Math.round(transducerPosition[X]))+" counts");
                //Displays the gui y coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionYTextbox.setText("Y = "+Integer.toString(Math.round(transducerPosition[Y]))+" counts");
                //Displays the gui z coordinate of the transducer position.
                CoordinateDisplayTopComponent.transducerPositionZTextbox.setText("Z = "+Integer.toString(Math.round(transducerPosition[Z]))+" counts");
                //Breaks out of case staement.
                break;
            }
            //In the case of no configuration code.
            default:
            {
                //Breaks out of default statement.
                break;
            }
        }
    }
         
    //This is the first of a four tier system of functions which calculate the 
    //cartesian coordinates of the calibration sphere using the downrigger geometry
    //and the vessel configuration file values.  
     
   /*
    * Description:
    * 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static void roughCalibrateCoordinates()
    {
        //Defines a euclidian x dimension.
        ArrayList sampleLocationXRangeList=new ArrayList();
        //Defines a euclidian y dimension.
        ArrayList sampleLocationYRangeList=new ArrayList();
        //Defines a euclidian z dimension.
        ArrayList sampleLocationZRangeList=new ArrayList();
        //Defines a sample array list to compare to real downrigger counts.
        ArrayList euclidianSampleArrayList = new ArrayList();
        //Max depth worth analyzing.
        float maxDepth;
        //This is hardcoded but is an important factor since it can easily
        //double or triple the memory needed to run echocal.
        maxDepth = (2*echosounderBeamTargetPosition[Z]);
        //The step size is denoted by n. This loop picks points on the number
        //line and stores them.  
        switch(configurationCode)
        {
            //
            case "pmsfsa":
            {
                //
                for(float n=portMiddleDownriggerPosition[X]; n<starboardForwardDownriggerPosition[X]; n=(n+2))
                {
                    //Adds point on number line to sampleLocationRangeListX.
                    sampleLocationXRangeList.add(n);
                }
                //The step size is denoted by n. This loop picks points on the
                //number line and stores them. 
                for(float n=starboardAftDownriggerPosition[Y]; n<starboardForwardDownriggerPosition[Y]; n=(n+2))
                {
                    //Adds point on number line to sampleLocationRangeListY.
                    sampleLocationYRangeList.add(n);
                }
                //Breaks out of case statement.
                break;
            }
            //
            case "pfsmpa":
            {
                //
                for(float n=portForwardDownriggerPosition[X]; n<starboardMiddleDownriggerPosition[X]; n=(n+1))
                {
                    //Adds point on number line to sampleLocationRangeListX.
                    sampleLocationXRangeList.add(n);
                }
                //The step size is denoted by n. This loop picks points on the
                //number line and stores them. 
                for(float n=portAftDownriggerPosition[Y]; n<portForwardDownriggerPosition[Y]; n=(n+1))
                {
                    //Adds point on number line to sampleLocationRangeListY.
                    sampleLocationYRangeList.add(n);
                }
                //Breaks out of case statement.
                break;
            }
            //
            case "pfsfsmpm":
            {
                //
                for(float n=portForwardDownriggerPosition[X]; n<starboardForwardDownriggerPosition[X]; n=(n+1))
                {
                    //Adds point on number line to sampleLocationRangeListX.
                    sampleLocationXRangeList.add(n);
                }
                //The step size is denoted by n. This loop picks points on the
                //number line and stores them. 
                for(float n=portMiddleDownriggerPosition[Y]; n<portForwardDownriggerPosition[Y]; n=(n+1))
                {
                    //Adds point on number line to sampleLocationRangeListY.
                    sampleLocationYRangeList.add(n);
                }
                //Breaks out of case statement.
                break;
            }
            //
            case "pfsfsapa":
            {
                //
                for(float n=portForwardDownriggerPosition[X]; n<starboardForwardDownriggerPosition[X]; n=(n+1))
                {
                    //Adds point on number line to sampleLocationRangeListX.
                    sampleLocationXRangeList.add(n);
                }
                //The step size is denoted by n. This loop picks points on the
                //number line and stores them. 
                for(float n=portAftDownriggerPosition[Y]; n<portForwardDownriggerPosition[Y]; n=(n+1))
                {
                    //Adds point on number line to sampleLocationRangeListY.
                    sampleLocationYRangeList.add(n);
                }
                //Breaks out of case statement.
                break;
            }
            //
            case "pmsmsapa":
            {
                //
                for(float n=portMiddleDownriggerPosition[X]; n<starboardMiddleDownriggerPosition[X]; n=(n+1))
                {
                    //Adds point on number line to sampleLocationRangeListX.
                    sampleLocationXRangeList.add(n);
                }
                //The step size is denoted by n. This loop picks points on the
                //number line and stores them. 
                for(float n=portAftDownriggerPosition[Y]; n<portForwardDownriggerPosition[Y]; n=(n+1))
                {
                    //Adds point on number line to sampleLocationRangeListY.
                    sampleLocationYRangeList.add(n);
                }
                //Breaks out of case statement.
                break;
            }
            //
            default:
            {
                //Breaks out of case statement.
                break;
            }  
        }
        
        //
        
        //The step size is denoted by n. This loop picks points on the number
        //line and stores them. 
        for (float n=maxDepth; n<0; n=(float) (n+0.4))
        {
            //Adds point on number line to sampleLocationRangeListZ.
            sampleLocationZRangeList.add(n);
        }
        //Creates new array sampleLocationRangeArrayX and allocates the same
        //space as the list it was made to replace.
        Float[] sampleLocationXRangeArray=new Float[sampleLocationXRangeList.size()];
        //Creates new array sampleLocationRangeArrayY and allocates the same
        //space as the list it was made to replace.
        Float[] sampleLocationYRangeArray=new Float[sampleLocationYRangeList.size()];
        //Creates new array sampleLocationRangeArrayZ and allocates the same
        //space as the list it was made to replace.
        Float[] sampleLocationZRangeArray=new Float[sampleLocationZRangeList.size()];
        //Converts x list to array.
        sampleLocationXRangeList.toArray(sampleLocationXRangeArray);
        //Converts y list to array.
        sampleLocationYRangeList.toArray(sampleLocationYRangeArray);
        //Converts z list to array.
        sampleLocationZRangeList.toArray(sampleLocationZRangeArray);
        //Runs through the values of the sampleLocationRangeArrayX.
        for(float x : sampleLocationXRangeArray)
        {
            //Runs through the values of the sampleLocationRangeArrayY.
            for(float y : sampleLocationYRangeArray)
            {
                //Runs through the values of the sampleLocationRangeArrayZ.
                for(float z : sampleLocationZRangeArray)
                {
                    //Declares new sampleEuclidianPosition variable and
                    //initializes it to the current instance of x, y & z.
                    float[] sampleEuclidianArray={x,y,z};
                    //Adds new array to the sampleEuclidianPositionList.
                    euclidianSampleArrayList.add(sampleEuclidianArray);
                }
            }
        }
        //Defines the array which will hold sample euclidian position arrays.
        //This is an array of arrays.
        float[][] sampleEuclidianPositionArray=new float[euclidianSampleArrayList.size()][X+Y+Z];
        //Converts list of arrays to array of arrays by copying the
        //euclidianPositionSamplesArray.
        euclidianSampleArrayList.toArray(sampleEuclidianPositionArray);
        //Defines an arrayList which stores the sum of downrigger differences.
        //This may need a (fix) on the grounds that there are better ways to do this.
        ArrayList positionDeviationArrayList=new ArrayList();
        //Runs through the sample euclidian positions of the cartesian
        //position samples array.
        switch(configurationCode)
        {
            //
            case "pfsmpa":
            {
                for(float[] sampleArray : sampleEuclidianPositionArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma));
                }   
                //Breaks out of case statement.
                break;
            }
            case "pmsfsa":
            {
                for(float[] sampleArray : sampleEuclidianPositionArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double alpha = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma));
                }
                //Breaks out of case statement.
                break;
            }
            
            case "pfsfsmpm":
            {
                for(float[] sampleArray : sampleEuclidianPositionArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard middle position.
                    double gamma = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double delta = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma+delta));
                }    
                //Breaks out of case statement.
                break;
            }
            
            case "pfsfsapa":
            {
                for(float[] sampleArray : sampleEuclidianPositionArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port aft position.
                    double delta = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add(alpha+beta+gamma+delta);
                }
                //Breaks out of case statement.
                break;
            }
            
            case "pmsmsapa":
            {
                for(float[] sampleArray : sampleEuclidianPositionArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double alpha = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard middle position.
                    double beta = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port aft position.
                    double delta = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma+delta));
                }
                //Breaks out of case statement.
                break;
            }
            //
            default:
            {
                //Breaks out of case statement.
                break;     
            }
        }
        //Picks the most well approximated sample.  It seems the positionDeviationArrayList
        //size is somehow larger than the sampleEuclidianPositionsArray
        try 
        {
            euclidianSpherePosition=sampleEuclidianPositionArray[positionDeviationArrayList.indexOf(Collections.min(positionDeviationArrayList))];
        }
        catch (NoSuchElementException ex)
        {
            Terminal.systemLogMessage("roughCalibrateCoordinate function Failed", CoordinateDisplayTopComponent.positionUILogger);
        }
    }
    
    //This is the second of a four tier system of functions which calculate the 
    //cartesian coordinates of the calibration sphere using the downrigger geometry
    //and the vessel configuration file values. 
    
   /*
    * Description:
    * 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static void fineCalibrateCoordinates()
    {    
        //Defines new arrayList of arrays.  This is used to store new samples of
        //euclidian positions to be compared to the euclidian position reflecting
        //the actual downrigger counts.
        ArrayList euclidianSampleArrayList = new ArrayList();
        //Defines the maximum correction range and the increments which will be
        //used in each dimension of euclidian space to find better approximated
        //euclidian position.
        float[] euclidianLocationSampleIncrementArray={-2.0f, -1.8f, -1.6f,
                                                       -1.4f, -1.2f, -1.0f,
                                                       -0.8f, -0.6f, -0.4f,
                                                       -0.2f,  0.0f,  0.2f,
                                                        0.4f,  0.6f,  0.8f,
                                                        1.0f,  1.2f,  1.4f,
                                                        1.6f,  1.8f,  2.0f};
        //The beginning point for picking sample euclidian positions is the current
        //best approximated point.
        float[] euclidianLocationSample = euclidianSpherePosition;
        //Runs through the values in the euclidianCoordinateStepSizeArray and
        //assigns them to local variable x.
        for(float x : euclidianLocationSampleIncrementArray)
        {
            //Runs through the values in the euclidianCoordinateStepSizeArray and
            //assigns them to local variable y.
            for(float y : euclidianLocationSampleIncrementArray)
            {
                //Runs through the values in the euclidianCoordinateStepSizeArray and
                //assigns them to local variable z.
                for(float z : euclidianLocationSampleIncrementArray)
                {
                    //Declares new sample euclidian position array to be stored
                    //in euclidianPositionSamplesList.
                    float[] sampleEuclidianArray={0,0,0};
                    //Builds x component of new sample euclidian position.
                    sampleEuclidianArray[X]=x+euclidianLocationSample[X];   
                    //Builds y component of new sample euclidian position.
                    sampleEuclidianArray[Y]=y+euclidianLocationSample[Y]; 
                    //Builds z component of new sample euclidian position.
                    sampleEuclidianArray[Z]=z+euclidianLocationSample[Z];    
                    //Adds sample euclidian position array to euclidian position
                    //samples list.
                    euclidianSampleArrayList.add(sampleEuclidianArray);
                }
            }
        }
        //Defines the array which will hold sample euclidian position arrays.
        //This is an array of arrays.
        float[][] sampleEuclidianPositionsArray=new float[euclidianSampleArrayList.size()][X+Y+Z];
        //Converts list of arrays to array of arrays by copying the
        //euclidianPositionSamplesArray.
        euclidianSampleArrayList.toArray(sampleEuclidianPositionsArray);
        //Defines an arrayList which stores the sum of downrigger differences.
        //This may need a (fix) on the grounds that there are better ways to do this.
        ArrayList positionDeviationArrayList=new ArrayList();
        
        switch(configurationCode)
        {
            case "pfsmpa":
            {
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard middle position.
                    double beta = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port aft position.
                    double gamma = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma));
                }   
                //Breaks out of case statement.
                break;
            }
            case "pmsfsa":
            {
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double alpha = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma));
                }
                //Breaks out of case statement.
                break;
            }
            case "pfsfsmpm":
            {
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard middle position.
                    double gamma = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double delta = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma+delta));
                }     
                //Breaks out of case statement.
                break;
            }
            case "pfsfsapa":
            {
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port aft position.
                    double delta = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma+delta));
                }
                //Breaks out of case statement.
                break;
            }
            case "pmsmsapa":
            {
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double alpha = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard middle position.
                    double beta = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port aft position.
                    double delta = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma+delta));
                }
                //Breaks out of case statement.
                break;
            }
            default:
            {
                //Breaks out of case statement.
                break;
            }
        }
        //Picks the most well approximated sample.
        try 
        {
            euclidianSpherePosition=sampleEuclidianPositionsArray[positionDeviationArrayList.indexOf(Collections.min(positionDeviationArrayList))];
        }
        catch (NoSuchElementException ex)
        {
            Terminal.systemLogMessage("fineCalibrateCoordinate function Failed", CoordinateDisplayTopComponent.positionUILogger);
        }    
        //Set the coordinate indication label to euclidianSpherePosition[X].
        CoordinateDisplayTopComponent.spherePositionXTextbox.setText("X = "+Math.round(euclidianSpherePosition[X])+" counts");
        //Set the coordinate indication label to euclidianSpherePosition[Y].
        CoordinateDisplayTopComponent.spherePositionYTextbox.setText("Y = "+Math.round(euclidianSpherePosition[Y])+" counts");
        //Set the coordinate indication label to euclidianSpherePosition[Z].
        CoordinateDisplayTopComponent.spherePositionZTextbox.setText("Z = "+Math.round(euclidianSpherePosition[Z])+" counts");
    }
    
    //This is the third of a four tier system of functions which calculate the 
    //cartesian coordinates of the calibration sphere using the downrigger geometry
    //and the vessel configuration file values. 
    
   /*
    * Description:
    * 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static void superFineCalibrateCoordinates()
    {   
        //Defines new arrayList of arrays.  This is used to store new samples of
        //euclidian positions to be compared to the euclidian position reflecting
        //the actual downrigger counts.
        ArrayList euclidianSampleArrayList = new ArrayList();
        //Defines the maximum correction range and the increments which will be
        //used in each dimension of euclidian space to find better approximated
        //euclidian position.
        float[] EuclidianLocationSampleIncrementArray={-1.0f, -0.9f, -0.8f,
                                                       -0.7f, -0.6f, -0.5f,
                                                       -0.4f, -0.3f, -0.2f,
                                                       -0.1f,  0.0f,  0.1f,
                                                        0.2f,  0.3f,  0.4f,
                                                        0.5f,  0.6f,  0.7f,
                                                        0.8f,  0.9f,  1.0f};
        //The begin point for picking sample euclidian positions is the current
        //best approximated point.
        float[] euclidianLocationSample = euclidianSpherePosition;
        //Runs through the values in the euclidianCoordinateStepSizeArray and
        //assigns them to local variable x.
        for(float x : EuclidianLocationSampleIncrementArray)
        {
            //Runs through the values in the euclidianCoordinateStepSizeArray and
            //assigns them to local variable y.
            for(float y : EuclidianLocationSampleIncrementArray)
            {
                //Runs through the values in the euclidianCoordinateStepSizeArray and
                //assigns them to local variable z.
                for(float z : EuclidianLocationSampleIncrementArray)
                {
                    //Declares new sample euclidian position array to be stored
                    //in euclidianPositionSamplesList.
                    float[] sampleEuclidianArray={0,0,0};
                    //Builds x component of new sample euclidian position.
                    sampleEuclidianArray[X]=x+euclidianLocationSample[X];
                    //Builds y component of new sample euclidian position.
                    sampleEuclidianArray[Y]=y+euclidianLocationSample[Y];   
                    //Builds z component of new sample euclidian position.
                    sampleEuclidianArray[Z]=z+euclidianLocationSample[Z];  
                    //Adds sample euclidian position array to euclidian position
                    //samples list.
                    euclidianSampleArrayList.add(sampleEuclidianArray);
                }
            }
        }
        //Defines the array which will hold sample euclidian position arrays.
        //This is an array of arrays.
        float[][] sampleEuclidianPositionsArray=new float[euclidianSampleArrayList.size()][X+Y+Z];
        //Converts list of arrays to array of arrays by copying the
        //euclidianPositionSamplesArray.
        euclidianSampleArrayList.toArray(sampleEuclidianPositionsArray);
        //Defines an arrayList which stores the sum of downrigger differences.
        //This may need a (fix) on the grounds that there are better ways to do this.
        ArrayList positionDeviationArrayList=new ArrayList();
        //Checks the configuration code.
        switch(configurationCode)
        {
            case "pfsmpa":
            {
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard middle position.
                    double beta = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port aft position.
                    double gamma = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma));
                }   
                //Breaks out of case statement.
                break;
            }
            case "pmsfsa":
            {
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double alpha = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Defines the difference in the calculated downrigger 
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma));
                }
                //Breaks out of case statement.
                break;
            }
            case "pfsfsmpm":
            {
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard middle position.
                    double gamma = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double delta = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma+delta));
                }     
                //Breaks out of case statement.
                break;
            }
            case "pfsfsapa":
            {
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port aft position.
                    double delta = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma+delta));
                }
                //Breaks out of case statement.
                break;
            }
            case "pmsmsapa":
            {
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double alpha = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard middle position.
                    double beta = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port aft position.
                    double delta = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma+delta));
                }
                //Breaks out of case statement.
                break;
            }
            default:
            {
                //Breaks out of case statement.
                break;
            }
        }
        //Picks the most well approximated sample.
        try 
        {
            euclidianSpherePosition=sampleEuclidianPositionsArray[positionDeviationArrayList.indexOf(Collections.min(positionDeviationArrayList))];
        }
        catch (NoSuchElementException ex)
        {
            Terminal.systemLogMessage("superFineCalibrateCoordinate function Failed", CoordinateDisplayTopComponent.positionUILogger);
        }    
        //Set the coordinate indication label to euclidianSpherePosition[X].
        CoordinateDisplayTopComponent.spherePositionXTextbox.setText("X = "+Math.round(euclidianSpherePosition[X])+" counts");
        //Set the coordinate indication label to euclidianSpherePosition[Y].
        CoordinateDisplayTopComponent.spherePositionYTextbox.setText("Y = "+Math.round(euclidianSpherePosition[Y])+" counts");
        //Set the coordinate indication label to euclidianSpherePosition[Z].
        CoordinateDisplayTopComponent.spherePositionZTextbox.setText("Z = "+Math.round(euclidianSpherePosition[Z])+" counts");
    }
    
    //This is the last of a four tier system of functions which calculate the 
    //cartesian coordinates of the calibration sphere using the downrigger
    //geometry and the vessel configuration file values. 
    
   /*
    * Description:
    * 
    * Example:
    * Not applicable.
    * Status:
    * Function is stable.
    */
    public static void hyperFineCalibrateCoordinates()
    {   
        //Defines new arrayList of arrays.  This is used to store new samples of
        //euclidian positions to be compared to the euclidian position reflecting
        //the actual downrigger counts.
        ArrayList euclidianSampleArrayList = new ArrayList();
        //Defines the maximum correction range and the increments which will be
        //used in each dimension of euclidian space to find better approximated
        //euclidian position.
        float[] EuclidianLocationSampleIncrementArray={-0.20f, -0.18f, -0.16f,
                                                       -0.14f, -0.12f, -0.10f,
                                                       -0.08f, -0.06f, -0.04f,
                                                       -0.02f,  0.00f,  0.02f,
                                                        0.04f,  0.06f,  0.08f,
                                                        0.10f,  0.12f,  0.14f,
                                                        0.16f,  0.18f,  0.20f};
        //The begin point for picking sample euclidian positions is the current
        //best approximated point.
        float[] euclidianPositionSample = euclidianSpherePosition;
        //Runs through the values in the euclidianCoordinateStepSizeArray and
        //assigns them to local variable x.
        for(float x : EuclidianLocationSampleIncrementArray)
        {
            //Runs through the values in the euclidianCoordinateStepSizeArray and
            //assigns them to local variable y.
            for(float y : EuclidianLocationSampleIncrementArray)
            {
                //Runs through the values in the euclidianCoordinateStepSizeArray and
                //assigns them to local variable z.
                for(float z : EuclidianLocationSampleIncrementArray)
                {
                    //Declares new sample euclidian position array to be stored
                    //in euclidianPositionSamplesList.
                    float[] sampleEuclidianArray={0,0,0};
                    //Builds x component of new sample euclidian position.
                    sampleEuclidianArray[X]=x+euclidianPositionSample[X];       
                    //Builds y component of new sample euclidian position.
                    sampleEuclidianArray[Y]=y+euclidianPositionSample[Y];        
                    //Builds z component of new sample euclidian position.
                    sampleEuclidianArray[Z]=z+euclidianPositionSample[Z];        
                    //Adds sample euclidian position array to euclidian position
                    //samples list.
                    euclidianSampleArrayList.add(sampleEuclidianArray);
                }
            }
        }
        //Defines the array which will hold sample euclidian position arrays.
        //This is an array of arrays.
        float[][] sampleEuclidianPositionsArray=new float[euclidianSampleArrayList.size()][X+Y+Z];
        //Converts list of arrays to array of arrays by copying the
        //euclidianPositionSamplesArray.
        euclidianSampleArrayList.toArray(sampleEuclidianPositionsArray);
        //Defines an arrayList which stores the sum of downrigger differences.
        //This may need a (fix) on the grounds that there are better ways to do this.
        ArrayList positionDeviationArrayList=new ArrayList();
        //Runs through the sample euclidian positions of the cartesian position
        //samples array.
        switch(configurationCode)
        {
            //
            case "pfsmpa":
            {
                //
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard middle position.
                    double beta = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port aft position.
                    double gamma = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma));
                }   
                //Breaks out of case statement.
                break;
            }
            //
            case "pmsfsa":
            {
                //
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double alpha = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma));
                }
                //Breaks out of case statement.
                break;
            }
            //
            case "pfsfsmpm":
            {
                //
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard middle position.
                    double gamma = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double delta = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma+delta));
                }  
                //Breaks out of case statement.
                break;
            }
            //
            case "pfsfsapa":
            {
                //
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port forward position.
                    double alpha = Math.abs(magnitude(portForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[portForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard forward position.
                    double beta = Math.abs(magnitude(starboardForwardDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardForward]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port aft position.
                    double delta = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma+delta));
                }
                //Breaks out of case statement.
                break;
            }
            //
            case "pmsmsapa":
            {
                //
                for(float[] sampleArray : sampleEuclidianPositionsArray)
                {
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port middle position.
                    double alpha = Math.abs(magnitude(portMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[portMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard middle position.
                    double beta = Math.abs(magnitude(starboardMiddleDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardMiddle]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the starboard aft position.
                    double gamma = Math.abs(magnitude(starboardAftDownriggerPosition,sampleArray)-downriggerSpherePosition[starboardAft]);
                    //Defines the difference in the calculated downrigger coordinate
                    //for the euclidian sample position to the actual downrigger
                    //counts for the port aft position.
                    double delta = Math.abs(magnitude(portAftDownriggerPosition,sampleArray)-downriggerSpherePosition[portAft]);
                    //Appends the sum of the magnitudes.
                    //The smallest sum will be the best.
                    positionDeviationArrayList.add((alpha+beta+gamma+delta));
                }
                //Breaks out of case statement.
                break;
            }
            default:
            {
                //Breaks out of case statement.
                break;
            }
        }
        //Finds the most accurate position of the samples by picking the minimum
        //of the position deviation arraylist.
        try 
        {
            euclidianSpherePosition=sampleEuclidianPositionsArray[positionDeviationArrayList.indexOf(Collections.min(positionDeviationArrayList))];
        }
        catch (NoSuchElementException ex)
        {
            Terminal.systemLogMessage("hyperFineCalibrateCoordinate function Failed", CoordinateDisplayTopComponent.positionUILogger);
        }        
        //Set the coordinate indication label to euclidianSpherePosition[X].
        CoordinateDisplayTopComponent.spherePositionXTextbox.setText("X = "+Math.round(euclidianSpherePosition[X])+" counts");
        //Set the coordinate indication label to euclidianSpherePosition[Y].
        CoordinateDisplayTopComponent.spherePositionYTextbox.setText("Y = "+Math.round(euclidianSpherePosition[Y])+" counts");
        //Set the coordinate indication label to euclidianSpherePosition[Z].
        CoordinateDisplayTopComponent.spherePositionZTextbox.setText("Z = "+Math.round(euclidianSpherePosition[Z])+" counts");
    }
    
}
