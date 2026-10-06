import React from 'react';
import { View, Text, StyleSheet, FlatList } from 'react-native';
import { MaterialCommunityIcons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';

const historyItems = [
  {
    id: '1',
    name: 'Amul Taaza Milk',
    batch: '#AM202409',
    time: '2 hours ago',
    icon: 'bottle-tonic-outline',
  },
  {
    id: '2',
    name: 'Modern Whole Wheat Bread',
    batch: '#MDN202409',
    time: 'Yesterday',
    icon: 'bread-slice-outline',
  },
  {
    id: '3',
    name: 'Organic Wildflower Honey',
    batch: '#HNY202408',
    time: '3 days ago',
    icon: 'flower-outline',
  },
];

export const HistoryScreen: React.FC = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Verification History</Text>
      <Text style={styles.subtitle}>Immutable blockchain audit trail</Text>

      <FlatList
        data={historyItems}
        keyExtractor={(item) => item.id}
        contentContainerStyle={{ paddingBottom: 120 }}
        renderItem={({ item }) => (
          <View style={styles.card}>
            <View style={styles.iconCircle}>
              <MaterialCommunityIcons name={item.icon as any} size={24} color={Colors.dustyOlive} />
            </View>
            <View style={{ flex: 1, marginLeft: 14 }}>
              <Text style={styles.itemName}>{item.name}</Text>
              <Text style={styles.itemMeta}>Batch {item.batch} • {item.time}</Text>
            </View>
            <MaterialCommunityIcons name="check-decagram" size={20} color={Colors.badgeVerifiedText} />
          </View>
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
    fontSize: 14,
    color: Colors.textSecondary,
    marginTop: 4,
    marginBottom: 20,
  },
  card: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFF',
    padding: 14,
    borderRadius: 16,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    marginBottom: 10,
  },
  iconCircle: {
    width: 44,
    height: 44,
    borderRadius: 12,
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
    marginTop: 2,
  },
});
