package match.observer.handle.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class HandleUtils {

	private final static Logger logger = Logger.getLogger(HandleUtils.class);
	
	/**
	 * Return the index of string KILLED 
	 * @param st
	 * @return
	 */
	public static int middlePos(String[] st) {
		return search(st,HandleConstants.KILLED);
	}	
	
	public static int search(String[] st, String key){
		for (int i = 0; i < st.length; i++) {
			String string = st[i];
			if (string.equalsIgnoreCase(key)){
				return i;
			}
		}
		return -1;		
	}
	
	
	public static Date parseToDate(String st) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		return sdf.parse(st);
	}
	
	public static void validateLineLog(String line){
		if (isValidString(line)){
			logger.debug("Valide log line:"+line);
		} else {
			throw new IllegalArgumentException("Invalid log line:"+line);
		}
	}	
	
	public static boolean isValidString(String st){
		return (st != null) && 
		       (st.length() > 0) && 
		       (!StringUtils.containsOnly(st, HandleConstants.SPACE));		
	}
}
