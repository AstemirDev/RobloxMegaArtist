package org.astemir.core.event;

public abstract class Bind{

    private int code;

    public Bind(int code) {
        this.code = code;
    }

    abstract void run();

    public int getCode() {
        return code;
    }

    public static Bind.KeyPress keyPress(int code,int modifiers,Runnable onRun){
        return new KeyPress(code,modifiers) {
            @Override
            void run() {
                onRun.run();;
            }
        };
    }

    public static Bind.KeyPress keyPress(int code,Runnable onRun){
        return new KeyPress(code,-1) {
            @Override
            void run() {
                onRun.run();;
            }
        };
    }

    public static Bind.KeyRelease keyRelease(int code,int modifiers,Runnable onRun){
        return new KeyRelease(code,modifiers) {
            @Override
            void run() {
                onRun.run();;
            }
        };
    }


    public static Bind.KeyRelease keyRelease(int code,Runnable onRun){
        return new KeyRelease(code,-1) {
            @Override
            void run() {
                onRun.run();;
            }
        };
    }

    public static Bind.MousePress mousePress(int code,int modifiers,Runnable onRun){
        return new MousePress(code,modifiers) {
            @Override
            void run() {
                onRun.run();;
            }
        };
    }


    public static Bind.MousePress mousePress(int code,Runnable onRun){
        return new MousePress(code,-1) {
            @Override
            void run() {
                onRun.run();;
            }
        };
    }

    public static Bind.MouseRelease mouseRelease(int code,int modifiers,Runnable onRun){
        return new MouseRelease(code,modifiers) {
            @Override
            void run() {
                onRun.run();;
            }
        };
    }


    public static Bind.MouseRelease mouseRelease(int code,Runnable onRun){
        return new MouseRelease(code,-1) {
            @Override
            void run() {
                onRun.run();;
            }
        };
    }

    public static abstract class Modified extends Bind{
        private int modifiers = -1;
        private boolean pressed = false;
        private boolean oneshot = true;

        public Modified(int code, int modifiers) {
            super(code);
            this.modifiers = modifiers;
        }

        public Modified(int code) {
            super(code);
        }

        public boolean isActive(int code,int modifiers){
            return code == getCode() && (hasModifiers() ? modifiers == getModifiers() : true);
        }

        public Modified longPress() {
            this.oneshot = false;
            return this;
        }


        public void setPressed(boolean pressed) {
            this.pressed = pressed;
        }

        public boolean isPressed() {
            return pressed;
        }

        public boolean isOneShot() {
            return oneshot;
        }

        public int getModifiers() {
            return modifiers;
        }

        public boolean hasModifiers(){
            return modifiers != -1;
        }
    }

    public static abstract class KeyPress extends Modified{

        public KeyPress(int code, int modifiers) {
            super(code, modifiers);
        }

        public KeyPress(int code) {
            super(code);
        }
    }

    public static abstract class KeyRelease extends Modified{

        public KeyRelease(int code, int modifiers) {
            super(code, modifiers);
        }

        public KeyRelease(int code) {
            super(code);
        }
    }

    public static abstract class MousePress extends Modified{

        public MousePress(int code, int modifiers) {
            super(code, modifiers);
        }

        public MousePress(int code) {
            super(code);
        }
    }

    public static abstract class MouseRelease extends Modified{

        public MouseRelease(int code, int modifiers) {
            super(code, modifiers);
        }

        public MouseRelease(int code) {
            super(code);
        }
    }
}
    