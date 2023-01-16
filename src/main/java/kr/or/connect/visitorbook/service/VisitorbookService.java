package kr.or.connect.visitorbook.service;

import java.util.List;

import kr.or.connect.visitorbook.dto.Visitorbook;

public interface VisitorbookService {
	public static final Integer LIMIT = 5;
	public List<Visitorbook> getVisitorbooks(Integer start);
	public int deleteVisitorbook(Long id, String ip);
	public Visitorbook addVisitorbook(Visitorbook visitorbook, String ip);
	public int getCount();
}
