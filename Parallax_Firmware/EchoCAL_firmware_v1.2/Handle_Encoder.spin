'' Handle_Encoder.spin
''
'' This section of code will process the SigA and SigB signals coming from an encoder.  SigA and SigB are pulsed DC signals which
'' are normally low, and will pulse to 5 VDC as the magnet passes over the sensor.
''
'' Theory:
'' The encoder for this project is a Hall Effect sensor with Quadrature Phase 
''
'' Revision History:
''      Version 1.00 :  17 April 2012
''          1. Initial Release by:
''                Joseph M. Godlewski
''                Electronics Engineer
''                NOAA/NMFS, Northeast Fisheries Science Center
''                166 Water Street, Woods Hole, MA  02543
''                Telephone # (508) 495-2039
''                Email:  Joseph.Godlewski@noaa.gov
''
CON
  iIN    = 1
  iOUT   = 0

{ --------------------  Direction Indicators... ------------------------}    
  indOUT = 3                    'Red LED showing motor is being driven in the outward direction.
  indIN  = 4                    'Green LED showing motor is being driven in the inward direction.

VAR
            
  long  stack[50]               'Stack space for new cog.
  long  cog                     'cog flag/id
  long  pulseCNT                'Number of pulses received
  byte  prev_state              'Holds the previous "state" of the signal pulses. ie %00, %01, etc...
  byte  state                   'Holds the current "state" of the signal pulses. ie %00, %01, etc...
  byte  prev_dir                'Holds the direction of the motor rotation. "1" = IN, "0" = OUT.
  byte  dir                     'Holds the direction of the motor rotation. "1" = IN, "0" = OUT.
  byte  tick                    'Holds the number of states that the motor rotation transitioned through
                                'Note: Four "ticks" represents that the magnet has completely passed through the encoder
                                '      range.
  byte  lockID                  'ID of the mutually exclusive lock placed on shared variable resources.

PUB start(sigA_pin, sigB_pin) : okay
''
'' Start Handle_Encoder - starts a cog
'' returns false if no cog available
''

  stop
  okay := cog := cognew(monitorENC(sigA_pin, sigB_pin), @stack) + 1

  return
                                       
PUB stop
''
'' Stop Handle_Encoder - frees a cog

  if cog
    cogstop(cog~ - 1)

  return

PUB readCNT : count
''
'' Read the current pulse count and return it to caller.
''
  count := pulseCNT

  return

PUB zeroCNT : count
''
'' Zero the current pulse count and return it to caller.
''
  pulseCNT := 0
  count := pulseCNT

  return

PUB updateCNT(newCNT) : count
''
'' Update the current pulse count and return it to caller.
''
  pulseCNT := newCNT
  count := pulseCNT

  return

PUB readDIR : direction
''                            
'' Read the current pulse count and return it to caller.
''
  direction := dir

  return

PUB readTICK : tickcount
''
'' Read the current TICK count and return it to caller.
'' Note: For DEBUG purposes only. Not needed during operational use.
''
  tickcount := tick

  return

PUB readSTATE : currentSTATE
''
'' Read the current "state" variable and return it to caller.
'' Note: For DEBUG purposes only. Not needed during operational use.
''
  currentSTATE := state

  return

PUB readPSTATE : previousSTATE
''
'' Read the current "state" variable and return it to caller.
'' Note: For DEBUG purposes only. Not needed during operational use.
''
  previousSTATE := prev_state

  return

PRI monitorENC(sigApin, sigBpin) | revDIR
''
'' Monitor encoder signal A and signal B pins, and update direction "dir" and count "pulseCNT" variables.
''
  dira[sigApin..sigBpin]~                               'Set the direction of sigA and sigB pins as inputs.
  prev_state := ina[sigApin..sigBpin]                   'Initialize the "prev_State" variable.
  tick := 0                                             'Initialize "tick" count to zero.
  pulseCNT := 0                                         'Initialize "pulseCNT" variable to zero.
  dir := iOUT                                           'Initialize the "dir" variable to indicate OUT direction.
  prev_dir := dir                                       'Assume the previous direction == current direction to start.
  repeat
    state := ina[sigApin..sigBpin]                      'Read the input state of the encoder signals.
    case state
      %00:                                              'Process state "00".
        if state <> prev_state                          'If the new state is different than the previous state,
          case prev_state                               'then the motor has moved and we should process this movement.
              %10:                                      'For case "10", the motor has moved in the CCW (OUT) direction.
                  dir := iOUT                           'Indicate that the movement is in the OUT direction.
                  directionLED(dir)                     'Illuminate the proper LED in relation to the direction that the
                                                        'motor is turning.
                  prev_state := state                   'Update the "state" variable.
                  tick := tick + 1                      'Increment "tick" count.
                  prev_dir := dir                       'Update "prev_dir"
                  if tick == 4
                      pulseCNT := pulseCNT + 1          'Increment number of pulses seen. Motor is moving OUT direction.                    

              %01:                                      'For case "01", the motor has moved in the CW (IN) direction.
                  dir := iIN                            'Indicate that the movement is in the IN direction.
                  directionLED(dir)                     'Illuminate the proper LED in relation to the direction that the
                                                        'motor is turning.
                  prev_state := state                   'Update the "state" variable.
                  tick := tick + 1                      'Increment "tick" count.
                  prev_dir := dir                       'Update "prev_dir"
                  if tick == 4
                      pulseCNT := pulseCNT - 1          'Decrement number of pulses seen. Motor is moving IN direction.                    

              OTHER:
'<--------------- Do nothing and fall through CASE statement.------------------->
        tick := 0                                       'Reset "tick" count to zero. We are in-between encoder pulses.

      %01:                                              'Process state "01".
        if state <> prev_state                          'If the new state is different than the previous state,
          case prev_state                               'then the motor has moved and we should process this movement.
              %00:                                      'For case "00", the motor has moved in the CCW (OUT) direction.
                  dir := iOUT                           'Indicate that the movement is in the OUT direction.
                  directionLED(dir)                     'Illuminate the proper LED in relation to the direction that the
                                                        'motor is turning.
                  prev_state := state                   'Update the "state" variable.

                  tick := tick + 1                      'Increment "tick" count.

                  prev_dir := dir                       'Update "prev_dir"
                  revDIR := FALSE                       'Reset "revDIR" flag to indicate we have not changed motor direction.

              %11:                                      'For case "11", the motor has moved in the CW (IN) direction.
                  dir := iIN                            'Indicate that the movement is in the IN direction.
                  directionLED(dir)                     'Illuminate the proper LED in relation to the direction that the
                                                        'motor is turning.
                  prev_state := state                   'Update the "state" variable.
                  if (dir == prev_dir) AND (NOT revDIR) 'If the current direction is same as previous, then increment "tick".
                      tick := tick + 1                  'Increment "tick" count.
                  else
                      revDIR := TRUE                    'Indicate that motor direction has changed.
                      tick := tick - 1                  'Decrement "tick" count.
                  prev_dir := dir                       'Update "prev_dir"


              OTHER:
'<--------------- Do nothing and fall through CASE statement.------------------->

      %11:                                              'Process state "11".
        if state <> prev_state                          'If the new state is different than the previous state,
          case prev_state                               'then the motor has moved and we should process this movement.
              %01:                                      'For case "01", the motor has moved in the CCW (OUT) direction.
                  dir := iOUT                           'Indicate that the movement is in the OUT direction.
                  directionLED(dir)                     'Illuminate the proper LED in relation to the direction that the
                                                        'motor is turning.
                  prev_state := state                   'Update the "state" variable.
                  if (dir == prev_dir) AND (NOT revDIR) 'If the current direction is same as previous, then increment "tick".
                      tick := tick + 1                  'Increment "tick" count.
                  else
                      revDIR := TRUE                    'Indicate that motor direction has changed.
                      tick := tick - 1                  'Decrement "tick" count.
                  prev_dir := dir                       'Update "prev_dir"

              %10:                                      'For case "10", the motor has moved in the CW (IN) direction.
                  dir := iIN                            'Indicate that the movement is in the IN direction.
                  directionLED(dir)                     'Illuminate the proper LED in relation to the direction that the
                                                        'motor is turning.
                  prev_state := state                   'Update the "state" variable.
                  if (dir == prev_dir) AND (NOT revDIR) 'If the current direction is same as previous, then increment "tick".
                      tick := tick + 1                  'Increment "tick" count.
                  else
                      revDIR := TRUE                    'Indicate that motor direction has changed.
                      tick := tick - 1                  'Decrement "tick" count.
                  prev_dir := dir                       'Update "prev_dir"

              OTHER:
'<--------------- Do nothing and fall through CASE statement.------------------->

      %10:                                              'Process state "10".
        if state <> prev_state                          'If the new state is different than the previous state,
          case prev_state                               'then the motor has moved and we should process this movement.
              %11:                                      'For case "11", the motor has moved in the CCW (OUT) direction.
                  dir := iOUT                           'Indicate that the movement is in the OUT direction.
                  directionLED(dir)                     'Illuminate the proper LED in relation to the direction that the
                                                        'motor is turning.
                  prev_state := state                   'Update the "state" variable.
                  if (dir == prev_dir) AND (NOT revDIR) 'If the current direction is same as previous, then increment "tick".
                      tick := tick + 1                  'Increment "tick" count.
                  else
                      revDIR := TRUE                    'Indicate that motor direction has changed.
                      tick := tick - 1                  'Decrement "tick" count.
                  prev_dir := dir                       'Update "prev_dir"

              %00:                                      'For case "00", the motor has moved in the CW (IN) direction.
                  dir := iIN                            'Indicate that the movement is in the IN direction.
                  directionLED(dir)                     'Illuminate the proper LED in relation to the direction that the
                                                        'motor is turning.
                  prev_state := state                   'Update the "state" variable.

                  tick := tick + 1                      'Increment "tick" count.

                  prev_dir := dir                       'Update "prev_dir"
                  revDIR := FALSE                       'Reset "revDIR" flag to indicate we have not changed motor direction.


              OTHER:
'<--------------- Do nothing and fall through CASE statement.------------------->

  return
    
PUB  directionLED(iDirection)
''    This procedure will illuminate one of the LEDs on the circuit board depending on which way the downrigger motor
'' is turning.  If the motor is turning CCW (ie. letting out monofillament), the RED LED will be turned on. If the
'' motor is turning in the CW direction (ie. bringing in monofillament), the GREEN LED will be turned on.
''
  DIRA[indOUT..indIN]~~                                 'Set direction LED control pins to OUTPUT.

  case iDirection
    0:
      OUTA[indOUT..indIN] := %10                        'Set indOUT pin HI. This will turn on the RED LED.

    1:
      OUTA[indOUT..indIN] := %01                        'Set indIN pin HI. This will turn on the GREENLED.

    OTHER:
      OUTA[indOUT..indIN] := %00                        'Set both pins LO. Turn off the LEDs.

  return