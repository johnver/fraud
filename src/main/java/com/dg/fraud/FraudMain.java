/**
 * 
 */
package com.dg.fraud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.dg.fraud.util.FraudDetectionHelper;

/**
 * @author johnver
 *
 */
public class FraudMain {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		if (args != null && args.length == 2) {
			String dateParam = args[0];
			String tresholdParam = args[1];
			
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateParsed = dateFormat.parse(dateParam);
				
				BigDecimal treshold = new BigDecimal(tresholdParam);
				
				List<String> transactions = readTransactionsFromFile();
				List<String> fraudAccount = FraudDetectionHelper
						.identifyFraudTransactions(transactions, 
								dateParsed, treshold);
				System.out.println("There are " + fraudAccount.size() +
						" fraud account(s) detected for this day: " + dateParam);
				for (String string : fraudAccount) {
					System.out.println(string);
				}
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		else {
			System.out.println("Usage: <date> <priceTreshold>");
		}
		
	}

	private static List<String> readTransactionsFromFile() throws Exception {
		final InputStream data = FraudMain.class
				.getClassLoader().getResourceAsStream("transaction-log.txt");
		
		BufferedReader br = null;

		List<String> transactions = new ArrayList<String>();
		try {

			System.out.println("Analyzing the data.. Please wait..");
			String sCurrentLine;
			final InputStreamReader inputStreamReader = new InputStreamReader(
					data);
			br = new BufferedReader(inputStreamReader);

			
			while ((sCurrentLine = br.readLine()) != null) {
				transactions.add(sCurrentLine);

			}
			System.out.println("Done analyzing the data...");

		} catch (final Exception e) {
			throw e;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return transactions;
	}
}
