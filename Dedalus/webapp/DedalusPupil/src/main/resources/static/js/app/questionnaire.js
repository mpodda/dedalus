const templatesUri = contextPath + 'templates';
const loadQuestionsUri = contextPath + 'data/questions/next';
const loadPreviousQuestionsUri = contextPath + 'data/questions/previous';
const loadStepsUri = contextPath + 'data/steps/current';
const loadPreviousStepsUri = contextPath + 'data/steps/previous';
const saveAnswersUri =  contextPath + 'data/answers';
const isCompletedUri = contextPath + 'isCompleted';


async function isQuestionnaireCompleted () {
	return new Promise((resolve, reject) => {
		Connection.get(isCompletedUri, false).then((response) => {
			resolve(response.data);
		});
	});
}

async function loadStepsTemplate () {
	return new Promise((resolve, reject) => {
		TemplateLoader.loadTemplate(templatesUri, "stepsTemplate").then((response) => {
			resolve(response);
		})
	});
}

async function loadEnabledStepTemplate () {
	return new Promise((resolve, reject) => {
		TemplateLoader.loadTemplate(templatesUri, "enabledStepTemplate").then((response) => {
			resolve(response);
		})
	});
}

async function loadDisabledStepsTemplate () {
	return new Promise((resolve, reject) => {
		TemplateLoader.loadTemplate(templatesUri, "disabledStepTemplate").then((response) => {
			resolve(response);
		})
	});
}

async function loadQuestionTemplate () {
	return new Promise((resolve, reject) => {
		TemplateLoader.loadTemplate(templatesUri, "questionTemplate").then((response) => {
			resolve(response);
		})
	});
}

async function loadValueTemplate () {
	return new Promise((resolve, reject) => {
		TemplateLoader.loadTemplate(templatesUri, "valueTemplate").then((response) => {
			resolve(response);
		})
	});
}

async function renderSteps(stepsTemplate, enabledStepTemplate, disabledStepsTemplate, previousSteps = false) {
	return new Promise((resolve, reject) => {
		Connection.get(previousSteps?loadPreviousStepsUri:loadStepsUri).then((response) => {
			
			let stepsGrid = new Grid(response.data,
			        document.getElementById("stepsPlaceHolder"),
			        stepsTemplate);
			
			stepsGrid.renderers = [
				{
					"name": "stepNo",
					"code": (element, modelObject, gr) => {
						element.innerHTML = "";
						
						let currentTemplate = modelObject["isCurrent"] ?enabledStepTemplate.cloneNode(true):disabledStepsTemplate.cloneNode(true);
						element.appendChild(currentTemplate);
						
						gr.form.addComponent(new Component("stepNo", XBrowser.getElement(element, "stepNo1")));
						gr.form.addComponent(new Component("stepNo", XBrowser.getElement(element, "stepNo2")));
					}
				}
			];
			
			stepsGrid.renderGrid();
			resolve(stepsTemplate);
		});
	});
}

async function renderGrid(questionTemplate, valueTemplate, previousQuestions = false) {
	return new Promise((resolve, reject) => {
		Connection.get(previousQuestions?loadPreviousQuestionsUri:loadQuestionsUri).then((response) => {

			let answerGrid = new Grid(response.data,
			        document.getElementById("questionsPlaceHolder"),
			        questionTemplate);
			
			answerGrid.components = [
	            {
	                "id": "value",
	                "code": function (gr, id, element, objectValue) {
	                	gr.form.addComponent(new RadioSet("value", [
	                		XBrowser.getElement(element, "value_" + gr.model["question"]["number"] + "_0"),
	                		XBrowser.getElement(element, "value_" + gr.model["question"]["number"] + "_1"),
	                		XBrowser.getElement(element, "value_" + gr.model["question"]["number"] + "_2"),
	                		XBrowser.getElement(element, "value_" + gr.model["question"]["number"] + "_3"),
	                		XBrowser.getElement(element, "value_" + gr.model["question"]["number"] + "_4")
	                		]
	                	));
	                }
	            }
			];
			
			answerGrid.renderGrid();
			
			//d3.select(document.getElementById("wizard")).style("opacity", 0.0).transition().duration(3000).ease(d3.easeLinear).style("opacity", 1);
			
			resolve(answerGrid);
		});
	});
}


async function renderPrevious() {
	/* Steps */
	let enabledStepTemplate = await loadEnabledStepTemplate();
	let disabledStepsTemplate = await loadDisabledStepsTemplate();
	let stepsTemplate = await loadStepsTemplate();
	let stepsGrid = await renderSteps(stepsTemplate, enabledStepTemplate, disabledStepsTemplate, true);

	/* Questions */
	let gridTemplate = await loadQuestionTemplate();
	let valueTemplate = await loadValueTemplate();
	answerGrid = await renderGrid(gridTemplate, valueTemplate, true);
	
}

async function init() {
	d3.select(document.getElementById("wizard")).style("opacity", 0.0).transition().duration(3000).ease(d3.easeLinear).style("opacity", 1);
	
	const isCompleted = await isQuestionnaireCompleted();
	
	const reload = (isCompleted === 'true');
	
	if  (reload) {
		console.info("Reload window");
		window.location.href = window.location.href;
	}
	
	/* Steps */
	let enabledStepTemplate = await loadEnabledStepTemplate();
	let disabledStepsTemplate = await loadDisabledStepsTemplate();
	let stepsTemplate = await loadStepsTemplate();
	let stepsGrid = await renderSteps(stepsTemplate, enabledStepTemplate, disabledStepsTemplate);
	
	/* Questions */
	let gridTemplate = await loadQuestionTemplate();
	let valueTemplate = await loadValueTemplate();
	answerGrid = await renderGrid(gridTemplate, valueTemplate);
}

(async () => {
    await init();
})();


async function saveAnswers (answers) {
	/*
	return new Promise((resolve, reject) => {
		resolve (
			Connection.post(saveAnswersUri, answers).then((response) => {
				resolve(response.data);
			})
		)
	}).catch ((e) => {
		alert("Σφάλμα κατά την καταχώριση: " + e.data);
	});
	*/
	
	return new Promise((resolve, reject) => {
		Connection.post(saveAnswersUri, answers).then((response) => {
			resolve(response.data);
		}).catch ((e) => {
			reject(e);
		});

		
		
	});
}


XBrowser.addHandler(document.getElementById("btnSaveQuestions"), "click", async (e) => {
	try {
		let a = await saveAnswers(answerGrid.gridModel);
		await init();
	} catch (e) {
		alert("Σφάλμα κατά την καταχώρηση: " + e.data);
	}
	 
});


XBrowser.addHandler(document.getElementById("btnPreviousQuestions"), "click", async (e) => {
	await renderPrevious();
});