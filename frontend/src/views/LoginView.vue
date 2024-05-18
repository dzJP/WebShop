<template>
    <LoginForm />
    <RegisterForm />
</template>

<script>
import LoginForm from '@/components/LoginForm.vue';
import RegisterForm from '@/components/RegisterForm.vue';

export default {
	name: 'HomeView',
	components: {
		LoginForm,
        RegisterForm,
	},
};

</script>