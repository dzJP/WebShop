<template>
    <div class="container">
        <div class="registration-form">
            <form @submit.prevent="onSubmit">
                <h4>Register</h4>
                <div class="form-group mb-3">
                    <input type="email" class="form-control" id="email" v-model="user.email" placeholder="email" />
                </div>

                <div class="form-group mb-3">
                    <input type="firstName" class="form-control" id="firstName" v-model="user.firstName"
                        placeholder="first name" />
                </div>

                <div class="form-group mb-3">
                    <input type="lastName" class="form-control" id="lastName" v-model="user.lastName"
                        placeholder="last name" />
                </div>

                <div class="form-group mb-3">
                    <input type="date" class="form-control" id="birthdate" v-model="user.birthDate" />
                </div>

                <div class="form-group mb-3">
                    <input type="password" class="form-control" id="password" v-model="user.password"
                        placeholder="password" />
                </div>

                <div class="form-group mb-3">
                    <input type="phoneNumber" class="form-control" id="phoneNumber" v-model="user.phoneNumber"
                        placeholder="phone number" />
                </div>

                <button type="submit" class="register-btn btn btn-primary">Sign up</button>
            </form>
        </div>
        <div v-if="showPopup" class="popup">
            <p>{{ popupMessage }}</p>
            <button @click="closePopup">Close</button>
        </div>
    </div>
</template>

<script>
import { reactive, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';

const user = reactive({
    email: '',
    password: '',
});

const showPopup = ref(false);
const popupMessage = ref('');

function onSubmit() {
    console.log('onSubmit triggered', user);
    if (user.email !== '' && user.firstName !== '' && user.lastName !== '' && user.password !== '' && user.phoneNumber !== '' && user.birthDate !== '') {
        useAuthStore().register(
            user.email,
            user.firstName,
            user.lastName,
            user.password,
            user.phoneNumber,
            user.birthDate
        ).then(() => {
            // if registration is successful show popup
            showPopup.value = true;
            popupMessage.value = 'Registration successful!';
            // Reset form after successful registration
            resetForm();
        }).catch(error => {
            console.error('Registration failed:', error);
            // if registration fails show error popup
            showPopup.value = true;
            popupMessage.value = 'Registration failed. Please try again.';
        });
    }
}

function resetForm() {
    user.email = '';
    user.firstName = '';
    user.lastName = '';
    user.birthDate = '';
    user.phoneNumber = '';
    user.password = '';

}

function closePopup() {
    showPopup.value = false;
}

export default {
    name: 'RegistrationForm',
    setup() {
        return {
            user,
            onSubmit,
            showPopup,
            popupMessage,
            closePopup,
        };
    },
};
</script>
<style scoped>
.popup {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    border: 1px solid #ccc;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>