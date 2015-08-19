package at.joachimbutz.typing_sounds;

import java.util.HashMap;
import java.util.Map;

public class Key2PitchWicki implements Key2Pitch {

    private Map<String, String> key2Pitch = new HashMap<>();

    public Key2PitchWicki() {
        key2Pitch.put("Y", "Fis2");
        key2Pitch.put("X", "Gis2");
        key2Pitch.put("C", "Ais2");
        key2Pitch.put("V", "C3");
        key2Pitch.put("B", "D3");
        key2Pitch.put("N", "E3");
        key2Pitch.put("M", "Fis3");
        key2Pitch.put(",", "Gis3");
        key2Pitch.put(".", "Ais3");
        key2Pitch.put("-", "C4");

        key2Pitch.put("A", "B2");
        key2Pitch.put("S", "Cis3");
        key2Pitch.put("D", "Dis3");
        key2Pitch.put("F", "F3");
        key2Pitch.put("G", "G3");
        key2Pitch.put("H", "A3");
        key2Pitch.put("J", "B3");
        key2Pitch.put("K", "Cis4");
        key2Pitch.put("L", "Dis4");
        key2Pitch.put("Ö", "F4");
        key2Pitch.put("Ä", "G4");
        key2Pitch.put("#", "A4");

        key2Pitch.put("W", "Fis3");
        key2Pitch.put("E", "Gis3");
        key2Pitch.put("R", "Ais3");
        key2Pitch.put("T", "C4");
        key2Pitch.put("Z", "D4");
        key2Pitch.put("U", "E4");
        key2Pitch.put("I", "Fis4");
        key2Pitch.put("O", "Gis4");
        key2Pitch.put("P", "Ais4");
        key2Pitch.put("Ü", "C5");
        key2Pitch.put("+", "D5");

        key2Pitch.put("2", "B3");
        key2Pitch.put("3", "Cis4");
        key2Pitch.put("4", "Dis4");
        key2Pitch.put("5", "F4");
        key2Pitch.put("6", "G4");
        key2Pitch.put("7", "A4");
        key2Pitch.put("8", "B4");
        key2Pitch.put("9", "Cis5");
        key2Pitch.put("0", "Dis5");
        key2Pitch.put("SS", "F5");
    }

    public Map<String, String> getMap() {
        return key2Pitch;
    }

}
