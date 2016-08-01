package com.youngor.sample;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface SampleProdRepository extends IRepository<SampleProd, com.youngor.sample.SampleProd.PK>{
	public  List<SampleProdVO> queryPage( Map<String,Object> params);

}
