import React from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Dimensions,
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
  const tabs: { key: NavTab; label: string; icon: string }[] = [
    { key: 'scan', label: 'Scan', icon: 'qrcode-scan' },
    { key: 'home', label: 'Home', icon: 'home' },
    { key: 'history', label: 'History', icon: 'history' },
  ];

  // Concentric circular cradle geometry matching the native implementation
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

  const activeIndex = tabs.findIndex((t) => t.key === currentTab);
  const targetFraction = (activeIndex * 2 + 1) / (tabs.length * 2);
  const cx = SCREEN_WIDTH * targetFraction;

  // The 5 key junction points for the exact circular arc cradle
  const p0x = cx - deltaX;
  const p0y = topY;
  const p1x = cx - rCradle * (deltaX / sumR);
  const p1y = cy + rCradle * (deltaY / sumR);
  const p3x = cx + rCradle * (deltaX / sumR);
  const p3y = p1y;
  const p4x = cx + deltaX;
  const p4y = topY;

  // Concentric SVG path
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

  return (
    <View style={styles.container}>
      <Svg width={SCREEN_WIDTH} height={120} style={StyleSheet.absoluteFill}>
        <Path d={navBarPath} fill={Colors.navBar} />
      </Svg>

      <View style={styles.row}>
        {tabs.map((tab) => {
          const isSelected = currentTab === tab.key;

          return (
            <TouchableOpacity
              key={tab.key}
              style={styles.tabItem}
              activeOpacity={0.85}
              onPress={() => onTabSelect(tab.key)}
            >
              <View
                style={[
                  styles.iconWrap,
                  isSelected && styles.activeIconCircle,
                ]}
              >
                <MaterialCommunityIcons
                  name={tab.icon as any}
                  size={isSelected ? 30 : 24}
                  color={isSelected ? Colors.activeIcon : Colors.inactiveIcon}
                />
              </View>

              <Text
                style={[
                  styles.label,
                  isSelected && styles.activeLabel,
                ]}
              >
                {tab.label}
              </Text>
            </TouchableOpacity>
          );
        })}
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
    position: 'relative',
  },
  iconWrap: {
    marginTop: 42,
    width: 36,
    height: 36,
    alignItems: 'center',
    justifyContent: 'center',
  },
  activeIconCircle: {
    marginTop: 6,
    width: 60,
    height: 60,
    borderRadius: 30,
    backgroundColor: Colors.activeCircle,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.25,
    shadowRadius: 6,
    elevation: 8,
  },
  label: {
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
