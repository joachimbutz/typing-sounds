/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.joachimbutz.typing_sounds;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 *
 * @author Joachim Butz
 */
public class Synth {

    private Synthesizer synthesizer;
    private MidiChannel currentChannel;
    private Map<Integer, Boolean> noteState;
    private Map<String, Integer> pitch2MidiNum;

    public void init() throws MidiUnavailableException {
        int[] octaves = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        String[] pitchNames = {"C", "Cis", "D", "Dis", "E", "F", "Fis", "G", "Gis", "A", "Ais", "B"};

        pitch2MidiNum = new HashMap<>();
        int midiNum = 12;
        for (int octave : octaves) {
            for (String name : pitchNames) {
                pitch2MidiNum.put(name + octave, midiNum);
                midiNum++;
            }
        }

        if (synthesizer == null) {
            synthesizer = MidiSystem.getSynthesizer();
            if (synthesizer == null) {
                System.out.println("getting synthesizer failed!");
                return;
            }
            synthesizer.open();
            MidiChannel[] channels = synthesizer.getChannels();
            currentChannel = channels[0];
        }

        noteState = new HashMap<>();

    }

    public void play(String pitch) {
        if (pitch == null) {
            return;
        }
        Integer num = pitch2MidiNum.get(pitch);
        if (num == null) {
            return;
        }
        play(num);
    }

    public void stop(String pitch) {
        if (pitch == null) {
            return;
        }
        Integer num = pitch2MidiNum.get(pitch);
        if (num == null) {
            return;
        }
        stop(num);
    }

    private void play(int num) {
        if (!isOn(num)) {
            currentChannel.noteOn(num, 80);
            noteState.put(num, Boolean.TRUE);
        }
    }

    private Boolean isOn(int num) {
        Boolean b = noteState.get(num);
        if (b == null || b == Boolean.FALSE) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private void stop(int num) {
        currentChannel.noteOff(num);
        noteState.put(num, Boolean.FALSE);
    }

    public void destroy() {
        synthesizer.close();
    }

    public static void main(String[] args) throws MidiUnavailableException {
        Synth synth2 = new Synth();
        synth2.init();
        synth2.play(60);
        synth2.destroy();
    }

}
