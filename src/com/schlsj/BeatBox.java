package com.schlsj;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author 67429
 */
public class BeatBox {
    JPanel mainPanel;
    ArrayList<JCheckBox> checkboxes;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame theFrame;

    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
            "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
            "Low-mid Tom", "High Agogo", "Open Hi Conga"};
    int[] instrments={35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

    public void play() throws InvalidMidiDataException, MidiUnavailableException {
        setUpMidi();
        buildGUI();
    }

    private void buildGUI(){
        theFrame=new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout=new BorderLayout();
        JPanel background=new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        Box buttonBox=new Box(BoxLayout.Y_AXIS);
        JButton btnStart=new JButton("Start");
        btnStart.addActionListener(new MyStartListener());
        buttonBox.add(btnStart);
        JButton btnStop=new JButton("Stop");
        btnStop.addActionListener(new MyStopListener());
        buttonBox.add(btnStop);
        JButton btnUpTempo=new JButton("Tempo Up");
        btnUpTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(btnUpTempo);
        JButton btnDownTempo=new JButton("Tempo Down");
        btnDownTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(btnDownTempo);

        Box nameBox=new Box(BoxLayout.Y_AXIS);
        for (String instrumentName : instrumentNames) {
            nameBox.add(new Label(instrumentName));
        }

        background.add(BorderLayout.EAST,buttonBox);
        background.add(BorderLayout.WEST,nameBox);

        theFrame.getContentPane().add(background);

        checkboxes= new ArrayList<>();
        GridLayout grid=new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel=new JPanel(grid);
        background.add(BorderLayout.CENTER,mainPanel);
        for(int i=0;i<256;i++){
            JCheckBox checkBox=new JCheckBox();
            checkBox.setSelected(false);
            checkboxes.add(checkBox);
            mainPanel.add(checkBox);
        }

        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    private void setUpMidi() throws MidiUnavailableException, InvalidMidiDataException {
        sequencer= MidiSystem.getSequencer();
        sequencer.open();
        sequence=new Sequence(Sequence.PPQ,4);
        track=sequence.createTrack();
        sequencer.setTempoInBPM(120);
    }

    public void buildTrackAndStart() throws InvalidMidiDataException {
        int[] selectInstruments;
        sequence.deleteTrack(track);
        track=sequence.createTrack();
        for(int i=0;i<16;i++){
            selectInstruments=new int[16];
            int key=instrments[i];
            for(int j=0;j<16;j++){
                JCheckBox jc=checkboxes.get(j+16*i);
                if(jc.isSelected()){
                    selectInstruments[j]=key;
                }
                else{
                    selectInstruments[j]=0;
                }
            }
            makeTracks(selectInstruments);
            track.add(makeEvent(176,1,127,0,16));
        }
        track.add(makeEvent(192,9,1,0,15));
        sequencer.setSequence(sequence);
        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        sequencer.setTempoInBPM(120);
        sequencer.start();
    }

    private void makeTracks(int[] list) throws InvalidMidiDataException {
        for(int i=0;i<16;i++){
            int key=list[i];
            if(key!=0){
                track.add(makeEvent(144,9,key,100,i));
                track.add(makeEvent(128,9,key,100,i+1));
            }
        }
    }

    private MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) throws InvalidMidiDataException {
        ShortMessage message=new ShortMessage();
        message.setMessage(comd,chan,one,two);
        return new MidiEvent(message,tick);
    }

    public class MyStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                buildTrackAndStart();
            } catch (InvalidMidiDataException invalidMidiDataException) {
                invalidMidiDataException.printStackTrace();
            }
        }
    }

    public class MyStopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    }

    public class MyUpTempoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor=sequencer.getTempoFactor();
            sequencer.setTempoFactor(tempoFactor*1.03f);
        }
    }

    public class MyDownTempoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor=sequencer.getTempoFactor();
            sequencer.setTempoFactor(tempoFactor*0.97f);

        }
    }
}
