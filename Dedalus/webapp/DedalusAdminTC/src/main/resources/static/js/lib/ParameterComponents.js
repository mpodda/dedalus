let EditParameterComponent = Vue.component('edit-parameter', {
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
        onEditParameter: function (){
        	parameterApp.editParameter(this.data);
        }
    },
    template: '#edit-parameter-component-template'
})

let DeleteParameterComponent = Vue.component('delete-parameter', {
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
        onDeleteParameter: function (){
        	parameterApp.deleteParameterConfirmationDialog(this.data);
        }
    },
    template: '#delete-parameter-component-template'
})