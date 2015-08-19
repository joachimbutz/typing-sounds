package at.joachimbutz.typing_sounds;

import javafx.animation.FillTransition;
import javafx.animation.FillTransitionBuilder;
import javafx.animation.SequentialTransitionBuilder;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.util.Duration;

public class KeyRectangle extends Group {

    private Transition transition;

    private Transition stopTransition;

    private boolean isPitch;

    public KeyRectangle(int size, String key, String pitch, boolean isPitch, boolean isBlack) {
        this.isPitch = isPitch;
        Color startColor = Color.GRAY;
        if (isPitch) {
            if (isBlack) {
                startColor = Color.DARKGREEN;
            } else {
                startColor = Color.LIGHTGREEN;
            }
        }

        Rectangle rect = new Rectangle(size, size, startColor);
        rect.setStroke(Color.BLACK);
        Text textKey = new Text(10, 20, key);
        textKey.setFill(Color.BLACK);
        textKey.setStroke(Color.BLACK);
        
        Text textPitch = new Text(20, 40, pitch);
        textPitch.setFill(Color.BLACK);
        textPitch.setStroke(Color.BLACK);
        
        getChildren().addAll(rect, textKey, textPitch);

        FillTransition initTransition = new FillTransition(
                Duration.seconds(0.2), rect, startColor, Color.DODGERBLUE);
        initTransition.setCycleCount(1);
        initTransition.setAutoReverse(true);

        transition = SequentialTransitionBuilder.create()
                .node(rect)
                .children(initTransition)
                .cycleCount(Timeline.INDEFINITE)
                .autoReverse(true)
                .build();

        stopTransition = FillTransitionBuilder.create()
                .duration(Duration.seconds(0.5)).shape(rect).toValue(startColor).build();
    }

    public void startAnimation() {
        if (!isPitch) {
            return;
        }
        transition.play();
    }

    public void stopAnimation() {
        if (!isPitch) {
            return;
        }
        transition.stop();
        stopTransition.play();
    }

}
