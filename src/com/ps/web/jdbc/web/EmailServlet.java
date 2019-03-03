package com.ps.web.jdbc.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.catalina.tribes.util.Arrays;

import com.ps.web.jdbc.api.EmailSendApi;
import com.ps.web.jdbc.dao.UserDao;
import com.ps.web.jdbc.model.Email;

@WebServlet("/emailSender/*")
public class EmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/userTracker")
	private DataSource datasource;

	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDao(datasource);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			StringBuilder st = new StringBuilder().append(request.getServletPath()).append(request.getPathInfo());
			switch (st.toString()) {
			case "/emailSender/emailForm":
				showEmailForm(request, response);
				break;
			case "/emailSender/emailProceed":
				emailProcessing(request, response);
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showEmailForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String idd = Arrays.toString(request.getParameterValues("itemId")).replaceAll("\\{", "").replaceAll("\\}", "");
		request.setAttribute("ids", idd);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/emailForm.jsp");
		dispatcher.forward(request, response);
	}
	
	private void emailProcessing(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		if (request.getParameter("id").length() >= 1) {
			String id = request.getParameter("id");
			String subject = request.getParameter("subject");
			String message = request.getParameter("message");
			Map<String, String> mapOfRecipients = userDao.getNamesEmailsFromDb(id);
			EmailSendApi em = new EmailSendApi();
			em.sendEmail(new Email(subject, message, mapOfRecipients));
		}

		response.sendRedirect("/servlet");
	}
}
