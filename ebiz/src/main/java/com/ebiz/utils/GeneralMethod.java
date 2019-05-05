package com.ebiz.utils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.mail.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * general method to deal with time, data and files.
 * @author tgxfff
 *
 */

public class GeneralMethod {
	public  static int getYear(long timeMillis){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(timeMillis);
		return gc.get(Calendar.YEAR);
	}
	public static Calendar getCalendar(long timeMillis){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(timeMillis);
		return gc;
	}
	/**
	 * return time at 00:00:00 of the same day for time zone (New_York)
	 * @param timeMillis
	 * @return
	 */
	public static long getTimeMillisOfBeginningOfDay(long timeMillis){
		GregorianCalendar gc = new GregorianCalendar();

		gc.clear();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(timeMillis);
		//gc.clear(Calendar.HOUR); // clear function does not set it to zero
		//gc.clear(Calendar.MINUTE);
		//gc.clear(Calendar.SECOND);
		//gc.clear(Calendar.MILLISECOND);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);
		return gc.getTimeInMillis();
	}
	public static long getTimeMillisOfBeginningOfMinute(long timeMillis){
		GregorianCalendar gc = new GregorianCalendar();
		gc.clear();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(timeMillis);
		//gc.clear(Calendar.SECOND);// clear function does not set it to zero
		//gc.clear(Calendar.MILLISECOND);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);
		return gc.getTimeInMillis();
	}
	public  static int getMonth(long timeMillis){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(timeMillis);
		return gc.get(Calendar.MONTH)+1;
	}

	public static long getTimeMillisOfLastMonthBeginning(long timeMillis){
		int year=getYear(timeMillis);
		int month=getMonth(timeMillis);
		GregorianCalendar gc = new GregorianCalendar();
		gc.clear();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.set(year, month, 0);
		gc.add(Calendar.MONTH, -1);
		return gc.getTimeInMillis();
	}
	public static boolean isSameMonth(long timeMillis01,long timeMillis02){
		GregorianCalendar gc01 = new GregorianCalendar();
		gc01.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		GregorianCalendar gc02 = new GregorianCalendar();
		gc02.setTimeZone(TimeZone.getTimeZone("America/New_York"));

		int year01=getYear(timeMillis01);
		int month01=getMonth(timeMillis01);
		int year02=getYear(timeMillis02);
		int month02=getMonth(timeMillis02);
		gc01.set(year01, month01, 0);
		gc02.set(year02, month02, 0);
		return gc01.getTimeInMillis()==gc02.getTimeInMillis();
	}
	public static boolean isFriday(long timeMillis){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return gc.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY;
	}
	public static boolean isMonday(long timeMillis){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return gc.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY;
	}
	public static boolean isSameDay(long timeMillis01,long timeMillis02){
		GregorianCalendar gc01 = new GregorianCalendar();
		gc01.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc01.setTimeInMillis(timeMillis01);
		GregorianCalendar gc02 = new GregorianCalendar();
		gc02.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc02.setTimeInMillis(timeMillis02);

		//gc.set(Calendar.DAY_OF_MONTH, 1); // the first day of the month is 1
		gc01.set(Calendar.HOUR_OF_DAY, 0);
		gc01.set(Calendar.MINUTE, 0);
		gc01.set(Calendar.SECOND, 0);
		gc01.set(Calendar.MILLISECOND, 0);
		gc02.set(Calendar.HOUR_OF_DAY, 0);
		gc02.set(Calendar.MINUTE, 0);
		gc02.set(Calendar.SECOND, 0);
		gc02.set(Calendar.MILLISECOND, 0);
		return gc01.getTimeInMillis()==gc02.getTimeInMillis();
	}
	public static boolean isSameWeek(long timeMillis01,long timeMillis02){

		return (getYear(timeMillis01)==getYear(timeMillis02)&&getWeek(timeMillis01)==getWeek(timeMillis02));
	}
	public  static int getWeek(long timeMillis){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(timeMillis);
		return gc.get(Calendar.WEEK_OF_YEAR);
	}
	public static int getDayOfMonth(long timeSeconds){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(timeSeconds*1000);
		return gc.get(Calendar.DAY_OF_MONTH);
	}
	public static int getDayOfWeek(long timeMillis){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(timeMillis);
		return gc.get(Calendar.DAY_OF_WEEK);
	}
	public static int getHour(long timeSeconds){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(timeSeconds*1000);
		return gc.get(Calendar.HOUR_OF_DAY);
	}
	public static int getMinute(long timeSeconds){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(timeSeconds*1000);
		return gc.get(Calendar.MINUTE);
	}
	public static int getSecond(long timeSeconds){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(timeSeconds*1000);
		return gc.get(Calendar.SECOND);
	}


	public static long getTimeMilliSeconds(String date) {
		long time = 0;
		String timeString=new String(date);
		timeString=timeString.replaceAll(" ", "");
		timeString=timeString.replaceAll(":", "");
		int year = Integer.parseInt(timeString.substring(0, 4));
		int month = Integer.parseInt(timeString.substring(4, 6));
		int day = Integer.parseInt(timeString.substring(6, 8));
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (timeString.length()==14) {
			hour = Integer.parseInt(timeString.substring(8, 10));
			minute = Integer.parseInt(timeString.substring(10, 12));
			second = Integer.parseInt(timeString.substring(12, 14));
		}

		GregorianCalendar gc = new GregorianCalendar();
		gc.clear();
		// can not use new york time zone here.
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.set(year, month - 1, day, hour, minute, second);
		time = gc.getTimeInMillis();
		return time;
	}
	public static long getTimeMilliSecondsOfFileDate(String filaName) {
		Pattern p = Pattern.compile("(\\d{8})");
		Matcher m = p.matcher(filaName);
		if (!m.find()){
			System.out.println("FileName does not have correct formate: yyyymmdd");
			return 0;
		}
		String date=m.group(0);
		return getTimeMilliSeconds(date);
	}
	public static String getTimeStringForSeconds(long time) {

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(time*1000);
		int year=gc.get(Calendar.YEAR);
		int month=gc.get(Calendar.MONTH) + 1;
		int day=gc.get(Calendar.DAY_OF_MONTH);
		int hour=gc.get(Calendar.HOUR_OF_DAY);
		int minute=gc.get(Calendar.MINUTE);
		int second=gc.get(Calendar.SECOND);


		String date=pad(year)+"-"+pad(month)+"-"+pad(day)+" "+pad(hour)+":"+pad(minute)+":"+pad(second);//+" "+ gc.getTimeZone().getDisplayName(false, TimeZone.SHORT);
		return date;
	}

	public static String getTimeStringForMillis(long time) {

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(time);
		int year=gc.get(Calendar.YEAR);
		int month=gc.get(Calendar.MONTH) + 1;
		int day=gc.get(Calendar.DAY_OF_MONTH);
		int hour=gc.get(Calendar.HOUR_OF_DAY);
		int minute=gc.get(Calendar.MINUTE);
		int second=gc.get(Calendar.SECOND);
		int millis=gc.get(Calendar.MILLISECOND);
		String date=pad(year)+pad(month)+pad(day)+"  "+pad(hour)+":"+pad(minute)+":"+pad(second)+":"+pad(millis/10)+millis%10;
		return date;
	}
	public static String getTodayDateString() {

		return getDateString(System.currentTimeMillis());
	}

	public static String getDateString(long millis){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		gc.setTimeInMillis(millis);
		int year=gc.get(Calendar.YEAR);
		int month=gc.get(Calendar.MONTH) + 1;
		int day=gc.get(Calendar.DAY_OF_MONTH);
		String date=pad(year)+pad(month)+pad(day);
		return date;
	}
	public static String pad(int val) {
		return val < 10 ? "0" + val : "" + val;
	}
	public static void copyFile(String fileFrom, String fileTo){
		File f1=new File(fileFrom);
		if (!f1.exists()){
			System.out.println(fileFrom+" does not exist.");
			return;
		}
		File f2=new File(fileTo);
		if (!f2.getParentFile().exists()) {
			f2.getParentFile().mkdirs();
		}
		FileOutputStream fos = null;
		InputStream in=null;
		try {
			in = new FileInputStream(f1);
			fos = new FileOutputStream(f2);
			byte[] buf = new byte[2048];
			int len;
			while ((len = in.read(buf)) > 0){
				fos.write(buf, 0, len);
			}
			in.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Object readObject(Object obj,String fileName){
		//System.out.println("Read "+ fileName);
		File f = new File(fileName);
		if (!f.exists()) {
			return obj;
		}
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(f);
			in = new ObjectInputStream(fis);
			obj = obj.getClass().cast(in.readObject());
			in.close();
		} catch (IOException ex) {
			System.out.println("Reading "+fileName+" got a problem");
			ex.printStackTrace();
			return obj;

		} catch (ClassNotFoundException ex) {
			System.out.println("Reading "+fileName+" got a problem");
			ex.printStackTrace();
			return obj;
		}
		return obj;
	}

	public static boolean saveObject(Object obj,String fileName){
		//System.out.println("Save "+ fileName);
		File f = new File(fileName);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(f);
			out = new ObjectOutputStream(fos);
			out.writeObject(obj);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return true;
	}



	/*
	 * save a string as a line to a file;
	 */
	public static boolean writeString(String fileName,String string, boolean append){
		File txt = new File(fileName);
		if (!txt.getParentFile().exists()) {
			txt.getParentFile().mkdirs();
		}
		PrintWriter txtout = null;
		try {
			txtout = new PrintWriter(new BufferedWriter(new FileWriter(fileName, append)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			txtout.close();
			e.printStackTrace();
			return false;
		}
		txtout.println(string);
		txtout.close();
		return true;
	}
	public static boolean writestrings(String fileName,List<String> strings,boolean append) {
		long timer=System.currentTimeMillis();
		PrintWriter writer = null;
		File hisTxt = new File(fileName);
		if (!hisTxt.getParentFile().exists()) {
			hisTxt.getParentFile().mkdirs();
		}
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, append)));
		} catch (IOException ioe) {
			//System.out.println("I am marketDataStream writer and I got a problem");
			ioe.printStackTrace();
			return false;
		}
		Iterator<String> itr = strings.iterator();
		while (itr.hasNext()) {
			writer.println(itr.next().toString());
		}
		writer.close();
		timer=(System.currentTimeMillis()-timer)/1000;

		return true;
	}

	public static List<String> readStrings(String fileName){
		File file=new File(fileName);
		if (!file.exists()){
			//System.out.println(fileName+" is not exist, Can not read");
			return null;
		}
		String line = "";
		List<String> strings = new ArrayList<String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			while ((line = reader.readLine()) != null) {
				strings.add(line);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strings;
	}

	public static ArrayList<String>getAllFilesName(String folderName){
		File file=new File(folderName);
		File[] fileNames = null;
		ArrayList<String> allNames=new ArrayList<String>();
		if (file.exists()&&file.isDirectory()){
			fileNames=file.listFiles();
		}
		if (fileNames==null||fileNames.length==0){
			return null;
		}
		if (fileNames.length>0){
			for (int i=0;i<fileNames.length;i++){
				allNames.add(fileNames[i].getName());
			}
			return (ArrayList<String>)allNames;
		}else{
			return null;
		}

	}




	public Boolean isOnline() {
		try {
			Process process = Runtime.getRuntime().exec("ping -c 1 www.google.com");
			int returnVal = process.waitFor();
			boolean reachable = (returnVal==0);
			return reachable;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static Double retriveDoubleFromString(String string ){
		String tempStr=string.replaceAll(",", "");
		String doubleRegExp = "[0-9,\\.]+";
		double temp = 0;
		Pattern pattern = Pattern.compile(doubleRegExp);
		Matcher	matcher = pattern.matcher(tempStr);
		List<String> lstMatches = new ArrayList<String>();
		while (matcher.find()) {
			lstMatches.add(matcher.group());
		}
		for (String match : lstMatches) {
			if (match.startsWith(".")) match="0"+match;
			temp = Double.parseDouble(match.replace(",", ""));
			//if (temp != 0)
			return temp;
		}
		return null;
	}
	public static int retriveIntegerFromString(String string ){
		String tempStr=string.replaceAll(",", "");
		String intRegExp = "[0-9,\\.]+";
		int temp = 0;
		Pattern pattern = Pattern.compile(intRegExp);
		Matcher	matcher = pattern.matcher(tempStr);
		List<String> lstMatches = new ArrayList<String>();
		while (matcher.find()) {
			lstMatches.add(matcher.group());
		}
		for (String match : lstMatches) {
			if (match.startsWith(".")) return 0;
			temp = Integer.parseInt(match.replace(",", ""));
			if (temp != 0)
				return temp;
		}
		return 0;
	}
	public static Double retriveLastDoubleFromString(String string ){
		String tempStr=string.replaceAll(",", "");
		String doubleRegExp = "[0-9,\\.]+";
		double temp = 0;
		Pattern pattern = Pattern.compile(doubleRegExp);
		Matcher	matcher = pattern.matcher(tempStr);
		List<String> lstMatches = new ArrayList<String>();
		while (matcher.find()) {
			lstMatches.add(matcher.group());
		}
		if (lstMatches.size()>0){
			String match=lstMatches.get(lstMatches.size()-1);
			if (match.startsWith(".")) match="0"+match;
			temp = Double.parseDouble(match.replace(",", ""));
			//if (temp != 0)
			return temp;
		}
		return 0.0;
	}

	public static String getMyIp(){
		try {
			URL myIP = new URL("http://myip.dnsomatic.com/");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(myIP.openStream())
			);

			return in.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	public static String getMyMac(){
		try {
			InetAddress ip = InetAddress.getLocalHost();

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();
			if (mac==null) return null;

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			return sb.toString();

		} catch (UnknownHostException e) {
			return null;

		} catch (SocketException e){
			return null;

		}
	}
	public static void printElement(Element element){
		if (element==null) {
			System.out.println("Can not print element since it is null");
			return;
		}
		System.out.println("link : " + element.attr("href"));
		System.out.println("text : " + element.text());
		System.out.println("data: "+ element.data());
		System.out.println("tagName: "+ element.tagName());
		System.out.println("className: "+ element.className());
		System.out.println("nodeName: "+ element.nodeName());
		System.out.println("ownText: "+ element.ownText());
		System.out.println("");

	}
	public static ArrayList<Element> getCertainLevelElements(Elements elements,int i,int certainLevel,boolean needOutPut,String string){
		i++;
		ArrayList<Element> results=new ArrayList<Element>();
		if (i==certainLevel){
			if (elements.size()!=0&&elements!=null){
				for (Element element:elements){
					results.add(element);
					if (needOutPut){

						System.out.println("this is "+i+"th level");
						GeneralMethod.printElement(element);
					}
				}
			}
			return results;
		}else{

			for (Element element:elements){
				ArrayList<Element> temp=getCertainLevelElements(element.children(),i,certainLevel,needOutPut,string);
				if (needOutPut&&element.attr("href").contains("http://www.ebay.com/itm/")){

					System.out.println("Found something at "+i+"th level");
					GeneralMethod.printElement(element);

				}
				if (i>1&&string!=null&&(element.text().toLowerCase().contains(string.toLowerCase())||element.ownText().toLowerCase().contains(string.toLowerCase()))){
					System.out.println("Found target string at "+i+"th level");
					GeneralMethod.printElement(element);
				}
				results.addAll(temp);

			}

			return results;
		}

	}
	public static String readEmailsFromGmail(String emailAddress,String PassWords){
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect("imap.gmail.com", emailAddress,
					PassWords);
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			Message msg = inbox.getMessage(inbox.getMessageCount());
			Address[] in = msg.getFrom();

			Multipart mp = (Multipart) msg.getContent();
			BodyPart bp = mp.getBodyPart(0);
			//System.out.println("SENT DATE:" + msg.getSentDate());
			//System.out.println("SUBJECT:" + msg.getSubject());
			//System.out.println("CONTENT:" + bp.getContent());
			String[] strings=bp.getContent().toString().split("\n");

			for (int i=0;i<strings.length;i++){
				if (strings[i].length()>2)
					//System.out.println(strings[i].length());
					System.out.println(strings[i].replaceAll("\n", ""));
			}


		} catch (Exception mex) {
			mex.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * @param fileName
	 * @param lines
	 * @return Time of the latest saved data
	 */
	public String readLastLines(String fileName, int lines) {
		File file = new File(fileName);
		RandomAccessFile fileHandler = null;
		try {
			fileHandler = new RandomAccessFile(file, "r");
			long fileLength = file.length() - 1;
			if (fileLength == 0) {
				return null;
			}
			StringBuilder sb = new StringBuilder();
			int line = 0;

			for (long filePointer = fileLength; filePointer != -1; filePointer--) {
				fileHandler.seek(filePointer);
				int readByte = fileHandler.readByte();

				if (readByte == 0xA) {
					if (line == lines) {
						if (filePointer == fileLength) {
							// fileLength--;
							continue;
						} else {
							break;
						}
					}
				} else if (readByte == 0xD) {
					line = line + 1;
					if (line == lines) {
						if (filePointer == fileLength - 1) {
							// fileLength--;
							continue;
						} else {
							break;
						}
					}
				}
				sb.append((char) readByte);
			}
			// sb.deleteCharAt(sb.length()-1);
			String lastLine = sb.reverse().toString();

			if (lastLine.length() > 1) {
				return lastLine;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				fileHandler.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public static boolean isgoodASIN(String ASIN){
		if (ASIN==null) return false;
		if (ASIN.startsWith("B0")&&ASIN.length()==10) return true;
		return false;
	}
	public static String getHost(String url){
		if(!url.startsWith("http") && !url.startsWith("https")){
			url = "http://" + url;
		}
		URL netUrl = null;
		try {
			netUrl = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String host = netUrl.getHost();
		if (host==null) return null;
		if (host.contains("stores.ebay.com")){
			String te=netUrl.getPath().split("/")[1];
			//System.out.println(te);
			host=host+"/"+te;
		}
		//if(host.startsWith("www")){
		//   host = host.substring("www".length()+1);
		//}
		//System.out.println("host is: "+host);
		return host;

	}
	public static String getAmazonProductWebAddress(String ASIN){

		return "http://www.amazon.com/gp/product/"+ASIN;
	}
	public static boolean changeFileName(String oldFileName,String newFileName) throws IOException{
		File file=new File(oldFileName);
		if (!file.exists()){
			//System.out.println(fileName+" is not exist, Can not read");
			return false;
		}
		File file2 = new File(newFileName);
		if(file2.exists()) throw new IOException("file exists");
		return file.renameTo(file2);

	}

	public static String getNewEggItemNumber(String URL){

		if (!URL.contains("www.newegg.com")) return null;

		System.out.println(URL);
		int index=URL.indexOf("Item=");
		if (index==-1) return null;

		String itemNumber=URL.substring(index+5, URL.length()-1);

		return itemNumber;


	}
	public static String getASINFromURL(String URL){

		String[] strings=URL.split("/");
		//System.out.println(URL);
		//System.out.println(strings[5]);
		String temp="";
		if (URL.contains("/dp/")){
			//System.out.println(strings[3]);
			temp= strings[5];
		}
		if (URL.contains("/gp/")){

			temp= strings[5];
		}
		if(temp.contains("#")){
			temp=temp.split("#")[0];
		}
		return temp;

	}
	public static String getAmazonProductNameFromURL(String URL){
		//System.out.println(URL);
		URL=URL.replace("-", " ");
		String[] strings=URL.split("/");
		//System.out.println(strings.length);
		//System.out.println(strings[3]);
		if (URL.contains("/dp/")){
			return strings[3];
		}

		return "Unknown";

	}

	public static boolean isAlert(String string){
		String temp=string.toLowerCase();

		if (
				temp.contains("monitor")||temp.contains("laptop")||temp.contains("lenovo")||temp.contains("best buy")||temp.contains("bestbuy")
						||temp.contains("headphone")||temp.contains("adorama")||temp.contains("hdtv")||temp.contains("cell phone")
						||temp.contains("lego")||temp.contains("sony")||temp.contains("dell")||temp.contains("acer")||temp.contains("asus")
						||temp.contains("bhphotovideo")||temp.contains("microsoft")||temp.contains("desktop")||temp.contains("staples")
						||temp.contains("amazon")||temp.contains("ebucks")||temp.contains("apple")||temp.contains("iphone")
						||temp.contains("ipad")||temp.contains("hp ")||temp.contains("airpot")||temp.contains("canon")
						||temp.contains("nikon")||temp.contains("samsung")||temp.contains("all in one")||temp.contains("workstation")
						||temp.contains("aoc")||temp.contains("lg ")||temp.contains("ultrasharp")||temp.contains("zbook")
						||temp.contains("ultrabook")||temp.contains("flex")||temp.contains("vivobook ")||temp.contains("playstation ")
						||temp.contains("xbox")||temp.contains("console")||temp.contains("inspiron")||temp.contains("touchscreen")
						||temp.contains("surface")||temp.contains("sandisk ")||temp.contains("galaxy")||temp.contains("aspire")||temp.contains("wireless")
						||temp.contains("ssd")||temp.contains("boss")||temp.contains("solid state drive")||temp.contains("hard drive")||temp.contains("flash drive")
						||temp.contains("viewsonic")||temp.contains("olympus")||temp.contains("panosonic")){

			return true;

		}


		if (string.contains("裤")||
				string.contains("鞋")||string.contains("衫")||string.contains("太阳镜")||string.contains("腰带")
				||string.contains("吸尘器")||string.contains("微波炉")||string.contains("双肩包")
				||string.contains("马桶")||string.contains("厕所")||string.contains("靴")
				||string.contains("美容")||string.contains("化妆")||string.contains("底霜")
				||string.contains("冲锋衣")||string.contains("羽绒")||string.contains("大衣")||string.contains("羊毛")||string.contains("外套")
				||string.contains("滑雪服")||string.contains("毛衣")||string.contains("帽子")||string.contains("夹克")||string.contains("外套")
				||string.contains("T恤")||string.contains("纤维素")||string.contains("护发素")||string.contains("洗发水")||string.contains("厕所")
				||string.contains("纸")||string.contains("沐浴露")||string.contains("玉兰油")||string.contains("剂")||string.contains("花盆")
				||string.contains("锅")||string.contains("壶")||string.contains("清洁球")||string.contains("滤")||string.contains("灯泡")
				||string.contains("床")||string.contains("纸")||string.contains("零食")||string.contains("菠萝")||string.contains("海绵")
				||string.contains("餐")||string.contains("牙")||string.contains("厨")||string.contains("加仑")||string.contains("垃圾")
				||string.contains("酒")||string.contains("擀面")||string.contains("洗发")||string.contains("香波")||string.contains("被")
				||string.contains("净水")||string.contains("鼓风机")||string.contains("开瓶器")||string.contains("开罐器")||string.contains("插座")
				||string.contains("桌子")||string.contains("拉杆箱")||string.contains("背包")||string.contains("双肩包")||string.contains("项链")
				||string.contains("公文包")||string.contains("旅行包")||string.contains("手提包")||string.contains("钱包")||string.contains("斜挎包")
				||string.contains("霜")||string.contains("奶")||string.contains("乳")||string.contains("胸")||string.contains("脸")
				||string.contains("精华")||string.contains("美容")||string.contains("护肤")||string.contains("年龄")||string.contains("衰")
				||string.contains("香水")||string.contains("避孕套")||string.contains("膏")||string.contains("天然")||string.contains("痘")
				||string.contains("皱")||string.contains("雾")||string.contains("背心")||string.contains("夹克")||string.contains("打气筒")
				||string.contains("葡萄糖")||string.contains("胺")||string.contains("保健")||string.contains("草本")||string.contains("胶囊")
				||string.contains("素")||string.contains("草本")||string.contains("结石")||string.contains("酮")||string.contains("腺")
				||string.contains("尿布")||string.contains("营养")||string.contains("倩碧")||string.contains("皱")||string.contains("肤")
				||string.contains("腕表")||string.contains("枕")||string.contains("酸")||string.contains("水壶")||string.contains("蛋白")
				||string.contains("蛋白")||string.contains("面膜")||string.contains("湿巾")||string.contains("巧克力")||string.contains("食品")
				||string.contains("健康")||string.contains("奴")||string.contains("服饰")||string.contains("睡衣")||string.contains("羊绒")
				||string.contains("棉条")||string.contains("丹碧丝")||string.contains("时尚")||string.contains("西装")||string.contains("洗面奶")
				||string.contains("茶")||string.contains("果冻")||string.contains("毛绒")||string.contains("范思哲")||string.contains("巾")
				||string.contains("行李箱")||string.contains("拉杆箱")||string.contains("晚宴包")||string.contains("新秀丽")||string.contains("单肩包")
				||string.contains("手枪")||string.contains("乒乓球")||string.contains("戒指")||string.contains("毯")

				||temp.contains("mango")||temp.contains("outerwear")||temp.contains("shoemall")||temp.contains("maxx")
				||temp.contains("refurb")||temp.contains("crocs")||temp.contains("basketball")||temp.contains("sunglass")||temp.contains("blanket")
				||temp.contains("samsonite")||temp.contains("luggage")||temp.contains("toaster")||temp.contains("oven")||temp.contains("owned")
				||temp.contains("nautica")||temp.contains("puritans pride")||temp.contains("travelon")||temp.contains("aeropostale")
				||temp.contains("furla")||temp.contains("tommy hilfiger")||temp.contains("shoulder bag")||temp.contains("evening bag")
				||temp.contains("skateboard")||temp.contains("new balance")||temp.contains("teavana")||temp.contains("towel")
				||temp.contains("hoodie")||temp.contains("charles tyrwhitt")||temp.contains("shirt")||temp.contains("gander mountain")
				||temp.contains("ju-ju-be b.f.f")||temp.contains("diaper")||temp.contains("versace")||temp.contains("pants")||temp.contains("nike")
				||temp.contains(" tea ")||temp.contains(" furla ")||temp.contains(" candy ")||temp.contains("boon")||temp.contains("raise")
				||temp.contains("kate spade")||temp.contains("eddie bauer")||temp.contains("outwear")||temp.contains("esteeLauder")
				||temp.contains("givenchy")||temp.contains("versace dollection")||temp.contains("vince camuto cris")||temp.contains("skechers")
				||temp.contains("rebecca minkoff")||temp.contains("williams cashmere")||temp.contains("tampax")||temp.contains("diesel")
				||temp.contains("train")||temp.contains("j.crew")||temp.contains("victorias secret")||temp.contains("swimwear")
				||temp.contains("loft")||temp.contains("asics")||temp.contains("easy spirit")||temp.contains("pajama")
				||temp.contains("gucci")||temp.contains("christian dior")||temp.contains("balenciaga")||temp.contains("magazine")
				||temp.contains("jockey")||temp.contains("cotton")||temp.contains("brief")||temp.contains("chocolate")
				||temp.contains("facial mask")||temp.contains("luna bar")||temp.contains("pampers")||temp.contains("wipes")
				||temp.contains("boot")||temp.contains("ralph lauren")||temp.contains("reebok")||temp.contains("water bottle")
				||temp.contains("easy spirit")||temp.contains("coffee")||temp.contains("north face")||temp.contains("ugg")
				||temp.contains("toner")||temp.contains("perricone")||temp.contains("cos a bank")||temp.contains("jos a bank")
				||temp.contains("fitbit")||temp.contains("apparel")||temp.contains("moisturizer")||temp.contains("furniture")
				||temp.contains("la roche")||temp.contains("bergdorf goodman")||temp.contains("cole haan")||temp.contains("yoox")
				||temp.contains("acid")||temp.contains("darphin")||temp.contains("gilt city")||temp.contains("bloomingdales")
				||temp.contains(" lip ")||temp.contains("bath and body works")||temp.contains("elizabeth arden")||temp.contains("tommy hilfiger")
				||temp.contains("prescriptives")||temp.contains("benefit cosmetics")||temp.contains("nivea")||temp.contains("vitamin")
				||temp.contains("zoya")||temp.contains("shea")||temp.contains("almond ")||temp.contains("honey")
				||temp.contains("creme")||temp.contains("cvs")||temp.contains("aveda")||temp.contains("dave")
				||temp.contains("shu uemura")||temp.contains("bobbi brown")||temp.contains("saks fifth avenue")||temp.contains("frizz")
				||temp.contains("sephora")||temp.contains("first aid beauty")||temp.contains("saks fifth avenue")||temp.contains("stila")
				||temp.contains("drugstore")||temp.contains("lotion")||temp.contains("peter thomas roth")||temp.contains("lord and taylor")
				||temp.contains("ulta")||temp.contains("lergdorf goodman")||temp.contains("neiman marcus")||temp.contains("la mer")
				||temp.contains("shu uemura")||temp.contains("crabtree and evelyn")||temp.contains("vintage")||temp.contains("jurlique")
				||temp.contains(" spa ")||temp.contains("nail")||temp.contains("cuticle")||temp.contains("therapy")
				||temp.contains("hollister")||temp.contains("davidoff")||temp.contains("toilette")||temp.contains("spray")
				||temp.contains("clairol")||temp.contains("lipstick")||temp.contains("shampoo")||temp.contains("body shop")
				||string.contains("L'Occitane")||temp.contains("shiseido")||temp.contains("shu uemura")||temp.contains("tarte")
				||temp.contains("cream")||temp.contains("saks fifth avenue")||temp.contains("pantene")||temp.contains("origins")
				||temp.contains("nordstrom")||temp.contains("maybelline")||temp.contains("ahava")||temp.contains("macys")
				||temp.contains("avon")||temp.contains("maybelline")||temp.contains("ahava")||temp.contains("macys")
				||temp.contains("kiehl's")||temp.contains("philosophy")||temp.contains("clarisonic")||temp.contains(" mia ")
				||temp.contains("neiman marcus")||temp.contains("pillow")||temp.contains("clinique")||temp.contains("clarins")
				||temp.contains("estee lauder")||temp.contains("giorgio armani")||temp.contains("beauty")||temp.contains("eye")
				||temp.contains("mascara")||temp.contains("calvin klein")||temp.contains("biotherm")||temp.contains("mega")
				||temp.contains("duffel")||temp.contains("lancome")||temp.contains("butter")||temp.contains("skincare")
				||temp.contains("pearl")||temp.contains("tote")||temp.contains("asos")||temp.contains("briefcase")
				||temp.contains("cook")||temp.contains("shoe")||temp.contains("cloth")||temp.contains("gown")
				||temp.contains("hotel")||temp.contains("jetblue")||temp.contains("wallet")||temp.contains("handbag")
				||temp.contains("watch")||temp.contains("jacket")||temp.contains("coach")
				||temp.contains("hair")||temp.contains("gnc")||temp.contains("maca")
				||temp.contains("provence")||temp.contains("salomon")||temp.contains("maca")
				||temp.contains("bulova")||temp.contains("swarovski")||temp.contains("skinstore")
				||temp.contains("kipling")||temp.contains("tumi")||temp.contains("prada")
				||temp.contains("pantene")||temp.contains("olay")||temp.contains("led")
				||temp.contains("columbia")||temp.contains("ann taylor")||temp.contains("banana republic")){

			return false;

		}
		return false;
	}

}
