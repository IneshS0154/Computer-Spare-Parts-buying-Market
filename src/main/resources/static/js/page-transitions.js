// Page Transition Handler
class PageTransitions {
    constructor() {
        this.init();
    }

    init() {
        this.setupPageTransitions();
        this.setupFormAnimations();
        this.setupButtonAnimations();
        this.setupCardAnimations();
    }

    setupPageTransitions() {
        // Add fade-in animation to body when page loads
        document.addEventListener('DOMContentLoaded', () => {
            document.body.classList.add('fade-in');
        });

        // Handle link clicks for smooth transitions
        document.addEventListener('click', (e) => {
            const link = e.target.closest('a');
            if (link && this.isInternalLink(link)) {
                e.preventDefault();
                this.navigateToPage(link.href);
            }
        });

        // Handle form submissions
        document.addEventListener('submit', (e) => {
            const form = e.target;
            if (form.tagName === 'FORM') {
                this.showLoadingState();
            }
        });
    }

    isInternalLink(link) {
        const href = link.getAttribute('href');
        return href && 
               !href.startsWith('http') && 
               !href.startsWith('mailto:') && 
               !href.startsWith('tel:') && 
               !href.startsWith('#') &&
               !href.startsWith('javascript:');
    }

    navigateToPage(url) {
        // Direct navigation without overlay
        window.location.href = url;
    }

    showLoadingState() {
        const buttons = document.querySelectorAll('button[type="submit"]');
        buttons.forEach(button => {
            button.disabled = true;
            button.classList.add('pulse');
            const originalText = button.textContent;
            button.textContent = 'Loading...';
            
            // Reset after 3 seconds (fallback)
            setTimeout(() => {
                button.disabled = false;
                button.classList.remove('pulse');
                button.textContent = originalText;
            }, 3000);
        });
    }

    setupFormAnimations() {
        // Add animation classes to form groups
        const formGroups = document.querySelectorAll('.form-group, .space-y-6 > div, .space-y-4 > div');
        formGroups.forEach((group, index) => {
            group.classList.add('form-group');
            group.style.animationDelay = `${index * 0.1}s`;
        });
    }

    setupButtonAnimations() {
        // Add animation classes to buttons
        const buttons = document.querySelectorAll('button, .btn, [role="button"]');
        buttons.forEach(button => {
            button.classList.add('btn-animate');
        });
    }

    setupCardAnimations() {
        // Add animation classes to cards
        const cards = document.querySelectorAll('.card, .bg-white, .bg-card-light, .bg-card-dark');
        cards.forEach(card => {
            card.classList.add('card-animate');
        });
    }

    // Utility method to add staggered animations
    addStaggeredAnimation(container, selector = '> *') {
        const elements = container.querySelectorAll(selector);
        elements.forEach((element, index) => {
            element.style.animationDelay = `${index * 0.1}s`;
            element.classList.add('fade-in-up');
        });
    }
}

// Initialize page transitions when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new PageTransitions();
});

// Handle browser back/forward buttons
window.addEventListener('popstate', () => {
    document.body.classList.add('fade-in');
});

// Add smooth scrolling for anchor links
document.addEventListener('click', (e) => {
    const link = e.target.closest('a[href^="#"]');
    if (link) {
        e.preventDefault();
        const target = document.querySelector(link.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    }
});

// Add loading states for async operations
window.showLoading = function(element) {
    if (element) {
        element.classList.add('pulse');
        element.disabled = true;
    }
};

window.hideLoading = function(element) {
    if (element) {
        element.classList.remove('pulse');
        element.disabled = false;
    }
};
