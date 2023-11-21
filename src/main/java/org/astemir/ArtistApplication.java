package org.astemir;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.astemir.core.MegaArtist;
import org.astemir.core.SimpleRobot;
import org.astemir.core.event.EventManager;
import org.astemir.core.event.Bind;
import org.astemir.core.event.KeyBinding;
import org.astemir.core.gui.MainController;
import org.astemir.core.utils.ImageUtils;
import java.awt.event.InputEvent;
import java.io.File;


public class ArtistApplication extends Application {

    public static final MegaArtist ARTIST = new MegaArtist();
    private static Parent root;
    private Thread artistThread;
    private FXMLLoader fxmlLoader;

    public void run(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        EventManager eventManager = new EventManager();
        eventManager.initialize();
        eventManager.registerEvents();
        KeyBinding.bind(Bind.keyPress(NativeKeyEvent.VC_DOWN,()->{
            try {
                MainController mainController = fxmlLoader.getController();
                if (mainController.isUseBinds()) {
                    SimpleRobot.INSTANCE.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    ARTIST.stop();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }));
        KeyBinding.bind(Bind.keyPress(NativeKeyEvent.VC_UP,()->{
            MainController mainController = fxmlLoader.getController();
            if (mainController.isUseBinds()) {
                TextField delayInput = (TextField) root.lookup("#delayInput");
                if (!delayInput.getText().isEmpty()) {
                    SimpleRobot.DELAY_TIME = Integer.parseInt(delayInput.getText());
                } else {
                    SimpleRobot.DELAY_TIME = 1;
                }

                if (ARTIST.getCells() != null) {
                    if (!ARTIST.isRunning()) {
                        artistThread = new Thread(ARTIST);
                        artistThread.setDaemon(true);
                        artistThread.start();
                        ARTIST.continueWorking();
                    }
                } else {
                    System.out.println("Selected image is null.");
                }
            }
        }));
        stage.setOnCloseRequest((e)->System.exit(1));
        fxmlLoader = new FXMLLoader(ArtistApplication.class.getClassLoader().getResource("main.fxml"));
        root = fxmlLoader.load();
        MenuButton button = (MenuButton) root.lookup("#selectScale");
        for (ImageUtils.ImageScaling value : ImageUtils.ImageScaling.values()) {
            MenuItem menuItem = new MenuItem(value.name());
            menuItem.setOnAction((e)->{
                button.setText(value.name());
                MainController mainController = fxmlLoader.getController();
                mainController.setScaling(value);
                mainController.updateImage();
            });
            button.getItems().add(menuItem);
        }
        CheckBox useBinds = (CheckBox) root.lookup("#useBinds");
        useBinds.setOnAction((e)->{
            MainController mainController = fxmlLoader.getController();
            mainController.setUseBinds(useBinds.isSelected());
        });

        CheckBox realisticDraw = (CheckBox) root.lookup("#realismCheckbox");
        realisticDraw.setOnAction((e)->{
            ARTIST.setRealisticMode(realisticDraw.isSelected());
        });
        Scene scene = new Scene(root, 420, 312);
        stage.setTitle("Roblox Mega Artist!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }

    public static int getSimplification(){
        TextField simplifyInput = (TextField) root.lookup("#simplifyInput");
        if (!simplifyInput.getText().isEmpty()) {
            return Integer.parseInt(simplifyInput.getText());
        } else {
            return -1;
        }
    }


    public static void shutdown(){
        System.out.println("Shutting Down...");
        System.exit(1);
    }

    public static Parent getRoot() {
        return root;
    }
}
