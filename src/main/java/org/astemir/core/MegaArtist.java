package org.astemir.core;

import org.astemir.ArtistApplication;
import org.astemir.core.utils.ColorUtils;
import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;

import java.util.concurrent.CopyOnWriteArrayList;

public class MegaArtist implements Runnable{

    public static final CellPoint WINDOW_POSITION = new CellPoint(486,162);
    public static final CellPoint COLOR_PICKER_POSITION = new CellPoint(814,666);
    public static final CellPoint COLOR_INPUT_POSITION = new CellPoint(810,594);
    public static final CellPoint COLOR_PICKER_CLOSE_POSITION = new CellPoint(993,386);

    public static final float CELL_SIZE = 14.5f;

    private CopyOnWriteArrayList<ColoredCell> cells;

    private BufferedImage SCREENSHOT;

    private boolean isRunning = false;

    private int pickedColor = -1;

    private int currentCellIndex = 0;
    private boolean realisticMode = false;
    private int simplification = -1;

    @Override
    public void run(){
        pickedColor = 1;
        currentCellIndex = 0;
        isRunning = true;
        SCREENSHOT = SimpleRobot.INSTANCE.createScreenCapture(new Rectangle(WINDOW_POSITION.toPoint(),new Dimension((int) (CELL_SIZE*32), (int) (CELL_SIZE*32))));
        System.out.println("Artist is starting working on your masterpiece!");
        while(isRunning){
            if (currentCellIndex < cells.size()) {
                ColoredCell cell = cells.get(currentCellIndex);
                CellPoint CellPoint = getCellPosition(cell.getPosition().x, cell.getPosition().y);
                int existingColor = SCREENSHOT.getRGB(CellPoint.x, CellPoint.y);
                if (!ColorUtils.isSimilarColor(existingColor, cell.getColor(), ColorUtils.EQUALITY)) {
                    if (pickedColor != cell.getColor()) {
                        pickColor(cell.getColor());
                        SimpleRobot.delay();
                    }
                    moveToCell(cell.getPosition().x, cell.getPosition().y);
                    SimpleRobot.delay();
                    SimpleRobot.mousePress();
                }
                currentCellIndex++;
            }else{
                stop();
                System.out.println("Artist work is done, check your result!");
            }
        }
    }



    public BufferedImage loadPixelData(BufferedImage image){
        int simplification = ArtistApplication.getSimplification();
        if (simplification != -1){
            image = ColorSimplifier.colorize(image,simplification);
        }
        CopyOnWriteArrayList<ColoredCell> pixelData = new CopyOnWriteArrayList<>();
        for (int i = 0;i<32;i++){
            for (int j = 0;j<32;j++){
                int pixelColor = image.getRGB(i,j);
                if (!ColorUtils.isTransparent(pixelColor)) {
                    pixelData.add(new ColoredCell(pixelColor,new CellPoint(i, j)));
                }
            }
        }
        this.cells = pixelData;
        sort();
        return image;
    }


    public void pickColor(int color){
        SimpleRobot.moveAndPress(COLOR_PICKER_POSITION);
        SimpleRobot.moveAndPress(COLOR_INPUT_POSITION);
        SimpleRobot.delay();
        SimpleRobot.pasteText(ColorUtils.hex(color));
        SimpleRobot.delay();
        SimpleRobot.moveAndPress(COLOR_PICKER_CLOSE_POSITION);
        pickedColor = color;
    }


    public CellPoint getCellPosition(int i,int j){
        float x1 =(i*CELL_SIZE+CELL_SIZE/2);
        float y1 =(j*CELL_SIZE+CELL_SIZE/2);
        return new CellPoint((int)x1,(int)y1);
    }

    public void moveToCell(int i,int j){
        moveRelative(i*CELL_SIZE+CELL_SIZE/2,j*CELL_SIZE+CELL_SIZE/2);
    }

    public void moveRelative(float x,float y){
        float x1 =WINDOW_POSITION.x+x;
        float y1 =WINDOW_POSITION.y+y;
        SimpleRobot.mouseMove((int)x1,(int)y1);
    }

    public boolean isRealisticMode() {
        return realisticMode;
    }

    public void setRealisticMode(boolean realisticMode) {
        this.realisticMode = realisticMode;
        sort();
    }

    public void sort(){
        if (cells != null) {
            if (!realisticMode) {
                Collections.sort(cells, Comparator.comparing(ColoredCell::getDistance));
                Collections.sort(cells, Comparator.comparing(ColoredCell::getColor));
            } else {
                Collections.sort(cells, Comparator.comparing(ColoredCell::getPosition));
            }
        }
    }

    public CopyOnWriteArrayList<ColoredCell> getCells() {
        return cells;
    }

    public boolean isRunning() {
        return isRunning;
    }


    public void continueWorking(){isRunning = true;}

    public void stop(){
        isRunning = false;
    }

    public void setSimplification(int simplification) {
        this.simplification = simplification;
    }
}
