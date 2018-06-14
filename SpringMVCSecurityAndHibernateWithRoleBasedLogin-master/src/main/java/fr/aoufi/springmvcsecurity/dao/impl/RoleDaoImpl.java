package fr.aoufi.springmvcsecurity.dao.impl;

import fr.aoufi.springmvcsecurity.dao.RoleDao;
import fr.aoufi.springmvcsecurity.dao.impl.AbstractDao;
import fr.aoufi.springmvcsecurity.model.Role;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements RoleDao {
	
	@Override
	public Role findById(int id) {
		return getByKey(id);
	}

	@Override
	public List<Role> findAll() {
		// Get Criteria Builder
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();

		// Create Criteria Query
		CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
		Root<Role> roleRoot = criteriaQuery.from(Role.class);
		criteriaQuery.select(roleRoot);

		// Utiliser des crit�res de requ�te avec session pour r�cup�rer tous les r�les
		Query<Role> query = getSession().createQuery(criteriaQuery);
		List<Role> roles = query.getResultList();

		return roles;
	}

	@Override
	public Role findByType(String type) {
		CriteriaQuery<Role> criteriaQuery = getCriteriaBuilder().createQuery(Role.class);
		Root<Role> root = criteriaQuery.from(Role.class);
		criteriaQuery.select(root);		
		criteriaQuery.where(getCriteriaBuilder().equal(root.get("type"), type));
		TypedQuery<Role> typedQuery = getSession().createQuery(criteriaQuery);
		try {
			return typedQuery.getSingleResult();
		} catch (final NoResultException nre) {
			return null;
		}
	}
	
}