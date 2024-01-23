<script lang="ts">
	import BottomBar from '$lib/components/sections/bottom-bar.svelte';
	import Pagination from '$lib/components/sections/pagination.svelte';

	import OfferCarousels from '$lib/components/sections/carousel-offers.svelte';

	import CategoryGrid from '$lib/components/sections/category-grid.svelte';
	import Title from '$lib/components/sections/title.svelte';
	import type { OfferPagerItem } from '$lib/data/HomePage';
	import { onMount } from 'svelte';

	let offerPagerItem: OfferPagerItem[] = [];
	const onOfferClicked = (offerItem: OfferPagerItem) => {
		console.log(offerItem);
	};

	onMount(async () => {
		try {
			const response = await fetch(`http://0.0.0.0:8080/v1/offers`);
			if (response.ok) {
				let _response: any = await response.json();
				console.log('offer response ===>');
				console.log(_response);
				offerPagerItem = _response.data.map((it: any) => {
					let item: OfferPagerItem = {
						offerId: it.offerId,
						bannerUrl: it.bannerUrl
					};
					return item;
				});
				console.log('offerUrls mapped===>');
				console.log(offerPagerItem);
			} else {
				console.error('Failed to fetch data:', response.status, response.statusText);
			}
		} catch (error) {
			console.error('Error during fetch:', error.message);
		}
	});
</script>

<div class="py-4">
	<Title label={'The Fashion Jewellery'} />

	<OfferCarousels {offerPagerItem} {onOfferClicked} />

	<Title label={'Categories'} />

	<CategoryGrid />


	<BottomBar />

	<div class="my-12" />
</div>
