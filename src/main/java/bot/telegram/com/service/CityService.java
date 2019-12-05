package bot.telegram.com.service;

import bot.telegram.com.entity.City;
import bot.telegram.com.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    public City findCityByName(String name) {
        return cityRepository.findCityByName(name);
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public ResponseEntity createCity(City city) {
        try {
            cityRepository.save(city);
            return new ResponseEntity<>("Добавлено", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Такой город уже есть в списке", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity updateCity(City city, String description) {
        if (city == null) {
            return new ResponseEntity<>("Такого города нет в базе", HttpStatus.BAD_REQUEST);
        }
        city.setDescription(description);
        cityRepository.save(city);
        return new ResponseEntity<>("Добавлено", HttpStatus.OK);
    }

    public ResponseEntity deleteCity(City city) {
        cityRepository.delete(city);
        return new ResponseEntity<>("Удалено", HttpStatus.OK);
    }
}
