let validator = null;
let searchTokensValidator = null;
let assignTokensValidator = null;

/*
----------------
-- Components --
----------------
*/


const TokenSelectionState = Object.freeze({
    all_selected : Symbol('all_selected'),
    all_not_selected: Symbol('all_not_selected'),
    payed_selected: Symbol('payed_selected'),
    not_payed_selected: Symbol('not_payed_selected')
});
            
let SearchTokensGrid = Vue.component('search-tokens-grid', {
	props :{
		defaulttemplate: {
	           type: Object,
	           required : false
		},
		pagingenabled: {
	           type: Object,
	           required : false
		},
		pg: {
	           type: Object,
	           required : false
		},
		sorting: {
	           type: Object,
	           required : false
		},
		fieldsset: {
	           type: Object,
	           required : false
		},
		dataset: {
	           type: Object,
	           required : false
		},
		gridcomponents: {
	           type: Object,
	           required : false
		},
		renderers: {
	           type: Object,
	           required : false
		}
	},	
	methods: {
		selectAllTokens(){
			if (this.tokenSelectionState != TokenSelectionState.all_selected) {
				this.tokenSelectionState = TokenSelectionState.all_selected;
			} else {
				this.tokenSelectionState = TokenSelectionState.all_not_selected;
			}
		},
		selectPayedTokens(){
			if (this.tokenSelectionState != TokenSelectionState.payed_selected){
				this.tokenSelectionState = TokenSelectionState.payed_selected;
			} else {
				this.tokenSelectionState = TokenSelectionState.all_not_selected;
			}
		},
		selectNotPayedTokens(){
			if (this.tokenSelectionState != TokenSelectionState.not_payed_selected){
				this.tokenSelectionState = TokenSelectionState.not_payed_selected;
			} else {
				this.tokenSelectionState = TokenSelectionState.all_not_selected;
			}
		},
		markAsPayed() {
			new Promise((resolve, reject) => {
				setTimeout(() => resolve (
	  			   		 axios.post(contextPath + "data/tc/token/manage/markaspayed", this.dataset).then((response) => {
	  			   			 editTokensApp.tokens = response.data;
	  				 	 })
	      		), 125);
           })
		},
		markAsNotPayed(){
			new Promise((resolve, reject) => {
				setTimeout(() => resolve (
	  			   		 axios.post(contextPath + "data/tc/token/manage/markasnotpayed", this.dataset).then((response) => {
	  			   			 editTokensApp.tokens = response.data;
	  				 	 })
	      		), 125);
           })			
		},
		selectTokens(selectionUri) {
			new Promise((resolve, reject) => {
				setTimeout(() => resolve (
	  			   		 axios.post(contextPath + selectionUri, this.dataset).then((response) => {
	  			   			 editTokensApp.tokens = response.data;
	  				 	 })
	      		), 125);
           })			
		}
	},
	watch: {
		tokenSelectionState() {
			
			console.info("tokenSelectionState= " + String(this.tokenSelectionState));
			
			let selectionUrl = "";
			
			switch (this.tokenSelectionState) {
				case TokenSelectionState.all_selected:
					selectionUrl = "data/tc/token/manage/select";
				break;

				case TokenSelectionState.all_not_selected:
					selectionUrl = "data/tc/token/manage/deselect";
				break;

				case TokenSelectionState.payed_selected:
					selectionUrl = "data/tc/token/manage/selectpayed";
				break;

				case TokenSelectionState.not_payed_selected:
					selectionUrl = "data/tc/token/manage/selectnotpayed";
				break;
				
				default:
				break;
			}
			
			this.selectTokens(selectionUrl);
		}
	},	
	template: '#search-tokens-grid-template',
	components: {
		searchTokensCommands : grid
	},
	data() {
		return {
			tokenSelectionState: TokenSelectionState.all_not_selected,
			dataset: null
		}
	}	
});


/*
 * Active tuition centre renderer
 * 
 * */
let activeTuitionCentreComponent = Vue.component('active-tuition-centre', {
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
   template: '#active-tuition-centre-template'
})


/*
 * Not Active tuition centre renderer
 * 
 * */

let notActiveTuitionCentreComponent = Vue.component('not-active-tuition-centre', {
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
   template: '#not-active-tuition-centre-template'
})



/*
 * Payed Token renderer
 * 
 * */

let payedTokenComponent = Vue.component('payed-token', {
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
   template: '#payed-token-template'
})


/*
 * Not payed Token renderer
 * 
 * */

let notPayedTokenComponent = Vue.component('not-payed-token', {
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
   template: '#not-payed-token-template'
})

/*
--------------
-- Dialogs --
--------------
*/


/*
Edit Tuition Centre
*/

let EditTuitionCentreDialog = Vue.component('edit-tuition-centre-dialog', {
	props :{
		tc: {
            type: Object,
            required: false,
        }
	},	
	template: '#edit-tuition-centre-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	},
	computed : {
		isEditMode () {
			return this.tc["id"] != null;
		}
	},
	mounted() {
		validator = new Validator();
		
		validator.addField({
			element: this.$refs.description, 
            mandatory:true,
            errorMessage: {
            	mandatory: "Το πεδίο είναι υποχρεωτικό"
            },
            errorElement:this.$refs.descriptionError
		});
		
		validator.addField({
			element: this.$refs.address, 
            mandatory:true,
            errorMessage: {
            	mandatory: "Το πεδίο είναι υποχρεωτικό"
            },
            errorElement:this.$refs.addressError
		});
		
		if (!this.isEditMode){
			const component = this;
			
			validator.addField({
				element: this.$refs.userName, 
	            mandatory:true,
	            customValidation : function (){
	            	const userName = this.element.value;
	            	
	        		return new Promise((resolve, reject) => {
	        			
	        			setTimeout(() => resolve (
	        					axios.get(contextPath + 'data/tc/userExists/' + userName).then((response) => {
	        						return !response.data;
	        					})
	           			), 125);
	        			
	        			setTimeout(() => reject (
	        				() => {
	        					return false;	
	        				}
	        			), 125);
	        		});
	            },
	            errorMessage: {
	            	mandatory: "Το πεδίο είναι υποχρεωτικό",
	            	   custom: "Αυτό το Όνομα Χρήστη υπάρχει ήδη"
	            },
	            errorElement:this.$refs.userNameError
			});
		}
		
		if (this.isEditMode) {
			validator.addField({
				element: this.$refs.password, 
	            mandatory:true,
	            errorMessage: {
	            	mandatory: "Το πεδίο είναι υποχρεωτικό"
	            },
	            errorElement:this.$refs.passwordError
			});
		}
		
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
			title: "Διαγραφή Φροντιστηρίου",
			 text: "Επιβεβαίωση διαγραφής "
		},
		deletionData() {
			return this.currentParameter?this.currentParameter.description:"";
		},
		defaultTemplateObject : defaultValue,
		dataset : new Promise((resolve, reject) => {
					setTimeout(() => resolve (
							axios.get(contextPath + 'data/tcs').then((response) => {
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
	  			   		 axios.post(contextPath + 'data/tc/save', data,
	  			   			{
								headers: {
									'Content-Type': 'application/json; charset=utf-8'
								}
	    					}).then((response) => {
	  			   			 parameterApp.currentParameter = response.data;
	  				 	 })
	      		), 125);
           })
		},
		createParameterAction() {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
   			   		 	axios.post(contextPath + 'data/tc/create').then((response) => {
   			   		 		parameterApp.currentParameter = response.data;
   			   		 	})
       			), 125);
    		});
		},
		deleteParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/tc/delete', data).then((response) => {
    					})
       			), 125);
    		});
		},
		loadParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/tc/get', data).then((response) => {
    						parameterApp.currentParameter = response.data;
    					})
       			), 125);
    		});
			
		}
	}
});

//-------------------------------------------------------------------------------------------------


/*
------------
-- Tokens --
------------
*/


/*
 * Manage tokens button component
 */
let ManageTokenComponent = Vue.component('manage-token', {
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
	   onAddToken: function (){
		   tcTokenBatchApp.editParameter(this.data);
       },
       onAEditToken: function (){
    	   //editTokensApp.createCriteria(this.data);
    	   //editTokensApp.tokens = null;
    	   editTokensApp.clearResults();
    	   editTokensApp.editParameter(this.data);
    	   
       }
   },
   template: '#manage-token-template'
});


/*
 * Add token batch dialog 
 * */
let AddTuitionCentreTokensDialog = Vue.component('add-tuition-centre-tokens-dialog', {
	props :{
		batch: {
            type: Object,
            required: false,
        }
	},	
	template: '#add-tuition-centre-tokens-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	},
   mounted() {
	   assignTokensValidator = new Validator();
	   
	   assignTokensValidator.addField({
		   	element: this.$refs.batchAmount, 
            mandatory:true,
            numeric:true,
            errorMessage: {
            	mandatory: "Το πεδίο είναι υποχρεωτικό",
            	numeric: "Η τιμή του πεδίου πρέπει να είναι αριθμός"
            },
            errorElement:this.$refs.batchAmountError
		});
	   
		const component = this;
		
		assignTokensValidator.addField({
			element: this.$refs.batchQuestionnaire,
			customValidation : function (){
				return !(component.batch.questionnaire == null);
			},
            errorMessage: {
            	custom: "Το πεδίο είναι υποχρεωτικό",
            	
            },
            errorElement:this.$refs.batchQuestionnaireError
		});
	   
   }
	
});


/*
 * Add token batch dialog actions 
 * */
let tcTokenBatchApp = new ParameterAppClass({
	propsData: {
		defaultTemplateObject : defaultValue,
   		saveAction (data) {
			return new Promise((resolve, reject) => {
				assignTokensValidator.validate();
				
				if (!assignTokensValidator.valid) {
					return reject("error");
				}
				
				setTimeout(() => resolve (
	  			   		 axios.post(contextPath + 'data/tc/saveTokenBatch', data,
	  			   			{
								headers: {
									'Content-Type': 'application/json; charset=utf-8'
								}
	    					}).then((response) => {
	    						tcTokenBatchApp.currentParameter = response.data;
	  				 	 })
	      		), 125);
           })
		},
		loadParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
   			   		 	axios.post(contextPath + 'data/tc/createTokenBatch', data).then((response) => {
   			   		 		console.info("loadParameterAction:" + JSON.stringify(response.data));
   			   		 	tcTokenBatchApp.currentParameter = response.data;
   			   		 	})
       			), 125);
    		});
		}		
	}
});

/*
 * Select questionnaire for Token Batch
 * */
let editTokenBatchQuestionnaireApp = new SelectEntryClass({
	propsData: {
		loadEntriesUri: contextPath + 'data/tc/tokenBatch/questionnaires/eligible',
		   setEntryUri: contextPath + 'data/tc/tokenBatch/set/questionnaire',
		   removeEntryUri: contextPath + 'data/tc/tokenBatch/remove/questionnaire',
		   setEntity(data) {
			   tcTokenBatchApp.currentParameter = data;
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

/*
Select questionnaire dialog component
*/

let selectTokenQuestionnaireDialog = Vue.component('select-token-questionnaire-dialog', {
	props :{
		questionnaires: {
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
	template: '#select-token-questionnaire-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});


/*
 * Edit token dialog 
 * */
let EditTuitionCentreTokensDialog = Vue.component('edit-tuition-centre-tokens-dialog', {
	props :{
		tc: {
            type: Object,
            required: false,
        },
		criteria: {
            type: Object,
            required: false,
        }        
	},	
	template: '#edit-tuition-centre-tokens-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	},
	mounted() {
		searchTokensValidator = new Validator();
		
		const component = this;
		
		searchTokensValidator.addField({
			customValidation : function (){
				const momentCreationDateFrom = moment(component.criteria["creationDateFromAsString"], "DD/MM/YYYY");
				const momentCreationDateTo = moment(component.criteria["creationDateToAsString"], "DD/MM/YYYY");
				return momentCreationDateFrom.diff(momentCreationDateTo) <= 0;
			},
			errorMessage: {
				custom: "Η 'Ημ/νία από' είναι μεταγενέστερη από την 'Ημ/νία έως'",
			},
			errorElement:this.$refs.dateIntervalError
		});
	}
});


/* 
 *	Select Token renderer component
 */


let SelectTokenRendererComponent = Vue.component('select-token', {
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
   template: '#select-token-template'
})


/* 
 *	Edit Token dialog actions
 */

let editTokensApp = new ParameterAppClass({
	propsData: {
		defaultTemplateObject : defaultValue,
		loadParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/tc/token/criteria/create', data).then((response) => {
    						editTokensApp.currentParameter = response.data;
    					})
       			), 125);
    		});
		}
	},
	data () {
		return {
			tokens : null,
	        fieldsset: [
	        	{property:"token.number", title:"Α/Α", sortable:true},
	        	{property:"token.value", title:"Κωδικός", sortable:false},
	        	{property:"token.batch", title:"Δέσμη", sortable:true},
	        	{property:"token.creationDate", title:"Ημ/νία Δημ.", sortable:true},
//	        	{property:"token.payed", title:"Πληρωμένα", sortable:true},
//	        	{property:"selected", title:"Επιλογή", sortable:false}
	        ],
	        renderers: [
            	{
					id: "token.payed",
	                value: function (value){
	                    switch (value){
	                        case true : return "Yes";
	                        case false : return "No";
	                       default : return "Unknown Value";
	                    }
	                },
	                component:
	                   function (value){
	                        switch (value){
	                            case true : return payedTokenComponent;
	                            case false : return notPayedTokenComponent;
	                           default : return parameterApp.defaultTemplateObject;
	                        }
	                }            		
            	},
            	{
					id: "token.creationDate",
	                value: function (v){
	                	return moment(v).format('DD/MM/YYYY');
	                },
	                component:
	                   function (value){
	                	return parameterApp.defaultTemplateObject;
	                }            		
            	}
	        ],
	        gridcomponents: [
	            {
	                id: "selected",
	                value: function (value){
	                    return value
	                },
	                component: function (row){
	                    return SelectTokenRendererComponent;
	                }
	            }
	        ],	        
            paging: new Paginator({
        	  	propsData: {
        	  		recordsPerPage : 5
        	  	}
          	})
          		        
		}
	},	
	methods: {
		search() {
    		new Promise((resolve, reject) => {
    			searchTokensValidator.validate();
				
				if (!searchTokensValidator.valid) {
					return reject("error");
				}
				
    			setTimeout(() => resolve (
					axios.post(contextPath + 'data/tc/token/search', editTokensApp.currentParameter).then((response) => {
						editTokensApp.tokens = response.data;
					})
       			), 125);
    		});			
		},
		clearResults() {
			this.tokens = null;
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
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:true}, //TODO: i18n!
				{property:"active", title:"Ενεργό", sortable:true},
				{property:"tokens", title:"Κουπόνια", sortable:false},
				{property:"edit", title:"Επεξεργασία", sortable:false},
				{property:"delete", title:"Διαγραφή", sortable:false}
            ],
            
            gridcomponents: [
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
                },
                {
                    id: "tokens",
                    value: function (value){ 
                        return "Tokens"
                    },
                    component: function (row){
                        return ManageTokenComponent;
                    }
                }                
            ],
            renderers: [
            	{
					id: "active",
	                value: function (value){
	                    switch (value){
	                        case true : return "Yes";
	                        case false : return "No";
	                       default : return "Unknown Value";
	                    }
	                },
	                component:
	                   function (value){
	                        switch (value){
	                            case true : return activeTuitionCentreComponent;
	                            case false : return notActiveTuitionCentreComponent;
	                           default : return parameterApp.defaultTemplateObject;
	                        }
	                }            		
            	}
            ]
		}
	},
	methods: {

	}
});