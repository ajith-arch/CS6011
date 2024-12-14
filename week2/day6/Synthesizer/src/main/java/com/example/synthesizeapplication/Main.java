package com.example.synthesizeapplication;


import com.example.synthesizeapplication.synthesizer.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Main {

    public static void main(String[] args) throws LineUnavailableException {
        Clip audioPlayer = AudioSystem.getClip();
        AudioFormat audioFormat = new AudioFormat(44100, 16, 1, true, false);

        // Create generators
        AudioComponent sineWaveGenerator = new SineWave(200);
        AudioComponent squareWaveGenerator = new SquareWave(200);
        AudioComponent triangleWaveGenerator = new TriangleWave(200);
        AudioComponent noiseGenerator = new WhiteNoise();

        // Use Mixer instead of Filters to combine components
        Mixer mixer = new Mixer();
        mixer.attachInput(sineWaveGenerator);
        mixer.attachInput(squareWaveGenerator);
        mixer.attachInput(triangleWaveGenerator);
        mixer.attachInput(noiseGenerator);

        // Generate and normalize final audio clip
        AudioClip finalClip = mixer.produceClip();
        finalClip.normalize();

        // Debugging: Check the first 10 samples
        for (int i = 0; i < 10; i++) {
            System.out.println("Sample " + i + ": " + finalClip.fetchSample(i));
        }

        // Play audio
        audioPlayer.open(audioFormat, finalClip.copyData(), 0, finalClip.copyData().length);
        System.out.println("Starting playback...");
        audioPlayer.start();

        // Ensure playback is not interrupted
        while (audioPlayer.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Playback complete.");
        audioPlayer.close();
    }
}
