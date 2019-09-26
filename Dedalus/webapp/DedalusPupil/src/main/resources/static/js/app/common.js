const pupilNameUri =  contextPath + 'data/pupilName';
const reliabilityUri = contextPath + 'data/reliability';
const completionUri =  contextPath + 'data/completion';
const questionnaireUri =  contextPath + 'data/questionnaire';

async function loadPupilName () {
	return new Promise((resolve, reject) => {
		Connection.get(pupilNameUri, false).then((response) => {
			resolve(response.data);
		});
	});
}

async function loadQuestionnaireName () {
	return new Promise((resolve, reject) => {
		Connection.get(questionnaireUri, false).then((response) => {
			resolve(response.data);
		});
	});
}

async function loadReliability () {
	return new Promise((resolve, reject) => {
		Connection.get(reliabilityUri, false).then((response) => {
			resolve(response.data);
		});
	});
}

async function loadCompletion () {
	return new Promise((resolve, reject) => {
		Connection.get(completionUri, false).then((response) => {
			resolve(response.data);
		});
	});
}

async function renderMisc (pupilName, questionnaireName, reliability, completion) {
	let ok  = false;
	
	return new Promise((resolve, reject) => {
		document.getElementById("pupilName").innerHTML = pupilName;
		document.getElementById("reliability").innerHTML = reliability + "%";
		document.getElementById("completion").innerHTML = completion + "%";
		document.getElementById("questionnaire").innerHTML = questionnaireName;
		
		ok = true;
	
		resolve(ok);
	});
}

async function initCommon() {
	/* Misc (Pupil Name, Questionnaire, Reliability, Completion) */
	let pupilName = await loadPupilName();
	let questionnaireName = await loadQuestionnaireName();
	let reliability = await loadReliability();
	let completion = await loadCompletion();
	
	let ok = await renderMisc(pupilName, questionnaireName, reliability, completion);

}

(async () => {
    await initCommon();
})();
