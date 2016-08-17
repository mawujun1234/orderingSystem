package com.youngor.generator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.mawujun.generator.ExtenConfig;
import com.mawujun.generator.GeneratorService;
import com.youngor.order.bw.BwOrdszdtl;

import freemarker.template.TemplateException;
/**
 * 
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class GeneratorCode {
	static GeneratorService generatorService=new GeneratorService();

	public static void main(String[] args) throws TemplateException, IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {	
		
		//这里弄成更加好用的方式，弄成级联的方式，例如getExtjsConfig.set。。。
		ExtenConfig aa=new ExtenConfig();
		aa.extjs_treeForm_model=false;
		aa.extjs_packagePrefix="y";
		aa.extjs_form_layoutColumns=-1;
		
		aa.extjs_grid_createDelUpd_button=true;
		aa.extjs_grid_enable_cellEditing=false;
		generatorService.setExtenConfig(aa);
		
		generatorService.generatorAllFile(BwOrdszdtl.class);

	}
	


}
