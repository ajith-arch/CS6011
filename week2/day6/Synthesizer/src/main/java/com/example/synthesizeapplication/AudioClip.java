package com.example.synthesizeapplication;

import java.util.Arrays;

public class AudioClip {
    // Duration of the audio clip in seconds
    public static double duration = 2.0;
    // Sample rate (number of samples per second)
    public static int rate = 44100;
    // Array to store audio sample data in bytes
    public byte[] bArray;

    // Constructor initializes the byte array to hold 176400 bytes (2 seconds of audio data)
    AudioClip() {
        bArray = new byte[176400];
    }

    // Method to get a sample value at a specific index
    public int getSample(int index) {
        // Extracts the high byte and shifts it left by 8 bits to make space for the low byte
        short high = (short) (bArray[2 * index + 1] << 8);
        // Extracts the low byte and converts it to an unsigned integer
        short low = (short) Byte.toUnsignedInt(bArray[2 * index]);

        // Combines the high and low bytes to form the complete sample value
        return high | low;
    }

    // Method to set a sample value at a specific index
    public void setSample(int index, int value) {
        // Stores the low byte of the value in the byte array
        bArray[2 * index] = (byte) (value & 0x00ff);
        // Stores the high byte of the value in the byte array by shifting the value right by 8 bits
        bArray[2 * index + 1] = (byte) (value >> 8);
    }

    // Method to get a copy of the audio data as a byte array
    public byte[] getData() {
        return Arrays.copyOf(bArray, bArray.length);
    }
}