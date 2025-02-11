<template>
    <div class="checkout-container">
        <div v-if="cartItems.length === 0">Your cart is empty</div>
        <div v-else>
            <div class="cart-summary">
                <p>Total items: {{ totalItems }}</p>
                <p>Total price: ${{ totalPrice }}</p>

                <!-- Authentication Section -->
                <input v-model="personalNumber" placeholder="Enter your Personal Number" class="input-field" type="text" />
                <button @click="authenticateUser" :disabled="isAuthenticating" class="auth-button">
                    {{ isAuthenticating ? "Authenticating..." : "Authenticate with BankID" }}
                </button>
                <p v-if="isAuthenticated" class="auth-success">✅ Authentication successful!</p>

                <!-- Place Order Button -->
                <button @click="placeOrder" :disabled="!isAuthenticated || placingOrder" class="place-order-button">
                    {{ placingOrder ? "Processing..." : "Place Order" }}
                </button>
                <span v-if="placingOrder">Placing order...</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useCartStore } from '@/stores/cart';
import api from '@/stores/api';

const cart = useCartStore();
const cartItems = computed(() => cart.items);
const totalItems = computed(() => cart.getTotalItems());
const totalPrice = computed(() => cart.getTotalPrice());

const personalNumber = ref("");
const placingOrder = ref(false);
const isAuthenticated = ref(localStorage.getItem("isAuthenticated") === "true");
const isAuthenticating = ref(false);
const orderRef = ref(null);  // Stores the orderRef from BankID authentication

// Function to authenticate user
const authenticateUser = async () => {
    const accessToken = localStorage.getItem('token');
    console.log("Access token retrieved:", accessToken);  // ✅ Debug: Access token

    if (!accessToken) {
        alert("No valid access token found. Please log in.");
        return;
    }

    if (!personalNumber.value) {
        alert("Please enter your personal number.");
        return;
    }

    isAuthenticating.value = true;
    try {
        // FIX: Send personal number as plain text, not an object
        const response = await api.post('/bankid/authenticate', personalNumber.value, {
            headers: {
                "Content-Type": "text/plain",
                Authorization: `Bearer ${accessToken}`
            }
        });

        console.log("Authentication Response:", response.data);  // ✅ Debug: Response data

        const { autoStartToken, orderRef: newOrderRef } = response.data;

        if (!autoStartToken || !newOrderRef) {
            alert("Failed to get autoStartToken. Please try again.");
            return;
        }

        orderRef.value = newOrderRef;
        window.location.href = `bankid:///?autostarttoken=${autoStartToken}&redirect=null`;
        pollAuthenticationStatus(orderRef.value);
    } catch (error) {
        console.error("Authentication failed:", error.response?.data || error.message);  // ✅ Debug error
        alert("Authentication failed. Please check the console for details.");
        
        if (error.response) {
            console.error("Response Status:", error.response.status);
            console.error("Response Headers:", error.response.headers);
        }
    } finally {
        isAuthenticating.value = false;
    }
};

// Poll authentication status from backend
const pollAuthenticationStatus = async (orderRef) => {
    const maxRetries = 15;
    let retries = 0;

    const interval = setInterval(async () => {
        if (retries >= maxRetries) {
            clearInterval(interval);
            alert("Authentication timed out. Please try again.");
            resetAuthenticationState();
            return;
        }

        try {
            // Check authentication status from backend
            const response = await api.get('/bankid/status', {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                },
                params: { orderRef }
            });

            console.log("BankID Status:", response.data);  // ✅ Debug: BankID status response

            if (response.data.status === "complete") {
                isAuthenticated.value = true;
                localStorage.setItem("isAuthenticated", "true");
                clearInterval(interval);
                alert("Authentication successful!");
            } else if (response.data.status === "failed") {
                clearInterval(interval);
                alert("Authentication failed.");
                resetAuthenticationState();
            }
        } catch (error) {
            console.error("Error checking authentication status:", error.response?.data || error.message);  // ✅ Debug error
            alert("Error checking authentication status. Please try again.");
            clearInterval(interval);
            resetAuthenticationState();
        }
        retries++;
    }, 2000);
};

// Reset authentication state
const resetAuthenticationState = () => {
    isAuthenticated.value = false;
    localStorage.removeItem("isAuthenticated");
    orderRef.value = null;
};

// Function to place order
const placeOrder = async () => {
    if (!isAuthenticated.value) {
        alert("Please authenticate before placing an order.");
        return;
    }

    placingOrder.value = true;
    try {
        const accessToken = localStorage.getItem('token');
        const response = await api.post('/orders/place', {}, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        });

        console.log("Order Response:", response.data);  // ✅ Debug: Order response
        alert("Order placed successfully!");
    } catch (error) {
        console.error("Order failed:", error.response?.data || error.message);  // ✅ Debug error
        alert("Failed to place order. Please check the console for details.");
    } finally {
        placingOrder.value = false;
    }
};
</script>

<style scoped>
.checkout-container {
    max-width: 600px;
    margin: 0 auto;
}

.cart-summary {
    text-align: center;
}

.input-field {
    width: 100%;
    padding: 10px;
    margin: 10px 0;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
}

.auth-button,
.place-order-button {
    padding: 10px 20px;
    margin-top: 10px;
    background-color: #4CAF50;
    color: white;
    border: none;
    cursor: pointer;
    font-size: 16px;
}

.auth-button:disabled,
.place-order-button:disabled {
    background-color: gray;
    cursor: not-allowed;
}

.auth-success {
    color: green;
    font-weight: bold;
}

span {
    display: inline-block;
    margin-top: 10px;
    font-style: italic;
}
</style>
