package com.youngor.order;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.mawujun.annotation.FieldDefine;
import com.youngor.ordmt.OrdOrg;

@Entity(name="ord_ord")
public class Ord {
	@Id
	@Column(length=30)
	@FieldDefine(title="订单号",sort=50,hidden=false)
	private String mtorno;//ord.getOrmtno()+"_"+ord.getOrtyno()+"_"+ord.getOrdorg();
	@Column(length=30)
	@FieldDefine(title="订货会编号",sort=50,hidden=false)
	private String ormtno;
	@Column(length=30)
	@FieldDefine(title="订货类型",sort=50,hidden=false)
	private String ortyno;
	@Column(length=30)
	@FieldDefine(title="订货单位",sort=50,hidden=false)
	private String ordorg;
	@Column(length=30)
	@FieldDefine(title="订货单位类型",sort=50,hidden=false)
	private String channo;
	
	@Transient
	private SampleVO sampleVO;//是作为临时缓存用的，保存的是当前扫描的样衣编号数据,只保存最新扫描的那个样衣编号
	@Transient
	private Map<String,Object> ordCheckInfo;//是否能订货的的内容
	@Transient
	private OrdOrg ordMethod;//当前订货单位使用的订货方式
	
	public Boolean canOrd(){
		if(ordCheckInfo==null){
			return false;
		}
		return (Boolean)ordCheckInfo.get("canOrd");
	}
	
	public String getMtorno() {
		return mtorno;
	}
	public void setMtorno(String mtorno) {
		this.mtorno = mtorno;
	}
	public String getOrmtno() {
		return ormtno;
	}
	public void setOrmtno(String ormtno) {
		this.ormtno = ormtno;
	}
	public String getOrtyno() {
		return ortyno;
	}
	public void setOrtyno(String ortyno) {
		this.ortyno = ortyno;
	}
	public String getOrdorg() {
		return ordorg;
	}
	public void setOrdorg(String ordorg) {
		this.ordorg = ordorg;
	}
	public String getChanno() {
		return channo;
	}
	public void setChanno(String channo) {
		this.channo = channo;
	}
	/**
	 * //是作为临时缓存用的，保存的是当前扫描的样衣编号数据
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public SampleVO getSampleVO() {
		return sampleVO;
	}
	public void setSampleVO(SampleVO sampleVO) {
		this.sampleVO = sampleVO;
	}
	public Map<String, Object> getOrdCheckInfo() {
		return ordCheckInfo;
	}
	public void setOrdCheckInfo(Map<String, Object> ordCheckInfo) {
		this.ordCheckInfo = ordCheckInfo;
	}

	public OrdOrg getOrdMethod() {
		return ordMethod;
	}

	public void setOrdMethod(OrdOrg ordMethod) {
		this.ordMethod = ordMethod;
	}



}
