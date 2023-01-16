package kr.or.connect.visitorbook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.connect.visitorbook.argumentresolver.HeaderInfo;
import kr.or.connect.visitorbook.dto.Visitorbook;
import kr.or.connect.visitorbook.service.VisitorbookService;

@Controller
public class VisitorbookController {
	@Autowired
	VisitorbookService visitorbookService;
	
	@GetMapping(path="/list")
	public String list(@RequestParam(name="start", required=false, defaultValue="0") int start,
					   ModelMap model, @CookieValue(value="count", defaultValue="0", required=true) String value,
					   HttpServletResponse response, HeaderInfo headerInfo) {
		
		System.out.println("-----------------------------------------------------");
		System.out.println(headerInfo.get("user-agent"));
		System.out.println("-----------------------------------------------------");
		
//		String value = null;
//		boolean find = false;
//		Cookie[] cookies = request.getCookies();
//		if(cookies != null) {
//			for(Cookie cookie : cookies) {
//				if("count".equals(cookie.getName())) {
//					find = true;
//					value = cookie.getValue();
//					break;
//				}
//			}
//		}
		
//		if(!find) {
//			value = "1";
//		} else {
//			try {
//				int i = Integer.parseInt(value);
//				value = Integer.toString(++i);
//			} catch(Exception ex) {
//				value = "1";
//			}
//		}
		
		try {
			int i = Integer.parseInt(value);
			value = Integer.toString(++i);
		} catch(Exception ex) {
			value = "1";
		}
		
		Cookie cookie = new Cookie("count", value);
		cookie.setMaxAge(60*50*24*365); // 1년 유지
		cookie.setPath("/"); // '/' 경로 아래에 모두 쿠키 적용
		response.addCookie(cookie);
		
		// start로 시작하는 방명록 목록 구하기
		List<Visitorbook> list = visitorbookService.getVisitorbooks(start);
		
		// 전체 페이지수 구하기
		int count = visitorbookService.getCount();
		int pageCount = count / VisitorbookService.LIMIT;
		if(count % VisitorbookService.LIMIT > 0)
			pageCount++;
		
		// 페이지 수만큼 start의 값을 리스트로 저장
		// 예를 들면 페이지수가 3이면
		// 0, 5, 10 이렇게 저장된다.
		// list?start=0 , list?start=5, list?start=10 으로 링크가 걸린다.
		List<Integer> pageStartList = new ArrayList<>();
		for(int i = 0; i < pageCount; i++) {
			pageStartList.add(i * VisitorbookService.LIMIT);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageStartList", pageStartList);
		model.addAttribute("cookieCount", value);
		
		return "list";
	}
	
	@PostMapping(path="/write")
	public String write(@ModelAttribute Visitorbook visitorbook,
						HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		System.out.println("clientIp : " + clientIp);
		visitorbookService.addVisitorbook(visitorbook, clientIp);
		return "redirect:list";
	}
	
	@GetMapping(path="/delete")
	public String delete(@RequestParam(name="id", required=true) Long id, 
			@SessionAttribute("isAdmin") String isAdmin,
			HttpServletRequest request,
			RedirectAttributes redirectAttr) {
		if(isAdmin == null || !"true".equals(isAdmin)) {
			redirectAttr.addFlashAttribute("errorMessage", "로그인을 하지 않았습니다.");
			return "redirect:loginform";
		}
		String clientIp = request.getRemoteAddr();
		visitorbookService.deleteVisitorbook(id, clientIp);
		return "redirect:list";
	}
}
