-- Create admin user in the admin table
INSERT INTO admin (username, email, password, first_name, last_name, phone, address, created_at, updated_at, is_active)
VALUES ('admin', 'admin@techparts.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Admin', 'User', '+1234567890', 'Admin Office', NOW(), NOW(), true);

-- Note: The password 'admin123' is encoded using BCrypt
-- The encoded password above corresponds to 'admin123'
