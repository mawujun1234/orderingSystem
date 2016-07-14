Ext.define('y.sample.SamplePhotoShow', {
	extend : 'Ext.panel.Panel',
	requires : ['y.sample.SamplePhoto'],

	frame : true,
	autoScroll : true,
	buttonAlign : 'center',
	bodyPadding : '5 5 0',

	

	initComponent : function() {
		var me = this;
		me.store = Ext.create('Ext.data.Store', {
			autoLoad:false,
	        model: 'y.sample.SamplePhoto',
	        proxy: {
	            type: 'ajax',
	            url: Ext.ContextPath+'/samplePhoto/query.do',
	            reader: {
	                type: 'json'
	                //rootProperty: 'images'
	            }
	        },

	        listeners:{
//	        	load:function(store){
//	        		me.down("#samplePhotoView").render();
//	        	}
	        }
	    });
	    //store.load();
    
		me.items = Ext.create('Ext.view.View', {
			store : me.store,
			//itemId:'samplePhotoView',
			//height:500,
//			tpl : [
//					'<tpl for=".">',
//					'<div class="thumb-wrap" id="{id}">',
//					'<div class="thumb"><img src="{imgnm}" title="{photms}"></div>',
//					'<span class="x-editable">{photnm}</span>',
//					'</div>', '</tpl>', '<div class="x-clear"></div>'],
			tpl:['<tpl for=".">',
			        '<div style="margin-bottom: 10px;" class="thumb-wrap">',
			          '<img src="{imgnm}" width="100%" />',
			          '<br/><span>{photnm}</span>',
			        '</div>',
			    '</tpl>'],
			multiSelect : false,
			//height : 310,
			trackOver : true,
			overItemCls : 'x-item-over',
			itemSelector : 'div.thumb-wrap',
			emptyText : 'No images to display',
//			plugins : [Ext.create('Ext.ux.DataView.DragSelector', {}),
//					Ext.create('Ext.ux.DataView.LabelEditor', {
//								dataIndex : 'name'
//							})],
			prepareData : function(data) {
//				Ext.apply(data, {
//							shortName : Ext.util.Format.ellipsis(data.name, 15),
//							sizeString : Ext.util.Format.fileSize(data.size),
//							dateString : Ext.util.Format.date(data.lastmod,
//									"m/d/Y g:i a")
//						});
				return data;
			},
			listeners : {
				selectionchange : function(dv, nodes) {
//					var l = nodes.length, s = l !== 1 ? 's' : '';
//					this.up('panel').setTitle('Simple DataView (' + l + ' item' + s
//							+ ' selected)');
					me.dv_nodes=nodes;
				}
			}
		});
		
		
	   me.dockedItems=[];

	  
	   me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '新增',
				itemId:'create',
				hidden:!Permision.canShow('sample_design_photo_create'),
				handler: function(btn){
					me.onCreate();
				},
				iconCls: 'icon-plus'
			},{
			    text: '删除',
			    itemId:'destroy',
			    hidden:!Permision.canShow('sample_design_photo_destroy'),
			    handler: function(){
			    	me.onDelete();    
			    },
			    iconCls: 'icon-trash'
			}]
		});

		me.callParent();
	},
	onCreate:function(){
		var me=this;
		if(!window.sampno){
			Ext.Msg.alert("消息","请先先保存'设计开发'中的信息!");
			return;
		}
		var child=Ext.create('y.sample.SamplePhoto',{
			sampno:window.sampno.sampno,
			ormtno:window.ordmt_record.get("ormtno")
		});
		child.set("id",null);
		
		var formpanel=Ext.create('y.sample.SamplePhotoForm',{
			region:'west',
			 width: 240
		});
		formpanel.loadRecord(child);
		//预览图片
		var prevImage = Ext.create('Ext.Img', {
			itemId:'prevImage',
		    src: '',
		   
		    style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',
		    region:"center"
		});
		//formpanel.prevImage=prevImage;
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'border',
    		title:'新增',
    		modal:true,
    		width:430,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel,prevImage],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
	},
	onDelete:function(){
		if(this.dv_nodes){
			this.dv_nodes[0].erase({
				success:function(){
					Ext.Msg.alert("消息","删除成功!");
				}
			});
//			Ext.Ajax.request({
//				url:Ext.ContextPath+'/samplePhoto/destroy.do',
//				
//				
//			});
		}
	},
	getStore:function(){
		return this.store;
	}
});
