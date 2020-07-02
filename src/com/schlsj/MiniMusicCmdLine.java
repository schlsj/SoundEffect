package com.schlsj;

import javax.sound.midi.*;

public class MiniMusicCmdLine {
    public void play(int instrument, int note) throws MidiUnavailableException, InvalidMidiDataException {
        Sequencer sequencer= MidiSystem.getSequencer();
        sequencer.open();

        Sequence seq=new Sequence(Sequence.PPQ,4);
        var track=seq.createTrack();

        ShortMessage instrumentMessage=new ShortMessage();
        instrumentMessage.setMessage(192,1,instrument,0);
        MidiEvent instrumentEvent=new MidiEvent(instrumentMessage,1);
        track.add(instrumentEvent);

        ShortMessage onMessage=new ShortMessage();
        onMessage.setMessage(144,1,note,100);
        MidiEvent onEvent=new MidiEvent(onMessage,1);
        track.add(onEvent);

        ShortMessage offMessage=new ShortMessage();
        offMessage.setMessage(128,1,note,100);
        MidiEvent offEvent=new MidiEvent(offMessage,16);
        track.add(offEvent);

        sequencer.setSequence(seq);
        sequencer.start();
    }
}
