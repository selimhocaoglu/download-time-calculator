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
     * Calculate download time by type of seconds.
     *
     * @param fileSize File size (MB or GB)
     * @param internetSpeedMbps Internet speed (Mbps)
     * @param isGB Show file size is GB or not
     * @return Download time (seconds)
     */
    public double calculateDownloadTimeInSeconds(double fileSize, double internetSpeedMbps, boolean isGB) {
        double fileSizeMB;
        if(isGB){
            fileSizeMB = fileSize * BYTES_IN_A_GIGABYTE;
        }
        else{
            fileSizeMB = fileSize;
        }
        double fileSizeMb = fileSizeMB * BITS_IN_A_BYTE;
        return fileSizeMb / internetSpeedMbps;
    }

    /**
     * Calculate download time by type of minutes.
     *
     * @param fileSize File Size (MB or GB)
     * @param internetSpeedMbps Internet speed (Mbps)
     * @param isGB Show file size is GB or not
     * @return Download time (minutes)
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
        return downloadTimeInSeconds / SECONDS_IN_A_MINUTE;
    }

    /**
     * Calculate download time by type of hours.
     *
     * @param fileSize File size (MB or GB)
     * @param internetSpeedMbps Internet speed (Mbps)
     * @param isGB Show file size is GB or not
     * @return Download time (hours)
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
        return downloadTimeInMinutes / MINUTES_IN_AN_HOUR;
    }

    /**
     * Calculate download time understandable for human.
     *
     * @param fileSize File size (MB or GB)
     * @param internetSpeedMbps Internet speed (Mbps)
     * @param isGB Show file size is GB or not
     * @return Download time (human understandable)
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

