const _initEvents = Symbol('initEvents');
const _createList = Symbol('createList');
const _addListItem = Symbol('addListItem');
const _clearList = Symbol('clearList');
const _isSpecialKey = Symbol('isSpecialKey');
const _hasValueFields = Symbol('hasValueFields');
const _hasTextField = Symbol('hasTextField');
const _filterData = Symbol('filterData');
const _selectArrowElement = Symbol('selectArrowElement');
const _showFullList = Symbol('showFullList');
const _showFilteredData = Symbol('showFilteredData');

const specialKeys = Object.freeze({
        BACKSPACE: 8,
    		SHIFT: 16,
    	      ALT: 18,
             CTRL: 17,
        CAPS_LOCK: 20,
           ESCAPE: 27,
            ENTER: 13,
            KEYUP: 38,
          KEYDOWN: 40,
          KEYLEFT: 37,
         KEYRIGHT: 39
    	});

const showStates = Object.freeze({
    INITIAL: 0,
       SHOW: 1,
       HIDE: 2,
    NEUTRAL: 3
});

function getNextState(state) {
    switch(state) {
        case showStates.INITIAL : return showStates.HIDE;
           case showStates.HIDE : return showStates.SHOW;
           case showStates.SHOW : return showStates.HIDE;
    }
}

class AutoComplete extends Component {
    constructor(id, element, elementPlaceHolder, data = null) {
        super(id, element);
        
        this._value = null;
        this._data = data;
        this._element = element;
        this._elementPlaceHolder = elementPlaceHolder;
        this._listElement = null;
        this._filteredData = [];
        
        this._valueFields = [];
        this._textField = null;
        
        this._showState = showStates.INITIAL;
        
        this.onValueSet = (value) => {};
        
        this[_initEvents]();
    }
    
    [_initEvents] () {
        //document.onselectstart = () => {return false};
        
        let arrowDownElement = this[_selectArrowElement] ();
        
        this._listElement = this[_createList] ();
        this._elementPlaceHolder.appendChild(this._listElement);
        
        /* Click on Arrow Element */
        if (arrowDownElement != null) {
            XBrowser.addHandler(arrowDownElement, "click", (e) => {
                this[_clearList] ();
                if ((this._showState == showStates.SHOW || this._showState == showStates.INITIAL) && this._data!=null) {
                    this[_showFullList] ();
                } else {
                    this._listElement.style.zIndex = "";
                    this._showState = getNextState(this._showState);
                }
            });
        }
        
        //console.info("this._element: ", this._element);
        
        XBrowser.addHandler(this._element, "keyup", (e) => {
            this._listElement.style.zIndex = "99";
            
            this._showState = showStates.INITIAL;
            
            //console.info("Key Code", e.keyCode);
            
            if (!this[_isSpecialKey](e.keyCode)) {
                this[_clearList] ();
                
                if (this._element.value.trim() != "" && this._data != null) {
                    this[_showFilteredData] ();
                } else {
                    this._listElement.style.zIndex = "";
                    this._value = null;
                    this.onValueSet(this._value);
                }
            } else {
                switch (e.keyCode) {
                    case specialKeys.ESCAPE: 
                        this[_clearList] ();
                        this._showState = showStates.INITIAL;
                    break;
                    case specialKeys.BACKSPACE:
                        
                        if (this._element.value.trim() == "") {
                            this[_clearList] ();
                            this[_showFullList] ();
                            
                            this._showState = showStates.INITIAL;
                        } else {
                            if (this._data != null) {
                                this[_clearList] ();
                                this[_showFilteredData] ();
                                
                                this._showState = getNextState(this._showState);
                            }
                        }
                    break;
                    default: break;
                }
            }
            
        });
    }
    
    [_showFullList] () {
        this._listElement.style.zIndex = "99";
        this._filteredData = [...this._data];
        this._filteredData.forEach((item, index) => {
            this[_addListItem] (item, index);
        });
        this._showState = getNextState(this._showState);
    }
    
    [_showFilteredData] () {
        this._filteredData = this[_filterData]();

        this._filteredData.forEach((item, index) => {
            this[_addListItem] (item, index);
        });
    }
    
    [_selectArrowElement] () {
        for (let i=0; i<this._elementPlaceHolder.childNodes.length; i++) {
            let child = this._elementPlaceHolder.childNodes[i];
            
            if (child.className && child.className.indexOf("arrowDownSpan") > -1) {
                return child;
            }
        }
        
        return null;
    }
    
    [_filterData] () {
        return this._data.filter((value, index, array) => {
            if (this[_hasValueFields] ()) {
                let filter = false;
                
                this._valueFields.forEach ((valueField, index2) => {
                	if (value[valueField] == null) {
                		filter = filter || false;
                	} else {
                		filter = filter || value[valueField].toUpperCase().indexOf(this._element.value.toUpperCase()) /*== 0*/ > -1;
                	}
                });
                
                return filter;
            }
            
            return value.indexOf(this._element.value) /*== 0*/ > -1;
        });        
    }
    
    [_isSpecialKey] (keyCode) {
        return Object.values(specialKeys).includes(keyCode);
    }
    
    [_clearList] () {
        if (this._listElement.childNodes.length > 0){
            for (let i = this._listElement.childNodes.length - 1;; i--){
                this._listElement.removeChild(this._listElement.childNodes[i]);
                if (i == 0) {
                    break;
                }
            }
        }
    }
    
    [_createList] () {
        let listPlaceHolder = document.createElement("DIV");
        listPlaceHolder.setAttribute("class", "autocomplete-items");
        
        return listPlaceHolder;
    }
    
    [_addListItem] (model, index) {
        let listItem = document.createElement("DIV");
        listItem.innerHTML = this[_hasTextField]()?model[this._textField]:model;
        
        XBrowser.addHandler(listItem, "click", (e) => {
            //console.info("Click On Item");
            this._element.value = listItem.innerHTML;
            this._value = this._filteredData[index];
            
            this[_clearList]();
            this._listElement.style.zIndex = "";
            this._showState = showStates.INITIAL;
            
            this.onValueSet(this._value);
        });
        
        this._listElement.appendChild(listItem);
    }
    
    [_hasValueFields] () {
        return this._valueFields.length > 0;
    }

    [_hasTextField] () {
        return this._textField != null;
    }

    addValueField(valueField) {
        this._valueFields.push(valueField);
    }
        
    getValue() {
        return this._value;
    }
    
    setValue(value) {
        this._value = value;
        
        if (this[_hasTextField]()) {
            this._element.value = this._value[this._textField];
        } else {
            this._element.value = value;
        }
        
        this.onValueSet(this._value);
    }
    
    set data (value) {
        this._data = value;
        this._value = null;
        this._element.value = "";
        this._showState = showStates.INITIAL;
        this[_clearList]();
        
        //this[_initEvents]();
    }
    
    get data() {
        return this._data;
    }
    
    set textField (value) {
        this._textField = value;
    }
    
    get textField () {
        return this._textField;
    }
}


// ---------------------------------------------------------------------------------------------------

class SelectComponent extends Component {
	constructor (selectElement) {
		super(selectElement.id, selectElement);
		
		this.element = selectElement;
	    this._dataList = null;
	    this._idProperty = null;
	}
	
    setValue(value) {
        if (this.dataList != null && value != null) {
            this.element.value = value[this.idProperty];
        } else {
           this.element.value = value;
        }

        
        //console.info("setValue=", value, " id=", this.id);
        //console.info(this.element);
        
//        this.element.value = 1;
    }

    getValue() {
        if (this.dataList != null) {
            return this.dataList[this.element.selectedIndex];
        }

        return this.element.value;
    }
    
    renderChoices(dataList, idProperty, valueProperty) {
        this.dataList = dataList;
        this.idProperty = idProperty;

        const selectComponent = this;
        
        this.dataList.forEach (
        	(dataItem, index) => {
        		selectComponent.addOption(dataItem[idProperty], dataItem[valueProperty]);
        	}
        )
    }

    clear() {
        const len = element.options.length;

        if (len == 0) {
            return;
        }

        for (var i = len; ; i--) {
            this.deleteOption(this.element.options[i - 1].value);
            if (i == 1) {
                return;
            }
        }
    }

    addOption(optValue, optText) {
        let option = new Option();

        option.value = optValue;
        option.text = optText;

        this.element[this.element.length] = option;
    }

    deleteOption(optValue) {
    	this.element.forEach (
    		(option, index) => {
                if (option.value == optValue) {
                    this.options[index] = null;
                }
                
                if (this.options.length > 0) {
                    this.options[0].selected = true;
                }
    		}
    	);
    }

    set dataList (dataList) {
    	this._dataList = dataList;
    }
    
    get dataList () {
    	return this._dataList;
    }
    
    set idProperty(idProperty) {
    	this._idProperty = idProperty;
    }
    
    get idProperty() {
    	return this._idProperty;
    }    
}

// ------------------------------------------------------------------

class RadioSet extends Component {
	constructor(id, elements) {
		super(id, null);
		
		this.elements = elements;
	}
	
    setValue(value) {
        for (var i = 0; i < this.elements.length; i++) {
            if (this.elements[i].value == value) {
            	this.elements[i].checked = true;
                return;
            }
        }
    }

    getValue() {
        for (var i = 0; i < this.elements.length; i++) {
            if (this.elements[i].checked) {
                return this.elements[i].value;
            }
        }
    }
}