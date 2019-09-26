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
            
            this.fields.forEach (
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
            if (this.isMandatory(field["mandatory"])) {
                if (!this.hasValue(field["element"])){
                	this.showError(field, "mandatory");
                	
                    this.valid = false;
                    return;
                } 
            }
            
            if (this.isNumeric(field["numeric"])) {
            	if (!this.isNumericValue(field["element"].value)){
            		this.showError(field, "numeric");
            		
            		this.valid = false;
            		return;
            	}
            }
            
            if (field["customValidation"]) {
            	console.info(typeof field["customValidation"]() == 'boolean');
            	
            	if (typeof field["customValidation"]() == 'boolean'){
            		this.valid = field["customValidation"]();
            		
                	if (!this.valid){
                		this.showError(field, "custom");
                	}

            	} else {
            		let promise = field["customValidation"]();
            		
            		let validator = this; 
            		promise.then (
            			(result) => {
            				validator.valid = result;
            			
                        	if (!validator.valid){
                        		validator.showError(field, "custom");
                        	}
            			}
            		);
            	}
            	
            	return;
            }
        },
        showError(field, errorType) {
            if (field["errorElement"]) {
                field["errorElement"].innerHTML = field["errorMessage"][errorType];
            }
            
            if (field["errorMessage"]["errorType"]) {
                this.currentErrorMessage = field["errorMessage"][errorType];
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
        isNumeric(numeric) {
            if (numeric) {
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
                if (element.value.trim().length === 0 || element.value === this.emptyValueForInput) {
                    return false;
                }
                
            }
            
            console.info("hasValue");
            
            return true;
        },
        isNumericValue(value) {
            let isNumber = false;
            
            if (value.toString().trim() == "") {
                isNumber = false;
            } else {
            	value = value.toString().replace(",", ".");
                
                isNumber = !isNaN(value) && isFinite(value);
            }
            
            return isNumber;
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