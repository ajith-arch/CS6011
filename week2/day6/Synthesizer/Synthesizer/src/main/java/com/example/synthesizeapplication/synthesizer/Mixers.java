package com.example.synthesizeapplication.synthesizer;


public class Mixers implements AudioComponent{

    private byte[] clipsBytes = new byte[0];

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip(clipsBytes);
        return clip;
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
        AudioClip clip = input.getClip();
        byte[] clipByte = clip.getData();
        clipsBytes = concatenate(clipsBytes, clipByte);
    }

    private byte[] concatenate(byte[] a, byte[] b){
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0,result,0,a.length);
        System.arraycopy(b,0,result,a.length,b.length);
        return result;
    }

}
