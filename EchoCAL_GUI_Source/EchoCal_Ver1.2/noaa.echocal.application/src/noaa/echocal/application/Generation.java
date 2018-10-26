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

//EchoCAL java package.
package noaa.echocal.application;

//Imports FileNotFoundException library for these kind of errors.
import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * Description:
 * This class defines and describes the operation of writing the vessel configuration xml file.
 * Status:
 * Class is stable.
 */
public class Generation 
{
    //Defines the vessel configuration code.
    public static String configurationCode;
    //Alongship distance global variables. (For vessel configuration xml file generation only!!!)
    public static String sfad="null"; 
    //Declares configuration file input variables. These will be used in the xml file generation.
    public static String pfad="null"; 
    //Declares configuration file input variables. These will be used in the xml file generation.
    public static String smad="null"; 
    //Declares configuration file input variables. These will be used in the xml file generation.
    public static String pmad="null";
    //Declares configuration file input variables. These will be used in the xml file generation.
    public static String pfpm="null", pfpa="null", pfsf="null", pfsm="null", pfsa="null";
    //Declares configuration file input variables. These will be used in the xml file generation.
    public static String pmpf="null", pmpa="null", pmsf="null", pmsm="null", pmsa="null";
    //Declares configuration file input variables. These will be used in the xml file generation.
    public static String papf="null", papm="null", pasf="null", pasm="null", pasa="null";
    //Declares configuration file input variables. These will be used in the xml file generation.
    public static String sfpf="null", sfpm="null", sfpa="null", sfsm="null", sfsa="null";
    //Declares configuration file input variables. These will be used in the xml file generation.
    public static String smpf="null", smpm="null", smpa="null", smsf="null", smsa="null";
    //Declares configuration file input variables. These will be used in the xml file generation.
    public static String sapf="null", sapm="null", sapa="null", sasf="null", sasm="null";
    //Declares configuration file input variables. These will be used in the xml file generation.
    public static String pfho="null", pmho="null", paho="null", sfho="null", smho="null", saho="null", vw="null";
    final static JFileChooser saveFileChooser = new JFileChooser();  
   /*
    * Description:
    * Main function of this thread.  Writes xml file in a formalized way; xml protocol. 
    * Inputs are pulled from Configuration GUI. When the configuration files are loaded 
    * they directy interact with the geometric information and the configuration codes 
    * seen throughout this software.
    * Status:
    * Function is stable.
    * Note:
    * These inputs seem arbitrary and abstract but they are actually the values generated from the configuration.
    */
    public static void generateVesselConfigurationFile(String configurationCode,
    String pfho, String pfpm, String pfpa, String pfsf, String pfsm, String pfsa, String pmho, String pmpf, 
    String pmpa, String pmsf, String pmsm, String pmsa, String paho, String papf, String papm, String pasf, 
    String pasm, String pasa, String sfho, String sfpf, String sfpm, String sfpa, String sfsm, String sfsa,
    String smho, String smpf, String smpm, String smpa, String smsf, String smsa, String saho, String sapf, 
    String sapm, String sapa, String sasf, String sasm, String sfad, String pfad, String smad, String pmad, 
    String dfcl, String td,   String des, String vw ) throws FileNotFoundException, UnsupportedEncodingException
    {     
        
        //saveFileChooser.showOpenDialog(saveFileChooser);
        saveFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        saveFileChooser.setName("");
        if (saveFileChooser.showSaveDialog(saveFileChooser) == JFileChooser.APPROVE_OPTION)
        {
            String pathVariable = saveFileChooser.getCurrentDirectory().toString();
            String fileName = saveFileChooser.getSelectedFile().getName();
            //Trys to write an XML file describing the vessel configuration.
            try (PrintWriter xmlConfigurationFile = new PrintWriter(pathVariable+"/"+fileName+".xml", "UTF-8"))
            {
                //Writes "<?xml version=\"1.0\"?>" for the first line of the xml file.
                xmlConfigurationFile.println("<?xml version=\"1.0\"?>");
                //Writes "<configuration_file>" in the next line of the xml file.
                xmlConfigurationFile.println("<configurationFile>"); 
                //Writes "<configurationFileName>\""+fn+"\"</configurationFileName>" in the next line of the xml file.
                xmlConfigurationFile.println("<configurationFileName>\""+fileName+"\"</configurationFileName>");
                //Writes "<location_code>\""+lsc+"\"</location_code>" in the next line of the xml file.
                xmlConfigurationFile.println("<configurationCode>\""+configurationCode+"\"</configurationCode>");
                //Writes "<port_forward_distance_to_ocean_surface>\""+pfho+"\"</port_forward_distance_to_ocean_surface>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_forward_distance_to_ocean_surface>\""+pfho+"\"</port_forward_distance_to_ocean_surface>");
                //Writes "<port_forward_distance_to_port_middle>\""+pfpm+"\"</port_forward_distance_to_port_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_forward_distance_to_port_middle>\""+pfpm+"\"</port_forward_distance_to_port_middle>");
                //Writes "<port_forward_distance_to_port_aft>\""+pfpa+"\"</port_forward_distance_to_port_aft>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_forward_distance_to_port_aft>\""+pfpa+"\"</port_forward_distance_to_port_aft>");
                //Writes "<port_forward_distance_to_starboard_forward>\""+pfsf+"\"</port_forward_distance_to_starboard_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_forward_distance_to_starboard_forward>\""+pfsf+"\"</port_forward_distance_to_starboard_forward>");
                //Writes "<port_forward_distance_to_starboard_middle>\""+pfsm+"\"</port_forward_distance_to_starboard_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_forward_distance_to_starboard_middle>\""+pfsm+"\"</port_forward_distance_to_starboard_middle>");
                //Writes "<port_forward_distance_to_starboard_aft>\""+pfsa+"\"</port_forward_distance_to_starboard_aft>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_forward_distance_to_starboard_aft>\""+pfsa+"\"</port_forward_distance_to_starboard_aft>");
                //Writes "<port_middle_distance_to_ocean_surface>\""+pmho+"\"</port_middle_distance_to_ocean_surface>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_middle_distance_to_ocean_surface>\""+pmho+"\"</port_middle_distance_to_ocean_surface>");
                //Writes "<port_middle_distance_to_port_forward>\""+pmpf+"\"</port_middle_distance_to_port_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_middle_distance_to_port_forward>\""+pmpf+"\"</port_middle_distance_to_port_forward>");
                //Writes "<port_middle_distance_to_port_aft>\""+pmpa+"\"</port_middle_distance_to_port_aft>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_middle_distance_to_port_aft>\""+pmpa+"\"</port_middle_distance_to_port_aft>");
                //Writes "<port_middle_distance_to_starboard_forward>\""+pmsf+"\"</port_middle_distance_to_starboard_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_middle_distance_to_starboard_forward>\""+pmsf+"\"</port_middle_distance_to_starboard_forward>");
                //Writes "<port_middle_distance_to_starboard_middle>\""+pmsm+"\"</port_middle_distance_to_starboard_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_middle_distance_to_starboard_middle>\""+pmsm+"\"</port_middle_distance_to_starboard_middle>");
                //Writes "<port_middle_distance_to_starboard_aft>\""+pmsa+"\"</port_middle_distance_to_starboard_aft>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_middle_distance_to_starboard_aft>\""+pmsa+"\"</port_middle_distance_to_starboard_aft>");
                //Writes "<port_aft_distance_to_ocean_surface>\""+paho+"\"</port_aft_distance_to_ocean_surface>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_aft_distance_to_ocean_surface>\""+paho+"\"</port_aft_distance_to_ocean_surface>");
                //Writes "<port_aft_distance_to_port_forward>\""+papf+"\"</port_aft_distance_to_port_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_aft_distance_to_port_forward>\""+papf+"\"</port_aft_distance_to_port_forward>");
                //Writes "<port_aft_distance_to_port_middle>\""+papm+"\"</port_aft_distance_to_port_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_aft_distance_to_port_middle>\""+papm+"\"</port_aft_distance_to_port_middle>");
                //Writes "<port_aft_distance_to_starboard_forward>\""+pasf+"\"</port_aft_distance_to_starboard_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_aft_distance_to_starboard_forward>\""+pasf+"\"</port_aft_distance_to_starboard_forward>");
                //Writes "<port_aft_distance_to_starboard_middle>\""+pasm+"\"</port_aft_distance_to_starboard_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_aft_distance_to_starboard_middle>\""+pasm+"\"</port_aft_distance_to_starboard_middle>");
                //Writes "<port_aft_distance_to_starboard_aft>\""+pasa+"\"</port_aft_distance_to_starboard_aft>" in the next line of the xml file.
                xmlConfigurationFile.println("<port_aft_distance_to_starboard_aft>\""+pasa+"\"</port_aft_distance_to_starboard_aft>");
                //Writes "<starboard_forward_distance_to_ocean_surface>\""+sfho+"\"</starboard_forward_distance_to_ocean_surface>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_forward_distance_to_ocean_surface>\""+sfho+"\"</starboard_forward_distance_to_ocean_surface>");
                //Writes "<starboard_forward_distance_to_port_forward>\""+sfpf+"\"</starboard_forward_distance_to_port_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_forward_distance_to_port_forward>\""+sfpf+"\"</starboard_forward_distance_to_port_forward>");
                //Writes "<starboard_forward_distance_to_port_middle>\""+sfpm+"\"</starboard_forward_distance_to_port_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_forward_distance_to_port_middle>\""+sfpm+"\"</starboard_forward_distance_to_port_middle>");
                //Writes "<starboard_forward_distance_to_port_aft>\""+sfpa+"\"</starboard_forward_distance_to_port_aft>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_forward_distance_to_port_aft>\""+sfpa+"\"</starboard_forward_distance_to_port_aft>");
                //Writes "<starboard_forward_distance_to_starboard_middle>\""+sfsm+"\"</starboard_forward_distance_to_starboard_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_forward_distance_to_starboard_middle>\""+sfsm+"\"</starboard_forward_distance_to_starboard_middle>");
                //Writes "<starboard_forward_distance_to_starboard_aft>\""+sfsa+"\"</starboard_forward_distance_to_starboard_aft>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_forward_distance_to_starboard_aft>\""+sfsa+"\"</starboard_forward_distance_to_starboard_aft>");
                //Writes "<starboard_middle_distance_to_ocean_surface>\""+smho+"\"</starboard_middle_distance_to_ocean_surface>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_middle_distance_to_ocean_surface>\""+smho+"\"</starboard_middle_distance_to_ocean_surface>");
                //Writes "<starboard_middle_distance_to_port_forward>\""+smpf+"\"</starboard_middle_distance_to_port_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_middle_distance_to_port_forward>\""+smpf+"\"</starboard_middle_distance_to_port_forward>");
                //Writes "<starboard_middle_distance_to_port_middle>\""+smpm+"\"</starboard_middle_distance_to_port_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_middle_distance_to_port_middle>\""+smpm+"\"</starboard_middle_distance_to_port_middle>");
                //Writes "<starboard_middle_distance_to_port_aft>\""+smpa+"\"</starboard_middle_distance_to_port_aft>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_middle_distance_to_port_aft>\""+smpa+"\"</starboard_middle_distance_to_port_aft>");
                //Writes "<starboard_middle_distance_to_starboard_forward>\""+smsf+"\"</starboard_middle_distance_to_starboard_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_middle_distance_to_starboard_forward>\""+smsf+"\"</starboard_middle_distance_to_starboard_forward>");
                //Writes "<starboard_middle_distance_to_starboard_aft>\""+smsa+"\"</starboard_middle_distance_to_starboard_aft>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_middle_distance_to_starboard_aft>\""+smsa+"\"</starboard_middle_distance_to_starboard_aft>");
                //Writes "<starboard_aft_distance_to_ocean_surface>\""+saho+"\"</starboard_aft_distance_to_ocean_surface>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_aft_distance_to_ocean_surface>\""+saho+"\"</starboard_aft_distance_to_ocean_surface>");
                //Writes "<starboard_aft_distance_to_port_forward>\""+sapf+"\"</starboard_aft_distance_to_port_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_aft_distance_to_port_forward>\""+sapf+"\"</starboard_aft_distance_to_port_forward>");
                //Writes "<starboard_aft_distance_to_port_middle>\""+sapm+"\"</starboard_aft_distance_to_port_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_aft_distance_to_port_middle>\""+sapm+"\"</starboard_aft_distance_to_port_middle>");
                //Writes "<starboard_aft_distance_to_port_aft>\""+sapa+"\"</starboard_aft_distance_to_port_aft>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_aft_distance_to_port_aft>\""+sapa+"\"</starboard_aft_distance_to_port_aft>");
                //Writes "<starboard_aft_distance_to_starboard_forward>\""+sasf+"\"</starboard_aft_distance_to_starboard_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_aft_distance_to_starboard_forward>\""+sasf+"\"</starboard_aft_distance_to_starboard_forward>");
                //Writes "<starboard_aft_distance_to_starboard_middle>\""+sasm+"\"</starboard_aft_distance_to_starboard_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<starboard_aft_distance_to_starboard_middle>\""+sasm+"\"</starboard_aft_distance_to_starboard_middle>");
                //Writes "<vessel_width>\""+vw+"\"</vessel_width>" in the next line of the xml file.
                xmlConfigurationFile.println("<vessel_width>\""+vw+"\"</vessel_width>");
                //Writes "<alongship_distance_to_starboard_forward>\""+sfad+"\"</alongship_distance_to_starboard_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<alongship_distance_to_starboard_forward>\""+sfad+"\"</alongship_distance_to_starboard_forward>");
                //Writes "<alongship_distance_to_port_forward>\""+pfad+"\"</alongship_distance_to_port_forward>" in the next line of the xml file.
                xmlConfigurationFile.println("<alongship_distance_to_port_forward>\""+pfad+"\"</alongship_distance_to_port_forward>");
                //Writes "<alongship_distance_to_starboard_middle>\""+smad+"\"</alongship_distance_to_starboard_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<alongship_distance_to_starboard_middle>\""+smad+"\"</alongship_distance_to_starboard_middle>");
                //Writes "<alongship_distance_to_port_middle>\""+pmad+"\"</alongship_distance_to_port_middle>" in the next line of the xml file.
                xmlConfigurationFile.println("<alongship_distance_to_port_middle>\""+pmad+"\"</alongship_distance_to_port_middle>");
                //Writes "<distance_from_center_line>\""+dfcl+"\"</distance_from_center_line>" in the next line of the xml file.
                xmlConfigurationFile.println("<distance_from_center_line>\""+dfcl+"\"</distance_from_center_line>");
                //Writes "<transducer_depth>\""+td+"\"</transducer_depth>" in the next line of the xml file.
                xmlConfigurationFile.println("<transducer_depth>\""+td+"\"</transducer_depth>");
                //Writes "<depth_of_echosounder_view_centroid>\""+des+"\"</depth_of_echosounder_view_centroid>" in the next line of the xml file.
                xmlConfigurationFile.println("<depth_of_echosounder_view_centroid>\""+des+"\"</depth_of_echosounder_view_centroid>");
                //Writes "</configuration_file>" in the next line of the xml file.
                xmlConfigurationFile.println("</configurationFile>");           
            }
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "The vessel configuration file was saved!");
        }
        else
        {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "The file save was canceled.");
        }
    }
    
    public static void generateFile()
    {
        switch (VesselConfigurationUITopComponent.configurationTypeCombobox.getSelectedItem().toString())
        {
            case "PF-SM-PA":
            {
                configurationCode="pfsmpa";
                break;
            }
            case "PM-SF-SA":
            {
                configurationCode="pmsfsa";
                break;
            }
            case "PF-SF-SA-PA":
            {
                configurationCode="pfsfsapa";
                break;
            }
            case "PF-SF-SM-PM":
            {
                configurationCode="pfsfsmpm";
                break;
            }
            case "PM-SM-SA-PA":
            {
                configurationCode="pmsmsapa";
                break;
            }
            case "Choose Configuration Type..":
            {
                configurationCode="empty";
                break;
            }
            default:
            {
                break;
            }
        }
        //Checks the configuration code.
        switch (configurationCode) 
        {
            //In the case of a port forward - starboard middle - port aft configuration.
            case "pfsmpa":
            {
                pfsm=VesselConfigurationUITopComponent.firstRelativeDistanceInputTextfield.getText();
                smpa=VesselConfigurationUITopComponent.secondRelativeDistanceInputTextfield.getText();
                papf=VesselConfigurationUITopComponent.thirdRelativeDistanceInputTextfield.getText();
                pfad=VesselConfigurationUITopComponent.transducerYDistanceTextbox.getText();
                break;
            }
            //In the case of a port middle - starboard forward - starboard aft configuration.  
            case "pmsfsa":
            {
                pmsf=VesselConfigurationUITopComponent.firstRelativeDistanceInputTextfield.getText();
                sfsa=VesselConfigurationUITopComponent.secondRelativeDistanceInputTextfield.getText();
                sapm=VesselConfigurationUITopComponent.thirdRelativeDistanceInputTextfield.getText();
                sfad=VesselConfigurationUITopComponent.transducerYDistanceTextbox.getText();
                break;
            }
            //In the case of a port forward - starboard forward - starboard middle - port middle configuration.  
            case "pfsfsmpm":
            {
                pfsf=VesselConfigurationUITopComponent.firstRelativeDistanceInputTextfield.getText();
                sfsm=VesselConfigurationUITopComponent.secondRelativeDistanceInputTextfield.getText();
                smpm=VesselConfigurationUITopComponent.thirdRelativeDistanceInputTextfield.getText();
                pmpf=VesselConfigurationUITopComponent.fourthRelativeDistanceInputTextfield.getText();
                pfad=VesselConfigurationUITopComponent.transducerYDistanceTextbox.getText();
                break;
            }
            //In the case of a port forward - starboard forward - starboard aft - port aft configuration.   
            case "pfsfsapa":
            {
                pfsf=VesselConfigurationUITopComponent.firstRelativeDistanceInputTextfield.getText();
                sfsa=VesselConfigurationUITopComponent.secondRelativeDistanceInputTextfield.getText();
                sapa=VesselConfigurationUITopComponent.thirdRelativeDistanceInputTextfield.getText();
                papf=VesselConfigurationUITopComponent.fourthRelativeDistanceInputTextfield.getText();
                pfad=VesselConfigurationUITopComponent.transducerYDistanceTextbox.getText();
                break;
            }
            //In the case of a port middle - starboard middle - starboard aft - port aft configuration.   
            case "pmsmsapa":
            {
                pmsm=VesselConfigurationUITopComponent.firstRelativeDistanceInputTextfield.getText();
                smsa=VesselConfigurationUITopComponent.secondRelativeDistanceInputTextfield.getText();
                sapa=VesselConfigurationUITopComponent.thirdRelativeDistanceInputTextfield.getText();
                papm=VesselConfigurationUITopComponent.fourthRelativeDistanceInputTextfield.getText();
                pmad=VesselConfigurationUITopComponent.transducerYDistanceTextbox.getText();
                break;
            }
            case "empty":
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "You must choose a configuration type from the combobox.");
                break;
            }
            //
            default:
            {
                //Breaks out of default statement.
                break;  
            }
        }
        //Downrigger height from ocean parameters.
        pfho = VesselConfigurationUITopComponent.portForwardHeightFromOceanTextbox.getText();
        pmho = VesselConfigurationUITopComponent.portMiddleHeightFromOceanTextbox.getText();
        paho = VesselConfigurationUITopComponent.portAftHeightFromOceanTextbox.getText();
        sfho = VesselConfigurationUITopComponent.starboardForwardHeightFromOceanTextbox.getText();
        smho = VesselConfigurationUITopComponent.starboardMiddleHeightFromOceanTextbox.getText();
        saho = VesselConfigurationUITopComponent.starboardAftHeightFromOceanTextbox.getText();
        //This code block converts empty text fields into "null" in the vessel configuration xml file.
        if (pfho.equals("") || pfho.equals(" ") || pfho.equals("  "))
        {
            pfho="null";
        }
        //
        if (pmho.equals("") || pmho.equals(" ") || pmho.equals("  "))
        {
            pmho="null";
        }
        //
        if (paho.equals("") || paho.equals(" ") || paho.equals("  "))
        {
            paho="null";
        }
        //
        if (sfho.equals("") || sfho.equals(" ") || sfho.equals("  "))
        {
            sfho="null";
        }
        //
        if (smho.equals("") || smho.equals(" ") || smho.equals("  "))
        {
            smho="null";
        }
        //
        if (saho.equals("") || saho.equals(" ") || saho.equals("  "))
        {
            saho="null";
        } 
        //Distance from centerline.
        String transducerXOffset = VesselConfigurationUITopComponent.transducerXOffsetTextbox.getText(); 
        //The transducer depth.
        String transducerDepth = VesselConfigurationUITopComponent.transducerDepthTextbox.getText(); 
        //The vessels width.
        String vesselWidth = VesselConfigurationUITopComponent.vesselWidthTextbox.getText();
        //Echosounder Parameters.
        String echosounderDepth = VesselConfigurationUITopComponent.targetDepthTextbox.getText();
        //Tries to generate the vessel configuration file.
        
        if (!("empty".equals(configurationCode)))
        {
            try 
            {
                //Generates vessel configuration file.
                generateVesselConfigurationFile(configurationCode, pfho, pfpm, 
                pfpa, pfsf, pfsm, pfsa, pmho, pmpf, pmpa, pmsf, pmsm, pmsa, paho, papf,
                papm, pasf, pasm, pasa, sfho, sfpf, sfpm, sfpa, sfsm, sfsa, smho, smpf, 
                smpm, smpa, smsf, smsa, saho, sapf, sapm, sapa, sasf, sasm, sfad, pfad, 
                smad, pmad, transducerXOffset, transducerDepth, echosounderDepth, vesselWidth);
            } 
            //If an exception is caught.
            catch (FileNotFoundException | UnsupportedEncodingException ex) 
            {

            }
        }
    }
}
