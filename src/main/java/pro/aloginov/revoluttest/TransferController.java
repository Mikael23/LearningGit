package pro.aloginov.revoluttest;

import pro.aloginov.revoluttest.protocol.TransferRequest;
import pro.aloginov.revoluttest.protocol.TransferResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferController {

    private final TransferService transferService;

    @Inject
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @POST
    public TransferResponse transfer(TransferRequest request) {
        transferService.transfer(request.fromId, request.toId, request.currency, request.value);
        return new TransferResponse();
    }

}
