package com.example.people.management;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.people.management.controller.ApplicationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PeopleManagementApplicationTests {

    @Autowired
    private ApplicationController controller;

	@Test
	void contextLoads() {
	    assertThat(controller).isNotNull();
	}

}
