package bismark.it.blogic.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bismark.it.blogic.interfaces.IEnterDataService;
import bismark.it.blogic.interfaces.IPPNService;

public class EnterDataService implements IEnterDataService, Serializable {

	private static final long serialVersionUID = 1L;

	private static final String EQUALY = "=";

	private static final String QUOTE = "'";

	private String SEPARATOR;

	public String getSEPARATOR() {
		return SEPARATOR;
	}

	public void setSEPARATOR(String sEPARATOR) {
		SEPARATOR = sEPARATOR;
	}

	private IPPNService ppnService = new PPNService();

	private List<String> list = new ArrayList<String>();

	public EnterDataService(List<String> lst) {
		this.list = lst;
	}

	@Override
	public String getDataFromCocoleWithSeprator(String separator,
			BufferedReader br, String finishCommand) throws IOException {
		SEPARATOR = separator;
		HashMap<String, String> map = new LinkedHashMap<String, String>();

		int count = 0;
		String line = null;
		while (!(line = br.readLine()).equals(finishCommand)) {
			String[] f = line.split(separator);
			int u = 1;
			for (String string : f) {
				map.put(list.get(count) + u, string);
				u++;
			}
			map.put(String.valueOf((new Date()).getTime()), "\n");
			count++;
		}
		return getFinalExpression(map);
	}

	private String getFinalExpression(HashMap<String, String> map) {
		StringBuilder strBld = new StringBuilder();
		for (Entry<String, String> entry : map.entrySet()) {
			strBld.append(analyzeFirstSymbolAndReturnRes(entry.getValue(), map))
					.append((entry.getValue().equals("\n")) ? "" : SEPARATOR);
		}
		return strBld.toString();
	}

	private String analyzeFirstSymbolAndReturnRes(String value,
			HashMap<String, String> map) {
		if (!value.isEmpty()) {
			try {
				if (value.substring(0, 1).equals(QUOTE)) {
					return value.substring(1, value.length());
				} else if (value.substring(0, 1).equals(EQUALY)) {
					return changeCellForValues(
							value.substring(1, value.length()), map);
				} else {
					return value;
				}
			} catch (Exception e) {
				return "#Error: " + e.getMessage();
			}
		} else {
			return value;
		}
	}

	private String changeCellForValues(String expr, HashMap<String, String> map)
			throws Exception {
		String stringPatternGetCellName = "([A-Z]\\d+)";
		Pattern patternCell = Pattern.compile(stringPatternGetCellName);
		Matcher m = patternCell.matcher(expr);

		while (m.find()) {
			expr = expr.replace(m.group(), map.get(m.group()));
		}
		return String.valueOf(ppnService.calculate(expr));
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

}
