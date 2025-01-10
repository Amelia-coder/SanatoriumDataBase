package ru.vsu.cs.bordyugova_l_n;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/static-test")
public class StaticPageController {
    @GetMapping
    public String staticPage() {
        return "test-page"; // Файл test-page.html в папке templates
    }
}
