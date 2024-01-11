package id.kevinchristian.snap.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import id.kevinchristian.snap.dto.response.MediaFileResponseDTO;
import id.kevinchristian.snap.service.CloudStorageService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FirebaseStorageServiceImpl implements CloudStorageService {
    private static final String BUCKET_NAME = "snap-346c3.appspot.com";
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME +"/o/%s?alt=media";

    @Override
    public MediaFileResponseDTO upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(getExtention(fileName));

            File file = convertToFile(multipartFile, fileName);
            String fileURL = uploadFile(file, fileName);
            file.delete();

            return new MediaFileResponseDTO("ID", fileName, multipartFile.getContentType(), fileURL);
        } catch (Exception e) {
            return null;
        }
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(
                new FileInputStream("src/main/resources/firebase/snap-346c3-firebase-adminsdk-5f4va-d07be35ad2.json"));

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file = new File(fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        return file;
    }

    private String getExtention(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
