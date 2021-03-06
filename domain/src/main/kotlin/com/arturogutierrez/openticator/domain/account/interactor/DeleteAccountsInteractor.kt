package com.arturogutierrez.openticator.domain.account.interactor

import com.arturogutierrez.openticator.domain.account.interactor.DeleteAccountsInteractor.Params
import com.arturogutierrez.openticator.domain.account.model.Account
import com.arturogutierrez.openticator.domain.account.repository.AccountRepository
import com.arturogutierrez.openticator.executor.PostExecutionThread
import com.arturogutierrez.openticator.executor.ThreadExecutor
import com.arturogutierrez.openticator.interactor.CompletableUseCase
import io.reactivex.Completable

class DeleteAccountsInteractor(private val accountRepository: AccountRepository,
                               threadExecutor: ThreadExecutor,
                               postExecutionThread: PostExecutionThread) : CompletableUseCase<Params>(threadExecutor,
    postExecutionThread) {

  override fun buildObservable(params: Params): Completable {
    val accounts = params.accounts
    if (accounts.isEmpty()) {
      throw IllegalStateException("You can't delete zero accounts")
    }
    return Completable.merge(accounts.map { accountRepository.remove(it) })
  }

  data class Params(val accounts: List<Account>)
}
