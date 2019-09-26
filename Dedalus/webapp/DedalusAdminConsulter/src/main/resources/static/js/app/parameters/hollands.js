let validator = null;

let EditHollandDialog = Vue.component('edit-holland-dialog', {
	i18n: i18n,
	props :{
		holland: {
            type: Object,
            required: false,
        }
	},	
	template: '#edit-holland-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	},
	mounted() {
		validator = new Validator();
		
		validator.addField({element: this.$refs.hollandValue, 
            mandatory:true,
            numeric: true,
            errorMessage: {
            	mandatory: "Το πεδίο είναι υποχρεωτικό",
            	  numeric: "Η τιμή του πεδίου πρέπει να είναι αριθμός"
            },
            errorElement:this.$refs.hollandValueError
		});
	}	
});

let parameterApp = new ParameterAppClass({
	propsData: {
		questionMessage: {
			title: "Διαγραφή Holland",
			 text: "Επιβεβαίωση διαγραφής "
		},
		deletionData() {
			return this.currentParameter?this.currentParameter.description:"";
		},
		defaultTemplateObject : defaultValue,
		dataset : new Promise((resolve, reject) => {
					setTimeout(() => resolve (
							axios.get(contextPath + 'data/hollands').then((response) => {
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
	  			   		 axios.post(contextPath + 'data/holland/save', data).then((response) => {
	  			   			 parameterApp.currentParameter = response.data;
	  				 	 })
	      		), 125);
           })
		},
		createParameterAction() {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
   			   		 	axios.post(contextPath + 'data/holland/create').then((response) => {
   			   		 		parameterApp.currentParameter = response.data;
   			   		 	})
       			), 125);
    		});
		},
		deleteParameterAction (data) {
			console.info ("Delete: " + JSON.stringify(this.currentParameter));
			
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/holland/delete', data).then((response) => {
    						
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
				{property:"value", title:"Τιμή", sortable:true},
				{property:"edit", title: "Επεξεργασία", sortable:false},
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
                }
            ]
		}
	}
});