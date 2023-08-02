import src.config.Settings;
import src.config.SettingsReader;
import com.github.kwhat.jnativehook.GlobalScreen;
import src.KeyListener;
import src.NTPublisher;

import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IOException {
        final Settings settings = new SettingsReader().getSettings();
        final NTPublisher ntPublisher = new NTPublisher(settings.getHostname());
        final KeyListener keyListener = new KeyListener(
                ntPublisher,
                settings.getButtonMapping(),
                args.length > 0 && args[0].equalsIgnoreCase("keycode")
        );
        GlobalScreen.addNativeKeyListener(keyListener);
    }
}
