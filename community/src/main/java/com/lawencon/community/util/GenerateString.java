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
	        String pattern = "yyMMdd";
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	        String date = simpleDateFormat.format(new Date());
	        DecimalFormat decimalFormat = new DecimalFormat("000000");
	        String counterString = decimalFormat.format(++counter);
	        return date + counterString;
	  
	}

}
