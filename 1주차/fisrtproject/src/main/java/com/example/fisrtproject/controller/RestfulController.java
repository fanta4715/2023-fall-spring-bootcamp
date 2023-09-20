package com.example.fisrtproject.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestfulController {

    @GetMapping("/rest")
    public List<Integer> list(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        return list;
    }

}
