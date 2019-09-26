const tokenStatusTemplate =  
	Vue.component('token-status', {
		props :{
			avt: {
	            type: Object
	        },
	        description: {
	        	type: Object
	        },
	        text: {
	            type: Object
	        }
		},
		methods: {
			renderMessages() {
				switch (this.avt.status) {
					case 'Available': 
						this.text = "Διαθέσιμα"; 
						this.description = 'Επί του συνόλου';
					break;
					case 'NotStarted': 
						this.text = "Αναπάντητα";
						this.description =  'Επί του συνόλου των ανατεθημένων (μη ολοκληρωμένων)';
					break;
					case 'OnGoing': 
						this.text = "Σε διαδικασία απάντησης";
						this.description =  'Επί του συνόλου των ανατεθημένων (μη ολοκληρωμένων)';
					break;
					case 'Completed': 
						this.text = "Ολοκληρωμένα";
						this.description =  'Επί του συνόλου';
					break;
					default:
						this.text = '';
						this.description =  '';
					break;
				}
			}
		},
		template: '#token-status-template'
	});

let homeApp = new Vue ({
	el: "#homeApp",
	data () {
		return {
			available: this.loadAvailableTokens(),
		   notStarted: this.loadNotStartedTokens(),
			  onGoing: this.loadOnGoingTokens(),
			completed: this.loadCompletedTokens()
		}
	},
	methods: {
		loadAvailableTokens() {
			new Promise((resolve, reject) => {
				setTimeout(() => resolve (
						axios.get(contextPath + 'data/availableTokens').then((response) => {
							this.available = response.data;
						})
					), 125
				);
			})
		},
		loadNotStartedTokens() {
			new Promise((resolve, reject) => {
				setTimeout(() => resolve (
						axios.get(contextPath + 'data/notStartedTokens').then((response) => {
							this.notStarted = response.data;
						})
					), 125
				);
			})
		},
		loadOnGoingTokens() {
			new Promise((resolve, reject) => {
				setTimeout(() => resolve (
						axios.get(contextPath + 'data/onGoingTokens').then((response) => {
							this.onGoing = response.data;
						})
					), 125
				);
			})
		},
		loadCompletedTokens() {
			new Promise((resolve, reject) => {
				setTimeout(() => resolve (
						axios.get(contextPath + 'data/completedTokens').then((response) => {
							this.completed = response.data;
						})
					), 125
				);
			})
		}
	}
});