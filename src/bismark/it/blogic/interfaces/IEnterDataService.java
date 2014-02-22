package bismark.it.blogic.interfaces;

import java.io.BufferedReader;
import java.io.IOException;

public interface IEnterDataService {
	public String getDataFromCocoleWithSeprator(String separator,
			BufferedReader br, String finishCommand) throws IOException,
			IndexOutOfBoundsException;
}
