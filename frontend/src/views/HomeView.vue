<template>
	<WelcomeUser />
	<ShopComponent />
</template>

<script>
import WelcomeUser from '@/components/WelcomeUser.vue';
import ShopComponent from '@/components/ShopComponent.vue';

export default {
	components: {
		WelcomeUser,
		ShopComponent,
	}
}
</script>
