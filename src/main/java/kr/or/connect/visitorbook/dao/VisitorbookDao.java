package kr.or.connect.visitorbook.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.visitorbook.dto.Visitorbook;

import static kr.or.connect.visitorbook.dao.VisitorbookDaoSqls.*;

@Repository
public class VisitorbookDao {
	 private NamedParameterJdbcTemplate jdbc;
	    private SimpleJdbcInsert insertAction;
	    private RowMapper<Visitorbook> rowMapper = BeanPropertyRowMapper.newInstance(Visitorbook.class);

	    public VisitorbookDao(DataSource dataSource) {
	        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	        this.insertAction = new SimpleJdbcInsert(dataSource)
	                .withTableName("visitorbook")
	                .usingGeneratedKeyColumns("id");
	    }
	    
	    public List<Visitorbook> selectAll(Integer start, Integer limit) {
	    		Map<String, Integer> params = new HashMap<>();
	    		params.put("start", start);
	    		params.put("limit", limit);
	        return jdbc.query(SELECT_PAGING, params, rowMapper);
	    }


		public Long insert(Visitorbook visitorbook) {
			SqlParameterSource params = new BeanPropertySqlParameterSource(visitorbook);
			return insertAction.executeAndReturnKey(params).longValue();
		}
		
		public int deleteById(Long id) {
			Map<String, ?> params = Collections.singletonMap("id", id);
			return jdbc.update(DELETE_BY_ID, params);
		}
		
		public int selectCount() {
			return jdbc.queryForObject(SELECT_COUNT, Collections.emptyMap(), Integer.class);
		}
}
