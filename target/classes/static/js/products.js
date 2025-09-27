// Products management JavaScript

let products = [];
let filteredProducts = [];

document.addEventListener('DOMContentLoaded', function() {
    initializeProducts();
});

function initializeProducts() {
    loadProducts();
    setupProductEventListeners();
}

function setupProductEventListeners() {
    // Search functionality
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        searchInput.addEventListener('input', TechPartsApp.debounce(filterProducts, 300));
    }
    
    // Filter functionality
    const categoryFilter = document.getElementById('categoryFilter');
    const statusFilter = document.getElementById('statusFilter');
    
    if (categoryFilter) {
        categoryFilter.addEventListener('change', filterProducts);
    }
    
    if (statusFilter) {
        statusFilter.addEventListener('change', filterProducts);
    }
    
    // Save product button
    const saveProductBtn = document.getElementById('saveProduct');
    if (saveProductBtn) {
        saveProductBtn.addEventListener('click', saveProduct);
    }
    
    // Modal events
    const productModal = document.getElementById('productModal');
    if (productModal) {
        productModal.addEventListener('hidden.bs.modal', clearProductForm);
    }
}

async function loadProducts() {
    try {
        showLoading('#productsTableBody');
        
        // Simulate API call - replace with actual implementation
        products = await fetchProducts();
        filteredProducts = [...products];
        renderProductsTable();
        
    } catch (error) {
        console.error('Error loading products:', error);
        TechPartsApp.showAlert('Failed to load products', 'danger');
    }
}

async function fetchProducts() {
    // Mock data - replace with actual API call
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve([
                {
                    id: 1,
                    name: 'Intel Core i7-12700K',
                    category: 'CPU',
                    price: 399.99,
                    stock: 25,
                    status: 'ACTIVE',
                    description: '12th Gen Intel Core i7 processor'
                },
                {
                    id: 2,
                    name: 'NVIDIA RTX 4080',
                    category: 'GPU',
                    price: 1199.99,
                    stock: 8,
                    status: 'ACTIVE',
                    description: 'High-performance graphics card'
                },
                {
                    id: 3,
                    name: 'Corsair Vengeance LPX 32GB',
                    category: 'RAM',
                    price: 149.99,
                    stock: 3,
                    status: 'ACTIVE',
                    description: '32GB DDR4 RAM kit'
                },
                {
                    id: 4,
                    name: 'Samsung 980 PRO 1TB',
                    category: 'Storage',
                    price: 199.99,
                    stock: 0,
                    status: 'INACTIVE',
                    description: 'High-speed NVMe SSD'
                }
            ]);
        }, 500);
    });
}

function renderProductsTable() {
    const tbody = document.getElementById('productsTableBody');
    if (!tbody) return;
    
    if (filteredProducts.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="text-center text-muted">No products found</td></tr>';
        return;
    }
    
    const rows = filteredProducts.map(product => `
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td><span class="badge bg-secondary">${product.category}</span></td>
            <td>${TechPartsApp.formatCurrency(product.price)}</td>
            <td>
                <span class="${product.stock === 0 ? 'text-danger' : product.stock < 10 ? 'text-warning' : ''}">
                    ${product.stock}
                </span>
            </td>
            <td>
                <span class="badge ${product.status === 'ACTIVE' ? 'bg-success' : 'bg-danger'}">
                    ${product.status}
                </span>
            </td>
            <td>
                <div class="btn-group btn-group-sm" role="group">
                    <button class="btn btn-outline-primary" onclick="editProduct(${product.id})" title="Edit">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-outline-danger" onclick="deleteProduct(${product.id})" title="Delete">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
    
    tbody.innerHTML = rows;
}

function filterProducts() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();
    const categoryFilter = document.getElementById('categoryFilter').value;
    const statusFilter = document.getElementById('statusFilter').value;
    
    filteredProducts = products.filter(product => {
        const matchesSearch = product.name.toLowerCase().includes(searchTerm) ||
                            product.description.toLowerCase().includes(searchTerm);
        const matchesCategory = !categoryFilter || product.category === categoryFilter;
        const matchesStatus = !statusFilter || product.status === statusFilter;
        
        return matchesSearch && matchesCategory && matchesStatus;
    });
    
    renderProductsTable();
}

function editProduct(productId) {
    const product = products.find(p => p.id === productId);
    if (!product) return;
    
    // Populate form with product data
    document.getElementById('productId').value = product.id;
    document.getElementById('productName').value = product.name;
    document.getElementById('productCategory').value = product.category;
    document.getElementById('productPrice').value = product.price;
    document.getElementById('productStock').value = product.stock;
    document.getElementById('productDescription').value = product.description;
    document.getElementById('productStatus').value = product.status;
    
    // Update modal title
    document.getElementById('productModalTitle').textContent = 'Edit Product';
    
    // Show modal
    const modal = new bootstrap.Modal(document.getElementById('productModal'));
    modal.show();
}

function deleteProduct(productId) {
    if (confirm('Are you sure you want to delete this product?')) {
        // Remove from products array
        products = products.filter(p => p.id !== productId);
        filteredProducts = [...products];
        renderProductsTable();
        
        TechPartsApp.showAlert('Product deleted successfully', 'success');
    }
}

function saveProduct() {
    if (!TechPartsApp.validateForm('productForm')) {
        TechPartsApp.showAlert('Please fill in all required fields', 'warning');
        return;
    }
    
    const productData = {
        id: document.getElementById('productId').value,
        name: document.getElementById('productName').value,
        category: document.getElementById('productCategory').value,
        price: parseFloat(document.getElementById('productPrice').value),
        stock: parseInt(document.getElementById('productStock').value),
        description: document.getElementById('productDescription').value,
        status: document.getElementById('productStatus').value
    };
    
    if (productData.id) {
        // Update existing product
        const index = products.findIndex(p => p.id == productData.id);
        if (index !== -1) {
            products[index] = { ...products[index], ...productData };
            TechPartsApp.showAlert('Product updated successfully', 'success');
        }
    } else {
        // Add new product
        productData.id = Math.max(...products.map(p => p.id), 0) + 1;
        products.push(productData);
        TechPartsApp.showAlert('Product added successfully', 'success');
    }
    
    filteredProducts = [...products];
    renderProductsTable();
    
    // Close modal
    const modal = bootstrap.Modal.getInstance(document.getElementById('productModal'));
    modal.hide();
}

function clearProductForm() {
    document.getElementById('productForm').reset();
    document.getElementById('productId').value = '';
    document.getElementById('productModalTitle').textContent = 'Add New Product';
}
