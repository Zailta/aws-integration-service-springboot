package com.aws.resourceUploadServcie.Service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3Service {
	
	final S3Client client;

	S3Service(S3Client client) {
		this.client = client;
	}
	
	public void importDocumentToS3(MultipartFile file) throws S3Exception, AwsServiceException, SdkClientException, IOException {
		PutObjectResponse putObject = client.
		putObject(PutObjectRequest.builder()
				.bucket("gdms-mock")
				.key("document-metadata/" + file.getOriginalFilename())
				.build(), 
				RequestBody.fromBytes(file.getBytes()));
		System.out.println("putObject: "+putObject);
		
	}
	
	public byte[] getS3documentForDownload(String fileName) {
		ResponseBytes<GetObjectResponse> objectAsBytes = client.getObjectAsBytes(
				GetObjectRequest
				.builder()
				.bucket("gdms-mock")
				.key("document-metadata/"+fileName)
				.build());
		
		return objectAsBytes.asByteArray();
	}

}
