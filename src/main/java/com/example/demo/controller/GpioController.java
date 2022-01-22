package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.*;

@RestController
public class GpioController {

    @GetMapping("/read/{number}")
    public boolean readGPIO(@PathVariable Integer number) {

        var pi4j = Pi4J.newAutoContext();

        var config = DigitalInput.newConfigBuilder(pi4j)
                .id("input-gpio")
                .name("Input GPIO")
                .address(number)
                .pull(PullResistance.PULL_DOWN)
                .provider("pigpio-digital-input");
        var input = pi4j.create(config);

        return input.isHigh();
    }

    @GetMapping("/set/{number}/{status}")
    public boolean setStatus(@PathVariable Integer number, @PathVariable boolean status) {

        var pi4j = Pi4J.newAutoContext();

        DigitalState state = (status) ? DigitalState.HIGH : DigitalState.LOW;

        var ledConfig = DigitalOutput.newConfigBuilder(pi4j)
                .id("led")
                .name("LED Flasher")
                .address(number)
                .shutdown(state)
                .initial(state)
                .provider("pigpio-digital-output");

        var led = pi4j.create(ledConfig);

        return status;
    }
}
