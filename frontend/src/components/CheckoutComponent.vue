<template>
    <div>
        <h1>Checkout</h1>
        <div v-if="cartItems.length === 0">Your cart is empty</div>
        <div v-else>
            <h2>Cart Items</h2>
            <div v-for="item in cartItems" :key="item.id" class="cart-item">
                <p>{{ item.name }}</p>
                <p>Price: ${{ item.price }}</p>
                <p>Quantity: {{ item.quantity }}</p>
                <button @click="removeFromCart(item)">Remove</button>
            </div>
            <div class="cart-summary">
                <p>Total items: {{ totalItems }}</p>
                <p>Total price: ${{ totalPrice }}</p>
                <button @click="placeOrder" :disabled="placingOrder">Place Order</button>
                <span v-if="placingOrder">Placing order...</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useCartStore } from '@/stores/cart';
import axios from 'axios';

const cart = useCartStore();
const cartItems = computed(() => cart.items);
const totalItems = computed(() => cart.getTotalItems());
const totalPrice = computed(() => cart.getTotalPrice());
const placingOrder = ref(false);

const removeFromCart = (item) => {
    const index = cart.items.indexOf(item);
    if (index > -1) {
        cart.removeFromCart(index);
    }
};

const placeOrder = async () => {
    try {
        placingOrder.value = true;
        const email = localStorage.getItem('user');

        console.log("Cart Items:", cartItems.value);

        const orderItems = cartItems.value.map(item => ({
            id: item.id,
            quantity: item.quantity,
            price: item.price
        }));

        const response = await axios.post('http://localhost:8080/api/v1/orders/checkout',
            orderItems, // Directly passing the array of order items
            {
                params: { email: email }
            }
        );
        console.log('Order placed successfully:', response.data);
        cart.clearCart();
    } catch (error) {
        console.error('Error placing order:', error);
    } finally {
        placingOrder.value = false;
    }
};
</script>

<style scoped>
.cart-item {
    border: 1px solid #ccc;
    padding: 16px;
    margin-bottom: 16px;
}

.cart-summary {
    margin-top: 20px;
    border-top: 2px solid #ccc;
    padding-top: 10px;
}
</style>
