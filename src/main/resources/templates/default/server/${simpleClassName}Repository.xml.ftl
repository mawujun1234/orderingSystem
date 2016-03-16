
<#assign simpleClassNameFirstLower = simpleClassName?uncap_first>   
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//ibatis.apache.org//DTD Mapper 3.0//EN"
"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd">

<#macro mapperEl value>${r"#{"}${value}}</#macro>
<#macro printDollar value>${r"${"}${value}}</#macro>
<#macro namespace>${basepackage}</#macro>
<!-- mawujun qq:16064988 e-mail:16064988@qq.com-->
<mapper namespace="<@namespace/>.${simpleClassName}Repository">

	
</mapper>

