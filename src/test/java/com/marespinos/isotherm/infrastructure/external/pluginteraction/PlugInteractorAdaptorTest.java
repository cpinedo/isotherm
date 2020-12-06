package com.marespinos.isotherm.infrastructure.external.pluginteraction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlugInteractorAdaptorTest {
    @Autowired
    PlugInteractorAdaptor instance;

//    @Test
//    void status(){
//        assertTrue(instance.retrieveSwitchStatus());
//    }
//
//    @Test
//    void on(){
//        assertDoesNotThrow(() -> instance.turnSwitchOn());
//    }
//
//    @Test
//    void off(){
//        assertDoesNotThrow(() -> instance.turnSwitchOff());
//    }

}