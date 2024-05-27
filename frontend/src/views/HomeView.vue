<template>
	<WelcomeUser />
	<ShopComponent />
	<CheckoutComponent />
</template>

<script>
import WelcomeUser from '@/components/WelcomeUser.vue';
import ShopComponent from '@/components/ShopComponent.vue';
import CheckoutComponent from '@/components/CheckoutComponent.vue';

export default {
	components: {
		WelcomeUser,
		ShopComponent,
		CheckoutComponent
	}
}
</script>
