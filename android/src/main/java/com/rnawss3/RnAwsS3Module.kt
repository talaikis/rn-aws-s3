package com.rnawss3

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import kotlinx.coroutines.runBlocking
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient

class RnAwsS3Module(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun putObject(
      filePath: String,
      bucketName: String,
      fileKey: String,
      region: String,
      accessKey: String,
      secretKey: String,
      promise: Promise
  ) {
      try {
          if (filePath.isBlank() || bucketName.isBlank() || fileKey.isBlank() ||
              region.isBlank() || accessKey.isBlank() || secretKey.isBlank()
          ) {
              promise.reject(
                  "INVALID_ARGUMENTS",
                  "One or more arguments are missing or empty."
              )
              return
          }

          val file = File(filePath)
          if (!file.exists() || !file.isFile) {
              promise.reject(
                  "FILE_NOT_FOUND",
                  "The file at path $filePath does not exist or is not a file."
              )
              return
          }

          val awsCredentials = AwsBasicCredentials.create(accessKey, secretKey)
          val credentialsProvider = StaticCredentialsProvider.create(awsCredentials)
          val awsRegion = Region.of(region)
          val s3Client: S3Client = S3Client.builder()
              .region(awsRegion)
              .credentialsProvider(credentialsProvider)
              .httpClient(UrlConnectionHttpClient.create())
              .build()

          val putObjectRequest = PutObjectRequest.builder()
              .bucket(bucketName)
              .key(fileKey)
              .build()

          val uploadResult = runBlocking {
              try {
                  s3Client.putObject(putObjectRequest, file.toPath())
                  true
              } catch (e: Exception) {
                  e.printStackTrace()
                  false
              } finally {
                  s3Client.close()
              }
          }

          promise.resolve(uploadResult)
      } catch (e: Exception) {
          e.printStackTrace()
          promise.reject("ERROR", e.message, e)
      }
  }

  companion object {
    const val NAME = "RnAwsS3"
  }
}
