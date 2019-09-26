let categoryValidator = null;

let EditCategoryDialog = Vue.component('edit-category-dialog', {
	i18n: i18n,
	props :{
        category: {
            type: Object,
            required: false,
        }
	},
	template: '#edit-category-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	},
	mounted() {
		categoryValidator = new Validator();
		
		categoryValidator.addField({element: this.$refs.description, 
            mandatory:true,
            errorMessage: {
                //mandatory: "Το πεδίο είναι υποχρεωτικό"
            	mandatory: this.$t("validation.field.mandatory")
            },
            errorElement:this.$refs.descriptionError
		});
	}
});

let parameterApp = new ParameterAppClass({
	propsData: {
		questionMessage: {
			title: "Διαγραφή Κατηγορίας",
			 text: "Επιβεβαίωση διαγραφής "
		},
		deletionData() {
			return this.currentParameter?this.currentParameter.description:"";
		},
		defaultTemplateObject : defaultValue,
		dataset : new Promise((resolve, reject) => {
					setTimeout(() => resolve (
							axios.get(contextPath + 'data/categories').then((response) => {
								parameterApp.setDataSet(response.data);
							})
						), 125
					);
   				})
		,
   		saveAction (data) {
			return new Promise((resolve, reject) => {
				categoryValidator.validate();
				
				if (!categoryValidator.valid) {
					return reject("error");
				}
				
				setTimeout(() => resolve (
	  			   		 axios.post(contextPath + 'data/category/save', data).then((response) => {
	  			   			 parameterApp.currentParameter = response.data;
	  				 	 })
	      		), 125);
           })
		},
		createParameterAction() {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
   			   		 	axios.post(contextPath + 'data/category/create').then((response) => {
   			   		 		parameterApp.currentParameter = response.data;
   			   		 	})
       			), 125);
    		});
		},
		deleteParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/category/delete', data).then((response) => {
    						
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
				{property:"edit", title: this.$t("parameters.edit"), sortable:false},
				{property:"delete", title: this.$t("parameters.delete"), sortable:false}
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