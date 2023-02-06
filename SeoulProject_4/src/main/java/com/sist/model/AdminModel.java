package com.sist.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.BoardDAO;
import com.sist.dao.NoticeDAO;
import com.sist.vo.BoardVO;
import com.sist.vo.NoticeVO;

@Controller
public class AdminModel {
  private String[] prefix={"","일반공지","이벤트공지","맛집공지","여행공지","상품공지"};
  @RequestMapping("adminpage/admin_main.do")
  public String admin_main(HttpServletRequest request,HttpServletResponse response)
  {
	  request.setAttribute("admin_jsp", "../adminpage/admin_home.jsp");
	  request.setAttribute("main_jsp", "../adminpage/admin_main.jsp");
	  
	  return "../main/main.jsp";
  }
  @RequestMapping("adminpage/board_list.do")
	 public String board(HttpServletRequest request, HttpServletResponse response) {
		String page = request.getParameter("page");
	      if (page == null) {
	         page = "1";
	      }
	      int curpage = Integer.parseInt(page);
	BoardDAO dao=new BoardDAO();
	ArrayList<BoardVO> list=dao.boardListData(curpage);
	int totalpage = dao.boardTotalPage();
 
 final int BLOCK = 10;
 int startPage = ((curpage-1)/BLOCK*BLOCK) + 1;
 int endPage = ((curpage-1)/BLOCK * BLOCK) + BLOCK;
 if (endPage > totalpage)
     endPage = totalpage;
 if (totalpage==0)
 	totalpage = totalpage+1;
 
 request.setAttribute("list", list);
 request.setAttribute("curpage", curpage);
 request.setAttribute("totalpage", totalpage);
 request.setAttribute("startPage", startPage);
 request.setAttribute("endPage", endPage);
 request.setAttribute("main_jsp", "../board/list.jsp"); // main.jsp에서 include되는 파일 지정
 request.setAttribute("admin_jsp", "../adminpage/board_list.jsp");
 request.setAttribute("main_jsp", "../adminpage/admin_main.jsp");
 return "../main/main.jsp";
	}
	@RequestMapping("board/insert.do")
	public String board_insert(HttpServletRequest request, HttpServletResponse response)
	{
		// 중간에 들어가는 파일을 지정
		request.setAttribute("main_jsp", "../board/insert.jsp"); // 화면 출력
		return "../main/main.jsp";
	}
  @RequestMapping("adminpage/notice_list.do")
  public String admin_notice_list(HttpServletRequest request,HttpServletResponse response)
  {
	// 사용자 보내준 데이터 받기 
	   String page=request.getParameter("page");
	   if(page==null)
		   page="1";
	   int curpage=Integer.parseInt(page);
	   NoticeDAO dao=new NoticeDAO();
	   List<NoticeVO> list=dao.noticeListData(curpage);
	   
	   for(NoticeVO vo:list)
	   {
		   vo.setPrefix("["+prefix[vo.getType()]+"]");
	   }
	   
	   int totalpage=dao.noticeTotalPage();
	   
	   request.setAttribute("list", list);
	   request.setAttribute("curpage", curpage);
	   request.setAttribute("totalpage", totalpage);
	  request.setAttribute("admin_jsp", "../adminpage/notice_list.jsp");
	  request.setAttribute("main_jsp", "../adminpage/admin_main.jsp");
	  
	  return "../main/main.jsp";
  }
  @RequestMapping("adminpage/notice_insert.do")
  public String admin_notice_insert(HttpServletRequest request,HttpServletResponse response)
  {
	   request.setAttribute("admin_jsp", "../adminpage/notice_insert.jsp");
	   request.setAttribute("main_jsp", "../adminpage/admin_main.jsp");
	   
	   return "../main/main.jsp";
  }
  @RequestMapping("adminpage/notice_insert_ok.do")
  // (NoticeVO vo)
  public String admin_notice_insert_ok(HttpServletRequest request,HttpServletResponse response)
  {
	  // 사용자가 전송한 데이터 받기 
	  try
	  {
		  request.setCharacterEncoding("UTF-8");
	  }catch(Exception ex) {}
	  String type=request.getParameter("type");
	  String name=request.getParameter("name");
	  String title=request.getParameter("title");
	  String content=request.getParameter("content");
	  NoticeVO vo=new NoticeVO();
	  vo.setType(Integer.parseInt(type));
	  vo.setName(name);
	  vo.setTitle(title);
	  vo.setContent(content);
	  
	  //DAO연결 => 오라클 
	  NoticeDAO dao=new NoticeDAO();
	  dao.noticeInsert(vo);
	  return "redirect:notice_list.do";
  }
  @RequestMapping("adminpage/notice_delete.do")
  public String admin_notice_delete(HttpServletRequest request,HttpServletResponse response)
  {
	  String nno=request.getParameter("nno");
	  //DAO연동 
	  NoticeDAO dao=new NoticeDAO();
	  dao.noticeDelete(Integer.parseInt(nno));
	  return "redirect:notice_list.do";
  }
  @RequestMapping("adminpage/notice_update.do")
  public String admin_notice_update(HttpServletRequest request,HttpServletResponse response)
  {
	   String nno=request.getParameter("nno");
	   NoticeDAO dao=new NoticeDAO();
	   NoticeVO vo=dao.noticeUpdateData(Integer.parseInt(nno));
	   request.setAttribute("vo", vo);
	   // include => request를 공유 
	   request.setAttribute("admin_jsp", "../adminpage/notice_update.jsp");
	   request.setAttribute("main_jsp", "../adminpage/admin_main.jsp");
	   
	   return "../main/main.jsp";
  }
  @RequestMapping("adminpage/notice_update_ok.do")
  public String admin_notice_update_ok(HttpServletRequest request,HttpServletResponse response)
  {
	  try
	  {
		  request.setCharacterEncoding("UTF-8");
	  }catch(Exception ex){}
	  String type=request.getParameter("type");
	  String name=request.getParameter("name");
	  String title=request.getParameter("title");
	  String content=request.getParameter("content");
	  String nno=request.getParameter("nno");
	  NoticeVO vo=new NoticeVO();
	  vo.setType(Integer.parseInt(type));
	  vo.setName(name);
	  vo.setTitle(title);
	  vo.setContent(content);
	  vo.setNno(Integer.parseInt(nno));
	  NoticeDAO dao=new NoticeDAO();
	  // 메소드 호출 
	  dao.noticeUpdate(vo);
	  return "redirect:notice_list.do";
  }
  ///adminpage/board_list.do
  @RequestMapping("adminpage/board_list.do")
  public String admin_board_list(HttpServletRequest request,HttpServletResponse response)
  {
	// 사용자 보내준 데이터 받기 
	  String page = request.getParameter("page");
      if (page == null) {
         page = "1";
      }
      int curpage = Integer.parseInt(page);
		BoardDAO dao=new BoardDAO();
		ArrayList<BoardVO> list=dao.boardListData(curpage);
		int totalpage = dao.boardTotalPage();
		
		final int BLOCK = 10;
		int startPage = ((curpage-1)/BLOCK*BLOCK) + 1;
		int endPage = ((curpage-1)/BLOCK * BLOCK) + BLOCK;
		if (endPage > totalpage)
		 endPage = totalpage;
		if (totalpage==0)
			totalpage = totalpage+1;
		
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
	   
	   
	  request.setAttribute("admin_jsp", "../adminpage/board_list.jsp");
	  request.setAttribute("main_jsp", "../adminpage/admin_main.jsp");
	  
	  return "../main/main.jsp";
  }
  @RequestMapping("adminpage/board_insert.do")
  public String admin_board_insert(HttpServletRequest request,HttpServletResponse response)
  {
	  request.setAttribute("admin_jsp", "../adminpage/board_insert.jsp");
	   request.setAttribute("main_jsp", "../adminpage/admin_main.jsp");
		return "../main/main.jsp";
  }
  @RequestMapping("adminpage/board_insert_ok.do")
  // (NoticeVO vo)
  public String admin_board_insert_ok(HttpServletRequest request,HttpServletResponse response)
  {
	  try {
			request.setCharacterEncoding("UTF-8");
			String path="C:\\webDev\\webStudy\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\SeoulTempProject_4\\upload"; // 업로드된 파일 저장 위치
			int size=1024*1024*100; // 업로드된 파일의 최대 크기 : 100MB
			String enctype="UTF-8"; // 한글 파일명
			MultipartRequest mr=new MultipartRequest(request,path,size,enctype,new DefaultFileRenamePolicy());
			// 사용자가 보내준 데이터를 받는다
			HttpSession session=request.getSession();
			String id=(String)session.getAttribute("id");
	//		String id=mr.getParameter("id");
			String name=mr.getParameter("name");
			String title=mr.getParameter("title");
			String content=mr.getParameter("content");
			String filename=mr.getFilesystemName("upload");			
			// BoardVO에 묶어서 오라클 전송
			BoardVO vo=new BoardVO();
			vo.setId(id);
			vo.setName(name);
			vo.setTitle(title);
			vo.setContent(content);
			if(filename==null) // 업로드가 없는 상태
			{
				vo.setFilename("");
				vo.setFilesize(0);
			}
			else // 업로드가 된 상태
			{
				File file=new File(path+"\\"+filename);
				vo.setFilename(filename);
				vo.setFilesize((int)file.length()); // 실제 저장된 파일의 크기를 읽어온다
			}
			BoardDAO dao=new BoardDAO();
			dao.ad_boardInsert(vo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 화면 이동
		return "redirect:board_list.do";
  }
  //adminpage/update.do
  @RequestMapping("adminpage/update.do")
	public String board_update(HttpServletRequest request, HttpServletResponse response)
	   {
		   String bno=request.getParameter("bno");
		   BoardDAO dao=new BoardDAO();
		   BoardVO vo=dao.boardUpdateData(Integer.parseInt(bno));
		   
		   request.setAttribute("vo", vo);
		   request.setAttribute("admin_jsp", "../adminpage/board_update.jsp");
		   request.setAttribute("main_jsp", "../adminpage/admin_main.jsp");
		   
		   return "../main/main.jsp";
	   }
	@RequestMapping("adminpage/update_ok.do")
	public String board_update_ok(HttpServletRequest request, HttpServletResponse response)
	   {
		   try
		   {
			   // 한글 변환
			   request.setCharacterEncoding("UTF-8");
		   }catch(Exception ex) {}
		   String name=request.getParameter("name");
		   String title=request.getParameter("title");
		   String content=request.getParameter("content");
		   String bno=request.getParameter("bno");
		   String moddate=request.getParameter(bno);
		   BoardVO vo=new BoardVO();
		   vo.setName(name);
		   vo.setTitle(title);
		   vo.setContent(content);
		   vo.setBno(Integer.parseInt(bno));
		   BoardDAO dao=new BoardDAO();
		   dao.ad_boardUpdate(vo);
		   String msg="yes";
		   request.setAttribute("msg", msg);
		   

		   return "redirect:board_list.do";
	   }
	//adminpage/board_delete.do
	@RequestMapping("adminpage/board_delete.do")
	  public String admin_board_delete(HttpServletRequest request,HttpServletResponse response)
	  {
		  String no=request.getParameter("no");
		  //DAO연동 
		  BoardDAO dao=new BoardDAO();
		  dao.ad_boardDelete(Integer.parseInt(no));
		  return "redirect:board_list.do";
	  }
}
