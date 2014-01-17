package core;

/**
 * Constante for EV3 communication
 * @author Jordan
 */
public class EV3Types {
    

	static class ArgumentSize
	{
		static final int Byte = 0x81;	// 1 byte
		static final int Short = 0x82;	// 2 bytes
		static final int Int = 0x83;	// 4 bytes
		static final int String = 0x84; // null-terminated string
	}

	static class ReplyType
	{
		static final int DirectReply = 0x02;
		static final int SystemReply = 0x03;
		static final int DirectReplyError = 0x04;
		static final int SystemReplyError = 0x05;
	}

	static class Opcode
	{
		static final int UIRead_GetFirmware = 0x810a;

		static final int UIWrite_LED = 0x821b;

		static final int UIButton_Pressed = 0x8309;

		static final int UIDraw_Update = 0x8400;
		static final int UIDraw_Clean = 0x8401;
		static final int UIDraw_Pixel = 0x8402;
		static final int UIDraw_Line = 0x8403;
		static final int UIDraw_Circle = 0x8404;
		static final int UIDraw_Text = 0x8405;
		static final int UIDraw_FillRect = 0x8409;
		static final int UIDraw_Rect = 0x840a;
		static final int UIDraw_InverseRect = 0x8410;
		static final int UIDraw_SelectFont = 0x8411;
		static final int UIDraw_Topline = 0x8412;
		static final int UIDraw_FillWindow = 0x8413;
		static final int UIDraw_DotLine = 0x8415;
		static final int UIDraw_FillCircle = 0x8418;
		static final int UIDraw_BmpFile = 0x841c;

		static final int Sound_Break = 0x9400;
		static final int Sound_Tone = 0x9401;
                static final int Sound_Play = 0x9402;
		static final int Sound_Repeat = 0x9403;
		static final int Sound_Service = 0x9404;

		static final int InputDevice_GetTypeMode = 0x9905;
		static final int InputDevice_GetDeviceName = 0x9915;
		static final int InputDevice_GetModeName = 0x9916;
		static final int InputDevice_ReadyPct = 0x991b;
		static final int InputDevice_ReadyRaw = 0x991c;
		static final int InputDevice_ReadySI = 0x991d;
		static final int InputDevice_ClearAll = 0x990a;
		static final int InputDevice_ClearChanges = 0x991a;

		static final int InputRead = 0x9a;
		static final int InputReadExt = 0x9e;
		static final int InputReadSI = 0x9d;

		static final int OutputStop = 0xa3;
		static final int OutputPower = 0xa4;
		static final int OutputSpeed = 0xa5;
		static final int OutputStart = 0xa6;
		static final int OutputPolarity = 0xa7;
		static final int OutputStepPower = 0xac;
		static final int OutputTimePower = 0xad;
		static final int OutputStepSpeed = 0xae;
		static final int OutputTimeSpeed = 0xaf;
		static final int OutputStepSync = 0xb0;
		static final int OutputTimeSync = 0xb1;

		static final int Tst = 0xff;
	}

	static class SystemOpcode
	{
		static final int BeginDownload = 0x92;
		static final int ContinueDownload = 0x93;
		static final int CloseFileHandle = 0x98;
		static final int CreateDirectory = 0x9b;
		static final int DeleteFile = 0x9c;
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
		static final int DirectReply = 0x00;
		
		// Direct command with no reply
		static final int DirectNoReply = 0x80;
		
		//  System command with a reply expected
		static final int SystemReply = 0x01;
		
		// System command with no reply
		static final int SystemNoReply = 0x81;
	}

	
	// Format for sensor data.
	
	static class Format
	{
		// Percentage
		static final int Percent = 0x10;
		
		// Raw
		static final int Raw = 0x11;
		
		// International System of Units
		static final int SI = 0x12;
	}

	
	// Polarity/direction to turn the motor
	
	static class Polarity
	{
		
		// Turn backward
		static final int Backward = -1;
		
		// Turn in the opposite direction
		static final int Opposite = 0;
		
		// Turn forward
		static final int Forward = 1;
	}

	
	// Ports which can receive input data
	static class InputPort
	{
		
		// Port 1
		static final int One   = 0x00;
		
		// Port 2
		static final int Two   = 0x01;
		
		// Port 3
		static final int Three	= 0x02;
		
		// Port 4
		static final int Four	= 0x03;

		
		// Port A
		static final int A = 0x10;
		
		// Port B
		static final int B = 0x11;
		
		// Port C
		static final int C = 0x12;
		
		// Port D
		static final int D = 0x13;
	}

	
	// Ports which can send output
	static class OutputPort
	{
		
		// Port A
		static final int A	= 0x01;
		
		// Port B
		static final int B	= 0x02;
		
		// Port C
		static final int C	= 0x04;
		
		// Port D
		static final int D	= 0x08;
		
		// Ports A;B;C and D simultaneously
		static final int All	= 0x0f;
	}

	
	// List of devices which can be recognized as input or output devices
	
	static class DeviceType
	{
		// NXT devices
		
		// NXT Touch sensor
		static final int NxtTouch = 1;
		
		// NXT Light sensor
		static final int NxtLight = 2;
		
		// NXT Sound sensor
		static final int NxtSound = 3;
		
		// NXT Color sensor
		static final int NxtColor = 4;
		
		// NXT Ultrasonic sensor
		static final int NxtUltrasonic = 5;
		
		//  NXT Temperature sensor
		static final int NxtTemperature = 6;

		// 2 motors
		
		// Large motor
		static final int LMotor = 7;
		
		// Medium motor
		static final int MMotor = 8;

		// Ev3 devices
		
		// EV3 Touch sensor
		static final int Touch = 16;
		
		// EV3 Color sensor
		static final int Color = 29;
		
		// EV3 Ultrasonic sensor
		static final int Ultrasonic = 30;
		
		// EV3 Gyroscope sensor
		static final int Gyroscope = 32;
		
		// EV3 IR sensor
		static final int Infrared = 33;

		// other
		
		// Sensor is initializing
		static final int Initializing = 0x7d;
		
		// Port is empty
		static final int Empty = 0x7e;
		
		// Sensor is plugged into a motor port; or vice-versa
		static final int WrongPort = 0x7f;
		
		// Unknown sensor/status
		static final int Unknown = 0xff;
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
