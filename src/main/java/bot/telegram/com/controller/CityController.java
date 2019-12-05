package bot.telegram.com.controller;

import bot.telegram.com.entity.City;
import bot.telegram.com.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CityController {

    @Autowired
    CityService cityService;

    @PostMapping("/create")
    public ResponseEntity create(String name, String description) {
        return ResponseEntity.ok(cityService.createCity(new City(name, description)));
    }

    @PutMapping("/update")
    public ResponseEntity update(String name, String description) {
        return ResponseEntity.ok(cityService.updateCity(cityService.findCityByName(name), description));
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(String name) {
        return ResponseEntity.ok(cityService.deleteCity(cityService.findCityByName(name)));
    }

}
