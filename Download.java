/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.teamspeakbot;

import com.github.kiulian.downloader.OnYoutubeDownloadListener;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioFormat;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author borys
 */
public class Download
{

    private static YoutubeDownloader downloader;

    public String Download(String videoId)
    {
        try
        {
            downloader = new YoutubeDownloader();
            YoutubeVideo video = downloader.getVideo(videoId);
            File outputDir = new File("video");
            List<AudioFormat> audioFormats = video.audioFormats();
            video.downloadAsync(audioFormats.get(0), outputDir, new OnYoutubeDownloadListener()
            {
                @Override
                public void onDownloading(int progress)
                {
                    System.out.printf("Pobieranie %d%%\n", progress);
                }

                @Override
                public void onFinished(File file)
                {
                    System.out.println("Zapisano plik: " + file);
                    Converter converter = new Converter();
                    converter.Convert(file.getPath());
                }

                @Override
                public void onError(Throwable throwable)
                {
                    System.out.println("Error: " + throwable.getLocalizedMessage());
                }
            });

        } catch (IOException | YoutubeException ex)
        {
            return "Błąd pobierania. Prawdopodobnie film nie istnieje.";
        }
        return "Film dodany do listy!";
    }
}
