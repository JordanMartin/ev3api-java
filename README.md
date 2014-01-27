ev3api-java
===========

This Java API allow to you control an EV3 over a serial port (which is emulated with bluetooth). It use the Java Simple Serial Connector (jSSC) library to access the serial port. It is compatible with Windows, Linux and Mac.

This API was written with the help of the microsoft API (in C#) and the source code of the brick (see links bellow)


What this API can do ?
======================

 - Control the motors
    * Start at specified power
    * Start during x milliseconds
    * Turn during x degrees
    * Synchronise rotation of two motors for x degrees
    * Get the value of the internal tacho counter (number of degrees made by the motors)
    * Reset the value of the tacho counter


 - Retrieve the value of sensors
    * Ultrasonic 
    * Gyroscope
    * HiTechnic compass (two degrees precision for now...)

For the moment only these sensors are implemeted but if you look at what has already been done you will be able to easily add another.

 - Other 
    * Play a tone 



TODO
====

 - Implements USB and WIFI communication
 - Implements more sensors 



Links
=====
 - C# API : http://legoev3.codeplex.com/
 - Source code of the brick  :  https://github.com/mindboards/ev3sources
