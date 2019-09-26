let validator = null;

let QuestionnaireQuestions = Vue.component('questionnaire-questions', {
     props: {
         value: {
            type: String,
            required : false
        },
        data: {
            type: Object,
            required: false
        },
        onEditEvent: {
        	type: Function,
        	required: false
        }
     },
    methods: {
    	onEdit: function (){
    		editQuestionnairesQuestionsApp.showEntries(this.data);
        }
    },
    template: '#questionnaire-questions-template'
})

// ------------------------------------------------------------------------------------------------

let EditQuestionnaireDialog = Vue.component('edit-questionnaire-dialog', {
	props :{
		questionnaire: {
            type: Object,
            required: false,
        }
	},	
	template: '#edit-questionnaire-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	},
	mounted() {
		validator = new Validator();
		
		validator.addField({element: this.$refs.description, 
            mandatory:true,
            errorMessage: {
            	mandatory: "Το πεδίο είναι υποχρεωτικό"
            },
            errorElement:this.$refs.descriptionError
		});
	}
});

// ------------------------------------------------------------------------------------------------

let EditQuestionnaireQuestionsDialog = Vue.component('edit-questionnaire-questions-dialog', {
	props :{
		questions: {
            type: Object,
            required: false
        }
	},	
	template: '#edit-questionnaire-questions-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});

//------------------------------------------------------------------------------------------------

let selectQuestionnaireEligibleQuestionsDialog = Vue.component('select-questionnaire-eligible-questions-dialog', {
	props :{
		questions: {
            type: Object,
            required: false
        }
	},	
	template: '#select-questionnaire-eligible-questions-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});

//------------------------------------------------------------------------------------------------

let selectQuestionnaireEligibleQuestionComponent = Vue.component('select-questionnaire-eligible-question', {
    props: {
        value: {
           type: Boolean,
           required : false
       },
       data: {
           type: Object,
           required: false
       }
    },
   methods: {
	   
   }
   ,
   template: '#select-questionnaire-eligible-question-template'
})

//------------------------------------------------------------------------------------------------

let DeleteQuestionnaireQuestionComponent = Vue.component('delete-questionnaire-question', {
    props: {
        value: {
           type: String,
           required : false
       },         
       data: {
           type: Object,
           required: false
       }
    },
   methods: {
	   onDeleteEntry: function (){
		   editQuestionnairesQuestionsApp.deleteEntry(this.data);
       }
   },
   template: '#delete-questionnaire-question-template'
})

//------------------------------------------------------------------------------------------------

let editQuestionnairesQuestionsApp = new EditEligibleEntriesClass ({
	propsData: {
		loadEntriesUri: contextPath + 'data/questionnaire/questions',
		showEiligbleEntriesUri: contextPath + 'data/questionnaire/questions/eligible',
		updateEntriesUri: contextPath + 'data/questionnaire/questions/update',
		addEntriesUri: contextPath + 'data/questionnaire/questions/temp/add',
		deleteEntryUri: contextPath + 'data/questionnaire/questions/temp/delete'
	},
	data () {
		return {
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:false},
				{property:"delete", title:"Διαγραφή", sortable:false}
            ],
	        eligibleFieldsset: [
	        	{property:"question.description", title:"Περιγραφή", sortable:false},
	        	{property:"selected", title:"Επιλογή", sortable:false}
	        ],
	        gridcomponents: [
	            {
	                id: "delete",
	                value: function (value){ 
	                    return "Delete"
	                },
	                component: function (row){
	                    return DeleteQuestionnaireQuestionComponent;
	                }
	            }            	
	        ],
	        eligibleGridcomponents: [
	            {
	                id: "selected",
	                value: function (value){
	                    return value
	                },
	                component: function (row){
	                    return selectQuestionnaireEligibleQuestionComponent;
	                }
	            }
	        ]		
		}
	}
});

// ------------------------------------------------------------------------------------------------

let parameterApp = new ParameterAppClass({
	propsData: {
		questionMessage: {
			title: "Διαγραφή Ερωτηματολογίου",
			 text: "Επιβεβαίωση διαγραφής "
		},
		deletionData() {
			return this.currentParameter?this.currentParameter.description:"";
		},
		defaultTemplateObject : defaultValue,
		dataset : new Promise((resolve, reject) => {
					setTimeout(() => resolve (
							axios.get(contextPath + 'data/questionnaires').then((response) => {
								parameterApp.setDataSet(response.data);
							})
						), 125
					);
   				})
		,
   		saveAction (data) {
			return new Promise((resolve, reject) => {
				validator.validate();
				
				if (!validator.valid) {
					return reject("error");
				}
				
				setTimeout(() => resolve (
	  			   		 axios.post(contextPath + 'data/questionnaire/save', data,
	  			   			{
								headers: {
									'Content-Type': 'application/json; charset=utf-8'
								}
	    					}).then((response) => {
	    						
	    						console.info ("Saved: " + JSON.stringify(response.data));
	    						
	  			   			 parameterApp.currentParameter = response.data;
	  				 	 })
	      		), 125);
           })
		},
		createParameterAction() {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
   			   		 	axios.post(contextPath + 'data/questionnaire/create').then((response) => {
   			   		 		parameterApp.currentParameter = response.data;
   			   		 	})
       			), 125);
    		});
		},
		deleteParameterAction (data) {
			console.info ("Delete: " + JSON.stringify(this.currentParameter));
			
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/questionnaire/delete', data).then((response) => {
    						
    					})
       			), 125);
    		});
		},
		loadParameterAction (data) {
			console.info("Load Parameter Action implementation. Data= " + JSON.stringify(data));
			
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/questionnaire/get', data).then((response) => {
    						parameterApp.currentParameter = response.data;
    					})
       			), 125);
    		});
			
		}
	}
});

//------------------------------------------------------------------------------------------------

let app = new Vue ({
	el: "#app-content",
	data () {
		return {
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:true}, //TODO: i18n!
				{property:"questions", title:"Ερωτήσεις", sortable:false},
				{property:"edit", title:"Επεξεργασία", sortable:false},
				{property:"delete", title:"Διαγραφή", sortable:false}
            ],
            
            gridcomponents: [
                {
                    id: "questions",
                    value: function (value){ 
                        return "Questions"
                    },
                    component: function (row){
                        return QuestionnaireQuestions;
                    }
                },            	
            	{
                    id: "edit",
                    value: function (value){ 
                    	return "Edit"
                    },
                    component: function (row){
                        return EditParameterComponent;
                    }
                },
                {
                    id: "delete",
                    value: function (value){ 
                        return "Delete"
                    },
                    component: function (row){
                        return DeleteParameterComponent;
                    }
                }
            ]
		}
	},
	methods: {
		
	}
});