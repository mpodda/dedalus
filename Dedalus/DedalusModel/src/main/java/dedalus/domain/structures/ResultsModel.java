package dedalus.domain.structures;

import java.io.Serializable;
import java.util.List;

import dedalus.domain.Category;
import dedalus.domain.Holland;
import dedalus.domain.ScientificField;

public class ResultsModel implements Serializable {
	private static final long serialVersionUID = -2714233377191573934L;
	
	List<ResultSet<Category>> categoryResultSet;
	List<ResultSet<ScientificField>> scientificFieldResultSet;
	List<ResultSet<Holland>> hollandResultSet;
	List<ResultSet<Category>> topCategoryForChartResultSet;
	List<ResultSet<Category>> topCategoryForListResultSet;
	
	 
	public ResultsModel() {
		
	}

	public List<ResultSet<Category>> getCategoryResultSet() {
		return categoryResultSet;
	}

	public void setCategoryResultSet(List<ResultSet<Category>> categoryResultSet) {
		this.categoryResultSet = categoryResultSet;
	}

	public List<ResultSet<ScientificField>> getScientificFieldResultSet() {
		return scientificFieldResultSet;
	}

	public void setScientificFieldResultSet(List<ResultSet<ScientificField>> scientificFieldResultSet) {
		this.scientificFieldResultSet = scientificFieldResultSet;
	}

	public List<ResultSet<Holland>> getHollandResultSet() {
		return hollandResultSet;
	}

	public void setHollandResultSet(List<ResultSet<Holland>> hollandResultSet) {
		this.hollandResultSet = hollandResultSet;
	}

	public List<ResultSet<Category>> getTopCategoryForChartResultSet() {
		return topCategoryForChartResultSet;
	}

	public void setTopCategoryForChartResultSet(List<ResultSet<Category>> topCategoryForChartResultSet) {
		this.topCategoryForChartResultSet = topCategoryForChartResultSet;
	}

	public List<ResultSet<Category>> getTopCategoryForListResultSet() {
		return topCategoryForListResultSet;
	}

	public void setTopCategoryForListResultSet(List<ResultSet<Category>> topCategoryForListResultSet) {
		this.topCategoryForListResultSet = topCategoryForListResultSet;
	}
}