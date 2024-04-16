package org.jsp.todo_app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.jsp.todo_app.dto.Task;
import org.jsp.todo_app.dto.User;

public class TodoDao
{
	EntityManagerFactory factory=Persistence.createEntityManagerFactory("m7");
	EntityManager manager=factory.createEntityManager();
	EntityTransaction transaction=manager.getTransaction();
	
	public void saveUser(User user) {
		transaction.begin();
		manager.persist(user);
		transaction.commit();
		
	}
	public List<User> findByEmail(String email){
		return manager.createQuery("select x from User x where email=?1").setParameter(1, email).getResultList();
	}
	
	public List<User> findByMobile(long mobile){
		return manager.createQuery("select x from User x where mobile=?1").setParameter(1, mobile).getResultList();
	}
	
	public void saveTask(Task task) {
		transaction.begin();
		manager.persist(task);
		transaction.commit();
		
	}
	
	public List<Task> fetchTaskByUserId(int userId)
	{
		return manager.createQuery("select x from Task x where user_Id=?1").setParameter(1,userId).getResultList() ;
	}
	
	public Task findTaskById(int id){
		return manager.find(Task.class, id);
	}
	
	 public void updateTask(Task task){
		 transaction.begin();
			manager.merge(task);
			transaction.commit();
	 }
	 public void deleteTask(Task task) {
			manager.getTransaction().begin();
			manager.remove(task);
			manager.getTransaction().commit();
	 }

}