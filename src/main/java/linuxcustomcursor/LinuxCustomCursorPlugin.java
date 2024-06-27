package linuxcustomcursor;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;

import lombok.extern.slf4j.Slf4j;
import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.RuneLite;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.customcursor.CustomCursorPlugin;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.ClientUI;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;


@Slf4j
@PluginDescriptor(
	name = "Linux Custom Cursor",
	description = "Workaround for custom cursor plugin not working on Linux OS's",
	tags = {"cursor", "overlay", "linux"}
)
@PluginDependency(CustomCursorPlugin.class)
public class LinuxCustomCursorPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientUI clientUI;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ItemManager itemManager;

	@Inject
	private LinuxCustomCursorConfig config;

	@Inject
	private LinuxCustomCursorOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	protected final static int CURSOR_WIDTH = 32;
	protected final static int CURSOR_HEIGHT = 32;
	private static final File CUSTOM_IMAGE_FILE = new File(RuneLite.RUNELITE_DIR, "cursor.png");
	private BufferedImage invisibleCursor;
	private BufferedImage customCursor;


	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
		try
		{
			invisibleCursor = ImageUtil.loadImageResource(LinuxCustomCursorPlugin.class, "/invisible-cursor.png");
		}
		catch (Exception e)
		{
			invisibleCursor = null;
			log.error("Unable to load invisible cursor, plugin won't work as expected.", e);
		}

		try
		{
			synchronized (ImageIO.class)
			{
				customCursor = ImageIO.read(CUSTOM_IMAGE_FILE);
			}
		}
		catch (Exception e)
		{
			customCursor = null;
			log.error("Unable to load custom cursor file. This option will not work");
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		clientUI.resetCursor();
	}

	@Provides
	LinuxCustomCursorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LinuxCustomCursorConfig.class);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged state)
	{
		// Needed since overlay is disabled on login screen, and won't change until tabbing out and back in otherwise
		if (state.getGameState() == GameState.LOGGED_IN && config.selectedCursor() != LinuxCustomCursor.NONE)
		{
			overlay.setCursorImg(config.selectedCursor().getCursorImage());
			clientUI.setCursor(invisibleCursor, "Invisible Cursor");
		}
	}

	@Subscribe
	public void onFocusChanged(FocusChanged f)
	{
		if (f.isFocused() && client.getGameState() == GameState.LOGGED_IN)
		{
			overlay.setCursorImg(config.selectedCursor().getCursorImage());
			if (config.selectedCursor() != LinuxCustomCursor.NONE)
			{
				clientUI.setCursor(invisibleCursor, "Invisible Cursor");
			}
		}
		else
		{
			overlay.setCursorImg(null);
			clientUI.resetCursor();
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("linuxcustomcursor"))
		{
			if (event.getKey().equals("cursorStyle"))
			{
				updateCursor();
			}
		}
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged container)
	{
		if (config.selectedCursor() == LinuxCustomCursor.EQUIPPED_WEAPON &&
				container.getContainerId() == InventoryID.EQUIPMENT.getId())
		{
			updateCursor();
		}
	}

	private void updateCursor()
	{
		LinuxCustomCursor selectedCursor = config.selectedCursor();
		if (selectedCursor == LinuxCustomCursor.NONE)
		{
			overlay.setCursorImg(null);
			clientUI.resetCursor();
		}
		else if (selectedCursor == LinuxCustomCursor.CUSTOM_IMAGE)
		{
			if (customCursor != null)
			{
				overlay.setCursorImg(customCursor);
				clientUI.setCursor(invisibleCursor, "Invisible Cursor");
			}
			else
			{
				overlay.setCursorImg(null);
				clientUI.resetCursor();
			}
		}
		else if (selectedCursor == LinuxCustomCursor.EQUIPPED_WEAPON)
		{
			clientThread.invokeLater(() ->
			{
				final ItemContainer playerEquipment = client.getItemContainer(InventoryID.EQUIPMENT);
				if (playerEquipment == null) {
					overlay.setCursorImg(null);
					clientUI.resetCursor();
				}

				Item equippedWeapon = playerEquipment.getItem(EquipmentInventorySlot.WEAPON.getSlotIdx());
				// should short circuit if null
				if (equippedWeapon == null && equippedWeapon.getQuantity() <= 0)
				{
					overlay.setCursorImg(null);
					clientUI.resetCursor();
				}

				final BufferedImage weaponImage = itemManager.getImage(equippedWeapon.getId());
				overlay.setCursorImg(weaponImage);
				clientUI.setCursor(invisibleCursor, "Invisible Cursor");
			});
		}
		else
		{
			if (selectedCursor.getCursorImage() != null)
			{
				overlay.setCursorImg(selectedCursor.getCursorImage());
				clientUI.setCursor(invisibleCursor, "Invisible Cursor");
			}
		}
	}
}
