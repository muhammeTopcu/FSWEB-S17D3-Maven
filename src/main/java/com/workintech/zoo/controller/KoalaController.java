package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private Map<Integer, Koala> koalas; // 🔥 Long → Integer

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public Collection<Koala> getAll() {
        return koalas.values();
    }

    @GetMapping("/{id}")
    public Koala getById(@PathVariable int id) { // 🔥 Long → int

        Koala k = koalas.get(id);

        if (k == null) {
            throw new ZooException("Koala not found", HttpStatus.NOT_FOUND);
        }

        return k;
    }

    @PostMapping
    public Koala save(@RequestBody Koala koala) {

        if (koala.getId() == 0 || koala.getName() == null) { // 🔥 null değil 0 kontrolü
            throw new ZooException("Invalid koala", HttpStatus.BAD_REQUEST);
        }

        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable int id, @RequestBody Koala koala) {

        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala not found", HttpStatus.NOT_FOUND);
        }

        koala.setId(id);
        koalas.put(id, koala);

        return koala;
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable int id) {

        Koala k = koalas.get(id);

        if (k == null) {
            throw new ZooException("Koala not found", HttpStatus.NOT_FOUND);
        }

        koalas.remove(id);
        return k;
    }
}