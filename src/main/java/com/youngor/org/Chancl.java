package com.youngor.org;

/**
 * 组织类型
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public enum Chancl {
	ZY("自营"),SC("商场"),TX("特许"),WX("网销")
	,GSBB("公司本部"),YXGS("营销公司"),FGS("分公司"),QY("区域"),PQ("片区"),FS("服饰"),CK("仓库"),OTH("其他");

	private String name;
	
	Chancl(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
}
