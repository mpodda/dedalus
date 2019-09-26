function QuestionAnswer(id, questionId, description, isYes, pupilId, questionnaireId){
	this.id = id;
	this.questionId = questionId; 
	this.description = description;
	this.isYes = isYes;
	this.pupilId = pupilId;
	this.questionnaireId = questionnaireId;
	
	return this;
}

QuestionAnswer.prototype.setIsYes = function(){
	var radioInput = document.createElement("input");
	radioInput.type = "radio";
	radioInput.name = "isYes" + "_" + this.questionId;
	radioInput.value = "true";
	
	radioInput.checked = this.isYes;
	
	return radioInput;
};


QuestionAnswer.prototype.setIsNo = function(){
	var radioInput = document.createElement("input");
	radioInput.type = "radio";
	radioInput.name = "isYes"  + "_" + this.questionId;
	radioInput.value = "false";
	
	radioInput.checked = !this.isYes;
	
	return radioInput;
};

// --------------------------------------------------------------------------------------

function WizardStep(stepNo){
	this.stepNo = stepNo;
	
	return this;
}

WizardStep.prototype.link = function(){
	return "ililililil";
};

WizardStep.prototype.number = function(){
	return this.stepNo;
};

WizardStep.prototype.description = function(){
	return "Βήμα " + this.stepNo;
};
