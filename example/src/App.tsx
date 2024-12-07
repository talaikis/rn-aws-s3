/* eslint-disable */
import { useEffect } from 'react';
import { View, StyleSheet } from 'react-native';
import { putObject } from 'rn-aws-s3';

export default function App() {
  useEffect(() => {
    putObject({ filePath: 'test', bucketName: 'test', fileKey: 'test', region: 'test', accessKey: 'test', secretKey: 'test' })
  }, []);

  return (
    <View style={styles.container}>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
