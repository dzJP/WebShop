<template>
	<LoginComponent />
	<RegisterComponent />
</template>

<script>
import LoginComponent from '@/components/LoginComponent.vue';
import RegisterComponent from '@/components/RegisterComponent.vue';

export default {
	components: {
		LoginComponent,
		RegisterComponent,
	}
}
</script>
