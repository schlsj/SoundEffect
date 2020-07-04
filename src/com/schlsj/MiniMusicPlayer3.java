package com.schlsj;

import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;
import java.util.Random;

public class MiniMusicPlayer3 {
    JFrame f=new JFrame("My First Music Video");
    MyDrawPanel ml;

    private void setUpGui(){
        ml=new MyDrawPanel();
        f.setContentPane(ml);
        f.setBounds(30,30,300,300);
        f.setVisible(true);
    }

    public void play() throws MidiUnavailableException, InvalidMidiDataException {
        setUpGui();
        Sequencer sequencer= MidiSystem.getSequencer();
        sequencer.open();
        sequencer.addControllerEventListener(ml,new int[]{127});
        Sequence sequence=new Sequence(Sequence.PPQ,4);
        var track= sequence.createTrack();

        Random random =new Random();
        int r=0;
        for(int i=0;i<60;i+=4){
            r=random.nextInt(50)+1;
            track.add(makeEvent(144,1,r,100,i));
            track.add(makeEvent(176,1,127,0,i));
            track.add(makeEvent(128,1,r,100,i+2));
        }
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(120);
        sequencer.start();
    }

    private MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) throws InvalidMidiDataException {
        ShortMessage message=new ShortMessage();
        message.setMessage(comd,chan,one,two);
        return new MidiEvent(message,tick);
    }
}
