package edu.ucsd.itd.mines.dao;

import edu.ucsd.itd.mines.dto.MinesClient;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;

/**
 * Implementation using Spring's JdbcTemplate, a utility for performing JDBC based
 * operations. Using JdbcTemplate abstracts out the need to open and close connections and check for exceptions.
 */
public class MinesClientDaoJdbcTemplateImpl implements MinesClientDao {
    private static final String READ_SQL = "SELECT clm_source_ip FROM tbl_mines_client WHERE clm_source_ip = inet_aton(?)";
    private static final String INSERT_SQL = "INSERT INTO tbl_mines_client(clm_source_ip, clm_date_time) VALUES (inet_aton(?), now())";
    private static final String UPDATE_SQL = "UPDATE tbl_mines_client set clm_date_time = now() where clm_source_ip = inet_aton(?)";

    private JdbcTemplate jdbcTemplate;

    /**
     * Performs a SQL INSERT if date does not exist and UPDATE if date exists.
     * @param minesClient
     */
    public void save(MinesClient minesClient) {
        if(minesClient != null) {
            Object [] params = {minesClient.getIpAddress()};
            int [] types = {Types.VARCHAR};

            String sql;
            // Asserting that the inet aton address property indicates that a row exists
            if(minesClient.getInetAtonAddress() != null) {
                sql = UPDATE_SQL; // update if exists
            } else {
                sql = INSERT_SQL; // insert if new
            }

            jdbcTemplate.update(sql, params, types);
        }
    }

    /**
     * Performs a read.
     * @param ipAddress the IP Address to look up by.
     * @return a corresponding MinesClient or null if does not exist.
     */
    @SuppressWarnings("unchecked")
    public MinesClient getByIpAddress(String ipAddress) {
        Object [] params = {ipAddress};
        int [] types = {Types.VARCHAR};
        List<MinesClient>results = (List<MinesClient>)jdbcTemplate.query(READ_SQL, params, types, new MinesClientRowMapper());

        MinesClient minesClient;
        if(results != null && !results.isEmpty()) {
            minesClient = results.get(0);
        } else {
            minesClient = new MinesClient();
        }

        minesClient.setIpAddress(ipAddress);

        return minesClient;
    }

    /**
     * Sets the JdbcTemplate.
     * @param jdbcTemplate the JdbcTemplate to set.
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Inner class for performing 'OR/M' mapping for the MinesClient object.
     */
    class MinesClientRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int i) throws SQLException {
            MinesClient minesClient = new MinesClient();
            minesClient.setInetAtonAddress(rs.getString(1));
            return minesClient;
        }
    }
}
