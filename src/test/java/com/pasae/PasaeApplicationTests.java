package com.pasae;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pasae.configuration.PasaeApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PasaeApplication.class)
@WebAppConfiguration
public class PasaeApplicationTests {

	@Test
	public void contextLoads() {
	}

}
