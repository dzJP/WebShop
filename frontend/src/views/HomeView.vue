<template>
	<WelcomeUser />
	<CheckoutComponent />
	<ShopComponent />
</template>

<script>
import WelcomeUser from '@/components/WelcomeUser.vue';
import ShopComponent from '@/components/ShopComponent.vue';
import CheckoutComponent from '@/components/CheckoutComponent.vue';

export default {
	components: {
		WelcomeUser,
		CheckoutComponent,
		ShopComponent,

	}
}
</script>
