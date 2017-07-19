package com.youngor.org;

public class NodeVO {
	
	private String id;//职位id或者orgno
	private String name;
	//private Boolean leaf;
	private String type;
	private String remark;
	
	private String orgno;//所在节点的组织单元，如果是组织单元就是自身的orgno
	
	private Boolean checked;//在查看职位可以访问的组织单元的时候用的
	
	private Boolean isleaf=null;
	
	public String getType_name(){
		if( "position".equals(this.getType())){
			return "职位";
		} else if(this.getType()!=null && !"".equals(this.getType())){
			return Orgty.valueOf(this.getType()).getName();
		}
		return "";
	}
//	public String getName_full() {
//		return name+"("+getType_name()+")";
//	}
	
	public String getIconCls(){
		if("position".equals(this.getType())){
			return "icon-group";
		} else {
			return "";
		}
	}
	
	public Boolean getLeaf(){
		if(isleaf!=null){
			return isleaf;
		}
		if( "position".equals(this.getType())){
			return true;
		} if( "SHOP".equals(this.getType())){
			return true;
		} else {
			return false;
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		//return name;
		return name+"("+getType_name()+")";
	}
	public void setName(String name) {
		this.name = name;
	}
//	public Boolean getLeaf() {
//		return leaf;
//	}
//	public void setLeaf(Boolean leaf) {
//		this.leaf = leaf;
//	}
	public String getOrgno() {
		return orgno;
	}
	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Boolean isleaf) {
		this.isleaf = isleaf;
	}

}
