package com.sist.vo;

import java.sql.Date;

/*
 * rno NUMBER,
   fno NUMBER,
   id VARCHAR2(20),
   rdate VARCHAR2(20) CONSTRAINT res_rdate_nn_4 NOT NULL,
   rtime VARCHAR2(20) CONSTRAINT res_rtime_nn_4 NOT NULL,
   inwon NUMBER,
   reserve_no VARCHAR2(20) CONSTRAINT res_rn_nn_4 NOT NULL,
   ok CHAR(1),
   regdate DATE DEFAULT SYSDATE,
   CONSTRAINT res_rno_pk_4 PRIMARY KEY(rno),
   CONSTRAINT res_fno_4 FOREIGN KEY(fno)
   REFERENCES gg_foodDetail_4(fno),
   CONSTRAINT res_id_fk_4 FOREIGN KEY(id)
   REFERENCES gg_member_4 (id),
   CONSTRAINT res_inwon_ck_4 CHECK(inwon>0)

 */
public class ReserveVO {
	private int rno,fno,inwon;
	private String id,rdate,rtime,reserve_no,ok;
	private Date regdate;
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public int getFno() {
		return fno;
	}
	public void setFno(int fno) {
		this.fno = fno;
	}
	public int getInwon() {
		return inwon;
	}
	public void setInwon(int inwon) {
		this.inwon = inwon;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public String getRtime() {
		return rtime;
	}
	public void setRtime(String rtime) {
		this.rtime = rtime;
	}
	public String getReserve_no() {
		return reserve_no;
	}
	public void setReserve_no(String reserve_no) {
		this.reserve_no = reserve_no;
	}
	public String getOk() {
		return ok;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	

}
