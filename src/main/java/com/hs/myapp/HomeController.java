package com.hs.myapp;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hs.myapp.bean.AjaxDTO;
import com.hs.myapp.dao.AjaxDAO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private AjaxDAO dao;
	
	@RequestMapping(value = "main.do")
	public String home() {
		return "home";
	}
	
	@RequestMapping("AjaxInsert.do")
	public String insert() {
		return "insert";// �� �ڷ� prefix // surfix
	}
	
	@RequestMapping("AjaxInsertProc.do")
	public String insertProc(AjaxDTO dto) {
		int result = dao.ajaxInsert(dto);

//		if(result>0){
//			return "redirct:/AjaxList.do";
//		}else {
//			return "error";// error.jsp
//		}
		
		return "redirect:/AjaxList.do";
	}
	
	@RequestMapping("AjaxList.do")
	public String list(
			@RequestParam(value="pageNum", required=false, defaultValue="1")String strNum, 
			Model model) {
		
		int pageSize = 10;
		int totalCount = dao.ajaxCnt();
		int pageCount = totalCount/pageSize;//������ �ƴϱ� ������ 12�ϰ�� 12/10 �̸� 1�̵�.
		    if(totalCount%pageSize > 0) pageCount++;
		int numTmp = (Integer.parseInt(strNum)-1)*pageSize;    
		
			model.addAttribute("list", dao.ajaxList(numTmp, pageSize));
			model.addAttribute("cnt",dao.ajaxCnt());// ��ü ������ ���� �ǹ̰� ����.
			model.addAttribute("pageCount",pageCount);// �� �������� , jsp���� for�� �̿���.
		return "list";
	}// �ʿ����, �ֳĸ� ���� ����� search�� ����!
	
	
//	@RequestMapping("AjaxSearchList.do")
//	public String searList(Model model, String name) {
//			model.addAttribute("list", dao.ajaxSearchList(name));
//			model.addAttribute("cnt", dao.ajaxSearchCnt(name));
//		return "list";
//	}
	
	@RequestMapping("AjaxSearchList.do")
	public ModelAndView searList(@RequestParam(value="pageNum", required=false, defaultValue="1")String strNum,ModelAndView mv, String name) {
			int pageSize = 10;
			int totalCount = dao.ajaxCnt();
			int pageCount = totalCount/pageSize;//������ �ƴϱ� ������ 12�ϰ�� 12/10 �̸� 1�̵�.
			    if(totalCount%pageSize > 0) pageCount++;
			int numTmp = (Integer.parseInt(strNum)-1)*pageSize;
		
			mv.setViewName("list");
			mv.addObject("cnt", dao.ajaxSearchCnt(name));
			mv.addObject("list", dao.ajaxSearchList(name, numTmp, pageSize));
			mv.addObject("pageCount",pageCount);// �� �������� , jsp���� for�� �̿���.
			
			
		return mv;
	}
	
	
//	model : �����͸� �����Ѵ�.
//	modelandview :  �����Ϳ� �̵��ϰ��� �ϴ� �������� ���� �����Ѵ�.

	
	
	
	
	
	
	
	
	
	
	
	
//	@RequestMapping("isExsit.do")// Ajax ���� ��ü�� ���⿡ �ѷ� ������, �װ��� jsp���� �ܾ����.
//	public void idCheck(String id, HttpServletResponse response) throws IOException {
//		JSONObject obj = new JSONObject();
//		obj.put("res", dao.isExsitId(id));		
//		response.getWriter().print(obj.toString());
//	}
	
	
	//public HashMap<String, String> ajaxCheck(AjaxDTO dto) {
	
	@RequestMapping(value="isIdExsit.do", method=RequestMethod.POST)	
	@ResponseBody
	public HashMap<String, String> ajaxCheck(@RequestBody AjaxDTO dto) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("res",dao.isExsitId(dto.getId()));		
		return map;
	}
	
	// �������������� ���� �Ѿ�ɴϴ�. �� name�� id �����ϴ�. ������ json�����Ͱ� ��Ʈ������ �ٲ���
	// �Ѿ���� �ǰ� �츮�� �װ��� �޾Ƽ�(���� ��ÿ��� java�� String ��ü��) json�����ͷ� �ٲ���Ѵ�.
	// ���۾��� ���ش��� responseBody, requestBody �̴�.
	
	// RequestBody �� Http�� ���� ��û�� �ڹ� ��ü�� ������ �ش�.
	// jsp�������� �Ѿ�°Ÿ� java(AjaxDTO)�� �־��ش�.
	
	// ResonseBody �� �ڹٰ�ü�� ��û�� http ��û���� ���� �� �ش�.
	// ����� ����� jsp�� �����ش�.
	
	
	
	@RequestMapping("Login.do")
	public String login(String id, String pw, HttpSession session) {
		AjaxDTO dto= dao.loginInfo(id,pw);
		if(dto != null) {// id,pw�� �´ٸ�
			session.setAttribute("userInfo", dto);
		}
		return "home";
	}
	
	@RequestMapping("Logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "home";
	}
	
	
	
	
}
