const DataTypes = 
	Object.freeze({
        DATE : "date",
     NUMERIC : "numeric",
       EMAIL : "email"
	});


let Field = Vue.extend({
    
});

let errors = [];

let Validator = Vue.extend({
    props: {
        shortCircuitErrors: {
                type: Boolean,
            required: false,
             default: true
        },
        emptyValueForSelect: {
                type: String,
            required: false,
             default: "0"
        },
        emptyValueForInput: {
                type: String,
            required: false,
             default: ""
        }
    },
    data() {
      return {
                         valid: true,
                        fields: [],
          currentErrorMessage : ""
      }
    },
    watch: {
        
    },
    methods: {
        validate() {
            this.valid = true;
            
            const validator = this;
            
            this.fields.forEach(
                function clear(field, index) {
                    if (field["errorElement"]) {
                        validator.clearErrors(field["errorElement"]);
                    }
                }
            );
            
            if (!this.shortCircuitErrors) {
                this.fields.forEach(this.validateField);
            } else {
                if (this.fields) {
                    for (let f = 0; f<this.fields.length; f++){
                        this.validateField(this.fields[f]);
                        if (!this.valid) {
                            return;
                        }
                    }
                }
            }
        },
        validateField(field) {
            //console.info("Field: " + this.getValue(field["element"]) + " mandatory: " + this.isMandatory(field["mandatory"]));
            
        	//console.info("Value="  + this.getValue(field["element"]));
        	
            if (this.isMandatory(field["mandatory"])) {
            	
                if (!this.hasValue(field["element"])){
                    if (field["errorElement"]) {
                        field["errorElement"].innerHTML = field["errorMessage"]["mandatory"];
                        
                        //console.info("Show error: " + field["errorMessage"]["mandatory"]);
                        //console.info(field["errorElement"]);
                    }
                    
                    if (field["errorMessage"]["mandatory"]) {
                        this.currentErrorMessage = field["errorMessage"]["mandatory"];
                    }
                    
                    this.valid = false;
                } 
                /*
                else {
                	this.valid = false;
                    this.currentErrorMessage = "";
                    
                    if (field["errorMessage"]["mandatory"]) {
                        this.currentErrorMessage = "";
                    }
                    
                    //console.info("NOT SHOW ERROR: ");
                    //console.info(field["errorElement"]);
                    
                }
                */
                
            }
        },
        getValue(element) {
            return element.value;
        },
        isMandatory(mandatory) {
            if (mandatory) {
                return true;
            }
            
            return false;
        },
        hasValue(element) {
            if (element.type.toLowerCase() == "checkbox" ||
                    element.type.toLowerCase() == "radio") {
                if (!element.checked) {
                    return false;
                }
            } else if (element.type.toLowerCase() == "select-one") {
                if (element.value.length == 0 || element.value == this.emptyValueForSelect) {
                    return false;
                }
            }  
            else if (element.type.toLowerCase() == "select-multiple") {
                if (element.options.length == 0) {
                    return false;
                }
            } else {
                if (element.value.length === 0 || element.value === this.emptyValueForInput) {
                    return false;
                }
                
            }          
            
            return true;
        },
        isNumericValue(value) {
                let isNumber = false;
                
                if (v.toString().trim() == "") {
                    isNumber = false;
                } else {
                    v = v.toString().replace(",", ".");
                    
                    isNumber = !isNaN(v) && isFinite(v);
                }            
        },
        addField(field) {
            this.fields.push(field);
        },
        clearErrors(errorElement) {
            errorElement.innerHTML = "";
        }
    },
    computed : {
    }
});