import { defineStore } from 'pinia';

export const useCartStore = defineStore({
    id: 'cart',
    state: () => ({
        items: [],
    }),
    actions: {
        addToCart(product) {
            const existingItem = this.items.find(item => item.id === product.id);

            if (existingItem) {
                this.incrementItem(existingItem);
            } else {
                this.items.push({ ...product, quantity: 1 });
            }
        },
        incrementItem(item) {
            item.quantity++;
        },
        decrementItem(item) {
            if (item.quantity > 1) {
                item.quantity--;
            } else {
                this.removeFromCart(this.items.indexOf(item));
            }
        },
        removeFromCart(index) {
            this.items.splice(index, 1);
        },
        getTotalItems() {
            return this.items.reduce((total, item) => total + item.quantity, 0);
        },
        getTotalPrice() {
            return this.items.reduce((total, item) => total + (item.price * item.quantity), 0);
        },
        clearCart() {
            this.items = [];
        },
    },
});
