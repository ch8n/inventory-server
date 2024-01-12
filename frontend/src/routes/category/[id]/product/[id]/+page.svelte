<script lang="ts">
	import { page } from '$app/stores';
	import type { Product } from '$lib/data/HomePage';
	import { onMount } from 'svelte';
	let productId = $page.params.id;

	let product: Product | null = null;

	onMount(async () => {
		try {
			const response = await fetch(`http://0.0.0.0:8080/v1/products?id=${productId}`);
			if (response.ok) {
				let _response: any = await response.json();
				let data = _response.data[0];
				product = {
					productId: data.productId,
					name: data.name,
					description: data.description,
					price: data.price,
					stockQuantity: data.stockQuantity,
					imageUrls: data.imageUrls,
					categoryId: data.categoryId,
					variants: data.variants
				} as Product;
				console.log(product);
			} else {
				console.error('Failed to fetch data:', response.status, response.statusText);
			}
		} catch (error) {
			console.error('Error during fetch:', error.message);
		}
	});
</script>

<div class="mx-auto px-6 py-6">
	<p class="text-xl">Product {productId}</p>
	<p>Name - {product?.name}</p>
</div>
