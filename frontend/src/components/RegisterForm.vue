<template>
    <div class="container">
        <div class="registration-form">
            <form @submit.prevent="onSubmit">
                <h4>Register</h4>
                <div class="form-group">
                    <input type="email" class="form-control" id="email" v-model="user.email" placeholder="email" />
                </div>

                <div class="form-group">
                    <input type="firstName" class="form-control" id="firstName" v-model="user.firstName"
                        placeholder="first name" />
                </div>

                <div class="form-group">
                    <input type="lastName" class="form-control" id="lastName" v-model="user.lastName"
                        placeholder="last name" />
                </div>

                <div class="form-group">
                    <input type="date" class="form-control" id="birthdate" v-model="user.birthDate" />
                </div>

                <div class="form-group">
                    <input type="password" class="form-control" id="password" v-model="user.password"
                        placeholder="password" />
                </div>

                <div class="form-group">
                    <input type="phoneNumber" class="form-control" id="phoneNumber" v-model="user.phoneNumber"
                        placeholder="phone number" />
                </div>

                <button type="submit" class="register-button">Register</button>
            </form>
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


export default {
    name: 'RegistrationForm',
    setup() {
        return {
            user,
            onSubmit,
            showPopup,
            popupMessage,
        };
    },
};
</script>
<style scoped>
    .container {
        max-width: 400px;
        margin: 0 auto;
        padding: 20px;
        background-color: #f7f7f7;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    h4 {
        display: flex;
        justify-content: center;
        font-size: 1.5em;
        margin-bottom: 20px;
        color: #333;
    }

    .form-group {
        margin-bottom: 20px;
    }

    .form-control {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 1em;
    }

    .register-button {
        margin-left: 8.75em;
        padding: 12px;
        border: none;
        border-radius: 5px;
        background-color: #007bff;
        color: #fff;
        font-size: 0.9em;
        cursor: pointer;
        font-family: 'Oxanium', sans-serif;
        text-transform: uppercase;
        letter-spacing: 2px;
    }

    .register-button:hover {
        background-color: #0056b3;
    }



</style>
