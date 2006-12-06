package edu.ucsd.itd.mines.dao;

import edu.ucsd.itd.mines.dto.MinesSurvey;
import edu.ucsd.itd.mines.MinesUtilities;

/**
 * File persistence implementation.
 */
public class SurveyDaoFileImpl implements SurveyDao {
    private String filePath;

    /**
     * File path to persist log to.
     * @param filePath the path to persist the log to.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void save(MinesSurvey minesSurvey) {
        String surveyLog = minesSurvey.toString();

        // wrap in stringbuffer and add newline
        StringBuffer buf = new StringBuffer(surveyLog);
        buf.append("\n");

        MinesUtilities.writeToFile(filePath, buf);
    }
}
