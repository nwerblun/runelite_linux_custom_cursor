package linuxcustomcursor;

import javax.inject.Inject;
import java.awt.Rectangle;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.events.BeforeRender;
import net.runelite.api.events.CanvasSizeChanged;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.GameState;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.customcursor.CustomCursorPlugin;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.game.ItemManager;


@PluginDescriptor(
	name = "Linux Custom Cursor",
	description = "Workaround for custom cursor plugin not working on Linux OS's",
	tags = {"cursor", "overlay", "linux", "custom-cursor"}
)
@PluginDependency(CustomCursorPlugin.class)
public class LinuxCustomCursorPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private LinuxCustomCursorConfig config;

	@Inject
	private LinuxCustomCursorOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ItemManager itemManager;


	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Provides
	LinuxCustomCursorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LinuxCustomCursorConfig.class);
	}

	@Subscribe
	public void onFocusChanged(FocusChanged f)
	{
		overlay.setDisableOverlay(!f.isFocused() || client.getGameState() != GameState.LOGGED_IN);
	}
}
