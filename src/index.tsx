/* eslint-disable */
import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'rn-aws-s3' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const RnAwsS3 = NativeModules.RnAwsS3
  ? NativeModules.RnAwsS3
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

interface PutObjectInput {
  filePath: string,
  bucketName: string,
  fileKey: string,
  region: string,
  accessKey: string,
  secretKey: string
}

export function putObject({ filePath, bucketName, fileKey, region, accessKey, secretKey }: PutObjectInput): Promise<boolean> {
  return RnAwsS3.putObject(filePath, bucketName, fileKey, region, accessKey, secretKey);
}
