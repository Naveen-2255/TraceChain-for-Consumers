import React, { useState } from 'react';
import { View, StyleSheet, StatusBar, SafeAreaView } from 'react-native';
import { BottomNavBar, NavTab } from './src/components/BottomNavBar';
import { HomeScreen } from './src/screens/HomeScreen';
import { ScanScreen } from './src/screens/ScanScreen';
import { HistoryScreen } from './src/screens/HistoryScreen';
import { ProductDetailsScreen } from './src/screens/ProductDetailsScreen';
import { LoginScreen } from './src/screens/LoginScreen';
import { Product } from './src/data/productData';
import { Colors } from './src/theme/colors';

export default function App() {
  const [user, setUser] = useState<{ name: string; email: string } | null>({
    name: 'Naveen Joseph',
    email: 'naveenjosephvadakkel@gmail.com',
  });
  const [currentTab, setCurrentTab] = useState<NavTab>('home');
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);

  // If user is not logged in, render Login Screen
  if (!user) {
    return (
      <View style={styles.container}>
        <StatusBar barStyle="dark-content" backgroundColor={Colors.background} />
        <LoginScreen onLoginSuccess={(loggedInUser) => setUser(loggedInUser)} />
      </View>
    );
  }

  // If viewing product details
  if (selectedProduct) {
    return (
      <View style={styles.container}>
        <StatusBar barStyle="dark-content" backgroundColor={Colors.background} />
        <ProductDetailsScreen
          product={selectedProduct}
          onBack={() => setSelectedProduct(null)}
        />
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <StatusBar barStyle="dark-content" backgroundColor={Colors.background} />

      {/* Screen Render based on active Tab */}
      {currentTab === 'home' && (
        <HomeScreen
          user={user}
          onScanClick={() => setCurrentTab('scan')}
          onProductSelect={(product) => setSelectedProduct(product)}
          onLogout={() => setUser(null)}
        />
      )}

      {currentTab === 'scan' && (
        <ScanScreen
          onProductScanned={(scannedProduct) => {
            setSelectedProduct(scannedProduct);
            setCurrentTab('home');
          }}
          onBack={() => setCurrentTab('home')}
        />
      )}

      {currentTab === 'history' && (
        <HistoryScreen
          onProductSelect={(product) => setSelectedProduct(product)}
        />
      )}

      {/* Concentric Cradled Bottom Navigation Bar */}
      {currentTab !== 'scan' && (
        <BottomNavBar
          currentTab={currentTab}
          onTabSelect={(tab) => setCurrentTab(tab)}
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.background,
  },
});
