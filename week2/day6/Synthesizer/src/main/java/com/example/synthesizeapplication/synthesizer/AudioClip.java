package com.example.synthesizeapplication.synthesizer;

import java.util.Arrays;

public class AudioClip {
    public static double duration = 5.0; // Length in seconds
    public static int sampleRate = 44100; // Samples per second
    public byte[] dataBuffer;

    // Constructor to initialize the byte array for audio data
    public AudioClip() {
        int totalSamples = (int) (duration * sampleRate); // Total samples for duration
        dataBuffer = new byte[totalSamples * 2]; // 2 bytes per sample
    }

    // Fetch a 16-bit sample from the buffer at the given position
    public int fetchSample(int position) {
        if (position < 0 || position >= dataBuffer.length / 2) {
            throw new IndexOutOfBoundsException("Invalid sample position: " + position);
        }
        int highByte = (dataBuffer[2 * position + 1] & 0xFF) << 8; // High byte
        int lowByte = (dataBuffer[2 * position] & 0xFF); // Low byte
        return (short) (highByte | lowByte); // Combine into signed 16-bit integer
    }

    // Assign a 16-bit sample value to the buffer at the given position
    public void assignSample(int position, int value) {
        if (position < 0 || position >= dataBuffer.length / 2) {
            throw new IndexOutOfBoundsException("Invalid sample position: " + position);
        }
        value = Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, value)); // Clamp value
        dataBuffer[2 * position] = (byte) (value & 0x00FF); // Low byte
        dataBuffer[2 * position + 1] = (byte) (value >> 8); // High byte
    }

    // Create a copy of the audio data buffer
    public byte[] copyData() {
        return Arrays.copyOf(dataBuffer, dataBuffer.length);
    }

    // Normalize the amplitude of the audio samples
    public void normalize() {
        int maxAmplitude = 0;
        for (int i = 0; i < dataBuffer.length / 2; i++) {
            maxAmplitude = Math.max(maxAmplitude, Math.abs(fetchSample(i)));
        }
        if (maxAmplitude == 0) return; // Avoid division by zero
        double scaleFactor = (double) Short.MAX_VALUE / maxAmplitude;
        for (int i = 0; i < dataBuffer.length / 2; i++) {
            int normalizedValue = (int) (fetchSample(i) * scaleFactor);
            assignSample(i, normalizedValue);
        }
    }

    // Debug: Print the first few samples for inspection
    public void debugSamples(int count) {
        System.out.println("Debugging first " + count + " samples:");
        for (int i = 0; i < count && i < dataBuffer.length / 2; i++) {
            System.out.println("Sample " + i + ": " + fetchSample(i));
        }
    }

    // Generate a sine wave with the specified frequency and amplitude
    public void generateSineWave(double frequency, double amplitude) {
        for (int i = 0; i < dataBuffer.length / 2; i++) {
            double angle = 2 * Math.PI * frequency * i / sampleRate;
            int sampleValue = (int) (amplitude * Math.sin(angle));
            assignSample(i, sampleValue);
        }
    }
}



/*


import java.util.Arrays;

public class AudioClip {
    public static double duration = 2.0; // Length in seconds
    public static int sampleRate = 44100; // Samples per second
    public byte[] dataBuffer;

    // Constructor to initialize the byte array for audio data
    public AudioClip() {
        dataBuffer = new byte[176400];
    }


    public int fetchSample(int position) {
        short highByte = (short) (dataBuffer[2 * position + 1] << 8);
        short lowByte = (short) Byte.toUnsignedInt(dataBuffer[2 * position]);
        return highByte | lowByte;
    }



    public void assignSample(int position, int value) {
        value = Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, value)); // Clamp value
        dataBuffer[2 * position] = (byte) (value & 0x00FF); // Low byte
        dataBuffer[2 * position + 1] = (byte) (value >> 8); // High byte
    }




    public byte[] copyData() {
        return Arrays.copyOf(dataBuffer, dataBuffer.length);
    }
}

 */