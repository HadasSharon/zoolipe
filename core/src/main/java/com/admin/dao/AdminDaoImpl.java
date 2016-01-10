package com.admin.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.admin.domain.AdminImpl;



@Repository("blAdminDao")
public class AdminDaoImpl implements AdminDao {
	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Resource(name = "blEntityConfiguration")
	protected EntityConfiguration entityConfiguration;

	@Override

	public long saveAccount(AdminImpl adminImpl) {
		// TODO Auto-generated method stub
		try {
			
			adminImpl = new AdminImpl();
			//adminImpl.setId(new Long(1));
			adminImpl.setAccountNumber("aaa");
			adminImpl.setName("ad");
			System.out.print("Saving data" + em.isOpen());

			em.persist(adminImpl);
			em.flush();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

}
