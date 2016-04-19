Ext.define('y.permission.RolePubCodeGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.pubcode.PubCode'
	],
	columnLines :true,
	stripeRows:true,
	selModel: {
		checkOnly :true,
        selType: 'checkboxmodel'
    },
   
	initComponent: function () {
      var me = this;
//      alert(me.tyno);
//      if(!me.tyno){
//      	alert("请先配置tyno属性。");
//      	return;
//      }
      me.columns=[
      	//{xtype: 'rownumberer'},
		{dataIndex:'itnm',header:'名称'
        }
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			model: 'y.permission.User',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/pubCode/query.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{
			    	limit:50,
			    	tyno:me.tyno
			    },
			    reader:{
					type:'json'	
				}
			}
	  });
	  me.dockedItems=[];

	  

	  
       
      me.callParent();
	},
	/**
	 * 选中选中的品牌和角色
	 * @param {} array
	 */
	checkSel:function(array){
		var me=this;
		var selModel=me.getSelectionModel( );
		selModel.deselectAll(true);
		
		var aaa={};
		for(var i=0;i<array.length;i++){
			aaa[array[i]]=true;
		}
		var all=me.getStore().getRange( ) ;
		var selArray=[];
		for(var i=0;i<all.length;i++){
			if(aaa[all[i].get("itno")]){
				selArray.push(all[i]);
			}
		}
		selModel.select(selArray,false,true);
	}
});
