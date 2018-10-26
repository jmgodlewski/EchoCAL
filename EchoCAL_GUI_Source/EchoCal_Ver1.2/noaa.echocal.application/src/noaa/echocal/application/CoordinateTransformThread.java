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

/*
* Description:
*
* Status:
* Class is stable.
*/
/*
public class CoordinateTransformThread implements Runnable
{
    @Override
    public void run() 
    {
        //Calculates initial rough estimate of euclidian coordiantes.
        Geometry.roughCalibrateCoordinates();
        //Calculates second finer estimate of euclidian coordinates.
        Geometry.fineCalibrateCoordinates();
        //Calculates third even finer estimate of euclidian coordinates.
        Geometry.superFineCalibrateCoordinates();
        //Calculates last and most accurate approximation of euclidian coordinates of sphere.
        Geometry.hyperFineCalibrateCoordinates();
    } 
}
*/

public class CoordinateTransformThread
{
    public static void calibrate() 
    {
        //Calculates initial rough estimate of euclidian coordiantes.
        Geometry.roughCalibrateCoordinates();
        //Calculates second finer estimate of euclidian coordinates.
        Geometry.fineCalibrateCoordinates();
        //Calculates third even finer estimate of euclidian coordinates.
        //Geometry.superFineCalibrateCoordinates();
        //Calculates last and most accurate approximation of euclidian coordinates of sphere.
        //Geometry.hyperFineCalibrateCoordinates();
    } 
}

