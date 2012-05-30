package edu.ucsd.itd.mines.util;

import edu.ucsd.itd.mines.dto.MinesSurvey;
import edu.ucsd.itd.mines.SurveyField;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;

/**
 * Conversion utility to convert a collection of SurveyField
 * values to a MinesSurvey object.
 */
public class ConversionUtil {

    /**
     * Converts a typesafe map to a MinesSurvey instance. Whenever a field is added
     * to the SurveyField, this method should be adjusted accordingly.
     * @param map typesafe map of properties.
     * @return MinesSurvey with properties populated from the map.
     */
    public static MinesSurvey mapToMinesSurvey(Map<SurveyField, String> map) {
        MinesSurvey minesSurvey = new MinesSurvey();
        minesSurvey.setAcademicDepartment(map.get(SurveyField.ACADEMIC_DEPARTMENT));
        minesSurvey.setDestinationUrl(map.get(SurveyField.DESTINATION_URL));
        //minesSurvey.setPatronBehalfOf(map.get(SurveyField.PATRON_BEHALF_OF));
        minesSurvey.setPatronLocation(map.get(SurveyField.PATRON_LOCATION));
        minesSurvey.setPatronStatus(map.get(SurveyField.PATRON_STATUS));
        minesSurvey.setResearchType(map.get(SurveyField.RESEARCH_TYPE));
        minesSurvey.setSponsorProof(map.get(SurveyField.SPONSOR_PROOF));
        minesSurvey.setNewDestinationUrl(map.get(SurveyField.NEW_DESTINATION_URL));
        minesSurvey.setClientIpParam(map.get(SurveyField.CLIENT_IP_PARAM));

        return minesSurvey;
    }

    /**
     * Convenience method for converting an array of cookies to a
     * MinesSurvey
     * @param cookies Cookies to convert.
     * @return cookie values mapped to a MinesSurvey.
     */
    public static MinesSurvey cookiesToMinesSurvey(Cookie [] cookies) {
        // first populate cookies to a regular hashmap for convenience
        Map<String, String> map = new HashMap<String, String>();

        if(cookies != null) {
            for(Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie.getValue());
            }
        }

        // convert to stronger typed map
        Map<SurveyField, String> surveyFieldMap = new EnumMap<SurveyField, String>(SurveyField.class);
        for(SurveyField type : SurveyField.values()) {
            String value = map.get(type.getFieldName());
            surveyFieldMap.put(type, value);

        }

        return mapToMinesSurvey(surveyFieldMap);
    }
}