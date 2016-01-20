package neco;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Neco {

	public static void main(String[] args) {
	
		//Pokus upravit	
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22

		//c1.set(2000, Calendar.JANUARY, 30);
		cal.set(2016, Calendar.FEBRUARY, 28);
		boolean jeto=isLastDayOfTheMonth(cal.getTime());
		
		
		
		System.out.println(jeto);
		
		//String addres1="Industriepark Klause / Schmiedeweg 1";
		//String prd = addres1.replaceAll("[\\//]", "").trim();
		
		//System.out.println(prd);
		
		//String account = "a";
		//if (account != null && account.length() > 1 && !account.substring(account.length() - 1).equals("%")) account = account + "%";
		//System.out.println(account);
		
		
		/*
		StringBuilder sbAccount = new StringBuilder("#.20018436.050.EUR.#.#.#.#");

		while (sbAccount.length() > 0 && (sbAccount.charAt(sbAccount.length()-1)=='#')||(sbAccount.charAt(sbAccount.length()-1)=='.')) {
			sbAccount.setLength(sbAccount.length()-1);
		}
		
		String from="#"; String to ="%";
		int index = sbAccount.indexOf(from);
	    while (index != -1)
	    {
	    	sbAccount.replace(index, index + from.length(), to);
	        index += to.length(); // Move to the end of the replacement
	        index = sbAccount.indexOf(from, index);
	    }
		

		System.out.println(sbAccount.toString());
		*/
		
		/*
		String mediaCodes = "[\"PRD\",\"DRD\",\"NO-PAY\"]";
		//String mediaCodes ="[\"DE-DTAZV\"]";
		ArrayList<String> mediacodes = new ArrayList<String>() ;
		System.out.println(mediaCodes);
		if (mediaCodes !=null){
			mediaCodes = mediaCodes.replaceAll("\\[|\\]|\"", "");
			String medCodes[] = mediaCodes.split(",");
			
			boolean nopay = false;									//-ccc
			for (int i=0; i < medCodes.length;i++ ){
				mediacodes.add(medCodes[i]);
				if (medCodes[i].equals("NO-PAY")) nopay = true;					//-ccc
				//System.out.println(medCodes[i]);	
			}
			
			//-ccc
			if (nopay){
				mediacodes.clear();
				mediacodes.add("NO-PAY");
			}//-ccc
			
		}
		
		
		//just dhow
		for (String med:mediacodes){
			System.out.println(med);
		}
		
		*/
		
		
		//String testum= "'**NextAge Technologies? / Ltd.**'";
		 
		//testum.replaceAll("[\\//]", "").trim();
		//String outik =  testum.replaceAll("[^\\x00-\\x7F]", "");
		
		//String outik = testum.replaceAll("[^\\x00-\\x7F]", "");
		
		//String outik = testum.replaceAll("[-+?^:*'\\//]","");
		//if (outik.length() == 0) outik = "."
		//String outik = testum.replaceAll("[-+.^:,*']","");
		
		
		//String testum= "Prdel\"";
		/*
		String testum= "'SIA \"OpticLab\"_LOST'";
		System.out.println(testum);
		//String outik= testum.replace("\"", "").trim();
		String outik= testum.replaceAll("[+?^:*'?³ß\\//\"]","").trim();
		System.out.println(outik);
		
		int rokomes = 201512;
		int period = rokomes%100;
		int yr = (rokomes - period)/100;
		
		System.out.println(yr);
		System.out.println(period);
		*/
		
		//System.out.println(String.format("%02d", 12));
		
		/* test update flexi
		
		MerchantMasterXML mm = new MerchantMasterXML();
		try{
			String content = readFile("sample.xml", Charset.defaultCharset());
			
			
			mm.setStrXMLResponse(content);
			ArrayList<String> dummy = mm.getContactsForRole("Settlement Note Recipient");
			System.out.println("Mails:");
			for (String item : dummy) {
				System.out.println(item);
			   
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		*/
		
	}

	/**
	 * Helper to read file into string
	 * @param path
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	static String readFile(String path, Charset encoding) throws IOException 
	{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
	}
	
	
	
	public static boolean isLastDayOfTheMonth(Date dat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dat);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return  removeTime(cal.getTime()).equals(removeTime(dat)); 
	}// end of isLastDayOfTheMonth
	
	public static Date removeTime(Date date) {    
	    Calendar cal = Calendar.getInstance();  
	    cal.setTime(date);  
	    cal.set(Calendar.HOUR_OF_DAY, 0);  
	    cal.set(Calendar.MINUTE, 0);  
	    cal.set(Calendar.SECOND, 0);  
	    cal.set(Calendar.MILLISECOND, 0);  
	    return cal.getTime(); 
	}
	

}
