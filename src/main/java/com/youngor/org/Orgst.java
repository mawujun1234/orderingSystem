package com.youngor.org;

/**
 * 组织节点状态
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public enum Orgst {

	GB("关闭"),DKQ("待开启"),KQ("开启");
	
	private String name;
	Orgst(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
}
