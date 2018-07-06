package com.fm.internal.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @RequestMapping(method = RequestMethod.GET, path = "/lucky")
    public Dog getDog() {
        return new Dog();
    }

    private static class Dog {
        private String tailColor = "red";
        private int age = 10;

        public String getTailColor() {
            return tailColor;
        }

        public void setTailColor(String tailColor) {
            this.tailColor = tailColor;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
