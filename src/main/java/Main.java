import com.github.kwhat.jnativehook.GlobalScreen;
import src.KeyListener;
import src.NTPublisher;
import src.config.Settings;
import src.config.SettingsReader;

import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IOException {
        final Settings settings = new SettingsReader().getSettings();
        final NTPublisher ntPublisher = new NTPublisher(
                settings.getHostname(),
                settings.getNetworkTable(),
                settings.getPublishTopic()
        );
        // There is an option to pass in "keycode" as the first argument to the program to have it
        // print the keys pressed so that you can find the keycodes for the buttons you want to use.
        final KeyListener keyListener = new KeyListener(
                ntPublisher,
                settings.getButtonMapping(),
                args.length > 0 && args[0].equalsIgnoreCase("keycode")
        );
        GlobalScreen.addNativeKeyListener(keyListener);
    }
}
