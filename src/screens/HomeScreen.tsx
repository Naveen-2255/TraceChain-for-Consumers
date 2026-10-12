import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Alert,
} from 'react-native';
import { MaterialCommunityIcons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';
import { Product, PRODUCTS_DATABASE } from '../data/productData';

interface HomeScreenProps {
  user: { name: string; email: string };
  onScanClick: () => void;
  onProductSelect: (product: Product) => void;
  onLogout: () => void;
}

export const HomeScreen: React.FC<HomeScreenProps> = ({
  user,
  onScanClick,
  onProductSelect,
  onLogout,
}) => {
  const getInitials = (name: string) => {
    return name
      .split(' ')
      .map((n) => n[0])
      .slice(0, 2)
      .join('')
      .toUpperCase();
  };

  const handleAvatarClick = () => {
    Alert.alert(
      'Account Session',
      `Signed in as ${user.name} (${user.email})`,
      [
        { text: 'Cancel', style: 'cancel' },
        { text: 'Log Out', style: 'destructive', onPress: onLogout },
      ]
    );
  };

  return (
    <ScrollView
      style={styles.container}
      contentContainerStyle={styles.content}
      showsVerticalScrollIndicator={false}
    >
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity
          style={styles.userInfo}
          activeOpacity={0.8}
          onPress={handleAvatarClick}
        >
          <View style={styles.avatar}>
            <Text style={styles.avatarText}>{getInitials(user.name)}</Text>
          </View>
          <View style={{ marginLeft: 12 }}>
            <Text style={styles.greeting}>Good morning,</Text>
            <Text style={styles.userName}>{user.name}</Text>
          </View>
        </TouchableOpacity>

        <TouchableOpacity style={styles.bellButton} onPress={onLogout}>
          <MaterialCommunityIcons name="logout-variant" size={20} color={Colors.pitchBlack} />
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

      {/* Product Card 1: Amul Milk */}
      <TouchableOpacity
        style={styles.productCard}
        activeOpacity={0.8}
        onPress={() => onProductSelect(PRODUCTS_DATABASE['AM202409'])}
      >
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
      </TouchableOpacity>

      {/* Product Card 2: Modern Bread */}
      <TouchableOpacity
        style={styles.productCard}
        activeOpacity={0.8}
        onPress={() => onProductSelect(PRODUCTS_DATABASE['MDN202409'])}
      >
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
      </TouchableOpacity>

      {/* Expiring Soon Card: Honey */}
      <TouchableOpacity
        style={styles.expiryCard}
        activeOpacity={0.85}
        onPress={() => onProductSelect(PRODUCTS_DATABASE['HNY202408'])}
      >
        <View style={styles.expiryHeader}>
          <MaterialCommunityIcons name="clock-alert-outline" size={20} color={Colors.darkCoffee} />
          <Text style={styles.expiryTitle}>Expiring Soon</Text>
        </View>
        <Text style={styles.expiryDesc}>
          Organic Wildflower Honey expires in 3 days (28 Sep 2026). Tap to view batch test report.
        </Text>
      </TouchableOpacity>

      {/* About TraceChain Blockchain Trust Card */}
      <View style={styles.aboutCard}>
        <View style={styles.aboutHeaderRow}>
          <MaterialCommunityIcons name="shield-lock-outline" size={22} color={Colors.dustyOlive} />
          <Text style={styles.aboutTitle}>Zero-Trust Verification</Text>
        </View>
        <Text style={styles.aboutDesc}>
          Every verified product is backed by immutable cryptographic proof on Polygon Blockchain. Counterfeits and temperature deviations are flagged automatically.
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
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    alignItems: 'center',
    justifyContent: 'center',
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
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.04,
    shadowRadius: 8,
    elevation: 2,
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
    marginTop: 6,
    marginBottom: 16,
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
    lineHeight: 18,
  },
  aboutCard: {
    backgroundColor: '#FFFFFF',
    padding: 16,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
  },
  aboutHeaderRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 6,
  },
  aboutTitle: {
    fontSize: 15,
    fontWeight: '700',
    color: Colors.pitchBlack,
    marginLeft: 8,
  },
  aboutDesc: {
    fontSize: 12.5,
    color: Colors.textSecondary,
    lineHeight: 18,
  },
});
