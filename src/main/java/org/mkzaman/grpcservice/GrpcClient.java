package org.mkzaman.grpcservice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        PaymentServiceGrpc.PaymentServiceBlockingStub blockingStub
                = PaymentServiceGrpc.newBlockingStub(channel);

        PaymentRequest paymentRequest = PaymentRequest.newBuilder()
                                .setSender(Person.newBuilder().setName("Alice").setId(1).setEmail("alice@alice.com").build())
                                .setReceiver(Person.newBuilder().setName("Bob").setId(2).setEmail("bob@bob.com").build())
                                .setPurpose("Private")
                                .setAmount(1000.00)
                                .build();

        PaymentResponse paymentResponse = blockingStub.sendPayment(paymentRequest);

        channel.shutdown();
    }
}
