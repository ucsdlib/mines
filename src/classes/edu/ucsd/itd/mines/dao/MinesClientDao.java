package edu.ucsd.itd.mines.dao;
import edu.ucsd.itd.mines.dto.MinesClient;

/**
 * Access layer to the database
 */
public interface MinesClientDao {
    public void save(MinesClient minesClient);
    public MinesClient getByIpAddress(String ipAddress);

}
