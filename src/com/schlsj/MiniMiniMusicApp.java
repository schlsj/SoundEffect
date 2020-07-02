package com.schlsj;

import javax.sound.midi.*;

/**
 * @author 67429
 */
public class MiniMiniMusicApp {
    public void play() throws MidiUnavailableException, InvalidMidiDataException {
        Sequencer player= MidiSystem.getSequencer();
        player.open();

        Sequence seq=new Sequence(Sequence.PPQ,4);
        Track track=seq.createTrack();

        ShortMessage a=new ShortMessage();
        a.setMessage(144,1,80,100);
        MidiEvent noteOn=new MidiEvent(a,1);
        track.add(noteOn);

        ShortMessage b=new ShortMessage();
        b.setMessage(128,1,80,100);
        MidiEvent noteOff=new MidiEvent(b,16);
        track.add(noteOff);

        player.setSequence(seq);
        player.start();
    }
}
