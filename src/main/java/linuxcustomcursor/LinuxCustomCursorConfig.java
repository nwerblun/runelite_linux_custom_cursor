package linuxcustomcursor;

import net.runelite.client.config.*;


@ConfigGroup("linuxcustomcursor")
public interface LinuxCustomCursorConfig extends Config
{

	@ConfigSection(
			position = 1,
			name = "General Settings",
			description = "General Cursor Settings"
	)
	String generalSettingsSection = "General Settings";

	@ConfigItem(
		position = 2,
		name = "Cursor",
		keyName = "cursorStyle",
		description = "Select which cursor you wish to use",
		section = generalSettingsSection
	)
	default LinuxCustomCursor selectedCursor()
	{
		return LinuxCustomCursor.NONE;
	}

	@ConfigItem(
		position = 3,
		name = "Mirror Cursor Horizontally",
		keyName = "mirrorCursorX",
		description = "Flip cursor image across the Y axis. Useful for some equipped weapons or custom images.",
		section = generalSettingsSection
	)
	default boolean getMirrorCursorHorz() { return false; }

	@ConfigItem(
		position = 4,
		name = "Mirror Cursor Vertically",
		keyName = "mirrorCursorY",
		description = "Flip cursor image across the X axis. Useful for some equipped weapons or custom images.",
		section = generalSettingsSection
	)
	default boolean getMirrorCursorVert() { return false; }

	@ConfigSection(
			position = 5,
			name = "Configuration",
			description = "Options to tune the plugin"
	)
	String configSettingsSection = "Configuration";

	@Range(
			min = 0
	)
	@ConfigItem(
		position = 6,
		name = "Cursor Size Scaling",
		keyName = "systemCursorScale",
		description = "Factor to scale the size of the cursor. Numbers less than one will shrink the cursor. " +
				"If the cursor becomes too small, it will be reset to 1x.",
		section = configSettingsSection
	)
	@Units("x")
	default double getScaleFactor() { return 1f; }

	@ConfigItem(
		position = 7,
		name = "System Cursor Width",
		keyName = "systemCursorWidth",
		description = "Width of the 'image' used by your OS. " +
				"Default system cursor shapes are generally square like 24x24 or 32x32",
		section = configSettingsSection
	)
	@Units("px")
	default int getSystemCursorWidth()
	{
		return 24;
	}

	@ConfigItem(
		position = 8,
		name = "System Cursor Height",
		keyName = "systemCursorHeight",
		description = "Height of the 'image' used by your OS. " +
				"Default system cursor shapes are generally square like 24x24 or 32x32",
		section = configSettingsSection
	)
	@Units("px")
	default int getSystemCursorHeight()
	{
		return 24;
	}

	@ConfigItem(
		position = 9,
		name = "System Cursor Hotspot X Offset",
		keyName = "systemCursorHotSpotXOffset",
		description = "How far to the right is the 'click point' of your cursor. " +
				"Generally around 10 pixels on a 24x24 cursor.",
		section = configSettingsSection
	)
	@Units("px")
	default int getSystemCursorHotSpotXOffset()
	{
		return 10;
	}

	@ConfigItem(
		position = 10,
		name = "System Cursor Hotspot Y Offset",
		keyName = "systemCursorHotSpotYOffset",
		description = "How far down from the top is the 'click point' of your cursor. " +
				"Generally around 5 pixels on a 24x24 cursor.",
		section = configSettingsSection
	)
	@Units("px")
	default int getSystemCursorHotSpotYOffset()
	{
		return 5;
	}

	@ConfigSection(
		position = 11,
		name = "Debug Options",
		description = "Debug options, not meant to be used in most cases",
		closedByDefault = true
	)
	String debugSection = "Debug Section";

	@ConfigItem(
		position = 12,
		name = "Also draw system cursor",
		keyName = "drawSystemCursor",
		description = "Used to check alignment of clickboxes. " +
				"Draws both cursors instead of making the system cursor invisible",
		section = debugSection
	)
	default boolean debugEnableDrawSystemCursor()
	{
		return false;
	}
}
