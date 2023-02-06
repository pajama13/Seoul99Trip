package com.sist.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.ReserveDAO;
import com.sist.vo.FoodVO;
import com.sist.vo.ReserveVO;
@Controller
public class ReserveModel {
  @RequestMapping("reserve/reserve_main.do")
  public String reserve_main(HttpServletRequest request,HttpServletResponse response)
  {
	  request.setAttribute("main_jsp", "../reserve/reserve_main.jsp");
	  return "../main/main.jsp";
  }
  
  @RequestMapping("reserve/food_list.do")
  public String reserve_food_list(HttpServletRequest request,HttpServletResponse response)
  {
	  try
	  {
		  request.setCharacterEncoding("UTF-8");
	  }catch(Exception ex) {}
	  String fd=request.getParameter("fd");
	  ReserveDAO dao=new ReserveDAO();
	  List<FoodVO> list=dao.reserveFoodListData(fd);
	  request.setAttribute("list", list);
	  return "../reserve/reserve_food.jsp";
  }
  @RequestMapping("reserve/reserve_date.do")
  public String reserve_date(HttpServletRequest request,HttpServletResponse response)
  {
	  Date date=new Date();
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-d");
	  String today=sdf.format(date);
	  StringTokenizer st=new StringTokenizer(today,"-");
	  
	  String strYear=st.nextToken();
	  String strMonth=st.nextToken();
	  String strDay=st.nextToken();
	  
	  String syear=request.getParameter("year");
	  String smonth=request.getParameter("month");
	  String fno=request.getParameter("fno");
	  
	  if(syear==null)
	  {
		  syear=strYear;
	  }
	  if(smonth==null)
	  {
		  smonth=strMonth;
	  }


	  int year=Integer.parseInt(syear);
	  int month=Integer.parseInt(smonth);
	  int day=Integer.parseInt(strDay);
	  
	  //요일
	  Calendar cal=Calendar.getInstance();
	  cal.set(Calendar.YEAR, year);
	  cal.set(Calendar.MONTH, month-1);
	  cal.set(Calendar.DATE, 1);
	  
	  int week=cal.get(Calendar.DAY_OF_WEEK);
	  
	  //마지막 날짜
	  int lastday=cal.getActualMaximum(Calendar.DATE);
	  
	  //요일
	  String[] strWeek={"일","월","화","수","목","금","토"};
	  
	  //전송
	  request.setAttribute("year", year);
	  request.setAttribute("month", month);
	  request.setAttribute("day", day);
	  request.setAttribute("week", week-1);
	  request.setAttribute("lastday", lastday);
	  request.setAttribute("strWeek", strWeek);
	  request.setAttribute("strWeek", strWeek);
	  //request.setAttribute("", month)  //예약 가능 날짜 (food_location) => 예약일 => 시간 => 인원
	  
	  ReserveDAO dao=new ReserveDAO();
	     String rdate=dao.reserveDayData(Integer.parseInt(fno));
	     int[] rdays=new int[32];
	     String[] temp=rdate.split(",");
	     for(String ss:temp)
	     {
	        if(Integer.parseInt(ss)>=day)
	        {
	           rdays[Integer.parseInt(ss)]=1;
	        }
	     }
	     request.setAttribute("rdays", rdays);
	  return "../reserve/reserve_date.jsp";
  }
  @RequestMapping("reserve/reserve_time.do")
  public String reserve_time(HttpServletRequest request, HttpServletResponse response)
  {
     String day = request.getParameter("day");
     List<String> reserve_time = new ArrayList<String>();
     // DAO 연결
     ReserveDAO dao = new ReserveDAO();
     String dd=dao.reserveTimeData(Integer.parseInt(day));
     // 1,2,3
     StringTokenizer st = new StringTokenizer(dd,",");
     while(st.hasMoreTokens())
     {
        String ss = dao.reserveTimeRealData(Integer.parseInt(st.nextToken()));
        reserve_time.add(ss);
     }
     request.setAttribute("rtime", reserve_time);
     return "../reserve/reserve_time.jsp";
  }
  
    @RequestMapping("reserve/reserve_inwon.do")
    public String reserve_inwon(HttpServletRequest request, HttpServletResponse response)
    {
       return "../reserve/reserve_inwon.jsp";
    }
  
    
}















