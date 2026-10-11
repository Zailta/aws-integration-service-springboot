package com.aws.resourceUploadServcie.Controllers;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aws.resourceUploadServcie.Service.S3Service;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

@RestController
@RequestMapping(value = "/aws-ingest-service")
public class ResourceUploadController {
	
	final S3Client client;
	final S3Service s3Service;

	ResourceUploadController(S3Client client, S3Service s3Service) {
		this.client = client;
		this.s3Service = s3Service;
	}
	
	@PostMapping(value = "/aws-doc-upload")
	public ResponseEntity<String> importDocumentToS3( @RequestParam MultipartFile file) throws S3Exception, AwsServiceException, SdkClientException, IOException{
		s3Service.importDocumentToS3(file);		
		return ResponseEntity.ok("File has been uploaded Successfully!");
	}
	
	@GetMapping
	public String healthCheckUp() {
		return "AWS Ingest Service is running";
		
	}
	
	@GetMapping(value = "aws-doc-retreive")
	public ResponseEntity<byte[]> getS3documentForDownload(@RequestParam String fileName) {
		byte[] s3documentForDownloadDoc = s3Service.getS3documentForDownload(fileName);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName="+fileName)
				.body(s3documentForDownloadDoc);
		
	}
	

}
