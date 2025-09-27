// Dashboard specific JavaScript

document.addEventListener('DOMContentLoaded', function() {
    initializeDashboard();
});

function initializeDashboard() {
    loadDashboardData();
    setupDashboardEventListeners();
}

function setupDashboardEventListeners() {
    // Refresh button if exists
    const refreshBtn = document.getElementById('refreshDashboard');
    if (refreshBtn) {
        refreshBtn.addEventListener('click', loadDashboardData);
    }
}

async function loadDashboardData() {
    try {
        showLoading('#totalProducts');
        showLoading('#totalCustomers');
        showLoading('#pendingOrders');
        showLoading('#lowStockItems');
        
        // Simulate API calls - replace with actual API endpoints
        const [products, customers, orders, inventory] = await Promise.all([
            fetchDashboardStats('products'),
            fetchDashboardStats('customers'),
            fetchDashboardStats('orders'),
            fetchDashboardStats('inventory')
        ]);
        
        updateDashboardStats(products, customers, orders, inventory);
        loadRecentActivity();
        
    } catch (error) {
        console.error('Error loading dashboard data:', error);
        TechPartsApp.showAlert('Failed to load dashboard data', 'danger');
    }
}

async function fetchDashboardStats(type) {
    // Simulate API call - replace with actual implementation
    return new Promise((resolve) => {
        setTimeout(() => {
            const mockData = {
                products: { total: 156, active: 142, inactive: 14 },
                customers: { total: 89, active: 85, inactive: 4 },
                orders: { total: 234, pending: 12, completed: 222 },
                inventory: { totalItems: 156, lowStock: 8, outOfStock: 2 }
            };
            resolve(mockData[type]);
        }, 500);
    });
}

function updateDashboardStats(products, customers, orders, inventory) {
    // Update total products
    const totalProductsEl = document.getElementById('totalProducts');
    if (totalProductsEl) {
        totalProductsEl.textContent = products.total;
    }
    
    // Update total customers
    const totalCustomersEl = document.getElementById('totalCustomers');
    if (totalCustomersEl) {
        totalCustomersEl.textContent = customers.total;
    }
    
    // Update pending orders
    const pendingOrdersEl = document.getElementById('pendingOrders');
    if (pendingOrdersEl) {
        pendingOrdersEl.textContent = orders.pending;
    }
    
    // Update low stock items
    const lowStockItemsEl = document.getElementById('lowStockItems');
    if (lowStockItemsEl) {
        lowStockItemsEl.textContent = inventory.lowStock;
    }
}

function loadRecentActivity() {
    const recentActivityEl = document.getElementById('recentActivity');
    if (!recentActivityEl) return;
    
    // Mock recent activity data
    const activities = [
        { type: 'order', message: 'New order #1234 received from John Doe', time: '2 minutes ago' },
        { type: 'product', message: 'Product "Intel i7-12700K" stock updated', time: '15 minutes ago' },
        { type: 'customer', message: 'New customer "Jane Smith" registered', time: '1 hour ago' },
        { type: 'order', message: 'Order #1233 completed and shipped', time: '2 hours ago' }
    ];
    
    const activityHtml = activities.map(activity => `
        <div class="d-flex align-items-center mb-2">
            <div class="flex-shrink-0 me-2">
                <i class="fas fa-${getActivityIcon(activity.type)} text-primary"></i>
            </div>
            <div class="flex-grow-1">
                <div class="small">${activity.message}</div>
                <div class="text-muted small">${activity.time}</div>
            </div>
        </div>
    `).join('');
    
    recentActivityEl.innerHTML = activityHtml;
}

function getActivityIcon(type) {
    const icons = {
        order: 'shopping-cart',
        product: 'box',
        customer: 'user',
        inventory: 'warehouse'
    };
    return icons[type] || 'info-circle';
}

// Chart functionality (if needed)
function initializeCharts() {
    // This would integrate with Chart.js or similar library
    console.log('Charts initialized');
}
