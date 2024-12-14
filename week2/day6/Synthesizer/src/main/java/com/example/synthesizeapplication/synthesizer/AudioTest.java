package com.example.synthesizeapplication.synthesizer;

import javax.sound.sampled.*;

public class AudioTest {
    public static void main(String[] args) throws LineUnavailableException {
        AudioClip clip = new AudioClip();

        // Generate a sine wave (440 Hz, full amplitude)
        clip.generateSineWave(440, Short.MAX_VALUE);
        clip.debugSamples(10); // Print the first 10 samples for verification

        // Normalize (not needed here since it's already at full amplitude)
        clip.normalize();

        // Play the audio
        AudioFormat format = new AudioFormat(AudioClip.sampleRate, 16, 1, true, false);
        Clip audioPlayer = AudioSystem.getClip();
        audioPlayer.open(format, clip.copyData(), 0, clip.copyData().length);

        System.out.println("Playing audio...");
        audioPlayer.start();
        while (audioPlayer.isRunning()) {
            try {
                Thread.sleep(100); // Wait for playback to complete
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Playback finished.");
        audioPlayer.close();
    }
}
