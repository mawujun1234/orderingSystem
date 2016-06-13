Ext.define('y.order.QyVONewForm',{
	extend:'Ext.form.Panel',
	requires: [
	     //'y.ordmt.Ordmt'
	],
	
    frame: true,
    autoScroll : true,
	buttonAlign : 'center',
    bodyPadding: '5 5 0',

	width:530,
    height:500,
    defaults: {
        msgTarget: 'under',
        labelWidth: 75,
        labelAlign:'right',
        anchor: '90%'
    },
	initComponent: function () {
       var me = this;
      
       var suigrid=this.createSuitGrid();
       var fieldset={
	        // Fieldset in Column 1 - collapsible via toggle button
	        xtype:'fieldset',
	        //columnWidth: 0.5,
	        title: '套件数量',
	        itemId:'suigrid_fieldset',
	        collapsible: true,
	       
	        //defaultType: 'textfield',
	        defaults: {anchor: '100%'},
	        layout: 'anchor',
	        items :[suigrid]
	      }
       // console.log(1);
       //me.unmask();
       me.items= [{
				fieldLabel: '订货单位',
				labelWidth:65,
				width:170,
//				allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"订货单位不允许为空",
				name:'ordorg',
				itemId: 'ordorg',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'orgnm',
			    valueField: 'orgno',
			    store:Ext.data.StoreManager.lookup('ordorg_storeId'),
	            hidden:false,
	            value:me.params.ordorg,
				xtype:'combobox'
			 },
		{
	        fieldLabel: '样衣编号',
	        name: 'sampnm',
            allowBlank: false,
            //editable:false,
            emptyText:'按回车键刷新套件信息',
            afterLabelTextTpl: Ext.required,
            blankText:"样衣编号不允许为空",
	        xtype:'textfield',
	        enableKeyEvents:true,
	        listeners:{
//	        	blur:function(field){
//	        		suigrid.getStore().reload({params:{
//	        			ormtno:me.params.ormtno,
//	        			sampnm:field.getValue()
//	        		}});
//	        	},
	        	keypress:function( field, e, eOpts ){
	        		if(e.getKey( ) ==e.ENTER){
	        			suigrid.getStore().reload({params:{
		        			ormtno:me.params.ormtno,
		        			sampnm:field.getValue()
		        		}});
	        		}
	        	}
	        }
	    },
	    fieldset
	  ];   
	  
	  
	  this.buttons = [];
		this.buttons.push({
			text : '保存',
			itemId : 'save',
			formBind: true, //only enabled once the form is valid
       		disabled: true,
			glyph : 0xf0c7,
			handler : function(button){
				//alert("新增给哪个订货单位");
				//return;
				//var formpanel = button.up('form');
				var params=me.params;
				var records=suigrid.getStore().getRange();
				var jsonData=[];
				for(var i=0;i<records.length;i++){
					//params["orddtles["+i+"].sampno"]=records[i].get("sampno");
					//params["orddtles["+i+"].suitno"]=records[i].get("suitno");
					//params["orddtles["+i+"].ormtqt"]=records[i].get("ormtqt");
					jsonData.push({
						sampno:records[i].get("sampno"),
						suitno:records[i].get("suitno"),
						ormtqt:records[i].get("ormtqt")
					});
				}
				var ordorg_field=button.up("form").getForm().findField("ordorg");
				params.ordorg=ordorg_field.getValue();
				Ext.Ajax.request({
					url:Ext.ContextPath+'/ord/qyVO/createNew.do',
					params:params,
					jsonData:jsonData,
					method:'POST',
					success:function(response){
						//console.log(response.responseText);
						var obj=Ext.decode(response.responseText);
						//console.log(obj);
						//alert(obj.success);
						if(obj.success==false){
							Ext.Msg.alert("消息",obj.msg);
							return;
						}
						//alert(1);
						Ext.Msg.alert("消息","保存成功");
						//alert(2);
						button.up('window').close();
						//alert(3);
					}
				});
				
			}
			},{
				text : '关闭',
				itemId : 'close',
				glyph : 0xf00d,
				handler : function(button){
					button.up('window').close();
				}
	    });
      me.callParent();
	},
	createSuitGrid:function(){
    	var store=Ext.create('Ext.data.Store', {
		    fields:[ 'sampno','suitno', 'suitnm', 'ormtqt'],
		    proxy:{
		    	autoLoad:false,
				type: 'ajax',
			    url : Ext.ContextPath+'/ord/qyVO/querySuitBySampnm.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json'
//					rootProperty:'root',
//					successProperty:'success',
//					totalProperty:'total'		
				}
				
			},
			listeners:{
					load:function(store,recordes){
						if(!recordes || recordes.length==0){
							Ext.Msg.alert("消息","该样衣可能没设置套件!");
							return;
						}
					}
				}
		});
		var cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      	});  
		var grid=Ext.create('Ext.grid.Panel', {
		    store: store,
		    columns: [
		        { text: '套件', dataIndex: 'suitnm' },
		        { text: '数量', dataIndex: 'ormtqt', flex: 1,
		        renderer:function(value, metaData, record, rowIndex, colIndex, store){
					metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	 return value;
	            },editor: {
	                xtype: 'numberfield',
	                allowDecimals:false,
	                selectOnFocus:true 
	            } }
		    ],
		    plugins: cellEditing
		    
		});
		return grid;
    
    }
});
