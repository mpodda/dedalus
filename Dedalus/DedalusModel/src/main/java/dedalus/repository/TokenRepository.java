package dedalus.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import dedalus.domain.Pupil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import dedalus.domain.Token;
import dedalus.domain.TuitionCentre;
import dedalus.domain.search.SearchTokenCriteria;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class TokenRepository extends AbstractJpaDao<Token> {

	@Override
	protected Class<Token> getEntityBean() {
		return Token.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> batches(Long tuitionCentreId) {
		//"select distinct token.batch from Token token where token.tuitionCentre.id=:tcId"
		return entityManager.createQuery("select token.number from Token token where token.tuitionCentre.id=:tcId and token.number=1")
		.setParameter("tcId", tuitionCentreId).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Token> byTuitionCentre(Long tuitionCentreId) {
		return entityManager.createQuery("select distinct token from Token token where token.tuitionCentre.id=:tcId order by token.creationDate")
				.setParameter("tcId", tuitionCentreId)
			    .getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Token> byPupil(Long pupilId) {
		return entityManager.createQuery("select distinct token from Token token where token.pupil.id=:pupilId order by token.assignDate")
				.setParameter("pupilId", pupilId)
				.getResultList();
	}

	public Token byCode(String code) {
		return (Token)entityManager.createQuery("select distinct token from Token token where token.value=:code")
				.setParameter("code", code)
				.getSingleResult();
	}
	
	public int countTokensByStatus(Long tcId, int status) {
		return ((Number)entityManager.createQuery("select Count(token) from Token token where token.tuitionCentre.id=:id and status = :status")
				.setParameter("id", tcId)
				.setParameter("status", status).getSingleResult()).intValue();
	}
	
	
	public int countTotalAvailable(Long tcId, int completedStatus) {
		return ((Number)entityManager.createQuery("select Count(token) from Token token where token.tuitionCentre.id=:id and " +
				" token.status <> :completedStatus")
				.setParameter("id", tcId)
				.setParameter("completedStatus", completedStatus)
				.getSingleResult()).intValue();
		
		/*
		return ((Number)entityManager.createQuery("select Count(token) from Token token where token.tuitionCentre.id=:id and "
				+ "(select Count(token2) from Token token2 where token2.batch = token.batch and token2.status = :completedStatus) <> "
				+ "(select Count(token2) from Token token2 where token2.batch = token.batch)")
				.setParameter("id", tcId)
				.setParameter("completedStatus", completedStatus)
				.getSingleResult()).intValue();
		*/
	} 
	
	public int countTotalAssigned(Long tcId, int notStartedStatus, int onGoingStatus) {
		return ((Number)entityManager.createQuery("select Count(token) from Token token "
				+ "where token.tuitionCentre.id=:id  "
				+ " and (token.status = :notStartedStatus or token.status = :onGoingStatus)")
				.setParameter("id", tcId)
				.setParameter("notStartedStatus", notStartedStatus)
				.setParameter("onGoingStatus", onGoingStatus)
				.getSingleResult()).intValue();
	}
	
	public int countTotalAssignedAndCompleted(Long tcId, int notStartedStatus, int onGoingStatus, int completedStatus) {
		return ((Number)entityManager.createQuery("select Count(token) "
				+ "from Token token "
				+ "where token.tuitionCentre.id=:id and "
				+ "(token.status = :notStartedStatus or token.status = :onGoingStatus or token.status = :completedStatus) and "
				+ "(select Count(token2) from Token token2 where token2.batch = token.batch and token2.status = :completedStatus) <> "
				+ "(select Count(token2) from Token token2 where token2.batch = token.batch)")
				.setParameter("id", tcId)
				.setParameter("notStartedStatus", notStartedStatus)
				.setParameter("onGoingStatus", onGoingStatus)
				.setParameter("completedStatus", completedStatus)
				.getSingleResult()).intValue();
	}
	
	public int countCompleted(Long tcId, int completedStatus) {
		return ((Number)entityManager.createQuery("select Count(token) "
				+ "from Token token "
				+ "where token.tuitionCentre.id=:id and "
				+ "token.status = :completedStatus and "
				+ "(select Count(token2) from Token token2 where token2.batch = token.batch and token2.status = :completedStatus) <> "
				+ "(select Count(token2) from Token token2 where token2.batch = token.batch)")
				.setParameter("id", tcId)
				.setParameter("completedStatus", completedStatus)
				.getSingleResult()).intValue();
	}
	
	
	public List<Token> search(SearchTokenCriteria tokenCriteria) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Token> criteriaQuery = criteriaBuilder.createQuery(Token.class);
		
		Root<Token> queryRoot = criteriaQuery.from(Token.class);
		criteriaQuery.select(queryRoot);
		criteriaQuery.distinct(true);

		
		/* 
		  ---------------------
		  -- Construct query --
		  --------------------- 
		 */
		
		List<Predicate> criteria = new ArrayList<Predicate>();

		//Tuition Centre
		if (tokenCriteria.hasTuitionCentre()) {
			Join<Token, TuitionCentre> joinToTuitionCentre = queryRoot.join("tuitionCentre", JoinType.INNER);
			ParameterExpression<Long> p = criteriaBuilder.parameter(Long.class, SearchTokenCriteria.ParameterNames.TUITION_CENTRE_ID.parameterName());
			criteria.add(criteriaBuilder.equal(joinToTuitionCentre.get("id"), p));
		}
		
		//Creation Date From
		if (tokenCriteria.hasCreationDateFrom()) {
			ParameterExpression<Date> p = criteriaBuilder.parameter(Date.class, SearchTokenCriteria.ParameterNames.CREATION_DATE_FROM.parameterName());
			criteria.add(criteriaBuilder.greaterThanOrEqualTo(queryRoot.<Date>get("creationDate"), p.as(Date.class)));
		}
		
		
		//Creation Date To
		if (tokenCriteria.hasCreationDateTo()) {
			ParameterExpression<Date> p = criteriaBuilder.parameter(Date.class, SearchTokenCriteria.ParameterNames.CREATION_DATE_TO.parameterName());
			criteria.add(criteriaBuilder.lessThanOrEqualTo(queryRoot.<Date>get("creationDate"), p.as(Date.class)));
		}
		
		//Payed
		if (tokenCriteria.hasPayed()) {
			ParameterExpression<Boolean> p = criteriaBuilder.parameter(Boolean.class, SearchTokenCriteria.ParameterNames.PAYED.parameterName());
			criteria.add(criteriaBuilder.equal(queryRoot.get("payed"), p));
		}

		//Pupil
		if (tokenCriteria.hasPupil()) {
			Join<Token, Pupil> joinToPupil = queryRoot.join("pupil", JoinType.INNER);
			ParameterExpression<Long> p = criteriaBuilder.parameter(Long.class, SearchTokenCriteria.ParameterNames.PUPIL_ID.parameterName());
			criteria.add(criteriaBuilder.equal(joinToPupil.get("id"), p));
		}


		/* 
		  -----------------
		  -- Build where --
		  -----------------
		 */

		if (criteria.size() == 1) {
			criteriaQuery.where(criteria.get(0));
		} else {
			criteriaQuery.where(criteriaBuilder.and(criteria.toArray(new Predicate[0])));
		}
		
		/* 
		  -------------------
		  -- Assign values --
		  ------------------- 
		 */
		
		TypedQuery<Token> typedQuery = entityManager.createQuery(criteriaQuery);
		
		if (tokenCriteria.hasTuitionCentre()) {
			typedQuery.setParameter(SearchTokenCriteria.ParameterNames.TUITION_CENTRE_ID.parameterName(), tokenCriteria.getTuitionCentre().getId());
		}
		
		if (tokenCriteria.hasCreationDateFrom()) {
			typedQuery.setParameter(SearchTokenCriteria.ParameterNames.CREATION_DATE_FROM.parameterName(), tokenCriteria.getCreationDateFrom());
		}
		
		if (tokenCriteria.hasCreationDateTo()) {
			typedQuery.setParameter(SearchTokenCriteria.ParameterNames.CREATION_DATE_TO.parameterName(), tokenCriteria.getCreationDateTo());
		}

		if (tokenCriteria.hasPayed()) {
			typedQuery.setParameter(SearchTokenCriteria.ParameterNames.PAYED.parameterName(), tokenCriteria.getPayed());
		}

		if (tokenCriteria.hasPupil()) {
			typedQuery.setParameter(SearchTokenCriteria.ParameterNames.PUPIL_ID.parameterName(), tokenCriteria.getPupil().getId());
		}

		return typedQuery.getResultList();
	}
}