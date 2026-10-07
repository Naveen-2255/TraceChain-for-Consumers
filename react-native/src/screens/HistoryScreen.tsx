import React from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import { MaterialCommunityIcons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';
import { Product, PRODUCTS_DATABASE } from '../data/productData';

interface HistoryScreenProps {
  onProductSelect: (product: Product) => void;
}

const historyList = [
  {
    ...PRODUCTS_DATABASE['AM202409'],
    verifiedAgo: '2 hours ago',
  },
  {
    ...PRODUCTS_DATABASE['MDN202409'],
    verifiedAgo: 'Yesterday',
  },
  {
    ...PRODUCTS_DATABASE['HNY202408'],
    verifiedAgo: '3 days ago',
  },
];

export const HistoryScreen: React.FC<HistoryScreenProps> = ({ onProductSelect }) => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Verification History</Text>
      <Text style={styles.subtitle}>Cryptographically sealed supply chain records</Text>

      <FlatList
        data={historyList}
        keyExtractor={(item) => item.id}
        contentContainerStyle={{ paddingBottom: 120 }}
        showsVerticalScrollIndicator={false}
        renderItem={({ item }) => (
          <TouchableOpacity
            style={styles.card}
            activeOpacity={0.8}
            onPress={() => onProductSelect(item)}
          >
            <View style={styles.iconCircle}>
              <MaterialCommunityIcons
                name={item.imageIcon as any}
                size={26}
                color={Colors.dustyOlive}
              />
            </View>
            <View style={{ flex: 1, marginLeft: 14 }}>
              <Text style={styles.itemName}>{item.name}</Text>
              <Text style={styles.itemMeta}>Batch #{item.batchNumber} • {item.verifiedAgo}</Text>
            </View>
            <View style={styles.verifiedBadge}>
              <MaterialCommunityIcons name="shield-check" size={14} color={Colors.badgeVerifiedText} />
              <Text style={styles.badgeText}>Verified</Text>
            </View>
          </TouchableOpacity>
        )}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.background,
    padding: 20,
    paddingTop: 54,
  },
  title: {
    fontSize: 22,
    fontWeight: '800',
    color: Colors.pitchBlack,
  },
  subtitle: {
    fontSize: 13.5,
    color: Colors.textSecondary,
    marginTop: 4,
    marginBottom: 20,
  },
  card: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFF',
    padding: 14,
    borderRadius: 18,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    marginBottom: 12,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.04,
    shadowRadius: 6,
    elevation: 2,
  },
  iconCircle: {
    width: 48,
    height: 48,
    borderRadius: 14,
    backgroundColor: Colors.floralWhite,
    alignItems: 'center',
    justifyContent: 'center',
  },
  itemName: {
    fontSize: 14,
    fontWeight: '600',
    color: Colors.pitchBlack,
  },
  itemMeta: {
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
  badgeText: {
    fontSize: 11,
    fontWeight: '700',
    color: Colors.badgeVerifiedText,
    marginLeft: 3,
  },
});
