package bot.telegram.com.controller;

import bot.telegram.com.entity.City;
import bot.telegram.com.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    @Autowired
    CityRepository cityRepository;

    @GetMapping("/qwe")
    public String home() {
        System.out.println("qwe");
        return "Hello World!";
    }

}
