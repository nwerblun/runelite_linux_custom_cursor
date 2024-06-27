package linuxcustomcursor;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class LinuxCustomCursorPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(LinuxCustomCursorPlugin.class);
		RuneLite.main(args);
	}
}