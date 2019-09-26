let validator = null;

/*
----------------
-- Components --
----------------
*/

let QuestionDetailsComponent = Vue.component('question-details', {
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
	   onViewDetails: function (){
		   viewParameterApp.editParameter(this.data);
       }
   },
   template: '#question-details-template'
});

let QuestionQuestionnairesComponent = Vue.component('question-questionnaire', {
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
	   onEdit: function (){
		   editQuestionQuestionnairesApp.showEntries(this.data);
       }
   },
   template: '#question-questionnaire-template'
});

let QuestionSameQuestionsComponent = Vue.component('question-same-questions', {
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
	   onEdit: function (){
		   editQuestionSameQuestionsApp.showEntries(this.data);
       }
   },
   template: '#question-same-questions-template'
});


let QuestionFacultiesComponent = Vue.component('question-faculties', {
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
	   onEdit: function (){
		   editQuestionFacultiesApp.showEntries(this.data);
       }
   },
   template: '#question-faculties-template'
});

// ------------------------------------------------------------------------------------------------

/*
--------------
-- Dialogs --
--------------
*/


/*
Edit question
*/
let EditQuestionDialog = Vue.component('edit-question-dialog', {
	props :{
		question: {
            type: Object,
            required: false,
        },
        hollands: {
            type: Object,
            required: false,
        },
        scientificfields: {
            type: Object,
            required: false,
        }
	},	
	template: '#edit-question-dialog-template',
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
		
		const component = this;
		
		validator.addField({
			element: this.$refs.category,
			customValidation : function (){
				return !(component.question.category == null);
			},
            errorMessage: {
            	custom: "Το πεδίο είναι υποχρεωτικό",
            	
            },
            errorElement:this.$refs.categoryError
		});

		validator.addField({
			element: this.$refs.scientificField1,
			customValidation : function (){
				return !(component.question.scientificField1 == null);
			},
            errorMessage: {
            	custom: "Το πεδίο είναι υποχρεωτικό",
            	
            },
            errorElement:this.$refs.scientificField1Error
		});
		
		
	}
});

/*
View question
*/
let ViewQuestionDialog = Vue.component('view-question-dialog', {
	props :{
		question: {
            type: Object,
            required: false,
        }
	},	
	template: '#view-question-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	}
});

/*
Select category
*/

let selectQuestionCategoryDialog = Vue.component('select-question-category-dialog', {
	props :{
		categories: {
            type: Object,
            required: false
        },
        fieldsset: {
            type: Object,
            required: false
        },
        pg: {
            type: Object,
            required: false
        },        
        saveaction: {
            type: Function,
            required: false
        },
        cancelaction: {
            type: Function,
            required: false
        },
        selectentry: {
            type: Function,
            required: false
        }
	},	
	template: '#select-question-category-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});

//------------------------------------------------------------------------------------------------

/*
---------------
-- Main Data --
---------------
*/

let parameterApp = new ParameterAppClass({
	propsData: {
		questionMessage: {
			title: "Διαγραφή Ερώτησης",
			 text: "Επιβεβαίωση διαγραφής "
		},
		deletionData() {
			return this.currentParameter?this.currentParameter.description:"";
		},
		defaultTemplateObject : defaultValue,
		dataset : new Promise((resolve, reject) => {
					setTimeout(() => resolve (
							axios.get(contextPath + 'data/questions').then((response) => {
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
	  			   		 axios.post(contextPath + 'data/question/save', data,
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
   			   		 	axios.post(contextPath + 'data/question/create').then((response) => {
   			   		 		parameterApp.currentParameter = response.data;
   			   		 	})
       			), 125);
    		});
		},
		deleteParameterAction (data) {
			console.info ("Delete: " + JSON.stringify(this.currentParameter));
			
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/question/delete', data).then((response) => {
    					})
       			), 125);
    		});
		},
		loadParameterAction (data) {
			console.info("Load Parameter Action implementation. Data= " + JSON.stringify(data));
			
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/question/get', data).then((response) => {
    						parameterApp.currentParameter = response.data;
    					})
       			), 125);
    		});
			
		}
	}
});

// ------------------------------------------------------------------------------------------------ 

let viewParameterApp = new ParameterAppClass({
	propsData: {
		/*
		questionMessage: {
			title: "Διαγραφή Ερώτησης",
			 text: "Επιβεβαίωση διαγραφής "
		},
		deletionData() {
			return "";
		},*/		
		defaultTemplateObject : defaultValue,
		loadParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/question/get', data).then((response) => {
    						parameterApp.currentParameter = response.data;
    					})
       			), 125);
    		});
		}		
	}
});

//-------------------------------------------------------------------------------------------------

/*
----------------
-- Categories --
----------------
*/

let editQuestionCategoryApp = new SelectEntryClass({
	propsData: {
		loadEntriesUri: contextPath + 'data/questions/categories/eligible',
		   setEntryUri: contextPath + 'data/question/set/category',
		   removeEntryUri: contextPath + 'data/question/remove/category',
		   setEntity(data) {
			   parameterApp.currentParameter = data;
			   console.info("Set[1]");
		   }
	},
	data () {
		return {
	        fieldsset: [
	        	{property:"description", title:"Περιγραφή", sortable:true}
	        ]
		}
	},
	methods: {

	}
});

let editQuestionCategory2App = new SelectEntryClass({
	propsData: {
		loadEntriesUri: contextPath + 'data/questions/categories/eligible',
		   setEntryUri: contextPath + 'data/question/set/category2',
		   removeEntryUri: contextPath + 'data/question/remove/category2',
		   setEntity(data) {
			   parameterApp.currentParameter = data;
			   console.info("Set[2]");
		   }
	},
	data () {
		return {
			fieldsset: [
	        	{property:"description", title:"Περιγραφή", sortable:true}
	        ]
		}
	},
	methods: {
	
	}
});

let editQuestionSubCategoryApp = new SelectEntryClass({
	propsData: {
		loadEntriesUri: contextPath + 'data/questions/categories/eligible',
		   setEntryUri: contextPath + 'data/question/set/subCategory',
		   removeEntryUri: contextPath + 'data/question/remove/subCategory',
		   setEntity(data) {
			   parameterApp.currentParameter = data;
			   console.info("Set[3]");
		   }
	},
	data () {
		return {
			fieldsset: [
	        	{property:"description", title:"Περιγραφή", sortable:true}
	        ]
		}
	}
});

//-------------------------------------------------------------------------------------------------

/*
--------------------
-- Questionnaires --
--------------------
*/

let EditQuestionQuestionnairesDialog = Vue.component('edit-question-questionnaires-dialog', {
	props :{
		questionnaires: {
            type: Object,
            required: false
        }
	},	
	template: '#edit-question-questionnaires-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});


let DeleteQuestionQuestionnaireComponent = Vue.component('delete-question-questionnaire', {
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
		   editQuestionQuestionnairesApp.deleteEntry(this.data);
       }
   },
   template: '#delete-question-questionnaire-template'
})

let SelectQuestionEligibleQuestionnaireComponent = Vue.component('select-question-eligible-questionnaire', {
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
   template: '#select-question-eligible-questionnaire-template'
})

let selectQuestionEligibleQuestionnairesDialog = Vue.component('select-question-eligible-questionnaires-dialog', {
	props :{
		questionnaires: {
            type: Object,
            required: false
        }
	},	
	template: '#select-question-eligible-questionnaires-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});


let editQuestionQuestionnairesApp = new EditEligibleEntriesClass({
	propsData: {
		loadEntriesUri: contextPath + 'data/question/questionnaires',
		showEiligbleEntriesUri: contextPath + 'data/question/questionnaires/eligible',
		updateEntriesUri: contextPath + 'data/question/questionnaires/update',
		addEntriesUri: contextPath + 'data/question/questionnaires/temp/add',
		deleteEntryUri: contextPath + 'data/question/questionnaires/temp/delete'
	},
	data () {
		return {
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:false},
				{property:"delete", title:"Delete", sortable:false}
            ],
	        eligibleFieldsset: [
	        	{property:"questionnaire.description", title:"Περιγραφή", sortable:false},
	        	{property:"selected", title:"Επιλογή", sortable:false}
	        ],
	        gridcomponents: [
	            {
	                id: "delete",
	                value: function (value){ 
	                    return "Delete"
	                },
	                component: function (row){
	                    return DeleteQuestionQuestionnaireComponent;
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
	                    return SelectQuestionEligibleQuestionnaireComponent;
	                }
	            }
	        ]		
		}
	}	
});

//-------------------------------------------------------------------------------------------------

/*
--------------------
-- Same Questions --
--------------------
*/

let EditQuestionSameQuestionsDialog = Vue.component('edit-question-same-questions-dialog', {
	props :{
		questions: {
            type: Object,
            required: false
        }
	},	
	template: '#edit-question-same-questions-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});

let DeleteQuestionSameQuestionsComponent = Vue.component('delete-question-same-question', {
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
		   editQuestionSameQuestionsApp.deleteEntry(this.data);
       }
   },
   template: '#delete-question-same-question-template'
})

let selectQuestionEligibleSameQuestionsDialog = Vue.component('select-question-eligible-same-questions-dialog', {
	props :{
		questions: {
            type: Object,
            required: false
        }
	},	
	template: '#select-question-eligible-same-questions-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});

let SelectQuestionEligibleSameQuestionsComponent = Vue.component('select-question-eligible-same-question', {
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
   template: '#select-question-eligible-same-question-template'
})

let editQuestionSameQuestionsApp = new EditEligibleEntriesClass({
	propsData: {
		loadEntriesUri: contextPath + 'data/question/sameQuestions',
		showEiligbleEntriesUri: contextPath + 'data/question/sameQuestions/eligible',
		updateEntriesUri: contextPath + 'data/question/sameQuestions/update',
		addEntriesUri: contextPath + 'data/question/sameQuestions/temp/add',
		deleteEntryUri: contextPath + 'data/question/sameQuestions/temp/delete'
	},
	data () {
		return {
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:false},
				{property:"delete", title:"Delete", sortable:false}
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
	                    return DeleteQuestionSameQuestionsComponent;
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
	                    return SelectQuestionEligibleQuestionnaireComponent;
	                }
	            }
	        ]		
		}
	}	
});

//-------------------------------------------------------------------------------------------------

/*
---------------
-- Faculties --
---------------
*/

// TODO: Edit this
let EditQuestionFacultiesDialog = Vue.component('edit-question-faculties-dialog', {
	props :{
		faculties: {
            type: Object,
            required: false
        }
	},	
	template: '#edit-question-faculties-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});

let DeleteQuestionFacultyComponent = Vue.component('delete-question-faculty', {
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
		   editQuestionFacultiesApp.deleteEntry(this.data);
       }
   },
   template: '#delete-question-faculty-template'
})

let selectQuestionEligibleFacultiesDialog = Vue.component('select-question-eligible-faculties-dialog', {
	props :{
		faculties: {
            type: Object,
            required: false
        }
	},	
	template: '#select-question-eligible-faculties-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});

let SelectQuestionEligibleFacultyComponent = Vue.component('select-question-eligible-faculty', {
    props: {
        value: {
           type: Boolean,
           required : false
       },
       data: {
           type: Object,
           required: false
       }
    }
   ,
   template: '#select-question-eligible-faculty-template'
})

let editQuestionFacultiesApp = new EditEligibleEntriesClass({
	propsData: {
		loadEntriesUri: contextPath + 'data/question/faculties',
		showEiligbleEntriesUri: contextPath + 'data/question/faculties/eligible',
		updateEntriesUri: contextPath + 'data/question/faculties/update',
		addEntriesUri: contextPath + 'data/question/faculties/temp/add',
		deleteEntryUri: contextPath + 'data/question/faculties/temp/delete'
	},
	data () {
		return {
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:false},
				{property:"delete", title:"Delete", sortable:false}
            ],
	        eligibleFieldsset: [
	        	{property:"faculty.description", title:"Περιγραφή", sortable:false},
	        	{property:"selected", title:"Επιλογή", sortable:false}
	        ],
	        gridcomponents: [
	            {
	                id: "delete",
	                value: function (value){ 
	                    return "Delete"
	                },
	                component: function (row){
	                    return DeleteQuestionFacultyComponent;
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
	                    return SelectQuestionEligibleFacultyComponent;
	                }
	            }
	        ]		
		}
	}	
});

//-------------------------------------------------------------------------------------------------


/*
-----------------
-- Application --
-----------------
*/

let app = new Vue ({
	el: "#app-content",
	data () {
		return {
			hollands: this.loadHollands(),
			scientificFields: this.loadScientificFields(),
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:true}, //TODO: i18n!
				//{property:"number", title:"Αριθμός", sortable:false},
				{property:"category.description", title:"Κατηγορία", sortable:false},
				{property:"scientificField1.description", title:"Επιστ. Πεδίο", sortable:false},
//				{property:"holland1.value", title:"Holland (1)", sortable:false},
//				{property:"holland2", title:"Holland (2)", sortable:false},
//				{property:"holland3", title:"Holland (3)", sortable:false},
//				{property:"holland4", title:"Holland (4)", sortable:false},
				{property:"questionnaires", title:"Ερωτημ/λγια", sortable:false},
				{property:"sameQuestions", title:"Ίδιες Ερωτήσεις", sortable:false},
//				{property:"faculties", title:"Faculties", sortable:false},
				{property:"view", title:"Προβολή Όλων", sortable:false},
				{property:"edit", title:"Edit", sortable:false},
				{property:"delete", title:"Delete", sortable:false}
            ],
            
            gridcomponents: [
                {
                    id: "sameQuestions",
                    value: function (value){ 
                        return "SameQuestions"
                    },
                    component: function (row){
                        return QuestionSameQuestionsComponent;
                    }
                },
                {
                    id: "questionnaires",
                    value: function (value){ 
                        return "Questionnaires"
                    },
                    component: function (row){
                        return QuestionQuestionnairesComponent;
                    }
                },
                {
                    id: "faculties",
                    value: function (value){ 
                        return "Faculties"
                    },
                    component: function (row){
                        return QuestionFacultiesComponent;
                    }
                },                
                {
                    id: "view",
                    value: function (value){ 
                        return "View"
                    },
                    component: function (row){
                        return QuestionDetailsComponent;
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
		loadHollands() {
    		return new Promise((resolve, reject) => {
				setTimeout(() => resolve (
					axios.get(contextPath + 'data/hollands').then((response) => {
						this.hollands = response.data;
					})
				), 125)
			})
		},
		loadScientificFields () {
			return new Promise((resolve, reject) => {
				setTimeout(() => resolve (
						axios.get(contextPath + 'data/scientificFields').then((response) => {
							this.scientificFields = response.data;
						})
				), 125)
			})
		}
	}
});
