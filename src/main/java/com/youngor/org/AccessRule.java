package com.youngor.org;

/**
 * 访问组织节点的类型，是全部可以访问 还是 部分可以访问
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public enum AccessRule {

	all_org("全部"),this_org("所在组织单元"),parent_org("开启");
	
	private String name;
	AccessRule(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
}
