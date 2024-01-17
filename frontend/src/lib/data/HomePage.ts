export enum HomePageTab {
	Women = 'Women',
	Men = 'Men'
}

export type Category = {
	categoryId: string;
	categoryName: string;
};

export type Product = {
	productId: number;
	name: string;
	description: string;
	price: number;
	stockQuantity: number;
	imageUrls: string[];
	categoryId: number;
	variants: number[];
};

