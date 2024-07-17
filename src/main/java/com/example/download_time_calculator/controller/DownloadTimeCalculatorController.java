package com.example.download_time_calculator.controller;

import com.example.download_time_calculator.service.DownloadTimeCalculatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class DownloadTimeCalculatorController {


    private final DownloadTimeCalculatorService calculatorService;

    public DownloadTimeCalculatorController(DownloadTimeCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam("fileSize") double fileSize,
                            @RequestParam("internetSpeed") double internetSpeed,
                            @RequestParam("unit") String unit,
                            @RequestParam("startTime") String startTime,
                            Model model) {

        if (fileSize <= 0) {
            model.addAttribute("error", "File size must be greater than zero.");
            return "index";
        }

        if (internetSpeed <= 0) {
            model.addAttribute("error", "Internet speed must be greater than zero.");
            return "index";
        }

        boolean isGB = "GB".equalsIgnoreCase(unit);

        double downloadTimeInSeconds = calculatorService.calculateDownloadTimeInSeconds(fileSize, internetSpeed, isGB);
//        double downloadTimeInMinutes = calculatorService.calculateDownloadTimeInMinutes(fileSize, internetSpeed, isGB);
//        double downloadTimeInHours = calculatorService.calculateDownloadTimeInHours(fileSize, internetSpeed, isGB);

//        model.addAttribute("downloadTimeInSeconds", downloadTimeInSeconds);
//        model.addAttribute("downloadTimeInMinutes", downloadTimeInMinutes);
//        model.addAttribute("downloadTimeInHours", downloadTimeInHours);

        LocalDateTime startDateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime endDateTime = startDateTime.plusSeconds((long) downloadTimeInSeconds);

        String humanUnderstandableDownloadTime =
                calculatorService.calculateHumanUnderstandableDownloadTime(fileSize, internetSpeed, isGB);
        model.addAttribute("humanUnderstandableDownloadTime", humanUnderstandableDownloadTime);
        model.addAttribute("endDateTime", endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return "result";
    }
}


