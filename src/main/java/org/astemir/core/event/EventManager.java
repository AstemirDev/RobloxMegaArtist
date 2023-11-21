package org.astemir.core.event;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

public class EventManager {

    public void initialize(){
        try {
            GlobalScreen.registerNativeHook();
            System.out.println("Initializing Native Hook Status: %s".formatted(GlobalScreen.isNativeHookRegistered()));
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    public void registerEvents(){
        GlobalEventListener eventListener = new GlobalEventListener();
        GlobalScreen.addNativeKeyListener(eventListener);
        GlobalScreen.addNativeMouseListener(eventListener);
    }


}
