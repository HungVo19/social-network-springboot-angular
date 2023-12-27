package com.olympus.service.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.olympus.config.FirebaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {
    @InjectMocks
    ImageServiceImpl imageService;

    @Mock
    FirebaseConfig firebaseConfig;

    @Test
    public void testGetImageUrl() {
        // Arrange
        String name = "image.jpg";
        String expectedUrl = "https://bucketname/path/" + name;
        when(firebaseConfig.getImageUrl()).thenReturn("https://bucketname/path/%s");

        // Act
        String url = imageService.getImageUrl(name);

        // Assert
        assertEquals(expectedUrl, url, "URL should be correctly formatted with the image name");
    }
}