package org.astemir.core.gui;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.astemir.ArtistApplication;
import org.astemir.core.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;

public class MainController {

    @FXML // fx:id="delayInput"
    private TextField delayInput; // Value injected by FXMLLoader

    @FXML // fx:id="imagePanel"
    private Canvas imagePanel;

    @FXML // fx:id="realismCheckbox"
    private CheckBox realismCheckbox; // Value injected by FXMLLoader

    @FXML // fx:id="selectArt"
    private Button selectArt; // Value injected by FXMLLoader

    @FXML // fx:id="selectScale"
    private MenuButton selectScale; // Value injected by FXMLLoader

    @FXML // fx:id="simplifyInput"
    private TextField simplifyInput; // Value injected by FXMLLoader

    @FXML
    private CheckBox useBinds;

    @FXML
    private Button updateButton;

    private BufferedImage image;

    private ImageUtils.ImageScaling scaling = ImageUtils.ImageScaling.DEFAULT;

    private File file;

    private boolean isUsingBinds;


    @FXML
    public void onClickUpdate(ActionEvent event){
        updateImage();
    }

    @FXML
    public void onSelectArt(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Выберите арт");
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("images","*.png"));
        file = chooser.showOpenDialog(null);
        updateImage();
    }

    public void updateImage(){
        if (file != null){
            image = ImageUtils.readImage(file.getAbsolutePath(), scaling);
            image = ArtistApplication.ARTIST.loadPixelData(image);
            Image img = SwingFXUtils.toFXImage(image,null);
            GraphicsContext g = imagePanel.getGraphicsContext2D();
            g.setImageSmoothing(false);
            g.clearRect(0,0,256,256);
            g.drawImage(img,0,0,256,256);
        }
    }

    public void setUseBinds(boolean useBinds) {
        this.isUsingBinds = useBinds;
    }

    public boolean isUseBinds() {
        return isUsingBinds;
    }

    public void setScaling(ImageUtils.ImageScaling scaling) {
        this.scaling = scaling;
    }
}
