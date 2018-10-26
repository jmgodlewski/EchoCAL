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
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static noaa.echocal.application.VesselConfigurationUITopComponent.configurationPathLabelButton;
import static noaa.echocal.application.VesselConfigurationUITopComponent.configurationTypeCombobox;
import static noaa.echocal.application.VesselConfigurationUITopComponent.firstRelativeDistanceInputTextfield;
import static noaa.echocal.application.VesselConfigurationUITopComponent.fourthRelativeDistanceInputTextfield;
import static noaa.echocal.application.VesselConfigurationUITopComponent.portAftHeightFromOceanTextbox;
import static noaa.echocal.application.VesselConfigurationUITopComponent.portForwardHeightFromOceanTextbox;
import static noaa.echocal.application.VesselConfigurationUITopComponent.portMiddleHeightFromOceanTextbox;
import static noaa.echocal.application.VesselConfigurationUITopComponent.secondRelativeDistanceInputTextfield;
import static noaa.echocal.application.VesselConfigurationUITopComponent.starboardAftHeightFromOceanTextbox;
import static noaa.echocal.application.VesselConfigurationUITopComponent.starboardForwardHeightFromOceanTextbox;
import static noaa.echocal.application.VesselConfigurationUITopComponent.starboardMiddleHeightFromOceanTextbox;
import static noaa.echocal.application.VesselConfigurationUITopComponent.targetDepthTextbox;
import static noaa.echocal.application.VesselConfigurationUITopComponent.thirdRelativeDistanceInputTextfield;
import static noaa.echocal.application.VesselConfigurationUITopComponent.transducerDepthTextbox;
import static noaa.echocal.application.VesselConfigurationUITopComponent.transducerXOffsetTextbox;
import static noaa.echocal.application.VesselConfigurationUITopComponent.transducerYDistanceTextbox;
import static noaa.echocal.application.VesselConfigurationUITopComponent.vesselWidthTextbox;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//noaa.echocal.application//CoordinateDisplay//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "CoordinateDisplayTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "noaa.echocal.application.CoordinateDisplayTopComponent")
@ActionReference(path = "Menu/Tools" , position = 20)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_CoordinateDisplayAction",
        preferredID = "CoordinateDisplayTopComponent"
)
@Messages({
    "CTL_CoordinateDisplayAction=Coordinate Display Window",
    "CTL_CoordinateDisplayTopComponent=Coordinate Window",
    "HINT_CoordinateDisplayTopComponent=This is the Coordinate Window"
})
public final class CoordinateDisplayTopComponent extends TopComponent {

    public CoordinateDisplayTopComponent() {
        initComponents();
        setName("Coordinate Display Window");
        setToolTipText("Coordinate Display Window");
        //Added this.. 7 Sep 2016 JMG
        putClientProperty(TopComponent.PROP_DRAGGING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);
    }
    
    public final JFileChooser fileChooser = new javax.swing.JFileChooser();
    
    public void configureDownriggerStationControls(String configurationCode)
    {
        switch (configurationCode)
        {
            case "pmsfsa":
            {
                portMiddleDownriggerStationButton.setEnabled(true);
                portMiddleDownriggerLineLengthButton.setEnabled(true);
                portMiddleCountTextbox.setEnabled(true);
                portMiddleDeviceCombobox.setEnabled(true);
                portMiddleSetCountsButton.setEnabled(true);
                portMiddleSetCountsTextbox.setEnabled(true);
                portMiddleTensionButton.setEnabled(true);
                portMiddleTensionBar.setEnabled(true);
                
                starboardForwardDownriggerStationButton.setEnabled(true);
                starboardForwardDownriggerLineLengthButton.setEnabled(true);
                starboardForwardCountTextbox.setEnabled(true);
                starboardForwardDeviceCombobox.setEnabled(true);
                starboardForwardSetCountsButton.setEnabled(true);
                starboardForwardSetCountsTextbox.setEnabled(true);
                starboardForwardTensionButton.setEnabled(true);
                starboardForwardTensionBar.setEnabled(true);
                
                starboardAftDownriggerStationButton.setEnabled(true);
                starboardAftDownriggerLineLengthButton.setEnabled(true);
                starboardAftCountTextbox.setEnabled(true);
                starboardAftDeviceCombobox.setEnabled(true);
                starboardAftSetCountsButton.setEnabled(true);
                starboardAftSetCountsTextbox.setEnabled(true);
                starboardAftTensionButton.setEnabled(true);
                starboardAftTensionBar.setEnabled(true);
                
                portForwardDownriggerStationButton.setEnabled(false);
                portForwardDownriggerLineLengthButton.setEnabled(false);
                portForwardCountTextbox.setEnabled(false);
                portForwardDeviceCombobox.setEnabled(false);
                portForwardSetCountsButton.setEnabled(false);
                portForwardSetCountsTextbox.setEnabled(false);
                portForwardTensionButton.setEnabled(false);
                portForwardTensionBar.setEnabled(false);
                
                starboardMiddleDownriggerStationButton.setEnabled(false);
                starboardMiddleDownriggerLineLengthButton.setEnabled(false);
                starboardMiddleCountTextbox.setEnabled(false);
                starboardMiddleDeviceCombobox.setEnabled(false);
                starboardMiddleSetCountsButton.setEnabled(false);
                starboardMiddleSetCountsTextbox.setEnabled(false);
                starboardMiddleTensionButton.setEnabled(false);
                starboardMiddleTensionBar.setEnabled(false);
                
                portAftDownriggerStationButton.setEnabled(false);
                portAftDownriggerLineLengthButton.setEnabled(false);
                portAftCountTextbox.setEnabled(false);
                portAftDeviceCombobox.setEnabled(false);
                portAftSetCountsButton.setEnabled(false);
                portAftSetCountsTextbox.setEnabled(false);
                portAftTensionButton.setEnabled(false);
                portAftTensionBar.setEnabled(false);
                
                break;
            }
            case "pfsmpa":
            {
                
                portForwardDownriggerStationButton.setEnabled(true);
                portForwardDownriggerLineLengthButton.setEnabled(true);
                portForwardCountTextbox.setEnabled(true);
                portForwardDeviceCombobox.setEnabled(true);
                portForwardSetCountsButton.setEnabled(true);
                portForwardSetCountsTextbox.setEnabled(true);
                portForwardTensionButton.setEnabled(true);
                portForwardTensionBar.setEnabled(true);
                
                starboardMiddleDownriggerStationButton.setEnabled(true);
                starboardMiddleDownriggerLineLengthButton.setEnabled(true);
                starboardMiddleCountTextbox.setEnabled(true);
                starboardMiddleDeviceCombobox.setEnabled(true);
                starboardMiddleSetCountsButton.setEnabled(true);
                starboardMiddleSetCountsTextbox.setEnabled(true);
                starboardMiddleTensionButton.setEnabled(true);
                starboardMiddleTensionBar.setEnabled(true);
                
                portAftDownriggerStationButton.setEnabled(true);
                portAftDownriggerLineLengthButton.setEnabled(true);
                portAftCountTextbox.setEnabled(true);
                portAftDeviceCombobox.setEnabled(true);
                portAftSetCountsButton.setEnabled(true);
                portAftSetCountsTextbox.setEnabled(true);
                portAftTensionButton.setEnabled(true);
                portAftTensionBar.setEnabled(true);
                
                portMiddleDownriggerStationButton.setEnabled(false);
                portMiddleDownriggerLineLengthButton.setEnabled(false);
                portMiddleCountTextbox.setEnabled(false);
                portMiddleDeviceCombobox.setEnabled(false);
                portMiddleSetCountsButton.setEnabled(false);
                portMiddleSetCountsTextbox.setEnabled(false);
                portMiddleTensionButton.setEnabled(false);
                portMiddleTensionBar.setEnabled(false);
                
                starboardForwardDownriggerStationButton.setEnabled(false);
                starboardForwardDownriggerLineLengthButton.setEnabled(false);
                starboardForwardCountTextbox.setEnabled(false);
                starboardForwardDeviceCombobox.setEnabled(false);
                starboardForwardSetCountsButton.setEnabled(false);
                starboardForwardSetCountsTextbox.setEnabled(false);
                starboardForwardTensionButton.setEnabled(false);
                starboardForwardTensionBar.setEnabled(false);
                
                starboardAftDownriggerStationButton.setEnabled(false);
                starboardAftDownriggerLineLengthButton.setEnabled(false);
                starboardAftCountTextbox.setEnabled(false);
                starboardAftDeviceCombobox.setEnabled(false);
                starboardAftSetCountsButton.setEnabled(false);
                starboardAftSetCountsTextbox.setEnabled(false);
                starboardAftTensionButton.setEnabled(false);
                starboardAftTensionBar.setEnabled(false);
                        
                break;
            }
            case "pfsfsapa":
            {
                portForwardDownriggerStationButton.setEnabled(true);
                portForwardDownriggerLineLengthButton.setEnabled(true);
                portForwardCountTextbox.setEnabled(true);
                portForwardDeviceCombobox.setEnabled(true);
                portForwardSetCountsButton.setEnabled(true);
                portForwardSetCountsTextbox.setEnabled(true);
                portForwardTensionButton.setEnabled(true);
                portForwardTensionBar.setEnabled(true);
                
                starboardForwardDownriggerStationButton.setEnabled(true);
                starboardForwardDownriggerLineLengthButton.setEnabled(true);
                starboardForwardCountTextbox.setEnabled(true);
                starboardForwardDeviceCombobox.setEnabled(true);
                starboardForwardSetCountsButton.setEnabled(true);
                starboardForwardSetCountsTextbox.setEnabled(true);
                starboardForwardTensionButton.setEnabled(true);
                starboardForwardTensionBar.setEnabled(true);
                
                portMiddleDownriggerStationButton.setEnabled(false);
                portMiddleDownriggerLineLengthButton.setEnabled(false);
                portMiddleCountTextbox.setEnabled(false);
                portMiddleDeviceCombobox.setEnabled(false);
                portMiddleSetCountsButton.setEnabled(false);
                portMiddleSetCountsTextbox.setEnabled(false);
                portMiddleTensionButton.setEnabled(false);
                portMiddleTensionBar.setEnabled(false);
                
                starboardMiddleDownriggerStationButton.setEnabled(false);
                starboardMiddleDownriggerLineLengthButton.setEnabled(false);
                starboardMiddleCountTextbox.setEnabled(false);
                starboardMiddleDeviceCombobox.setEnabled(false);
                starboardMiddleSetCountsButton.setEnabled(false);
                starboardMiddleSetCountsTextbox.setEnabled(false);
                starboardMiddleTensionButton.setEnabled(false);
                starboardMiddleTensionBar.setEnabled(false);
                
                starboardAftDownriggerStationButton.setEnabled(true);
                starboardAftDownriggerLineLengthButton.setEnabled(true);
                starboardAftCountTextbox.setEnabled(true);
                starboardAftDeviceCombobox.setEnabled(true);
                starboardAftSetCountsButton.setEnabled(true);
                starboardAftSetCountsTextbox.setEnabled(true);
                starboardAftTensionButton.setEnabled(true);
                starboardAftTensionBar.setEnabled(true);
                
                portAftDownriggerStationButton.setEnabled(true);
                portAftDownriggerLineLengthButton.setEnabled(true);
                portAftCountTextbox.setEnabled(true);
                portAftDeviceCombobox.setEnabled(true);
                portAftSetCountsButton.setEnabled(true);
                portAftSetCountsTextbox.setEnabled(true);
                portAftTensionButton.setEnabled(true);
                portAftTensionBar.setEnabled(true);
                break;
            }
            case "pfsfsmpm":
            {
                portForwardDownriggerStationButton.setEnabled(true);
                portForwardDownriggerLineLengthButton.setEnabled(true);
                portForwardCountTextbox.setEnabled(true);
                portForwardDeviceCombobox.setEnabled(true);
                portForwardSetCountsButton.setEnabled(true);
                portForwardSetCountsTextbox.setEnabled(true);
                portForwardTensionButton.setEnabled(true);
                portForwardTensionBar.setEnabled(true);
                
                starboardForwardDownriggerStationButton.setEnabled(true);
                starboardForwardDownriggerLineLengthButton.setEnabled(true);
                starboardForwardCountTextbox.setEnabled(true);
                starboardForwardDeviceCombobox.setEnabled(true);
                starboardForwardSetCountsButton.setEnabled(true);
                starboardForwardSetCountsTextbox.setEnabled(true);
                starboardForwardTensionButton.setEnabled(true);
                starboardForwardTensionBar.setEnabled(true);
                
                starboardMiddleDownriggerStationButton.setEnabled(true);
                starboardMiddleDownriggerLineLengthButton.setEnabled(true);
                starboardMiddleCountTextbox.setEnabled(true);
                starboardMiddleDeviceCombobox.setEnabled(true);
                starboardMiddleSetCountsButton.setEnabled(true);
                starboardMiddleSetCountsTextbox.setEnabled(true);
                starboardMiddleTensionButton.setEnabled(true);
                starboardMiddleTensionBar.setEnabled(true);
                
                portMiddleDownriggerStationButton.setEnabled(true);
                portMiddleDownriggerLineLengthButton.setEnabled(true);
                portMiddleCountTextbox.setEnabled(true);
                portMiddleDeviceCombobox.setEnabled(true);
                portMiddleSetCountsButton.setEnabled(true);
                portMiddleSetCountsTextbox.setEnabled(true);
                portMiddleTensionButton.setEnabled(true);
                portMiddleTensionBar.setEnabled(true);
                
                starboardAftDownriggerStationButton.setEnabled(false);
                starboardAftDownriggerLineLengthButton.setEnabled(false);
                starboardAftCountTextbox.setEnabled(false);
                starboardAftDeviceCombobox.setEnabled(false);
                starboardAftSetCountsButton.setEnabled(false);
                starboardAftSetCountsTextbox.setEnabled(false);
                starboardAftTensionButton.setEnabled(false);
                starboardAftTensionBar.setEnabled(false);
                
                portAftDownriggerStationButton.setEnabled(false);
                portAftDownriggerLineLengthButton.setEnabled(false);
                portAftCountTextbox.setEnabled(false);
                portAftDeviceCombobox.setEnabled(false);
                portAftSetCountsButton.setEnabled(false);
                portAftSetCountsTextbox.setEnabled(false);
                portAftTensionButton.setEnabled(false);
                portAftTensionBar.setEnabled(false);
                break;
            }
            case "pmsmsapa":
            {
                portForwardDownriggerStationButton.setEnabled(false);
                portForwardDownriggerLineLengthButton.setEnabled(false);
                portForwardCountTextbox.setEnabled(false);
                portForwardDeviceCombobox.setEnabled(false);
                portForwardSetCountsButton.setEnabled(false);
                portForwardSetCountsTextbox.setEnabled(false);
                portForwardTensionButton.setEnabled(false);
                portForwardTensionBar.setEnabled(false);
                
                starboardForwardDownriggerStationButton.setEnabled(false);
                starboardForwardDownriggerLineLengthButton.setEnabled(false);
                starboardForwardCountTextbox.setEnabled(false);
                starboardForwardDeviceCombobox.setEnabled(false);
                starboardForwardSetCountsButton.setEnabled(false);
                starboardForwardSetCountsTextbox.setEnabled(false);
                starboardForwardTensionButton.setEnabled(false);
                starboardForwardTensionBar.setEnabled(false);
                
                portMiddleDownriggerStationButton.setEnabled(true);
                portMiddleDownriggerLineLengthButton.setEnabled(true);
                portMiddleCountTextbox.setEnabled(true);
                portMiddleDeviceCombobox.setEnabled(true);
                portMiddleSetCountsButton.setEnabled(true);
                portMiddleSetCountsTextbox.setEnabled(true);
                portMiddleTensionButton.setEnabled(true);
                portMiddleTensionBar.setEnabled(true);
                
                starboardMiddleDownriggerStationButton.setEnabled(true);
                starboardMiddleDownriggerLineLengthButton.setEnabled(true);
                starboardMiddleCountTextbox.setEnabled(true);
                starboardMiddleDeviceCombobox.setEnabled(true);
                starboardMiddleSetCountsButton.setEnabled(true);
                starboardMiddleSetCountsTextbox.setEnabled(true);
                starboardMiddleTensionButton.setEnabled(true);
                starboardMiddleTensionBar.setEnabled(true);
                
                starboardAftDownriggerStationButton.setEnabled(true);
                starboardAftDownriggerLineLengthButton.setEnabled(true);
                starboardAftCountTextbox.setEnabled(true);
                starboardAftDeviceCombobox.setEnabled(true);
                starboardAftSetCountsButton.setEnabled(true);
                starboardAftSetCountsTextbox.setEnabled(true);
                starboardAftTensionButton.setEnabled(true);
                starboardAftTensionBar.setEnabled(true);
                
                portAftDownriggerStationButton.setEnabled(true);
                portAftDownriggerLineLengthButton.setEnabled(true);
                portAftCountTextbox.setEnabled(true);
                portAftDeviceCombobox.setEnabled(true);
                portAftSetCountsButton.setEnabled(true);
                portAftSetCountsTextbox.setEnabled(true);
                portAftTensionButton.setEnabled(true);
                portAftTensionBar.setEnabled(true);
                break;
            }
            default:
            {
                
            }
        }
    }
    
    public void loadVesselConfigurationFile()
    {
        //
        int returnVal = fileChooser.showOpenDialog(this);
        //
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            //
            File vesselConfigurationXmlFile = fileChooser.getSelectedFile();
            //
            String pathVariableString = vesselConfigurationXmlFile.getAbsolutePath();
            //
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            //
            try 
            {
                //
                DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();
                //
                //Document document = (Document) dbuilder.parse(vesselConfigurationXmlFile);
                Document document = dbuilder.parse(vesselConfigurationXmlFile);
                //
                document.getDocumentElement().normalize();
                //Check this
                NodeList configurationFile = document.getElementsByTagName("configurationFile");
                //
                Node vesselConfigurationCode = configurationFile.item(0);
                //
                Element configurationCodeElement = (Element) vesselConfigurationCode;
                //
                float meterToCountUnitConversionFactor=(float) 5.4;
                double conversionFactor = (double) meterToCountUnitConversionFactor;
                
                //Check this ~ when variable names were changed I didn't check
                //the purpose of the string.
                switch (configurationCodeElement.getElementsByTagName("configurationCode").item(0).getTextContent()) 
                {
                    //
                    case "\"pfsmpa\"":
                    {
                        //
                        Geometry.configurationCode="pfsmpa";
                        //
                        Element port_forward_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element port_forward_distance_to_starboard_middle_element = (Element) configurationFile.item(0);
                        Element port_aft_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element port_aft_distance_to_port_forward_element = (Element) configurationFile.item(0);
                        Element starboard_middle_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element starboard_middle_distance_to_port_aft_element = (Element) configurationFile.item(0);
                        Element alongship_distance_to_port_forward_element = (Element) configurationFile.item(0);
                        Element distance_from_center_line_element = (Element) configurationFile.item(0);
                        Element transducer_depth_element = (Element) configurationFile.item(0);
                        Element depth_of_echosounder_view_centroid_element = (Element) configurationFile.item(0);
                        Element vessel_width_element = (Element) configurationFile.item(0);
                        //
                        Geometry.pfho = meterToCountUnitConversionFactor*Float.parseFloat(port_forward_distance_to_ocean_surface_element.getElementsByTagName("port_forward_distance_to_ocean_surface").item(0).getTextContent().split("\"")[1]);
                        Geometry.pfsm = meterToCountUnitConversionFactor*Float.parseFloat(port_forward_distance_to_starboard_middle_element.getElementsByTagName("port_forward_distance_to_starboard_middle").item(0).getTextContent().split("\"")[1]);
                        Geometry.paho = meterToCountUnitConversionFactor*Float.parseFloat(port_aft_distance_to_ocean_surface_element.getElementsByTagName("port_aft_distance_to_ocean_surface").item(0).getTextContent().split("\"")[1]);
                        Geometry.papf = meterToCountUnitConversionFactor*Float.parseFloat(port_aft_distance_to_port_forward_element.getElementsByTagName("port_aft_distance_to_port_forward").item(0).getTextContent().split("\"")[1]);
                        Geometry.smho = meterToCountUnitConversionFactor*Float.parseFloat(starboard_middle_distance_to_ocean_surface_element.getElementsByTagName("starboard_middle_distance_to_ocean_surface").item(0).getTextContent().split("\"")[1]);
                        Geometry.smpa = meterToCountUnitConversionFactor*Float.parseFloat(starboard_middle_distance_to_port_aft_element.getElementsByTagName("starboard_middle_distance_to_port_aft").item(0).getTextContent().split("\"")[1]);
                        Geometry.pfad = meterToCountUnitConversionFactor*Float.parseFloat(alongship_distance_to_port_forward_element.getElementsByTagName("alongship_distance_to_port_forward").item(0).getTextContent().split("\"")[1]);
                        Geometry.dfcl = meterToCountUnitConversionFactor*Float.parseFloat(distance_from_center_line_element.getElementsByTagName("distance_from_center_line").item(0).getTextContent().split("\"")[1]);
                        Geometry.td = meterToCountUnitConversionFactor*Float.parseFloat(transducer_depth_element.getElementsByTagName("transducer_depth").item(0).getTextContent().split("\"")[1]);
                        Geometry.esd = meterToCountUnitConversionFactor*Float.parseFloat(depth_of_echosounder_view_centroid_element.getElementsByTagName("depth_of_echosounder_view_centroid").item(0).getTextContent().split("\"")[1]);
                        Geometry.vw = meterToCountUnitConversionFactor*Float.parseFloat(vessel_width_element.getElementsByTagName( "vessel_width" ).item(0).getTextContent().split("\"")[1]);
                        
                        portForwardHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.pfho/conversionFactor*10.0)/(float)10.0));
                        portMiddleHeightFromOceanTextbox.setText("Not Applicable");
                        portAftHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.paho/conversionFactor*10.0)/(float)10.0));
                        starboardForwardHeightFromOceanTextbox.setText("Not Applicable");
                        starboardMiddleHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.smho/conversionFactor*10.0)/(float)10.0));
                        starboardAftHeightFromOceanTextbox.setText("Not Applicable");
                        
                        firstRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.pfsm/conversionFactor*10.0)/(float)10.0));
                        secondRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.smpa/conversionFactor*10.0)/(float)10.0));
                        thirdRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.papf/conversionFactor*10.0)/(float)10.0));
                        fourthRelativeDistanceInputTextfield.setText("Not Applicable");
                        
                        vesselWidthTextbox.setText(Float.toString(Math.round(Geometry.vw/conversionFactor*10.0)/(float)10.0));
                        transducerDepthTextbox.setText(Float.toString(Math.round(Geometry.td/conversionFactor*10.0)/(float)10.0));
                        targetDepthTextbox.setText(Float.toString(Math.round(Geometry.esd/conversionFactor*10.0)/(float)10.0));
                        transducerYDistanceTextbox.setText(Float.toString(Math.round(Geometry.pfad/conversionFactor*10.0)/(float)10.0));
                        transducerXOffsetTextbox.setText(Float.toString(Math.round(Geometry.dfcl/conversionFactor*10.0)/(float)10.0));
                        //
                        configurationTypeCombobox.setSelectedItem("PF-SM-PA");
                        //
                        Geometry.generateCoordinatesForThreeDownriggers("pfsmpa");
                        //
                        CoordinateDisplayTopComponent.configDiagram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/PFSMPAConfigDiagram.png")));
                        configureDownriggerStationControls(Geometry.configurationCode);
                        //
                        Terminal.systemLogMessage("A port forward - starboard middle - port aft configuration has been set for this calibration session.", CoordinateDisplayTopComponent.positionUILogger);
                        //
                        break;
                    }
                    //
                    case "\"pmsfsa\"":
                    {
                        //
                        Geometry.configurationCode="pmsfsa";
                        //
                        Element port_middle_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element port_middle_distance_to_starboard_forward_element = (Element) configurationFile.item(0);
                        Element starboard_forward_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element starboard_forward_distance_to_starboard_aft_element = (Element) configurationFile.item(0);
                        Element starboard_aft_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element starboard_aft_distance_to_port_middle_element = (Element) configurationFile.item(0);
                        Element alongship_distance_to_starboard_forward_element = (Element) configurationFile.item(0);
                        Element distance_from_center_line_element = (Element) configurationFile.item(0);
                        Element transducer_depth_element = (Element) configurationFile.item(0);
                        Element depth_of_echosounder_view_centroid_element = (Element) configurationFile.item(0);
                        Element vessel_width_element = (Element) configurationFile.item(0);
                        //    
                        Geometry.pmho = meterToCountUnitConversionFactor*Float.parseFloat(port_middle_distance_to_ocean_surface_element.getElementsByTagName( "port_middle_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.pmsf = meterToCountUnitConversionFactor*Float.parseFloat(port_middle_distance_to_starboard_forward_element.getElementsByTagName( "port_middle_distance_to_starboard_forward" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.sfho = meterToCountUnitConversionFactor*Float.parseFloat(starboard_forward_distance_to_ocean_surface_element.getElementsByTagName( "starboard_forward_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.sfsa = meterToCountUnitConversionFactor*Float.parseFloat(starboard_forward_distance_to_starboard_aft_element.getElementsByTagName( "starboard_forward_distance_to_starboard_aft" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.saho = meterToCountUnitConversionFactor*Float.parseFloat(starboard_aft_distance_to_ocean_surface_element.getElementsByTagName( "starboard_aft_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.sapm = meterToCountUnitConversionFactor*Float.parseFloat(starboard_aft_distance_to_port_middle_element.getElementsByTagName( "starboard_aft_distance_to_port_middle" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.sfad = meterToCountUnitConversionFactor*Float.parseFloat(alongship_distance_to_starboard_forward_element.getElementsByTagName( "alongship_distance_to_starboard_forward" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.dfcl = meterToCountUnitConversionFactor*Float.parseFloat(distance_from_center_line_element.getElementsByTagName( "distance_from_center_line" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.td = meterToCountUnitConversionFactor*Float.parseFloat(transducer_depth_element.getElementsByTagName( "transducer_depth" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.esd = meterToCountUnitConversionFactor*Float.parseFloat(depth_of_echosounder_view_centroid_element.getElementsByTagName( "depth_of_echosounder_view_centroid" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.vw = meterToCountUnitConversionFactor*Float.parseFloat(vessel_width_element.getElementsByTagName( "vessel_width" ).item(0).getTextContent().split("\"")[1]);
                        

                        portForwardHeightFromOceanTextbox.setText("Not Applicable");
                        portMiddleHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.pmho/conversionFactor*10.0)/(float)10.0));
                        portAftHeightFromOceanTextbox.setText("Not Applicable");
                        starboardForwardHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.sfho/conversionFactor*10.0)/(float)10.0));
                        starboardMiddleHeightFromOceanTextbox.setText("Not Applicable");
                        starboardAftHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.saho/conversionFactor*10.0)/(float)10.0));
                        
                        firstRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.pmsf/conversionFactor*10.0)/(float)10.0));
                        secondRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.sfsa/conversionFactor*10.0)/(float)10.0));
                        thirdRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.sapm/conversionFactor*10.0)/(float)10.0));
                        fourthRelativeDistanceInputTextfield.setText("Not Applicable");
                        
                        vesselWidthTextbox.setText(Float.toString(Math.round(Geometry.vw/conversionFactor*10.0)/(float)10.0));
                        transducerDepthTextbox.setText(Float.toString(Math.round(Geometry.td/conversionFactor*10.0)/(float)10.0));
                        targetDepthTextbox.setText(Float.toString(Math.round(Geometry.esd/conversionFactor*10.0)/(float)10.0));
                        transducerYDistanceTextbox.setText(Float.toString(Math.round(Geometry.sfad/conversionFactor*10.0)/(float)10.0));
                        transducerXOffsetTextbox.setText(Float.toString(Math.round(Geometry.dfcl/conversionFactor*10.0)/(float)10.0));
                        
                        configurationTypeCombobox.setSelectedItem("PM-SF-SA");
                        //
                        Geometry.generateCoordinatesForThreeDownriggers("pmsfsa");
                        //
                        CoordinateDisplayTopComponent.configDiagram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/PMSFSAConfigDiagram.png")));
                        configureDownriggerStationControls(Geometry.configurationCode);
                        //
                        Terminal.systemLogMessage("A port middle - starboard forward - starboard aft configuration has been set for this calibration session.", CoordinateDisplayTopComponent.positionUILogger);
                        break;
                    }
                    //
                    case "\"pmsmsapa\"":
                    {
                        //
                        Geometry.configurationCode="pmsmsapa";
                        //
                        Element port_middle_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element port_middle_distance_to_starboard_middle_element = (Element) configurationFile.item(0);
                        Element port_aft_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element port_aft_distance_to_port_middle_element = (Element) configurationFile.item(0);
                        Element starboard_middle_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element starboard_middle_distance_to_starboard_aft_element = (Element) configurationFile.item(0);
                        Element starboard_aft_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element starboard_aft_distance_to_port_aft_element = (Element) configurationFile.item(0);
                        Element alongship_distance_to_port_middle_element = (Element) configurationFile.item(0);
                        Element distance_from_center_line_element = (Element) configurationFile.item(0);
                        Element transducer_depth_element = (Element) configurationFile.item(0);
                        Element depth_of_echosounder_view_centroid_element = (Element) configurationFile.item(0);
                        //   
                        Geometry.pmho = meterToCountUnitConversionFactor*Float.parseFloat( port_middle_distance_to_ocean_surface_element.getElementsByTagName( "port_middle_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.pmsm = meterToCountUnitConversionFactor*Float.parseFloat( port_middle_distance_to_starboard_middle_element.getElementsByTagName( "port_middle_distance_to_starboard_middle" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.paho = meterToCountUnitConversionFactor*Float.parseFloat( port_aft_distance_to_ocean_surface_element.getElementsByTagName( "port_aft_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.papm = meterToCountUnitConversionFactor*Float.parseFloat( port_aft_distance_to_port_middle_element.getElementsByTagName( "port_aft_distance_to_port_middle" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.smho = meterToCountUnitConversionFactor*Float.parseFloat( starboard_middle_distance_to_ocean_surface_element.getElementsByTagName( "starboard_middle_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.smsa = meterToCountUnitConversionFactor*Float.parseFloat( starboard_middle_distance_to_starboard_aft_element.getElementsByTagName( "starboard_middle_distance_to_starboard_aft" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.saho = meterToCountUnitConversionFactor*Float.parseFloat( starboard_aft_distance_to_ocean_surface_element.getElementsByTagName( "starboard_aft_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.sapa = meterToCountUnitConversionFactor*Float.parseFloat( starboard_aft_distance_to_port_aft_element.getElementsByTagName( "starboard_aft_distance_to_port_aft" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.pmad = meterToCountUnitConversionFactor*Float.parseFloat( alongship_distance_to_port_middle_element.getElementsByTagName( "alongship_distance_to_port_middle" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.dfcl = meterToCountUnitConversionFactor*Float.parseFloat( distance_from_center_line_element.getElementsByTagName( "distance_from_center_line" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.td = meterToCountUnitConversionFactor*Float.parseFloat( transducer_depth_element.getElementsByTagName( "transducer_depth" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.esd = meterToCountUnitConversionFactor*Float.parseFloat( depth_of_echosounder_view_centroid_element.getElementsByTagName( "depth_of_echosounder_view_centroid" ).item(0).getTextContent().split("\"")[1]);
                        
                        portForwardHeightFromOceanTextbox.setText("Not Applicable");
                        portMiddleHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.pmho/conversionFactor*10.0)/(float)10.0));
                        portAftHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.paho/conversionFactor*10.0)/(float)10.0));
                        starboardForwardHeightFromOceanTextbox.setText("Not Applicable");
                        starboardMiddleHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.smho/conversionFactor*10.0)/(float)10.0));
                        starboardAftHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.saho/conversionFactor*10.0)/(float)10.0));
                        
                        firstRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.pmsm/conversionFactor*10.0)/(float)10.0));
                        secondRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.smsa/conversionFactor*10.0)/(float)10.0));
                        thirdRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.sapa/conversionFactor*10.0)/(float)10.0));
                        fourthRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.papm/conversionFactor*10.0)/(float)10.0));
                        
                        vesselWidthTextbox.setText("Not Applicable");
                        transducerDepthTextbox.setText(Float.toString(Math.round(Geometry.td/conversionFactor*10.0)/(float)10.0));
                        targetDepthTextbox.setText(Float.toString(Math.round(Geometry.esd/conversionFactor*10.0)/(float)10.0));
                        transducerYDistanceTextbox.setText(Float.toString(Math.round(Geometry.pfad/conversionFactor*10.0)/(float)10.0));
                        transducerXOffsetTextbox.setText(Float.toString(Math.round(Geometry.dfcl/conversionFactor*10.0)/(float)10.0));
                        
                        configurationTypeCombobox.setSelectedItem("PM-SM-SA-PA");
                        
                        Geometry.generateCoordinatesForFourDownriggers("pmsmsapa");
                        //
                        CoordinateDisplayTopComponent.configDiagram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/PMSMSAPAConfigDiagram.png")));
                        configureDownriggerStationControls(Geometry.configurationCode);
                        Terminal.systemLogMessage("A port middle - starboard middle - starboard aft - port aft configuration has been set for this calibration session.", CoordinateDisplayTopComponent.positionUILogger);
                        break;
                    }
                    //
                    case "\"pfsfsmpm\"":
                    {
                        //
                        Geometry.configurationCode="pfsfsmpm";
                        //
                        Element port_forward_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element port_forward_distance_to_starboard_forward_element = (Element) configurationFile.item(0);
                        Element port_middle_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element port_middle_distance_to_port_forward_element = (Element) configurationFile.item(0);
                        Element starboard_forward_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element starboard_forward_distance_to_starboard_middle_element = (Element) configurationFile.item(0);
                        Element starboard_middle_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element starboard_middle_distance_to_port_middle_element = (Element) configurationFile.item(0);
                        Element alongship_distance_to_port_forward_element = (Element) configurationFile.item(0);
                        Element distance_from_center_line_element = (Element) configurationFile.item(0);
                        Element transducer_depth_element = (Element) configurationFile.item(0);
                        Element depth_of_echosounder_view_centroid_element = (Element) configurationFile.item(0);
                        //
                        Geometry.pfho = meterToCountUnitConversionFactor*Float.parseFloat( port_forward_distance_to_ocean_surface_element.getElementsByTagName( "port_forward_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.pfsf = meterToCountUnitConversionFactor*Float.parseFloat( port_forward_distance_to_starboard_forward_element.getElementsByTagName( "port_forward_distance_to_starboard_forward" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.pmho = meterToCountUnitConversionFactor*Float.parseFloat( port_middle_distance_to_ocean_surface_element.getElementsByTagName( "port_middle_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.pmpf = meterToCountUnitConversionFactor*Float.parseFloat( port_middle_distance_to_port_forward_element.getElementsByTagName( "port_middle_distance_to_port_forward" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.sfho = meterToCountUnitConversionFactor*Float.parseFloat( starboard_forward_distance_to_ocean_surface_element.getElementsByTagName( "starboard_forward_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.sfsm = meterToCountUnitConversionFactor*Float.parseFloat( starboard_forward_distance_to_starboard_middle_element.getElementsByTagName( "starboard_forward_distance_to_starboard_middle" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.smho = meterToCountUnitConversionFactor*Float.parseFloat( starboard_middle_distance_to_ocean_surface_element.getElementsByTagName( "starboard_middle_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.smpm = meterToCountUnitConversionFactor*Float.parseFloat( starboard_middle_distance_to_port_middle_element.getElementsByTagName( "starboard_middle_distance_to_port_middle" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.pfad = meterToCountUnitConversionFactor*Float.parseFloat( alongship_distance_to_port_forward_element.getElementsByTagName( "alongship_distance_to_port_forward" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.dfcl = meterToCountUnitConversionFactor*Float.parseFloat( distance_from_center_line_element.getElementsByTagName( "distance_from_center_line" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.td = meterToCountUnitConversionFactor*Float.parseFloat( transducer_depth_element.getElementsByTagName( "transducer_depth" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.esd = meterToCountUnitConversionFactor*Float.parseFloat( depth_of_echosounder_view_centroid_element.getElementsByTagName( "depth_of_echosounder_view_centroid" ).item(0).getTextContent().split("\"")[1]);
                        
                        portForwardHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.pfho/conversionFactor*10.0)/(float)10.0));
                        portMiddleHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.pmho/conversionFactor*10.0)/(float)10.0));
                        portAftHeightFromOceanTextbox.setText("Not Applicable");
                        starboardForwardHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.sfho/conversionFactor*10.0)/(float)10.0));
                        starboardMiddleHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.smho/conversionFactor*10.0)/(float)10.0));
                        starboardAftHeightFromOceanTextbox.setText("Not Applicable");
                        
                        firstRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.pfsf/conversionFactor*10.0)/(float)10.0));
                        secondRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.sfsm/conversionFactor*10.0)/(float)10.0));
                        thirdRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.smpm/conversionFactor*10.0)/(float)10.0));
                        fourthRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.pmpf/conversionFactor*10.0)/(float)10.0));
                        
                        vesselWidthTextbox.setText("Not Applicable");
                        transducerDepthTextbox.setText(Float.toString(Math.round(Geometry.td/conversionFactor*10.0)/(float)10.0));
                        targetDepthTextbox.setText(Float.toString(Math.round(Geometry.esd/conversionFactor*10.0)/(float)10.0));
                        transducerYDistanceTextbox.setText(Float.toString(Math.round(Geometry.pfad/conversionFactor*10.0)/(float)10.0));
                        transducerXOffsetTextbox.setText(Float.toString(Math.round(Geometry.dfcl/conversionFactor*10.0)/(float)10.0));
                        
                        configurationTypeCombobox.setSelectedItem("PF-SF-SM-PM");

                        Geometry.generateCoordinatesForFourDownriggers("pfsfsmpm");
                        //
                        CoordinateDisplayTopComponent.configDiagram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/PFSFSMPMConfigDiagram.png")));
                        configureDownriggerStationControls(Geometry.configurationCode);
                        //
                        Terminal.systemLogMessage("A port forward - starboard forward - starboard middle - port middle configuration has been set for this calibration session.", CoordinateDisplayTopComponent.positionUILogger);
                        break;
                    }
                    
                    case "\"pfsfsapa\"":
                    {
                        //
                        Geometry.configurationCode="pfsfsapa";
                        //
                        Element port_forward_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element port_forward_distance_to_starboard_forward_element = (Element) configurationFile.item(0);
                        Element port_aft_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element port_aft_distance_to_port_forward_element = (Element) configurationFile.item(0);
                        Element starboard_forward_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element starboard_forward_distance_to_starboard_aft_element = (Element) configurationFile.item(0);
                        Element starboard_aft_distance_to_ocean_surface_element = (Element) configurationFile.item(0);
                        Element starboard_aft_distance_to_port_aft_element = (Element) configurationFile.item(0);
                        Element alongship_distance_to_port_forward_element = (Element) configurationFile.item(0);
                        Element distance_from_center_line_element = (Element) configurationFile.item(0);
                        Element transducer_depth_element = (Element) configurationFile.item(0);
                        Element depth_of_echosounder_view_centroid_element = (Element) configurationFile.item(0);
                        //
                        Geometry.pfho = meterToCountUnitConversionFactor*Float.parseFloat( port_forward_distance_to_ocean_surface_element.getElementsByTagName( "port_forward_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.pfsf = meterToCountUnitConversionFactor*Float.parseFloat( port_forward_distance_to_starboard_forward_element.getElementsByTagName( "port_forward_distance_to_starboard_forward" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.paho = meterToCountUnitConversionFactor*Float.parseFloat( port_aft_distance_to_ocean_surface_element.getElementsByTagName( "port_aft_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.papf = meterToCountUnitConversionFactor*Float.parseFloat( port_aft_distance_to_port_forward_element.getElementsByTagName( "port_aft_distance_to_port_forward" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.sfho = meterToCountUnitConversionFactor*Float.parseFloat( starboard_forward_distance_to_ocean_surface_element.getElementsByTagName( "starboard_forward_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.sfsa = meterToCountUnitConversionFactor*Float.parseFloat( starboard_forward_distance_to_starboard_aft_element.getElementsByTagName( "starboard_forward_distance_to_starboard_aft" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.saho = meterToCountUnitConversionFactor*Float.parseFloat( starboard_aft_distance_to_ocean_surface_element.getElementsByTagName( "starboard_aft_distance_to_ocean_surface" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.sapa = meterToCountUnitConversionFactor*Float.parseFloat( starboard_aft_distance_to_port_aft_element.getElementsByTagName( "starboard_aft_distance_to_port_aft" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.pfad = meterToCountUnitConversionFactor*Float.parseFloat( alongship_distance_to_port_forward_element.getElementsByTagName( "alongship_distance_to_port_forward" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.dfcl = meterToCountUnitConversionFactor*Float.parseFloat( distance_from_center_line_element.getElementsByTagName( "distance_from_center_line" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.td = meterToCountUnitConversionFactor*Float.parseFloat( transducer_depth_element.getElementsByTagName( "transducer_depth" ).item(0).getTextContent().split("\"")[1]);
                        Geometry.esd = meterToCountUnitConversionFactor*Float.parseFloat( depth_of_echosounder_view_centroid_element.getElementsByTagName( "depth_of_echosounder_view_centroid" ).item(0).getTextContent().split("\"")[1]);
                        
                        portForwardHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.pfho/conversionFactor*10.0)/(float)10.0));
                        portMiddleHeightFromOceanTextbox.setText("Not Applicable");
                        portAftHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.paho/conversionFactor*10.0)/(float)10.0));
                        starboardForwardHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.sfho/conversionFactor*10.0)/(float)10.0));
                        starboardMiddleHeightFromOceanTextbox.setText("Not Applicable");
                        starboardAftHeightFromOceanTextbox.setText(Float.toString(Math.round(Geometry.saho/conversionFactor*10.0)/(float)10.0));
                        
                        firstRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.pfsf/conversionFactor*10.0)/(float)10.0));
                        secondRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.sfsa/conversionFactor*10.0)/(float)10.0));
                        thirdRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.sapa/conversionFactor*10.0)/(float)10.0));
                        fourthRelativeDistanceInputTextfield.setText(Float.toString(Math.round(Geometry.papf/conversionFactor*10.0)/(float)10.0));
                        
                        vesselWidthTextbox.setText("Not Applicable");
                        transducerDepthTextbox.setText(Float.toString(Math.round(Geometry.td/conversionFactor*10.0)/(float)10.0));
                        targetDepthTextbox.setText(Float.toString(Math.round(Geometry.esd/conversionFactor*10.0)/(float)10.0));
                        transducerYDistanceTextbox.setText(Float.toString(Math.round(Geometry.pfad/conversionFactor*10.0)/(float)10.0));
                        transducerXOffsetTextbox.setText(Float.toString(Math.round(Geometry.dfcl/conversionFactor*10.0)/(float)10.0));
                        
                        configurationTypeCombobox.setSelectedItem("PF-SF-SA-PA");

                        Geometry.generateCoordinatesForFourDownriggers("pfsfsapa");
                        //
                        CoordinateDisplayTopComponent.configDiagram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/PFSFSAPAConfigDiagram.png")));
                        configureDownriggerStationControls(Geometry.configurationCode);
                        //
                        Terminal.systemLogMessage("A port forward - starboard forward - starboard aft - port aft configuration has been set for this calibration session.", CoordinateDisplayTopComponent.positionUILogger);
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                configurationPathLabelButton.setText("Configuation File: "+pathVariableString);
            } 
            catch (SAXException | IOException | ParserConfigurationException | NullPointerException | ArrayIndexOutOfBoundsException ex) 
            {
                //Exceptions.printStackTrace(ex);
                Terminal.systemLogMessage("The vessel configuration file has not been read properly.", CoordinateDisplayTopComponent.positionUILogger);
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "The vessel config file failed to load.\n"
                                                    +"Make sure the 'Vessel Configuration Tool' window is open..");
            }
            //configurationPathLabelButton.setText("Configuation File: "+pathVariableString);
        } 
        else 
        {
            //Pass.   
        }
    }
    
    public static void unassignSerialNumber(String serialNumber)
    {
        serialNumber = "";
    }
    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        downriggerPanel = new javax.swing.JPanel();
        portForwardDownriggerStationButton = new javax.swing.JButton();
        portForwardDeviceCombobox = new javax.swing.JComboBox();
        portForwardDownriggerLineLengthButton = new javax.swing.JButton();
        portForwardTensionButton = new javax.swing.JButton();
        portForwardCountTextbox = new javax.swing.JTextField();
        portForwardTensionBar = new javax.swing.JProgressBar();
        portForwardSetCountsButton = new javax.swing.JButton();
        portForwardSetCountsTextbox = new javax.swing.JTextField();
        starboardForwardDownriggerStationButton = new javax.swing.JButton();
        starboardForwardDeviceCombobox = new javax.swing.JComboBox();
        starboardForwardTensionButton = new javax.swing.JButton();
        starboardForwardTensionBar = new javax.swing.JProgressBar();
        starboardForwardSetCountsButton = new javax.swing.JButton();
        starboardForwardDownriggerLineLengthButton = new javax.swing.JButton();
        starboardForwardCountTextbox = new javax.swing.JTextField();
        starboardForwardSetCountsTextbox = new javax.swing.JTextField();
        starboardMiddleTensionBar = new javax.swing.JProgressBar();
        starboardMiddleSetCountsTextbox = new javax.swing.JTextField();
        starboardMiddleCountTextbox = new javax.swing.JTextField();
        starboardMiddleDownriggerLineLengthButton = new javax.swing.JButton();
        starboardMiddleSetCountsButton = new javax.swing.JButton();
        starboardMiddleDeviceCombobox = new javax.swing.JComboBox();
        starboardMiddleDownriggerStationButton = new javax.swing.JButton();
        starboardMiddleTensionButton = new javax.swing.JButton();
        portMiddleTensionBar = new javax.swing.JProgressBar();
        portMiddleSetCountsTextbox = new javax.swing.JTextField();
        portMiddleCountTextbox = new javax.swing.JTextField();
        portMiddleDownriggerLineLengthButton = new javax.swing.JButton();
        portMiddleSetCountsButton = new javax.swing.JButton();
        portMiddleDeviceCombobox = new javax.swing.JComboBox();
        portMiddleDownriggerStationButton = new javax.swing.JButton();
        portMiddleTensionButton = new javax.swing.JButton();
        starboardAftTensionBar = new javax.swing.JProgressBar();
        starboardAftSetCountsTextbox = new javax.swing.JTextField();
        starboardAftCountTextbox = new javax.swing.JTextField();
        starboardAftDownriggerLineLengthButton = new javax.swing.JButton();
        starboardAftSetCountsButton = new javax.swing.JButton();
        starboardAftDeviceCombobox = new javax.swing.JComboBox();
        starboardAftDownriggerStationButton = new javax.swing.JButton();
        starboardAftTensionButton = new javax.swing.JButton();
        portAftTensionBar = new javax.swing.JProgressBar();
        portAftSetCountsTextbox = new javax.swing.JTextField();
        portAftCountTextbox = new javax.swing.JTextField();
        portAftDownriggerLineLengthButton = new javax.swing.JButton();
        portAftSetCountsButton = new javax.swing.JButton();
        portAftDeviceCombobox = new javax.swing.JComboBox();
        portAftDownriggerStationButton = new javax.swing.JButton();
        portAftTensionButton = new javax.swing.JButton();
        configDiagram = new javax.swing.JLabel();
        positionPanel = new javax.swing.JPanel();
        transducerPositionButton = new javax.swing.JButton();
        spherePositionButton = new javax.swing.JButton();
        transducerPositionXTextbox = new javax.swing.JTextField();
        transducerPositionYTextbox = new javax.swing.JTextField();
        transducerPositionZTextbox = new javax.swing.JTextField();
        targetPositionButton = new javax.swing.JButton();
        spherePositionXTextbox = new javax.swing.JTextField();
        spherePositionYTextbox = new javax.swing.JTextField();
        spherePositionZTextbox = new javax.swing.JTextField();
        targetPositionXTextbox = new javax.swing.JTextField();
        targetPositionYTextbox = new javax.swing.JTextField();
        targetPositionZTextbox = new javax.swing.JTextField();
        loggerPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        positionUILogger = new javax.swing.JTextArea();
        clearPositionUILoggerButton = new javax.swing.JButton();
        loadConfigurationButton = new javax.swing.JButton();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        downriggerPanel.setVerifyInputWhenFocusTarget(false);

        org.openide.awt.Mnemonics.setLocalizedText(portForwardDownriggerStationButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portForwardDownriggerStationButton.text")); // NOI18N
        portForwardDownriggerStationButton.setEnabled(false);

        portForwardDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select device..." }));
        portForwardDeviceCombobox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        portForwardDeviceCombobox.setEnabled(false);
        portForwardDeviceCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portForwardDeviceComboboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(portForwardDownriggerLineLengthButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portForwardDownriggerLineLengthButton.text")); // NOI18N
        portForwardDownriggerLineLengthButton.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(portForwardTensionButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portForwardTensionButton.text")); // NOI18N
        portForwardTensionButton.setEnabled(false);

        portForwardCountTextbox.setEditable(false);
        portForwardCountTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portForwardCountTextbox.text")); // NOI18N
        portForwardCountTextbox.setEnabled(false);

        portForwardTensionBar.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(portForwardSetCountsButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portForwardSetCountsButton.text")); // NOI18N
        portForwardSetCountsButton.setEnabled(false);
        portForwardSetCountsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portForwardSetCountsButtonActionPerformed(evt);
            }
        });

        portForwardSetCountsTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portForwardSetCountsTextbox.text")); // NOI18N
        portForwardSetCountsTextbox.setEnabled(false);
        portForwardSetCountsTextbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portForwardSetCountsTextboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(starboardForwardDownriggerStationButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardForwardDownriggerStationButton.text")); // NOI18N
        starboardForwardDownriggerStationButton.setEnabled(false);

        starboardForwardDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select device..." }));
        starboardForwardDeviceCombobox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        starboardForwardDeviceCombobox.setEnabled(false);
        starboardForwardDeviceCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starboardForwardDeviceComboboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(starboardForwardTensionButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardForwardTensionButton.text")); // NOI18N
        starboardForwardTensionButton.setEnabled(false);

        starboardForwardTensionBar.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(starboardForwardSetCountsButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardForwardSetCountsButton.text")); // NOI18N
        starboardForwardSetCountsButton.setEnabled(false);
        starboardForwardSetCountsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starboardForwardSetCountsButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(starboardForwardDownriggerLineLengthButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardForwardDownriggerLineLengthButton.text")); // NOI18N
        starboardForwardDownriggerLineLengthButton.setEnabled(false);

        starboardForwardCountTextbox.setEditable(false);
        starboardForwardCountTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardForwardCountTextbox.text")); // NOI18N
        starboardForwardCountTextbox.setEnabled(false);

        starboardForwardSetCountsTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardForwardSetCountsTextbox.text")); // NOI18N
        starboardForwardSetCountsTextbox.setEnabled(false);
        starboardForwardSetCountsTextbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starboardForwardSetCountsTextboxActionPerformed(evt);
            }
        });

        starboardMiddleTensionBar.setEnabled(false);

        starboardMiddleSetCountsTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardMiddleSetCountsTextbox.text")); // NOI18N
        starboardMiddleSetCountsTextbox.setEnabled(false);
        starboardMiddleSetCountsTextbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starboardMiddleSetCountsTextboxActionPerformed(evt);
            }
        });

        starboardMiddleCountTextbox.setEditable(false);
        starboardMiddleCountTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardMiddleCountTextbox.text")); // NOI18N
        starboardMiddleCountTextbox.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(starboardMiddleDownriggerLineLengthButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardMiddleDownriggerLineLengthButton.text")); // NOI18N
        starboardMiddleDownriggerLineLengthButton.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(starboardMiddleSetCountsButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardMiddleSetCountsButton.text")); // NOI18N
        starboardMiddleSetCountsButton.setEnabled(false);
        starboardMiddleSetCountsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starboardMiddleSetCountsButtonActionPerformed(evt);
            }
        });

        starboardMiddleDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select device..." }));
        starboardMiddleDeviceCombobox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        starboardMiddleDeviceCombobox.setEnabled(false);
        starboardMiddleDeviceCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starboardMiddleDeviceComboboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(starboardMiddleDownriggerStationButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardMiddleDownriggerStationButton.text")); // NOI18N
        starboardMiddleDownriggerStationButton.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(starboardMiddleTensionButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardMiddleTensionButton.text")); // NOI18N
        starboardMiddleTensionButton.setEnabled(false);

        portMiddleTensionBar.setEnabled(false);

        portMiddleSetCountsTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portMiddleSetCountsTextbox.text")); // NOI18N
        portMiddleSetCountsTextbox.setEnabled(false);
        portMiddleSetCountsTextbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portMiddleSetCountsTextboxActionPerformed(evt);
            }
        });

        portMiddleCountTextbox.setEditable(false);
        portMiddleCountTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portMiddleCountTextbox.text")); // NOI18N
        portMiddleCountTextbox.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(portMiddleDownriggerLineLengthButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portMiddleDownriggerLineLengthButton.text")); // NOI18N
        portMiddleDownriggerLineLengthButton.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(portMiddleSetCountsButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portMiddleSetCountsButton.text")); // NOI18N
        portMiddleSetCountsButton.setEnabled(false);
        portMiddleSetCountsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portMiddleSetCountsButtonActionPerformed(evt);
            }
        });

        portMiddleDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select device..." }));
        portMiddleDeviceCombobox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        portMiddleDeviceCombobox.setEnabled(false);
        portMiddleDeviceCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portMiddleDeviceComboboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(portMiddleDownriggerStationButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portMiddleDownriggerStationButton.text")); // NOI18N
        portMiddleDownriggerStationButton.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(portMiddleTensionButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portMiddleTensionButton.text")); // NOI18N
        portMiddleTensionButton.setEnabled(false);

        starboardAftTensionBar.setEnabled(false);

        starboardAftSetCountsTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardAftSetCountsTextbox.text")); // NOI18N
        starboardAftSetCountsTextbox.setEnabled(false);
        starboardAftSetCountsTextbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starboardAftSetCountsTextboxActionPerformed(evt);
            }
        });

        starboardAftCountTextbox.setEditable(false);
        starboardAftCountTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardAftCountTextbox.text")); // NOI18N
        starboardAftCountTextbox.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(starboardAftDownriggerLineLengthButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardAftDownriggerLineLengthButton.text")); // NOI18N
        starboardAftDownriggerLineLengthButton.setEnabled(false);
        starboardAftDownriggerLineLengthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starboardAftDownriggerLineLengthButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(starboardAftSetCountsButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardAftSetCountsButton.text")); // NOI18N
        starboardAftSetCountsButton.setEnabled(false);
        starboardAftSetCountsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starboardAftSetCountsButtonActionPerformed(evt);
            }
        });

        starboardAftDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select device..." }));
        starboardAftDeviceCombobox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        starboardAftDeviceCombobox.setEnabled(false);
        starboardAftDeviceCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starboardAftDeviceComboboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(starboardAftDownriggerStationButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardAftDownriggerStationButton.text")); // NOI18N
        starboardAftDownriggerStationButton.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(starboardAftTensionButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.starboardAftTensionButton.text")); // NOI18N
        starboardAftTensionButton.setEnabled(false);

        portAftTensionBar.setEnabled(false);

        portAftSetCountsTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portAftSetCountsTextbox.text")); // NOI18N
        portAftSetCountsTextbox.setEnabled(false);
        portAftSetCountsTextbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portAftSetCountsTextboxActionPerformed(evt);
            }
        });

        portAftCountTextbox.setEditable(false);
        portAftCountTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portAftCountTextbox.text")); // NOI18N
        portAftCountTextbox.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(portAftDownriggerLineLengthButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portAftDownriggerLineLengthButton.text")); // NOI18N
        portAftDownriggerLineLengthButton.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(portAftSetCountsButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portAftSetCountsButton.text")); // NOI18N
        portAftSetCountsButton.setEnabled(false);
        portAftSetCountsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portAftSetCountsButtonActionPerformed(evt);
            }
        });

        portAftDeviceCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select device..." }));
        portAftDeviceCombobox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        portAftDeviceCombobox.setEnabled(false);
        portAftDeviceCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portAftDeviceComboboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(portAftDownriggerStationButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portAftDownriggerStationButton.text")); // NOI18N
        portAftDownriggerStationButton.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(portAftTensionButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.portAftTensionButton.text")); // NOI18N
        portAftTensionButton.setEnabled(false);

        configDiagram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/NULLConfigDiagram.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(configDiagram, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.configDiagram.text")); // NOI18N

        javax.swing.GroupLayout downriggerPanelLayout = new javax.swing.GroupLayout(downriggerPanel);
        downriggerPanel.setLayout(downriggerPanelLayout);
        downriggerPanelLayout.setHorizontalGroup(
            downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downriggerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(downriggerPanelLayout.createSequentialGroup()
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(portForwardDownriggerStationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(portForwardDeviceCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(portForwardTensionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(portForwardSetCountsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                    .addComponent(portForwardDownriggerLineLengthButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portForwardCountTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(portForwardSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(portForwardTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(downriggerPanelLayout.createSequentialGroup()
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(portMiddleDownriggerStationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(portMiddleDeviceCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(portMiddleTensionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(portMiddleSetCountsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(portMiddleDownriggerLineLengthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portMiddleCountTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(portMiddleSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(portMiddleTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(downriggerPanelLayout.createSequentialGroup()
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(portAftDownriggerStationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(portAftDeviceCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(portAftTensionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(portAftSetCountsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(portAftDownriggerLineLengthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portAftCountTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(portAftSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(portAftTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(configDiagram, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(downriggerPanelLayout.createSequentialGroup()
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(starboardForwardDownriggerStationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(starboardForwardDeviceCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(starboardForwardTensionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(starboardForwardSetCountsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(starboardForwardDownriggerLineLengthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardForwardCountTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(starboardForwardSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(starboardForwardTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(downriggerPanelLayout.createSequentialGroup()
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(starboardMiddleDownriggerStationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(starboardMiddleDeviceCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(starboardMiddleTensionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(starboardMiddleSetCountsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(starboardMiddleDownriggerLineLengthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardMiddleCountTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(starboardMiddleSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(starboardMiddleTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(downriggerPanelLayout.createSequentialGroup()
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(starboardAftDownriggerStationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(starboardAftDeviceCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(starboardAftTensionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(starboardAftSetCountsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(starboardAftDownriggerLineLengthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardAftCountTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(starboardAftSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(starboardAftTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        downriggerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {portAftDeviceCombobox, portAftDownriggerStationButton, portAftTensionButton, portForwardDeviceCombobox, portForwardDownriggerStationButton, portForwardTensionButton, portMiddleDeviceCombobox, portMiddleDownriggerStationButton, portMiddleTensionButton, starboardAftDeviceCombobox, starboardAftDownriggerStationButton, starboardAftTensionButton, starboardForwardDeviceCombobox, starboardForwardDownriggerStationButton, starboardForwardTensionButton, starboardMiddleDeviceCombobox, starboardMiddleDownriggerStationButton, starboardMiddleTensionButton});

        downriggerPanelLayout.setVerticalGroup(
            downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downriggerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(downriggerPanelLayout.createSequentialGroup()
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardForwardCountTextbox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(starboardForwardDownriggerStationButton)
                                        .addComponent(starboardForwardDownriggerLineLengthButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardForwardSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(starboardForwardDeviceCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(starboardForwardSetCountsButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardForwardTensionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(starboardForwardTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portForwardCountTextbox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(portForwardDownriggerStationButton)
                                        .addComponent(portForwardDownriggerLineLengthButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portForwardSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(portForwardDeviceCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(portForwardSetCountsButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portForwardTensionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(portForwardTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardMiddleCountTextbox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(starboardMiddleDownriggerStationButton)
                                        .addComponent(starboardMiddleDownriggerLineLengthButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardMiddleSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(starboardMiddleDeviceCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(starboardMiddleSetCountsButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardMiddleTensionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(starboardMiddleTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portMiddleCountTextbox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(portMiddleDownriggerStationButton)
                                        .addComponent(portMiddleDownriggerLineLengthButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portMiddleSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(portMiddleDeviceCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(portMiddleSetCountsButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portMiddleTensionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(portMiddleTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardAftCountTextbox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(starboardAftDownriggerStationButton)
                                        .addComponent(starboardAftDownriggerLineLengthButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardAftSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(starboardAftDeviceCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(starboardAftSetCountsButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(starboardAftTensionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(starboardAftTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(downriggerPanelLayout.createSequentialGroup()
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portAftCountTextbox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(portAftDownriggerStationButton)
                                        .addComponent(portAftDownriggerLineLengthButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portAftSetCountsTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(portAftDeviceCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(portAftSetCountsButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(downriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portAftTensionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(portAftTensionBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(configDiagram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        downriggerPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {portAftCountTextbox, portAftDeviceCombobox, portAftDownriggerLineLengthButton, portAftDownriggerStationButton, portAftSetCountsButton, portAftSetCountsTextbox, portAftTensionBar, portAftTensionButton, portForwardCountTextbox, portForwardDeviceCombobox, portForwardDownriggerLineLengthButton, portForwardDownriggerStationButton, portForwardSetCountsButton, portForwardSetCountsTextbox, portForwardTensionBar, portForwardTensionButton, portMiddleCountTextbox, portMiddleDeviceCombobox, portMiddleDownriggerLineLengthButton, portMiddleDownriggerStationButton, portMiddleSetCountsButton, portMiddleSetCountsTextbox, portMiddleTensionBar, portMiddleTensionButton, starboardAftCountTextbox, starboardAftDeviceCombobox, starboardAftDownriggerLineLengthButton, starboardAftDownriggerStationButton, starboardAftSetCountsButton, starboardAftSetCountsTextbox, starboardAftTensionBar, starboardAftTensionButton, starboardForwardCountTextbox, starboardForwardDeviceCombobox, starboardForwardDownriggerLineLengthButton, starboardForwardDownriggerStationButton, starboardForwardSetCountsButton, starboardForwardSetCountsTextbox, starboardForwardTensionBar, starboardForwardTensionButton, starboardMiddleCountTextbox, starboardMiddleDeviceCombobox, starboardMiddleDownriggerLineLengthButton, starboardMiddleDownriggerStationButton, starboardMiddleSetCountsButton, starboardMiddleSetCountsTextbox, starboardMiddleTensionBar, starboardMiddleTensionButton});

        org.openide.awt.Mnemonics.setLocalizedText(transducerPositionButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.transducerPositionButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(spherePositionButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.spherePositionButton.text")); // NOI18N

        transducerPositionXTextbox.setEditable(false);
        transducerPositionXTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.transducerPositionXTextbox.text")); // NOI18N

        transducerPositionYTextbox.setEditable(false);
        transducerPositionYTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.transducerPositionYTextbox.text")); // NOI18N

        transducerPositionZTextbox.setEditable(false);
        transducerPositionZTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.transducerPositionZTextbox.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(targetPositionButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.targetPositionButton.text")); // NOI18N
        targetPositionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetPositionButtonActionPerformed(evt);
            }
        });

        spherePositionXTextbox.setEditable(false);
        spherePositionXTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.spherePositionXTextbox.text")); // NOI18N

        spherePositionYTextbox.setEditable(false);
        spherePositionYTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.spherePositionYTextbox.text")); // NOI18N

        spherePositionZTextbox.setEditable(false);
        spherePositionZTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.spherePositionZTextbox.text")); // NOI18N

        targetPositionXTextbox.setEditable(false);
        targetPositionXTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.targetPositionXTextbox.text")); // NOI18N

        targetPositionYTextbox.setEditable(false);
        targetPositionYTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.targetPositionYTextbox.text")); // NOI18N

        targetPositionZTextbox.setEditable(false);
        targetPositionZTextbox.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.targetPositionZTextbox.text")); // NOI18N

        javax.swing.GroupLayout positionPanelLayout = new javax.swing.GroupLayout(positionPanel);
        positionPanel.setLayout(positionPanelLayout);
        positionPanelLayout.setHorizontalGroup(
            positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(positionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(positionPanelLayout.createSequentialGroup()
                        .addComponent(targetPositionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(targetPositionXTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(targetPositionYTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(targetPositionZTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, positionPanelLayout.createSequentialGroup()
                        .addComponent(transducerPositionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transducerPositionXTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transducerPositionYTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transducerPositionZTextbox, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, positionPanelLayout.createSequentialGroup()
                        .addComponent(spherePositionButton, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spherePositionXTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spherePositionYTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spherePositionZTextbox, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        positionPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {spherePositionButton, spherePositionXTextbox, spherePositionYTextbox, spherePositionZTextbox, targetPositionButton, targetPositionXTextbox, targetPositionYTextbox, targetPositionZTextbox, transducerPositionButton, transducerPositionXTextbox, transducerPositionYTextbox, transducerPositionZTextbox});

        positionPanelLayout.setVerticalGroup(
            positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(positionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(transducerPositionButton)
                    .addGroup(positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(transducerPositionXTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(transducerPositionYTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(transducerPositionZTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(targetPositionButton)
                    .addComponent(targetPositionXTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(targetPositionYTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(targetPositionZTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spherePositionButton)
                    .addComponent(spherePositionXTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spherePositionYTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spherePositionZTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        positionPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {spherePositionButton, spherePositionXTextbox, spherePositionYTextbox, spherePositionZTextbox, targetPositionButton, targetPositionXTextbox, targetPositionYTextbox, targetPositionZTextbox, transducerPositionButton, transducerPositionXTextbox, transducerPositionYTextbox, transducerPositionZTextbox});

        positionUILogger.setColumns(20);
        positionUILogger.setRows(5);
        positionUILogger.setText(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.positionUILogger.text")); // NOI18N
        positionUILogger.setWrapStyleWord(true);
        positionUILogger.setMinimumSize(new java.awt.Dimension(0, 0));
        jScrollPane1.setViewportView(positionUILogger);

        org.openide.awt.Mnemonics.setLocalizedText(clearPositionUILoggerButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.clearPositionUILoggerButton.text")); // NOI18N
        clearPositionUILoggerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearPositionUILoggerButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(loadConfigurationButton, org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.loadConfigurationButton.text")); // NOI18N
        loadConfigurationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadConfigurationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loggerPanelLayout = new javax.swing.GroupLayout(loggerPanel);
        loggerPanel.setLayout(loggerPanelLayout);
        loggerPanelLayout.setHorizontalGroup(
            loggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loggerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 998, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(loggerPanelLayout.createSequentialGroup()
                        .addComponent(clearPositionUILoggerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadConfigurationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        loggerPanelLayout.setVerticalGroup(
            loggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loggerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearPositionUILoggerButton)
                    .addComponent(loadConfigurationButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addContainerGap())
        );

        loggerPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {clearPositionUILoggerButton, loadConfigurationButton});

        clearPositionUILoggerButton.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.clearPositionUILoggerButton.AccessibleContext.accessibleName")); // NOI18N
        loadConfigurationButton.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CoordinateDisplayTopComponent.class, "CoordinateDisplayTopComponent.loadConfigurationButton.AccessibleContext.accessibleName")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(downriggerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(positionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(loggerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(downriggerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(positionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loggerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void portForwardSetCountsTextboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portForwardSetCountsTextboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portForwardSetCountsTextboxActionPerformed

    private void starboardForwardSetCountsTextboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starboardForwardSetCountsTextboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_starboardForwardSetCountsTextboxActionPerformed

    private void starboardMiddleSetCountsTextboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starboardMiddleSetCountsTextboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_starboardMiddleSetCountsTextboxActionPerformed

    private void portMiddleSetCountsTextboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portMiddleSetCountsTextboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portMiddleSetCountsTextboxActionPerformed

    private void starboardAftSetCountsTextboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starboardAftSetCountsTextboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_starboardAftSetCountsTextboxActionPerformed

    private void portAftSetCountsTextboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portAftSetCountsTextboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portAftSetCountsTextboxActionPerformed

    private void targetPositionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetPositionButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_targetPositionButtonActionPerformed

    private void clearPositionUILoggerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearPositionUILoggerButtonActionPerformed
        //Clears the position UI logger.
        positionUILogger.setText("");
    }//GEN-LAST:event_clearPositionUILoggerButtonActionPerformed

    private void portForwardSetCountsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portForwardSetCountsButtonActionPerformed
        //Get the device serial number from the combo box and append the "$" character to the front of it.
        //String serialNumber = "$" + (String)portForwardDeviceCombobox.getSelectedItem().toString();
        String serialNumber = "$" + portForwardDeviceCombobox.getSelectedItem().toString();
        
        if (serialNumber.equals("$Unassigned")) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                  "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
        }
        else {
            try 
            {
                String sCount = portForwardSetCountsTextbox.getText();
                //Convert sCount to an integer. This will throw a NumberFormatException error if the string is not a number..
                int iCount = Integer.parseInt(sCount);
                //If iCount is a number, then we can output the string sCount to the downrigger..
                Communication.setCounts(Communication.selectedCommPortTag, serialNumber, sCount);
                Component frame = null;
                //JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+"$"+(String)portForwardDeviceCombobox.getSelectedItem().toString()+" have been set to "+portForwardSetCountsTextbox.getText()+".");
                JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+serialNumber+" have been set to "+sCount+".");
                portForwardCountTextbox.setText(sCount+" counts");
            } // If the data entered in the "Counts" text box is not an integer, let user know..  
            catch (NumberFormatException ex)
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "You must enter a valid integer in the 'Set Counts' text field \n"
                                              + "before you can modify the downrigger count value..");
            }
            catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                      "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
            }
        }
    }//GEN-LAST:event_portForwardSetCountsButtonActionPerformed

    private void portMiddleSetCountsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portMiddleSetCountsButtonActionPerformed
        //Get the device serial number from the combo box and append the "$" character to the front of it.
        //String serialNumber = "$" + (String)portMiddleDeviceCombobox.getSelectedItem().toString();
        String serialNumber = "$" + portMiddleDeviceCombobox.getSelectedItem().toString();
        
        if (serialNumber.equals("$Unassigned")) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                  "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
        }
        else{
            try 
            {
                String sCount = portMiddleSetCountsTextbox.getText();
                //Convert sCount to an integer. This will throw a NumberFormatException error if the string is not a number..
                int iCount = Integer.parseInt(sCount);
                //If iCount is a number, then we can output the string sCount to the downrigger..
                Communication.setCounts(Communication.selectedCommPortTag, serialNumber, sCount);
                Component frame = null;
                //JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+"$"+(String)portMiddleDeviceCombobox.getSelectedItem().toString()+" have been set to "+portMiddleSetCountsTextbox.getText()+".");      
                JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+serialNumber+" have been set to "+sCount+".");      
                portMiddleCountTextbox.setText(sCount+" counts");
            } // If the data entered in the "Counts" text box is not an integer, let user know..  
            catch (NumberFormatException ex)
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "You must enter a valid integer in the 'Set Counts' text field \n"
                                              + "before you can modify the downrigger count value..");
            }
            catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                      "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
            }
        }
    }//GEN-LAST:event_portMiddleSetCountsButtonActionPerformed

    private void portAftSetCountsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portAftSetCountsButtonActionPerformed
        //Get the device serial number from the combo box and append the "$" character to the front of it.
        //String serialNumber = "$" + (String)portAftDeviceCombobox.getSelectedItem().toString();
        String serialNumber = "$" + portAftDeviceCombobox.getSelectedItem().toString();
        
        if (serialNumber.equals("$Unassigned")) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                  "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
        }
        else{
            try 
            {
                String sCount = portAftSetCountsTextbox.getText();
                //Convert sCount to an integer. This will throw a NumberFormatException error if the string is not a number..
                int iCount = Integer.parseInt(sCount);
                //If iCount is a number, then we can output the string sCount to the downrigger..
                Communication.setCounts(Communication.selectedCommPortTag, serialNumber, sCount);
                Component frame = null;
                //JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+"$"+(String)portAftDeviceCombobox.getSelectedItem().toString()+" have been set to "+portAftSetCountsTextbox.getText()+".");        
                JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+serialNumber+" have been set to "+sCount+".");        
                portAftCountTextbox.setText(sCount+" counts");
            } // If the data entered in the "Counts" text box is not an integer, let user know..  
            catch (NumberFormatException ex)
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "You must enter a valid integer in the 'Set Counts' text field \n"
                                              + "before you can modify the downrigger count value..");
            }
            catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                      "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
            }
        }
    }//GEN-LAST:event_portAftSetCountsButtonActionPerformed

    private void starboardForwardSetCountsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starboardForwardSetCountsButtonActionPerformed
        //Get the device serial number from the combo box and append the "$" character to the front of it.
        //String serialNumber = "$" + (String)starboardForwardDeviceCombobox.getSelectedItem().toString();
        String serialNumber = "$" + starboardForwardDeviceCombobox.getSelectedItem().toString();
        
        if (serialNumber.equals("$Unassigned")) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                  "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
        }
        else{
            try 
            {
                String sCount = starboardForwardSetCountsTextbox.getText();
                //Convert sCount to an integer. This will throw a NumberFormatException error if the string is not a number..
                int iCount = Integer.parseInt(sCount);
                //If iCount is a number, then we can output the string sCount to the downrigger..
                Communication.setCounts(Communication.selectedCommPortTag, serialNumber, sCount);
                Component frame = null;
                //JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+"$"+(String)starboardForwardDeviceCombobox.getSelectedItem().toString()+" have been set to "+starboardForwardSetCountsTextbox.getText()+".");  
                JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+serialNumber+" have been set to "+sCount+".");  
                starboardForwardCountTextbox.setText(sCount+" counts");
            } // If the data entered in the "Counts" text box is not an integer, let user know..  
            catch (NumberFormatException ex)
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "You must enter a valid integer in the 'Set Counts' text field \n"
                                              + "before you can modify the downrigger count value..");
            }
            catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                      "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
            }
        }
    }//GEN-LAST:event_starboardForwardSetCountsButtonActionPerformed

    private void starboardMiddleSetCountsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starboardMiddleSetCountsButtonActionPerformed
        //Get the device serial number from the combo box and append the "$" character to the front of it.
        //String serialNumber = "$" + (String)starboardMiddleDeviceCombobox.getSelectedItem().toString();
        String serialNumber = "$" + starboardMiddleDeviceCombobox.getSelectedItem().toString();
        
        if (serialNumber.equals("$Unassigned")) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                  "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
        }
        else{
            try 
            {
                String sCount = starboardMiddleSetCountsTextbox.getText();
                //Convert sCount to an integer. This will throw a NumberFormatException error if the string is not a number..
                int iCount = Integer.parseInt(sCount);
                //If iCount is a number, then we can output the string sCount to the downrigger..
                Communication.setCounts(Communication.selectedCommPortTag, serialNumber, sCount);
                Component frame = null;
                //JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+"$"+(String)starboardMiddleDeviceCombobox.getSelectedItem().toString()+" have been set to "+starboardMiddleSetCountsTextbox.getText()+".");   
                JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+serialNumber+" have been set to "+sCount+".");   
                starboardMiddleCountTextbox.setText(sCount+" counts");
            } // If the data entered in the "Counts" text box is not an integer, let user know..  
            catch (NumberFormatException ex)
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "You must enter a valid integer in the 'Set Counts' text field \n"
                                              + "before you can modify the downrigger count value..");
            }
            catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                      "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
            }
        }
    }//GEN-LAST:event_starboardMiddleSetCountsButtonActionPerformed

    private void starboardAftSetCountsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starboardAftSetCountsButtonActionPerformed
        //Get the device serial number from the combo box and append the "$" character to the front of it.
        //String serialNumber = "$" + (String)starboardAftDeviceCombobox.getSelectedItem().toString();
        String serialNumber = "$" + starboardAftDeviceCombobox.getSelectedItem().toString();
        
        if (serialNumber.equals("$Unassigned")) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                  "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
        }
        else{
            try 
            {
                String sCount = starboardAftSetCountsTextbox.getText();
                //Convert sCount to an integer. This will throw a NumberFormatException error if the string is not a number..
                int iCount = Integer.parseInt(sCount);
                //If iCount is a number, then we can output the string sCount to the downrigger..
                Communication.setCounts(Communication.selectedCommPortTag, serialNumber, sCount);
                Component frame = null;
                //JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+"$"+(String)starboardAftDeviceCombobox.getSelectedItem().toString()+" have been set to "+starboardAftSetCountsTextbox.getText()+".");   
                JOptionPane.showMessageDialog(frame, "The counts of downrigger: "+serialNumber+" have been set to "+sCount+".");   
                starboardAftCountTextbox.setText(sCount+" counts");
            } // If the data entered in the "Counts" text box is not an integer, let user know..  
            catch (NumberFormatException ex)
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "You must enter a valid integer in the 'Set Counts' text field \n"
                                              + "before you can modify the downrigger count value..");
            }
            catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, ("You must select a downrigger device from the device selection combobox \n"+
                                                      "and input an integer value in the 'Set counts' textfield before you can modify it's counts."));  
            }
        }
    }//GEN-LAST:event_starboardAftSetCountsButtonActionPerformed

    private void portForwardDeviceComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portForwardDeviceComboboxActionPerformed
        Communication.portForwardSerialNumber="$"+portForwardDeviceCombobox.getSelectedItem().toString();
        if(portForwardDeviceCombobox.getSelectedItem().toString().equals("Unassigned") || portForwardDeviceCombobox.getSelectedItem().toString().equals("Select device..."))
        {
            unassignSerialNumber(Communication.portForwardSerialNumber);
            NavigationControls.disableNavigationControls();
        }
        else
        {
            if(portForwardDeviceCombobox.getSelectedItem().toString().equals(starboardForwardDeviceCombobox.getSelectedItem().toString()) || portForwardDeviceCombobox.getSelectedItem().toString().equals(portMiddleDeviceCombobox.getSelectedItem().toString()) || portForwardDeviceCombobox.getSelectedItem().toString().equals(portAftDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
            }
            else if(portForwardDeviceCombobox.getSelectedItem().toString().equals(starboardMiddleDeviceCombobox.getSelectedItem().toString()) || portForwardDeviceCombobox.getSelectedItem().toString().equals(starboardAftDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
            }
            else
            {
                //Gets the counts from the selected downrigger and returns it to
                //the view.
                try 
                {
                    Communication.getCounts(Communication.selectedCommPortTag, "$"+portForwardDeviceCombobox.getSelectedItem().toString());
                    NavigationControls.enableNavigationControls();
                    CoordinateTransformThread.calibrate();
                    //(new Thread(new CoordinateTransformThread())).start();
                } 
                catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
                {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }//GEN-LAST:event_portForwardDeviceComboboxActionPerformed

    private void portMiddleDeviceComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portMiddleDeviceComboboxActionPerformed
        Communication.portMiddleSerialNumber="$"+portMiddleDeviceCombobox.getSelectedItem().toString();
        if(portMiddleDeviceCombobox.getSelectedItem().toString().equals("Unassigned") || portMiddleDeviceCombobox.getSelectedItem().toString().equals("Select device..."))
        {
            unassignSerialNumber(Communication.portMiddleSerialNumber);
            NavigationControls.disableNavigationControls();
        }
        else
        {
            if(portMiddleDeviceCombobox.getSelectedItem().toString().equals(portForwardDeviceCombobox.getSelectedItem().toString()) || portMiddleDeviceCombobox.getSelectedItem().toString().equals(starboardForwardDeviceCombobox.getSelectedItem().toString()) || portMiddleDeviceCombobox.getSelectedItem().toString().equals(portAftDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
            }
            else if(portMiddleDeviceCombobox.getSelectedItem().toString().equals(starboardMiddleDeviceCombobox.getSelectedItem().toString()) || portMiddleDeviceCombobox.getSelectedItem().toString().equals(starboardAftDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
            }
            else
            {
                //Gets the counts from the selected downrigger and returns it to
                //the view.
                try 
                {
                    Communication.getCounts(Communication.selectedCommPortTag, "$"+portMiddleDeviceCombobox.getSelectedItem().toString());
                    NavigationControls.enableNavigationControls();
                    CoordinateTransformThread.calibrate();
                    //(new Thread(new CoordinateTransformThread())).start();
                } 
                catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
                {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }//GEN-LAST:event_portMiddleDeviceComboboxActionPerformed

    private void portAftDeviceComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portAftDeviceComboboxActionPerformed
        Communication.portAftSerialNumber="$"+portAftDeviceCombobox.getSelectedItem().toString();
        if(portAftDeviceCombobox.getSelectedItem().toString().equals("Unassigned") || portAftDeviceCombobox.getSelectedItem().toString().equals("Select device..."))
        {
            unassignSerialNumber(Communication.portAftSerialNumber);
            NavigationControls.disableNavigationControls();
        }
        else
        {
            if(portAftDeviceCombobox.getSelectedItem().toString().equals(portForwardDeviceCombobox.getSelectedItem().toString()) || portAftDeviceCombobox.getSelectedItem().toString().equals(portMiddleDeviceCombobox.getSelectedItem().toString()) || portAftDeviceCombobox.getSelectedItem().toString().equals(starboardForwardDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
                
            }
            else if(portAftDeviceCombobox.getSelectedItem().toString().equals(starboardMiddleDeviceCombobox.getSelectedItem().toString()) || portAftDeviceCombobox.getSelectedItem().toString().equals(starboardAftDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
            }
            else
            {
                //Gets the counts from the selected downrigger and returns it to
                //the view.
                try 
                {
                    Communication.getCounts(Communication.selectedCommPortTag, "$"+portAftDeviceCombobox.getSelectedItem().toString());
                    NavigationControls.enableNavigationControls();
                    CoordinateTransformThread.calibrate();
                    //(new Thread(new CoordinateTransformThread())).start();
                } 
                catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
                {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }//GEN-LAST:event_portAftDeviceComboboxActionPerformed

    private void starboardForwardDeviceComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starboardForwardDeviceComboboxActionPerformed
        Communication.starboardForwardSerialNumber="$"+starboardForwardDeviceCombobox.getSelectedItem().toString();
        if(starboardForwardDeviceCombobox.getSelectedItem().toString().equals("Unassigned") || starboardForwardDeviceCombobox.getSelectedItem().toString().equals("Select device..."))
        {
            unassignSerialNumber(Communication.starboardForwardSerialNumber);
            NavigationControls.disableNavigationControls();
        }
        else
        {
            if(starboardForwardDeviceCombobox.getSelectedItem().toString().equals(portForwardDeviceCombobox.getSelectedItem().toString()) || starboardForwardDeviceCombobox.getSelectedItem().toString().equals(portMiddleDeviceCombobox.getSelectedItem().toString()) || starboardForwardDeviceCombobox.getSelectedItem().toString().equals(portAftDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
            }
            else if(starboardForwardDeviceCombobox.getSelectedItem().toString().equals(starboardMiddleDeviceCombobox.getSelectedItem().toString()) || starboardForwardDeviceCombobox.getSelectedItem().toString().equals(starboardAftDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
            }
            else
            {
                //Gets the counts from the selected downrigger and returns it to
                //the view.
                try 
                {
                    Communication.getCounts(Communication.selectedCommPortTag, "$"+starboardForwardDeviceCombobox.getSelectedItem().toString());
                    NavigationControls.enableNavigationControls();
                    CoordinateTransformThread.calibrate();
                    //(new Thread(new CoordinateTransformThread())).start();
                } 
                catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
                {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }//GEN-LAST:event_starboardForwardDeviceComboboxActionPerformed

    private void starboardMiddleDeviceComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starboardMiddleDeviceComboboxActionPerformed
        Communication.starboardMiddleSerialNumber="$"+starboardMiddleDeviceCombobox.getSelectedItem().toString();
        if(starboardMiddleDeviceCombobox.getSelectedItem().toString().equals("Unassigned") || starboardMiddleDeviceCombobox.getSelectedItem().toString().equals("Select device..."))
        {
            unassignSerialNumber(Communication.starboardMiddleSerialNumber);
            NavigationControls.disableNavigationControls();
        }
        else
        {
            if(starboardMiddleDeviceCombobox.getSelectedItem().toString().equals(portForwardDeviceCombobox.getSelectedItem().toString()) || starboardMiddleDeviceCombobox.getSelectedItem().toString().equals(portMiddleDeviceCombobox.getSelectedItem().toString()) || starboardMiddleDeviceCombobox.getSelectedItem().toString().equals(portAftDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
            }
            else if(starboardMiddleDeviceCombobox.getSelectedItem().toString().equals(starboardForwardDeviceCombobox.getSelectedItem().toString()) || starboardMiddleDeviceCombobox.getSelectedItem().toString().equals(starboardAftDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
                //CoordinateTransformThread.calibrate();
                //(new Thread(new CoordinateTransformThread())).start();
            }
            else
            {
                //Gets the counts from the selected downrigger and returns it to
                //the view.
                try 
                {
                    Communication.getCounts(Communication.selectedCommPortTag, "$"+starboardMiddleDeviceCombobox.getSelectedItem().toString());
                    NavigationControls.enableNavigationControls();
                    CoordinateTransformThread.calibrate();
                    //(new Thread(new CoordinateTransformThread())).start();
                } 
                catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
                {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }//GEN-LAST:event_starboardMiddleDeviceComboboxActionPerformed

    private void starboardAftDeviceComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starboardAftDeviceComboboxActionPerformed
        Communication.starboardAftSerialNumber="$"+starboardAftDeviceCombobox.getSelectedItem().toString();
        if(starboardAftDeviceCombobox.getSelectedItem().toString().equals("Unassigned") || starboardAftDeviceCombobox.getSelectedItem().toString().equals("Select device..."))
        {
            unassignSerialNumber(Communication.starboardAftSerialNumber);
            NavigationControls.disableNavigationControls();
        }
        else
        {
            if(starboardAftDeviceCombobox.getSelectedItem().toString().equals(portForwardDeviceCombobox.getSelectedItem().toString()) || starboardAftDeviceCombobox.getSelectedItem().toString().equals(portMiddleDeviceCombobox.getSelectedItem().toString()) || starboardAftDeviceCombobox.getSelectedItem().toString().equals(portAftDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
            }
            else if(starboardAftDeviceCombobox.getSelectedItem().toString().equals(starboardForwardDeviceCombobox.getSelectedItem().toString()) || starboardAftDeviceCombobox.getSelectedItem().toString().equals(starboardMiddleDeviceCombobox.getSelectedItem().toString()))
            {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Warning: Two devices should never be assigned to the same station!");
                NavigationControls.disableNavigationControls();
            }
            else
            {
                //Gets the counts from the selected downrigger and returns it to
                //the view.
                try 
                {
                    Communication.getCounts(Communication.selectedCommPortTag, "$"+starboardAftDeviceCombobox.getSelectedItem().toString());
                    NavigationControls.enableNavigationControls();
                    CoordinateTransformThread.calibrate();
                    //(new Thread(new CoordinateTransformThread())).start();
                } 
                catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException ex) 
                {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }//GEN-LAST:event_starboardAftDeviceComboboxActionPerformed

    private void starboardAftDownriggerLineLengthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starboardAftDownriggerLineLengthButtonActionPerformed
        
    }//GEN-LAST:event_starboardAftDownriggerLineLengthButtonActionPerformed

    private void loadConfigurationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadConfigurationButtonActionPerformed
        
        loadVesselConfigurationFile();
    }//GEN-LAST:event_loadConfigurationButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearPositionUILoggerButton;
    public static javax.swing.JLabel configDiagram;
    private javax.swing.JPanel downriggerPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
    public static javax.swing.JButton loadConfigurationButton;
    private javax.swing.JPanel loggerPanel;
    public static javax.swing.JTextField portAftCountTextbox;
    public static javax.swing.JComboBox portAftDeviceCombobox;
    public static javax.swing.JButton portAftDownriggerLineLengthButton;
    public static javax.swing.JButton portAftDownriggerStationButton;
    public static javax.swing.JButton portAftSetCountsButton;
    public static javax.swing.JTextField portAftSetCountsTextbox;
    public static javax.swing.JProgressBar portAftTensionBar;
    public static javax.swing.JButton portAftTensionButton;
    public static javax.swing.JTextField portForwardCountTextbox;
    public static javax.swing.JComboBox portForwardDeviceCombobox;
    public static javax.swing.JButton portForwardDownriggerLineLengthButton;
    public static javax.swing.JButton portForwardDownriggerStationButton;
    public static javax.swing.JButton portForwardSetCountsButton;
    public static javax.swing.JTextField portForwardSetCountsTextbox;
    public static javax.swing.JProgressBar portForwardTensionBar;
    public static javax.swing.JButton portForwardTensionButton;
    public static javax.swing.JTextField portMiddleCountTextbox;
    public static javax.swing.JComboBox portMiddleDeviceCombobox;
    public static javax.swing.JButton portMiddleDownriggerLineLengthButton;
    public static javax.swing.JButton portMiddleDownriggerStationButton;
    public static javax.swing.JButton portMiddleSetCountsButton;
    public static javax.swing.JTextField portMiddleSetCountsTextbox;
    public static javax.swing.JProgressBar portMiddleTensionBar;
    public static javax.swing.JButton portMiddleTensionButton;
    private javax.swing.JPanel positionPanel;
    public static javax.swing.JTextArea positionUILogger;
    private javax.swing.JButton spherePositionButton;
    public static javax.swing.JTextField spherePositionXTextbox;
    public static javax.swing.JTextField spherePositionYTextbox;
    public static javax.swing.JTextField spherePositionZTextbox;
    public static javax.swing.JTextField starboardAftCountTextbox;
    public static javax.swing.JComboBox starboardAftDeviceCombobox;
    public static javax.swing.JButton starboardAftDownriggerLineLengthButton;
    public static javax.swing.JButton starboardAftDownriggerStationButton;
    public static javax.swing.JButton starboardAftSetCountsButton;
    public static javax.swing.JTextField starboardAftSetCountsTextbox;
    public static javax.swing.JProgressBar starboardAftTensionBar;
    public static javax.swing.JButton starboardAftTensionButton;
    public static javax.swing.JTextField starboardForwardCountTextbox;
    public static javax.swing.JComboBox starboardForwardDeviceCombobox;
    public static javax.swing.JButton starboardForwardDownriggerLineLengthButton;
    public static javax.swing.JButton starboardForwardDownriggerStationButton;
    public static javax.swing.JButton starboardForwardSetCountsButton;
    public static javax.swing.JTextField starboardForwardSetCountsTextbox;
    public static javax.swing.JProgressBar starboardForwardTensionBar;
    public static javax.swing.JButton starboardForwardTensionButton;
    public static javax.swing.JTextField starboardMiddleCountTextbox;
    public static javax.swing.JComboBox starboardMiddleDeviceCombobox;
    public static javax.swing.JButton starboardMiddleDownriggerLineLengthButton;
    public static javax.swing.JButton starboardMiddleDownriggerStationButton;
    public static javax.swing.JButton starboardMiddleSetCountsButton;
    public static javax.swing.JTextField starboardMiddleSetCountsTextbox;
    public static javax.swing.JProgressBar starboardMiddleTensionBar;
    public static javax.swing.JButton starboardMiddleTensionButton;
    private javax.swing.JButton targetPositionButton;
    public static javax.swing.JTextField targetPositionXTextbox;
    public static javax.swing.JTextField targetPositionYTextbox;
    public static javax.swing.JTextField targetPositionZTextbox;
    private javax.swing.JButton transducerPositionButton;
    public static javax.swing.JTextField transducerPositionXTextbox;
    public static javax.swing.JTextField transducerPositionYTextbox;
    public static javax.swing.JTextField transducerPositionZTextbox;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
