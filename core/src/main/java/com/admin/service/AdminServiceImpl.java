package com.admin.service;



import javax.annotation.Resource;


import org.springframework.stereotype.Service;


import com.admin.dao.AdminDao;
import com.admin.domain.AdminImpl;

@Service("blAdminService")
public class AdminServiceImpl implements AdminService {

	@Resource(name = "blAdminDao")
	protected AdminDao adminDao;

	@Override
	public long savePassportType(AdminImpl adminImpl) {
		// TODO Auto-generated method stub
		 return adminDao.saveAccount(adminImpl);
	}

}
