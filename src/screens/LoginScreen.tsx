import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
} from 'react-native';
import { MaterialCommunityIcons } from '@expo/vector-icons';
import { Colors } from '../theme/colors';

interface LoginScreenProps {
  onLoginSuccess: (user: { name: string; email: string }) => void;
}

export const LoginScreen: React.FC<LoginScreenProps> = ({ onLoginSuccess }) => {
  const [email, setEmail] = useState('naveenjosephvadakkel@gmail.com');
  const [password, setPassword] = useState('password123');
  const [showPassword, setShowPassword] = useState(false);
  const [rememberMe, setRememberMe] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');

  const handleSignIn = () => {
    if (!email.trim() || !password.trim()) {
      setErrorMessage('Please enter both email and password.');
      return;
    }
    setErrorMessage('');
    onLoginSuccess({
      name: 'Naveen Joseph',
      email: email.trim(),
    });
  };

  const handleGuestSignIn = () => {
    onLoginSuccess({
      name: 'Guest Consumer',
      email: 'guest@tracechain.network',
    });
  };

  return (
    <KeyboardAvoidingView
      behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
      style={styles.container}
    >
      <ScrollView
        contentContainerStyle={styles.scrollContent}
        showsVerticalScrollIndicator={false}
      >
        {/* Brand Logo & Header */}
        <View style={styles.brandHeader}>
          <View style={styles.logoBadge}>
            <MaterialCommunityIcons name="shield-check" size={38} color={Colors.dustyOlive} />
          </View>
          <View style={styles.brandNameRow}>
            <Text style={styles.brandTrace}>Trace</Text>
            <Text style={styles.brandChain}>Chain</Text>
          </View>
          <Text style={styles.brandTagline}>
            Blockchain-Verified Supply Chain & Consumer Trust
          </Text>
        </View>

        {/* Login Form Card */}
        <View style={styles.card}>
          <Text style={styles.welcomeTitle}>Welcome Back</Text>
          <Text style={styles.welcomeSubtitle}>Sign in to verify products and view your audit history</Text>

          {errorMessage ? (
            <View style={styles.errorBanner}>
              <MaterialCommunityIcons name="alert-circle-outline" size={18} color="#C84B31" />
              <Text style={styles.errorText}>{errorMessage}</Text>
            </View>
          ) : null}

          {/* Email / Username Input */}
          <Text style={styles.inputLabel}>Email Address</Text>
          <View style={styles.inputField}>
            <MaterialCommunityIcons name="email-outline" size={20} color={Colors.textSecondary} />
            <TextInput
              style={styles.textInput}
              placeholder="name@example.com"
              placeholderTextColor="#A0988F"
              value={email}
              onChangeText={(text) => {
                setEmail(text);
                setErrorMessage('');
              }}
              autoCapitalize="none"
              keyboardType="email-address"
            />
          </View>

          {/* Password Input */}
          <Text style={styles.inputLabel}>Password</Text>
          <View style={styles.inputField}>
            <MaterialCommunityIcons name="lock-outline" size={20} color={Colors.textSecondary} />
            <TextInput
              style={styles.textInput}
              placeholder="Enter your password"
              placeholderTextColor="#A0988F"
              value={password}
              onChangeText={(text) => {
                setPassword(text);
                setErrorMessage('');
              }}
              secureTextEntry={!showPassword}
            />
            <TouchableOpacity onPress={() => setShowPassword(!showPassword)} hitSlop={{ top: 8, bottom: 8, left: 8, right: 8 }}>
              <MaterialCommunityIcons
                name={showPassword ? 'eye-off-outline' : 'eye-outline'}
                size={20}
                color={Colors.textSecondary}
              />
            </TouchableOpacity>
          </View>

          {/* Remember Me & Forgot Password */}
          <View style={styles.optionsRow}>
            <TouchableOpacity
              style={styles.rememberMeRow}
              activeOpacity={0.8}
              onPress={() => setRememberMe(!rememberMe)}
            >
              <MaterialCommunityIcons
                name={rememberMe ? 'checkbox-marked' : 'checkbox-blank-outline'}
                size={20}
                color={rememberMe ? Colors.dustyOlive : Colors.textSecondary}
              />
              <Text style={styles.rememberMeText}>Remember me</Text>
            </TouchableOpacity>

            <TouchableOpacity activeOpacity={0.8}>
              <Text style={styles.forgotText}>Forgot password?</Text>
            </TouchableOpacity>
          </View>

          {/* Primary Sign In Button */}
          <TouchableOpacity style={styles.signInButton} activeOpacity={0.85} onPress={handleSignIn}>
            <Text style={styles.signInButtonText}>Sign In</Text>
            <MaterialCommunityIcons name="arrow-right" size={20} color="#FFFFFF" />
          </TouchableOpacity>

          {/* Divider */}
          <View style={styles.dividerRow}>
            <View style={styles.dividerLine} />
            <Text style={styles.dividerText}>or continue with</Text>
            <View style={styles.dividerLine} />
          </View>

          {/* Social / Guest Sign In */}
          <TouchableOpacity style={styles.googleButton} activeOpacity={0.85} onPress={handleGuestSignIn}>
            <MaterialCommunityIcons name="google" size={20} color={Colors.pitchBlack} />
            <Text style={styles.googleButtonText}>Continue with Google</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.guestButton} activeOpacity={0.85} onPress={handleGuestSignIn}>
            <MaterialCommunityIcons name="account-outline" size={20} color={Colors.dustyOlive} />
            <Text style={styles.guestButtonText}>Continue as Guest</Text>
          </TouchableOpacity>
        </View>

        {/* Footer */}
        <View style={styles.footerRow}>
          <Text style={styles.footerText}>Don't have an account? </Text>
          <TouchableOpacity activeOpacity={0.8} onPress={handleGuestSignIn}>
            <Text style={styles.signUpText}>Sign Up</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.background,
  },
  scrollContent: {
    padding: 24,
    paddingTop: 64,
    paddingBottom: 40,
    justifyContent: 'center',
  },
  brandHeader: {
    alignItems: 'center',
    marginBottom: 28,
  },
  logoBadge: {
    width: 68,
    height: 68,
    borderRadius: 34,
    backgroundColor: '#FFFFFF',
    borderWidth: 1.5,
    borderColor: Colors.cardBorder,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 12,
    shadowColor: Colors.dustyOlive,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.15,
    shadowRadius: 8,
    elevation: 4,
  },
  brandNameRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  brandTrace: {
    fontSize: 30,
    fontWeight: '800',
    color: Colors.pitchBlack,
    letterSpacing: -0.5,
  },
  brandChain: {
    fontSize: 30,
    fontWeight: '800',
    color: Colors.dustyOlive,
    letterSpacing: -0.5,
  },
  brandTagline: {
    fontSize: 13,
    color: Colors.textSecondary,
    marginTop: 4,
    textAlign: 'center',
  },
  card: {
    backgroundColor: '#FFFFFF',
    borderRadius: 24,
    padding: 22,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.06,
    shadowRadius: 10,
    elevation: 3,
  },
  welcomeTitle: {
    fontSize: 20,
    fontWeight: '700',
    color: Colors.pitchBlack,
  },
  welcomeSubtitle: {
    fontSize: 13,
    color: Colors.textSecondary,
    marginTop: 4,
    marginBottom: 18,
  },
  errorBanner: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FDF2F0',
    padding: 10,
    borderRadius: 10,
    marginBottom: 14,
  },
  errorText: {
    fontSize: 12.5,
    color: '#C84B31',
    marginLeft: 8,
    fontWeight: '500',
  },
  inputLabel: {
    fontSize: 13,
    fontWeight: '600',
    color: Colors.pitchBlack,
    marginBottom: 6,
    marginTop: 10,
  },
  inputField: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.floralWhite,
    borderRadius: 14,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    paddingHorizontal: 14,
    height: 50,
  },
  textInput: {
    flex: 1,
    marginLeft: 10,
    fontSize: 14.5,
    color: Colors.pitchBlack,
  },
  optionsRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginTop: 14,
    marginBottom: 20,
  },
  rememberMeRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  rememberMeText: {
    fontSize: 13,
    color: Colors.textSecondary,
    marginLeft: 6,
  },
  forgotText: {
    fontSize: 13,
    fontWeight: '600',
    color: Colors.dustyOlive,
  },
  signInButton: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: Colors.dustyOlive,
    borderRadius: 16,
    height: 52,
    shadowColor: Colors.dustyOlive,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 6,
    elevation: 4,
  },
  signInButtonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: '700',
    marginRight: 8,
  },
  dividerRow: {
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 18,
  },
  dividerLine: {
    flex: 1,
    height: 1,
    backgroundColor: Colors.cardBorder,
  },
  dividerText: {
    fontSize: 12,
    color: Colors.textSecondary,
    paddingHorizontal: 12,
  },
  googleButton: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: Colors.floralWhite,
    borderRadius: 14,
    borderWidth: 1,
    borderColor: Colors.cardBorder,
    height: 48,
    marginBottom: 10,
  },
  googleButtonText: {
    fontSize: 14,
    fontWeight: '600',
    color: Colors.pitchBlack,
    marginLeft: 8,
  },
  guestButton: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#FFFFFF',
    borderRadius: 14,
    borderWidth: 1,
    borderColor: Colors.dustyOlive,
    height: 48,
  },
  guestButtonText: {
    fontSize: 14,
    fontWeight: '600',
    color: Colors.dustyOlive,
    marginLeft: 8,
  },
  footerRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 22,
  },
  footerText: {
    fontSize: 13.5,
    color: Colors.textSecondary,
  },
  signUpText: {
    fontSize: 13.5,
    fontWeight: '700',
    color: Colors.dustyOlive,
  },
});
