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

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;
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
        dtd = "-//noaa.echocal.application//VesselConfigurationUI//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "VesselConfigurationUITopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "noaa.echocal.application.VesselConfigurationUITopComponent")
@ActionReference(path = "Menu/Tools" , position = 50)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_VesselConfigurationUIAction",
        preferredID = "VesselConfigurationUITopComponent"
)
@Messages({
    "CTL_VesselConfigurationUIAction=Vessel Configuration Tool",
    "CTL_VesselConfigurationUITopComponent=Vessel Configuration Tool",
    "HINT_VesselConfigurationUITopComponent=This is a Vessel Configuration tool"
})
public final class VesselConfigurationUITopComponent extends TopComponent {

    //This configuration code variable will be used for generating new configuration files.  
    //This is different than the one used in the Geometry Class which is pulled 
    //from a saved vessel configuration file after it has loaded.
    public static String configurationCode=null;
    
    //Alongship distance global variables.
    //(For vessel configuration xml file generation only!!!)
    public static String sfad="null"; 
    //Declares configuration file input variables. These will be used in the
    //xml file generation.
    public static String pfad="null"; 
    //Declares configuration file input variables. These will be used in the
    //xml file generation.
    public static String smad="null"; 
    //Declares configuration file input variables. These will be used in the
    //xml file generation.
    public static String pmad="null";
    //Declares configuration file input variables. These will be used in the
    //xml file generation.
    public static String pfpm="null", pfpa="null", pfsf="null", pfsm="null", pfsa="null";
    //Declares configuration file input variables. These will be used in the
    //xml file generation.
    public static String pmpf="null", pmpa="null", pmsf="null", pmsm="null", pmsa="null";
    //Declares configuration file input variables. These will be used in the
    //xml file generation.
    public static String papf="null", papm="null", pasf="null", pasm="null", pasa="null";
    //Declares configuration file input variables. These will be used in the
    //xml file generation.
    public static String sfpf="null", sfpm="null", sfpa="null", sfsm="null", sfsa="null";
    //Declares configuration file input variables. These will be used in the
    //xml file generation.
    public static String smpf="null", smpm="null", smpa="null", smsf="null", smsa="null";
    //Declares configuration file input variables. These will be used in the
    //xml file generation.
    public static String sapf="null", sapm="null", sapa="null", sasf="null", sasm="null";
    //Declares configuration file input variables. These will be used in the
    //xml file generation.
    public static String pfho="null", pmho="null", paho="null", sfho="null", smho="null", saho="null", vw="null";    
    
    public final JFileChooser fileChooser = new javax.swing.JFileChooser();
    
    public VesselConfigurationUITopComponent() {
        initComponents();
        setName("Vessel Configuration Tool");
        setToolTipText("Vessel Configuration Tool");
        putClientProperty(TopComponent.PROP_DRAGGING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vesselConfigurationUIPanelLeft = new javax.swing.JPanel();
        configurationTypeCombobox = new javax.swing.JComboBox();
        portForwardHeightFromOceanTextbox = new javax.swing.JTextField();
        portMiddleHeightFromOceanTextbox = new javax.swing.JTextField();
        portForwardHeightFromOceanButton = new javax.swing.JButton();
        portMiddleHeightFromOceanButton = new javax.swing.JButton();
        portAftHeightFromOceanButton = new javax.swing.JButton();
        starboardForwardHeightFromOceanButton = new javax.swing.JButton();
        starboardMiddleHeightFromOceanButton = new javax.swing.JButton();
        starboardAftHeightFromOceanButton = new javax.swing.JButton();
        portAftHeightFromOceanTextbox = new javax.swing.JTextField();
        starboardForwardHeightFromOceanTextbox = new javax.swing.JTextField();
        starboardMiddleHeightFromOceanTextbox = new javax.swing.JTextField();
        starboardAftHeightFromOceanTextbox = new javax.swing.JTextField();
        vesselConfigurationUIPanelRight = new javax.swing.JPanel();
        distanceFromButton1 = new javax.swing.JButton();
        firstRelativeDistanceFromTextfield = new javax.swing.JTextField();
        distanceFromButton2 = new javax.swing.JButton();
        secondRelativeDistanceFromTextfield = new javax.swing.JTextField();
        distanceFromButton3 = new javax.swing.JButton();
        thirdRelativeDistanceFromTextfield = new javax.swing.JTextField();
        distanceFromButton4 = new javax.swing.JButton();
        fourthRelativeDistanceFromTextfield = new javax.swing.JTextField();
        toLabel1 = new javax.swing.JButton();
        toLabel2 = new javax.swing.JButton();
        toLabel3 = new javax.swing.JButton();
        toLabel4 = new javax.swing.JButton();
        firstRelativeDistanceToTextfield = new javax.swing.JTextField();
        secondRelativeDistanceToTextfield = new javax.swing.JTextField();
        thirdRelativeDistanceToTextfield = new javax.swing.JTextField();
        fourthRelativeDistanceToTextfield = new javax.swing.JTextField();
        firstRelativeDistanceInputTextfield = new javax.swing.JTextField();
        secondRelativeDistanceInputTextfield = new javax.swing.JTextField();
        thirdRelativeDistanceInputTextfield = new javax.swing.JTextField();
        fourthRelativeDistanceInputTextfield = new javax.swing.JTextField();
        vesselWidthButton = new javax.swing.JButton();
        transducerDepthButton = new javax.swing.JButton();
        targetDepthButton = new javax.swing.JButton();
        vesselWidthTextbox = new javax.swing.JTextField();
        transducerDepthTextbox = new javax.swing.JTextField();
        targetDepthTextbox = new javax.swing.JTextField();
        transducerYDistanceTextbox = new javax.swing.JTextField();
        downriggerYOffsetButton = new javax.swing.JButton();
        transducerXOffsetButton = new javax.swing.JButton();
        transducerXOffsetTextbox = new javax.swing.JTextField();
        saveConfigurationButton = new javax.swing.JButton();
        loadConfigurationButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        configurationPathLabelButton = new javax.swing.JButton();
        vesselConfigurationGraphic1 = new javax.swing.JLabel();
        vesselConfigurationGraphic2 = new javax.swing.JLabel();

        configurationTypeCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Configuration Type..", "PF-SM-PA", "PM-SF-SA", "PF-SF-SA-PA" }));
        configurationTypeCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configurationTypeComboboxActionPerformed(evt);
            }
        });

        portForwardHeightFromOceanTextbox.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.portForwardHeightFromOceanTextbox.text")); // NOI18N

        portMiddleHeightFromOceanTextbox.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.portMiddleHeightFromOceanTextbox.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(portForwardHeightFromOceanButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.portForwardHeightFromOceanButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(portMiddleHeightFromOceanButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.portMiddleHeightFromOceanButton.text")); // NOI18N
        portMiddleHeightFromOceanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portMiddleHeightFromOceanButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(portAftHeightFromOceanButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.portAftHeightFromOceanButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(starboardForwardHeightFromOceanButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.starboardForwardHeightFromOceanButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(starboardMiddleHeightFromOceanButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.starboardMiddleHeightFromOceanButton.text")); // NOI18N
        starboardMiddleHeightFromOceanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starboardMiddleHeightFromOceanButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(starboardAftHeightFromOceanButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.starboardAftHeightFromOceanButton.text")); // NOI18N

        portAftHeightFromOceanTextbox.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.portAftHeightFromOceanTextbox.text")); // NOI18N

        starboardForwardHeightFromOceanTextbox.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.starboardForwardHeightFromOceanTextbox.text")); // NOI18N

        starboardMiddleHeightFromOceanTextbox.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.starboardMiddleHeightFromOceanTextbox.text")); // NOI18N

        starboardAftHeightFromOceanTextbox.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.starboardAftHeightFromOceanTextbox.text")); // NOI18N

        javax.swing.GroupLayout vesselConfigurationUIPanelLeftLayout = new javax.swing.GroupLayout(vesselConfigurationUIPanelLeft);
        vesselConfigurationUIPanelLeft.setLayout(vesselConfigurationUIPanelLeftLayout);
        vesselConfigurationUIPanelLeftLayout.setHorizontalGroup(
            vesselConfigurationUIPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vesselConfigurationUIPanelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vesselConfigurationUIPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(vesselConfigurationUIPanelLeftLayout.createSequentialGroup()
                        .addComponent(portAftHeightFromOceanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(portAftHeightFromOceanTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(vesselConfigurationUIPanelLeftLayout.createSequentialGroup()
                        .addComponent(starboardForwardHeightFromOceanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(starboardForwardHeightFromOceanTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(vesselConfigurationUIPanelLeftLayout.createSequentialGroup()
                        .addComponent(starboardAftHeightFromOceanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(starboardAftHeightFromOceanTextbox))
                    .addGroup(vesselConfigurationUIPanelLeftLayout.createSequentialGroup()
                        .addComponent(starboardMiddleHeightFromOceanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(starboardMiddleHeightFromOceanTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(vesselConfigurationUIPanelLeftLayout.createSequentialGroup()
                        .addGroup(vesselConfigurationUIPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(portForwardHeightFromOceanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(portMiddleHeightFromOceanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(vesselConfigurationUIPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(portForwardHeightFromOceanTextbox, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(portMiddleHeightFromOceanTextbox, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
                    .addComponent(configurationTypeCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vesselConfigurationUIPanelLeftLayout.setVerticalGroup(
            vesselConfigurationUIPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vesselConfigurationUIPanelLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(configurationTypeCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(portForwardHeightFromOceanTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portForwardHeightFromOceanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portMiddleHeightFromOceanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portMiddleHeightFromOceanTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(portAftHeightFromOceanTextbox)
                    .addComponent(portAftHeightFromOceanButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(starboardForwardHeightFromOceanTextbox)
                    .addComponent(starboardForwardHeightFromOceanButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(starboardMiddleHeightFromOceanTextbox)
                    .addComponent(starboardMiddleHeightFromOceanButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(starboardAftHeightFromOceanButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(starboardAftHeightFromOceanTextbox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(distanceFromButton1, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.distanceFromButton1.text")); // NOI18N

        firstRelativeDistanceFromTextfield.setEditable(false);
        firstRelativeDistanceFromTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.firstRelativeDistanceFromTextfield.text")); // NOI18N
        firstRelativeDistanceFromTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstRelativeDistanceFromTextfieldActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(distanceFromButton2, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.distanceFromButton2.text")); // NOI18N

        secondRelativeDistanceFromTextfield.setEditable(false);
        secondRelativeDistanceFromTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.secondRelativeDistanceFromTextfield.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(distanceFromButton3, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.distanceFromButton3.text")); // NOI18N

        thirdRelativeDistanceFromTextfield.setEditable(false);
        thirdRelativeDistanceFromTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.thirdRelativeDistanceFromTextfield.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(distanceFromButton4, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.distanceFromButton4.text")); // NOI18N

        fourthRelativeDistanceFromTextfield.setEditable(false);
        fourthRelativeDistanceFromTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.fourthRelativeDistanceFromTextfield.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(toLabel1, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.toLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(toLabel2, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.toLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(toLabel3, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.toLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(toLabel4, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.toLabel4.text")); // NOI18N

        firstRelativeDistanceToTextfield.setEditable(false);
        firstRelativeDistanceToTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.firstRelativeDistanceToTextfield.text")); // NOI18N

        secondRelativeDistanceToTextfield.setEditable(false);
        secondRelativeDistanceToTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.secondRelativeDistanceToTextfield.text")); // NOI18N

        thirdRelativeDistanceToTextfield.setEditable(false);
        thirdRelativeDistanceToTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.thirdRelativeDistanceToTextfield.text")); // NOI18N

        fourthRelativeDistanceToTextfield.setEditable(false);
        fourthRelativeDistanceToTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.fourthRelativeDistanceToTextfield.text")); // NOI18N

        firstRelativeDistanceInputTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.firstRelativeDistanceInputTextfield.text")); // NOI18N

        secondRelativeDistanceInputTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.secondRelativeDistanceInputTextfield.text")); // NOI18N

        thirdRelativeDistanceInputTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.thirdRelativeDistanceInputTextfield.text")); // NOI18N

        fourthRelativeDistanceInputTextfield.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.fourthRelativeDistanceInputTextfield.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(vesselWidthButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.vesselWidthButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(transducerDepthButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.transducerDepthButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(targetDepthButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.targetDepthButton.text")); // NOI18N

        vesselWidthTextbox.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.vesselWidthTextbox.text")); // NOI18N
        vesselWidthTextbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vesselWidthTextboxActionPerformed(evt);
            }
        });

        transducerDepthTextbox.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.transducerDepthTextbox.text")); // NOI18N
        transducerDepthTextbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transducerDepthTextboxActionPerformed(evt);
            }
        });

        targetDepthTextbox.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.targetDepthTextbox.text")); // NOI18N

        transducerYDistanceTextbox.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.transducerYDistanceTextbox.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(downriggerYOffsetButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.downriggerYOffsetButton.text")); // NOI18N
        downriggerYOffsetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downriggerYOffsetButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(transducerXOffsetButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.transducerXOffsetButton.text")); // NOI18N

        transducerXOffsetTextbox.setText(org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.transducerXOffsetTextbox.text")); // NOI18N
        transducerXOffsetTextbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transducerXOffsetTextboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(saveConfigurationButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.saveConfigurationButton.text")); // NOI18N
        saveConfigurationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveConfigurationButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(loadConfigurationButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.loadConfigurationButton.text")); // NOI18N
        loadConfigurationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadConfigurationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout vesselConfigurationUIPanelRightLayout = new javax.swing.GroupLayout(vesselConfigurationUIPanelRight);
        vesselConfigurationUIPanelRight.setLayout(vesselConfigurationUIPanelRightLayout);
        vesselConfigurationUIPanelRightLayout.setHorizontalGroup(
            vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vesselConfigurationUIPanelRightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(vesselConfigurationUIPanelRightLayout.createSequentialGroup()
                        .addComponent(targetDepthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(targetDepthTextbox, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                    .addGroup(vesselConfigurationUIPanelRightLayout.createSequentialGroup()
                        .addComponent(transducerDepthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transducerDepthTextbox, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                    .addGroup(vesselConfigurationUIPanelRightLayout.createSequentialGroup()
                        .addComponent(vesselWidthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vesselWidthTextbox, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                    .addGroup(vesselConfigurationUIPanelRightLayout.createSequentialGroup()
                        .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(distanceFromButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(distanceFromButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(distanceFromButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(distanceFromButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(firstRelativeDistanceFromTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(secondRelativeDistanceFromTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(thirdRelativeDistanceFromTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(fourthRelativeDistanceFromTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(toLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(firstRelativeDistanceToTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(secondRelativeDistanceToTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(thirdRelativeDistanceToTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(fourthRelativeDistanceToTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(downriggerYOffsetButton, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(transducerXOffsetButton, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(loadConfigurationButton, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(transducerYDistanceTextbox, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(firstRelativeDistanceInputTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(secondRelativeDistanceInputTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(thirdRelativeDistanceInputTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(fourthRelativeDistanceInputTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(transducerXOffsetTextbox, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(saveConfigurationButton, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vesselConfigurationUIPanelRightLayout.setVerticalGroup(
            vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vesselConfigurationUIPanelRightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(firstRelativeDistanceInputTextfield)
                    .addComponent(firstRelativeDistanceToTextfield)
                    .addComponent(distanceFromButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(firstRelativeDistanceFromTextfield)
                    .addComponent(toLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(secondRelativeDistanceInputTextfield)
                    .addComponent(secondRelativeDistanceToTextfield)
                    .addComponent(distanceFromButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(secondRelativeDistanceFromTextfield)
                    .addComponent(toLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(thirdRelativeDistanceInputTextfield)
                    .addComponent(distanceFromButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(thirdRelativeDistanceFromTextfield)
                    .addComponent(toLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(thirdRelativeDistanceToTextfield))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(distanceFromButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(fourthRelativeDistanceFromTextfield)
                    .addComponent(toLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fourthRelativeDistanceToTextfield)
                    .addComponent(fourthRelativeDistanceInputTextfield))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(vesselWidthTextbox)
                    .addComponent(vesselWidthButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(transducerYDistanceTextbox)
                    .addComponent(downriggerYOffsetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(transducerDepthTextbox)
                    .addComponent(transducerDepthButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(transducerXOffsetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transducerXOffsetTextbox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(vesselConfigurationUIPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(targetDepthButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(targetDepthTextbox)
                    .addComponent(saveConfigurationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loadConfigurationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(configurationPathLabelButton, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.configurationPathLabelButton.text")); // NOI18N

        vesselConfigurationGraphic1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/VesselConfigurationDiagram.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(vesselConfigurationGraphic1, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.vesselConfigurationGraphic1.text")); // NOI18N

        vesselConfigurationGraphic2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/VesselConfigurationSideDiagram.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(vesselConfigurationGraphic2, org.openide.util.NbBundle.getMessage(VesselConfigurationUITopComponent.class, "VesselConfigurationUITopComponent.vesselConfigurationGraphic2.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(configurationPathLabelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(vesselConfigurationGraphic1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(vesselConfigurationGraphic2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(configurationPathLabelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vesselConfigurationGraphic1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vesselConfigurationGraphic2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(vesselConfigurationUIPanelLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vesselConfigurationUIPanelRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(vesselConfigurationUIPanelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vesselConfigurationUIPanelLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void portMiddleHeightFromOceanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portMiddleHeightFromOceanButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portMiddleHeightFromOceanButtonActionPerformed

    private void starboardMiddleHeightFromOceanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starboardMiddleHeightFromOceanButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_starboardMiddleHeightFromOceanButtonActionPerformed

    private void vesselWidthTextboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vesselWidthTextboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vesselWidthTextboxActionPerformed

    private void transducerDepthTextboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transducerDepthTextboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_transducerDepthTextboxActionPerformed

    private void firstRelativeDistanceFromTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstRelativeDistanceFromTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstRelativeDistanceFromTextfieldActionPerformed

    private void configurationTypeComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configurationTypeComboboxActionPerformed
    
        switch (configurationTypeCombobox.getSelectedItem().toString())
        {
            case "PF-SM-PA":
            {
                configurationCode="pfsmpa";
                firstRelativeDistanceFromTextfield.setText("Port Forward");
                secondRelativeDistanceFromTextfield.setText("Starboard Middle");
                thirdRelativeDistanceFromTextfield.setText("Port Aft");
                fourthRelativeDistanceFromTextfield.setText("");
                fourthRelativeDistanceFromTextfield.setEnabled(false);
                firstRelativeDistanceToTextfield.setText("Starboard Middle");
                secondRelativeDistanceToTextfield.setText("Port Aft");
                thirdRelativeDistanceToTextfield.setText("Port Forward");
                fourthRelativeDistanceToTextfield.setText("");
                fourthRelativeDistanceToTextfield.setEnabled(false);
                fourthRelativeDistanceInputTextfield.setEnabled(false);
                downriggerYOffsetButton.setText("PF Y Offset");
                break;
            }
            case "PM-SF-SA":
            {
                configurationCode="pmsfsa";
                firstRelativeDistanceFromTextfield.setText("Port Middle");
                secondRelativeDistanceFromTextfield.setText("Starboard Forward");
                thirdRelativeDistanceFromTextfield.setText("Starboard Aft");
                fourthRelativeDistanceFromTextfield.setText("");
                fourthRelativeDistanceFromTextfield.setEnabled(false);
                firstRelativeDistanceToTextfield.setText("Starboard Forward");
                secondRelativeDistanceToTextfield.setText("Starboard Aft");
                thirdRelativeDistanceToTextfield.setText("Port Middle");
                fourthRelativeDistanceToTextfield.setText("");
                fourthRelativeDistanceToTextfield.setEnabled(false);
                fourthRelativeDistanceInputTextfield.setEnabled(false);
                downriggerYOffsetButton.setText("SF Y Offset");
                break;
            }
            case "PF-SF-SA-PA":
            {
                configurationCode="pfsfsapa";
                firstRelativeDistanceFromTextfield.setText("Port Forward");
                secondRelativeDistanceFromTextfield.setText("Starboard Forward");
                thirdRelativeDistanceFromTextfield.setText("Starboard Aft");
                fourthRelativeDistanceFromTextfield.setText("Port Aft");
                fourthRelativeDistanceFromTextfield.setEnabled(true);
                firstRelativeDistanceToTextfield.setText("Starboard Forward");
                secondRelativeDistanceToTextfield.setText("Starboard Aft");
                thirdRelativeDistanceToTextfield.setText("Port Aft");
                fourthRelativeDistanceToTextfield.setText("Port Forward");
                fourthRelativeDistanceToTextfield.setEnabled(true);
                fourthRelativeDistanceInputTextfield.setEnabled(true);
                downriggerYOffsetButton.setText("PF Y Offset");
                break;
            }
            //------------------------------------------------------------------
            //--  The following two cases will never be used... JMG 11/23/2015
            /*
            case "PF-SF-SM-PM":
            {
                configurationCode="pfsfsmpm";
                firstRelativeDistanceFromTextfield.setText("Port Forward");
                secondRelativeDistanceFromTextfield.setText("Starboard Forward");
                thirdRelativeDistanceFromTextfield.setText("Starboard Middle");
                fourthRelativeDistanceFromTextfield.setText("Port Middle");
                fourthRelativeDistanceFromTextfield.setEnabled(true);
                firstRelativeDistanceToTextfield.setText("Starboard Forward");
                secondRelativeDistanceToTextfield.setText("Starboard Middle");
                thirdRelativeDistanceToTextfield.setText("Port Middle");
                fourthRelativeDistanceToTextfield.setText("Port Forward");  
                fourthRelativeDistanceToTextfield.setEnabled(true);
                fourthRelativeDistanceInputTextfield.setEnabled(true);
                downriggerYOffsetButton.setText("PF Y Offset");
                break;
            }
            case "PM-SM-SA-PA":
            {
                configurationCode="pmsmsapa";
                firstRelativeDistanceFromTextfield.setText("Port Middle");
                secondRelativeDistanceFromTextfield.setText("Starboard Middle");
                thirdRelativeDistanceFromTextfield.setText("Starboard Aft");
                fourthRelativeDistanceFromTextfield.setText("Port Aft");
                fourthRelativeDistanceFromTextfield.setEnabled(true);
                firstRelativeDistanceToTextfield.setText("Starboard Middle");
                secondRelativeDistanceToTextfield.setText("Starboard Aft");
                thirdRelativeDistanceToTextfield.setText("Port Aft");
                fourthRelativeDistanceToTextfield.setText("Port Middle");
                fourthRelativeDistanceToTextfield.setEnabled(true);
                fourthRelativeDistanceInputTextfield.setEnabled(true);
                downriggerYOffsetButton.setText("PM Y Offset");
                break;
            }
            */
            //------------------------------------------------------------------
            case "Choose Configuration Type..":
            {
                configurationCode=null;
                firstRelativeDistanceFromTextfield.setText("");
                secondRelativeDistanceFromTextfield.setText("");
                thirdRelativeDistanceFromTextfield.setText("");
                fourthRelativeDistanceFromTextfield.setText("");
                firstRelativeDistanceToTextfield.setText("");
                secondRelativeDistanceToTextfield.setText("");
                thirdRelativeDistanceToTextfield.setText("");
                fourthRelativeDistanceToTextfield.setText("");
                downriggerYOffsetButton.setText("Downrigger Y Offset");
                break;
            }
            default:
            {
                break;
            }
        }
    }//GEN-LAST:event_configurationTypeComboboxActionPerformed
   
    public void configureDownriggerStationControls(String configurationCode)
    {
        switch (configurationCode)
        {
            case "pmsfsa":
            {
                CoordinateDisplayTopComponent.portMiddleDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.starboardForwardDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.starboardAftDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.portForwardDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardTensionBar.setEnabled(false);
                
                CoordinateDisplayTopComponent.starboardMiddleDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleTensionBar.setEnabled(false);
                
                CoordinateDisplayTopComponent.portAftDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.portAftDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.portAftCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portAftDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.portAftSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.portAftSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portAftTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.portAftTensionBar.setEnabled(false);
                
                break;
            }
            case "pfsmpa":
            {
                
                CoordinateDisplayTopComponent.portForwardDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.starboardMiddleDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.portAftDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portAftDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.portAftSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portAftTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.portMiddleDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleTensionBar.setEnabled(false);
                
                CoordinateDisplayTopComponent.starboardForwardDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardTensionBar.setEnabled(false);
                
                CoordinateDisplayTopComponent.starboardAftDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftTensionBar.setEnabled(false);
                        
                break;
            }
            case "pfsfsapa":
            {
                CoordinateDisplayTopComponent.portForwardDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.starboardForwardDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.portMiddleDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.portMiddleTensionBar.setEnabled(false);
                
                CoordinateDisplayTopComponent.starboardMiddleDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardMiddleTensionBar.setEnabled(false);
                
                CoordinateDisplayTopComponent.starboardAftDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.portAftDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portAftDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.portAftSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portAftTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftTensionBar.setEnabled(true);
                break;
            }
            case "pfsfsmpm":
            {
                CoordinateDisplayTopComponent.portForwardDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.portForwardTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.starboardForwardDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardForwardTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.starboardMiddleDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.portMiddleDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.starboardAftDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardAftTensionBar.setEnabled(false);
                
                CoordinateDisplayTopComponent.portAftDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.portAftDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.portAftCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portAftDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.portAftSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.portAftSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portAftTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.portAftTensionBar.setEnabled(false);
                break;
            }
            case "pmsmsapa":
            {
                CoordinateDisplayTopComponent.portForwardDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.portForwardTensionBar.setEnabled(false);
                
                CoordinateDisplayTopComponent.starboardForwardDownriggerStationButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardDownriggerLineLengthButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardCountTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardDeviceCombobox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardSetCountsButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardSetCountsTextbox.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardTensionButton.setEnabled(false);
                CoordinateDisplayTopComponent.starboardForwardTensionBar.setEnabled(false);
                
                CoordinateDisplayTopComponent.portMiddleDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.portMiddleTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.starboardMiddleDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardMiddleTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.starboardAftDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.starboardAftTensionBar.setEnabled(true);
                
                CoordinateDisplayTopComponent.portAftDownriggerStationButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftDownriggerLineLengthButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftCountTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portAftDeviceCombobox.setEnabled(true);
                CoordinateDisplayTopComponent.portAftSetCountsButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftSetCountsTextbox.setEnabled(true);
                CoordinateDisplayTopComponent.portAftTensionButton.setEnabled(true);
                CoordinateDisplayTopComponent.portAftTensionBar.setEnabled(true);
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
            } 
            catch (SAXException | IOException | ParserConfigurationException | ArrayIndexOutOfBoundsException ex) 
            {
                //Exceptions.printStackTrace(ex);
                Terminal.systemLogMessage("The vessel configuration file has not been read properly.", CoordinateDisplayTopComponent.positionUILogger);
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "The vessel config file failed to load properly.\n"
                                                    +"Some parameters in the file may be missing..");
            }
            configurationPathLabelButton.setText("Configuation File: "+pathVariableString);
        } 
        else 
        {
            //Pass.   
        }
    }
    
    
    
    private void saveConfigurationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveConfigurationButtonActionPerformed
        Generation.generateFile();
    }//GEN-LAST:event_saveConfigurationButtonActionPerformed

    private void loadConfigurationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadConfigurationButtonActionPerformed
        loadVesselConfigurationFile();
    }//GEN-LAST:event_loadConfigurationButtonActionPerformed

    private void downriggerYOffsetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downriggerYOffsetButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_downriggerYOffsetButtonActionPerformed

    private void transducerXOffsetTextboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transducerXOffsetTextboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_transducerXOffsetTextboxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton configurationPathLabelButton;
    public static javax.swing.JComboBox configurationTypeCombobox;
    private javax.swing.JButton distanceFromButton1;
    private javax.swing.JButton distanceFromButton2;
    private javax.swing.JButton distanceFromButton3;
    private javax.swing.JButton distanceFromButton4;
    private javax.swing.JButton downriggerYOffsetButton;
    public static javax.swing.JTextField firstRelativeDistanceFromTextfield;
    public static javax.swing.JTextField firstRelativeDistanceInputTextfield;
    public static javax.swing.JTextField firstRelativeDistanceToTextfield;
    public static javax.swing.JTextField fourthRelativeDistanceFromTextfield;
    public static javax.swing.JTextField fourthRelativeDistanceInputTextfield;
    public static javax.swing.JTextField fourthRelativeDistanceToTextfield;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JButton loadConfigurationButton;
    private javax.swing.JButton portAftHeightFromOceanButton;
    public static javax.swing.JTextField portAftHeightFromOceanTextbox;
    private javax.swing.JButton portForwardHeightFromOceanButton;
    public static javax.swing.JTextField portForwardHeightFromOceanTextbox;
    private javax.swing.JButton portMiddleHeightFromOceanButton;
    public static javax.swing.JTextField portMiddleHeightFromOceanTextbox;
    private javax.swing.JButton saveConfigurationButton;
    public static javax.swing.JTextField secondRelativeDistanceFromTextfield;
    public static javax.swing.JTextField secondRelativeDistanceInputTextfield;
    public static javax.swing.JTextField secondRelativeDistanceToTextfield;
    private javax.swing.JButton starboardAftHeightFromOceanButton;
    public static javax.swing.JTextField starboardAftHeightFromOceanTextbox;
    private javax.swing.JButton starboardForwardHeightFromOceanButton;
    public static javax.swing.JTextField starboardForwardHeightFromOceanTextbox;
    private javax.swing.JButton starboardMiddleHeightFromOceanButton;
    public static javax.swing.JTextField starboardMiddleHeightFromOceanTextbox;
    private javax.swing.JButton targetDepthButton;
    public static javax.swing.JTextField targetDepthTextbox;
    public static javax.swing.JTextField thirdRelativeDistanceFromTextfield;
    public static javax.swing.JTextField thirdRelativeDistanceInputTextfield;
    public static javax.swing.JTextField thirdRelativeDistanceToTextfield;
    private javax.swing.JButton toLabel1;
    private javax.swing.JButton toLabel2;
    private javax.swing.JButton toLabel3;
    private javax.swing.JButton toLabel4;
    private javax.swing.JButton transducerDepthButton;
    public static javax.swing.JTextField transducerDepthTextbox;
    private javax.swing.JButton transducerXOffsetButton;
    public static javax.swing.JTextField transducerXOffsetTextbox;
    public static javax.swing.JTextField transducerYDistanceTextbox;
    private javax.swing.JLabel vesselConfigurationGraphic1;
    private javax.swing.JLabel vesselConfigurationGraphic2;
    private javax.swing.JPanel vesselConfigurationUIPanelLeft;
    private javax.swing.JPanel vesselConfigurationUIPanelRight;
    private javax.swing.JButton vesselWidthButton;
    public static javax.swing.JTextField vesselWidthTextbox;
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
