package dedalus.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import dedalus.domain.Category;
import dedalus.service.api.CategoryService;
import net.minidev.json.JSONArray;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	 @MockBean
	 private CategoryService categoryService;
	
	 
	 @WithMockUser(value = "springTester") 
	 @Test
	 public void allCategories_Json_Fields_Integration_Test () throws Exception {
		 Category category = new Category();
		 category.setId(1L);
		 category.setDescription("Test Category");

		 final List<Category> categories = Arrays.asList(category);
		 
		 given(categoryService.findAll()).willReturn(categories) ;
		 
		 mvc.perform (
				get("/data/categories")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].description", is("Test Category")))
				.andDo(print())
				;
			
	}
	 
	 @WithMockUser(value = "springTester") 
	 @Test
	 public void allCategories_Array_Length_Integration_Test () throws Exception {
		 Category category = new Category();
		 category.setId(1L);
		 category.setDescription("Test Category");
		 
		 final List<Category> categories = Arrays.asList(category);
		 
		 given(categoryService.findAll()).willReturn(categories) ;
		 
		 mvc.perform (
				 get("/data/categories")
				 	.contentType(MediaType.APPLICATION_JSON)
		 )
		.andExpect(status().isOk())
	
		.andExpect(jsonPath("$",   new BaseMatcher<List<Category>>() {
			@Override
			public boolean matches(Object object) {
				if (object == null) {
					return false;
				}
				
				if (!(object instanceof JSONArray)) {
					return false;
				}
				
				return ((JSONArray)object).size() == categories.size();
			}

			@Override
			public void describeTo(Description description) {
			
			}
		}));
	 }	 
}
