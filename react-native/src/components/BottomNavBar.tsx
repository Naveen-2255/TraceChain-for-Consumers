import React, { useEffect, useRef } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Dimensions,
  Animated,
  Easing,
} from 'react-native';
import Svg, { Path } from 'react-native-svg';
import { MaterialCommunityIcons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';

const { width: SCREEN_WIDTH } = Dimensions.get('window');

export type NavTab = 'scan' | 'home' | 'history';

interface BottomNavBarProps {
  currentTab: NavTab;
  onTabSelect: (tab: NavTab) => void;
}

export const BottomNavBar: React.FC<BottomNavBarProps> = ({
  currentTab,
  onTabSelect,
}) => {
  // Animation drivers
  const scanUpDownAnim = useRef(new Animated.Value(0)).current;
  const historyRotateAnim = useRef(new Animated.Value(0)).current;
  const homeScaleAnim = useRef(new Animated.Value(1)).current;

  // 1. Scan animation: Up-and-down oscillating movement and scanning beam
  useEffect(() => {
    let scanLoop: Animated.CompositeAnimation | null = null;
    if (currentTab === 'scan') {
      scanUpDownAnim.setValue(0);
      scanLoop = Animated.loop(
        Animated.sequence([
          Animated.timing(scanUpDownAnim, {
            toValue: 1,
            duration: 750,
            easing: Easing.inOut(Easing.quad),
            useNativeDriver: true,
          }),
          Animated.timing(scanUpDownAnim, {
            toValue: 0,
            duration: 750,
            easing: Easing.inOut(Easing.quad),
            useNativeDriver: true,
          }),
        ])
      );
      scanLoop.start();
    } else {
      Animated.timing(scanUpDownAnim, {
        toValue: 0,
        duration: 200,
        useNativeDriver: true,
      }).start();
    }

    return () => {
      if (scanLoop) scanLoop.stop();
    };
  }, [currentTab]);

  // 2. History animation: Continuous 360-degree rotation of the history circle
  useEffect(() => {
    let historyLoop: Animated.CompositeAnimation | null = null;
    if (currentTab === 'history') {
      historyRotateAnim.setValue(0);
      historyLoop = Animated.loop(
        Animated.timing(historyRotateAnim, {
          toValue: 1,
          duration: 2000,
          easing: Easing.linear,
          useNativeDriver: true,
        })
      );
      historyLoop.start();
    } else {
      Animated.timing(historyRotateAnim, {
        toValue: 0,
        duration: 300,
        useNativeDriver: true,
      }).start();
    }

    return () => {
      if (historyLoop) historyLoop.stop();
    };
  }, [currentTab]);

  // 3. Home bounce when selected
  useEffect(() => {
    if (currentTab === 'home') {
      homeScaleAnim.setValue(0.92);
      Animated.spring(homeScaleAnim, {
        toValue: 1,
        friction: 4,
        tension: 60,
        useNativeDriver: true,
      }).start();
    }
  }, [currentTab]);

  // Geometry: Permanently centered cradle notch (NO transfer)
  const topY = 32;
  const cornerR = 20;
  const btnDiameter = 60;
  const btnRadius = btnDiameter / 2; // 30
  const cradleMargin = 6;
  const rCradle = btnRadius + cradleMargin; // 36
  const rShoulder = 14;
  const cy = topY + 4; // 36
  const Ys = topY + rShoulder; // 46
  const deltaY = Ys - cy; // 10
  const sumR = rCradle + rShoulder; // 50
  const deltaX = Math.sqrt(sumR * sumR - deltaY * deltaY); // ~48.99

  // Anchor cradle strictly at center
  const cx = SCREEN_WIDTH * 0.5;

  const p0x = cx - deltaX;
  const p0y = topY;
  const p1x = cx - rCradle * (deltaX / sumR);
  const p1y = cy + rCradle * (deltaY / sumR);
  const p3x = cx + rCradle * (deltaX / sumR);
  const p3y = p1y;
  const p4x = cx + deltaX;
  const p4y = topY;

  const navBarPath = `
    M 0 ${topY + cornerR}
    Q 0 ${topY} ${cornerR} ${topY}
    L ${p0x} ${p0y}
    A ${rShoulder} ${rShoulder} 0 0 1 ${p1x} ${p1y}
    A ${rCradle} ${rCradle} 0 0 0 ${p3x} ${p3y}
    A ${rShoulder} ${rShoulder} 0 0 1 ${p4x} ${p4y}
    L ${SCREEN_WIDTH - cornerR} ${topY}
    Q ${SCREEN_WIDTH} ${topY} ${SCREEN_WIDTH} ${topY + cornerR}
    L ${SCREEN_WIDTH} 120
    L 0 120
    Z
  `;

  // Interpolations for Scan animation: prominent up-and-down motion
  const scanIconTranslateY = scanUpDownAnim.interpolate({
    inputRange: [0, 1],
    outputRange: [-6, 6],
  });

  const scanLaserTranslateY = scanUpDownAnim.interpolate({
    inputRange: [0, 1],
    outputRange: [-12, 12],
  });

  // Interpolations for History rotation: smooth 360-degree rotation
  const historyRotation = historyRotateAnim.interpolate({
    inputRange: [0, 1],
    outputRange: ['0deg', '360deg'],
  });

  const isHomeSelected = currentTab === 'home';
  const isScanSelected = currentTab === 'scan';
  const isHistorySelected = currentTab === 'history';

  return (
    <View style={styles.container}>
      <Svg width={SCREEN_WIDTH} height={120} style={StyleSheet.absoluteFill}>
        <Path d={navBarPath} fill={Colors.navBar} />
      </Svg>

      <View style={styles.row}>
        {/* TAB 1: SCAN (Up-and-Down bouncing & laser scanning animation, NO circle transfer) */}
        <TouchableOpacity
          style={styles.tabItem}
          activeOpacity={0.8}
          onPress={() => onTabSelect('scan')}
        >
          <View style={styles.sideIconWrap}>
            <Animated.View
              style={{
                transform: [{ translateY: isScanSelected ? scanIconTranslateY : 0 }],
                alignItems: 'center',
                justifyContent: 'center',
              }}
            >
              <MaterialCommunityIcons
                name="qrcode-scan"
                size={27}
                color={isScanSelected ? Colors.activeCircle : Colors.inactiveIcon}
              />

              {/* Up-and-down laser scanning beam */}
              {isScanSelected && (
                <Animated.View
                  style={[
                    styles.scanLaserBeam,
                    { transform: [{ translateY: scanLaserTranslateY }] },
                  ]}
                />
              )}
            </Animated.View>
          </View>
          <Text style={[styles.label, isScanSelected && styles.activeLabel]}>
            Scan
          </Text>
        </TouchableOpacity>

        {/* TAB 2: HOME (Fixed in permanent concentric cradle, NEVER transfers) */}
        <TouchableOpacity
          style={styles.centerTabItem}
          activeOpacity={0.85}
          onPress={() => onTabSelect('home')}
        >
          <Animated.View
            style={[
              styles.centerCircle,
              isHomeSelected && styles.activeCenterCircle,
              { transform: [{ scale: isHomeSelected ? homeScaleAnim : 1 }] },
            ]}
          >
            <MaterialCommunityIcons
              name="home"
              size={30}
              color={isHomeSelected ? Colors.activeIcon : Colors.inactiveIcon}
            />
          </Animated.View>
          <Text style={[styles.centerLabel, isHomeSelected && styles.activeLabel]}>
            Home
          </Text>
        </TouchableOpacity>

        {/* TAB 3: HISTORY (Continuous circular rotation animation, NO circle transfer) */}
        <TouchableOpacity
          style={styles.tabItem}
          activeOpacity={0.8}
          onPress={() => onTabSelect('history')}
        >
          <View style={styles.sideIconWrap}>
            <Animated.View
              style={{
                transform: [{ rotate: isHistorySelected ? historyRotation : '0deg' }],
                alignItems: 'center',
                justifyContent: 'center',
              }}
            >
              <MaterialCommunityIcons
                name="history"
                size={28}
                color={isHistorySelected ? Colors.activeCircle : Colors.inactiveIcon}
              />
            </Animated.View>
          </View>
          <Text style={[styles.label, isHistorySelected && styles.activeLabel]}>
            History
          </Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    bottom: 0,
    width: '100%',
    height: 98,
  },
  row: {
    flexDirection: 'row',
    height: 98,
  },
  tabItem: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'flex-start',
  },
  centerTabItem: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'flex-start',
  },
  sideIconWrap: {
    marginTop: 44,
    width: 34,
    height: 34,
    alignItems: 'center',
    justifyContent: 'center',
    position: 'relative',
  },
  scanLaserBeam: {
    position: 'absolute',
    width: 26,
    height: 2.5,
    backgroundColor: Colors.activeCircle,
    shadowColor: Colors.activeCircle,
    shadowOffset: { width: 0, height: 0 },
    shadowOpacity: 0.95,
    shadowRadius: 5,
    elevation: 4,
  },
  centerCircle: {
    marginTop: 6,
    width: 60,
    height: 60,
    borderRadius: 30,
    backgroundColor: 'rgba(246, 242, 234, 0.22)',
    borderWidth: 1.5,
    borderColor: 'rgba(234, 221, 208, 0.35)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  activeCenterCircle: {
    backgroundColor: Colors.activeCircle,
    borderColor: Colors.activeCircle,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 5 },
    shadowOpacity: 0.3,
    shadowRadius: 7,
    elevation: 8,
  },
  label: {
    position: 'absolute',
    top: 74,
    fontSize: 12,
    color: Colors.inactiveIcon,
    fontWeight: '400',
  },
  centerLabel: {
    position: 'absolute',
    top: 74,
    fontSize: 12,
    color: Colors.inactiveIcon,
    fontWeight: '400',
  },
  activeLabel: {
    color: Colors.activeCircle,
    fontWeight: '700',
  },
});
