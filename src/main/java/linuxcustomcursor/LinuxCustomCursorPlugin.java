package linuxcustomcursor;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.AccessLevel;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.input.KeyManager;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Farm Run Helper"
)
public class LinuxCustomCursorPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private LinuxCustomCursorConfig config;

	@Inject
	private KeyManager keyManager;

	@Inject
	private FarmRunHelperKeyListener keyListener;

	@Inject
	private LinuxCustomCursorOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	@Getter(AccessLevel.PROTECTED)
	private boolean currentlyActive = false;

	@Override
	protected void startUp() throws Exception
	{
		keyManager.registerKeyListener(keyListener);
		overlayManager.add(overlay);
		log.info("Farm Run Helper started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		keyManager.unregisterKeyListener(keyListener);
		overlayManager.remove(overlay);
		log.info("Farm Run Helper stopped!");
	}

	@Provides
	LinuxCustomCursorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LinuxCustomCursorConfig.class);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
//			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Farm Run Helper says " + config.greeting(), null);
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Farm Run Helper is up ", null);
		}
	}

	protected void toggleActive()
	{
		currentlyActive = !currentlyActive;
	}

	protected boolean playerHasEnoughTreeSaplings()
	{
		int numSaplingsNeeded;
		int numSaplingsInInventory;
		int numSaplingsInBank;
		return false;
	}
}
