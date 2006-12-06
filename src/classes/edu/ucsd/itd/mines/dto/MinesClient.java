package edu.ucsd.itd.mines.dto;

import java.util.Date;

/**
 * The client information to persist for Mines.
 */
public class MinesClient extends BaseDataTransferObject {
    private String ipAddress;
    private String inetAtonAddress;
    private Date entryDate;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getInetAtonAddress() {
        return inetAtonAddress;
    }

    public void setInetAtonAddress(String inetAtonAddress) {
        this.inetAtonAddress = inetAtonAddress;
    }
}
