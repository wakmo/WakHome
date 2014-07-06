package com.wakkir.designpattern.structural.adapter;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 15:13
 */
public class MediaAdapter implements MediaPlayer
{

    AdvancedMediaPlayer advancedMusicPlayer;

    public MediaAdapter(String audioType)
    {
        if (audioType.equalsIgnoreCase("vlc"))
        {
            advancedMusicPlayer = new VlcPlayer();
        }
        else if (audioType.equalsIgnoreCase("mp4"))
        {
            advancedMusicPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String audioType, String fileName)
    {
        if (audioType.equalsIgnoreCase("vlc"))
        {
            advancedMusicPlayer.playVlc(fileName);
        }
        else if (audioType.equalsIgnoreCase("mp4"))
        {
            advancedMusicPlayer.playMp4(fileName);
        }
    }
}
