package org.mkzaman.grpcservice;

import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.util.JsonFormat;

public class GrpcClient {

    public static void main(String[] args) throws InvalidProtocolBufferException {
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

        final int serializedSize = paymentRequest.getSerializedSize();
        System.out.println("serializedSize = " + serializedSize);

        final String jsonMessage = JsonFormat.printer().print(paymentRequest);
        System.out.println("jsonMessageSize = " + jsonMessage.length());
        PaymentResponse paymentResponse = blockingStub.sendPayment(paymentRequest);

        channel.shutdown();
    }
}
