<script lang="ts">
	import CartComponent from '$lib/components/sections/cart-component.svelte';
	import OfferCarousels from '$lib/components/sections/carousel-offers.svelte';
	import { page } from '$app/stores';
	import type { OfferPagerItem, Product } from '$lib/data/HomePage';
	import { onMount } from 'svelte';
	import { cart, type CartItem } from '$lib/stores/cartStore';
	import { goto, } from '$app/navigation';
	import { Button } from '$lib/components/ui/button';
	import { ShoppingCart, ShoppingBag, ArrowBigLeft } from 'lucide-svelte';
	import { browser } from "$app/environment";


	let productId = $page.params.id;

	let product: Product | null = null;
	let bannerImages: OfferPagerItem[] = [];
	let similarProducts: Product[] = [];

	$: {
		if (product === null) {
			onFetchProduct(productId);
		} else {
			getPagerItems(product);
			onFetchVarients(product);
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

	const onFetchProduct = async (productId:string) => {
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

	const onFetchVarients = async (product: Product) => {
		product.variants.forEach(async (id) => {
			try {
				const response = await fetch(`http://0.0.0.0:8080/v1/products?id=${id}`);
				if (response.ok) {
					let _response: any = await response.json();
					let data = _response.data[0];
					let product = {
						productId: data.productId,
						name: data.name,
						description: data.description,
						price: data.price,
						stockQuantity: data.stockQuantity,
						imageUrls: data.imageUrls,
						categoryId: data.categoryId,
						variants: data.variants
					} as Product;
					similarProducts = [...similarProducts, product];
					console.log(product);
				} else {
					console.error('Failed to fetch data:', response.status, response.statusText);
				}
			} catch (error) {
				console.error('Error during fetch:', error.message);
			}
		});
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

<div>
	<div
		class="fixed top-0 left-0 w-full bg-gray-800 text-black p-4 flex justify-between z-10"
	>
		<Button
			variant="outline"
			class="center px-2"
			on:click={() => {
				if (browser) window.history.back();
			}}
		>
			<ArrowBigLeft class="h-6 w-6" color="#000000" />
		</Button>

		<Button
			variant="outline"
			class="center px-2 self-end"
			on:click={() => {
				goto(`/cart`)
			}}
		>
			<ShoppingCart class="h-6 w-6" color="#000000" />
		</Button>
	</div>

	<div class="mx-auto px-6 py-20">
		<OfferCarousels offerPagerItem={bannerImages} onOfferClicked={() => {}} />

		<p class="text-xl">{product?.name ?? 'Name'}</p>
		<p class="text-lg my-2">{product?.price ?? 'Price'}</p>
		<p class="text-base my-2">{product?.description ?? 'Description'}</p>

		<div
			class="fixed bottom-0 left-0 w-full place-items-center bg-gray-800 text-black p-4 grid grid-cols-2 grid-rows-1"
		>
			<Button
				variant="outline"
				class="center px-2 self-end"
				on:click={() => {
					goto(`/cart`);
				}}
			>
				<ShoppingBag class="h-6 w-6" color="#000000" />
				<p class="px-2">Checkout</p>
			</Button>

			<Button
				variant="outline"
				class="center px-2 self-end"
				on:click={() => {
					addToCart();
				}}
			>
				<ShoppingCart class="h-6 w-6" color="#000000" />
				<p class="px-2">Add to Cart</p>
			</Button>
		</div>

		<div class="border mt-4 mb-2" />

		<p class="text-lg my-4">Similar Products</p>

		<div class="grid grid-cols-2 gap-4">
			{#each similarProducts as product}
				<button
					class="border-2 border-sky-500 rounded-lg w-36 h-52 text-wrap"
					on:click={(e) => {
						console.log(product);
						console.log(`/category/${product.categoryId}/product/${product.productId}`);
						goto(`/category/${product.categoryId}/product/${product.productId}`);
					}}
				>
					<div
						class="bg-cover bg-center w-full h-3/4 overflow-clip"
						style={`background-image: url(${product.imageUrls[0]})`}
					/>
					<div class="truncate text-center">{product.name}</div>
					<div class="text-md text-center">{product.price}</div>
				</button>
			{/each}
		</div>

		<div class="py-10" />
	</div>
</div>
