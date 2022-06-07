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
		return "insert";// 앞 뒤로 prefix // surfix
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
		int pageCount = totalCount/pageSize;//더블이 아니기 때문에 12일경우 12/10 이면 1이됨.
		    if(totalCount%pageSize > 0) pageCount++;
		int numTmp = (Integer.parseInt(strNum)-1)*pageSize;    
		
			model.addAttribute("list", dao.ajaxList(numTmp, pageSize));
			model.addAttribute("cnt",dao.ajaxCnt());// 전체 개수는 별로 의미가 없음.
			model.addAttribute("pageCount",pageCount);// 총 페이지수 , jsp에서 for에 이용함.
		return "list";
	}// 필요없음, 왜냐면 쿼리 결과가 search랑 같음!
	
	
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
			int pageCount = totalCount/pageSize;//더블이 아니기 때문에 12일경우 12/10 이면 1이됨.
			    if(totalCount%pageSize > 0) pageCount++;
			int numTmp = (Integer.parseInt(strNum)-1)*pageSize;
		
			mv.setViewName("list");
			mv.addObject("cnt", dao.ajaxSearchCnt(name));
			mv.addObject("list", dao.ajaxSearchList(name, numTmp, pageSize));
			mv.addObject("pageCount",pageCount);// 총 페이지수 , jsp에서 for에 이용함.
			
			
		return mv;
	}
	
	
//	model : 데이터만 저장한다.
//	modelandview :  데이터와 이동하고자 하는 페이지를 같이 저장한다.

	
	
	
	
	
	
	
	
	
	
	
	
//	@RequestMapping("isExsit.do")// Ajax 내용 자체를 여기에 뿌려 버리고, 그것을 jsp에서 긁어가야함.
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
	
	// 이전페이지에서 값이 넘어옵니다. 그 name은 id 였습니다. 하지만 json데이터가 스트링으로 바껴서
	// 넘어오게 되고 우리는 그것을 받아서(받을 당시에는 java의 String 객체임) json데이터로 바꿔야한다.
	// 이작업을 해준느게 responseBody, requestBody 이다.
	
	// RequestBody 는 Http로 들어온 요청을 자바 객체에 매핑해 준다.
	// jsp페이지로 넘어온거를 java(AjaxDTO)에 넣어준다.
	
	// ResonseBody 는 자바객체의 요청을 http 요청으로 매핑 해 준다.
	// 실행된 결과를 jsp로 돌려준다.
	
	
	
	@RequestMapping("Login.do")
	public String login(String id, String pw, HttpSession session) {
		AjaxDTO dto= dao.loginInfo(id,pw);
		if(dto != null) {// id,pw가 맞다면
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
