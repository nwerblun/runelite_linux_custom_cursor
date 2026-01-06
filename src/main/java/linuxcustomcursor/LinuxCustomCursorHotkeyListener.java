package linuxcustomcursor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.util.HotkeyListener;
import javax.inject.Inject;

@Slf4j
public class LinuxCustomCursorHotkeyListener extends HotkeyListener {

    @Setter
    private LinuxCustomCursorOverlay overlay;

    @Inject
    private LinuxCustomCursorHotkeyListener(RuneLiteConfig runeLiteConfig) {
        super(runeLiteConfig::dragHotkey);
    }

    @Override
    public void hotkeyPressed() {
        if(overlay != null) { this.overlay.setDisableOverlay(true); }
    }

    @Override
    public void hotkeyReleased() {
        if(overlay != null) { this.overlay.setDisableOverlay(false); }
    }
}
