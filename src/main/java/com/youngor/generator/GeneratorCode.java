package com.youngor.generator;

import java.io.IOException;

import com.mawujun.generator.GeneratorService;
import com.youngor.base.OrderMeet;

import freemarker.template.TemplateException;
/**
 * 
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class GeneratorCode {
	static GeneratorService generatorService=new GeneratorService();

	public static void main(String[] args) throws TemplateException, IOException, ClassNotFoundException {	
		generatorService.generatorAllFile(OrderMeet.class);

	}
	


}
