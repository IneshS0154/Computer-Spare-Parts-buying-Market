# Tech Parts Solutions - Template Mapping Guide

## 📁 Template Structure Overview

This guide shows you where to place your HTML files in the Spring Boot project structure.

## 🎯 Template Directory Structure

```
src/main/resources/templates/
├── index.html                    # Home page (✅ Already exists)
├── dashboard.html                # Main dashboard (✅ Already exists)
├── products.html                 # Products management (✅ Already exists)
├── login.html                    # User login (✅ Created)
├── register.html                 # User registration (✅ Created)
├── cart.html                     # Shopping cart (✅ Created)
├── admin-dashboard.html          # Admin dashboard (✅ Created)
├── checkout.html                 # Checkout process
├── orders.html                   # Order history
├── order-details.html            # Individual order details
├── profile.html                  # User profile management
├── user-dashboard.html          # User-specific dashboard
├── admin-users.html              # Admin user management
├── admin-orders.html             # Admin order management
├── admin-inventory.html          # Admin inventory management
├── admin-complaints.html         # Admin complaint management
├── admin-repairs.html            # Admin repair tracking
├── admin-suppliers.html          # Admin supplier management
├── supplier-dashboard.html       # Supplier dashboard
├── supplier-requests.html        # Supplier stock requests
├── warranty.html                 # Warranty tracking
├── repair-request.html           # Submit repair request
└── complaints.html               # Submit complaints/reviews
```

## 🔄 How to Map Your HTML Files

### Step 1: Identify Your HTML Files
Look at your existing HTML files and match them to the template structure above.

### Step 2: Upload Process
1. **Copy your HTML files** to the appropriate locations in `src/main/resources/templates/`
2. **Replace the existing template files** if you have better versions
3. **Add Thymeleaf attributes** to make them dynamic

### Step 3: Thymeleaf Integration
Add these Thymeleaf attributes to your HTML files:

```html
<!-- Add to <html> tag -->
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<!-- For dynamic content -->
<div th:text="${variableName}">Default text</div>

<!-- For forms -->
<form th:action="@{/endpoint}" method="post">

<!-- For links -->
<a th:href="@{/path}">Link text</a>

<!-- For loops -->
<div th:each="item : ${items}">
    <span th:text="${item.name}"></span>
</div>
```

## 📋 Template Mapping Checklist

### ✅ Core Pages (Ready for your files)
- [ ] **index.html** - Home page
- [ ] **login.html** - Login page  
- [ ] **register.html** - Registration page
- [ ] **dashboard.html** - Main dashboard
- [ ] **products.html** - Products page

### ✅ Shopping & Orders
- [ ] **cart.html** - Shopping cart
- [ ] **checkout.html** - Checkout process
- [ ] **orders.html** - Order history
- [ ] **order-details.html** - Order details

### ✅ User Management
- [ ] **profile.html** - User profile
- [ ] **user-dashboard.html** - User dashboard

### ✅ Admin Panel
- [ ] **admin-dashboard.html** - Admin dashboard
- [ ] **admin-users.html** - User management
- [ ] **admin-orders.html** - Order management
- [ ] **admin-inventory.html** - Inventory management
- [ ] **admin-complaints.html** - Complaint management
- [ ] **admin-repairs.html** - Repair tracking
- [ ] **admin-suppliers.html** - Supplier management

### ✅ Supplier Interface
- [ ] **supplier-dashboard.html** - Supplier dashboard
- [ ] **supplier-requests.html** - Supplier requests

### ✅ Customer Features
- [ ] **warranty.html** - Warranty tracking
- [ ] **repair-request.html** - Repair requests
- [ ] **complaints.html** - Complaints/reviews

## 🎨 CSS and JavaScript Integration

### CSS Files Location:
```
src/main/resources/static/css/
├── style.css                     # Main stylesheet (✅ Already exists)
├── admin.css                     # Admin-specific styles
├── user.css                      # User-specific styles
└── supplier.css                  # Supplier-specific styles
```

### JavaScript Files Location:
```
src/main/resources/static/js/
├── main.js                       # Main JavaScript (✅ Already exists)
├── dashboard.js                  # Dashboard functionality (✅ Already exists)
├── products.js                   # Products management (✅ Already exists)
├── cart.js                       # Cart functionality
├── admin.js                      # Admin functionality
├── user.js                       # User functionality
└── supplier.js                   # Supplier functionality
```

## 🚀 Next Steps After Upload

1. **Upload your HTML files** to the templates directory
2. **Test the application** by running `mvn spring-boot:run`
3. **Add Thymeleaf attributes** to make pages dynamic
4. **Update navigation links** to match your routing
5. **Customize styling** in the CSS files
6. **Add JavaScript functionality** for interactive features

## 📝 Important Notes

- **Keep the navigation structure** consistent across all pages
- **Use Bootstrap classes** for responsive design
- **Add Thymeleaf attributes** for dynamic content
- **Test each page** after uploading
- **Maintain the same CSS/JS structure** for consistency

## 🔧 Controller Mapping

After uploading templates, we'll create controllers to map URLs to templates:

```java
@GetMapping("/login")
public String login() { return "login"; }

@GetMapping("/register") 
public String register() { return "register"; }

@GetMapping("/cart")
public String cart() { return "cart"; }
```

Ready to upload your HTML files! 🚀
