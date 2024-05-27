<template>
    <div v-if="showRegisterPopup || showForgotPasswordPopup" class="overlay" @click="closePopup"></div>
    <div>
        <div class="login-container">
            <div class="login-header">
                <h1>Jakobs</h1>
                <h2>Webshop</h2>
            </div>

            <div class="login-form">
                <h4>Sign in</h4>
                <div class="form-group">
                    <input type="email" class="form-control" id="email" v-model="user.email" placeholder="email" />
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" id="password" v-model="user.password"
                        placeholder="password" />
                </div>
                <div class="actions-container">
                    <a @click="openRegisterPopup" class="register-text">Register</a>
                    <a @click="showForgotPasswordPopup" class="forgotpassword">Forgot password?</a>
                </div>
                <div class="button">
                    <button type="submit" class="signin-button" @click="onSubmit">Sign in</button>
                </div>
            </div>

            <div v-if="showRegisterPopup" class="registerPopup">
                <i class="register-close" @click="closeRegisterPopup">X</i>
                <RegisterForm />
            </div>

            <div v-if="showPopup" class="forgotPasswordPopup">
                <i class="forgotPassword-close" @click="closePopup">X</i>
                <ForgotPassword />
            </div>
        </div>


    </div>
</template>

<script>
import { reactive, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import ForgotPassword from './ForgotPassword.vue';
import RegisterForm from './RegisterForm.vue';


export default {
    name: 'LoginForm',
    components: {
        ForgotPassword,
        RegisterForm,

    },
    setup() {
        const showPopup = ref(false);
        const showRegisterPopup = ref(false);

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
            showRegisterPopup.value = false; // Close register popup as well
        }

        function openRegisterPopup() {
            showPopup.value = false; // Close other popups
            showRegisterPopup.value = true;
        }

        function closeRegisterPopup() {
            showRegisterPopup.value = false;
        }

        return {
            user,
            onSubmit,
            showPopup,
            showForgotPasswordPopup,
            closePopup,
            openRegisterPopup,
            closeRegisterPopup,
            showRegisterPopup,
        };
    },
};
</script>

<style scoped>
.login-container {
    position: absolute;
    top: 35%;
    left: 50%;
    width: 400px;
    transform: translate(-50%, -50%);
    margin: 0 auto;
    padding: 20px;
    background-color: #f7f7f7;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.login-header {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: row;
    gap: 0.5em;
}

h1,
h2 {
    margin: 0;
    color: #333;
    font-size: 2em;
}

h4 {
    text-align: center;
    margin: 20px;
    font-size: 1.5em;
}

h2 {
    color: #666;
}

.login-form {
    margin-top: 5px;
    display: flex;
    flex-direction: column;
    width: 100%;
    align-items: center;
}

form {
    display: flex;
    flex-direction: column;
    width: 100%;
}

.form-group {
    width: 100%;
    position: relative;
    display: flex;
    flex-direction: column;
}

.form-control {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-bottom: 10px;
    font-size: 1em;
}

.actions-container {
    display: flex;
    justify-content: center;
    gap: 10em;
    width: 100%;
    margin-top: 5px;
}

.register-text,
.forgotpassword {
    font-size: 0.9em;
    color: #007bff;
    text-decoration: none;
    cursor: pointer;
    margin-bottom: 20px;
}

.register-text:hover,
.forgotpassword:hover {
    text-decoration: underline;
}

.signin-button {
    width: 100%;
    padding: 10px;
    border: none;
    border-radius: 5px;
    background-color: #007bff;
    color: #fff;
    font-size: 1.1em;
    cursor: pointer;
}

.signin-button:hover {
    background-color: #0056b3;
}

.registerPopup {
    position: fixed;
    top: 45%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    align-items: center;
    z-index: 1000;
}

.container {
    width: 400px;
}

.register-close {
    position: absolute;
    top: 5px;
    right: 10px;
    font-size: 1.7em;
    font-weight: 700;
    color: #414141;
    cursor: pointer;
    font-style: normal;
}

.forgotPassword-close {
    position: absolute;
    top: 10%;
    right: -20%;
    font-size: 1.7em;
    font-weight: 700;
    color: #414141;
    cursor: pointer;
    font-style: normal;
    z-index: 1001;
}

.register-close:hover,
.forgotPassword-close:hover {
    color: #c50000;
}
</style>