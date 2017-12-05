package com.topicme.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.WebApplicationContext;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:topicme-web-servlet.xml")
@WebAppConfiguration
public class MainControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;
	
	@Mock
	SessionStatus sessionStatus;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		Assert.assertNotNull(mockMvc);
	}

	@Test
	public void testAddLink() throws Exception {
		
		User user = new User("khoa", "1", AuthorityUtils.createAuthorityList("ROLE_USER"));
		TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);
		SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
		
		

		mockMvc.perform(MockMvcRequestBuilders.post("/index/addTopic")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				 	.param("link", "https://www.gobalto.com/")
		            .param("notes", "GoBalto Homepage")
		            .param("topic", "spring_mvc")
		            .sessionAttr("status", sessionStatus));//.andExpect(MockMvcResultMatchers.forwardedUrl("/index/spring_mvc"));
		
		//mockMvc.perform(MockMvcRequestBuilders.get("/hello/khoa").accept(MediaType.ALL)).andExpect(status().isOk());
	}
	
	
	
	
}
