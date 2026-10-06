import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { MaterialCommunityIcons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';

export const ScanScreen: React.FC = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Scan QR / Barcode</Text>
      <Text style={styles.subtitle}>Align camera with product QR code</Text>

      <View style={styles.viewfinder}>
        <MaterialCommunityIcons name="qrcode-scan" size={140} color={Colors.dustyOlive} />
        <View style={styles.scanLine} />
      </View>

      <TouchableOpacity style={styles.demoButton} activeOpacity={0.8}>
        <Text style={styles.demoButtonText}>Simulate Product Scan</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.background,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 24,
    paddingBottom: 110,
  },
  title: {
    fontSize: 22,
    fontWeight: '800',
    color: Colors.pitchBlack,
  },
  subtitle: {
    fontSize: 14,
    color: Colors.textSecondary,
    marginTop: 6,
    marginBottom: 36,
  },
  viewfinder: {
    width: 240,
    height: 240,
    borderRadius: 24,
    borderWidth: 2,
    borderColor: Colors.dustyOlive,
    backgroundColor: '#FFF',
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 36,
  },
  scanLine: {
    position: 'absolute',
    width: '80%',
    height: 2,
    backgroundColor: '#C84B31',
  },
  demoButton: {
    backgroundColor: Colors.dustyOlive,
    paddingHorizontal: 24,
    paddingVertical: 14,
    borderRadius: 14,
  },
  demoButtonText: {
    color: '#FFF',
    fontWeight: '700',
    fontSize: 15,
  },
});
