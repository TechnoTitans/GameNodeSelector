import com.formdev.flatlaf.FlatDarkLaf;
import com.github.kwhat.jnativehook.GlobalScreen;
import src.KeyListener;
import src.NTListener;
import src.config.Settings;
import src.config.SettingsReader;
import src.ui.UI;

import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IOException, InterruptedException {
        FlatDarkLaf.setup();

        final Settings settings = new SettingsReader().getSettings();
        final NTListener ntListener = new NTListener(settings);
        // There is an option to pass in "keycode" as the first argument to the program to have it
        // print the keys pressed so that you can find the keycodes for the buttons you want to use.
        final KeyListener keyListener = new KeyListener(
                ntListener,
                settings.getButtonMapping(),
                args.length > 0 && args[0].equalsIgnoreCase("keycode")
        );
        GlobalScreen.addNativeKeyListener(keyListener);

        new UI(ntListener);
    }
}
