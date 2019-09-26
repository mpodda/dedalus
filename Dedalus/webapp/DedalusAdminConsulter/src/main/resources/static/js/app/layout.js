let currentUserApp = new Vue ({
	el: "#currentUser",
	data() {
		return {
			fullName: this.loadFullName()
		}
	},
	methods: {
		loadFullName() {
    		new Promise((resolve, reject) => {
    			setTimeout(() => resolve (
   			   		 	axios.post(contextPath + 'data/currentUserName').then((response) => {
    						this.fullName = response.data;
   			   		 	})
       			), 125);
    		});
		}
	}
});