package com.mkyong.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @ResponseBody
    @GetMapping("/")
    public String hello() {
        return "Hello Controller";
    }
    @PostMapping("/create")
    public Emp createEmps(@RequestBody Emp emp){
        return save(emp);
    }
    @GetMapping("/getbyid/{id})
    public Emp getByEmpId(@PathVariable int id){
        return getEmpById(id);
}
public String deleteId(){
    return null;
}
