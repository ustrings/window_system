package com.hidata.framework.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.BeanOperator.SqlTypes;

@Component
public class JDBCTemplateManager implements DBManager {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getTemplate() {
        return jdbcTemplate;
    }

    /**
     * 插入/更新/删除
     *
     * @param sql
     * @param args
     * @return 本次更新影响的行数
     */
    public int update(String sql, Object[] args) {
        int result = -1;
        result = jdbcTemplate.update(sql, args);
        return result;
    }

    /**
     * 批量插入/更新/删除
     *
     * @param sql
     * @param batchArgs
     * @return 每一条的状态
     */
    public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
        int[] result = {};
        result = jdbcTemplate.batchUpdate(sql, batchArgs);
        return result;
    }

    /**
     * 查询列表
     *
     * @param sql
     * @param args
     * @return 查询的数据列表
     */
    public List<Map<String, Object>> queryForList(String sql, Object[] args) {
        List<Map<String, Object>> result = null;
        result = jdbcTemplate.queryForList(sql, args);
        return result;
    }

    public <T> int insertObject(T obj) {
        String sql = BeanOperator.getSqlByObject(SqlTypes.INSERT, obj);
        return jdbcTemplate.update(sql);
    }
    
    /**
     * 插入一个对象，并返回这个对象的自增id
     * @param obj
     * @return
     */
    public <T> int insertObjectAndGetAutoIncreaseId(T obj) {
        final String sql = BeanOperator.getSqlByObject(SqlTypes.INSERT, obj);
        
		KeyHolder keyHolder = new GeneratedKeyHolder();
        int autoIncId = 0;
        
        jdbcTemplate.update(new PreparedStatementCreator() {
    		public PreparedStatement createPreparedStatement(Connection con)
    				throws SQLException {
    			PreparedStatement ps = jdbcTemplate
    					.getDataSource()
    					.getConnection()
    					.prepareStatement(sql,
    							PreparedStatement.RETURN_GENERATED_KEYS);
    			return ps;
    		}
    	}, keyHolder);

        autoIncId = keyHolder.getKey().intValue();
    	
        return autoIncId;
    }
    
    
    
	

    /**
     * @param sql
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
        return jdbcTemplate.queryForList(sql);
    }

    
    public <T> List<T> queryForListObject(String sql, Class<T> elementType) throws DataAccessException {
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        //使用了bean builder
        return BeanOperator.listMap2Object(resultList, elementType);
    }

    public <T> List<T> queryForListObject(String sql, Object[] args, Class<T> elementType) throws DataAccessException {
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, args);
        //使用了bean builder
        return BeanOperator.listMap2Object(resultList, elementType);
    }

    
    public <T> List<T> queryForListObject(String sql, Class<T> elementType, int page, int pageSize) throws DataAccessException {
        int startIndex = page * pageSize;
        int endIndex = (page + 1) * pageSize;
        sql = "select * from (select rOraclePageSQL.*,ROWNUM as currentRow from (" +
                sql + ") rOraclePageSQL where rownum <=" + endIndex + ") where currentRow>" + startIndex;

        return queryForListObject(sql, elementType);
    }


    /**
     * 分页查询列表
     *
     * @param sql         原始sql
     * @param args        参数
     * @param elementType 返回对象类型
     * @param rowNum      起始行
     * @param limit       查询的行数
     * @return
     * @throws DataAccessException
     */
    
    public <T> List<T> queryForListObject(String sql, Object[] args, Class<T> elementType, int page, int pageSize) throws DataAccessException {
        int startIndex = page * pageSize;
        int endIndex = (page + 1) * pageSize;
        sql = "select * from (select rOraclePageSQL.*,ROWNUM as currentRow from (" +
                sql + ") rOraclePageSQL where rownum <=" + endIndex + ") where currentRow>" + startIndex;

        return queryForListObject(sql, args, elementType);
    }

    /**
     * 分页查询列表
     *
     * @param sql         原始sql
     * @param args        可变参数
     * @param elementType 返回对象类型
     * @param rowNum      起始行
     * @param limit       查询的行数
     * @return
     * @throws DataAccessException
     */
    
    public <T> List<T> queryForListObject(String sql, Class<T> elementType, int page, int pageSize, Object... args) throws DataAccessException {
        int startIndex = page * pageSize;
        int endIndex = (page + 1) * pageSize;
        sql = "select * from (select rOraclePageSQL.*,ROWNUM as currentRow from (" +
                sql + ") rOraclePageSQL where rownum <=" + endIndex + ") where currentRow>" + startIndex;

        return queryForListObject(sql, elementType, args);
    }

    
    public <T> List<T> queryForListObject(String sql, Class<T> elementType, Object... args) throws DataAccessException {
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, args);
        //使用了bean builder
        return BeanOperator.listMap2Object(resultList, elementType);
    }

    
    public Map<String, Object> queryForMap(String sql) throws DataAccessException {
        try {
            return jdbcTemplate.queryForMap(sql);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    
    public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {
        try {
            return jdbcTemplate.queryForMap(sql, args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    
    public Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes)
            throws DataAccessException {
        try {
            return jdbcTemplate.queryForMap(sql, args, argTypes);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    
    public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
        try {
            Map<String, Object> resultMap = this.queryForMap(sql);
            //使用了bean builder
            return BeanOperator.map2Object(resultMap, requiredType);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    
    public long queryForLong(String sql) throws DataAccessException {
        try {
            return jdbcTemplate.queryForLong(sql);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    
    public int queryForInt(String sql) throws DataAccessException {
        try {
            return jdbcTemplate.queryForInt(sql);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    
    public int update(String sql) throws DataAccessException {
        return jdbcTemplate.update(sql);
    }

    
    public int[] batchUpdate(String[] sql) throws DataAccessException {
        return jdbcTemplate.batchUpdate(sql);
    }

    
    public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) throws DataAccessException {
        try {
            Map<String, Object> resultMap = this.queryForMap(sql, args);
            //使用了bean builder
            return BeanOperator.map2Object(resultMap, requiredType);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    
    public <T> T queryForObject(String sql, Class<T> requiredType,
                                Object... args) throws DataAccessException {
        try {
            Map<String, Object> resultMap = this.queryForMap(sql, args);
            //使用了bean builder
            return BeanOperator.map2Object(resultMap, requiredType);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    
    public long queryForLong(String sql, Object... args)
            throws DataAccessException {
        try {
            // TODO Auto-generated method stub
            return jdbcTemplate.queryForLong(sql, args);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    
    public int queryForInt(String sql, Object... args)
            throws DataAccessException {
        try {
            // TODO Auto-generated method stub
            return jdbcTemplate.queryForInt(sql, args);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    
    public int[] batchUpdate(String sql, List<Object[]> batchArgs,
                             int[] argTypes) {
        return jdbcTemplate.batchUpdate(sql, batchArgs, argTypes);
    }

    public DataSource getDataSource() {
        return jdbcTemplate.getDataSource();
    }

    
    public <T> T queryForBean(String sql, Object[] parameter, Class<T> type) {
        try {
            return jdbcTemplate.queryForObject(sql, parameter, BeanPropertyRowMapper.newInstance(type));
        } catch (DataAccessException e) {
            return null;
        }
    }
}
