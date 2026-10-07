import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  Dimensions,
  Platform,
} from 'react-native';
import { CameraView, useCameraPermissions } from 'expo-camera';
import { MaterialCommunityIcons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';
import { Product, PRODUCTS_DATABASE } from '../data/productData';

const { width: SCREEN_WIDTH } = Dimensions.get('window');

interface ScanScreenProps {
  onProductScanned: (product: Product) => void;
  onBack: () => void;
}

export const ScanScreen: React.FC<ScanScreenProps> = ({
  onProductScanned,
  onBack,
}) => {
  const [permission, requestPermission] = useCameraPermissions();
  const [torchOn, setTorchOn] = useState(false);
  const [facing, setFacing] = useState<'back' | 'front'>('back');
  const [scanned, setScanned] = useState(false);

  const handleBarcodeScanned = ({ data }: { type: string; data: string }) => {
    if (scanned) return;
    setScanned(true);

    // Look up barcode in database or fallback to default
    const matchedProduct =
      PRODUCTS_DATABASE[data] ||
      Object.values(PRODUCTS_DATABASE).find(
        (p) => data.toLowerCase().includes(p.barcode.toLowerCase()) || p.barcode.includes(data)
      ) ||
      PRODUCTS_DATABASE['AM202409'];

    onProductScanned(matchedProduct);

    setTimeout(() => {
      setScanned(false);
    }, 2500);
  };

  const handleSimulateScan = (code: string) => {
    const product = PRODUCTS_DATABASE[code] || PRODUCTS_DATABASE['AM202409'];
    onProductScanned(product);
  };

  // If permission state is still loading
  if (!permission) {
    return (
      <View style={styles.centerContainer}>
        <Text style={styles.permissionText}>Initializing Camera...</Text>
      </View>
    );
  }

  // If permission is not granted yet
  if (!permission.granted) {
    return (
      <View style={styles.permissionContainer}>
        <View style={styles.permissionIconCircle}>
          <MaterialCommunityIcons name="camera-off-outline" size={48} color={Colors.dustyOlive} />
        </View>
        <Text style={styles.permissionTitle}>Camera Access Required</Text>
        <Text style={styles.permissionDesc}>
          TraceChain needs camera permission to scan product QR codes and verify supply chain authenticity on the blockchain.
        </Text>

        <TouchableOpacity style={styles.grantButton} activeOpacity={0.85} onPress={requestPermission}>
          <MaterialCommunityIcons name="camera-outline" size={20} color="#FFFFFF" />
          <Text style={styles.grantButtonText}>Grant Camera Permission</Text>
        </TouchableOpacity>

        {/* Fallback instant demo testing */}
        <View style={styles.demoSection}>
          <Text style={styles.demoSectionTitle}>Or Test Without Camera:</Text>
          <View style={styles.demoRow}>
            <TouchableOpacity
              style={styles.demoTag}
              onPress={() => handleSimulateScan('AM202409')}
            >
              <Text style={styles.demoTagText}>Milk (AM202409)</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.demoTag}
              onPress={() => handleSimulateScan('MDN202409')}
            >
              <Text style={styles.demoTagText}>Bread (MDN202409)</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.demoTag}
              onPress={() => handleSimulateScan('HNY202408')}
            >
              <Text style={styles.demoTagText}>Honey (HNY202408)</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    );
  }

  // Camera is granted and live
  return (
    <View style={styles.container}>
      <CameraView
        style={StyleSheet.absoluteFillObject}
        facing={facing}
        enableTorch={torchOn}
        barcodeScannerSettings={{
          barcodeTypes: ['qr', 'ean13', 'ean8', 'upc_a', 'code128', 'code39'],
        }}
        onBarcodeScanned={scanned ? undefined : handleBarcodeScanned}
      >
        {/* Darkened overlay with cutout */}
        <View style={styles.overlay}>
          {/* Top Bar Controls */}
          <View style={styles.topControls}>
            <TouchableOpacity style={styles.controlButton} onPress={onBack}>
              <MaterialCommunityIcons name="arrow-left" size={24} color="#FFFFFF" />
            </TouchableOpacity>

            <Text style={styles.scannerHeaderTitle}>Scan Product QR</Text>

            <View style={styles.topRightControls}>
              <TouchableOpacity
                style={[styles.controlButton, torchOn && styles.controlButtonActive]}
                onPress={() => setTorchOn(!torchOn)}
              >
                <MaterialCommunityIcons
                  name={torchOn ? 'flashlight' : 'flashlight-off'}
                  size={22}
                  color="#FFFFFF"
                />
              </TouchableOpacity>

              <TouchableOpacity
                style={[styles.controlButton, { marginLeft: 10 }]}
                onPress={() => setFacing(facing === 'back' ? 'front' : 'back')}
              >
                <MaterialCommunityIcons name="camera-flip-outline" size={22} color="#FFFFFF" />
              </TouchableOpacity>
            </View>
          </View>

          {/* Viewfinder Target Frame */}
          <View style={styles.viewfinderContainer}>
            <View style={styles.viewfinderBox}>
              {/* 4 Corner Markers */}
              <View style={[styles.corner, styles.cornerTL]} />
              <View style={[styles.corner, styles.cornerTR]} />
              <View style={[styles.corner, styles.cornerBL]} />
              <View style={[styles.corner, styles.cornerBR]} />

              {/* Animated laser line */}
              <View style={styles.laserLine} />
            </View>
            <Text style={styles.instructionText}>
              Align product QR code or barcode within frame
            </Text>
          </View>

          {/* Quick Demo Test Buttons Bar at Bottom */}
          <View style={styles.bottomBar}>
            <Text style={styles.quickTestLabel}>Quick Test Barcodes:</Text>
            <View style={styles.quickTestRow}>
              <TouchableOpacity
                style={styles.quickTestButton}
                onPress={() => handleSimulateScan('AM202409')}
              >
                <Text style={styles.quickTestButtonText}>Amul Milk</Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={styles.quickTestButton}
                onPress={() => handleSimulateScan('MDN202409')}
              >
                <Text style={styles.quickTestButtonText}>Modern Bread</Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={styles.quickTestButton}
                onPress={() => handleSimulateScan('HNY202408')}
              >
                <Text style={styles.quickTestButtonText}>Wild Honey</Text>
              </TouchableOpacity>
            </View>
          </View>
        </View>
      </CameraView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
  },
  centerContainer: {
    flex: 1,
    backgroundColor: Colors.background,
    alignItems: 'center',
    justifyContent: 'center',
  },
  permissionContainer: {
    flex: 1,
    backgroundColor: Colors.background,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 28,
  },
  permissionIconCircle: {
    width: 84,
    height: 84,
    borderRadius: 42,
    backgroundColor: '#FFFFFF',
    borderWidth: 1.5,
    borderColor: Colors.cardBorder,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 20,
  },
  permissionTitle: {
    fontSize: 20,
    fontWeight: '700',
    color: Colors.pitchBlack,
    textAlign: 'center',
  },
  permissionDesc: {
    fontSize: 14,
    color: Colors.textSecondary,
    textAlign: 'center',
    marginTop: 8,
    lineHeight: 20,
    marginBottom: 28,
  },
  permissionText: {
    fontSize: 16,
    color: Colors.pitchBlack,
    fontWeight: '600',
  },
  grantButton: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.dustyOlive,
    paddingHorizontal: 24,
    paddingVertical: 14,
    borderRadius: 16,
  },
  grantButtonText: {
    color: '#FFF',
    fontSize: 15,
    fontWeight: '700',
    marginLeft: 8,
  },
  demoSection: {
    marginTop: 36,
    alignItems: 'center',
  },
  demoSectionTitle: {
    fontSize: 13,
    color: Colors.textSecondary,
    marginBottom: 10,
  },
  demoRow: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
  },
  demoTag: {
    backgroundColor: '#FFFFFF',
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    paddingHorizontal: 12,
    paddingVertical: 8,
    borderRadius: 12,
    margin: 4,
  },
  demoTagText: {
    fontSize: 12,
    fontWeight: '600',
    color: Colors.dustyOlive,
  },
  overlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.45)',
    justifyContent: 'space-between',
    paddingTop: Platform.OS === 'ios' ? 54 : 36,
    paddingBottom: 30,
  },
  topControls: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
  },
  scannerHeaderTitle: {
    color: '#FFFFFF',
    fontSize: 17,
    fontWeight: '700',
  },
  topRightControls: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  controlButton: {
    width: 42,
    height: 42,
    borderRadius: 21,
    backgroundColor: 'rgba(0,0,0,0.5)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  controlButtonActive: {
    backgroundColor: Colors.dustyOlive,
  },
  viewfinderContainer: {
    alignItems: 'center',
    justifyContent: 'center',
  },
  viewfinderBox: {
    width: SCREEN_WIDTH * 0.72,
    height: SCREEN_WIDTH * 0.72,
    borderRadius: 24,
    position: 'relative',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'transparent',
  },
  corner: {
    position: 'absolute',
    width: 32,
    height: 32,
    borderColor: Colors.activeCircle,
  },
  cornerTL: {
    top: 0,
    left: 0,
    borderTopWidth: 4,
    borderLeftWidth: 4,
    borderTopLeftRadius: 16,
  },
  cornerTR: {
    top: 0,
    right: 0,
    borderTopWidth: 4,
    borderRightWidth: 4,
    borderTopRightRadius: 16,
  },
  cornerBL: {
    bottom: 0,
    left: 0,
    borderBottomWidth: 4,
    borderLeftWidth: 4,
    borderBottomLeftRadius: 16,
  },
  cornerBR: {
    bottom: 0,
    right: 0,
    borderBottomWidth: 4,
    borderRightWidth: 4,
    borderBottomRightRadius: 16,
  },
  laserLine: {
    width: '85%',
    height: 2.5,
    backgroundColor: Colors.activeCircle,
    shadowColor: Colors.activeCircle,
    shadowOffset: { width: 0, height: 0 },
    shadowOpacity: 0.9,
    shadowRadius: 6,
    elevation: 4,
  },
  instructionText: {
    color: '#FFFFFF',
    fontSize: 13.5,
    marginTop: 20,
    textAlign: 'center',
    fontWeight: '500',
    backgroundColor: 'rgba(0,0,0,0.55)',
    paddingHorizontal: 16,
    paddingVertical: 8,
    borderRadius: 14,
    overflow: 'hidden',
  },
  bottomBar: {
    paddingHorizontal: 20,
    alignItems: 'center',
  },
  quickTestLabel: {
    color: 'rgba(255,255,255,0.8)',
    fontSize: 12,
    marginBottom: 8,
  },
  quickTestRow: {
    flexDirection: 'row',
    justifyContent: 'center',
  },
  quickTestButton: {
    backgroundColor: 'rgba(255,255,255,0.2)',
    borderWidth: 1,
    borderColor: 'rgba(255,255,255,0.3)',
    paddingHorizontal: 14,
    paddingVertical: 8,
    borderRadius: 14,
    marginHorizontal: 4,
  },
  quickTestButtonText: {
    color: '#FFFFFF',
    fontSize: 12.5,
    fontWeight: '600',
  },
});
