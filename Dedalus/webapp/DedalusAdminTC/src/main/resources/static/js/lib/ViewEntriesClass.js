let ViewEntriesClass = Vue.extend({
	props: {
		loadEntriesUri: {
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
          	isViewEntriesVisible: false,
          	entity: null,
			entries: null,
			fieldsset: null,
            gridcomponents: null,
            sorting: {
           	 sortableClass: "sortable",
           	  sortAscClass: "sortasc",
           	 sortDescClass: "sortdesc"
            }            
		}
	},
	methods: {
		showEntries(entity) {
			this.entity = entity;
			
			this.loadEntries().then(
				() => {
					this.isViewEntriesVisible = true;
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
		closeViewDialog() {
			this.isViewEntriesVisible = false;
			this.entries = null;
			this.entity = null;
		}
	}
});