package linuxcustomcursor;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;


@ConfigGroup("linuxcustomcursor")
public interface LinuxCustomCursorConfig extends Config
{

	@ConfigItem(
		position = 1,
		keyName = "cursorStyle",
		name = "Cursor",
		description = "Select which cursor you wish to use"
	)
	default LinuxCustomCursor selectedCursor()
	{
		return LinuxCustomCursor.NONE;
	}
}
