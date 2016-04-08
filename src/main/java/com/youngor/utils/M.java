package com.youngor.utils;
public final class M {
public static final class Ordmt {
	public static final String ormtno="ormtno";
	public static final String ormtnm="ormtnm";
	public static final String ormtsn="ormtsn";
	public static final String pryear="pryear";
	public static final String mtstdt="mtstdt";
	public static final String mtfidt="mtfidt";
	public static final String ormtst="ormtst";
	public static final String ormtfg="ormtfg";
	public static final String ormtmk="ormtmk";
	public static final String mtrgsp="mtrgsp";
	public static final String mtrgdt="mtrgdt";
	public static final String mtlmsp="mtlmsp";
	public static final String mtlmdt="mtlmdt";
}
public static final class OrdmtScde {
	public static final String ormtno="ormtno";
	public static final String orgty="orgty";
	public static final String mtstdt="mtstdt";
	public static final String mtfidt="mtfidt";
	public static final String mtsttm="mtsttm";
	public static final String mtfitm="mtfitm";
	public static final String mtlmsp="mtlmsp";
	public static final String mtlmdt="mtlmdt";
}
public static final class OrdmtSeason {
	public static final String ormtno="ormtno";
	public static final String seasno="seasno";
	public static final String mtlmsp="mtlmsp";
	public static final String mtlmdt="mtlmdt";
}
public static final class Org {
	public static final String orgno="orgno";
	public static final String orgnm="orgnm";
	public static final String orgsn="orgsn";
	public static final String orgty="orgty";
	public static final String chancl="chancl";
	public static final String orgst="orgst";
}
public static final class OrgOrg {
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class parent {
		public static final String orgno="parent.orgno";
		public static final String orgnm="parent.orgnm";
		public static final String orgsn="parent.orgsn";
		public static final String orgty="parent.orgty";
		public static final String chancl="parent.chancl";
		public static final String orgst="parent.orgst";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "parent";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class child {
		public static final String orgno="child.orgno";
		public static final String orgnm="child.orgnm";
		public static final String orgsn="child.orgsn";
		public static final String orgty="child.orgty";
		public static final String chancl="child.chancl";
		public static final String orgst="child.orgst";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "child";
	    }
	}
	public static final String dim="dim";
}
public static final class Position {
	public static final String id="id";
	public static final String name="name";
	public static final String remark="remark";
	public static final String orgno="orgno";
}
public static final class PositionOrgUser {
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class position {
		public static final String id="position.id";
		public static final String name="position.name";
		public static final String remark="position.remark";
		public static final String orgno="position.orgno";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "position";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class org {
		public static final String orgno="org.orgno";
		public static final String orgnm="org.orgnm";
		public static final String orgsn="org.orgsn";
		public static final String orgty="org.orgty";
		public static final String chancl="org.chancl";
		public static final String orgst="org.orgst";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "org";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class user {
		public static final String id="user.id";
		public static final String name="user.name";
		public static final String loginName="user.loginName";
		public static final String pwd="user.pwd";
		public static final String remark="user.remark";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "user";
	    }
	}
}
public static final class Menu {
	public static final String id="id";
	public static final String code="code";
	public static final String name="name";
	public static final String url="url";
	public static final String menuType="menuType";
	public static final String parent_id="parent_id";
	public static final String remark="remark";
}
public static final class Role {
	public static final String id="id";
	public static final String name="name";
	public static final String roleType="roleType";
	public static final String remark="remark";
	public static final String parent_id="parent_id";
}
public static final class RoleMenu {
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class menu {
		public static final String id="menu.id";
		public static final String code="menu.code";
		public static final String name="menu.name";
		public static final String url="menu.url";
		public static final String menuType="menu.menuType";
		public static final String parent_id="menu.parent_id";
		public static final String remark="menu.remark";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "menu";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class role {
		public static final String id="role.id";
		public static final String name="role.name";
		public static final String roleType="role.roleType";
		public static final String remark="role.remark";
		public static final String parent_id="role.parent_id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "role";
	    }
	}
}
public static final class RoleUser {
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class user {
		public static final String id="user.id";
		public static final String name="user.name";
		public static final String loginName="user.loginName";
		public static final String pwd="user.pwd";
		public static final String remark="user.remark";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "user";
	    }
	}
	 /**
	 * 返回关联对象的属性，，以对象关联的方式(a.b这种形式)，只有一些基本属性，层级不再往下了
	 */
	public static final class role {
		public static final String id="role.id";
		public static final String name="role.name";
		public static final String roleType="role.roleType";
		public static final String remark="role.remark";
		public static final String parent_id="role.parent_id";
			
	    /**
	    * 返回的是关联类的属性名称，主要用于属性过滤的时候
	    */
	    public static String name(){ 
		    return "role";
	    }
	}
}
public static final class User {
	public static final String id="id";
	public static final String name="name";
	public static final String loginName="loginName";
	public static final String pwd="pwd";
	public static final String remark="remark";
}
public static final class PubCode {
	public static final String itno="itno";
	public static final String tyno="tyno";
	public static final String fitno="fitno";
	public static final String itnm="itnm";
	public static final String itms="itms";
	public static final String itmk="itmk";
	public static final String itso="itso";
	public static final String itst="itst";
	public static final String stat="stat";
	public static final String rgsp="rgsp";
	public static final String rgdt="rgdt";
	public static final String lmsp="lmsp";
	public static final String lmdt="lmdt";
}
public static final class PubCodeType {
	public static final String tyno="tyno";
	public static final String tynm="tynm";
	public static final String ftyno="ftyno";
	public static final String tyms="tyms";
	public static final String tymk="tymk";
	public static final String tyst="tyst";
	public static final String rgsp="rgsp";
	public static final String rgdt="rgdt";
	public static final String lmsp="lmsp";
	public static final String lmdt="lmdt";
}
}
