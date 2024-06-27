package linuxcustomcursor;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Keybind;

import java.awt.event.KeyEvent;

@ConfigGroup("linuxcustomcursor")
public interface LinuxCustomCursorConfig extends Config
{

	@ConfigSection(
			name = "General Settings",
			description = "Configuration for overall behavior",
			position = 1
	)
	String generalSection = "General Settings";

	@ConfigItem(
		position = 1,
		keyName = "hotkeyactivation",
		name = "Hotkey Activation<br>(disables auto-overlay)",
		description = "Activate the overlay via hotkey<br>Note that this will disable automatic overlay starting",
		section = generalSection
	)
	default boolean hotkeyActivationKeybindEnable()
	{
		return false;
	}

	@ConfigItem(
		position = 2,
		keyName = "hotkeybind",
		name = "Key For Activation",
		description = "Which key will activate the overlay",
		section = generalSection
	)
	default Keybind hotkeyActivationKeybind()
	{
		return new Keybind(KeyEvent.VK_T, KeyEvent.SHIFT_DOWN_MASK);
	}

	@ConfigSection(
		name = "Run Selection",
		description = "Configure which farming runs to provide hints on",
		position = 3
	)
	String runsSection = "Run Selection";

	@ConfigItem(
		position = 4,
		keyName = "enableallotmentherbs",
		name = "Allotment: Herbs",
		description = "Enable hints for herb planting in allotment patches",
		section = runsSection
	)
	default boolean enableAllotmentHerbRun()
	{
		return true;
	}

	@ConfigItem(
		position = 5,
		keyName = "enableallotmentcrops",
		name = "Allotment: Crops",
		description = "Enable hints for crop planting in allotment patches",
		section = runsSection
	)
	default boolean enableAllotmentCropRun()
	{
		return false;
	}

	@ConfigItem(
		position = 6,
		keyName = "enableallotmentflowers",
		name = "Allotment: Flowers",
		description = "Enable hints for flower planting in allotment patches",
		section = runsSection
	)
	default boolean enableAllotmentFlowerRun()
	{
		return false;
	}

	@ConfigItem(
		position = 7,
		keyName = "enabletrees",
		name = "Trees",
		description = "Enable hints for tree planting in tree patches",
		section = runsSection
	)
	default boolean enableTreeRun()
	{
		return true;
	}

	@ConfigItem(
		position = 8,
		keyName = "enablefruittrees",
		name = "Fruit Trees",
		description = "Enable hints for fruit tree planting in fruit tree patches",
		section = runsSection
	)
	default boolean enableFruitTreeRun()
	{
		return true;
	}

	@ConfigItem(
		position = 9,
		keyName = "enablehops",
		name = "Hops",
		description = "Enable hints for hops planting in hops patches",
		section = runsSection
	)
	default boolean enableHopsRun()
	{
		return false;
	}

	@ConfigItem(
		position = 10,
		keyName = "enablebushes",
		name = "Bushes",
		description = "Enable hints for bush planting in bush patches",
		section = runsSection
	)
	default boolean enableBushesRun()
	{
		return false;
	}

	@ConfigItem(
		position = 11,
		keyName = "enablespecial",
		name = "Special Patches (Not Implemented)",
		description = "Enable hints for special crop planting in special patches, such as spirit trees",
		section = runsSection
	)
	default boolean enableSpecialRun()
	{
		return false;
	}

	@ConfigSection(
		name = "Available Allotment Patches",
		description = "Configure which allotment patches to use or exclude",
		position = 12,
		closedByDefault = true
	)
	String availableAllotmentPatchesSection = "Allotment Patch Selection";

	@ConfigItem(
			position = 13,
			keyName = "enablefaladorallotment",
			name = "Falador Allotment",
			description = "Include Falador allotment patch in run helper",
			section = availableAllotmentPatchesSection
	)
	default boolean enableFaladorAllotmentUsage()
	{
		return true;
	}

	@ConfigItem(
		position = 14,
		keyName = "enablemorytaniaallotment",
		name = "Morytania Allotment",
		description = "Include Morytania allotment patch in run helper",
		section = availableAllotmentPatchesSection
	)
	default boolean enableMorytaniaAllotmentUsage()
	{
		return false;
	}

	@ConfigItem(
		position = 15,
		keyName = "enablecatherbyallotment",
		name = "Catherby Allotment",
		description = "Include Catherby allotment patch in run helper",
		section = availableAllotmentPatchesSection
	)
	default boolean enableCatherbyAllotmentUsage()
	{
		return true;
	}

	@ConfigItem(
		position = 16,
		keyName = "enableardougneallotment",
		name = "Ardougne Allotment",
		description = "Include Ardougne allotment patch in run helper",
		section = availableAllotmentPatchesSection
	)
	default boolean enableArdougneAllotmentUsage()
	{
		return true;
	}

	@ConfigItem(
		position = 17,
		keyName = "enablehosidiusallotment",
		name = "Hosidius Allotment",
		description = "Include Hosidius allotment patch in run helper",
		section = availableAllotmentPatchesSection
	)
	default boolean enableHosidiusAllotmentUsage()
	{
		return false;
	}

	@ConfigItem(
		position = 18,
		keyName = "enableharmonyislandallotment",
		name = "Harmony Island Allotment",
		description = "Include Harmony Island allotment patch in run helper",
		section = availableAllotmentPatchesSection
	)
	default boolean enableHarmonyIslandAllotmentUsage()
	{
		return false;
	}

	@ConfigItem(
		position = 19,
		keyName = "enablefarmingguildallotment",
		name = "Farming Guild Allotment",
		description = "Include Farming Guild allotment patch in run helper",
		section = availableAllotmentPatchesSection
	)
	default boolean enableFarmingGuildAllotmentUsage()
	{
		return false;
	}

	@ConfigItem(
		position = 20,
		keyName = "enableprifddinasallotment",
		name = "Prifddinas Allotment",
		description = "Include Prifddinas allotment patch in run helper",
		section = availableAllotmentPatchesSection
	)
	default boolean enablePrifddinasAllotmentUsage()
	{
		return false;
	}

	@ConfigItem(
			position = 21,
			keyName = "enablecivitasillafortisallotment",
			name = "Civitas illa Fortis Allotment",
			description = "Include Civitas illa Fortis allotment patch in run helper",
			section = availableAllotmentPatchesSection
	)
	default boolean enableCivitasIllaFortisAllotmentUsage()
	{
		return false;
	}
}
