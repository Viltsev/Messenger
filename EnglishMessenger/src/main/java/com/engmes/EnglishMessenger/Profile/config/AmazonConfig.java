package com.engmes.EnglishMessenger.Profile.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class AmazonConfig {
    @Bean
    public AmazonS3 getAwsClient() {
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
        return s3;
    }
}
