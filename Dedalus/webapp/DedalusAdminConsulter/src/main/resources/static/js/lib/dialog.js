let InfoDialog = Vue.component('info-dialog', {
    props: {
        message: {
            type: Object,
            required: false,
        }
    },
    
    methods: {
        
    },
    template: '#info-dialog-template'
});


let YesNoDialog = Vue.component('yesno-dialog', {
    props: {
        message: {
            type: Object,
            required: false,
        },
        confirmationfunc: {
            type: Function,
            required: true
        },
        disagreementfunc: {
            type: Function,
            required: false
        },
        cancelfunc: {
            type: Function,
            required: false
        }
    },
    
    data() {
        return {
            confirm: false
        }
    },
    template: '#yesno-dialog-template'
});

const EditGenericDialogComponent = Vue.component('edit-generic-dialog', {
	props :{
        data: {
            type: Object,
            required: false
        },
        saveaction: {
            type: Function,
            required: false
        },
        cancelaction: {
            type: Function,
            required: false
        }
	},
	methods: {
		cancel () {
			this.cancelaction();
		},
		save () {
			this.saveaction(this.data);
		}
	},
	template: '#edit-generic-dialog-template'
});


const EditGenericParameterDialogComponent = Vue.component('edit-generic-parameter-dialog', {
	props :{
        data: {
            type: Object,
            required: false
        },
        saveaction: {
            type: Function,
            required: false
        },
        cancelaction: {
            type: Function,
            required: false
        },
        viewonly: {
            type: Object,
        required: false,
         default: false
        }
	},
	methods: {
		cancel () {
			this.cancelaction();
		},
		save () {
			this.saveaction(this.data);
		}
	},
	template: '#edit-generic-parameter-dialog-template'
});