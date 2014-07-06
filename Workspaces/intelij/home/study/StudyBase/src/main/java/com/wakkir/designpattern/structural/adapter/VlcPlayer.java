package com.wakkir.designpattern.structural.adapter;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 15:10
 */
public class VlcPlayer implements AdvancedMediaPlayer
{
    @Override
    public void playVlc(String fileName)
    {
        System.out.println("Playing vlc file. Name: " + fileName);
    }

    @Override
    public void playMp4(String fileName)
    {
        //do nothing
    }
}
