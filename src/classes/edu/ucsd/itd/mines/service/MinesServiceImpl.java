package edu.ucsd.itd.mines.service;

import edu.ucsd.itd.mines.dao.MinesClientDao;
import edu.ucsd.itd.mines.dao.SurveyDao;
import edu.ucsd.itd.mines.dto.MinesSurvey;
import edu.ucsd.itd.mines.dto.MinesClient;
import edu.ucsd.itd.mines.util.StringUtil;

/**
 * Mines Service Impl
 */
public class MinesServiceImpl implements MinesService {
    private SurveyDao surveyDao;
    private MinesClientDao minesClientDao;

    public void setMinesClientDao(MinesClientDao minesClientDao) {
        this.minesClientDao = minesClientDao;
    }

    public void setSurveyDao(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }

    /**
     * First saves the MinesSurvey, then the MinesClient based on IP address.
     *
     * @param minesSurvey MinesSurvey to persist
     * @param clientIp IP Address to use for lookup.
     */
    public void saveSurvey(MinesSurvey minesSurvey, String clientIp) {
        surveyDao.save(minesSurvey);

        if(StringUtil.isDefined(clientIp)) {
            MinesClient minesClient = minesClientDao.getByIpAddress(clientIp);

            // no result found, so create one.
            if(minesClient == null)
                minesClient = new MinesClient();

            // always set the ip address to use because if no result was found,
            // the ip address will be used to create a new record.
            minesClient.setIpAddress(clientIp);

            minesClientDao.save(minesClient);
        }
    }


}
