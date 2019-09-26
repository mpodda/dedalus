let currentPageIndicator = 
Vue.component('current-page-indicator', {
	props :{
		templateloader: {
            type: Function,
            required: false,
        }		
	},
	template: '#current-page-indicator-template'
});


const currentPageIndicatorHome = Vue.component('current-page-indicator-home', {
	template: '#current-page-indicator-home-template'
});

const currentPageIndicatorPupils = Vue.component('current-page-indicator-pupils', {
	template: '#current-page-indicator-pupils-template'
});

const currentPageIndicatorTokens = Vue.component('current-page-indicator-tokens', {
	template: '#current-page-indicator-tokens-template'
});


let currentPageApp = new Vue ({
	el: "#app-current-page",
	data () {
		return {
			currentPageName: this.loadCurrentPageName()
		}
	},
	methods: {
		loadCurrentPageName() {
			new Promise((resolve, reject) => {
				setTimeout(() => resolve (
						axios.get(contextPath + 'data/currentPage').then((response) => {
							this.currentPageName = response.data;
						})
					), 125
				);
			})
		},
		getTemplate() {
			switch (this.currentPageName) {
				case 'home':
					return currentPageIndicatorHome;
				break;
				
				case 'pupils' :
					return currentPageIndicatorPupils;
				break;

				case 'tokens':
				    return currentPageIndicatorTokens;
                break;

				default:
					return currentPageIndicatorHome;
				break;
			}
		}
	}
});




/*
const footerApp = new Vue ({
	el: "#app-current-page",
	data() {
		return {
			
		}
	}
});
*/