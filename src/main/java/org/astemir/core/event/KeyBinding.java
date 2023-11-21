package org.astemir.core.event;

import java.util.ArrayList;
import java.util.List;

public class KeyBinding {

    public static List<Bind> BINDS = new ArrayList<>();

    public static void bind(Bind bind){
        BINDS.add(bind);
    }
}
