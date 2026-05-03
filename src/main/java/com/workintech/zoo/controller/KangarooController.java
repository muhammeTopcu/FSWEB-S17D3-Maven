package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos; // 🔥 Long → Integer

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public Collection<Kangaroo> getAll() {
        return kangaroos.values();
    }

    @GetMapping("/{id}")
    public Kangaroo getById(@PathVariable int id) { // 🔥 Long → int

        Kangaroo k = kangaroos.get(id);

        if (k == null) {
            throw new ZooException("Kangaroo not found", HttpStatus.NOT_FOUND);
        }

        return k;
    }

    @PostMapping
    public Kangaroo save(@RequestBody Kangaroo kangaroo) {

        if (kangaroo.getId() == 0 || kangaroo.getName() == null) {
            throw new ZooException("Invalid kangaroo", HttpStatus.BAD_REQUEST);
        }

        kangaroos.put(kangaroo.getId(), kangaroo); // artık tipler uyumlu
        return kangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable int id, @RequestBody Kangaroo kangaroo) {

        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo not found", HttpStatus.NOT_FOUND);
        }

        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);

        return kangaroo;
    }

    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable int id) {

        Kangaroo k = kangaroos.get(id);

        if (k == null) {
            throw new ZooException("Kangaroo not found", HttpStatus.NOT_FOUND);
        }

        kangaroos.remove(id);
        return k;
    }
}