let EditPupilDialog = Vue.component('edit-pupil-dialog', {
	props :{
        pupil: {
            type: Object,
            required: false,
        }
	},
	template: '#edit-pupil-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	},
	mounted() {
		//T
	}
});

let parameterApp = new ParameterAppClass({
	propsData: {
		questionMessage: {
			title: "Διαγραφή Μαθητή",
			 text: "Επιβεβαίωση διαγραφής "
		},
		deletionData() {
			return this.currentParameter?this.currentParameter.name:"";
		},
		defaultTemplateObject : defaultValue,
		dataset : new Promise((resolve, reject) => {
					setTimeout(() => resolve (
							axios.get(contextPath + 'data/pupils').then((response) => {
								parameterApp.setDataSet(response.data);
							})
						), 125
					);
   				})
		,
   		saveAction (data) {
			return new Promise((resolve, reject) => {
				setTimeout(() => resolve (
	  			   		 axios.post(contextPath + 'data/pupil/save', data).then((response) => {
	  			   			 parameterApp.currentParameter = response.data;
	  			   			 
	  			   			 
	  				 	 })
	      		), 125);
           })
		},
		createParameterAction() {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
   			   		 	axios.post(contextPath + 'data/pupil/create').then((response) => {
   			   		 		parameterApp.currentParameter = response.data;
   			   		 		
   			   		 	console.info("Create: " + JSON.stringify(response.data));
   			   		 	})
       			), 125);
    		});
		},
		deleteParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/pupil/delete', data).then((response) => {
    						
    					})
       			), 125);
    		});
		}
	}
});

//-------------------------------------------------------------------------------------------------

/*
 --------------------
 -- Questionnaires --
 --------------------
*/

let ShowEligibleQuestionnairesCommandComponent = Vue.component('show-eligible-questionnaires', {
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
    	onShowEligibleQuestionnaires: function (){
        	viewQuestionnairesApp.showEntries(this.data);
        }
    },
    template: '#show-eligible-questionnaires-command-component-template'
});

let EligibleQuestionnairesDialog = Vue.component('eligible-questionnaires-dialog', {
	
	props :{
        pupil: {
            type: Object,
            required: false,
        }
	},
	
	template: '#eligible-questionnaires-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	}
});


let SendQuestionnaireConfirmationDialog = Vue.component('send-questionnaire-confirmation', {
	props : {
        confirmationfunc: {
            type: Function,
            required: true
        },
        disagreementfunc: {
            type: Function,
            required: false
        },
        cancelfunc: {
            type: Function,
            required: false
        },
        pupil: {
            type: Object,
            required: false,
        }        
	},
	template: '#yesno-dialog-template',
	components: {
		//yesNoDialog : YesNoDialog
	},
	data() {
		return {
			message: {
				title: "Επιβεβαίωση αποστολής ερωτηματολογίου",
				text: "Να αποσταλεί το ερωτηματολόγιο στον Μαθητή " + viewQuestionnairesApp.entity.name
			}
		}
	}
	
});


let SendTokenCommandComponent = Vue.component('show-eligible-questionnaires', {
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
   	onTokenSend: function (){
   		const questionnaire = this.data;
   		
		new Promise((resolve, reject) => {
			setTimeout(() => resolve (
					axios.get(contextPath + 'data/pupil/sendToken/create').then((response) => {
						let sendTokenRequest = response.data;
						sendTokenRequest["pupil"] = viewQuestionnairesApp.entity;
						sendTokenRequest["questionnaire"] = questionnaire;
						
						console.info("Send sendTokenRequest: " + JSON.stringify(sendTokenRequest));
						
						viewQuestionnairesApp.confirmSendToken(sendTokenRequest);
					})
				), 
				125
			)
			}).catch (() => {
				
			});   		
   		
       }
   },
   template: '#send-token-command-component-template'
});


let viewQuestionnairesApp = new ViewEntriesClass ({
	propsData: {
		loadEntriesUri: contextPath + 'data/pupil/availableQuestionnaires'
	},
	data () {
		return {
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:true},
				{property:"sendToken", title:"Αποστολή", sortable:false}
	        ],
	        gridcomponents: [
            	{
                    id: "sendToken",
                    value: function (value){ 
                    	return "Send Token"
                    },
                    component: function (row){
                        return SendTokenCommandComponent;
                    }
                }	        	
	        ],
	        isConfirmVisible: false,
	        enablePaging: true,
	        sendTokenRequest: null
		}
	},
	methods: {
		confirmSendToken(sendTokenRequest) {
			this.sendTokenRequest = sendTokenRequest;
			this.isConfirmVisible = true;
		},
		cancel () {
			console.info("Cancel");
			
			this.invisibleConfirmation();
		},
		sendToken() {
			console.info("Send Token");
			
			new Promise((resolve, reject) => {
				setTimeout(() => resolve (
						axios.post(contextPath + 'data/pupil/sendToken', this.sendTokenRequest).then((response) => {
							viewQuestionnairesApp.loadEntries();
							alert("Το Ερωτηματολόγιο απεστάλη επιτυχώς");
						})
					), 
					125
				)
				}).catch (() => {
					alert("Σφάλμα κατά την αποστολή του Ερωτηματολογίου");
				});
			
			this.invisibleConfirmation();
			
		},
		doNotSendToken() {
			console.info("Do Not Send Token");
			this.invisibleConfirmation();
		},
		invisibleConfirmation() {
			this.isConfirmVisible = false;
		},
		showTokenHistoty() {
			console.info("showTokenHistoty of pupil " + JSON.stringify(this.entity));
			viewTokensApp.showEntries(this.entity);
		}
	}	

});




/*
--------------------
-- Tokens History --
--------------------
*/

let TokensHistoryDialog = Vue.component('view-pupil-tokens-history-dialog', {
	
	props :{
        pupil: {
            type: Object,
            required: false,
        }
	},
	
	template: '#view-pupil-tokens-history-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	}
});


let viewTokensApp = new ViewEntriesClass ({
	propsData: {
		loadEntriesUri: contextPath + 'data/pupil/tokens'
	},
	data () {
		return {
			fieldsset:[
				{property:"value", title:"Κωδικός", sortable:false},
				{property:"assignDateAsString", title:"Ημ/νία αποστολής", sortable:true},
				{property:"questionnaire.description", title:"Ερωτηματολόγιο", sortable:true}
	        ],
	        enablePaging : true,
	        sorting: true
		}
	},
	methods: {
		
	}
});


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
				{property:"name", title:"Όνομα", sortable:true}, //TODO: i18n!
				{property:"phone", title:"Τηλέφωνο", sortable:true},
				{property:"mobile", title:"Κινητό", sortable:true},
				{property:"email", title:"Email", sortable:true},
				{property:"edit", title:"Επεξεργασία", sortable:false},
				{property:"delete", title:"Διαγραφή", sortable:false},
				{property:"eligibleQuestionnaires", title:"Ερωτηματολόγια", sortable:false}
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
                    id: "eligibleQuestionnaires",
                    value: function (value){
                        return "EligibleQuestionnaires"
                    },
                    component: function (row){
                        return ShowEligibleQuestionnairesCommandComponent;
                    }
                }
            ]
		}
	}
});