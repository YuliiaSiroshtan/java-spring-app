package com.api.gateway.controllers;

import com.api.gateway.service.GatewayService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Iterator;

public abstract class CRUDController<T> {
    private final GatewayService<T> service;

    public CRUDController(GatewayService<T> service) {
        this.service = service;
    }

    @GetMapping()
    public Iterator<T> getAll(@RequestParam int size, @RequestParam int page) {
        return service.getAll(size, page);
    }

    @GetMapping("/{id}")
    public T getOne(@PathVariable Integer id) {
        return service.getOne(id);
    }

    @PostMapping("/find")
    public Iterator<T> find(@RequestBody String field) {
        return service.find(field);
    }

    @PostMapping
    public T save(@Valid @RequestBody T object) {
        return service.save(object);
    }

    @PutMapping
    public T update(@Valid @RequestBody T object) {
        return service.update(object);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return service.delete(id);
    }
}
