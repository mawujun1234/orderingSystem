package com.youngor.org;

/**
 * 渠道类型
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public enum Orgty {
	SHOP("门店"),AREA("片区"),BRCH("分公司"),REGN("区域"),COMP("公司"),DEPT("部门"),CK("仓库"),OTH("其它");
	
	private String name;
	
	Orgty(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
}
