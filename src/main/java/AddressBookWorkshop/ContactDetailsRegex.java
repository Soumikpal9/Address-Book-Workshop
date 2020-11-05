package AddressBookWorkshop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactDetailsRegex {
	IAddBookValidate firstNameValidation = n -> {
		return Pattern.compile("^[A-Z]+[a-z A-Z]{2,}").matcher(n).find();
	};
	
	IAddBookValidate lastNameValidation = n -> {
		return Pattern.compile("^[A-Z]+[a-z A-Z]{2,}").matcher(n).find();
	};
	
	IAddBookValidate addressValidation = n -> {
		return Pattern.compile("[0-9 a-z \\s A-Z]").matcher(n).find();
	};
	
	IAddBookValidate cityValidation = n -> {
		return Pattern.compile("^[A-Z]+[a-z A-Z]{2,}").matcher(n).find();
	};
	
	IAddBookValidate stateValidation = n -> {
		return Pattern.compile("^[A-Z]+[a-z A-Z]{2,}").matcher(n).find();
	};
	
	IAddBookValidate zipcodeValidation = n -> {
		return Pattern.compile("^[0-9]{6}").matcher(n).find();
	};
	
	IAddBookValidate phoneValidation = n -> {
		return Pattern.compile("^[0-9]{2}\\\\s[0-9]{10}$").matcher(n).find();
	};
	
	IAddBookValidate emailValidation = n -> {
		return Pattern.compile("^[A-Z]+([+._-]{0,1}([A-Z 0-9]+))*[@][A-Z 0-9]{1,}[.][A-Z]{2,}([.][A-Z]{2,}){0,1}$").matcher(n).find();
	};
}
