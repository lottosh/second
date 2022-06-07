package com.hs.myapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hs.myapp.bean.AjaxDTO;

@Repository
public class AjaxDAO {
	
	@Autowired
	private JdbcTemplate jdbcTmp;
	
	@Value("insert into spajax values (null, ?, ?, ?)")
	private String insertData;
	
	@Value("update spajax set id=?, pw=?, name=? where idx=?")
	private String updateData;
	
	@Value("delete from spajax where idx=?")
	private String deleteData;
	
	@Value("select * from spajax limit ?, ?")
	private String selectAllData;
	
	@Value("select count(idx) from spajax")
	private String selectAllCntData;
	
	@Value("select * from spajax where name like ? limit ?, ?")
	private String selectSearchData;
	
	@Value("select count(idx) from spajax where name like ?")
	private String selectSearchCntData;	
	
	@Value("select count(*) from spajax where id=?")
	private String selectAjaxData;
	
	@Value("select * from spajax where id=? and pw=?")
	private String loginData;
	
	
	public int ajaxInsert(AjaxDTO dto) {
		return jdbcTmp.update(insertData, dto.getId(),dto.getPw(),dto.getName());
	}
	
	public List<AjaxDTO> ajaxList(int start, int cnt){
		return jdbcTmp.query(selectAllData, new AjaxMapper(), start, cnt);
	}
	
	public int ajaxCnt(){
		return jdbcTmp.queryForObject(selectAllCntData, Integer.class);
	}
	
	
	public List<AjaxDTO> ajaxSearchList(String name, int start, int cnt){
		return jdbcTmp.query(selectSearchData, new AjaxMapper(), "%"+name+"%", start, cnt);
	}
	
	public int ajaxSearchCnt(String name){
		return jdbcTmp.queryForObject(selectSearchCntData, Integer.class, "%"+name+"%");
	}
	
//	public boolean isExsitId(String id) {
//		boolean boo = true;
//		// false 일경우에 이미 있는 아이디 (사용할수 없다.)
//		// true 일 경우 사용가능하다는 뜻.
//		
//		int res = jdbcTmp.queryForObject(selectAjaxData, Integer.class, id);
//		if(res>0) {
//			boo=false;
//		}		
//		return boo;
//	}
	
	public String isExsitId(String id) {
		String str = "사용할 수 있는 아이디 입니다.";
		
		int res = jdbcTmp.queryForObject(selectAjaxData, Integer.class, id);
		if(res>0) {
			str = "이미 존재하는 아이디 입니다.";
		}		
		return str;
	}
	
	
	
	public AjaxDTO loginInfo(String id, String pw) {
		return jdbcTmp.queryForObject(loginData, new AjaxMapper(),id,pw);
	}
	
	
	class AjaxMapper implements RowMapper<AjaxDTO>{

		@Override
		public AjaxDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			AjaxDTO dto = new AjaxDTO();
			dto.setIdx(rs.getInt(1));
			dto.setId(rs.getString(2));
			dto.setPw(rs.getString(3));
			dto.setName(rs.getString(4));
			return dto;
		}
		
	}
	
}
