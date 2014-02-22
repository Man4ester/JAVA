package bismark.it.blogic.services;

import java.util.LinkedList;

import bismark.it.blogic.interfaces.IPPNService;

public class PPNService implements IPPNService {

	@Override
	public int calculate(String exp) {
		return eval(exp);
	}

	private int eval(String s) {
		LinkedList<Integer> st = new LinkedList<Integer>();
		LinkedList<Character> op = new LinkedList<Character>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (isDelim(c))
				continue;
			if (c == '(')
				op.add('(');
			else if (c == ')') {
				while (op.getLast() != '(')
					processOperator(st, op.removeLast());
				op.removeLast();
			} else if (isOperator(c)) {
				while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
					processOperator(st, op.removeLast());
				op.add(c);
			} else {
				String operand = "";
				while (i < s.length() && Character.isDigit(s.charAt(i)))
					operand += s.charAt(i++);
				--i;
				st.add(Integer.parseInt(operand));
			}
		}
		while (!op.isEmpty())
			processOperator(st, op.removeLast());
		return st.get(0);
	}

	private void processOperator(LinkedList<Integer> st, char op) {
		int r = st.removeLast();
		int l = st.removeLast();
		switch (op) {
		case '+':
			st.add(l + r);
			break;
		case '-':
			st.add(l - r);
			break;
		case '*':
			st.add(l * r);
			break;
		case '/':
			st.add(l / r);
			break;
		case '%':
			st.add(l % r);
			break;
		}
	}

	private int priority(char op) {
		switch (op) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
		case '%':
			return 2;
		default:
			return -1;
		}
	}

	private boolean isDelim(char c) {
		return c == ' ';
	}

	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
	}

}

