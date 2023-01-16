package kr.or.connect.visitorbook.dao;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.visitorbook.config.ApplicationConfig;
import kr.or.connect.visitorbook.dto.Log;
import kr.or.connect.visitorbook.dto.Visitorbook;

public class VisitorbookDaoTest {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		VisitorbookDao visitorbookDao = ac.getBean(VisitorbookDao.class);
		
		Visitorbook visitorbook = new Visitorbook();
		visitorbook.setName("에닝");
		visitorbook.setContent("할로~");
		visitorbook.setRegdate(new Date());
		Long id = visitorbookDao.insert(visitorbook);
		System.out.println("id : " + id);
		
		LogDao logDao = ac.getBean(LogDao.class);
		Log log = new Log();
		log.setIp("127.0.0.1");
		log.setMethod("insert");
		log.setRegdate(new Date());
		logDao.insert(log);		
	}
}
