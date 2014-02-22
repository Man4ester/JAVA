package bismark.it.blogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import bismark.it.blogic.interfaces.IEnterDataService;
import bismark.it.blogic.services.EnterDataService;

/*
 * read all line
 * set separator
 * feel hasMap
 */

public class Main {

	private static final Logger logger = Logger.getLogger("MAIN");

	public static void main(String[] arg) {
		List<String> tmpLst = new ArrayList<String>();
		tmpLst.add("A");
		tmpLst.add("B");
		tmpLst.add("C");
		tmpLst.add("D");
		tmpLst.add("E");

		IEnterDataService service = new EnterDataService(tmpLst);

		System.out
				.println("Enter something here(use Tab for separate and 'calc' for get result) : ");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		try {
			String map = service
					.getDataFromCocoleWithSeprator("\t", br, "calc");
			System.out.println(map.toString());
		} catch (IOException e) {
			logger.info(e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			logger.info(e.getMessage());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

	}
}
