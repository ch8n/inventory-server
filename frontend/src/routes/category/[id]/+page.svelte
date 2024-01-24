<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import BottomBar from '$lib/components/sections/bottom-bar.svelte';
	import Pagination from '$lib/components/sections/pagination.svelte';
	import Title from '$lib/components/sections/title.svelte';
	import type { Product } from '$lib/data/HomePage';
	import { onMount } from 'svelte';
	import { categories, type Category } from '$lib/stores/categoryStore';

	let categoryId: number = parseInt($page.params.id);
	let products: Product[] = [];

	let currentPage = 0;
	let totalProductCount = 1;
	const onPageChange = (pageNumber: number) => {
		currentPage = pageNumber - 1;
	};

	const getCategory = (categoryId: number): Category | undefined => {
		const current = $categories;
		return current.find((it) => {
			return it.categoryId === categoryId;
		});
	};

	const onFetchProducts = async (currentPage:number) => {
		try {
			const response = await fetch(
				`http://0.0.0.0:8080/v1/products/category?id=${categoryId}&page=${currentPage}`
			);
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
				totalProductCount = _response.paged.totalPageSize;
				console.log(`totalProductCount ${totalProductCount}`);
			} else {
				console.error('Failed to fetch data:', response.status, response.statusText);
			}
		} catch (error) {
			console.error('Error during fetch:', error.message);
		}
	};


	$: {
		onFetchProducts(currentPage);
	}
</script>

<div class="mx-auto my-4">
	<div class="text-gray-900 flex justify-center">
		<Title label={getCategory(categoryId)?.categoryName ?? ''} />
	</div>

	<div class="p-4 grid gap-4 grid-cols-2 grid-rows-2">
		{#if products.length !== 0}
			{#each products as product}
				<button
					class="border-2 border-sky-500 rounded-lg w-11/12 h-52 text-wrap"
					on:click={(e) => {
						goto(`/category/${categoryId}/product/${product.productId}`);
					}}
				>
					<div
						class="bg-cover bg-center w-full h-3/4 overflow-clip"
						style={`background-image: url(${product.imageUrls[0]})`}
					/>
					<div class="truncate text-center">{product.name}</div>
					<div class="text-md">{product.price}</div>
				</button>
			{/each}
		{/if}
	</div>

	<div class="my-40" />

	<div class="fixed bottom-16 left-0 w-full place-items-center grid grid-cols-1 grid-rows-1">
		<div class="grid grid-cols-1 px-2 pt-2 pb-4 place-items-center bg-white rounded-lg border">
			<Pagination totalCount={totalProductCount} onPageNumberChange={onPageChange} />
		</div>
	</div>

	<BottomBar />

	<div class="my-12" />
</div>
