<template>
    <div class="checkout-container">
        <div v-if="cartItems.length === 0">Your cart is empty</div>
        <div v-else>
            <div class="cart-summary">
                <p>Total items: {{ totalItems }}</p>
                <p>Total price: ${{ totalPrice }}</p>
                <button @click="placeOrder" :disabled="placingOrder" class="place-order-button">Place Order</button>
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
            orderItems,
            {
                params: { email: email }
            }
        );
        console.log('Order placed successfully:', response.data);
        cart.clearCart();
    } catch (error) {
        if (error.response) {
            console.error('Server error:', error.response.data);
        } else if (error.request) {
            console.error('Network error:', error.message);
        } else {
            console.error('Error:', error.message);
        }
    } finally {
        placingOrder.value = false;
    }
};

</script>

<style scoped>
.checkout-container {
    flex: 0 0 10%;
    padding: 10px;
    margin: 5.3em 20px;
    text-align: center;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 5px;
}

.place-order-button {
    background-color: #ff5f6d;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.place-order-button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}

.place-order-button:hover:not(:disabled) {
    background-color: #e04a57;
}
</style>
