package susussg.pengreenlive.util.Service;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageService {

    public byte[] compressAndResizeImage(byte[] imageBytes, int targetSize, float quality) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage originalImage = ImageIO.read(inputStream);

        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        int newWidth, newHeight;

        if (originalWidth < originalHeight) {
            newWidth = targetSize;
            newHeight = (int) (((double) originalHeight / originalWidth) * targetSize);
        } else {
            newHeight = targetSize;
            newWidth = (int) (((double) originalWidth / originalHeight) * targetSize);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(originalImage)
            .size(newWidth, newHeight)
            .outputFormat("jpg")
            .outputQuality(quality)
            .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }
}