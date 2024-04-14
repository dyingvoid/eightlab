package project.eightlab.fileProcessing;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Component
public class ImageProcessor implements FileProcessor {
    @Override
    public boolean isSupported(String path) {
        return Optional.ofNullable(path)
                .filter(s -> s.contains("."))
                .map(s -> s.substring(s.lastIndexOf(".") + 1))
                .filter(s -> s.equals("jpeg") || s.equals("jpg") || s.equals("png"))
                .isPresent();
    }

    @Override
    public String getDescription() {
        return """
                1. Gets size of an image.
                2. Gets exif data of an image.
                3. Prints image in ascii characters.
                """;
    }

    @Override
    public void process(String path, int option) throws IOException {
        File imageFile = new File(path);

        switch (option) {
            case (1):
                String size = getSize(imageFile);
                System.out.println(size);
                break;
            case (2):
                String exif = getMetaData(imageFile);
                System.out.println(exif);
                break;
            case (3):
                printImage(imageFile);
                break;
        }
    }

    private void printImage(File imageFile) throws IOException {
        ImageConsolePrinter printer = new ImageConsolePrinter();
        printer.ConvertToAscii(imageFile);
    }

    private String getSize(File imageFile) throws IOException {
        @Cleanup ImageInputStream imageInputStream =
                ImageIO.createImageInputStream(imageFile);
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);

        if (imageReaders.hasNext()) {
            ImageReader reader = imageReaders.next();
            reader.setInput(imageInputStream);
            int width = reader.getWidth(0);
            int height = reader.getHeight(0);
            return "size: " + width + "x" + height;
        }

        return "0x0";
    }

    @SneakyThrows
    private String getMetaData(File imageFile) throws IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
        ExifIFD0Directory exifDir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

        if (exifDir != null) {
            Collection<Tag> tags = exifDir.getTags();
            StringBuilder sb = new StringBuilder();

            for (Tag tag : tags) {
                sb.append(tag.getTagName()).append(": ").append(tag.getDescription());
            }

            return sb.toString();
        }

        return "empty";
    }
}
