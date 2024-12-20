import { ViewConfig } from '@vaadin/hilla-file-router/types.js';

export const config: ViewConfig = { menu: { title: 'Hilla' }};

export default function Hilla() {
    return <Grid dataProvider={useDataProvider(ProductService).dataProvider}>
        <GridSortColumn path="name" />
        <GridSortColumn path="price" />
        <GridSortColumn path="category" />
        

    </Grid>)
}
