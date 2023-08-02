package src;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.Map;

public class KeyListener implements NativeKeyListener {
    final NTPublisher ntPublisher;
    final Map<Integer, Integer> buttonMapping;
    final boolean debug;

    public KeyListener(
            final NTPublisher ntPublisher,
            final Map<Integer, Integer> buttonMapping,
            final boolean debug
    ) {
        this.ntPublisher = ntPublisher;
        this.buttonMapping = buttonMapping;
        this.debug = debug;

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
    }

    public void nativeKeyPressed(final NativeKeyEvent e) {
        if (debug) {
            System.out.println(e.getKeyCode());
        }

        final Integer state = buttonMapping.get(e.getKeyCode());
        if (state != null) {
            ntPublisher.publish(state);
        }
    }
}
