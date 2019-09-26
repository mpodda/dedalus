let SelectEntryClass = Vue.extend({
	props: {
		loadEntriesUri: {
            type: Object,
        required: false
		},
		setEntryUri: {
            type: Object,
        required: false
		},
		removeEntryUri: {
            type: Object,
        required: false
		},
		 setEntity: {
             type: Function,
         required: false
     }
	},
	data() {
		return {
			pg: new Paginator({
        	  	propsData: {
        	  		recordsPerPage : 5
        	  	}
          	  }),			
			entity: null,
			isSelectEntryVisible: false,
			eligibleEntries: null
		}
	},
	methods: {
		showEntries(entity) {
			this.entity = entity;
			console.info("entity [Before]: " + JSON.stringify (this.entity));
			
			this.loadEntries().then(
				() => {
					this.isSelectEntryVisible = true;
				}
			);
		},
		loadEntries() {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(this.loadEntriesUri, this.entity).then((response) => {
    						this.eligibleEntries = response.data;
    					})
       			), 125);
    		});
		},		
		closeSelectEntryDialog() {
			this.entity = null;
			this.isSelectEntryVisible = false;
		},
		selectEntry(row) {
			console.info("Select: " + JSON.stringify (row));
			
			this.setEntry(row).then(
				() => {
					this.isSelectEntryVisible = false;
					console.info("close");
				}
			);
		},
		setEntry(entry) {
			let entity = this.entity;
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(this.setEntryUri, entry).then((response) => {
    					//	console.info("Entity [after] : " + JSON.stringify (parameterApp.currentParameter));
    						this.setEntity(response.data); 
    					})
       			), 125);
    		});			
		},
		removeEntry() {
    		return new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
    					axios.post(this.removeEntryUri).then((response) => {
    						this.setEntity(response.data); 
    					})
       			), 125);
    		});			
		}
	}
});