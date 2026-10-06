import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
} from 'react-native';
import { MaterialCommunityIcons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';

interface HomeScreenProps {
  onScanClick: () => void;
}

export const HomeScreen: React.FC<HomeScreenProps> = ({ onScanClick }) => {
  return (
    <ScrollView
      style={styles.container}
      contentContainerStyle={styles.content}
      showsVerticalScrollIndicator={false}
    >
      {/* Header */}
      <View style={styles.header}>
        <View style={styles.userInfo}>
          <View style={styles.avatar}>
            <Text style={styles.avatarText}>NJ</Text>
          </View>
          <View style={{ marginLeft: 12 }}>
            <Text style={styles.greeting}>Good morning,</Text>
            <Text style={styles.userName}>Naveen Joseph</Text>
          </View>
        </View>

        <TouchableOpacity style={styles.bellButton}>
          <MaterialCommunityIcons name="bell-outline" size={24} color={Colors.pitchBlack} />
          <View style={styles.unreadDot} />
        </TouchableOpacity>
      </View>

      {/* Verify a Product Card */}
      <TouchableOpacity style={styles.verifyCard} activeOpacity={0.85} onPress={onScanClick}>
        <View style={styles.qrCircle}>
          <MaterialCommunityIcons name="qrcode-scan" size={28} color={Colors.dustyOlive} />
        </View>
        <View style={{ flex: 1, marginLeft: 16 }}>
          <Text style={styles.verifyTitle}>Verify a Product</Text>
          <Text style={styles.verifySubtitle}>
            Scan the QR code on your product to view its complete journey
          </Text>
        </View>
        <View style={styles.arrowCircle}>
          <MaterialCommunityIcons name="chevron-right" size={24} color={Colors.dustyOlive} />
        </View>
      </TouchableOpacity>

      {/* Recent Products Section */}
      <View style={styles.sectionRow}>
        <Text style={styles.sectionTitle}>Recent Products</Text>
        <TouchableOpacity>
          <Text style={styles.seeAll}>See All</Text>
        </TouchableOpacity>
      </View>

      {/* Product Card 1 */}
      <View style={styles.productCard}>
        <View style={styles.productThumb}>
          <MaterialCommunityIcons name="bottle-tonic-outline" size={30} color={Colors.dustyOlive} />
        </View>
        <View style={{ flex: 1, marginLeft: 14 }}>
          <Text style={styles.productName}>Amul Taaza Homogenised Toned Milk</Text>
          <Text style={styles.productMeta}>Verified 2 hrs ago • Batch #AM202409</Text>
        </View>
        <View style={styles.verifiedBadge}>
          <MaterialCommunityIcons name="check-circle" size={14} color={Colors.badgeVerifiedText} />
          <Text style={styles.verifiedText}>Verified</Text>
        </View>
      </View>

      {/* Product Card 2 */}
      <View style={styles.productCard}>
        <View style={styles.productThumb}>
          <MaterialCommunityIcons name="bread-slice-outline" size={30} color={Colors.dustyOlive} />
        </View>
        <View style={{ flex: 1, marginLeft: 14 }}>
          <Text style={styles.productName}>Modern 100% Whole Wheat Bread</Text>
          <Text style={styles.productMeta}>Verified Yesterday • Batch #MDN202409</Text>
        </View>
        <View style={styles.verifiedBadge}>
          <MaterialCommunityIcons name="check-circle" size={14} color={Colors.badgeVerifiedText} />
          <Text style={styles.verifiedText}>Verified</Text>
        </View>
      </View>

      {/* Expiring Soon Card */}
      <View style={styles.expiryCard}>
        <View style={styles.expiryHeader}>
          <MaterialCommunityIcons name="clock-alert-outline" size={20} color={Colors.darkCoffee} />
          <Text style={styles.expiryTitle}>Expiring Soon</Text>
        </View>
        <Text style={styles.expiryDesc}>
          Organic Wildflower Honey expires in 3 days (28 Sep 2026).
        </Text>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.background,
  },
  content: {
    padding: 20,
    paddingTop: 54,
    paddingBottom: 130,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 24,
  },
  userInfo: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  avatar: {
    width: 44,
    height: 44,
    borderRadius: 22,
    backgroundColor: Colors.dustyOlive,
    alignItems: 'center',
    justifyContent: 'center',
  },
  avatarText: {
    color: '#FFF',
    fontWeight: '700',
    fontSize: 16,
  },
  greeting: {
    fontSize: 13,
    color: Colors.textSecondary,
  },
  userName: {
    fontSize: 17,
    fontWeight: '700',
    color: Colors.pitchBlack,
  },
  bellButton: {
    width: 40,
    height: 40,
    borderRadius: 20,
    backgroundColor: '#FFF',
    alignItems: 'center',
    justifyContent: 'center',
  },
  unreadDot: {
    position: 'absolute',
    top: 8,
    right: 8,
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: '#C84B31',
  },
  verifyCard: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFF',
    padding: 18,
    borderRadius: 20,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    marginBottom: 26,
  },
  qrCircle: {
    width: 52,
    height: 52,
    borderRadius: 26,
    backgroundColor: Colors.badgeVerifiedBg,
    alignItems: 'center',
    justifyContent: 'center',
  },
  verifyTitle: {
    fontSize: 17,
    fontWeight: '700',
    color: Colors.pitchBlack,
  },
  verifySubtitle: {
    fontSize: 12.5,
    color: Colors.textSecondary,
    marginTop: 2,
  },
  arrowCircle: {
    width: 36,
    height: 36,
    borderRadius: 18,
    backgroundColor: Colors.floralWhite,
    alignItems: 'center',
    justifyContent: 'center',
  },
  sectionRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 14,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: '700',
    color: Colors.pitchBlack,
  },
  seeAll: {
    fontSize: 13,
    fontWeight: '600',
    color: Colors.dustyOlive,
  },
  productCard: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFF',
    padding: 14,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    marginBottom: 12,
  },
  productThumb: {
    width: 48,
    height: 48,
    borderRadius: 12,
    backgroundColor: Colors.floralWhite,
    alignItems: 'center',
    justifyContent: 'center',
  },
  productName: {
    fontSize: 14,
    fontWeight: '600',
    color: Colors.pitchBlack,
  },
  productMeta: {
    fontSize: 12,
    color: Colors.textSecondary,
    marginTop: 3,
  },
  verifiedBadge: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.badgeVerifiedBg,
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 12,
  },
  verifiedText: {
    fontSize: 11,
    fontWeight: '600',
    color: Colors.badgeVerifiedText,
    marginLeft: 3,
  },
  expiryCard: {
    backgroundColor: Colors.badgeWarningBg,
    padding: 16,
    borderRadius: 16,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    marginTop: 8,
  },
  expiryHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 6,
  },
  expiryTitle: {
    fontSize: 14,
    fontWeight: '700',
    color: Colors.darkCoffee,
    marginLeft: 6,
  },
  expiryDesc: {
    fontSize: 13,
    color: Colors.darkCoffee,
  },
});
