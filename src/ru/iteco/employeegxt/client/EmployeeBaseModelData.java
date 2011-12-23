package ru.iteco.employeegxt.client;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.google.gwt.i18n.client.DateTimeFormat;

public class EmployeeBaseModelData extends BaseModelData {

	private DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/y");

	public EmployeeBaseModelData() {

	}

	public EmployeeBaseModelData( Long empno, String ename, String job, Long mgr, String hiredate, Long sal, Long comm, Long deptno) {
		setEMPNO(empno);
		setENAME(ename);
		setJOB(job);
		setMGR(mgr);
		setHIREDATE(hiredate);
		setSAL(sal);
		setCOMM(comm);
		setDEPTNO(deptno);
	}
	
	public Long getEMPNO() {
		return get("empno");
	}

	public void setEMPNO(Long empno) {
		set("empno",empno);
	}

	public String getENAME() {
		return get("ename");
	}

	public void setENAME(String ename) {
		set("ename",ename);
	}

	public String getJOB() {
		return get("job");
	}

	public void setJOB(String job) {
		set("job",job);
	}

	public Long getMGR() {
		return get("mgr");
	}

	public void setMGR(Long mgr) {
		set("mgr",mgr);
	}

	public Date getHIREDATE() {
		return get("hiredate");
	}
	
	public void setHIREDATE(String hiredate) {
		set("hiredate",df.parse(hiredate));
	}

	public Long getSAL() {
		return get("sal");
	}

	public void setSAL(Long sal) {
		set("sal",sal);
	}

	public Long getCOMM() {
		return get("comm");
	}

	public void setCOMM(Long comm) {
		set("comm", comm);
	}

	public Long getDEPTNO() {
		return get("deptno");
	}

	public void setDEPTNO(Long deptno) {
		set("deptno",deptno);
	}
}
