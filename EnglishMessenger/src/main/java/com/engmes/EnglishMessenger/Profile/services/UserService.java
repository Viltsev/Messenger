package com.engmes.EnglishMessenger.Profile.services;

import com.engmes.EnglishMessenger.Profile.config.AmazonConfig;
import com.engmes.EnglishMessenger.Profile.utils.Base64DecodedMultipartFile;
import com.engmes.EnglishMessenger.Profile.model.OnboardingInfo;
import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.repository.UserRepository;
import com.engmes.EnglishMessenger.Profile.utils.UniqueIdGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.auth.AWSCredentials;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AmazonConfig amazonConfig;
    private final UniqueIdGenerator idGenerator;

    public Optional<User> findByEmail(String email){
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Transactional
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не был найден", email)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("EmptyRole"))
        );
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByEmail(user.getEmail());

        if (userFromDB != null) {
            return false;
        }

        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public void updateUser(User user) { userRepository.save(user); }

    public int getLanguageLevel(User user) {
        if (user.getLanguageLevel() == null) {
            return 0;
        }
        else {
            String strLanguageLevel = user.getLanguageLevel();

            return switch (strLanguageLevel) {
                case "A1" -> 1;
                case "A2" -> 2;
                case "B1" -> 3;
                case "B2" -> 4;
                case "C1" -> 5;
                case "C2" -> 6;
                default -> 0;
            };
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void uploadPhoto(MultipartFile multipartFile, String bucketName, String fileName) throws IOException {
        AWSCredentials credentials = new BasicAWSCredentials(
                "UHV1BYZW3MODFPHOR31Z",
                "Pew5ZaEi5PpXHcmQ9UNOBQkwDJR3IAaGjbhkl2QP"
        );

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                "https://s3.timeweb.cloud","ru-1"
                        )
                )
                .build();

        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, multipartFile.getInputStream(), null);

        s3.putObject(request);
    }

    public void setOnboardingInfo(OnboardingInfo onboardingInfo) {
        String email = onboardingInfo.getEmail();
        User user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' has been not found", email)
        ));

        Optional<String> photoId = setPhoto(onboardingInfo.getPhoto());

        user.setUsername(onboardingInfo.getUsername());
        user.setDateOfBirth(onboardingInfo.getDateOfBirth());

        photoId.ifPresent(user::setPhoto);

        updateUser(user);
        logger.info("Onboarding data successfully added!");
    }

    public Optional<String> setPhoto(String photo) {
        // get base64 code of image from client
        byte[] photoByte = Base64.getDecoder().decode(photo);
        // decode to MultipartFile
        MultipartFile photoFile = new Base64DecodedMultipartFile(photoByte);
        // generate photo id
        String photoId = idGenerator.generateUniqueId();
        String fileName = String.format("%s.jpg", photoId);

        try {
            uploadPhotoAws(
                    photoFile,
                    "c69f4719-fa278707-76a9-4ddc-bc9e-bc582ad152d2",
                    fileName
            );

        } catch (IOException e) {
            logger.info(e.getMessage());
            return Optional.empty();
        }
        return fileName.describeConstable();
    }

    public void uploadPhotoAws(MultipartFile multipartFile, String bucketName, String fileName) throws IOException {
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, multipartFile.getInputStream(), null);
        amazonConfig.getAwsClient().putObject(request);
    }
}
