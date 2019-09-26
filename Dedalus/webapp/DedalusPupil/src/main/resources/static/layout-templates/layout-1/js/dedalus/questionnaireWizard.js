function QuestionnaireWizard(token, wizardPlaceHolderId, questionsPlaceHolderId){
	this.token = token;
	this.wizardPlaceHolderId = wizardPlaceHolderId;
	
	this.templateLoader = new TemplateLoader(contextPath + "/questionTemplate"); 
	var questionnaireWizard = this;
	
	//Current Step ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	this.currentStep;
	this.cnCurrentStep = new Connection(contextPath + "/getCurrentStep", "GET", "DATAROW", "ERROR");
	this.cnCurrentStep.onResponse = function(){
		var results = this.responseText.trim();
		questionnaireWizard.currentStep = eval("(function(){return " + results + ";})()");
		questionnaireWizard.renderCurrentStep();
		questionnaireWizard.onCurrentStepLoaded();
	};
//	this.cnCurrentStep.async = true;
	this.cnCurrentStep.setMethod("GET");
	this.cnCurrentStep.SetFunctionName("getCurrentStep");
	this.cnCurrentStep.setArguments("token=" + token);
	
	this.onCurrentStepLoaded = function(){};
	
	//this.getCurrentStep();

	//Next Steps ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	this.nextSteps = new Array();
	this.cnNextSteps = new Connection(contextPath + "/getNextSteps", "GET", "DATAROW", "ERROR");
	this.cnNextSteps.onResponse = function(){
		var results = this.responseText.trim();
		questionnaireWizard.nextSteps = eval("(function(){return " + results + ";})()");
		questionnaireWizard.onNextStepsLoaded();
	};
	//this.cnNextSteps.async = true;
	this.cnNextSteps.setMethod("GET");
	this.cnNextSteps.SetFunctionName("getNextSteps");
	this.cnNextSteps.setArguments("token=" + token);
	this.onNextStepsLoaded = function(){};
	
	//this.getNextSteps();
	
	// Questions ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	this.questionsPlaceHolderId = questionsPlaceHolderId;
	this.questionsGrid;
	
	this.cn = new Connection(contextPath + "/getQuestions", "GET", "DATAROW", "ERROR");
	
	this.cn.onResponse = function(){
		var results = this.responseText.trim();
		var questions = eval("(function(){return " + results + ";})()");
		questionnaireWizard.renderQuestions(questions);
	}
	
	//this.cn.async = true;
	this.cn.setMethod("GET");
	this.cn.SetFunctionName("getQuestions");
	this.cn.setArguments("token=" + token);

	//this.loadQuestions();
	
	// Save answers ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	this.cnSaveAnswers = new Connection(contextPath + "/setPupilsQuestionAnswer", "GET", "DATAROW", "ERROR");
	this.cnSaveAnswers.setMethod("GET");
	this.cnSaveAnswers.SetFunctionName("setPupilsQuestionAnswer");	
	
	return this;
}

// --------------------------------------------------------------------------------------------------

QuestionnaireWizard.prototype.loadQuestions = function(){
	return this.cn.callNoXML();
	
};

//--------------------------------------------------------------------------------------------------

QuestionnaireWizard.prototype.getCurrentStep = function(){
	var results = this.cnCurrentStep.callNoXML();
	results = results.trim();
	
	this.currentStep = eval("(function(){return " + results + ";})()");
	
	return this.currentStep;
};

//--------------------------------------------------------------------------------------------------

QuestionnaireWizard.prototype.getNextSteps = function(){
	var results = this.cnNextSteps.callNoXML();
	results = results.trim();
	this.nextSteps = eval("(function(){return " + results + ";})()");
	
	return this.nextSteps;
};

//--------------------------------------------------------------------------------------------------

QuestionnaireWizard.prototype.renderCurrentStep = function(){
	var wizardStepTemplate = this.templateLoader.loadTemplate("wizard-step-template");
	document.getElementById("wizard_steps_placeHolder").appendChild(wizardStepTemplate);
	
	var currentStepTemplate = this.templateLoader.loadTemplate("step-template");
	
	currentStepTemplate.id = "step-" + this.currentStep;
	document.getElementById(this.wizardPlaceHolderId).appendChild(currentStepTemplate);
};

//--------------------------------------------------------------------------------------------------

QuestionnaireWizard.prototype.renderSteps = function(){
	
	/*
	var wizardStepTemplate = this.templateLoader.loadTemplate("wizard-step-template");
	wizardStepTemplate.id="";
	
	var stepsList = new Array();
	stepsList.push(new WizardStep(this.currentStep));
	
	for (var i=0; i<this.nextSteps.length; i++){
		stepsList.push(new WizardStep(this.nextSteps[i]));
	}
	
	//alert(JSON.stringify(stepsList));
	
	var stepsGrid = new Grid(stepsList, document.getElementById("wizard_steps_placeHolder"), wizardStepTemplate);
	stepsGrid.renderGrid();
	
	//alert(document.getElementById("wizard_steps_placeHolder").outerHTML);
	
	//alert(wizardStepTemplate.outerHTML);
	*/
	//Steps ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
	document.getElementById("wizard_steps_placeHolder").appendChild(renderStep(this.currentStep));
	
	for (var i=0; i<this.nextSteps.length; i++){
		if (this.nextSteps[i]=="0"){
			this.nextSteps[i] = "-";
		}
		document.getElementById("wizard_steps_placeHolder").appendChild(renderStep(this.nextSteps[i]));
		
	}
	
	//Questions ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	var currentStepTemplate = this.templateLoader.loadTemplate("step-template");
	
	currentStepTemplate.id = "step-" + this.currentStep;
	document.getElementById(this.wizardPlaceHolderId).appendChild(currentStepTemplate);
};

//--------------------------------------------------------------------------------------------------

QuestionnaireWizard.prototype.renderQuestions = function (questions){
	 //this.templateLoader = new TemplateLoader(contextPath + "/questionTemplate");
	var questionTemplate = this.templateLoader.loadTemplate("questionTemplate");
	
	this.questionsGrid = new Grid(questions, document.getElementById(this.questionsPlaceHolderId), questionTemplate);
	
	this.questionsGrid.onGridRowRender = function(gridRow){
		gridRow.defineComponent = function(id, element, objectValue){
			if (id == "isYes"){
				element.innerHTML = "";
				var randomName = "answer_" + getRandom();  
				var activeClassName = "btn btn-default active";
				var inactiveClassName = "btn btn-default";
				var dataToggleClassName="btn-default"; 
				var dataTogglePassiveClass="btn-primary";

				
				//YES ----------------------------------------------
				var isYesRadio = document.createElement("input");
				isYesRadio.type="radio";
				isYesRadio.value="1";
				isYesRadio.name = randomName;
				isYesRadio.id = "isYes_" + getRandom();
				
				var isYesLabel = document.createElement("label");
				isYesLabel.className = inactiveClassName;
				if (objectValue==1){
					isYesLabel.className = activeClassName;
				}
				isYesLabel.setAttribute("for", isYesRadio.id);
				
				isYesLabel.innerHTML = "ΝΑΙ";
				
				//NO -----------------------------------------------
				var isNoRadio = document.createElement("input");
				isNoRadio.type="radio";
				isNoRadio.value="2";
				isNoRadio.name = randomName;
				isNoRadio.id = "isNo_" + getRandom();
				
				var isNoLabel = document.createElement("label");
				isNoLabel.className = inactiveClassName;
				if (objectValue==2){
					isNoLabel.className = activeClassName;
				}
				isNoLabel.setAttribute("for", isNoRadio.id);
				isNoLabel.innerHTML = "ΟΧΙ";
				
				//-----------------------------------------------------
				
				isYesLabel.appendChild(isYesRadio);
				isNoLabel.appendChild(isNoRadio);
				
				element.appendChild(isYesLabel);
				element.appendChild(isNoLabel);
				
				var radioElements = new Array();
				radioElements.push(isYesRadio);
				radioElements.push(isNoRadio);
				
				var radioSet = new RadioSet(radioElements); 
				
				this.form.addComponent(new Component(id, radioSet));
			}
		};
	};
	this.questionsGrid.rowMouseOverClass = "form-group";
	this.questionsGrid.rowSelectedClass = "form-group";
	this.questionsGrid.renderGrid();	
};

//--------------------------------------------------------------------------------------------------

QuestionnaireWizard.prototype.getAnswers = function(){
	return this.questionsGrid.getGridModelObject();
};

//--------------------------------------------------------------------------------------------------

QuestionnaireWizard.prototype.saveAnswers = function(){
	this.cnSaveAnswers.setArguments("data=" + JSON.stringify(this.questionsGrid.getGridModelObject()));
	this.cnSaveAnswers.callNoXML();
	
};

//--------------------------------------------------------------------------------------------------

QuestionnaireWizard.prototype.init = function(){
	
	var ps = this.currentStep/*-1*/;
	if (document.getElementById("step-" + ps) != null){
		//alert(document.getElementById(this.wizardPlaceHolderId).innerHTML);
		document.getElementById("step-" + ps).parentNode.removeChild(document.getElementById("step-" + ps));
		//document.getElementById(this.wizardPlaceHolderId).childNodes[0].removeChild(document.getElementById("step-" + ps));
	}
//	
//	if (document.getElementById("wizard_steps_placeHolder").childNodes.length>0){
//        for (var i = document.getElementById("wizard_steps_placeHolder").childNodes.length - 1; ; i--) {
//        	document.getElementById("wizard_steps_placeHolder").removeChild(document.getElementById("wizard_steps_placeHolder").childNodes[i]);
//            if (i == 0) {
//                break;
//            }
//        }		
//	}	
	
	this.getCurrentStep();
	this.getNextSteps();

	this.renderSteps();

	var results = this.loadQuestions();
	results = results.trim();
	
	var questions = eval("(function(){return " + results + ";})()");
	this.renderQuestions(questions);
};

//--------------------------------------------------------------------------------------------------

QuestionnaireWizard.prototype.showNextAnswers = function(){
	//alert("Next");
	
	questionnaireWizard.init();
};

// ===================================================================================================

////////////
// Static //
////////////

function getRandom(){
	return parseInt(Math.random()*10000000);
}

//----------------------------------------------------------------------------------------

function renderStep(stepNo){
	//<li id="wizard-step-template"><a href="#step-1"><span class="step_no">1</span><span class="step_descr">Βήμα 1</span></a></li>
	
	var li = document.createElement("li");
	
	var anchor = document.createElement("a");
	anchor.setAttribute("href", "#step-" + stepNo);
	
	var spanStepNo = document.createElement("span");
	spanStepNo.className = "step_no";
	spanStepNo.innerHTML = stepNo;
	
	var spanDescription = document.createElement("span");
	spanDescription.className = "step_descr";
	if (stepNo != "-"){
		spanDescription.innerHTML = "Βήμα " + stepNo;
	}
	
	anchor.appendChild(spanStepNo);
	anchor.appendChild(spanDescription);
	li.appendChild(anchor);
	
	return li;
}
//===================================================================================================
/*
var cn;
var questionsGrid;

//Get questions ----------------------------------------------------------------------------------

cn = new Connection(contextPath + "/getQuestions", "GET", "DATAROW", "ERROR");

cn.onResponse = function(){
	//alert (this.callNoXML());
	//alert(this.responseText.trim());
	
	var results = this.responseText.trim();
	
	var questions = eval("(function(){return " + results + ";})()");
	
	//alert(JSON.stringify(questions[0]["question"]["questionnaire"]["id"]));
	
//	var questionsArray = new Array();
	
//	for (var i=0; i<questions.length; i++){
//		questionsArray.push(new QuestionAnswer(questions[i]["id"], questions[i]["question"]["id"], questions[i]["question"]["description"], questions[i]["isYes"], questions[i]["pupil"]["id"], questions[i]["question"]["questionnaire"]["id"]));
//	} 
	
	//alert(JSON.stringify(questionsArray));
	
	//renderQuestions(questionsArray);
	renderQuestions(questions);
}

cn.async = true;
cn.setMethod("GET");
cn.SetFunctionName("getQuestions");
cn.setArguments("token=" + token);

cn.callNoXML();

// ----------------------------------------------------------------------------------------

function getRandom(){
	return parseInt(Math.random()*10000000);
}

//----------------------------------------------------------------------------------------

function renderQuestions(questions){
	var templateLoader = new TemplateLoader(contextPath + "/questionTemplate");
	var questionTemplate = templateLoader.loadTemplate("questionTemplate");
	
	questionsGrid = new Grid(questions, document.getElementById("questionsPlaceHolder"), questionTemplate);
	
	questionsGrid.onGridRowRender = function(gridRow){
		gridRow.defineComponent = function(id, element, objectValue){
			if (id == "isYes"){
				element.innerHTML = "";
				var randomName = "answer_" + getRandom();  
				var activeClassName = "btn btn-default active";
				var inactiveClassName = "btn btn-default";
				var dataToggleClassName="btn-default"; 
				var dataTogglePassiveClass="btn-primary";

				//alert(objectValue==true);
				
				//YES ----------------------------------------------
				var isYesRadio = document.createElement("input");
				isYesRadio.type="radio";
				isYesRadio.value="1";
				isYesRadio.name = randomName;
				isYesRadio.id = "isYes_" + getRandom();
				
				var isYesLabel = document.createElement("label");
				isYesLabel.className = inactiveClassName;
				if (objectValue==1){
					isYesLabel.className = activeClassName;
				}
				isYesLabel.setAttribute("for", isYesRadio.id);
				
				isYesLabel.innerHTML = "ΝΑΙ";
				
				//NO -----------------------------------------------
				var isNoRadio = document.createElement("input");
				isNoRadio.type="radio";
				isNoRadio.value="2";
				isNoRadio.name = randomName;
				isNoRadio.id = "isNo_" + getRandom();
				
				var isNoLabel = document.createElement("label");
				isNoLabel.className = inactiveClassName;
				if (objectValue==2){
					isNoLabel.className = activeClassName;
				}
				isNoLabel.setAttribute("for", isNoRadio.id);
				isNoLabel.innerHTML = "ΟΧΙ";
				
				//-----------------------------------------------------
				
//				element.appendChild(isYesRadio);
//				element.appendChild(isYesLabel);
//				element.appendChild(isNoRadio);
//				element.appendChild(isNoLabel);
				
				isYesLabel.appendChild(isYesRadio);
				isNoLabel.appendChild(isNoRadio);
				
				element.appendChild(isYesLabel);
				element.appendChild(isNoLabel);
				
				var radioElements = new Array();
				radioElements.push(isYesRadio);
				radioElements.push(isNoRadio);
				
				var radioSet = new RadioSet(radioElements); 
				
				this.form.addComponent(new Component(id, radioSet));
			}
		};
	};
	questionsGrid.rowMouseOverClass = "form-group";
	questionsGrid.rowSelectedClass = "form-group";
	questionsGrid.renderGrid();
}
*/
//----------------------------------------------------------------------------------------

var questionnaireWizard = new QuestionnaireWizard(token, "wizard", "questionsPlaceHolder");

questionnaireWizard.onCurrentStepLoaded = function(){
	//alert(this.currentStep);
	this.getNextSteps();
}

questionnaireWizard.onNextStepsLoaded = function(){
	//alert(this.nextSteps);
	this.loadQuestions();
}

questionnaireWizard.init();


//----------------------------------------------------------------------------------------

function getAnswers(){
	
	/*
	alert(JSON.stringify(questionnaireWizard.questionsGrid.getGridModelObject()[0]));
	
	if (questionnaireWizard.questionsGrid.getGridModelObject()[0].hasOwnProperty("isYes")){
		alert(questionnaireWizard.questionsGrid.getGridModelObject()[0]["isYes"]==1 || questionnaireWizard.questionsGrid.getGridModelObject()[0]["isYes"]==2);
	}
	*/
	
	var numberOfNonAnsweredQuestions = 0;
	
	for (var i=0; i<questionnaireWizard.questionsGrid.getGridModelObject().length; i++){
		if (questionnaireWizard.questionsGrid.getGridModelObject()[i].hasOwnProperty("isYes")){
			if (questionnaireWizard.questionsGrid.getGridModelObject()[i]["isYes"] !=1 && questionnaireWizard.questionsGrid.getGridModelObject()[i]["isYes"] !=2){
				numberOfNonAnsweredQuestions++;
			}
		}else{
			numberOfNonAnsweredQuestions++;
		}
	}
	
	if (numberOfNonAnsweredQuestions > 0){
		document.getElementById("modalText").innerHTML = "Πρέπει να απαντήσετε σε όλες τις ερωτήσεις για να προχωρίσετε στο επόμενο βήμα.";
		if (numberOfNonAnsweredQuestions==1){
			document.getElementById("modalHead").innerHTML = "Έχετε ακόμη 1 αναπάντητη ερώτηση";
		}else{
			document.getElementById("modalHead").innerHTML = "Έχετε ακόμη " + numberOfNonAnsweredQuestions + " αναπάντητες ερωτήσεις";
		}
				
		//alert("Πρέπει να απαντήσετε σε όλες τις ερωτήσεις για να προχωρίσετε στο επόμενο βήμα. \n Έχετε ακόμη " + numberOfNonAnsweredQuestions + " αναπάντητες ερωτήσεις");
		$('#myModal').modal('show');
		return false;
	}
	
	questionnaireWizard.saveAnswers();
	
	window.location = window.location;
	
	//alert("QQQQ");
	
	//questionnaireWizard.init();
	
	//questionnaireWizard.showNextAnswers();
	
	return true;
	
	//window.location = window.location;
	
	//alert(JSON.stringify(questionsGrid.getGridModelObject()));
	//alert(JSON.stringify(questionnaireWizard.getAnswers()));
	
	//questionnaireWizard.loadData();
	
	//return true;
	
	//var randomName = "answer_" + parseInt(Math.random()*10000000);
	//alert(randomName);
}

// -------------------------------------------------------------------------------

function showHelp(){
	$('#helpModal').modal('show');
	return false;
}