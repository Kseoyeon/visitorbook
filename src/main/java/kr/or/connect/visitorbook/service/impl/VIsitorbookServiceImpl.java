package kr.or.connect.visitorbook.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.visitorbook.dao.LogDao;
import kr.or.connect.visitorbook.dao.VisitorbookDao;
import kr.or.connect.visitorbook.dto.Log;
import kr.or.connect.visitorbook.dto.Visitorbook;
import kr.or.connect.visitorbook.service.VisitorbookService;

@Service
public class VIsitorbookServiceImpl implements VisitorbookService{
	@Autowired
	VisitorbookDao visitorbookDao;
	
	@Autowired
	LogDao logDao;

	@Override
	@Transactional
	public List<Visitorbook> getVisitorbooks(Integer start) {
		List<Visitorbook> list = visitorbookDao.selectAll(start, VisitorbookService.LIMIT);
		return list;
	}

	@Override
	@Transactional(readOnly=false)
	public int deleteVisitorbook(Long id, String ip) {
		int deleteCount = visitorbookDao.deleteById(id);
		Log log = new Log();
		log.setIp(ip);
		log.setMethod("delete");
		log.setRegdate(new Date());
		logDao.insert(log);
		return deleteCount;
	}

	@Override
	@Transactional(readOnly=false)
	public Visitorbook addVisitorbook(Visitorbook visitorbook, String ip) {
		visitorbook.setRegdate(new Date());
		Long id = visitorbookDao.insert(visitorbook);
		visitorbook.setId(id);
		
//		if(1 == 1)
//			throw new RuntimeException("test exception");
//			
		Log log = new Log();
		log.setIp(ip);
		log.setMethod("insert");
		log.setRegdate(new Date());
		logDao.insert(log);
		
		
		return visitorbook;
	}

	@Override
	public int getCount() {
		return visitorbookDao.selectCount();
	}	
}
