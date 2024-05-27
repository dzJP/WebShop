<template>
    <div>
        <h1>Checkout</h1>
        <div v-if="cartItems.length === 0">Your cart is empty</div>
        <div v-else>
            <h2>Cart Items</h2>
            <div class="shopping-cart-container" @click="openCartPopup">
                <img src="@/assets/shoppingcart.png" alt="Shopping Cart" class="shopping-cart-image">
                <div class="cart-badge">{{ totalItems }}</div>
            </div>
            <div class="cart-summary">
                <p>Total items: {{ totalItems }}</p>
                <p>Total price: ${{ totalPrice }}</p>
                <button @click="placeOrder" :disabled="placingOrder">Place Order</button>
                <span v-if="placingOrder">Placing order...</span>
            </div>
            <div v-if="showCartPopup" class="cart-popup" @click="closeCartPopup">
                <div class="cart-items">
                    <div v-for="item in cartItems" :key="item.id" class="cart-item">
                        <div class="cart-item-details">
                            <p>{{ item.name }}</p>
                            <p>Price: ${{ item.price }}</p>
                            <div>
                                <button @click.stop="decreaseQuantity(item)">-</button>
                                <span>{{ item.quantity }}</span>
                                <button @click.stop="increaseQuantity(item)">+</button>
                                <button @click.stop="removeFromCart(item)">Remove</button>
                            </div>
                        </div>
                    </div>
                </div>
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
const showCartPopup = ref(false);

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

const openCartPopup = () => {
    showCartPopup.value = true;
};
const closeCartPopup = () => {
    showCartPopup.value = false;
};
const removeFromCart = (item) => {
    const index = cart.items.indexOf(item);
    if (index > -1) {
        cart.removeFromCart(index);
    }
};

const increaseQuantity = (item) => {
    item.quantity++;
};

const decreaseQuantity = (item) => {
    if (item.quantity > 1) {
        item.quantity--;
    }
};

</script>

<style scoped>
.shopping-cart-container {
    position: relative;
    display: inline-block;
}

.shopping-cart-image {
    width: 40px;
}
.shopping-cart-image:hover {
    cursor: pointer;
}
.cart-badge {
    position: absolute;
    top: -8px;
    right: -8px;
    background-color: #ff5f6d; /* Coral pink */
    color: white;
    border-radius: 50%;
    padding: 4px 8px; /* Increased padding */
    font-size: 12px;
}

.cart-popup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7); /* Darker overlay */
    z-index: 999;
    display: flex;
    justify-content: center;
    align-items: center;
}

.cart-items {
    background-color: #fff;
    padding: 20px;
    border-radius: 10px; /* Rounded corners */
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
    max-width: 400px;
}

.cart-item {
    margin-bottom: 20px; /* Increased margin between items */
    padding-bottom: 20px; /* Increased padding between items */
    border-bottom: 1px solid #eee;
}

.cart-item-details button {
    margin-left: 10px;
    cursor: pointer;
    padding: 8px 16px; /* Increased button padding */
    border: none;
    border-radius: 5px; /* Slightly larger border radius */
    background-color: #4caf50; /* Green */
    color: white;
    transition: background-color 0.3s ease;
}

.cart-item-details button:hover {
    background-color: #388e3c; /* Darker green on hover */
}
</style>
