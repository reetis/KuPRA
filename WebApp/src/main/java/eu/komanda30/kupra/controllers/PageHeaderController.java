package eu.komanda30.kupra.controllers;

import org.springframework.stereotype.Component;

@Component("pageHeader")
public class PageHeaderController {
    public String getTestString() {
        return "Hello world!";
    }
}
