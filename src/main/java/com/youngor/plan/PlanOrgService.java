package com.youngor.plan;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.bean.BeanUtils;
import com.youngor.org.Chancl;
import com.youngor.org.Org;
import com.youngor.org.OrgService;
import com.youngor.permission.ShiroUtils;
import com.youngor.pubcode.PubCode;
import com.youngor.pubcode.PubCodeCache;
import com.youngor.utils.ContextUtils;
import com.youngor.utils.M;
import com.youngor.utils.MapParams;


/**
 * @author mawujun qq:16064988 e-mail:mawujun1234@163.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PlanOrgService extends AbstractService<PlanOrg, String>{

	@Autowired
	private PlanOrgRepository planOrgRepository;
	@Autowired
	private PlanOrgdtlRepository planOrgdtlRepository;
	@Autowired
	private OrgService orgServcie;
	
	@Override
	public PlanOrgRepository getRepository() {
		return planOrgRepository;
	}
	
	public List<PlanOrgdtlVO> queryPlanOrgdtlVO(MapParams params) {
		List<PlanOrgdtlVO> list= planOrgRepository.queryPlanOrgdtlVO(params.getParams());
		List<PlanOrgdtlVO> list_new= new ArrayList<PlanOrgdtlVO>();
		//计算合计和小计
		
		
		
		//String subtotal_sptyno_temp=null;
		PlanOrgdtlVO subtotal_sptyno=new PlanOrgdtlVO();//小类
		PlanOrgdtlVO subtotal_spclno=new PlanOrgdtlVO();//大类
		PlanOrgdtlVO subtotal_all=new PlanOrgdtlVO();//合计
		for(PlanOrgdtlVO planOrgdtlVO:list){
			list_new.add(planOrgdtlVO);
			//添加小类合计
			if(!planOrgdtlVO.getSptyno().equals(subtotal_sptyno.getSptyno())){
				//排除把小计添加在第一行
				if(subtotal_sptyno.getOrdorg()!=null){
					list_new.add(subtotal_sptyno);
				}
				subtotal_sptyno=new PlanOrgdtlVO();
				//subtotal_sptyno.setOrdorg("notedit");
				subtotal_sptyno.setSptyno(planOrgdtlVO.getSptyno());
				subtotal_sptyno.setSptynm("小计:");
				subtotal_sptyno.setIsTotal(true);
			}
			
			subtotal_sptyno.addQymtqt(planOrgdtlVO.getQymtqt());
			subtotal_sptyno.addQymtam(planOrgdtlVO.getQymtam());
			subtotal_sptyno.addTxmtqt(planOrgdtlVO.getTxmtqt());
			subtotal_sptyno.addTxmtam(planOrgdtlVO.getTxmtam());
			//添加大类小计
			if(!planOrgdtlVO.getSpclno().equals(subtotal_spclno.getSpclno())){
				//排除把小计添加在第一行
				if(subtotal_spclno.getOrdorg()!=null){
					list_new.add(subtotal_spclno);
				}
				subtotal_spclno=new PlanOrgdtlVO();
				//subtotal_spclno.setOrdorg("notedit");
				subtotal_spclno.setSpclno(planOrgdtlVO.getSpclno());
				subtotal_spclno.setSpclnm("小计:");
				subtotal_spclno.setIsTotal(true);
			}
			
			subtotal_spclno.addQymtqt(planOrgdtlVO.getQymtqt());
			subtotal_spclno.addQymtam(planOrgdtlVO.getQymtam());
			subtotal_spclno.addTxmtqt(planOrgdtlVO.getTxmtqt());
			subtotal_spclno.addTxmtam(planOrgdtlVO.getTxmtam());
			if(!planOrgdtlVO.getOrdorg().equals(subtotal_all.getOrdorg())){
				//排除把小计添加在第一行
				if(subtotal_all.getOrdorg()!=null){
					list_new.add(subtotal_all);
				}
				subtotal_all=new PlanOrgdtlVO();
				subtotal_all.setOrdorg(planOrgdtlVO.getOrdorg());
				subtotal_all.setOrgnm("合计:");
				subtotal_all.setIsTotal(true);
			}
			
			subtotal_all.addQymtqt(planOrgdtlVO.getQymtqt());
			subtotal_all.addQymtam(planOrgdtlVO.getQymtam());
			subtotal_all.addTxmtqt(planOrgdtlVO.getTxmtqt());
			subtotal_all.addTxmtam(planOrgdtlVO.getTxmtam());
		}
		list_new.add(subtotal_sptyno);
		list_new.add(subtotal_spclno);
		list_new.add(subtotal_all);
		return list_new;
	}

	public String getPlorno(String ormtno,String ordorg,String bradno){
		return ormtno+"_"+ordorg+"_"+bradno;
	}
	public  void update(PlanOrgdtlVO planOrgdtlVO) {
		String plorno=getPlorno(planOrgdtlVO.getOrmtno(),planOrgdtlVO.getOrdorg(),planOrgdtlVO.getBradno());//planOrgdtlVO.getOrmtno()+"_"+planOrgdtlVO.getOrdorg()+"_"+planOrgdtlVO.getBradno();
		if(planOrgRepository.get(plorno)==null) {
			PlanOrg planOrg=new PlanOrg();
			planOrg.setPlorno(plorno);
			planOrg.setOrmtno(planOrgdtlVO.getOrmtno());
			planOrg.setOrdorg(planOrgdtlVO.getOrdorg());
			planOrg.setBradno(planOrgdtlVO.getBradno());
			planOrg.setPlstat(0);
			planOrgRepository.create(planOrg);
			
		}
		PlanOrgdtl planOrgdtl=BeanUtils.copyOrCast(planOrgdtlVO, PlanOrgdtl.class);
		planOrgdtl.setPlorno(plorno);
		planOrgdtlRepository.createOrUpdate(planOrgdtl);
		
		
	}
	
	public  void onPass(String ormtno, String ordorg,String bradno) {
		//如果状态是编辑中，但是不是当前组织单元 就不能提交审批
		if(!ShiroUtils.getAuthenticationInfo().inTheOrg(ordorg)){
			throw new BusinessException("你没有权限进行提交!");
		}
		
		String plorno=ormtno+"_"+ordorg+"_"+bradno;
		PlanOrg planOrg=planOrgRepository.get(plorno);
		if(planOrg.getPlstat()==3){
			return;
		}
		if(ShiroUtils.getAuthenticationInfo().inTheOrg(ordorg)  && planOrg.getPlstat()!=0){
			throw new BusinessException("已经提交过了，你没有权限进行提交!");
		}
		planOrg.setPlstat(planOrg.getPlstat()+1);
		planOrgRepository.update(planOrg);
	}
	
	public  void onBack(String ormtno, String ordorg,String bradno) {
		//如果状态是编辑中，但是不是当前组织单元 就不能提交审批
		if(ShiroUtils.getAuthenticationInfo().inTheOrg(ordorg)){
			throw new BusinessException("你没有权限进行退回!");
		}
		
		String plorno=ormtno+"_"+ordorg+"_"+bradno;
		PlanOrg planOrg=planOrgRepository.get(plorno);
		if(planOrg.getPlstat()==0){
			return;
		}
		planOrg.setPlstat(planOrg.getPlstat()-1);
		planOrgRepository.update(planOrg);
	}
	
	public void onimport(MultipartFile imageFile) throws IOException, EncryptedDocumentException, InvalidFormatException {
		// 获取订货会编号
		String ormtno = ContextUtils.getFirstOrdmt().getOrmtno();
		//准备品牌的cache
		Map<String,PubCode> map=PubCodeCache.cache.get("1");
		Map<String,PubCode> brandno_map=new HashMap<String,PubCode>();//换成以名称作为key的map
		for(Entry<String,PubCode> entry:map.entrySet()) {
			brandno_map.put(entry.getValue().getItnm(), entry.getValue());
		}
		map=PubCodeCache.cache.get("0");
		Map<String,PubCode> spclno_map=new HashMap<String,PubCode>();
		for(Entry<String,PubCode> entry:map.entrySet()) {
			spclno_map.put(entry.getValue().getItnm(), entry.getValue());
		}
		map=PubCodeCache.cache.get("2");
		Map<String,PubCode> sptyno_map=new HashMap<String,PubCode>();
		for(Entry<String,PubCode> entry:map.entrySet()) {
			sptyno_map.put(entry.getValue().getItnm(), entry.getValue());
		}
		map=PubCodeCache.cache.get("5");
		Map<String,PubCode> spseno_map=new HashMap<String,PubCode>();
		for(Entry<String,PubCode> entry:map.entrySet()) {
			spseno_map.put(entry.getValue().getItnm(), entry.getValue());
		}
		//获取区域的list
		List<Org> list=orgServcie.query(Cnd.select().andEquals(M.Org.channo, Chancl.QY));
		Map<String,Org> org_map=new HashMap<String,Org>();
		for(Org org:list){
			org_map.put(org.getOrgnm(), org);
		}
		
		
		
		InputStream stream = imageFile.getInputStream();
		Workbook wb = WorkbookFactory.create(stream);
		Sheet sheet = wb.getSheetAt(0);
		int rownum=sheet.getLastRowNum();
		for(int i=2;i<=rownum;i++){
			PlanOrgdtlVO planOrgdtlVO=new PlanOrgdtlVO();
			Row row = sheet.getRow(i);

			
			planOrgdtlVO.setOrmtno(ormtno);
			Cell cell = row.getCell(0);
			if(org_map.get(cell.getStringCellValue())==null){
				throw new BusinessException("第"+(i+1)+"行的区域不存在!");
			}
			planOrgdtlVO.setOrdorg(org_map.get(cell.getStringCellValue()).getOrgno());
			cell = row.getCell(1);
			if(brandno_map.get(cell.getStringCellValue())==null){
				throw new BusinessException("第"+(i+1)+"行的品牌不存在!");
			}
			planOrgdtlVO.setBradno(brandno_map.get(cell.getStringCellValue()).getItno());
			cell = row.getCell(2);
			if(spclno_map.get(cell.getStringCellValue())==null){
				throw new BusinessException("第"+(i+1)+"行的大类不存在!");
			}
			planOrgdtlVO.setSpclno(spclno_map.get(cell.getStringCellValue()).getItno());
			cell = row.getCell(3);
			if(sptyno_map.get(cell.getStringCellValue())==null){
				throw new BusinessException("第"+(i+1)+"行的小类不存在!");
			}
			planOrgdtlVO.setSptyno(sptyno_map.get(cell.getStringCellValue()).getItno());
			cell = row.getCell(4);
			if(spseno_map.get(cell.getStringCellValue())==null){
				throw new BusinessException("第"+(i+1)+"行的小类不存在!");
			}
			planOrgdtlVO.setSpseno(spseno_map.get(cell.getStringCellValue()).getItno());
			cell = row.getCell(5);
			if(cell!=null){
				planOrgdtlVO.setQymtqt(cell.getNumericCellValue());
			}
			
			cell = row.getCell(6);
			if(cell!=null){
				planOrgdtlVO.setQymtam(cell.getNumericCellValue());
			}
			cell = row.getCell(7);
			if(cell!=null){
				planOrgdtlVO.setTxmtqt(cell.getNumericCellValue());
			}
			cell = row.getCell(8);
			if(cell!=null){
				planOrgdtlVO.setTxmtam(cell.getNumericCellValue());
			}
			
			this.update(planOrgdtlVO);
		}
		
		

		
		stream.close();
	}
}
