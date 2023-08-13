import com.formdev.flatlaf.FlatDarkLaf;
import com.github.kwhat.jnativehook.GlobalScreen;
import src.KeyListener;
import src.NTListener;
import src.ui.UI;

public class Main {
    public static void main(final String[] args) {
        FlatDarkLaf.setup();

        final NTListener ntListener = new NTListener();
        // There is an option to pass in "keycode" as the first argument to the program to have it
        // print the keys pressed so that you can find the keycodes for the buttons you want to use.
        final KeyListener keyListener = new KeyListener(
                ntListener,
                args.length > 0 && args[0].equalsIgnoreCase("keycode")
        );
        GlobalScreen.addNativeKeyListener(keyListener);

        new UI(ntListener);
    }
}
