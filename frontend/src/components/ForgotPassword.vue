<template>
    <div class="forgot-password-form">
        <h4>{{ headingText }}</h4>
        <form @submit.prevent="onSubmit">
            <div class="form-group">
                <div class="reset-msg" v-if="passwordSent">
                    <p>An email containing your new password has been sent to the provided email address. Please check your inbox and spam/junk folder.</p>
                </div>

                <div class="reset-msg" v-if="!passwordSent">
                    <p>Please enter the email address associated with your account.</p> <p>We'll send you a new password shortly.</p>
                </div>
                
                <input type="email" v-if="!passwordSent" class="form-control" id="email" name="email" v-model="email"  placeholder="Email" required/>
            </div>
            <button type="submit" v-if="!passwordSent" class="resetPassword-button">Reset Password</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'ForgotPassword',
    data() {
        return {
            email: '',
            passwordSent: false,
            headingText: 'Reset your password',
        };
    },
    methods: {
        onSubmit() {
            const formData = new FormData();
            formData.append('email', this.email);

            axios.post('http://localhost:8080/api/v1/reset-password', formData)
                .then(() => {
                    this.passwordSent = true;
                    this.headingText = 'Password Successfully Sent';
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        },
    },
};
</script>

<style scoped>
.forgot-password-form {
    position: absolute;
    top: 50%;
    left: 50%;
    transform:translate(-50%, -50%);
    background-color: #f2f2f2;
    border-radius: 8px;
    padding: 20px;
    margin: 0 auto;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
    z-index: 1000;
}

h4 {
    text-align: center;
    margin-bottom: 20px;
    color: #333;
    font-size: 1.5em;
    white-space: nowrap;
}

.reset-msg {
    margin-bottom: 20px;
    font-size: 1.1em;
    color: #555;
    text-align: center;
    white-space: nowrap;
}


.resetPassword-button {
    margin-left: auto;
    margin-right: auto;
    display: block;
    background-color: #007bff;
    border: none;
    color: #fff;
    padding: 10px 20px;
    font-size: 16px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.resetPassword-button:hover {
    background-color: #0056b3;
}

.form-control {
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
    margin: 20px 0px;
}

.form-control::placeholder {
    color: #999;
}

</style>
