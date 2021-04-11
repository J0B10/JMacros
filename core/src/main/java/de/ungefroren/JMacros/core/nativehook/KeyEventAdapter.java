package de.ungefroren.JMacros.core.nativehook;

import com.github.kwhat.jnativehook.NativeInputEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import de.ungefroren.JMacros.core.utils.KeyAdapter;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

class KeyEventAdapter implements NativeKeyListener {

    public static final Logger logger = LoggerFactory.getLogger(KeyEventAdapter.class);

    private final EventType<KeyEvent> eventType;
    private final EventHandler<KeyEvent> fxKeyEventHandler;

    KeyEventAdapter(EventType<KeyEvent> eventType, EventHandler<KeyEvent> fxKeyEventHandler) {
        this.eventType = eventType;
        this.fxKeyEventHandler = fxKeyEventHandler;
    }

    private KeyEvent convert(EventType<KeyEvent> eventType, NativeKeyEvent nativeKeyEvent) {
        KeyCode code = KeyAdapter.JNativeHook.map(nativeKeyEvent.getKeyCode());
        String keyChar = String.valueOf(nativeKeyEvent.getKeyChar());
        String text = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
        int modifiers = nativeKeyEvent.getModifiers();
        boolean shiftDown = (modifiers & NativeInputEvent.SHIFT_MASK) != 0;
        boolean controlDown = (modifiers & NativeInputEvent.CTRL_MASK) != 0;
        boolean altDown = (modifiers & NativeInputEvent.ALT_MASK) != 0;
        boolean metaDown = (modifiers & NativeInputEvent.META_MASK) != 0;
        return new KeyEvent(eventType, keyChar, text, code, shiftDown, controlDown, altDown, metaDown);
    }

    private void consumeEvent(NativeInputEvent event) {
        try {
            final Method m = NativeInputEvent.class.getDeclaredMethod("setReserved", Short.TYPE);
            m.setAccessible(true);
            m.invoke(event, (short) 0x01);
        } catch (Exception cause) {
            logger.warn("could not consume event (" + event.paramString() + ")", cause);
        }
    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        if (eventType == KeyEvent.ANY || eventType == KeyEvent.KEY_TYPED) {
            KeyEvent fxKeyEvent = convert(KeyEvent.KEY_TYPED, nativeKeyEvent);
            fxKeyEventHandler.handle(fxKeyEvent);
            if (fxKeyEvent.isConsumed()) consumeEvent(nativeKeyEvent);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (eventType == KeyEvent.ANY || eventType == KeyEvent.KEY_PRESSED) {
            KeyEvent fxKeyEvent = convert(KeyEvent.KEY_PRESSED, nativeKeyEvent);
            fxKeyEventHandler.handle(fxKeyEvent);
            if (fxKeyEvent.isConsumed()) consumeEvent(nativeKeyEvent);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (eventType == KeyEvent.ANY || eventType == KeyEvent.KEY_RELEASED) {
            KeyEvent fxKeyEvent = convert(KeyEvent.KEY_RELEASED, nativeKeyEvent);
            fxKeyEventHandler.handle(fxKeyEvent);
            if (fxKeyEvent.isConsumed()) consumeEvent(nativeKeyEvent);
        }
    }
}
