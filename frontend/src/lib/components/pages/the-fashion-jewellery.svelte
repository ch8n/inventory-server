<script lang="ts">
	import OfferCarousels from '$lib/components/sections/carousel-offers.svelte';

	import CategoryGrid from '$lib/components/sections/category-grid.svelte';
	import Title from '$lib/components/sections/title.svelte';

	let offerUrls: string[] = [];
	const onOfferClicked = (url: string) => {};
</script>

<div class="py-4">
	<Title label={'The Fashion Jewellery'} />

	<OfferCarousels {offerUrls} {onOfferClicked} />

	<div class="py-4">
		<div class="text-xl bold">Explore</div>
		<div class="text-grey-900 my-4">
			<CategoryGrid />
		</div>
	</div>
</div>
