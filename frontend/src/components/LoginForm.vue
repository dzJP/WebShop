<template>
    <div class="">
        <div class="login-container">
            <div>
                <h1>Jakobs</h1>
                <h2>Webshop</h2>
            </div>
            <div class="login-form">
                <form @submit.prevent="onSubmit">
                    <h4>Sign in</h4>
                    <div class="form-group mb-3">
                        <input type="email" class="form-control" id="email" v-model="user.email" placeholder="email" />
                    </div>
                    <div class="form-group mb-3">
                        <input type="password" class="form-control" id="password" v-model="user.password" placeholder="password" />
                        <a @click="showForgotPasswordPopup" class="forgotpassword">Forgot password?</a>
                    </div>
                    <div class="pt-1 mb-4">
                        <button type="submit" class="signin-btn btn btn-primary">Sign in</button>
                    </div>
                </form>
            </div>

            <transition name="fade">
                <div v-if="showPopup" class="forgotpasswordPopup">
                    <i class="forgetpassword-toggle bi bi-x" @click="closePopup"></i>
                    <ForgotPassword />
                </div>
            </transition>
        </div>
    </div>

    <Overlay v-if="showPopup" @close-overlay="closePopup" />
</template>

<script>
import { reactive, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import ForgotPassword from './ForgotPassword.vue';
import Overlay from './OverlayComponent.vue';

export default {
    name: 'LoginForm',
    components: {
        ForgotPassword,
        Overlay,
    },
    setup() {
        const showPopup = ref(false);

        const user = reactive({
            email: '',
            password: '',
        });

        function onSubmit() {
            console.log(user);
            if (user.email !== '' && user.password !== '') {
                useAuthStore().login(user.email, user.password);
            }
        }

        function showForgotPasswordPopup() {
            showPopup.value = true;
        }

        function closePopup() {
            showPopup.value = false;
        }

        return {
            user,
            onSubmit,
            showPopup,
            showForgotPasswordPopup,
            closePopup,
        };
    },
};
</script>

