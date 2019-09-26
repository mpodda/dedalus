(async () => {
	const resultsUri = contextPath + 'results';
	const tokenCodeUri = contextPath + 'token'; 
	const templatesUri = contextPath + 'templates';
	
	async function loadTemplate (templateId) {
		return new Promise(async (resolve, reject) => {
			TemplateLoader.loadTemplate(templatesUri, templateId).then((response) => {
				resolve(response);
			})
		});
	}
	
	async function getTokenCode () {
		return new Promise(async (resolve, reject) => {
			Connection.get(tokenCodeUri, false).then((response) => {
				resolve(response.data);
			});
		});
	} 
	
	async function loadResults() {
		return new Promise(async (resolve, reject) => {
			const tokenCode = await getTokenCode();
			
//			console.info("Call ", `${resultsUri}/${tokenCode}`);
			
			Connection.get(`${resultsUri}/${tokenCode}`).then((response) => {
				resolve(response.data);
			});
		});
	}
	
	
	
	/*
	 --------------
	 -- Hollands --
	 --------------
	*/

	async function renderHollands(hollands) {
		return new Promise(async (resolve, reject) => {
			console.info("Hollands: ", hollands);
		
			const hollandsTemplate = await loadTemplate("hollandTemplate"); 
			
			let hollandsGrid = new Grid(hollands,
		        document.getElementById("hollandPlaceHolder"),
		        hollandsTemplate);
		
			hollandsGrid.renderers = [
				{
					name: "cssClass",
					code: async(element, modelObject, gr) => {
						switch (modelObject["subject"]["value"]) {
							case 1: element.className = "fa fa-gears"; break;
							case 2: element.className = "fa fa-user-secret"; break;
							case 3: element.className = "fa fa-paint-brush"; break;
							case 4: element.className = "fa fa-users"; break;
							case 5: element.className = "fa fa-industry"; break;
							case 6: element.className = "fa fa-folder"; break;
							
							default: break;
						}
					}
				},
				{
					name: "descriptionEN",
					code: async(element, modelObject, gr) => {
						switch (modelObject["subject"]["value"]) {
							case 1: element.innerHTML = "Realistic"; break;
							case 2: element.innerHTML = "Investigative"; break;
							case 3: element.innerHTML = "Artistic"; break;
							case 4: element.innerHTML = "Social"; break;
							case 5: element.innerHTML = "Enterprising"; break;
							case 6: element.innerHTML = "Conventional"; break;
						
							default: break;
						}
					}
				}
				
			];
				
			hollandsGrid.renderGrid();
			
			resolve(true);
		});
				
	} 
	
	async function renderHollandsAnalytical(hollands) {
		return new Promise(async (resolve, reject) => {
			const hollandsAnalyticalTemplate = await loadTemplate("hollandAnalyticalTemplate");
			
			let hollandsAnalyticalGrid = new Grid(hollands,
			        document.getElementById("hollandAnalyticalPlaceHolder"),
			        hollandsAnalyticalTemplate);
			
			hollandsAnalyticalGrid.renderGrid();
			
			resolve(true);
		});
	}
	
	/*
	 ----------------
	 -- / Hollands --
	 ----------------
	*/
	
	
	
	/*
	 ----------------
	 -- Categories --
	 ----------------
	*/

	async function renderTopCategoriesForChart (topCategories) {
		return new Promise(async (resolve, reject) => {
			const topCategoriesTemplate = await loadTemplate("topCategoryForChartTemplate");

			let topCategoriesGrid = new Grid(topCategories,
			        document.getElementById("topCategoriesForChartPlaceHolder"),
			        topCategoriesTemplate);
			
			topCategoriesGrid.renderGrid();
			
			return (true);
		});
	}
	

	async function renderTopCategoriesForList (topCategories) {
		return new Promise(async (resolve, reject) => {
			const topCategoriesTemplate = await loadTemplate("listTemplate");

			let topCategoriesGrid = new Grid(topCategories,
			        document.getElementById("topCategoriesForListPlaceHolder"),
			        topCategoriesTemplate);
			
			topCategoriesGrid.renderers = [
				{
					name: "rowNumber",
					code: async(element, modelObject, gr) => {
						element.innerHTML = gr.index + 1;
					}
				}
			];			
			topCategoriesGrid.renderGrid();
			
			resolve (true);
		});
	}
	
	/*
	 ------------------
	 -- / Categories --
	 ------------------
	*/
	
	
	
	/*
	 -----------------------
	 -- Scientific Fields --
	 -----------------------
	*/

	async function renderScientificFields (scientificFields) {
		return new Promise(async (resolve, reject) => {
			const scientificFieldsTemplate = await loadTemplate("listTemplate");

			let scientificFieldsGrid = new Grid(scientificFields,
			        document.getElementById("scientificFieldsPlaceHolder"),
			        scientificFieldsTemplate);
			
			scientificFieldsGrid.renderers = [
				{
					name: "rowNumber",
					code: async(element, modelObject, gr) => {
						element.innerHTML = gr.index + 1;
					}
				}
			];			
			scientificFieldsGrid.renderGrid();
			
			resolve (true);
		});
	}
	
	/*
	 -------------------------
	 -- / Scientific Fields --
	 -------------------------
	*/
	
	
	async function init() {
		let results = await loadResults();
		
		await renderHollands(results["hollandResultSet"]);
		
		await renderHollandsAnalytical(results["hollandResultSet"]);
		
		// await renderTopCategoriesForChart (results["topCategoryForChartResultSet"]);
		
		await renderTopCategoriesForList (results["topCategoryForListResultSet"]);
		
		await renderScientificFields (results["scientificFieldResultSet"]);
		
		//console.info("Results: ", results);
	}
	
    await init();
})();



$(function () {
    $('.chart').easyPieChart({
        easing: 'easeOutElastic',
        delay: 3000,
        barColor: '#3498DB',
        trackColor: '#E6E9ED',
        scaleColor: false,
        lineWidth: 20,
        trackWidth: 16,
        lineCap: 'butt',
        onStep: function (from, to, percent) {
            $(this.el).find('.percent').text(Math.round(percent));
        }
    });
    var chart = window.chart = $('.chart').data('easyPieChart');
    $('.js_update').on('click', function () {
        chart.update(Math.random() * 200 - 100);
    });
});

//NProgress.done();