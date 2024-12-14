package com.example.synthesizeapplication.synthesizer;

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
        dataBuffer[2 * position] = (byte) (value & 0x00FF);
        dataBuffer[2 * position + 1] = (byte) (value >> 8);
    }

    public byte[] copyData() {
        return Arrays.copyOf(dataBuffer, dataBuffer.length);
    }
}
