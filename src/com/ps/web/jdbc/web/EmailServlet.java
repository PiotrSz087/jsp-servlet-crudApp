package com.ps.web.jdbc.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ps.web.jdbc.api.EmailSendApi;
import com.ps.web.jdbc.model.Email;

@WebServlet("/emailSender")
public class EmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Email em = new Email();
		em.setName(request.getParameter("name"));
		em.setEmail(request.getParameter("email"));
		em.setSubject(request.getParameter("subject"));
		em.setMessage(request.getParameter("message"));
		try {
			EmailSendApi emailSenderApi = new EmailSendApi();
			emailSenderApi.sendEmail(em);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.sendRedirect("/");
		}
	}
}
