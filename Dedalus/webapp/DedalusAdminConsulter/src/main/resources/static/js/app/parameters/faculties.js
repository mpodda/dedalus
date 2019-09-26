/*  
	-------------------------------
	-- Command buttons templates --
	-------------------------------
*/


/* 
 Show faculty locations command 
 */
let FacultyLocations = Vue.component('faculty-locations', {
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
    	onEditFacultyLocations: function (){
    		editFacultyLocationsApp.showEntries(this.data);
        }
    },
    template: '#faculty-locations-template'
})


/* 
 Show faculty universities command
*/
let FacultyUniversities = Vue.component('faculty-universities', {
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
    	onEditFacultyUniversities: function (){
    		editFacultyUniversitiesApp.showEntries(this.data);
        }
    },
    template: '#faculty-universities-template'
})



/*  
	------------------------------
	-- Dialog templates (Lists) --
	-----------------------------
*/

/* Locations */
let EditFacultyLocationsDialog = Vue.component('edit-faculty-locations-dialog', {
	props :{
		locations: {
            type: Object,
            required: false
        }
	},	
	template: '#edit-faculty-locations-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});


/* Universities */
let EditFacultyUniversitiesDialog = Vue.component('edit-faculty-universities-dialog', {
	props :{
		universities: {
            type: Object,
            required: false
        }
	},	
	template: '#edit-faculty-universities-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});


/*
---------------------------------------
-- Dialog templates (Eligible Lists) --
---------------------------------------
*/


// ------------------------ //
// -- Eligible Locations -- //
//------------------------ //

/* Select Locations list */
let SelectFacultyEligibleLocationsDialog = Vue.component('select-faculty-eligible-locations-dialog', {
	props :{
		locations: {
            type: Object,
            required: false
        }
	},	
	template: '#select-faculty-eligible-locations-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});

/* Delete location command button */
let DeleteFalcultyLocationComponent = Vue.component('delete-faculty-location', {
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
	   onDeleteFacultyLocation: function (){
		   editFacultyLocationsApp.deleteEntry(this.data);
       }
   },
   template: '#delete-faculty-location-template'
})

/* Eligible Locations List */
let SelectFalcultyEligibleLocationComponent = Vue.component('select-faculty-eligible-location', {
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
	   onSelectEligibleLocation: function (){

       }
   }
   ,
   template: '#select-faculty-eligible-location-template'
})


// --------------------------- //
// -- Eligible Universities -- //
//---------------------------- //

/* Select universities list */
let selectFacultyEligibleUniversitiesDialog = Vue.component('select-faculty-eligible-universities-dialog', {
	props :{
		universities: {
            type: Object,
            required: false
        }
	},	
	template: '#select-faculty-eligible-universities-dialog-template',
	components: {
		editGenericDialog : EditGenericDialogComponent
	}
});


/* Delete University command button */
let DeleteFalcultyUniversityComponent = Vue.component('delete-faculty-university', {
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
	   onDeleteFacultyUniversity: function (){
		   editFacultyUniversitiesApp.deleteEntry(this.data);
       }
   },
   template: '#delete-faculty-university-template'
})


/* Select eligible Universities */
let SelectFalcultyEligibleUniversityComponent = Vue.component('select-faculty-eligible-university', {
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
   template: '#select-faculty-eligible-university-template'
})


/* Manage eligible locations */
let editFacultyLocationsApp = new EditEligibleEntriesClass ({
	propsData: {
		loadEntriesUri: contextPath + 'data/faculty/locations',
		showEiligbleEntriesUri: contextPath + 'data/faculty/locations/eligible',
		updateEntriesUri: contextPath + 'data/faculty/locations/update',
		addEntriesUri: contextPath + 'data/faculty/locations/temp/add',
		deleteEntryUri: contextPath + 'data/faculty/locations/temp/delete'
	},
	data () {
		return {
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:false},
				{property:"delete", title:"Delete", sortable:false}
            ],
	        eligibleFieldsset: [
	        	{property:"location.description", title:"Περιγραφή", sortable:false},
	        	{property:"selected", title:"Επιλογή", sortable:false}
	        ],
	        gridcomponents: [
	            {
	                id: "delete",
	                value: function (value){ 
	                    return "Delete"
	                },
	                component: function (row){
	                    return DeleteFalcultyLocationComponent;
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
	                    return SelectFalcultyEligibleLocationComponent;
	                }
	            }
	        ]		
		}
	}
});


/* Manage eligible universities */
let editFacultyUniversitiesApp = new EditEligibleEntriesClass ({
	propsData: {
		loadEntriesUri: contextPath + 'data/faculty/universities',
		showEiligbleEntriesUri: contextPath + 'data/faculty/universities/eligible',
		updateEntriesUri: contextPath + 'data/faculty/universities/update',
		addEntriesUri: contextPath + 'data/faculty/universities/temp/add',
		deleteEntryUri: contextPath + 'data/faculty/universities/temp/delete'
	},
	data () {
		return {
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:false},
				{property:"delete", title:"Delete", sortable:false}
            ],
	        eligibleFieldsset: [
	        	{property:"university.description", title:"Περιγραφή", sortable:false},
	        	{property:"selected", title:"Επιλογή", sortable:false}
	        ],
	        gridcomponents: [
	            {
	                id: "delete",
	                value: function (value){ 
	                    return "Delete"
	                },
	                component: function (row){
	                    return DeleteFalcultyUniversityComponent;
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
	                    return SelectFalcultyEligibleUniversityComponent;
	                }
	            }
	        ]		
		}
	}
});



//-------------------- //
//-- Manage Faculty -- //
//-------------------- //

/* Create / Edit Faculty dialog */
let EditFacultyDialog = Vue.component('edit-faculty-dialog', {
	props :{
		faculty: {
            type: Object,
            required: false,
        }
	},	
	template: '#edit-faculty-dialog-template',
	components: {
		editGenericDialog : EditGenericParameterDialogComponent
	}
});


/* Create / Edit / Delete Faculty */
let parameterApp = new ParameterAppClass({
	propsData: {
		questionMessage: {
			title: "Διαγραφή Σχολής",
			 text: "Επιβεβαίωση διαγραφής "
		},
		deletionData() {
			return this.currentParameter?this.currentParameter.description:"";
		},
		defaultTemplateObject : defaultValue,
		dataset : new Promise((resolve, reject) => {
					setTimeout(() => resolve (
							axios.get(contextPath + 'data/faculties').then((response) => {
								parameterApp.setDataSet(response.data);
							})
						), 125
					);
   				})
		,
   		saveAction (data) {
			return new Promise((resolve, reject) => {
				setTimeout(() => resolve (
	  			   		 axios.post(contextPath + 'data/faculty/save', data).then((response) => {
	  			   			 parameterApp.currentParameter = response.data;
	  				 	 })
	      		), 125);
           })
		},
		createParameterAction() {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
   			   		 	axios.post(contextPath + 'data/faculty/create').then((response) => {
   			   		 		parameterApp.currentParameter = response.data;
   			   		 	})
       			), 125);
    		});
		},
		deleteParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/faculty/delete', data).then((response) => {
    						
    					})
       			), 125);
    		});
		},
		loadParameterAction (data) {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(contextPath + 'data/faculty/get', data).then((response) => {
    						parameterApp.currentParameter = response.data;
    					})
       			), 125);
    		});
			
		}
	}
});


//----------------- //
//-- Application -- //
//----------------- //

let app = new Vue ({
	el: "#app-content",
	data () {
		return {
			fieldsset:[
				{property:"description", title:"Περιγραφή", sortable:true}, //TODO: i18n!
				{property:"universities", title:"Universities", sortable:false},
				{property:"locations", title:"Locations", sortable:false},
				{property:"edit", title:"Edit", sortable:false},
				{property:"delete", title:"Delete", sortable:false}
            ],
            gridcomponents: [
                {
                    id: "universities",
                    value: function (value){ 
                        return "Universities"
                    },
                    component: function (row){
                        return FacultyUniversities;
                    }
                },            	
                {
                    id: "locations",
                    value: function (value){ 
                        return "Locations"
                    },
                    component: function (row){
                        return FacultyLocations;
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
	}
});