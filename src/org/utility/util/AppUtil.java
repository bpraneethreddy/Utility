package org.utility.util;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class contains globally used methods.
 * 
 * @author Swetha.P
 *
 */
public class AppUtil {
	
	/**
	 * Parses the given object to Boolean Object type
	 * @param Object
	 * @return returns boolean value
	 */
	public static Boolean parseBoolean(Object o) {
		String str = o == null ? "false" : o.toString().trim();
		if (parseInt(str) == 1)
			return true;
		if (isBlank(str))
			return false;
		else {
			try {
				return Boolean.parseBoolean(str);
			} catch (Exception e) {
				return false;
			}
		}
	}

	
	/**
	 * Return the file extension by passing the file name
	 * 
	 * @param name
	 * @return String
	 * @throws Exception
	 */
	public static String getFileExtentionByName(String name) throws Exception {
		int dotIndex = name.lastIndexOf('.');
		if (dotIndex == -1)
			return "";
		else
			return name.substring(dotIndex + 1, name.length());
	}
	
	/**
	 * This will return file name by taking entire file path
	 * 
	 * @param path
	 * @param pathSeparator
	 * @return
	 */
	public static String getFileNameByFilePath(String path, String pathSeparator) { // gets
																					// filename
																					// without
																					// extension
		int dot = path.length();
		int sep = path.lastIndexOf(pathSeparator);
		return path.substring(sep + 1, dot);
	}
	
	/**
	 * Creates a file if it does not exists in the given directory
	 * @param path
	 * @return File
	 * @throws Exception
	 */
	public static File saveFileIfNotExist(String path) throws Exception {
		if (isBlank(path)) {
			return null;
		}
		File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		return file;
	}
	
	/**
	 * Trims the given string to the specified length provided
	 * @param s
	 * @param len
	 * @return String
	 */
	public static String truncate(String s,int...len) {
		if(len==null || len.length==0)len=new int[] {1000};
		return s==null?null:(s.length()<=len[0]?s:s.substring(0, len[0]));
	}

	/**
	 * Converts given JSON String to Map
	 * @param s
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static final Map<String,Object> jsonToObject(String s) {
		try{return new ObjectMapper().readValue(s==null?null:s, Map.class);}catch (Exception e) {return null;}
	}
	
	/**
	 * Given a <code>String</code>, replaces all occurrances of a given
	 * <code>String</code> with the new <code>String</code>.
	 * 
	 * @param String
	 *            - Original string
	 * @param String
	 *            - String to replace
	 * @param String
	 *            - Value to replace with
	 * 
	 * @return String
	 */
	public static String replace(String sOriginal, String sReplaceThis, String sWithThis) {
		int start = 0;
		int end = 0;
		StringBuffer sbReturn = new StringBuffer();

		if (sOriginal != null) {
			while ((end = sOriginal.indexOf(sReplaceThis, start)) >= 0) {
				sbReturn.append(sOriginal.substring(start, end));
				sbReturn.append(sWithThis);
				start = end + sReplaceThis.length();
			}
			sbReturn.append(sOriginal.substring(start));
		} else {
			sbReturn.append("");
		}

		return (sbReturn.toString());
	}	
	
	/**
	 * Removes unwanted spaces of the given object
	 * @param o
	 * @return String
	 */
	public static String trim(Object o) {
		return o==null?null:o.toString().trim();
	}
	
	/**
	 * Convenience method to determine if a Object is null or blank
	 * 
	 * @param Object
	 * @return boolean
	 */
	public static boolean isBlank(Object o) {
		if (o == null)
			return true;
		else if (o instanceof String) {
			if (((String) o).trim().equals(""))
				return true;
		} else if (o instanceof Collection<?>) {
			if (((Collection<?>) o).isEmpty())
				return true;
		} else if (o instanceof Integer) {
			if (((Integer) o) <= 0)
				return true;
		} else if (o instanceof Long) {
			if (((Long) o) <= 0)
				return true;
		} else if (o instanceof Short) {
			if (((Short) o) <= 0)
				return true;
		} else if (o instanceof Byte) {
			if (((Byte) o) <= 0)
				return true;
		} else if (o instanceof Double) {
			if (((Double) o) <= 0)
				return true;
		} else if (o instanceof Float) {
			if (((Float) o) <= 0)
				return true;
		} else if (o instanceof Map<?, ?>) {
			if (((Map<?, ?>) o).isEmpty())
				return true;
		}
		return false;
	}

	/**
	 * Parses the given object to Integer Object type
	 * @param o
	 * @return Integer
	 */
	public static Integer parseInt(Object o) {
		if (o == null)
			return null;
		String str = o.toString().trim();
		if (isBlank(str))
			return 0;
		else {
			try {
				return (int) Integer.parseInt(str);
			} catch (Exception e) {
				return 0;
			}
		}
	}
	
	/**
	 * Converts the given input string to TitleCase, which is to remove underscores and replace it with spaces
	 * 	and Capitalize the first letter of all words.
	 * @param input
	 * @return String
	 */
	public static String titleCase(String input) {
		if(input==null) return null;
		if(input.trim().equals("")) return "";
		input=input.replace("_", " ").toLowerCase();
		String result = "";
        char firstChar = input.charAt(0);
        result = result + Character.toUpperCase(firstChar);
        for (int i = 1; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            char previousChar = input.charAt(i - 1);
            if (previousChar == ' ') {
                result = result + Character.toUpperCase(currentChar);
            } else {
                result = result + currentChar;
            }
        }
		return result;
	}
	
	/**
	 * Converts the given String to pascalCase
	 * @param input
	 * @return
	 */
	public static String pascalCase(String input) {
		if(input==null) return null;
		if(input.trim().equals("")) return "";
		input=titleCase(input);
		input=input.replaceAll("\\s+", "");
		char c=(input.charAt(0)+"").toLowerCase().charAt(0);
		return c+input.substring(1);
	}
	
	

	/**
	 * Get the difference between two dates in days. Disregard time.
	 * 
	 * @param Date
	 *            from
	 * @param Date
	 *            to
	 * @return int number of days
	 */
	public static int getDays(Date date1, Date date2) {
		int elapsed = 0;
		Calendar calendar1 = Calendar.getInstance(), calendar2 = Calendar.getInstance();
		if (isBlank(date1))
			return 0;
		if (date2.after(date1)) {
			calendar2.setTime(date2);
			calendar1.setTime(date1);
		} else {
			calendar2.setTime(date1);
			calendar1.setTime(date2);
		}
		calendar1.clear(Calendar.MILLISECOND);
		calendar1.clear(Calendar.SECOND);
		calendar1.clear(Calendar.MINUTE);
		calendar1.clear(Calendar.HOUR_OF_DAY);
		calendar2.clear(Calendar.MILLISECOND);
		calendar2.clear(Calendar.SECOND);
		calendar2.clear(Calendar.MINUTE);
		calendar2.clear(Calendar.HOUR_OF_DAY);
		while (calendar1.before(calendar2)) {
			calendar1.add(Calendar.DATE, 1);
			elapsed++;
		}
		return elapsed;
	}

	/**
	 * @author Mahesh.J
	 * @param request
	 * @return returns user agent details in a Map object
	 * @since 2018-02-12
	 */
	public static Map<String, String> getClientDetails(HttpServletRequest request) {
		String browserDetails = request.getHeader("User-Agent");
		if (browserDetails == null)
			browserDetails = "No-Data";
		String userAgent = browserDetails;
		String user = userAgent.toLowerCase();
		String ip = request.getRemoteAddr();
		String os = "";
		String browser = "";

		// =================OS=======================
		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		// ===============Browser===========================
		if (user.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera"))
				browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			else if (user.contains("opr"))
				browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
						.replace("OPR", "Opera");
		} else if (user.contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)
				|| (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1)
				|| (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
			browser = "Netscape-?";

		} else if (user.contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			browser = "IE";
		} else {
			browser = "UnKnown, More-Info: " + userAgent;
		}
		//if(ZcUtil.isBlank(request.getHeader(name))
		Map<String, String> result = new HashMap<String, String>();
		result.put("os", os);
		result.put("browser", browser);
		result.put("ip", ip);
		result.put("agent", browserDetails);
		return result;
	}

	/**
	 * @author Mahesh.J
	 * @param name
	 * @return returns a string replaced with data base MySql Like search format ,
	 *         for example "%str%"
	 * @since 2018-02-12
	 */
	public static String likeSearchString(Object str) {
		str = str == null ? "" : str.toString().trim().toLowerCase();
		return "%" + str + "%";
	}
	public static String getDbIdentifierName(String name,String prefix, String langMsg)throws Exception {
		return getDbIdentifierName(name, prefix, null);
	}
	
	/**
	 * @author Mahesh.J
	 * @param name
	 * @return returns given string replaced all white spaces with _
	 * @since 2018-02-12
	 */
	public static String getNameForFile(String name) {
		String retName = "";
		if (name == null)
			retName = "";
		else
			retName = name;
		retName = retName.trim().replaceAll("\\s{2,}", " ");
		return retName.replaceAll(" ", "_");
	}
	
	/**
	 * This method will generate random string base on length given
	 * 
	 * @author Mahesh.J
	 * @since 2018-02-15
	 * @param lenOfStr
	 * @return random string
	 */
	public static String generateRandomString(int lenOfStr) {
		Random random = new Random();
		String str = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder randomStr = new StringBuilder();
		for (int i = 0; i < lenOfStr - 1; i++) {
			randomStr.append(str.charAt(random.nextInt(str.length())));
		}
		return randomStr.toString();
	}
	
	
	/**
	 * @author Mahesh.J
	 * @since 2018-02-17
	 * @param s
	 * @return String 
	 * @throws Exception
	 */
	public static String encodeUrl(String s) throws Exception {
		URL url = null;
		try {
			url = new URL(s);
		} catch (MalformedURLException mue) {
			// throw new Exception(mue);
			return URLEncoder.encode(s, "UTF-8");
		}
		try {
			return URLEncoder.encode(url.toString(), "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			throw new Exception(uee);
		}
	}

	
	/**
	 * This will make the given data into a proper name by removing the spaces
	 * 
	 * @author Jagadeesh.T
	 * @param data
	 * @return converted data
	 */
	public static String makeName(String...data) {
		String result="";
		boolean flag=false;
		for(String s:data) {
			result+=(isBlank(result)?"":" ")+(isBlank(s)?"":s.trim());
			if(!isBlank(s)) flag=true;
		}
		return flag==false?null:result;//titleCase(result);
	}

	
	
	/**
	 * This method takes Date as input and gives UTC formatted date in return
	 * 
	 * @author Jagadeesh.T
	 * @param date
	 * @return UTC formatted date
	 */
	public static String getUtcStringFromDate(Date date){
		if(date==null) return null;
		return date.toInstant().toString();
	}
	
	/**
	 * This method return the current date
	 * 
	 * @author Jagadeesh.T
	 * @return current date
	 */
	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * Copies single file from one location to another
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFile(File source, File dest, boolean deleteSource) throws IOException {
		if (!dest.exists()) {
			dest.getParentFile().mkdirs();
			dest.createNewFile();
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(source);
			out = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} finally {
			in.close();
			out.close();
			if (deleteSource) {
				source.delete();
			}
		}

	}	
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	/**
	 * This method will format the given text
	 * 
	 * @author Jagadeesh.T
	 * @param text
	 * @return
	 */
	public static String printText(String text){
		try {
			int []wh=new int[] {600,100};
			BufferedImage image = new BufferedImage(wh[0], wh[1], BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			g.setFont(new Font("Serief", Font.BOLD, 12));
			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
			        RenderingHints.VALUE_STROKE_DEFAULT);
			graphics.drawString(text, 5, 12);
			StringBuilder result = new StringBuilder();
			for (int y = 0; y < wh[1]; y++) {
			    StringBuilder sb = new StringBuilder();
			    for (int x = 0; x < wh[0]; x++)
			        sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");
			    if (sb.toString().trim().isEmpty()) continue;
			    	//System.out.println(sb);
			    result.append(result.length()==0?sb:"\n"+sb);
			}
			return result.toString();
		}catch (Exception e) {
			return "";
		}
	}		
	public static void main(String[] args) throws Exception {
		Date d=new Date();
		String s=getUtcStringFromDate(d);
		System.out.println(d);
		System.out.println(s);
		System.out.println(parseInt(null));
	}

}

