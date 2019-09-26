function resolveObjectValue (path, object) {
    return path.split('.').reduce(
        function(prev, curr) {
            if (prev) {
                return prev[curr];
            }

            return null;
        },
        object || self);      
}

let gridRow = Vue.component('grid-row', {
    props: {
        rawdata: {
            type: Object,
            required: false
        },
        selected: {
            type: Boolean,
            required: false
        },
        currentclassname : {
          type: String,
          required: false
        }
    },
   
    methods: {
        isSelected: function() {
          return this.selected;  
        },
        
        selectRow: function(event) {
            var currentUid = this._uid;
            
            this.$parent.$children.forEach (
                function (child, index) {
                    if (child._uid != currentUid && child.selected) {
                        //Remove selection indication
                        child.selected = false;
                        
                        //Undo selection
                        child._vnode.elm.className = child.currentclassname;
                    }
                }
            );
            
            this.selected = !this.selected;
            
            event.currentTarget.className += " " + this.$parent.selectedrowclassname;
            
            this.$emit('onselectRow', this.rawdata);
        },
        
        getRenderer: function(columnIndex) {
            var field = this.$parent.fieldsset[columnIndex]["property"];
            
            if (field != undefined) {
                for (var r=0; r<this.$parent.renderers.length; r++) {
                    var renderer = this.$parent.renderers[r];
                    
                    if (field==renderer["id"]) {
                        return renderer;
                    }
                }
            }
        },
        getComponent: function(columnIndex) {
            var field = this.$parent.fieldsset[columnIndex]["property"];
            
            if (field != undefined) {
                for (var c=0; c<this.$parent.gridcomponents.length; c++) {
                    var component = this.$parent.gridcomponents[c];
                    
                    if (field == component["id"]) {
                        return component;
                    }
                }
            }
            
            return this.$parent.defaulttemplate;
        },
        
        getValue: function (f) {
            var field = this.$parent.fieldsset[f]["property"];
            
            if (this.$parent.isRenderer(field)) {
                 return this.getRenderer(f).value(resolveObjectValue(field, this.rawdata));
            } else {
                if (this.$parent.isComponent(field)) {
                    this.getComponent(f);
                    return this.getComponent(f).value(resolveObjectValue(field, this.rawdata));
                } else {
                    return resolveObjectValue(field, this.rawdata);
                }
            }        	
        },
        
        renderRow: function() {
            var row = [];
            
            for (var f=0; f<this.$parent.fieldsset.length; f++){
            	row[f] = this.getValue(f);
            }
                        
            return row;
        },
        
        getTemplate: function (colIndex) {
            var field = this.$parent.fieldsset[colIndex]["property"];
            
            if (this.$parent.isComponent(field)){
                return this.getComponent(colIndex).component(this.rawdata);
            }
            
            if (this.$parent.isRenderer(field)) {
                return this.getRenderer(colIndex).component(resolveObjectValue(field, this.rawdata));
            }
            
            return this.$parent.defaulttemplate;
        },
        
        onMouseOver : function (event) {
            if (this.selected) {
                return;
            }
            
            this.currentclassname = event.currentTarget.className;
            event.currentTarget.className += " " + this.$parent.rowoverclassname;
        },
        onMouseOut : function (event) {
            if (this.selected) {
                return;
            }
            
            event.currentTarget.className = this.currentclassname;
        },
        
        onClick : function (event) {
            this.selectRow(event);
        }
    },
    
    computed : {
        row: function() {
            return this.renderRow();
        }
    },
    
    template: '#grid-row-template'
})

let paginatorComponent = Vue.component('paginator', {
    props: {
        me:{
            type: Object,
            required: false
        },
         Pages: {
            type: Number,
            required: false
        },        
         recordsPerPage: {
            type: Number,
            required: false
        },
        
         currentPage: {
            type: Number,
            required: false
        },
        totalRecordsCount: {
            type: Number,
            required: false
        },
        someValue: {
            type: Number,
            required: false
        }
    },
    methods: {
         gotoFirstPage : function() {
             this.currentPage = 1;
         },
         gotoPreviousPage : function() {
            if (this.currentPage > 1) {
                 this.currentPage--;
            }             
         },
        
         gotoNextPage : function() {
             if (this.currentPage < this.Pages) {
                 this.currentPage++; 
            }
         },
         gotoLastPage : function() {
             this.currentPage = this.Pages;
         },
         setTotalRecordsCount : function(totalRecordsCount) {
             this.totalRecordsCount = totalRecordsCount;
         },
         setCurrentPage: function(currentPage) {
             this.currentPage = currentPage;
         }        
    },
    watch: {
        currentPage: function() {
            if (this.me) {
                this.me.currentPage = this.currentPage;
            }
        }
    },
    computed: {
        pages: function (){
            if (this.totalrecordscount == 0) {
                return 1;
            }

            if (this.totalrecordscount % this.recordsPerPage == 0) {
                return this.totalrecordscount / this.recordsPerPage;
            }

            return parseInt(this.totalrecordscount / this.recordsPerPage) + 1;            
        },
         
        pageBegin: function (){
            return ((this.currentPage - 1) * this.recordsPerPage) + 1;
        },
         
        pageEnd: function (){
            if (this.currentPage * this.recordsPerPage >= this.totalrecordscount) {
                return this.totalrecordscount;
            }

            return this.currentPage * this.recordsPerPage;            
        },
         
        totalrecordscount: function() {
        	return this.totalRecordsCount;
        }
        
        ,
        currentpage: function() {
        	return this.currentPage;
         }
         
    },
    template: '#paginator-template'
});


let Paginator = paginatorComponent.extend({
     created() {
         console.info("Paginator created");
         this.currentPage = 1;
     },
     props: {
        
     },
     computed: {
        
     },
     methods: {
         
     }
})

let grid = Vue.component('grid', {
    destroyed() {
        console.info("grid destroyed");
    },
    created() {
    	console.info("grid created");
    },
    
    mounted() {
    	console.info("grid mounted");
    },
    
    props: {
    	pgid: {
            type: String,
            required: false,
            default: 'paginator'
    	},    	
    	templateid: {
            type: String,
            required: false,
            default: '#grid-template'
    	},    	
    	sortTypes: {
            type: Object,
            required: false
    	},
    	pg: {
            type: Object,
            required: false
    	},
    	recordscount: {
            type: Number,
            required: false
    	},    	
        filter: {
            type: String,
            required: false
            },    	
        sorting:{
        type: Object,
        required: false
      },
      currentsortfield: {
          type: Object,
          required: false
      },
      pagingenabled : {
        type: Boolean,
        required: false
      },
      paging:{
        type: Object,
        required: false
      },
    fieldsset: {
        type: Array,
        required: false
    },
    dataset: {
        type: Array,
        required: false
    },
    rawdata: {
        type: Array,
        required: false
    },
    renderers: {
        type: Array,
        required: false
    },
    gridcomponents: {
        type: Array,
        required: false
    },
    rowoverclassname : {
      type: String,
      required: false
    },
    selectedrowclassname : {
      type: String,
      required: false
    },
    defaulttemplate: {
      type: Object,
      required: false
    }
  },
    
    watch: {
      filter: function (){
          if (this.pagingenabled) {
            this.pg.setCurrentPage(1);
          }
      }
    },
    methods: {
    	getSortTypes() {
    		return Object.freeze({ascending: "asc", descending:"desc"});
    	},
    	
    	createPaginator() {
            return new Paginator({
                propsData: {
                    recordsPerPage : this.paging.recordsPerPage
                }
            });
    	},
    	
    	getPaginator() {
    		return this.pg;
    	},
        getSortClass: function(field){
            if (field.sortable) {
                if (this.currentsortfield == null) {
                    return this.sorting.sortableClass;
                }
                
                if (field["property"] == this.currentsortfield["property"]){
                  if (this.currentsortfield["sortType"] == this.getSortTypes().ascending){
                    return this.sorting.sortAscClass;
                  } else {
                      return this.sorting.sortDescClass;
                  }
                } else {
                	return this.sorting.sortableClass;
                }
            }
            return "";
        },
        dataComparator: function(object1, object2) {
        	const sortProperty = this.currentsortfield["sortField"]?this.currentsortfield["sortField"]:this.currentsortfield["property"];
        	
            const value1 = resolveObjectValue(sortProperty, object1);
            const value2 = resolveObjectValue(sortProperty, object2);
            
            if (value2 > value1) {
                if (this.currentsortfield.sortType == this.getSortTypes().ascending){
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (this.currentsortfield.sortType == this.getSortTypes().ascending){
                    return 1;
                } else {
                    return -1;
                }
            }
            
            return 0;
        },
        sort: function (field) {
            if (field.sortable) {
                this.currentsortfield = {
                    property: field["property"],
                    sortType: this.getSortType(field),
                    title: field["title"],
                    comparatorAsc: field["comparatorAsc"],
                    comparatorDesc: field["comparatorDesc"],
                    sortField: field["sortField"]
                };
                
                gridInstance = new GridClass({
                	propsData: {
                		currentsortfield: this.currentsortfield              		
                	}
                });
                
                this.rawdata.splice(0, this.rawdata.length);
            }
        },
        getSortType: function (field) {
            if (this.currentsortfield == null) {
            	return this.getSortTypes().ascending;
            }
            
            if (field["property"] == this.currentsortfield["property"]){
              if (this.currentsortfield["sortType"] == this.getSortTypes().ascending){
                return this.getSortTypes().descending;
              }
            }
            return this.getSortTypes().ascending;
        },
        formatDataToCsv: function() {
            var grid = this;
            var csvData = "";

            this.rawdata.forEach (
                function (row, index){
                    grid.fieldsset.forEach (
                        function (field, fieldIndex) {
                            if (!grid.isRenderer(field) && !grid.isComponent(field)) {
                                csvData += resolveObjectValue(field["property"], row) + ",";
                            }
                        }
                    );
                    csvData = csvData.slice(0, csvData.length-1);
                    csvData += "\n";
                }
            );

            return csvData;
        },
        exportToCsv : function() {
            var csvString = this.formatDataToCsv();
            var blob = new Blob([csvString]);

            if (window.navigator.msSaveOrOpenBlob) { // IE hack; see http://msdn.microsoft.com/en-us/library/ie/hh779016.aspx
                window.navigator.msSaveBlob(blob, "data.csv");
            } else {
                var a = window.document.createElement("a");
                a.href = window.URL.createObjectURL(blob, {type: "text/plain"});
                a.download = "data.csv";
                document.body.appendChild(a);
                a.click();  // IE: "Access is denied"; see: https://connect.microsoft.com/IE/feedback/details/797361/ie-10-treats-blob-url-as-cross-origin-and-denies-access
                document.body.removeChild(a);
            }         
        },
    
        isRenderer: function (id){
        	if (!this.renderers) {
        		return false;
        	}
        	
            for (var i=0; i<this.renderers.length; i++){
                if (this.renderers[i]["id"] == id) {
                    return true;
                }
            }

            return false;
        },
    
        isComponent: function (id){
        	if (!this.gridcomponents) {
        		return false;
        	}
        	
            for (var i=0; i<this.gridcomponents.length; i++){
                if (this.gridcomponents[i]["id"] == id) {
                    return true;
                }
            }

            return false;
        },
    
        onClick : function (event) {

        },
    
        onselectRowListener: function(row){
            this.$emit("on-select-row", row);
        },
        
        hasFilter : function () {
            if (this.filter==null || this.filter==''){
                return false;
            }
            return true;
        },
        
        filterMatchInRow: function (row, i) {
        	let countMatchesInRow = 0;
        	let filterParts = this.filter.trim().split(' ');
        	
        	for (let i=0; i<row.length; i++) {
        		for (let j=0; j<filterParts.length; j++) {
	        		if (row[i] && row[i].toString().toUpperCase().indexOf(filterParts[j].toUpperCase()) > -1) {
	        			countMatchesInRow++;
	        		}
        		}
        	}
        	
        	return countMatchesInRow == filterParts.length;
        }
        ,
        renderRows : function() {
        	console.info("renderRows");
            this.rawdata = [];
            
            for (var i=0; i<this.dataset.length; i++){
                var row = [];

                for (var f=0; f<this.fieldsset.length; f++){
                    val = resolveObjectValue(this.fieldsset[f]["property"], this.dataset[i]);
                    row[f] = val;
                }

                if (this.hasFilter()) {
                    if (this.filterMatchInRow(row, i)){
                        this.rawdata.push(this.dataset[i]);
                    }
                } else {
                    this.rawdata.push(this.dataset[i]);
                }
            }
            
            if (this.pagingenabled) {
            	this.getPaginator().setTotalRecordsCount(this.rawdata.length);
            	//this.getPaginator(); //.setTotalRecordsCount(this.rawdata.length);
            	//console.info("data count: " + this.rawdata.length)
                
                console.info("Paginator: " + new String (this.getPaginator().totalrecordscount));
            }
            
            if (gridInstance) {
            	if (gridInstance.currentsortfield) {
            		this.currentsortfield = gridInstance.currentsortfield;
            	}
            }
            
            if (this.currentsortfield != null) {
            	if (this.currentsortfield.comparatorAsc && this.currentsortfield["sortType"] == this.getSortTypes().ascending) {
            		return this.rawdata.sort(this.currentsortfield.comparatorAsc);
            	}
            	
            	if (this.currentsortfield.comparatorDesc && this.currentsortfield["sortType"] == this.getSortTypes().descending) {
            		return this.rawdata.sort(this.currentsortfield.comparatorDesc);
            	}
            	
                return this.rawdata.sort(this.dataComparator);
            }
            
            return this.rawdata;
        }
    },
    
    computed : {
        recordscount : function () {
          if (this.rawdata == null) {
              return 0;
          }
            return this.rawdata.length;
        },
    
        rows: function() {
            if (!this.pagingenabled){
                return this.renderRows();
            }
            
            return this.renderRows().slice(this.pg.pageBegin-1, this.pg.pageEnd);
        },
        rawData: function() {
            return this.rawdata;
        },
        currentSortField : function() {
            return this.currentsortfield;
        }
    },
    data () {
      return {
      	sortTypes: null,
      	gridrow: null,
      	currentrow: null,
        templateId: 'grid-template'
      }  
    }
    ,
    template: '#grid-template'
})


//Hold state for re-rendering
let GridClass = grid.extend({
	props: {
	      currentsortfield: {
	          type: Object,
	          required: false
	      }/*,
        template:'#grid-template'
        */
	}
	/*,
	template:'#grid-template'
	*/
});



let gridInstance = null;