<script lang="ts">
	import * as Carousel from '$lib/components/ui/carousel/index.js';
	import type { OfferPagerItem } from '$lib/data/HomePage';
	import Autoplay from 'embla-carousel-autoplay';
	const plugin = Autoplay({ delay: 2000, stopOnInteraction: true });
	export let offerPagerItem: OfferPagerItem[] = [];
	export const onOfferClicked = (offerUrl: OfferPagerItem) => {};
</script>

<div class="my-4">
	{#if offerPagerItem.length != 0}
		<div>
			<Carousel.Root
				plugins={[plugin]}
				class="w-full max-w-xs"
				on:mousenter={plugin.stop}
				on:mouseleave={plugin.reset}
			>
				<Carousel.Content>
					{#each offerPagerItem as pageItem}
						<Carousel.Item>
							<div class="h-64 border border-gray-400 rounded-lg overflow-clip">
								<div
									on:click={(e) => {
										onOfferClicked(pageItem);
									}}
									class="bg-cover bg-center w-full h-full"
									style={`background-image: url(${pageItem.bannerUrl})`}
								/>
							</div>
						</Carousel.Item>
					{/each}
				</Carousel.Content>
				<Carousel.Previous class="mx-8" />
				<Carousel.Next class="mx-8" />
			</Carousel.Root>
		</div>
	{/if}
</div>
