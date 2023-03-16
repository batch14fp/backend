package com.lawencon.community.util;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class GenerateString {
    private static int counter = 0;
    
	public static String generateCode(final int totalLength) {
		final int leftLimit = 48;
		final int rightLimit = 58;
		final Random random = new Random();

		final String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(totalLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString.toUpperCase();
	}
	
	public static String generateInvoice() {
	    String pattern = "yyMM/dd/";
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	    String date = simpleDateFormat.format(new Date());
	    String romanNumeral = convertToRomanNumeral(counter);
	    counter++;
	    return date + romanNumeral;
	}

	public static String convertToRomanNumeral(int num) {
	    String[] romans = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX",
	            "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC",
	            "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM",
	            "", "M", "MM", "MMM", "MMMM"};
	    final StringBuilder roman = new StringBuilder();
	    int digit, position = 0;
	    while (num > 0) {
	        digit = num % 10;
	        num /= 10;
	        roman.insert(0, romans[position + (digit != 0 ? (digit + (position == 0 ? 0 : 10)) : 0)]);
	        position++;
	    }
	    return String.format("%04d", Integer.parseInt(roman.toString()));
	}

}
