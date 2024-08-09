package practice.general;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.*;
import practice.general.backend.Iphone;
import practice.general.web.GeneralController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@WebMvcTest(GeneralController.class)
class GeneralApplicationTests {

	private static final String BASE_URL = "http://localhost:8080/api";

	@Autowired
	private MockMvc mockMvc;


	@Test
	void contextLoads() {
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	void testGetEndpoint() throws Exception {

		this.mockMvc.perform(get(BASE_URL + "/info").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.[0].name").value("iPhone 13"))
				.andExpect(jsonPath("$.[0].price").value("799.0"));

		this.mockMvc.perform(get(BASE_URL + "/info/2").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[0].name").value("iPhone 12"))
				.andExpect(jsonPath("$.[0].price").value("699.0"));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	void testPostEndpoint() throws Exception {

		Iphone iphone = new Iphone();
		iphone.setDescription("First iphone");
		iphone.setName("Iphone 1");
		iphone.setId(54);
		iphone.setPrice(2500.0);

		MvcResult result =  mockMvc.perform(post(BASE_URL + "/put")
						.content(new ObjectMapper().writeValueAsString(iphone))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json"))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		assertThat(content).isEqualTo("Created 54");

	}
	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	void testDeleteEndpoint() throws Exception {

		MvcResult result =  mockMvc.perform(delete(BASE_URL + "/delete/54").accept("application/json"))
				.andExpect(status().isNoContent())
				.andExpect(content().contentType("application/json"))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		assertThat(content).isEqualTo("Deleted 54");

	}
}
