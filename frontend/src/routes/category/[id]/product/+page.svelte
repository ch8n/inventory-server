<script lang="ts">
	import { page } from '$app/stores';
	import type { Product } from '$lib/data/HomePage';
	import { onMount } from 'svelte';
	let id = $page.params.id;

	let products: Product[] = [];

	onMount(async () => {
		try {
			const response = await fetch('http://0.0.0.0:8080/v1/products');
			if (response.ok) {
				let _response: any = await response.json();
				products = _response.data.map((it: any) => {
					let item: Product = {
						productId: it.productId,
						name: it.name,
						description: it.description,
						price: it.price,
						stockQuantity: it.stockQuantity,
						imageUrls: it.imageUrls,
						categoryId: it.categoryId,
						variants: it.variants
					};
					return item;
				});
				console.log(products);
			} else {
				console.error('Failed to fetch data:', response.status, response.statusText);
			}
		} catch (error) {
			console.error('Error during fetch:', error.message);
		}
	});
</script>

<div class="mx-auto px-6 py-6">
	<p class="text-xl">Produce {id}</p>
</div>
