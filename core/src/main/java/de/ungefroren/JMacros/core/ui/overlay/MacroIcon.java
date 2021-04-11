package de.ungefroren.JMacros.core.ui.overlay;

import de.ungefroren.JMacros.core.JMacros;
import de.ungefroren.JMacros.core.config.MacroData;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MacroIcon extends VBox {

    private final KeyCode key;

    public MacroIcon(KeyCode key, Image img, Paint color, DoubleProperty size) {
        this.key = key;
        ImageView img1 = new ImageView(img);
        Label label = new Label(key.name());
        Rectangle background = new Rectangle();
        background.heightProperty().bind(size);
        background.widthProperty().bind(size);
        background.setFill(color);
        NumberBinding arc = size.divide(2.4);
        background.arcHeightProperty().bind(arc);
        background.arcWidthProperty().bind(arc);
        background.addEventHandler(TouchEvent.TOUCH_PRESSED, this::onTouch);
        background.setMouseTransparent(false);
        super.getChildren().add(new Pane(background, img1));
        super.getChildren().add(label);
        img1.setMouseTransparent(true);
        img1.fitWidthProperty().bind(size);
        img1.fitHeightProperty().bind(size);
        img1.setMouseTransparent(true);
        ObjectBinding<Font> font = Bindings.createObjectBinding(
                () -> Font.font("Arial", FontWeight.BOLD, size.get() * 24 / 64),
                size);
        label.fontProperty().bind(font);
        label.setTextFill(color);
        label.setMouseTransparent(true);
        setAlignment(Pos.CENTER);
        setBackground(Background.EMPTY);
    }

    public MacroIcon(MacroData macro, DoubleProperty size) {
        this(macro.getKeyCode(), new Image(macro.getImageUrl().toUri().toString()), macro.getColor(), size);
    }

    private void onTouch(InputEvent event) {
        JMacros jMacros = JMacros.getInstance();
        jMacros.performAction(key);
        jMacros.showOverlay(false);
    }
}
