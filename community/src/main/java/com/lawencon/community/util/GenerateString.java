package com.lawencon.community.util;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	   

	  public static String generateFileName(String fileExtension) {
		   long timestamp = Instant.now().toEpochMilli();
	       int randomNum = (int) (Math.random() * 1000);
	        return String.format("%d_%03d%s", timestamp, randomNum, "."+fileExtension);
	    }
	  
	  public static String getIndonesianDate(LocalDate date) {
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		    String formattedDate = date.format(formatter);
		    return formattedDate;
		}

}
