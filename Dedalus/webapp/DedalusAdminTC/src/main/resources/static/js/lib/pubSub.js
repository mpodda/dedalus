/**
 * Publisher / Subscriber 
 * 
 * (c) Marcello Podda
 * 
 * v 1.1
 * 
 * 2018-07-04
 * 
 * */

let subUid = -1;

let PubSub = Vue.extend({
	data ()  {
		return {
			topics : {},
			actions: {}
		}
	},
	watch: {
		topics: function () {
			for (let t=0; t<this.topics.length; t++) {
				for (let a=0; a<this.actions.length; a++) {
					this.subscribe(this.topics[t], this.actions[a]);
				}
			}
		}
	},
	methods : {
		publish (topic, ...args) {
			if ( !this.topics[topic] ) {            
				return false;        
			}
			
			
			
			let subscribers = this.topics[topic];            
            let len = subscribers ? subscribers.length : 0;

            while (len--) {
            	//console.info(subscribers[len]);
            	subscribers[len].func(topic, ...args);
            }
            
            
            //console.info("publish");
            
            return true;
		},
		subscribe (topic, func) {
            if (!this.topics[topic]) {            
                this.topics[topic] = [];        
            }
            
            let token = (++subUid).toString();
            
            //console.info(func);
            
            this.topics[topic].push({
                token: token,            
                func: func        
            });
            
            return token;  			
		},
		
		unsubscribe (token) {
			
		}
	}
});