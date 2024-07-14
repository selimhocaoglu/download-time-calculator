package com.example.download_time_calculator.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadTimeCalculatorService {

    private final double BITS_IN_A_BYTE = 8.0;
    private final double BYTES_IN_A_GIGABYTE = 1024.0;
    private final double SECONDS_IN_A_MINUTE = 60.0;
    private final double MINUTES_IN_AN_HOUR = 60.0;
    private final double SECONDS_IN_AN_HOUR = 3600.0;

    /**
     * Dosya indirme süresini saniye cinsinden hesaplar.
     *
     * @param fileSize Dosya boyutu (MB veya GB)
     * @param internetSpeedMbps İnternet hızı (Mbps)
     * @param isGB Dosya boyutunun GB cinsinden olup olmadığını belirtir
     * @return İndirme süresi (saniye)
     */
    public double calculateDownloadTimeInSeconds(double fileSize, double internetSpeedMbps, boolean isGB) {
        double fileSizeMB; // GB to MB dönüşümü
        if(isGB){
            fileSizeMB = fileSize * BYTES_IN_A_GIGABYTE;
        }
        else{
            fileSizeMB = fileSize;
        }
        double fileSizeMb = fileSizeMB * BITS_IN_A_BYTE; // MB to Mb dönüşümü
        return fileSizeMb / internetSpeedMbps; // saniye cinsinden
    }

    /**
     * Dosya indirme süresini dakika cinsinden hesaplar.
     *
     * @param fileSize Dosya boyutu (MB veya GB)
     * @param internetSpeedMbps İnternet hızı (Mbps)
     * @param isGB Dosya boyutunun GB cinsinden olup olmadığını belirtir
     * @return İndirme süresi (dakika)
     */
    public double calculateDownloadTimeInMinutes(double fileSize, double internetSpeedMbps, boolean isGB) {
        double fileSizeMB;
        if(isGB){
            fileSizeMB = fileSize * BYTES_IN_A_GIGABYTE;
        }
        else{
            fileSizeMB = fileSize;
        }
        double fileSizeMb = fileSizeMB * BITS_IN_A_BYTE;
        double downloadTimeInSeconds = fileSizeMb / internetSpeedMbps;
        return downloadTimeInSeconds / SECONDS_IN_A_MINUTE; // dakikaya dönüşüm
    }

    /**
     * Dosya indirme süresini saat cinsinden hesaplar.
     *
     * @param fileSize Dosya boyutu (MB veya GB)
     * @param internetSpeedMbps İnternet hızı (Mbps)
     * @param isGB Dosya boyutunun GB cinsinden olup olmadığını belirtir
     * @return İndirme süresi (saat)
     */
    public double calculateDownloadTimeInHours(double fileSize, double internetSpeedMbps, boolean isGB) {
        double fileSizeMB;
        if(isGB){
            fileSizeMB = fileSize * BYTES_IN_A_GIGABYTE;
        }
        else{
            fileSizeMB = fileSize;
        }
        double fileSizeMb = fileSizeMB * BITS_IN_A_BYTE;

        double downloadTimeInMinutes = fileSizeMb / internetSpeedMbps / SECONDS_IN_A_MINUTE;
        return downloadTimeInMinutes / MINUTES_IN_AN_HOUR; // saate dönüşüm
    }

    /**
     * Dosya indirme süresini insana hitap eder formatta hesaplar.
     *
     * @param fileSize Dosya boyutu (MB veya GB)
     * @param internetSpeedMbps İnternet hızı (Mbps)
     * @param isGB Dosya boyutunun GB cinsinden olup olmadığını belirtir
     * @return İndirme süresi (insana hitap eder formatta)
     */
    public String calculateHumanUnderstandableDownloadTime(double fileSize, double internetSpeedMbps, boolean isGB){
        double totalSeconds = calculateDownloadTimeInSeconds(fileSize, internetSpeedMbps, isGB);
        int hours = (int) (totalSeconds / SECONDS_IN_AN_HOUR);
        int minutes = (int) ((totalSeconds % SECONDS_IN_AN_HOUR) / SECONDS_IN_A_MINUTE);
        int seconds = (int) (totalSeconds % SECONDS_IN_A_MINUTE);

        return formatTime(hours, minutes, seconds);
    }

    public String formatTime(int hours, int minutes, int seconds) {
        List<String> parts = new ArrayList<>();

        if (hours > 0) {
            parts.add(hours + " hour" + (hours > 1 ? "s" : ""));
        }
        if (minutes > 0) {
            parts.add(minutes + " minute" + (minutes > 1 ? "s" : ""));
        }
        if (seconds > 0) {
            parts.add(seconds + " second" + (seconds > 1 ? "s" : ""));
        }

        return String.join(" ", parts);
    }
}

