let EditLocationDialog = Vue.component('edit-location-dialog', {
	props :{
		location: {
            type: Object,
            required: false,
        }
	},	
	template: '#edit-location-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	}
});

let parameterApp = new ParameterAppClass({
	propsData: {
		questionMessage: {
			title: "Διαγραφή Πόλης",
			 text: "Επιβεβαίωση διαγραφής "
		},
		deletionData() {
			return this.currentParameter?this.currentParameter.description:"";
		},
		defaultTemplateObject : defaultValue,
		dataset : new Promise((resolve, reject) => {
					setTimeout(() => resolve (
							axios.get(contextPath + 'data/locations').then((response) => {
								parameterApp.setDataSet(response.data);
							})
						), 125
					);
   				})
		,
   		saveAction (data) {
			return new Promise((resolve, reject) => {
				setTimeout(() => resolve (
	  			   		 axios.post(contextPath + 'data/location/save', data).then((response) => {
	  			   			 parameterApp.currentParameter = response.data;
	  				 	 })
	      		), 125);
           })
		},
		createParameterAction() {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
   			   		 	axios.post(contextPath + 'data/location/create').then((response) => {
   			   		 		parameterApp.currentParameter = response.data;
   			   		 	})
       			), 125);
    		});
		},
		deleteParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/location/delete', data).then((response) => {
    						
    					})
       			), 125);
    		});
		}
	}
});

let app = new Vue ({
	el: "#app-content",
	data () {
		return {
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:true}, //TODO: i18n!
				{property:"edit", title:"Edit", sortable:false},
				{property:"delete", title:"Delete", sortable:false}
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
	},
	methods: {

	}
});