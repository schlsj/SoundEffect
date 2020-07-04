package com.schlsj;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * @author 67429
 */
public class Main {
    public static void main(String[] args){
        MiniMusicPlayer3 test=new MiniMusicPlayer3();
        try{
            test.play();
        }
        catch (MidiUnavailableException ex){
            System.out.println("Bummer");
        }
        catch (InvalidMidiDataException ex){
            ex.printStackTrace();
        }
    }
}
