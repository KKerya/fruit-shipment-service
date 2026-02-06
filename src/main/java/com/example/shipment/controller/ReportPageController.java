package com.example.shipment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportPageController {
    @GetMapping({"/report", "/report/"})
    public String reportPage(Model model) {
        return "report";
    }
}
