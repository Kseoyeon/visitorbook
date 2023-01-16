package kr.or.connect.visitorbook.service.impl;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.visitorbook.config.ApplicationConfig;
import kr.or.connect.visitorbook.dto.Visitorbook;
import kr.or.connect.visitorbook.service.VisitorbookService;

public class VisitorbookServiceTest {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		VisitorbookService visitorbookService = ac.getBean(VisitorbookService.class);
		
		Visitorbook visitorbook = new Visitorbook();
		visitorbook.setName("에닝");
		visitorbook.setContent("하위");
		visitorbook.setRegdate(new Date());
		Visitorbook result = visitorbookService.addVisitorbook(visitorbook, "127.0.0.1");
		System.out.println(result);
	}
}
