package edu.ucsd.itd.mines;

/**
 * Typesafe cookie names.
 */
public enum SurveyField {
    DESTINATION_URL("destination_url"),
    NEW_DESTINATION_URL("new_destination_url"),
    PATRON_STATUS("patron_status"),
    CLIENT_IP_PARAM("client_ip_param"),
    //PATRON_BEHALF_OF("patron_behalf_of"),
    RESEARCH_TYPE("research_type"),
    SPONSOR_PROOF("sponsor_proof"),
    ACADEMIC_DEPARTMENT("academic_department"),
    PATRON_LOCATION("patron_location");

    private String cookieName;

    SurveyField(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getFieldName() {
        return this.cookieName;
    }
}
