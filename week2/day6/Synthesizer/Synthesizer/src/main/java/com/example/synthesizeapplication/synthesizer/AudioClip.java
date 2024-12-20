package com.example.synthesizeapplication.synthesizer;

import java.util.ArrayList;
import java.util.Arrays;

public class AudioClip {



    static final double duration = 2.0;
    public static final int sampleRate = 44100;
    private final int clipSize = (int) (sampleRate * duration);
    //    private byte[] data = new byte[clipSize];
    private byte[] data;

    public AudioClip(byte[] data){
        this.data = Arrays.copyOf(data, data.length);
    }



    public int getSample(int index){

        int lowerBits = data[index*2] & 0xFF;
        int upperBits = data[(index*2) + 1] & 0xFF;
        int sample = (upperBits << 8) | lowerBits;

        return (short) sample;

    }





    public void setSample(int index, int value){
        short shortValue = (short) value;
        data[index*2] = (byte) (shortValue & 0xFF);//lower bit
        data[(index*2) + 1] = (byte) ((shortValue >> 8) & 0xFF);
    }

    public byte[] getData(){
        return Arrays.copyOf(data, data.length);
    }




}

