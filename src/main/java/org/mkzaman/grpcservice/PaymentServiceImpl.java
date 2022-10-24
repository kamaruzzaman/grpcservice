package org.mkzaman.grpcservice;

import io.grpc.stub.StreamObserver;

import java.time.Instant;
import java.util.UUID;

import com.google.protobuf.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public void sendPayment(PaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {

        Instant time = Instant.now();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(time.getEpochSecond())
                .setNanos(time.getNano()).build();
        PaymentResponse response = PaymentResponse.newBuilder()
                .setPaymentId(UUID.randomUUID().toString())
                .setPaymentTime(timestamp)
                .setPaymentStatus(PaymentStatus.SUCCESS)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        logger.info("Payment request = " + request);
        logger.info("Payment response = " + response);
    }
}
