package com.youngor.order;

import com.youngor.pubcode.PubCodeCache;
import com.youngor.utils.ContextUtils;

/**
 * 区域平衡中用的
 * @author mawujun qq:16064988 mawujun1234@163.com
 *
 */
public class QyVO {
	private String mtorno;
	
	private String channo;//渠道类型
	private String ordorg;//订货单位
	private String sptyno;//小类
	private String spseno;//系列
	private String plspno;//企划样衣编号
	private String plspnm;//企划样衣编号
	private String sampno;//设计样衣编号
	private String sampnm;//设计样衣编号
	private String packqt;//包装要求,就是数字
	private String suitno;//套件
	private Integer ormtqs;//原始数量
	private Integer ormtqt;//确认数量
	private String ormark;//备注
	private Integer orstat;//总量状态
	
	private Double spftpr;//出厂价
	private Double sprtpr;//零售价
	
//	//----名称
//	private String channo_name;
	private String ordorg_name;
	
	
	
//	//-------查询条件相关的
//	private String orgno;//区域的代码。因为默认肯定会选区域
//	private String ormtno;//订货会 编号
//	private String ortyno;//订货类型
//	private String bradno;//品牌
//	private String spclno;//大类
	/**
	 * 获取原始数量折算
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public Double getOrmtqs_zhes(){
		if(ormtqs==null){
			return 0d;
		}
		if("T00".equals(this.getSuitno())){
			return ormtqs*1d;
		} else if("T01".equals(this.getSuitno())){//上衣
			return ormtqs*0.75d; 
		} else if("T02".equals(this.getSuitno())){//裤子
			return ormtqs*0.25d; 
		} else if("T04".equals(this.getSuitno())){//裙子
			return ormtqs*0.25d;
		} else {
			return ormtqs*1d;
		}
	}
	
	/**
	 * 获取原始数量折算,放在这里是因为ext的原因
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public Double getOrmtqt_zhes(){
		if("T00".equals(this.getSuitno())){
			return (ormtqt==null?0:ormtqt)*1d;
		} else if("T01".equals(this.getSuitno())){//上衣
			return (ormtqt==null?0:ormtqt)*0.75d; 
		} else if("T02".equals(this.getSuitno())){//裤子
			return (ormtqt==null?0:ormtqt)*0.25d; 
		} else if("T04".equals(this.getSuitno())){//裙子
			return (ormtqt==null?0:ormtqt)*0.25d;
		} else {
			return (ormtqt==null?0:ormtqt)*1d;
		}
	}
	public Double getSpftpr_jine(){
		if(spftpr==null || ormtqt==null){
			return 0d;
		}
		return spftpr*ormtqt;
	}
	public Double getSprtpr_jine(){
		if(sprtpr==null || ormtqt==null){
			return 0d;
		}
		return sprtpr*ormtqt;
	}
	
	public String getSptyno_name() {
		return PubCodeCache.getSptyno_name(this.getSptyno());
	}
	public String getSpseno_name() {
		return PubCodeCache.getSpseno_name(this.getSpseno());
	}
	public String getSuitno_name(){
		return PubCodeCache.getSuitno_name(this.getSuitno());
	}
	public String getChanno_name(){
		return ContextUtils.getChanno(this.getChanno()).getChannm();
	}
	
	
	
	public String getChanno() {
		return channo;
	}
	public void setChanno(String channo) {
		this.channo = channo;
	}
	public String getOrdorg() {
		return ordorg;
	}
	public void setOrdorg(String ordorg) {
		this.ordorg = ordorg;
	}
	public String getSptyno() {
		return sptyno;
	}
	public void setSptyno(String sptyno) {
		this.sptyno = sptyno;
	}
	public String getSpseno() {
		return spseno;
	}
	public void setSpseno(String spseno) {
		this.spseno = spseno;
	}
	public String getPlspno() {
		return plspno;
	}
	public void setPlspno(String plspno) {
		this.plspno = plspno;
	}
	public String getSampno() {
		return sampno;
	}
	public void setSampno(String sampno) {
		this.sampno = sampno;
	}
	public String getPackqt() {
		return packqt;
	}
	public void setPackqt(String packqt) {
		this.packqt = packqt;
	}
	public String getSuitno() {
		return suitno;
	}
	public void setSuitno(String suitno) {
		this.suitno = suitno;
	}
	
	public String getOrmark() {
		return ormark;
	}
	public void setOrmark(String ormark) {
		this.ormark = ormark;
	}

	public String getPlspnm() {
		return plspnm;
	}
	public void setPlspnm(String plspnm) {
		this.plspnm = plspnm;
	}
	public String getSampnm() {
		return sampnm;
	}
	public void setSampnm(String sampnm) {
		this.sampnm = sampnm;
	}
	public Integer getOrmtqs() {
		return ormtqs;
	}
	public void setOrmtqs(Integer ormtqs) {
		this.ormtqs = ormtqs;
	}
	public Integer getOrmtqt() {
		return ormtqt;
	}
	public void setOrmtqt(Integer ormtqt) {
		this.ormtqt = ormtqt;
	}
	public String getOrdorg_name() {
		return ordorg_name;
	}
	public void setOrdorg_name(String ordorg_name) {
		this.ordorg_name = ordorg_name;
	}
	public String getMtorno() {
		return mtorno;
	}
	public void setMtorno(String mtorno) {
		this.mtorno = mtorno;
	}
	public Integer getOrstat() {
		return orstat;
	}
	public void setOrstat(Integer orstat) {
		this.orstat = orstat;
	}
	public Double getSpftpr() {
		return spftpr;
	}
	public void setSpftpr(Double spftpr) {
		this.spftpr = spftpr;
	}
	public Double getSprtpr() {
		return sprtpr;
	}
	public void setSprtpr(Double sprtpr) {
		this.sprtpr = sprtpr;
	}


}
