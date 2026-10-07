export interface JourneyStage {
  id: string;
  stageName: string;
  location: string;
  timestamp: string;
  handler: string;
  status: 'completed' | 'in_progress' | 'pending';
  icon: string;
  details: string;
}

export interface Product {
  id: string;
  barcode: string;
  name: string;
  category: string;
  brand: string;
  batchNumber: string;
  manufacturingDate: string;
  expiryDate: string;
  isVerified: boolean;
  blockchainHash: string;
  blockNumber: number;
  carbonFootprintKg: number;
  purityScorePercent: number;
  labTestReport: string;
  farmerSource: string;
  imageIcon: string;
  journey: JourneyStage[];
}

export const PRODUCTS_DATABASE: Record<string, Product> = {
  'AM202409': {
    id: 'prod-1',
    barcode: 'AM202409',
    name: 'Amul Taaza Homogenised Toned Milk',
    category: 'Dairy Products',
    brand: 'Amul (GCMMF)',
    batchNumber: 'AM202409-A4',
    manufacturingDate: '26 Sep 2026, 04:30 AM',
    expiryDate: '28 Sep 2026, 11:59 PM',
    isVerified: true,
    blockchainHash: '0x8f4c2b9a1d3e7f605a4b8c2d1e9f0a3b5c7d9e1f',
    blockNumber: 19842512,
    carbonFootprintKg: 0.82,
    purityScorePercent: 99.8,
    labTestReport: 'ISO 22000 Certified - Zero Adulterants / Pure Cow & Buffalo Milk',
    farmerSource: 'Anand Cooperative Dairy Society, Gujarat',
    imageIcon: 'bottle-tonic-outline',
    journey: [
      {
        id: 'j-1',
        stageName: 'Milk Collection & Testing',
        location: 'Anand Village Society, Gujarat',
        timestamp: '26 Sep 2026, 04:30 AM',
        handler: 'Kisan Cooperative Hub',
        status: 'completed',
        icon: 'cow',
        details: 'Fat 3.5%, SNF 8.5% verified via automated milk analyzer.'
      },
      {
        id: 'j-2',
        stageName: 'Homogenisation & Pasteurisation',
        location: 'Amul Central Processing Plant #2',
        timestamp: '26 Sep 2026, 08:15 AM',
        handler: 'Quality Assurance Line 4',
        status: 'completed',
        icon: 'factory',
        details: 'High-temperature short-time (HTST) pasteurisation at 72°C for 15s.'
      },
      {
        id: 'j-3',
        stageName: 'Cold Chain Transit (4°C)',
        location: 'Highway Corridor Inter-State #8',
        timestamp: '26 Sep 2026, 12:45 PM',
        handler: 'ThermoKing Refrigerated Logistics',
        status: 'completed',
        icon: 'truck-fast-outline',
        details: 'Continuous temperature sensor logging: avg 3.8°C.'
      },
      {
        id: 'j-4',
        stageName: 'Retail Distribution Hub',
        location: 'Metropolitan Supermarket Depot',
        timestamp: '26 Sep 2026, 03:20 PM',
        handler: 'FreshMart Distribution Hub',
        status: 'completed',
        icon: 'store-outline',
        details: 'Delivered to retail shelves and logged to TraceChain blockchain.'
      }
    ]
  },
  'MDN202409': {
    id: 'prod-2',
    barcode: 'MDN202409',
    name: 'Modern 100% Whole Wheat Bread',
    category: 'Bakery & Grains',
    brand: 'Modern Foods',
    batchNumber: 'MDN202409-B2',
    manufacturingDate: '25 Sep 2026, 02:00 AM',
    expiryDate: '29 Sep 2026, 11:59 PM',
    isVerified: true,
    blockchainHash: '0x3d7e8a9b2c1f0d4e5a6b7c8d9e0f1a2b3c4d5e6f',
    blockNumber: 19841890,
    carbonFootprintKg: 0.45,
    purityScorePercent: 99.4,
    labTestReport: 'FSSAI Certified - 100% Whole Wheat Flour, Zero Bromate',
    farmerSource: 'Organic Sharbati Wheat Farms, Sehore (MP)',
    imageIcon: 'bread-slice-outline',
    journey: [
      {
        id: 'j-1',
        stageName: 'Organic Grain Harvest',
        location: 'Sehore Organic Wheat Fields',
        timestamp: '18 Sep 2026, 10:00 AM',
        handler: 'Sehore Organic Farmer Collective',
        status: 'completed',
        icon: 'barley',
        details: 'Non-GMO certified grain harvested under fair-trade protocols.'
      },
      {
        id: 'j-2',
        stageName: 'Stone Ground Milling',
        location: 'Modern Milling Unit #1',
        timestamp: '22 Sep 2026, 06:00 AM',
        handler: 'Flour Quality Unit',
        status: 'completed',
        icon: 'grain',
        details: 'Bran and germ preserved for maximum dietary fiber content.'
      },
      {
        id: 'j-3',
        stageName: 'Baking & Packaging',
        location: 'Modern Bakery Plant #5',
        timestamp: '25 Sep 2026, 02:00 AM',
        handler: 'Modern Production Line B',
        status: 'completed',
        icon: 'chef-hat',
        details: 'Baked at 210°C, cooled, sliced, and sealed in nitrogen-flushed pouch.'
      },
      {
        id: 'j-4',
        stageName: 'Retail Supply Check',
        location: 'City Grocery Hub',
        timestamp: '25 Sep 2026, 08:30 AM',
        handler: 'Local Retail Dispatch',
        status: 'completed',
        icon: 'store-outline',
        details: 'QR tamper seal verified upon arrival.'
      }
    ]
  },
  'HNY202408': {
    id: 'prod-3',
    barcode: 'HNY202408',
    name: 'Organic Wildflower Honey (Raw & Unfiltered)',
    category: 'Natural Sweeteners',
    brand: 'NectarPure Organic',
    batchNumber: 'HNY202408-W1',
    manufacturingDate: '15 Aug 2026, 11:00 AM',
    expiryDate: '15 Aug 2028, 11:59 PM',
    isVerified: true,
    blockchainHash: '0x1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b',
    blockNumber: 19830114,
    carbonFootprintKg: 0.22,
    purityScorePercent: 100.0,
    labTestReport: 'NMR Tested - 100% Pure Floral Nectar, Zero C3/C4 Sugar Syrup',
    farmerSource: 'Nilgiri Biosphere Reserve Apiaries',
    imageIcon: 'flower-outline',
    journey: [
      {
        id: 'j-1',
        stageName: 'Wild Apiary Harvest',
        location: 'Nilgiri Forest Canopy',
        timestamp: '10 Aug 2026, 07:00 AM',
        handler: 'Tribal Bee Keepers Federation',
        status: 'completed',
        icon: 'bee',
        details: 'Ethically harvested from wild beehives without disturbing brood.'
      },
      {
        id: 'j-2',
        stageName: 'Cold Centrifuge Extraction',
        location: 'Organic Honey Processing Hub, Ooty',
        timestamp: '12 Aug 2026, 02:00 PM',
        handler: 'NectarPure Quality Lab',
        status: 'completed',
        icon: 'filter-variant',
        details: 'Unheated, cold-strained to preserve live enzymes and pollen.'
      },
      {
        id: 'j-3',
        stageName: 'NMR Sugar Testing',
        location: 'Certified Nuclear Magnetic Resonance Lab',
        timestamp: '14 Aug 2026, 04:30 PM',
        handler: 'Eurofins Analytics Lab',
        status: 'completed',
        icon: 'microscope',
        details: 'Passed 100% purity test with zero corn/rice syrup detection.'
      },
      {
        id: 'j-4',
        stageName: 'Glass Jar Sealing & QR Minting',
        location: 'NectarPure Packaging Facility',
        timestamp: '15 Aug 2026, 11:00 AM',
        handler: 'TraceChain Smart Contract #814',
        status: 'completed',
        icon: 'shield-check-outline',
        details: 'Blockchain digital certificate minted on Polygon Network.'
      }
    ]
  }
};

export const DEFAULT_PRODUCT = PRODUCTS_DATABASE['AM202409'];
