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
 * This software is designed to communicate with the firmware of the downriggers built by Joe.
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
 * Northeast Fisheries Science Center
 * Ecosystems Survey Branch
 * 
 */

/*
 * Location
 * NOAA/NMFS/NEFSC
 * 166 Water Street
 * Woods Hole, MA 02543
 */

//The EchoCAL java package.
package noaa.echocal.application;

//Imports library for querying comm ports and identifying them.
import gnu.io.CommPortIdentifier;
//Imports NoSuchPortException library to handle these kind of errors.
import gnu.io.NoSuchPortException;
//Imports PortInUseException library to handle these kind of errors.
import gnu.io.PortInUseException;
//Import serial port library.
import gnu.io.SerialPort;
//Imports UnsupportedCommOperationException library to handle these kind of errors.
import gnu.io.UnsupportedCommOperationException;
import java.awt.Component;
//Imports IOException library to handle these kind of errors.
import java.io.IOException;
//Imports arrayList library.
import java.util.ArrayList;
import javax.swing.JOptionPane;


/*
 * Description:
 * This class is used for device querying.  The option to connect to the tranceiver;
 * the functions in this class defines the operation.
 * Status:
 * Could use some work.
 */
public class ConnectionListener implements Runnable
{
    //Declares an array list for storing queryed devices.
    public static ArrayList deviceArrayList = new ArrayList();
   /*
    * Description:
    * This function is used to query devices by the transceiver.
    * Status:
    * Class is stable.
    */
    public static void communicateWithTransceiver()
    {
        //
        int deviceCount = 0;
        //
        if (CommPortConfigUITopComponent.commPortCombobox.getSelectedItem().toString().equals("Select serial port..."))
        {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "You must select a serial port from the drop-down box in the serial port configuration window.");
            Communication.transceiverConnectionLock = true;
        }
        else
        {
            //Tries to interact with the comm port.
            try 
            {   
                //Creates a new line in the text area.
                Terminal.systemLogMessage("\r\n", CommPortConfigUITopComponent.commPortConfigLogger);
                //EchoCal Log: Attempting to connect comm port to transceiver.
                Terminal.systemLogMessage("Echocal is attempting to open the serial port: '"+Communication.selectedCommPortTag+"'...", CommPortConfigUITopComponent.commPortConfigLogger);
                //Constructs "comm_port" object.
                CommPortIdentifier commPort = null;
                //Tries to identify and assign the serial port whos string identifier is equal to the serial connection menu selection string name.
                //Assigns a specific port identifier to the "comm_port" object.
                commPort = CommPortIdentifier.getPortIdentifier(Communication.selectedCommPortTag);

                //Constructs and opens a "selected_serial_port" object out from the "comm_port" object.
                System.out.println("Opening serial port.");
                Communication.selectedSerialPort = (SerialPort) commPort.open("Selected Serial Port",2000); 
                //
                Terminal.systemLogMessage("The selected serial port: "+Communication.selectedCommPortTag+" was opened successfully!", CommPortConfigUITopComponent.commPortConfigLogger);

                //Sets serial parameters for the "selected_serial_port".
                Communication.selectedSerialPort.setSerialPortParams(Integer.parseInt(CommPortConfigUITopComponent.baudRateCombobox.getSelectedItem().toString()), Integer.parseInt(CommPortConfigUITopComponent.dataBitsCombobox.getSelectedItem().toString()), Integer.parseInt(CommPortConfigUITopComponent.stopBitsCombobox.getSelectedItem().toString()), SerialPort.PARITY_NONE);

                //Defines a handshake message "+++".
                String handshakeMessage = "+++";
                //Sends success message to the system log window.
                Terminal.systemLogMessage("Sending +++ signal through tranceiver.", CommPortConfigUITopComponent.commPortConfigLogger);
                //Terminal.systemLogMessage("", CommPortConfigUITopComponent.commPortConfigLogger);            
                //Writes "handshake_message" to "selected_serial_port".
                Communication.selectedSerialPort.getOutputStream().write(handshakeMessage.getBytes());

                //Sleeps until adequate time has been given to the tranceiver
                //to issue a response. (2 seconds)
                Thread.sleep(2000);

                //Defines the byte buffer reading step size....for the upcoming
                //do while loop.
                byte[] okResponseByteArray = new byte[1];
                //Defines an initialized string with zero information 
                String transceiverResponseStringElement="";

                if (Communication.selectedSerialPort.getInputStream().available()>0)
                {
                    Terminal.systemLogMessage("Looking for 'OK' response.", CommPortConfigUITopComponent.commPortConfigLogger);
                    //Do at least once.
                    do
                    {
                        //Reads in "OK" response. 
                        Communication.selectedSerialPort.getInputStream().read(okResponseByteArray);
                        //Converts byte[] type transceiver response into a string character.
                        transceiverResponseStringElement=new String(okResponseByteArray);
                    }
                    //Checks for the end of the downrigger response.
                    while(!(transceiverResponseStringElement.equals("K")));
                    //Sends a status signal to show that function execution
                    //reached this far.
                    Terminal.systemLogMessage("Received 'OK' response signal from transceiver.", CommPortConfigUITopComponent.commPortConfigLogger);
                    //Inform the user by means of the transceiverConnectionStateLabel
                    //that there are connections to devices being made.
                    Terminal.systemLogMessage("Echocal is now receiving initial data from nearby devices!", CommPortConfigUITopComponent.commPortConfigLogger);
                }   

                Terminal.systemLogMessage("Sending 'atnd' device query signal to tranceiver.", CommPortConfigUITopComponent.commPortConfigLogger);
                //The second necessary tranceiver command in order to query the
                //serial devices. The ATND command tells the XBee transceiver to
                //discover all nodes on the wireless Mesh Network.
                String atndString="atnd\r\n";
 
                //Writes the "antd_message".
                Communication.selectedSerialPort.getOutputStream().write(atndString.getBytes());
                //Closes the output stream.
                Communication.selectedSerialPort.getOutputStream().close();
 
                Terminal.systemLogMessage("Please wait 20 seconds while EchoCal attempts to query the available devices...", CommPortConfigUITopComponent.commPortConfigLogger);
                Terminal.systemLogMessage("", CommPortConfigUITopComponent.commPortConfigLogger);
                //Waits the thread 20 seconds.
                Thread.sleep(1000);
                Terminal.systemLogMessage("20 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("19 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("18 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("17 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("16 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("15 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("14 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("13 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("12 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("11 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("10 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("9 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("8 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("7 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("6 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("5 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("4 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("3 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("2 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("1 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                Thread.sleep(1000);
                Terminal.systemLogMessage("0 sec..", CommPortConfigUITopComponent.commPortConfigLogger);
                //Defines the size of the read steps for the upcoming do while loop.
                byte[] deviceLoaderByteArray = new byte[1];
                //Defines a string element which shall be used in the upcoming
                //do while loop.
                String deviceLoaderStringElement;
                //Defines an array list which is used to store the individual
                //bytes of information which describes the available devices.
                ArrayList deviceLoaderArrayList = new ArrayList();
                //An identifier for a particular loop I may need to break from
                //if there is a transceiver but no downrigger boxes.
                outmostLoop:
                //Checks to see if the buffer actually has any information.
                //This is tricky because even if an input stream is available it
                //may not come from the downriggers, but instead the transceiver.
                //Knowing the difference is important.
                if (Communication.selectedSerialPort.getInputStream().available()>0)
                {   
                    int bufferChecker = 0;
                    int deviceQuantity = 0;
                    //Do this at least once (because it must in order to be sensible).
                    do
                    {
                        //while the device loader array list is less than two
                        //elements big, add some placeholder values so that the
                        //upcoming while statement can be checked.
                        while (deviceLoaderArrayList.size() < 2)
                        {
                            //adds a place holder value twice
                            deviceLoaderArrayList.add("\n");
                        }
                        while (Communication.selectedSerialPort.getInputStream().available()==0)
                        {
                            //adds a place holder value twice
                            bufferChecker = bufferChecker +1;
                            if (bufferChecker > 40)
                            {
                                break outmostLoop;
                            }
                        }
                        //"downrigger_response_serial_port.readBytes(parameters)"
                        //is the serial functions which reads in the serial
                        //buffer from the downriggers
                        Communication.selectedSerialPort.getInputStream().read(deviceLoaderByteArray);
                        //converts byte[] type downrigger response into a string character
                        deviceLoaderStringElement=new String(deviceLoaderByteArray);
                        //appends the string characters into "downrigger_response_list"
                        deviceLoaderArrayList.add(deviceLoaderStringElement);
                        deviceQuantity = deviceQuantity + 1;
                    }
                    //checks for the end of the Transceiver response which is a
                    //triple carriage return.
                    while(!((deviceLoaderArrayList.get(deviceLoaderArrayList.size()-1).equals("\r")) && (deviceLoaderArrayList.get(deviceLoaderArrayList.size()-2).equals("\r")) && (deviceLoaderArrayList.get(deviceLoaderArrayList.size()-3).equals("\r"))) || deviceQuantity < 20);
                }
                //Closes the selected serial port because it is no longer needed,
                //however the identifier of this comm port is still saved in
                //memory as a getter in the Communication class.
                Communication.selectedSerialPort.close();
                //Defines a new array which is needed for the upcoming enhanced
                //for loop.
                String[] deviceLoaderArray=new String[deviceLoaderArrayList.size()];
                //Converts "downrigger_response_list" into an array called
                //"downrigger_response_array"
                deviceLoaderArrayList.toArray(deviceLoaderArray);
                //Defines a new string which will be used to store the entire
                //device query as a single string
                String deviceString="";
                //Appends each string element of the "device_loader_array" into
                //a single continuous string
                for(String deviceLoaderArrayIndex : deviceLoaderArray)
                {
                    //Sums up the string elements one element at a time
                    deviceString = deviceString+deviceLoaderArrayIndex;
                }
                //Initializes a new array which is split by the carriage returns.
                String[] deviceArray = deviceString.split("\r");
                //Now let's sort "deviceArray"...
                java.util.Arrays.sort(deviceArray);
                //Adds 'unassign' option to list of available serial devices.
                deviceArrayList.clear();
                //Adds an "Unassigned" object to the deviceArrayList.  (Might need to change)
                deviceArrayList.add("Unassigned");
                //Clear the "availableDeviceList" text area on the GUI.
                CommPortConfigUITopComponent.availableDeviceList.setText(null);
                //Runs through values in the deviceLoaderStringArray
                for (String device: deviceArray)
                {
                    //Based on an adopted formalism set by Joe, each useable
                    //downrigger box must be named EK60CXXX.
                    //This if statement is a filter so that only these
                    //devices are placed into the deviceArrayList.
                    if (device.startsWith("EK60C"))
                    {
                        deviceCount = deviceCount+1;
                        //Adds device serial number to the deviceArrayList.
                        deviceArrayList.add(device);
                        //Adds device to the available device listing.
                        CommPortConfigUITopComponent.availableDeviceList.append(device+"\r\n");
                    }
                }
                //Declares string array and allocates space as large as the deviceArrayList.          
                String[] deviceStringArray=new String[deviceArrayList.size()];
                //Converts arrayList to an array.
                deviceArrayList.toArray(deviceStringArray);
                //Informs user that the EchoCAL system has finished identifying
                //and loading available serial devices.
                Terminal.systemLogMessage("Finished identifying and loading available serial devices!", CommPortConfigUITopComponent.commPortConfigLogger);
                //Populates combobox with devices.
                CoordinateDisplayTopComponent.portForwardDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(deviceStringArray));
                //Populates combobox with devices.
                CoordinateDisplayTopComponent.portMiddleDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(deviceStringArray));
                //Populates combobox with devices.
                CoordinateDisplayTopComponent.portAftDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(deviceStringArray));
                //Populates combobox with devices.
                CoordinateDisplayTopComponent.starboardForwardDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(deviceStringArray));
                //Populates combobox with devices.
                CoordinateDisplayTopComponent.starboardMiddleDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(deviceStringArray));
                //Populates combobox with devices.
                CoordinateDisplayTopComponent.starboardAftDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(deviceStringArray));      
            } 
            //Handle any I/O exceptions that may occure with the Serial Ports.
            catch (NoSuchPortException ex) 
            {
                Terminal.systemLogMessage("ERROR: No such serial port exists...",
                                            CommPortConfigUITopComponent.commPortConfigLogger);
                System.out.println("ERROR: No such serial port exists...");
                
            }
            catch (PortInUseException ex) 
            {
                Terminal.systemLogMessage("ERROR: Serial Port is currently being used by another application...",
                                            CommPortConfigUITopComponent.commPortConfigLogger);
                System.out.println("ERROR: Serial Port is currently being used by another application...");
                
            }
            catch (UnsupportedCommOperationException ex) 
            {
                Terminal.systemLogMessage("ERROR: Comm operation is not supported on this port...",
                                            CommPortConfigUITopComponent.commPortConfigLogger);
                System.out.println("ERROR: Failed to get data from the Serial Port...");
                
            }
            catch (NullPointerException ex) 
            {
                //EchoCal Log: The selected comm port is invalid.
                Terminal.systemLogMessage("Echocal experienced an error while trying to connect.",
                                            CommPortConfigUITopComponent.commPortConfigLogger);
                Terminal.systemLogMessage("Make sure the USB transceiver is plugged in and a serial port is selected above.",
                                            CommPortConfigUITopComponent.commPortConfigLogger);
                System.out.println("ERROR: Serial Port does not exist...");
                
            }
            catch (IOException | InterruptedException ex) 
            {
                Terminal.systemLogMessage("ERROR: Failed to get data from Serial Port...",
                                            CommPortConfigUITopComponent.commPortConfigLogger);
                System.out.println("ERROR: Failed to get data from the Serial Port...");
                
            }
            //Sets transceiverConnection lock to true.
            Communication.transceiverConnectionLock=true;
            // Re-enable the Scan button..
            CommPortConfigUITopComponent.scanButton.setEnabled(true);
            if (deviceCount>0)
            {
                Terminal.systemLogMessage("", CommPortConfigUITopComponent.commPortConfigLogger);
                Terminal.systemLogMessage("You have finished configuring the serial port and transceiver!", CommPortConfigUITopComponent.commPortConfigLogger);
                Terminal.systemLogMessage("The next step is to load a vessel configuration file.", CommPortConfigUITopComponent.commPortConfigLogger);
                Terminal.systemLogMessage("Now that this is finished,  pair downrigger stations with discovered devices in the 'Coordinate Display' window.", CommPortConfigUITopComponent.commPortConfigLogger);
                //CoordinateDisplayTopComponent.loadConfigurationButton.setEnabled(true);
                //VesselConfigurationUITopComponent.loadConfigurationButton.setEnabled(true);
            }
            else
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Error: No downrigger boxes were available to connect with EchoCal.");
            }
        }
        Communication.transceiverConnectionLock=true;
        // Re-enable the Scan button..
        CommPortConfigUITopComponent.scanButton.setEnabled(true);
    }

    /*
        This thread will allow the "ComPortConfig" UI to scan the devices on
        the EchoCAL mesh network while updating the UI in real-time.
    */
    @Override
    public void run() 
    {
        //Start the scan for EchoCAL devices on the mesh network..
        communicateWithTransceiver();
    }
}

