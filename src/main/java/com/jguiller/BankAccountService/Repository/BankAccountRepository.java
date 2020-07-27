package com.jguiller.BankAccountService.Repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.jguiller.BankAccountService.Model.BankAccount;

import reactor.core.publisher.Mono;

@Repository
public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, Integer>{

	Mono<BankAccount> findByIdCliente(Integer id);

}
