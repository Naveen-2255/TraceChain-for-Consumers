import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Share,
  Alert,
} from 'react-native';
import { MaterialCommunityIcons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';
import { Product } from '../data/productData';

interface ProductDetailsScreenProps {
  product: Product;
  onBack: () => void;
}

export const ProductDetailsScreen: React.FC<ProductDetailsScreenProps> = ({
  product,
  onBack,
}) => {
  const [copiedHash, setCopiedHash] = useState(false);

  const handleShare = async () => {
    try {
      await Share.share({
        message: `Verified Authentic on TraceChain: ${product.name} (Batch #${product.batchNumber}). Blockchain Hash: ${product.blockchainHash}`,
      });
    } catch (e) {
      // Ignore
    }
  };

  const handleCopyHash = () => {
    setCopiedHash(true);
    Alert.alert('Hash Copied', `Blockchain Transaction Hash:\n${product.blockchainHash}`);
    setTimeout(() => setCopiedHash(false), 2000);
  };

  return (
    <View style={styles.container}>
      {/* Top App Bar */}
      <View style={styles.topBar}>
        <TouchableOpacity style={styles.iconButton} onPress={onBack}>
          <MaterialCommunityIcons name="arrow-left" size={24} color={Colors.pitchBlack} />
        </TouchableOpacity>
        <Text style={styles.topBarTitle}>Product Verification</Text>
        <TouchableOpacity style={styles.iconButton} onPress={handleShare}>
          <MaterialCommunityIcons name="share-variant-outline" size={22} color={Colors.pitchBlack} />
        </TouchableOpacity>
      </View>

      <ScrollView
        contentContainerStyle={styles.scrollContent}
        showsVerticalScrollIndicator={false}
      >
        {/* Verification Status Banner */}
        <View style={styles.verifiedBanner}>
          <View style={styles.verifiedIconCircle}>
            <MaterialCommunityIcons name="shield-check" size={28} color={Colors.badgeVerifiedText} />
          </View>
          <View style={{ flex: 1, marginLeft: 12 }}>
            <View style={styles.verifiedTagRow}>
              <Text style={styles.verifiedTagText}>100% AUTHENTIC & VERIFIED</Text>
            </View>
            <Text style={styles.verifiedSubtitle}>
              Cryptographically validated on Polygon Blockchain
            </Text>
          </View>
        </View>

        {/* Product Identity Card */}
        <View style={styles.card}>
          <View style={styles.productHeaderRow}>
            <View style={styles.productIconCircle}>
              <MaterialCommunityIcons
                name={product.imageIcon as any}
                size={34}
                color={Colors.dustyOlive}
              />
            </View>
            <View style={{ flex: 1, marginLeft: 14 }}>
              <Text style={styles.brandName}>{product.brand}</Text>
              <Text style={styles.productName}>{product.name}</Text>
              <Text style={styles.categoryBadge}>{product.category}</Text>
            </View>
          </View>

          <View style={styles.divider} />

          {/* Key Specs Grid */}
          <View style={styles.specsGrid}>
            <View style={styles.specItem}>
              <Text style={styles.specLabel}>Batch Number</Text>
              <Text style={styles.specValue}>{product.batchNumber}</Text>
            </View>
            <View style={styles.specItem}>
              <Text style={styles.specLabel}>Purity Score</Text>
              <Text style={[styles.specValue, { color: Colors.badgeVerifiedText }]}>
                {product.purityScorePercent}%
              </Text>
            </View>
            <View style={styles.specItem}>
              <Text style={styles.specLabel}>Mfg Date</Text>
              <Text style={styles.specValue}>{product.manufacturingDate}</Text>
            </View>
            <View style={styles.specItem}>
              <Text style={styles.specLabel}>Expiry Date</Text>
              <Text style={[styles.specValue, { color: Colors.darkCoffee }]}>
                {product.expiryDate}
              </Text>
            </View>
          </View>
        </View>

        {/* Lab Certification & Purity Card */}
        <View style={styles.card}>
          <View style={styles.sectionHeaderRow}>
            <MaterialCommunityIcons name="certificate-outline" size={22} color={Colors.dustyOlive} />
            <Text style={styles.sectionTitle}>Lab Safety & Quality Test</Text>
          </View>
          <Text style={styles.labReportDesc}>{product.labTestReport}</Text>
          <View style={styles.labMetricsRow}>
            <View style={styles.metricPill}>
              <MaterialCommunityIcons name="check" size={16} color={Colors.badgeVerifiedText} />
              <Text style={styles.metricText}>Zero Preservatives</Text>
            </View>
            <View style={styles.metricPill}>
              <MaterialCommunityIcons name="check" size={16} color={Colors.badgeVerifiedText} />
              <Text style={styles.metricText}>Cold Chain Maintained</Text>
            </View>
          </View>
        </View>

        {/* Blockchain Supply Chain Journey Timeline */}
        <View style={styles.card}>
          <View style={styles.sectionHeaderRow}>
            <MaterialCommunityIcons name="transit-connection-variant" size={22} color={Colors.dustyOlive} />
            <Text style={styles.sectionTitle}>Blockchain Journey Timeline</Text>
          </View>
          <Text style={styles.journeyDesc}>
            Each milestone is sealed with an immutable cryptographic block hash.
          </Text>

          <View style={styles.timelineContainer}>
            {product.journey.map((stage, index) => {
              const isLast = index === product.journey.length - 1;

              return (
                <View key={stage.id} style={styles.timelineRow}>
                  {/* Left Column: Icon node & connector line */}
                  <View style={styles.nodeColumn}>
                    <View style={styles.nodeCircle}>
                      <MaterialCommunityIcons
                        name={stage.icon as any}
                        size={18}
                        color={Colors.dustyOlive}
                      />
                    </View>
                    {!isLast && <View style={styles.nodeConnector} />}
                  </View>

                  {/* Right Column: Stage info */}
                  <View style={styles.stageContent}>
                    <View style={styles.stageHeaderRow}>
                      <Text style={styles.stageTitle}>{stage.stageName}</Text>
                      <View style={styles.doneBadge}>
                        <Text style={styles.doneBadgeText}>Verified</Text>
                      </View>
                    </View>

                    <Text style={styles.stageLocation}>
                      {stage.location} • {stage.handler}
                    </Text>
                    <Text style={styles.stageTime}>{stage.timestamp}</Text>
                    <Text style={styles.stageDetails}>{stage.details}</Text>
                  </View>
                </View>
              );
            })}
          </View>
        </View>

        {/* Blockchain Smart Contract Hash Card */}
        <View style={styles.card}>
          <View style={styles.sectionHeaderRow}>
            <MaterialCommunityIcons name="cube-outline" size={22} color={Colors.dustyOlive} />
            <Text style={styles.sectionTitle}>On-Chain Certificate</Text>
          </View>

          <View style={styles.hashBox}>
            <View style={{ flex: 1 }}>
              <Text style={styles.hashLabel}>Transaction Hash</Text>
              <Text style={styles.hashValue} numberOfLines={1} ellipsizeMode="middle">
                {product.blockchainHash}
              </Text>
              <Text style={styles.blockMeta}>Block #{product.blockNumber} • Polygon Mainnet</Text>
            </View>

            <TouchableOpacity style={styles.copyButton} onPress={handleCopyHash}>
              <MaterialCommunityIcons
                name={copiedHash ? 'check' : 'content-copy'}
                size={18}
                color={Colors.dustyOlive}
              />
            </TouchableOpacity>
          </View>

          <View style={styles.originRow}>
            <MaterialCommunityIcons name="leaf" size={18} color={Colors.dustyOlive} />
            <Text style={styles.originText}>
              Carbon footprint: {product.carbonFootprintKg} kg CO₂e (Eco-certified)
            </Text>
          </View>
        </View>

        {/* Report counterfeit action */}
        <TouchableOpacity style={styles.reportButton} activeOpacity={0.85}>
          <MaterialCommunityIcons name="alert-octagon-outline" size={18} color="#C84B31" />
          <Text style={styles.reportButtonText}>Report Counterfeit or Tamper</Text>
        </TouchableOpacity>
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.background,
  },
  topBar: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 18,
    paddingTop: 54,
    paddingBottom: 12,
    backgroundColor: Colors.background,
  },
  iconButton: {
    width: 40,
    height: 40,
    borderRadius: 20,
    backgroundColor: '#FFFFFF',
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    alignItems: 'center',
    justifyContent: 'center',
  },
  topBarTitle: {
    fontSize: 18,
    fontWeight: '700',
    color: Colors.pitchBlack,
  },
  scrollContent: {
    padding: 18,
    paddingBottom: 40,
  },
  verifiedBanner: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.badgeVerifiedBg,
    borderRadius: 18,
    padding: 16,
    marginBottom: 16,
    borderWidth: 1,
    borderColor: 'rgba(69,85,61,0.15)',
  },
  verifiedIconCircle: {
    width: 46,
    height: 46,
    borderRadius: 23,
    backgroundColor: '#FFFFFF',
    alignItems: 'center',
    justifyContent: 'center',
  },
  verifiedTagRow: {
    alignSelf: 'flex-start',
  },
  verifiedTagText: {
    fontSize: 12.5,
    fontWeight: '800',
    color: Colors.badgeVerifiedText,
    letterSpacing: 0.5,
  },
  verifiedSubtitle: {
    fontSize: 12,
    color: Colors.badgeVerifiedText,
    marginTop: 2,
    opacity: 0.9,
  },
  card: {
    backgroundColor: '#FFFFFF',
    borderRadius: 20,
    padding: 18,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    marginBottom: 16,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.04,
    shadowRadius: 8,
    elevation: 2,
  },
  productHeaderRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  productIconCircle: {
    width: 60,
    height: 60,
    borderRadius: 16,
    backgroundColor: Colors.floralWhite,
    alignItems: 'center',
    justifyContent: 'center',
  },
  brandName: {
    fontSize: 12.5,
    fontWeight: '600',
    color: Colors.dustyOlive,
    textTransform: 'uppercase',
  },
  productName: {
    fontSize: 16,
    fontWeight: '700',
    color: Colors.pitchBlack,
    marginTop: 2,
  },
  categoryBadge: {
    fontSize: 11.5,
    color: Colors.textSecondary,
    marginTop: 3,
  },
  divider: {
    height: 1,
    backgroundColor: Colors.cardBorder,
    marginVertical: 14,
  },
  specsGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  specItem: {
    width: '50%',
    marginVertical: 6,
  },
  specLabel: {
    fontSize: 11.5,
    color: Colors.textSecondary,
    fontWeight: '500',
  },
  specValue: {
    fontSize: 13.5,
    fontWeight: '700',
    color: Colors.pitchBlack,
    marginTop: 2,
  },
  sectionHeaderRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 8,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: '700',
    color: Colors.pitchBlack,
    marginLeft: 8,
  },
  labReportDesc: {
    fontSize: 13,
    color: Colors.textSecondary,
    lineHeight: 18,
  },
  labMetricsRow: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    marginTop: 12,
  },
  metricPill: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.floralWhite,
    paddingHorizontal: 10,
    paddingVertical: 6,
    borderRadius: 12,
    marginRight: 8,
    marginBottom: 6,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
  },
  metricText: {
    fontSize: 12,
    fontWeight: '600',
    color: Colors.pitchBlack,
    marginLeft: 4,
  },
  journeyDesc: {
    fontSize: 12.5,
    color: Colors.textSecondary,
    marginBottom: 16,
  },
  timelineContainer: {
    paddingLeft: 4,
  },
  timelineRow: {
    flexDirection: 'row',
  },
  nodeColumn: {
    alignItems: 'center',
    width: 32,
  },
  nodeCircle: {
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: Colors.badgeVerifiedBg,
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 1.5,
    borderColor: Colors.dustyOlive,
  },
  nodeConnector: {
    width: 2,
    flex: 1,
    minHeight: 38,
    backgroundColor: Colors.cardBorder,
    marginVertical: 4,
  },
  stageContent: {
    flex: 1,
    marginLeft: 12,
    paddingBottom: 20,
  },
  stageHeaderRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  stageTitle: {
    fontSize: 14,
    fontWeight: '700',
    color: Colors.pitchBlack,
  },
  doneBadge: {
    backgroundColor: Colors.badgeVerifiedBg,
    paddingHorizontal: 8,
    paddingVertical: 2,
    borderRadius: 8,
  },
  doneBadgeText: {
    fontSize: 10.5,
    fontWeight: '700',
    color: Colors.badgeVerifiedText,
  },
  stageLocation: {
    fontSize: 12,
    color: Colors.dustyOlive,
    marginTop: 2,
    fontWeight: '500',
  },
  stageTime: {
    fontSize: 11,
    color: Colors.textSecondary,
    marginTop: 1,
  },
  stageDetails: {
    fontSize: 12,
    color: Colors.textSecondary,
    marginTop: 4,
    lineHeight: 16,
  },
  hashBox: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.floralWhite,
    padding: 12,
    borderRadius: 14,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    marginTop: 6,
  },
  hashLabel: {
    fontSize: 11,
    color: Colors.textSecondary,
    fontWeight: '500',
  },
  hashValue: {
    fontSize: 12.5,
    fontWeight: '700',
    color: Colors.dustyOlive,
    marginTop: 1,
    fontFamily: Platform.OS === 'ios' ? 'Courier' : 'monospace',
  },
  blockMeta: {
    fontSize: 11,
    color: Colors.textSecondary,
    marginTop: 3,
  },
  copyButton: {
    width: 36,
    height: 36,
    borderRadius: 18,
    backgroundColor: '#FFFFFF',
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    alignItems: 'center',
    justifyContent: 'center',
    marginLeft: 10,
  },
  originRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 12,
  },
  originText: {
    fontSize: 12,
    color: Colors.textSecondary,
    marginLeft: 6,
  },
  reportButton: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#FFFFFF',
    paddingVertical: 14,
    borderRadius: 16,
    borderWidth: 1,
    borderColor: 'rgba(200,75,49,0.3)',
    marginTop: 6,
    marginBottom: 20,
  },
  reportButtonText: {
    fontSize: 13.5,
    fontWeight: '700',
    color: '#C84B31',
    marginLeft: 6,
  },
});
