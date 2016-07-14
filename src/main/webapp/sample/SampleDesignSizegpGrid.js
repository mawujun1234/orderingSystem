Ext.define('y.sample.SampleDesignSizegpGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.sample.SampleDesignSizegp'
	],
	columnLines :true,
	stripeRows:true,
	
	initComponent: function () {
      var me = this;
      
      me.sizegp_editor_store=Ext.create('Ext.data.Store', {
			    	autoLoad:false,
				    fields: ['sizeno', 'sizenm'],
				    proxy:{
				    	type:'ajax',
				    	//extraParams:{szbrad:'sjs'},
				    	url:Ext.ContextPath+'/pubSize/queryPRDSZFW4SampleDesign.do'
				    }
				}  );
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'suitno_name',header:'套件',width:60
        },
		{dataIndex:'sizegp',header:'规格范围',flex:1
			,editor: {
		        //fieldLabel: '规格范围',
		       // name: 'sizegp',
//	            allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"规格系列不允许为空",
	            selectOnFocus:true,
		        xtype:'combobox',
		        queryMode: 'local',
				editable:true,
		        selectOnFocus:true,
				forceSelection:true,
			    displayField: 'sizenm',
			    valueField: 'sizeno',
			    store:me.sizegp_editor_store
		    }
			,renderer: function(val,metaData,record ,rowIndex ,colIndex ,store ){
				if(!val){
					return val;
				}
				var combobox_store=me.sizegp_editor_store;
				//console.log(val);
	            var record = combobox_store.findRecord('sizeno',val,0,false,false,true); 
	           // console.log(record.get("sizeno")+"----");
	            if (record != null){
	                return record.get("sizenm"); 
	            } else {
	            	return null;
	                //return combobox_store.getAt(0).get("sizenm");
	            }
            }

        }
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.sample.SampleDesignSizegp',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/sampleDesignSizegp/queryAll.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });

	  me.dockedItems=[];
      
	   this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1 ,
            listeners:{
//            	beforeedit:function( editor, context, eOpts ) {
//            		var record=context.record;
//            		//标准套件不准修改
//            		if(record.get("suitno")=='T00'){
//            			return false;
//            		} else {
//            			return true;
//            		}
//            		
//            	}   
            	edit:function(editor , context ){
            		var value=context.value;
            		var grid=context.grid;
            		var store=grid.getStore();
            		var records=store.getRange ();
            		for(var i=0;i<records.length;i++){
            			if(records[i].get("suitno_name")=="上衣"){
            				records[i].set("sizegp",value);
            			}
            		}
            	}
            }       
      });  
	  this.plugins = [this.cellEditing];
	  
      me.callParent();
	},
	reloadEditor:function(ormtno,bradno,spclno){
		
		//var sizegpField=this.sizegp_editor;
			this.sizegp_editor_store.getProxy().extraParams={
				ormtno:ormtno,
				szbrad:bradno,
				szclno:spclno
			};
			this.sizegp_editor_store.reload();
	}
});
