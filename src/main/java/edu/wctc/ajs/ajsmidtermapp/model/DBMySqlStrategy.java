package edu.wctc.ajs.ajsmidtermapp.model;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import edu.wctc.ajs.ajsmidtermapp.exception.NullOrEmptyArgumentException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.sql.DataSource;

/**
 *
 * @author Alyson
 */
@Dependent
public class DBMySqlStrategy implements DBStrategy, Serializable {

    private Connection conn;

    /**
     * Opens a connection to the mySql database. Uses connection strings: driver
     * class, database url, database username and database password. Extends
     * DataAccess Exception? Yes. Checks for null or empty? Yes.
     *
     * @param driverClass MySql driver.
     * @param url Database URL.
     * @param userName Database username.
     * @param password Database password.
     * @throws DataAccessException custom exception class.
     */
    @Override
    public final void openConnection(String driverClass, String url, String userName, String password) throws DataAccessException {
        if (driverClass == null || driverClass.isEmpty() || url == null
                || url.isEmpty() || userName == null || userName.isEmpty()
                || password == null || password.isEmpty()) {
            throw new NullOrEmptyArgumentException();
        } else {
            try {
                Class.forName(driverClass);
                conn = DriverManager.getConnection(url, userName, password);
            } catch (ClassNotFoundException | SQLException e) {
                throw new DataAccessException(e.getMessage(), e.getCause());
            }
        }
    }

    /**
     * Open a connection using a connection pool configured on server. Extends
     * DataAccessException? Yes Checks for null or empty? Yes
     *
     * @param ds - A reference to a connection pool via a JNDI name, producing
     * this object. Typically done in a servlet using InitalContext object.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException Custom
     * exception class.
     */
    @Override
    public final void openConnection(DataSource ds) throws DataAccessException {
        if (ds == null) {
            throw new NullOrEmptyArgumentException();
        }
        try {
            conn = ds.getConnection();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Closes database connection with MySql Database. Extends
     * DataAccessException? Yes N
     *
     * @throws DataAccessException Custom Exception Class.
     */
    @Override
    public final void closeConnection() throws DataAccessException {
        try {
            conn.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Make sure you open and close a connection when using this method. Future
     * optimizations may include changing the return type to an array. Extends
     * DataAccess Exception? Yes; Checks for null or empty params? yes; Final?
     * yes;
     *
     * @param tableName Name of the table being accessed.
     * @param maxRecords - Limit records found to first maxRecords or if
     * maxRecords is zero (0) then no limit.
     * @return A list of maps containing a string as the key and an object as
     * the value.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException Custom
     * Exception class.
     */
    @Override
    public final List<Map<String, Object>> findAllRecordsForTable(String tableName, int maxRecords) throws DataAccessException {
        if (tableName == null || tableName.isEmpty() || maxRecords < 0) {
            throw new NullOrEmptyArgumentException();
        } else {
            String sql;
            if (maxRecords < 1) {
                sql = "SELECT * FROM " + tableName;
            } else {
                sql = "SELECT * FROM " + tableName + " LIMIT " + maxRecords;
            }

            Statement smt;
            try {
                smt = conn.createStatement();

                ResultSet rs = smt.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                List<Map<String, Object>> records = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> record = new HashMap<>();
                    for (int colNo = 1; colNo <= columnCount; colNo++) {
                        Object colData = rs.getObject(colNo);
                        String colName = rsmd.getColumnName(colNo);
                        record.put(colName, colData);
                    }
                    records.add(record);
                }
                return records;
            } catch (SQLException ex) {
                throw new DataAccessException(ex.getMessage(), ex.getCause());
            }
        }
    }

    /**
     * Make sure to open and close and connection string when using this method.
     * Finds a record by it's specific primary key id. DataAccess Exception?
     * Yes; Checks for null or empty params? Yes; Final? Yes;
     *
     * @param tableName Name of the table in database that is being searched.
     * @param primaryKeyColName The column name of the primary key.
     * @param primaryKeyValue The id/ unique primary key value being found.
     * @return A map of the specified record from the table being searched.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException
     */
    @Override
    public final Map<String, Object> findRecordById(String tableName, String primaryKeyColName, Object primaryKeyValue) throws DataAccessException {
        if (tableName == null || tableName.isEmpty() || primaryKeyColName == null || primaryKeyColName.isEmpty() || primaryKeyValue == null) {
            throw new NullOrEmptyArgumentException();
        } else {
            String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyColName + " = ?";

            PreparedStatement smt = null;
            Map<String, Object> record = new HashMap();

            try {
                smt = conn.prepareStatement(sql);
                smt.setObject(1, primaryKeyValue);
                ResultSet rs = smt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                if (rs.next()) {
                    for (int colNo = 1; colNo <= columnCount; colNo++) {
                        Object colData = rs.getObject(colNo);
                        String colName = rsmd.getColumnName(colNo);
                        record.put(colName, colData);
                    }
                }
            } catch (SQLException ex) {
                throw new DataAccessException(ex.getMessage(), ex.getCause());
            } finally {
                try {
                    smt.close();
                    conn.close();
                } catch (SQLException ex) {
                    throw new DataAccessException(ex.getMessage(), ex.getCause());
                }

            }
            return record;
        }

    }

    /**
     * Make sure you open and close a connection when using this method. Method
     * deletes a single record in a table. Decision on the keys value which has
     * been brought in as an object is decided through an instanceOf statement.
     * Only set up currently for String and Int. Returns int to show how many
     * records have been deleted.
     *
     * DataAccess Exception? Yes; Checks for null or empty params? Yes ; Final?
     * Yes;
     *
     * @param tableName Name of the table being accessed.
     * @param columnName Name of the primary key column.
     * @param primaryKey Value of the primary key.
     * @return Returns the number or records that have been deleted.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException Custom
     * Exception Class.
     */
    @Override
    public final int deleteRecordById(String tableName, String columnName, Object primaryKey) throws DataAccessException {
        if (tableName.isEmpty() || columnName.isEmpty() || primaryKey == null) {
            throw new NullOrEmptyArgumentException();
        } else {
            PreparedStatement deleteRecord = null;
            String deleteQryString = null;
            deleteQryString = "DELETE FROM " + tableName + " WHERE " + columnName + "=?";

            try {
                deleteRecord = conn.prepareStatement(deleteQryString);
                deleteRecord.setObject(1, primaryKey);
                int result = deleteRecord.executeUpdate();
                return result;
            } catch (SQLException ex) {
                throw new DataAccessException(ex.getMessage(), ex.getCause());
            }
        }

    }

    /**
     * Make sure you open and close a connection when using this method. Creates
     * a new record in the table specified, and the lists of column names and
     * data to be put into that record. DataAccess Exception? Yes; Checks for
     * null or empty params? yes; Final? yes;
     *
     * @param tableName name of the table being accessed
     * @param colNames name of the columns from the table to apply values to
     * @param colData values to be added to the columns in the table. Values
     * must match up with the column names.
     * @return number of records created
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException
     */
    @Override
    public final int createNewRecordInTable(String tableName, List colNames, List colData) throws DataAccessException {
        if (tableName == null || tableName.isEmpty() || colNames == null || colData == null) {
            throw new NullOrEmptyArgumentException();
        } else {
            PreparedStatement createRecord = null;
            int result;
            try {
                createRecord = buildInsertStatement(conn, tableName, colNames);
                int colDataSize = colData.size();
                for (int i = 0; i < colDataSize; i++) {
                    Object obj = colData.get(i);
                    createRecord.setObject((i + 1), obj);
                }

                result = createRecord.executeUpdate();
            } catch (SQLException sql) {
                throw new DataAccessException(sql.getMessage(), sql.getCause());
            } finally {
                try {
                    createRecord.close();
                    conn.close();
                } catch (SQLException ex) {
                    throw new DataAccessException(ex.getMessage(), ex.getCause());
                }

            }
            return result;
        }
    }

    /**
     * Make sure you open and close a connection when using this method. Method
     * updates a single record by it's ID/ primary key and specified ID/primary
     * key column name. Passes in a list of column names to be updates and a
     * list of values to be updated. DataAccess Exception? Yes; Checks for null
     * or empty params? Yes; Final? Yes;
     *
     * @param tableName Name of the table being accessed
     * @param colNames Names of the columns being updated in the table
     * @param colValues Values of the columns being updated. Must match up in
     * order to the col names to be updated.
     * @param pkColName Primary key column name.
     * @param pkValue Primary key value for specific record being updated.
     * @return Number of records updated.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException Custom
     * Exception Class.
     */
    @Override
    public final int updateRecordById(String tableName, List<String> colNames,
            List<Object> colValues, String pkColName,
            Object pkValue) throws DataAccessException {
        if (tableName == null || tableName.isEmpty() || colNames == null
                || colValues == null || pkColName == null || pkColName.isEmpty()
                || pkValue == null) {
            throw new NullOrEmptyArgumentException();
        } else {
            PreparedStatement pstmt = null;
            int recsUpdated = 0;
            try {
                pstmt = buildUpdateStatement(conn, tableName, colNames, pkColName);

                final Iterator i = colValues.iterator();
                int index = 1;
                Object obj;
                while (i.hasNext()) {
                    obj = i.next();
                    pstmt.setObject(index++, obj);
                }
                pstmt.setObject(index, pkValue);

                recsUpdated = pstmt.executeUpdate();

            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage(), e.getCause());
            } finally {
                try {
                    pstmt.close();
                    conn.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e.getMessage(),e.getCause());
                }
            }

            return recsUpdated;
        }
    }

    /**
     * Builds a java.sql.PreparedStatement for an sql update using only one
     * where clause test.
     *
     * @param conn - a JDBC <code>Connection</code> object
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - a <code>List</code> containing the column
     * descriptors for the fields that can be updated.
     * @param whereField - a <code>String</code> representing the field name for
     * the search criteria.
     * @return java.sql.PreparedStatement
     * @throws SQLException
     */
    private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName,
            List colDescriptors, String whereField)
            throws SQLException {
        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(" = ?, ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));
        ((sql.append(" WHERE ")).append(whereField)).append(" = ?");
        final String finalSQL = sql.toString();
        return conn_loc.prepareStatement(finalSQL);
    }

    /**
     * Builds a java.sql.PreparedStatement for an sql insert
     *
     *
     * @param conn - a valid connection
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - a <code>List</code> containing the column
     * descriptors for the fields that can be inserted.
     * @return java.sql.PreparedStatement
     * @throws DataAccessException
     */
    private PreparedStatement buildInsertStatement(Connection conn, String tableName, List colDescriptors)
            throws SQLException {
        StringBuffer sql = new StringBuffer("INSERT INTO ");
        (sql.append(tableName)).append(" (");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(", ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ") VALUES (");
        for (int j = 0; j < colDescriptors.size(); j++) {
            sql.append("?, ");
        }
        final String finalSQL = (sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ")";
        PreparedStatement psmt = null;
        try {
            psmt = conn.prepareStatement(finalSQL);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e.getCause());
        }
        return psmt;
    }

    //CHECKS
//    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
//        DBStrategy db = new DBMySqlStrategy();
//
//        db.openConnection("com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book",
//                "root", "admin");
//        List<Map<String, Object>> rawData = db.findAllRecordsForTable("author", 0);
//        System.out.println(rawData);
////        int result = db.deleteRecordById("author", "author_id", 8);
////        System.out.println(result);;
//        db.closeConnection();
//        db.openConnection("com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book",
//                "root", "admin");
//        List data = new ArrayList(Arrays.asList("John Green",
//                "2007-01-02"));
//        List colNames = new ArrayList(Arrays.asList("author_name", "date_added"));
//        int result = db.createNewRecordInTable("author", colNames, data);
////        List<String> colNames = Arrays.asList("author_name", "date_added");
////        List<Object> colValues = Arrays.asList("My Little Pony Rainbow Dash", "1993-10-31");
////        int result = db.updateRecordById("author", colNames, colValues, "author_id", 2);
////        db.closeConnection();
////        db.openConnection("com.mysql.jdbc.Driver",
////                "jdbc:mysql://localhost:3306/book",
////                "root", "admin");
//        db.closeConnection();
//        db.openConnection("com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book",
//                "root", "admin");
//        rawData = db.findAllRecordsForTable("author", 0);
//
//        System.out.println(rawData);
//        //Map<String, Object> result = db.findRecordById("author", "author_id", "1");
//        db.closeConnection();
//        System.out.println(result);
//
//    }
}
