/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('y.pubcode.PubCodeTypeTree', {
    extend: 'Ext.tree.Panel',
    requires:['y.pubcode.PubCodeType'],
    displayField:'tynm',
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'parent_id',//传递到后台的数据，默认是node
	       	model:'y.pubcode.PubCodeType',
			root: {
			    expanded: true,
			    tyno:'root',
			    tynm:"属性类型" 
			}
		});
		me.initAction();
       
		me.callParent(arguments);
    },

    initAction:function(){
     	var me = this;
     	var actions=[];
     	
     	actions.push({
		        fieldLabel: '品牌',
		        itemId: 'bradno',
		        labelWidth:40,
		        width:160,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            //value:'Y',
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1',
		        listeners:{
		        	select:function( combo, record, eOpts ) {
		        		var root=me.getRootNode()
		        		root.set("tynm",record.get("itnm"));
		        		root.commit();
		        		me.getStore().getProxy().extraParams={
		        			bradno:record.get("itno")
		        		};
		        		me.pubCodeGrid.mask();
		        		me.getStore().reload();
//		        		var toolbar=combo.up("toolbar");
//		        		var suitno=toolbar.down("#suitno");
//		        		suitno.changeBradno(record.get("itno"));
//		        		suitno.getStore().reload();
		        	}	
		        }
		});
		
//		var reload = new Ext.Action({
//		    text: '查询',
//		    itemId:'reload',
//		    handler: function(){
//		    	me.onReload();
//		    },
//		    iconCls: 'icon-refresh'
//		});
//		//me.addAction(reload);
//		actions.push(reload);
		
		me.dockedItems=[{
	        xtype: 'toolbar',
	        dock: 'top',
	        items: actions
	    }]

//		var menu=Ext.create('Ext.menu.Menu', {
//			items: actions
//		});	
//		me.on('itemcontextmenu',function(tree,record,item,index,e){
//			menu.showAt(e.getXY());
//			e.stopEvent();
//		});
//		me.contextMenu=menu;
		
    },
   
    onReload:function(node){
    	var me=this;
    	var parent=node||me.getSelectionModel( ).getLastSelected( );
		if(parent){
		    me.getStore().reload({node:parent});
		} else {
		    me.getStore().reload();	
		}      
    },
   
    
    
    getLastSelected:function(){
    	return this.getSelectionModel( ).getLastSelected( );
    }
    
});
