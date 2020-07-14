package com.jguiller.BankAccountService.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jguiller.BankAccountService.Model.BankAccount;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, Integer>{

	BankAccount findByIdCuenta(Integer idCuenta);
	void deleteByIdCuenta(Integer idCuenta);
	
}
