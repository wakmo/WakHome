/* Copyright 2008 Alin Dreghiciu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.atomikos.osgi.sample.web.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atomikos.osgi.sample.AccountManager;

class Controller extends HttpServlet {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

	/**
	 *
	 */
	private static final long serialVersionUID = -3659012210736234388L;

	private final AccountManager accountManager;

	Controller(final AccountManager accountManager) {

		this.accountManager = accountManager;
	}

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		final PrintWriter writer = response.getWriter();
		writer.println("<html><body align='center'>");
		writer.println("<h1>Atomikos</h1>");

		writer.println("<p>");
		writer.println("<form METHOD=GET>");
		String operation = request.getParameter("operation");
		if(operation==null){
			//the default...
			operation="balance";
		}
		writer.println("<input type='radio' name='operation' value='balance' " + isChecked(operation, "balance") + "> balance");
		writer.println("<input type='radio' name='operation' value='withdraw' " + isChecked(operation, "withdraw") + "> withdraw");
		writer.println("<input type='radio' name='operation' value='owner' " + isChecked(operation, "owner") + "> owner");
		writer.println("<br>");
		writer.println("accno (0 to 100): <INPUT type=text name='accno' value='50'> <br>");
		writer.println("amount: <INPUT type=text name='amount' value='10'> <br>");

		writer.println("<INPUT type='submit' value='Envoyer'>");

		writer.println("</form>");

		int accno = 50;
		String accnoStr = request.getParameter("accno");
		if (accnoStr != null) {

			try {
				accno = Integer.parseInt(accnoStr);
			} catch (NumberFormatException e1) {
				LOGGER.warning(e1.getMessage());
				throw new ServletException("accno must be an integer...");
			}
		}

		writer.println("last operation " + operation + " <br>");
		if ("balance".equals(operation)) {

			try {
				writer.println("balance on  " + accno + " is :" + accountManager.getBalance(accno));
			} catch (Exception e) {
				LOGGER.warning(e.getMessage());
				throw new ServletException(e);
			}

		} else if ("withdraw".equals(operation)) {
			int amount = 0;
			String amountStr = request.getParameter("amount");
			if (amountStr != null) {

				try {
					amount = Integer.parseInt(amountStr);
				} catch (NumberFormatException e1) {
					throw new ServletException("amount must be an integer...");
				}
			}
			try {
				accountManager.withdraw(accno, amount);
				writer.println("balance on  " + accno + " is :" + accountManager.getBalance(accno));
			} catch (Exception e) {
				LOGGER.warning(e.getMessage());
				throw new ServletException(e);
			}
		}  else if("owner".equals(operation)){
			try {
				writer.println("owner on  " + accno + " is :" + accountManager.getOwner(accno ));
			} catch (Exception e) {
				LOGGER.warning(e.getMessage());
				throw new ServletException(e);
			}
		}


		writer.println("</p>");
		writer.println("</body></html>");
	}

	private String isChecked(String operation, String option) {
		//The default...
		return operation.equals(option) ? "checked" : "";
	}
}
