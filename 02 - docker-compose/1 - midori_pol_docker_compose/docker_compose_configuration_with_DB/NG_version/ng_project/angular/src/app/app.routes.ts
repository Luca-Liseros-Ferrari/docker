import { Routes } from '@angular/router';
import { ProductListComponent } from './product-list/product-list.component';
// import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { SuccessComponent } from './success/success.component';
import { CancelComponent } from './cancel/cancel.component';

export const routes: Routes = [
  { path: '', component: ProductListComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'success', component: SuccessComponent },
  { path: 'cancel', component: CancelComponent },
  { path: '', redirectTo: '', pathMatch: 'full' },
  { path: '**', redirectTo: '', pathMatch: 'full' }
  /*
  { path: 'cart', component: CartComponent },
  { path: 'midoripol/carrello/add', component: CartComponent },
  { path: 'midoripol/carrello/less', component: CartComponent },
  { path: '', redirectTo: 'midoripol/beats', pathMatch: 'full' },
  */
];
