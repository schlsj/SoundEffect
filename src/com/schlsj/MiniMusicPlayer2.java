package com.schlsj;

import javax.sound.midi.*;

/**
 * @author 67429
 */
public class MiniMusicPlayer2 implements ControllerEventListener{
    public void play() throws MidiUnavailableException, InvalidMidiDataException {
        Sequencer sequencer= MidiSystem.getSequencer();
        sequencer.open();

        int[] eventsIWant={127};
        sequencer.addControllerEventListener(this,eventsIWant);

        Sequence sequence=new Sequence(Sequence.PPQ,4);
        var track=sequence.createTrack();
        for(int i=5;i<61;i+=4){
            track.add(makeEvent(144,1,i,100,i));
            track.add(makeEvent(176,1,127,0,i));
            track.add(makeEvent(128,1,i,100,i+2));
        }
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(220);
        sequencer.start();

    }

    @Override
    public void controlChange(ShortMessage message){
        System.out.println("la");
    }

    public static MidiEvent makeEvent(int command, int channel, int one, int two, int tick) throws InvalidMidiDataException {
        ShortMessage message=new ShortMessage();
        message.setMessage(command,channel,one,two);
        MidiEvent midiEvent=new MidiEvent(message,tick);
        return midiEvent;
    }
}
