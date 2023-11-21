package org.astemir.core.event;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import java.util.function.Predicate;

public class GlobalEventListener implements NativeKeyListener, NativeMouseInputListener {


    public void handleEvents(Predicate<Bind.Modified> predicate){
        for (Bind bind : KeyBinding.BINDS) {
            if (bind instanceof Bind.Modified modified) {
                if (predicate.test(modified)) {
                    modified.run();
                }
            }
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        handleEvents((bind)->{
            if (bind instanceof Bind.KeyPress) {
                boolean active = bind.isActive(nativeEvent.getKeyCode(), nativeEvent.getModifiers());
                if (active) {
                    if (bind.isOneShot()) {
                        if (!bind.isPressed()) {
                            bind.setPressed(true);
                            return true;
                        }
                    }else{
                        return true;
                    }
                }
            }
            return false;
        });
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        handleEvents((bind)->{
            if (bind instanceof Bind.KeyPress) {
                if (bind.isActive(nativeEvent.getKeyCode(),nativeEvent.getModifiers())){
                    if (bind.isOneShot()){
                        bind.setPressed(false);
                    }
                }
            }

            if (bind instanceof Bind.KeyRelease) {
                return bind.isActive(nativeEvent.getKeyCode(), nativeEvent.getModifiers());
            }
            return false;
        });
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
        handleEvents((bind)->{
            if (bind instanceof Bind.MousePress) {
                return bind.isActive(nativeEvent.getButton(), nativeEvent.getModifiers());
            }
            return false;
        });
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
        handleEvents((bind)->{
            if (bind instanceof Bind.MouseRelease) {
                return bind.isActive(nativeEvent.getButton(), nativeEvent.getModifiers());
            }
            return false;
        });
    }
}
