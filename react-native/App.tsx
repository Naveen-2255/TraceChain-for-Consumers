import React, { useState } from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import { BottomNavBar, NavTab } from './src/components/BottomNavBar';
import { HomeScreen } from './src/screens/HomeScreen';
import { ScanScreen } from './src/screens/ScanScreen';
import { HistoryScreen } from './src/screens/HistoryScreen';
import { Colors } from './src/theme/colors';

export default function App() {
  const [currentTab, setCurrentTab] = useState<NavTab>('home');

  return (
    <View style={styles.container}>
      <StatusBar barStyle="dark-content" backgroundColor={Colors.background} />

      {currentTab === 'home' && (
        <HomeScreen onScanClick={() => setCurrentTab('scan')} />
      )}

      {currentTab === 'scan' && <ScanScreen />}

      {currentTab === 'history' && <HistoryScreen />}

      <BottomNavBar currentTab={currentTab} onTabSelect={setCurrentTab} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.background,
  },
});
