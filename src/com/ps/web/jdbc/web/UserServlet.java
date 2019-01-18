package com.ps.web.jdbc.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.ps.web.jdbc.dao.UserDao;
import com.ps.web.jdbc.model.User;

@WebServlet("/")
public class UserServlet extends HttpServlet {
	/**
	 * Servlet implementation class UserCrud to run with all links in the .jsp pages
	 * work properly just change path from "/jsp-servlet-crudApp" to "/" in tomcat
	 * server.xml file
	 */
	
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/userTracker")
	private DataSource datasource;

	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = new UserDao(datasource);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String path = request.getServletPath();

			switch (path) {
			case "/list":
				showListOfUsers(request, response);
				break;
			case "/insert":
				showAddForm(request, response);
				break;
			case "/update":
				showUpdateForm(request, response);
				break;
			case "/addProcessing":
				insertUser(request, response);
				break;
			case "/updateProcessing":
				updateUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/email":
				showEmailForm(request, response);
				break;
			default:
				showListOfUsers(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showEmailForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		Long id = Long.parseLong(request.getParameter("itemId"));
		User user = userDao.getUser(id);
		request.setAttribute("userFromDbToEmail", user);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/emailForm.jsp");
		dispatcher.forward(request, response);

	}
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		User user = new User();
		user.setId(Long.parseLong(request.getParameter("userId")));
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		
		Boolean check = userDao.updateUserInfo(user);
		
		if (check) {
			request.setAttribute("info", "User update succesfully");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list");
			dispatcher.forward(request, response);
		}
	}

	private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		Long id = Long.parseLong(request.getParameter("itemId"));
		User user = userDao.getUser(id);
		request.setAttribute("userFromDb", user);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/insertEditForm.jsp");
		dispatcher.forward(request, response);
		
	}

	private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/insertEditForm.jsp");
				dispatcher.forward(request, response);
		
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("itemId"));
		 
		Boolean check = userDao.removeItem(id);
		
		if (check) {
			request.setAttribute("info", "Item successful deleted");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list");
			dispatcher.forward(request, response);
		}
		
	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = new User();
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		userDao.addNewUser(user);

		response.sendRedirect("list");
	}

	private void showListOfUsers(HttpServletRequest request, HttpServletResponse response)
			throws Exception, ServletException, IOException {
		List<User> myList = userDao.showListOfUsers();
		request.setAttribute("myList", myList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/userList.jsp");
		dispatcher.forward(request, response);
	}
}
