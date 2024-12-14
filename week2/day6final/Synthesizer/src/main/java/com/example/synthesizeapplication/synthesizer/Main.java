package com.example.synthesizeapplication.synthesizer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Main {

    public static void main(String[] args) throws LineUnavailableException {
        Clip audioPlayer = AudioSystem.getClip();
        AudioFormat audioFormat = new AudioFormat(44100, 16, 1, true, false);

        AudioComponent sineWaveGenerator = new SineWave(200);
        AudioComponent squareWaveGenerator = new SquareWave(200);
        AudioComponent triangleWaveGenerator = new TriangleWave(200);
        AudioComponent noiseGenerator = new WhiteNoise();

        Filters volumeAdjuster = new Filters(5);
        volumeAdjuster.attachInput(sineWaveGenerator);
        volumeAdjuster.attachInput(squareWaveGenerator);
        volumeAdjuster.attachInput(triangleWaveGenerator);
        volumeAdjuster.attachInput(noiseGenerator);

        AudioClip finalClip = volumeAdjuster.produceClip();

        audioPlayer.open(audioFormat, finalClip.copyData(), 0, finalClip.copyData().length);
        System.out.println("Starting playback...");
        audioPlayer.start();
        audioPlayer.loop(2);

        while (audioPlayer.isRunning()) {
            // Wait for playback to complete
        }

        System.out.println("Playback complete.");
        audioPlayer.close();
    }
}

