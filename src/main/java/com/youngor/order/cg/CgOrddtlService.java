package com.youngor.order.cg;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.controller.spring.mvc.MapParams;
import com.mawujun.exception.BusinessException;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Pager;
import com.youngor.permission.ShiroUtils;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CgOrddtlService extends AbstractService<CgOrddtl, com.youngor.order.cg.CgOrddtl.PK>{

	@Autowired
	private CgOrddtlRepository cgOrddtlRepository;
	@Autowired
	private CgOrddtRepository cgOrddtRepository;
	
	@Override
	public CgOrddtlRepository getRepository() {
		return cgOrddtlRepository;
	}
	//@Override
	public void create1(CgOrddtl cgOrddtl) {
		cgOrddtl.setRgdt(new Date());
		cgOrddtl.setRgsp(ShiroUtils.getUserId());
		cgOrddtl.setLmdt(new Date());
		cgOrddtl.setLmsp(ShiroUtils.getUserId());
		cgOrddtlRepository.createOrUpdate(cgOrddtl);
		
		//更新交货期为默认的交货期
		//同时更新交货期中的数量
		CgOrddt.PK cgOrddtPK = new CgOrddt.PK();
		cgOrddtPK.setOrcgno(cgOrddtl.getOrcgno());
		cgOrddtPK.setCgorno(cgOrddtl.getCgorno());
		cgOrddtPK.setSampno(cgOrddtl.getSampno());
		cgOrddtPK.setSuitno(cgOrddtl.getSuitno());

		CgOrddt cgOrddt = cgOrddtRepository.get(cgOrddtPK);
		if (cgOrddt != null) {
			//cgOrddt.setOrmtqt(cgOrddtl.getOrszqt());
			//cgOrddt.setPldate();
			cgOrddtRepository.update(cgOrddt);
		} else {
			cgOrddt=new CgOrddt();
			cgOrddt.setOrcgno(cgOrddtl.getOrcgno());
			cgOrddt.setCgorno(cgOrddtl.getCgorno());
			cgOrddt.setSampno(cgOrddtl.getSampno());
			cgOrddt.setSuitno(cgOrddtl.getSuitno());
			cgOrddtRepository.create(cgOrddt);
		}
		//更新成衣交货期
		cgOrddtlRepository.updatePldate(cgOrddtl.getSampno());
	}
	@Override
	public  void update(CgOrddtl cgOrddtl) {
		cgOrddtlRepository.update(cgOrddtl);
		
		//同时更新交货期中的数量
		CgOrddt.PK cgOrddtPK=new CgOrddt.PK();
		cgOrddtPK.setOrcgno(cgOrddtl.getOrcgno());
		cgOrddtPK.setCgorno(cgOrddtl.getCgorno());
		cgOrddtPK.setSampno(cgOrddtl.getSampno());
		cgOrddtPK.setSuitno(cgOrddtl.getSuitno());
		
		
		CgOrddt cgOrddt=cgOrddtRepository.get(cgOrddtPK);
		if(cgOrddt!=null){
			cgOrddt.setOrmtqt(cgOrddtl.getOrszqt());
			cgOrddtRepository.update(cgOrddt);
		}
		
	}
	public Pager<CgOrddt> queryPage4Insert(Pager<CgOrddt> pager) {
		return cgOrddtlRepository.queryPage4Insert(pager);
	}
//	@Override
//	public void delete(CgOrddtl cgOrddtl) {
//		//同时删除交货日期
//		CgOrddt cgOrddt=new CgOrddt();
//		cgOrddt.setOrcgno(cgOrddt.getOrcgno());
//		cgOrddt.setCgorno(cgOrddt.getCgorno());
//		cgOrddt.setSampno(cgOrddt.getSampno());
//		cgOrddt.setSuitno(cgOrddt.getSuitno());
//		
//		cgOrddtRepository.delete(cgOrddt);
//		
//		cgOrddtlRepository.delete(cgOrddtl);
//		
//		
//	}
	
	public void destroyBatch1(CgOrddtl.PK[] cgOrddtlPKes) {
		if(cgOrddtlPKes==null || cgOrddtlPKes.length==0){
			return;
		}
		for(CgOrddtl.PK pk:cgOrddtlPKes){
			CgOrddt.PK cgOrddtPK=new CgOrddt.PK();
			cgOrddtPK.setOrcgno(pk.getOrcgno());
			cgOrddtPK.setCgorno(pk.getCgorno());
			cgOrddtPK.setSampno(pk.getSampno());
			cgOrddtPK.setSuitno(pk.getSuitno());
			cgOrddtRepository.deleteById(cgOrddtPK);
			
			cgOrddtlRepository.deleteById(pk);
		}
	}
	/**
	 * 初始化所有的数量
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @param params
	 * @return
	 */
	public  List<CgOrddtlVO> initOrszqt(MapParams params)  {
		
		//清楚当前订货子批次下的所有数据，
		cgOrddtlRepository.delete4Page4Insert(params.getParams());
		
		//清楚所有的交货日期
		
		//重新插入所有的数据
		List<CgOrddtlVO> list=cgOrddtlRepository.queryPage4Insert(params.getParams());
		if(list!=null){
			for(CgOrddtlVO cgOrddtlVO:list){
				CgOrddtl cgOrddtl=new CgOrddtl();
				try {
					BeanUtils.copyProperties(cgOrddtl, cgOrddtlVO);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException("初始化失败!");
				}
				cgOrddtl.setOrszqt(cgOrddtlVO.getOrszqt_residue());
				create1(cgOrddtl);
			}
		}
		
		return list;
	}
	
	public  List<CgOrddtlVO> queryPage(Map<String,Object> params) {
		return cgOrddtlRepository.queryPage(params);
	}
}
