package edu.ucsd.itd.mines.service;

import edu.ucsd.itd.mines.dto.MinesSurvey;

/**
 * Encapsulates Business Logic.
 */
public interface MinesService {

    /**
     * Business logic to saves a survey.
     * @param minesSurvey the survey to save.
     * @param ip the IP address to use for lookup.
     */
    public void saveSurvey(MinesSurvey minesSurvey, String ip);
}
