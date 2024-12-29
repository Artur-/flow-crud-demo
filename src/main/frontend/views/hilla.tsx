import { ViewConfig } from "@vaadin/hilla-file-router/types.js";
import { ProductCrudRepositoryService } from "Frontend/generated/endpoints";
import { Grid, GridSortColumn } from "@vaadin/react-components";
import { useDataProvider } from "@vaadin/hilla-react-crud";

export const config: ViewConfig = { menu: { title: "Hilla" } };

export default function Hilla() {
  return (
    <Grid dataProvider={useDataProvider(ProductCrudRepositoryService).dataProvider}>
      <GridSortColumn path="name" header="Name" />
      <GridSortColumn path="description" header="Description" />
      <GridSortColumn path="price" header="Price" />
      <GridSortColumn path="stockQuantity" header="Stock Quantity" />
    </Grid>
  );
}
