<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import BottomBar from '$lib/components/sections/bottom-bar.svelte';
	import Pagination from '$lib/components/sections/pagination.svelte';
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

<div class="mx-auto">
	<p class="text-xl">Page {categoryId}</p>

	<div class="py-4 grid gap-4 grid-cols-2 grid-rows-2">
		{#each products as product}
			<button
				on:click={(e) => {
					goto(`/category/${categoryId}/product/${product.productId}`);
				}}
				class="py-2 border-2 border-sky-500 rounded-sm w-5/6 h-48 flex-col"
			>
				<img class="h-24 w-full object-cover" src={product.imageUrls[0]} />
				<div class="text-md">{product.name}</div>
				<div class="text-md">{product.price}</div>
			</button>
		{/each}
	</div>

	<div class="fixed bottom-16 p-4 left-0 w-full place-items-center grid grid-cols-1 grid-rows-1">
		<div class="grid grid-cols-1 place-items-center">
			<Pagination />
		</div>
	</div>

	<BottomBar />

	<div class="my-12" />
</div>
