package kr.or.connect.visitorbook.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.visitorbook.dto.Visitorbook;
import kr.or.connect.visitorbook.service.VisitorbookService;

@RestController
@RequestMapping(path="/visitorbooks")
public class VisitorbookApiController {
	@Autowired
	VisitorbookService visitorbookService;
	
	@GetMapping
	public Map<String, Object> list(@RequestParam(name="start", required=false, defaultValue="0") int start) {
	
		List<Visitorbook> list = visitorbookService.getVisitorbooks(start);
		
		int count = visitorbookService.getCount();
		int pageCount = count/VisitorbookService.LIMIT;
		if(count%VisitorbookService.LIMIT>0)
			pageCount++;
		
		List<Integer> pageStartList = new ArrayList<>();
		for(int i = 0; i < pageCount; i++) {
			pageStartList.add(i*VisitorbookService.LIMIT);
		}
		
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		map.put("pageStartList", pageStartList);
		
		return map;
	}
	
	@PostMapping
	public Visitorbook write(@RequestBody Visitorbook visitorbook,
			HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		Visitorbook resultVisitorbook = visitorbookService.addVisitorbook(visitorbook, clientIp);
		return resultVisitorbook;
	}
	
	@DeleteMapping("/{id}")
	public Map<String, String> delete(@PathVariable(name="id")Long id,
			HttpServletRequest request){
		String clientIp = request.getRemoteAddr();
		
		int deleteCount = visitorbookService.deleteVisitorbook(id, clientIp);
		return Collections.singletonMap("success", deleteCount > 0 ? "true" : "false");
	}
}
