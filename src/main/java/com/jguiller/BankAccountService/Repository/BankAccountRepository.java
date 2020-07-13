package com.jguiller.BankAccountService.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jguiller.BankAccountService.Model.BankAccount;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, Integer>{

	void deleteByIdCuenta(int idCuenta);

	Optional<BankAccount> findByIdCuenta(int idCuenta);

}
