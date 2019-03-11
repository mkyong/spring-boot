package com.mkyong;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.assertj.core.api.Assertions.assertThat;

public class TestApplication {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void testDefaultProfile() {
        Application.main(new String[0]);
        String output = this.outputCapture.toString();
        assertThat(output).contains("Today is sunny day!");
    }

    @Test
    public void testRainingProfile() {
        System.setProperty("spring.profiles.active", "raining");
        Application.main(new String[0]);
        String output = this.outputCapture.toString();
        assertThat(output).contains("Today is raining day!");
    }

    @Test
    public void testRainingProfile_withDoption() {
        Application.main(new String[]{"--spring.profiles.active=raining"});
        String output = this.outputCapture.toString();
        assertThat(output).contains("Today is raining day!");
    }

    @After
    public void after() {
        System.clearProperty("spring.profiles.active");
    }

}
