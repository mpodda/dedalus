package dedalus;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

public class UnicodePropertiesPropertySourceLoader implements PropertySourceLoader {

	@Override
	public String[] getFileExtensions() {
		return new String[]{"properties"};
	}

	
	@Override
	public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
		//System.out.println("Load Prop");
		
        Properties properties = new Properties();
        properties.load(new InputStreamReader(resource.getInputStream(), "UTF-8"));
        
        if (!properties.isEmpty()) {
        	List<PropertySource<?>> propertySourceList = new ArrayList<>();
        	
        	@SuppressWarnings("rawtypes")
			PropertySource<?> propertySource = new PropertySource(name) {
				@Override
				public Object getProperty(String key) {
					System.out.println(String.format("Key = %s", key));
					return properties.getProperty(key);
				}
			};
        	
			propertySourceList.add(propertySource);
			
        	return propertySourceList;
        }
        
        
        return null;
	}

}
