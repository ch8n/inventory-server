<script lang="ts">
	import * as Pagination from '$lib/components/ui/pagination';
	import { ChevronLeft, ChevronRight } from 'lucide-svelte';
	export let totalCount: number;
	export let onPageNumberChange = (pageNumber: number) => {};

	const onClickPagerNumbers = (pageNumber: number) => {
		onPageNumberChange(pageNumber);
	};
</script>

<Pagination.Root count={totalCount} perPage={1} let:pages let:currentPage>
	<Pagination.Content>
		{#each pages as page (page.key)}
			{#if page.type === 'ellipsis'}
				<Pagination.Item>
					<Pagination.Ellipsis />
				</Pagination.Item>
			{:else}
				<div
					on:click={(e) => {
						onClickPagerNumbers(currentPage ?? 1);
					}}
				>
					<Pagination.Item>
						<Pagination.Link {page} isActive={currentPage == page.value}>
							{page.value}
						</Pagination.Link>
					</Pagination.Item>
				</div>
			{/if}
		{/each}
	</Pagination.Content>
</Pagination.Root>
