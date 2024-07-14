package com.example.download_time_calculator.controller;

import com.example.download_time_calculator.service.DownloadTimeCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DownloadTimeCalculatorController {

    @Autowired
    private DownloadTimeCalculatorService calculatorService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam("fileSize") double fileSize,
                            @RequestParam("internetSpeed") double internetSpeed,
                            @RequestParam("unit") String unit,
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

//        double downloadTimeInSeconds = calculatorService.calculateDownloadTimeInSeconds(fileSize, internetSpeed, isGB);
//        double downloadTimeInMinutes = calculatorService.calculateDownloadTimeInMinutes(fileSize, internetSpeed, isGB);
//        double downloadTimeInHours = calculatorService.calculateDownloadTimeInHours(fileSize, internetSpeed, isGB);

//        model.addAttribute("downloadTimeInSeconds", downloadTimeInSeconds);
//        model.addAttribute("downloadTimeInMinutes", downloadTimeInMinutes);
//        model.addAttribute("downloadTimeInHours", downloadTimeInHours);

        String humanUnderstandableDownloadTime =
                calculatorService.calculateHumanUnderstandableDownloadTime(fileSize, internetSpeed, isGB);
        model.addAttribute("humanUnderstandableDownloadTime", humanUnderstandableDownloadTime);

        return "result";
    }
}


