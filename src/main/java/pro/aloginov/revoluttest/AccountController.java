package pro.aloginov.revoluttest;

import pro.aloginov.revoluttest.model.Account;
import pro.aloginov.revoluttest.protocol.AccountCreationRequest;
import pro.aloginov.revoluttest.protocol.AccountCreationResponse;
import pro.aloginov.revoluttest.protocol.AccountDetailsResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Singleton
@Path("account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    private final AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @POST
    public AccountCreationResponse createAccount(AccountCreationRequest request) {
        return new AccountCreationResponse(accountService.addAccount(request.userId, request.currency, request.value));
    }

    @GET
    @Path("{id}")
    public AccountDetailsResponse getAccountDetails(@PathParam("id") int id) {
        Account account = accountService.getAccount(id);
        return new AccountDetailsResponse(account.id, account.currency, account.value);
    }

    @GET
    @Path("user/{id}")
    public List<Integer> getUserAccountIds(@PathParam("id") int id) {
        List<Account> accounts = accountService.getAccountsByUser(id);
        return accounts.stream().map(a -> a.id).collect(toList());
    }

}
