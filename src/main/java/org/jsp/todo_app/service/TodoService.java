package org.jsp.todo_app.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.jsp.todo_app.dao.TodoDao;
import org.jsp.todo_app.dto.Task;
import org.jsp.todo_app.dto.User;

public class TodoService {

	TodoDao dao=new TodoDao();
	
	public void signup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String name=req.getParameter("name");
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		String mobile=req.getParameter("mobile");
		String dob=req.getParameter("dob");
		String gender=req.getParameter("gender");
		

		User user =new User();
		user.setDob(LocalDate.parse(req.getParameter("dob")));
		user.setName(req.getParameter("name"));
		user.setEmail(req.getParameter("email"));
		user.setGender(req.getParameter("gender"));
		user.setMobile(Long.parseLong(req.getParameter("mobile")));
		user.setPassword(req.getParameter("password"));
		
		
		List<User> users1=dao.findByEmail(user.getEmail());
		List<User> users2=dao.findByMobile(user.getMobile());
		if(users1.isEmpty() && users2.isEmpty()){
		dao.saveUser(user);
        resp.getWriter().println("<h1 style='color:green'>Account Created Successfully</h1>");
		req.getRequestDispatcher("login.html").include(req,resp);
		}
		else{
			if(users1.isEmpty()){
				resp.getWriter().println("<h1 style='color:red'>mobile number should be unique</h1>");
			}
			else if(users2.isEmpty()){
				resp.getWriter().println("<h1 style='color:red'>email sholud be unique</h1>");
			}
			else{
				resp.getWriter().println("<h1 style='color:red'>mobile number and email should be unique</h1>");
			}
			req.getRequestDispatcher("signup.html").include(req,resp);
		}
		
	}

	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String emph = req.getParameter("emph");
		String passsword = req.getParameter("password");
		List<User> list = null;
		try {
			long mobile = Long.parseLong(emph);
			list = dao.findByMobile(mobile);
			if (list.isEmpty())
				resp.getWriter().print("<h1 align='center' style='color:red'>Incorrect Mobile Number</h1>");
		} catch (NumberFormatException e) {
			String email = emph;
			list = dao.findByEmail(email);
			if (list.isEmpty())
				resp.getWriter().print("<h1 align='center' style='color:red'>Incorrect Email</h1>");
		}

		if (!list.isEmpty()) {
			User user=list.get(0);
			if (user.getPassword().equals(passsword)) {
				req.getSession().setAttribute("user",user);
				resp.getWriter().print("<h1 align='center' style='color:green'>Login Success</h1>");
				
				List<Task> tasks =dao.fetchTaskByUserId(user.getId());
				req.setAttribute("tasks", tasks);
				
				
				req.getRequestDispatcher("home.jsp").include(req, resp);
			} else {
				resp.getWriter().print("<h1 align='center' style='color:red'>Incorrect Password</h1>");
				req.getRequestDispatcher("login.html").include(req, resp);
			}
		} else {
			req.getRequestDispatcher("login.html").include(req, resp);
		}
	}
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		req.getSession().removeAttribute("user");
		resp.getWriter().print("<h1 align='center' style='color:green'> Logout Success</h1>");
		req.getRequestDispatcher("login.html").include(req, resp);
	}

	public void addTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	String name=req.getParameter("name");
	String description=req.getParameter("description");
    Part image=req.getPart("image");	
    Task task=new Task();
    task.setName(name);
    task.setDescription(description);
    task.setStatus(false);
	task.setAddTime(LocalDateTime.now());
	byte[] pic=new byte[image.getInputStream().available()];
	image.getInputStream().read(pic);
	task.setImage(pic);
	
	
	User user=(User)req.getSession().getAttribute("user");
	task.setUser(user);
	dao.saveTask(task);
	
	resp.getWriter().print("<h1 align='center' style='color:green'>Task added succussfully</h1>");
	List<Task> tasks =dao.fetchTaskByUserId(user.getId());
	req.setAttribute("tasks", tasks);
	req.getRequestDispatcher("home.jsp").include(req, resp);
		
	}
	public void completeTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Task task = dao.findTaskById(id);
		task.setStatus(true);
		dao.updateTask(task);
		
		resp.getWriter().print("<h1 align='center' style='color:green'>Status change success</h1>");
		
		User user = (User) req.getSession().getAttribute("user");
		List<Task> tasks = dao.fetchTaskByUserId(user.getId());
		req.setAttribute("tasks", tasks);
		
		req.getRequestDispatcher("home.jsp").include(req, resp);
		
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("id"));
		Task task = dao.findTaskById(id);
		
		dao.deleteTask(task);
		resp.getWriter().print("<h1 align='center' style='color:green'>Task deleted success</h1>");
		
		User user = (User) req.getSession().getAttribute("user");
		List<Task> tasks = dao.fetchTaskByUserId(user.getId());
		req.setAttribute("tasks", tasks);
		
		req.getRequestDispatcher("home.jsp").include(req, resp);
		
	}

	public void updateTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String name=req.getParameter("name");
		String description=req.getParameter("description");
	    Part image=req.getPart("image");
	    int id = Integer.parseInt(req.getParameter("id"));
	    
	    Task task=new Task();
	    task.setId(id);
	    task.setName(name);
	    task.setDescription(description);
	    task.setStatus(false);
		task.setAddTime(LocalDateTime.now());
		
		byte[] pic=new byte[image.getInputStream().available()];
		image.getInputStream().read(pic);
		
		if(pic.length==0)
			task.setImage(dao.findTaskById(id).getImage());
		else
			task.setImage(pic);
		
		User user=(User)req.getSession().getAttribute("user");
		task.setUser(user);
		
		dao.updateTask(task);
		
		resp.getWriter().print("<h1 align='center' style='color:green'>Task updated succussfully</h1>");
		List<Task> tasks =dao.fetchTaskByUserId(user.getId());
		req.setAttribute("tasks", tasks);
		req.getRequestDispatcher("home.jsp").include(req, resp);
		
	}

}