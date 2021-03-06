package com.streamr.broker;

import com.streamr.client.protocol.message_layer.StreamMessage;
import com.streamr.client.protocol.message_layer.StreamMessageV29;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.ByteBuffer;

public class StreamrBinaryMessageV29 extends StreamrBinaryMessage {
    public static final byte VERSION = 29; //0x1D

    private final SignatureType signatureType;
    private final byte[] addressBytes;
    private final byte[] signatureBytes;

    public StreamrBinaryMessageV29(String streamId, int partition, long timestamp, int ttl, byte contentType, byte[] content,
                                   SignatureType signatureType, String address, String signature) {
        super(VERSION, streamId, partition, timestamp, ttl, contentType, content);
        this.signatureType = signatureType;
        this.addressBytes = hexToBytes(address);
        this.signatureBytes = hexToBytes(signature);
    }

    public StreamrBinaryMessageV29(String streamId, int partition, long timestamp, int ttl, byte contentType, byte[] content,
                                   SignatureType signatureType, byte[] addressBytes, byte[] signatureBytes) {
        super(VERSION, streamId, partition, timestamp, ttl, contentType, content);
        this.signatureType = signatureType;
        this.addressBytes = addressBytes;
        this.signatureBytes = signatureBytes;
    }

    @Override
    protected void toByteBuffer(ByteBuffer bb) {
        super.toByteBuffer(bb);
        bb.put(signatureType.getId()); // 1 byte
        if (signatureType == SignatureType.SIGNATURE_TYPE_ETH) {
            bb.put(addressBytes); // 20 bytes
            bb.put(signatureBytes); // 65 bytes
        }
    }

    @Override
    public int sizeInBytes() {
        if (signatureType == SignatureType.SIGNATURE_TYPE_NONE) {
            // super + signatureType
            return super.sizeInBytes() + 1;
        } else if (signatureType == SignatureType.SIGNATURE_TYPE_ETH) {
            // super + signatureType + addressBytes + signatureBytes
            return super.sizeInBytes() + 1 + 20 + 65;
        } else {
            throw new IllegalArgumentException("Unknown signature type: "+signatureType);
        }
    }

    public SignatureType getSignatureType() {
        return signatureType;
    }

    public String getAddress() {
        return bytesToHex(addressBytes);
    }

    public String getSignature() {
        return bytesToHex(signatureBytes);
    }

    private byte[] hexToBytes(String s) {
        if (s == null) {
            return null;
        }
        if (s.startsWith("0x")) {
            return DatatypeConverter.parseHexBinary(s.substring(2));
        }
        return DatatypeConverter.parseHexBinary(s);
    }

    private String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return "0x" + DatatypeConverter.printHexBinary(bytes);
    }

    @Override
    public StreamMessageV29 toStreamrMessage(Long offset, Long previousOffset) throws IOException {
        return new StreamMessageV29(streamId, partition, timestamp, ttl, offset, previousOffset, StreamMessage.ContentType.fromId(contentType), toString(),
                StreamMessage.SignatureType.fromId(signatureType.getId()), getAddress(), getSignature());
    }
}
