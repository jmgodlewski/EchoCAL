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
 * Office: (508) 617-3738
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
 * Northeast Fisheries Scienc Center
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

//Imports the library for comm port enumeration.
import java.util.Enumeration;
//Imports the library for identifying comm ports.
import gnu.io.CommPortIdentifier;
//Imports the NoSuchPortException library for dealing with errors of this kind.
import gnu.io.NoSuchPortException;
//Imports the PortInUseException library for dealing with errors of this kind.
import gnu.io.PortInUseException;
//Imports the library required for serial port access.
import gnu.io.SerialPort;
//Imports the UnsupportedCommOperationException library for dealing with errors of this kind.
import gnu.io.UnsupportedCommOperationException;
import java.awt.Component;
//Imports the IOException library for dealing with errors of this kind.
import java.io.IOException;
//Imports the import stream library.
import java.io.InputStream;
//Imports the output stream library.
import java.io.OutputStream;
//Imports the arrayList library.
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;

/*
 * Description:
 * This class defines and describes all of the functions and algorithms respectively of the ECHOCal wireless protocol.
 * Status:
 * Class is stable.
 */
public class Communication 
{
    //Defines the serial port for sending commands.
    public static SerialPort commandSerialPort;
    //Defines the serial port for receiving responses from downrigger.
    public static SerialPort downriggerResponseSerialPort;
    //Defines the stream of information sent to the buffer as a response.
    public static InputStream inputStream;
    //Defines the stream of information sent to the buffer as a command.
    public static OutputStream outputStream;
    //Defines the variable which sets the serial speed.
    public static int baudRate = 9600;
    //Defines the identifier object which matches comm ports with comm port labels
    public static CommPortIdentifier commPortId = null;
    //Defines the serial connection menu comm port user selection
    public static SerialPort selectedSerialPort;
    //A string tag for the selected serial port.
    public static String selectedCommPortTag = "";
    //
    public static boolean transceiverConnectionLock = true;
    
   /*
    * Description:
    * Downrigger serial number identifier string variables.
    */

    //Reference to the serial number of the downrigger box assigned to the
    //port forward region of the vessel.
    public static String portForwardSerialNumber="$Unassigned";
    //Reference to the serial number of the downrigger box assigned to the
    //port middle region of the vessel.
    public static String portMiddleSerialNumber="$Unassigned";
    //Reference to the serial number of the downrigger box assigned to the
    //port aft region of the vessel.
    public static String portAftSerialNumber="$Unassigned";
    //Reference to the serial number of the downrigger box assigned to the
    //starboard forward region of the vessel.
    public static String starboardForwardSerialNumber="$Unassigned";
    //Reference to the serial number of the downrigger box assigned to the
    //starboard middle region of the vessel.
    public static String starboardMiddleSerialNumber="$Unassigned";
    //Reference to the serial number of the downrigger box assigned to the
    //starboard aft region of the vessel.
    public static String starboardAftSerialNumber="$Unassigned";
    
   /*
    * Description:
    * downrigger coordinate identifiers.
    */
    
    //Starboard forward downrigger coordinate identifier.
    public static int starboardForward=3;
    //Starboard middle downrigger coordinate identifier.
    public static int starboardMiddle=4;
    //Starboard aft downrigger coordinate identifier.
    public static int starboardAft=5;
    //Port forward downrigger coordinate identifier.
    public static int portForward=0;
    //Port middle downrigger coordinate identifier.
    public static int portMiddle=1;
    //Port aft downrigger coordinate identifier.
    public static int portAft=2;
    
   /*
    * Description:
    * Command string component identifiers which were defined to increase readability and consistency of the code. 
    */
    
    //Command type string.
    public static String cmd = "cmd";
    //Used for setting new counts.
    public static String updt = "updt";
    //For reel in commands.
    public static String in = "in";
    //For reel out commands.
    public static String out = "out";
    //For fine motor adjustments.
    public static String fine = "fine";
    //For course motor adjustments.
    public static String course = "course";
    //For any general response or command.
    public static String comma = ",";
    //To close any response or command.
    public static String carriageReturn = "\r";
    //So... "count_element" is an integer identifier which represents the index of the array
    //"Float.parseFloat(downrigger_response_string.split(comma,response_array_size)"
    //which contains the float value of the downrigger count.
    public static int countElement=2;
    //Sets the length of the downrigger respons array
    public static int responseArraySize=4;
    //Defines modular count float array variable.
    public static float[] modularCountArray = {0,0,0,0,0,0};
    //Defines a downrigger count unit.
    public static float downriggerCountUnit = 1;
    //Just to make more readable.
    public static float zero = 0;
    //Previous count of the downrigger.
    public static int previousDownriggerCount = 0;
    
   /*
    * Description:
    * This function enumerates all the comm ports from the terminal.
    */
    public static void enumerateSerialPorts() throws PortInUseException, UnsupportedCommOperationException, IOException
    {
        int originalListSize = CommPortConfigUITopComponent.commPortCombobox.getModel().getSize();
        //Run through each existing commport on the terminal.
        for (final String commPortListElement : enumeratedCommPortList())
        {
            for(int i=0; i<CommPortConfigUITopComponent.commPortCombobox.getModel().getSize(); i++)
            {
                if(commPortListElement.equals(CommPortConfigUITopComponent.commPortCombobox.getModel().getElementAt(i)))
                {
                    CommPortConfigUITopComponent.commPortCombobox.removeItemAt(i);
                }
            }
            //Lists the comm port tag to the commport combobox.
            CommPortConfigUITopComponent.commPortCombobox.addItem(commPortListElement);
        }
        int listSize = CommPortConfigUITopComponent.commPortCombobox.getModel().getSize();
        if(listSize == originalListSize)
        {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "No extra serial ports were enumerated. Make sure the transceiver is plugged into a USB port.");
        }
    }
    
    //Enumerates the comm ports.
    public static ArrayList<String> enumeratedCommPortList() throws PortInUseException, UnsupportedCommOperationException, IOException, IOException 
    {
        //Constructs an enumeration class object called "commPortEnumerationList".
        Enumeration<CommPortIdentifier> commPortEnumerationList;
        //Constructs an arrayList object called "comm_port_list".
        ArrayList<String> commPortList = new ArrayList<>();
        //Gets available comm port identifiers.
        commPortEnumerationList = CommPortIdentifier.getPortIdentifiers();
        //Querys all serial ports and puts them into an arrayList object called "commPortList".
        while (commPortEnumerationList.hasMoreElements()) 
        {
            //Looks for the comm port identifiers from the enumeration functions.
            //commPortId = (CommPortIdentifier) commPortEnumerationList.nextElement();
            commPortId = commPortEnumerationList.nextElement();
            //Checks if ports are indeed serial ports.
            if (commPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) 
            {
                //Adds the identifier string to the "commPortList" arrayList.
                commPortList.add(commPortId.getName());
            }
        }
        //
        return commPortList;
    }
     
   /*
    * Description:
    * Most basic reel command of the ECHOCal software.
    * Status:
    * Function is stable.
    */
    public static synchronized void reelDownrigger(String serialPortIdentifier, String deviceSerialNumber, String direction, String speed) throws PortInUseException, UnsupportedCommOperationException, IOException, NoSuchPortException
    {
        if (deviceSerialNumber.equals("$Unassigned"))
        {
            System.out.println("No device assigned to downrigger position\r\n");
            return;
        }
        //Constructs "commPort" object.
        CommPortIdentifier commPort;
        //Assigns a specific port identifier to the "commPort" object.
        commPort = CommPortIdentifier.getPortIdentifier(serialPortIdentifier);
        //Constructs and opens a "command_serial_port" object out from the "commPort" object.
        commandSerialPort = (SerialPort) commPort.open("Command Serial Port",500);
        //Sets serial parameters for the "command_serial_port".
        commandSerialPort.setSerialPortParams(Integer.parseInt(CommPortConfigUITopComponent.baudRateCombobox.getSelectedItem().toString()),
                                              Integer.parseInt(CommPortConfigUITopComponent.dataBitsCombobox.getSelectedItem().toString()),
                                              Integer.parseInt(CommPortConfigUITopComponent.stopBitsCombobox.getSelectedItem().toString()),
                                              SerialPort.PARITY_NONE);
        //Defines "downriggerCommandString" as a collection of inputs which correlate with downrigger actions.
        String downriggerCommandString = deviceSerialNumber+comma+cmd+comma+direction+comma+speed+carriageReturn;
        //Defines and opens outputStream buffer from command_serial_port.
        outputStream = commandSerialPort.getOutputStream();
        //Writes "downrigger_command_string" to "command_serial_port".
        outputStream.write(downriggerCommandString.getBytes());
        //Flushes the buffer.
        outputStream.flush();
        //Closes outputStream buffer.
        outputStream.close();
        // Add the READCounts function here. Pass in the "commandSerialPort"
        // and downrigger serial number, and have the function read the response
        // to the above downrigger command. This function will open the "inputStream",
        // read the data coming in from the downrigger's control box, update the
        // count values, and then close the "inputStream".
        
        String responseString = READCounts(commandSerialPort, deviceSerialNumber);

        //Pass the downriggers response string to the Tension Sensor
        //thread for processing of the downrigger count value...
        TensionSensor.currentResponseString = responseString;
        System.out.println(responseString + "\r\n");
        //
        //Closes "commandSerialPort".
        commandSerialPort.close();
    }

   /*
    * Description:
    * Resets the counts of a given downrigger box serial number and serial port.
    * Status:
    * Function is stable.
    */
    public static synchronized void setCounts(String serialPortIdentifier, String deviceSerialNumber, String count) throws PortInUseException, UnsupportedCommOperationException, IOException, NoSuchPortException
    {
        if (deviceSerialNumber.equals("$Unassigned"))
        {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "No device assigned to downrigger postion.");
            return;
        }
        //Constructs "comm_port" object.
        CommPortIdentifier commPort;
        //Assigns a specific port identifier to the "comm_port" object.
        commPort = CommPortIdentifier.getPortIdentifier(serialPortIdentifier);
        //Constructs and opens a "command_serial_port" object out from the "comm_port" object.
        commandSerialPort = (SerialPort) commPort.open("Command Serial Port",2000);
        //Sets serial parameters for the "command_serial_port".
        commandSerialPort.setSerialPortParams(Integer.parseInt(CommPortConfigUITopComponent.baudRateCombobox.getSelectedItem().toString()), Integer.parseInt(CommPortConfigUITopComponent.dataBitsCombobox.getSelectedItem().toString()), Integer.parseInt(CommPortConfigUITopComponent.stopBitsCombobox.getSelectedItem().toString()), SerialPort.PARITY_NONE);
        //Defines "downrigger_command_string" as a collection of inputs which correlate with downrigger actions.
        String downriggerCommandString = deviceSerialNumber+comma+updt+comma+count+comma+fine+carriageReturn;
        //Defines and opens outputStream buffer from command_serial_port.
        outputStream = commandSerialPort.getOutputStream();
        //Writes "downrigger_command_string" to "command_serial_port".
        outputStream.write(downriggerCommandString.getBytes());
        //Flushes the buffer.
        outputStream.flush();
        //Closes outputStream buffer.
        outputStream.close();
        // Add the READCounts function here. Pass in the "commandSerialPort"
        // and downrigger serial number, and have the function read the response
        // to the above downrigger command. This function will open the "inputStream",
        // read the data coming in from the downrigger's control box, update the
        // count values, and then close the "inputStream".
        
        String responseString = READCounts(commandSerialPort, deviceSerialNumber);

        //Pass the downriggers response string to the Tension Sensor
        //thread for processing of the downrigger count value...
        TensionSensor.currentResponseString = responseString;
        System.out.println(responseString + "\r\n");
        //
        //Closes "command_serial_port".
        commandSerialPort.close();
    } 
    
    public static synchronized void getCounts(String serialPortIdentifier, String deviceSerialNumber) 
            throws PortInUseException, UnsupportedCommOperationException, IOException, NoSuchPortException
    {
        if (deviceSerialNumber.equals("$Unassigned"))
        {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "No device assigned to downrigger postion.");
            return;
        }
        //Constructs "comm_port" object.
        CommPortIdentifier commPort;
        //Assigns a specific port identifier to the "comm_port" object.
        commPort = CommPortIdentifier.getPortIdentifier(serialPortIdentifier);
        //Constructs and opens a "command_serial_port" object out from the "comm_port" object.
        commandSerialPort = (SerialPort) commPort.open("Command Serial Port",2000);
        //Sets serial parameters for the "command_serial_port".
        commandSerialPort.setSerialPortParams(Integer.parseInt(CommPortConfigUITopComponent.baudRateCombobox.getSelectedItem().toString()), Integer.parseInt(CommPortConfigUITopComponent.dataBitsCombobox.getSelectedItem().toString()), Integer.parseInt(CommPortConfigUITopComponent.stopBitsCombobox.getSelectedItem().toString()), SerialPort.PARITY_NONE);
        //Defines "downrigger_command_string" as a collection of inputs which correlate with downrigger actions.
        String downriggerCommandString = deviceSerialNumber+comma+"read"+comma+"out"+comma+fine+carriageReturn;
        //Defines and opens outputStream buffer from command_serial_port.
        outputStream = commandSerialPort.getOutputStream();
        //Writes "downrigger_command_string" to "command_serial_port".
        outputStream.write(downriggerCommandString.getBytes());
        //Flushes the buffer.
        outputStream.flush();
        //Closes outputStream buffer.
        outputStream.close();
        // Add the READCounts function here. Pass in the "commandSerialPort"
        // and downrigger serial number, and have the function read the response
        // to the above downrigger command. This function will open the "inputStream",
        // read the data coming in from the downrigger's control box, update the
        // count values, and then close the "inputStream".
        
        String responseString = READCounts(commandSerialPort, deviceSerialNumber);

        //Pass the downriggers response string to the Tension Sensor
        //thread for processing of the downrigger count value...
        TensionSensor.currentResponseString = responseString;
        System.out.println(responseString + "\r\n");
        //
        //Closes "command_serial_port".
        commandSerialPort.close();
    } 
   /*
    * Description:
    * Function "READCounts" will read the response from the downrigger control box
    * coming in on the serial port. The response is a comma separated string with
    * the following structure:
    *            $EK60CXXX,rsp,cnts,dir<CR>
    * where:
    *       $EK60CXXX   = serial number of the responding downrigger control box.
    *       rsp         = identifier that this string is a response to a command.
    *       cnts        = current number of counts that the downrigger wheel has rotated.
    *       dir         = direction that the downrigger wheel rotated (either "in" or "out").

    * Reads the number of counts for a given downrigger box serial number and a given serial port.
    * Status:
    * Function is stable.
    *
    */
    public static synchronized String READCounts(SerialPort responseSerialPort, String deviceSerialNumber) 
            throws IOException
    {   
        //Defines new instance "downriggerResponseByteArray" which allocates
        //memory of byte[] array length "downrigger_response_memory_allocation_size"
        byte[] downriggerResponseByteArray = new byte[1];
        //Stores inputSteam buffer to inputStream variable.
        inputStream = responseSerialPort.getInputStream();
        //Declares downriggerResponseStringElement string variable.
        String downriggerResponseStringElement="";
        //"downriggerResponseList" is a list which stores each byte from
        //the downriggers response as a single charater string.
        ArrayList downriggerResponseList=new ArrayList();
        int bufferChecker = 0;
        //Do at least once.
        do
        {
            //If the bytes which are available in the inputStreamBuffer is zero.
            if (inputStream.available()==0)
            {
                //Add one to previous value of bufferChecker.
                bufferChecker=bufferChecker+1;
            }
            //If bufferChecker variable is greater than ten.
            //System.out.println("BufferChecker = "+bufferChecker);
            if (bufferChecker>100)
            {
                //Makes final reference to serial number of response string.
                final String serialNumber = deviceSerialNumber;
                //Start new Lost communication gui thread.
                inputStream.close();
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Downrigger "+serialNumber+" has lost communication with EchoCal.");
                //Breaks out of the "do..while" loop.
                break;
            }
            try 
            {
                //System.out.println(Arrays.toString(downriggerResponseByteArray));
                //Reads in the byte.
                inputStream.read(downriggerResponseByteArray);
            } 
            catch (IOException ex) 
            {
                System.out.println("Error reading inputStream.\r\n");
                //Exceptions.printStackTrace(ex);
            }
                
            //converts byte[] type downrigger response into a string character
            downriggerResponseStringElement=new String(downriggerResponseByteArray);
            //appends the string characters into "downriggerResponseList"
            downriggerResponseList.add(downriggerResponseStringElement);
            //System.out.println(downriggerResponseList);
        }
        //checks for the end of the downrigger response
        while(!(downriggerResponseStringElement.equals("\r")));

        //Close the input stream and serial port.
        inputStream.close();

        String[] downriggerResponseArray=new String[downriggerResponseList.size()];
        //converts "downrigger_response_list" into an array called "downrigger_response_array"
        downriggerResponseList.toArray(downriggerResponseArray);
        //defines a new string which will be used to store the entire response
        //as a single string
        String downriggerResponseString="";
        //So that the first element of the response string retrieved is indeed a dollar sign.
        boolean dollarSignLock = true;
        //appends each string element of the "downrigger_response_array" into
        //a single continuous string
        for(String downriggerResponseArrayIndex : downriggerResponseArray)
        {
            //If the dollar sign has not been received yet.
            if (dollarSignLock == true)
            {
                //If the string component of the response is a "$".
                if (downriggerResponseArrayIndex.equals("$"))
                {
                    //The dollar sign lock is set to false.
                    dollarSignLock = false;
                }
            }
            //If the dollar sign lock is set to false.
            if (dollarSignLock == false)
            {
                //Sums up the string elements one element at a time.
                downriggerResponseString = downriggerResponseString+downriggerResponseArrayIndex;
            }
        }
        //Defines the isolated count value from the downrigger response and
        //converts it into a float.
        int actualDownriggerCount;
        //This is how the tension sensor extracts all it information.
        //TensionSensor.currentResponseString = downriggerResponseString;
        //(new Thread(new TensionSensor())).start();
        //Try to not run into an ArrayIndexOutOfBoundsException.
        try
        {
            //Converts string response count to integer variable.
            actualDownriggerCount = Math.round(Float.parseFloat(downriggerResponseString.split(comma,responseArraySize)[countElement]));
            //Forgot why I did this --> Fix.
            previousDownriggerCount = actualDownriggerCount;
        }
        //If an ArrayIndexOutOfBoundsException is caught.
        catch (ArrayIndexOutOfBoundsException ex)
        {
            //Actual downrigger count is set equal to the previous downrigger point.
            actualDownriggerCount = previousDownriggerCount;
        }
        // Now let's update the COUNTER value depending on which serial number
        // downrigger responded to the command.
        
        //If the device serial number is equal to the port forward serial number.
        if (deviceSerialNumber.equals(portForwardSerialNumber))
        {
            //Sets downriggerSpherePosition[port_forward] to actualCount.
            Geometry.downriggerSpherePosition[portForward]=actualDownriggerCount;
            //Indicates the counts to the user from the gui.
            CoordinateDisplayTopComponent.portForwardCountTextbox.setText(Integer.toString(actualDownriggerCount)+" counts");
        }
        //If the device serial number is equal to the port middle serial number.
        else if (deviceSerialNumber.equals(portMiddleSerialNumber)) 
        {
            //Sets downriggerSpherePosition[port_middle] to actualCount.
            Geometry.downriggerSpherePosition[portMiddle]=actualDownriggerCount;
            //Indicates the counts to the user from the gui.
            CoordinateDisplayTopComponent.portMiddleCountTextbox.setText(Integer.toString(actualDownriggerCount)+" counts");
        }
        //If the device serial number is equal to the port aft serial number.
        else if(deviceSerialNumber.equals(portAftSerialNumber)) 
        {
            //Sets downriggerSpherePosition[port_aft] to actualCount.
            Geometry.downriggerSpherePosition[portAft]=actualDownriggerCount;
            //Indicates the counts to the user from the gui.
            CoordinateDisplayTopComponent.portAftCountTextbox.setText(Integer.toString(actualDownriggerCount)+" counts");
            }
        //If the device serial number is equal to the starboard forward serial number.
        else if (deviceSerialNumber.equals(starboardForwardSerialNumber)) 
        {
            //Sets downriggerSpherePosition[starboard_forward] to actualCount.
            Geometry.downriggerSpherePosition[starboardForward]=actualDownriggerCount;
            //Indicates the counts to the user from the gui.
            CoordinateDisplayTopComponent.starboardForwardCountTextbox.setText(Integer.toString(actualDownriggerCount)+" counts");
        }
        //If the device serial number is equal to the starboard middle serial number.
        else if(deviceSerialNumber.equals(starboardMiddleSerialNumber)) 
        {
            //Sets downriggerSpherePosition[starboard_middle] to actualCount.
            Geometry.downriggerSpherePosition[starboardMiddle]=actualDownriggerCount;
            //Indicates the counts to the user from the gui.
            CoordinateDisplayTopComponent.starboardMiddleCountTextbox.setText(Integer.toString(actualDownriggerCount)+" counts");
        }
        //If the device serial number is equal to the starboard aft serial number.
        else if (deviceSerialNumber.equals(starboardAftSerialNumber)) 
        {
            //Sets downriggerSpherePosition[starboard_aft] to actualCount.
            Geometry.downriggerSpherePosition[starboardAft]=actualDownriggerCount;
            //Indicates the counts to the user from the gui.
            CoordinateDisplayTopComponent.starboardAftCountTextbox.setText(Integer.toString(actualDownriggerCount)+" counts");
        }
        //If the device serial number is equal to no serial number.
        else
        {
            //Informs user with the sytem log that there are no downriggers
            //associated with the current serial number.
            Terminal.systemLogMessage("Failure: There are no downriggers associated with the current serial number "+deviceSerialNumber+"\n\r\n", CoordinateDisplayTopComponent.positionUILogger);
        }
        return downriggerResponseString;
    }
   /*
    * Description:
    * Main method for testing purposes only.
    * Status:
    * Function is stable.
    */
    public static void main(String[] args)
    {
        //Write in some test code here.
    }
}
