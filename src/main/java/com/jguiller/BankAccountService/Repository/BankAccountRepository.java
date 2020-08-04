package com.jguiller.BankAccountService.Repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.jguiller.BankAccountService.Model.BankAccount;

import reactor.core.publisher.Flux;

@Repository
public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, Integer>{

	Flux<BankAccount> findByIdCliente(Integer id);

}
