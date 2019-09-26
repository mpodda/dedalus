var defaultValue = Vue.component('default', {
    props: {
        value: {
            type: String,
            required : true
        }
    },
    template: "#default-template"
})