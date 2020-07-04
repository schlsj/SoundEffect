package com.schlsj;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.ShortMessage;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * @author 67429
 */
public class MyDrawPanel extends JPanel implements ControllerEventListener {
    boolean msg=false;
    @Override
    public void controlChange(ShortMessage event) {
        msg=true;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        if(!msg){
            return;
        }
        Random random=new Random();
        int r=random.nextInt(250);
        int gr=random.nextInt(250);
        int b=random.nextInt(250);
        g.setColor(new Color(r,gr,b));
        int ht=random.nextInt(120)+10;
        int width=random.nextInt(120)+10;
        int x=random.nextInt(40)+10;
        int y=random.nextInt(40)+10;
        g.fillRect(x,y,width,ht);
        msg=false;
    }
}
