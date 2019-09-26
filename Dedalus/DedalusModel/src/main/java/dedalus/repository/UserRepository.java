package dedalus.repository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.User;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE)
@Transactional

public class UserRepository extends AbstractJpaDao<User> {

	@Override
	protected Class<User> getEntityBean() {
		return User.class;
	}
	
	public int countUsers() {
		return ((Number)entityManager.createQuery("select count(user) from User user")
				.getSingleResult()).intValue();
	}

	public User findByUserName(String userName) {
		try { 
			return (User)entityManager.createQuery("select user from User user where user.username = :username")
			.setParameter("username", userName)
			.getSingleResult();
		} catch(NoResultException noResultException) {
			return null;
		}
	}
}
