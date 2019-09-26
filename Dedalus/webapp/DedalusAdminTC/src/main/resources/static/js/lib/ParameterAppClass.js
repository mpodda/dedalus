let ParameterAppClass = Vue.extend({
	props: {
		questionMessage: {
                type: Object,
            required: false,
             default: ""
        },
        defaultQuestionText: {
        	type: String,
        	required: false
        },
        deletionData: {
            type: Function,
        required: false
        },        
        defaultTemplateObject: {
                type: Object,
            required: false        	
        },
        saveAction: {
                type: Function,
            required: false
        },
        createParameterAction: {
                type: Function,
            required: false
        },
        deleteParameterAction: {
            	type: Function,
            required: false
        },
        loadParameterAction: {
        	type: Function,
            required: false
        }        
	},
	created() {
		this.defaultQuestionText = this.questionMessage.text;
	},
	data () {
		return {
			             dataset: null,
			    currentParameter: null,
			 isInfoDialogVisible: false,
			 isEditDialogVisible: false,
			isYesNoDialogVisible: false,
			     defaultTemplate: this.defaultTemplateObject,
			        enablePaging: true,
                              pg: new Paginator({
                            	  	propsData: {
                            	  		recordsPerPage : 5
                            	  	}
                              	  }),
                         sorting: {
                        	 sortableClass: "sortable",
                        	  sortAscClass: "sortasc",
                        	 sortDescClass: "sortdesc"
                         },
                 isEditParameter: true
		}
	},
	methods: {
		createParameter() {
			console.info("Create parameter");
			this.currentParameter = 
				this.createParameterAction().then (
					() => {
						this.isEditDialogVisible = true;
						this.isEditParameter = false;
					}
				); 
		},
		editParameter(data) {
			if (this.loadParameterAction) {
				console.info("Load Parameter. Data= " + JSON.stringify(data));
				this.loadParameterAction(data).then (
					(response) => {
						this.isEditParameter = true;
						this.isEditDialogVisible = true;
					}
				);
			} else {
				this.currentParameter = data;
				this.isEditParameter = true;
				this.isEditDialogVisible = true;
			}
		},
		deleteParameterConfirmationDialog(data) {
			this.currentParameter = data;
			 
			this.questionMessage.text += this.deletionData();
			this.isYesNoDialogVisible = true;
		},
		denialParameterDeletion() {
			this.currentParameter = null;
			this.questionMessage.text = this.defaultQuestionText;
			this.isYesNoDialogVisible = false;
		},
		deleteParameter() {
			this.deleteParameterAction(this.currentParameter).then (
				() => {
					const index = this.dataset.indexOf(this.currentParameter);
					this.dataset.splice(index, 1);
					this.currentParameter = null;
					this.isYesNoDialogVisible = false;
					this.questionMessage.text = this.defaultQuestionText;
				}
			);
		},
		closeEditDialog() {
			this.isEditDialogVisible = false;
			this.currentParameter = null;
		},
		setDataSet(dataSet) {
			this.dataset = dataSet;
		},
		saveParameter(data){
			this.saveAction(data).then (
				() => {
					data = this.currentParameter;
					this.closeEditDialog();
					
					if (!this.isEditParameter) {
						this.dataset.push(data);
					} else {
						if (this.dataset != null) {
							for (let i=0; i<this.dataset.length; i++){
								if (this.dataset[i]["id"] == data["id"]){
									this.dataset[i] = data;
									break;
								}
							}
						}
					}
					
					//TODO: Show info dialog
				}
			).catch(() => console.log("Error"))
			;
		}
	}
});
