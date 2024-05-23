package susussg.pengreenlive.order.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.nio.ByteBuffer;
import java.util.UUID;

@Converter(autoApply = false)
public class UUIDConverter implements AttributeConverter<String, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(String uuid) {
        if (uuid == null) {
            return null;
        }
        UUID u = UUID.fromString(uuid);

        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(u.getMostSignificantBits());
        byteBuffer.putLong(u.getLeastSignificantBits());
        return byteBuffer.array();
    }

    @Override
    public String convertToEntityAttribute(byte[] dbData) {
        if (dbData == null) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(dbData);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        UUID uuid = new UUID(high, low);
        return uuid.toString();
    }
}