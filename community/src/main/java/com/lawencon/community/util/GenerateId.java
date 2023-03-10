package com.lawencon.community.util;

import java.util.Random;

public class GenerateId {
	public static String generateCode(final int totalLength) {
		final int leftLimit = 48;
		final int rightLimit = 58;
		final Random random = new Random();

		final String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(totalLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString.toUpperCase();
	}

}
