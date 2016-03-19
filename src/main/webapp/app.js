Ext.onReady(function(){
	var tabpanel=Ext.create('Ext.tab.Panel', {
	   region: 'center',
	    activeTab: 0,
	    
	    //bodyPadding: 10,
	   // tabPosition: 'bottom',
	    items: [
	        {
	            title: 'Tab 1',
	            closable:true,
	            html : 'A simple tab'
	        },
	        {
	            title: 'Tab 2',
	            closable:true,
	            html : 'Another one'
	        }
	    ]
	});
	
	var store = Ext.create('Ext.data.TreeStore', {
	    root: {
	        expanded: true,
	        children: [
	            { text: 'detention', leaf: true },
	            { text: 'homework', expanded: true, children: [
	                { text: 'book report', leaf: true },
	                { text: 'algebra', leaf: true}
	            ] },
	            { text: 'buy lottery tickets', leaf: true }
	        ]
	    }
	});
	
	//延迟加载
	//http://examples.sencha.com/extjs/6.0.1/examples/classic/tree/locking-buffer-rendered-treegrid.js
	var tree=Ext.create('Ext.tree.Panel', {
	    title: '订货系统',
	    width: 200,
	    height: 200,
	    split: true,
	    region: 'west',
	    store: store,
	    rootVisible: false,
	     reserveScrollbar: true,
        collapsible: true,
        loadMask: true,
        useArrows: true,
	    listeners:{
	    	itemclick:function(view,record){
	    		
	    		var tabs=tabpanel.items;
	    		
	    		for(var i=0;i<tabs.length;i++){
	    			//alert(tabs.items[i].itemId);
	    			//alert(record.get("id"));
	    			var tab=tabs.items[i];
	    			if(tab.itemId==record.get("id")){
	    				tabpanel.setActiveTab(tab);
	    				return;
	    			}
	    		}
	    		
	    		tabpanel.setActiveTab(tabpanel.add({
	    			itemId:record.get("id"),
	    			title:record.get("text"),
	    			closable:true,
	    			reorderable:true
	    		}));
	    		
	    	}
	    }
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,tabpanel]
	});
});