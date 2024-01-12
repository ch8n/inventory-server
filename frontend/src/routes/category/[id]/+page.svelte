<script lang="ts">
	import { page } from '$app/stores';
	import type { Product } from '$lib/data/HomePage';
	import { onMount } from 'svelte';
	let categoryId = $page.params.id;

	let products: Product[] = [];

	onMount(async () => {
		try {
			const response = await fetch(`http://0.0.0.0:8080/v1/products/category?id=${categoryId}`);
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
	<p class="text-xl">Page {categoryId}</p>

	<div class="py-4 grid gap-4 grid-cols-2 grid-rows-2">
		{#if products.length === 0}
			<div class="border-2 border-sky-500 rounded-sm w-96 h-96">
				<div class="text-md">Loading...</div>
			</div>
		{:else}
			{#each products as product}
				<div class="py-2 border-2 border-sky-500 rounded-sm w-5/6 h-48 flex-col">
					<img class="h-24 w-full object-cover" src={product.imageUrls[0]} />
					<div class="text-md">{product.name}</div>
					<div class="text-md">{product.price}</div>
				</div>
			{/each}
		{/if}
	</div>
</div>
