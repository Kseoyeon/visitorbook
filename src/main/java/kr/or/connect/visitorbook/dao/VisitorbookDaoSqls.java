package kr.or.connect.visitorbook.dao;

public class VisitorbookDaoSqls {
	public static final String SELECT_PAGING = "SELECT id, name, content, regdate FROM visitorbook ORDER BY id DESC limit :start, :limit";
	public static final String DELETE_BY_ID = "DELETE FROM visitorbook WHERE id = :id";
	public static final String SELECT_COUNT = "SELECT count(*) FROM visitorbook";
}
