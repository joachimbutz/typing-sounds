/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.joachimbutz.typing_sounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author Joachim
 */
public class TypingSounds extends Application {

    private final static String[] keyboard = new String[]{
        "1234567890ß",
        "QWERTZUIOPÜ+",
        "ASDFGHJKLÖÄ#",
        "<YXCVBNM,.-"
    };

    private Key2Pitch key2Pitch;

    private Map<String, KeyRectangle> keys;

    private Synth synth;

    private Map<String, Boolean> pressedState = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        key2Pitch = new Key2PitchWicki();

        Group root = new Group();

        keys = new HashMap<String, KeyRectangle>();
        for (String s : keyboard) {
            for (char c : s.toCharArray()) {
                String pitch = key2Pitch.getMap().get(String.valueOf(c));
                boolean isPitch = pitch != null;
                boolean isBlack = pitch != null && pitch.contains("is");
                KeyRectangle r = new KeyRectangle(60, String.valueOf(c), pitch, isPitch, isBlack);
                keys.put(String.valueOf(c), r);
            }
        }

        HBox hBox0 = new HBox();
        {
            List<KeyRectangle> list = new ArrayList<KeyRectangle>();
            for (char c : keyboard[0].toCharArray()) {
                list.add(keys.get(String.valueOf(c)));
            }
            hBox0.getChildren().addAll(list);
        }

        HBox hBox1 = new HBox();
        hBox1.setPadding(new Insets(0, 0, 0, 20));
        {
            List<KeyRectangle> list = new ArrayList<KeyRectangle>();
            for (char c : keyboard[1].toCharArray()) {
                list.add(keys.get(String.valueOf(c)));
            }
            hBox1.getChildren().addAll(list);
        }

        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(0, 0, 0, 40));
        {
            List<KeyRectangle> list = new ArrayList<KeyRectangle>();
            for (char c : keyboard[2].toCharArray()) {
                list.add(keys.get(String.valueOf(c)));
            }
            hBox2.getChildren().addAll(list);
        }

        HBox hBox3 = new HBox();
        hBox3.setPadding(new Insets(0, 0, 0, 20));
        {
            List<KeyRectangle> list = new ArrayList<KeyRectangle>();
            for (char c : keyboard[3].toCharArray()) {
                list.add(keys.get(String.valueOf(c)));
            }
            hBox3.getChildren().addAll(list);
        }

        VBox vBox = new VBox();

        vBox.getChildren().addAll(hBox0, hBox1, hBox2, hBox3);

        root.getChildren().addAll(vBox);

        Scene scene = new Scene(root, 500, 300);

        synth = new Synth();
        try {
            synth.init();
        } catch (MidiUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(-1);
        }

        final EventHandler<KeyEvent> keyPressedHandler = new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                // System.out.println("char: " + keyEvent.getCharacter());
//				 System.out.println("code: " + keyEvent.getCode());
                String text = keyEvent.getText().toUpperCase();
//				System.out.println("text: " + text);
                keyEvent.consume();
                if (text == null || text.length() == 0) {
                    return;
                }
                if (isPressed(text)) {
                    return;
                }
                pressedState.put(text, Boolean.TRUE);
                KeyRectangle rect = keys.get(text);
                if (rect == null) {
                    return;
                }
                rect.startAnimation();

                String pitch = key2Pitch.getMap().get(text);
                synth.play(pitch);
            }
        };

        final EventHandler<KeyEvent> keyReleasedHandler = new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
				// System.out.println("char: " + keyEvent.getCharacter());
                // System.out.println("code: " + keyEvent.getCode());
                // System.out.println("text: " + keyEvent.getText());
                String text = keyEvent.getText().toUpperCase();
                keyEvent.consume();
                if (text == null || text.length() == 0) {
                    return;
                }

                pressedState.put(text, Boolean.FALSE);
                KeyRectangle rect = keys.get(text);
                if (rect == null) {
                    return;
                }
                rect.stopAnimation();

                String pitch = key2Pitch.getMap().get(text);
                synth.stop(pitch);
            }
        };

        scene.setOnKeyPressed(keyPressedHandler);
        scene.setOnKeyReleased(keyReleasedHandler);

        primaryStage.setTitle("Keyboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Boolean isPressed(String text) {
        Boolean b = pressedState.get(text);
        if (b == null || b == Boolean.FALSE) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stop");
        synth.destroy();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
