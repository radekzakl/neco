package neco;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MerchantMasterXML {
	private String strXMLResponse;
	
	public void setStrXMLResponse(String strXMLResponse){this.strXMLResponse = strXMLResponse;}
	public String getStrXMLResponse(){return this.strXMLResponse;}
	
	
	public ArrayList<String> getContactsForRole(String role) throws ParserConfigurationException, SAXException, IOException, Exception {
		
	//in this version for development purposes read xml from file - later from the string strXMLResponse
		
		ArrayList<String> ret = new ArrayList<String>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//just for test - parse using builder to get DOM representation of the XML file
			//Document dom = db.parse("sample.xml");
			//this works but is deprecated Document dom =  db.parse(new StringBufferInputStream(strXMLResponse));
			Document dom =  db.parse(new ByteArrayInputStream(strXMLResponse.getBytes("UTF-8")));
			
			//root 
			Element docEle = dom.getDocumentElement();
			
			//At first check the status and if not OK then return empty string
			NodeList nlStat = docEle.getElementsByTagName("status");
			if(nlStat != null && nlStat.getLength() > 0) {
				Element statEl = (Element)nlStat.item(0);
				String textVal = statEl.getFirstChild().getNodeValue();
				try {
					if (Integer.parseInt(textVal) !=0) return ret;
				} catch (Exception e){
					return ret;
				}
			} else {
				return ret;
			}

			//here we have data returned
			NodeList nlMA = docEle.getElementsByTagName("merchantAccount");
			if(nlMA != null && nlMA.getLength() > 0) {
				for(int i = 0 ; i < nlMA.getLength();i++) {
					Element elMA = (Element)nlMA.item(i);
					String name = getTextValue(elMA, "accountName");
					System.out.println(name);
					//within the merchant account I need all contacts 
					NodeList nlContacts = elMA.getElementsByTagName("contact");
					if(nlContacts != null && nlContacts.getLength() > 0) {
						for(int j = 0 ; j < nlContacts.getLength();j++) {
							Element elContact = (Element)nlContacts.item(j);
							String email = getTextValue(elContact, "email");
							boolean addThisEmail = false;
							//For this contact we must go through the roles and check if there is	"Settlement Note Recipient" among them
							NodeList nlRoles = elContact.getElementsByTagName("role");
							if(nlRoles != null && nlRoles.getLength() > 0) {
								for(int k = 0 ; k < nlRoles.getLength();k++) {
									Element elRole = (Element)nlRoles.item(k);
									if (getTextValue(elRole, "roleName")!=null){
										if(getTextValue(elRole, "roleName").toUpperCase().equals("SETTLEMENT NOTE RECIPIENT")){
											addThisEmail = true;
										}
									}	
								}
							}//if some roles exist within particular contact
							
							//if we are here we can add this contact
							if (addThisEmail) ret.add(email);
						}
					} //if some Contacts within particular MA Exist
				}//End of the lloop of merchantAccounts
			}//if some merchantAccount elements were found
			
			return removeDuplicities(ret);

		}catch(ParserConfigurationException pce) {
			throw new ParserConfigurationException("Error in parsing at getContactsForRole " + pce.getMessage());
		}catch(SAXException se) {
			se.printStackTrace();
			throw new SAXException("Error in SAX at getContactsForRole " + se.getMessage());
		}catch(IOException ioe) {
			throw new IOException("Error of IO operation at getContactsForRole " + ioe.getMessage());
		}catch(Exception e) {
			throw new Exception("Error in getContactsForRole " + e.getMessage());
		}
		
	}// end ofgetContactsForRole
	
	/**
	 * <h2>getTextValue</h2>
	 * @param ele
	 * @param tagName
	 * @return text value of the element
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		return textVal;
	}
	
	/**
	 * NOT USED NOW
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private int getIntValue(Element ele, String tagName) {
		try {
			return Integer.parseInt(getTextValue(ele,tagName));	
		} catch (Exception e){
			return - 1;
		}
		
	}
	
	/**
	 * <h2>removeDuplicities</h2>
	 * @param list
	 * @return the list without duplicities
	 */
	public ArrayList<String> removeDuplicities(ArrayList<String> list) {
		ArrayList<String> result = new ArrayList<String>();
		HashSet<String> set = new HashSet<>();
		for (String item : list) {

		    // If String is not in set, add it to the list and the set.
		    if (!set.contains(item)) {
			result.add(item);
			set.add(item);
		    }
		}
		return result;
	}//End of removeDuplicities
	
	

}
