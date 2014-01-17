package core;

/**
 * Constante for EV3 communication
 * @author Jordan
 */
public class Enums {
    

	static class ArgumentSize
	{
		final int Byte = 0x81;	// 1 byte
		final int Short = 0x82;	// 2 bytes
		final int Int = 0x83;	// 4 bytes
		final int String = 0x84; // null-terminated string
	}

	static class ReplyType
	{
		final int DirectReply = 0x02;
		final int SystemReply = 0x03;
		final int DirectReplyError = 0x04;
		final int SystemReplyError = 0x05;
	}

	static class Opcode
	{
		final int UIRead_GetFirmware = 0x810a;

		final int UIWrite_LED = 0x821b;

		final int UIButton_Pressed = 0x8309;

		final int UIDraw_Update = 0x8400;
		final int UIDraw_Clean = 0x8401;
		final int UIDraw_Pixel = 0x8402;
		final int UIDraw_Line = 0x8403;
		final int UIDraw_Circle = 0x8404;
		final int UIDraw_Text = 0x8405;
		final int UIDraw_FillRect = 0x8409;
		final int UIDraw_Rect = 0x840a;
		final int UIDraw_InverseRect = 0x8410;
		final int UIDraw_SelectFont = 0x8411;
		final int UIDraw_Topline = 0x8412;
		final int UIDraw_FillWindow = 0x8413;
		final int UIDraw_DotLine = 0x8415;
		final int UIDraw_FillCircle = 0x8418;
		final int UIDraw_BmpFile = 0x841c;

		final int Sound_Break = 0x9400;
		final int Sound_Tone = 0x9401;
                final int Sound_Play = 0x9402;
		final int Sound_Repeat = 0x9403;
		final int Sound_Service = 0x9404;

		final int InputDevice_GetTypeMode = 0x9905;
		final int InputDevice_GetDeviceName = 0x9915;
		final int InputDevice_GetModeName = 0x9916;
		final int InputDevice_ReadyPct = 0x991b;
		final int InputDevice_ReadyRaw = 0x991c;
		final int InputDevice_ReadySI = 0x991d;
		final int InputDevice_ClearAll = 0x990a;
		final int InputDevice_ClearChanges = 0x991a;

		final int InputRead = 0x9a;
		final int InputReadExt = 0x9e;
		final int InputReadSI = 0x9d;

		final int OutputStop = 0xa3;
		final int OutputPower = 0xa4;
		final int OutputSpeed = 0xa5;
		final int OutputStart = 0xa6;
		final int OutputPolarity = 0xa7;
		final int OutputStepPower = 0xac;
		final int OutputTimePower = 0xad;
		final int OutputStepSpeed = 0xae;
		final int OutputTimeSpeed = 0xaf;
		final int OutputStepSync = 0xb0;
		final int OutputTimeSync = 0xb1;

		final int Tst = 0xff;
	}

	static class SystemOpcode
	{
		final int BeginDownload = 0x92;
		final int ContinueDownload = 0x93;
		final int CloseFileHandle = 0x98;
		final int CreateDirectory = 0x9b;
		final int DeleteFile = 0x9c;
	}

	enum SystemReplyStatus
	{
		Success,
		UnknownHandle,
		HandleNotReady,
		CorruptFile,
		NoHandlesAvailable,
		NoPermission,
		IllegalPath,
		FileExists,
		EndOfFile,
		SizeError,
		UnknownError,
		IllegalFilename,
		IllegalConnection
	}

	
	// The type of command being sent to the brick
	
	static class CommandType
	{		
		// Direct command with a reply expected
		final int DirectReply = 0x00;
		
		// Direct command with no reply
		final int DirectNoReply = 0x80;

		
		//  System command with a reply expected
		final int SystemReply = 0x01;
		
		// System command with no reply
		final int SystemNoReply = 0x81;
	}

	
	// Format for sensor data.
	
	static class Format
	{
		// Percentage
		final int Percent = 0x10;
		
		// Raw
		final int Raw = 0x11;
		
		// International System of Units
		final int SI = 0x12;
	}

	
	// Polarity/direction to turn the motor
	
	static class Polarity
	{
		
		// Turn backward
		final int Backward = -1;
		
		// Turn in the opposite direction
		final int Opposite = 0;
		
		// Turn forward
		final int Forward = 1;
	}

	
	// Ports which can receive input data
	static class InputPort
	{
		
		// Port 1
		final int One   = 0x00;
		
		// Port 2
		final int Two   = 0x01;
		
		// Port 3
		final int Three	= 0x02;
		
		// Port 4
		final int Four	= 0x03;

		
		// Port A
		final int A = 0x10;
		
		// Port B
		final int B = 0x11;
		
		// Port C
		final int C = 0x12;
		
		// Port D
		final int D = 0x13;
	}

	
	// Ports which can send output
	static class OutputPort
	{
		
		// Port A
		final int A	= 0x01;
		
		// Port B
		final int B	= 0x02;
		
		// Port C
		final int C	= 0x04;
		
		// Port D
		final int D	= 0x08;
		
		// Ports A;B;C and D simultaneously
		final int All	= 0x0f;
	}

	
	// List of devices which can be recognized as input or output devices
	
	static class DeviceType
	{
		// NXT devices
		
		// NXT Touch sensor
		final int NxtTouch = 1;
		
		// NXT Light sensor
		final int NxtLight = 2;
		
		// NXT Sound sensor
		final int NxtSound = 3;
		
		// NXT Color sensor
		final int NxtColor = 4;
		
		// NXT Ultrasonic sensor
		final int NxtUltrasonic = 5;
		
		//  NXT Temperature sensor
		final int NxtTemperature = 6;

		// 2 motors
		
		// Large motor
		final int LMotor = 7;
		
		// Medium motor
		final int MMotor = 8;

		// Ev3 devices
		
		// EV3 Touch sensor
		final int Touch = 16;
		
		// EV3 Color sensor
		final int Color = 29;
		
		// EV3 Ultrasonic sensor
		final int Ultrasonic = 30;
		
		// EV3 Gyroscope sensor
		final int Gyroscope = 32;
		
		// EV3 IR sensor
		final int Infrared = 33;

		// other
		
		// Sensor is initializing
		final int Initializing = 0x7d;
		
		// Port is empty
		final int Empty = 0x7e;
		
		// Sensor is plugged into a motor port; or vice-versa
		final int WrongPort = 0x7f;
		
		// Unknown sensor/status
		final int Unknown = 0xff;
	}

	
	// Buttons on the face of the EV3 brick
	
	enum BrickButton
	{		
		// No button
		None,
		
		// Up button
		Up,
		
		// Enter button
		Enter,
		
		// Down button
		Down,
		
		// Right button
		Right,
                
		// Left button		
		Left,
                
		// Back button		
		Back,
                
		// Any button		
		Any
	}

	
	// Pattern to light up the EV3 brick's LED
	
	enum  LedPattern
	{		
		// LED off		
		Black,
		
		// Solid green		
		Green,
		
		// Solid red		
		Red,
		
		// Solid orange		
		Orange,
		
		// Flashing green		
		GreenFlash,
		
		// Flashing red		
		RedFlash,
		
		// Flashing orange		
		OrangeFlash,
		
		// Pulsing green		
		GreenPulse,
		
		// Pulsing red		
		RedPulse,
		
		// Pulsing orange		
		OrangePulse
	}

	
	// UI colors
	
	enum  Color
	{		
		// Color of the background		
		Background,
		
		// Color of the foreground		
		Foreground
	}

	
	// Font types for drawing text to the screen
	
	enum  FontType
	{		
		// Small font		
		Small,
		
		// Medium font		
		Medium,
		
		// Large font		
		Large
	}

	
	// NXT and EV3 Touch Sensor mode
	
	enum  TouchMode
	{		
		// On when pressed, off when released		
		Touch,
		
		// Running counter of number of presses		
		Bumps
	}

	
	// NXT Light Sensor mode
	
	enum  NxtLightMode
	{		
		// Amount of reflected light		
		Reflect,
		
		// Amoutn of ambient light		
		Ambient
	}

	
	// NXT Sound Sensor mode
	
	enum  NxtSoundMode
	{		
		// Decibels		
		Decibels,
		
		// Adjusted Decibels		
		AdjustedDecibels
	}

	
	// NXT Color Sensor mode
	
	enum  NxtColorMode
	{		
		// Reflected color		
		Reflective,
		
		// Ambient color		
		Ambient,
		
		// Specific color		
		Color,
		
		// Amount of green		
		Green,
		
		// Amount of blue		
		Blue,
		
		// Raw sensor value		
		Raw
	}

	
	// NXT Ultrasonic Sensor mode
	
	enum  NxtUltrasonicMode
	{		
		// Values in centimeter units		
		Centimeters,
		
		// Values in inch units		
		Inches
	}

	
	// NXT Temperature Sensor mode
	
	enum  NxtTemperatureMode
	{		
		// Values in Celsius units		
		Celsius,
		
		// Values in Fahrenheit units		
		Fahrenheit,
	}

	
	// Motor mode
	
	enum  MotorMode
	{
		// Values in degrees		
		Degrees,
		
		// Values in rotations		
		Rotations,
		
		// Values in percentage		
		Percent
	}

	
	// EV3 Color Sensor mode
	
	enum  ColorMode
	{		
		// Reflected color		
		Reflective,
		
		// Ambient color		
		Ambient,
		
		// Specific color		
		Color,
		
		// Reflected color raw value		
		ReflectiveRaw,
		
		// Reflected color RGB value		
		ReflectiveRgb,
		
		// Calibration		
		Calibration // TODO: ??
	}

	
	// EV3 Ultrasonic Sensor mode
	
	enum  UltrasonicMode
	{		
		// Values in centimeter units		
		Centimeters,
		
		// Values in inch units		
		Inches,
		
		// Listen mode		
		Listen,
		
		// Unknown		
		SiCentimeters,
		
		// Unknown		
		SiInches,
		
		// Unknown		
		DcCentimeters,	// TODO: DC?
		
		// Unknown		
		DcInches		// TODO: DC?
	}

	
	// EV3 Gyroscope Sensor mode
	
	enum  GyroscopeMode
	{		
		// Angle		
		Angle,
		
		// Rate of movement		
		Rate,
		
		// Unknown		
		Fas,		// TOOD: ??
		
		// Unknown		
		GandA,		// TODO: ??
		
		// Calibrate		
		Calibrate
	}

	
	// EV3 Infrared Sensor mode
	
	enum  InfraredMode
	{
		
		// Proximity		
		Proximity,
		
		// Seek		
		Seek,
		
		// EV3 remote control		
		Remote,
		
		// Unknown		
		RemoteA,	// TODO: ??
		
		// Unknown
		SAlt,		// TODO: ??
                
		//  Calibrate
		Calibrate
	}

	// Values returned by the color sensor
	enum  ColorSensorColor
	{
		Transparent,
		Black,
		Blue,
		Green,
		Yellow,
		Red,
		White,
		Brown
	}
}
