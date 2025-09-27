-- Sample product data for Tech Parts Solutions
-- Insert 8 products based on the products.html template

-- First, let's create a sample supplier user
INSERT INTO users (username, email, password, first_name, last_name, phone, address, role, is_active, created_at, updated_at) 
VALUES ('supplier1', 'supplier@techparts.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Tech', 'Supplier', '+1234567890', '123 Tech Street, Tech City', 'SUPPLIER', true, NOW(), NOW())
ON DUPLICATE KEY UPDATE username = username;

-- Get the supplier ID
SET @supplier_id = (SELECT id FROM users WHERE username = 'supplier1' LIMIT 1);

-- Insert 8 sample products
INSERT INTO inventory (name, description, category, model, price, stock_quantity, warranty_period_months, supplier_id, is_active, created_at, updated_at) VALUES
(
    'High-Performance CPU',
    'High-performance CPU with excellent processing power for gaming and professional applications. Features advanced architecture and efficient power consumption.',
    'CPU',
    'Intel Core i7-12700K',
    350.00,
    15,
    24,
    @supplier_id,
    true,
    NOW(),
    NOW()
),
(
    'Gaming Motherboard',
    'High-quality gaming motherboard with advanced features for gaming enthusiasts. Supports latest processors and offers excellent connectivity options.',
    'Motherboard',
    'ASUS ROG Strix Z690-E',
    180.00,
    12,
    36,
    @supplier_id,
    true,
    NOW(),
    NOW()
),
(
    '16GB DDR4 RAM',
    'High-speed DDR4 memory module with excellent performance for gaming and professional work. Features low latency and high bandwidth.',
    'Memory',
    'Corsair Vengeance LPX 16GB',
    75.00,
    25,
    24,
    @supplier_id,
    true,
    NOW(),
    NOW()
),
(
    '500GB NVMe SSD',
    'Ultra-fast NVMe SSD with exceptional read/write speeds. Perfect for operating system and application storage.',
    'Storage',
    'Samsung 980 PRO 500GB',
    55.00,
    30,
    60,
    @supplier_id,
    true,
    NOW(),
    NOW()
),
(
    '750W Power Supply',
    'High-efficiency power supply unit with 80+ Gold certification. Provides stable power delivery for high-end systems.',
    'Power Supply',
    'EVGA SuperNOVA 750 G5',
    90.00,
    18,
    36,
    @supplier_id,
    true,
    NOW(),
    NOW()
),
(
    'CPU Cooler',
    'High-performance CPU cooler with excellent thermal management. Features quiet operation and easy installation.',
    'Cooling',
    'Noctua NH-D15',
    65.00,
    20,
    24,
    @supplier_id,
    true,
    NOW(),
    NOW()
),
(
    'Case Fan',
    'High-quality case fan with excellent airflow and low noise levels. Perfect for system cooling and ventilation.',
    'Cooling',
    'Corsair AF120 LED',
    15.00,
    50,
    12,
    @supplier_id,
    true,
    NOW(),
    NOW()
),
(
    'Thermal Paste',
    'High-quality thermal paste for optimal heat transfer between CPU and cooler. Easy to apply and long-lasting.',
    'Accessories',
    'Arctic MX-4',
    8.00,
    100,
    12,
    @supplier_id,
    true,
    NOW(),
    NOW()
);
