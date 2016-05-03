package edu.wctc.ajs.ajsmidtermapp.model;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Alyson
 */
public interface DBStrategy {

    public abstract void openConnection(String driverClass, String url,
            String userName, String password)
            throws DataAccessException;

    public abstract void closeConnection() throws DataAccessException;
    public abstract void openConnection(DataSource ds) throws DataAccessException;
    
    public abstract List<Map<String, Object>> findAllRecordsForTable(String tableName, int maxRecords) throws DataAccessException;
    
    public abstract Map<String, Object> findRecordById(String tableName, String primaryKeyColName, Object primaryKeyValue) throws DataAccessException;
    
    public abstract int deleteRecordById(String tableName, String columnName, Object primaryKey) throws DataAccessException;
    
    public abstract int createNewRecordInTable(String tableName, List colNames, List colData) throws DataAccessException;
    
    public abstract int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object pkValue) throws DataAccessException;
}
