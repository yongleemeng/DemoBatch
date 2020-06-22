package com.LoadCsvIntoDb.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.logging.Logger;

import com.LoadCsvIntoDb.demo.model.Transaction;

public class CommonBatchUtils {

	private static Logger logger = Logger.getLogger(CommonBatchUtils.class);

	/***
	 * Get date from String
	 * 
	 * @param strDate YYYY-mm-DD
	 * @param strTime HH:MM:SS
	 * @return
	 */
	public static Date getDateTimeFromString(String strDate, String strTime) throws ParseException {
		Date createDate = null;
		if (strDate != null && strTime != null) {
			SimpleDateFormat fmt = new SimpleDateFormat(CommonConstants.DATE_DB_FORMAT);
			createDate = fmt.parse(strDate + " " + strTime);
		}
		return createDate;
	}

}
