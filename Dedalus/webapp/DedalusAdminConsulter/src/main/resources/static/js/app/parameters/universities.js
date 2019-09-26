/*  
	-------------------------------
	-- Command buttons templates --
	-------------------------------
*/


/* Show Universities Faculties */
let UniversityFaculties = Vue.component('university-faculties', {
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
    	onEditUniversityFaculties: function (){
    		editUniversityFacultiesApp.showEntries(this.data);
        }
    },
    template: '#edit-university-faculties-template'
})



/*  
	------------------------------
	-- Dialog templates (Lists) --
	-----------------------------
*/

let EditFacultyLocationsDialog = Vue.component('edit-university-faculties-dialog', {
	props :{
		faculties: {
            type: Object,
            required: false
        }
	},	
	template: '#edit-university-faculties-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});



/*
---------------------------------------
-- Dialog templates (Eligible Lists) --
---------------------------------------
*/

//------------------------ //
//-- Eligible Faculties -- //
//------------------------ //

/* Select faculties list */
let SelectUniversityEligibleFacultiesDialog = Vue.component('select-university-eligible-faculties-dialog', {
	props :{
		faculties: {
            type: Object,
            required: false
        }
	},	
	template: '#select-university-eligible-faculties-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});

/* Delete faculty command button */
let DeleteUniversityFalcultyComponent = Vue.component('delete-university-faculty', {
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
	   onDeleteUniversityFaculty: function (){
		   editUniversityFacultiesApp.deleteEntry(this.data);
       }
   },
   template: '#delete-university-faculty-template'
})

/* Eligible faculties list */
let SelectUniversityEligibleFacultyComponent = Vue.component('select-university-eligible-faculty', {
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
	   onSelectEligibleFaculty: function (){

       }
   }
   ,
   template: '#select-university-eligible-faculty-template'
})


let editUniversityFacultiesApp = new EditEligibleEntriesClass ({
	propsData: {
		loadEntriesUri: contextPath + 'data/university/faculties',
		showEiligbleEntriesUri: contextPath + 'data/university/faculties/eligible',
		updateEntriesUri: contextPath + 'data/university/faculties/update',
		addEntriesUri: contextPath + 'data/university/faculties/temp/add',
		deleteEntryUri: contextPath + 'data/university/faculties/temp/delete'
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
	                    return DeleteUniversityFalcultyComponent;
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
	                    return SelectUniversityEligibleFacultyComponent;
	                }
	            }
	        ]		
		}
	}
});



let EditUniversityDialog = Vue.component('edit-university-dialog', {
	props :{
		university: {
            type: Object,
            required: false,
        }
	},	
	template: '#edit-university-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	}
});



let parameterApp = new ParameterAppClass({
	propsData: {
		questionMessage: {
			title: "Διαγραφή Πανεπιστημίου",
			 text: "Επιβεβαίωση διαγραφής "
		},
		deletionData() {
			return this.currentParameter?this.currentParameter.description:"";
		},
		defaultTemplateObject : defaultValue,
		dataset : new Promise((resolve, reject) => {
					setTimeout(() => resolve (
							axios.get(contextPath + 'data/universities').then((response) => {
								parameterApp.setDataSet(response.data);
							})
						), 125
					);
   				})
		,
   		saveAction (data) {
			return new Promise((resolve, reject) => {
				setTimeout(() => resolve (
	  			   		 axios.post(contextPath + 'data/university/save', data,
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
   			   		 	axios.post(contextPath + 'data/university/create').then((response) => {
   			   		 		parameterApp.currentParameter = response.data;
   			   		 	})
       			), 125);
    		});
		},
		deleteParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/university/delete', data).then((response) => {
    						
    					})
       			), 125);
    		});
		},
		loadParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/university/get', data).then((response) => {
    						parameterApp.currentParameter = response.data;
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
				{property:"faculties", title:"Faculties", sortable:false},
				{property:"edit", title:"Edit", sortable:false},
				{property:"delete", title:"Delete", sortable:false}
            ],
            
            gridcomponents: [
                {
                    id: "faculties",
                    value: function (value){ 
                        return "Faculties"
                    },
                    component: function (row){
                        return UniversityFaculties;
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