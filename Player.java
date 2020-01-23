/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.teamspeakbot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author doma3
 */
public class Player
{

    private Long currentFrame;
    private Clip clip;
    private String status;
    private AudioInputStream audioInputStream;
    private File f;
    private List<String> fileList = new ArrayList<>();

    public Player()
    {
        try (Stream<Path> walk = Files.walk(Paths.get("audio")))
        {
            fileList = walk.filter(Files::isRegularFile).map(x -> x.toString()).filter(w -> w.endsWith(".wav")).collect(Collectors.toList());
            f = new File(fileList.get(0));
        } catch (IOException e)
        {
            System.out.println("Path error!");
        }
    }

    public void controls(String c)
    {
        switch (c)
        {
            case "play":
                play();
                break;
            case "pause":
                pause();
                break;
            case "stop":
                stop();
                break;
            case "restart":
                restart();
                break;
            case "lista":
                list();
                break;
        }
    }

    public String list()
    {
        try (Stream<Path> walk = Files.walk(Paths.get("audio")))
        {
            fileList = walk.filter(Files::isRegularFile).map(x -> x.toString()).filter(w -> w.endsWith(".wav")).collect(Collectors.toList());
        } catch (IOException e)
        {
            System.out.println("Path error!");
        }
        String text = "";
        if (!fileList.isEmpty())
        {
            for (int i = 0; i < fileList.size(); i++)
            {
                text += i + 1 + ". " + Util.stringFilename(fileList.get(i)) + "\n";
            }
        }
        return text;
    }

    private void play()
    {
        try
        {
            if (audioInputStream == null || status.equals("stopped") || clip.getMicrosecondLength() == clip.getMicrosecondPosition())
            {
                audioInputStream = AudioSystem.getAudioInputStream(f);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } else
            {
                if (status.equals("paused"))
                {
                    clip.close();
                    resetStream();
                    clip.setMicrosecondPosition(currentFrame);
                    clip.start();
                }
            }
            status = "play";
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e)
        {
            System.out.println("Play error!");
        }
    }

    private void pause()
    {
        if (status.equals("play"))
        {
            this.currentFrame = this.clip.getMicrosecondPosition();
            clip.stop();
            status = "paused";
        }
    }

    private void stop()
    {
        if (audioInputStream != null)
        {
            currentFrame = 0L;
            clip.stop();
            clip.close();
        }
        status = "stopped";
    }

    private void restart()
    {
        this.stop();
        this.play();
    }

    private void resetStream()
    {
        try
        {
            audioInputStream = AudioSystem.getAudioInputStream(f);
            clip.open(audioInputStream);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e)
        {
            System.out.println("Reset error!");
        }
    }

    public String selectFile(int n)
    {
        if (n != -1 && n < fileList.size())
        {
            String name = fileList.get(n);
            this.stop();
            f = new File(fileList.get(n));
            this.play();
            return "Słuchasz: " + Util.stringFilename(name);
        } else
        {
            return "Błąd, brak takiego numeru!";
        }
    }
}
