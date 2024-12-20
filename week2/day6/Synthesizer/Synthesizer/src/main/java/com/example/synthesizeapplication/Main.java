package com.example.synthesizeapplication;


import com.example.synthesizeapplication.synthesizer.*;

import javax.sound.sampled.*;

public class Main {
    public static void main(String[] args) throws LineUnavailableException {

        Clip c = AudioSystem.getClip();
        AudioFormat format16 = new AudioFormat( 44100, 16, 1, true, false );

//        AudioComponent gen = new SineWave(440); // Your code
//        AudioClip clip = gen.getClip();

        AudioComponent gen1 = new SineWave(440); // Your code
        AudioComponent gen2 = new SineWave(493.883);
        AudioComponent gen3 = new SquareWave(440);
        AudioComponent gen4 = new WhiteNoise();
        AudioComponent gen5 = new LinearRamp(50,10000);
        AudioComponent gen6 = new VariableFrequency();
        gen6.connectInput(gen5);
//        AudioComponent volumeAdjuster = new VolumeAdjuster(0.1);
//        volumeAdjuster.connectInput(gen);
        AudioComponent mixer = new Mixers();
        mixer.connectInput(gen1);
        mixer.connectInput(gen2);
        AudioClip clip = gen6.getClip();

        c.open( format16, clip.getData(), 0, clip.getData().length );
        System.out.println( "About to play..." );
        c.start(); // Plays it.
        c.loop( 2 );

        while( c.getFramePosition() < AudioClip.sampleRate || c.isActive() || c.isRunning() ){
            // Do nothing while we wait for the note to play.
        }

        System.out.println( "Done." );
        c.close();

        System.out.println("Debug: AudioClip samples:");
        for (int i = 0; i < 10; i++) {
            System.out.println("Sample " + i + ": " + clip.getSample(i));
        }

    }
}
