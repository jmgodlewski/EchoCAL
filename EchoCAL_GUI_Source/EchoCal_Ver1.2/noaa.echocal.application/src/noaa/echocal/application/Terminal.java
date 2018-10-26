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

//
package noaa.echocal.application;

import javax.swing.JTextArea;

/*
* Description:
* This class facilitates the prompting of messages in the system log as the
//software is running.
* Status:
* Java class is stable.
*/
public class Terminal
{
    //An empty string variable used for storing system log messages.
    public static String systemLogMessage;
    //A lock designed to be interpreted by the Terminal.java Thread.
    //When the lock is on, nothing happens, but when it is turned off
    //a message is available.
    public static boolean systemLogMessageLock = true;
   
   /*
    * Description:
    * Writes the message in the system log. This function is never actually
    * touched by the programmer.  
    * This function is instead called in the Terminal thread when the
    * systemLogMessage(String message); is executed.
    * Example:
    * writeSystemLogMesage("Downrigger is caught!");
    * displays "Downrigger is caught!" in the system log.
    * Status:
    * Function is stable.
    */
    public static void systemLogMessage(String message, JTextArea textArea)
    {
        //Displays "message" in the system log so that is can be seen by the user.
        textArea.append(message+"\n\r");
        //Keeps scroll pane in the system log at the bottom at all times.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
   
}
