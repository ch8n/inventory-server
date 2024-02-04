<script lang="ts">
	import CartComponent from '$lib/components/sections/cart-component.svelte';
	import OfferCarousels from '$lib/components/sections/carousel-offers.svelte';
	import { page } from '$app/stores';
	import type { OfferPagerItem, Product } from '$lib/data/HomePage';
	import { onMount } from 'svelte';
	import { cart, type CartItem } from '$lib/stores/cartStore';

	let productId = $page.params.id;

	let product: Product | null = null;
	let bannerImages: OfferPagerItem[] = [];

	$: {
		onFetchProduct();
		if (product !== null) {
			getPagerItems(product);
		}
	}

	const getPagerItems = (product: Product) => {
		let id = 0;
		bannerImages = product.imageUrls.map((it) => {
			id += 1;
			return {
				offerId: id.toString(),
				bannerUrl: it
			} as OfferPagerItem;
		});
	};

	const onFetchProduct = async () => {
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
	};

	const addToCart = () => {
		let currentCart = $cart;

		const existingItemIndex = currentCart.findIndex(
			(item) => item.productId === product?.productId
		);

		if (existingItemIndex !== -1) {
			let current = currentCart[existingItemIndex];
			currentCart[existingItemIndex] = { ...current, quantity: current.quantity + 1 } as CartItem;
		} else {
			// Item doesn't exist, add it to the array
			currentCart = [
				...currentCart,
				{
					productId: product?.categoryId,
					quantity: 1
				} as CartItem
			];
		}

		cart.set(currentCart);
	};
</script>

<div class="mx-auto px-6 py-6">
	<OfferCarousels offerPagerItem={bannerImages} onOfferClicked={() => {}} />

	<p class="text-xl">Product {productId}</p>
	<p>Name - {product?.name}</p>

	<button
		on:click={(e) => {
			addToCart();
		}}>Add to cart</button
	>

	<CartComponent />
</div>
