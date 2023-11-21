package org.astemir.core;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SimpleRobot {

    public static Robot INSTANCE = null;
    public static Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();
    public static int DELAY_TIME = 1;

    static {
        try {
            INSTANCE = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void delay(){
        try {
            Thread.sleep(DELAY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void delay(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pasteText(String text){
        copy(text);
        paste();
    }

    public static void paste(){
        INSTANCE.keyPress(KeyEvent.VK_META);
        INSTANCE.keyPress(KeyEvent.VK_V);
        INSTANCE.keyRelease(KeyEvent.VK_META);
        INSTANCE.keyRelease(KeyEvent.VK_V);
    }

    public static void copy(String text){
        StringSelection stringSelection = new StringSelection(text);
        CLIPBOARD.setContents(stringSelection,stringSelection);
    }

    public static void mousePress(){
        INSTANCE.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        delay();
        INSTANCE.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void mouseMove(CellPoint CellPoint){
        mouseMove(CellPoint.x,CellPoint.y);
    }

    public static void mouseMove(int x,int y){
        INSTANCE.mouseMove(x,y);
    }

    public static void moveAndPress(CellPoint CellPoint){
        moveAndPress(CellPoint.x,CellPoint.y);
    }

    public static void moveAndPress(int x,int y){
        mouseMove(x,y);
        delay();
        mousePress();
    }


}
