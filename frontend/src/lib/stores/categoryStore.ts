import { writable, type Writable } from 'svelte/store';

export type Category = {
	categoryId: number;
	categoryName: string;
};

export const categories: Writable<Category[]> = writable([]);
