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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import static noaa.echocal.application.JoystickConfigurationTopComponent.jButtonPanel;
import static noaa.echocal.application.JoystickConfigurationTopComponent.jHatSwitchPanel;
import static noaa.echocal.application.JoystickConfigurationTopComponent.jXYAxisPanel;
import static noaa.echocal.application.JoystickConfigurationTopComponent.jZAxisPanel;

/**
 *
 * @author JGodlewski
 */
public class JoystickThread extends Thread {
    
    //A lock designed to stop a single navigation thread.
    public static boolean stopJoystick = false;
    
    public static void JoystickThread()
    {
        //System.out.println("Joystick Thread is active...");
        // Currently selected controller.
        int selectedControllerIndex = getSelectedControllerName();
        Controller controller = JoystickConfigurationTopComponent.foundControllers.get(selectedControllerIndex);

        // Poll controller for current data, and break while loop if controller is disconnected.
        if( !controller.poll() ){
            //JoystickConfigurationTopComponent.showControllerDisconnected();
            showControllerDisconnected();
            //break;
            return;
        }
            
            // X axis and Y axis
        int xAxisPercentage = 0;
        int yAxisPercentage = 0;

        // JPanel for other axes.
        JPanel axesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 2));
        axesPanel.setBounds(0, 0, 200, 190);
            
        // JPanel for controller buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
        buttonsPanel.setBounds(6, 19, 246, 110);

        // Go trough all components of the controller.
        Component[] components = controller.getComponents();
        for(int i=0; i < components.length; i++)
        {
            Component component = components[i];
            Component.Identifier componentIdentifier = component.getIdentifier();
                
            // Buttons
            //if(component.getName().contains("Button")){ // If the language is not english, this won't work.
            if(componentIdentifier.getName().matches("^[0-9]*$")){ // If the component identifier name contains only numbers, then this is a button.
                // Is button pressed?
                boolean isItPressed = true;
                if(component.getPollData() == 0.0f)
                    isItPressed = false;
                    
                // Button index
                String buttonIndex;
                buttonIndex = component.getIdentifier().toString();
                    
                // Create and add new button to panel.
                JToggleButton aToggleButton = new JToggleButton(buttonIndex, isItPressed);
                aToggleButton.setPreferredSize(new Dimension(48, 25));
                aToggleButton.setEnabled(false);
                buttonsPanel.add(aToggleButton);
                    
                // We know that this component was button so we can skip to next component.
                continue;
            }
                
            // Hat switch
            if(componentIdentifier == Component.Identifier.Axis.POV){
                float hatSwitchPosition = component.getPollData();
                setHatSwitch(hatSwitchPosition);
                    
                // We know that this component was hat switch so we can skip to next component.
                continue;
            }
                
            // Axes
            if(component.isAnalog()){
                float axisValue = component.getPollData();
                int axisValueInPercentage = getAxisValueInPercentage(axisValue);
                //System.out.println("Joystick Axis Value in % = " + axisValueInPercentage);
                
                // X axis
                if(componentIdentifier == Component.Identifier.Axis.X){
                    xAxisPercentage = axisValueInPercentage;
                    JoystickConfigurationTopComponent.jXField.setText("" + xAxisPercentage);
                    continue; // Go to next component.
                }
                // Y axis
                if(componentIdentifier == Component.Identifier.Axis.Y){
                    yAxisPercentage = axisValueInPercentage;
                    JoystickConfigurationTopComponent.jYField.setText("" + yAxisPercentage);
                    continue; // Go to next component.
                }
                    
                // Other axis
                JLabel progressBarLabel = new JLabel(component.getName());
                JProgressBar progressBar = new JProgressBar(0, 100);
                progressBar.setValue(axisValueInPercentage);
                axesPanel.add(progressBarLabel);
                axesPanel.add(progressBar);
            }
        }
            
        // Now that we go trough all controller components,
        // we add buttons panel to window.
        setControllerButtons(buttonsPanel);
        // Set x and y axes..
        setXYAxis(xAxisPercentage, yAxisPercentage);
        // Add other axes panel to window.
        addAxisPanel(axesPanel);
        //Now move sphere according to joystick X-Y input..
        moveSphere(xAxisPercentage, yAxisPercentage);
    }

    /* Methods for setting components on the window. */
    
    public static int getSelectedControllerName(){
        return JoystickConfigurationTopComponent.jComboBox_controllers.getSelectedIndex();
    }
    
    public static void showControllerDisconnected(){
        JoystickConfigurationTopComponent.jComboBox_controllers.removeAllItems();
        JoystickConfigurationTopComponent.jComboBox_controllers.addItem("Controller disconnected!");
    }
    
    public static void setXYAxis(int xPercentage, int yPercentage){
        try
        {            
            Graphics2D g2d = (Graphics2D)jXYAxisPanel.getGraphics();
            g2d.clearRect(1, 1, jXYAxisPanel.getWidth() - 2, jXYAxisPanel.getHeight() - 2);
            g2d.fillOval(xPercentage, yPercentage, 10, 10);
        }
        catch (NullPointerException ex) 
        {
            //This exception is thrown when window is minimized with Joystick
            //still enabled.. Let's fail quietly..
            //System.out.println("Error in updating XY Axis...");
        }
    }
    
    public static void setControllerButtons(JPanel buttonsPanel){
        jButtonPanel.removeAll();
        jButtonPanel.add(buttonsPanel);
        jButtonPanel.validate();
    }

    public static void setHatSwitch(float hatSwitchPosition) {
        int circleSize = 100;
        try
        {
            Graphics2D g2d = (Graphics2D)jHatSwitchPanel.getGraphics();
            g2d.clearRect(5, 15, jHatSwitchPanel.getWidth() - 10, jHatSwitchPanel.getHeight() - 22);
            g2d.drawOval(20, 22, circleSize, circleSize);
        
            if(Float.compare(hatSwitchPosition, Component.POV.OFF) == 0)
                return;
        
            int smallCircleSize = 10;
            int upCircleX = 65;
            int upCircleY = 17;
            int leftCircleX = 15;
            int leftCircleY = 68;
            int betweenX = 37;
            int betweenY = 17;
        
            int x = 0;
            int y = 0;
        
            g2d.setColor(Color.blue);
                        
            if(Float.compare(hatSwitchPosition, Component.POV.UP) == 0){
                x = upCircleX;
                y = upCircleY;
            }else if(Float.compare(hatSwitchPosition, Component.POV.DOWN) == 0){
                x = upCircleX;
                y = upCircleY + circleSize;
            }else if(Float.compare(hatSwitchPosition, Component.POV.LEFT) == 0){
                x = leftCircleX;
                y = leftCircleY;
            }else if(Float.compare(hatSwitchPosition, Component.POV.RIGHT) == 0){
                x = leftCircleX + circleSize;
                y = leftCircleY;
            }else if(Float.compare(hatSwitchPosition, Component.POV.UP_LEFT) == 0){
                x = upCircleX - betweenX;
                y = upCircleY + betweenY;
            }else if(Float.compare(hatSwitchPosition, Component.POV.UP_RIGHT) == 0){
                x = upCircleX + betweenX;
                y = upCircleY + betweenY;
            }else if(Float.compare(hatSwitchPosition, Component.POV.DOWN_LEFT) == 0){
                x = upCircleX - betweenX;
                y = upCircleY + circleSize - betweenY;
            }else if(Float.compare(hatSwitchPosition, Component.POV.DOWN_RIGHT) == 0){
                x = upCircleX + betweenX;
                y = upCircleY + circleSize - betweenY;
            }
        
            g2d.fillOval(x, y, smallCircleSize, smallCircleSize);
            
        }
        catch (NullPointerException ex)
        {
            //This exception is thrown when window is minimized with Joystick
            //still enabled.. Let's fail quietly..
            //System.out.println("Error in updating XY Axis...");
        }
    }

    public static void addAxisPanel(javax.swing.JPanel axesPanel){
        jZAxisPanel.removeAll();
        jZAxisPanel.add(axesPanel);
        jZAxisPanel.validate();
    }

    /*
     * Move the calibration sphere according to the X and Y axis percentage value.
     * 
     * If XAxis & YAxis is between 0 and 30, then FWD Left movement required.
     * If 0 <= XAxis < 30, and 30 <= YAxis < 70, then Left movement reguired.
     * If 0 <= YAxis < 30, and 30 <= XAxis < 70, then FWD movement reguired.
     * If 0 <= XAxis < 30, and 70 <= YAxis < 100, then AFT Left movement reguired.
     * If 0 <= YAxis < 30, and 70 <= XAxis < 100, then FWD Right movement reguired.
     * If 30 <= XAxis < 70, and 70 <= YAxis < 100, then AFT movement reguired.
     * If 30 <= YAxis < 70, and 70 <= XAxis < 100, then Right movement reguired.
     * If XAxis & YAxis is between 70 and 100, then AFT Right movement required.
     */
    public static void moveSphere(int XAxis, int YAxis){

        //Check XAxis first:
        if (XAxis >= 0 && XAxis < 20){
            if (YAxis >= 0 && YAxis < 20){
                //Move FWD LEFT...
                NavigationControls.forwardLeftButton.setEnabled(true);
                System.out.println("Moving FWD LEFT...");
                try {
                    if (ButtonHandler.buttonWasPressed == false)
                    {
                        ButtonHandler.buttonWasPressed = true;
                        Geometry.forwardLeft();
                        ButtonHandler.buttonWasPressed = false;
                    }
                    //Thread.sleep(200);
                }
                //Gracefully catch any exceptions.
                catch (PortInUseException | IOException | InterruptedException | UnsupportedCommOperationException | NoSuchPortException ex) 
                {
                    System.out.println("FWD LEFT Button Failed");
                }
                NavigationControls.forwardLeftButton.setEnabled(false);
                
            }else if (YAxis >= 45 && YAxis < 55){
                //Move LEFT...
                NavigationControls.leftButton.setEnabled(true);
                System.out.println("Moving LEFT...");
                try {
                    if (ButtonHandler.buttonWasPressed == false)
                    {
                        ButtonHandler.buttonWasPressed = true;
                        Geometry.left();
                        ButtonHandler.buttonWasPressed = false;
                    }
                    //Thread.sleep(200);
                }
                //Gracefully catch any exceptions.
                catch (PortInUseException | IOException | InterruptedException | UnsupportedCommOperationException | NoSuchPortException ex) 
                {
                    System.out.println("LEFT Button Failed");
                }
                NavigationControls.leftButton.setEnabled(false);

            }else if (YAxis >= 80 && YAxis <= 100){
                //Move AFT LEFT...
                NavigationControls.aftLeftButton.setEnabled(true);
                System.out.println("Moving AFT LEFT...");
                try {
                    if (ButtonHandler.buttonWasPressed == false)
                    {
                        ButtonHandler.buttonWasPressed = true;
                        Geometry.backwardLeft();
                        ButtonHandler.buttonWasPressed = false;
                    }
                    //Thread.sleep(200);
                }
                //Gracefully catch any exceptions.
                catch (PortInUseException | IOException | InterruptedException | UnsupportedCommOperationException | NoSuchPortException ex) 
                {
                    System.out.println("AFT LEFT Button Failed");
                }
                NavigationControls.aftLeftButton.setEnabled(false);

            }else {
                //Do nothing..
            }
        }else if (XAxis >= 45 && XAxis < 55){
            if (YAxis >= 0 && YAxis < 20){
                //Move FWD...
                NavigationControls.forwardButton.setEnabled(true);
                System.out.println("Moving FWD...");
                try {
                    if (ButtonHandler.buttonWasPressed == false)
                    {
                        ButtonHandler.buttonWasPressed = true;
                        Geometry.forward();
                        ButtonHandler.buttonWasPressed = false;
                    }
                    //Thread.sleep(200);
                }
                //Gracefully catch any exceptions.
                catch (PortInUseException | IOException | InterruptedException | UnsupportedCommOperationException | NoSuchPortException ex) 
                {
                    System.out.println("FWD Button Failed");
                }
                NavigationControls.forwardButton.setEnabled(false);

            }else if (YAxis >= 80 && YAxis <= 100){
                //Move AFT...
                NavigationControls.aftButton.setEnabled(true);
                System.out.println("Moving AFT...");
                try {
                    if (ButtonHandler.buttonWasPressed == false)
                    {
                        ButtonHandler.buttonWasPressed = true;
                        Geometry.backward();
                        ButtonHandler.buttonWasPressed = false;
                    }
                    //Thread.sleep(200);
                }
                //Gracefully catch any exceptions.
                catch (PortInUseException | IOException | InterruptedException | UnsupportedCommOperationException | NoSuchPortException ex) 
                {
                    System.out.println("LEFT Button Failed");
                }
                NavigationControls.aftButton.setEnabled(false);

            }else {
                //Do nothing..
            }            
        }else if (XAxis >= 80 && XAxis <= 100){
            if (YAxis >= 0 && YAxis < 20){
                //Move FWD RIGHT...
                NavigationControls.forwardRightButton.setEnabled(true);
                System.out.println("Moving FWD RIGHT...");
                try {
                    if (ButtonHandler.buttonWasPressed == false)
                    {
                        ButtonHandler.buttonWasPressed = true;
                        Geometry.forwardRight();
                        ButtonHandler.buttonWasPressed = false;
                    }
                    //Thread.sleep(200);
                }
                //Gracefully catch any exceptions.
                catch (PortInUseException | IOException | InterruptedException | UnsupportedCommOperationException | NoSuchPortException ex) 
                {
                    System.out.println("FWD RIGHT Button Failed");
                }
                NavigationControls.forwardRightButton.setEnabled(false);

            }else if (YAxis >= 45 && YAxis < 55){
                //Move RIGHT...
                NavigationControls.rightButton.setEnabled(true);
                System.out.println("Moving RIGHT...");
                try {
                    if (ButtonHandler.buttonWasPressed == false)
                    {
                        ButtonHandler.buttonWasPressed = true;
                        Geometry.right();
                        ButtonHandler.buttonWasPressed = false;
                    }
                    //Thread.sleep(200);
                }
                //Gracefully catch any exceptions.
                catch (PortInUseException | IOException | InterruptedException | UnsupportedCommOperationException | NoSuchPortException ex) 
                {
                    System.out.println("RIGHT Button Failed");
                }
                NavigationControls.rightButton.setEnabled(false);

            }else if (YAxis >= 80 && YAxis <= 100){
                //Move AFT RIGHT...
                NavigationControls.aftRightButton.setEnabled(true);
                System.out.println("Moving AFT RIGHT...");
                try {
                    if (ButtonHandler.buttonWasPressed == false)
                    {
                        ButtonHandler.buttonWasPressed = true;
                        Geometry.backwardRight();
                        ButtonHandler.buttonWasPressed = false;
                    }
                    //Thread.sleep(200);
                }
                //Gracefully catch any exceptions.
                catch (PortInUseException | IOException | InterruptedException | UnsupportedCommOperationException | NoSuchPortException ex) 
                {
                    System.out.println("AFT RIGHT Button Failed");
                }
                NavigationControls.aftRightButton.setEnabled(false);

            }else {
                //Do nothing..
            }            
        }
    }

    /**
     * Given value of axis in percentage.
     * Percentages increases from left/top to right/bottom.
     * If idle (in center) returns 50, if joystick axis is pushed to the left/top 
     * edge returns 0 and if it's pushed to the right/bottom returns 100.
     * 
     * @return value of axis in percentage.
     */
    public static int getAxisValueInPercentage(float axisValue)
    {
        return (int)(((2 - (1 - axisValue)) * 100) / 2);
    }

    @Override
    public void run() 
    {
        while(!stopJoystick){
            JoystickThread();
            try {

                Thread.sleep(200);
                
            }
            //If interrupted exception is caught.
            catch (InterruptedException ex) 
            {
                System.out.println("Joystick Thread interrupted...");
            }
        }
        //System.out.println("Exiting Joystick Thread...");
    }
}

