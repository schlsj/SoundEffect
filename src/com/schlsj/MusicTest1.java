package com.schlsj;

import javax.sound.midi.*;

/**
 * @author 67429
 */
public class MusicTest1 {
    public void play() throws MidiUnavailableException {
        Sequencer sequencer=MidiSystem.getSequencer();
        System.out.println("we got a sequencer");
    }
}
