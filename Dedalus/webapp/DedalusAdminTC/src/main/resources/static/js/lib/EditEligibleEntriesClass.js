let EditEligibleEntriesClass = Vue.extend({
	props: {
		loadEntriesUri: {
	            type: Object,
	        required: false
		},
		showEiligbleEntriesUri: {
		    type: Object,
		    required: false
		},
		updateEntriesUri: {
		    type: Object,
		    required: false
		},
		addEntriesUri: {
		    type: Object,
		    required: false
		},
		deleteEntryUri: {
		    type: Object,
		    required: false
		}
	},
	data () {
		return {
			pg: new Paginator({
        	  	propsData: {
        	  		recordsPerPage : 5
        	  	}
          	  }),
  			pg2: new Paginator({
        	  	propsData: {
        	  		recordsPerPage : 5
        	  	}
          	  }),
          	pg3Records: new Paginator({
        	  	propsData: {
        	  		recordsPerPage : 3
        	  	}
          	  }),
          	isSelectEligibleEntriesVisible: false,
			isEditEntriesVisible: false,
			entries: null,
			eligibleEntries: null,
			entity: null,
			fieldsset: null,
            eligibleFieldsset: null,
            gridcomponents: null,
            eligibleGridcomponents: null
		}
	},
	methods: {
		showEntries(entity) {
			this.entity = entity;
			
			this.loadEntries().then(
				() => {
					this.isEditEntriesVisible = true;
				}
			);
		},
		loadEntries() {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(this.loadEntriesUri, this.entity).then((response) => {
    						this.entries = response.data;
    					})
       			), 125);
    		});
		},
		showEiligbleEntries() {
    		new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(this.showEiligbleEntriesUri, this.entity).then((response) => {
    						this.isSelectEligibleEntriesVisible = true;
    						this.eligibleEntries = response.data;
    					})
       			), 125);
    		});
    		
		},
		closeEditDialog() {
			this.isEditEntriesVisible = false;
			this.entries = null;
			this.entity = null;
		},
		updateEntries() {
    		new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(this.updateEntriesUri, this.entries).then((response) => {
    						this.isEditEntriesVisible = false;
    						this.entries = null;
    						this.entity = response.data;
    					})
       			), 125);
    		});			
			
		},
		closeEligibleEntriesDialog () {
			this.isSelectEligibleEntriesVisible = false;
			this.eligibleEntries = null;
			
		},
		addEntries() {
	    	   let eligibleEntries = this.eligibleEntries;
	    		new Promise((resolve, reject) => {
	    			setTimeout(() => resolve (
	    					axios.post(this.addEntriesUri,
	    							eligibleEntries,
			    					{
										headers: {
											'Content-Type': 'application/json; charset=utf-8'
										}
			    					}
	    					).then((response) => {
	    						this.isSelectEligibleEntriesVisible = false;
	    						this.entries = response.data;
	    					})
	       			), 125);
	    		});	    	   
	    	   
	    	   this.isSelectEligibleEntriesVisible = false;
	    	   this.eligibleEntries = null;
	    },
	    deleteEntry(entry) {
    		new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(this.deleteEntryUri, entry).then((response) => {
    						this.entries = response.data;
    					})
       			), 125);
    		});
	    }
	}
});