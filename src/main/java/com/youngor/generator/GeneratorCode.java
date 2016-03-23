package com.youngor.generator;

import java.io.IOException;

import com.mawujun.generator.ExtenConfig;
import com.mawujun.generator.GeneratorService;
import com.youngor.permission.Menu;

import freemarker.template.TemplateException;
/**
 * 
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class GeneratorCode {
	static GeneratorService generatorService=new GeneratorService();

	public static void main(String[] args) throws TemplateException, IOException, ClassNotFoundException {	
		ExtenConfig aa=new ExtenConfig();
		aa.extjs_packagePrefix="y";
		aa.extjs_form_layoutColumns=2;
		generatorService.setExtenConfig(aa);
		
		generatorService.generatorAllFile(Menu.class);

	}
	


}
