/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.teamspeakbot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

/**
 *
 * @author borys
 */
public class Converter
{

    private static FFmpeg ffmpeg;
    private static FFprobe ffprobe;
    private FFmpegBuilder builder;
    private static final DateTimeFormatter CONSTANT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH-mm-ss");
    private static final File CONSTANT_VIDEO = new File("video");

    public Converter()
    {
        try
        {
            ffmpeg = new FFmpeg("ffmpeg\\bin\\ffmpeg.exe");
            ffprobe = new FFprobe("ffmpeg\\bin\\ffprobe.exe");
        } catch (IOException ex)
        {
            System.out.println("Błąd FFmpeg");
        }
    }

    public void Convert(String video)
    {
        builder = new FFmpegBuilder()
                .setInput(video)
                .overrideOutputFiles(true)
                .addOutput("audio\\YouTube " + LocalDateTime.now().format(CONSTANT_FORMATTER) + ".wav")
                .setFormat("wav")
                .setAudioCodec("pcm_s16le")
                .setAudioSampleRate(44_100)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
        Util.clearFolder(CONSTANT_VIDEO);
    }
}
