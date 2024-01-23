<script lang="ts">
	import * as Pagination from '$lib/components/ui/pagination';
	import { ChevronLeft, ChevronRight } from 'lucide-svelte';

	let count = 20;
	$: perPage = 3;
	$: siblingCount = 1;
</script>

<Pagination.Root {count} {perPage} {siblingCount} let:pages let:currentPage>
	<Pagination.Content>
		
        <Pagination.Item>
			<Pagination.PrevButton>
				<ChevronLeft class="h-4 w-4" />
			</Pagination.PrevButton>
		</Pagination.Item>

		{#each pages as page (page.key)}
			{#if page.type === 'ellipsis'}
				<Pagination.Item>
					<Pagination.Ellipsis />
				</Pagination.Item>
			{:else}
				<Pagination.Item>
					<Pagination.Link {page} isActive={currentPage == page.value}>
						{page.value}
					</Pagination.Link>
				</Pagination.Item>
			{/if}
		{/each}
		
        <Pagination.Item>
			<Pagination.NextButton>
				<ChevronRight class="h-4 w-4" />
			</Pagination.NextButton>
		</Pagination.Item>
        
	</Pagination.Content>
</Pagination.Root>
