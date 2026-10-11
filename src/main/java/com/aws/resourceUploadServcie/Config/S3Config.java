package com.aws.resourceUploadServcie.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
	
	@Bean
	 S3Client getS3Client() {
		System.out.println("S3Client Bean Creation Completed Successfully");
		return S3Client.builder().region(Region.AP_SOUTHEAST_2)
				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("", ""))).build();

	}

}
