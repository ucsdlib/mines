package edu.ucsd.itd.mines.dto;

import edu.ucsd.itd.mines.util.FormattingUtil;
import edu.ucsd.itd.mines.util.StringUtil;

import java.util.Date;

/**
 * Survey data to persist for Mines.
 */
public class MinesSurvey extends BaseDataTransferObject {
    private String destinationUrl;
    private String patronStatus;
    private String patronBehalfOf;
    private String researchType;
    private String sponsorProof;
    private String academicDepartment;
    private String patronLocation;
    private String newDestinationUrl;
    private String clientIpParam;
    
    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    public String getPatronStatus() {
        return patronStatus;
    }

    public void setPatronStatus(String patronStatus) {
        this.patronStatus = patronStatus;
    }

    public String getPatronBehalfOf() {
        return patronBehalfOf;
    }

    public void setPatronBehalfOf(String patronBehalfOf) {
        this.patronBehalfOf = patronBehalfOf;
    }

    public String getResearchType() {
        return researchType;
    }

    public void setResearchType(String researchType) {
        this.researchType = researchType;
    }

    public String getSponsorProof() {
        return sponsorProof;
    }

    public void setSponsorProof(String sponsorProof) {
        this.sponsorProof = sponsorProof;
    }

    public String getAcademicDepartment() {
        return academicDepartment;
    }

    public void setAcademicDepartment(String academicDepartment) {
        this.academicDepartment = academicDepartment;
    }

    public String getPatronLocation() {
        return patronLocation;
    }

    public void setPatronLocation(String patronLocation) {
        this.patronLocation = patronLocation;
    }

    public String getNewDestinationUrl() {
        return newDestinationUrl;
    }

    public void setNewDestinationUrl(String newDestinationUrl) {
        this.newDestinationUrl = newDestinationUrl;
    }
    
    public String getClientIpParam() {
        return clientIpParam;
    }

    public void setClientIpParam(String clientIpParam) {
        this.clientIpParam = clientIpParam;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String dateStr = FormattingUtil.dateToStr(new Date());

        sb.append("\"").append(dateStr).append("\"");

        if(StringUtil.isDefined(newDestinationUrl))
            sb.append(", \"").append(newDestinationUrl).append("\"");
        if(StringUtil.isDefined(patronStatus))
            sb.append(", \"").append(patronStatus).append("\"");
        if(StringUtil.isDefined(patronBehalfOf))
            sb.append(", \"").append(patronBehalfOf).append("\"");
        if(StringUtil.isDefined(researchType))
            sb.append(", \"").append(researchType).append("\"");

        if(StringUtil.isDefined(sponsorProof))
            sb.append(" \"").append(sponsorProof).append("\"");
        else
            sb.append(" \"N/A\"");

        if(StringUtil.isDefined(academicDepartment))
            sb.append(" \"").append(academicDepartment).append("\"");
        else
            sb.append(" \"N/A\"");

        if(StringUtil.isDefined(patronLocation))
            sb.append(", \"").append(patronLocation).append("\"");

        if(StringUtil.isDefined(clientIpParam))
            sb.append(", \"").append(clientIpParam).append("\"");
        
        return sb.toString();
    }
}
