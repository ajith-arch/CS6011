package com.example.synthesizeapplication;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;


public class Main {

    public static void main(String[] args) throws LineUnavailableException {

        Clip c = AudioSystem.getClip();
        AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
        AudioComponent gen = new SineWave(200);
        AudioComponent gen2 = new SquareWave(200);
        AudioComponent gen3 = new TriangleWave(200);
        AudioComponent gen4 = new WhiteNoise();
//

        Filters f1 = new Filters(5);
//        Filters f2 = new Filters(0.5);
        f1.connectInput(gen);
        f1.connectInput(gen2);
        f1.connectInput(gen3);
        f1.connectInput(gen4);
//        f2.connectInput(gen2);
//
//        Mixer m1 = new Mixer();
//        m1.connectInput(f1);
//        m1.connectInput(f2);

//        LinearRamp l1 = new LinearRamp(50,10000);
//        vfGen.connectInput(l1);
        AudioClip clip = f1.getClip();



        c.open(format16, clip.getData(), 0, clip.getData().length);
        System.out.println("About to play...");
        c.start();
        c.loop(2);
        while (c.getFramePosition() < clip.bArray.length/2 || c.isActive() || c.isRunning()) {
            // Do nothing while we wait for the note to play.
        }

        System.out.println("Done. ");
        c.close();
    }
}