package AddressBookWorkshop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactDetailsRegex {
	public boolean FirstNameValidation(String input) {
		String regex = "^[A-Z]+[a-z A-Z]{2,}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		boolean found = matcher.find();
		if(found) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean LastNameValidation(String input) {
		String regex = "^[A-Z]+[a-z A-Z]{2,}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		boolean found = matcher.find();
		if(found) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean AddressValidation(String input) {
		String regex = "[0-9 a-z \\s A-Z]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		boolean found = matcher.find();
		if(found) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean CityValidation(String input) {
		String regex = "^[A-Z]+[a-z A-Z]{2,}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		boolean found = matcher.find();
		if(found) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean StateValidation(String input) {
		String regex = "^[A-Z]+[a-z A-Z]{2,}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		boolean found = matcher.find();
		if(found) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean ZipcodeValidation(String input) {
		String regex = "^[0-9]{6}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		boolean found = matcher.find();
		if(found) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean PhoneValidation(String input) {
		String regex = "^[0-9]{2}\\s[0-9]{10}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		boolean found = matcher.find();
		if(found) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean EmailValidation(String input) {
		String regex = "^[A-Z]+([+._-]{0,1}([A-Z 0-9]+))*[@][A-Z 0-9]{1,}[.][A-Z]{2,}([.][A-Z]{2,}){0,1}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		boolean found = matcher.find();
		if(found) {
			return true;
		}
		else {
			return false;
		}
	}
}
