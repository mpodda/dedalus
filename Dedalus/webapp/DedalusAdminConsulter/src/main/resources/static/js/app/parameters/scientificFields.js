let validator = null;

let EditScientificFieldDialog = Vue.component('edit-scientific-field-dialog', {
	i18n: i18n,
	props :{
		scientificfield: {
            type: Object,
            required: false,
        }
	},	
	template: '#edit-scientific-field-dialog-template',
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
		
		
		validator.addField({element: this.$refs.number, 
            mandatory:true,
            numeric: true,
            errorMessage: {
            	mandatory: "Το πεδίο είναι υποχρεωτικό",
            	  numeric: "Η τιμή του πεδίου πρέπει να είναι αριθμός"
            },
            errorElement:this.$refs.numberError
		});
	}
});


let parameterApp = new ParameterAppClass({
	propsData: {
		questionMessage: {
			title: "Διαγραφή Επιστημονικού Πεδίου",
			 text: "Επιβεβαίωση διαγραφής "
		},
		deletionData() {
			return this.currentParameter?this.currentParameter.description:"";
		},
		defaultTemplateObject : defaultValue,
		dataset : new Promise((resolve, reject) => {
					setTimeout(() => resolve (
							axios.get(contextPath + 'data/scientificFields').then((response) => {
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
	  			   		 axios.post(contextPath + 'data/scientificField/save', data).then((response) => {
	  			   			 parameterApp.currentParameter = response.data;
	  				 	 })
	      		), 125);
           })
		},
		createParameterAction() {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
   			   		 	axios.post(contextPath + 'data/scientificField/create').then((response) => {
   			   		 		parameterApp.currentParameter = response.data;
   			   		 	})
       			), 125);
    		});
		},
		deleteParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/scientificField/delete', data).then((response) => {
    						
    					})
       			), 125);
    		});
		}
	}
});


let app = new Vue ({
	i18n: i18n,
	el: "#app-content",
	data () {
		return {
			fieldsset:[
				{property:"description", title: this.$t("parameters.description"), sortable:true},
				{property:"number", title:"Αριθμός", sortable:true},
				{property:"edit", title: "Επεξεργασία", sortable:false},
				{property:"delete", title: "Διαγραφή", sortable:false}
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
                }
            ]
		}
	}
});