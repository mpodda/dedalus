package dedalus.service.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dedalus.domain.Category;
import dedalus.repository.CategoryRepository;
import dedalus.repository.ParameterRepository;

@Service
public class CategoryService extends ParameterService<Category> {
	private CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public ParameterRepository<Category> getParameterRepository() {
		return this.categoryRepository;
	}
	
	public List<Category> findAllExcept(Category categoryToExlude) {
		if (categoryToExlude == null) {
			return this.findAll();
		}
		
		return this.findAll().stream().filter( c-> !c.equals(categoryToExlude)).collect(Collectors.toList());
	}

	public List<Category> findAllExcept(Category categoryToExlude, Category category2ToExlude, Category subCategoryToExlude) {
		if (categoryToExlude == null && category2ToExlude == null && subCategoryToExlude == null) {
			return this.findAll();
		}

		
		return this.findAll().stream()
				.filter( c-> !c.equals(categoryToExlude) && !c.equals(category2ToExlude) && !c.equals(subCategoryToExlude))
				.collect(Collectors.toList());
	}

	/*
	public static void main(String[] args) {
		List<String> persons = new ArrayList<String>();
		
		persons.add("GR-Giorgos");
		persons.add("DE-Johan");
		persons.add("GB-John");
		persons.add("GR-Pantelis");
		persons.add("IT-Antonio");
		
		String filter1 = "IT-", filter2 = "DE-";
		
//		List<String> filtered = persons.stream().filter(
//					p -> (!p.startsWith(filter1) && !p.startsWith(filter2)) 
//				).collect(Collectors.toList());
		

		List<String> filtered = persons.stream().filter(
				p -> (!p.startsWith(filter1))
			)
				.filter( p -> !p.startsWith(filter2))
			.collect(Collectors.toList());

		filtered.forEach (
			p-> {System.out.println(p);}
		);
		
	}
	*/
}