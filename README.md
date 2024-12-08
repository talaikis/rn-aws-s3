# rn-aws-s3

React Native AWS S3 client (some utils)

## Installation

```sh
yarn add rn-aws-s3
```

Build dependency:

```
implementation(platform("software.amazon.awssdk:bom:2.27.21"))
```

Build fixes, if required:

```
packagingOptions {
  exclude "META-INF/DEPENDENCIES"
  exclude "META-INF/LICENSE"
  exclude "META-INF/LICENSE.txt"
  exclude "META-INF/license.txt"
  exclude "META-INF/NOTICE"
  exclude "META-INF/NOTICE.txt"
  exclude "META-INF/notice.txt"
  exclude "META-INF/ASL2.0"
  exclude "META-INF/INDEX.LIST"
  pickFirst "META-INF/io.netty.versions.properties"
}
```

Proguard rules

```
-keep class org.apache.commons.logging.**               { *; }
-keep class com.amazonaws.services.sqs.QueueUrlHandler  { *; }
-keep class com.amazonaws.javax.xml.transform.sax.*     { public *; }
-keep class com.amazonaws.javax.xml.stream.**           { *; }
-keep class com.amazonaws.services.**.model.*Exception* { *; }
-keep class org.codehaus.**                             { *; }
-keepattributes Signature,*Annotation*
```

## Usage

```js
import { putObject } from 'rn-aws-s3';
await putObject({ filePath, bucketName, fileKey, region, accessKey, secretKey })
```


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
