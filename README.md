# TraceChain Mobile (React Native / Expo)

A complete React Native implementation of TraceChain featuring:
- **Authentication**: Modern Login screen with remember me and guest session.
- **Concentric Bottom Navigation Bar**: Custom curved SVG cradle notch and floating circular active tab.
- **Fixed Home Button**: Center cradle Home button stays visible across all tabs.
- **One-Time Tab Animations**:
  - Scan tab: Smooth one-time laser sweep up and down upon selection.
  - History tab: Clock icon rotates 360 degrees strictly one time upon selection.
- **Live Camera Scanner**: Working QR / barcode scanner (`expo-camera`) with viewfinder and flashlight control.
- **Product Details & Verification**: Comprehensive blockchain journey timeline, lab purity certifications, transaction hashes, and carbon metrics.
- **Audit History**: Immutable history log with tap-to-inspect.
- **Earthy Organic Palette**: 5-color palette (Floral White, Almond Cream, Dusty Olive, Pitch Black, Dark Coffee).

---

## How to Run on Your Laptop / Physical Phone with Expo:

1. **Install dependencies**:
   ```bash
   npm install
   ```

2. **Start the Expo development server**:
   ```bash
   npx expo start
   ```

3. **Launch on Your Device / Simulator**:
   - Install **Expo Go** from [Google Play Store](https://play.google.com/store/apps/details?id=host.exp.exponent) (Android) or [Apple App Store](https://apps.apple.com/app/expo-go/id982107779) (iPhone).
   - Scan the QR code displayed in your terminal with your phone camera (iOS) or the Expo Go app (Android).
   - Press `a` in the terminal to launch in an Android Emulator, `i` for iOS Simulator, or `w` for Web.
