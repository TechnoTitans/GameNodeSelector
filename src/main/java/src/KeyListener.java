package src;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.Map;

public class KeyListener implements NativeKeyListener {
    final NTListener ntListener;
    final Map<Integer, Integer> buttonMapping;
    final boolean debug;

    public KeyListener(
            final NTListener ntListener,
            final Map<Integer, Integer> buttonMapping,
            final boolean debug
    ) {
        this.ntListener = ntListener;
        this.buttonMapping = buttonMapping;
        this.debug = debug;


        //I think there is an issue when using driverstation AND in sim I think the DS reports a dif host IP and thus it publishes
        // to the wrong thing. I'm not 100% sure but if that isn't the issue then what might be going on is that the DS is
        // hooking the keyboard at the same time as this but since it has admin right it might be overriding this one, thus
        // making this unable to listen to the keyboard.
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
            ntListener.publish(state);
        }
    }
}
